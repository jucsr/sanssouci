package br.UFSC.GRIMA.shopFloor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.table.DefaultTableModel;

import br.UFSC.GRIMA.cad.visual.AddNewBallEndMillFrame;
import br.UFSC.GRIMA.entidades.ferramentas.BallEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.entidades.machiningResources.CuttingToolHandlingDevice;

public class AddNewBallEndMill extends AddNewBallEndMillFrame implements ActionListener, ItemListener
{
	private String materialClass = "P";
	private int handOfCut = 1;
	private CreateMillingMachine janelaMillingMachine;
	private CreateDrillingMachine janelaDrillingMachine;
	//private CreateTurningMachine janelaTurningMachine;
	public AddNewBallEndMill(CreateMillingMachine owner) 
	{
		super(owner);
		this.janelaMillingMachine = (CreateMillingMachine) owner;
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		this.comboBox1.addItemListener(this);
		this.comboBox3.addItemListener(this);
	}
	
	public AddNewBallEndMill(CreateDrillingMachine owner) 
	{
		super(owner);
		this.janelaDrillingMachine = owner;
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		this.comboBox1.addItemListener(this);
		this.comboBox3.addItemListener(this);
	}
	
	/*public AddNewBallEndMill(CreateTurningMachine owner) 
	{
		super(owner);
		this.janelaTurningMachine = owner;
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		this.comboBox1.addItemListener(this);
		this.comboBox3.addItemListener(this);
	}
	  */
	
	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		Object o = e.getItem();
		if(o == this.comboBox1.getItemAt(0))
		{
			this.materialClass = "P";
		} else if (o == this.comboBox1.getItemAt(1))
		{
			this.materialClass = "M";
		} else if (o == this.comboBox1.getItemAt(2))
		{
			this.materialClass = "S";
		} else if (o == this.comboBox1.getItemAt(3))
		{
			this.materialClass = "K";
		} else if (o == this.comboBox1.getItemAt(4))
		{
			this.materialClass = "H";
		} else if (o == this.comboBox1.getItemAt(5))
		{
			this.materialClass = "N";
		} else if (o == this.comboBox3.getItemAt(0))
		{
			this.handOfCut = Ferramenta.RIGHT_HAND_OF_CUT;
		}  else if (o == this.comboBox3.getItemAt(1))
		{
			this.handOfCut = Ferramenta.LEFT_HAND_OF_CUT;
		}  else if (o == this.comboBox3.getItemAt(2))
		{
			this.handOfCut = Ferramenta.NEUTRAL_HAND_OF_CUT;
		} 
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object object = e.getSource();
		if(object == okButton)
		{
			this.ok();
		} else if (object == cancelButton)
		{
			this.dispose();
		}
	}
	private void ok()
	{
		
		String nome = this.textField1.getText();
		double diametro = (Double)this.spinner4.getValue();
		double cuttingEdge = (Double)this.spinner2.getValue();
		double profundidade = (Double)this.spinner3.getValue();
		double offSetLength = (Double)this.spinner1.getValue();
		double dm = (Double)this.spinner5.getValue();
		double edgeRadius = diametro / 2;
		double edgeCenterVertical = diametro / 2;
		String material = "Carbide";
		double rugosidade = 0;
		double tolerancia = 0;
		String hand = "";
		if (handOfCut == Ferramenta.RIGHT_HAND_OF_CUT)
			hand = "Right";
		else if(this.handOfCut == Ferramenta.LEFT_HAND_OF_CUT)
			hand = "Left";
		else if(this.handOfCut == Ferramenta.NEUTRAL_HAND_OF_CUT)
			hand = "Neutral";
				
		BallEndMill bem = new BallEndMill(nome, material, diametro, edgeRadius, edgeCenterVertical, cuttingEdge, profundidade, offSetLength, dm, rugosidade, tolerancia, handOfCut);
		
		if(!janelaMillingMachine.equals(null)){
			int id = this.janelaMillingMachine.table1.getRowCount() + 1;
			janelaMillingMachine.toolList.add(bem);
			Object[] linha = {false, id, nome, diametro, "Ball End Mill"};
			DefaultTableModel modelo = (DefaultTableModel)this.janelaMillingMachine.table1.getModel();
			this.janelaMillingMachine.table1.setModel(modelo);
			modelo.addRow(linha);
		}else if(!janelaDrillingMachine.equals(null)){
			int id = this.janelaDrillingMachine.table1.getRowCount() + 1;
			janelaDrillingMachine.toolList.add(bem);
			Object[] linha = {false, id, nome, diametro, "Ball End Mill"};
			DefaultTableModel modelo = (DefaultTableModel)this.janelaDrillingMachine.table1.getModel();
			this.janelaDrillingMachine.table1.setModel(modelo);
			modelo.addRow(linha);
		}/*else if(!janelaTurningMachine.equals(null)){
			int id = this.janelaTurningMachine.table1.getRowCount() + 1;
			janelaTurningMachine.toolList.add(bem);
			Object[] linha = {false, id, nome, diametro, "Ball End Mill"};
			DefaultTableModel modelo = (DefaultTableModel)this.janelaTurningMachine.table1.getModel();
			this.janelaTurningMachine.table1.setModel(modelo);
			modelo.addRow(linha);
		}
		*/
		this.dispose();
	}
}
