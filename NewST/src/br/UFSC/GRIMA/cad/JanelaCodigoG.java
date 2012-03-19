package br.UFSC.GRIMA.cad;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;


import br.UFSC.GRIMA.cad.visual.GCodeFrame;
/**
 * 
 * @author Jc
 *
 */
public class JanelaCodigoG extends GCodeFrame implements ActionListener
{
	public JanelaCodigoG()
	{
		this.menuItem1.addActionListener(this);
		this.menuItem2.addActionListener(this);
		this.menuItem3.addActionListener(this);
		this.menuItem4.addActionListener(this);		
		this.menuItem5.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object object = e.getSource();
		if(object == this.menuItem1)
		{
			this.salvar1();
		} else if(object == this.menuItem2)
		{
			this.close();
		} else if(object == this.menuItem3)
		{
			this.selectAll();
		} else if(object == this.menuItem4)
		{
			this.copy();
		} else if(object == this.menuItem5)
		{
			this.find();
		}
	}
	private void find() 
	{
		JanelaFind jf = new JanelaFind(this, this.textPane1);
		jf.setVisible(true);
		
	}
	private void copy() 
	{
		this.textPane1.copy();
	}
	private void selectAll() 
	{
		this.textPane1.selectAll();
	}
	private void salvar() {
		FileDialog fd = new FileDialog(this, "Salvar", FileDialog.SAVE);

		fd.setVisible(true);
		String dir = fd.getDirectory();
		String file = fd.getFile();
		String filePath = dir + file + ".txt";
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(filePath, false));
			out.writeObject(this.textPane1.getText());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void salvar1() {
		JFileChooser fc = new JFileChooser();

		if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
			return;

		File arquivo = fc.getSelectedFile();
		if (arquivo == null)
			return;

		FileWriter writer = null;
		try {
			writer = new FileWriter(arquivo);
			writer.write(this.textPane1.getText());
		} catch (IOException ex) {
			// Possiveis erros aqui
			ex.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException x) {
					//
					x.printStackTrace();
				}
			}
		}
	}
	private void close()
	{
		this.dispose();
	}
}
