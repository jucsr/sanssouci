package br.UFSC.GRIMA.shopFloor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.Boring;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideFinishMilling;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.capp.machiningOperations.Drilling;
import br.UFSC.GRIMA.capp.machiningOperations.MachiningOperation;
import br.UFSC.GRIMA.capp.machiningOperations.PlaneFinishMilling;
import br.UFSC.GRIMA.capp.machiningOperations.PlaneRoughMilling;
import br.UFSC.GRIMA.capp.machiningOperations.Reaming;
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
			MachiningOperation boring = new Boring("name", 5);
			ws.setOperation(boring);

			EditBoringToolWS bTool = new EditBoringToolWS(janelaShopFloor, projetoSF, ws);
			bTool.setVisible(true);
			dispose();
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
			MachiningOperation bsRoughMilling = new BottomAndSideRoughMilling("name", 5);
			ws.setOperation(bsRoughMilling);

			EditBallEndMillWS bmWS = new EditBallEndMillWS(janelaShopFloor, projetoSF, ws);
			bmWS.setVisible(true);
			dispose();
		} else if(this.radioButton4.isSelected())
		{
			MachiningOperation drilling = new Drilling("name", 5);
			ws.setOperation(drilling);

			EditCenterDrillWS cd = new EditCenterDrillWS(janelaShopFloor, projetoSF, ws);
			cd.setVisible(true);
			dispose();
		} else if(this.radioButton5.isSelected())
		{
//			MachiningOperation freeForm = new FreeformOperation("name", 5);
//			ws.setOperation(freeForm);
//
//			EditBallEndMillWS bmWS = new EditBallEndMillWS(janelaShopFloor, projetoSF, ws);
//			bmWS.setVisible(true);
//			dispose();
		} else if(this.radioButton6.isSelected())
		{
			MachiningOperation pfinish = new PlaneFinishMilling("name", 5);
			ws.setOperation(pfinish);

			EditFaceMillWS fm = new EditFaceMillWS(janelaShopFloor, projetoSF, ws);
			fm.setVisible(true);
			dispose();
		} else if(this.radioButton7.isSelected())
		{
			MachiningOperation pRough = new PlaneRoughMilling("name", 5);
			ws.setOperation(pRough);

			EditFaceMillWS fm = new EditFaceMillWS(janelaShopFloor, projetoSF, ws);
			fm.setVisible(true);
			dispose();
		} else if(this.radioButton8.isSelected())
		{
			MachiningOperation r = new Reaming("name", 5);
			ws.setOperation(r);

			EditReamerWS fm = new EditReamerWS(janelaShopFloor, projetoSF, ws);
			fm.setVisible(true);
			dispose();
		} else if(this.radioButton9.isSelected())
		{
			MachiningOperation operation = new Drilling("", 5);
			ws.setOperation(operation);
					
			EditTwistDrillWS fm = new EditTwistDrillWS(janelaShopFloor, projetoSF, ws);
			fm.setVisible(true);
			dispose();
		}
	}
}
