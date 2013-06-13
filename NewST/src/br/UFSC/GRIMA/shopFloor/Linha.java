package br.UFSC.GRIMA.shopFloor;

import java.util.ArrayList;

public class Linha extends ArrayList<Double> {
	
	Double min(){
		Double minimum = 999999999.99;
		for(int i =0 ; i < this.size();i++){
			if(this.get(i)<= minimum ){
				minimum = this.get(i);
			}
		}
		return minimum;
	}
	

}
