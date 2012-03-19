package br.UFSC.GRIMA.integracao;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import jsdai.SCombined_schema.EBlock;
import jsdai.SCombined_schema.ELinear_path;
import jsdai.SCombined_schema.EPlus_minus_value;
import jsdai.SCombined_schema.ESlot;
import jsdai.SCombined_schema.ESquare_u_profile;
import jsdai.lang.SdaiException;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.util.projeto.Axis2Placement3D;

public class RanhuraReader {
	
	public static Ranhura getRanhura(ESlot slot) throws SdaiException {
		
		String id = slot.getIts_id(null);
		
		double locX = slot.getFeature_placement(null).getLocation(null)
				.getCoordinates(null).getByIndex(1);
		double locY = slot.getFeature_placement(null).getLocation(null)
				.getCoordinates(null).getByIndex(2);
		double locZ = slot.getFeature_placement(null).getLocation(null)
				.getCoordinates(null).getByIndex(3);
		
		double larguraRanhura = ((ESquare_u_profile)slot.getSwept_shape(null)).getWidth(null).getTheoretical_size(null);

		double profundidadeRanhura = slot.getDepth(null).getPosition(null).getLocation(null).getCoordinates(null).getByIndex(3)*(-1);
		
		double comprimentoRanhura = ((ELinear_path)slot.getCourse_of_travel(null)).getDistance(null).getTheoretical_size(null);
		
		double alturaBloco = ((EBlock)slot.getIts_workpiece(null).getIts_bounding_geometry(null)).getZ(null);
		
		double xDir = ((ELinear_path)slot.getCourse_of_travel(null)).getIts_direction(null).getDirection_ratios(null).getByIndex(1);
		double yDir = ((ELinear_path)slot.getCourse_of_travel(null)).getIts_direction(null).getDirection_ratios(null).getByIndex(2);
		double zDir = ((ELinear_path)slot.getCourse_of_travel(null)).getIts_direction(null).getDirection_ratios(null).getByIndex(3);
		
		int eixoAtual = 0;
		double x=0,y=0,z=0;
		
		Face faceAtual = FaceReader.getFace(slot);
		
		if(faceAtual.getTipo()== Face.XY){

			if(xDir==1 && yDir==0 && zDir==0){
				eixoAtual = Ranhura.HORIZONTAL;
				x = 0;
				y = locY - larguraRanhura/2;
				z = alturaBloco - locZ;
			}else if (xDir==0 && yDir==1 && zDir==0) {
				eixoAtual = Ranhura.VERTICAL;
				x = locX - larguraRanhura/2;
				y = 0;
				z = alturaBloco - locZ;
			}else{
				System.out.println("Eixo Desconhecido: "+"( "+xDir+" , "+yDir+" , "+zDir+" )");
			}
			

		}else if(faceAtual.getTipo()==Face.YX){

			if(xDir==1 && yDir==0 && zDir==0){
				eixoAtual = Ranhura.HORIZONTAL;
				x = 0;
				y = faceAtual.getLargura() - locY + larguraRanhura/2;
				z = locZ;
			}else if (xDir==0 && yDir==1 && zDir==0) {
				eixoAtual = Ranhura.VERTICAL;
				x = locX - larguraRanhura/2;
				y = 0;
				z = locZ;
			}else{
				System.out.println("Eixo Desconhecido: "+"( "+xDir+" , "+yDir+" , "+zDir+" )");
			}

		}else if(faceAtual.getTipo()==Face.YZ){

			if(xDir==1 && yDir==0 && zDir==0){
				eixoAtual = Ranhura.HORIZONTAL;
				x = 0;
				y = locY - larguraRanhura/2;
				z = faceAtual.getProfundidadeMaxima() - locX;
			}else if (xDir==0 && yDir==1 && zDir==0) {
				eixoAtual = Ranhura.VERTICAL;
				x = faceAtual.getComprimento() - locZ;
				y = 0;
				z = faceAtual.getProfundidadeMaxima() - locX;
			}else{
				System.out.println("Eixo Desconhecido: "+"( "+xDir+" , "+yDir+" , "+zDir+" )");
			}
			
		}else if(faceAtual.getTipo()==Face.ZY){

			if(xDir==1 && yDir==0 && zDir==0){
				eixoAtual = Ranhura.HORIZONTAL;
				x = 0;
				y = locY - larguraRanhura/2;
				z = locX;
			}else if (xDir==0 && yDir==1 && zDir==0) {
				eixoAtual = Ranhura.VERTICAL;
				x = locZ - larguraRanhura/2;
				y = 0;
				z = locX;
			}else{
				System.out.println("Eixo Desconhecido: "+"( "+xDir+" , "+yDir+" , "+zDir+" )");
			}

		}else if(faceAtual.getTipo()==Face.XZ){

			if(xDir==1 && yDir==0 && zDir==0){
				eixoAtual = Ranhura.HORIZONTAL;
				x = 0;
				y = locZ - larguraRanhura/2;
				z = locY;
			}else if (xDir==0 && yDir==1 && zDir==0) {
				eixoAtual = Ranhura.VERTICAL;
				x = locX - larguraRanhura/2;
				y = 0;
				z = locY;
			}else{
				System.out.println("Eixo Desconhecido: "+"( "+xDir+" , "+yDir+" , "+zDir+" )");
			}
			
		}else if(faceAtual.getTipo()==Face.ZX){

			if(xDir==1 && yDir==0 && zDir==0){
				eixoAtual = Ranhura.HORIZONTAL;
				x = 0;
				y = faceAtual.getLargura() - larguraRanhura/2 - locZ;
				z = faceAtual.getProfundidadeMaxima() - locY;
			}else if (xDir==0 && yDir==1 && zDir==0) {
				eixoAtual = Ranhura.VERTICAL;
				x = locX - larguraRanhura/2;
				y = 0;
				z = faceAtual.getProfundidadeMaxima() - locY;
			}else{
				System.out.println("Eixo Desconhecido: "+"( "+xDir+" , "+yDir+" , "+zDir+" )");
			}

		}

		Ranhura ranhura = new Ranhura(id, x, y, z, locX, locY, locZ, larguraRanhura, profundidadeRanhura, eixoAtual);
		//talvez nao precise
		double tolerancia = ((EPlus_minus_value)((ELinear_path)slot.getCourse_of_travel(null)).getDistance(null).getImplicit_tolerance(null)).getUpper_limit(null);
		ranhura.setTolerancia(tolerancia);
		ranhura.setComprimento(comprimentoRanhura);
		
		
		double zLinhaX = slot.getFeature_placement(null).getAxis(null).getDirection_ratios(null).getByIndex(1);
		double zLinhaY = slot.getFeature_placement(null).getAxis(null).getDirection_ratios(null).getByIndex(2);
		double zLinhaZ = slot.getFeature_placement(null).getAxis(null).getDirection_ratios(null).getByIndex(3);
		
		
		ArrayList<Double> axis = new ArrayList<Double>();
		axis.add(zLinhaX);
		axis.add(zLinhaY);
		axis.add(zLinhaZ);
		
		zLinhaX = slot.getFeature_placement(null).getRef_direction(null).getDirection_ratios(null).getByIndex(1);
		zLinhaY = slot.getFeature_placement(null).getRef_direction(null).getDirection_ratios(null).getByIndex(2);
		zLinhaZ = slot.getFeature_placement(null).getRef_direction(null).getDirection_ratios(null).getByIndex(3);
		
		
		ArrayList<Double> refDirection = new ArrayList<Double>();
		refDirection.add(zLinhaX);
		refDirection.add(zLinhaY);
		refDirection.add(zLinhaZ);
		
		double zOriginal = slot.getFeature_placement(null).getLocation(null)
		.getCoordinates(null).getByIndex(3);
		
		Point3d coordinates = new Point3d(x,y,zOriginal);
		
		Axis2Placement3D position = new Axis2Placement3D(coordinates,axis,refDirection);
		
		ranhura.setPosition(position);
		
		return ranhura;
	}
	
}
