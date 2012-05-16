package br.UFSC.GRIMA.bReps;

import javax.vecmath.Point3d;
/**
 * 
 * @author Jc
 *
 */
public class BezierSurface
{
	private Point3d [][] meshArray;
	private Point3d [][] controlVertexArray;
	private int splitU = 40;
	private int splitV = 40;
	
	public BezierSurface(Point3d [][] controlVertexArray, int splitU, int splitV)
	{
		this.controlVertexArray = controlVertexArray;
		this.splitU = splitU;
		this.splitV = splitV;
		this.meshArray = this.generatePointsInSurface();
	}
	
	private Point3d[][] generatePointsInSurface()
	{
		Point3d [][] saida = new Point3d[splitU][splitV];
		int n = controlVertexArray.length - 1;
		int m = controlVertexArray[0].length - 1;
		double u, v;
		for (int i = 0; i < splitU; i++)
		{			
			u = i * Math.pow(splitU - 1, -1);
			
			for (int j = 0; j < splitV; j++)
			{
				v = j * Math.pow(splitV - 1, -1);

				double x = 0, y = 0, z = 0;
				for (int k = 0; k < controlVertexArray.length; k++)
				{
					for (int l = 0; l < controlVertexArray[k].length; l++)
					{
						x = x + controlVertexArray[k][l].x * (Math.pow(1 - u, n - l) * calculateC(n, l) * Math.pow(u, l)) * (Math.pow(1 - v, m - k) * calculateC(m, k) * Math.pow(v, k));
						y = y + controlVertexArray[k][l].y * (Math.pow(1 - u, n - l) * calculateC(n, l) * Math.pow(u, l)) * (Math.pow(1 - v, m - k) * calculateC(m, k) * Math.pow(v, k));
						z = z + controlVertexArray[k][l].z * (Math.pow(1 - u, n - l) * calculateC(n, l) * Math.pow(u, l)) * (Math.pow(1 - v, m - k) * calculateC(m, k) * Math.pow(v, k));
						
					}
				}
				saida[i][j] = new Point3d(x, y, z);
			}
		}
		return saida;
	}
	/**
	 * 
	 * @param nI	--> number of control points - 1
	 * @param i 	--> index of the control point i
	 * @return 		--> multiply factor C (necessary in Bezier equation)
	 */
	private double calculateC(int nI, int i)
	{
		double result = calculateFactorial(nI)/(calculateFactorial(i) * calculateFactorial(nI - i));
		return result;
	}
	/**
	 * 
	 * @param n --> number which we want to calculate the factorial (n!)
	 * @return Factorial of n
	 */
	private double calculateFactorial(int n)
	{
		int factorial = 1;  
		  
		for( int k = 2; k <= n; k++ )  
		{   
		     factorial *= k;  
		}  
		//System.out.println("fatorial = " + factorial);
		return factorial;
	}

	public Point3d[][] getMeshArray() 
	{
		return meshArray;
	}
}
