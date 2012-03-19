package br.UFSC.GRIMA.bReps;

import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

public class CreatePocketRoundBottomBrep
{
   public Point3d [] vertexArray;
   public int [] indexArray;
   public Color3f[] color3f;
   private int numberOfPointsInCircle = 5;
   private int numberOfPointsInFloorCircle = 4;
   private float d1, d2, depth, radius0, radius1, radius2, radius3, radiusBottom;
   private String name;
   private Point3d [] arcPointsBottom;
   private Point3d[][] arraysInArcLateral;

   public CreatePocketRoundBottomBrep(String name, float d1, float d2, float depth, float radius0, float radius1, float radius2, float radius3, float radiusBottom)
   {
       this.name = name;
       this.d1 = d1;
       this.d2 = d2;
       this.depth = depth;
       this.radius0 = radius0;
       this.radius1 = radius1;
       this.radius2 = radius2;
       this.radius3 = radius3;
       this.radiusBottom = radiusBottom;
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
       vertexArray = new Point3d[(numberOfPointsInCircle * 4 * (numberOfPointsInFloorCircle + 1) + 2)];
       
       //determinate the first and the last element
       vertexArray[0] = new Point3d(0, depth/2, 0);
       vertexArray[vertexArray.length - 1] = new Point3d(0, -depth/2, 0);

       // determinate the vertex in the first level curve
       Point3d[] firstLevel = new Point3d[4 * numberOfPointsInCircle];
       firstLevel = determinateVertexInLevelCurve(d1, d2, radius0, radius1, radius2, radius3, depth/2);
       
       //upload into vertexArray
       for (int i = 0; i < firstLevel.length; i++)
       {
    	   vertexArray[i + 1] = firstLevel[i];
       }
       
       //generate levelCurves arrays
       arraysInArcLateral = new Point3d[numberOfPointsInFloorCircle][numberOfPointsInCircle * 4];
       arcPointsBottom = determinatePointsInLateralCircunference(0, -Math.PI/2, radiusBottom, numberOfPointsInFloorCircle, 0);
       float radiusTemp0 = 0, radiusTemp1 = 0, radiusTemp2 = 0, radiusTemp3 = 0, d1Tmp = 0, d2Tmp = 0, yPlane = 0;
       Point3d [] arrayTmp;
       for (int i = 0; i < arraysInArcLateral.length; i++)
       {
    	   radiusTemp0 = radius0 - radiusBottom + (float)arcPointsBottom[i].getX();
           radiusTemp1 = radius1 - radiusBottom + (float)arcPointsBottom[i].getX();
           radiusTemp2 = radius2 - radiusBottom + (float)arcPointsBottom[i].getX();
           radiusTemp3 = radius3 - radiusBottom + (float)arcPointsBottom[i].getX();
           
           d1Tmp = d1 - radius0 - radius1 + radiusTemp0 + radiusTemp1;
           d2Tmp = d2 - radius0 - radius3 + radiusTemp0 + radiusTemp3; 
           yPlane = -(depth/2 - radiusBottom - (float)arcPointsBottom[i].getY());
    	   arrayTmp = new Point3d[numberOfPointsInCircle * 4];
    	   arrayTmp = determinateVertexInLevelCurve(d1Tmp, d2Tmp, radiusTemp0, radiusTemp1, radiusTemp2, radiusTemp3, yPlane);
    	   arraysInArcLateral[i] = arrayTmp;
       }
       
       //upload into vertexArray
       for (int i = 0; i < arraysInArcLateral.length; i++)
       {
    	   for (int j = 0; j < arraysInArcLateral[i].length; j++)
    	   {
    		   vertexArray[arraysInArcLateral[i].length * (i + 1) + 1 + j] = arraysInArcLateral[i][j];
    	   }
       }
   	}
  
   private void generateIndexArray()
   {
       indexArray = new int[8 * numberOfPointsInCircle * (numberOfPointsInFloorCircle + 1) * 3];

       for(int i = 0; i < (numberOfPointsInCircle * 4) - 1; i++)
       {
           //generate index for create the top face
    	   indexArray[3 * i] = 0;
    	   indexArray[3 * i + 1] = i + 1;
           indexArray[3 * i + 2] = i + 2;

           //generate index for create the side face
           indexArray[numberOfPointsInCircle * 4 * 3 + (3 * i)] = 2 + i;
           indexArray[numberOfPointsInCircle * 4 * 3 + (3 * i + 1)] = 1 + i;
           indexArray[numberOfPointsInCircle * 4 * 3 + (3 * i + 2)] = numberOfPointsInCircle * 4 + 1 + i;

           indexArray[numberOfPointsInCircle * 4 * 3 + numberOfPointsInCircle * 4 * 3 + (3 * i)] = numberOfPointsInCircle * 4 + 1 + i;
           indexArray[numberOfPointsInCircle * 4 * 3 + numberOfPointsInCircle * 4 * 3 + (3 * i + 1)] = numberOfPointsInCircle * 4 + 2 + i;
           indexArray[numberOfPointsInCircle * 4 * 3 + numberOfPointsInCircle * 4 * 3 + (3 * i + 2)] = 2 + i;
           
           //generate index for create the "arc floor" surface
           
           for(int j = 0; j < (numberOfPointsInFloorCircle - 1); j++)
           {
        	   indexArray[numberOfPointsInCircle * 4 * 3 * 3 + (3 * i) + (numberOfPointsInCircle * 4 * 3 * 2 * j)] = numberOfPointsInCircle * 4 + 2 + i + j * numberOfPointsInCircle * 4;
        	   indexArray[numberOfPointsInCircle * 4 * 3 * 3 + (3 * i + 1) + (numberOfPointsInCircle * 4 * 3 * 2 * j)] = numberOfPointsInCircle * 4 + 1 + i + j * numberOfPointsInCircle * 4;
        	   indexArray[numberOfPointsInCircle * 4 * 3 * 3 + (3 * i + 2) + (numberOfPointsInCircle * 4 * 3 * 2 * j)] = numberOfPointsInCircle * 4 * 2 + 1 + i + j * numberOfPointsInCircle * 4;
        	   
        	   indexArray[numberOfPointsInCircle * 4 * 3 * 4 + (3 * i) + (numberOfPointsInCircle * 4 * 3 * 2 * j)] = numberOfPointsInCircle * 4 * 2 + 1 + i + j * numberOfPointsInCircle * 4;
        	   indexArray[numberOfPointsInCircle * 4 * 3 * 4 + (3 * i + 1) + (numberOfPointsInCircle * 4 * 3 * 2 * j)] = numberOfPointsInCircle * 4 * 2 + 2 + i + j * numberOfPointsInCircle * 4;
        	   indexArray[numberOfPointsInCircle * 4 * 3 * 4 + (3 * i + 2) + (numberOfPointsInCircle * 4 * 3 * 2 * j)] = numberOfPointsInCircle * 4 + 2 + i + j * numberOfPointsInCircle * 4;
           }
           //generate index for create the bottom face
           indexArray[numberOfPointsInCircle * 4 * 3 * 3 + (numberOfPointsInFloorCircle - 1) * numberOfPointsInCircle * 4 * 3 * 2 + (3 * i)] = vertexArray.length - 1;
           indexArray[numberOfPointsInCircle * 4 * 3 * 3 + (numberOfPointsInFloorCircle - 1) * numberOfPointsInCircle * 4 * 3 * 2 + (3 * i + 1)] = numberOfPointsInCircle * 4 * numberOfPointsInFloorCircle + 2 + i;
           indexArray[numberOfPointsInCircle * 4 * 3 * 3 + (numberOfPointsInFloorCircle - 1) * numberOfPointsInCircle * 4 * 3 * 2 + (3 * i + 2)] = numberOfPointsInCircle * 4 * numberOfPointsInFloorCircle + 1 + i;
       }
       //**SPECIALS
       //special last triangle in top face
       indexArray[numberOfPointsInCircle * 4 * 3 - 3] = 0;
       indexArray[numberOfPointsInCircle * 4 * 3 - 2] = numberOfPointsInCircle * 4;
       indexArray[numberOfPointsInCircle * 4 * 3 - 1] = 1;

       //special last triangle in bottom face
       indexArray[indexArray.length - 3] = vertexArray.length - 1;
       indexArray[indexArray.length - 1] = vertexArray.length - 2;
       indexArray[indexArray.length - 2] = vertexArray.length - 1 - numberOfPointsInCircle * 4;

       //special last triangle in side face
       indexArray[numberOfPointsInCircle * 4 * 3 * 2 - 3] = 1;
       indexArray[numberOfPointsInCircle * 4 * 3 * 2 - 2] = numberOfPointsInCircle * 4;
       indexArray[numberOfPointsInCircle * 4 * 3 * 2 - 1] = numberOfPointsInCircle * 4 * 2;

       indexArray[numberOfPointsInCircle * 4 * 3 * 3 - 3] = numberOfPointsInCircle * 4 * 2;
       indexArray[numberOfPointsInCircle * 4 * 3 * 3 - 2] = numberOfPointsInCircle * 4 + 1;
       indexArray[numberOfPointsInCircle * 4 * 3 * 3 - 1] = 1;
       
       //special triangles in "arc floor"
       for (int i = 0; i < numberOfPointsInFloorCircle - 1; i++)
       {
    	   indexArray[numberOfPointsInCircle * 4 * 3 * 4 + (i * numberOfPointsInCircle * 4 * 3 * 2) - 3] = numberOfPointsInCircle * 4 * (i + 1) + 1;
           indexArray[numberOfPointsInCircle * 4 * 3 * 4 + (i * numberOfPointsInCircle * 4 * 3 * 2) - 2] = numberOfPointsInCircle * 4 * 2 + numberOfPointsInCircle * 4 * i;
           indexArray[numberOfPointsInCircle * 4 * 3 * 4 + (i * numberOfPointsInCircle * 4 * 3 * 2) - 1] = numberOfPointsInCircle * 4 * 3 + numberOfPointsInCircle * 4 * i;
           
           indexArray[numberOfPointsInCircle * 4 * 3 * 5 + (i * numberOfPointsInCircle * 4 * 3 * 2) - 3] = numberOfPointsInCircle * 4 * 3 + numberOfPointsInCircle * 4 * i;
           indexArray[numberOfPointsInCircle * 4 * 3 * 5 + (i * numberOfPointsInCircle * 4 * 3 * 2) - 2] = numberOfPointsInCircle * 4 * 2 + numberOfPointsInCircle * 4 * i + 1;
           indexArray[numberOfPointsInCircle * 4 * 3 * 5 + (i * numberOfPointsInCircle * 4 * 3 * 2) - 1] = numberOfPointsInCircle * 4 * (i + 1) + 1;
       }
       
       
       /*for(int i = 0; i < indexArray.length; i++)
       {
           System.out.println(i + " --> " + indexArray[i]);
       }*/
   }
   private Point3d[] determinateVertexInLevelCurve(float d1Tmp, float d2Tmp, float radius0, float radius1, float radius2, float radius3, float yPlane) 
	{
		Point3d [] output = new Point3d[4 * numberOfPointsInCircle];
		
		Point3d [] arcPoints1 = new Point3d[numberOfPointsInCircle];
   	Point3d [] arcPoints2 = new Point3d[numberOfPointsInCircle];
   	Point3d [] arcPoints3 = new Point3d[numberOfPointsInCircle];
   	Point3d [] arcPoints4 = new Point3d[numberOfPointsInCircle];
   	
		arcPoints1 = determinatePointsInCircunference(0, -Math.PI/2, radius0, numberOfPointsInCircle, yPlane);
		arcPoints2 = determinatePointsInCircunference(-Math.PI/2, -Math.PI/2, radius1, numberOfPointsInCircle, yPlane);
		arcPoints3 = determinatePointsInCircunference(Math.PI, -Math.PI/2, radius2, numberOfPointsInCircle, yPlane);
		arcPoints4 = determinatePointsInCircunference(Math.PI/2, -Math.PI/2, radius3, numberOfPointsInCircle, yPlane);
		
		for (int i = 0; i < numberOfPointsInCircle; i++)
		{
			output[i] = new Point3d(arcPoints1[i].getX() + d1Tmp/2 - radius0, arcPoints1[i].getY(), arcPoints1[i].getZ() + (-d2Tmp/2) + radius0);
			output[i + numberOfPointsInCircle] = new Point3d(arcPoints2[i].getX() - d1Tmp/2 + radius1, arcPoints2[i].getY(), arcPoints2[i].getZ() + (-d2Tmp/2) + radius1);	
			output[i + 2 * numberOfPointsInCircle] = new Point3d(arcPoints3[i].getX() - d1Tmp/2 + radius2, arcPoints3[i].getY(), arcPoints3[i].getZ() + d2Tmp/2 - radius2);
			output[i + 3 * numberOfPointsInCircle] = new Point3d(arcPoints4[i].getX() + d1Tmp/2 - radius3, arcPoints4[i].getY(), arcPoints4[i].getZ() + d2Tmp/2 - radius3);
		}
		return output;
	}
	private Point3d[] determinatePointsInLateralCircunference(double initialAngle, double deltaAngle, double radius, int numberOfPoints, double zPlane)
	{
      Point3d[] output = new Point3d [numberOfPoints];
      double x, y;
      double dAngle = 0;
      dAngle = deltaAngle / (numberOfPoints-1);
      for(int i = 0; i < numberOfPoints; i++)
      {
          x = radius * Math.cos(initialAngle + i * dAngle);
          y = radius * Math.sin(initialAngle  + i * dAngle);
          output[i] = new Point3d((float)x,(float)y, (float)zPlane);
          //System.out.println(i + " x z:" + output[i].getX() + " " + output[i].getZ());
      }
      return output;
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
      }
      return output;
  }

}
