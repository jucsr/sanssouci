package br.UFSC.GRIMA.shopFloor;

import java.awt.Frame;
import java.awt.Graphics2D;
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
import br.UFSC.GRIMA.cad.JanelaPrincipal;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.entidades.machiningResources.CuttingToolHandlingDevice;
import br.UFSC.GRIMA.entidades.machiningResources.MachineTool;
import br.UFSC.GRIMA.entidades.machiningResources.DrillingMachine;
import br.UFSC.GRIMA.entidades.machiningResources.Spindle;
import br.UFSC.GRIMA.entidades.machiningResources.ToolMagazine;
import br.UFSC.GRIMA.entidades.machiningResources.WorkpieceHandlingDevice;
import br.UFSC.GRIMA.shopFloor.JanelaShopFloor;
import br.UFSC.GRIMA.shopFloor.visual.CreateDrillingMachineFrame;
/**
 * 
 * @author jc
 *
 */
public class CreateDrillingMachine extends CreateDrillingMachineFrame implements ActionListener, ChangeListener
{	
	AcessaBD aBD = new AcessaBD("150.162.105.1", "webTools", "webcad", "julio123");
	public JanelaShopFloor janelaShopFloor;
	private ShopFloor shopFloor;
	private Ferramenta itsCurrentTool;
	private DrillingMachine drillingMachine= new DrillingMachine();
	private ArrayList<MachineTool> machineTools;
	public ArrayList<Ferramenta> toolList = new ArrayList<Ferramenta>();
	private ArrayList<Spindle> arraySpindle = new ArrayList<Spindle>();
	private ArrayList<WorkpieceHandlingDevice> arrayWorkpieceHandlingDevices =  new ArrayList<WorkpieceHandlingDevice>();
	private ArrayList<CuttingToolHandlingDevice> listaDeMagazines = new ArrayList<CuttingToolHandlingDevice>();
	private int userID;
	
	public CreateDrillingMachine(JanelaShopFloor janelaShopFloor, ShopFloor shopFloor) 
	{
		super(janelaShopFloor);
		
		this.userID = shopFloor.getUserID();
		this.machineTools = shopFloor.getMachines();
		drillingMachine.setWorkpieceHandlingDevice(arrayWorkpieceHandlingDevices);
		drillingMachine.setToolHandlingDevice(listaDeMagazines);
		this.shopFloor = shopFloor;
		this.janelaShopFloor = janelaShopFloor;
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		this.menuItem1.addActionListener(this);
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
		this.button13.addActionListener(this);
		this.button14.addActionListener(this);
		this.button15.addActionListener(this);
		this.button16.addActionListener(this);
	
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object o = e.getSource();
		if(o.equals(okButton))
		{
			this.ok();
		} else if(o.equals(cancelButton))
		{
			this.dispose();
			
		} else if(o.equals(button1))// VIEW TOOL
		{
			
		} else if(o.equals(button2))// ADD TOOL
		{
			CreateTool ct = new CreateTool(this);
			ct.setVisible(true);
		} else if(o.equals(button3)) // REMOVE TOOL
		{
			DefaultTableModel modelo;
			
			for(int i = 0; i < this.table1.getRowCount(); i++)
			{
				modelo = (DefaultTableModel)this.table1.getModel();
				
				if((Boolean) this.table1.getValueAt(i, 0))
				{
					modelo.removeRow(i);	
					toolList.remove(i);
					i = 0;
					i--;
				}
			}
		}  else if(o.equals(button4))// SELECT ALL
		{
			for(int i = 0; i < this.table1.getRowCount(); i++)
			{
				this.table1.setValueAt(true, i, 0);
			}
		} else if(o.equals(button5))// REMOVE ALL
		{
			for(int i = 0; i < this.table1.getRowCount(); i++)
			{
				this.table1.setValueAt(false, i, 0);
			}
		} else if(o.equals(button6))// VIEW DEVICE
		{
			
		} else if(o.equals(button7))// ADD DEVICE
		{
			CreateWorkpieceHandlingDevices workpieceHandling = new CreateWorkpieceHandlingDevices(this, drillingMachine);
			workpieceHandling.setVisible(true);
		} else if(o.equals(button8))// REMOVE DEVICE
		{
				DefaultTableModel modelo;
			
			for(int i = 0; i < this.table2.getRowCount(); i++)
			{
				modelo = (DefaultTableModel)this.table2.getModel();
				arrayWorkpieceHandlingDevices = drillingMachine.getWorkpieceHandlingDevice();
				
				if((Boolean) this.table2.getValueAt(i, 0))
				{
					modelo.removeRow(i);	
					arrayWorkpieceHandlingDevices.remove(i);
					drillingMachine.setWorkpieceHandlingDevice(arrayWorkpieceHandlingDevices);
					i = 0;
					i--;
				}
			}
		} else if(o.equals(button9))// SELECT ALL(DEVICE)
		{
			for(int i = 0; i < this.table2.getRowCount(); i++)
			{
				this.table2.setValueAt(true, i, 0);
			}
		} else if(o.equals(button10))// REMOVE ALL(DEVICE)
		{
			for(int i = 0; i < this.table2.getRowCount(); i++)
			{
				this.table2.setValueAt(false, i, 0);
			}
		}else if(o.equals(button13))//ADD SPINDLES
		{
			CreateSpindle spindle = new CreateSpindle(this, drillingMachine);
			spindle.setVisible(true);
		}
		else if(o.equals(button14))//REMOVE SPINDLES
		{
			DefaultTableModel modelo;
			
			for(int i = 0; i < this.table4.getRowCount(); i++)
			{
				modelo = (DefaultTableModel)this.table4.getModel();
				arraySpindle = drillingMachine.getItsSpindle();
				
				if((Boolean) this.table4.getValueAt(i, 0))
				{
					modelo.removeRow(i);	
					arraySpindle.remove(i);
					drillingMachine.setItsSpindle(arraySpindle);
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
		}else if(o.equals(menuItem1))
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
	
	private void saveMachine() throws IOException
	{		
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(bout);
		String machineName;
		
		drillingMachine.setItsId(this.textField1.getText());

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
	
		out.writeObject(drillingMachine);
		out.flush();
		out.close();
		
		byte[] b = bout.toByteArray();
		String s = write(b);
				
		if (aBD.salvoNoBDMachines(drillingMachine, userID)) {
			aBD.updateMaquina(drillingMachine, s, userID);
		} else
			aBD.insertMaquina(drillingMachine, s, userID);
				
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
	
	public void ok(){
//		machineTool.setItsCapability(itsCapability);
//		machineTool.setItsId(itsId);
//		machineTool.setItsLocation();
		drillingMachine.setItsOrigin(new Point3d(((Double)spinner8.getValue()).doubleValue(),((Double)spinner9.getValue()).doubleValue(),0));
		if(janelaShopFloor.validateMachine(shopFloor, drillingMachine))
		{
			drillingMachine.setAccuracy(((Double)spinner4.getValue()).doubleValue());
			drillingMachine.setItsId(textField1.getText());
//			machineTool.setItsSpindle(itsSpindle);
//			machineTool.setToolHandlingDevice(toolHandlingDevice);
//			machineTool.setWorkpieceHandlingDevice(workpieceHandlingDevice);
			//mandar o machineTool para o array de machines do shopFloor
			
			//SETAR AS FERRAMENTAS ANTES!
//			CuttingToolHandlingDevice cuttingTool = new CuttingToolHandlingDevice(this);
			//... (sets)
			setMagazine();
			machineTools = shopFloor.getMachines();
			machineTools.add(drillingMachine);
			shopFloor.setMachines(machineTools);
			janelaShopFloor.shopPanel.repaint();
//			janelaShopFloor.atualizarArvoreMaquinas();
			janelaShopFloor.atualizarArvoreMaquinas1();
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
		listaDeMagazines.get(0).setAllowedToolDiameter(((Double) this.spinner12.getValue()).doubleValue());
		listaDeMagazines.get(0).setItsCurrentTool(itsCurrentTool);
		listaDeMagazines.get(0).setItsToolCapacity(((Integer) this.spinner10.getValue()).intValue());
		listaDeMagazines.get(0).setMaxAllowedToolLength(((Double) this.spinner13.getValue()).doubleValue());
		listaDeMagazines.get(0).setMaxAllowedToolWeight(((Double) this.spinner11.getValue()).doubleValue());
		listaDeMagazines.get(0).setToolList(toolList);
		((ToolMagazine) listaDeMagazines.get(0)).setToolChangeTime(((Double) this.spinner14.getValue()).doubleValue());
		drillingMachine.setToolHandlingDevice(listaDeMagazines);
	}

	@Override
	public void stateChanged(ChangeEvent c) {
		// TODO Auto-generated method stub
		Object o = c.getSource();
		
		if(o.equals(spinner1)){
			
		}else if(o.equals(spinner2)){
			
		}else if(o.equals(spinner3)){
			
		}else if(o.equals(spinner4)){
			
		}else if(o.equals(spinner5)){
			
		}else if(o.equals(spinner6)){
			
		}else if(o.equals(spinner7)){
			
		}
	}
}
