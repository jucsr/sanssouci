package br.UFSC.GRIMA.integracao;

import javax.vecmath.Point3d;

import jsdai.SCombined_schema.CBidirectional;
import jsdai.SCombined_schema.CContour_parallel;
import jsdai.SCombined_schema.CPlunge_ramp;
import jsdai.SCombined_schema.CPlunge_toolaxis;
import jsdai.SCombined_schema.CPlunge_zigzag;
import jsdai.SCombined_schema.CTrochoidal_and_contourn_parallel;
import jsdai.SCombined_schema.EBidirectional;
import jsdai.SCombined_schema.EBoring;
import jsdai.SCombined_schema.EBottom_and_side_finish_milling;
import jsdai.SCombined_schema.EBottom_and_side_milling;
import jsdai.SCombined_schema.EBottom_and_side_rough_milling;
import jsdai.SCombined_schema.ECartesian_point;
import jsdai.SCombined_schema.ECenter_drilling;
import jsdai.SCombined_schema.EContour_parallel;
import jsdai.SCombined_schema.EDrilling;
import jsdai.SCombined_schema.EFreeform_operation;
import jsdai.SCombined_schema.EMachining_operation;
import jsdai.SCombined_schema.EMachining_workingstep;
import jsdai.SCombined_schema.EMilling_machine_functions;
import jsdai.SCombined_schema.EMilling_type_operation;
import jsdai.SCombined_schema.EPlane_finish_milling;
import jsdai.SCombined_schema.EPlane_rough_milling;
import jsdai.SCombined_schema.EPlunge_ramp;
import jsdai.SCombined_schema.EPlunge_strategy;
import jsdai.SCombined_schema.EPlunge_toolaxis;
import jsdai.SCombined_schema.EPlunge_zigzag;
import jsdai.SCombined_schema.EReaming;
import jsdai.SCombined_schema.ERot_direction;
import jsdai.SCombined_schema.ETrochoidal_and_contourn_parallel;
import jsdai.SCombined_schema.ETwo5d_milling_strategy;
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
import br.UFSC.GRIMA.capp.movimentacoes.estrategias.Bidirectional;
import br.UFSC.GRIMA.capp.movimentacoes.estrategias.ContourParallel;
import br.UFSC.GRIMA.capp.movimentacoes.estrategias.ContourParallel.RotationDirection;
import br.UFSC.GRIMA.capp.movimentacoes.estrategias.TrochoidalAndContourParallelStrategy;
import br.UFSC.GRIMA.capp.movimentacoes.estrategias.Two5DMillingStrategy;
import br.UFSC.GRIMA.capp.plunge.PlungeRamp;
import br.UFSC.GRIMA.capp.plunge.PlungeStrategy;
import br.UFSC.GRIMA.capp.plunge.PlungeToolAxis;
import br.UFSC.GRIMA.capp.plunge.PlungeZigzag;

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
			/*
			 *  cuidado, pode dar erro pois algumas operacoes nao tem este tipo de estrategia
			 */
			
			bottomAndSideRoughMilling.setMachiningStrategy(getStrategy(((EBottom_and_side_rough_milling)eMachining_operation).getIts_machining_strategy(null)));
			bottomAndSideRoughMilling.setApproachStrategy(getPlungeStrategy(((EBottom_and_side_rough_milling)eMachining_operation)));
//			System.err.println(getStrategy(((EBottom_and_side_rough_milling)eMachining_operation).getIts_machining_strategy(null)));
			
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
	private static Two5DMillingStrategy getStrategy(ETwo5d_milling_strategy eTwo5d_milling_strategy) throws SdaiException
	{
		Two5DMillingStrategy estrategia = null;
		/*
		 * estrategia trocoidal e contour parallel
		 */
//		System.out.println("strategy = " + eTwo5d_milling_strategy.getClass());
		if(eTwo5d_milling_strategy.getClass() == CTrochoidal_and_contourn_parallel.class)
		{
//			System.err.println("lololloo");
			ETrochoidal_and_contourn_parallel eTrocoidal = (ETrochoidal_and_contourn_parallel)eTwo5d_milling_strategy;
			TrochoidalAndContourParallelStrategy trocoidal = new TrochoidalAndContourParallelStrategy();
			trocoidal.setAllowMultiplePasses(eTrocoidal.getAllow_multiple_passes(null));
			trocoidal.setCutmodeType(eTrocoidal.getCut_mode(null));
			trocoidal.setTrochoidalRadius(eTrocoidal.getTrochoidal_radius(null));
//			trocoidal.setTrochoidalFeedRate(6); //FORCEI UM VALOR PARA O TESTE FUNCIONAR (TEM QUE CRIAR UMA REFERENCIA NO STEP-NC)
//			System.out.println(eTrocoidal);
			trocoidal.setOverLap(eTrocoidal.getOverlap(null));
			if(eTrocoidal.getTrochoidal_rot_direction(null) == ERot_direction.CCW) //Trochoidal rotation
			{
				trocoidal.setTrochoidalSense(TrochoidalAndContourParallelStrategy.CCW);
			}
			else
			{
				trocoidal.setTrochoidalSense(TrochoidalAndContourParallelStrategy.CW);
			}
				
			if(eTrocoidal.getRotation_direction(null) == ERot_direction.CCW) //baseline direction
			{
				trocoidal.setRotationDirectionCCW(TrochoidalAndContourParallelStrategy.CCW);
			}
			else
			{
				trocoidal.setRotationDirectionCCW(TrochoidalAndContourParallelStrategy.CW);
			}
			
			return trocoidal;
		} 
		else if(eTwo5d_milling_strategy.getClass() == CContour_parallel.class)
		{
			EContour_parallel eContourn = (EContour_parallel)eTwo5d_milling_strategy;
			ContourParallel contourn = new ContourParallel();
			contourn.setAllowMultiplePasses(eContourn.getAllow_multiple_passes(null));
			contourn.setCutmodeType(eContourn.getCutmode(null));
			contourn.setOverLap(eContourn.getOverlap(null));
			if(eContourn.getRotation_direction(null) == ERot_direction.CCW) //baseline direction
			{
				contourn.setRotationDirection(RotationDirection.CCW);
			}
			else
			{
				contourn.setRotationDirection(RotationDirection.CW);
			}
			return contourn;
		}
		else if(eTwo5d_milling_strategy.getClass() == CBidirectional.class)
		{
			EBidirectional eBidirecional = (EBidirectional)eTwo5d_milling_strategy;
			Bidirectional bidirecional = new Bidirectional();
			bidirecional.setAllowMultiplePasses(eBidirecional.getAllow_multiple_passes(null));
			bidirecional.setOverLap(eBidirecional.getOverlap(null));
//			bidirecional.setOverLap(eBidirecional.getOverlap(null));
//			{
//				bidirecional.setRotationDirection(RotationDirection.CCW);
//			}
//			else
//			{
//				bidirecional.setRotationDirection(RotationDirection.CW);
//			}
			return bidirecional;
		}
		return estrategia;
	}
	private static PlungeStrategy getPlungeStrategy(EMilling_type_operation operation) throws SdaiException
	{
		EPlunge_strategy plungeType = (EPlunge_strategy)operation.getApproach(null);
		PlungeStrategy plungeStrategy = null;
		if (plungeType.getClass() == CPlunge_toolaxis.class)
		{
			EPlunge_toolaxis pl = (EPlunge_toolaxis)plungeType;
			Point3d direction = new Point3d (pl.getTool_orientation(null).getDirection_ratios(null).getByIndex(1),pl.getTool_orientation(null).getDirection_ratios(null).getByIndex(2), pl.getTool_orientation(null).getDirection_ratios(null).getByIndex(3));
			plungeStrategy = new PlungeToolAxis();
			plungeStrategy.setToolDirection(direction);
		}
		if(plungeType.getClass() == CPlunge_ramp.class)
		{
			EPlunge_ramp pl = (EPlunge_ramp)plungeType;
			Point3d direction = new Point3d (pl.getTool_orientation(null).getDirection_ratios(null).getByIndex(1),pl.getTool_orientation(null).getDirection_ratios(null).getByIndex(2), pl.getTool_orientation(null).getDirection_ratios(null).getByIndex(3));
			double angle = pl.getAngle(null);
			plungeStrategy = new PlungeRamp(angle);
			plungeStrategy.setToolDirection(direction);
		}
		if(plungeType.getClass() == CPlunge_zigzag.class)
		{
			EPlunge_zigzag pl = (EPlunge_zigzag)plungeType;
			Point3d direction = new Point3d (pl.getTool_orientation(null).getDirection_ratios(null).getByIndex(1),pl.getTool_orientation(null).getDirection_ratios(null).getByIndex(2), pl.getTool_orientation(null).getDirection_ratios(null).getByIndex(3));
			double angle = pl.getAngle(null);
			double width = pl.getWidth(null);
			plungeStrategy = new PlungeZigzag(angle, width);
			plungeStrategy.setToolDirection(direction);
		}
		return plungeStrategy;
	}
	private static Point3d cartesianPoint(ECartesian_point eCartesian_point) throws SdaiException
	{
		Point3d point = new Point3d(eCartesian_point.getCoordinates(null).getByIndex(1), eCartesian_point.getCoordinates(null).getByIndex(2), eCartesian_point.getCoordinates(null).getByIndex(3));
		return point;
	}
}
