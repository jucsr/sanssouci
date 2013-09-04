package br.UFSC.GRIMA.shopFloor;

import java.io.Serializable;
import java.util.ArrayList;

import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class ProjetoSF implements Serializable
{
	private ShopFloor shopFloor;
	private ArrayList<Workingstep> workingsteps = new ArrayList<Workingstep>();
	private int lotSize=1000;
	private Projeto projeto;

	public int getLotSize() {
		return lotSize;
	}
	public void setLotSize(int lotSize) {
		this.lotSize = lotSize;
	}
	public ProjetoSF(ShopFloor shopFloor)
	{
		this.shopFloor = shopFloor;
	}
	public ShopFloor getShopFloor() {
		return shopFloor;
	}
	public void setShopFloor(ShopFloor shopFloor) {
		this.shopFloor = shopFloor;
	}
	public ArrayList<Workingstep> getWorkingsteps() {
		return workingsteps;
	}
	public void setWorkingsteps(ArrayList<Workingstep> workingstepsNew) {
		this.workingsteps = workingstepsNew;
	}
	public Projeto getProjeto() {
		return projeto;
	}
	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}
}
