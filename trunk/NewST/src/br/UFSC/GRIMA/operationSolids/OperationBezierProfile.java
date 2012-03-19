package br.UFSC.GRIMA.operationSolids;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.bReps.CreateBezierLinearPath;
import br.UFSC.GRIMA.bReps.CreateVeeProfileBrep;

/**
 * 
 * @author Jc
 *
 */

public class OperationBezierProfile extends CSGSolid
{
	private String name;
	private Point3d [] controlPoints;
	
	public OperationBezierProfile(String name, Point3d[] controlPoints)
	{
		super();
		this.name = name;
		this.controlPoints = controlPoints;
		
		CreateBezierLinearPath veeProfile = new CreateBezierLinearPath(this.name,this.controlPoints);
		this.vertices = veeProfile.vertexArray;
		this.indices = veeProfile.indexArray;
		this.colors = veeProfile.color3f;
		this.scale(5, 5, 5);
	}
	public CSGSolid copy() 
	{
		OperationBezierProfile operationSlotVeeProfile = new OperationBezierProfile(this.name, this.controlPoints);
		operationSlotVeeProfile.updateLocation(getLocation());
		return operationSlotVeeProfile;	
	}
	
}
