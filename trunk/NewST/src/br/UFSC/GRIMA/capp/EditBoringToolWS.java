package br.UFSC.GRIMA.capp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.cad.JanelaPrincipal;
import br.UFSC.GRIMA.cad.visual.EditBoringToolFrame;
import br.UFSC.GRIMA.capp.machiningOperations.Boring;
import br.UFSC.GRIMA.entidades.ferramentas.BoringTool;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;

public class EditBoringToolWS extends EditBoringToolFrame implements ActionListener, ItemListener{

	JanelaPrincipal janelaPrincipal;
	
	private String materialClass = "P";
	private int handOfCut = 1;
	private Workingstep workingstep;
	private String tipo;
	
	private Boring operation;
	private BoringTool boringTool;
	private CondicoesDeUsinagem condicoes;
	
	public EditBoringToolWS(JanelaPrincipal janelaPrincipal, Workingstep wsTmp) {
		
		super(janelaPrincipal);	
	
		this.janelaPrincipal = janelaPrincipal;
		
		this.workingstep = wsTmp;
		this.operation = (Boring)wsTmp.getOperation();
		this.boringTool = (BoringTool) wsTmp.getFerramenta();
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
		String idOp = this.operation.getId();
		this.formattedTextField2.setText(idOp);
		this.checkBox1.setSelected(operation.isCoolant());
		
		double retractPlane = this.operation.getRetractPlane();
		this.spinner9.setValue(retractPlane);
		
		double zStart = this.operation.getStartPoint().getZ();
		this.spinner7.setValue(zStart);
		
		double cuttingDepth = this.operation.getCuttingDepth();
		this.spinner11.setValue(cuttingDepth);
		
		double previousDiameter = this.operation.getPreviousDiameter();
		this.spinner10.setValue(previousDiameter);
		
		
		//TOOL
		String nome = this.boringTool.getName();
		double diametro = this.boringTool.getDiametroFerramenta();
		double cuttingEdge = this.boringTool.getCuttingEdgeLength();
		double profundidade = this.boringTool.getProfundidadeMaxima();
		double offSetLength = this.boringTool.getOffsetLength();
		double dm = this.boringTool.getDm();
		String material = this.boringTool.getMaterial();
		int handOfCut = this.boringTool.getHandOfCut();
		int numberOfTeeth = this.boringTool.getNumberOfTeeth();
		String faixaDiametro = this.boringTool.getDiametro();
		String dMinimo = faixaDiametro.split("-")[0];
		double diametroMinimo = Double.parseDouble(dMinimo);
		
		this.textField1.setText(nome);
		this.spinner4.setValue(diametro);
		this.spinner6.setValue(diametroMinimo);
		this.spinner5.setValue(cuttingEdge);
		this.spinner2.setValue(profundidade);
		this.spinner1.setValue(offSetLength);
		this.spinner3.setValue(dm);
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
		this.spinner15.setValue(n);
		
		double vc = this.condicoes.getVc();
		this.spinner13.setValue(vc);
		
		double f = this.condicoes.getF();
		this.spinner14.setValue(f);
		
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
		this.operation.setId(this.formattedTextField2.getText());
		this.operation.setCoolant(this.checkBox1.isSelected());
		this.operation.setRetractPlane((Double) this.spinner9.getValue());
		this.operation.setCuttingDepth((Double) this.spinner11.getValue());
		this.operation.setPreviousDiameter((Double) this.spinner10.getValue());
		double zStart = (Double) this.spinner7.getValue();
		this.operation.setStartPoint(new Point3d(0.0,0.0,zStart));
		
		//TOOL
		this.boringTool.setName(this.textField1.getText());
		double diametro = (Double)this.spinner4.getValue();
		double dMinimo = (Double)this.spinner6.getValue();
		this.boringTool.setDiametroFerramenta(diametro);
		this.boringTool.setDiametro(dMinimo+"-"+diametro);
		this.boringTool.setCuttingEdgeLength((Double)this.spinner5.getValue());
		this.boringTool.setProfundidadeMaxima((Double)this.spinner2.getValue());
		this.boringTool.setOffsetLength((Double)this.spinner1.getValue());
		this.boringTool.setDm((Double)this.spinner3.getValue());
		this.boringTool.setMaterial(this.materialClass);
		this.boringTool.setHandOfCut(this.handOfCut);
		this.boringTool.setNumberOfTeeth((Integer) this.spinner8.getValue());
		
		//CONDICOES DE USINAGEM (TECHNOLOGY)
		this.condicoes.setN((Integer) this.spinner15.getValue());
		this.condicoes.setVc((Double) this.spinner13.getValue());
		this.condicoes.setF((Double) this.spinner14.getValue());
		
		
		this.janelaPrincipal.atualizarArvoreCAPP();
		this.janelaPrincipal.atualizarArvore();
		
		this.dispose();
	}


}
