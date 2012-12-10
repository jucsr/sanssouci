package br.UFSC.GRIMA.shopFloor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.entidades.machiningResources.MillingMachine;
import br.UFSC.GRIMA.shopFloor.visual.RotaryAxisFrame;
import br.UFSC.GRIMA.util.projeto.Axis;
import br.UFSC.GRIMA.util.projeto.RotaryAxis;

public class CreateRotaryAxisShopFloor extends RotaryAxisFrame implements ActionListener, ItemListener{

	private CreateMillingMachine janelaMillingMachine;
	private double maxRot;
	private RotaryAxis rotaryAxis = new RotaryAxis();
	private MillingMachine millingMachine;
	private String name;
	private double originX;
	private double originY;
	private double originZ;
	private Point3d origin;
	
	public CreateRotaryAxisShopFloor(CreateMillingMachine janelaMillingMachine, MillingMachine millingMachine) 
	{
		super(janelaMillingMachine);
		this.janelaMillingMachine = janelaMillingMachine;
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		this.millingMachine = millingMachine;
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
		name = this.textField1.getText();
		rotaryAxis.setName(name);
		originX = ((Double) this.spinner2.getValue()).doubleValue();
		originY = ((Double) this.spinner3.getValue()).doubleValue();
		originZ = ((Double) this.spinner4.getValue()).doubleValue();
		origin = new Point3d(originX , originY, originZ);
		rotaryAxis.setOrigin(origin);
		maxRot = ((Double) this.spinner1.getValue()).doubleValue();
		rotaryAxis.setMaxRotSpeed(maxRot);
		ArrayList<Axis> axis = millingMachine.getAxis();
		int size = axis.size();
		if(size!=0)
		{
			for(int i = 0; i < size; i++)
			{
				if(origin.equals(axis.get(i).getOrigin()) && axis.get(i).getClass().equals(RotaryAxis.class))
				{
					break;
				}
				else if(size-1 == i)
				{
					int id = this.janelaMillingMachine.table3.getRowCount() + 1;
					String originS = originX+" ,"+originY+" ,"+originZ;
					Object[] linha = {false, id, name, originS};
					DefaultTableModel modelo = (DefaultTableModel)this.janelaMillingMachine.table3.getModel();
					this.janelaMillingMachine.table3.setModel(modelo);
					modelo.addRow(linha);	
					axis.add(rotaryAxis);
				}
			}
		}
		else
		{
			int id = this.janelaMillingMachine.table3.getRowCount() + 1;
			String originS = originX+" ,"+originY+" ,"+originZ;
			Object[] linha = {false, id, name, originS};
			DefaultTableModel modelo = (DefaultTableModel)this.janelaMillingMachine.table3.getModel();
			this.janelaMillingMachine.table3.setModel(modelo);
			modelo.addRow(linha);
			axis.add(rotaryAxis);
		}
		millingMachine.setAxis(axis);

			
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		
		int index;

		if(e.equals(comboBox1)){
			
			index = this.comboBox1.getSelectedIndex();
			
			switch (index) {
			case 0:		
				rotaryAxis.setRotaryDirection(RotaryAxis.X_DIRECTION);
				break;
			case 1:
				rotaryAxis.setRotaryDirection(RotaryAxis.Y_DIRECTION);			
				break;
			}
		}
		else if(e.equals(comboBox2)){

			index = this.comboBox2.getSelectedIndex();

			switch (index) {
			case 0:
				rotaryAxis.setRotaryType(RotaryAxis.CCW_TYPE);		
				break;
			case 1:
				rotaryAxis.setRotaryType(RotaryAxis.CW_TYPE);	
				break;
			case 2:
				rotaryAxis.setRotaryType(RotaryAxis.CW_AND_CCW_TYPE);			
				break;
			}
		}
	}

}
