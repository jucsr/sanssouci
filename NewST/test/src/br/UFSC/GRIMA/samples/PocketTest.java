package br.UFSC.GRIMA.samples;

import java.util.ArrayList;
import java.util.Properties;

import javax.vecmath.Point3d;

import jsdai.SCombined_schema.ACartesian_point;
import jsdai.SCombined_schema.ACutting_component;
import jsdai.SCombined_schema.AExecutable;
import jsdai.SCombined_schema.AMachining_operation;
import jsdai.SCombined_schema.AProperty_parameter;
import jsdai.SCombined_schema.AWorkpiece;
import jsdai.SCombined_schema.AWorkpiece_setup;
import jsdai.SCombined_schema.EAxis2_placement_3d;
import jsdai.SCombined_schema.EBlock;
import jsdai.SCombined_schema.EBottom_and_side_rough_milling;
import jsdai.SCombined_schema.ECartesian_point;
import jsdai.SCombined_schema.EClosed_pocket;
import jsdai.SCombined_schema.EClosed_profile;
import jsdai.SCombined_schema.EContour_parallel;
import jsdai.SCombined_schema.ECutmode_type;
import jsdai.SCombined_schema.ECutting_component;
import jsdai.SCombined_schema.EDirection;
import jsdai.SCombined_schema.EDrilling;
import jsdai.SCombined_schema.EDrilling_type_strategy;
import jsdai.SCombined_schema.EExecutable;
import jsdai.SCombined_schema.EFacemill;
import jsdai.SCombined_schema.EHand;
import jsdai.SCombined_schema.EMachine_functions;
import jsdai.SCombined_schema.EMachining_operation;
import jsdai.SCombined_schema.EMachining_tool;
import jsdai.SCombined_schema.EMachining_workingstep;
import jsdai.SCombined_schema.EManufacturing_feature;
import jsdai.SCombined_schema.EMaterial;
import jsdai.SCombined_schema.EMilling_cutting_tool;
import jsdai.SCombined_schema.EMilling_machine_functions;
import jsdai.SCombined_schema.EMilling_technology;
import jsdai.SCombined_schema.EMilling_tool_dimension;
import jsdai.SCombined_schema.ENumeric_parameter;
import jsdai.SCombined_schema.EPlanar_pocket_bottom_condition;
import jsdai.SCombined_schema.EPlane;
import jsdai.SCombined_schema.EPlunge_toolaxis;
import jsdai.SCombined_schema.EPlus_minus_value;
import jsdai.SCombined_schema.EProject;
import jsdai.SCombined_schema.ERectangular_closed_profile;
import jsdai.SCombined_schema.ERot_direction;
import jsdai.SCombined_schema.ERound_hole;
import jsdai.SCombined_schema.ESetup;
import jsdai.SCombined_schema.ETechnology;
import jsdai.SCombined_schema.EThrough_bottom_condition;
import jsdai.SCombined_schema.EToleranced_length_measure;
import jsdai.SCombined_schema.ETool_reference_point;
import jsdai.SCombined_schema.ETwist_drill;
import jsdai.SCombined_schema.EWorkpiece;
import jsdai.SCombined_schema.EWorkpiece_setup;
import jsdai.SCombined_schema.EWorkplan;
import jsdai.SCombined_schema.SCombined_schema;
import jsdai.lang.A_double;
import jsdai.lang.SdaiException;
import jsdai.lang.SdaiIterator;
import jsdai.lang.SdaiModel;
import jsdai.lang.SdaiRepository;
import jsdai.lang.SdaiSession;
import br.UFSC.GRIMA.entidades.features.Feature;

public class PocketTest 
{
	static SdaiModel model;
	static SdaiRepository repository;
	static double blockHigh = 20;
	static EWorkpiece eWorkpiece = null;
	static EPlane securityPlane;
	static double globalTolerance = 0.05;
	static double pocketLenght = 80, pocketWidth = 50, pocketDepth = 30, pocketPlanarRadius = 10, ortogonalRadius = 0, pocketTolerance = 0.01;
	static int pocketBottonCondition = 0;
	static ArrayList<ArrayList<Double>> pocketPlacement = new ArrayList<ArrayList<Double>>();
	static ArrayList<Double> pocketLocation = new ArrayList<Double>();
	static ArrayList<Double> pocketAxis = new ArrayList<Double>();
	static ArrayList<Double> pocketRefDirection = new ArrayList<Double>();
	static EWorkplan eWorkplan;
	static EProject eProject;
	static double toolLength = 120, toolTipAngle = 0 * Math.PI / 180, toolDiameter = 20, toolEdgeCuttingLength = 10;
	static double feedRate = 0.04, spindleRotation = 12.0;
	static ArrayList<Point3d> clampingPoints = new ArrayList<Point3d>();
	
	public static void main(String[] args) throws SdaiException 
	{
		Properties properties = new Properties();

		properties.setProperty("repositories", "C:/repositories.tmp");
		SdaiSession.setSessionProperties(properties);
		SdaiSession session = SdaiSession.openSession();
		session.startTransactionReadWriteAccess();
		repository = session.createRepository("", null);
		repository.openRepository();
		model = repository.createSdaiModel("DemoSTEP_NC", SCombined_schema.class);
		model.startReadWriteAccess();
		
		/**
		 *  steps for create a new pocket
		 *  1. create a project (setProject();) -- this will create the workpiece, block, etc... // this shall done only one time
		 *  2. create a setup (createSetup();) // this shall be done one time
		 *  3. create the operations related with the pocket (i.e. roughing, finishing... with createOperations();)
		 *  4. create the closed pocket (createClosedPocket();)
		 *  5. create the workplan (createWorkplan();)
		 *  
		 *  ready!
		 *  
		 */
		
		
		//================ init of project ==============
		
		Point3d point1 = new Point3d(60, 0.0, 25);
		Point3d point2 = new Point3d(60, 100, 25);
		Point3d point3 = new Point3d(50, 0.0, 25);
		Point3d point4 = new Point3d(50, 120, 25);
		
		clampingPoints.add(point1);
		clampingPoints.add(point2);
		clampingPoints.add(point3);
		clampingPoints.add(point4);
		
		setProject("pocket test project", globalTolerance);
		
		// == setup ==
		ArrayList<ArrayList<Double>> origin = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> location = new ArrayList<Double>();
		ArrayList<Double> axis = new ArrayList<Double>();
		ArrayList<Double> refDirection = new ArrayList<Double>();
		location.add(0, 0.0);
		location.add(1, 0.0);
		location.add(2, 0.0);
		
		axis.add(0, 1.0);
		axis.add(1, 0.0);
		axis.add(2, 0.0);

		refDirection.add(0, 0.0);
		refDirection.add(1, 0.0);
		refDirection.add(2, 1.0);

		origin.add(0, location);
		origin.add(1, axis);
		origin.add(2, refDirection);
		
		ESetup setup = createSetup("SETUP", origin);
		
		// == end setup ==
		
		pocketLocation.add(70.0);
		pocketLocation.add(60.0);
		pocketLocation.add(50.0);

		pocketAxis.add(1.0);
		pocketAxis.add(0.0);
		pocketAxis.add(0.0);
		
		pocketRefDirection.add(0.0);
		pocketRefDirection.add(0.0);
		pocketRefDirection.add(1.0);
		
		pocketPlacement.add(pocketLocation);
		pocketPlacement.add(pocketAxis);
		pocketPlacement.add(pocketRefDirection);
		
		EAxis2_placement_3d placement = createAxis2Placement3D("placement pocket", pocketLocation, pocketAxis, pocketRefDirection);
		
		ArrayList<Double> locationProf = new ArrayList<Double>();
		locationProf.add(0.0);
		locationProf.add(0.0);
		locationProf.add(0.0);

		double toolAp = 2.0;
		
		EMachining_operation operation = createOperations(Feature.CAVIDADE, 0, "rough pocket", securityPlane.getPosition(null).getLocation(null).getCoordinates(null).getByIndex(3), toolAp);
		
		EMachining_operation operationHole = createOperations(Feature.FURO, 0, "Drilling Hole", securityPlane.getPosition(null).getLocation(null).getCoordinates(null).getByIndex(3), toolAp);
		
		AMachining_operation operations = new AMachining_operation();
		operations.addUnordered(operation);
		
		AMachining_operation aMachining_operation = new AMachining_operation();
		aMachining_operation.addUnordered(operationHole);
		
		EAxis2_placement_3d profilePlacement = createAxis2Placement3D("placement profile", locationProf, pocketAxis, pocketRefDirection);
		ERectangular_closed_profile profile = createRectangularClosedProfile(profilePlacement, pocketLenght, pocketWidth);
		
		EClosed_pocket closed_pocket = createClosedPocket("pocket test", eWorkpiece, operations, placement, pocketDepth, pocketBottonCondition, pocketPlanarRadius, ortogonalRadius, profile);
		
		
		//EClosed_pocket closed_pocket2 = createClosedPocket("Pocket 02", eWorkpiece, operations, placement, 60, 0, 10, 5, profile);
		
		EMachining_workingstep eMachining_workingstep = createElements("workingstep pocket rough", securityPlane, closed_pocket, operation);
		
		//EMachining_workingstep eMachining_workingstep2 = createElements("WS POCKET2", securityPlane, closed_pocket2, operation);
		double holeDepth = 50, holeDiameter = 20;
		
		ERound_hole eRound_hole = createRoundHole("Round Hole", eWorkpiece, aMachining_operation, profilePlacement, holeDepth, 0, holeDiameter);
		EMachining_workingstep eMachining_workingstep2 = createElements("WS Hole", securityPlane, eRound_hole, operationHole);
		
		AExecutable aExecutable = new AExecutable();
		eWorkplan = createWorkplan("main workplan", aExecutable, setup);
		aExecutable.addByIndex(1, eMachining_workingstep);
		//aExecutable.addByIndex(2, eMachining_workingstep2);
		aExecutable.addByIndex(2, eMachining_workingstep2);
		
		AExecutable elements = eWorkplan.createIts_elements(null);
		
		SdaiIterator iterator = aExecutable.createIterator();
		int i = 1;
		while(iterator.next())
		{
			EExecutable executable = aExecutable.getCurrentMember(iterator);
			elements.addByIndex(i, executable);
			i++;
		}
		
		eProject.setMain_workplan(null, eWorkplan);

		generateFile21();
	}
	static private void setProject(String id, double globalTolerance) throws SdaiException
	{
		eProject = (EProject)model.createEntityInstance(EProject.class);
		eProject.setIts_id(null, id);
		AWorkpiece aWorkpiece = eProject.createIts_workpieces(null);
		aWorkpiece.addUnordered(createWorkpiece("workpiece", globalTolerance, 120, 100, 50, clampingPoints));
		
	}
	static EWorkplan createWorkplan(String id, AExecutable machiningWorkingsteps, ESetup setup) throws SdaiException
	{
		eWorkplan = (EWorkplan)model.createEntityInstance(EWorkplan.class);
		eWorkplan.setIts_id(null, id);
		eWorkplan.setIts_setup(null, setup);
		return eWorkplan;
	}
	private static EWorkpiece createWorkpiece(String id, double globalTolerance, double length, double width, double hight, ArrayList<Point3d> clampingPoints) throws SdaiException
	{
		if (eWorkpiece == null)
		{
			/*Iterator<Point3d> iterator = clampingPoints.iterator();
			while(iterator.hasNext());
			{
				ECartesian_point eCartesian_point = (ECartesian_point)model.createEntityInstance(ECartesian_point.class);
				A_double a_double = eCartesian_point.createCoordinates(null);
				
				Point3d pointTmp = iterator.next();
				
			}*/
			eWorkpiece = (EWorkpiece)model.createEntityInstance(EWorkpiece.class);
			ACartesian_point clamping = eWorkpiece.createClamping_positions(null);
			int i = 1;
			for(Point3d point : clampingPoints)
			{
				ECartesian_point eCartesian_point = createCartesianPoint("clamping point " + i, point);
				clamping.addUnordered(eCartesian_point);
				i++;
			}
			
			ArrayList<Double> location = new ArrayList<Double>();
			ArrayList<Double> axis = new ArrayList<Double>();
			ArrayList<Double> refDirection = new ArrayList<Double>();
			location.add(0, 0.0);
			location.add(1, 0.0);
			location.add(2, 0.0);
			
			axis.add(0, 1.0);
			axis.add(1, 0.0);
			axis.add(2, 0.0);
			
			refDirection.add(0, 0.0);
			refDirection.add(1, 0.0);
			refDirection.add(2, 1.0);
			
			eWorkpiece.setIts_id(null, id);
			eWorkpiece.setIts_material(null, createMaterial("SAE 1020", "", "Young Module", 200, "Mpa"));
			eWorkpiece.setGlobal_tolerance(null, globalTolerance);
			eWorkpiece.setIts_bounding_geometry(null, createBoundingGoemetry("piece", createAxis2Placement3D("workpiece null point", location, axis, refDirection), length, width, hight));
			
			
		}
		
		return eWorkpiece;
	}
	private static EBlock createBoundingGoemetry(String name, EAxis2_placement_3d position, double length, double width, double hight) throws SdaiException
	{
		EBlock eBlock = (EBlock)model.createEntityInstance(EBlock.class);
		eBlock.setName(null, name);
		eBlock.setPosition(null, position);
		eBlock.setX(null, length);
		eBlock.setY(null, width);
		eBlock.setZ(null, hight);
		return eBlock;
	}
	private static EMachining_workingstep createElements(String id, EPlane secPlane, EManufacturing_feature itsFeature, EMachining_operation machining_operation) throws SdaiException
	{
		EMachining_workingstep eMachining_workingstep = (EMachining_workingstep)model.createEntityInstance(EMachining_workingstep.class);
		eMachining_workingstep.setIts_id(null, id);
		eMachining_workingstep.setIts_secplane(null, secPlane);
		eMachining_workingstep.setIts_feature(null, itsFeature);
		eMachining_workingstep.setIts_operation(null, machining_operation);
		
		
		return eMachining_workingstep;
	}
	private static ESetup createSetup(String id, ArrayList<ArrayList<Double>> origin) throws SdaiException
	{
		ESetup eSetup = (ESetup)model.createEntityInstance(ESetup.class);
		eSetup.setIts_id(null, id);
		
		
		ArrayList<ArrayList<Double>> positionSecPlane = new ArrayList<ArrayList<Double>>();
		
		ArrayList<Double> location = new ArrayList<Double>();
		ArrayList<Double> axis = new ArrayList<Double>();
		ArrayList<Double> refDirection = new ArrayList<Double>();
		location.add(0, 0.0);
		location.add(1, 0.0);
		location.add(2, blockHigh + 5.0);
		
		axis.add(0, 1.0);
		axis.add(1, 0.0);
		axis.add(2, 0.0);

		refDirection.add(0, 0.0);
		refDirection.add(1, 0.0);
		refDirection.add(2, 1.0);

		positionSecPlane.add(0, location);
		positionSecPlane.add(1, axis);
		positionSecPlane.add(2, refDirection);
		
		// == coordinates for WORKPIECE_SETUP
		ArrayList<Double> locationWPS = new ArrayList<Double>();

		locationWPS.add(0, 0.0);
		locationWPS.add(1, 0.0);
		locationWPS.add(2, 0.0);
		//===
		
		securityPlane = createPlane("security plane", createAxis2Placement3D("", positionSecPlane.get(0), positionSecPlane.get(1), positionSecPlane.get(2)));
		eSetup.setIts_origin(null, createAxis2Placement3D("origin", origin.get(0), origin.get(1), origin.get(2)));
		eSetup.setIts_secplane(null, securityPlane);
		AWorkpiece_setup aWorkpiece_setup = eSetup.createIts_workpiece_setup(null);
		EWorkpiece_setup eWorkpiece_setup = (EWorkpiece_setup)model.createEntityInstance(EWorkpiece_setup.class);
		eWorkpiece_setup.setIts_workpiece(null, eWorkpiece);
		eWorkpiece_setup.setIts_origin(null, createAxis2Placement3D("", locationWPS, axis, refDirection));
		aWorkpiece_setup.addUnordered(eWorkpiece_setup);
		
		return eSetup;
	}
	private static EMaterial createMaterial(String standarId, String materialId, String paramName, double paramValue, String units) throws SdaiException
	{
		EMaterial eMaterial = (EMaterial)model.createEntityInstance(EMaterial.class);
		eMaterial.setStandard_identifier(null, standarId);
		eMaterial.setMaterial_identifier(null, materialId);
		ENumeric_parameter eNumeric_parameter = (ENumeric_parameter)model.createEntityInstance(ENumeric_parameter.class);
		eNumeric_parameter.setParameter_name(null, paramName);
		eNumeric_parameter.setIts_parameter_value(null, paramValue);
		eNumeric_parameter.setIts_parameter_unit(null, units);
		AProperty_parameter aProperty_parameter = eMaterial.createMaterial_property(null);
		aProperty_parameter.addUnordered(eNumeric_parameter);
		return eMaterial;
	}
	private static ECartesian_point createCartesianPoint(String name, ArrayList<Double> coordinates) throws SdaiException
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
	private static ECartesian_point createCartesianPoint(String name, Point3d coordinates) throws SdaiException
	{
		ECartesian_point eCartesian_point = (ECartesian_point)model.createEntityInstance(ECartesian_point.class);
		eCartesian_point.setName(null, name);
		A_double coord = eCartesian_point.createCoordinates(null);
		coord.addByIndex(1, coordinates.x);
		coord.addByIndex(2, coordinates.y);
		coord.addByIndex(3, coordinates.z);
		return eCartesian_point;
	}
	private static EDirection createDirection(String name, ArrayList<Double> ratios) throws SdaiException
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
	private static EAxis2_placement_3d createAxis2Placement3D(String name, ArrayList<Double> location, ArrayList<Double> axis, ArrayList<Double> refDirection) throws SdaiException
	{
		EAxis2_placement_3d eAxis2_placement_3d = (EAxis2_placement_3d)model.createEntityInstance(EAxis2_placement_3d.class);
		eAxis2_placement_3d.setName(null, name);
		eAxis2_placement_3d.setLocation(null, createCartesianPoint("", location));
		eAxis2_placement_3d.setAxis(null, createDirection("", axis));
		eAxis2_placement_3d.setRef_direction(null, createDirection("", refDirection));
		return eAxis2_placement_3d;
	}
	private static EPlane createPlane(String name, EAxis2_placement_3d position) throws SdaiException
	{
		EPlane ePlane = (EPlane)model.createEntityInstance(EPlane.class);
		ePlane.setName(null, name);
		ePlane.setPosition(null, position);
		return ePlane;
	}
	private static EClosed_pocket createClosedPocket(String id, EWorkpiece workpiece, AMachining_operation operations, EAxis2_placement_3d placement, double depth, int bottomCondition, double planarRadius, double ortogonalRadius, EClosed_profile profile) throws SdaiException
	{
		
		EClosed_pocket eClosed_pocket = (EClosed_pocket)model.createEntityInstance(EClosed_pocket.class);
		
		eClosed_pocket.setIts_id(null, id);
		eClosed_pocket.setIts_workpiece(null, eWorkpiece);
		AMachining_operation aMachining_operation = eClosed_pocket.createIts_operations(null);
		
		SdaiIterator iterator = operations.createIterator();
		while(iterator.next())
		{
			EMachining_operation eMachining_operation = operations.getCurrentMember(iterator);
			aMachining_operation.addUnordered(eMachining_operation);
		}
		
		
		eClosed_pocket.setFeature_placement(null, placement);
		
		ArrayList<Double> depthPlacement = new ArrayList<Double>();
		depthPlacement.add(0.0);
		depthPlacement.add(0.0);
		depthPlacement.add(depth);
		
		eClosed_pocket.setIts_workpiece(null, eWorkpiece);
		eClosed_pocket.setDepth(null, createPlane("depth of closed pocket", createAxis2Placement3D("", depthPlacement, pocketAxis, pocketRefDirection)));
		eClosed_pocket.setSlope(null, 0.0);
		if (bottomCondition == 0) // planar
		{
			EPlanar_pocket_bottom_condition	planar_pocket_bottom_condition = (EPlanar_pocket_bottom_condition)model.createEntityInstance(EPlanar_pocket_bottom_condition.class);
				eClosed_pocket.setBottom_condition(null, planar_pocket_bottom_condition);
		}
		
		eClosed_pocket.setPlanar_radius(null, createToleranceLengthMeasure(planarRadius, globalTolerance));
		eClosed_pocket.setOrthogonal_radius(null, createToleranceLengthMeasure(ortogonalRadius, globalTolerance));
		return eClosed_pocket;
	}
	private static ERound_hole createRoundHole(String id, EWorkpiece eWorkpiece, AMachining_operation operations, EAxis2_placement_3d placement, double depth, int bottomCondition, double diameter) throws SdaiException
	{
		ERound_hole eRound_hole = (ERound_hole)model.createEntityInstance(ERound_hole.class);
		eRound_hole.setIts_id(null, id);
		eRound_hole.setIts_workpiece(null, eWorkpiece);
		eRound_hole.setFeature_placement(null, placement);
		
		AMachining_operation aMachining_operation = eRound_hole.createIts_operations(null);
		
		SdaiIterator iterator = operations.createIterator();
		while(iterator.next())
		{
			EMachining_operation eMachining_operation = operations.getCurrentMember(iterator);
			aMachining_operation.addUnordered(eMachining_operation);
		}
		
		ArrayList<Double> holeDepthLocation = new ArrayList<Double>();
		holeDepthLocation.add(0.0);
		holeDepthLocation.add(0.0);
		holeDepthLocation.add(depth);

		eRound_hole.setDepth(null, createPlane("hole depth", createAxis2Placement3D("hole depth", holeDepthLocation, pocketAxis, pocketRefDirection)));
		eRound_hole.setDiameter(null, createToleranceLengthMeasure(diameter, pocketTolerance));
		
		if (bottomCondition == 0) // 0 --> Through Bottom Condition
		{
			EThrough_bottom_condition eThrough_bottom_condition = (EThrough_bottom_condition)model.createEntityInstance(EThrough_bottom_condition.class);
			eRound_hole.setBottom_condition(null, eThrough_bottom_condition);
		}
		return eRound_hole;
	}
	private static EMachining_operation createOperations(int featureType, int finishingType, String name, double retractPlane, double ap) throws SdaiException
	{
		if (featureType == Feature.RANHURA||featureType == Feature.CAVIDADE || featureType == Feature.DEGRAU && finishingType == 0) // finishingType = 0 --> roughing, = 1 --> finishing
		{
			EBottom_and_side_rough_milling eBottom_and_side_rough_milling =(EBottom_and_side_rough_milling)model.createEntityInstance(EBottom_and_side_rough_milling.class);
			eBottom_and_side_rough_milling.setIts_id(null, name);
			eBottom_and_side_rough_milling.setRetract_plane(null, retractPlane);
			eBottom_and_side_rough_milling.setIts_tool(null, createMachiningTool("tool", Feature.CAVIDADE, toolLength));
			eBottom_and_side_rough_milling.setIts_technology(null, createTechnology(feedRate, spindleRotation));
			eBottom_and_side_rough_milling.setIts_machine_functions(null, createMachineFunctions(Boolean.FALSE));
			eBottom_and_side_rough_milling.setOvercut_length(null, 0.00);
			EPlunge_toolaxis strategyApproach = (EPlunge_toolaxis)model.createEntityInstance(EPlunge_toolaxis.class);
			ArrayList<Double> ratios = new ArrayList<Double>();
			ratios.add(pocketRefDirection.get(0));
			ratios.add(pocketRefDirection.get(1));
			ratios.add(pocketRefDirection.get(2));
			
			EPlunge_toolaxis strategyRetract = (EPlunge_toolaxis)model.createEntityInstance(EPlunge_toolaxis.class);
			ArrayList<Double> ratios1 = new ArrayList<Double>();
			ratios1.add(pocketRefDirection.get(0));
			ratios1.add(pocketRefDirection.get(1));
			ratios1.add(-pocketRefDirection.get(2));
			
			EDirection approach = createDirection("approach strategy direction", ratios);
			strategyApproach.setTool_orientation(null, approach);
			
			EDirection retract = createDirection("retract strategy direction", ratios1);
			strategyRetract.setTool_orientation(null, retract);
			eBottom_and_side_rough_milling.setApproach(null, strategyApproach);
			eBottom_and_side_rough_milling.setRetract(null, strategyRetract);
			eBottom_and_side_rough_milling.setAxial_cutting_depth(null, ap);
			eBottom_and_side_rough_milling.setRadial_cutting_depth(null, 0.75 * toolDiameter);
			EContour_parallel eContour_parallel = (EContour_parallel)model.createEntityInstance(EContour_parallel.class);
			eContour_parallel.setOverlap(null, 0.25 * toolDiameter);
			eContour_parallel.setAllow_multiple_passes(null, Boolean.TRUE);
			eContour_parallel.setRotation_direction(null, ERot_direction.CCW);
			eContour_parallel.setCutmode(null, ECutmode_type.CONVENTIONAL);
			eBottom_and_side_rough_milling.setIts_machining_strategy(null, eContour_parallel);
			eBottom_and_side_rough_milling.setAllowance_side(null, 0.0);
			eBottom_and_side_rough_milling.setAllowance_bottom(null, 0.0);
			
			return eBottom_and_side_rough_milling;
		}
		else if (featureType == Feature.FURO && finishingType == 0)
		{
			EDrilling eDrilling = (EDrilling)model.createEntityInstance(EDrilling.class);
			eDrilling.setIts_id(null, name);
			eDrilling.setRetract_plane(null, retractPlane);
			eDrilling.setIts_tool(null, createMachiningTool("Twist drill", featureType, toolLength));
			eDrilling.setIts_technology(null, createTechnology(feedRate, spindleRotation));
			eDrilling.setIts_machine_functions(null, createMachineFunctions(Boolean.FALSE));
			EDrilling_type_strategy eDrilling_type_strategy = (EDrilling_type_strategy)model.createEntityInstance(EDrilling_type_strategy.class);
			eDrilling.setIts_machining_strategy(null, eDrilling_type_strategy);
			return eDrilling;
		}
		return null;
	}
	private static ERectangular_closed_profile createRectangularClosedProfile(EAxis2_placement_3d placement, double length, double width) throws SdaiException
	{
		ERectangular_closed_profile eRectangular_closed_profile = (ERectangular_closed_profile)model.createEntityInstance(ERectangular_closed_profile.class);
		eRectangular_closed_profile.setPlacement(null, placement);
		eRectangular_closed_profile.setProfile_length(null, createToleranceLengthMeasure(pocketLenght, pocketTolerance));
		eRectangular_closed_profile.setProfile_width(null, createToleranceLengthMeasure(pocketWidth, pocketTolerance));
		
		return eRectangular_closed_profile;
	}
	private static EToleranced_length_measure createToleranceLengthMeasure(double theoricalSize, double tolerance) throws SdaiException
	{
		EToleranced_length_measure eToleranced_length_measure = (EToleranced_length_measure)model.createEntityInstance(EToleranced_length_measure.class);
		EPlus_minus_value ePlus_minus_value = (EPlus_minus_value)model.createEntityInstance(EPlus_minus_value.class);
		ePlus_minus_value.setUpper_limit(null, tolerance);
		ePlus_minus_value.setLower_limit(null, tolerance);
		ePlus_minus_value.setSignificant_digits(null, 4);

		eToleranced_length_measure.setTheoretical_size(null, theoricalSize);
		eToleranced_length_measure.setImplicit_tolerance(null, ePlus_minus_value);
		return eToleranced_length_measure;
	}
	private static EMachining_tool createMachiningTool(String id, int featureType, double offsetLength) throws SdaiException
	{
		EMilling_cutting_tool tool = (EMilling_cutting_tool)model.createEntityInstance(EMilling_cutting_tool.class);
		if(featureType == Feature.RANHURA ||featureType == Feature.DEGRAU || featureType == Feature.CAVIDADE)
		{
			EFacemill eFacemill = (EFacemill)model.createEntityInstance(EFacemill.class);
			EMilling_tool_dimension eTool_dimension = createToolDimension(toolDiameter, toolTipAngle, 0, toolEdgeCuttingLength, 0);
			eFacemill.setDimension(null, eTool_dimension);
			eFacemill.setDimension(null, eTool_dimension);
			tool.setIts_tool_body(null, eFacemill);
			ACutting_component aCutting_component = tool.createIts_cutting_edge(null);
			ECutting_component eCutting_component = (ECutting_component)model.createEntityInstance(ECutting_component.class);
			eCutting_component.setTool_offset_length(null, offsetLength);
			aCutting_component.addByIndex(1, eCutting_component);
		}
		else if(featureType == Feature.FURO)
		{
			ETwist_drill eTwist_drill = (ETwist_drill)model.createEntityInstance(ETwist_drill.class);
			EMilling_tool_dimension eTool_dimension = createToolDimension(toolDiameter, toolTipAngle, 0, toolEdgeCuttingLength, 0);
			eTwist_drill.setDimension(null, eTool_dimension);
			eTwist_drill.setNumber_of_teeth(null, 2);
			eTwist_drill.setHand_of_cut(null, EHand.RIGHT);
			eTwist_drill.setPilot_length(null, offsetLength);
			tool.setIts_tool_body(null, eTwist_drill);
			ACutting_component aCutting_component = tool.createIts_cutting_edge(null);
			ECutting_component eCutting_component = (ECutting_component)model.createEntityInstance(ECutting_component.class);
			eCutting_component.setTool_offset_length(null, offsetLength);
			aCutting_component.addByIndex(1, eCutting_component);
		}
		tool.setIts_id(null, id);
		
		return tool;
	}
	private static EMilling_tool_dimension createToolDimension(double diameter, double toolTipHalfAngle,  double toolCircunferenceAngle, double cuttingEdgeLength, double edgeRadius) throws SdaiException
	{
		EMilling_tool_dimension eTool_dimension = (EMilling_tool_dimension)model.createEntityInstance(EMilling_tool_dimension.class);
		eTool_dimension.setDiameter(null, diameter);
		eTool_dimension.setTool_tip_half_angle(null, toolTipHalfAngle);
		eTool_dimension.setTool_circumference_angle(null, toolCircunferenceAngle);
		eTool_dimension.setCutting_edge_length(null, cuttingEdgeLength);
		eTool_dimension.setEdge_radius(null, edgeRadius);
		return eTool_dimension;
	}
	private static ETechnology createTechnology(double feedrate, double spindleRot) throws SdaiException
	{
		EMilling_technology eMilling_technology = (EMilling_technology)model.createEntityInstance(EMilling_technology.class);
		eMilling_technology.setFeedrate(null, feedrate);
		eMilling_technology.setFeedrate_reference(null, ETool_reference_point.TCP);
		eMilling_technology.setSpindle(null, -spindleRot);
		eMilling_technology.setSynchronize_spindle_with_feed(null, Boolean.FALSE);
		eMilling_technology.setInhibit_feedrate_override(null, Boolean.FALSE);
		eMilling_technology.setInhibit_spindle_override(null, Boolean.FALSE);
		
		return eMilling_technology;
	}
	private static EMachine_functions createMachineFunctions(boolean coolant) throws SdaiException
	{
		EMilling_machine_functions functions = (EMilling_machine_functions)model.createEntityInstance(EMilling_machine_functions.class);
		functions.setCoolant(null, coolant);
		functions.setChip_removal(null, Boolean.TRUE);
		functions.setThrough_spindle_coolant(null, coolant);
		return functions;
	}
	
	
	
	private static void generateFile21() throws SdaiException 
	{
		repository.exportClearTextEncoding("STEP-NC_pocket File.p21");
		System.out.println("======= DONE=======");
	}
}
