package br.UFSC.GRIMA.cad;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import br.UFSC.GRIMA.cad.visual.JanelaCodigoHPGL_view;

public class JanelaCodigoHPGL extends JanelaCodigoHPGL_view{
	public String codigo;	
	
	public JanelaCodigoHPGL(String texto) {
		// TODO Auto-generated constructor stub
		this.codigo = texto;
		this.init();
		this.initListeners();
		this.setVisible(true);
	}
	
	public void init(){
		this.textArea.setText(this.codigo);
		this.setSize(400, 500);
	}
	
	public void initListeners(){
		JanelaCodigoHPGL_menu_actionAdapter menuListener = new JanelaCodigoHPGL_menu_actionAdapter(this);
		
		this.saveJMenuItem.addActionListener(menuListener);
	}
	
	public void salvar(){
		FileDialog fd = new FileDialog(this, "Salvar", FileDialog.SAVE);

		fd.setVisible(true);
		String dir = fd.getDirectory();
		String file = fd.getFile();
		String filePath = dir + file;
		try
		{
			ObjectOutputStream out = new ObjectOutputStream
			(new FileOutputStream(filePath, false));
			out.writeObject(this.codigo);
			out.writeUTF(this.codigo);
			out.flush();
			out.close();
			//arquivo vai estar salvo
			//this.salvo = true;//global
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}

class JanelaCodigoHPGL_menu_actionAdapter implements ActionListener{
	public JanelaCodigoHPGL parent;
	
	public JanelaCodigoHPGL_menu_actionAdapter(JanelaCodigoHPGL adaptee){
		this.parent = adaptee;
	}
	
	public void actionPerformed(ActionEvent e){
		Object source = e.getSource();
		
		if(source.equals(this.parent.saveJMenuItem)){
			this.parent.salvar();
		}
		else{
			this.parent.dispose();
		}
	}
}
