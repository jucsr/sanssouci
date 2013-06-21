package br.UFSC.GRIMA.shopFloor;

import java.util.ArrayList;

public class Linha extends ArrayList<Double> {
	
	double min(){
		double minimum = 999999999.99;
		for(int i =0 ; i < this.size();i++){
			if(this.get(i)<= minimum ){
				
				minimum = this.get(i);
			}
		}
		return minimum;
	}
	int indexmin(){
		double mini = 999999999.99;
		int j = 0;
		for(int i =0 ; i < this.size();i++){
			if(this.get(i)<= mini ){
				j= i;
				mini = this.get(i);
			}
		}
		return (j+1);
	}

}
