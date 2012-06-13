package br.UFSC.GRIMA.cad;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import br.UFSC.GRIMA.cad.visual.PontosDeApoioFrame2;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class PontosDeApoio2 extends PontosDeApoioFrame2 implements ActionListener, ItemListener, PropertyChangeListener{
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
		this.pointsTable.addPropertyChangeListener(this);
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
			if (gerador.setupsArray.size()>0){
				int index = setupComboBox.getSelectedIndex();
				desenhador.setFacePrincipal(index, 0);
				faceComboBox.setSelectedIndex(0);
				drawPoints(index, 0);
			}
		}
		if (e.getSource() == this.faceComboBox){
			if (gerador.setupsArray.size()>0){
				int index = faceComboBox.getSelectedIndex()+setupComboBox.getSelectedIndex();
				if (index > 5) index -= 5;
				desenhador.setFacePrincipal(index, 0);
				drawPoints(setupComboBox.getSelectedIndex(), index);
			}
		}
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
	}
	
/**Passando os dados para a tabela:**/
	public void fillTable(int indexSetup, int indexFace){
		DefaultTableModel model = (DefaultTableModel) pointsTable.getModel();
		model.setRowCount(0);
		model.setRowCount(8);
		pointsTable.setModel(model);
		try{
			for (int i=0; i<this.gerador.setupsArray.get(indexSetup).get(indexFace).size(); i++){
				this.pointsTable.setValueAt(this.gerador.setupsArray.get(indexSetup).get(indexFace).get(i).x, i, 0);
				this.pointsTable.setValueAt(this.gerador.setupsArray.get(indexSetup).get(indexFace).get(i).y, i, 1);
				this.pointsTable.setValueAt(this.gerador.setupsArray.get(indexSetup).get(indexFace).get(i).z, i, 2);
			}
		}
		catch(Exception ex){
			//não escreve nada na tabela
		}
	}
/**Gerando seletor de SETUPS:**/
	public void fillSetupComboBox(){
//		setupComboBox.setMaximumRowCount(0);
//		setupComboBox.setMaximumRowCount(this.gerador.setupsArray.size());
		setupComboBox.removeAllItems();
		for (int i=0; i<this.gerador.setupsArray.size(); i++){
			setupComboBox.addItem("Setup " + (i+1));	
		}
	}
/**Gerando seletor de FACES:**/
	public void fillFacesComboBox(int setupIndex){
		faceComboBox.setModel(new DefaultComboBoxModel(new String[] {
				"XY",
				"ZY",
				"ZX",
				"YX",
				"YZ",
				"XZ"
			}));
	}
/**Desenhando os pontos:**/
	public void drawPoints(int indexSetup, int indexFace){
		try{
			if (gerador.setupsArray.size() > 0 && gerador.setupsArray.get(indexSetup).contains(null) && indexFace == 0){
				JOptionPane.showMessageDialog(this, "A peça pode ser apoiada na mesa", "!", JOptionPane.INFORMATION_MESSAGE);
			}
			else{
//				if (setupComboBox.getSelectedIndex()==0)	desenhador.dFeaturesSecundarias = false;
//				else desenhador.dFeaturesSecundarias = true;
				desenhador.addClampPoints(gerador.setupsArray.get(indexSetup).get(indexFace), diametro/2);
				fillTable(indexSetup, indexFace);
			}
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
		finally{
			this.desenhador.repaint();
			this.desenhador.revalidate();
		}
	}
	private void gneratePoints() {
		diametro = (int) (((Double)this.diameterSpinner.getValue()).doubleValue());
		gerador = new PointsGenerator(this.projeto, this.diametro);	
		fillSetupComboBox();
		setupComboBox.setSelectedIndex(0);
		faceComboBox.setSelectedIndex(0);
		desenhador.alterarProjeto(this.projeto);
		drawingScrollPane.setViewportView(desenhador);
		if (gerador.setupsArray.get(0).contains(null)) System.out.println("A base do primeiro setup está vazio");
		
//		fillFacesComboBox(0);
		fillTable(0,0);
//		drawPoints(0,0);
	}

	
}