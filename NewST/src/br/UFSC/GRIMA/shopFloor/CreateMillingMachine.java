package br.UFSC.GRIMA.shopFloor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.entidades.machiningResources.MachineTool;
import br.UFSC.GRIMA.entidades.machiningResources.WorkpieceHandlingDevice;
import br.UFSC.GRIMA.shopFloor.visual.CreateMillingMachineFrame;
import br.UFSC.GRIMA.util.projeto.Axis;

public class CreateMillingMachine extends CreateMillingMachineFrame implements ActionListener
{
	public JanelaShopFloor shopFloor;
	private MachineTool machineTool = new MachineTool();
	public ArrayList<Ferramenta> tools = new ArrayList<Ferramenta>();
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
	

	public CreateMillingMachine(JanelaShopFloor shopFloor) 
	{
		super(shopFloor);
		this.shopFloor = shopFloor;
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
		this.menuItem1.addActionListener(this);
		
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		Object o = e.getSource();
		if(o.equals(okButton))
		{
			
//			machineTool.setAxis();
//			machineTool.setItsCapability(itsCapability);
//			machineTool.setItsId(itsId);
//			machineTool.setItsLocation();
//			machineTool.setItsOrigin(itsOrigin);
//			machineTool.setItsSpindle(itsSpindle);
//			machineTool.setToolHandlingDevice(toolHandlingDevice);
//			machineTool.setWorkpieceHandlingDevice(workpieceHandlingDevice);
			//mandar o machineToll para o array de machines do shopFloor
			this.dispose();
		} 
		else if(o.equals(cancelButton))
		{
			this.dispose();
		}
		else if(o.equals(button1))
		{
			
		}
		else if(o.equals(button2))
		{
			CreateTool ct = new CreateTool(this);
			ct.setVisible(true);
		}
		else if(o.equals(button3))
		{
			
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
		else if(o.equals(button11))
		{
			
		}
		else if(o.equals(button12))
		{
			
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
