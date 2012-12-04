package br.UFSC.GRIMA.shopFloor.applet;

import javax.swing.JApplet;
import javax.swing.JButton;

import br.UFSC.GRIMA.shopFloor.CriarNovoProjetoShopFloor;

public class AppletShopFloor extends JApplet {
	
	private static final long serialVersionUID = 4502765806345122994L;
	public JButton iniciarButton = new JButton("iiii");
	@SuppressWarnings("unused")
	private int userID;
	private String userName;

	public AppletShopFloor() {
	}

	public void init() {
		// Para carregar os parametros da p�gina HTML
//		this.userID = Integer.parseInt(getParameter("userID"));
//		this.userName = getParameter("userName");
		this.userName = "Cassio";
		this.userID = 106;
		new CriarNovoProjetoShopFloor(userID, userName);
	}
}
