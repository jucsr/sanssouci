package br.UFSC.GRIMA.shopFloor;

import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import br.UFSC.GRIMA.bancoDeDados.AcessaBD;
import br.UFSC.GRIMA.cad.JanelaPrincipal;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.entidades.machiningResources.CuttingToolHandlingDevice;
import br.UFSC.GRIMA.entidades.machiningResources.MachineTool;
import br.UFSC.GRIMA.entidades.machiningResources.DrillingMachine;
import br.UFSC.GRIMA.entidades.machiningResources.Spindle;
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
		}
		
		
		
	}
	public void ok(){
		
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
