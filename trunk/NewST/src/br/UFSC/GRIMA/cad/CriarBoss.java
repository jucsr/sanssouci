package br.UFSC.GRIMA.cad;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import br.UFSC.GRIMA.cad.visual.CriarBossFrame;
import br.UFSC.GRIMA.entidades.features.Feature;

public class CriarBoss extends CriarBossFrame implements ActionListener{

	public CriarBoss(JanelaPrincipal owner, Feature feature) {  //primeiro arg. eh referente a subordinacao a janela principal
		super(owner);
		this.buttonCancel.addActionListener(this);
		this.buttonOK.addActionListener(this);
		this.buttonCircular.addActionListener(this);
		this.buttonRectangular.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();     // diz de onde veio o evento
		if(o.equals(buttonCancel))
		{
			this.cancel();
			
		}else if (o == buttonOK)
		{
			this.ok();
		}
	}

	private void ok() {
		if(buttonCircular.isSelected())
		{
			CreateCircularBoss ccb = new CreateCircularBoss(owner, feature);
			ccb.setVisible(true);
			
		}else if(this.buttonRectangular.isSelected())
		{
			CreateRectangularBoss crb = new CreateRectangularBoss(owner, feature);
			crb.setVisible(true);
		}
	}

	private void cancel() {
		this.dispose();
	}
	

}
