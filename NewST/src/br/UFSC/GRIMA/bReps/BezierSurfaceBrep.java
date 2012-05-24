package br.UFSC.GRIMA.bReps;

import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

/**
 * 
 * @author Jc
 *
 */
public class BezierSurfaceBrep 
{
	public Point3d[] vertexArray;
	public int [] indexArray;
	public Color3f[] color3f;
	
	private String name;
	private Point3d[][] controlVertex;
	private BezierSurface bezierSurface;
	private int splitU, splitV;
	
	public BezierSurfaceBrep(String name, Point3d[][] controlVertex, int splitU, int splitV)
	{
		this.controlVertex = controlVertex;
		this.name = name;
		this.splitU = splitU;
		this.splitV = splitV;
		this.bezierSurface = new BezierSurface(this.controlVertex, this.splitU, this.splitV);
		generateVertex();
		generateIndex();
		generateColors();
	}
	public BezierSurfaceBrep(String name, Point3d[][] controlVertex, int splitU, int splitV, Color3f color)
	{
		this.controlVertex = controlVertex;
		this.name = name;
		this.splitU = splitU;
		this.splitV = splitV;
		this.bezierSurface = new BezierSurface(this.controlVertex, this.splitU, this.splitV);
		generateVertex();
		generateIndex();
		generateColors();
	}
	private void generateColors() 
	{
		color3f = new Color3f [vertexArray.length];
		for (int i = 0; i < vertexArray.length; i++)
		{
			color3f [i] = new Color3f(0.3f, 0.3f, 0.3f);
		}
	}

	private void generateIndex() 
	{
		int n = bezierSurface.getMeshArray().length;
		int m = bezierSurface.getMeshArray()[0].length;
		indexArray = new int[3 * ((n - 1) * 2 * (m - 1) * 2 + (n - 1) * 2 * 2 + (m - 1) * 2 * 2)];
		int a = 0, b =  (n - 1) * (m - 1) * 3, c = (n - 1) * (m -1) * 3 * 2, d = (n - 1) * (m -1) * 3 * 3;
		for(int i = 0; i < n - 1; i++)
		{
			for(int j = 0; j < m - 1; j++)
			{
				// ===== index of surface ===

				indexArray[a] = m * i + j;
				a++;
				indexArray[a] = m * i + j + 1;
				a++;
				indexArray[a] = (i + 1) *  m + j;
				a++;
				
				indexArray[b] = m * i + j + 1;
				b++;
				indexArray[b] = (i + 1) *  m + j + 1;
				b++;
				indexArray[b] = (i + 1) *  m + j;
				b++;
				
//				indexArray[c] = m * i + j + m * n;
//				c++;
//				indexArray[c] = m * i + j + 1 + m * n;
//				c++;
//				indexArray[c] = (i + 1) *  m + j + m * n;
//				c++;
//				
//				indexArray[d] = m * i + j + 1 + m * n;
//				d++;
//				indexArray[d] = (i + 1) *  m + j + 1 + m * n;
//				d++;
//				indexArray[d] = (i + 1) *  m + j + m * n;
//				d++;
				
				
				indexArray[c] = m * i + j + m * n;
				c++;
				indexArray[c] = (i + 1) *  m + j + m * n;
				c++;
				indexArray[c] = m * i + j + 1 + m * n;
				c++;
				
				indexArray[d] = m * i + j + 1 + m * n;
				d++;
				indexArray[d] = (i + 1) *  m + j + m * n;
				d++;
				indexArray[d] = (i + 1) *  m + j + 1 + m * n;
				d++;
				
			}
		}
		int e = (n - 1) * (m -1) * 3 * 4, f = (n - 1) * (m -1) * 3 * 4 + (n - 1) * 3,
		g = (n - 1) * (m -1) * 3 * 4 + (n - 1) * 3 * 2, h = (n - 1) * (m -1) * 3 * 4 + (n - 1) * 3 * 3;
		for (int i = 0; i < n - 1; i++) 
		{
			// lados

			indexArray[e] = i * m;
			e++;
			indexArray[e] = i * m + m;
			e++;
			indexArray[e] = i * m + n * m;
			e++;

			indexArray[f] = (i + 1) * m;
			f++;
			indexArray[f] = (i + 1) * m + m * n;
			f++;
			indexArray[f] = i * m + n * m;
			f++;
			
			indexArray[g] = (i + 2) * m - 1;
			g++;
			indexArray[g] = (i + 1) * m - 1;
			g++;
			indexArray[g] =(i + 1) * m - 1 + m * n;
			g++;
			
			indexArray[h] = (i + 2) * m - 1;
			h++;
			indexArray[h] = (i + 1) * m - 1 + m * n;
			h++;
			indexArray[h] = (i + 2) * m - 1 + m * n;
			h++;
		}
			
			int o = (n - 1) * (m -1) * 3 * 4 + (n - 1) * 3 * 4, p = (n - 1) * (m -1) * 3 * 4 + (n - 1) * 3 * 4 + (m - 1) * 3,
			q = (n - 1) * (m -1) * 3 * 4 + (n - 1) * 3 * 4 + (m - 1) * 3 * 2, r = (n - 1) * (m -1) * 3 * 4 + (n - 1) * 3 * 4 + (m - 1) * 3 * 3;
		for (int i = 0; i < m - 1; i++) 
		{
			// lados
//			indexArray[o] = i;
//			o++;
//			indexArray[o] = i + 1;
//			o++;
//			indexArray[o] = n * m + i;
//			o++;
//
//			indexArray[p] = i + 1;
//			p++;
//			indexArray[p] = n * m + i + 1;
//			p++;
//			indexArray[p] = n * m + i;
//			p++;

			indexArray[o] = i;
			o++;
			indexArray[o] = n * m + i;
			o++;
			indexArray[o] = i + 1;
			o++;

			indexArray[p] = i + 1;
			p++;
			indexArray[p] = n * m + i;
			p++;
			indexArray[p] = n * m + i + 1;
			p++;
			
			
			
			indexArray[q] = m * (n - 1) + i;
			q++;
			indexArray[q] = m * (n - 1) + i + 1;
			q++;
			indexArray[q] = m * n + m * (n - 1) + i;
			q++;

			indexArray[r] = m * (n - 1) + i + 1;
			r++;
			indexArray[r] = m * n + m * (n - 1) + i;
			r++;
			indexArray[r] = m * n + m * (n - 1) + i + 1;
			r++;
		}
	}
	private void generateVertex() 
	{
		vertexArray = new Point3d[bezierSurface.getMeshArray().length * bezierSurface.getMeshArray()[0].length * 2];
		int a = 0;
		for(int i = 0; i < bezierSurface.getMeshArray().length; i++)
		{
			for(int j = 0; j < bezierSurface.getMeshArray()[i].length; j++)
			{				
				/**
				 * 	Generating surface
				 */
				vertexArray[a] = new Point3d(bezierSurface.getMeshArray()[i][j].x, bezierSurface.getMeshArray()[i][j].y, bezierSurface.getMeshArray()[i][j].z);
				/**
				 * 	Generating Floor
				 */
				vertexArray[bezierSurface.getMeshArray().length * bezierSurface.getMeshArray()[0].length + a] = new Point3d(bezierSurface.getMeshArray()[i][j].x, bezierSurface.getMeshArray()[i][j].y, -bezierSurface.getMeshArray()[i][j].z);
				a++;
			}
		}
	}
}
