package br.UFSC.GRIMA.cad;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;
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
			try{
				desenhador.setFacePrincipal(index, 0);
				faceComboBox.setSelectedIndex(0);
				drawPoints(index, 0);
			}
			finally{}
		}
		if (e.getSource() == this.faceComboBox){
			int index = faceComboBox.getSelectedIndex()+setupComboBox.getSelectedIndex();
			if (index > 5) index -= 5;
			try{
				desenhador.setFacePrincipal(index, 0);
				drawPoints(setupComboBox.getSelectedIndex(), index);
			}
			finally{}
		}
	}
	
/**Passando os dados para a tabela:**/
	public void fillTable(int indexSetup, int indexFace){
		pointsTable.removeAll();
		for (int i=0; i<this.gerador.setupsArray.get(indexSetup).get(indexFace).size(); i++){
			this.pointsTable.setValueAt(this.gerador.setupsArray.get(indexSetup).get(indexFace).get(i).x, i, 0);
			this.pointsTable.setValueAt(this.gerador.setupsArray.get(indexSetup).get(indexFace).get(i).y, i, 1);
			this.pointsTable.setValueAt(this.gerador.setupsArray.get(indexSetup).get(indexFace).get(i).z, i, 2);
		}
	}
	
/**Gerando seletor de SETUPS:**/
	public void fillSetupComboBox(){
		setupComboBox.removeAllItems();
		if (!gerador.setupsArray.contains(null)){
			for (int i=0; i<this.gerador.setupsArray.size(); i++){
				setupComboBox.addItem("Setup " + (i+1));	
			}
		}
	}
	
/**Gerando seletor de FACES:**/
	public void fillFacesComboBox(int setupIndex){
		
		faceComboBox.removeAll();
		for (int i=0; i<gerador.setupsArray.get(setupIndex).size(); i++){
			faceComboBox.addItem(i);
		}
	}
	
/**Desenhando os pontos:**/
	public void drawPoints(int indexSetup, int indexFace){
		if (gerador.setupsArray.get(indexSetup).contains(null) && indexFace == 0){
			JOptionPane.showMessageDialog(this, "A peça pode ser apoiada na mesa", "Aviso", JOptionPane.INFORMATION_MESSAGE);
		}
		else{
			desenhador.addClampPoints(gerador.setupsArray.get(indexSetup).get(indexFace), diametro/2);
			fillTable(indexSetup, indexFace);
		}
		this.desenhador.revalidate();
		
		
		
/**Função principal**/
	}
	private void gneratePoints() {
		setupComboBox.removeItemListener(this);
		faceComboBox.removeItemListener(this);
		diametro = (int) (((Double)this.diameterSpinner.getValue()).doubleValue());
		gerador = new PointsGenerator(this.projeto, this.diametro);	
		desenhador.alterarProjeto(this.projeto);
		drawingScrollPane.setViewportView(desenhador);
		try{
			fillSetupComboBox();	
			fillTable(0,0);
			drawPoints(0,0);
			setupComboBox.setSelectedIndex(0);
			faceComboBox.setSelectedIndex(0);
		}
		catch (Exception e){
			JOptionPane.showMessageDialog(this, "Não há features na peça", "Erro", JOptionPane.ERROR_MESSAGE);
		}
		finally{
			this.setupComboBox.addItemListener(this);
			this.faceComboBox.addItemListener(this);
		}
	}
	
}