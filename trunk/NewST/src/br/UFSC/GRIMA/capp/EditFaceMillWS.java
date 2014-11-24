package br.UFSC.GRIMA.capp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;

import br.UFSC.GRIMA.cad.JanelaPrincipal;
import br.UFSC.GRIMA.cad.visual.EditFaceMillFrame;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.capp.plunge.PlungeRamp;
import br.UFSC.GRIMA.capp.plunge.PlungeStrategy;
import br.UFSC.GRIMA.capp.plunge.PlungeToolAxis;
import br.UFSC.GRIMA.capp.plunge.PlungeZigzag;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;

public class EditFaceMillWS extends EditFaceMillFrame implements ActionListener, ItemListener{


	JanelaPrincipal janelaPrincipal;
	
	private String materialClass = "P";
	private int handOfCut = 1;
	private Workingstep workingstep;
	private String tipo;
	
	private BottomAndSideRoughMilling operation;
	private PlungeStrategy plungeStrategy;
	private FaceMill faceMill;
	private CondicoesDeUsinagem condicoes;
	
	public EditFaceMillWS(JanelaPrincipal janelaPrincipal, Workingstep wsTmp) {
		
		super(janelaPrincipal);	
	
		this.janelaPrincipal = janelaPrincipal;
		
		this.workingstep = wsTmp;
		this.operation = (BottomAndSideRoughMilling)wsTmp.getOperation();
		this.faceMill = (FaceMill) wsTmp.getFerramenta();
		this.condicoes = wsTmp.getCondicoesUsinagem();
		this.plungeStrategy = (PlungeStrategy)operation.getApproachStrategy();
		
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		this.comboBox1.addItemListener(this);
		this.comboBox3.addItemListener(this);
		this.bolaVert.addActionListener(this);
		this.bolaRamp.addActionListener(this);
		this.bolaZigzag.addActionListener(this);
		this.setVisible(true);
		this.widthBox.setVisible(false);
		this.retractPlane.setVisible(false);
		this.retractVertical.setVisible(true);
		this.angle.setVisible(false);
		this.angleZigZag.setVisible(false);
		
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
		String idOp = this.operation.getId();
		this.formattedTextField3.setText(idOp);
		this.checkBox3.setSelected(operation.isCoolant());
		
		double retractPlane = this.operation.getRetractPlane();
		this.spinner15.setValue(retractPlane);
		
		double allowanceSide = this.operation.getAllowanceSide();
		this.spinner13.setValue(allowanceSide);
		
		double allowanceBottom = this.operation.getAllowanceBottom();
		this.spinner14.setValue(allowanceBottom);
		
		//Approach Strategy
		
		if (plungeStrategy.getClass() == PlungeToolAxis.class)
		{
			PlungeToolAxis pl = (PlungeToolAxis)plungeStrategy;
			label1.setIcon(new ImageIcon(getClass().getResource("/images/Axis.png")));
			this.retractVertical.setVisible(true);
			this.retractPlane.setVisible(false);
			this.angle.setVisible(false);
			this.angleZigZag.setVisible(false);
			this.widthBox.setVisible(false);
			this.bolaVert.setSelected(true);
			this.retractVertical.setValue(retractPlane);
			
		}
		
		
		else if (plungeStrategy.getClass() == PlungeRamp.class)
		{
			PlungeRamp pl = (PlungeRamp)plungeStrategy;
			double angle =  pl.getAngle();
			this.angle.setValue(angle);
			label1.setIcon(new ImageIcon(getClass().getResource("/images/Ramp Plunge.png")));
			this.angle.setVisible(true);
			this.angleZigZag.setVisible(false);
			this.widthBox.setVisible(false);
			this.retractPlane.setVisible(true);
			this.retractVertical.setVisible(false);
			this.bolaRamp.setSelected(true);
			this.retractPlane.setValue(retractPlane);
			
			
		} else if( plungeStrategy.getClass() == PlungeZigzag.class)
		{
			PlungeZigzag pl = (PlungeZigzag)plungeStrategy;
			double angle = pl.getAngle();
			double width = pl.getWidth();
			this.angleZigZag.setValue(angle);
			this.widthBox.setValue(width);
			this.retractPlane.setValue(retractPlane);
			label1.setIcon(new ImageIcon(getClass().getResource("/images/Zig Zag.png")));
			this.angleZigZag.setVisible(true);
			this.retractPlane.setVisible(true);
			this.widthBox.setVisible(true);
			this.retractVertical.setVisible(false);
			this.angle.setVisible(false);
			this.bolaZigzag.setSelected(true);
		}
		
		//TOOL
		String nome = this.faceMill.getName();
		double diametro = this.faceMill.getDiametroFerramenta();
		double cuttingEdge = this.faceMill.getCuttingEdgeLength();
		double profundidade = this.faceMill.getProfundidadeMaxima();
		double offSetLength = this.faceMill.getOffsetLength();
		double dm = this.faceMill.getDm();
		String material = this.faceMill.getMaterial();
		int handOfCut = this.faceMill.getHandOfCut();
		int numberOfTeeth = this.faceMill.getNumberOfTeeth();
		
		this.textField1.setText(nome);
		this.spinner6.setValue(diametro);
		this.spinner4.setValue(diametro);
		this.spinner3.setValue(cuttingEdge);
		this.spinner2.setValue(profundidade);
		this.spinner1.setValue(offSetLength);
		this.spinner5.setValue(dm);
		this.spinner8.setValue(numberOfTeeth);
		
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
		else if (object == bolaVert)
		{
			label1.setIcon(new ImageIcon(getClass().getResource("/images/Axis.png")));
			this.retractVertical.setVisible(true);
			this.retractPlane.setVisible(false);
			this.angle.setVisible(false);
			this.angleZigZag.setVisible(false);
			this.widthBox.setVisible(false);
		}
		
		else if (object == bolaRamp)
		{
			label1.setIcon(new ImageIcon(getClass().getResource("/images/Ramp Plunge.png")));
			this.angle.setVisible(true);
			this.angleZigZag.setVisible(false);
			this.widthBox.setVisible(false);
			this.retractPlane.setVisible(true);
			this.retractVertical.setVisible(false);
		}
		else if (object == bolaZigzag)
		{
			label1.setIcon(new ImageIcon(getClass().getResource("/images/Zig Zag.png")));
			this.angleZigZag.setVisible(true);
			this.retractPlane.setVisible(true);
			this.widthBox.setVisible(true);
			this.retractVertical.setVisible(false);
			this.angle.setVisible(false);
		}
	}
	private void ok() 
	{
		//ID Workinstep
		this.workingstep.setId(this.formattedTextField1.getText()+"_"+this.tipo);
		
		//OPERATION
		this.operation.setId(this.formattedTextField3.getText());
		this.operation.setCoolant(this.checkBox3.isSelected());
		this.operation.setRetractPlane((Double) this.spinner15.getValue());
		this.operation.setAllowanceSide((Double) this.spinner13.getValue());
		this.operation.setAllowanceBottom((Double) this.spinner14.getValue());
		
		//PLUNGE STRATEGY
		if(bolaVert.isSelected())
		{
			PlungeToolAxis pl = (PlungeToolAxis)plungeStrategy;
			operation.setApproachStrategy(pl);
		} else if (bolaRamp.isSelected())
		{
			PlungeRamp pl = (PlungeRamp)plungeStrategy;
			pl.setAngle((Double)angle.getValue());
			operation.setApproachStrategy(pl);
		} else if (bolaZigzag.isSelected())
		{
			PlungeZigzag pl = (PlungeZigzag)plungeStrategy;
			pl.setAngle((Double)angle.getValue());
			pl.setWidth((Double)widthBox.getValue());
			operation.setApproachStrategy(pl);
		}
			
		
		//TOOL
		this.faceMill.setName(this.textField1.getText());
		this.faceMill.setDiametroFerramenta((Double)this.spinner4.getValue());
		this.faceMill.setCuttingEdgeLength((Double)this.spinner3.getValue());
		this.faceMill.setProfundidadeMaxima((Double)this.spinner2.getValue());
		this.faceMill.setOffsetLength((Double)this.spinner1.getValue());
		this.faceMill.setDm((Double)this.spinner5.getValue());
		this.faceMill.setMaterial(this.materialClass);
		this.faceMill.setHandOfCut(this.handOfCut);
		this.faceMill.setNumberOfTeeth((Integer) this.spinner8.getValue());
		
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
