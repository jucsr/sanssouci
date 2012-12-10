package br.UFSC.GRIMA.shopFloor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.entidades.machiningResources.MillingMachine;
import br.UFSC.GRIMA.shopFloor.visual.CreateTravelingAxisFrame;
import br.UFSC.GRIMA.util.projeto.Axis;
import br.UFSC.GRIMA.util.projeto.RotaryAxis;
import br.UFSC.GRIMA.util.projeto.TravelingAxis;

public class CreateTravelingAxisShopFloor extends CreateTravelingAxisFrame implements ActionListener, ItemListener{


	private CreateMillingMachine janelaMillingMachine;
	private TravelingAxis travelingAxis = new TravelingAxis();
	private MillingMachine millingMachine;
	private String name;
	private double originX;
	private double originY;
	private double originZ;
	private Point3d origin;
	private double itsFeedRateRange;
	private double itsTravelingRange;
	private double rapidMovementSpeed;
	
	public CreateTravelingAxisShopFloor(CreateMillingMachine janelaMillingMachine, MillingMachine millingMachine)
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
		travelingAxis.setName(name);
		
		itsFeedRateRange = ((Double) this.spinner3.getValue()).doubleValue();
		travelingAxis.setItsFeedRateRange(itsFeedRateRange);
		
		itsTravelingRange = ((Double) this.spinner1.getValue()).doubleValue();
		travelingAxis.setItsTravelingRange(itsTravelingRange);
		
		originX = ((Double) this.spinner4.getValue()).doubleValue();
		originY = ((Double) this.spinner5.getValue()).doubleValue();
		originZ = ((Double) this.spinner6.getValue()).doubleValue();
		origin = new Point3d(originX, originY, originZ);
		travelingAxis.setOrigin(origin);
		
		rapidMovementSpeed = ((Double) this.spinner2.getValue()).doubleValue();
		travelingAxis.setRapidMovementSpeed(rapidMovementSpeed);
		
		ArrayList<Axis> axis = millingMachine.getAxis();
		int size = axis.size();
		if(size!=0)
		{
			for(int i = 0; i < size; i++)
			{
				if(origin.equals(axis.get(i).getOrigin()) && axis.get(i).getClass().equals(TravelingAxis.class))
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
					axis.add(travelingAxis);
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
			axis.add(travelingAxis);
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
				travelingAxis.setTravelingDirection(TravelingAxis.X);
				break;
			case 1:
				travelingAxis.setTravelingDirection(TravelingAxis.Y);			
				break;
			case 2:
				travelingAxis.setTravelingDirection(TravelingAxis.Z);
			}
		}
	}

}
