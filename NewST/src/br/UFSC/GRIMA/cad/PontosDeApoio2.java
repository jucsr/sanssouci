package br.UFSC.GRIMA.cad;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import br.UFSC.GRIMA.cad.visual.PontosDeApoioFrame2;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class PontosDeApoio2 extends PontosDeApoioFrame2 implements ActionListener, ItemListener{
	//Variáveis Auxiliares:
	private PointsGenerator gerador;
	private Projeto projeto;
	public DesenhadorDeFaces desenhador;
	private int diametro;
	//Função Principal:
	public PontosDeApoio2(Frame owner, Projeto projeto)
	{
		super(owner);
		this.projeto = projeto;
		this.desenhador = new DesenhadorDeFaces(this.projeto);
		this.autoGenButton.addActionListener(this);
		this.setupComboBox.addItemListener(this);
		this.faceComboBox.addItemListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o == this.autoGenButton)
		{
			this.gneratePoints();
		}
	}
	@Override
	public void itemStateChanged(ItemEvent e){
		if (e.getSource() == this.setupComboBox){
			int index = setupComboBox.getSelectedIndex();
		}
		if (e.getSource() == this.faceComboBox){
			int index = faceComboBox.getSelectedIndex();
			desenhador.setFacePrincipal(index, 0);
			desenhador.addClampPoints(gerador.setupsArray.get(0).get(index), diametro/2);
		}
	}
	public void fillTable(){
		for (int i=0; i<this.gerador.facesArray.get(0).size(); i++){
			this.pointsTable.setValueAt(this.gerador.facesArray.get(0).get(i).x, i, 0);
			this.pointsTable.setValueAt(this.gerador.facesArray.get(0).get(i).y, i, 1);
			this.pointsTable.setValueAt(this.gerador.facesArray.get(0).get(i).z, i, 2);
		}
	}
	public void fillSetupComboBox(){
		this.setupComboBox.removeAllItems();
		for (int i=0; i<this.gerador.setupsArray.size(); i++){
			setupComboBox.addItem("Setup " + (i+1));	
		}
	}
	private void gneratePoints() {
	
		System.out.println("gerar pontos");
		diametro = (int) (((Double)this.diameterSpinner.getValue()).doubleValue());
		gerador = new PointsGenerator(this.projeto, diametro);
		

	/**Gerando seletor de setups:**/
		fillSetupComboBox();
		
	/**Passando os dados para a tabela:**/
		fillTable();
		
	/**Desenhando os pontos:**/
		desenhador.alterarProjeto(this.projeto);
		drawingScrollPane.setViewportView(desenhador);
		desenhador.addClampPoints(gerador.setupsArray.get(0).get(0), diametro/2);
		this.desenhador.revalidate();
		System.out.println("Imprindo Vetor do SETUPS:");
		for (int i=0; i<gerador.setupsArray.size(); i++){
			System.out.printf("Setup %d:\t", i);
			System.out.println(gerador.setupsArray.get(i));
		}
		System.out.println("Imprindo Vetor do FACES:");
		for (int i=0; i<gerador.facesArray.size(); i++){
			System.out.printf("Face %d:\t", i);
			System.out.println(gerador.facesArray.get(i));
		}
	}
	
}