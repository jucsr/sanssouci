package br.UFSC.GRIMA.bReps;
import javax.vecmath.*;

/**
 * 
 * @author Jc
 *
 */
public class CreateBezierLinearPath 
{
	public Point3d[] vertexArray;
	public int [] indexArray;
	public Color3f[] color3f;
	
	private String name;
	private Point3d[] controlPoints;
	private float pathDisplacement;
	private Bezier_1 bezier;
	//private int numberOfPointsToInterpolate = 7;
	public CreateBezierLinearPath(String name, Point3d[] controlPoints)
	{
		this.name = name;
		this.controlPoints = controlPoints;
		//this.pathDisplacement = pathDisplacement;
		
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
	private void generateVertex() 
	{
		bezier = new Bezier_1(controlPoints);
		vertexArray = new Point3d[bezier.meshArray.length * 2 * 2];
		
		for (int i = 0; i < bezier.meshArray.length; i++)
		{
			//generate vertex in top face
			vertexArray[i] = new Point3d(bezier.meshArray[i].x, 0, bezier.meshArray[i].z);
			vertexArray[bezier.meshArray.length * 2 + i] = new Point3d(bezier.meshArray[i].x, 0, - bezier.meshArray[i].z);
		
			//generate vertex in floor
			vertexArray[bezier.meshArray.length + i] = bezier.meshArray[bezier.meshArray.length - 1 - i];
			vertexArray[bezier.meshArray.length * 3 + i] = new Point3d(bezier.meshArray[bezier.meshArray.length - 1 - i].x, bezier.meshArray[bezier.meshArray.length - 1 - i].y, - bezier.meshArray[bezier.meshArray.length - 1 - i].z);
		}
		//verification
		/*for (int i = 0; i < vertexArray.length; i++)
		{
			System.out.println(i + " vertexArray = " + vertexArray[i]);
		}*/
		
	}
	private void generateIndex() 
	{
		indexArray = new int[3 * (bezier.meshArray.length - 1) * 2 * 4 + 3 * 4];
		
		for (int i = 0; i < bezier.meshArray.length * 2 - 1; i++)
		{
			//generate index for bounds
			indexArray[3 * i] = i;
			indexArray[3 * i + 1] = i + 1;
        	indexArray[3 * i + 2] = bezier.meshArray.length * 2 + i;
			
        	indexArray[3 * bezier.meshArray.length * 2 + i * 3] = i + 1;
			indexArray[3 * bezier.meshArray.length * 2 + i * 3 + 1] = bezier.meshArray.length * 2 + i + 1 ;
        	indexArray[3 * bezier.meshArray.length * 2 + i * 3 + 2] = bezier.meshArray.length * 2 + i;
        	
			
		}
		
		for (int i = 0; i < bezier.meshArray.length - 1; i++)
		{
			//generate index for side face (front)
        	indexArray[3 * bezier.meshArray.length * 4 + i * 3] = i;
        	indexArray[3 * bezier.meshArray.length * 4 + i * 3 + 2] = i + 1;
        	indexArray[3 * bezier.meshArray.length * 4 + i * 3 + 1] = bezier.meshArray.length * 2 - 1 - i;

			indexArray[3 * bezier.meshArray.length * 2 * 2 + 3 * (bezier.meshArray.length - 1) + i * 3] = i + 1;
			indexArray[3 * bezier.meshArray.length * 2 * 2 + 3 * (bezier.meshArray.length - 1) + i * 3 + 1] = bezier.meshArray.length * 2 - 1 - i;
			indexArray[3 * bezier.meshArray.length * 2 * 2 + 3 * (bezier.meshArray.length - 1) + i * 3 + 2] = bezier.meshArray.length * 2 - 2 - i;
			
			//generate index for side face (rear)

			indexArray[3 * bezier.meshArray.length * 2 * 2 + 3 * (bezier.meshArray.length - 1) * 2 + i * 3] = bezier.meshArray.length * 2 + i;
			indexArray[3 * bezier.meshArray.length * 2 * 2 + 3 * (bezier.meshArray.length - 1) * 2 + i * 3 + 1] = bezier.meshArray.length * 2 + i + 1;
			indexArray[3 * bezier.meshArray.length * 2 * 2 + 3 * (bezier.meshArray.length - 1) * 2 + i * 3 + 2] = bezier.meshArray.length * 4 - 1 - i;
			
			indexArray[3 * bezier.meshArray.length * 2 * 2 + 3 * (bezier.meshArray.length - 1) * 3 + i * 3] = bezier.meshArray.length * 2 + i + 1;
			indexArray[3 * bezier.meshArray.length * 2 * 2 + 3 * (bezier.meshArray.length - 1) * 3 + i * 3 + 1] = bezier.meshArray.length * 4 - i - 2;
			indexArray[3 * bezier.meshArray.length * 2 * 2 + 3 * (bezier.meshArray.length - 1) * 3 + i * 3 + 2] = bezier.meshArray.length * 4 - i - 1;
		}
		
		//special last triangles
		indexArray[3 * bezier.meshArray.length * 2 - 3] = 0;
		indexArray[3 * bezier.meshArray.length * 2 - 1] = bezier.meshArray.length * 2 - 1;
		indexArray[3 * bezier.meshArray.length * 2 - 2] = bezier.meshArray.length * 4 - 1;
		
		indexArray[3 * bezier.meshArray.length * 2 * 2 - 3] = 0;
		indexArray[3 * bezier.meshArray.length * 2 * 2  - 2] = bezier.meshArray.length * 2;
		indexArray[3 * bezier.meshArray.length * 2 * 2 - 1] = bezier.meshArray.length * 4 - 1;
		
		//verification
		/*for (int i = 0; i < indexArray.length; i++)
		{
			System.out.println(i + " indexArray = " + indexArray[i]);
		}*/
	}
}
