package br.UFSC.GRIMA.util;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ImageSource {

	URL iconURL;

	public Icon getToolIcon(int selectedIndex) {

		if (selectedIndex == 0) {
			iconURL = getClass().getResource("/images/R411.5-10034D.jpg");
		} else if (selectedIndex == 1) {
			iconURL = getClass().getResource("/images/R411.5-13554DK20.jpg");
		} else if (selectedIndex == 2) {
			iconURL = getClass().getResource("/images/R411.5-15034DK20.jpg");
		}

		return new ImageIcon(iconURL);
	}

}
