package br.UFSC.GRIMA.cad;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JOptionPane;

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
//			fillFacesComboBox(index);
		}
		if (e.getSource() == this.faceComboBox){
			int index = faceComboBox.getSelectedIndex();
			desenhador.setFacePrincipal(index, 0);
			try{
					desenhador.addClampPoints(gerador.setupsArray.get(setupComboBox.getSelectedIndex()).get(index), diametro/2);
					fillTable(setupComboBox.getSelectedIndex(), index);
			}
			catch(Exception ex){
				JOptionPane.showMessageDialog(this, "A peça pode ser apoiada na mesa", "!", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	public void fillTable(int indexSetup, int indexFace){
		for (int i=0; i<this.gerador.setupsArray.get(indexSetup).get(indexFace).size(); i++){
			this.pointsTable.setValueAt(this.gerador.setupsArray.get(indexSetup).get(indexFace).get(i).x, i, 0);
			this.pointsTable.setValueAt(this.gerador.setupsArray.get(indexSetup).get(indexFace).get(i).y, i, 1);
			this.pointsTable.setValueAt(this.gerador.setupsArray.get(indexSetup).get(indexFace).get(i).z, i, 2);
		}
	}
	public void fillSetupComboBox(){
		setupComboBox.removeAllItems();
		if (!gerador.setupsArray.isEmpty()){
			for (int i=0; i<this.gerador.setupsArray.size(); i++){
				setupComboBox.addItem("Setup " + (i+1));	
			}
		}
	}
	public void fillFacesComboBox(int setupIndex){
		faceComboBox.removeAll();
		for (int i=0; i<gerador.setupsArray.get(setupIndex).size(); i++){
			faceComboBox.addItem(i);
		}
	}
	private void gneratePoints() {
	
		System.out.println("gerar pontos");
		diametro = (int) (((Double)this.diameterSpinner.getValue()).doubleValue());
		gerador = new PointsGenerator(this.projeto, diametro);
//		System.out.println("Tamanho do vetor setup 0:");
//		System.out.println(gerador.setupsArray.get(0).size());
//		for (int i=0; i<gerador.setupsArray.get(0).size(); i++){
//			System.out.println(gerador.setupsArray.get(0).get(i));
//		}	

	/**Gerando seletor de setups:**/
		fillSetupComboBox();
		fillFacesComboBox(0);
		
	/**Passando os dados para a tabela:**/
		fillTable(0,0);
		
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