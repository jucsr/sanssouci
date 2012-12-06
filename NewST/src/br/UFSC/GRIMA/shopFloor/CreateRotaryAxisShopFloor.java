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
		rotaryAxis.setOrigin(new Point3d(originX , originY, originZ));
		maxRot = ((Double) this.spinner1.getValue()).doubleValue();
		rotaryAxis.setMaxRotSpeed(maxRot);
		ArrayList<Axis> axis = millingMachine.getAxis();
		for(int i = 0; i < axis.size(); i++)
		{
			if(rotaryAxis.getOrigin() == axis.get(i).getOrigin())
			{
				if(axis.get(i).equals(RotaryAxis.class))
				{
					break;
				}
			}
			if(i == axis.size()-1)
			{
				axis.add(rotaryAxis);
			}
		}
		millingMachine.setAxis(axis);

		int id = this.janelaMillingMachine.table3.getRowCount() + 1;
		String origin = originX+" ,"+originY+" ,"+originZ;
		Object[] linha = {false, id, name, origin};
		DefaultTableModel modelo = (DefaultTableModel)this.janelaMillingMachine.table3.getModel();
		this.janelaMillingMachine.table3.setModel(modelo);
		modelo.addRow(linha);		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		
		int index = this.comboBox1.getSelectedIndex();
		
		switch (index) {
		case 0:		
			rotaryAxis.setRotaryDirection(RotaryAxis.X_DIRECTION);
			break;
		case 1:
			rotaryAxis.setRotaryDirection(RotaryAxis.Y_DIRECTION);			
			break;
		}
		
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
