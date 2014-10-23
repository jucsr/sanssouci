package br.UFSC.GRIMA.entidades;

import java.awt.FileDialog;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.JFrame;

import jsdai.SCombined_schema.AExecutable;
import jsdai.SCombined_schema.AProperty_parameter;
import jsdai.SCombined_schema.AWorkpiece;
import jsdai.SCombined_schema.AWorkpiece_setup;
import jsdai.SCombined_schema.EAxis2_placement_3d;
import jsdai.SCombined_schema.EBlock;
import jsdai.SCombined_schema.ECartesian_point;
import jsdai.SCombined_schema.EDirection;
import jsdai.SCombined_schema.EMachining_workingstep;
import jsdai.SCombined_schema.EMaterial;
import jsdai.SCombined_schema.EPlane;
import jsdai.SCombined_schema.EProject;
import jsdai.SCombined_schema.EProperty_parameter;
import jsdai.SCombined_schema.ESetup;
import jsdai.SCombined_schema.EWorkpiece;
import jsdai.SCombined_schema.EWorkpiece_setup;
import jsdai.SCombined_schema.EWorkplan;
import jsdai.SCombined_schema.SCombined_schema;
import jsdai.lang.ASdaiModel;
import jsdai.lang.A_double;
import jsdai.lang.SdaiException;
import jsdai.lang.SdaiModel;
import jsdai.lang.SdaiRepository;
import jsdai.lang.SdaiSession;
import jsdai.lang.SdaiTransaction;
import jsdai.xml.LateBindingReader;
import br.UFSC.GRIMA.util.Util;

public class STEPProject {

	protected EProject eProject;
	protected EWorkplan workplan;
	protected EWorkpiece workpiece;
	protected EMachining_workingstep machining_workingstep;
	protected ESetup setup;
	protected EMaterial material;
	protected AWorkpiece_setup workpiece_setup_a;
	public SdaiSession session;
	public SdaiRepository repository;
	protected EWorkpiece_setup workpiece_setup;
	protected EProperty_parameter property_parameter;
	protected AExecutable machining;
	protected String path;
	protected int stepIndex = 0;
	protected Material materialModel;
	protected SdaiModel model;
	protected static SdaiModel eModel;
	protected EBlock block;
	private A_double p4;
	private A_double p3;
	private String pathRepositories = "";
	
	public STEPProject() throws SdaiException 
	{
		if (SdaiSession.getSession() != null)
			SdaiSession.getSession().closeSession();

		materialModel = new Material();
		path = "";
		Properties properties = new Properties();
		String osName = System.getProperty("os.name");
		osName = osName.substring(0, 7);
		System.out.println("subString = " + osName);
		if(osName.substring(0, 7).equals("Windows"))
		{
			System.out.println("OSNAME ======" + osName);
			pathRepositories = "C:\\repositories.tmp\\";
			properties.setProperty("repositories", pathRepositories);
		} else if(osName.equals("Linux") && osName.equals("Solaris"))
		{
			pathRepositories = "/tmp/repositories.tmp/";
			properties.setProperty("repositories", pathRepositories);
		} else if(osName == "Mac");
		{
//			Eu nao sei
		}
		{
			System.out.println("sistema operacional desconhecido");
		}
		SdaiSession.setSessionProperties(properties);
		session = SdaiSession.openSession();
		session.startTransactionReadWriteAccess();
		repository = session.createRepository("", null);
		repository.openRepository();
		model = repository.createSdaiModel("STEP_NC", SCombined_schema.class);
		model.startReadWriteAccess();
		eModel = model;
	}
	
	public static ASdaiModel getAModel() {
		ASdaiModel model = new ASdaiModel();
		try {
			model.addByIndex(1, eModel);
		} catch (SdaiException e) {
			e.printStackTrace();
		}
		return model;
	}
	
	public ASdaiModel getModel(){
		ASdaiModel aModel = new ASdaiModel();
		try {
			aModel.addByIndex(1, this.model);
		} catch (SdaiException e) {
			e.printStackTrace();
		}
		
		return aModel;
	}
	public void setAModel(SdaiModel model) {
		eModel = model;
	}

	public Material getMaterialModel() {
		return materialModel;
	}

	public void setMaterialModel(Material materialModel) {
		this.materialModel = materialModel;
	}

	public void setProject(String ProjectId) throws SdaiException {
		eProject = (EProject) model.createEntityInstance(EProject.class);
		eProject.setIts_id(null, ProjectId);
		setWorkplan("workplan");
		setSetup("setup");
	}

	public void setWorkplan(String WorkplanId) throws SdaiException {
		workplan = (EWorkplan) model.createEntityInstance(EWorkplan.class);
		workplan.setIts_id(null, WorkplanId);
		machining = workplan.createIts_elements(null);
	}

	public void relationateVars() throws SdaiException {
		workpiece.setIts_material(null, material);

		workplan.setIts_setup(null, setup);

		eProject.setMain_workplan(null, workplan);

		AWorkpiece work = eProject.createIts_workpieces(null);
		work.addUnordered(workpiece, null);

		AProperty_parameter property = material.createMaterial_property(null);
		property.addUnordered(property_parameter, null);

	}

	protected void addWorkingStep(EMachining_workingstep machining_workingstep)
			throws SdaiException {

		// machining.addByIndex(stepIndex++,machining_workingstep, null);
		machining.addUnordered(machining_workingstep, null);

	}

	// protected void removeWorkingStep(int index) throws SdaiException {
	// SdaiIterator sdaiIterator = machining.createIterator();
	// machining.attachIterator(sdaiIterator);
	// removeThisIndex(sdaiIterator, index);
	// }
	//
	// private void removeThisIndex(SdaiIterator sdaiIterator, int index)
	// throws SdaiException {
	// int i = 0;
	// System.out.println("AAA");
	// while (sdaiIterator.next()) {
	// System.out.println("A");
	// if (i++ == index) {
	// System.out.println("B");
	// sdaiIterator.remove();
	// }
	// }
	//
	// }

	public void setWorkPiece(String WorkPieceID, double x, double y, double z)
			throws SdaiException {

		workpiece = (EWorkpiece) model.createEntityInstance(EWorkpiece.class);
		workpiece.setIts_id(null, WorkPieceID);

		// Considerando que a peça deve ser um bloco
		block = (EBlock) model.createEntityInstance(EBlock.class);
		block.setName(null, "Block");

		EAxis2_placement_3d axis2_placement_3d = (EAxis2_placement_3d) model
				.createEntityInstance(EAxis2_placement_3d.class);
		axis2_placement_3d.setName(null, "");

		ECartesian_point cartesian_point = (ECartesian_point) model
				.createEntityInstance(ECartesian_point.class);
		cartesian_point.setName(null, "");
		A_double p = cartesian_point.createCoordinates(null);
		p.addByIndex(1, 0.0);
		p.addByIndex(2, 0.0);
		p.addByIndex(3, 0.0);

		EDirection direction = (EDirection) model
				.createEntityInstance(EDirection.class);
		direction.setName(null, "");
		A_double d = direction.createDirection_ratios(null);
		d.addByIndex(1, 0.0);
		d.addByIndex(2, 0.0);
		d.addByIndex(3, 1.0);

		EDirection direction1 = (EDirection) model
				.createEntityInstance(EDirection.class);
		direction1.setName(null, "");
		A_double d1 = direction1.createDirection_ratios(null);
		d1.addByIndex(1, 1.0);
		d1.addByIndex(2, 0.0);
		d1.addByIndex(3, 0.0);

		axis2_placement_3d.setLocation(null, cartesian_point);
		axis2_placement_3d.setAxis(null, direction);
		axis2_placement_3d.setRef_direction(null, direction1);

		block.setPosition(null, axis2_placement_3d);
		block.setX(null, x);
		block.setY(null, y);
		block.setZ(null, z);

		workpiece.setGlobal_tolerance(null, 0.01);
		workpiece.setIts_bounding_geometry(null, block);
		setWorkpieceSetup("");
		createSetupPoints();
	}

	public EMachining_workingstep createWorkingStep(String WorkingStepId)
			throws SdaiException {
		machining_workingstep = (EMachining_workingstep) model
				.createEntityInstance(EMachining_workingstep.class);
		machining_workingstep.setIts_id(null, WorkingStepId);
		return machining_workingstep;

	}

	public void createSetupPoints() throws SdaiException {
		p4.addByIndex(1, block.getX(null) / 2);
		p4.addByIndex(2, 0.0);
		p4.addByIndex(3, block.getZ(null) / 2);

		p3.addByIndex(1, 0.0);
		p3.addByIndex(2, block.getY(null) / 2);
		p3.addByIndex(3, block.getZ(null) / 2);
	}

	public void setSetup(String SetupId) throws SdaiException {

		setup = (ESetup) model.createEntityInstance(ESetup.class);
		setup.setIts_id(null, SetupId);

		ECartesian_point cartesian_point4 = (ECartesian_point) model
				.createEntityInstance(ECartesian_point.class);
		cartesian_point4.setName(null, "");
		p4 = cartesian_point4.createCoordinates(null);

		ECartesian_point cartesian_point3 = (ECartesian_point) model
				.createEntityInstance(ECartesian_point.class);
		cartesian_point3.setName(null, "");
		p3 = cartesian_point3.createCoordinates(null);

		EDirection direction4 = (EDirection) model
				.createEntityInstance(EDirection.class);
		direction4.setName(null, "");
		A_double d4 = direction4.createDirection_ratios(null);
		d4.addByIndex(1, 0.0);
		d4.addByIndex(2, 0.0);
		d4.addByIndex(3, 1.0);

		EDirection direction5 = (EDirection) model
				.createEntityInstance(EDirection.class);
		direction5.setName(null, "");
		A_double d5 = direction5.createDirection_ratios(null);
		d5.addByIndex(1, 1.0);
		d5.addByIndex(2, 0.0);
		d5.addByIndex(3, 0.0);

		EAxis2_placement_3d axis2_placement_3d4 = (EAxis2_placement_3d) model
				.createEntityInstance(EAxis2_placement_3d.class);
		axis2_placement_3d4.setName(null, "");
		axis2_placement_3d4.setLocation(null, cartesian_point4);

		EAxis2_placement_3d axis2_placement_3d3 = (EAxis2_placement_3d) model
				.createEntityInstance(EAxis2_placement_3d.class);
		axis2_placement_3d3.setLocation(null, cartesian_point3);
		axis2_placement_3d3.setAxis(null, direction4);
		axis2_placement_3d3.setRef_direction(null, direction5);
		axis2_placement_3d3.setName(null, "");

		EPlane plane1 = (EPlane) model.createEntityInstance(EPlane.class);
		plane1.setPosition(null, axis2_placement_3d4);
		plane1.setName(null, "");

		setup.setIts_origin(null, axis2_placement_3d3);
		setup.setIts_secplane(null, plane1);

	}

	public void setMaterial(String Material_identifier,
			String Standard_identifier, String property_parameterName)
			throws SdaiException {
		material = (EMaterial) model.createEntityInstance(EMaterial.class);
		material.setMaterial_identifier(null, Material_identifier);
		material.setStandard_identifier(null, Standard_identifier);

		property_parameter = (EProperty_parameter) model
				.createEntityInstance(EProperty_parameter.class);
		property_parameter.setParameter_name(null, property_parameterName);
	}

	public String createSTEP21File() throws SdaiException {
		repository.exportClearTextEncoding(pathRepositories + eProject.getIts_id(eProject));

		File file = new File(pathRepositories + eProject.getIts_id(eProject));
		System.out.println("====== DONE ======");
		return Util.getContents(file);
	}

	public String createSTEP21FileHere() throws SdaiException {
		repository.exportClearTextEncoding("test/res/"
				+ eProject.getIts_id(eProject));

		File file = new File("test/res/" + eProject.getIts_id(eProject));
		System.out.println("====== DONE ======");
		return Util.getContents(file);
	}

	public String createSTEP21FileWithoutHeader() throws SdaiException {
		repository.exportClearTextEncoding(path + eProject.getIts_id(eProject));

		File file = new File(path + eProject.getIts_id(eProject));
		System.out.println("====== DONE ======");

		return Util.getContents(file);
	}

	public String createSTEP21File(String path) throws SdaiException {
		repository.exportClearTextEncoding(path + eProject.getIts_id(eProject));
		File file = new File(path + eProject.getIts_id(eProject));
		System.out.println("====== DONE ======");
		return path + eProject.getIts_id(eProject);
	}

	public void closeAndDeleteSession() throws SdaiException, InterruptedException {
	SdaiTransaction sdaiTransaction = session.getActiveTransaction();
//	sdaiTransaction.commit();
//	sdaiTransaction.abort();
	
	session.getActiveTransaction().endTransactionAccessCommit();
	repository.closeRepository();
	repository.deleteRepository();
//	session.getSystemRepository().closeRepository();
//	session.getSystemRepository().deleteRepository();
	session.closeSession();
}
	
	public void closeSession() throws SdaiException, InterruptedException {
		SdaiTransaction sdaiTransaction = session.getActiveTransaction();
		sdaiTransaction.commit();
		repository.closeRepository();
		session.closeSession();
	}

	public void setWorkpieceSetup(String axisName) throws SdaiException {
		EDirection direction6 = (EDirection) model
				.createEntityInstance(EDirection.class);
		direction6.setName(null, "");

		EDirection direction7 = (EDirection) model
				.createEntityInstance(EDirection.class);
		direction7.setName(null, "");

		ECartesian_point cartesian_point5 = (ECartesian_point) model
				.createEntityInstance(ECartesian_point.class);
		cartesian_point5.setName(null, "");

		A_double d6 = direction6.createDirection_ratios(null);
		d6.addByIndex(1, 0.0);
		d6.addByIndex(2, 0.0);
		d6.addByIndex(3, 1.0);

		A_double d7 = direction7.createDirection_ratios(null);
		d7.addByIndex(1, 1.0);
		d7.addByIndex(2, 0.0);
		d7.addByIndex(3, 0.0);

		A_double p5 = cartesian_point5.createCoordinates(null);
		p5.addByIndex(1, block.getX(null));
		p5.addByIndex(2, block.getY(null) / 2);
		p5.addByIndex(3, block.getZ(null) / 2);

		EAxis2_placement_3d axis2_placement_3d5 = (EAxis2_placement_3d) model
				.createEntityInstance(EAxis2_placement_3d.class);
		axis2_placement_3d5.setName(null, axisName);
		axis2_placement_3d5.setLocation(null, cartesian_point5);
		axis2_placement_3d5.setAxis(null, direction6);
		axis2_placement_3d5.setRef_direction(null, direction7);

		workpiece_setup = (EWorkpiece_setup) model
				.createEntityInstance(EWorkpiece_setup.class);
		workpiece_setup.setIts_workpiece(null, workpiece);
		workpiece_setup.setIts_origin(null, axis2_placement_3d5);

		workpiece_setup_a = setup.createIts_workpiece_setup(null);
		workpiece_setup_a.addByIndex(1, workpiece_setup);

	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void exportXML() throws SdaiException, IOException {
		FileDialog fd = new FileDialog(new JFrame(), "Salvar", FileDialog.SAVE);
		fd.setFile(this.eProject.getIts_id(null));
		fd.setVisible(true);
		String dir = fd.getDirectory();
		String file = fd.getFile();
		String filePath = dir + file + ".xml";
		LateBindingReader reader = new LateBindingReader();
//		reader.setFeature("machining_schema", true);
//		reader.setFeature("make-schema-populations", true);
		FileOutputStream fileStream = new FileOutputStream(filePath, false);
		OutputStream toStream = new BufferedOutputStream(fileStream, 4096);
		try {
			repository.exportXml(toStream, reader);
		} finally {
			toStream.close();
		}
	}

	
	public SdaiSession getSession() {
		return session;
	}
}
