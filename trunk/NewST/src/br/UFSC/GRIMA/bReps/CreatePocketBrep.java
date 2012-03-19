package br.UFSC.GRIMA.bReps;
import javax.vecmath.*;

public class CreatePocketBrep 
{
    public Point3d [] vertexArray;
    public int [] indexArray;
    public Color3f[] color3f;
    private int numberOfPointsInCircle = 5;
    private float d1, d2, depth, radius;
    private String name;
    
    public CreatePocketBrep(String name, float d1, float d2, float depth, float radius)
    {
    	this.name = name;
    	this.d1 = d1;
    	this.d2 = d2;
    	this.depth = depth;
    	this.radius = radius;
        generateVertexPocket();
        generateIndexArray(); 
        generateColorArray();
    }
    private void generateColorArray()
    {
		color3f = new Color3f [vertexArray.length];
		for (int i = 0; i < color3f.length; i++)
		{
			color3f [i] = new Color3f(0.3f, 0.3f, 0.3f);
		}		
	}
	private void generateVertexPocket()
    {
    	vertexArray = new Point3d[(numberOfPointsInCircle * 4 + 1) * 2];
    	
    	vertexArray[0] = new Point3d(0, depth/2, 0);
    	Point3d [] arcPoints1 = new Point3d[numberOfPointsInCircle];
    	Point3d [] arcPoints2 = new Point3d[numberOfPointsInCircle];
    	Point3d [] arcPoints3 = new Point3d[numberOfPointsInCircle];
    	Point3d [] arcPoints4 = new Point3d[numberOfPointsInCircle];
    	
		arcPoints1 = determinatePointsInCircunference(0, -Math.PI/2, radius, numberOfPointsInCircle, depth/2);
		arcPoints2 = determinatePointsInCircunference(-Math.PI/2, -Math.PI/2, radius, numberOfPointsInCircle, depth/2);
		arcPoints3 = determinatePointsInCircunference(Math.PI, -Math.PI/2, radius, numberOfPointsInCircle, depth/2);
		arcPoints4 = determinatePointsInCircunference(Math.PI/2, -Math.PI/2, radius, numberOfPointsInCircle, depth/2);
		
		
		for (int i = 0; i < numberOfPointsInCircle; i++)
		{
			vertexArray[i + 1] = new Point3d(arcPoints1[i].getX() + d1/2 - radius, arcPoints1[i].getY(), arcPoints1[i].getZ() + (-d2/2) + radius);
			vertexArray[i + 1 + numberOfPointsInCircle] = new Point3d(arcPoints2[i].getX() - d1/2 + radius, arcPoints2[i].getY(), arcPoints2[i].getZ() + (-d2/2) + radius);	
			vertexArray[i + 1 + 2 * numberOfPointsInCircle] = new Point3d(arcPoints3[i].getX() - d1/2 + radius, arcPoints3[i].getY(), arcPoints3[i].getZ() + d2/2 - radius);
			vertexArray[i + 1 + 3 * numberOfPointsInCircle] = new Point3d(arcPoints4[i].getX() + d1/2 - radius, arcPoints4[i].getY(), arcPoints4[i].getZ() + d2/2 - radius);
		}
		
		//generate vertex in bottom face of pocket
		for (int i = 0; i < vertexArray.length/2; i++)
		{
			vertexArray[vertexArray.length/2 + i] = new Point3d(vertexArray[i].getX(), -depth/2, vertexArray[i].getZ());
		}
		
		// verification
		/*for (int i = 0; i < vertexArray.length; i++)
		{
			System.out.println(i + " x y z:" + vertexArray[i].getX() + " " + vertexArray[i].getY() + " " + vertexArray[i].getZ());
		}*/
		
    }
    private Point3d [] determinatePointsInCircunference(double initialAngle, double deltaAngle, double radius, int numberOfPoints, double yPlane)
    {
        Point3d[] output = new Point3d [numberOfPoints];
        double x, z;
        double dAngle = 0;
        dAngle = deltaAngle / (numberOfPoints-1);
        for(int i = 0; i < numberOfPoints; i++)
        {
            x = radius * Math.cos(initialAngle + i * dAngle);
            z = radius * Math.sin(initialAngle  + i * dAngle);
            output[i] = new Point3d((float)x,(float)yPlane, (float)z);
            //System.out.println(i + " x z:" + output[i].getX() + " " + output[i].getZ());
        }
        return output;
    }
    
    private void generateIndexArray()
    {   
    	indexArray = new int[numberOfPointsInCircle * 4 * 4 * 3];
    	
        for(int i = 0; i < (numberOfPointsInCircle * 4) - 1; i++)
        {
        	//generate index for create the top face
        	indexArray[3 * i] = 0;
        	indexArray[3 * i + 1] = i + 1;
        	indexArray[3 * i + 2] = i + 2;
        	
        	//generate index for create the bottom face
        	indexArray[numberOfPointsInCircle * 4 * 3 + (3 * i)] = vertexArray.length/2;
        	indexArray[numberOfPointsInCircle * 4 * 3 + (3 * i + 2)] = vertexArray.length/2 + 1 + i;
        	indexArray[numberOfPointsInCircle * 4 * 3 + (3 * i + 1)] = vertexArray.length/2 + 2 + i;
        
        	//generate index for create the side faces
        	indexArray[numberOfPointsInCircle * 4 * 2 * 3 + (3 * i)] = 2 + i;
        	indexArray[numberOfPointsInCircle * 4 * 2 * 3 + (3 * i + 1)] = 1 + i;
        	indexArray[numberOfPointsInCircle * 4 * 2 * 3 + (3 * i + 2)] = vertexArray.length/2 + 1 + i;
        	
        	indexArray[numberOfPointsInCircle * 4 * 2 * 3 + numberOfPointsInCircle * 4 * 3 + (3 * i)] = vertexArray.length/2 + 1 + i;
        	indexArray[numberOfPointsInCircle * 4 * 2 * 3 + numberOfPointsInCircle * 4 * 3 + (3 * i + 1)] = vertexArray.length/2 + 2 + i;
        	indexArray[numberOfPointsInCircle * 4 * 2 * 3 + numberOfPointsInCircle * 4 * 3 + (3 * i + 2)] = 2 + i;
        }
        //special last triangle in top face
        indexArray[numberOfPointsInCircle * 4 * 3 - 3] = 0;
        indexArray[numberOfPointsInCircle * 4 * 3 - 2] = numberOfPointsInCircle * 4;
        indexArray[numberOfPointsInCircle * 4 * 3 - 1] = 1;
        
        //special last triangle in bottom face
        indexArray[numberOfPointsInCircle * 4 * 2 * 3 - 3] = vertexArray.length/2;
        indexArray[numberOfPointsInCircle * 4 * 2 * 3 - 1] = vertexArray.length - 1;
        indexArray[numberOfPointsInCircle * 4 * 2 * 3 - 2] = vertexArray.length/2 + 1;
        
        //special last triangle in side face
        indexArray[(vertexArray.length/2 - 1) * 3 * 3 - 3] = 1;
        indexArray[(vertexArray.length/2 - 1) * 3 * 3 - 2] = vertexArray.length/2 - 1;
        indexArray[(vertexArray.length/2 - 1) * 3 * 3 - 1] = vertexArray.length - 1;
        
        indexArray[indexArray.length - 3] = vertexArray.length/2 + 1;
        indexArray[indexArray.length - 2] = 1;
        indexArray[indexArray.length - 1] = vertexArray.length - 1;
        
        //System.out.println("indexArray \n");
        /*for(int i = 0; i < indexArray.length; i++)
        {
        	System.out.println(i + " --> " + indexArray[i]);
        }*/
    }
}
