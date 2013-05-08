package br.UFSC.GRIMA.shopFloor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import br.UFSC.GRIMA.shopFloor.visual.CreateToolFrame;

public class CreateTool extends CreateToolFrame implements ActionListener{

	private CreateMillingMachine createMillingMachine;
	private CreateDrillingMachine createDrillingMachine;
	
	public CreateTool(CreateDrillingMachine createDrillingMachine){
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		this.createDrillingMachine = createDrillingMachine;
	}
	
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
			this.ok();
			this.dispose();
		}
		else if(o.equals(cancelButton))
		{
			this.dispose();
		}
		
	}
	
	private void ok()
	{
		if(createMillingMachine != null){
		if(radioButton1.isSelected())
		{
			AddNewBallEndMill bem = new AddNewBallEndMill(createMillingMachine);//MILLING
			bem.setVisible(true);
		}
		else if(radioButton2.isSelected())
		{
			AddNewBoringTool bt = new AddNewBoringTool(createMillingMachine);//DRILLING MILLING TURNING
			bt.setVisible(true);
		}
		else if(radioButton3.isSelected())
		{
			AddNewBullnoseEndMill bnem = new AddNewBullnoseEndMill(createMillingMachine);//MILLING
			bnem.setVisible(true);
		}
		else if(radioButton4.isSelected())
		{
			AddNewCenterDrill cd = new AddNewCenterDrill(createMillingMachine);// MILLING,DRILLING
			cd.setVisible(true);
		}
		else if(radioButton5.isSelected())
		{
			AddNewReamer r = new AddNewReamer(createMillingMachine);//MILLING DRILLING
			r.setVisible(true);
		}
		else if(radioButton6.isSelected())
		{
			AddNewEndMill em = new AddNewEndMill(createMillingMachine);//MILLING
			em.setVisible(true);
		}
		else if(radioButton7.isSelected())
		{
			AddNewFaceMill fm = new AddNewFaceMill(createMillingMachine);//MILLING
			fm.setVisible(true);
		}
		 if(radioButton8.isSelected())
		{
			AddNewTwistDrill td = new AddNewTwistDrill(createMillingMachine);// MILLING,DRILLING,TURNING
			td.setVisible(true);
		}
		}else if(createDrillingMachine !=(null)){
			
			 if(radioButton2.isSelected())
			{
				AddNewBoringTool bt = new AddNewBoringTool(createDrillingMachine);//DRILLING MILLING TURNING
				bt.setVisible(true);
			}
			
			else if(radioButton4.isSelected())
			{
				AddNewCenterDrill cd = new AddNewCenterDrill(createDrillingMachine);// MILLING,DRILLING
				cd.setVisible(true);
			}
			else if(radioButton5.isSelected())
			{
				AddNewReamer r = new AddNewReamer(createDrillingMachine);//MILLING DRILLING
				r.setVisible(true);
			}
			
			else if(radioButton8.isSelected())
			{
				AddNewTwistDrill td = new AddNewTwistDrill(createDrillingMachine);// MILLING,DRILLING,TURNING
				td.setVisible(true);
			}else{
				JOptionPane.showMessageDialog(null,"Tool not able to current Machine");
			}
		}/*else if(createTurningMachine !=(null)){
		
		if(radioButton2.isSelected())
		{
			AddNewBoringTool bt = new AddNewBoringTool(createTurningMachine);//DRILLING MILLING TURNING
			bt.setVisible(true);
		}else if(radioButton8.isSelected())
			{
				AddNewTwistDrill td = new AddNewTwistDrill(createTurningMachine);// MILLING,DRILLING,TURNING
				td.setVisible(true);
			}
		*
		*/
	}//end of ok method
	
}

