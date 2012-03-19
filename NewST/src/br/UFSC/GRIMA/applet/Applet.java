package br.UFSC.GRIMA.applet;

import javax.swing.JApplet;
import javax.swing.JButton;

public class Applet extends JApplet {
	private static final long serialVersionUID = 4502765806345122994L;
	public JButton iniciarButton = new JButton("iiii");
	@SuppressWarnings("unused")
	private Principal principal;
	private int userID;
	private String userName;

	public Applet() {
	}

	public void init() {
		// Para carregar os parametros da p�gina HTML
//		this.userID = Integer.parseInt(getParameter("userID"));
//		this.userName = getParameter("userName");
		this.userName = "Cassio";
		this.userID = 106;
		principal = new Principal(userID, userName);
	}
//
//	private void inicializar() {
//		try {
//			jbInit();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}
//
//	private void jbInit() throws Exception {
//		this.getContentPane().add(iniciarButton);
//		iniciarButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				if (principal == null)
//					
//			}
//		});
//	}
}
