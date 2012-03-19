package br.UFSC.GRIMA.STEPmanager;

import jsdai.SCombined_schema.AAxis2_placement_3d;
import jsdai.SCombined_schema.ACartesian_point;
import jsdai.SCombined_schema.AProject;
import jsdai.SCombined_schema.AProperty_parameter;
import jsdai.SCombined_schema.AWorkpiece;
import jsdai.SCombined_schema.CDescriptive_parameter;
import jsdai.SCombined_schema.CNumeric_parameter;
import jsdai.SCombined_schema.EAxis2_placement_3d;
import jsdai.SCombined_schema.EBlock;
import jsdai.SCombined_schema.ECartesian_point;
import jsdai.SCombined_schema.EDescriptive_parameter;
import jsdai.SCombined_schema.EMaterial;
import jsdai.SCombined_schema.ENumeric_parameter;
import jsdai.SCombined_schema.EProject;
import jsdai.SCombined_schema.EProperty_parameter;
import jsdai.SCombined_schema.EWorkpiece;
import jsdai.lang.ASdaiModel;
import jsdai.lang.SdaiException;
import jsdai.lang.SdaiIterator;
import jsdai.lang.SdaiModel;
import jsdai.lang.SdaiRepository;
import jsdai.lang.SdaiSession;
import jsdai.lang.SdaiTransaction;

//import jsdai.*;

public class LerArquivoStep {
	static SdaiRepository repository;
	static SdaiTransaction transaction;
	static SdaiSession session;
	static SdaiModel model;

	public static void main(String[] args) throws SdaiException {
		try {
			java.util.Properties prop = new java.util.Properties();
			prop.setProperty("repositories", "Repositories.tmp");
			SdaiSession.setSessionProperties(prop);
			session = SdaiSession.openSession();

			transaction = session.startTransactionReadWriteAccess();
			repository = session
					.importClearTextEncoding(
							"ImportRepository",
							"C:\\Users\\Admin\\Eclipse\\StepNcMachining\\STEP-NC File.p21",
							null);
			System.out.println("flag");
			if (!repository.isActive()) {
				repository.openRepository();
			}
			ASdaiModel models = repository.getModels();
			SdaiIterator modelIterator = models.createIterator();

			while (modelIterator.next()) {

				model = models.getCurrentMember(modelIterator);
				if (model.getMode() == SdaiModel.NO_ACCESS) {
					model.startReadOnlyAccess();
				}
				System.out.println("Model found: " + model.getName() + "");
				AProject projects = (AProject) model
						.getInstances(EProject.class);

				SdaiIterator projectIterator = projects.createIterator();
				while (projectIterator.next()) {
					EProject project = projects
							.getCurrentMember(projectIterator);
					System.out.println("Project instance No.: "
							+ project.getPersistentLabel());
					System.out
							.println("Project ID: " + project.getIts_id(null));

					if (project.testIts_owner(null)) {
						System.out.println("Project Owner:"
								+ project.getIts_owner(null));
					}
					if (project.testIts_release(null)) {
						System.out.println("Project Release:"
								+ project.getIts_release(null));
					}
					if (project.testIts_status(null)) {
						System.out.println("Project Status:"
								+ project.getIts_status(null));
					}
					AWorkpiece workpieces = project.getIts_workpieces(null);

					SdaiIterator workpieceIterator = workpieces
							.createIterator();
					while (workpieceIterator.next()) {
						EWorkpiece workpiece = workpieces
								.getCurrentMember(workpieceIterator);
						System.out.println("WorkPiece Id:"
								+ workpiece.getIts_id(null));
						if (workpiece.testIts_material(null)) {
							EMaterial material = workpiece
									.getIts_material(null);
							System.out.println("Its standard_identifier:"
									+ material.getStandard_identifier(null));
							System.out.println("Its material_identifier:"
									+ material.getMaterial_identifier(null));
							AProperty_parameter property_parameters = material
									.getMaterial_property(null);

							SdaiIterator property_parameterIterator = property_parameters
									.createIterator();
							while (property_parameterIterator.next()) {
								EProperty_parameter property_parameter = property_parameters
										.getCurrentMember(property_parameterIterator);
								if (property_parameter
										.isKindOf(CDescriptive_parameter.class)) {
									EDescriptive_parameter descritive_parameter = (EDescriptive_parameter) property_parameter;
									System.out
											.println("Descriptive String:"
													+ descritive_parameter
															.getDescriptive_string(null));
								}
								if (property_parameter
										.isKindOf(CNumeric_parameter.class)) {
									ENumeric_parameter numeric_parameter = (ENumeric_parameter) property_parameter;
									System.out
											.println("Parameter Value:"
													+ numeric_parameter
															.getIts_parameter_value(null));
									System.out
											.println("Parameter Unit:"
													+ numeric_parameter
															.getIts_parameter_unit(null));
								}
							}
						}

						if (workpiece.testGlobal_tolerance(null)) {
							System.out.println("Global Tolerance"
									+ workpiece.getGlobal_tolerance(null));
						}
						if (workpiece.testIts_rawpiece(null)) {
							System.out.println("Rawpiece:"
									+ workpiece.getIts_rawpiece(null));
						}
						if (workpiece.testIts_geometry(null)) {
							System.out.println("Geometry:"
									+ workpiece.getIts_geometry(null));
						}
						if (workpiece.testIts_bounding_geometry(null)) {
							// System.out.println("AAA"+workpiece
							// .getIts_bounding_geometry(null).getClass());
							System.out.println("Bounidng_geometry:"
									+ workpiece.getIts_bounding_geometry(null));
							EBlock block = (EBlock) workpiece
									.getIts_bounding_geometry(null);
							System.out.println("X:" + block.getX(null));
							System.out.println("Y:" + block.getY(null));
							System.out.println("Z:" + block.getZ(null));
							AAxis2_placement_3d axis2_placement_3ds = (AAxis2_placement_3d) block
									.getPosition(null);
							SdaiIterator axis2_placement_3dIterator = axis2_placement_3ds
									.createIterator();
							while (axis2_placement_3dIterator.next()) {
								EAxis2_placement_3d axis2_placement_3d = axis2_placement_3ds
										.getCurrentMember(axis2_placement_3dIterator);

							}
						}

						ACartesian_point cartesianPoints = workpiece
								.getClamping_positions(null);

						SdaiIterator cartesianPointsIterator = cartesianPoints
								.createIterator();
						while (cartesianPointsIterator.next()) {
							ECartesian_point cartesian_Point = cartesianPoints
									.getCurrentMember(cartesianPointsIterator);
						}

					}

					if (model.getMode() == SdaiModel.READ_ONLY) {
						model.endReadOnlyAccess();
					} else if (model.getMode() == SdaiModel.READ_WRITE) {
						model.endReadWriteAccess();
					}
				}

			}
		} catch (Exception e) {
		}

		finally {
			repository.closeRepository();
			repository.deleteRepository();
			transaction.endTransactionAccessCommit();
			session.closeSession();
			System.out.println();
			System.out.println("finished");
		}

	}
}