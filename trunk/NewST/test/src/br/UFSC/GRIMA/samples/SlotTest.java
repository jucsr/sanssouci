package br.UFSC.GRIMA.samples;

import java.util.Properties;

import jsdai.SCombined_schema.ACartesian_point;
import jsdai.SCombined_schema.ACutting_component;
import jsdai.SCombined_schema.AExecutable;
import jsdai.SCombined_schema.AMachining_operation;
import jsdai.SCombined_schema.AProperty_parameter;
import jsdai.SCombined_schema.ASlot_end_type;
import jsdai.SCombined_schema.AWorkpiece;
import jsdai.SCombined_schema.AWorkpiece_setup;
import jsdai.SCombined_schema.EAxis2_placement_3d;
import jsdai.SCombined_schema.EBlock;
import jsdai.SCombined_schema.EBottom_and_side_rough_milling;
import jsdai.SCombined_schema.ECartesian_point;
import jsdai.SCombined_schema.EDirection;
import jsdai.SCombined_schema.EFacemill;
import jsdai.SCombined_schema.EHand;
import jsdai.SCombined_schema.ELinear_path;
import jsdai.SCombined_schema.EMachining_workingstep;
import jsdai.SCombined_schema.EMaterial;
import jsdai.SCombined_schema.EMilling_cutting_tool;
import jsdai.SCombined_schema.EMilling_machine_functions;
import jsdai.SCombined_schema.EMilling_technology;
import jsdai.SCombined_schema.EMilling_tool_dimension;
import jsdai.SCombined_schema.EOpen_slot_end_type;
import jsdai.SCombined_schema.EPlane;
import jsdai.SCombined_schema.EPlunge_toolaxis;
import jsdai.SCombined_schema.EPlus_minus_value;
import jsdai.SCombined_schema.EProject;
import jsdai.SCombined_schema.EProperty_parameter;
import jsdai.SCombined_schema.ESetup;
import jsdai.SCombined_schema.ESlot;
import jsdai.SCombined_schema.ESquare_u_profile;
import jsdai.SCombined_schema.EToleranced_length_measure;
import jsdai.SCombined_schema.EWorkpiece;
import jsdai.SCombined_schema.EWorkpiece_setup;
import jsdai.SCombined_schema.EWorkplan;
import jsdai.SCombined_schema.SCombined_schema;
import jsdai.lang.A_double;
import jsdai.lang.A_string;
import jsdai.lang.SdaiException;
import jsdai.lang.SdaiModel;
import jsdai.lang.SdaiRepository;
import jsdai.lang.SdaiSession;

public class SlotTest {

	public static void main(String[] args) throws SdaiException {
		Properties properties = new Properties();

		properties.setProperty("repositories", "C:/repositories.tmp");
		SdaiSession.setSessionProperties(properties);
		SdaiSession session = SdaiSession.openSession();
		session.startTransactionReadWriteAccess();
		SdaiRepository repository = session.createRepository("", null);
		repository.openRepository();
		SdaiModel model = repository.createSdaiModel("DemoSTEP_NC",
				SCombined_schema.class);
		model.startReadWriteAccess();

		EProject project = (EProject) model
				.createEntityInstance(EProject.class);
		project.setIts_id(null, "Frasen Prufung");

		EWorkplan workplan = (EWorkplan) model
				.createEntityInstance(EWorkplan.class);
		workplan.setIts_id(null, "Arbeitsplan");

		EWorkpiece workpiece = (EWorkpiece) model
				.createEntityInstance(EWorkpiece.class);
		workpiece.setIts_id(null, "Bauteil1");

		EMachining_workingstep machining_workingstep = (EMachining_workingstep) model
				.createEntityInstance(EMachining_workingstep.class);
		machining_workingstep.setIts_id(null, "Frasen");

		ESetup setup = (ESetup) model.createEntityInstance(ESetup.class);
		setup.setIts_id(null, "Aufspannung_Bohrung");

		EMaterial material = (EMaterial) model
				.createEntityInstance(EMaterial.class);
		material.setMaterial_identifier(null, "St50");
		material.setStandard_identifier(null, "Stahl");

		EProperty_parameter property_parameter = (EProperty_parameter) model
				.createEntityInstance(EProperty_parameter.class);
		property_parameter.setParameter_name(null, "E210000 N/MM^2");

		EBlock block = (EBlock) model.createEntityInstance(EBlock.class);
		block.setName(null, "Block");

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

		EPlane plane = (EPlane) model.createEntityInstance(EPlane.class);
		plane.setName(null, "");

		EAxis2_placement_3d axis2_placement_3d1 = (EAxis2_placement_3d) model
				.createEntityInstance(EAxis2_placement_3d.class);
		axis2_placement_3d1.setName(null, "");

		ECartesian_point cartesian_point1 = (ECartesian_point) model
				.createEntityInstance(ECartesian_point.class);
		cartesian_point1.setName(null, "");

		// perfil a ser extrudado
		ESquare_u_profile eSquare_u_profile = (ESquare_u_profile) model
				.createEntityInstance(ESquare_u_profile.class);
		eSquare_u_profile.setPlacement(null, axis2_placement_3d);

		ESlot eSlot = (ESlot) model.createEntityInstance(ESlot.class);
		eSlot.setIts_id(null, "Slot_ID");

		// setar os parametros
		EMilling_cutting_tool eMilling_cutting_tool = (EMilling_cutting_tool) model
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
		eBottom_and_side_rough_milling.setIts_tool(null, eMilling_cutting_tool);
		eBottom_and_side_rough_milling.setApproach(null, ePlunge_toolaxis);
		eBottom_and_side_rough_milling.setRetract(null, ePlunge_toolaxis1);
		eBottom_and_side_rough_milling.setAxial_cutting_depth(null, 2.0);
		eBottom_and_side_rough_milling.setRadial_cutting_depth(null, 20.0);
		eBottom_and_side_rough_milling.setAllowance_bottom(null, 0.5);
		eBottom_and_side_rough_milling.setAllowance_side(null, 0.5);

		// EDrilling drilling =
		// (EDrilling)model.createEntityInstance(EDrilling.class);
		// drilling.setIts_id(null, "Bohrung 20mm");

		EMilling_cutting_tool cutting_tool = (EMilling_cutting_tool) model
				.createEntityInstance(EMilling_cutting_tool.class);

		EMilling_tool_dimension tool_dimension = (EMilling_tool_dimension) model
				.createEntityInstance(EMilling_tool_dimension.class);

		EMilling_technology milling_technology = (EMilling_technology) model
				.createEntityInstance(EMilling_technology.class);

		EMilling_machine_functions milling_machine_functions = (EMilling_machine_functions) model
				.createEntityInstance(EMilling_machine_functions.class);

		EAxis2_placement_3d axis2_placement_3d2 = (EAxis2_placement_3d) model
				.createEntityInstance(EAxis2_placement_3d.class);
		axis2_placement_3d2.setName(null, "Bohrung");

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
		toleranced_length_measure.setTheoretical_size(null, 20.000);
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
		a_double.addByIndex(3, -20.0);

		EAxis2_placement_3d eAxis2_placement_3d3 = (EAxis2_placement_3d) model
				.createEntityInstance(EAxis2_placement_3d.class);
		eAxis2_placement_3d3.setRef_direction(null, direction3);
		eAxis2_placement_3d3.setName(null, "depth");
		eAxis2_placement_3d3.setLocation(null, cartesian_point6);

		EPlane ePlane = (EPlane) model.createEntityInstance(EPlane.class);
		ePlane.setName(null, "slot plane depth");
		ePlane.setPosition(null, eAxis2_placement_3d3);

		EAxis2_placement_3d eAxis2_placement_3d4 = (EAxis2_placement_3d) model
				.createEntityInstance(EAxis2_placement_3d.class);

		ELinear_path eLinearPath = (ELinear_path) model
				.createEntityInstance(ELinear_path.class);
		eLinearPath.setPlacement(null, eAxis2_placement_3d4);

		EAxis2_placement_3d axis2_placement_3d3 = (EAxis2_placement_3d) model
				.createEntityInstance(EAxis2_placement_3d.class);
		axis2_placement_3d3.setName(null,
				"Nullpunkt relativ zum Maschinennullpunkt");

		ECartesian_point cartesian_point4 = (ECartesian_point) model
				.createEntityInstance(ECartesian_point.class);
		cartesian_point4.setName(null, "");

		EDirection direction4 = (EDirection) model
				.createEntityInstance(EDirection.class);
		direction4.setName(null, "");

		EDirection direction5 = (EDirection) model
				.createEntityInstance(EDirection.class);
		direction5.setName(null, "");

		EPlane plane1 = (EPlane) model.createEntityInstance(EPlane.class);
		plane1.setName(null, "");

		EAxis2_placement_3d axis2_placement_3d4 = (EAxis2_placement_3d) model
				.createEntityInstance(EAxis2_placement_3d.class);
		axis2_placement_3d4.setName(null, "Sicherheitsebene");

		ECartesian_point cartesian_point3 = (ECartesian_point) model
				.createEntityInstance(ECartesian_point.class);
		cartesian_point4.setName(null, "");

		// workpiece_position n�o existe no schema
		// foi substituido por workpiece_setup
		EWorkpiece_setup workpiece_setup = (EWorkpiece_setup) model
				.createEntityInstance(EWorkpiece_setup.class);

		EAxis2_placement_3d axis2_placement_3d5 = (EAxis2_placement_3d) model
				.createEntityInstance(EAxis2_placement_3d.class);
		axis2_placement_3d5.setName(null, "Werkstucknullpkt");

		ECartesian_point cartesian_point5 = (ECartesian_point) model
				.createEntityInstance(ECartesian_point.class);
		cartesian_point5.setName(null, "");

		EDirection direction6 = (EDirection) model
				.createEntityInstance(EDirection.class);
		direction6.setName(null, "");

		EDirection direction7 = (EDirection) model
				.createEntityInstance(EDirection.class);
		direction7.setName(null, "");

		// atributos
		axis2_placement_3d.setLocation(null, cartesian_point);
		axis2_placement_3d.setAxis(null, direction);
		axis2_placement_3d.setRef_direction(null, direction1);

		block.setPosition(null, axis2_placement_3d);
		block.setX(null, 110.000);
		block.setY(null, 110.000);
		block.setZ(null, 80.000);

		workpiece.setIts_material(null, material);
		workpiece.setGlobal_tolerance(null, 0.01);
		workpiece.setIts_bounding_geometry(null, block);

		axis2_placement_3d1.setLocation(null, cartesian_point1);

		plane.setPosition(null, axis2_placement_3d1);

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

		ASlot_end_type aSlot_end_type = eSlot.createEnd_conditions(null);
		aSlot_end_type.addByIndex(1, eOpen_slot_end_type);
		aSlot_end_type.addByIndex(2, eOpen_slot_end_type);

		machining_workingstep.setIts_secplane(null, plane);
		machining_workingstep.setIts_feature(null, eSlot);
		machining_workingstep.setIts_operation(null,
				eBottom_and_side_rough_milling);

		axis2_placement_3d3.setLocation(null, cartesian_point3);
		axis2_placement_3d3.setAxis(null, direction4);
		axis2_placement_3d3.setRef_direction(null, direction5);

		axis2_placement_3d4.setLocation(null, cartesian_point4);

		plane1.setPosition(null, axis2_placement_3d4);

		setup.setIts_origin(null, axis2_placement_3d3);
		setup.setIts_secplane(null, plane1);

		workplan.setIts_setup(null, setup);

		project.setMain_workplan(null, workplan);

		tool_dimension.setDiameter(null, 20.000);
		tool_dimension.setTool_tip_half_angle(null, 0.00);

		EFacemill facemill = (EFacemill) model
				.createEntityInstance(EFacemill.class);

		facemill.setDimension(null, tool_dimension);
		facemill.setNumber_of_teeth(null, 2);
		facemill.setHand_of_cut(null, EHand.RIGHT);
		facemill.setCoolant_through_tool(null, false);
		cutting_tool.setIts_tool_body(null, facemill);

		workpiece_setup.setIts_workpiece(null, workpiece);
		workpiece_setup.setIts_origin(null, axis2_placement_3d5);

		axis2_placement_3d5.setLocation(null, cartesian_point5);
		axis2_placement_3d5.setAxis(null, direction6);
		axis2_placement_3d5.setRef_direction(null, direction7);

		cutting_tool.setIts_tool_body(null, facemill);
		cutting_tool.setOverall_assembly_length(null, 75.000);

		// coordenadas para "cartesian_point" e "direction"
		A_double p = cartesian_point.createCoordinates(null);
		p.addByIndex(1, -5.0);
		p.addByIndex(2, -5.0);
		p.addByIndex(3, -5.0);

		A_double p1 = cartesian_point1.createCoordinates(null);
		p1.addByIndex(1, 0.0);
		p1.addByIndex(2, 0.0);
		p1.addByIndex(3, 60.0);

		A_double p2 = cartesian_point2.createCoordinates(null);
		p2.addByIndex(1, 50.0);
		p2.addByIndex(2, 50.0);
		p2.addByIndex(3, 60.0);

		A_double p3 = cartesian_point3.createCoordinates(null);
		p3.addByIndex(1, 20.0);
		p3.addByIndex(2, 40.0);
		p3.addByIndex(3, 120.0);

		A_double p4 = cartesian_point4.createCoordinates(null);
		p4.addByIndex(1, 0.0);
		p4.addByIndex(2, 0.0);
		p4.addByIndex(3, 65.0);

		A_double p5 = cartesian_point5.createCoordinates(null);
		p5.addByIndex(1, 0.0);
		p5.addByIndex(2, 0.0);
		p5.addByIndex(3, 0.0);

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
		d3.addByIndex(1, 1.0);
		d3.addByIndex(2, 0.0);
		d3.addByIndex(3, 0.0);

		A_double d4 = direction4.createDirection_ratios(null);
		d4.addByIndex(1, 0.0);
		d4.addByIndex(2, 0.0);
		d4.addByIndex(3, 1.0);

		A_double d5 = direction5.createDirection_ratios(null);
		d5.addByIndex(1, 1.0);
		d5.addByIndex(2, 0.0);
		d5.addByIndex(3, 0.0);

		A_double d6 = direction6.createDirection_ratios(null);
		d6.addByIndex(1, 0.0);
		d6.addByIndex(2, 0.0);
		d6.addByIndex(3, 1.0);

		A_double d7 = direction7.createDirection_ratios(null);
		d7.addByIndex(1, 1.0);
		d7.addByIndex(2, 0.0);
		d7.addByIndex(3, 0.0);

		// Atributos tipo "LIST"

		AWorkpiece_setup workpiece_setup_a = setup
				.createIts_workpiece_setup(null);
		workpiece_setup_a.addByIndex(1, workpiece_setup);

		// Atributos tipo "SET"
		AWorkpiece work = project.createIts_workpieces(null);
		work.addUnordered(workpiece, null);

		AExecutable machining = workplan.createIts_elements(null);
		machining.addUnordered(machining_workingstep, null);

		ACartesian_point cart = workpiece.createClamping_positions(null);

		AProperty_parameter property = material.createMaterial_property(null);
		property.addUnordered(property_parameter, null);

		AMachining_operation dril = eSlot.createIts_operations(null);
		dril.addUnordered(eBottom_and_side_rough_milling);

		ACutting_component cut = cutting_tool.createIts_cutting_edge(null);

		A_string id = milling_machine_functions.createAxis_clamping(null);
		AProperty_parameter prop = milling_machine_functions
				.createOther_functions(null);

		repository.exportClearTextEncoding("STEP-NC_slot File.p21");
		session.closeSession();
		System.out.println("====== DONE ======");
	}

}
