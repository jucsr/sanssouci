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

import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.Drilling;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.entidades.ferramentas.TwistDrill;
import br.UFSC.GRIMA.shopFloor.visual.EditTwistDrillFrame;

public class EditTwistDrillWS extends EditTwistDrillFrame implements ActionListener, ItemListener{


	private DefaultTableModel model = new DefaultTableModel();
	   
	private JanelaShopFloor janelaShopFloor;
	
	private String materialClass = "P";
	private int handOfCut = 1;
	private ArrayList<Workingstep> wsArray;
	private Workingstep ws;
	private Workingstep workingstepPrecedente = null;
	private ProjetoSF projetoSF;
	private String tipo;
	private Drilling operation;
	private TwistDrill twistDrill = new TwistDrill();
	private CondicoesDeUsinagem condicoes = new CondicoesDeUsinagem();
	
	public EditTwistDrillWS(JanelaShopFloor janelaShopFloorNew, ProjetoSF projetoSFNew, Workingstep wsNew) 
	{
		super(janelaShopFloorNew);	
		
		this.projetoSF = projetoSFNew;
		this.janelaShopFloor = janelaShopFloorNew;
		this.wsArray = projetoSFNew.getWorkingsteps();
		this.ws = wsNew;
		this.loadWorkingstepsList();
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		this.comboBox1.addItemListener(this);
		this.comboBox3.addItemListener(this);
		this.table1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				int indicePrecedente = table1.getSelectedRow();

				if((Boolean)(table1.getValueAt(indicePrecedente, 0)) == true)
				{
					for(int i = 0; i < table1.getRowCount(); i++)
					{
						if (i != indicePrecedente)
						table1.setValueAt(false, i, 0);
					}
				} else
				{
					table1.setValueAt(false, indicePrecedente, 0);
				} 
				if((Boolean)(table1.getValueAt(indicePrecedente, 0)) == true)
				{
					workingstepPrecedente = wsArray.get(indicePrecedente);
				}else
					workingstepPrecedente = null;
			}
		});
		this.setVisible(true);
	}

	private void loadWorkingstepsList()
	{
		wsArray = this.projetoSF.getWorkingsteps();
		DefaultTableModel modelo = (DefaultTableModel) table1.getModel();
		
		for(int i = 0; i < wsArray.size(); i++)
		{
			Object row [] = {false, wsArray.get(i).getId(), wsArray.get(i).getOperation().getOperationType()};
			modelo.addRow(row);
		}
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
	private void ok() 
	{
		//ID Workinstep
		this.ws.setId(this.formattedTextField1.getText());
		
		//OPERATION
		this.operation = new Drilling("", 5);
		this.operation.setId(this.formattedTextField2.getText());
		this.operation.setCoolant(this.checkBox1.isSelected());
		this.operation.setRetractPlane((Double) this.spinner9.getValue());
		this.operation.setCuttingDepth((Double) this.spinner11.getValue());
		this.operation.setPreviousDiameter((Double) this.spinner10.getValue());
		double zStart = (Double) this.spinner7.getValue();
		this.operation.setStartPoint(new Point3d(0,0,zStart));
		
		//TOOL
		this.twistDrill.setName(this.textField1.getText());
		this.twistDrill.setDiametroFerramenta((Double)this.spinner4.getValue());
		this.twistDrill.setToolTipHalfAngle(90 - (Double)this.spinner6.getValue());
		this.twistDrill.setCuttingEdgeLength((Double)this.spinner3.getValue());
		this.twistDrill.setProfundidadeMaxima((Double)this.spinner2.getValue());
		this.twistDrill.setOffsetLength((Double)this.spinner1.getValue());
		this.twistDrill.setDm((Double)this.spinner5.getValue());
		this.twistDrill.setMaterial(this.materialClass);
		this.twistDrill.setHandOfCut(this.handOfCut);
		this.twistDrill.setNumberOfTeeth((Integer) this.spinner8.getValue());
		
		//CONDICOES DE USINAGEM (TECHNOLOGY)
		this.condicoes.setN((Integer) this.spinner15.getValue());
		this.condicoes.setVc((Double) this.spinner13.getValue());
		this.condicoes.setF((Double) this.spinner14.getValue());
		
		this.ws.setOperation(operation);
		this.ws.setFerramenta(twistDrill);
		this.ws.setCondicoesUsinagem(condicoes);
		this.ws.setWorkingstepPrecedente(workingstepPrecedente);
		this.wsArray.add(ws);
		this.projetoSF.setWorkingsteps(wsArray);
		this.janelaShopFloor.atualizarArvorePrecendences();
		
		this.dispose();
	}

}
