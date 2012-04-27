package br.UFSC.GRIMA.cad;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JOptionPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.cad.visual.CreateRectangularBossFrame;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;
import br.UFSC.GRIMA.util.findPoints.LimitedLine;

/**
 * 
 * @author Lucas K
 *
 */

public class CriarRectangularBoss extends CreateRectangularBossFrame implements 
		ActionListener {

	public Face face = null;
	public JanelaPrincipal parent;
	private Cavidade cavidade = null;
	
	public CriarRectangularBoss(JanelaPrincipal parent, Face face, Cavidade cavidade) {
		super(parent);
		this.cavidade = cavidade;
		this.face = face;
		this.parent = parent;
		
		this.init();
	}
	
	public void init() {
		this.adjustSize();
		this.adjustPosition();

		this.label16.setText(this.face.getTipoString());

		super.okButton.addActionListener(this);
		super.cancelButton.addActionListener(this);
		// this.checkBox1.addActionListener(this);
		super.spinner7.setVisible(false);
		
		this.setVisible(true);

	}
	
}
