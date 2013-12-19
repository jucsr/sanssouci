package br.UFSC.GRIMA.shopFloor;

import java.util.ArrayList;

import br.UFSC.GRIMA.capp.Workingstep;

public class vectorWorkingPath{
	private ArrayList<Workingstep> workingstep;
	private ArrayList<Integer> list = new ArrayList<Integer>();
	private Double time;
	public ArrayList<Workingstep> getWorkingstep() {
		return workingstep;
	}
	public void setWorkingstep(ArrayList<Workingstep> workingstep) {
		this.workingstep = workingstep;
	}
	public void setWorkingstep(int index, Workingstep workingstep){
		this.workingstep.set(index, workingstep);
		
	}
	public ArrayList<Integer> getList() {
		return list;
	}
	public Integer getList(int i){
		return list.get(i);
	}
	public void setList(ArrayList<Integer> list) {
		this.list = list;
	}
	public void setList(int i, Integer j){
		this.list.set(i,j);
	}
	public double getTime() {
		return time;
	}
	public void setTime(Double time) {
		this.time = time;
	}
}
