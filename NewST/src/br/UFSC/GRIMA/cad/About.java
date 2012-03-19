package br.UFSC.GRIMA.cad;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;

import br.UFSC.GRIMA.cad.visual.AboutFrame;

public class About extends AboutFrame implements ActionListener
{
	public About(JanelaPrincipal owner) 
	{
		super(owner);
		this.okButton.addActionListener(this);
		new StyledEditorKit.AlignmentAction("Justificado", StyleConstants.ALIGN_JUSTIFIED); 
//		this.editorPane1.setText("Aplicativo desenvolvido na Universidade Federal de Santa Catarina Faculdade de Engenharia Mecânica Centro Tecnológico | Florianópolis - SC - Brasil GRIMA/GRUCON, Caixa Postal 476  88040-900 E-mails: jcarlos@emc.ufsc.br jcf@grucon.ufsc.br");
		this.editorPane1.setText("                  Aplicativo desenvolvido na\n"
		+ "      Universidade Federal de Santa Catarina\n"
		+ "          Faculdade de Engenharia Mecânica\n"
		+ "Centro Tecnol\u00f3gico | Florianópolis - SC - Brasil\n"
		+ "GRIMA/GRUCON, Caixa Postal 476  88040-900\n"
		+ "                E-mails: jcarlos@emc.ufsc.br \n "
		+ "                          jcf@grucon.ufsc.br");
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object origem = e.getSource();
		if (origem == this.okButton)
		{
			this.dispose();
		}
	}
}
