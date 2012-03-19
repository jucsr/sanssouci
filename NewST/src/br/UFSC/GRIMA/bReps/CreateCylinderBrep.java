package br.UFSC.GRIMA.bReps;
import javax.vecmath.*;

public class CreateCylinderBrep 
{
	public Point3d [] vertexArray;
	public int [] indexArray;
	public Color3f [] color3f;
	
	private String name;
	private float radius;
	private float depth;
	private int numberOfPointsInCircle = 4 * 4;
	
	public CreateCylinderBrep(String name, float radius, float depth)
	{
		this.name = name;
		this.radius = radius;
		this.depth = depth;
		generateVertexArray();
		generateIndexArray();
		generateColorArray();
	}
	public CreateCylinderBrep(String name, float radius, float depth, int numberOfPoints)
	{
		this.name = name;
		this.radius = radius;
		this.depth = depth;
		this.numberOfPointsInCircle = numberOfPoints;
		this.generateVertexArray();
		this.generateIndexArray();
		this.generateColorArray();
	}
	public CreateCylinderBrep(String name, float radius, float depth, int numberOfPoints, Color3f color3f)
	{
		this.name = name;
		this.radius = radius;
		this.depth = depth;
		this.numberOfPointsInCircle = numberOfPoints;
		this.generateVertexArray();
		this.generateIndexArray();
		this.generateColorArray(color3f);
	}
	private void generateIndexArray() 
	{
		indexArray=new int[(numberOfPointsInCircle)*12];
        int flag=0;
        for (int i=0;i<numberOfPointsInCircle-1;i++)
        {
            indexArray[3*i]=0;    
            indexArray[3*i+2]=i+1;
            indexArray[3*i+1]=i+2;

            indexArray[3*i+3*numberOfPointsInCircle]=vertexArray.length/2;    
            indexArray[3*i+3*numberOfPointsInCircle+1]=i+1+vertexArray.length/2;
            indexArray[3*i+3*numberOfPointsInCircle+2]=i+2+vertexArray.length/2;
            flag=3*i+2+3*numberOfPointsInCircle;
            //System.out.println(indexArray[3*i] + " " + indexArray[3*i+1]+ " " + indexArray[3*i+2]);
            //System.out.println(indexArray[3*i+3*numberOfPointsInCircle] + " " + indexArray[3*i+1+3*numberOfPointsInCircle]+ " " + indexArray[3*i+2+3*numberOfPointsInCircle]);
            //System.out.println("Last index " + flag);
        }        
        
        indexArray[3*(numberOfPointsInCircle-1)]=0;    
        indexArray[3*(numberOfPointsInCircle-1)+2]=vertexArray.length/2-1;
        indexArray[3*(numberOfPointsInCircle-1)+1]=1;
        
        indexArray[3*(numberOfPointsInCircle-1)+3*numberOfPointsInCircle]=vertexArray.length/2;    
        indexArray[3*(numberOfPointsInCircle-1)+1+3*numberOfPointsInCircle]=vertexArray.length-1;
        indexArray[3*(numberOfPointsInCircle-1)+2+3*numberOfPointsInCircle]=vertexArray.length/2+1;
        
        //System.out.println(indexArray[3*(numberOfPointsInCircle-1)] + " " + indexArray[3*(numberOfPointsInCircle-1)+1]+ " " + indexArray[3*(numberOfPointsInCircle-1)+2]);
        //System.out.println(indexArray[3*(numberOfPointsInCircle-1)+3*numberOfPointsInCircle] + " " + indexArray[3*(numberOfPointsInCircle-1)+1+3*numberOfPointsInCircle]+ " " + indexArray[3*(numberOfPointsInCircle-1)+2+3*numberOfPointsInCircle]);
        flag=3*(numberOfPointsInCircle-1)+2+3*numberOfPointsInCircle;
        //System.out.println("Last index " + flag);
        
        //generate index of side
        
       // System.out.println( "-->indexArray.length = " + indexArray.length);
        for (int i = 0; i < vertexArray.length/2 - 2; i++)
        {
            indexArray[indexArray.length/2 + (3 * i)] = 1 + i;
            indexArray[indexArray.length/2 + (3 * i) + 1] = i + 2;
            indexArray[indexArray.length/2 + (3 * i) + 2] =  vertexArray.length/2 + 1 + i;
            
            indexArray[indexArray.length * 3/4 + (3 * i)] = 2 + i;
            indexArray[indexArray.length * 3/4 + (3 * i) + 1] = vertexArray.length/2 + 2 + i;
            indexArray[indexArray.length * 3/4 + (3 * i) + 2] = vertexArray.length/2 + 1 + i;
        }
        
        indexArray[indexArray.length * 3/4 - 3] = vertexArray.length/2 - 1;
        indexArray[indexArray.length * 3/4 - 2] = 1;
        indexArray[indexArray.length * 3/4 - 1] = vertexArray.length - 1;
        
        indexArray[indexArray.length - 3] = 1;
        indexArray[indexArray.length - 2] = vertexArray.length/2 + 1;
        indexArray[indexArray.length - 1] = vertexArray.length - 1;
        
        /*for (int i = 0; i < indexArray.length; i++)
        {
        	System.out.println( i + "-->indexArray = " + indexArray[i]);
        }*/
	}
	private void generateColorArray() 
	{
		color3f = new Color3f [vertexArray.length];
		for (int i = 0; i < color3f.length; i++)
		{
			color3f [i] = new Color3f(0.3f, 0.3f, 0.3f);
		}		
	}
	private void generateColorArray(Color3f color3f)
	{
		this.color3f = new Color3f[vertexArray.length];
		for(int i = 0; i < this.color3f.length; i++)
		{
			this.color3f[i] = color3f;
		}
	}
	private void generateVertexArray() 
	{
		Point3d [] temp = new Point3d[numberOfPointsInCircle];
		//generate top points
			//center of top circle
		vertexArray = new Point3d[(numberOfPointsInCircle + 1) * 2];
		vertexArray[0] = new Point3d(0, depth/2, 0);
			//generate interpolation points in top
		temp = determinatePointsInCircunference(0, 2 * Math.PI, radius, numberOfPointsInCircle, depth/2);
		for (int i = 0; i < temp.length; i++)
		{
			vertexArray[i+1] = temp[i];
		}
		//generate bottom points
		//translate all points in top to y = -depth/2
		for(int i = 0; i < vertexArray.length/2; i++)
		{
			vertexArray[i + vertexArray.length/2] = new Point3d();
			vertexArray[i + vertexArray.length/2].setX(vertexArray[i].getX());
			vertexArray[i + vertexArray.length/2].setY(-depth/2);
			vertexArray[i + vertexArray.length/2].setZ(vertexArray[i].getZ());
		}
		
		//verification
		/*for(int i = 0; i < vertexArray.length; i++)
		{
			System.out.println("element [" + i + "] = [" + vertexArray[i].x + ", "+ vertexArray[i].y + ", " + vertexArray[i].z + "]");
		}*/
	}
	public Point3d [] determinatePointsInCircunference(double initialAngle, double deltaAngle, double radius, int numberOfPoints, double yPlane)
	{
		Point3d[] output = new Point3d [numberOfPoints];
		double x, z;
		double dAngle = 0;
		dAngle = deltaAngle / numberOfPoints;
		for(int i = 0; i < numberOfPoints; i++)
		{
			x = radius * Math.cos(initialAngle + i * dAngle);
			z = radius * Math.sin(initialAngle  + i * dAngle);
			output[i] = new Point3d((float)x,(float)yPlane, (float)z);
		}
		return output;
	}
}
