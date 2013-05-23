package br.UFSC.GRIMA.cad;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.vecmath.Point3d;

import jsdai.lang.SdaiException;
import br.UFSC.GRIMA.acceptance.STEP_NCReader;
import br.UFSC.GRIMA.cad.visual.JanelaCodigoSTEP_view;
import br.UFSC.GRIMA.entidades.Rectangle3D;
import br.UFSC.GRIMA.entidades.StepNcProject;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.simulator.ProjetoDeSimulacao;
import br.UFSC.GRIMA.simulator.SimulationPanel;
import br.UFSC.GRIMA.util.GCodeGenerator;
import br.UFSC.GRIMA.util.HeaderUtil;
import br.UFSC.GRIMA.util.VValidator;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class JanelaCodigoStep extends JanelaCodigoSTEP_view implements
		ActionListener {

	private StepNcProject stepNcProject;
	private Rectangle3D block;
	private ArrayList<Feature> featuresList;
	private Vector workingsteps;
	private STEP_NCReader reader;
	private Projeto projeto;

	public JanelaCodigoStep(StepNcProject stepNcProject) {

		this.stepNcProject = stepNcProject;

		this.menuItem2.addActionListener(this);
		this.menuItem3.addActionListener(this);
		this.menuItem4.addActionListener(this);
		this.menuItem5.addActionListener(this);
		this.menuItem6.addActionListener(this);
		this.menuItem7.addActionListener(this);
		this.menuItem8.addActionListener(this);
		this.menuItem9.addActionListener(this);
		
		lerProjeto();
		
	}

	public void editTextArea(String code) {
		this.textArea1.setText(code);
	}

	public void lerProjeto() {

		reader = new STEP_NCReader(StepNcProject.getAModel());
		
		
		
		try {
			this.workingsteps = reader.getAllWorkingSteps();
			
			this.projeto = reader.getProjeto();
			this.editTextArea(HeaderUtil.changeHeader(stepNcProject.createSTEP21File(), "GRIMA"));
		
//			stepNcProject.closeAndDeleteSession();

		} catch (SdaiException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
		}

	}

	public void simular() {

		// Calcula pontos de apoio

		// Utilizaremos por default:
		// RaioApoio = 2;
		// DivVertical,divBase,divHorizontal = 1.1314;

		double raioApoio = 2;
		

		VValidator validator = new VValidator(block, featuresList, raioApoio);

		// ArrayList<Point3d> list = validator.getSupportPoints();
		// ProjetoDeSimulacao projetoDeSimulacao = new
		// ProjetoDeSimulacao(list,block,featuresList);
		// ProjetoDeSimulacao projetoDeSimulacao = new
		// ProjetoDeSimulacao(block,featuresList);

		ArrayList<ArrayList<Point3d>> apoiosPeca;
		ArrayList<Point3d> apoiosFaceInferior = new ArrayList<Point3d>();
		
		//Setei os apoios manualmente, o validador estava dando erro
		apoiosFaceInferior.add(new Point3d(0,0,0));
		apoiosFaceInferior.add(new Point3d(100,0,0));
		apoiosFaceInferior.add(new Point3d(50,100,0));

//		try {
//
//			apoiosPeca = validator.getPoints(3, 0, 0);
//			apoiosFaceInferior = apoiosPeca.get(0);
//
//		} catch (ProjetoInvalidoException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		
		
		// SIMULAÇÃO
		JFrame win;

		System.out.println(projeto.getDadosDeProjeto());
		
		Bloco bloco = this.projeto.getBloco();
		
		ProjetoDeSimulacao projetoDeSimulacao;


		projetoDeSimulacao = new ProjetoDeSimulacao(bloco, apoiosFaceInferior, this.workingsteps);
		win = new SimulationPanel(projetoDeSimulacao);
		win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		win.pack();
		win.setVisible(true);



	}

	private void gerarCodigoG() throws SdaiException {
		
		
		
//		this.workingsteps = this.projeto.getWorkingsteps();
		
		GCodeGenerator gCodeGenerator = new GCodeGenerator(this.workingsteps,this.projeto);
		
//		GCodeGenerator gCodeGenerator = new GCodeGenerator(workingsteps, reader.getProject());
		
		String gCode = gCodeGenerator.GenerateGCodeString();
		JanelaCodigoG jcg = new JanelaCodigoG();
		jcg.textPane1.setText(gCode);
		jcg.setVisible(true);
		
//		System.out.println(gCode);
		
	}
	public void keyTyped(KeyEvent e)
	{
		
	}
	public void actionPerformed(ActionEvent event) {
		
		Object origem = event.getSource();

		if (origem == menuItem4) {

//			lerProjeto();
			
			simular();

		} else if (origem == menuItem5) {

//			lerProjeto();
			
			try {
				gerarCodigoG();
			} catch (SdaiException e) {
				e.printStackTrace();
			}

		} else if (origem == this.menuItem2)
		{
			this.salvar();
		} else if (origem == this.menuItem3)
		{
			this.dispose();
		} else if (origem == this.menuItem6)
		{
			try {
				this.stepNcProject.exportXML();
			} catch (SdaiException e) 
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		} else if(origem == this.menuItem7)
		{
			this.selectAll();
		} else if(origem == this.menuItem8)
		{
			this.copy();
		} else if(origem == this.menuItem9)
		{
			this.find();
		}
	}
	private void find() 
	{
		JanelaFind jf = new JanelaFind(this, this.textArea1);
		jf.comboBox2.setEnabled(false);
		jf.label2.setEnabled(false);
		jf.setVisible(true);
	}
	private void copy() 
	{
		this.textArea1.copy();
	}
	private void selectAll()
	{
		this.textArea1.selectAll();
	}
	private void salvar() 
	{
		JFileChooser fc = new JFileChooser();
	
		this.projeto.setCappDone(true);
		this.projeto.setToolsDone(true);
		
		if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
			return;

		File arquivo = fc.getSelectedFile();
		if (arquivo == null)
			return;

		FileWriter writer = null;
		try {
			writer = new FileWriter(arquivo);
			writer.write(this.textArea1.getText());
		} catch (IOException ex) {
			// Possiveis erros aqui
			ex.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException x) {
					//
					x.printStackTrace();
				}
			}
		}
	}
}
