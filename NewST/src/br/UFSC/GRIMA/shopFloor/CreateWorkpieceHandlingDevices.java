package br.UFSC.GRIMA.shopFloor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.entidades.machiningResources.BarFeeder;
import br.UFSC.GRIMA.entidades.machiningResources.Chuck;
import br.UFSC.GRIMA.entidades.machiningResources.Collet;
import br.UFSC.GRIMA.entidades.machiningResources.MachineTool;
import br.UFSC.GRIMA.entidades.machiningResources.MillingMachine;
import br.UFSC.GRIMA.entidades.machiningResources.Pallet;
import br.UFSC.GRIMA.entidades.machiningResources.Table;
import br.UFSC.GRIMA.entidades.machiningResources.WorkpieceHandlingDevice;
import br.UFSC.GRIMA.shopFloor.visual.CreateWorkpieceHandlingDeviceFrame;

public class CreateWorkpieceHandlingDevices extends CreateWorkpieceHandlingDeviceFrame implements ActionListener, ItemListener{
	
	private WorkpieceHandlingDevice workpieceHandlingDevice;
	private ArrayList<WorkpieceHandlingDevice> arrayWorkpiece = new ArrayList<WorkpieceHandlingDevice>();
	private MachineTool millingMachine;
	private String name;
	private String type;
	private Point3d origin;
	private double originX;
	private double originY;
	private double originZ;
	private int maxLoadCapacity;
	private int jaws;
	private double minDiameter;
	private double maxDiameter;
	private double maxAllowedLength;
	private CreateMillingMachine janelaMillingMachine;
	private CreateDrillingMachine janelaDrillingMachine;
	private String itsId;
	
	public CreateWorkpieceHandlingDevices(CreateDrillingMachine janelaDrillingMachine, MachineTool millingMachine) {
		super(janelaDrillingMachine);
		this.janelaDrillingMachine = janelaDrillingMachine;
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		this.comboBox1.addItemListener(this);
		this.comboBox1.setSelectedIndex(4);
		this.millingMachine = millingMachine;
		type = "Table";
	}
	
	public CreateWorkpieceHandlingDevices(CreateMillingMachine janelaMillingMachine, MachineTool millingMachine) {
		super(janelaMillingMachine);
		this.janelaMillingMachine = janelaMillingMachine;
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		this.comboBox1.addItemListener(this);
		this.comboBox1.setSelectedIndex(4);
		this.millingMachine = millingMachine;
		type = "Table";
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
		if(o.equals(okButton))
		{
			this.ok();
			this.dispose();
		}
		else if(o.equals(cancelButton))
		{
			this.dispose();
		}
	}
	
	private void ok()
	{
		arrayWorkpiece = millingMachine.getWorkpieceHandlingDevice();
		name = this.textField1.getText();
		maxLoadCapacity = ((Integer) this.spinner1.getValue()).intValue();
		originX = ((Double) this.spinner2.getValue()).doubleValue();
		originY = ((Double) this.spinner3.getValue()).doubleValue();
		originZ = ((Double) this.spinner4.getValue()).doubleValue();
		origin = new Point3d(originX, originY, originZ);
		
		
		Object[] linha = {false, name, maxLoadCapacity, type};
		if(janelaMillingMachine != (null)){
		DefaultTableModel modelo = (DefaultTableModel)this.janelaMillingMachine.table2.getModel();
		this.janelaMillingMachine.table2.setModel(modelo);
		modelo.addRow(linha);
		}else if(janelaDrillingMachine!=(null)){
			DefaultTableModel modelo = (DefaultTableModel)this.janelaDrillingMachine.table2.getModel();
			this.janelaDrillingMachine.table2.setModel(modelo);
			modelo.addRow(linha);
			}
		if(type == "Chuck")
		{
			workpieceHandlingDevice = new Chuck(name);
			jaws = ((Integer) this.spinner5.getValue()).intValue();
			minDiameter = ((Double) this.spinner6.getValue()).doubleValue();
			maxDiameter = ((Double) this.spinner7.getValue()).doubleValue();
			maxAllowedLength = ((Double) this.spinner8.getValue()).doubleValue();
			
			workpieceHandlingDevice.setType(type);
			workpieceHandlingDevice.setItsOrigin(origin);
			workpieceHandlingDevice.setMaxLoadCapacity(maxLoadCapacity);
			((Chuck) workpieceHandlingDevice).setMaxAllowedLength(maxAllowedLength);
			((Chuck) workpieceHandlingDevice).setMaxDiameter(maxDiameter);
			((Chuck) workpieceHandlingDevice).setMinDiameter(minDiameter);
			((Chuck) workpieceHandlingDevice).setNumberOfJaws(jaws);
			
		}
		else if(type == "Bar feeder"){
			workpieceHandlingDevice = new BarFeeder(name);
			workpieceHandlingDevice.setType(type);
			workpieceHandlingDevice.setItsOrigin(origin);
			workpieceHandlingDevice.setMaxLoadCapacity(maxLoadCapacity);
		}
		else if(type == "Collet"){
			workpieceHandlingDevice = new Collet(name);
			workpieceHandlingDevice.setType(type);
			workpieceHandlingDevice.setItsOrigin(origin);
			workpieceHandlingDevice.setMaxLoadCapacity(maxLoadCapacity);
		}
		else if(type == "Pallet"){
			workpieceHandlingDevice = new Pallet(name);
			workpieceHandlingDevice.setType(type);
			workpieceHandlingDevice.setItsOrigin(origin);
			workpieceHandlingDevice.setMaxLoadCapacity(maxLoadCapacity);
		}
		else if(type == "Table"){
			workpieceHandlingDevice = new Table(name);
			workpieceHandlingDevice.setType(type);
			workpieceHandlingDevice.setItsOrigin(origin);
			workpieceHandlingDevice.setMaxLoadCapacity(maxLoadCapacity);
		}
		
		arrayWorkpiece.add(workpieceHandlingDevice);
	
		millingMachine.setWorkpieceHandlingDevice(arrayWorkpiece);
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		
		int index = this.comboBox1.getSelectedIndex();
		
		switch (index){
		
		case 0:
			this.spinner5.setEnabled(true);
			this.spinner6.setEnabled(true);
			this.spinner7.setEnabled(true);
			this.spinner8.setEnabled(true);
			type = "Chuck";
			break;
		case 1:
			this.spinner5.setEnabled(false);
			this.spinner6.setEnabled(false);
			this.spinner7.setEnabled(false);
			this.spinner8.setEnabled(false);
			type = "Bar feeder";
			break;
		case 2:
			this.spinner5.setEnabled(false);
			this.spinner6.setEnabled(false);
			this.spinner7.setEnabled(false);
			this.spinner8.setEnabled(false);
			type = "Collet";
			break;
		case 3:
			this.spinner5.setEnabled(false);
			this.spinner6.setEnabled(false);
			this.spinner7.setEnabled(false);
			this.spinner8.setEnabled(false);
			type = "Pallet";
			break;
		case 4:
			this.spinner5.setEnabled(false);
			this.spinner6.setEnabled(false);
			this.spinner7.setEnabled(false);
			this.spinner8.setEnabled(false);
			type = "Table";
			break;
			
		}
		
		
		
		
		
		
		
	}

}
