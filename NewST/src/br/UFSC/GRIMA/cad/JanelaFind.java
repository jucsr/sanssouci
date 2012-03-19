package br.UFSC.GRIMA.cad;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextPane;
import javax.swing.event.ListDataListener;
import javax.swing.text.Caret;

import br.UFSC.GRIMA.cad.visual.FindFrame;

/**
 * 
 * @author Jc
 *
 */
public class JanelaFind extends FindFrame implements ActionListener, ItemListener
{
	private JTextPane textPane;
	private String textoBuscado;
	private String textoTotal;
	private String [] buscasAnteriores = new String[10];
	private Vector <String> ba = new Vector<String>();
	public JanelaFind(Frame owner, JTextPane textPane) 
	{
		super(owner);
		this.textPane = textPane;
		this.textoTotal = this.textPane.getText();
		
		String textoTotalTmp = "";
		
		int beginIndex = 1;
		int endIndex = 10;
		int lastIndex = textoTotal.length() - 1;
		
		while( textoTotal.indexOf("WORKPLAN") != 0   ){
			
		endIndex = endIndex + 10;
		
		System.out.println("End Index = " + endIndex );
		
		textoTotalTmp = textoTotalTmp + textoTotal.substring(beginIndex, endIndex);
		
		System.out.println("Last " + lastIndex);
		System.out.println("end " + endIndex);
		
		textoTotal = textoTotal.substring(endIndex, lastIndex);
		
		}
		
		textoTotalTmp = textoTotalTmp +  textoTotal.substring(beginIndex, lastIndex);
		
		this.textoTotal = textoTotalTmp;
		
		this.comboBox1.addItemListener(this);
		this.comboBox2.addItemListener(this);
		
		System.out.println("tamanho " + this.comboBox1.getItemCount());
		System.out.println("tttt " + this.comboBox1.getModel().getElementAt(0));
		for (int i = 0; i < this.comboBox1.getItemCount(); i++)
		{
			//this.comboBox1.addItem(this.buscasAnteriores[i]);
			System.out.println("passou");
		}
		if(this.buscasAnteriores.length > 0)
		{
			this.comboBox1.setModel(new DefaultComboBoxModel(this.ba));			
		}else if(this.buscasAnteriores.length == 0)
		{
			this.buscasAnteriores[0] = this.textPane.getSelectedText();
			this.comboBox1.setModel(new DefaultComboBoxModel(this.buscasAnteriores));			
			
		}
		this.comboBox2.setModel(new DefaultComboBoxModel());
		this.find.addActionListener(this);
		this.cancelButton.addActionListener(this);
		System.out.println("texto " + textoBuscado);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object o = e.getSource();
		if(o == this.find)
		{
			this.buscar();
		} else if(o == this.cancelButton)
		{
			this.dispose();
		}
	}
	@Override
	public void itemStateChanged(ItemEvent arg0) 
	{
		
	}

	private void buscar()
	{
		
		System.out.println("Texto Total = " + textoTotal );
		
		this.textoBuscado = this.comboBox1.getSelectedItem().toString();
		int posicion = textoTotal.indexOf(textoBuscado);
		Caret selecao = textPane.getCaret();
//		if (selecao.getDot() == selecao.getMark())
//		{
//			String selecionado = textPane.getSelectedText();
			
		   // No hay nada seleccionado
//			System.out.println("mmmmmmmm");
//		}
//		else
//		{
//			int posicionInicial = 0;
//			if (selecao.getDot() != selecao.getMark())
//			{
			   // Hay algo seleccionado
//			   posicionInicial = selecao.getDot();
			   posicion = textoTotal.indexOf(textoBuscado) - 1;
			   this.textPane.setCaretPosition(posicion);
			   this.textPane.moveCaretPosition(posicion + textoBuscado.length());
			   System.out.println("Texto Buscado: " + textoBuscado );
			   System.out.println("Posicion : " + posicion);
			   System.out.println("Length: " + textoBuscado.length());
			   System.out.println("entrou ");
//			} 
		}
	}

	
