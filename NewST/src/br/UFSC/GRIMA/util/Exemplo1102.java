package br.UFSC.GRIMA.util;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class Exemplo1102 extends JFrame implements ActionListener
{
	JLabel L1;
	JButton B2,B3;
	JComboBox TA1;
	JPanel P1;
	String nome[] = { "1",
			"2", "3", "4" };
	

	public static void main(String[] args)
	{
		JFrame Janela = new Exemplo1102();
		WindowListener x = new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		};
		Janela.addWindowListener(x);
		Janela.show();
		
	}

	Exemplo1102()
	{
		setTitle("Abrir projeto pelo servidor");
		setResizable(false);
		getContentPane().setLayout (new FlowLayout());
		L1 = new JLabel("Projetos:");
		L1.setForeground(Color.black);		
		B2 = new JButton("Abrir");
		B3 = new JButton("Cancelar");		
		B2.addActionListener(this);
		B3.addActionListener(this);
		P1 = new JPanel();
		P1.setLayout (new FlowLayout());
		TA1 = new JComboBox(nome);
		TA1.setEnabled(true);
		getContentPane().add(L1);
		getContentPane().add(TA1);
		//getContentPane().add(B1);
		getContentPane().add(B2);
		getContentPane().add(B3);
		getContentPane().add(P1);
		pack();
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		String nome_do_arquivo;
		if (e.getSource() == B3) //cancelar
		{
		dispose();
		}
		
		if (e.getSource() == B2) //abrir
		{
			System.out.println("" +TA1.getSelectedItem());
			
		}
	}
} 