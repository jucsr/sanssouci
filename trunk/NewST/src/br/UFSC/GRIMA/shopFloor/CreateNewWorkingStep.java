package br.UFSC.GRIMA.shopFloor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideFinishMilling;
import br.UFSC.GRIMA.capp.machiningOperations.MachiningOperation;
import br.UFSC.GRIMA.shopFloor.visual.CreateNewWorkingStepFrame;

public class CreateNewWorkingStep extends CreateNewWorkingStepFrame implements ActionListener{
	
	private JanelaShopFloor janelaShopFloor;
	private ShopFloor shopFloor;
	private ProjetoSF projetoSF;
	private ArrayList<Workingstep> wsArray = new ArrayList<Workingstep>();
	private Workingstep ws = new Workingstep();
//	private ProjetoSF projetoSF2 = new ProjetoSF(shopFloor);

	public CreateNewWorkingStep(JanelaShopFloor janelaShopFloorNew, ShopFloor shopFloorNew, ProjetoSF projetoSFNew)
	{
		super(janelaShopFloorNew);
		this.projetoSF = projetoSFNew;
//		this.wsArray = projetoSF.getWorkingsteps();
		this.shopFloor = shopFloorNew;
		this.janelaShopFloor = janelaShopFloorNew;
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object o = e.getSource();
		if(o.equals(this.okButton))
		{
			this.ok();
		} else if(o.equals(this.cancelButton))
		{
			this.dispose();
		}
	}
	
	private void ok() 
	{
		if(this.radioButton1.isSelected())
		{
//			AddNewBoringTool bt = new AddNewBoringTool(JDialog);
//			bt.setVisible(true);
//			dispose();
		} else if(this.radioButton2.isSelected())
		{
			MachiningOperation bsFinishMilling = new BottomAndSideFinishMilling("name", 5);
			ws.setOperation(bsFinishMilling);
//			wsArray.add(wsNew);
//			projetoSF.setWorkingsteps(wsArray);
			
			EditBallEndMillWS bmWS = new EditBallEndMillWS(janelaShopFloor, projetoSF, ws);
			bmWS.setVisible(true);
			dispose();
		} else if(this.radioButton3.isSelected())
		{
//			EditBallEndMillWS bmWS = new EditBallEndMillWS(janelaShopFloor, wsArray);
//			bmWS.setVisible(true);
//			dispose();
		} else if(this.radioButton4.isSelected())
		{
			
		} else if(this.radioButton5.isSelected())
		{
			
		} else if(this.radioButton6.isSelected())
		{
			
		} else if(this.radioButton7.isSelected())
		{
			
		} else if(this.radioButton8.isSelected())
		{
			
		} 
	}
}
