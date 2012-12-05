package br.UFSC.GRIMA.shopFloor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import br.UFSC.GRIMA.shopFloor.visual.CreateToolFrame;

public class CreateTool extends CreateToolFrame implements ActionListener{

	private CreateMillingMachine createMillingMachine;
	
	public CreateTool(CreateMillingMachine createMillingMachine)
	{
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		this.createMillingMachine = createMillingMachine;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object o = e.getSource();
		
		if(o.equals(okButton))
		{
			ok();
			this.dispose();
		}
		else if(o.equals(cancelButton))
		{
			this.dispose();
		}
		
	}
	
	private void ok()
	{
		if(radioButton1.isSelected())
		{
			AddNewBallEndMill bem = new AddNewBallEndMill(createMillingMachine);
			bem.setVisible(true);
		}
		else if(radioButton2.isSelected())
		{
			AddNewBoringTool bt = new AddNewBoringTool(createMillingMachine);
			bt.setVisible(true);
		}
		else if(radioButton3.isSelected())
		{
			AddNewBullnoseEndMill bnem = new AddNewBullnoseEndMill(createMillingMachine);
			bnem.setVisible(true);
		}
		else if(radioButton4.isSelected())
		{
			AddNewCenterDrill cd = new AddNewCenterDrill(createMillingMachine);
			cd.setVisible(true);
		}
		else if(radioButton5.isSelected())
		{
			AddNewReamer r = new AddNewReamer(createMillingMachine);
			r.setVisible(true);
		}
		else if(radioButton6.isSelected())
		{
			AddNewEndMill em = new AddNewEndMill(createMillingMachine);
			em.setVisible(true);
		}
		else if(radioButton7.isSelected())
		{
			AddNewFaceMill fm = new AddNewFaceMill(createMillingMachine);
			fm.setVisible(true);
		}
		 if(radioButton8.isSelected())
		{
			AddNewTwistDrill td = new AddNewTwistDrill(createMillingMachine);
			td.setVisible(true);
		}
	}
	
}

