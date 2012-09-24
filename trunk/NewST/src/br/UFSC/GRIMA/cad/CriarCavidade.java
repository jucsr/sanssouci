package br.UFSC.GRIMA.cad;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.cad.visual.CavidadeFrame;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.util.projeto.Axis2Placement3D;


public class CriarCavidade extends CavidadeFrame implements ActionListener
{	public Face face = null;
	public JanelaPrincipal parent;
	
	public CriarCavidade(JanelaPrincipal parent, Face face)
	{	
		super(parent);
		
		this.face = face;
		this.parent = parent;
		//this.parent.setEnabled(false);
		
		this.init();
	}
	
	public void init(){
		this.adjustSize();
		this.adjustPosition();

		this.label16.setText(this.face.getTipoString());
		
		super.okButton.addActionListener(this);
		super.cancelButton.addActionListener(this);
		this.checkBox1.addActionListener(this);
		this.setVisible(true);
		
		if (this.checkBox1.isSelected() == true)
		{
			this.spinner8.setEnabled(false);
			this.spinner7.addChangeListener(new ChangeListener() 
			{
				public void stateChanged(ChangeEvent evt) 
				{
					double profTmp = face.getProfundidadeMaxima() - ((Double)(spinner7.getValue())).doubleValue();
					spinner8.setModel(new SpinnerNumberModel(profTmp, null, null, 1.0));
				}
			});
		}
		else if (this.checkBox1.isSelected() == false)
		{
			this.spinner8.setEnabled(true);
		}
	}
	
	public void adjustSize(){
		this.pack();
	}

	//centraliza a janela no desktop do usuario
	public void adjustPosition(){
		Toolkit toolkit = this.getToolkit();
		
		Dimension screenSize = toolkit.getScreenSize();
		Dimension size = this.getPreferredSize();
		
//		System.out.println(screenSize.toString());
//		System.out.println(size.toString());

		int posX = (int)(screenSize.getWidth() - size.getWidth())/2;
		int posY = (int)(screenSize.getHeight() - size.getHeight())/2;

		this.setLocation(posX, posY);
	}
	
	
	public void actionPerformed(ActionEvent event) 
	{
		Object origem = event.getSource();
		if (origem == okButton)
		{	ok();
		} else if (origem == cancelButton)
		{	cancel();
		} else if (origem == this.checkBox1)
		{
			selected();
		}
	}
	private void selected()
	{
		if (this.checkBox1.isSelected() == true)
		{
			this.spinner8.setEnabled(false);
			double profTmp = face.getProfundidadeMaxima() - ((Double)(spinner7.getValue())).doubleValue();
			spinner8.setModel(new SpinnerNumberModel(profTmp, null, null, 1.0));
			this.spinner7.addChangeListener(new ChangeListener() 
			{
				public void stateChanged(ChangeEvent evt) 
				{
					double profTmp = face.getProfundidadeMaxima() - ((Double)(spinner7.getValue())).doubleValue();
					spinner8.setModel(new SpinnerNumberModel(profTmp, null, null, 1.0));
				}
			});
		}
		else if (this.checkBox1.isSelected() == false)
		{
			this.spinner8.setEnabled(true);
		}
	}
	private void ok()
	{	boolean ok = true;
		double largura = 0.0;
		double comprimento = 0.0;
		double profundidade = 0.0;
		double raio = 0.0;
		double posicaoX = 0.0;
		double posicaoY = 0.0;
		double posicaoZ = 0.0;
		double refLargura = 0.0;
		double refComprimento = 0.0;
		double raioMin = 0;
		double tolerancia = 0.0;
		double rugosidade = 0.0;
		
		/****** validacao dos dados introduzidos no campo "largura" *****
		*/
		
		/** ****** validacao dos dados introduzidos no campo "Deslocamento X" ********
		 * 
		 */
		if (ok) {
			try {
				posicaoX = ((Double)this.spinner1.getValue()).doubleValue();
				if (posicaoX > 0 /*&& x < Maquina.posicaoXCv*/) {
					//System.out.println("deslocamento X: " + posicaoX);
					ok = true;
				} else {
					ok = false;
					JOptionPane.showMessageDialog(null,
							"Digite um Número positivo para a posição X da cavidade",
							"Erro na posição X", JOptionPane.OK_CANCEL_OPTION);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Digite um Número positivo para a posição X da cavidade"
						+ "\n               Não digite letras nem simbolos",
						"Erro na posição X", JOptionPane.OK_CANCEL_OPTION);
				ok = false;
			}
		}
		/** ****** validacao dos dados introduzidos no campo "Deslocamento Y" ********
		 * 
		 */
		if (ok) {
			try {
				posicaoY = ((Double)this.spinner2.getValue()).doubleValue();
				if (posicaoY > 0 /*&& x < Maquina.posicaoYCv*/) {
					//System.out.println("deslocamento Y: " + posicaoY);
					ok = true;
				} else {
					ok = false;
					JOptionPane.showMessageDialog(null,
							"Digite um Número positivo para o deslocamento em Y",
							"Erro no deslocamento em Y", JOptionPane.OK_CANCEL_OPTION);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Digite um Número positivo para o deslocamento em Y"
						+ "\n               Não digite letras nem simbolos",
						"Erro no deslocamento em Y", JOptionPane.OK_CANCEL_OPTION);
				ok = false;
			}
		}
		/** ****** validacao dos dados introduzidos no campo "Posicao Z" ********
		 * 
		 */
		if (ok) {
			try {
				posicaoZ = ((Double)this.spinner7.getValue()).doubleValue();
				if (posicaoZ >= 0 /*&& x < Maquina.posicaoZCv*/) {
				//	System.out.println("deslocamento Z: " + posicaoZ);
					ok = true;
				} else {
					ok = false;
					JOptionPane.showMessageDialog(null,
							"Digite um Número positivo para a posição Z",
							"Erro no deslocamento Z", JOptionPane.OK_CANCEL_OPTION);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Digite um Número positivo para a posição Z"
						+ "\n               Não digite letras nem simbolos",
						"Erro no deslocamento Z?????", JOptionPane.OK_CANCEL_OPTION);
				ok = false;
			}
		}
		if (ok) {
			try {
				largura = ((Double)this.spinner6.getValue()).doubleValue();
				
				switch(face.verticeAtivado)
				{
				case 0:
					
					refLargura = posicaoY + largura;
					if (largura > 0 && refLargura < face.getLargura())
					{
						//System.out.println("largura: " + largura);
						ok = true;
					}
					else
					{
						JOptionPane.showMessageDialog(null,
								"A br.UFSC.GRIMA.feature não esta dentro dos limites da face"
								+ "\n               (revise a L2 ou a posição Y)",
								"Erro ao criar Cavidade", JOptionPane.OK_CANCEL_OPTION);
						ok = false;
					}
					break;
				case 1:
					refLargura = posicaoY + largura;
					if (largura >0 && refLargura < face.getComprimento())
					{
						//System.out.println("largura: " + largura);
						ok = true;
					}
					else
					{
						JOptionPane.showMessageDialog(null,
								"A br.UFSC.GRIMA.feature não esta dentro dos limites da face"
								+ "\n               (revise a L2 ou a posição Y)",
								"Erro ao criar Cavidade", JOptionPane.OK_CANCEL_OPTION);
						ok = false;
					}
					break;
				case 2:
					refLargura = posicaoY + largura;
					if (largura > 0 && refLargura < face.getLargura())
					{
						//System.out.println("largura: " + largura);
						ok = true;
						
					}
					else
					{
						JOptionPane.showMessageDialog(null,
								"A br.UFSC.GRIMA.feature não esta dentro dos limites da face"
								+ "\n               (revise a L2 ou a posição Y)",
								"Erro ao criar Cavidade", JOptionPane.OK_CANCEL_OPTION);
						ok = false;
					}
					break;
				case 3:
					refLargura = posicaoY + largura;
					if (largura >0 && refLargura < face.getComprimento())
					{
						//System.out.println("largura: " + largura);
						ok = true;
					}
					else
					{
						JOptionPane.showMessageDialog(null,
								"A br.UFSC.GRIMA.feature não esta dentro dos limites da face"
								+ "\n               (revise a L2 ou a posi��o Y)",
								"Erro ao criar Cavidade", JOptionPane.OK_CANCEL_OPTION);
						ok = false;
					}
					break;
				default:
					break;
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Digite um Número positivo para a largura da cavidade"
						+ "\n               Não digite letras nem simbolos",
						"Erro na largura", JOptionPane.OK_CANCEL_OPTION);
				ok = false;
			}
		}
		/** ****** validacao dos dados introduzidos no campo "comprimento" *******
		*/
		if (ok) {
			try {
				comprimento = ((Double)this.spinner5.getValue()).doubleValue();
				
				refComprimento = posicaoX + comprimento;
				switch (face.verticeAtivado)
				{
				case 0:
					if (comprimento > 0 && refComprimento < face.getComprimento())
					{
						//System.out.println("Comprimento: " + comprimento);
						ok = true;
					}
					else
					{
						JOptionPane.showMessageDialog(null,
								"A br.UFSC.GRIMA.feature n�o esta dentro dos limites da face" + 
								"\n               (revise L1 ou a posi��o X)",
								"Erro ao criar Cavidade", JOptionPane.OK_CANCEL_OPTION);
						ok = false;
					}
					break;
				case 1:
					if (comprimento > 0 && refComprimento < face.getLargura())
					{
						//System.out.println("Comprimento: " + comprimento);
						ok = true;
					}
					else
					{
						JOptionPane.showMessageDialog(null,
								"A br.UFSC.GRIMA.feature n�o esta dentro dos limites da face" + 
								"\n               (revise L1 ou a posi��o X)",
								"Erro ao criar Cavidade", JOptionPane.OK_CANCEL_OPTION);
						ok = false;
					}
					break;
				case 2:
					if (comprimento > 0 && refComprimento < face.getComprimento())
					{
						//System.out.println("Comprimento: " + comprimento);
						ok = true;
					}
					else
					{
						JOptionPane.showMessageDialog(null,
								"A br.UFSC.GRIMA.feature n�o esta dentro dos limites da face" + 
								"\n               (revise L1 ou a posi��o X)",
								"Erro ao criar Cavidade", JOptionPane.OK_CANCEL_OPTION);
						ok = false;
					}
					break;
				case 3:
					if (comprimento > 0 && refComprimento < face.getLargura())
					{
						//System.out.println("Comprimento: " + comprimento);
						ok = true;
					}
					else
					{
						JOptionPane.showMessageDialog(null,
								"A br.UFSC.GRIMA.feature n�o esta dentro dos limites da face" + 
								"\n               (revise L1 ou a posi��o X)",
								"Erro ao criar Cavidade", JOptionPane.OK_CANCEL_OPTION);
						ok = false;
					}
					break;
				default:
					break;
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Digite um N�mero positivo para o comprimento da cavidade"
						+ "\n               N�o digite letras nem simbolos",
						"Erro no comprimento", JOptionPane.OK_CANCEL_OPTION);
				ok = false;
			}
		}
		
		/** ****** validacao dos dados introduzidos no campo "profundidade" ****** 
		*/
		
		if (ok) {
			try {
				profundidade = ((Double)this.spinner8.getValue()).doubleValue();
				double refProfundidade = 0;
				refProfundidade = profundidade + posicaoZ;
				if (profundidade > 0 && refProfundidade < face.getProfundidadeMaxima())
				{
					ok = true;
				} else if(profundidade > 0 && this.checkBox1.isSelected() == true && refProfundidade == this.face.getProfundidadeMaxima())
				{
					ok = true;
				}
				else {
					
					JOptionPane.showMessageDialog(null,
							"Voc� n�o pode criar a br.UFSC.GRIMA.feature pois ultrapassa a profundidade maxima do bloco",
							"Erro na profundidade", JOptionPane.OK_CANCEL_OPTION);
					ok = false;
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Digite um N�mero positivo para a profundidade da cavidade"
						+ "\n               N�o digite letras nem simbolos",
						"Erro na profundidade", JOptionPane.OK_CANCEL_OPTION);
				ok = false;
			}
		}
		
		/** ****** validacao dos dados introduzidos no campo "Raio" ********
		 * 
		 */
		if (ok) {
			double valor = largura/2;
			if (comprimento < largura)
				valor = comprimento/2;
			
			try {
				raio = ((Double)this.spinner3.getValue()).doubleValue();
				if (raio >= raioMin && raio <= valor) {
					
					ok = true;
				} else {
					ok = false;
//					JOptionPane.showMessageDialog(null,
//							"O raio de concordância deve ser maior que 0 e  menor igual que " + valor,
//							"Erro no raio de concordancia", JOptionPane.OK_CANCEL_OPTION);
					
					JOptionPane.showMessageDialog(null,
							"The closed pocket radius should be between 0 and " + valor,
							"Error in closed pocket radius data", JOptionPane.OK_CANCEL_OPTION);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Digite um Número positivo para o raio concordante"
						+ "\n               Não digite letras nem simbolos",
						"Erro no raio de concordancia", JOptionPane.OK_CANCEL_OPTION);
				ok = false;
			}
		}
		/**
		 * validacao da TOLERANCIA
		 * 
		 */
		if (ok) {
			try {
				tolerancia = ((Double)this.spinner4.getValue()).doubleValue();
				if (tolerancia >= 0.0) 
				{
					ok = true;
				} else {
					ok = false;
					JOptionPane.showMessageDialog(null,
							"Digite um Número positivo para a tolerância",
							"Erro ", JOptionPane.OK_CANCEL_OPTION);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Digite um Numero positivo para a Tolerancia",
						"Erro na Tolerancia", JOptionPane.OK_OPTION);
			}
		}
		/**
		 * set da RUGOSIDADE, nao precisa validacao
		 * 
		 */
		if (ok) {
				rugosidade = ((Double) cavidadeRug.getValue()).doubleValue();
		}
		
		/** 
		 * FIM das validacoes
		 */
		if (ok)
		{
			double X = 0, Y = 0, Z = 0;
			
			double tmp;
			switch(this.face.verticeAtivado)
			{
				case 0:
					/**
					 * Nao faz nada
					 */
					X = posicaoX;
					Y = posicaoY;
					Z = posicaoZ;
					break;
					
				case 1:
					
					X = posicaoY;
					Y = face.getLargura() - posicaoX - comprimento;
					Z = posicaoZ;
					tmp = comprimento;
					comprimento = largura;
					largura = tmp;
					
					break;
					
				case 2:
					X = face.getComprimento() - comprimento - posicaoX;
					Y = face.getLargura() - largura - posicaoY;
					Z = posicaoZ;
					break;
					
				case 3:
					
					X = face.getComprimento() - posicaoY - largura;
					Y = posicaoX;
					Z = posicaoZ;
					tmp = comprimento;
					comprimento = largura;
					largura = tmp;
					
					break;
					
				default:
					break;
			}
			Cavidade novaCavidade = new Cavidade();
			novaCavidade.setPosicao(X, Y, Z);
			novaCavidade.setProfundidade(profundidade);
			novaCavidade.setRaio(raio);
			novaCavidade.setComprimento(comprimento);
			novaCavidade.setLargura(largura);
			novaCavidade.setNome(textField9.getText());
			novaCavidade.setTolerancia(tolerancia*0.001);
			novaCavidade.setRugosidade(rugosidade*0.001);
			novaCavidade.setPassante(this.checkBox1.isSelected());
			
			if(tolerancia<=Feature.LIMITE_RUGOSIDADE && rugosidade<=Feature.LIMITE_TOLERANCIA){
				novaCavidade.setAcabamento(true);
			}
			
			if (this.face.validateFeature(novaCavidade))
			{
				Point3d coordinates = null;
				ArrayList<Double> axis = null, refDirection = null;
				if (this.face.getTipo() == Face.XY)
				{
					coordinates = new Point3d(X + novaCavidade.getComprimento() / 2, Y + novaCavidade.getLargura() / 2, this.face.getProfundidadeMaxima() - Z);
					axis = new ArrayList<Double>();
					axis.add(0.0);
					axis.add(0.0);
					axis.add(1.0);
					
					refDirection = new ArrayList<Double>();
					refDirection.add(1.0);
					refDirection.add(0.0);
					refDirection.add(0.0);
				} else if (this.face.getTipo() == Face.XZ)
				{
					coordinates = new Point3d(X + novaCavidade.getComprimento() / 2, Z, Y + novaCavidade.getLargura() / 2);
					axis = new ArrayList<Double>();
					axis.add(0.0);
					axis.add(-1.0);
					axis.add(0.0);
					
					refDirection = new ArrayList<Double>();
					refDirection.add(1.0);
					refDirection.add(0.0);
					refDirection.add(0.0);
					
					
				} else if (this.face.getTipo() == Face.YX)
				{
					coordinates = new Point3d(X + novaCavidade.getComprimento() / 2, this.face.getLargura() - Y - novaCavidade.getLargura()/2, Z);
					axis = new ArrayList<Double>();
					axis.add(0.0);
					axis.add(0.0);
					axis.add(-1.0);
					
					refDirection = new ArrayList<Double>();
					refDirection.add(1.0);
					refDirection.add(0.0);
					refDirection.add(0.0);
					
				} else if (this.face.getTipo() == Face.YZ)
				{
					coordinates = new Point3d(this.face.getProfundidadeMaxima() - Z, Y + novaCavidade.getLargura() / 2, this.face.getComprimento() - X - novaCavidade.getComprimento()/2 );
					axis = new ArrayList<Double>();
					axis.add(1.0);
					axis.add(0.0);
					axis.add(0.0);
					
					refDirection = new ArrayList<Double>();
					refDirection.add(0.0);
					refDirection.add(0.0);
					refDirection.add(-1.0);
					
				} else if (this.face.getTipo() == Face.ZX)
				{
					coordinates = new Point3d(X + novaCavidade.getComprimento() / 2, this.face.getProfundidadeMaxima() - Z, this.face.getLargura() - Y - novaCavidade.getLargura() / 2);
					axis = new ArrayList<Double>();
					axis.add(0.0);
					axis.add(1.0);
					axis.add(0.0);
					
					refDirection = new ArrayList<Double>();
					refDirection.add(1.0);
					refDirection.add(0.0);
					refDirection.add(0.0);
					
				} else if (this.face.getTipo() == Face.ZY)
				{
					coordinates = new Point3d(Z, Y + novaCavidade.getLargura() / 2, X + novaCavidade.getComprimento() / 2 );
					axis = new ArrayList<Double>();
					axis.add(-1.0);
					axis.add(0.0);
					axis.add(0.0);
					
					refDirection = new ArrayList<Double>();
					refDirection.add(0.0);
					refDirection.add(0.0);
					refDirection.add(1.0);
					
				}
				Axis2Placement3D position = new Axis2Placement3D(coordinates, axis, refDirection);
				position.setName(novaCavidade.getNome() + " placement");
				novaCavidade.setPosition(position);	
				
				this.face.addFeature(novaCavidade);
				this.parent.desenhador.repaint();
				this.parent.atualizarArvore();
				//face.imprimeDados(novaCavidade);
				this.parent.setEnabled(true);
				this.parent.textArea1.setText(this.parent.textArea1.getText() + "\n" +  "Cavidade: " +novaCavidade.getNome() + " adicionada com sucesso!");
				
				System.out.println("mae = " + novaCavidade.getFeaturePrecedente());
				dispose();
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Erro na criacao da Cavidade", "Feature invalida", JOptionPane.OK_CANCEL_OPTION);
			}
		}
	}
	private void cancel()
	{	
		this.parent.setEnabled(true);
		dispose();
	}
}