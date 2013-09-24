package br.UFSC.GRIMA.shopFloor;

import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideFinishMilling;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.capp.machiningOperations.FreeformOperation;
import br.UFSC.GRIMA.capp.machiningOperations.MachiningOperation;
import br.UFSC.GRIMA.capp.machiningOperations.PlaneFinishMilling;
import br.UFSC.GRIMA.capp.machiningOperations.PlaneRoughMilling;
import br.UFSC.GRIMA.entidades.ferramentas.BallEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.BullnoseEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.CenterDrill;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.entidades.ferramentas.TwistDrill;
import br.UFSC.GRIMA.entidades.machiningResources.CuttingToolHandlingDevice;
import br.UFSC.GRIMA.entidades.machiningResources.MachineTool;
import br.UFSC.GRIMA.entidades.machiningResources.Spindle;
import br.UFSC.GRIMA.entidades.machiningResources.WorkpieceHandlingDevice;

public class ArvoreMaquinas  
{
	private ProjetoSF projetoSF;
	private ArrayList<MachineTool> machines;
	
	public ArvoreMaquinas(ProjetoSF projetoSF)
	{
		this.projetoSF = projetoSF;
		this.machines = this.projetoSF.getShopFloor().getMachines();
	}
	public JTree getArvoreMaquinas()
	{
		JTree arvore = new JTree(this.criarRaizEFolhas());
		return arvore;
	}
	private DefaultMutableTreeNode criarRaizEFolhas()
	{
		DefaultMutableTreeNode root = null;
		for(int i = 0; i < this.machines.size(); i++)
		{
			MachineTool machineTmp = this.machines.get(i);
			root = new DefaultMutableTreeNode("Machine Tool");
			
			DefaultMutableTreeNode nodeGeneralTmp = new DefaultMutableTreeNode("General Data: ");
			if(machineTmp.getToolHandlingDevice().size() > 0)
			{
				root.add(this.createNodeToolMagazine(machineTmp));
			}
			if(machineTmp.getWorkpieceHandlingDevice().size() > 0)
			{
				root.add(this.createNodeWorkpieceHandlingDevices(machineTmp));
			}
			if(machineTmp.getItsSpindle().size() > 0)
			{
				root.add(this.createNodeItsSpindle(machineTmp));
			}
		}
		return root;
	}
	private DefaultMutableTreeNode createNodeWorkpieceHandlingDevices(MachineTool machine)
	{
		DefaultMutableTreeNode node = new DefaultMutableTreeNode("Its Handling Devices: ");
		for(int i = 0; i < machine.getWorkpieceHandlingDevice().size(); i++)
		{
			WorkpieceHandlingDevice workpieceHandlingDeviceTmp = machine.getWorkpieceHandlingDevice().get(i);
			DefaultMutableTreeNode nodeTmp = new DefaultMutableTreeNode("" + workpieceHandlingDeviceTmp.getItsId());
			nodeTmp.add(new DefaultMutableTreeNode("Max load capacity: " + workpieceHandlingDeviceTmp.getMaxLoadCapacity()));
			nodeTmp.add(new DefaultMutableTreeNode("type: " + workpieceHandlingDeviceTmp.getType()));
			node.add(nodeTmp);
		}
		return node;
	}
	private DefaultMutableTreeNode createNodeToolMagazine(MachineTool machine) 
	{
		DefaultMutableTreeNode node = new DefaultMutableTreeNode("Its ToolMagazine: ");
		CuttingToolHandlingDevice magazine = machine.getToolHandlingDevice().get(0);
		node.add(new DefaultMutableTreeNode("Its Tool Capacity: " + magazine.getItsToolCapacity()));
		node.add(new DefaultMutableTreeNode("Its Current Tool : " + magazine.getItsCurrentTool()));
		// por ora so esses aew
		
		for(int i = 0; i < magazine.getToolList().size(); i++)
		{
			Ferramenta tool = magazine.getToolList().get(i);
			DefaultMutableTreeNode nodoFerramentaTmp = new DefaultMutableTreeNode("Tool List : " + tool.getName());
		
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Type : " + tool.getClass().toString().substring(42)));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Diameter : " + tool.getDiametroFerramenta() + " mm"));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Cutting Edge Length : " + tool.getCuttingEdgeLength() + " mm"));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Max Depth : " + tool.getProfundidadeMaxima() + " mm"));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Off Set Length : " + tool.getOffsetLength() + " mm"));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Hand Of Cut : " + tool.getStringHandOfCut()));
			nodoFerramentaTmp.add(new DefaultMutableTreeNode("Material : Carbide - " + tool.getMaterial()));
			if(tool.getClass() == CenterDrill.class || tool.getClass() == TwistDrill.class)
			{
				nodoFerramentaTmp.add(new DefaultMutableTreeNode("Tip Tool Half Angle : " + tool.getToolTipHalfAngle() + " °"));
			} else if (tool.getClass() == BallEndMill.class)
			{
				nodoFerramentaTmp.add(new DefaultMutableTreeNode("Edge Radius: " + tool.getEdgeRadius() + " mm"));
			} else if (tool.getClass() == BullnoseEndMill.class)
			{
				nodoFerramentaTmp.add(new DefaultMutableTreeNode( "Edge Radius: " + tool.getEdgeRadius() + " mm"));
				nodoFerramentaTmp.add(new DefaultMutableTreeNode("Edge Center Vertical: " + tool.getEdgeCenterVertical() + " mm"));
				nodoFerramentaTmp.add(new DefaultMutableTreeNode("Edge Center Horizontal: " + tool.getEdgeCenterHorizontal() + " mm"));				
			}		
			node.add(nodoFerramentaTmp);
		}
		 return node;
	}
	private DefaultMutableTreeNode createNodeItsSpindle(MachineTool machine)
	{
		DefaultMutableTreeNode nodeItsSpindle = new DefaultMutableTreeNode("Its Spindles:");
		Spindle spindle;
		for(int i = 0; i < machine.getItsSpindle().size(); i++)
		{
			spindle = machine.getItsSpindle().get(i);
			nodeItsSpindle = new DefaultMutableTreeNode("Its Id: " + spindle.getItsId());
			nodeItsSpindle.add(new DefaultMutableTreeNode("Max Power (kW): " + spindle.getSpindleMaxPower()));
			nodeItsSpindle.add(new DefaultMutableTreeNode("Max Speed (rpm): " + spindle.getItsSpeedRange()));
			nodeItsSpindle.add(new DefaultMutableTreeNode("Max Torque (N-m): " + spindle.getMaxTorque()));
		}
		return nodeItsSpindle;
	}
}
