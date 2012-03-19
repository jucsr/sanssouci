package br.UFSC.GRIMA.operationSolids;

import java.io.File;

import javax.media.j3d.Appearance;
import javax.vecmath.Color3f;



public class OperationBlock extends CSGSolid{

	public float length;
	 public float width;
	 public float height;
	
	public OperationBlock(String name, double length, double height, double width){
		super();
		this.height = (float)height;
		this.width = (float)width;
		this.length = (float)length;
		
		String[] boxArg =
		{
		"8",
		"0 -5.00000000000000E-0001 -5.00000000000000E-0001 -5.00000000000000E-0001",
		"1  5.00000000000000E-0001 -5.00000000000000E-0001 -5.00000000000000E-0001",
		"2 -5.00000000000000E-0001  5.00000000000000E-0001 -5.00000000000000E-0001",
		"3  5.00000000000000E-0001  5.00000000000000E-0001 -5.00000000000000E-0001",
		"4 -5.00000000000000E-0001 -5.00000000000000E-0001  5.00000000000000E-0001",
		"5  5.00000000000000E-0001 -5.00000000000000E-0001  5.00000000000000E-0001",
		"6 -5.00000000000000E-0001  5.00000000000000E-0001  5.00000000000000E-0001",
		"7  5.00000000000000E-0001  5.00000000000000E-0001  5.00000000000000E-0001",
		"12",
		"0 0 2 3" ,
		"1 3 1 0" ,
		"2 4 5 7" ,
		"3 7 6 4" ,
		"4 0 1 5" ,
		"5 5 4 0" ,
		"6 1 3 7" ,
		"7 7 5 1" ,
		"8 3 2 6" ,
		"9 6 7 3" ,
		"10 2 0 4",
		"11 4 6 2",
		};
		
		
		loadCoordinateFile(boxArg, new Color3f(0.3f,0.3f,0.3f));
		scale(width*5, height*5, length*5);
	
	}
	
	@Override
	public CSGSolid copy()
	{
		OperationBlock box = new OperationBlock(name, length, height, width);
		box.updateLocation(getLocation());
		return box;
	}

}
