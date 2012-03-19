
package br.UFSC.GRIMA.cad;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.SocketPermission;

import javax.swing.JApplet;
import javax.swing.JButton;

public class AppletProjeto extends JApplet{
	public JButton iniciarButton = new JButton("Iniciar");
	private NovoProjeto novoProjeto;
	SocketPermission p1 = new SocketPermission("gauss", "connect,accept,listen,resolve");
	
	public AppletProjeto() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	private void jbInit() throws Exception {
		this.getContentPane().add(iniciarButton);
		iniciarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (novoProjeto == null)
					novoProjeto = new NovoProjeto(1, "");
				novoProjeto.setVisible(true);
			}
		});
	}
}
