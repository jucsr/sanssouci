package br.UFSC.GRIMA.cad;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;

import br.UFSC.GRIMA.cad.visual.CriarBossFrame;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.Degrau;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class CriarBoss extends CriarBossFrame implements ActionListener{

	private Feature feature;
	private JanelaPrincipal owner;
	private Face face;
	private Projeto projeto;
	public CriarBoss(JanelaPrincipal owner, Projeto projeto, Face face, Feature feature) {  //primeiro arg. eh referente a subordinacao a janela principal
		super(owner);
		this.feature = feature;
		this.owner = owner;
		this.projeto = projeto;
		this.face = face;
		this.buttonCancel.addActionListener(this);
		this.buttonOK.addActionListener(this);
		this.buttonCircular.addActionListener(this);
		this.buttonRectangular.addActionListener(this);
		this.radioButtonGeneral.addActionListener(this);
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
		}else if (o == buttonCircular)
		{
			labelTipoBoss.setIcon(new ImageIcon(getClass().getResource("/images/circBoss.png")));
		}else if (o == buttonRectangular)
		{
			labelTipoBoss.setIcon(new ImageIcon(getClass().getResource("/images/recBoss.png")));
		}else if (o == radioButtonGeneral)
		{
			
		}
	}

	private void ok() 
	{
		if(buttonCircular.isSelected())
		{
			if(feature.getClass() == Cavidade.class)
			{
				Cavidade cavidade = (Cavidade)this.feature;
//				CriarCircularBoss ccb = new CriarCircularBoss(owner, face, cavidade);
				CreateCircularBoss ccb = new CreateCircularBoss(owner, projeto, face, cavidade);

				ccb.setVisible(true);
				this.dispose();
			} else if(feature.getClass() == Degrau.class)
			{
				
			} else if(feature.getClass() == GeneralClosedPocket.class)
			{
				GeneralClosedPocket cavidadeGeral = (GeneralClosedPocket)this.feature;
//				CriarCircularBoss ccb = new CriarCircularBoss(owner, face, cavidadeGeral);
				CreateCircularBoss ccb = new CreateCircularBoss(owner, projeto, face, cavidadeGeral);
				ccb.setVisible(true);
				dispose();
			}
			
		}else if(this.buttonRectangular.isSelected())
		{
			if(feature.getClass() == Cavidade.class)
			{
				Cavidade cavidade = (Cavidade)this.feature;
//				CriarRectangularBoss crb = new CriarRectangularBoss(owner, face, cavidade);
				CreateRectangularBoss crb = new CreateRectangularBoss(owner, projeto, face, cavidade);
				crb.setVisible(true);
				this.dispose();
			} else if(feature.getClass() == Degrau.class)
			{
				
			} else if (feature.getClass() == GeneralClosedPocket.class)
			{
				GeneralClosedPocket cavidadeGeral = (GeneralClosedPocket)this.feature;
//				CriarRectangularBoss crb = new CriarRectangularBoss(owner, face, cavidadeGeral);
				CreateRectangularBoss crb = new CreateRectangularBoss(owner, projeto, face, cavidadeGeral);
				crb.setVisible(true);
				this.dispose();
			}
		}else if(this.radioButtonGeneral.isSelected())
		{
			CreateGeneralProfileBoss create = new CreateGeneralProfileBoss(owner, projeto, face, feature);
			create.setVisible(true);
			this.dispose();
		}
	}

	private void cancel() 
	{
		this.dispose();
	}
}
