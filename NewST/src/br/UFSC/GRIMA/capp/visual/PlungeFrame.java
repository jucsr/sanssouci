package br.UFSC.GRIMA.capp.visual;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import br.UFSC.GRIMA.util.Path;

//<acesso>		<tipo>		  <nome>		<subclasse de...> 	<nome do "pai">
   public 		class 		PlungeFrame 		extends 			JFrame implements ActionListener
{
	private JPanel panel;
	
	public JButton b = new JButton("botao"); //declara botao b
	public JButton b2 = new JButton("botao2");
	public JRadioButton rb = new JRadioButton("bolinha");
	public ArrayList<Path> paths;

	public PlungeFrame(ArrayList<Path> paths)
	{
		this.paths = paths;
		System.out.println("get 0" + this.paths.get(0).getInitialPoint());
		this.setTitle("Plunge");
		this.setSize(200, 160);
		this.setVisible(true);
		//this.setSize(60, 40);
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(b, BorderLayout.SOUTH); //adiciona o botao na 'camada' contentpane
		this.getContentPane().add(b2, BorderLayout.NORTH);
		this.getContentPane().add(rb, BorderLayout.CENTER);
		b.addActionListener(this); // acrescentando ouvidor pro botao b
		b2.addActionListener(this);
		rb.addActionListener(this);

	}
	
	public void actionPerformed(ActionEvent e)
	{
		Object o = e.getSource();
		if (o == b)
		{
			System.out.println("um");
		}
		else if(o == b2)
		{
			System.out.println("dois");
		}
		else if(o == rb)
		{
			System.out.println("funciona");
		}
	}

//public 		class 		PlungeFrame 		extends 			JFrame 
//{
//	private JPanel panel;
//	
//	public JButton b = new JButton("botao"); //declara botao b
//	public JButton b2 = new JButton("botao2");
//	public JRadioButton rb = new JRadioButton("bolinha");
//
//	public PlungeFrame()
//	{
//		this.setTitle("Plunge");
//		this.setSize(200, 160);
//		this.setVisible(true);
//		//this.setSize(60, 40);
//		this.getContentPane().setLayout(new BorderLayout());
//		this.getContentPane().add(b, BorderLayout.SOUTH); //adiciona o botao na 'camada' contentpane
//		this.getContentPane().add(b2, BorderLayout.NORTH);
//		this.getContentPane().add(rb, BorderLayout.CENTER);
//		b.addActionListener(new ActionListener() 
//		{	public void actionPerformed(ActionEvent e)
//			{
//				System.out.println("b");
//			}
//		}); // acrescentando ouvidor pro botao b
//		b2.addActionListener(new ActionListener()
//		{
//			public void actionPerformed(ActionEvent e)
//			{
//				System.out.println("b2");
//			}
//		});
//		rb.addActionListener(new ActionListener()
//		{
//			public void actionPerformed(ActionEvent e) 
//			{
//				System.out.println("funciona");
//			}
//		});
//
//	}
	
	
}
