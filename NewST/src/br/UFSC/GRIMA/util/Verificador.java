package br.UFSC.GRIMA.util;

import java.util.ArrayList;
import java.util.Iterator;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.entidades.Rectangle3D;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.Furo;

public class Verificador {
	
Rectangle3D rectangle3D;


public Verificador(Rectangle3D rectangle3D, double divisorBase, double divisorVertical, double divisorHorizontal){
	this.rectangle3D = rectangle3D;
	
}	

public boolean validateFuroComArrayBase(Furo furo, double raioApoio, ArrayList<Point3d> arrayList){
	boolean aux = true;
	Iterator<Point3d> iterator = arrayList.iterator();
	while (iterator.hasNext()) {
		Point3d pointAux = iterator.next();
		if (validateFurocomApoioBase(furo, raioApoio, pointAux) == false){aux = false;}
		
	}
	return aux;
}


private boolean validateFurocomApoioBase(Furo furo, double raioApoio, Point3d ponto) {
	double xDist = Math.abs(ponto.getX() - furo.getPosicaoX());
	double yDist = Math.abs(ponto.getY() - furo.getPosicaoY());
	double zDist = Math.abs(ponto.getZ() - furo.getPosicaoZ());
	double DistXYFuro = Math.hypot(xDist, yDist);
	double DistXYZFuro = Math.hypot(DistXYFuro, zDist);
	

	if ((DistXYZFuro) <= (furo.getRaio() + raioApoio)) {

		return false;
	} else {
		return true;
	}
}

public boolean verificarValoresFuro(Furo furo){

	
	if (furo.getDiametro() >= rectangle3D.getX() || furo.getDiametro() >= rectangle3D.getY()){
		return true;
		}
				
	else if ((furo.getPosicaoX() - furo.getRaio()) < 0 
			|| (furo.getPosicaoY() - furo.getRaio()) < 0
			|| (furo.getPosicaoZ() < 0)){
		return true;
		}
	
	else if((furo.getPosicaoX() + furo.getRaio()) > rectangle3D.getX()
			|| (furo.getPosicaoY() + furo.getRaio())> rectangle3D.getY() 
			|| (furo.getPosicaoZ() + furo.getRaio())> rectangle3D.getZ()){
		return true;
	}
	  			 			 

	else if (furo.getDiametro() <0){return true;}
	
	
	else {return false;}
	
	
}

public boolean verificarValoresCavidade(Cavidade cavidade) {
	if (cavidade.getComprimento() >= rectangle3D.getX()){
		return true;
		}
	
	else if (cavidade.getLargura() >= rectangle3D.getY()){
		return true;
		}
	
	
	else if (cavidade.getPosicaoX() < 0 
			|| cavidade.getPosicaoY() < 0 
			|| cavidade.getProfundidade() < 0 ) {
		return true;
		}

	else{return false;}
}

public boolean validateCavidadeComArrayBase(Cavidade cavidade, double raioApoio,
		ArrayList<Point3d> arrayList) {
	
	boolean aux = true;
	Iterator<Point3d> iterator = arrayList.iterator();
	while (iterator.hasNext()) {
		Point3d pointAux = iterator.next();
		
		if (cavidade.getRaio() == 0){
			if (validateCavidadecomApoioBase(cavidade, raioApoio, pointAux) == false){aux = false;}
		}
		
		else{
			Furo furo1aux = new Furo("Furo1aux", (cavidade.getPosicaoX() + cavidade.getRaio()), (cavidade.getPosicaoY() + cavidade.getRaio()), (rectangle3D.getZ() - cavidade.getProfundidade()), (cavidade.getRaio() * 2)); 
			if(validateFurocomApoioBase(furo1aux, raioApoio, pointAux) == false){aux = false;}
			
			Furo furo2aux = new Furo("Furo2aux", ((cavidade.getPosicaoX() +  cavidade.getComprimento()) - cavidade.getRaio()), (cavidade.getPosicaoY() + cavidade.getRaio()), (rectangle3D.getZ() - cavidade.getProfundidade()), (cavidade.getRaio() * 2));	
			if(validateFurocomApoioBase(furo2aux, raioApoio, pointAux) == false){aux = false;}
			
			Furo furo3aux = new Furo("Furo3aux", (cavidade.getPosicaoX() + cavidade.getRaio()), ((cavidade.getPosicaoY()+ cavidade.getLargura()) - cavidade.getRaio()), (rectangle3D.getZ() - cavidade.getProfundidade()), (cavidade.getRaio() * 2));
			if(validateFurocomApoioBase(furo3aux, raioApoio, pointAux) == false){aux = false;}
			
			Furo furo4aux = new Furo("Furo4aux", ((cavidade.getPosicaoX() +  cavidade.getComprimento()) - cavidade.getRaio()), ((cavidade.getPosicaoY()+ cavidade.getLargura()) - cavidade.getRaio()), (rectangle3D.getZ() - cavidade.getProfundidade()), (cavidade.getRaio() * 2));
			if(validateFurocomApoioBase(furo4aux, raioApoio, pointAux) == false){aux = false;}
			
			Cavidade cavidade1aux = new Cavidade(cavidade.getPosicaoX(),(cavidade.getPosicaoY() + cavidade.getRaio()),cavidade.getPosicaoZ(),0,(cavidade.getLargura() - (cavidade.getRaio() * 2)),cavidade.getComprimento(),cavidade.getProfundidade());
			if (validateCavidadecomApoioBase(cavidade1aux, raioApoio, pointAux) == false){aux = false;}
			
			Cavidade cavidade2aux = new Cavidade((cavidade.getPosicaoX() + cavidade.getRaio()), cavidade.getPosicaoY() ,cavidade.getPosicaoZ(),0, cavidade.getLargura() , (cavidade.getComprimento() - (cavidade.getRaio() * 2)) ,cavidade.getProfundidade());
			if (validateCavidadecomApoioBase(cavidade2aux, raioApoio, pointAux) == false){aux = false;}
		}
	}
	return aux;
}

private boolean validateCavidadecomApoioBase(Cavidade cavidade, double raioApoio,
		Point3d ponto) {
	boolean aux = true;
	
	if (cavidade.getRaio() == 0){
		boolean x, y;
		x = (((ponto.getX() + raioApoio) > cavidade.getPosicaoX()) && (ponto.getX() < (cavidade.getPosicaoX() + cavidade.getComprimento())) );
		y = (((ponto.getY() + raioApoio) > cavidade.getPosicaoY()) && (ponto.getY() < (cavidade.getPosicaoY() + cavidade.getLargura())) );

		if (((cavidade.getPosicaoZ() - cavidade.getProfundidade()) == 0) && x && y) {
			aux = false;
		} 

	}
	
	return aux;

}
/*
public boolean validateCavidadeComFeatures(Cavidade cavidade, int posic,
		ArrayList<Feature> arrayList) {
	boolean aux = true;
	
	
	return aux;
}

*/

public boolean validateFuroComArrayLateral(Furo furo, double raioApoio,
		ArrayList<Point3d> arrayList) {
	
	boolean aux = true;
	Iterator<Point3d> iterator = arrayList.iterator();
	while (iterator.hasNext()) {
		Point3d pointAux = iterator.next();
		
		if (validateFurocomApoioLateral(furo, raioApoio, pointAux) == false){aux = false;}
		
	}
	
	return aux;
	


}

private boolean validateFurocomApoioLateral(Furo furo, double raioApoio,
		Point3d pointAux) {

	
	double xDist = Math.abs(pointAux.getX() - furo.getPosicaoX());
	double yDist = Math.abs(pointAux.getY() - furo.getPosicaoY());
	
	double DistXYFuro = Math.hypot(xDist, yDist);
	
	
	if (DistXYFuro <= furo.getRaio()){
		
		return false;
	} else {
		return true;
	}
	
	

}

public boolean validateCavidadeComArrayLateral(Cavidade cavidade,
		double raioApoio, ArrayList<Point3d> arrayList) {
	
	
	boolean aux = true;
	Iterator<Point3d> iterator = arrayList.iterator();
	while (iterator.hasNext()) {
		Point3d pointAux = iterator.next();
		
		if (cavidade.getRaio() == 0){
			if (validateCavidadecomApoioLateral(cavidade, raioApoio, pointAux) == false){aux = false;}
		}
		
		else{
			Furo furo1aux = new Furo("Furo1aux", (cavidade.getPosicaoX() + cavidade.getRaio()), (cavidade.getPosicaoY() + cavidade.getRaio()), (rectangle3D.getZ() - cavidade.getProfundidade()), (cavidade.getRaio() * 2)); 
			if(validateFurocomApoioLateral(furo1aux, raioApoio, pointAux) == false){aux = false;}
			
			Furo furo2aux = new Furo("Furo2aux", ((cavidade.getPosicaoX() +  cavidade.getComprimento()) - cavidade.getRaio()), (cavidade.getPosicaoY() + cavidade.getRaio()), (rectangle3D.getZ() - cavidade.getProfundidade()), (cavidade.getRaio() * 2));	
			if(validateFurocomApoioLateral(furo2aux, raioApoio, pointAux) == false){aux = false;}
			
			Furo furo3aux = new Furo("Furo3aux", (cavidade.getPosicaoX() + cavidade.getRaio()), ((cavidade.getPosicaoY()+ cavidade.getLargura()) - cavidade.getRaio()), (rectangle3D.getZ() - cavidade.getProfundidade()), (cavidade.getRaio() * 2));
			if(validateFurocomApoioLateral(furo3aux, raioApoio, pointAux) == false){aux = false;}
			
			Furo furo4aux = new Furo("Furo4aux", ((cavidade.getPosicaoX() +  cavidade.getComprimento()) - cavidade.getRaio()), ((cavidade.getPosicaoY()+ cavidade.getLargura()) - cavidade.getRaio()), (rectangle3D.getZ() - cavidade.getProfundidade()), (cavidade.getRaio() * 2));
			if(validateFurocomApoioLateral(furo4aux, raioApoio, pointAux) == false){aux = false;}
			
			Cavidade cavidade1aux = new Cavidade(cavidade.getPosicaoX(),(cavidade.getPosicaoY() + cavidade.getRaio()),cavidade.getPosicaoZ(),0,(cavidade.getLargura() - (cavidade.getRaio() * 2)),cavidade.getComprimento(),cavidade.getProfundidade());
			if (validateCavidadecomApoioLateral(cavidade1aux, raioApoio, pointAux) == false){aux = false;}
			
			Cavidade cavidade2aux = new Cavidade((cavidade.getPosicaoX() + cavidade.getRaio()), cavidade.getPosicaoY() ,cavidade.getPosicaoZ(),0, cavidade.getLargura() , (cavidade.getComprimento() - (cavidade.getRaio() * 2)) ,cavidade.getProfundidade());
			if (validateCavidadecomApoioLateral(cavidade2aux, raioApoio, pointAux) == false){aux = false;}
		}
	}
	return aux;
}

private boolean validateCavidadecomApoioLateral(Cavidade cavidade,
		double raioApoio, Point3d pointAux) {

	boolean aux = true;
	
	if (cavidade.getRaio() == 0){
		boolean x, y;
		x = (((pointAux.getX()) > cavidade.getPosicaoX()) && (pointAux.getX() < (cavidade.getPosicaoX() + cavidade.getComprimento())) );
		y = (((pointAux.getY()) > cavidade.getPosicaoY()) && (pointAux.getY() < (cavidade.getPosicaoY() + cavidade.getLargura())) );

		if (((cavidade.getPosicaoZ() - cavidade.getProfundidade()) < (pointAux.getZ() + raioApoio)) && x && y) {
			aux = false;
		} 

	}
	
	return aux;

	
}
}


