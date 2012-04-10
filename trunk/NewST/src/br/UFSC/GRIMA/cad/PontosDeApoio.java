package br.UFSC.GRIMA.cad;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.cad.visual.PontosDeApoioFrame;
import br.UFSC.GRIMA.entidades.Rectangle3D;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.exceptions.ProjetoInvalidoException;
import br.UFSC.GRIMA.util.VValidator;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class PontosDeApoio extends PontosDeApoioFrame implements ActionListener {

	int numLinhas;
	Object[] linhaNula = {"", "", ""};
	double panelWidth;
	double panelHeight;
	double pieceWidth;
	double pieceHeight;
	
	
	int raioCP = 5;
	
	Projeto projAux = null;
	
	Rectangle3D peca;
	
	public VValidator Validador;
	
	
	public DesenhadorDeFaces desenhador;
	
	ArrayList<ArrayList<Point3d>> arrayAA = new ArrayList<ArrayList<Point3d>>();
	
	ArrayList<Point3d> arrayApoios = new ArrayList<Point3d>(); //Array de Apoios da face inferior somente
	
	
	
	public PontosDeApoio(JFrame parent, Projeto projeto)
	{
		super(parent);
		
		this.projAux = projeto;
		
		ArrayList<Feature> arrayFeat = new ArrayList<Feature>();
		
		this.buttonAutomatic.addActionListener(this);
		this.buttonManual.addActionListener(this);
		this.comboBox1.addActionListener(this);
		this.raioCPField.setEditable(true);
		this.raioCPField.addActionListener(this);
		this.buttonCancel.addActionListener(this);
		this.buttonConfirm.addActionListener(this);

//		panelWidth = panel2.getWidth();
//		panelHeight = panel2.getHeight();
	
		pieceWidth = projeto.getBloco().getComprimento();
		pieceHeight = projeto.getBloco().getLargura();
			
		JScrollPane JTest = new JScrollPane();
		
		this.desenhador = new DesenhadorDeFaces(this.projAux);
		this.desenhador.alterarProjeto(this.projAux);
				
		scrollPane1.setViewportView(desenhador);
		
		this.desenhador.revalidate();
		
		
		//this.scrollPane1.add(JTest);
//		dPanel = new DrawingPanel(panelWidth, panelHeight, pieceWidth, 
//				pieceHeight, arrayApoios, raioCP, projAux);
//		
//		this.panel2.add(dPanel);
		
		peca = new Rectangle3D(pieceWidth, pieceHeight, projeto.getBloco().getProfundidade());
		
		
		//So para a face XY Neste caso
		//Vector workinstepsTmp = (Vector) Workingsteps.get(0);
		
	
		Face faceXY =  (Face)projeto.getBloco().faces.get(0);
		Vector<Feature> vecFeatures = faceXY.features;
		
		System.out.println("Size vecFeat "+vecFeatures.size());
		
		for(int i = 0; i < vecFeatures.size() ; i++)
		{
					
//			Workingstep wsAux = (Workingstep)workinstepsTmp.elementAt(i);
			Feature featAux = vecFeatures.get(i);
					
			arrayFeat.add(featAux);
			
		}
		
		
		Validador = new VValidator(peca, arrayFeat, raioCP);
		
	}
	
	
	DrawingPanel dPanel; 

	@Override
	public void actionPerformed(ActionEvent arg0) {
				

//		JOptionPane.showMessageDialog(null,	"texto", "titulo", JOptionPane.ERROR_MESSAGE);	
		
		
		if(!(raioCPField.getText().isEmpty()))
		{
			if(Double.parseDouble(raioCPField.getText()) > 0)
			{
				raioCP = (int)Double.parseDouble(raioCPField.getText());
			}
			
		}
		
		if(arg0.getSource().equals(buttonCancel))
		{
			this.dispose();
//			this.setVisible(false);
		}
		
		if(arg0.getSource().equals(buttonConfirm))
		{
			((Face)projAux.getBloco().faces.get(0)).setPontosDeApoio(arrayApoios);
//			this.setVisible(false);
			this.dispose();
			
		}
				
		//if (arg0.getSource().equals(buttonAutomatic) && buttonAutomatic.isSelected())
		if (arg0.getSource().equals(buttonAutomatic) && buttonAutomatic.isSelected())
		{
			buttonManual.setSelected(false);
			this.buttonCalc.removeActionListener(this);
			
			if(comboBox1.getSelectedIndex() == 0)
			{
				DefaultTableModel modelo = (DefaultTableModel)this.pointsTable.getModel();
				
				arrayApoios = new ArrayList<Point3d>();
				
				modelo.setRowCount(0);
				
				try {
					System.out.println("entrou aquiii");
						
					arrayAA = Validador.getPoints(3, 0, 0);
				
					arrayApoios = arrayAA.get(0);
				} 
				catch (ProjetoInvalidoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("Array Arrays" + arrayAA);
				System.out.println("Apoio1" + arrayApoios.get(0).getY());
				System.out.println("");
				
				
				Object[] linha0 = {arrayApoios.get(0).getX(),arrayApoios.get(0).getY(),arrayApoios.get(0).getZ()};
				modelo.addRow(linha0);
				
				Object[] linha1 = {arrayApoios.get(1).getX(),arrayApoios.get(1).getY(),arrayApoios.get(1).getZ()};
				modelo.addRow(linha1);
				
				Object[] linha2 = {arrayApoios.get(2).getX(),arrayApoios.get(2).getY(),arrayApoios.get(2).getZ()};
				modelo.addRow(linha2);
							
				numLinhas = 3;
			}
			
			if(comboBox1.getSelectedIndex() == 1)
			{
				DefaultTableModel modelo = (DefaultTableModel)this.pointsTable.getModel();
								
				arrayApoios = new ArrayList<Point3d>();
				
				modelo.setRowCount(0);
				
				try {
					arrayAA = Validador.getPoints(4, 0, 0);
					arrayApoios = arrayAA.get(0);
				} 
				catch (ProjetoInvalidoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Object[] linha0 = {arrayApoios.get(0).getX(),arrayApoios.get(0).getY(),arrayApoios.get(0).getZ()};
				modelo.addRow(linha0);
				
				Object[] linha1 = {arrayApoios.get(1).getX(),arrayApoios.get(1).getY(),arrayApoios.get(1).getZ()};
				modelo.addRow(linha1);
				
				Object[] linha2 = {arrayApoios.get(2).getX(),arrayApoios.get(2).getY(),arrayApoios.get(2).getZ()};
				modelo.addRow(linha2);
				
				Object[] linha3 = {arrayApoios.get(3).getX(),arrayApoios.get(3).getY(),arrayApoios.get(3).getZ()};
				modelo.addRow(linha3);
				
				numLinhas = 4;
			}
			
			if(comboBox1.getSelectedIndex() == 2)
			{
				DefaultTableModel modelo = (DefaultTableModel)this.pointsTable.getModel();
							
				arrayApoios = new ArrayList<Point3d>();
				
				modelo.setRowCount(0);
				
				try {
					arrayAA = Validador.getPoints(5, 0, 0);
					arrayApoios = arrayAA.get(0);
				} 
				catch (ProjetoInvalidoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				Object[] linha0 = {arrayApoios.get(0).getX(),arrayApoios.get(0).getY(),arrayApoios.get(0).getZ()};
				modelo.addRow(linha0);
				
				Object[] linha1 = {arrayApoios.get(1).getX(),arrayApoios.get(1).getY(),arrayApoios.get(1).getZ()};
				modelo.addRow(linha1);
				
				Object[] linha2 = {arrayApoios.get(2).getX(),arrayApoios.get(2).getY(),arrayApoios.get(2).getZ()};
				modelo.addRow(linha2);
				
				Object[] linha3 = {arrayApoios.get(3).getX(),arrayApoios.get(3).getY(),arrayApoios.get(3).getZ()};
				modelo.addRow(linha3);
				
				Object[] linha4 = {arrayApoios.get(4).getX(),arrayApoios.get(4).getY(),arrayApoios.get(4).getZ()};
				modelo.addRow(linha4);
				
				numLinhas = 5;
			}
			
			if(comboBox1.getSelectedIndex() == 3)
			{
				DefaultTableModel modelo = (DefaultTableModel)this.pointsTable.getModel();
							
				arrayApoios = new ArrayList<Point3d>();
				
				modelo.setRowCount(0);
				
				try {
					arrayAA = Validador.getPoints(6, 0, 0);
					arrayApoios = arrayAA.get(0);
				} 
				catch (ProjetoInvalidoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Object[] linha0 = {arrayApoios.get(0).getX(),arrayApoios.get(0).getY(),arrayApoios.get(0).getZ()};
				modelo.addRow(linha0);
				
				Object[] linha1 = {arrayApoios.get(1).getX(),arrayApoios.get(1).getY(),arrayApoios.get(1).getZ()};
				modelo.addRow(linha1);
				
				Object[] linha2 = {arrayApoios.get(2).getX(),arrayApoios.get(2).getY(),arrayApoios.get(2).getZ()};
				modelo.addRow(linha2);
				
				Object[] linha3 = {arrayApoios.get(3).getX(),arrayApoios.get(3).getY(),arrayApoios.get(3).getZ()};
				modelo.addRow(linha3);
				
				Object[] linha4 = {arrayApoios.get(4).getX(),arrayApoios.get(4).getY(),arrayApoios.get(4).getZ()};
				modelo.addRow(linha4);
				
				Object[] linha5 = {arrayApoios.get(5).getX(),arrayApoios.get(5).getY(),arrayApoios.get(5).getZ()};
				modelo.addRow(linha5);
				
				numLinhas = 6;
			}
			
			if(comboBox1.getSelectedIndex() == 4)
			{
				DefaultTableModel modelo = (DefaultTableModel)this.pointsTable.getModel();
				Object[] linha = {7,2,3,4};
				
				arrayApoios = new ArrayList<Point3d>();
				
				modelo.setRowCount(0);
				
				try {
					arrayAA = Validador.getPoints(7, 0, 0);
					arrayApoios = arrayAA.get(0);
				} 
				catch (ProjetoInvalidoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				Object[] linha0 = {arrayApoios.get(0).getX(),arrayApoios.get(0).getY(),arrayApoios.get(0).getZ()};
				modelo.addRow(linha0);
				
				Object[] linha1 = {arrayApoios.get(1).getX(),arrayApoios.get(1).getY(),arrayApoios.get(1).getZ()};
				modelo.addRow(linha1);
				
				Object[] linha2 = {arrayApoios.get(2).getX(),arrayApoios.get(2).getY(),arrayApoios.get(2).getZ()};
				modelo.addRow(linha2);
				
				Object[] linha3 = {arrayApoios.get(3).getX(),arrayApoios.get(3).getY(),arrayApoios.get(3).getZ()};
				modelo.addRow(linha3);
				
				Object[] linha4 = {arrayApoios.get(4).getX(),arrayApoios.get(4).getY(),arrayApoios.get(4).getZ()};
				modelo.addRow(linha4);
				
				Object[] linha5 = {arrayApoios.get(5).getX(),arrayApoios.get(5).getY(),arrayApoios.get(5).getZ()};
				modelo.addRow(linha5);
				
				Object[] linha6 = {arrayApoios.get(6).getX(),arrayApoios.get(6).getY(),arrayApoios.get(6).getZ()};
				modelo.addRow(linha6);
									
				numLinhas = 7;
			}
			
			
			
			
			
			
			
			
			this.addClampPointButton.removeActionListener(this);
			this.removeClampPointButton.removeActionListener(this);
			//this.pointsTable.setEditable(false);
			
		}
		
		if (arg0.getSource().equals(buttonManual) && buttonManual.isSelected())
		{
//			buttonAutomatic.setSelected(false);
			
			this.addClampPointButton.addActionListener(this);
			this.removeClampPointButton.addActionListener(this);
			this.buttonCalc.addActionListener(this);
							
		}
		
		if (arg0.getSource().equals(buttonManual) && !(buttonManual.isSelected()))
		{
//			buttonAutomatic.setSelected(false);
			
			this.addClampPointButton.removeActionListener(this);
			this.removeClampPointButton.removeActionListener(this);
			
							
		}
		
		
		if (arg0.getSource().equals(addClampPointButton))
		{
			
			comboBox1.getAccessibleContext();
			DefaultTableModel modelo = (DefaultTableModel)this.pointsTable.getModel();
			
			if(numLinhas == 0)
			{
				
				modelo.addRow(linhaNula);
				modelo.addRow(linhaNula);
				modelo.addRow(linhaNula);
				
				numLinhas = 3;
				
				
				
			}
			
			else if(numLinhas < 7)
			{
	
				modelo.addRow(linhaNula);
				
				numLinhas++;
			}
		
		}
		
		if (arg0.getSource().equals(removeClampPointButton))
		{
			
			comboBox1.getAccessibleContext();
			DefaultTableModel modelo = (DefaultTableModel)this.pointsTable.getModel();
			
		    // Obtem a linha selecionada na tabela e chama o m�todo  
		    // para excluir a linha  
		    int linhaSelecionada = this.pointsTable.getSelectedRow();  
		      
		    // Verificamos se existe realmente alguma linha selecionada  
		    if( linhaSelecionada < 0 ){  
		        return;  
		    }else{  
		        // Remove a linha do modelo  
		    	 modelo.removeRow(linhaSelecionada);   
		    }  
		    
		    
		    numLinhas--;
		    
		    if(numLinhas < 3)
		    {
				modelo.addRow(linhaNula);
				numLinhas++;
		    	
		    }
		    
					
		}
		
		/*
		for(int i = 0; i < ((DefaultTableModel)this.pointsTable.getModel()).getRowCount(); i++)
		{
			
			DefaultTableModel modelo = (DefaultTableModel)this.pointsTable.getModel();
			
			modelo.getValueAt(i,0);
			//Point3d pointAux = new Point3d(()modelo.getValueAt(i,0), modelo.getValueAt(i,2), modelo.getValueAt(i,3));
			
			
			arrayApoios.add(pointAux);
		}
		
	*/
		
		if(buttonManual.isSelected())
		{
			System.out.println("Manual selecionado");
			arrayApoios = new ArrayList<Point3d>();
			
			for(int i = 0; i < ((DefaultTableModel)this.pointsTable.getModel()).getRowCount(); i++)
			{
				
				
				if(pointsTable.getValueAt(i, 0).toString().isEmpty())
				{
					System.out.println("Vazio");
				}
				
				if(!pointsTable.getValueAt(i, 0).toString().isEmpty())
				{
					System.out.println("Nao vazio");
				}
				
				if(!(pointsTable.getValueAt(i, 0).toString().isEmpty()) && !(pointsTable.getValueAt(i, 1).toString().isEmpty()) && !(pointsTable.getValueAt(i, 2).toString().isEmpty()))
				{
					System.out.println("Entrou aqui???? n:" +i);
					
					System.out.println("O que devia ser" + Double.parseDouble(pointsTable.getValueAt(i, 0).toString()));
					
					Point3d pontoAux = new Point3d(Double.parseDouble(pointsTable.getValueAt(i, 0).toString()),Double.parseDouble(pointsTable.getValueAt(i, 1).toString()),Double.parseDouble(pointsTable.getValueAt(i, 2).toString()));
					
					System.out.println("Ponto n"+i+" " + pontoAux);
					
					arrayApoios.add(pontoAux);
					
					System.out.println("Size " + arrayApoios.size());
				}
				
			}
			
		}
		
		System.out.println("Size antes de criar dPanel" + arrayApoios.size());
		
		System.out.println(""+arrayApoios);
		
		
		this.desenhador.addClampPoints(arrayApoios, raioCP);
//		dPanel = new DrawingPanel(panelWidth, panelHeight, pieceWidth, 
//				pieceHeight, arrayApoios, raioCP, projAux);
//		
//		panel2.removeAll();	
//		
//		this.panel2.add(dPanel);
//		
//		
//		panel2.repaint();
//		
//		this.panel2.validate();
//
//
//		panel2.repaint();
		
	}
	
	
	
}


