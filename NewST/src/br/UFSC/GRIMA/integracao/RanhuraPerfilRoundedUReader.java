package br.UFSC.GRIMA.integracao;

import jsdai.SCombined_schema.EBlock;
import jsdai.SCombined_schema.ELinear_path;
import jsdai.SCombined_schema.EPlus_minus_value;
import jsdai.SCombined_schema.ERounded_u_profile;
import jsdai.SCombined_schema.ESlot;
import jsdai.lang.SdaiException;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilRoundedU;

public class RanhuraPerfilRoundedUReader {
	
	public static RanhuraPerfilRoundedU getRanhura(ESlot slot) throws SdaiException {

		String id = slot.getIts_id(null);

		double locX = slot.getFeature_placement(null).getLocation(null)
		.getCoordinates(null).getByIndex(1);
		double locY = slot.getFeature_placement(null).getLocation(null)
		.getCoordinates(null).getByIndex(2);
		double locZ = slot.getFeature_placement(null).getLocation(null)
		.getCoordinates(null).getByIndex(3);
		
		double alturaBloco = ((EBlock)slot.getIts_workpiece(null).getIts_bounding_geometry(null)).getZ(null);
		
		double profundidadeRanhura = slot.getDepth(null).getPosition(null).getLocation(null).getCoordinates(null).getByIndex(3)*(-1);

		double larguraRanhura = ((ERounded_u_profile)slot.getSwept_shape(null)).getWidth(null).getTheoretical_size(null);

		double xDir = ((ELinear_path)slot.getCourse_of_travel(null)).getIts_direction(null).getDirection_ratios(null).getByIndex(1);
		double yDir = ((ELinear_path)slot.getCourse_of_travel(null)).getIts_direction(null).getDirection_ratios(null).getByIndex(2);
		double zDir = ((ELinear_path)slot.getCourse_of_travel(null)).getIts_direction(null).getDirection_ratios(null).getByIndex(3);

		int eixoAtual = 0;
		double x=0,y=0,z=0;
		
		if(xDir==1 && yDir==0 && zDir==0){
			eixoAtual = Ranhura.HORIZONTAL;
			x=locX;
			y=locY-larguraRanhura/2;
			z=alturaBloco - locZ;
		}else if (xDir==0 && yDir==1 && zDir==0) {
			eixoAtual = Ranhura.VERTICAL;
			x=locX-larguraRanhura/2;
			y=locY;
			z=alturaBloco - locZ;
		}else{
			System.out.println("Eixo Desconhecido: "+"( "+xDir+" , "+yDir+" , "+zDir+" )");

		}
		
		double tolerancia = ((EPlus_minus_value)((ELinear_path)slot.getCourse_of_travel(null)).getDistance(null).getImplicit_tolerance(null)).getUpper_limit(null);
		double comprimentoRanhura = ((ELinear_path)slot.getCourse_of_travel(null)).getDistance(null).getTheoretical_size(null);
		
		RanhuraPerfilRoundedU ranhura = new RanhuraPerfilRoundedU(id, x, y, z,locX,locY,locZ, larguraRanhura,profundidadeRanhura,eixoAtual);
		ranhura.setTolerancia(tolerancia);
		ranhura.setComprimento(comprimentoRanhura);
		
		return ranhura;
	}

}
