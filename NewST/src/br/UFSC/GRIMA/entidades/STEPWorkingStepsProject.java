package br.UFSC.GRIMA.entidades;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import jsdai.SCombined_schema.AMachining_operation;
import jsdai.SCombined_schema.ASlot_end_type;
import jsdai.SCombined_schema.EAxis2_placement_3d;
import jsdai.SCombined_schema.EBoring_tool;
import jsdai.SCombined_schema.EBottom_and_side_rough_milling;
import jsdai.SCombined_schema.ECartesian_point;
import jsdai.SCombined_schema.ECutting_tool;
import jsdai.SCombined_schema.EDirection;
import jsdai.SCombined_schema.EDrilling;
import jsdai.SCombined_schema.EFacemill;
import jsdai.SCombined_schema.EHand;
import jsdai.SCombined_schema.ELinear_path;
import jsdai.SCombined_schema.EMilling_cutting_tool;
import jsdai.SCombined_schema.EMilling_machine_functions;
import jsdai.SCombined_schema.EMilling_technology;
import jsdai.SCombined_schema.EMilling_tool_dimension;
import jsdai.SCombined_schema.EOpen_slot_end_type;
import jsdai.SCombined_schema.EPlane;
import jsdai.SCombined_schema.EPlunge_toolaxis;
import jsdai.SCombined_schema.EPlus_minus_value;
import jsdai.SCombined_schema.ERound_hole;
import jsdai.SCombined_schema.ESlot;
import jsdai.SCombined_schema.ESquare_u_profile;
import jsdai.SCombined_schema.EThrough_bottom_condition;
import jsdai.SCombined_schema.EToleranced_length_measure;
import jsdai.lang.A_double;
import jsdai.lang.SdaiException;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.exceptions.MissingDataException;

public class STEPWorkingStepsProject extends STEPProject {

	protected ERound_hole round_hole;
	protected EDrilling drilling;
	protected EToleranced_length_measure toleranced_length_measure1;
	protected EMilling_technology milling_technology;
	protected EMilling_machine_functions milling_machine_functions;
	protected double HoleRay;
	protected String HoleId;
	protected ECutting_tool cutting_tool;
	protected EBoring_tool boring_tool;
	protected String ToolId;
	private EPlane secPlane;

	public STEPWorkingStepsProject() throws SdaiException {
		super();
	}

	public ERound_hole getRound_hole() {
		return round_hole;
	}

	public void setRound_hole(ERound_hole round_hole) {
		this.round_hole = round_hole;
	}

	public String getToolId() {
		return ToolId;
	}

	public String getHoleId() {
		return HoleId;
	}

	public void setHoleId(String holeId) {
		HoleId = holeId;
	}

	public double getHoleRay() {
		return HoleRay;
	}

	public void setHoleRay(double holeRay) {
		HoleRay = holeRay;
	}

	public void addHoleWorkingStep(String workingStepId, double feedrate,
			double diameter, double x, double y, double z, String toolDepth, String toolTipAngle)
			throws SdaiException, MissingDataException {

		machining_workingstep = createWorkingStep(workingStepId);

		cutting_tool = (ECutting_tool) model
				.createEntityInstance(ECutting_tool.class);
		cutting_tool.setIts_id(null, "Drill " + diameter);

		EMilling_tool_dimension tool_dimension = (EMilling_tool_dimension) model
				.createEntityInstance(EMilling_tool_dimension.class);
		tool_dimension.setDiameter(null, diameter);
		tool_dimension.setTool_tip_half_angle(null, Double.parseDouble(toolTipAngle));
		tool_dimension.setCutting_edge_length(null, Double.parseDouble(toolDepth));

		boring_tool = (EBoring_tool) model
				.createEntityInstance(EBoring_tool.class);
		boring_tool.setDimension(null, tool_dimension);
		boring_tool.setNumber_of_teeth(null, 3);
		boring_tool.setHand_of_cut(null, EHand.RIGHT);
		boring_tool.setCoolant_through_tool(null, false);
		boring_tool.setRetract_movement_forbidden(null, true);

		cutting_tool.setIts_tool_body(null, boring_tool);
		cutting_tool.setOverall_assembly_length(null, 75.000);

		round_hole = (ERound_hole) model
				.createEntityInstance(ERound_hole.class);
		round_hole.setIts_id(null, "Hole " + diameter + "mm");

		drilling = (EDrilling) model.createEntityInstance(EDrilling.class);
		drilling.setIts_id(null, "Drill " + diameter + "mm");

		EAxis2_placement_3d axis2_placement_3d2 = (EAxis2_placement_3d) model
				.createEntityInstance(EAxis2_placement_3d.class);
		axis2_placement_3d2.setName(null, "Hole");

		ECartesian_point cartesian_point2 = (ECartesian_point) model
				.createEntityInstance(ECartesian_point.class);
		cartesian_point2.setName(null, "");

		EDirection direction2 = (EDirection) model
				.createEntityInstance(EDirection.class);
		direction2.setName(null, "");

		EDirection direction3 = (EDirection) model
				.createEntityInstance(EDirection.class);
		direction3.setName(null, "");

		A_double p2 = cartesian_point2.createCoordinates(null);
		p2.addByIndex(1, x);
		p2.addByIndex(2, y);
		p2.addByIndex(3, z);

		A_double d2 = direction2.createDirection_ratios(null);
		d2.addByIndex(1, 0.0);
		d2.addByIndex(2, 0.0);
		d2.addByIndex(3, 1.0);

		A_double d3 = direction3.createDirection_ratios(null);
		d3.addByIndex(1, 1.0);
		d3.addByIndex(2, 0.0);
		d3.addByIndex(3, 0.0);

		axis2_placement_3d2.setLocation(null, cartesian_point2);
		axis2_placement_3d2.setAxis(null, direction2);
		axis2_placement_3d2.setRef_direction(null, direction3);

		toleranced_length_measure1 = (EToleranced_length_measure) model
				.createEntityInstance(EToleranced_length_measure.class);
		toleranced_length_measure1.setTheoretical_size(null, diameter);

		milling_technology = (EMilling_technology) model
				.createEntityInstance(EMilling_technology.class);

		milling_machine_functions = (EMilling_machine_functions) model
				.createEntityInstance(EMilling_machine_functions.class);

		milling_technology.setFeedrate(null, feedrate);
		milling_technology.setSynchronize_spindle_with_feed(null, false);
		milling_technology.setInhibit_feedrate_override(null, false);
		milling_technology.setInhibit_spindle_override(null, false);

		milling_machine_functions.setCoolant(null, true);
		milling_machine_functions.setMist(null, true);
		milling_machine_functions.setThrough_spindle_coolant(null, false);
		milling_machine_functions.setChip_removal(null, true);

		drilling.setRetract_plane(null, 10.000);
		drilling.setIts_technology(null, milling_technology);
		drilling.setIts_machine_functions(null, milling_machine_functions);
		drilling.setIts_tool(null, cutting_tool);

		EThrough_bottom_condition through_bottom_condition = (EThrough_bottom_condition) model
				.createEntityInstance(EThrough_bottom_condition.class);

		round_hole.setFeature_placement(null, axis2_placement_3d2);
		round_hole.setDiameter(null, toleranced_length_measure1);
		round_hole.setBottom_condition(null, through_bottom_condition);

		round_hole.setIts_workpiece(null, workpiece);
		if (secPlane == null) {
			ECartesian_point cartesian_point1 = (ECartesian_point) model
					.createEntityInstance(ECartesian_point.class);
			cartesian_point1.setName(null, "");
			A_double p1 = cartesian_point1.createCoordinates(null);
			p1.addByIndex(1, 0.0);
			p1.addByIndex(2, 0.0);
			p1.addByIndex(3, (block.getZ(null) + 10.0));

			EAxis2_placement_3d axis2_placement_3d1 = (EAxis2_placement_3d) model
					.createEntityInstance(EAxis2_placement_3d.class);
			axis2_placement_3d1.setName(null, "");
			axis2_placement_3d1.setLocation(null, cartesian_point1);

			secPlane = (EPlane) model.createEntityInstance(EPlane.class);
			secPlane.setName(null, "secPlane");
			secPlane.setPosition(null, axis2_placement_3d1);

		}

		AMachining_operation dril = round_hole.createIts_operations(null);
		dril.addUnordered(drilling, null);
		machining_workingstep.setIts_secplane(null, secPlane);
		machining_workingstep.setIts_feature(null, round_hole);
		machining_workingstep.setIts_operation(null, drilling);

		addWorkingStep(machining_workingstep);

	}

	public void setToolId(String string) {
		ToolId = string;

	}

	public void addSlotWornkingStep(String name, Point3d placement,
			double depth, double width, double toolWidth, int axis, double ap)
			throws SdaiException {

		machining_workingstep = createWorkingStep(name);

		EAxis2_placement_3d axis2_placement_3d = (EAxis2_placement_3d) model
				.createEntityInstance(EAxis2_placement_3d.class);
		axis2_placement_3d.setName(null, "umschl. Korper relativ zum werkstuk");

		ECartesian_point cartesian_point = (ECartesian_point) model
				.createEntityInstance(ECartesian_point.class);
		cartesian_point.setName(null, "");

		EDirection direction = (EDirection) model
				.createEntityInstance(EDirection.class);
		direction.setName(null, "");

		EDirection direction1 = (EDirection) model
				.createEntityInstance(EDirection.class);
		direction1.setName(null, "");

		// perfil a ser extrudado
		ESquare_u_profile eSquare_u_profile = (ESquare_u_profile) model
				.createEntityInstance(ESquare_u_profile.class);
		eSquare_u_profile.setPlacement(null, axis2_placement_3d);

		ESlot eSlot = (ESlot) model.createEntityInstance(ESlot.class);
		eSlot.setIts_id(null, name);

		// setar os parametros
		EMilling_cutting_tool cutting_tool = (EMilling_cutting_tool) model
				.createEntityInstance(EMilling_cutting_tool.class);

		EDirection eDirection = (EDirection) model
				.createEntityInstance(EDirection.class);
		eDirection.setName(null, "plunge direction");
		EPlunge_toolaxis ePlunge_toolaxis = (EPlunge_toolaxis) model
				.createEntityInstance(EPlunge_toolaxis.class);
		ePlunge_toolaxis.setTool_orientation(null, eDirection);

		EDirection eDirection1 = (EDirection) model
				.createEntityInstance(EDirection.class);
		eDirection.setName(null, "retract direction");
		EPlunge_toolaxis ePlunge_toolaxis1 = (EPlunge_toolaxis) model
				.createEntityInstance(EPlunge_toolaxis.class);
		ePlunge_toolaxis1.setTool_orientation(null, eDirection1);

		A_double direcRatios = eDirection.createDirection_ratios(null);
		direcRatios.addByIndex(1, 0.00);
		direcRatios.addByIndex(2, 0.00);
		direcRatios.addByIndex(3, -1.00);

		A_double direcRatios1 = eDirection1.createDirection_ratios(null);
		direcRatios1.addByIndex(1, 0.00);
		direcRatios1.addByIndex(2, 0.00);
		direcRatios1.addByIndex(3, 1.00);

		EBottom_and_side_rough_milling eBottom_and_side_rough_milling = (EBottom_and_side_rough_milling) model
				.createEntityInstance(EBottom_and_side_rough_milling.class);
		eBottom_and_side_rough_milling.setIts_id(null, "Frasen Werkzeug");
		eBottom_and_side_rough_milling.setRetract_plane(null, 10.00);
		eBottom_and_side_rough_milling.setIts_tool(null, cutting_tool);
		eBottom_and_side_rough_milling.setApproach(null, ePlunge_toolaxis);
		eBottom_and_side_rough_milling.setRetract(null, ePlunge_toolaxis1);
		eBottom_and_side_rough_milling.setAxial_cutting_depth(null, ap);
//		eBottom_and_side_rough_milling.setAllowance_bottom(null, 0.5);
//		eBottom_and_side_rough_milling.setAllowance_side(null, 0.5);

		// EDrilling drilling =
		// (EDrilling)model.createEntityInstance(EDrilling.class);
		// drilling.setIts_id(null, "Bohrung 20mm");

		EMilling_tool_dimension tool_dimension = (EMilling_tool_dimension) model
				.createEntityInstance(EMilling_tool_dimension.class);

		EMilling_technology milling_technology = (EMilling_technology) model
				.createEntityInstance(EMilling_technology.class);

		EMilling_machine_functions milling_machine_functions = (EMilling_machine_functions) model
				.createEntityInstance(EMilling_machine_functions.class);

		EAxis2_placement_3d axis2_placement_3d2 = (EAxis2_placement_3d) model
				.createEntityInstance(EAxis2_placement_3d.class);
		axis2_placement_3d2.setName(null, "Placement");

		ECartesian_point cartesian_point2 = (ECartesian_point) model
				.createEntityInstance(ECartesian_point.class);
		cartesian_point2.setName(null, "");

		EDirection direction2 = (EDirection) model
				.createEntityInstance(EDirection.class);
		direction2.setName(null, "");

		EDirection direction3 = (EDirection) model
				.createEntityInstance(EDirection.class);
		direction3.setName(null, "");

		EPlus_minus_value ePlus_minus_value = (EPlus_minus_value) model
				.createEntityInstance(EPlus_minus_value.class);
		ePlus_minus_value.setLower_limit(null, -0.05);
		ePlus_minus_value.setUpper_limit(null, 0.05);
		ePlus_minus_value.setSignificant_digits(null, 3);

		EToleranced_length_measure toleranced_length_measure = (EToleranced_length_measure) model
				.createEntityInstance(EToleranced_length_measure.class);
		toleranced_length_measure.setTheoretical_size(null, width);
		toleranced_length_measure
				.setImplicit_tolerance(null, ePlus_minus_value);

		EToleranced_length_measure eToleranced_length_measure = (EToleranced_length_measure) model
				.createEntityInstance(EToleranced_length_measure.class);
		EToleranced_length_measure eToleranced_length_measure2 = (EToleranced_length_measure) model
				.createEntityInstance(EToleranced_length_measure.class);

		eToleranced_length_measure.setTheoretical_size(null, 0.0);
		eToleranced_length_measure2.setTheoretical_size(null, 0.0);

		eSquare_u_profile.setWidth(null, toleranced_length_measure);
		eSquare_u_profile.setFirst_radius(null, eToleranced_length_measure);
		eSquare_u_profile.setFirst_angle(null, 0.0);
		eSquare_u_profile.setSecond_radius(null, eToleranced_length_measure2);
		eSquare_u_profile.setSecond_angle(null, 0.0);

		ECartesian_point cartesian_point6 = (ECartesian_point) model
				.createEntityInstance(ECartesian_point.class);
		cartesian_point6.setName(null, "location_depth");
		A_double a_double = cartesian_point6.createCoordinates(null);
		a_double.addByIndex(1, 0.0);
		a_double.addByIndex(2, 0.0);
		a_double.addByIndex(3, -1 * depth);

		EAxis2_placement_3d eAxis2_placement_3d3 = (EAxis2_placement_3d) model
				.createEntityInstance(EAxis2_placement_3d.class);
		
		EDirection eDirection4=(EDirection) model
		.createEntityInstance(EDirection.class);
		
		A_double a_double3 = eDirection4.createDirection_ratios(null);
		a_double3.addByIndex(1, 0);
		a_double3.addByIndex(2, 0);
		a_double3.addByIndex(3, 1);
		
		eAxis2_placement_3d3.setAxis(null, eDirection4);
		eAxis2_placement_3d3.setName(null, "depth");
		eAxis2_placement_3d3.setLocation(null, cartesian_point6);

		EPlane ePlane = (EPlane) model.createEntityInstance(EPlane.class);
		ePlane.setName(null, "slot plane depth");
		ePlane.setPosition(null, eAxis2_placement_3d3);

		EAxis2_placement_3d eAxis2_placement_3d4 = (EAxis2_placement_3d) model
				.createEntityInstance(EAxis2_placement_3d.class);
		eAxis2_placement_3d4.setName(null,"path");

		// set axis

		ECartesian_point eCartesian_point = (ECartesian_point) model
				.createEntityInstance(ECartesian_point.class);
		A_double a_double2 = eCartesian_point.createCoordinates(null);

		EDirection eDirection2 = (EDirection) model
				.createEntityInstance(EDirection.class);
		EDirection eDirection3 = (EDirection) model
				.createEntityInstance(EDirection.class);

		a_double3 = eDirection2.createDirection_ratios(null);
		a_double3.addByIndex(1, 1);
		a_double3.addByIndex(2, 0);
		a_double3.addByIndex(3, 0);

		a_double3 = eDirection3.createDirection_ratios(null);
		a_double3.addByIndex(1, 0);
		a_double3.addByIndex(2, 1);
		a_double3.addByIndex(3, 0);

		eAxis2_placement_3d4.setLocation(null, eCartesian_point);
		eAxis2_placement_3d4.setAxis(null, eDirection2);
		eAxis2_placement_3d4.setRef_direction(null, eDirection3);

		a_double2.addByIndex(1, 0);
		a_double2.addByIndex(2, 0);
		a_double2.addByIndex(3, 0);

		ELinear_path eLinearPath = (ELinear_path) model
				.createEntityInstance(ELinear_path.class);
		eLinearPath.setPlacement(null, eAxis2_placement_3d4);

		// workpiece_position n�o existe no schema
		// foi substituido por workpiece_setup

		// atributos
		axis2_placement_3d.setLocation(null, cartesian_point);
		axis2_placement_3d.setAxis(null, direction);
		axis2_placement_3d.setRef_direction(null, direction1);

		axis2_placement_3d2.setLocation(null, cartesian_point2);
		axis2_placement_3d2.setAxis(null, direction2);
		axis2_placement_3d2.setRef_direction(null, direction3);

		milling_technology.setFeedrate(null, 0.01);
		milling_technology.setSynchronize_spindle_with_feed(null, false);
		milling_technology.setInhibit_feedrate_override(null, false);
		milling_technology.setInhibit_spindle_override(null, false);

		milling_machine_functions.setCoolant(null, true);
		milling_machine_functions.setMist(null, true);
		milling_machine_functions.setThrough_spindle_coolant(null, false);
		milling_machine_functions.setChip_removal(null, true);

		eBottom_and_side_rough_milling.setIts_technology(null,
				milling_technology);
		eBottom_and_side_rough_milling.setIts_machine_functions(null,
				milling_machine_functions);
		eBottom_and_side_rough_milling.setRetract_plane(null, 10.000);
		eBottom_and_side_rough_milling.setIts_tool(null, cutting_tool);
		eBottom_and_side_rough_milling.setIts_technology(null,
				milling_technology);
		eBottom_and_side_rough_milling.setIts_machine_functions(null,
				milling_machine_functions);

		EOpen_slot_end_type eOpen_slot_end_type = (EOpen_slot_end_type) model
				.createEntityInstance(EOpen_slot_end_type.class);

		eSlot.setIts_workpiece(null, workpiece);
		eSlot.setFeature_placement(null, axis2_placement_3d2);
		eSlot.setDepth(null, ePlane);
		eSlot.setCourse_of_travel(null, eLinearPath);
		eSlot.setSwept_shape(null, eSquare_u_profile);
		
		AMachining_operation slotOperation = eSlot.createIts_operations(null);
		slotOperation.addUnordered(eBottom_and_side_rough_milling);

		ASlot_end_type aSlot_end_type = eSlot.createEnd_conditions(null);
		aSlot_end_type.addByIndex(1, eOpen_slot_end_type);
		aSlot_end_type.addByIndex(2, eOpen_slot_end_type);

		if (secPlane == null) {
			ECartesian_point cartesian_point1 = (ECartesian_point) model
					.createEntityInstance(ECartesian_point.class);
			cartesian_point1.setName(null, "");
			A_double p1 = cartesian_point1.createCoordinates(null);
			p1.addByIndex(1, 0.0);
			p1.addByIndex(2, 0.0);
			p1.addByIndex(3, (block.getZ(null) + 10.0));

			EAxis2_placement_3d axis2_placement_3d1 = (EAxis2_placement_3d) model
					.createEntityInstance(EAxis2_placement_3d.class);
			axis2_placement_3d1.setName(null, "");
			axis2_placement_3d1.setLocation(null, cartesian_point1);

			secPlane = (EPlane) model.createEntityInstance(EPlane.class);
			secPlane.setName(null, "secPlane");
			secPlane.setPosition(null, axis2_placement_3d1);
		}

		machining_workingstep.setIts_secplane(null, secPlane);
		machining_workingstep.setIts_feature(null, eSlot);
		machining_workingstep.setIts_operation(null,
				eBottom_and_side_rough_milling);

		tool_dimension.setDiameter(null, toolWidth);
		tool_dimension.setTool_tip_half_angle(null, 0.00);

		EFacemill facemill = (EFacemill) model
				.createEntityInstance(EFacemill.class);

		facemill.setDimension(null, tool_dimension);
		facemill.setNumber_of_teeth(null, 2);
		facemill.setHand_of_cut(null, EHand.RIGHT);
		facemill.setCoolant_through_tool(null, false);
		cutting_tool.setIts_tool_body(null, facemill);

		cutting_tool.setIts_tool_body(null, facemill);
		cutting_tool.setOverall_assembly_length(null, 75.000);

		// coordenadas para "cartesian_point" e "direction"
		A_double p = cartesian_point.createCoordinates(null);
		p.addByIndex(1, 0.0);
		p.addByIndex(2, 0.0);
		p.addByIndex(3, 0.0);


		A_double d = direction.createDirection_ratios(null);
		d.addByIndex(1, 0.0);
		d.addByIndex(2, 0.0);
		d.addByIndex(3, 1.0);

		A_double d1 = direction1.createDirection_ratios(null);
		d1.addByIndex(1, 1.0);
		d1.addByIndex(2, 0.0);
		d1.addByIndex(3, 0.0);

		A_double d2 = direction2.createDirection_ratios(null);
		d2.addByIndex(1, 0.0);
		d2.addByIndex(2, 0.0);
		d2.addByIndex(3, 1.0);

		A_double d3 = direction3.createDirection_ratios(null);
		if (axis == Ranhura.HORIZONTAL) {
			d3.addByIndex(1, 1.0);
			d3.addByIndex(2, 0.0);
			d3.addByIndex(3, 0.0);
			A_double p2 = cartesian_point2.createCoordinates(null);
			p2.addByIndex(1, 0);
			p2.addByIndex(2, placement.y);
			p2.addByIndex(3, placement.z);
		} else {
			A_double p2 = cartesian_point2.createCoordinates(null);
			p2.addByIndex(1, placement.x);
			p2.addByIndex(2, 0);
			p2.addByIndex(3, placement.z);
			d3.addByIndex(1, 0.0);
			d3.addByIndex(2, 1.0);
			d3.addByIndex(3, 0.0);
		}

		addWorkingStep(machining_workingstep);

	}
	public ECartesian_point createCartesianPoint(String name, ArrayList<Double> coordinates) throws SdaiException
	{
		ECartesian_point eCartesian_point = null;
		if(coordinates.size() == 3)
		{
			eCartesian_point = (ECartesian_point)model.createEntityInstance(ECartesian_point.class);
			eCartesian_point.setName(null, name);
			A_double coord = eCartesian_point.createCoordinates(null);
			coord.addByIndex(1, coordinates.get(0));
			coord.addByIndex(2, coordinates.get(1));
			coord.addByIndex(3, coordinates.get(2));
		}
		return eCartesian_point;
	}
	public EDirection createDirection(String name, ArrayList<Double> ratios) throws SdaiException
	{
		EDirection eDirection = null;
		if(ratios.size() == 3)
		{
			eDirection = (EDirection)model.createEntityInstance(EDirection.class);
			eDirection.setName(null, name);
			A_double rat = eDirection.createDirection_ratios(null);
			rat.addByIndex(1, ratios.get(0));
			rat.addByIndex(2, ratios.get(1));
			rat.addByIndex(3, ratios.get(2));
		}
		return eDirection;
	}
	public EAxis2_placement_3d createAxis2Placement3D(String name, ArrayList<Double> location, ArrayList<Double> axis, ArrayList<Double> refDirection) throws SdaiException
	{
		EAxis2_placement_3d eAxis2_placement_3d = (EAxis2_placement_3d)model.createEntityInstance(EAxis2_placement_3d.class);
		eAxis2_placement_3d.setName(null, name);
		eAxis2_placement_3d.setLocation(null, createCartesianPoint(name, location));
		eAxis2_placement_3d.setAxis(null, createDirection(name, axis));
		eAxis2_placement_3d.setRef_direction(null, createDirection(name, refDirection));
		return eAxis2_placement_3d;
	}
	public EAxis2_placement_3d createAxis2Placement3D(String name, ECartesian_point location, EDirection axis, EDirection refDirection) throws SdaiException
	{
		EAxis2_placement_3d eAxis2_placement_3d = (EAxis2_placement_3d)model.createEntityInstance(EAxis2_placement_3d.class);
		eAxis2_placement_3d.setName(null, name);
		eAxis2_placement_3d.setLocation(null, location);
		eAxis2_placement_3d.setAxis(null, axis);
		eAxis2_placement_3d.setRef_direction(null, refDirection);
		return eAxis2_placement_3d;
	}
}
