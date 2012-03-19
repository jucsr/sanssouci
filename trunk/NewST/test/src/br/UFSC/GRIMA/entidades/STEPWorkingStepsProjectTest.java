package br.UFSC.GRIMA.entidades;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import jsdai.SCombined_schema.EAxis2_placement_3d;
import jsdai.SCombined_schema.ECartesian_point;
import jsdai.SCombined_schema.EDirection;
import jsdai.lang.A_double;
import jsdai.lang.SdaiException;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.exceptions.MissingDataException;

public class STEPWorkingStepsProjectTest {

	
	
	@Test
	public void createSimpleHoleProject() {
		try {
			STEPWorkingStepsProject project = ProjectManager.getProject();

			project.setProject("Hole");

			project.setMaterial("St50", "Stahl", "E210000 N/MM^2");

			project.setWorkPiece("Bauteil1", 110.000, 110.000, 40.000);

			project.addHoleWorkingStep("Hole0", 0.01, 10, 50.000, 50.000,
					50.000,"15","45");

			project.addHoleWorkingStep("Hole1", 0.02, 12, 80.000, 80.000,
					50.000,"15","45");

			project.addHoleWorkingStep("Hole2", 0.03, 13, 90.000, 90.000,
					50.000,"15","45");

			project.relationateVars();
			System.out.println(project.createSTEP21File());
			project.closeSession();

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void createSimpleSlotProject() {
		try {
			STEPWorkingStepsProject project = ProjectManager.getProject();

			project.setProject("Frasen Prufung");

			project.setMaterial("St50", "Stahl", "E210000 N/MM^2");

			project.setWorkPiece("Bauteil1", 110.000, 110.000, 40.000);

			Point3d point3d = new Point3d(50.0, 50.0, 60.0);
			double depth = 20.0;
			double width = 20.0;
			double toolWidth = width;
			int axis = Ranhura.VERTICAL;
			double ap = 2.0;

			project.addSlotWornkingStep("Slot_ID", point3d, depth, width,
					toolWidth, axis, ap);

			// project.addHoleWorkingStep("Hole0", 0.01,10, 50.000, 50.000,
			// 50.000);
			//
			// project.addHoleWorkingStep("Hole1", 0.02,12, 80.000, 80.000,
			// 50.000);
			//
			// project.addHoleWorkingStep("Hole2", 0.03,13, 90.000, 90.000,
			// 50.000);
			//			
			project.relationateVars();
			String answer = project.createSTEP21FileHere();
			project.closeSession();

			System.out.println(answer);

			Assert.assertNotSame(-1, answer.indexOf("PLUNGE_TOOLAXIS"));

			Assert.assertNotNull(answer);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void holeAndSlotTest() {

		STEPWorkingStepsProject project;
		try {
			project = ProjectManager.getProject();

			project.setProject("OneHoleOneSlot");

			project.setMaterial("St50", "Stahl", "E210000 N/MM^2");

			project.setWorkPiece("Bauteil1", 110.000, 110.000, 40.000);

			Point3d point3d = new Point3d(50.0, 50.0, 40.0);
			double depth = 20.0;
			double width = 20.0;
			double toolWidth = width;
			double ap = 3.0;

			project.addSlotWornkingStep("Slot1", point3d, depth, width,
					toolWidth, Ranhura.VERTICAL, ap);

			project
					.addHoleWorkingStep("Hole1", 0.01, 5, 50.000, 50.000,
							20.000,"15","45");

			project.relationateVars();
			String answer = project.createSTEP21FileHere();
			project.closeSession();

			System.out.println(answer);

			Assert.assertNotSame(-1, answer.indexOf("PLUNGE_TOOLAXIS"));

			Assert.assertNotNull(answer);

		} catch (SdaiException e) {
			e.printStackTrace();
			fail();
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail();
		} catch (MissingDataException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void createCartesianPointTest() {
		try {
			STEPWorkingStepsProject project;
			project = ProjectManager.getProject();
			ArrayList<Double> coordinates = new ArrayList<Double>();
			coordinates.add(50.0);
			coordinates.add(60.0);
			coordinates.add(70.0);

			ECartesian_point eCartesian_point = project.createCartesianPoint(
					"", coordinates);
			A_double coord = eCartesian_point.getCoordinates(null);
			double x = coord.getByIndex(1);
			project.closeSession();
			Assert.assertEquals(50.0, x);
		} catch (SdaiException e) {
			e.printStackTrace();
			fail();
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void createDirectionTest() {
		try {
			STEPWorkingStepsProject project;
			project = ProjectManager.getProject();
			ArrayList<Double> direction = new ArrayList<Double>();
			direction.add(0, 1.0);
			direction.add(1, 0.0);
			direction.add(2, 0.0);

			EDirection ratios = project.createDirection("", direction);
			A_double coord = ratios.getDirection_ratios(null);
			double x = coord.getByIndex(1);
			project.closeSession();
			Assert.assertEquals(1.0, x);
		} catch (SdaiException e) {
			e.printStackTrace();
			fail();
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void createAxis2Placement3DTest() {
		try {
			STEPWorkingStepsProject project;
			project = ProjectManager.getProject();
			ArrayList<Double> location = new ArrayList<Double>(), axis = new ArrayList<Double>(), refDirection = new ArrayList<Double>();
			location.add(20.6);
			location.add(30.9);
			location.add(22.1);
			axis.add(1.0);
			axis.add(0.0);
			axis.add(-1.0);
			refDirection.add(-1.0);
			refDirection.add(0.0);
			refDirection.add(1.0);

			EAxis2_placement_3d eAxis2_placement_3d = project
					.createAxis2Placement3D("", location, axis, refDirection);
			Assert.assertEquals("#1=AXIS2_PLACEMENT_3D('',#2,#3,#4);",
					eAxis2_placement_3d.toString());

			project.closeSession();
		} catch (SdaiException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void createAxis2Placement3DTest1() {
		STEPWorkingStepsProject project;
		try {
			project = ProjectManager.getProject();
			ArrayList<Double> location = new ArrayList<Double>(), axis = new ArrayList<Double>(), refDirection = new ArrayList<Double>();
			location.add(20.6);
			location.add(30.9);
			location.add(22.1);
			axis.add(1.0);
			axis.add(0.0);
			axis.add(-1.0);
			refDirection.add(-1.0);
			refDirection.add(0.0);
			refDirection.add(1.0);

			ECartesian_point loc = project.createCartesianPoint(
					"cartesian point", location);
			EDirection ax = project.createDirection("axis", axis);
			EDirection ref = project.createDirection("ref Direction",
					refDirection);

			EAxis2_placement_3d eAxis2_placement_3d = project
					.createAxis2Placement3D("axis2Placement3D name", loc, ax,
							ref);
			Assert.assertEquals(
					"#4=AXIS2_PLACEMENT_3D('axis2Placement3D name',#1,#2,#3);",
					eAxis2_placement_3d.toString());

			try {
				project.closeSession();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (SdaiException e) {
			e.printStackTrace();
		}
	}
	
	
	@After
	public void destroyObjects(){
		try {
			ProjectManager.clearProject();
		} catch (SdaiException e) {
			e.printStackTrace();
		} 
	}
}
