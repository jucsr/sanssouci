package br.UFSC.GRIMA.integracao;

import java.util.ArrayList;

import jsdai.SCombined_schema.EAxis2_placement_3d;
import jsdai.SCombined_schema.EBlock;
import jsdai.SCombined_schema.EMachining_feature;
import jsdai.SCombined_schema.EWorkpiece;
import jsdai.lang.SdaiException;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Face;

public class BlocoReader {

	private Bloco bloco;
	private EBlock eBlock;
	private String blockName;
	private EAxis2_placement_3d blockPosition;
	private double comprimento; 	//x
	private double largura; 		//y
	private double profundidade; 	//z
	private String material;
	private double toleranciaGlobal;
	
	public BlocoReader(EWorkpiece eWorkpiece) throws SdaiException {		
		
		eBlock = (EBlock) eWorkpiece.getIts_bounding_geometry(null);
		blockName = eBlock.getName(null);
		blockPosition = eBlock.getPosition(null);
		comprimento = eBlock.getX(null);
		largura = eBlock.getY(null);
		profundidade = eBlock.getZ(null);
		material = eWorkpiece.getIts_material(null).getStandard_identifier(null);
		toleranciaGlobal = eWorkpiece.getGlobal_tolerance(null);
		
	}

	public Bloco getBloco(){
		
		this.bloco = new Bloco(this.comprimento,this.largura,this.profundidade);
		
		return this.bloco;
	}
	
	public String getNome() throws SdaiException {
		return blockName;
	}

	public EAxis2_placement_3d getPosition() throws SdaiException {
		return blockPosition;
	}

	public double getComprimento() throws SdaiException {
		return comprimento;
	}

	public double getLargura() throws SdaiException {
		return largura;
	}

	public double getProfundidade() throws SdaiException {
		return profundidade;
	}
	
	public String getMaterial() throws SdaiException {
		return material;
	}
	
	public double getToleranciaGlobal() throws SdaiException {
		return toleranciaGlobal;
	}
	
	public int getFaceFeature(EMachining_feature feature) throws SdaiException{
		
		int faceFeature = 6;
		
		double zLinhaX = feature.getFeature_placement(null).getAxis(null).getDirection_ratios(null).getByIndex(1);
		double zLinhaY = feature.getFeature_placement(null).getAxis(null).getDirection_ratios(null).getByIndex(2);
		double zLinhaZ = feature.getFeature_placement(null).getAxis(null).getDirection_ratios(null).getByIndex(3);
		
		ArrayList<Double> eixoFeature = new ArrayList<Double>();
		eixoFeature.add(zLinhaX);
		eixoFeature.add(zLinhaY);
		eixoFeature.add(zLinhaZ);
		
		ArrayList<Double> eixoXY = new ArrayList<Double>();
		eixoXY.add(0.0);
		eixoXY.add(0.0);
		eixoXY.add(1.0);
		
		ArrayList<Double> eixoYX = new ArrayList<Double>();
		eixoYX.add(0.0);
		eixoYX.add(0.0);
		eixoYX.add(-1.0);
		
		ArrayList<Double> eixoXZ = new ArrayList<Double>();
		eixoXZ.add(0.0);
		eixoXZ.add(-1.0);
		eixoXZ.add(0.0);
		
		ArrayList<Double> eixoZX = new ArrayList<Double>();
		eixoZX.add(0.0);
		eixoZX.add(1.0);
		eixoZX.add(0.0);
		
		ArrayList<Double> eixoYZ = new ArrayList<Double>();
		eixoYZ.add(1.0);
		eixoYZ.add(0.0);
		eixoYZ.add(0.0);
		
		ArrayList<Double> eixoZY = new ArrayList<Double>();
		eixoZY.add(-1.0);
		eixoZY.add(0.0);
		eixoZY.add(0.0);
		
		if(eixoFeature.toString().equals(eixoXY.toString()))
			faceFeature = Face.XY;
		else if(eixoFeature.toString().equals(eixoYX.toString()))
			faceFeature = Face.YX;
		else if(eixoFeature.toString().equals(eixoXZ.toString()))
			faceFeature = Face.XZ;
		else if(eixoFeature.toString().equals(eixoZX.toString()))
			faceFeature = Face.ZX;
		else if(eixoFeature.toString().equals(eixoZY.toString()))
			faceFeature = Face.ZY;
		else if(eixoFeature.toString().equals(eixoYZ.toString()))
			faceFeature = Face.YZ;
		
		return faceFeature;
		
	}
	

}
