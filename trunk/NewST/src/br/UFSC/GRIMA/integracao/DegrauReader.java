package br.UFSC.GRIMA.integracao;

import jsdai.SCombined_schema.EBlock;
import jsdai.SCombined_schema.ELinear_path;
import jsdai.SCombined_schema.EPlus_minus_value;
import jsdai.SCombined_schema.EStep;
import jsdai.lang.SdaiException;
import br.UFSC.GRIMA.entidades.features.Degrau;

public class DegrauReader {
	
	static double larguraDegrau, comprimentoDegrau;
	static int eixoDegrau;
	static double x , y , z = 0;
	
	public static Degrau getDegrau(EStep step) throws SdaiException {
		
		String id = step.getIts_id(null);
		
		x = step.getFeature_placement(null).getLocation(null)
				.getCoordinates(null).getByIndex(1);
		y = step.getFeature_placement(null).getLocation(null)
				.getCoordinates(null).getByIndex(2);
		
		double alturaBloco = ((EBlock)step.getIts_workpiece(null).getIts_bounding_geometry(null)).getZ(null);
		
		z = step.getFeature_placement(null).getLocation(null)
				.getCoordinates(null).getByIndex(3)*(-1) + alturaBloco;
		
		double profundidadeDegrau = step.getDepth(null).getPosition(null).getLocation(null).getCoordinates(null).getByIndex(3)*(-1);
//		double profundidadeDegrau = 5;

		double xDir = step.getFeature_placement(null).getRef_direction(null).getDirection_ratios(null).getByIndex(1);
		double yDir = step.getFeature_placement(null).getRef_direction(null).getDirection_ratios(null).getByIndex(2);
		double zDir = step.getFeature_placement(null).getRef_direction(null).getDirection_ratios(null).getByIndex(3);
		
		System.out.println("REF DIRECTION : " + "( "+xDir+" , "+yDir+" , "+zDir+" )");

		EBlock block = (EBlock) step.getIts_workpiece(null).getIts_bounding_geometry(null);
		
		System.out.println("POSICAO FEATURE : " + "( "+x+" , "+y+" , "+z+" )");
		
		System.out.println("LARGURA DEGRAU : " + larguraDegrau);
		System.out.println("PROFUNDIDADE DEGRAU : " + profundidadeDegrau);
		System.out.println("EIXO DEGRAU : " + eixoDegrau);
		
		
		getEixoELarguraDegrau(xDir, yDir, zDir, block.getX(null), block.getY(null));
				
		Degrau degrau = new Degrau(id, eixoDegrau, x, y, z, larguraDegrau, profundidadeDegrau);
		degrau.setComprimento(comprimentoDegrau);
//		double tolerancia = ((EPlus_minus_value)step.getWall_boundary(null).getProfile_radius(null).getImplicit_tolerance(null)).getUpper_limit(null);
		
		double tolerancia = ((EPlus_minus_value)((ELinear_path)step.getOpen_boundary(null)).getDistance(null).getImplicit_tolerance(null)).getUpper_limit(null);

		
		degrau.setTolerancia(tolerancia);
		
		return degrau;
	}
	
	public static void getEixoELarguraDegrau(double refX, double refY, double refZ, double comprimentoBloco, double larguraBloco){
		
		if(refX ==-1 && refY == 0 && refZ == 0 ){//degrau vertical, lado esquerdo
			
			eixoDegrau = Degrau.VERTICAL;
			larguraDegrau = x;
			comprimentoDegrau = larguraBloco;
			x=0.0;
			y=0.0;
				
		}else if (refX ==1 && refY == 0 && refZ == 0 ){// degrau vertical, lado direito
			
			eixoDegrau = Degrau.VERTICAL;
			larguraDegrau =  comprimentoBloco - x;
			comprimentoDegrau = larguraBloco;
		}else if (refX ==0 && refY == -1 && refZ == 0 ){// degrau horizontal, lado inferior
			
			eixoDegrau = Degrau.HORIZONTAL;
			larguraDegrau = y;
			comprimentoDegrau = comprimentoBloco;
			y= 0.0;
				
		}else if (refX ==0 && refY == 1 && refZ == 0 ){//degrau horizontal, lado superior
			
			eixoDegrau = Degrau.HORIZONTAL;
			larguraDegrau = larguraBloco - y;
			comprimentoDegrau = comprimentoBloco;

			x= 0.0;
				
		}else{
			
			System.out.println("Eixo RefDir Desconhecido (DegrauReader): "+"( "+refX+" , "+refY+" , "+refZ+" )");
			
		}//else nas outras faces fica tudo errado
		
	}

}
