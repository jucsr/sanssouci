package br.UFSC.GRIMA.shopFloor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.cad.JanelaPrincipal;
import br.UFSC.GRIMA.shopFloor.visual.EditCenterDrillFrame;
import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.CenterDrilling;
import br.UFSC.GRIMA.capp.machiningOperations.Drilling;
import br.UFSC.GRIMA.capp.machiningOperations.MachiningOperation;
import br.UFSC.GRIMA.entidades.ferramentas.CenterDrill;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;

public class EditCenterDrillWS extends EditCenterDrillFrame implements ActionListener, ItemListener{

	private DefaultTableModel model = new DefaultTableModel();
	
	JanelaShopFloor janelaShopFloor;
	
	private String materialClass = "P";
	private int handOfCut = 1;
	private ArrayList<Workingstep> wsArray;
	private Workingstep ws;
	private Workingstep workingstepPrecedente = null;
	private ProjetoSF projetoSF;
	private String tipo;
	
	private CenterDrill centerDrill = new CenterDrill();
	private CondicoesDeUsinagem condicoes = new CondicoesDeUsinagem();
	
	public EditCenterDrillWS(JanelaShopFloor janelaShopFloorNew, ProjetoSF projetoSFNew, Workingstep wsNew) {
		
		super(janelaShopFloorNew);	
		
		this.projetoSF = projetoSFNew;
		this.janelaShopFloor = janelaShopFloorNew;
		this.wsArray = projetoSFNew.getWorkingsteps();
		this.ws = wsNew;
		
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		this.comboBox1.addItemListener(this);
		this.comboBox3.addItemListener(this);
		
		this.selectOnePrecedent(this.defaultTable(model));
		
		this.setVisible(true);
		}

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
	
	public DefaultTableModel defaultTable(DefaultTableModel model2)
	{
			for(int j=0; j < wsArray.size(); j++)
			{
				model2 = (DefaultTableModel) table1.getModel();
				Object row [] = {false, wsArray.get(j).getId(), wsArray.get(j).getOperation().getOperationType()};
				model2.addRow(row);
				
			}
			return model2;
	}
	
	public void selectOnePrecedent(final DefaultTableModel model3) // It allows one selection of WSPrecedent in the table
	{
		table1.addMouseListener(new MouseAdapter() {
		
			public void mouseClicked(MouseEvent e){
				int indicePrecedente = table1.getSelectedRow();
				editTable(model3, indicePrecedente); // every time the table is clicked, all selection parameters become "false"
			workingstepPrecedente = wsArray.get(indicePrecedente);
			}
		});
	}
	
	public void editTable(DefaultTableModel model, int lineIndex)
	{
		for (int i = 0; i < table1.getRowCount(); i++)
		{
			model.setValueAt(false, i, 0);
		}
		model.setValueAt(true, lineIndex, 0);
	}
	
	private void ok() 
	{
		//ID Workinstep
		this.ws.setId(this.formattedTextField1.getText());
		
		//OPERATION
		this.ws.getOperation().setId(this.formattedTextField2.getText());
		this.ws.getOperation().setCoolant(this.checkBox1.isSelected());
		this.ws.getOperation().setRetractPlane((Double) this.spinner13.getValue());
		((Drilling)this.ws.getOperation()).setCuttingDepth((Double) this.spinner14.getValue());
		((Drilling)this.ws.getOperation()).setPreviousDiameter((Double) this.spinner12.getValue());
		double zStart = (Double) this.spinner11.getValue();
		((Drilling)this.ws.getOperation()).setStartPoint(new Point3d(0,0,zStart));
		
		//TOOL
		this.centerDrill.setName(this.textField1.getText());
		this.centerDrill.setDiametroFerramenta((Double)this.spinner1.getValue());
		this.centerDrill.setToolTipHalfAngle((180 - (Double)this.spinner4.getValue()) / 2);
		this.centerDrill.setCuttingEdgeLength((Double)this.spinner3.getValue());
		this.centerDrill.setProfundidadeMaxima((Double)this.spinner6.getValue()/2);
		this.centerDrill.setOffsetLength((Double)this.spinner6.getValue());
		this.centerDrill.setDm((Double)this.spinner5.getValue());
		this.centerDrill.setMaterial(this.materialClass);
		this.centerDrill.setHandOfCut(this.handOfCut);
		this.centerDrill.setNumberOfTeeth((Integer) this.spinner8.getValue());
		
		//CONDICOES DE USINAGEM (TECHNOLOGY)
		this.condicoes.setN((Integer) this.spinner10.getValue());
		this.condicoes.setVc((Double) this.spinner7.getValue());
		this.condicoes.setF((Double) this.spinner9.getValue());
		
		//Add Technology and tool in the new workingstep
		this.ws.setCondicoesUsinagem(condicoes);
		this.ws.setFerramenta(centerDrill);

		// Cost, Time 
		this.ws.setTempo((Double)this.spinner15.getValue());
		this.ws.setCusto((Double) this.spinner16.getValue());

		ws.setWorkingstepPrecedente(workingstepPrecedente);
		wsArray.add(ws);
		projetoSF.setWorkingsteps(wsArray);
		
		//Chamada do metodo de atualiza�ao da JTree
		this.janelaShopFloor.atualizarArvorePrecendences(); //New
		
		this.dispose();
	}
}
