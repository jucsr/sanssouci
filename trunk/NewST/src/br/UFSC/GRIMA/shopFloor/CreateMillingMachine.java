package br.UFSC.GRIMA.shopFloor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.bancoDeDados.AcessaBD;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.entidades.machiningResources.CuttingToolHandlingDevice;
import br.UFSC.GRIMA.entidades.machiningResources.MachineTool;
import br.UFSC.GRIMA.entidades.machiningResources.MillingMachine;
import br.UFSC.GRIMA.entidades.machiningResources.Spindle;
import br.UFSC.GRIMA.entidades.machiningResources.ToolMagazine;
import br.UFSC.GRIMA.entidades.machiningResources.WorkpieceHandlingDevice;
import br.UFSC.GRIMA.shopFloor.visual.CreateMillingMachineFrame;

public class CreateMillingMachine extends CreateMillingMachineFrame implements ActionListener,ChangeListener
{
	AcessaBD aBD = new AcessaBD("150.162.105.1", "webTools", "webcad", "julio123");
	public JanelaShopFloor janelaShopFloor;
	private ShopFloor shopFloor;
	private Ferramenta itsCurrentTool;
	private MillingMachine millingMachine= new MillingMachine();
	private ArrayList<MachineTool> machineTools;
	public ArrayList<Ferramenta> toolList = new ArrayList<Ferramenta>();
	private ArrayList<Spindle> arraySpindle = new ArrayList<Spindle>();
	private ArrayList<WorkpieceHandlingDevice> arrayWorkpieceHandlingDevices =  new ArrayList<WorkpieceHandlingDevice>();
	private ArrayList<CuttingToolHandlingDevice> listaDeMagazines = new ArrayList<CuttingToolHandlingDevice>();
	private int userID;

	public CreateMillingMachine(JanelaShopFloor janelaShopFloor, ShopFloor shopFloor) 
	{
		super(janelaShopFloor);
		this.userID = shopFloor.getUserID();
		this.machineTools = shopFloor.getMachines();
		millingMachine.setWorkpieceHandlingDevice(arrayWorkpieceHandlingDevices);
		millingMachine.setToolHandlingDevice(listaDeMagazines);
		this.shopFloor = shopFloor;
		this.janelaShopFloor = janelaShopFloor;
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		this.button1.addActionListener(this);
		this.button1.setEnabled(false);
		this.button2.addActionListener(this);
		this.button3.addActionListener(this);
		this.button4.addActionListener(this);
		this.button5.addActionListener(this);
		this.button6.addActionListener(this);
		this.button6.setEnabled(false);
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
		this.button17.addActionListener(this);
		this.menuItem1.addActionListener(this);
		this.spinner14.addChangeListener(this);
		this.spinner13.addChangeListener(this);
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		Object o = e.getSource();
		if(o.equals(okButton))
		{
			this.ok();
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
				
				if((Boolean) this.table1.getValueAt(i, 0))
				{
					modelo.removeRow(i);	
					toolList.remove(i);
//					millingMachine.setItsSpindle(arraySpindle);
					i = 0;
					i--;
				}
			}
		}
		else if(o.equals(button4))//SELECIONA TODOS OS TOOLHANDLINGDEVICES
		{
			for(int i = 0; i < this.table1.getRowCount(); i++)
			{
				this.table1.setValueAt(true, i, 0);
			}
		}
		else if(o.equals(button5))//DESELECIONA TODOS OS TOOLHANDLINGDEVICES
		{
			for(int i = 0; i < this.table1.getRowCount(); i++)
			{
				this.table1.setValueAt(false, i, 0);
			}
		}
		else if(o.equals(button6))
		{
			
		}
		else if(o.equals(button7))//ADD WORKPIECEDEVICES
		{
			CreateWorkpieceHandlingDevices workpieceHandling = new CreateWorkpieceHandlingDevices(this, millingMachine);
			workpieceHandling.setVisible(true);
		}
		else if(o.equals(button8))//REMOVE WORKPIECEDEVICES
		{
			DefaultTableModel modelo;
			
			for(int i = 0; i < this.table2.getRowCount(); i++)
			{
				modelo = (DefaultTableModel)this.table2.getModel();
				arrayWorkpieceHandlingDevices = millingMachine.getWorkpieceHandlingDevice();
				
				if((Boolean) this.table2.getValueAt(i, 0))
				{
					modelo.removeRow(i);	
					arrayWorkpieceHandlingDevices.remove(i);
					millingMachine.setWorkpieceHandlingDevice(arrayWorkpieceHandlingDevices);
					i = 0;
					i--;
				}
			}
		}
		else if(o.equals(button9))//SELECIONA TODOS OS WORKPIECESHANDLINGDEVICES
		{
			for(int i = 0; i < this.table2.getRowCount(); i++)
			{
				this.table2.setValueAt(true, i, 0);
			}
		}
		else if(o.equals(button10))//DESELECIONA TODOS OS WORKPIECESHANDLINGDEVICES
		{
			for(int i = 0; i < this.table2.getRowCount(); i++)
			{
				this.table2.setValueAt(false, i, 0);
			}
		}
		else if(o.equals(button11))//JA DA SET NOS AXIS
		{
			CreateRotaryAxisShopFloor rotary = new CreateRotaryAxisShopFloor(this, millingMachine);
			rotary.setVisible(true);
		}
		else if(o.equals(button12))//JA DA SET NOS AXIS
		{
			CreateTravelingAxisShopFloor traveling = new CreateTravelingAxisShopFloor(this, millingMachine);
			traveling.setVisible(true);
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
		else if(o.equals(button17))//SETAR FERRAMENTA ATUAL
		{
			int rows = 0; 
			int ferramenta = 0;
			
			for(int i = 0; i < this.table1.getRowCount(); i++)
			{
				if((Boolean) this.table1.getValueAt(i, 0))
				{
					rows++;
					ferramenta = i;
				}
			}
			if(rows == 1)
			{
				itsCurrentTool = toolList.get(ferramenta);
				System.out.println(itsCurrentTool);
//				JComponent comp = (JComponent) this.table1.getCellRenderer(0, 2);  
//				comp.setForeground(new Color(0,0,255));
			}
			else
			{
				System.out.println("Erro");
				//Janela de erro avisando que mais de uma ferramenta est� selecionada.
			}
		}
		else if(o.equals(menuItem1))
		{
			setMagazine();
			machineTools = shopFloor.getMachines();
			
			shopFloor.setMachines(machineTools);
			try {
				saveMachine();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private void ok() 
	{
		
//		machineTool.setItsCapability(itsCapability);
//		machineTool.setItsId(itsId);
//		machineTool.setItsLocation();
		millingMachine.setItsOrigin(new Point3d(((Double)spinner13.getValue()).doubleValue(),((Double)spinner14.getValue()).doubleValue(),0));
		if(janelaShopFloor.validateMachine(shopFloor, millingMachine))
		{
			millingMachine.setAccuracy(((Double)spinner4.getValue()).doubleValue());
			millingMachine.setItsId(textField1.getText());
//			machineTool.setItsSpindle(itsSpindle);
//			machineTool.setToolHandlingDevice(toolHandlingDevice);
//			machineTool.setWorkpieceHandlingDevice(workpieceHandlingDevice);
			//mandar o machineTool para o array de machines do shopFloor
			
			//SETAR AS FERRAMENTAS ANTES!
//			CuttingToolHandlingDevice cuttingTool = new CuttingToolHandlingDevice(this);
			//... (sets)
			setMagazine();
			machineTools = shopFloor.getMachines();
			machineTools.add(millingMachine);
			shopFloor.setMachines(machineTools);
			janelaShopFloor.shopPanel.repaint();
			janelaShopFloor.atualizarArvoreMaquinas();
			this.dispose();		
		} else
		{
			JOptionPane.showMessageDialog(null, "The machine was not created");
		}
		
	}

	private void setMagazine()
	{
		ToolMagazine toolMagazine = new ToolMagazine();
		listaDeMagazines.add(toolMagazine);
		listaDeMagazines.get(0).setAllowedToolDiameter(((Double) this.spinner10.getValue()).doubleValue());
		listaDeMagazines.get(0).setItsCurrentTool(itsCurrentTool);
		listaDeMagazines.get(0).setItsToolCapacity(((Integer) this.spinner8.getValue()).intValue());
		listaDeMagazines.get(0).setMaxAllowedToolLength(((Double) this.spinner11.getValue()).doubleValue());
		listaDeMagazines.get(0).setMaxAllowedToolWeight(((Double) this.spinner9.getValue()).doubleValue());
		listaDeMagazines.get(0).setToolList(toolList);
		((ToolMagazine) listaDeMagazines.get(0)).setToolChangeTime(((Double) this.spinner12.getValue()).doubleValue());
		millingMachine.setToolHandlingDevice(listaDeMagazines);
	}
	
	private void saveMachine() throws IOException
	{		
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(bout);
		String machineName;
		
		millingMachine.setItsId(this.textField1.getText());

		// pega o nome do projeto que o usuario digitou
		machineName = this.textField1.getText();

		if(aBD.existeMaquina(this.userID, machineName)){

			int i = JOptionPane
					.showConfirmDialog(
							null,
							"Já existe um projeto com esse nome! \n"
									+ "Deseja substituí-lo ?",
									"Projeto Duplicado",
									JOptionPane.OK_CANCEL_OPTION);
		}
	
		out.writeObject(millingMachine);
		out.flush();
		out.close();
		
		byte[] b = bout.toByteArray();
		String s = write(b);
				
		if (aBD.salvoNoBDMachines(millingMachine, userID)) {
			aBD.updateMaquina(millingMachine, s, userID);
		} else
			aBD.insertMaquina(millingMachine, s, userID);
				
	}

	public static String write(byte[] barney) throws IOException 
	{
		String lyly = "";
		String sinal = "";
		
		for (int i = 0; i < barney.length; i++)
		{
			if (barney[i] < 0)
			{
				sinal = "-";
				if (barney[i] == (byte) -128) {
					sinal = "";
				}
				barney[i] = (byte) (barney[i] * (-1));
			}else
				sinal = "+";
			
			if (barney[i] < 10 && barney[i] > -10) 
			{
				lyly = lyly + sinal + "00" + barney[i];
			} else if (barney[i] < 100 && barney[i] > -100) 
			{
				lyly = lyly + sinal + "0" + barney[i];
			} else
				lyly = lyly + sinal + barney[i];
		}
		return lyly;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		
		}
	}


