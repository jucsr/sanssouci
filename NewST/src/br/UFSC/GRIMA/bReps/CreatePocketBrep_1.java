package br.UFSC.GRIMA.bReps;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;



public class CreatePocketBrep_1 
{
	public Point3d [] vertexArray;
	public int [] indexList;
	public Color3f[] color3f;
	
	private static final int X_AXIS = 0;
	private static final int Z_AXIS = 1;
	private float d1, d2, depth, radius;
	public CreatePocketBrep_1 (float d1, float d2, float depth, float radius)
	{
		this.d1 = d1;
		this.d2 = d2;
		this.depth = depth;
		this.radius = radius;
		generateVertexPocket(d1, d2, depth, radius);
		this.indexList = indexOfPocketFaces(vertexArray);
		color3f = new Color3f [vertexArray.length];
		for (int i = 0; i < color3f.length; i++)
		{
			color3f [i] = new Color3f(0.3f,0.3f,0.3f);
		}
	}
	
	public float getD1()
	{
		return d1;
	}
	public float getD2()
	{
		return d2;
	}
	public float getDepth()
	{
		return depth;
	}
	public float getRadius()
	{
		return radius;
	}
	private void generateVertexPocket(float d1, float d2, float depth, float radius)
	{
		int numberOfPointsInCircularInterpolation = 7;
		
		Point3d [] circularInterpolation = new Point3d [numberOfPointsInCircularInterpolation+1];
		Point3d [] firstQuadrant = new Point3d [4+circularInterpolation.length];
		Point3d [] secondQuadrant = new Point3d [firstQuadrant.length - 2];	
		Point3d [] thirdQuadrant = new Point3d [firstQuadrant.length - 2];
		Point3d [] quartQuadrant = new Point3d [thirdQuadrant.length - 1];
		this.vertexArray = new Point3d [2*(firstQuadrant.length+secondQuadrant.length+thirdQuadrant.length+quartQuadrant.length)];
		int ivertex=0;
		
		for(int iv=0;iv<vertexArray.length;iv++)
		{
			vertexArray[iv]=new Point3d();
		}
		
		circularInterpolation = determinatePointsInCircunference(0, Math.PI/2, radius, numberOfPointsInCircularInterpolation, depth/2);
		for (int i = 0; i < circularInterpolation.length; i++)
		{
			circularInterpolation[i].setX(circularInterpolation[i].getX() + d1/2 - radius);
			circularInterpolation[i].setY(circularInterpolation[i].getY());
			circularInterpolation[i].setZ(-circularInterpolation[i].getZ() - d2/2 + radius);
			
			//System.out.println("element[ " + i +"]"+ circularInterpolation[i]);
			//System.out.println("element[ " + i +"]"+ "X,Z "+ circularInterpolation[i].getX() + "\t\t "+ circularInterpolation[i].getZ());
		}

		for (int iPoint2 =0;iPoint2<circularInterpolation.length+4;iPoint2++)
		{
			firstQuadrant[iPoint2]=new Point3d();
		}
		
		firstQuadrant [0].setX(0);
		firstQuadrant [0].setY(depth/2);
		firstQuadrant [0].setZ(0);
		
		//System.out.println(" i am here ");
		
		firstQuadrant [1].setX(d1/2);
		firstQuadrant [1].setY(depth/2);
		firstQuadrant [1].setZ(0);
		
		firstQuadrant [2].setX(d1/2 - radius);
		firstQuadrant [2].setY(depth/2);
		firstQuadrant [2].setZ(-d2/2 + radius);
		
		for (int iPoint=0;iPoint<circularInterpolation.length;iPoint++)
		{
			firstQuadrant[iPoint+3]=circularInterpolation[iPoint];
		}
				
		firstQuadrant [circularInterpolation.length+3].setX(0);
		firstQuadrant [circularInterpolation.length+3].setY(depth/2);
		firstQuadrant [circularInterpolation.length+3].setZ(-d2/2);
		
		for (int i = 0; i < firstQuadrant.length - 2; i++)
		{
			secondQuadrant [i] = new Point3d();
			thirdQuadrant [i] = new Point3d();
			if(i<firstQuadrant.length-4)
			{
				quartQuadrant[i]=new Point3d();
			}
		}
		
		System.out.println("Sizefirstquad " + firstQuadrant.length);
		System.out.println("Sizesecondquad " + secondQuadrant.length);
		System.out.println("Sizethirdquad " + thirdQuadrant.length);
		System.out.println("Sizequartquad " + quartQuadrant.length);
		
		//System.out.println("FirstQuadrant ");
		
		for (int j=0;j<firstQuadrant.length;j++)
		{
			vertexArray[j]=firstQuadrant[j];
			System.out.println(" " + firstQuadrant[j].getX() + " " +  firstQuadrant[j].getY() + " " +  firstQuadrant[j].getZ() );		
		}
		ivertex=firstQuadrant.length;
		secondQuadrant = reflexQuadrantPoints(firstQuadrant, Z_AXIS);	
		//System.out.println("SecondQuadrant ");
		for (int j=0;j<secondQuadrant.length;j++)
		{
			vertexArray[ivertex+j]=secondQuadrant[j];
			System.out.println(" " + secondQuadrant[j].getX() + " " +  secondQuadrant[j].getY() + " " +  secondQuadrant[j].getZ() );		
		}
		ivertex=ivertex+secondQuadrant.length;
		thirdQuadrant = reflexQuadrantPoints(firstQuadrant, X_AXIS);
		//System.out.println("ThirdQuadrant ");
		for (int j=0;j<thirdQuadrant.length;j++)
		{
			vertexArray[ivertex+j]=thirdQuadrant[j];
			System.out.println(" " + thirdQuadrant[j].getX() + " " +  thirdQuadrant[j].getY() + " " +  thirdQuadrant[j].getZ() );		
		}
		ivertex=ivertex+thirdQuadrant.length;
		quartQuadrant = reflexQuadrantPoints(thirdQuadrant, Z_AXIS);
		//System.out.println("QuartQuadrant ");
		for (int j=0;j<quartQuadrant.length;j++)
		{
			vertexArray[ivertex+j]=quartQuadrant[j];
			System.out.println(" " + quartQuadrant[j].getX() + " " +  quartQuadrant[j].getY() + " " +  quartQuadrant[j].getZ() );		
		}		
		ivertex=ivertex+quartQuadrant.length;
		
		for (ivertex=vertexArray.length/2;ivertex<vertexArray.length;ivertex++)
		{
			int aa=ivertex-vertexArray.length/2;
			System.out.println("ivertex: " + ivertex + "  ivertex - vertexArray: " + aa);
			vertexArray[ivertex].setX(vertexArray[ivertex-vertexArray.length/2].getX());
			vertexArray[ivertex].setY(-vertexArray[ivertex-vertexArray.length/2].getY());
			vertexArray[ivertex].setZ(vertexArray[ivertex-vertexArray.length/2].getZ());
		}
		
		System.out.println("Vertex Array:");
		System.out.println("Length:" + vertexArray.length);
		for (int j=0;j<vertexArray.length;j++)
		{
			System.out.println(" " + vertexArray[j].getX() + " " +  vertexArray[j].getY() + " " +  vertexArray[j].getZ() );
		}
	}
	public static Point3d [] determinatePointsInCircunference(double initialAngle, double deltaAngle, double radius, int numberOfPoints, double yPlane)
	{
		Point3d[] output = new Point3d [numberOfPoints+1];
		double x, z;
		double dAngle = 0;
		dAngle = deltaAngle / numberOfPoints;
		for(int i = 0; i < numberOfPoints+1; i++)
		{
			x = radius * Math.cos(initialAngle + i * dAngle);
			z = radius * Math.sin(initialAngle  + i * dAngle);
			output[i] = new Point3d((float)x,(float)yPlane, (float)z);
			//System.out.println(i + " x z:" + output[i].getX() + " " + output[i].getZ());
		}
		return output;
	}
	public static Point3d [] reflexQuadrantPoints(Point3d [] inArray, int axis)
	{
		int ncount = countAxisPoints(inArray,axis);
		Point3d [] newQuadrantPoints=new Point3d [inArray.length-ncount];
		
		int iPf;
		switch (axis)
		{
			case X_AXIS:
				//System.out.println("X Reflextion");
				iPf=0;
				for (int iP=0;iP<inArray.length;iP++)
				{
					if (Math.abs(inArray[iP].getZ())>=10E-16)
					{
						newQuadrantPoints[iPf]=new Point3d();
						newQuadrantPoints[iPf].setX(inArray[iP].getX());
						newQuadrantPoints[iPf].setY(inArray[iP].getY());
						newQuadrantPoints[iPf].setZ(-inArray[iP].getZ());
						//System.out.println("iP=" + iP + " iPf=" + iPf);
						//System.out.println(inArray[iP].getX() + " " + inArray[iP].getY() + " " + inArray[iP].getZ() + " -> " + newQuadrantPoints[iPf].getX() + " " + newQuadrantPoints[iPf].getY() + " " + newQuadrantPoints[iPf].getZ());
						iPf++;
					}
				}
				break;
			case Z_AXIS:
				//System.out.println("Z Reflextion");
				iPf=0;
				for (int iP=0;iP<inArray.length;iP++)
				{
					if (Math.abs(inArray[iP].getX())>=10E-16)
					{
						newQuadrantPoints[iPf]=new Point3d();
						newQuadrantPoints[iPf].setX(-inArray[iP].getX());
						newQuadrantPoints[iPf].setY(inArray[iP].getY());
						newQuadrantPoints[iPf].setZ(inArray[iP].getZ());
						//System.out.println("iP=" + iP + " iPf=" + iPf);
						//System.out.println(inArray[iP].getX() + " " + inArray[iP].getY() + " " + inArray[iP].getZ() + " -> " + newQuadrantPoints[iPf].getX() + " " + newQuadrantPoints[iPf].getY() + " " + newQuadrantPoints[iPf].getZ());
						iPf++;
					}
				}	
				break;
			default:
		}
		return newQuadrantPoints;
	}
	public static int countAxisPoints(Point3d [] inArray, int axis)
	{	
		int ncount=0;
		switch (axis)
		{
			case X_AXIS:
				//System.out.println("X Axis");
				for (int iP=0;iP<inArray.length;iP++)
				{
					if (Math.abs(inArray[iP].getZ())<10E-16)
					{
						ncount++;
					}
				}
				break;
			case Z_AXIS:
				//System.out.println("Z Axis");
				for (int iP=0;iP<inArray.length;iP++)
				{
					if (Math.abs(inArray[iP].getX())<10E-16)
					{
						ncount++;
					}
				}	
				break;
			default:
		}
		//System.out.println("ncount " + ncount);
		return ncount; 
	}
	
	private int [] indexOfPocketFaces(Point3d[] vertex)
	{	
		int nPointsInArc=(vertex.length-18)/8;	
		int [][] indexOfPocketFaces=new int [72+8*(4+nPointsInArc-1)][3];
		int [] indexOfPocketFacesInLine=new int [(72+8*(4+nPointsInArc-1))*3];
		int [][] indexOfPocketBorders= new int [4*nPointsInArc+4][2];
		
		System.out.println("NPIArc: " + nPointsInArc);
		System.out.println("indexLength: " + indexOfPocketFaces.length);
		indexOfPocketFaces[0][0]=0;
		indexOfPocketFaces[0][1]=1;
		indexOfPocketFaces[0][2]=2;

		indexOfPocketFaces[1][0]=1;
		indexOfPocketFaces[1][1]=3;
		indexOfPocketFaces[1][2]=2;

		indexOfPocketFaces[2][0]=0;
		indexOfPocketFaces[2][1]=2;
		indexOfPocketFaces[2][2]=11;
		
		indexOfPocketFaces[3][0]=2;
		indexOfPocketFaces[3][1]=10;
		indexOfPocketFaces[3][2]=11;
		
		for (int iArc=1;iArc<nPointsInArc;iArc++)
		{
			indexOfPocketFaces[3+iArc][0]=2;
			indexOfPocketFaces[3+iArc][1]=iArc+2;
			indexOfPocketFaces[3+iArc][2]=iArc+3;			
		}
		
		indexOfPocketFaces[11][0]=0;
		indexOfPocketFaces[11][1]=13;
		indexOfPocketFaces[11][2]=12;

		indexOfPocketFaces[12][0]=12;
		indexOfPocketFaces[12][1]=13;
		indexOfPocketFaces[12][2]=14;
		
		indexOfPocketFaces[13][0]=0;
		indexOfPocketFaces[13][1]=11;
		indexOfPocketFaces[13][2]=13;		

		indexOfPocketFaces[14][0]=11;
		indexOfPocketFaces[14][1]=21;
		indexOfPocketFaces[14][2]=13;
		
		for (int iArc=1;iArc<nPointsInArc;iArc++)
		{
			indexOfPocketFaces[14+iArc][0]=13;
			indexOfPocketFaces[14+iArc][1]=iArc+14;
			indexOfPocketFaces[14+iArc][2]=iArc+13;			
		}

		indexOfPocketFaces[22][0]=0;
		indexOfPocketFaces[22][1]=22;
		indexOfPocketFaces[22][2]=1;

		indexOfPocketFaces[23][0]=1;
		indexOfPocketFaces[23][1]=22;
		indexOfPocketFaces[23][2]=23;
		
		indexOfPocketFaces[24][0]=0;
		indexOfPocketFaces[24][1]=31;
		indexOfPocketFaces[24][2]=22;
		
		indexOfPocketFaces[25][0]=22;
		indexOfPocketFaces[25][1]=31;
		indexOfPocketFaces[25][2]=30;		

		for (int iArc=1;iArc<nPointsInArc;iArc++)
		{
			indexOfPocketFaces[25+iArc][0]=22;
			indexOfPocketFaces[25+iArc][1]=iArc+23;
			indexOfPocketFaces[25+iArc][2]=iArc+22;			
		}

		indexOfPocketFaces[33][0]=0;
		indexOfPocketFaces[33][1]=12;
		indexOfPocketFaces[33][2]=32;		
		
		indexOfPocketFaces[34][0]=12;
		indexOfPocketFaces[34][1]=33;
		indexOfPocketFaces[34][2]=32;	
		
		indexOfPocketFaces[35][0]=0;
		indexOfPocketFaces[35][1]=33;
		indexOfPocketFaces[35][2]=31;	
		
		indexOfPocketFaces[36][0]=31;
		indexOfPocketFaces[36][1]=32;
		indexOfPocketFaces[36][2]=40;			

		for (int iArc=1;iArc<nPointsInArc;iArc++)
		{
			indexOfPocketFaces[36+iArc][0]=32;
			indexOfPocketFaces[36+iArc][1]=iArc+32;
			indexOfPocketFaces[36+iArc][2]=iArc+33;			
		}
		int lastpocketface=0;
		for (int iFace=0;iFace<44;iFace++)
		{
			indexOfPocketFaces[44+iFace][0]=indexOfPocketFaces[iFace][0]+41;
			indexOfPocketFaces[44+iFace][1]=indexOfPocketFaces[iFace][2]+41;
			indexOfPocketFaces[44+iFace][2]=indexOfPocketFaces[iFace][1]+41;
			lastpocketface=44+iFace;
		}
		
		System.out.println("Last pocket face number: " + lastpocketface);
		indexOfPocketBorders[0][0]=1;
		indexOfPocketBorders[1][0]=3;
		indexOfPocketBorders[2][0]=4;
		indexOfPocketBorders[3][0]=5;
		indexOfPocketBorders[4][0]=6;
		indexOfPocketBorders[5][0]=7;
		indexOfPocketBorders[6][0]=8;
		indexOfPocketBorders[7][0]=9;
		indexOfPocketBorders[8][0]=10;
		indexOfPocketBorders[9][0]=11;
		indexOfPocketBorders[10][0]=21;
		indexOfPocketBorders[11][0]=20;
		indexOfPocketBorders[12][0]=19;
		indexOfPocketBorders[13][0]=18;
		indexOfPocketBorders[14][0]=17;
		indexOfPocketBorders[15][0]=16;
		indexOfPocketBorders[16][0]=15;
		indexOfPocketBorders[17][0]=14;
		indexOfPocketBorders[18][0]=12;
		indexOfPocketBorders[19][0]=33;
		indexOfPocketBorders[20][0]=34;
		indexOfPocketBorders[21][0]=35;
		indexOfPocketBorders[22][0]=36;
		indexOfPocketBorders[23][0]=37;
		indexOfPocketBorders[24][0]=38;
		indexOfPocketBorders[25][0]=39;
		indexOfPocketBorders[26][0]=40;
		indexOfPocketBorders[27][0]=31;
		indexOfPocketBorders[28][0]=30;
		indexOfPocketBorders[29][0]=29;
		indexOfPocketBorders[30][0]=28;
		indexOfPocketBorders[31][0]=27;
		indexOfPocketBorders[32][0]=26;
		indexOfPocketBorders[33][0]=25;
		indexOfPocketBorders[34][0]=24;
		indexOfPocketBorders[35][0]=23;
		
		for(int iB=0;iB<36;iB++)
		{
			indexOfPocketBorders[iB][1]=indexOfPocketBorders[iB][0]+41;
		}
		
		for (int iB=0;iB<35;iB++)
		{
				indexOfPocketFaces[87+2*iB+1][0]=indexOfPocketBorders[iB][0];
				indexOfPocketFaces[87+2*iB+1][1]=indexOfPocketBorders[iB][1];
				indexOfPocketFaces[87+2*iB+1][2]=indexOfPocketBorders[iB+1][1];				

				indexOfPocketFaces[87+2*iB+2][0]=indexOfPocketBorders[iB][0];
				indexOfPocketFaces[87+2*iB+2][1]=indexOfPocketBorders[iB+1][1];
				indexOfPocketFaces[87+2*iB+2][2]=indexOfPocketBorders[iB+1][0];		
				
				indexOfPocketFaces[87+2*iB+1][0]=indexOfPocketBorders[iB][0];
				indexOfPocketFaces[87+2*iB+1][1]=indexOfPocketBorders[iB][1];
				indexOfPocketFaces[87+2*iB+1][2]=indexOfPocketBorders[iB+1][1];				

				indexOfPocketFaces[87+2*iB+2][0]=indexOfPocketBorders[iB][0];
				indexOfPocketFaces[87+2*iB+2][1]=indexOfPocketBorders[iB+1][1];
				indexOfPocketFaces[87+2*iB+2][2]=indexOfPocketBorders[iB+1][0];		
				
				lastpocketface=87+2*iB+2;
		}
			
		
		System.out.println("Last pocket face number: " + lastpocketface);
		
		indexOfPocketFaces[87+2*35+1][0]=indexOfPocketBorders[35][0];
		indexOfPocketFaces[87+2*35+1][1]=indexOfPocketBorders[35][1];
		indexOfPocketFaces[87+2*35+1][2]=indexOfPocketBorders[0][1];				

		indexOfPocketFaces[87+2*35+2][0]=indexOfPocketBorders[35][0];
		indexOfPocketFaces[87+2*35+2][1]=indexOfPocketBorders[0][1];
		indexOfPocketFaces[87+2*35+2][2]=indexOfPocketBorders[0][0];	
				
		int iInLine=0;
		//System.out.print("Lenght indexinline" + indexOfPocketFacesInLine.length);
		for (int i=0;i<160;i++)
		{
			//System.out.println(iInLine + " " + indexOfPocketFacesInLine[iInLine]);
			for(int j=0;j<3;j++)
			{
				System.out.print(indexOfPocketFaces[i][j]  + "  ");
				indexOfPocketFacesInLine[iInLine]=indexOfPocketFaces[i][j];
				iInLine++;
			}
			System.out.println(" ");
		}
		
		return indexOfPocketFacesInLine;
	}
}

