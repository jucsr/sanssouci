package br.UFSC.GRIMA.capp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import br.UFSC.GRIMA.cad.JanelaPrincipal;
import br.UFSC.GRIMA.cad.visual.EditBullnoseEndMillFrame;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideFinishMilling;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.entidades.ferramentas.BallEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.BullnoseEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;

public class EditBullnoseEndMillWS extends EditBullnoseEndMillFrame implements ActionListener, ItemListener{

JanelaPrincipal janelaPrincipal;
	
	private String materialClass = "P";
	private int handOfCut = 1;
	private Workingstep workingstep;
	private String tipo;
	
//	private MachiningOperation operation;
	private BullnoseEndMill bullnoseEndMill;
	private CondicoesDeUsinagem condicoes;
	
	public EditBullnoseEndMillWS(JanelaPrincipal janelaPrincipal, Workingstep wsTmp) {
		
		super(janelaPrincipal);	
	
		this.janelaPrincipal = janelaPrincipal;
		
		this.workingstep = wsTmp;
//		this.operation = wsTmp.getOperation();
		this.bullnoseEndMill = (BullnoseEndMill) wsTmp.getFerramenta();
		this.condicoes = wsTmp.getCondicoesUsinagem();
		
		
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		this.comboBox1.addItemListener(this);
		this.comboBox3.addItemListener(this);
		
		this.lerCampos();
		
		this.setVisible(true);
		}

	
	private void lerCampos() {
		
		//IDs Workinstep e Feature
		String idWs = workingstep.getId();
		this.formattedTextField1.setText(idWs);
		
		String[] arrayId = idWs.split("_");
		int last = arrayId.length-1;
		this.tipo = arrayId[last];
		
		String featureId = workingstep.getFeature().getNome();
		this.label5.setText(featureId);
		
		//OPERATION
		
		if(this.workingstep.getOperation().getClass()==BottomAndSideFinishMilling.class){
			
			BottomAndSideFinishMilling operation = (BottomAndSideFinishMilling)this.workingstep.getOperation();
			
			this.label24.setText("Bottom And Side Finish Milling");
			
			String idOp = operation.getId();
			this.formattedTextField3.setText(idOp);
			this.checkBox3.setSelected(operation.isCoolant());
			
			double retractPlane = operation.getRetractPlane();
			this.spinner16.setValue(retractPlane);
			
			double allowanceSide = operation.getAllowanceSide();
			this.spinner14.setValue(allowanceSide);
			
			double allowanceBottom = operation.getAllowanceBottom();
			this.spinner15.setValue(allowanceBottom);
			
		}else if(this.workingstep.getOperation().getClass()==BottomAndSideRoughMilling.class){
			
			BottomAndSideRoughMilling operation = (BottomAndSideRoughMilling)this.workingstep.getOperation();
			
			this.label24.setText("Bottom And Side Rough Milling");
			
			String idOp = operation.getId();
			this.formattedTextField3.setText(idOp);
			this.checkBox3.setSelected(operation.isCoolant());
			
			double retractPlane = operation.getRetractPlane();
			this.spinner16.setValue(retractPlane);
			
			double allowanceSide = operation.getAllowanceSide();
			this.spinner14.setValue(allowanceSide);
			
			double allowanceBottom = operation.getAllowanceBottom();
			this.spinner15.setValue(allowanceBottom);
			
		}else{
			
			System.out.println("Operation Desconhecida! (EditBallEndMillWS) : " + this.workingstep.getOperation().getClass());
		}
		
		
		//TOOL
		String nome = this.bullnoseEndMill.getName();
		double diametro = this.bullnoseEndMill.getDiametroFerramenta();
		double cuttingEdge = this.bullnoseEndMill.getCuttingEdgeLength();
		double profundidade = this.bullnoseEndMill.getProfundidadeMaxima();
		double offSetLength = this.bullnoseEndMill.getOffsetLength();
		double dm = this.bullnoseEndMill.getDm();
		double edgeRadius = this.bullnoseEndMill.getEdgeRadius();
		String material = this.bullnoseEndMill.getMaterial();
		int handOfCut = this.bullnoseEndMill.getHandOfCut();
		int numberOfTeeth = this.bullnoseEndMill.getNumberOfTeeth();
		
		this.textField1.setText(nome);
		this.spinner4.setValue(diametro);
		this.spinner3.setValue(cuttingEdge);
		this.spinner2.setValue(profundidade);
		this.spinner1.setValue(offSetLength);
		this.spinner5.setValue(dm);
		this.spinner8.setValue(edgeRadius);
		this.spinner13.setValue(numberOfTeeth);
		
		if(material.equals("P"))
		{
			this.comboBox1.setSelectedIndex(0);
		} else if (material.equals("M"))
		{
			this.comboBox1.setSelectedIndex(1);
		} else if (material.equals("S"))
		{
			this.comboBox1.setSelectedIndex(2);
		} else if (material.equals("K"))
		{
			this.comboBox1.setSelectedIndex(3);
		} else if (material.equals("H"))
		{
			this.comboBox1.setSelectedIndex(4);
		} else if (material.equals("N"))
		{
			this.comboBox1.setSelectedIndex(5);
		} 
		
		if (handOfCut == Ferramenta.RIGHT_HAND_OF_CUT)
		{
			this.comboBox3.setSelectedIndex(0);
		}  else if (handOfCut == Ferramenta.LEFT_HAND_OF_CUT)
		{
			this.comboBox3.setSelectedIndex(1);
		}  else if (handOfCut == Ferramenta.NEUTRAL_HAND_OF_CUT)
		{
			this.comboBox3.setSelectedIndex(2);
		} 
		
		
		//CONDICOES DE USINAGEM (TECHNOLOGY)
		int n = (int) this.condicoes.getN();
		this.spinner10.setValue(n);
		
		double vc = this.condicoes.getVc();
		this.spinner7.setValue(vc);
		
		double f = this.condicoes.getF();
		this.spinner9.setValue(f);
		
		double ap = this.condicoes.getAp();
		this.spinner11.setValue(ap);
		
		double ae = this.condicoes.getAe();
		this.spinner12.setValue(ae);
		
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
		this.workingstep.setId(this.formattedTextField1.getText()+"_"+this.tipo);
		
		//OPERATION
		this.workingstep.getOperation().setId(this.formattedTextField3.getText());
		this.workingstep.getOperation().setCoolant(this.checkBox3.isSelected());
		this.workingstep.getOperation().setRetractPlane((Double) this.spinner16.getValue());
		
		if(this.workingstep.getOperation().getClass()==BottomAndSideFinishMilling.class){
			((BottomAndSideFinishMilling)this.workingstep.getOperation()).setAllowanceSide((Double) this.spinner14.getValue());
			((BottomAndSideFinishMilling)this.workingstep.getOperation()).setAllowanceBottom((Double) this.spinner15.getValue());
		}else if(this.workingstep.getOperation().getClass()==BottomAndSideRoughMilling.class){
			((BottomAndSideRoughMilling)this.workingstep.getOperation()).setAllowanceSide((Double) this.spinner14.getValue());
			((BottomAndSideRoughMilling)this.workingstep.getOperation()).setAllowanceBottom((Double) this.spinner15.getValue());
		}

		//TOOL
		this.bullnoseEndMill.setName(this.textField1.getText());
		this.bullnoseEndMill.setDiametroFerramenta((Double)this.spinner4.getValue());
		this.bullnoseEndMill.setCuttingEdgeLength((Double)this.spinner3.getValue());
		this.bullnoseEndMill.setProfundidadeMaxima((Double)this.spinner2.getValue());
		this.bullnoseEndMill.setOffsetLength((Double)this.spinner1.getValue());
		this.bullnoseEndMill.setDm((Double)this.spinner5.getValue());
		this.bullnoseEndMill.setEdgeRadius((Double)this.spinner8.getValue());
		this.bullnoseEndMill.setMaterial(this.materialClass);
		this.bullnoseEndMill.setHandOfCut(this.handOfCut);
		this.bullnoseEndMill.setNumberOfTeeth((Integer) this.spinner13.getValue());
		
		//CONDICOES DE USINAGEM (TECHNOLOGY)
		this.condicoes.setN((Integer) this.spinner10.getValue());
		this.condicoes.setVc((Double) this.spinner7.getValue());
		this.condicoes.setF((Double) this.spinner9.getValue());
		this.condicoes.setAp((Double) this.spinner11.getValue());
		this.condicoes.setAe((Double) this.spinner12.getValue());
		
		
		this.janelaPrincipal.atualizarArvoreCAPP();
		this.janelaPrincipal.atualizarArvore();
		
		this.dispose();
	}

}
