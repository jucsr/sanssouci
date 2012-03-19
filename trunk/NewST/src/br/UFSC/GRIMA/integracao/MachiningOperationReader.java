package br.UFSC.GRIMA.integracao;

import javax.vecmath.Point3d;

import jsdai.SCombined_schema.EBoring;
import jsdai.SCombined_schema.EBottom_and_side_finish_milling;
import jsdai.SCombined_schema.EBottom_and_side_rough_milling;
import jsdai.SCombined_schema.ECartesian_point;
import jsdai.SCombined_schema.ECenter_drilling;
import jsdai.SCombined_schema.EDrilling;
import jsdai.SCombined_schema.EFreeform_operation;
import jsdai.SCombined_schema.EMachining_operation;
import jsdai.SCombined_schema.EMachining_workingstep;
import jsdai.SCombined_schema.EMilling_machine_functions;
import jsdai.SCombined_schema.EPlane_finish_milling;
import jsdai.SCombined_schema.EPlane_rough_milling;
import jsdai.SCombined_schema.EReaming;
import jsdai.lang.SdaiException;
import br.UFSC.GRIMA.capp.machiningOperations.Boring;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideFinishMilling;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.capp.machiningOperations.CenterDrilling;
import br.UFSC.GRIMA.capp.machiningOperations.Drilling;
import br.UFSC.GRIMA.capp.machiningOperations.FreeformOperation;
import br.UFSC.GRIMA.capp.machiningOperations.MachiningOperation;
import br.UFSC.GRIMA.capp.machiningOperations.PlaneFinishMilling;
import br.UFSC.GRIMA.capp.machiningOperations.PlaneRoughMilling;
import br.UFSC.GRIMA.capp.machiningOperations.Reaming;

public class MachiningOperationReader 
{
	public static MachiningOperation getOperation(EMachining_workingstep eMachining_workingstep) throws SdaiException
	{
		EMachining_operation eMachining_operation = eMachining_workingstep.getIts_operation(null);
		if(eMachining_operation.isKindOf(EDrilling.class))
		{
			Drilling drilling = new Drilling(eMachining_operation.getIts_id(null), eMachining_operation.getRetract_plane(null));
			drilling.setCoolant(((EMilling_machine_functions)eMachining_operation.getIts_machine_functions(null)).getCoolant(null));
			drilling.setStartPoint(cartesianPoint(eMachining_operation.getStart_point(null)));
			try{	drilling.setPreviousDiameter(((EDrilling)eMachining_operation).getPrevious_diameter(null));} // verificar isto
			catch(SdaiException e){ System.out.println("Sem previous diameter: " + eMachining_operation);}
			drilling.setCuttingDepth(((EDrilling)eMachining_operation).getCutting_depth(null));
			return drilling;
		} else if(eMachining_operation.isKindOf(ECenter_drilling.class))
		{
			CenterDrilling centerDrilling = new CenterDrilling(eMachining_operation.getIts_id(null), eMachining_operation.getRetract_plane(null));
			centerDrilling.setCoolant(((EMilling_machine_functions)eMachining_operation.getIts_machine_functions(null)).getCoolant(null));
			centerDrilling.setStartPoint(cartesianPoint(eMachining_operation.getStart_point(null)));
			try{	centerDrilling.setPreviousDiameter(((ECenter_drilling)eMachining_operation).getPrevious_diameter(null));} // verificar isto
			catch(SdaiException e){ System.out.println("Sem previous diameter: " + eMachining_operation);}
			centerDrilling.setCuttingDepth(((ECenter_drilling)eMachining_operation).getCutting_depth(null));
			return centerDrilling;
		} else if(eMachining_operation.isKindOf(EBoring.class))
		{
			Boring boring = new Boring(eMachining_operation.getIts_id(null), eMachining_operation.getRetract_plane(null));
			boring.setCoolant(((EMilling_machine_functions)eMachining_operation.getIts_machine_functions(null)).getCoolant(null));
			boring.setStartPoint(cartesianPoint(eMachining_operation.getStart_point(null)));
			try{	boring.setPreviousDiameter(((EBoring)eMachining_operation).getPrevious_diameter(null));} // verificar isto
			catch(SdaiException e){ System.out.println("Sem previous diameter: " + eMachining_operation);}
			boring.setCuttingDepth(((EBoring)eMachining_operation).getCutting_depth(null));
			return boring;
		} else if(eMachining_operation.isKindOf(EReaming.class))
		{
			Reaming reaming = new Reaming(eMachining_operation.getIts_id(null), eMachining_operation.getRetract_plane(null));
			reaming.setCoolant(((EMilling_machine_functions)eMachining_operation.getIts_machine_functions(null)).getCoolant(null));
			reaming.setStartPoint(cartesianPoint(eMachining_operation.getStart_point(null)));
			try{	reaming.setPreviousDiameter(((EReaming)eMachining_operation).getPrevious_diameter(null));} // verificar isto
			catch(SdaiException e){ System.out.println("Sem previous diameter: " + eMachining_operation);}
			reaming.setCuttingDepth(((EReaming)eMachining_operation).getCutting_depth(null));
			return reaming;
		} else if(eMachining_operation.isKindOf(EBottom_and_side_rough_milling.class))
		{
			BottomAndSideRoughMilling bottomAndSideRoughMilling = new BottomAndSideRoughMilling(eMachining_operation.getIts_id(null), eMachining_operation.getRetract_plane(null));
			bottomAndSideRoughMilling.setAllowanceBottom(((EBottom_and_side_rough_milling)eMachining_operation).getAllowance_bottom(null));
			bottomAndSideRoughMilling.setAllowanceSide(((EBottom_and_side_rough_milling)eMachining_operation).getAllowance_side(null));
			bottomAndSideRoughMilling.setStartPoint(cartesianPoint(eMachining_operation.getStart_point(null)));
			return bottomAndSideRoughMilling;
		} else if (eMachining_operation.isKindOf(EBottom_and_side_finish_milling.class))
		{
			BottomAndSideFinishMilling bottomAndSideFinishMilling = new BottomAndSideFinishMilling(eMachining_operation.getIts_id(null), eMachining_operation.getRetract_plane(null));
			bottomAndSideFinishMilling.setAllowanceBottom(((EBottom_and_side_finish_milling)eMachining_operation).getAllowance_bottom(null));
			bottomAndSideFinishMilling.setAllowanceSide(((EBottom_and_side_finish_milling)eMachining_operation).getAllowance_side(null));
			bottomAndSideFinishMilling.setStartPoint(cartesianPoint(eMachining_operation.getStart_point(null)));
			return bottomAndSideFinishMilling;
		} else if (eMachining_operation.isKindOf(EFreeform_operation.class))
		{
			FreeformOperation freeformOperation = new FreeformOperation(eMachining_operation.getIts_id(null), eMachining_operation.getRetract_plane(null));
			
			return freeformOperation;
		} else if (eMachining_operation.isKindOf(EPlane_rough_milling.class))
		{
			PlaneRoughMilling planeRoughMilling = new PlaneRoughMilling(eMachining_operation.getIts_id(null), eMachining_operation.getRetract_plane(null));
			planeRoughMilling.setAllowanceBottom(((EPlane_rough_milling)eMachining_operation).getAllowance_bottom(null));
			planeRoughMilling.setAllowanceBottom(((EPlane_rough_milling)eMachining_operation).getAxial_cutting_depth(null));
			return planeRoughMilling;
		} else if (eMachining_operation.isKindOf(EPlane_finish_milling.class))
		{
			PlaneFinishMilling planeFinishMilling = new PlaneFinishMilling(eMachining_operation.getIts_id(null), eMachining_operation.getRetract_plane(null));
			planeFinishMilling.setAllowanceBottom(((EPlane_finish_milling)eMachining_operation).getAllowance_bottom(null));
			planeFinishMilling.setAllowanceBottom(((EPlane_finish_milling)eMachining_operation).getAxial_cutting_depth(null));
			return planeFinishMilling;
		}
		//erro
		return null;
	}
	private static Point3d cartesianPoint(ECartesian_point eCartesian_point) throws SdaiException
	{
		Point3d point = new Point3d(eCartesian_point.getCoordinates(null).getByIndex(1), eCartesian_point.getCoordinates(null).getByIndex(2), eCartesian_point.getCoordinates(null).getByIndex(3));
		return point;
	}
}
