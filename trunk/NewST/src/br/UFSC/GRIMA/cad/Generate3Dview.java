package br.UFSC.GRIMA.cad;

import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.cad.visual.Frame3D;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.CavidadeFundoArredondado;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.Degrau;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.Furo;
import br.UFSC.GRIMA.entidades.features.FuroBaseArredondada;
import br.UFSC.GRIMA.entidades.features.FuroBaseConica;
import br.UFSC.GRIMA.entidades.features.FuroBaseEsferica;
import br.UFSC.GRIMA.entidades.features.FuroBasePlana;
import br.UFSC.GRIMA.entidades.features.FuroConico;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilBezier;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilCircularParcial;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilQuadradoU;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilRoundedU;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilVee;
import br.UFSC.GRIMA.j3d.InvalidBooleanOperationException;
import br.UFSC.GRIMA.j3d.J3D;
import br.UFSC.GRIMA.operationSolids.CSGSolid;
import br.UFSC.GRIMA.operationSolids.CompoundSolid;
import br.UFSC.GRIMA.operationSolids.OperationBezierProfile;
import br.UFSC.GRIMA.operationSolids.OperationBlock;
import br.UFSC.GRIMA.operationSolids.OperationConicalHoleBottom;
import br.UFSC.GRIMA.operationSolids.OperationCylinder_1;
import br.UFSC.GRIMA.operationSolids.OperationPocket_1;
import br.UFSC.GRIMA.operationSolids.OperationRoundFloorPocket;
import br.UFSC.GRIMA.operationSolids.OperationRoundHoleBottom;
import br.UFSC.GRIMA.operationSolids.OperationRoundedUProfile;
import br.UFSC.GRIMA.operationSolids.OperationSlotCircularProfile;
import br.UFSC.GRIMA.operationSolids.OperationSlotSquareUProfile;
import br.UFSC.GRIMA.operationSolids.OperationSlotVeeProfile;
import br.UFSC.GRIMA.operationSolids.OperationSphericalHoleBottom;
import br.UFSC.GRIMA.operationSolids.OperationTaperHole;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class Generate3Dview extends Frame3D 
{
	private Projeto projeto;
	J3D j3d;
	CSGSolid rawBlock = null;
	
	public Generate3Dview(Projeto projeto) 
	{
		this.projeto = projeto;
		this.setSize(600, 400);
		this.j3d = new J3D(this.panel3D);
		this.createCoordinateSystem();
		this.createBlock();
	}

	private void createCoordinateSystem() 
	{
		Bloco bloco = (Bloco) projeto.getBloco();
		float ray = (float)(bloco.getComprimento() + bloco.getLargura() + bloco.getProfundidade())/850, 
		tam = (float)(bloco.getComprimento() + bloco.getLargura() + bloco.getProfundidade())/20;

		OperationCylinder_1 axisX = new OperationCylinder_1("", ray, tam, 3, new Color3f(0, 0.1f, 0.6f));
		axisX.rotate(0, 0, Math.PI/2);
		axisX.translate(-bloco.getComprimento()/2 + tam/2, bloco.getProfundidade()/2, bloco.getLargura()/2);
		
		OperationCylinder_1 axisY = new OperationCylinder_1("", ray, tam, 3, new Color3f(0.6f, 0.1f, 0.0f));
		axisY.rotate(Math.PI/2, 0);
		axisY.translate(-bloco.getComprimento()/2, bloco.getProfundidade()/2, bloco.getLargura()/2 - tam/2);

		OperationCylinder_1 axisZ = new OperationCylinder_1("", ray, tam, 3 , new Color3f(0.1f, 0.6f, 0));
		axisZ.translate(-bloco.getComprimento()/2, bloco.getProfundidade()/2 + tam/2, bloco.getLargura()/2);
		this.j3d.addSolid(axisX);
		this.j3d.addSolid(axisY);
		this.j3d.addSolid(axisZ);
	}
	
	private void createBlock() 
	{
		float length = (float) projeto.getBloco().getComprimento();
		float width = (float) projeto.getBloco().getLargura();
		float height = (float) projeto.getBloco().getProfundidade();
		if (rawBlock == null)
		{	
			CSGSolid.appearance = true;
			rawBlock = new OperationBlock("", width, height, length);
			this.createFeatures();
			this.j3d.addSolid(rawBlock);
		}
	}

	/**
	 * 
	 */
	private void createFeatures() 
	{
		for (int i = 0; i < projeto.getBloco().faces.size(); i++) 
		{
			Face faceTmp = (Face) projeto.getBloco().faces.elementAt(i);
			switch(faceTmp.getTipo())
			{
			case Face.XY:
				for (int j = 0; j < faceTmp.features.size(); j++)
				{
					Feature feature = (Feature)faceTmp.features.elementAt(j);
					switch(feature.getTipo())
					{
					case Feature.FURO:
					
						if (feature.getClass() == FuroBasePlana.class)
						{
							Furo furoTmp = (Furo) faceTmp.features.elementAt(j);	
							int numberOfPoints = 4 * 4;
							if(furoTmp.getRaio() < 10)
							{
								numberOfPoints = 16;
							} else if(furoTmp.getRaio() >= 10 && furoTmp.getRaio() < 30)
							{
								numberOfPoints = 20;
							} else if(furoTmp.getRaio() >= 30)
							{
								numberOfPoints = 25;
							}
							OperationCylinder_1 hole = new OperationCylinder_1("",  (float) furoTmp.getRaio(), 
									(float) furoTmp.getProfundidade(), numberOfPoints);
							hole.translate( - faceTmp.getComprimento()/2 + furoTmp.X, 
									faceTmp.getProfundidadeMaxima()/2- furoTmp.Z- furoTmp.getProfundidade()/2, 
									faceTmp.getLargura()/2 - furoTmp.Y);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, hole);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						} else if (feature.getClass() == FuroBaseConica.class)
						{
							FuroBaseConica furoTmp = (FuroBaseConica)faceTmp.features.elementAt(j);
							
							OperationConicalHoleBottom hole = new OperationConicalHoleBottom("", (float)furoTmp.getProfundidade(), (float)furoTmp.getDiametro(), (float)(furoTmp.getTipAngle()));
							hole.translate( - faceTmp.getComprimento()/2 + furoTmp.X,
									-furoTmp.Z + faceTmp.getProfundidadeMaxima()/2, 
									faceTmp.getLargura()/2 - furoTmp.Y);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, hole);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						} else if (feature.getClass() == FuroBaseEsferica.class)
						{
							FuroBaseEsferica furoTmp = (FuroBaseEsferica)faceTmp.features.elementAt(j);
							OperationSphericalHoleBottom hole = new OperationSphericalHoleBottom("", (float)furoTmp.getProfundidade(), (float)furoTmp.getDiametro());
							hole.translate( - faceTmp.getComprimento()/2 + furoTmp.X, 
									-furoTmp.Z + faceTmp.getProfundidadeMaxima()/2, 
									faceTmp.getLargura()/2 - furoTmp.Y);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, hole);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						} else if(feature.getClass() == FuroBaseArredondada.class)
						{
							FuroBaseArredondada furoTmp = (FuroBaseArredondada)faceTmp.features.elementAt(j);
							OperationRoundHoleBottom hole = new OperationRoundHoleBottom("", (float)furoTmp.getProfundidade(), (float)furoTmp.getDiametro(), (float)furoTmp.getR1());
							hole.translate( - faceTmp.getComprimento()/2 + furoTmp.X, 
									-furoTmp.Z + faceTmp.getProfundidadeMaxima()/2, 
									faceTmp.getLargura()/2 - furoTmp.Y);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, hole);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						} else if(feature.getClass() == FuroConico.class)
						{
							FuroConico furoTmp = (FuroConico)faceTmp.features.elementAt(j);
							OperationTaperHole hole = new OperationTaperHole("", (float)furoTmp.getProfundidade(), (float)furoTmp.getDiametro(), (float)furoTmp.getRaio1() * 2);
							hole.translate( - faceTmp.getComprimento()/2 + furoTmp.X, 
									-furoTmp.Z + faceTmp.getProfundidadeMaxima()/2, 
									faceTmp.getLargura()/2 - furoTmp.Y);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, hole);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						}
						break;
						
					case Feature.RANHURA:
						if (feature.getClass() == Ranhura.class)
						{
							Ranhura ranhuraTmp = (Ranhura) faceTmp.features.elementAt(j);
						    if (ranhuraTmp.getEixo()== Ranhura.HORIZONTAL)
						    {						    	
						    	OperationBlock ranhura = new OperationBlock("", (float) ranhuraTmp.getLargura()  , 
										(float) ranhuraTmp.getProfundidade(), (float)faceTmp.getComprimento());
								ranhura.translate(ranhuraTmp.X , 
										faceTmp.getProfundidadeMaxima()/2- ranhuraTmp.Z- ranhuraTmp.getProfundidade()/2, 
										faceTmp.getLargura()/2 - ranhuraTmp.Y - ranhuraTmp.getLargura()/2);
						    	
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
						    	
						    } else if (ranhuraTmp.getEixo()== Ranhura.VERTICAL) {
						    	
						    	OperationBlock ranhura = new OperationBlock("", (float)faceTmp.getLargura(), 
										(float) ranhuraTmp.getProfundidade(), (float) ranhuraTmp.getLargura());
								
						    	ranhura.translate(ranhuraTmp.X - faceTmp.getComprimento()/2 + ranhuraTmp.getLargura()/2, 
										faceTmp.getProfundidadeMaxima()/2- ranhuraTmp.Z- ranhuraTmp.getProfundidade()/2, 
										ranhuraTmp.Y);
						    	
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
						    }
						} else if(feature.getClass() == RanhuraPerfilQuadradoU.class)
						{
							RanhuraPerfilQuadradoU ranhuraTmp = (RanhuraPerfilQuadradoU)faceTmp.features.elementAt(j);
							if (ranhuraTmp.getEixo() == Ranhura.HORIZONTAL)
							{
								OperationSlotSquareUProfile ranhura = new OperationSlotSquareUProfile("", (float)faceTmp.getComprimento(), (float)ranhuraTmp.getLargura2(), (float)ranhuraTmp.getProfundidade(), (float)ranhuraTmp.getRaio(), (float)ranhuraTmp.getRaio(), (float)ranhuraTmp.getAngulo(), (float)ranhuraTmp.getAngulo());
								ranhura.rotate(0, Math.PI/2);
								ranhura.translate(ranhuraTmp.X + faceTmp.getComprimento()/2, -ranhuraTmp.Z + faceTmp.getProfundidadeMaxima()/2, faceTmp.getLargura()/2 -ranhuraTmp.Y - ranhuraTmp.getLargura()/2);
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							} else if(ranhuraTmp.getEixo() == Ranhura.VERTICAL)
							{
								OperationSlotSquareUProfile ranhura = new OperationSlotSquareUProfile("", (float)faceTmp.getComprimento(), (float)ranhuraTmp.getLargura2(), (float)ranhuraTmp.getProfundidade(), (float)ranhuraTmp.getRaio(), (float)ranhuraTmp.getRaio(), (float)ranhuraTmp.getAngulo(), (float)ranhuraTmp.getAngulo());
								ranhura.translate(ranhuraTmp.X - faceTmp.getComprimento()/2 + ranhuraTmp.getLargura()/2, -ranhuraTmp.Z + faceTmp.getProfundidadeMaxima()/2, faceTmp.getLargura()/2 -ranhuraTmp.Y);
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							}
						} else if(feature.getClass() == RanhuraPerfilCircularParcial.class)
						{
							RanhuraPerfilCircularParcial ranhuraTmp = (RanhuraPerfilCircularParcial)faceTmp.features.elementAt(j);
							float initialAngle = (float)(Math.asin(ranhuraTmp.getDz()/ranhuraTmp.getRaio()) * 180 / Math.PI);
							float sweepAngle = (float)(180 - 2 * initialAngle);
							if (ranhuraTmp.getEixo() == Ranhura.HORIZONTAL)
							{
								OperationSlotCircularProfile ranhura = new OperationSlotCircularProfile("", (float)faceTmp.getComprimento(), (float)ranhuraTmp.getRaio(), initialAngle, sweepAngle);
								ranhura.rotate(0, Math.PI/2);
								ranhura.translate(ranhuraTmp.X + faceTmp.getComprimento()/2, +ranhuraTmp.getDz() + faceTmp.getProfundidadeMaxima()/2 - ranhuraTmp.Z, faceTmp.getLargura()/2 -ranhuraTmp.getLocY());
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							} else if (ranhuraTmp.getEixo() == Ranhura.VERTICAL)
							{
								OperationSlotCircularProfile ranhura = new OperationSlotCircularProfile("", (float)faceTmp.getLargura(), (float)ranhuraTmp.getRaio(), initialAngle, sweepAngle);
								ranhura.translate(-faceTmp.getComprimento()/2 + ranhuraTmp.getLocX(), ranhuraTmp.getDz() + faceTmp.getProfundidadeMaxima()/2 - ranhuraTmp.Z, faceTmp.getLargura()/2 - ranhuraTmp.getLocY());
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							}
						} else if(feature.getClass() == RanhuraPerfilRoundedU.class)
						{
							RanhuraPerfilRoundedU ranhuraTmp = (RanhuraPerfilRoundedU)faceTmp.features.elementAt(j);
							if(ranhuraTmp.getEixo() == Ranhura.HORIZONTAL)
							{
								OperationRoundedUProfile ranhura = new OperationRoundedUProfile("", (float)(ranhuraTmp.getProfundidade()), (float)ranhuraTmp.getLargura(), (float)faceTmp.getComprimento());
								ranhura.rotate(0, Math.PI/2);
								ranhura.translate(ranhuraTmp.X + faceTmp.getComprimento()/2, faceTmp.getProfundidadeMaxima()/2 - ranhuraTmp.getProfundidade() - ranhuraTmp.Z, faceTmp.getLargura()/2 - ranhuraTmp.getLocY());
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							} else if (ranhuraTmp.getEixo() == Ranhura.VERTICAL)
							{
								OperationRoundedUProfile ranhura = new OperationRoundedUProfile("", (float)(ranhuraTmp.getProfundidade()), (float)ranhuraTmp.getLargura(), (float)faceTmp.getLargura());
								ranhura.translate(ranhuraTmp.getLocX() - faceTmp.getComprimento()/2, faceTmp.getProfundidadeMaxima()/2 - ranhuraTmp.getProfundidade() - ranhuraTmp.Z, faceTmp.getLargura()/2);
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							}
						} else if(feature.getClass() == RanhuraPerfilVee.class)
						{
							RanhuraPerfilVee ranhuraTmp = (RanhuraPerfilVee)faceTmp.features.elementAt(j);
							OperationSlotVeeProfile ranhura = new OperationSlotVeeProfile("", (float)faceTmp.getComprimento(), (float)ranhuraTmp.getProfundidade(), (float)ranhuraTmp.getRaio(), (float)(90 - ranhuraTmp.getAngulo()/2), (float)ranhuraTmp.getAngulo());
							if(ranhuraTmp.getEixo() == Ranhura.HORIZONTAL)
							{
								ranhura.rotate(0, Math.PI/2);
								ranhura.translate(faceTmp.getComprimento()/2, faceTmp.getProfundidadeMaxima()/2 - ranhuraTmp.getProfundidade() - ranhuraTmp.Z, faceTmp.getLargura() / 2 - ranhuraTmp.getLocY());
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							} else if(ranhuraTmp.getEixo() == Ranhura.VERTICAL)
							{
								ranhura.translate(-faceTmp.getComprimento() / 2 + ranhuraTmp.getLocX(), faceTmp.getProfundidadeMaxima()/2 - ranhuraTmp.getProfundidade() - ranhuraTmp.Z, faceTmp.getComprimento() / 2);
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							}
						} else if(feature.getClass() == RanhuraPerfilBezier.class)
						{
							RanhuraPerfilBezier ranhuraTmp = (RanhuraPerfilBezier)faceTmp.features.elementAt(j);
							Point3d[] controlPoints = ranhuraTmp.getPontosDeControle();
							OperationBezierProfile ranhura = new OperationBezierProfile("", controlPoints);

							if (ranhuraTmp.getEixo() == Ranhura.HORIZONTAL)
							{
								ranhura.rotate(0, Math.PI/2);
								ranhura.translate(0, faceTmp.getProfundidadeMaxima()/2, faceTmp.getLargura()/2);
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							} else if (ranhuraTmp.getEixo() == Ranhura.VERTICAL)
							{
								ranhura.translate(-faceTmp.getComprimento()/2, faceTmp.getProfundidadeMaxima()/2 , 0);
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							}
						}
						
						break;
						
					case Feature.DEGRAU:
						Degrau degrauTmp = (Degrau)faceTmp.features.elementAt(j);
						if(degrauTmp.getEixo() == Degrau.HORIZONTAL)
						{
							OperationBlock degrau = new OperationBlock("", (float)degrauTmp.getLargura(), (float)degrauTmp.getProfundidade(), (float)faceTmp.getComprimento());
							degrau.translate(degrauTmp.X, faceTmp.getProfundidadeMaxima()/2- degrauTmp.Z- degrauTmp.getProfundidade()/2, faceTmp.getLargura()/2 - degrauTmp.Y - degrauTmp.getLargura()/2);
							try {
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, degrau);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						}else if(degrauTmp.getEixo() == Degrau.VERTICAL)
						{
							OperationBlock degrau = new OperationBlock("", (float)faceTmp.getLargura(), (float)degrauTmp.getProfundidade(), (float)degrauTmp.getLargura());
							degrau.translate(degrauTmp.X - faceTmp.getComprimento()/2 + degrauTmp.getLargura()/2, faceTmp.getProfundidadeMaxima()/2- degrauTmp.Z - degrauTmp.getProfundidade()/2, degrauTmp.Y);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, degrau);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						}
						break;
					case Feature.CAVIDADE:
						Cavidade cavidadeTmp = (Cavidade)faceTmp.features.elementAt(j);
						OperationPocket_1 pocket = new OperationPocket_1("", (float)cavidadeTmp.getComprimento(), (float)cavidadeTmp.getLargura(), (float)cavidadeTmp.getProfundidade(), (float)cavidadeTmp.getRaio());
						pocket.translate(-faceTmp.getComprimento()/2 + cavidadeTmp.X + cavidadeTmp.getComprimento()/2, faceTmp.getProfundidadeMaxima()/2 - cavidadeTmp.Z - cavidadeTmp.getProfundidade()/2, +faceTmp.getLargura()/2 - cavidadeTmp.getLargura()/2 - cavidadeTmp.Y);
						try 
						{
							rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, pocket);
						} catch (InvalidBooleanOperationException e) {
							e.printStackTrace();
						}
						break;
					case Feature.CAVIDADE_FUNDO_ARREDONDADO:
						CavidadeFundoArredondado cfaTmp = (CavidadeFundoArredondado)faceTmp.features.elementAt(j);
						OperationRoundFloorPocket opRoundPocket = new OperationRoundFloorPocket("", (float)cfaTmp.getComprimento(), (float)cfaTmp.getLargura(), (float)cfaTmp.getProfundidade(), (float)cfaTmp.getVerticeRaio(), (float)cfaTmp.getVerticeRaio(), (float)cfaTmp.getVerticeRaio(), (float)cfaTmp.getVerticeRaio(), (float)cfaTmp.getFundoRaio());
						opRoundPocket.translate(-faceTmp.getComprimento()/2 + cfaTmp.X + cfaTmp.getComprimento()/2, faceTmp.getProfundidadeMaxima()/2 - cfaTmp.Z - cfaTmp.getProfundidade()/2, + faceTmp.getLargura()/2 - cfaTmp.getLargura()/2 - cfaTmp.Y);
						try 
						{
							this.rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, this.rawBlock , opRoundPocket);
						} catch (InvalidBooleanOperationException e) 
						{
							e.printStackTrace();
						}
						break;
					case Feature.BOSS:
						if (feature.getClass() == CircularBoss.class)
						{
							CircularBoss bossTmp = (CircularBoss) faceTmp.features.elementAt(j);	
							
							OperationTaperHole boss = new OperationTaperHole("",  
									(float) bossTmp.getAltura(), (float) bossTmp.getDiametro1(),(float) bossTmp.getDiametro2());
							boss.translate( - faceTmp.getComprimento()/2 + bossTmp.X, 
									faceTmp.getProfundidadeMaxima()/2- bossTmp.Z, 
									faceTmp.getLargura()/2 - bossTmp.Y);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.UNION, rawBlock, boss);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						}
//						else if(feature.getClass() == RetangularBoss.class)
//						{
							
							
						
						break;
						
						default:
							break;
					}
				}
				break;
			case Face.XZ:
				for (int j = 0; j < faceTmp.features.size(); j++)
				{
					Feature feature = (Feature)faceTmp.features.elementAt(j);
					switch(feature.getTipo())
					{
					case Feature.FURO:
					
						if (feature.getClass() == FuroBasePlana.class)
						{
							Furo furoTmp = (Furo) faceTmp.features.elementAt(j);	
							int numberOfPoints = 4 * 4;
							if(furoTmp.getRaio() < 10)
							{
								numberOfPoints = 16;
							} else if(furoTmp.getRaio() >= 10 && furoTmp.getRaio() < 30)
							{
								numberOfPoints = 20;
							} else if(furoTmp.getRaio() >= 30)
							{
								numberOfPoints = 25;
							}
							OperationCylinder_1 hole = new OperationCylinder_1("",  (float) furoTmp.getRaio(), 
									(float) furoTmp.getProfundidade(), numberOfPoints);
							hole.rotate(Math.PI/2,	 0, 0);
							hole.translate( - faceTmp.getComprimento()/2 + furoTmp.X, 
									-faceTmp.getLargura()/2 + furoTmp.Y, 
									faceTmp.getProfundidadeMaxima()/2 - furoTmp.Z - furoTmp.getProfundidade()/2);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, hole);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						} else if (feature.getClass() == FuroBaseConica.class)
						{
							FuroBaseConica furoTmp = (FuroBaseConica)faceTmp.features.elementAt(j);
							
							OperationConicalHoleBottom hole = new OperationConicalHoleBottom("", (float)furoTmp.getProfundidade(), (float)furoTmp.getDiametro(), (float)(furoTmp.getTipAngle()));
							hole.rotate(Math.PI/2,	 0, 0);
							hole.translate( - faceTmp.getComprimento()/2 + furoTmp.X, 
									-faceTmp.getLargura()/2 + furoTmp.Y, 
									faceTmp.getProfundidadeMaxima()/2 - furoTmp.Z);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, hole);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						} else if (feature.getClass() == FuroBaseEsferica.class)
						{
							FuroBaseEsferica furoTmp = (FuroBaseEsferica)faceTmp.features.elementAt(j);
							OperationSphericalHoleBottom hole = new OperationSphericalHoleBottom("", (float)furoTmp.getProfundidade(), (float)furoTmp.getDiametro());
							hole.rotate(Math.PI/2,	 0, 0);
							hole.translate( - faceTmp.getComprimento()/2 + furoTmp.X, 
									-faceTmp.getLargura()/2 + furoTmp.Y, 
									faceTmp.getProfundidadeMaxima()/2 - furoTmp.Z);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, hole);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						} else if(feature.getClass() == FuroBaseArredondada.class)
						{
							FuroBaseArredondada furoTmp = (FuroBaseArredondada)faceTmp.features.elementAt(j);
							OperationRoundHoleBottom hole = new OperationRoundHoleBottom("", (float)furoTmp.getProfundidade(), (float)furoTmp.getDiametro(), (float)furoTmp.getR1());
							hole.rotate(Math.PI/2,	 0, 0);
							hole.translate( - faceTmp.getComprimento()/2 + furoTmp.X, 
									-faceTmp.getLargura()/2 + furoTmp.Y, 
									faceTmp.getProfundidadeMaxima()/2 - furoTmp.Z);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, hole);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						} else if(feature.getClass() == FuroConico.class)
						{
							FuroConico furoTmp = (FuroConico)faceTmp.features.elementAt(j);
							OperationTaperHole hole = new OperationTaperHole("", (float)furoTmp.getProfundidade(), (float)furoTmp.getDiametro(), (float)furoTmp.getRaio1() * 2);
							hole.rotate(Math.PI/2,	 0, 0);
							hole.translate( - faceTmp.getComprimento()/2 + furoTmp.X, 
									-faceTmp.getLargura()/2 + furoTmp.Y, 
									faceTmp.getProfundidadeMaxima()/2 - furoTmp.Z);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, hole);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						}
						break;
						
					case Feature.RANHURA:
						if (feature.getClass() == Ranhura.class)
						{
							Ranhura ranhuraTmp = (Ranhura) faceTmp.features.elementAt(j);
						    if (ranhuraTmp.getEixo()== Ranhura.HORIZONTAL)
						    {						    	
						    	OperationBlock ranhura = new OperationBlock("", (float) ranhuraTmp.getLargura()  , 
										(float) ranhuraTmp.getProfundidade(), (float)faceTmp.getComprimento());
						    	ranhura.rotate(Math.PI/2,	 0, 0);
								ranhura.translate( 0, 
										faceTmp.getLargura()/2 - ranhuraTmp.Y - ranhuraTmp.getLargura()/2,
										faceTmp.getProfundidadeMaxima()/2 - ranhuraTmp.Z - ranhuraTmp.getProfundidade()/2);						    	
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
						    	
						    } else if (ranhuraTmp.getEixo()== Ranhura.VERTICAL) {
						    	
						    	OperationBlock ranhura = new OperationBlock("", (float)faceTmp.getLargura(), 
										(float) ranhuraTmp.getProfundidade(), (float) ranhuraTmp.getLargura());
						    	ranhura.rotate(Math.PI/2,	 0, 0);
								ranhura.translate( ranhuraTmp.X - faceTmp.getComprimento()/2 + ranhuraTmp.getLargura()/2, 
										0,
										faceTmp.getProfundidadeMaxima()/2 - ranhuraTmp.Z - ranhuraTmp.getProfundidade()/2);	
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
						    }
						} else if(feature.getClass() == RanhuraPerfilQuadradoU.class)
						{
							RanhuraPerfilQuadradoU ranhuraTmp = (RanhuraPerfilQuadradoU)faceTmp.features.elementAt(j);
							if (ranhuraTmp.getEixo() == Ranhura.HORIZONTAL)
							{
								OperationSlotSquareUProfile ranhura = new OperationSlotSquareUProfile("", (float)faceTmp.getComprimento(), (float)ranhuraTmp.getLargura2(), (float)ranhuraTmp.getProfundidade(), (float)ranhuraTmp.getRaio(), (float)ranhuraTmp.getRaio(), (float)ranhuraTmp.getAngulo(), (float)ranhuraTmp.getAngulo());
								ranhura.rotate(0, Math.PI/2);
								ranhura.rotate(Math.PI/2,	 0, 0);
								ranhura.translate(ranhuraTmp.X + faceTmp.getComprimento()/2, 
										-faceTmp.getLargura()/2 +ranhuraTmp.Y + ranhuraTmp.getLargura()/2,
										-ranhuraTmp.Z + faceTmp.getProfundidadeMaxima()/2);						    	
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							} else if(ranhuraTmp.getEixo() == Ranhura.VERTICAL)
							{
								OperationSlotSquareUProfile ranhura = new OperationSlotSquareUProfile("", (float)faceTmp.getComprimento(), (float)ranhuraTmp.getLargura2(), (float)ranhuraTmp.getProfundidade(), (float)ranhuraTmp.getRaio(), (float)ranhuraTmp.getRaio(), (float)ranhuraTmp.getAngulo(), (float)ranhuraTmp.getAngulo());
								ranhura.rotate(Math.PI/2,	 0, 0);
								ranhura.translate(ranhuraTmp.X - faceTmp.getComprimento()/2 + ranhuraTmp.getLargura()/2, 
										-faceTmp.getLargura()/2 -ranhuraTmp.Y,
										-ranhuraTmp.Z + faceTmp.getProfundidadeMaxima()/2);	
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							}
						} else if(feature.getClass() == RanhuraPerfilCircularParcial.class)
						{
							RanhuraPerfilCircularParcial ranhuraTmp = (RanhuraPerfilCircularParcial)faceTmp.features.elementAt(j);
							float initialAngle = (float)(Math.asin(ranhuraTmp.getDz()/ranhuraTmp.getRaio()) * 180 / Math.PI);
							float sweepAngle = (float)(180 - 2 * initialAngle);
							if (ranhuraTmp.getEixo() == Ranhura.HORIZONTAL)
							{
								OperationSlotCircularProfile ranhura = new OperationSlotCircularProfile("", (float)faceTmp.getComprimento(), (float)ranhuraTmp.getRaio(), initialAngle, sweepAngle);
								ranhura.rotate(0, Math.PI/2);
								ranhura.rotate(Math.PI/2,	 0, 0);
								ranhura.translate(ranhuraTmp.X + faceTmp.getComprimento()/2, 
										-faceTmp.getLargura()/2 +ranhuraTmp.getLocY(),
										-ranhuraTmp.Z + faceTmp.getProfundidadeMaxima()/2 + ranhuraTmp.getDz());
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							} else if (ranhuraTmp.getEixo() == Ranhura.VERTICAL)
							{
								OperationSlotCircularProfile ranhura = new OperationSlotCircularProfile("", (float)faceTmp.getLargura(), (float)ranhuraTmp.getRaio(), initialAngle, sweepAngle);
								ranhura.rotate(Math.PI/2,	 0, 0);
								ranhura.translate(-faceTmp.getComprimento()/2 + ranhuraTmp.getLocX(), 
										-faceTmp.getLargura()/2 +ranhuraTmp.getLocY(),
										-ranhuraTmp.Z + faceTmp.getProfundidadeMaxima()/2 + ranhuraTmp.getDz());
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							}
						} else if(feature.getClass() == RanhuraPerfilRoundedU.class)
						{
							RanhuraPerfilRoundedU ranhuraTmp = (RanhuraPerfilRoundedU)faceTmp.features.elementAt(j);
							if(ranhuraTmp.getEixo() == Ranhura.HORIZONTAL)
							{
								OperationRoundedUProfile ranhura = new OperationRoundedUProfile("", (float)(ranhuraTmp.getProfundidade()), (float)ranhuraTmp.getLargura(), (float)faceTmp.getComprimento());
								ranhura.rotate(0, Math.PI/2);
								ranhura.rotate(Math.PI/2,	 0, 0);
								ranhura.translate(ranhuraTmp.X + faceTmp.getComprimento()/2, 
										-faceTmp.getLargura()/2 +ranhuraTmp.getLocY(),
										faceTmp.getProfundidadeMaxima()/2 - ranhuraTmp.getProfundidade() - ranhuraTmp.Z);
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							} else if (ranhuraTmp.getEixo() == Ranhura.VERTICAL)
							{
								OperationRoundedUProfile ranhura = new OperationRoundedUProfile("", (float)(ranhuraTmp.getProfundidade()), (float)ranhuraTmp.getLargura(), (float)faceTmp.getLargura());
								ranhura.rotate(Math.PI/2,	 0, 0);
								ranhura.translate(ranhuraTmp.getLocX() - faceTmp.getComprimento()/2,
										-faceTmp.getLargura()/2 +ranhuraTmp.getLocY(),
										faceTmp.getProfundidadeMaxima()/2 - ranhuraTmp.getProfundidade() - ranhuraTmp.Z);
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							}
						} else if(feature.getClass() == RanhuraPerfilVee.class)
						{
							RanhuraPerfilVee ranhuraTmp = (RanhuraPerfilVee)faceTmp.features.elementAt(j);
							OperationSlotVeeProfile ranhura = new OperationSlotVeeProfile("", (float)faceTmp.getComprimento(), (float)ranhuraTmp.getProfundidade(), (float)ranhuraTmp.getRaio(), (float)(90 - ranhuraTmp.getAngulo()/2), (float)ranhuraTmp.getAngulo());
							if(ranhuraTmp.getEixo() == Ranhura.HORIZONTAL)
							{
								ranhura.rotate(0, Math.PI/2);
								ranhura.rotate(Math.PI/2,	 0, 0);
								ranhura.translate(ranhuraTmp.X + faceTmp.getComprimento()/2, 
										-faceTmp.getLargura()/2 +ranhuraTmp.getLocY(),
										faceTmp.getProfundidadeMaxima()/2 - ranhuraTmp.getProfundidade() - ranhuraTmp.Z);
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							} else if(ranhuraTmp.getEixo() == Ranhura.VERTICAL)
							{
								ranhura.rotate(Math.PI/2,	 0, 0);
								ranhura.translate(-faceTmp.getComprimento() / 2 + ranhuraTmp.getLocX(), 
										-faceTmp.getLargura()/2 +ranhuraTmp.getLocY(),
										faceTmp.getProfundidadeMaxima()/2 - ranhuraTmp.getProfundidade() - ranhuraTmp.Z);
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							}
						} else if(feature.getClass() == RanhuraPerfilBezier.class)
						{
							RanhuraPerfilBezier ranhuraTmp = (RanhuraPerfilBezier)faceTmp.features.elementAt(j);
							Point3d[] controlPoints = ranhuraTmp.getPontosDeControle();
							OperationBezierProfile ranhura = new OperationBezierProfile("", controlPoints);

							if (ranhuraTmp.getEixo() == Ranhura.HORIZONTAL)
							{
								ranhura.rotate(0, Math.PI/2);
								ranhura.rotate(Math.PI/2,	 0, 0);
								ranhura.translate(0, 
										-faceTmp.getLargura()/2,
										faceTmp.getProfundidadeMaxima()/2);
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							} else if (ranhuraTmp.getEixo() == Ranhura.VERTICAL)
							{
								ranhura.rotate(Math.PI/2,	 0, 0);
								ranhura.translate(-faceTmp.getComprimento()/2, 
										0,
										faceTmp.getProfundidadeMaxima()/2);
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							}
						}
						
						break;
						
					case Feature.DEGRAU:
						Degrau degrauTmp = (Degrau)faceTmp.features.elementAt(j);
						if(degrauTmp.getEixo() == Degrau.HORIZONTAL)
						{
							OperationBlock degrau = new OperationBlock("", (float)degrauTmp.getLargura(), (float)degrauTmp.getProfundidade(), (float)faceTmp.getComprimento());
							degrau.rotate(Math.PI/2,	 0, 0);
							degrau.translate(degrauTmp.X,
									-faceTmp.getLargura()/2 + degrauTmp.Y + degrauTmp.getLargura()/2,
									faceTmp.getProfundidadeMaxima()/2- degrauTmp.Z- degrauTmp.getProfundidade()/2);
							try {
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, degrau);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						}else if(degrauTmp.getEixo() == Degrau.VERTICAL)
						{
							OperationBlock degrau = new OperationBlock("", (float)faceTmp.getLargura(), (float)degrauTmp.getProfundidade(), (float)degrauTmp.getLargura());
							degrau.rotate(Math.PI/2,	 0, 0);
							degrau.translate(degrauTmp.X - faceTmp.getComprimento()/2 + degrauTmp.getLargura()/2,
									degrauTmp.Y,
									faceTmp.getProfundidadeMaxima()/2- degrauTmp.Z- degrauTmp.getProfundidade()/2);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, degrau);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						}
						break;
					case Feature.CAVIDADE:
						Cavidade cavidadeTmp = (Cavidade)faceTmp.features.elementAt(j);
						OperationPocket_1 pocket = new OperationPocket_1("", (float)cavidadeTmp.getComprimento(), (float)cavidadeTmp.getLargura(), (float)cavidadeTmp.getProfundidade(), (float)cavidadeTmp.getRaio());
						pocket.rotate(Math.PI/2,	 0, 0);
						pocket.translate(-faceTmp.getComprimento()/2 + cavidadeTmp.X + cavidadeTmp.getComprimento()/2 ,
								-faceTmp.getLargura()/2 + cavidadeTmp.getLargura()/2 + cavidadeTmp.Y,
								faceTmp.getProfundidadeMaxima()/2 - cavidadeTmp.Z - cavidadeTmp.getProfundidade()/2);
						try 
						{
							rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, pocket);
						} catch (InvalidBooleanOperationException e) {
							e.printStackTrace();
						}
						break;
					case Feature.CAVIDADE_FUNDO_ARREDONDADO:
						CavidadeFundoArredondado cfaTmp = (CavidadeFundoArredondado)faceTmp.features.elementAt(j);
						OperationRoundFloorPocket opRoundPocket = new OperationRoundFloorPocket("", (float)cfaTmp.getComprimento(), (float)cfaTmp.getLargura(), (float)cfaTmp.getProfundidade(), (float)cfaTmp.getVerticeRaio(), (float)cfaTmp.getVerticeRaio(), (float)cfaTmp.getVerticeRaio(), (float)cfaTmp.getVerticeRaio(), (float)cfaTmp.getFundoRaio());
						opRoundPocket.rotate(Math.PI/2,	 0, 0);
						opRoundPocket.translate(-faceTmp.getComprimento()/2 + cfaTmp.X + cfaTmp.getComprimento()/2,
								-faceTmp.getLargura()/2 + cfaTmp.getLargura()/2 + cfaTmp.Y,
								faceTmp.getProfundidadeMaxima()/2 - cfaTmp.Z - cfaTmp.getProfundidade()/2);
						try 
						{
							this.rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, this.rawBlock , opRoundPocket);
						} catch (InvalidBooleanOperationException e) 
						{
							e.printStackTrace();
						}
						break;
						default:
							break;
					}
				}
				break;
			case Face.YX:
				for (int j = 0; j < faceTmp.features.size(); j++)
				{
					Feature feature = (Feature)faceTmp.features.elementAt(j);
					switch(feature.getTipo())
					{
					case Feature.FURO:
					
						if (feature.getClass() == FuroBasePlana.class)
						{
							Furo furoTmp = (Furo) faceTmp.features.elementAt(j);	
							int numberOfPoints = 4 * 4;
							if(furoTmp.getRaio() < 10)
							{
								numberOfPoints = 16;
							} else if(furoTmp.getRaio() >= 10 && furoTmp.getRaio() < 30)
							{
								numberOfPoints = 20;
							} else if(furoTmp.getRaio() >= 30)
							{
								numberOfPoints = 25;
							}
							OperationCylinder_1 hole = new OperationCylinder_1("",  (float) furoTmp.getRaio(), 
									(float) furoTmp.getProfundidade(), numberOfPoints);
							hole.rotate(Math.PI,	 0, 0);
							hole.translate( - faceTmp.getComprimento()/2 + furoTmp.X, 
									- faceTmp.getProfundidadeMaxima()/2+ furoTmp.Z+ furoTmp.getProfundidade()/2, 
									- faceTmp.getLargura()/2 + furoTmp.Y);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, hole);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						} else if (feature.getClass() == FuroBaseConica.class)
						{
							FuroBaseConica furoTmp = (FuroBaseConica)faceTmp.features.elementAt(j);
							
							OperationConicalHoleBottom hole = new OperationConicalHoleBottom("", (float)furoTmp.getProfundidade(), (float)furoTmp.getDiametro(), (float)(furoTmp.getTipAngle()));
							hole.rotate(Math.PI,	 0, 0);
							hole.translate( - faceTmp.getComprimento()/2 + furoTmp.X, 
									- faceTmp.getProfundidadeMaxima()/2+ furoTmp.Z, 
									- faceTmp.getLargura()/2 + furoTmp.Y);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, hole);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						} else if (feature.getClass() == FuroBaseEsferica.class)
						{
							FuroBaseEsferica furoTmp = (FuroBaseEsferica)faceTmp.features.elementAt(j);
							OperationSphericalHoleBottom hole = new OperationSphericalHoleBottom("", (float)furoTmp.getProfundidade(), (float)furoTmp.getDiametro());
							hole.rotate(Math.PI,	 0, 0);
							hole.translate( - faceTmp.getComprimento()/2 + furoTmp.X, 
									furoTmp.Z - faceTmp.getProfundidadeMaxima()/2, 
									-faceTmp.getLargura()/2 + furoTmp.Y);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, hole);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						} else if(feature.getClass() == FuroBaseArredondada.class)
						{
							FuroBaseArredondada furoTmp = (FuroBaseArredondada)faceTmp.features.elementAt(j);
							OperationRoundHoleBottom hole = new OperationRoundHoleBottom("", (float)furoTmp.getProfundidade(), (float)furoTmp.getDiametro(), (float)furoTmp.getR1());
							hole.rotate(Math.PI,	 0, 0);
							hole.translate( - faceTmp.getComprimento()/2 + furoTmp.X, 
									furoTmp.Z - faceTmp.getProfundidadeMaxima()/2, 
									-faceTmp.getLargura()/2 + furoTmp.Y);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, hole);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						} else if(feature.getClass() == FuroConico.class)
						{
							FuroConico furoTmp = (FuroConico)faceTmp.features.elementAt(j);
							OperationTaperHole hole = new OperationTaperHole("", (float)furoTmp.getProfundidade(), (float)furoTmp.getDiametro(), (float)furoTmp.getRaio1() * 2);
							hole.rotate(Math.PI,	 0, 0);
							hole.translate( - faceTmp.getComprimento()/2 + furoTmp.X, 
									furoTmp.Z - faceTmp.getProfundidadeMaxima()/2, 
									-faceTmp.getLargura()/2 + furoTmp.Y);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, hole);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						}
						break;
						
					case Feature.RANHURA:
						if (feature.getClass() == Ranhura.class)
						{
							Ranhura ranhuraTmp = (Ranhura) faceTmp.features.elementAt(j);
						    if (ranhuraTmp.getEixo()== Ranhura.HORIZONTAL)
						    {						    	
						    	OperationBlock ranhura = new OperationBlock("", (float) ranhuraTmp.getLargura()  , 
										(float) ranhuraTmp.getProfundidade(), (float)faceTmp.getComprimento());
						    	ranhura.rotate(Math.PI, 0, 0);
								ranhura.translate(ranhuraTmp.X , 
										-faceTmp.getProfundidadeMaxima()/2+ ranhuraTmp.Z+ ranhuraTmp.getProfundidade()/2, 
										-faceTmp.getLargura()/2 + ranhuraTmp.Y + ranhuraTmp.getLargura()/2);
						    	
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
						    	
						    } else if (ranhuraTmp.getEixo()== Ranhura.VERTICAL) {
						    	
						    	OperationBlock ranhura = new OperationBlock("", (float)faceTmp.getLargura(), 
										(float) ranhuraTmp.getProfundidade(), (float) ranhuraTmp.getLargura());
						    	ranhura.rotate(Math.PI, 0, 0);
						    	ranhura.translate(ranhuraTmp.X - faceTmp.getComprimento()/2 + ranhuraTmp.getLargura()/2, 
										-faceTmp.getProfundidadeMaxima()/2+ ranhuraTmp.Z+ ranhuraTmp.getProfundidade()/2, 
										-ranhuraTmp.Y);
						    	
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
						    }
						} else if(feature.getClass() == RanhuraPerfilQuadradoU.class)
						{
							RanhuraPerfilQuadradoU ranhuraTmp = (RanhuraPerfilQuadradoU)faceTmp.features.elementAt(j);
							if (ranhuraTmp.getEixo() == Ranhura.HORIZONTAL)
							{
								OperationSlotSquareUProfile ranhura = new OperationSlotSquareUProfile("", (float)faceTmp.getComprimento(), (float)ranhuraTmp.getLargura2(), (float)ranhuraTmp.getProfundidade(), (float)ranhuraTmp.getRaio(), (float)ranhuraTmp.getRaio(), (float)ranhuraTmp.getAngulo(), (float)ranhuraTmp.getAngulo());
								ranhura.rotate(0, Math.PI/2);
								ranhura.rotate(Math.PI, 0, 0);
								ranhura.translate(ranhuraTmp.X + faceTmp.getComprimento()/2,
										ranhuraTmp.Z - faceTmp.getProfundidadeMaxima()/2,
										-faceTmp.getLargura()/2 + ranhuraTmp.Y + ranhuraTmp.getLargura()/2);
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							} else if(ranhuraTmp.getEixo() == Ranhura.VERTICAL)
							{
								OperationSlotSquareUProfile ranhura = new OperationSlotSquareUProfile("", (float)faceTmp.getComprimento(), (float)ranhuraTmp.getLargura2(), (float)ranhuraTmp.getProfundidade(), (float)ranhuraTmp.getRaio(), (float)ranhuraTmp.getRaio(), (float)ranhuraTmp.getAngulo(), (float)ranhuraTmp.getAngulo());
								ranhura.rotate(Math.PI, 0, 0);
								ranhura.translate(ranhuraTmp.X - faceTmp.getComprimento()/2 + ranhuraTmp.getLargura()/2,
										ranhuraTmp.Z - faceTmp.getProfundidadeMaxima()/2,
										-faceTmp.getLargura()/2 + ranhuraTmp.Y);
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							}
						} else if(feature.getClass() == RanhuraPerfilCircularParcial.class)
						{
							RanhuraPerfilCircularParcial ranhuraTmp = (RanhuraPerfilCircularParcial)faceTmp.features.elementAt(j);
							float initialAngle = (float)(Math.asin(ranhuraTmp.getDz()/ranhuraTmp.getRaio()) * 180 / Math.PI);
							float sweepAngle = (float)(180 - 2 * initialAngle);
							if (ranhuraTmp.getEixo() == Ranhura.HORIZONTAL)
							{
								OperationSlotCircularProfile ranhura = new OperationSlotCircularProfile("", (float)faceTmp.getComprimento(), (float)ranhuraTmp.getRaio(), initialAngle, sweepAngle);
								ranhura.rotate(0, Math.PI/2);
								ranhura.rotate(Math.PI, 0, 0);
								ranhura.translate(ranhuraTmp.X + faceTmp.getComprimento()/2,
										-ranhuraTmp.getDz() - faceTmp.getProfundidadeMaxima()/2 + ranhuraTmp.Z,
										-faceTmp.getLargura()/2 + ranhuraTmp.getLocY());
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							} else if (ranhuraTmp.getEixo() == Ranhura.VERTICAL)
							{
								OperationSlotCircularProfile ranhura = new OperationSlotCircularProfile("", (float)faceTmp.getLargura(), (float)ranhuraTmp.getRaio(), initialAngle, sweepAngle);
								ranhura.rotate(Math.PI, 0, 0);
								ranhura.translate(-faceTmp.getComprimento()/2 + ranhuraTmp.getLocX(),
										-ranhuraTmp.getDz() - faceTmp.getProfundidadeMaxima()/2 + ranhuraTmp.Z,
										-faceTmp.getLargura()/2 + ranhuraTmp.getLocY());
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							}
						} else if(feature.getClass() == RanhuraPerfilRoundedU.class)
						{
							RanhuraPerfilRoundedU ranhuraTmp = (RanhuraPerfilRoundedU)faceTmp.features.elementAt(j);
							if(ranhuraTmp.getEixo() == Ranhura.HORIZONTAL)
							{
								OperationRoundedUProfile ranhura = new OperationRoundedUProfile("", (float)(ranhuraTmp.getProfundidade()), (float)ranhuraTmp.getLargura(), (float)faceTmp.getComprimento());
								ranhura.rotate(0, Math.PI/2);
								ranhura.rotate(Math.PI, 0, 0);
								ranhura.translate(ranhuraTmp.X + faceTmp.getComprimento()/2,
										-faceTmp.getProfundidadeMaxima()/2 + ranhuraTmp.getProfundidade() + ranhuraTmp.Z,
										-faceTmp.getLargura()/2 + ranhuraTmp.getLocY());
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							} else if (ranhuraTmp.getEixo() == Ranhura.VERTICAL)
							{
								OperationRoundedUProfile ranhura = new OperationRoundedUProfile("", (float)(ranhuraTmp.getProfundidade()), (float)ranhuraTmp.getLargura(), (float)faceTmp.getLargura());
								ranhura.rotate(Math.PI, 0, 0);
								ranhura.translate(ranhuraTmp.getLocX() - faceTmp.getComprimento()/2,
										-faceTmp.getProfundidadeMaxima()/2 + ranhuraTmp.getProfundidade() + ranhuraTmp.Z,
										-faceTmp.getLargura()/2);
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							}
						} else if(feature.getClass() == RanhuraPerfilVee.class)
						{
							RanhuraPerfilVee ranhuraTmp = (RanhuraPerfilVee)faceTmp.features.elementAt(j);
							OperationSlotVeeProfile ranhura = new OperationSlotVeeProfile("", (float)faceTmp.getComprimento(), (float)ranhuraTmp.getProfundidade(), (float)ranhuraTmp.getRaio(), (float)(90 - ranhuraTmp.getAngulo()/2), (float)ranhuraTmp.getAngulo());
							if(ranhuraTmp.getEixo() == Ranhura.HORIZONTAL)
							{
								ranhura.rotate(0, Math.PI/2);
								ranhura.rotate(Math.PI, 0, 0);
								ranhura.translate(faceTmp.getComprimento()/2,
										-faceTmp.getProfundidadeMaxima()/2 + ranhuraTmp.getProfundidade() + ranhuraTmp.Z,
										-faceTmp.getLargura() / 2 + ranhuraTmp.getLocY());
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							} else if(ranhuraTmp.getEixo() == Ranhura.VERTICAL)
							{
								ranhura.rotate(Math.PI, 0, 0);
								ranhura.translate(-faceTmp.getComprimento() / 2 + ranhuraTmp.getLocX(),
										-faceTmp.getProfundidadeMaxima()/2 + ranhuraTmp.getProfundidade() + ranhuraTmp.Z,
										-faceTmp.getComprimento() / 2);
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							}
						} else if(feature.getClass() == RanhuraPerfilBezier.class)
						{
							RanhuraPerfilBezier ranhuraTmp = (RanhuraPerfilBezier)faceTmp.features.elementAt(j);
							Point3d[] controlPoints = ranhuraTmp.getPontosDeControle();
							OperationBezierProfile ranhura = new OperationBezierProfile("", controlPoints);

							if (ranhuraTmp.getEixo() == Ranhura.HORIZONTAL)
							{
								ranhura.rotate(0, Math.PI/2);
								ranhura.rotate(Math.PI, 0, 0);
								ranhura.translate(0, 
										-faceTmp.getProfundidadeMaxima()/2,
										-faceTmp.getLargura()/2);
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							} else if (ranhuraTmp.getEixo() == Ranhura.VERTICAL)
							{
								ranhura.rotate(Math.PI, 0, 0);
								ranhura.translate(-faceTmp.getComprimento()/2,
										-faceTmp.getProfundidadeMaxima()/2 ,
										0);
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							}
						}
						
						break;
						
					case Feature.DEGRAU:
						Degrau degrauTmp = (Degrau)faceTmp.features.elementAt(j);
						if(degrauTmp.getEixo() == Degrau.HORIZONTAL)
						{
							OperationBlock degrau = new OperationBlock("", (float)degrauTmp.getLargura(), (float)degrauTmp.getProfundidade(), (float)faceTmp.getComprimento());
							degrau.rotate(Math.PI, 0, 0);
							degrau.translate(degrauTmp.X,
									-faceTmp.getProfundidadeMaxima()/2+ degrauTmp.Z+ degrauTmp.getProfundidade()/2,
									-faceTmp.getLargura()/2 + degrauTmp.Y + degrauTmp.getLargura()/2);
							try {
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, degrau);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						}else if(degrauTmp.getEixo() == Degrau.VERTICAL)
						{
							OperationBlock degrau = new OperationBlock("", (float)faceTmp.getLargura(), (float)degrauTmp.getProfundidade(), (float)degrauTmp.getLargura());
							degrau.rotate(Math.PI,	 0, 0);
							degrau.translate(degrauTmp.X - faceTmp.getComprimento()/2 + degrauTmp.getLargura()/2,
									-faceTmp.getProfundidadeMaxima()/2+ degrauTmp.Z + degrauTmp.getProfundidade()/2,
									-degrauTmp.Y);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, degrau);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						}
						break;
					case Feature.CAVIDADE:
						Cavidade cavidadeTmp = (Cavidade)faceTmp.features.elementAt(j);
						OperationPocket_1 pocket = new OperationPocket_1("", (float)cavidadeTmp.getComprimento(), (float)cavidadeTmp.getLargura(), (float)cavidadeTmp.getProfundidade(), (float)cavidadeTmp.getRaio());
						pocket.rotate(Math.PI, 0, 0);
						pocket.translate(-faceTmp.getComprimento()/2 + cavidadeTmp.X + cavidadeTmp.getComprimento()/2,
								-faceTmp.getProfundidadeMaxima()/2 + cavidadeTmp.Z + cavidadeTmp.getProfundidade()/2,
								-faceTmp.getLargura()/2 + cavidadeTmp.getLargura()/2 + cavidadeTmp.Y);
						try 
						{
							rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, pocket);
						} catch (InvalidBooleanOperationException e) {
							e.printStackTrace();
						}
						break;
					case Feature.CAVIDADE_FUNDO_ARREDONDADO:
						CavidadeFundoArredondado cfaTmp = (CavidadeFundoArredondado)faceTmp.features.elementAt(j);
						OperationRoundFloorPocket opRoundPocket = new OperationRoundFloorPocket("", (float)cfaTmp.getComprimento(), (float)cfaTmp.getLargura(), (float)cfaTmp.getProfundidade(), (float)cfaTmp.getVerticeRaio(), (float)cfaTmp.getVerticeRaio(), (float)cfaTmp.getVerticeRaio(), (float)cfaTmp.getVerticeRaio(), (float)cfaTmp.getFundoRaio());
						opRoundPocket.rotate(Math.PI, 0, 0);
						opRoundPocket.translate(-faceTmp.getComprimento()/2 + cfaTmp.X + cfaTmp.getComprimento()/2,
								-faceTmp.getProfundidadeMaxima()/2 + cfaTmp.Z + cfaTmp.getProfundidade()/2,
								-faceTmp.getLargura()/2 + cfaTmp.getLargura()/2 + cfaTmp.Y);
						try 
						{
							this.rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, this.rawBlock , opRoundPocket);
						} catch (InvalidBooleanOperationException e) 
						{
							e.printStackTrace();
						}
						break;
						default:
							break;
					}
				}
				break;
			case Face.YZ:
				for (int j = 0; j < faceTmp.features.size(); j++)
				{
					Feature feature = (Feature)faceTmp.features.elementAt(j);
					switch(feature.getTipo())
					{
					case Feature.FURO:
					
						if (feature.getClass() == FuroBasePlana.class)
						{
							Furo furoTmp = (Furo) faceTmp.features.elementAt(j);	
							int numberOfPoints = 4 * 4;
							if(furoTmp.getRaio() < 10)
							{
								numberOfPoints = 16;
							} else if(furoTmp.getRaio() >= 10 && furoTmp.getRaio() < 30)
							{
								numberOfPoints = 20;
							} else if(furoTmp.getRaio() >= 30)
							{
								numberOfPoints = 25;
							}
							OperationCylinder_1 hole = new OperationCylinder_1("",  (float) furoTmp.getRaio(), 
									(float) furoTmp.getProfundidade(), numberOfPoints);
							hole.rotate(0, 0, -Math.PI/2);
							hole.translate( faceTmp.getProfundidadeMaxima()/2 - furoTmp.Z - furoTmp.getProfundidade()/2, 
									faceTmp.getComprimento()/2 - furoTmp.X, 
									faceTmp.getLargura()/2 - furoTmp.Y);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, hole);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						} else if (feature.getClass() == FuroBaseConica.class)
						{
							FuroBaseConica furoTmp = (FuroBaseConica)faceTmp.features.elementAt(j);
							
							OperationConicalHoleBottom hole = new OperationConicalHoleBottom("", (float)furoTmp.getProfundidade(), (float)furoTmp.getDiametro(), (float)(furoTmp.getTipAngle()));
							hole.rotate(0, 0, -Math.PI/2);
							hole.translate(faceTmp.getProfundidadeMaxima()/2 - furoTmp.Z,
									faceTmp.getComprimento()/2 - furoTmp.X, 
									faceTmp.getLargura()/2 - furoTmp.Y);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, hole);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						} else if (feature.getClass() == FuroBaseEsferica.class)
						{
							FuroBaseEsferica furoTmp = (FuroBaseEsferica)faceTmp.features.elementAt(j);
							OperationSphericalHoleBottom hole = new OperationSphericalHoleBottom("", (float)furoTmp.getProfundidade(), (float)furoTmp.getDiametro());
							hole.rotate(0, 0, -Math.PI/2);
							hole.translate(faceTmp.getProfundidadeMaxima()/2 - furoTmp.Z,
									faceTmp.getComprimento()/2 - furoTmp.X,
									faceTmp.getLargura()/2 - furoTmp.Y);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, hole);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						} else if(feature.getClass() == FuroBaseArredondada.class)
						{
							FuroBaseArredondada furoTmp = (FuroBaseArredondada)faceTmp.features.elementAt(j);
							OperationRoundHoleBottom hole = new OperationRoundHoleBottom("", (float)furoTmp.getProfundidade(), (float)furoTmp.getDiametro(), (float)furoTmp.getR1());
							hole.rotate(0, 0, -Math.PI/2);
							hole.translate(faceTmp.getProfundidadeMaxima()/2 - furoTmp.Z,
									faceTmp.getComprimento()/2 - furoTmp.X, 
									faceTmp.getLargura()/2 - furoTmp.Y);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, hole);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						} else if(feature.getClass() == FuroConico.class)
						{
							FuroConico furoTmp = (FuroConico)faceTmp.features.elementAt(j);
							OperationTaperHole hole = new OperationTaperHole("", (float)furoTmp.getProfundidade(), (float)furoTmp.getDiametro(), (float)furoTmp.getRaio1() * 2);
							hole.rotate(0, 0, -Math.PI/2);
							hole.translate(faceTmp.getProfundidadeMaxima()/2 - furoTmp.Z,
									faceTmp.getComprimento()/2 - furoTmp.X, 
									faceTmp.getLargura()/2 - furoTmp.Y);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, hole);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						}
						break;
						
					case Feature.RANHURA:
						if (feature.getClass() == Ranhura.class)
						{
							Ranhura ranhuraTmp = (Ranhura) faceTmp.features.elementAt(j);
						    if (ranhuraTmp.getEixo()== Ranhura.HORIZONTAL)
						    {						    	
						    	OperationBlock ranhura = new OperationBlock("", (float) ranhuraTmp.getLargura()  , 
										(float) ranhuraTmp.getProfundidade(), (float)faceTmp.getComprimento());
						    	ranhura.rotate(0, 0, -Math.PI/2);
								ranhura.translate(faceTmp.getProfundidadeMaxima()/2 - ranhuraTmp.Z - ranhuraTmp.getProfundidade()/2,
										0, 
										faceTmp.getLargura()/2 - ranhuraTmp.Y - ranhuraTmp.getLargura()/2);						    	
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
						    	
						    } else if (ranhuraTmp.getEixo()== Ranhura.VERTICAL) {
						    	
						    	OperationBlock ranhura = new OperationBlock("", (float)faceTmp.getLargura(), 
										(float) ranhuraTmp.getProfundidade(), (float) ranhuraTmp.getLargura());
						    	ranhura.rotate(0, 0, -Math.PI/2);
								ranhura.translate(faceTmp.getProfundidadeMaxima()/2 - ranhuraTmp.Z - ranhuraTmp.getProfundidade()/2,
										-ranhuraTmp.X + faceTmp.getComprimento()/2 - ranhuraTmp.getLargura()/2, 
										0);	
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
						    }
						} else if(feature.getClass() == RanhuraPerfilQuadradoU.class)
						{
							RanhuraPerfilQuadradoU ranhuraTmp = (RanhuraPerfilQuadradoU)faceTmp.features.elementAt(j);
							if (ranhuraTmp.getEixo() == Ranhura.HORIZONTAL)
							{
								OperationSlotSquareUProfile ranhura = new OperationSlotSquareUProfile("", (float)faceTmp.getComprimento(), (float)ranhuraTmp.getLargura2(), (float)ranhuraTmp.getProfundidade(), (float)ranhuraTmp.getRaio(), (float)ranhuraTmp.getRaio(), (float)ranhuraTmp.getAngulo(), (float)ranhuraTmp.getAngulo());
								ranhura.rotate(0, Math.PI/2);
								ranhura.rotate(0, 0, -Math.PI/2);
								ranhura.translate(-ranhuraTmp.Z + faceTmp.getProfundidadeMaxima()/2,
										-ranhuraTmp.X - faceTmp.getComprimento()/2, 
										faceTmp.getLargura()/2 - ranhuraTmp.Y - ranhuraTmp.getLargura()/2);						    	
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							} else if(ranhuraTmp.getEixo() == Ranhura.VERTICAL)
							{
								OperationSlotSquareUProfile ranhura = new OperationSlotSquareUProfile("", (float)faceTmp.getLargura(), (float)ranhuraTmp.getLargura2(), (float)ranhuraTmp.getProfundidade(), (float)ranhuraTmp.getRaio(), (float)ranhuraTmp.getRaio(), (float)ranhuraTmp.getAngulo(), (float)ranhuraTmp.getAngulo());
								ranhura.rotate(0, 0, -Math.PI/2);
								ranhura.translate(-ranhuraTmp.Z + faceTmp.getProfundidadeMaxima()/2,
										-ranhuraTmp.X + faceTmp.getComprimento()/2 - ranhuraTmp.getLargura()/2, 
										faceTmp.getLargura()/2 - ranhuraTmp.Y);	
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							}
						} else if(feature.getClass() == RanhuraPerfilCircularParcial.class)
						{
							RanhuraPerfilCircularParcial ranhuraTmp = (RanhuraPerfilCircularParcial)faceTmp.features.elementAt(j);
							float initialAngle = (float)(Math.asin(ranhuraTmp.getDz()/ranhuraTmp.getRaio()) * 180 / Math.PI);
							float sweepAngle = (float)(180 - 2 * initialAngle);
							if (ranhuraTmp.getEixo() == Ranhura.HORIZONTAL)
							{
								OperationSlotCircularProfile ranhura = new OperationSlotCircularProfile("", (float)faceTmp.getComprimento(), (float)ranhuraTmp.getRaio(), initialAngle, sweepAngle);
								ranhura.rotate(0, Math.PI/2);
								ranhura.rotate(0, 0, -Math.PI/2);
								ranhura.translate(-ranhuraTmp.Z + faceTmp.getProfundidadeMaxima()/2 + ranhuraTmp.getDz(),
										-ranhuraTmp.X - faceTmp.getComprimento()/2, 
										faceTmp.getLargura()/2 - ranhuraTmp.getLocY());
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							} else if (ranhuraTmp.getEixo() == Ranhura.VERTICAL)
							{
								OperationSlotCircularProfile ranhura = new OperationSlotCircularProfile("", (float)faceTmp.getLargura(), (float)ranhuraTmp.getRaio(), initialAngle, sweepAngle);
								ranhura.rotate(0, 0, -Math.PI/2);
								ranhura.translate(-ranhuraTmp.Z + faceTmp.getProfundidadeMaxima()/2 + ranhuraTmp.getDz(),
										faceTmp.getComprimento()/2 - ranhuraTmp.getLocX(), 
										faceTmp.getLargura()/2 - ranhuraTmp.getLocY());
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							}
						} else if(feature.getClass() == RanhuraPerfilRoundedU.class)
						{
							RanhuraPerfilRoundedU ranhuraTmp = (RanhuraPerfilRoundedU)faceTmp.features.elementAt(j);
							if(ranhuraTmp.getEixo() == Ranhura.HORIZONTAL)
							{
								OperationRoundedUProfile ranhura = new OperationRoundedUProfile("", (float)(ranhuraTmp.getProfundidade()), (float)ranhuraTmp.getLargura(), (float)faceTmp.getComprimento());
								ranhura.rotate(0, Math.PI/2);
								ranhura.rotate(0, 0, -Math.PI/2);
								ranhura.translate(faceTmp.getProfundidadeMaxima()/2 - ranhuraTmp.getProfundidade() - ranhuraTmp.Z,
										-ranhuraTmp.X - faceTmp.getComprimento()/2, 
										faceTmp.getLargura()/2 - ranhuraTmp.getLocY());
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							} else if (ranhuraTmp.getEixo() == Ranhura.VERTICAL)
							{
								OperationRoundedUProfile ranhura = new OperationRoundedUProfile("", (float)(ranhuraTmp.getProfundidade()), (float)ranhuraTmp.getLargura(), (float)faceTmp.getLargura());
								ranhura.rotate(0, 0, -Math.PI/2);
								ranhura.translate(faceTmp.getProfundidadeMaxima()/2 - ranhuraTmp.getProfundidade() - ranhuraTmp.Z,
										-ranhuraTmp.getLocX() + faceTmp.getComprimento()/2,
										faceTmp.getLargura()/2 - ranhuraTmp.getLocY());
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							}
						} else if(feature.getClass() == RanhuraPerfilVee.class)
						{
							RanhuraPerfilVee ranhuraTmp = (RanhuraPerfilVee)faceTmp.features.elementAt(j);
							if(ranhuraTmp.getEixo() == Ranhura.HORIZONTAL)
							{
								OperationSlotVeeProfile ranhura = new OperationSlotVeeProfile("", (float)faceTmp.getComprimento(), (float)ranhuraTmp.getProfundidade(), (float)ranhuraTmp.getRaio(), (float)(90 - ranhuraTmp.getAngulo()/2), (float)ranhuraTmp.getAngulo());
								ranhura.rotate(0, Math.PI/2);
								ranhura.rotate(0, 0, -Math.PI/2);
								ranhura.translate(faceTmp.getProfundidadeMaxima()/2 - ranhuraTmp.getProfundidade() - ranhuraTmp.Z,
										-ranhuraTmp.X - faceTmp.getComprimento()/2, 
										faceTmp.getLargura()/2 - ranhuraTmp.getLocY());
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							} else if(ranhuraTmp.getEixo() == Ranhura.VERTICAL)
							{
								OperationSlotVeeProfile ranhura = new OperationSlotVeeProfile("", (float)faceTmp.getLargura(), (float)ranhuraTmp.getProfundidade(), (float)ranhuraTmp.getRaio(), (float)(90 - ranhuraTmp.getAngulo()/2), (float)ranhuraTmp.getAngulo());
								ranhura.rotate(0, 0, -Math.PI/2);
								ranhura.translate(faceTmp.getProfundidadeMaxima()/2 - ranhuraTmp.getProfundidade() - ranhuraTmp.Z,
										faceTmp.getComprimento() / 2 - ranhuraTmp.getLocX(), 
										faceTmp.getLargura()/2 - ranhuraTmp.getLocY());
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							}
						} else if(feature.getClass() == RanhuraPerfilBezier.class)
						{
							RanhuraPerfilBezier ranhuraTmp = (RanhuraPerfilBezier)faceTmp.features.elementAt(j);
							Point3d[] controlPoints = ranhuraTmp.getPontosDeControle();
							OperationBezierProfile ranhura = new OperationBezierProfile("", controlPoints);

							if (ranhuraTmp.getEixo() == Ranhura.HORIZONTAL)
							{
								ranhura.rotate(0, Math.PI/2);
								ranhura.rotate(0, 0, -Math.PI/2);
								ranhura.translate(faceTmp.getProfundidadeMaxima()/2,
										0, 
										faceTmp.getLargura()/2);
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							} else if (ranhuraTmp.getEixo() == Ranhura.VERTICAL)
							{
								ranhura.rotate(0, 0, -Math.PI/2);
								ranhura.translate(faceTmp.getProfundidadeMaxima()/2,
										faceTmp.getComprimento()/2, 
										0);
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							}
						}
						
						break;
						
					case Feature.DEGRAU:
						Degrau degrauTmp = (Degrau)faceTmp.features.elementAt(j);
						if(degrauTmp.getEixo() == Degrau.HORIZONTAL)
						{
							OperationBlock degrau = new OperationBlock("", (float)degrauTmp.getLargura(), (float)degrauTmp.getProfundidade(), (float)faceTmp.getComprimento());
							degrau.rotate(0, 0, -Math.PI/2);
							degrau.translate(faceTmp.getProfundidadeMaxima()/2 - degrauTmp.Z - degrauTmp.getProfundidade()/2,
									-degrauTmp.X,
									faceTmp.getLargura()/2 - degrauTmp.Y - degrauTmp.getLargura()/2);
							try {
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, degrau);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						}else if(degrauTmp.getEixo() == Degrau.VERTICAL)
						{
							OperationBlock degrau = new OperationBlock("", (float)faceTmp.getLargura(), (float)degrauTmp.getProfundidade(), (float)degrauTmp.getLargura());
							degrau.rotate(0, 0, -Math.PI/2);
							degrau.translate(faceTmp.getProfundidadeMaxima()/2 - degrauTmp.Z - degrauTmp.getProfundidade()/2,
									-degrauTmp.X + faceTmp.getComprimento()/2 - degrauTmp.getLargura()/2,
									degrauTmp.Y);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, degrau);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						}
						break;
					case Feature.CAVIDADE:
						Cavidade cavidadeTmp = (Cavidade)faceTmp.features.elementAt(j);
						OperationPocket_1 pocket = new OperationPocket_1("", (float)cavidadeTmp.getComprimento(), (float)cavidadeTmp.getLargura(), (float)cavidadeTmp.getProfundidade(), (float)cavidadeTmp.getRaio());
						pocket.rotate(0, 0, -Math.PI/2);
						pocket.translate(faceTmp.getProfundidadeMaxima()/2 - cavidadeTmp.Z - cavidadeTmp.getProfundidade()/2,
								-faceTmp.getComprimento()/2 + cavidadeTmp.X + cavidadeTmp.getComprimento()/2 ,
								faceTmp.getLargura()/2 - cavidadeTmp.getLargura()/2 - cavidadeTmp.Y);
						try 
						{
							rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, pocket);
						} catch (InvalidBooleanOperationException e) {
							e.printStackTrace();
						}
						break;
					case Feature.CAVIDADE_FUNDO_ARREDONDADO:
						CavidadeFundoArredondado cfaTmp = (CavidadeFundoArredondado)faceTmp.features.elementAt(j);
						OperationRoundFloorPocket opRoundPocket = new OperationRoundFloorPocket("", (float)cfaTmp.getComprimento(), (float)cfaTmp.getLargura(), (float)cfaTmp.getProfundidade(), (float)cfaTmp.getVerticeRaio(), (float)cfaTmp.getVerticeRaio(), (float)cfaTmp.getVerticeRaio(), (float)cfaTmp.getVerticeRaio(), (float)cfaTmp.getFundoRaio());
						opRoundPocket.rotate(0, 0, -Math.PI/2);
						opRoundPocket.translate(faceTmp.getProfundidadeMaxima()/2 - cfaTmp.Z - cfaTmp.getProfundidade()/2,
								-faceTmp.getComprimento()/2 + cfaTmp.X + cfaTmp.getComprimento()/2,
								faceTmp.getLargura()/2 - cfaTmp.getLargura()/2 - cfaTmp.Y);
						try 
						{
							this.rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, this.rawBlock , opRoundPocket);
						} catch (InvalidBooleanOperationException e) 
						{
							e.printStackTrace();
						}
						break;
						default:
							break;
					}
				}
				break;
			case Face.ZX:
				for (int j = 0; j < faceTmp.features.size(); j++)
				{
					Feature feature = (Feature)faceTmp.features.elementAt(j);
					switch(feature.getTipo())
					{
					case Feature.FURO:
					
						if (feature.getClass() == FuroBasePlana.class)
						{
							Furo furoTmp = (Furo) faceTmp.features.elementAt(j);	
							int numberOfPoints = 4 * 4;
							if(furoTmp.getRaio() < 10)
							{
								numberOfPoints = 16;
							} else if(furoTmp.getRaio() >= 10 && furoTmp.getRaio() < 30)
							{
								numberOfPoints = 20;
							} else if(furoTmp.getRaio() >= 30)
							{
								numberOfPoints = 25;
							}
							OperationCylinder_1 hole = new OperationCylinder_1("",  (float) furoTmp.getRaio(), 
									(float) furoTmp.getProfundidade(), numberOfPoints);
							hole.rotate(-Math.PI/2,	 0, 0);
							hole.translate( -faceTmp.getComprimento()/2 + furoTmp.X, 
									-faceTmp.getLargura()/2 + furoTmp.Y, 
									-faceTmp.getProfundidadeMaxima()/2 + furoTmp.Z + furoTmp.getProfundidade()/2);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, hole);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						} else if (feature.getClass() == FuroBaseConica.class)
						{
							FuroBaseConica furoTmp = (FuroBaseConica)faceTmp.features.elementAt(j);
							
							OperationConicalHoleBottom hole = new OperationConicalHoleBottom("", (float)furoTmp.getProfundidade(), (float)furoTmp.getDiametro(), (float)(furoTmp.getTipAngle()));
							hole.rotate(-Math.PI/2,	 0, 0);
							hole.translate( -faceTmp.getComprimento()/2 + furoTmp.X, 
									-faceTmp.getLargura()/2 + furoTmp.Y, 
									-faceTmp.getProfundidadeMaxima()/2 + furoTmp.Z);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, hole);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						} else if (feature.getClass() == FuroBaseEsferica.class)
						{
							FuroBaseEsferica furoTmp = (FuroBaseEsferica)faceTmp.features.elementAt(j);
							OperationSphericalHoleBottom hole = new OperationSphericalHoleBottom("", (float)furoTmp.getProfundidade(), (float)furoTmp.getDiametro());
							hole.rotate(-Math.PI/2,	 0, 0);
							hole.translate( -faceTmp.getComprimento()/2 + furoTmp.X, 
									-faceTmp.getLargura()/2 + furoTmp.Y, 
									-faceTmp.getProfundidadeMaxima()/2 + furoTmp.Z);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, hole);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						} else if(feature.getClass() == FuroBaseArredondada.class)
						{
							FuroBaseArredondada furoTmp = (FuroBaseArredondada)faceTmp.features.elementAt(j);
							OperationRoundHoleBottom hole = new OperationRoundHoleBottom("", (float)furoTmp.getProfundidade(), (float)furoTmp.getDiametro(), (float)furoTmp.getR1());
							hole.rotate(-Math.PI/2,	 0, 0);
							hole.translate( -faceTmp.getComprimento()/2 + furoTmp.X, 
									-faceTmp.getLargura()/2 + furoTmp.Y, 
									-faceTmp.getProfundidadeMaxima()/2 + furoTmp.Z);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, hole);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						} else if(feature.getClass() == FuroConico.class)
						{
							FuroConico furoTmp = (FuroConico)faceTmp.features.elementAt(j);
							OperationTaperHole hole = new OperationTaperHole("", (float)furoTmp.getProfundidade(), (float)furoTmp.getDiametro(), (float)furoTmp.getRaio1() * 2);
							hole.rotate(-Math.PI/2,	 0, 0);
							hole.translate( -faceTmp.getComprimento()/2 + furoTmp.X, 
									-faceTmp.getLargura()/2 + furoTmp.Y, 
									-faceTmp.getProfundidadeMaxima()/2 + furoTmp.Z);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, hole);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						}
						break;
						
					case Feature.RANHURA:
						if (feature.getClass() == Ranhura.class)
						{
							Ranhura ranhuraTmp = (Ranhura) faceTmp.features.elementAt(j);
						    if (ranhuraTmp.getEixo()== Ranhura.HORIZONTAL)
						    {						    	
						    	OperationBlock ranhura = new OperationBlock("", (float) ranhuraTmp.getLargura()  , 
										(float) ranhuraTmp.getProfundidade(), (float)faceTmp.getComprimento());
						    	ranhura.rotate(-Math.PI/2,	 0, 0);
								ranhura.translate( 0, 
										faceTmp.getLargura()/2 - ranhuraTmp.Y - ranhuraTmp.getLargura()/2,
										-faceTmp.getProfundidadeMaxima()/2 + ranhuraTmp.Z + ranhuraTmp.getProfundidade()/2);						    	
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
						    	
						    } else if (ranhuraTmp.getEixo()== Ranhura.VERTICAL) {
						    	
						    	OperationBlock ranhura = new OperationBlock("", (float)faceTmp.getLargura(), 
										(float) ranhuraTmp.getProfundidade(), (float) ranhuraTmp.getLargura());
						    	ranhura.rotate(-Math.PI/2,	 0, 0);
								ranhura.translate(ranhuraTmp.X - faceTmp.getComprimento()/2 + ranhuraTmp.getLargura()/2, 
										0,
										-faceTmp.getProfundidadeMaxima()/2 + ranhuraTmp.Z + ranhuraTmp.getProfundidade()/2);	
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
						    }
						} else if(feature.getClass() == RanhuraPerfilQuadradoU.class)
						{
							RanhuraPerfilQuadradoU ranhuraTmp = (RanhuraPerfilQuadradoU)faceTmp.features.elementAt(j);
							if (ranhuraTmp.getEixo() == Ranhura.HORIZONTAL)
							{
								OperationSlotSquareUProfile ranhura = new OperationSlotSquareUProfile("", (float)faceTmp.getComprimento(), (float)ranhuraTmp.getLargura2(), (float)ranhuraTmp.getProfundidade(), (float)ranhuraTmp.getRaio(), (float)ranhuraTmp.getRaio(), (float)ranhuraTmp.getAngulo(), (float)ranhuraTmp.getAngulo());
								ranhura.rotate(0, Math.PI/2);
								ranhura.rotate(-Math.PI/2,	 0, 0);
								ranhura.translate(ranhuraTmp.X + faceTmp.getComprimento()/2, 
										-faceTmp.getLargura()/2 +ranhuraTmp.Y + ranhuraTmp.getLargura()/2,
										ranhuraTmp.Z - faceTmp.getProfundidadeMaxima()/2);						    	
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							} else if(ranhuraTmp.getEixo() == Ranhura.VERTICAL)
							{
								OperationSlotSquareUProfile ranhura = new OperationSlotSquareUProfile("", (float)faceTmp.getComprimento(), (float)ranhuraTmp.getLargura2(), (float)ranhuraTmp.getProfundidade(), (float)ranhuraTmp.getRaio(), (float)ranhuraTmp.getRaio(), (float)ranhuraTmp.getAngulo(), (float)ranhuraTmp.getAngulo());
								ranhura.rotate(-Math.PI/2,	 0, 0);
								ranhura.translate(ranhuraTmp.X - faceTmp.getComprimento()/2 + ranhuraTmp.getLargura()/2, 
										faceTmp.getLargura()/2 +ranhuraTmp.Y,
										ranhuraTmp.Z - faceTmp.getProfundidadeMaxima()/2);	
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							}
						} else if(feature.getClass() == RanhuraPerfilCircularParcial.class)
						{
							RanhuraPerfilCircularParcial ranhuraTmp = (RanhuraPerfilCircularParcial)faceTmp.features.elementAt(j);
							float initialAngle = (float)(Math.asin(ranhuraTmp.getDz()/ranhuraTmp.getRaio()) * 180 / Math.PI);
							float sweepAngle = (float)(180 - 2 * initialAngle);
							if (ranhuraTmp.getEixo() == Ranhura.HORIZONTAL)
							{
								OperationSlotCircularProfile ranhura = new OperationSlotCircularProfile("", (float)faceTmp.getComprimento(), (float)ranhuraTmp.getRaio(), initialAngle, sweepAngle);
								ranhura.rotate(0, Math.PI/2);
								ranhura.rotate(-Math.PI/2,	 0, 0);
								ranhura.translate(ranhuraTmp.X + faceTmp.getComprimento()/2, 
										faceTmp.getLargura()/2 - ranhuraTmp.getLocY(),
										ranhuraTmp.Z - faceTmp.getProfundidadeMaxima()/2 - ranhuraTmp.getDz());
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							} else if (ranhuraTmp.getEixo() == Ranhura.VERTICAL)
							{
								OperationSlotCircularProfile ranhura = new OperationSlotCircularProfile("", (float)faceTmp.getLargura(), (float)ranhuraTmp.getRaio(), initialAngle, sweepAngle);
								ranhura.rotate(-Math.PI/2,	 0, 0);
								ranhura.translate(-faceTmp.getComprimento()/2 + ranhuraTmp.getLocX(), 
										faceTmp.getLargura()/2 - ranhuraTmp.getLocY(),
										ranhuraTmp.Z - faceTmp.getProfundidadeMaxima()/2 - ranhuraTmp.getDz());
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							}
						} else if(feature.getClass() == RanhuraPerfilRoundedU.class)
						{
							RanhuraPerfilRoundedU ranhuraTmp = (RanhuraPerfilRoundedU)faceTmp.features.elementAt(j);
							if(ranhuraTmp.getEixo() == Ranhura.HORIZONTAL)
							{
								OperationRoundedUProfile ranhura = new OperationRoundedUProfile("", (float)(ranhuraTmp.getProfundidade()), (float)ranhuraTmp.getLargura(), (float)faceTmp.getComprimento());
								ranhura.rotate(0, Math.PI/2);
								ranhura.rotate(-Math.PI/2,	 0, 0);
								ranhura.translate(ranhuraTmp.X + faceTmp.getComprimento()/2, 
										faceTmp.getLargura()/2 - ranhuraTmp.getLocY(),
										-faceTmp.getProfundidadeMaxima()/2 + ranhuraTmp.getProfundidade() + ranhuraTmp.Z);
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							} else if (ranhuraTmp.getEixo() == Ranhura.VERTICAL)
							{
								OperationRoundedUProfile ranhura = new OperationRoundedUProfile("", (float)(ranhuraTmp.getProfundidade()), (float)ranhuraTmp.getLargura(), (float)faceTmp.getLargura());
								ranhura.rotate(-Math.PI/2,	 0, 0);
								ranhura.translate(ranhuraTmp.getLocX() - faceTmp.getComprimento()/2,
										faceTmp.getLargura()/2 - ranhuraTmp.getLocY(),
										-faceTmp.getProfundidadeMaxima()/2 + ranhuraTmp.getProfundidade() + ranhuraTmp.Z);
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							}
						} else if(feature.getClass() == RanhuraPerfilVee.class)
						{
							RanhuraPerfilVee ranhuraTmp = (RanhuraPerfilVee)faceTmp.features.elementAt(j);
							OperationSlotVeeProfile ranhura = new OperationSlotVeeProfile("", (float)faceTmp.getComprimento(), (float)ranhuraTmp.getProfundidade(), (float)ranhuraTmp.getRaio(), (float)(90 - ranhuraTmp.getAngulo()/2), (float)ranhuraTmp.getAngulo());
							if(ranhuraTmp.getEixo() == Ranhura.HORIZONTAL)
							{
								ranhura.rotate(0, Math.PI/2);
								ranhura.rotate(-Math.PI/2,	 0, 0);
								ranhura.translate(ranhuraTmp.X + faceTmp.getComprimento()/2, 
										faceTmp.getLargura()/2 - ranhuraTmp.getLocY(),
										-faceTmp.getProfundidadeMaxima()/2 + ranhuraTmp.getProfundidade() + ranhuraTmp.Z);
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							} else if(ranhuraTmp.getEixo() == Ranhura.VERTICAL)
							{
								ranhura.rotate(-Math.PI/2,	 0, 0);
								ranhura.translate(-faceTmp.getComprimento() / 2 + ranhuraTmp.getLocX(), 
										faceTmp.getLargura()/2 - ranhuraTmp.getLocY(),
										-faceTmp.getProfundidadeMaxima()/2 + ranhuraTmp.getProfundidade() + ranhuraTmp.Z);
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							}
						} else if(feature.getClass() == RanhuraPerfilBezier.class)
						{
							RanhuraPerfilBezier ranhuraTmp = (RanhuraPerfilBezier)faceTmp.features.elementAt(j);
							Point3d[] controlPoints = ranhuraTmp.getPontosDeControle();
							OperationBezierProfile ranhura = new OperationBezierProfile("", controlPoints);

							if (ranhuraTmp.getEixo() == Ranhura.HORIZONTAL)
							{
								ranhura.rotate(0, Math.PI/2);
								ranhura.rotate(-Math.PI/2,	 0, 0);
								ranhura.translate(0, 
										faceTmp.getLargura()/2,
										-faceTmp.getProfundidadeMaxima()/2);
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							} else if (ranhuraTmp.getEixo() == Ranhura.VERTICAL)
							{
								ranhura.rotate(-Math.PI/2,	 0, 0);
								ranhura.translate(-faceTmp.getComprimento()/2, 
										0,
										-faceTmp.getProfundidadeMaxima()/2);
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							}
						}
						
						break;
						
					case Feature.DEGRAU:
						Degrau degrauTmp = (Degrau)faceTmp.features.elementAt(j);
						if(degrauTmp.getEixo() == Degrau.HORIZONTAL)
						{
							OperationBlock degrau = new OperationBlock("", (float)degrauTmp.getLargura(), (float)degrauTmp.getProfundidade(), (float)faceTmp.getComprimento());
							degrau.rotate(-Math.PI/2,	 0, 0);
							degrau.translate(degrauTmp.X,
									faceTmp.getLargura()/2 - degrauTmp.Y - degrauTmp.getLargura()/2,
									-faceTmp.getProfundidadeMaxima()/2 + degrauTmp.Z + degrauTmp.getProfundidade()/2);
							try {
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, degrau);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						}else if(degrauTmp.getEixo() == Degrau.VERTICAL)
						{
							OperationBlock degrau = new OperationBlock("", (float)faceTmp.getLargura(), (float)degrauTmp.getProfundidade(), (float)degrauTmp.getLargura());
							degrau.rotate(-Math.PI/2,	 0, 0);
							degrau.translate(degrauTmp.X - faceTmp.getComprimento()/2 + degrauTmp.getLargura()/2,
									-degrauTmp.Y,
									-faceTmp.getProfundidadeMaxima()/2 + degrauTmp.Z + degrauTmp.getProfundidade()/2);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, degrau);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						}
						break;
					case Feature.CAVIDADE:
						Cavidade cavidadeTmp = (Cavidade)faceTmp.features.elementAt(j);
						OperationPocket_1 pocket = new OperationPocket_1("", (float)cavidadeTmp.getComprimento(), (float)cavidadeTmp.getLargura(), (float)cavidadeTmp.getProfundidade(), (float)cavidadeTmp.getRaio());
						pocket.rotate(-Math.PI/2,	 0, 0);
						pocket.translate(-faceTmp.getComprimento()/2 + cavidadeTmp.X + cavidadeTmp.getComprimento()/2 ,
								faceTmp.getLargura()/2 - cavidadeTmp.getLargura()/2 - cavidadeTmp.Y,
								-faceTmp.getProfundidadeMaxima()/2 + cavidadeTmp.Z + cavidadeTmp.getProfundidade()/2);
						try 
						{
							rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, pocket);
						} catch (InvalidBooleanOperationException e) {
							e.printStackTrace();
						}
						break;
					case Feature.CAVIDADE_FUNDO_ARREDONDADO:
						CavidadeFundoArredondado cfaTmp = (CavidadeFundoArredondado)faceTmp.features.elementAt(j);
						OperationRoundFloorPocket opRoundPocket = new OperationRoundFloorPocket("", (float)cfaTmp.getComprimento(), (float)cfaTmp.getLargura(), (float)cfaTmp.getProfundidade(), (float)cfaTmp.getVerticeRaio(), (float)cfaTmp.getVerticeRaio(), (float)cfaTmp.getVerticeRaio(), (float)cfaTmp.getVerticeRaio(), (float)cfaTmp.getFundoRaio());
						opRoundPocket.rotate(-Math.PI/2,	 0, 0);
						opRoundPocket.translate(-faceTmp.getComprimento()/2 + cfaTmp.X + cfaTmp.getComprimento()/2,
								faceTmp.getLargura()/2 - cfaTmp.getLargura()/2 - cfaTmp.Y,
								-faceTmp.getProfundidadeMaxima()/2 + cfaTmp.Z + cfaTmp.getProfundidade()/2);
						try 
						{
							this.rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, this.rawBlock , opRoundPocket);
						} catch (InvalidBooleanOperationException e) 
						{
							e.printStackTrace();
						}
						break;
						default:
							break;
					}
				}
				break;
			case Face.ZY:
				for (int j = 0; j < faceTmp.features.size(); j++)
				{
					Feature feature = (Feature)faceTmp.features.elementAt(j);
					switch(feature.getTipo())
					{
					case Feature.FURO:
					
						if (feature.getClass() == FuroBasePlana.class)
						{
							Furo furoTmp = (Furo) faceTmp.features.elementAt(j);	
							int numberOfPoints = 4 * 4;
							if(furoTmp.getRaio() < 10)
							{
								numberOfPoints = 16;
							} else if(furoTmp.getRaio() >= 10 && furoTmp.getRaio() < 30)
							{
								numberOfPoints = 20;
							} else if(furoTmp.getRaio() >= 30)
							{
								numberOfPoints = 25;
							}
							OperationCylinder_1 hole = new OperationCylinder_1("",  (float) furoTmp.getRaio(), 
									(float) furoTmp.getProfundidade(), numberOfPoints);
							hole.rotate(0, 0, Math.PI/2);
							hole.translate( -faceTmp.getProfundidadeMaxima()/2 + furoTmp.Z + furoTmp.getProfundidade()/2, 
									-faceTmp.getComprimento()/2 + furoTmp.X, 
									faceTmp.getLargura()/2 - furoTmp.Y);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, hole);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						} else if (feature.getClass() == FuroBaseConica.class)
						{
							FuroBaseConica furoTmp = (FuroBaseConica)faceTmp.features.elementAt(j);
							
							OperationConicalHoleBottom hole = new OperationConicalHoleBottom("", (float)furoTmp.getProfundidade(), (float)furoTmp.getDiametro(), (float)(furoTmp.getTipAngle()));
							hole.rotate(0, 0, Math.PI/2);
							hole.translate(-faceTmp.getProfundidadeMaxima()/2 + furoTmp.Z,
									-faceTmp.getComprimento()/2 + furoTmp.X, 
									faceTmp.getLargura()/2 - furoTmp.Y);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, hole);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						} else if (feature.getClass() == FuroBaseEsferica.class)
						{
							FuroBaseEsferica furoTmp = (FuroBaseEsferica)faceTmp.features.elementAt(j);
							OperationSphericalHoleBottom hole = new OperationSphericalHoleBottom("", (float)furoTmp.getProfundidade(), (float)furoTmp.getDiametro());
							hole.rotate(0, 0, Math.PI/2);
							hole.translate(-faceTmp.getProfundidadeMaxima()/2 + furoTmp.Z,
									-faceTmp.getComprimento()/2 + furoTmp.X, 
									faceTmp.getLargura()/2 - furoTmp.Y);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, hole);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						} else if(feature.getClass() == FuroBaseArredondada.class)
						{
							FuroBaseArredondada furoTmp = (FuroBaseArredondada)faceTmp.features.elementAt(j);
							OperationRoundHoleBottom hole = new OperationRoundHoleBottom("", (float)furoTmp.getProfundidade(), (float)furoTmp.getDiametro(), (float)furoTmp.getR1());
							hole.rotate(0, 0, Math.PI/2);
							hole.translate(-faceTmp.getProfundidadeMaxima()/2 + furoTmp.Z,
									-faceTmp.getComprimento()/2 + furoTmp.X, 
									faceTmp.getLargura()/2 - furoTmp.Y);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, hole);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						} else if(feature.getClass() == FuroConico.class)
						{
							FuroConico furoTmp = (FuroConico)faceTmp.features.elementAt(j);
							OperationTaperHole hole = new OperationTaperHole("", (float)furoTmp.getProfundidade(), (float)furoTmp.getDiametro(), (float)furoTmp.getRaio1() * 2);
							hole.rotate(0, 0, Math.PI/2);
							hole.translate(-faceTmp.getProfundidadeMaxima()/2 + furoTmp.Z,
									-faceTmp.getComprimento()/2 + furoTmp.X, 
									faceTmp.getLargura()/2 - furoTmp.Y);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, hole);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						}
						break;
						
					case Feature.RANHURA:
						if (feature.getClass() == Ranhura.class)
						{
							Ranhura ranhuraTmp = (Ranhura) faceTmp.features.elementAt(j);
						    if (ranhuraTmp.getEixo()== Ranhura.HORIZONTAL)
						    {						    	
						    	OperationBlock ranhura = new OperationBlock("", (float) ranhuraTmp.getLargura()  , 
										(float) ranhuraTmp.getProfundidade(), (float)faceTmp.getComprimento());
						    	ranhura.rotate(0, 0, Math.PI/2);
								ranhura.translate(-faceTmp.getProfundidadeMaxima()/2 + ranhuraTmp.Z + ranhuraTmp.getProfundidade()/2,
										0, 
										faceTmp.getLargura()/2 - ranhuraTmp.Y - ranhuraTmp.getLargura()/2);						    	
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
						    	
						    } else if (ranhuraTmp.getEixo()== Ranhura.VERTICAL) {
						    	
						    	OperationBlock ranhura = new OperationBlock("", (float)faceTmp.getLargura(), 
										(float) ranhuraTmp.getProfundidade(), (float) ranhuraTmp.getLargura());
						    	ranhura.rotate(0, 0, Math.PI/2);
								ranhura.translate(-faceTmp.getProfundidadeMaxima()/2 + ranhuraTmp.Z + ranhuraTmp.getProfundidade()/2,
										ranhuraTmp.X - faceTmp.getComprimento()/2 + ranhuraTmp.getLargura()/2, 
										0);	
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
						    }
						} else if(feature.getClass() == RanhuraPerfilQuadradoU.class)
						{
							RanhuraPerfilQuadradoU ranhuraTmp = (RanhuraPerfilQuadradoU)faceTmp.features.elementAt(j);
							if (ranhuraTmp.getEixo() == Ranhura.HORIZONTAL)
							{
								OperationSlotSquareUProfile ranhura = new OperationSlotSquareUProfile("", (float)faceTmp.getComprimento(), (float)ranhuraTmp.getLargura2(), (float)ranhuraTmp.getProfundidade(), (float)ranhuraTmp.getRaio(), (float)ranhuraTmp.getRaio(), (float)ranhuraTmp.getAngulo(), (float)ranhuraTmp.getAngulo());
								ranhura.rotate(0, Math.PI/2);
								ranhura.rotate(0, 0, Math.PI/2);
								ranhura.translate(ranhuraTmp.Z - faceTmp.getProfundidadeMaxima()/2,
										ranhuraTmp.X + faceTmp.getComprimento()/2, 
										faceTmp.getLargura()/2 - ranhuraTmp.Y - ranhuraTmp.getLargura()/2);						    	
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							} else if(ranhuraTmp.getEixo() == Ranhura.VERTICAL)
							{
								OperationSlotSquareUProfile ranhura = new OperationSlotSquareUProfile("", (float)faceTmp.getLargura(), (float)ranhuraTmp.getLargura2(), (float)ranhuraTmp.getProfundidade(), (float)ranhuraTmp.getRaio(), (float)ranhuraTmp.getRaio(), (float)ranhuraTmp.getAngulo(), (float)ranhuraTmp.getAngulo());
								ranhura.rotate(0, 0, Math.PI/2);
								ranhura.translate(ranhuraTmp.Z - faceTmp.getProfundidadeMaxima()/2,
										ranhuraTmp.X - faceTmp.getComprimento()/2 + ranhuraTmp.getLargura()/2, 
										faceTmp.getLargura()/2 - ranhuraTmp.Y);	
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							}
						} else if(feature.getClass() == RanhuraPerfilCircularParcial.class)
						{
							RanhuraPerfilCircularParcial ranhuraTmp = (RanhuraPerfilCircularParcial)faceTmp.features.elementAt(j);
							float initialAngle = (float)(Math.asin(ranhuraTmp.getDz()/ranhuraTmp.getRaio()) * 180 / Math.PI);
							float sweepAngle = (float)(180 - 2 * initialAngle);
							if (ranhuraTmp.getEixo() == Ranhura.HORIZONTAL)
							{
								OperationSlotCircularProfile ranhura = new OperationSlotCircularProfile("", (float)faceTmp.getComprimento(), (float)ranhuraTmp.getRaio(), initialAngle, sweepAngle);
								ranhura.rotate(0, Math.PI/2);
								ranhura.rotate(0, 0, Math.PI/2);
								ranhura.translate(ranhuraTmp.Z - faceTmp.getProfundidadeMaxima()/2 - ranhuraTmp.getDz(),
										ranhuraTmp.X + faceTmp.getComprimento()/2, 
										faceTmp.getLargura()/2 - ranhuraTmp.getLocY());
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							} else if (ranhuraTmp.getEixo() == Ranhura.VERTICAL)
							{
								OperationSlotCircularProfile ranhura = new OperationSlotCircularProfile("", (float)faceTmp.getLargura(), (float)ranhuraTmp.getRaio(), initialAngle, sweepAngle);
								ranhura.rotate(0, 0, Math.PI/2);
								ranhura.translate(ranhuraTmp.Z - faceTmp.getProfundidadeMaxima()/2 - ranhuraTmp.getDz(),
										-faceTmp.getComprimento()/2 + ranhuraTmp.getLocX(), 
										faceTmp.getLargura()/2 - ranhuraTmp.getLocY());
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							}
						} else if(feature.getClass() == RanhuraPerfilRoundedU.class)
						{
							RanhuraPerfilRoundedU ranhuraTmp = (RanhuraPerfilRoundedU)faceTmp.features.elementAt(j);
							if(ranhuraTmp.getEixo() == Ranhura.HORIZONTAL)
							{
								OperationRoundedUProfile ranhura = new OperationRoundedUProfile("", (float)(ranhuraTmp.getProfundidade()), (float)ranhuraTmp.getLargura(), (float)faceTmp.getComprimento());
								ranhura.rotate(0, Math.PI/2);
								ranhura.rotate(0, 0, Math.PI/2);
								ranhura.translate(-faceTmp.getProfundidadeMaxima()/2 + ranhuraTmp.getProfundidade() + ranhuraTmp.Z,
										ranhuraTmp.X + faceTmp.getComprimento()/2, 
										faceTmp.getLargura()/2 - ranhuraTmp.getLocY());
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							} else if (ranhuraTmp.getEixo() == Ranhura.VERTICAL)
							{
								OperationRoundedUProfile ranhura = new OperationRoundedUProfile("", (float)(ranhuraTmp.getProfundidade()), (float)ranhuraTmp.getLargura(), (float)faceTmp.getLargura());
								ranhura.rotate(0, 0, Math.PI/2);
								ranhura.translate(-faceTmp.getProfundidadeMaxima()/2 + ranhuraTmp.getProfundidade() + ranhuraTmp.Z,
										ranhuraTmp.getLocX() - faceTmp.getComprimento()/2,
										faceTmp.getLargura()/2 - ranhuraTmp.getLocY());
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							}
						} else if(feature.getClass() == RanhuraPerfilVee.class)
						{
							RanhuraPerfilVee ranhuraTmp = (RanhuraPerfilVee)faceTmp.features.elementAt(j);
							if(ranhuraTmp.getEixo() == Ranhura.HORIZONTAL)
							{
								OperationSlotVeeProfile ranhura = new OperationSlotVeeProfile("", (float)faceTmp.getComprimento(), (float)ranhuraTmp.getProfundidade(), (float)ranhuraTmp.getRaio(), (float)(90 - ranhuraTmp.getAngulo()/2), (float)ranhuraTmp.getAngulo());
								ranhura.rotate(0, Math.PI/2);
								ranhura.rotate(0, 0, Math.PI/2);
								ranhura.translate(-faceTmp.getProfundidadeMaxima()/2 + ranhuraTmp.getProfundidade() + ranhuraTmp.Z,
										ranhuraTmp.X + faceTmp.getComprimento()/2, 
										faceTmp.getLargura()/2 - ranhuraTmp.getLocY());
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							} else if(ranhuraTmp.getEixo() == Ranhura.VERTICAL)
							{
								OperationSlotVeeProfile ranhura = new OperationSlotVeeProfile("", (float)faceTmp.getLargura(), (float)ranhuraTmp.getProfundidade(), (float)ranhuraTmp.getRaio(), (float)(90 - ranhuraTmp.getAngulo()/2), (float)ranhuraTmp.getAngulo());
								ranhura.rotate(0, 0, Math.PI/2);
								ranhura.translate(-faceTmp.getProfundidadeMaxima()/2 + ranhuraTmp.getProfundidade() + ranhuraTmp.Z,
										-faceTmp.getComprimento() / 2 + ranhuraTmp.getLocX(), 
										faceTmp.getLargura()/2 - ranhuraTmp.getLocY());
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							}
						} else if(feature.getClass() == RanhuraPerfilBezier.class)
						{
							RanhuraPerfilBezier ranhuraTmp = (RanhuraPerfilBezier)faceTmp.features.elementAt(j);
							Point3d[] controlPoints = ranhuraTmp.getPontosDeControle();
							OperationBezierProfile ranhura = new OperationBezierProfile("", controlPoints);

							if (ranhuraTmp.getEixo() == Ranhura.HORIZONTAL)
							{
								ranhura.rotate(0, Math.PI/2);
								ranhura.rotate(0, 0, Math.PI/2);
								ranhura.translate(-faceTmp.getProfundidadeMaxima()/2,
										0, 
										faceTmp.getLargura()/2);
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							} else if (ranhuraTmp.getEixo() == Ranhura.VERTICAL)
							{
								ranhura.rotate(0, 0, Math.PI/2);
								ranhura.translate(-faceTmp.getProfundidadeMaxima()/2,
										-faceTmp.getComprimento()/2, 
										0);
								try 
								{
									rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, ranhura);
								} catch (InvalidBooleanOperationException e) {
									e.printStackTrace();
								}
							}
						}
						
						break;
						
					case Feature.DEGRAU:
						Degrau degrauTmp = (Degrau)faceTmp.features.elementAt(j);
						if(degrauTmp.getEixo() == Degrau.HORIZONTAL)
						{
							OperationBlock degrau = new OperationBlock("", (float)degrauTmp.getLargura(), (float)degrauTmp.getProfundidade(), (float)faceTmp.getComprimento());
							degrau.rotate(0, 0, Math.PI/2);
							degrau.translate(-faceTmp.getProfundidadeMaxima()/2 + degrauTmp.Z + degrauTmp.getProfundidade()/2,
									degrauTmp.X,
									faceTmp.getLargura()/2 - degrauTmp.Y - degrauTmp.getLargura()/2);
							try {
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, degrau);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						}else if(degrauTmp.getEixo() == Degrau.VERTICAL)
						{
							OperationBlock degrau = new OperationBlock("", (float)faceTmp.getLargura(), (float)degrauTmp.getProfundidade(), (float)degrauTmp.getLargura());
							degrau.rotate(0, 0, Math.PI/2);
							degrau.translate(-faceTmp.getProfundidadeMaxima()/2 + degrauTmp.Z + degrauTmp.getProfundidade()/2,
									degrauTmp.X - faceTmp.getComprimento()/2 + degrauTmp.getLargura()/2,
									degrauTmp.Y);
							try 
							{
								rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, degrau);
							} catch (InvalidBooleanOperationException e) {
								e.printStackTrace();
							}
						}
						break;
					case Feature.CAVIDADE:
						Cavidade cavidadeTmp = (Cavidade)faceTmp.features.elementAt(j);
						OperationPocket_1 pocket = new OperationPocket_1("", (float)cavidadeTmp.getComprimento(), (float)cavidadeTmp.getLargura(), (float)cavidadeTmp.getProfundidade(), (float)cavidadeTmp.getRaio());
						pocket.rotate(0, 0, Math.PI/2);
						pocket.translate(-faceTmp.getProfundidadeMaxima()/2 + cavidadeTmp.Z + cavidadeTmp.getProfundidade()/2,
								faceTmp.getComprimento()/2 - cavidadeTmp.X - cavidadeTmp.getComprimento()/2 ,
								faceTmp.getLargura()/2 - cavidadeTmp.getLargura()/2 - cavidadeTmp.Y);
						try 
						{
							rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, rawBlock, pocket);
						} catch (InvalidBooleanOperationException e) {
							e.printStackTrace();
						}
						break;
					case Feature.CAVIDADE_FUNDO_ARREDONDADO:
						CavidadeFundoArredondado cfaTmp = (CavidadeFundoArredondado)faceTmp.features.elementAt(j);
						OperationRoundFloorPocket opRoundPocket = new OperationRoundFloorPocket("", (float)cfaTmp.getComprimento(), (float)cfaTmp.getLargura(), (float)cfaTmp.getProfundidade(), (float)cfaTmp.getVerticeRaio(), (float)cfaTmp.getVerticeRaio(), (float)cfaTmp.getVerticeRaio(), (float)cfaTmp.getVerticeRaio(), (float)cfaTmp.getFundoRaio());
						opRoundPocket.rotate(0, 0, Math.PI/2);
						opRoundPocket.translate(-faceTmp.getProfundidadeMaxima()/2 + cfaTmp.Z + cfaTmp.getProfundidade()/2,
								faceTmp.getComprimento()/2 - cfaTmp.X - cfaTmp.getComprimento()/2,
								faceTmp.getLargura()/2 - cfaTmp.getLargura()/2 - cfaTmp.Y);
						try 
						{
							this.rawBlock = new CompoundSolid("", CompoundSolid.DIFFERENCE, this.rawBlock , opRoundPocket);
						} catch (InvalidBooleanOperationException e) 
						{
							e.printStackTrace();
						}
						break;
						default:
							break;
					}
				}
				break;
				default:
			}
			
		}
	}
}
