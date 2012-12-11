package br.UFSC.GRIMA.shopFloor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.entidades.machiningResources.MachineTool;
import br.UFSC.GRIMA.entidades.machiningResources.MillingMachine;
import br.UFSC.GRIMA.entidades.machiningResources.Spindle;
import br.UFSC.GRIMA.entidades.machiningResources.WorkpieceHandlingDevice;
import br.UFSC.GRIMA.shopFloor.visual.CreateMillingMachineFrame;
import br.UFSC.GRIMA.util.projeto.Axis;

public class CreateMillingMachine extends CreateMillingMachineFrame implements ActionListener
{
	public JanelaShopFloor janelaShopFloor;
	private ShopFloor shopFloor;
	private MillingMachine millingMachine= new MillingMachine();
	private ArrayList<MachineTool> machineTool = new ArrayList<MachineTool>();
	public ArrayList<Ferramenta> tools = new ArrayList<Ferramenta>();
	private ArrayList<Spindle> arraySpindle;
	private ArrayList<WorkpieceHandlingDevice> workpieceHandlingDevices;
	private double lengthMax;
	private double widthMax;
	private double highMax;
	private String name;
	private double accuracy;
	private String control;
	private double nMax;
	private double powerMax;
	private int axis;
	private Axis xAxisRotation;
	

	public CreateMillingMachine(JanelaShopFloor janelaShopFloor, ShopFloor shopFloor) 
	{
		super(janelaShopFloor);
		this.shopFloor = shopFloor;
		this.janelaShopFloor = janelaShopFloor;
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		this.button1.addActionListener(this);
		this.button2.addActionListener(this);
		this.button3.addActionListener(this);
		this.button4.addActionListener(this);
		this.button5.addActionListener(this);
		this.button6.addActionListener(this);
		this.button7.addActionListener(this);
		this.button8.addActionListener(this);
		this.button9.addActionListener(this);
		this.button10.addActionListener(this);
		this.button11.addActionListener(this);
		this.button12.addActionListener(this);
		this.button13.addActionListener(this);
		this.button14.addActionListener(this);
		this.button15.addActionListener(this);
		this.button16.addActionListener(this);
		this.menuItem1.addActionListener(this);
		
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		Object o = e.getSource();
		if(o.equals(okButton))
		{
//			machineTool.setItsCapability(itsCapability);
//			machineTool.setItsId(itsId);
//			machineTool.setItsLocation();
//			machineTool.setItsOrigin(itsOrigin);
//			machineTool.setItsSpindle(itsSpindle);
//			machineTool.setToolHandlingDevice(toolHandlingDevice);
//			machineTool.setWorkpieceHandlingDevice(workpieceHandlingDevice);
			//mandar o machineTool para o array de machines do shopFloor
			machineTool = shopFloor.getMachines();
			machineTool.add(millingMachine);
			shopFloor.setMachines(machineTool);
			this.dispose();
		} 
		else if(o.equals(cancelButton))
		{
			this.dispose();
		}
		else if(o.equals(button1))
		{
			
		}
		else if(o.equals(button2))//ADD TOOLS
		{
			CreateTool ct = new CreateTool(this);
			ct.setVisible(true);
		}
		else if(o.equals(button3))//REMOVE TOOLS
		{
			DefaultTableModel modelo;
			
			for(int i = 0; i < this.table1.getRowCount(); i++)
			{
				modelo = (DefaultTableModel)this.table1.getModel();
				arraySpindle = millingMachine.getItsSpindle();
				
				if((Boolean) this.table1.getValueAt(i, 0))
				{
					modelo.removeRow(i);	
					arraySpindle.remove(i);
					millingMachine.setItsSpindle(arraySpindle);
					i = 0;
					i--;
				}
			}
		}
		else if(o.equals(button4))
		{
			
		}
		else if(o.equals(button5))
		{
			
		}
		else if(o.equals(button6))
		{
			
		}
		else if(o.equals(button7))
		{
			
		}
		else if(o.equals(button8))
		{
			
		}
		else if(o.equals(button9))
		{
			
		}
		else if(o.equals(button10))
		{
			
		}
		else if(o.equals(button11))//JA DA SET NOS AXIS
		{
			CreateRotaryAxisShopFloor x = new CreateRotaryAxisShopFloor(this, millingMachine);
			x.setVisible(true);
		}
		else if(o.equals(button12))//JA DA SET NOS AXIS
		{
			CreateTravelingAxisShopFloor y = new CreateTravelingAxisShopFloor(this, millingMachine);
			y.setVisible(true);
		}
		else if(o.equals(button13))//ADD SPINDLES
		{
			CreateSpindle spindle = new CreateSpindle(this, millingMachine);
			spindle.setVisible(true);
		}
		else if(o.equals(button14))//REMOVE SPINDLES
		{
			DefaultTableModel modelo;
			
			for(int i = 0; i < this.table4.getRowCount(); i++)
			{
				modelo = (DefaultTableModel)this.table4.getModel();
				arraySpindle = millingMachine.getItsSpindle();
				
				if((Boolean) this.table4.getValueAt(i, 0))
				{
					modelo.removeRow(i);	
					arraySpindle.remove(i);
					millingMachine.setItsSpindle(arraySpindle);
					i = 0;
					i--;
				}
			}
		}
		else if(o.equals(button15))//DESSELECIONA TODOS OS SPINDLES
		{
			for(int i = 0; i < this.table4.getRowCount(); i++)
			{
				this.table4.setValueAt(false, i, 0);
			}
		}
		else if(o.equals(button16))//SELECIONA TODOS OS SPINDLES
		{
			for(int i = 0; i < this.table4.getRowCount(); i++)
			{
				this.table4.setValueAt(true, i, 0);
			}
		}
		else if(o.equals(menuItem1))
		{
			saveMachine();
			saveToolMagazine();
			saveWorkpieceHandlingDevices();
		}
	}
	
	private void saveMachine()
	{
		
	}
	
	private void saveToolMagazine()
	{
		
	}
	
	private void saveWorkpieceHandlingDevices()
	{
		
	}
}
