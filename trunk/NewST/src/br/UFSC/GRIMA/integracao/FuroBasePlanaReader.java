package br.UFSC.GRIMA.integracao;

import jsdai.SCombined_schema.EBlock;
import jsdai.SCombined_schema.EPlus_minus_value;
import jsdai.SCombined_schema.ERound_hole;
import jsdai.SCombined_schema.EThrough_bottom_condition;
import jsdai.lang.SdaiException;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Furo;
import br.UFSC.GRIMA.entidades.features.FuroBasePlana;

public class FuroBasePlanaReader {

	public static FuroBasePlana getFuro(ERound_hole round_hole) throws SdaiException {

		String id = round_hole.getIts_id(null);
		
		double diametroFuro = round_hole.getDiameter(null).getTheoretical_size(
				null);
		
		double profundidade = round_hole.getDepth(null).getPosition(null)
				.getLocation(null).getCoordinates(null).getByIndex(3)*(-1);
		
		System.out.println("PROFUNDIDADE LEITOR: " + profundidade);
		
		FuroBasePlana furo = new FuroBasePlana(id, 0.0, 0.0, 0.0, diametroFuro,profundidade);
		setPosicaoFuroReader(furo, round_hole);
		
		//tolerancia e rugosidade talvez nao sejam necessárias nessa etapa de leitura
		double tolerancia = ((EPlus_minus_value)round_hole.getDiameter(null).getImplicit_tolerance(null)).getUpper_limit(null);
		furo.setTolerancia(tolerancia);
		
		boolean b;
		
		if (round_hole.getBottom_condition(null).isKindOf(EThrough_bottom_condition.class))
			b = true;
		else b = false;
		
		furo.setPassante(b);
		
		return furo;
	}
	
	public static void setPosicaoFuroReader(Furo furo, ERound_hole round_hole) throws SdaiException{

		double x = 0, y = 0, z = 0;
		
		Face faceAtual = FaceReader.getFace(round_hole);
		
		if(faceAtual.getTipo()==Face.XY){
			
			x = round_hole.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(1);

			y = round_hole.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(2);

			double alturaBloco = ((EBlock)round_hole.getIts_workpiece(null).getIts_bounding_geometry(null)).getZ(null);

			z = round_hole.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(3)*(-1) + alturaBloco;
			
//			System.out.println("Profundidade Face : " + faceAtual.getProfundidadeMaxima() );
//			System.out.println("Comprimento Face : " + faceAtual.getComprimento() );
//			System.out.println("Largura Face : " + faceAtual.getLargura() );
			
//			System.out.println("Index(1) : " + round_hole.getFeature_placement(null).getLocation(null)
//			.getCoordinates(null).getByIndex(1));
			
//			System.out.println(x+","+y+","+z);
			
		}else if(faceAtual.getTipo()==Face.YX){
		
			x = round_hole.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(1);
			
			y = faceAtual.getLargura() - round_hole.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(2);
			
			z = round_hole.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(3);
			
		}else if(faceAtual.getTipo()==Face.YZ){

			x = faceAtual.getComprimento() - round_hole.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(3);

			y = round_hole.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(2);

			z = faceAtual.getProfundidadeMaxima() - round_hole.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(1);
			
		}else if(faceAtual.getTipo()==Face.ZY){
		
			x = round_hole.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(3);
			
			y = round_hole.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(2);
			
			z = round_hole.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(1);
			
		}else if(faceAtual.getTipo()==Face.XZ){
		
			x = round_hole.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(1);
			
			y = round_hole.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(3);
			
			z = round_hole.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(2);
			
		}else if(faceAtual.getTipo()==Face.ZX){
		
			x = round_hole.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(1);
			
			y = faceAtual.getLargura() - round_hole.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(3);
			
			z = faceAtual.getProfundidadeMaxima() - round_hole.getFeature_placement(null).getLocation(null)
			.getCoordinates(null).getByIndex(2);
			
		}
		
		furo.setPosicao(x, y, z);

		
	}

}
