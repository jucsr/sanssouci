package br.UFSC.GRIMA.util;

import java.util.ArrayList;
import java.util.Vector;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.capp.DeterminarMovimentacao;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.Boring;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideFinishMilling;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.capp.machiningOperations.CenterDrilling;
import br.UFSC.GRIMA.capp.machiningOperations.Drilling;
import br.UFSC.GRIMA.capp.machiningOperations.FreeformOperation;
import br.UFSC.GRIMA.capp.machiningOperations.Reaming;
import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraDeWorkingsteps;
import br.UFSC.GRIMA.capp.movimentacoes.MovimentacaoCavidadeComProtuberancia;
import br.UFSC.GRIMA.capp.movimentacoes.MovimentacaoGeneralClosedPocket;
import br.UFSC.GRIMA.capp.movimentacoes.MovimentacaoRanhuraPerfilGenerico;
import br.UFSC.GRIMA.capp.movimentacoes.MovimentacaoRanhuraPerfilParcialCircular;
import br.UFSC.GRIMA.capp.movimentacoes.MovimentacaoRanhuraPerfilQuadradoU;
import br.UFSC.GRIMA.capp.movimentacoes.MovimentacaoRanhuraPerfilRoundedU;
import br.UFSC.GRIMA.capp.movimentacoes.MovimentacaoRanhuraPerfilVee;
import br.UFSC.GRIMA.capp.movimentacoes.MovimentacaoRegionSuperficieBezier;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.CavidadeFundoArredondado;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.Furo;
import br.UFSC.GRIMA.entidades.features.FuroBaseConica;
import br.UFSC.GRIMA.entidades.features.FuroBasePlana;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilBezier;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilCircularParcial;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilQuadradoU;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilRoundedU;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilVee;
import br.UFSC.GRIMA.entidades.features.Region;
import br.UFSC.GRIMA.entidades.ferramentas.BallEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.EndMill;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class GCodeGenerator {

	private Vector <Vector> Workingsteps ;
	private Projeto projeto;
	int lineNumber = 00;
	private double feedRate;
	private double spindleRotation;
	private int rotationDirection;
	public static final int NEUTRAL_HAND_OF_CUT = 0;
	public static final int RIGHT_HAND_OF_CUT = 1;
	public static final int LEFT_HAND_OF_CUT = 2;

	public GCodeGenerator(Vector <Vector> Workingsteps, Projeto projeto){

		this.Workingsteps = Workingsteps;
		this.projeto = projeto;
	}

	public String GenerateGCodeString(){




		String GCode = "N" + lineNumber + " G54" + "\n";
		lineNumber = lineNumber + 10;

		Feature anterior = null;

		for (int index = 0; index < this.Workingsteps.size(); index++)

		{
			//System.out.println("Entrou na iteracao int index = 0?");	

			Vector workinstepsFaceTmp = this.Workingsteps.get(index);
			for (int i = 0; i < workinstepsFaceTmp.size(); i++)
			{
				//System.out.println("Entrou na iteracao int i = 0; antes de verificar qual eh o wsTmp");	

				Workingstep wsTmp = (Workingstep)workinstepsFaceTmp.elementAt(i);

				double alturaSeguranca = wsTmp.getOperation().getRetractPlane();

				//if ((anterior == null) || !(wsTmp.getFeature().equals(anterior))){

				if (wsTmp.getFeature().getClass().equals(FuroBasePlana.class) || wsTmp.getFeature().getClass().equals(FuroBaseConica.class))
				{
					Furo furoTmp = (Furo)wsTmp.getFeature();
					int GAux = 0;

					if(wsTmp.getCondicoesUsinagem().getF() == 0){this.feedRate = wsTmp.getCondicoesUsinagem().getVf();}
					else{this.feedRate = wsTmp.getCondicoesUsinagem().getF();}

					this.spindleRotation = wsTmp.getCondicoesUsinagem().getN();
					this.rotationDirection = wsTmp.getFerramenta().getHandOfCut();

					if (rotationDirection == NEUTRAL_HAND_OF_CUT){GAux = 5;}
					else if (rotationDirection == RIGHT_HAND_OF_CUT){GAux = 3;}
					else if (rotationDirection == LEFT_HAND_OF_CUT){GAux = 4;}



					//					if (rotationDirection == 1){GAux = 3;}
					//					else if (rotationDirection == 2){GAux = 4;}
					//					else if (rotationDirection == 3){GAux = 5;}


					GCode = GCode + "N" + lineNumber + " S"+ spindleRotation +" F" + feedRate +" M"+GAux + "\n";
					lineNumber = lineNumber + 10;
					double positX = furoTmp.getPosicaoX();
					double positY = furoTmp.getPosicaoY();
					double profundidadeZ = furoTmp.getProfundidade();
					//String faceBloco = wsTmp.getFaceMae().getTipoString();
					double alturaBloco = projeto.getBloco().getProfundidade();
					String nomeFerramenta = wsTmp.getFerramenta().getName();
					double planodeReferencia = furoTmp.getPosicaoZ();
					double cuttingDepth = 0;
					Point3d startPoint = wsTmp.getOperation().getStartPoint();
					System.out.println("operation ---->" + wsTmp.getOperation().getId());
					System.out.println("Start Point ---->" + startPoint);

					double startZ = -startPoint.getZ();

					if (wsTmp.getOperation().getClass().equals(CenterDrilling.class)){
						cuttingDepth = ((CenterDrilling)wsTmp.getOperation()).getCuttingDepth();}

					if (wsTmp.getOperation().getClass().equals(Drilling.class)){
						cuttingDepth = ((Drilling)wsTmp.getOperation()).getCuttingDepth();}

					if (wsTmp.getOperation().getClass().equals(Boring.class)){
						cuttingDepth = ((Boring)wsTmp.getOperation()).getCuttingDepth();}

					if (wsTmp.getOperation().getClass().equals(Reaming.class)){
						cuttingDepth = ((Reaming)wsTmp.getOperation()).getCuttingDepth();}



					GCode = GCode + "N"+lineNumber+ " T = "+ "\"" + nomeFerramenta+ "\"" + "\n";
					lineNumber = lineNumber + 10;

					GCode = GCode + "N"+lineNumber+ " M6" + "\n" ;
					lineNumber = lineNumber + 10;

					GCode = GCode  + "N"+lineNumber+ " G0" + " X"+positX + " Y"+positY + "\n" ;
					lineNumber = lineNumber + 10;

					if (wsTmp.getOperation().isCoolant()){
						GCode = GCode  + "N" + lineNumber + " M8" + "\n" ;
						lineNumber = lineNumber + 10;

					}



					if (wsTmp.getOperation().getClass().equals(CenterDrilling.class) || wsTmp.getOperation().getClass().equals(Drilling.class)
							|| wsTmp.getOperation().getClass().equals(Reaming.class) || wsTmp.getOperation().getClass().equals(Boring.class)){

						GCode = GCode + "N"+lineNumber+ " CYCLE81("+alturaSeguranca+", "+startZ+", "
								+alturaSeguranca+ ", "+ (startZ-cuttingDepth)+", " +")" + "\n" ;
						lineNumber = lineNumber + 10;

					}

					double sideAllowance = 0;
					double bottomAllowance = 0;
					double Ap = wsTmp.getCondicoesUsinagem().getAp();


					if (wsTmp.getOperation().getClass().equals(BottomAndSideRoughMilling.class)){

						sideAllowance = ((BottomAndSideRoughMilling)wsTmp.getOperation()).getAllowanceSide();
						bottomAllowance = ((BottomAndSideRoughMilling)wsTmp.getOperation()).getAllowanceBottom();

						double dirFresamento = 0;

						if (rotationDirection == RIGHT_HAND_OF_CUT){dirFresamento = 2;}
						if (rotationDirection == LEFT_HAND_OF_CUT){dirFresamento = 3;}

						GCode = GCode + "N" + lineNumber + " POCKET2(" + alturaSeguranca + ", " + startZ +", " 
								+ ", " + (-1 * (profundidadeZ - bottomAllowance)) + ", " 
								+ ", " + (furoTmp.getRaio() - sideAllowance) 
								+ ", " + furoTmp.getPosicaoX() + ", " + furoTmp.getPosicaoY() + ", " 
								+ feedRate + ", "  + (feedRate*2) + ", " + Ap + ", " 
								+ dirFresamento + ", "+ ", " + ", "+ ", " + ", " + ") " + "\n";

						lineNumber = lineNumber + 10;

					}

					if (wsTmp.getOperation().getClass().equals(BottomAndSideFinishMilling.class)){

						double dirFresamento = 0;

						if (rotationDirection == RIGHT_HAND_OF_CUT){dirFresamento = 2;}
						if (rotationDirection == LEFT_HAND_OF_CUT){dirFresamento = 3;}

						GCode = GCode + "N" + lineNumber + " POCKET2(" + alturaSeguranca + ", " + startZ +", " 
								+ ", " + (-1 * (profundidadeZ)) + ", " 
								+ ", " + furoTmp.getRaio() 
								+ ", " + furoTmp.getPosicaoX() + ", " + furoTmp.getPosicaoY() + ", " 
								+ feedRate + ", "  + (feedRate*2) + ", " + Ap + ", " + dirFresamento + ", "+ ", " + ", "+ ", " + ", " + ") " + "\n";

						lineNumber = lineNumber + 10;

					}

					if (wsTmp.getOperation().isCoolant()){
						GCode = GCode + "N" + lineNumber + " M9" + "\n" ;
						lineNumber = lineNumber + 10;
					}

				}

				if (wsTmp.getFeature().getClass().equals(Cavidade.class) && ((Cavidade) wsTmp.getFeature()).getItsBoss().size()==0)
				{

					System.out.println("TAMANHO DO BOSS    "+((Cavidade) wsTmp.getFeature()).getItsBoss().size());
					//System.out.println("Verificou que eh cavidade?");
					Cavidade cavidadeTmp = (Cavidade)wsTmp.getFeature();
					int GAux = 0;

					this.feedRate = wsTmp.getCondicoesUsinagem().getVf();
					this.spindleRotation = wsTmp.getCondicoesUsinagem().getN();
					this.rotationDirection = wsTmp.getFerramenta().getHandOfCut();


					if (rotationDirection == 1){GAux = 3;}
					else if (rotationDirection == 2){GAux = 4;}
					else if (rotationDirection == 3){GAux = 5;}

					System.out.println("GAux = " + GAux);

					GCode = GCode + "N" + lineNumber + " S"+ spindleRotation +" F" +feedRate +" M"+GAux + "\n" ;
					lineNumber = lineNumber + 10;

					double positX = cavidadeTmp.getPosicaoX();
					double positY = cavidadeTmp.getPosicaoY();
					double comprimentoCavidade = cavidadeTmp.getComprimento();
					double larguraCavidade = cavidadeTmp.getLargura();
					double xCentro = (cavidadeTmp.getPosicaoX()+ (comprimentoCavidade/2));
					double yCentro = (cavidadeTmp.getPosicaoY()+ (larguraCavidade/2));
					double raioCavidade = cavidadeTmp.getRaio();
					double profundidadeZ = cavidadeTmp.getProfundidade()*-1;
					//String faceBloco = wsTmp.getFaceMae().getTipoString();
					double planoDeReferencia = cavidadeTmp.getPosicaoZ();
					//double alturaBloco = projeto.getBloco().getProfundidade();
					double avancoVertical = wsTmp.getCondicoesUsinagem().getAp();
					String nomeFerramenta = wsTmp.getFerramenta().getName();
					double velCorte = wsTmp.getCondicoesUsinagem().getVc();
					double tolerancia = wsTmp.getFeature().getTolerancia();

					System.out.println("Tolerancia = " + tolerancia);

					GCode = GCode + "N"+lineNumber+ " T = "+ "\"" + nomeFerramenta+ "\"" + "\n" ;
					lineNumber = lineNumber + 10;

					GCode = GCode + "N"+lineNumber+ " M6" + "\n" ;
					lineNumber = lineNumber + 10;

					if (wsTmp.getOperation().isCoolant()){
						GCode = GCode  + "N" + lineNumber + " M8" + "\n";
						lineNumber = lineNumber + 10;
					}

					double sideAllowance = 0;
					double bottomAllowance = 0;

					if (wsTmp.getOperation().getClass().equals(BottomAndSideRoughMilling.class)){

						sideAllowance = ((BottomAndSideRoughMilling)wsTmp.getOperation()).getAllowanceSide();
						bottomAllowance = ((BottomAndSideRoughMilling)wsTmp.getOperation()).getAllowanceBottom();


						System.out.println("side Allowance " + sideAllowance);
						System.out.println("bottom Allowance " + bottomAllowance);
					}



					//if (wsTmp.getFeature().isAcabamento()){
					if (wsTmp.getCondicoesUsinagem().isAcabamento() || (wsTmp.getTipo() == Workingstep.ACABAMENTO)){

						double raioFerramenta = wsTmp.getFerramenta().getDiametroFerramenta()/2;


						DeterminarMovimentacao dm = new DeterminarMovimentacao();


						//Acabamento dos Lados

						ArrayList<Path> trajetoriasCavidade = dm.getTrajetoriasAcabamentoLadosCavidade(wsTmp);



						for(int j = 0; j < trajetoriasCavidade.size(); j++)
						{
							double xAux = trajetoriasCavidade.get(j).getFinalPoint().getX();
							double yAux = trajetoriasCavidade.get(j).getFinalPoint().getY();
							double zAux = trajetoriasCavidade.get(j).getFinalPoint().getZ();

							if (trajetoriasCavidade.get(j).getClass().equals(LinearPath.class) ){

								int tipoDeMovimento = 0;

								if(((LinearPath)trajetoriasCavidade.get(j)).getTipoDeMovimento() == 0){tipoDeMovimento = 1;}
								else if (((LinearPath)trajetoriasCavidade.get(j)).getTipoDeMovimento() ==1){tipoDeMovimento =0;}

								GCode = GCode +"N" + lineNumber + " G"+tipoDeMovimento + " X"+xAux+ " Y" +yAux + " Z"+zAux + "\n";

								lineNumber = lineNumber + 10;
							}

							if (trajetoriasCavidade.get(j).getClass().equals(CircularPath.class) ){

								double rAux = ((CircularPath)trajetoriasCavidade.get(j)).getRadius();

								GCode = GCode + "N" + lineNumber + " G3" + " X"+xAux + " Y"+yAux+ " Z"+zAux +
										" CR="+rAux+


										"\n";

								lineNumber = lineNumber + 10;
							}

						}


						//								Aqui completa com pocket na base

						double profundidadePocketAcabamento = bottomAllowance;
						double referenciaPocketAcabamento = profundidadeZ - bottomAllowance;
						double comprimentoPocketAcabamento = comprimentoCavidade - (2*sideAllowance);
						double larguraPocketAcabamento = larguraCavidade - (2*sideAllowance);
						double raioPocketAcabamento = raioCavidade - sideAllowance;
						double startZfinish = wsTmp.getOperation().getStartPoint().getZ();

						//								GCode = GCode +"N"+lineNumber + " POCKET1("+ alturaSeguranca+","+(startZfinish*-1)+
						//							     ","+alturaSeguranca + ","+ profundidadeZ+ ","
						//							     +","+comprimentoPocketAcabamento + ","+ larguraPocketAcabamento + ","+ 
						//							     raioPocketAcabamento+ "," + xCentro +","+ yCentro +","+ "0" + ","+
						//							     feedRate + "," + feedRate+ ","+avancoVertical + ","+
						//							     "2"+ "," +"," + "0" + ","+ "," + ","+")" + "\n";

						GCode = GCode +"N"+lineNumber + " POCKET1("+ alturaSeguranca+","+(((startZfinish*-1)) + profundidadeZ + wsTmp.getFeature().LIMITE_DESBASTE ) +
								","+alturaSeguranca + ","+((startZfinish*-1) + profundidadeZ)+ ","
								+","+comprimentoPocketAcabamento + ","+ larguraPocketAcabamento + ","+ 
								raioPocketAcabamento+ "," + xCentro +","+ yCentro +","+ "0" + ","+
								feedRate + "," + feedRate+ ","+avancoVertical + ","+
								"2"+ "," +"," + "0" + ","+ "," + ","+")" + "\n";

						lineNumber = lineNumber + 10;




					}

					else{
						//System.out.println("Operacao de desbaste ");
						double raioFerramenta = wsTmp.getFerramenta().getDiametroFerramenta()/2;

						double raioMaiorDesbaste = 0;

						System.out.println("size ws " + wsTmp.getFeature().getWorkingsteps().size());
						for(int j = 0; j < wsTmp.getFeature().getWorkingsteps().size(); j++){

							if(((Workingstep)wsTmp.getFeature().getWorkingsteps().get(j)).getTipo() == Workingstep.DESBASTE){
								//System.out.println("j = " + j);
								//System.out.println("raio ws Desbaste " + j + " = " + ((Workingstep)wsTmp.getFeature().getWorkingsteps().get(j)).getFerramenta().getDiametroFerramenta()/2);
								if (((Workingstep)wsTmp.getFeature().getWorkingsteps().get(j)).getFerramenta().getDiametroFerramenta()/2 > raioMaiorDesbaste){
									raioMaiorDesbaste = ((Workingstep)wsTmp.getFeature().getWorkingsteps().get(j)).getFerramenta().getDiametroFerramenta()/2;
								}
							}
							System.out.println("Raio maior de desbaste" + raioMaiorDesbaste);

						}

						if (raioCavidade < raioFerramenta || raioFerramenta == raioMaiorDesbaste){
							GCode = GCode +"N"+lineNumber + " POCKET1("+ alturaSeguranca +","+(planoDeReferencia*-1)+
									","+alturaSeguranca + ","+ ((planoDeReferencia*-1)+(profundidadeZ + bottomAllowance))+ ","
									+","+(comprimentoCavidade - (2*sideAllowance)) + ","+ (larguraCavidade - (2*sideAllowance))
									+ ","+ (raioCavidade - sideAllowance)+ "," + xCentro +","+ yCentro +","+ "0" + ","+
									feedRate + "," + feedRate+ ","+avancoVertical + ","+
									"2"+ "," +"," + "0" + ","+ "," + ","+")" + "\n";

							lineNumber = lineNumber + 10;
						}

						if (raioCavidade >= raioFerramenta && !(raioFerramenta == raioMaiorDesbaste)){



							DeterminarMovimentacao dm = new DeterminarMovimentacao();
							ArrayList<Path> trajetoriasCantosCavidade = dm.getTrajetoriasCantosDaCavidade(wsTmp); 

							System.out.println("Size cantos " + trajetoriasCantosCavidade.size());

							for(int j = 0; j < trajetoriasCantosCavidade.size(); j++)
							{
								double xAux = trajetoriasCantosCavidade.get(j).getFinalPoint().getX();
								double yAux = trajetoriasCantosCavidade.get(j).getFinalPoint().getY();
								double zAux = trajetoriasCantosCavidade.get(j).getFinalPoint().getZ();

								if (trajetoriasCantosCavidade.get(j).getClass().equals(LinearPath.class) ){
									int tipoDeMovimento = 0;

									if(((LinearPath)trajetoriasCantosCavidade.get(j)).getTipoDeMovimento() == 0){tipoDeMovimento = 1;}
									else if (((LinearPath)trajetoriasCantosCavidade.get(j)).getTipoDeMovimento() ==1){tipoDeMovimento =0;}

									GCode = GCode +"N" + lineNumber + " G"+tipoDeMovimento + " X"+xAux+ " Y" +yAux + " Z"+zAux + "\n";

									lineNumber = lineNumber + 10;
								}

								if (trajetoriasCantosCavidade.get(j).getClass().equals(CircularPath.class) ){

									double rAux = ((CircularPath)trajetoriasCantosCavidade.get(j)).getRadius();
									int senseCP = ((CircularPath)trajetoriasCantosCavidade.get(j)).getSense();
									int senseRot = 0;
									if(((CircularPath)trajetoriasCantosCavidade.get(j)).getSense() == 0){
										senseRot = 2;
									}
									else if(((CircularPath)trajetoriasCantosCavidade.get(j)).getSense() == 1){
										senseRot = 3;
									}

									GCode = GCode + "N" + lineNumber + " G"+senseRot + " X"+xAux + " Y"+yAux+ " Z"+zAux +
											" CR="+rAux+


											"\n";

									lineNumber = lineNumber + 10;
								}

							}

						}

						//						GCode = GCode +"N"+lineNumber + " POCKET1("+ ((planoDeReferencia*-1)+10)+","+(planoDeReferencia*-1)+
						//					     ","+alturaSeguranca + ","+ ((planoDeReferencia*-1)+profundidadeZ)+ ","
						//					     +","+comprimentoCavidade + ","+ larguraCavidade + ","+ 
						//					     raioCavidade+ "," + xCentro +","+ yCentro +","+ "0" + ","+
						//					     feedRate + "," + feedRate+ ","+avancoVertical + ","+
						//					     "2"+ "," +"," + "0" + ","+ "," + ","+")" + "\n";




					}

					if (wsTmp.getOperation().isCoolant()){
						GCode = GCode +"N" + lineNumber + " M9" + "\n";
						lineNumber = lineNumber + 10;
					}



				}

				if(wsTmp.getFeature().getClass().equals(CavidadeFundoArredondado.class)){
					// Criar determinador de movimentacao
				}

				//			    if(wsTmp.getFeature().getClass().equals(FuroBaseArredondada.class)){
				//
				//			    	MovimentacaoFuroBaseArredondada furoBaseArredondada = new MovimentacaoFuroBaseArredondada(wsTmp);
				//
				//
				//			    	this.feedRate = wsTmp.getCondicoesUsinagem().getVf();
				//			    	this.spindleRotation = wsTmp.getCondicoesUsinagem().getN();
				//			    	this.rotationDirection = wsTmp.getFerramenta().getHandOfCut();
				//
				///********************************************************DESBASTE***************************************************/	
				//			    	if(wsTmp.getOperation().getClass() == BottomAndSideRoughMilling.class){
				//				    	ArrayList<Path> desbaste = furoBaseArredondada.desbaste();
				//			    		int GAux = 0;
				//			    		if (rotationDirection == 1){GAux = 3;}
				//			    		else if (rotationDirection == 2){GAux = 4;}
				//			    		else if (rotationDirection == 3){GAux = 5;}
				//
				//			    		GCode = GCode +"N" + lineNumber + " S"+ spindleRotation +" F" +feedRate +" M"+GAux + "\n";
				//			    		lineNumber = lineNumber + 10;
				//
				//			    		GCode = GCode +"N"+lineNumber+ " T = " + "\""+ wsTmp.getFerramenta().getName()+ "\"" + "\n";
				//			    		lineNumber = lineNumber + 10;
				//
				//			    		GCode = GCode +"N"+lineNumber+ " M6" + "\n";
				//			    		lineNumber = lineNumber + 10;
				//
				//			    		if (wsTmp.getOperation().isCoolant()){
				//			    			GCode = GCode +"N" + lineNumber + " M8" + "\n";
				//			    			lineNumber = lineNumber + 10;
				//			    		}
				//			    		
				//			    		double xAux = desbaste.get(0).getInitialPoint().getX();
				//		    			double yAux = desbaste.get(0).getInitialPoint().getY();
				//		    			double zAux = desbaste.get(0).getInitialPoint().getZ();
				//		    			
				//		    			if(desbaste.get(0).getClass()==LinearPath.class){
				//		    				if(((LinearPath) desbaste.get(0)).getTipoDeMovimento()==LinearPath.SLOW_MOV){
				//		    					GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
				//		    					lineNumber = lineNumber + 10;
				//		    				}
				//		    				else{
				//		    					GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
				//		    					lineNumber = lineNumber + 10;
				//		    				}
				//		    			}
				//		    			else{
				//		    				if(((CircularPath) desbaste.get(0)).getSense()==CircularPath.CCW){
				//		    					GCode = GCode + "N" + lineNumber + " G2" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
				//xFinal   yFinal , I=AC(centroX)    J=AC(centroY)  getCenter
				//		    					lineNumber = lineNumber + 10;
				//		    				}
				//		    				else if(((CircularPath) desbaste.get(0)).getSense()==CircularPath.CW){
				//		    					GCode = GCode + "N" + lineNumber + " G3" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
				//		    					lineNumber = lineNumber + 10;
				//		    				}
				//		    			}
				//			    		for(int j = 0; j < desbaste.size(); j++)
				//			    		{
				//			    			xAux = desbaste.get(j).getFinalPoint().getX();
				//			    			yAux = desbaste.get(j).getFinalPoint().getY();
				//			    			zAux = desbaste.get(j).getFinalPoint().getZ();
				//
				//			    			if(desbaste.get(j).getTipoDeMovimento()==LinearPath.SLOW_MOV){
				//			    				GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
				//			    				lineNumber = lineNumber + 10;
				//			    			}
				//			    			else{
				//			    				GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
				//			    				lineNumber = lineNumber + 10;
				//			    			}
				//			    		}
				//			    	}
				///********************************************************ACABAMENTO***************************************************/	
				//			    	if(wsTmp.getOperation().getClass() == BottomAndSideFinishMilling.class){
				//				    	ArrayList<LinearPath> acabamento = ranhuraQuadU.getMovimentacaoAcabamentoRanhuraPerfilQuadradoU();
				//			    		
				//				    	int GAux = 0;
				//			    		if (rotationDirection == 1){GAux = 3;}
				//			    		else if (rotationDirection == 2){GAux = 4;}
				//			    		else if (rotationDirection == 3){GAux = 5;}
				//
				//			    		GCode = GCode +"N" + lineNumber + " S"+ spindleRotation +" F" +feedRate +" M"+GAux + "\n";
				//			    		lineNumber = lineNumber + 10;
				//
				//			    		GCode = GCode +"N"+lineNumber+ " T = " + "\""+ wsTmp.getFerramenta().getName()+ "\"" + "\n";
				//			    		lineNumber = lineNumber + 10;
				//
				//			    		GCode = GCode +"N"+lineNumber+ " M6" + "\n";
				//			    		lineNumber = lineNumber + 10;
				//
				//			    		if (wsTmp.getOperation().isCoolant()){
				//			    			GCode = GCode +"N" + lineNumber + " M8" + "\n";
				//			    			lineNumber = lineNumber + 10;
				//			    		}
				//
				//			    		double xAux = acabamento.get(0).getInitialPoint().getX();
				//		    			double yAux = acabamento.get(0).getInitialPoint().getY();
				//		    			double zAux = acabamento.get(0).getInitialPoint().getZ();
				//		    			
				//		    			
				//		    			if(acabamento.get(0).getTipoDeMovimento()==LinearPath.SLOW_MOV){
				//		    				GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
				//		    				lineNumber = lineNumber + 10;
				//		    			}
				//		    			else{
				//		    				GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
				//		    				lineNumber = lineNumber + 10;
				//		    			}
				//			    		
				//			    		for(int j = 0; j < acabamento.size(); j++)
				//			    		{
				//
				//			    			xAux = acabamento.get(j).getFinalPoint().getX();
				//			    			yAux = acabamento.get(j).getFinalPoint().getY();
				//			    			zAux = acabamento.get(j).getFinalPoint().getZ();
				//
				//
				//			    			//********** Testar se é fast(G0) ou slow(G1)
				//			    			if(acabamento.get(j).getTipoDeMovimento()==LinearPath.SLOW_MOV){
				//			    				GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
				//			    				lineNumber = lineNumber + 10;
				//			    			}
				//			    			else{
				//			    				GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
				//			    				lineNumber = lineNumber + 10;
				//			    			}					
				//			    		}
				//			    	}
				//
				//			    	if (wsTmp.getOperation().isCoolant()){
				//			    		GCode = GCode +"N" + lineNumber + " M9" + "\n";
				//			    		lineNumber = lineNumber + 10;
				//			    	}
				//			    }

				/*************************************************************************************************************************************/

				if(wsTmp.getFeature().getClass().equals(RanhuraPerfilQuadradoU.class)){

					MovimentacaoRanhuraPerfilQuadradoU ranhuraQuadU = new MovimentacaoRanhuraPerfilQuadradoU(wsTmp);


					this.feedRate = wsTmp.getCondicoesUsinagem().getVf();
					this.spindleRotation = wsTmp.getCondicoesUsinagem().getN();
					this.rotationDirection = wsTmp.getFerramenta().getHandOfCut();

					/********************************************************DESBASTE***************************************************/	
					if(wsTmp.getOperation().getClass() == BottomAndSideRoughMilling.class){
						ArrayList<LinearPath> desbaste = ranhuraQuadU.getMovimentacaoDesbasteRanhuraPerfilQuadradoU();
						int GAux = 0;
						if (rotationDirection == 1){GAux = 3;}
						else if (rotationDirection == 2){GAux = 4;}
						else if (rotationDirection == 3){GAux = 5;}

						GCode = GCode +"N" + lineNumber + " S"+ spindleRotation +" F" +feedRate +" M"+GAux + "\n";
						lineNumber = lineNumber + 10;

						GCode = GCode +"N"+lineNumber+ " T = " + "\""+ wsTmp.getFerramenta().getName()+ "\"" + "\n";
						lineNumber = lineNumber + 10;

						GCode = GCode +"N"+lineNumber+ " M6" + "\n";
						lineNumber = lineNumber + 10;

						if (wsTmp.getOperation().isCoolant()){
							GCode = GCode +"N" + lineNumber + " M8" + "\n";
							lineNumber = lineNumber + 10;
						}

						double xAux = desbaste.get(0).getInitialPoint().getX();
						double yAux = desbaste.get(0).getInitialPoint().getY();
						double zAux = desbaste.get(0).getInitialPoint().getZ();


						if(desbaste.get(0).getTipoDeMovimento()==LinearPath.SLOW_MOV){
							GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
							lineNumber = lineNumber + 10;
						}
						else{
							GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
							lineNumber = lineNumber + 10;
						}

						for(int j = 0; j < desbaste.size(); j++)
						{
							xAux = desbaste.get(j).getFinalPoint().getX();
							yAux = desbaste.get(j).getFinalPoint().getY();
							zAux = desbaste.get(j).getFinalPoint().getZ();

							if(desbaste.get(j).getTipoDeMovimento()==LinearPath.SLOW_MOV){
								GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
								lineNumber = lineNumber + 10;
							}
							else{
								GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
								lineNumber = lineNumber + 10;
							}
						}
					}
					/********************************************************ACABAMENTO***************************************************/	
					if(wsTmp.getOperation().getClass() == BottomAndSideFinishMilling.class){
						ArrayList<LinearPath> acabamento = ranhuraQuadU.getMovimentacaoAcabamentoRanhuraPerfilQuadradoU();

						int GAux = 0;
						if (rotationDirection == 1){GAux = 3;}
						else if (rotationDirection == 2){GAux = 4;}
						else if (rotationDirection == 3){GAux = 5;}

						GCode = GCode +"N" + lineNumber + " S"+ spindleRotation +" F" +feedRate +" M"+GAux + "\n";
						lineNumber = lineNumber + 10;

						GCode = GCode +"N"+lineNumber+ " T = " + "\""+ wsTmp.getFerramenta().getName()+ "\"" + "\n";
						lineNumber = lineNumber + 10;

						GCode = GCode +"N"+lineNumber+ " M6" + "\n";
						lineNumber = lineNumber + 10;

						if (wsTmp.getOperation().isCoolant()){
							GCode = GCode +"N" + lineNumber + " M8" + "\n";
							lineNumber = lineNumber + 10;
						}

						double xAux = acabamento.get(0).getInitialPoint().getX();
						double yAux = acabamento.get(0).getInitialPoint().getY();
						double zAux = acabamento.get(0).getInitialPoint().getZ();


						if(acabamento.get(0).getTipoDeMovimento()==LinearPath.SLOW_MOV){
							GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
							lineNumber = lineNumber + 10;
						}
						else{
							GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
							lineNumber = lineNumber + 10;
						}

						for(int j = 0; j < acabamento.size(); j++)
						{

							xAux = acabamento.get(j).getFinalPoint().getX();
							yAux = acabamento.get(j).getFinalPoint().getY();
							zAux = acabamento.get(j).getFinalPoint().getZ();


							//********** Testar se é fast(G0) ou slow(G1)
							if(acabamento.get(j).getTipoDeMovimento()==LinearPath.SLOW_MOV){
								GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
								lineNumber = lineNumber + 10;
							}
							else{
								GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
								lineNumber = lineNumber + 10;
							}					
						}
					}

					if (wsTmp.getOperation().isCoolant()){
						GCode = GCode +"N" + lineNumber + " M9" + "\n";
						lineNumber = lineNumber + 10;
					}
				}

				/*************************************************************************************************************************************/
				if(wsTmp.getFeature().getClass().equals(Region.class) && (wsTmp.getFerramenta().getClass().equals(FaceMill.class) || wsTmp.getFerramenta().getClass().equals(EndMill.class))){

					MovimentacaoRegionSuperficieBezier regionBezier= new MovimentacaoRegionSuperficieBezier(wsTmp);

					
					this.feedRate = wsTmp.getCondicoesUsinagem().getVf();
					this.spindleRotation = wsTmp.getCondicoesUsinagem().getN();
					this.rotationDirection = wsTmp.getFerramenta().getHandOfCut();

					/********************************************************DESBASTE***************************************************/	
					if(wsTmp.getOperation().getClass() == BottomAndSideRoughMilling.class){

						ArrayList<LinearPath> desbaste = null;
						int l=0;

						if(wsTmp.getFerramenta().getClass().equals(EndMill.class)){
							desbaste = regionBezier.desbaste();
						}
						else if(wsTmp.getFerramenta().getClass().equals(FaceMill.class)){
							desbaste = regionBezier.desbaste1();	
						}

						int GAux = 0;
						if (rotationDirection == 1){GAux = 3;}
						else if (rotationDirection == 2){GAux = 4;}
						else if (rotationDirection == 3){GAux = 5;}

						GCode = GCode +"N" + lineNumber + " S"+ spindleRotation +" F" +feedRate +" M"+GAux + "\n";
						lineNumber = lineNumber + 10;

						GCode = GCode +"N"+lineNumber+ " T = " + "\""+ wsTmp.getFerramenta().getName()+ "\"" + "\n";
						lineNumber = lineNumber + 10;

						GCode = GCode +"N"+lineNumber+ " M6" + "\n";
						lineNumber = lineNumber + 10;

						if (wsTmp.getOperation().isCoolant()){
							GCode = GCode +"N" + lineNumber + " M8" + "\n";
							lineNumber = lineNumber + 10;
						}

						double xAux = desbaste.get(0).getInitialPoint().getX();
						double yAux = desbaste.get(0).getInitialPoint().getY();
						double zAux = desbaste.get(0).getInitialPoint().getZ();


						if(desbaste.get(0).getTipoDeMovimento()==LinearPath.SLOW_MOV){
							GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
							lineNumber = lineNumber + 10;
						}
						else{
							GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
							lineNumber = lineNumber + 10;
						}

						for(int j = 0; j < desbaste.size(); j++)
						{
							xAux = desbaste.get(j).getFinalPoint().getX();
							yAux = desbaste.get(j).getFinalPoint().getY();
							zAux = desbaste.get(j).getFinalPoint().getZ();

							if(desbaste.get(j).getTipoDeMovimento()==LinearPath.SLOW_MOV){
								if(l==0){
									GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
									lineNumber = lineNumber + 10;
								}
								else{
									if(desbaste.get(j-1).getFinalPoint().getZ()==zAux){
										GCode = GCode + "N" + lineNumber + " X" + xAux + " Y" + yAux + "\n";
										lineNumber = lineNumber + 10;
									}
									else{
										GCode = GCode + "N" + lineNumber + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
										lineNumber = lineNumber + 10;			    						
									}
								}
								l=1;
							}
							else{
								GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
								lineNumber = lineNumber + 10;
								l=0;
							}
						}
					}	
				}
				if(wsTmp.getFeature().getClass().equals(Region.class) && wsTmp.getFerramenta().getClass().equals(BallEndMill.class)){



					MovimentacaoRegionSuperficieBezier regionBezier= new MovimentacaoRegionSuperficieBezier(wsTmp);


					this.feedRate = wsTmp.getCondicoesUsinagem().getVf();
					this.spindleRotation = wsTmp.getCondicoesUsinagem().getN();
					this.rotationDirection = wsTmp.getFerramenta().getHandOfCut();

					/********************************************************ACABAMENTO***************************************************/	
					if(wsTmp.getOperation().getClass() == FreeformOperation.class){
						ArrayList<LinearPath> acabamento = regionBezier.acabamento();

						int GAux = 0;
						if (rotationDirection == 1){GAux = 3;}
						else if (rotationDirection == 2){GAux = 4;}
						else if (rotationDirection == 3){GAux = 5;}
						int l=0;

						GCode = GCode +"N" + lineNumber + " S"+ spindleRotation +" F" +feedRate +" M"+GAux + "\n";
						lineNumber = lineNumber + 10;

						GCode = GCode +"N"+lineNumber+ " T = " + "\""+ wsTmp.getFerramenta().getName()+ "\"" + "\n";
						lineNumber = lineNumber + 10;

						GCode = GCode +"N"+lineNumber+ " M6" + "\n";
						lineNumber = lineNumber + 10;

						if (wsTmp.getOperation().isCoolant()){
							GCode = GCode +"N" + lineNumber + " M8" + "\n";
							lineNumber = lineNumber + 10;
						}

						double xAux = acabamento.get(0).getInitialPoint().getX();
						double yAux = acabamento.get(0).getInitialPoint().getY();
						double zAux = acabamento.get(0).getInitialPoint().getZ();


						if(acabamento.get(0).getTipoDeMovimento()==LinearPath.SLOW_MOV){
							GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
							lineNumber = lineNumber + 10;
						}
						else{
							GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
							lineNumber = lineNumber + 10;
						}

						for(int j = 0; j < acabamento.size(); j++)
						{

							xAux = acabamento.get(j).getFinalPoint().getX();
							yAux = acabamento.get(j).getFinalPoint().getY();
							zAux = acabamento.get(j).getFinalPoint().getZ();


							//********** Testar se é fast(G0) ou slow(G1)
							if(acabamento.get(j).getTipoDeMovimento()==LinearPath.SLOW_MOV){
								if(l==0){
									GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
									lineNumber = lineNumber + 10;
								}
								else{
									if(acabamento.get(j-1).getFinalPoint().getX()==acabamento.get(j).getFinalPoint().getX()){
										GCode = GCode + "N" + lineNumber + " Y" + yAux + " Z" + zAux + "\n";
										lineNumber = lineNumber + 10;			    						
									}
									else{
										GCode = GCode + "N" + lineNumber + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
										lineNumber = lineNumber + 10;			    						
									}
								}
								l=1;
							}
							else{
								GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
								lineNumber = lineNumber + 10;
								l=0;
							}					
						}
					}	

				}
				if(wsTmp.getFeature().getClass().equals(RanhuraPerfilBezier.class)){

					MovimentacaoRanhuraPerfilGenerico ranhuraBezier= new MovimentacaoRanhuraPerfilGenerico(wsTmp);


					this.feedRate = wsTmp.getCondicoesUsinagem().getVf();
					this.spindleRotation = wsTmp.getCondicoesUsinagem().getN();
					this.rotationDirection = wsTmp.getFerramenta().getHandOfCut();

					/********************************************************DESBASTE***************************************************/	
					if(wsTmp.getOperation().getClass() == BottomAndSideRoughMilling.class){
						ArrayList<LinearPath> desbaste = ranhuraBezier.getMovimentacaoDesbasteRanhuraPerfilGenerico();
						int GAux = 0;
						if (rotationDirection == 1){GAux = 3;}
						else if (rotationDirection == 2){GAux = 4;}
						else if (rotationDirection == 3){GAux = 5;}

						GCode = GCode +"N" + lineNumber + " S"+ spindleRotation +" F" +feedRate +" M"+GAux + "\n";
						lineNumber = lineNumber + 10;

						GCode = GCode +"N"+lineNumber+ " T = " + "\""+ wsTmp.getFerramenta().getName()+ "\"" + "\n";
						lineNumber = lineNumber + 10;

						GCode = GCode +"N"+lineNumber+ " M6" + "\n";
						lineNumber = lineNumber + 10;

						if (wsTmp.getOperation().isCoolant()){
							GCode = GCode +"N" + lineNumber + " M8" + "\n";
							lineNumber = lineNumber + 10;
						}

						double xAux = desbaste.get(0).getInitialPoint().getX();
						double yAux = desbaste.get(0).getInitialPoint().getY();
						double zAux = desbaste.get(0).getInitialPoint().getZ();


						if(desbaste.get(0).getTipoDeMovimento()==LinearPath.SLOW_MOV){
							GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
							lineNumber = lineNumber + 10;
						}
						else{
							GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
							lineNumber = lineNumber + 10;
						}

						for(int j = 0; j < desbaste.size(); j++)
						{
							xAux = desbaste.get(j).getFinalPoint().getX();
							yAux = desbaste.get(j).getFinalPoint().getY();
							zAux = desbaste.get(j).getFinalPoint().getZ();

							if(desbaste.get(j).getTipoDeMovimento()==LinearPath.SLOW_MOV){
								GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
								lineNumber = lineNumber + 10;
							}
							else{
								GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
								lineNumber = lineNumber + 10;
							}
						}
					}
					/********************************************************ACABAMENTO***************************************************/	
					if(wsTmp.getOperation().getClass() == FreeformOperation.class){
						ArrayList<LinearPath> acabamento = ranhuraBezier.getMovimentacaoAcabamentoRanhuraPerfilGenerico();

						int GAux = 0;
						if (rotationDirection == 1){GAux = 3;}
						else if (rotationDirection == 2){GAux = 4;}
						else if (rotationDirection == 3){GAux = 5;}

						GCode = GCode +"N" + lineNumber + " S"+ spindleRotation +" F" +feedRate +" M"+GAux + "\n";
						lineNumber = lineNumber + 10;

						GCode = GCode +"N"+lineNumber+ " T = " + "\""+ wsTmp.getFerramenta().getName()+ "\"" + "\n";
						lineNumber = lineNumber + 10;

						GCode = GCode +"N"+lineNumber+ " M6" + "\n";
						lineNumber = lineNumber + 10;

						if (wsTmp.getOperation().isCoolant()){
							GCode = GCode +"N" + lineNumber + " M8" + "\n";
							lineNumber = lineNumber + 10;
						}

						double xAux = acabamento.get(0).getInitialPoint().getX();
						double yAux = acabamento.get(0).getInitialPoint().getY();
						double zAux = acabamento.get(0).getInitialPoint().getZ();


						if(acabamento.get(0).getTipoDeMovimento()==LinearPath.SLOW_MOV){
							GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
							lineNumber = lineNumber + 10;
						}
						else{
							GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
							lineNumber = lineNumber + 10;
						}

						for(int j = 0; j < acabamento.size(); j++)
						{

							xAux = acabamento.get(j).getFinalPoint().getX();
							yAux = acabamento.get(j).getFinalPoint().getY();
							zAux = acabamento.get(j).getFinalPoint().getZ();


							//********** Testar se é fast(G0) ou slow(G1)
							if(acabamento.get(j).getTipoDeMovimento()==LinearPath.SLOW_MOV){
								GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
								lineNumber = lineNumber + 10;
							}
							else{
								GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
								lineNumber = lineNumber + 10;
							}					
						}
					}

					if (wsTmp.getOperation().isCoolant()){
						GCode = GCode +"N" + lineNumber + " M9" + "\n";
						lineNumber = lineNumber + 10;
					}
				}

				/*************************************************************************************************************************************/

				if(wsTmp.getFeature().getClass().equals(RanhuraPerfilCircularParcial.class)){

					MovimentacaoRanhuraPerfilParcialCircular ranhuraParcCirc= new MovimentacaoRanhuraPerfilParcialCircular(wsTmp, (Face) projeto.getBloco().getFaces().get(i));


					this.feedRate = wsTmp.getCondicoesUsinagem().getVf();
					this.spindleRotation = wsTmp.getCondicoesUsinagem().getN();
					this.rotationDirection = wsTmp.getFerramenta().getHandOfCut();

					/********************************************************DESBASTE***************************************************/	
					if(wsTmp.getOperation().getClass() == BottomAndSideRoughMilling.class){
						ArrayList<LinearPath> desbaste = ranhuraParcCirc.getMovimentacaoDesbasteRanhuraPerfilCircularParcial();
						int GAux = 0;
						if (rotationDirection == 1){GAux = 3;}
						else if (rotationDirection == 2){GAux = 4;}
						else if (rotationDirection == 3){GAux = 5;}

						GCode = GCode +"N" + lineNumber + " S"+ spindleRotation +" F" +feedRate +" M"+GAux + "\n";
						lineNumber = lineNumber + 10;

						GCode = GCode +"N"+lineNumber+ " T = " + "\""+ wsTmp.getFerramenta().getName()+ "\"" + "\n";
						lineNumber = lineNumber + 10;

						GCode = GCode +"N"+lineNumber+ " M6" + "\n";
						lineNumber = lineNumber + 10;

						if (wsTmp.getOperation().isCoolant()){
							GCode = GCode +"N" + lineNumber + " M8" + "\n";
							lineNumber = lineNumber + 10;
						}

						double xAux = desbaste.get(0).getInitialPoint().getX();
						double yAux = desbaste.get(0).getInitialPoint().getY();
						double zAux = desbaste.get(0).getInitialPoint().getZ();


						if(desbaste.get(0).getTipoDeMovimento()==LinearPath.SLOW_MOV){
							GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
							lineNumber = lineNumber + 10;
						}
						else{
							GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
							lineNumber = lineNumber + 10;
						}

						for(int j = 0; j < desbaste.size(); j++)
						{
							xAux = desbaste.get(j).getFinalPoint().getX();
							yAux = desbaste.get(j).getFinalPoint().getY();
							zAux = desbaste.get(j).getFinalPoint().getZ();

							if(desbaste.get(j).getTipoDeMovimento()==LinearPath.SLOW_MOV){
								GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
								lineNumber = lineNumber + 10;
							}
							else{
								GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
								lineNumber = lineNumber + 10;
							}
						}
					}
					/********************************************************ACABAMENTO***************************************************/	
					if(wsTmp.getOperation().getClass() == BottomAndSideFinishMilling.class){
						ArrayList<LinearPath> acabamento = ranhuraParcCirc.getMovimentacaoAcabamentoRanhuraPerfilCircularParcial();

						int GAux = 0;
						if (rotationDirection == 1){GAux = 3;}
						else if (rotationDirection == 2){GAux = 4;}
						else if (rotationDirection == 3){GAux = 5;}

						GCode = GCode +"N" + lineNumber + " S"+ spindleRotation +" F" +feedRate +" M"+GAux + "\n";
						lineNumber = lineNumber + 10;

						GCode = GCode +"N"+lineNumber+ " T = " + "\""+ wsTmp.getFerramenta().getName()+ "\"" + "\n";
						lineNumber = lineNumber + 10;

						GCode = GCode +"N"+lineNumber+ " M6" + "\n";
						lineNumber = lineNumber + 10;

						if (wsTmp.getOperation().isCoolant()){
							GCode = GCode +"N" + lineNumber + " M8" + "\n";
							lineNumber = lineNumber + 10;
						}

						double xAux = acabamento.get(0).getInitialPoint().getX();
						double yAux = acabamento.get(0).getInitialPoint().getY();
						double zAux = acabamento.get(0).getInitialPoint().getZ();


						if(acabamento.get(0).getTipoDeMovimento()==LinearPath.SLOW_MOV){
							GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
							lineNumber = lineNumber + 10;
						}
						else{
							GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
							lineNumber = lineNumber + 10;
						}

						for(int j = 0; j < acabamento.size(); j++)
						{

							xAux = acabamento.get(j).getFinalPoint().getX();
							yAux = acabamento.get(j).getFinalPoint().getY();
							zAux = acabamento.get(j).getFinalPoint().getZ();


							//********** Testar se é fast(G0) ou slow(G1)
							if(acabamento.get(j).getTipoDeMovimento()==LinearPath.SLOW_MOV){
								GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
								lineNumber = lineNumber + 10;
							}
							else{
								GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
								lineNumber = lineNumber + 10;
							}					
						}
					}

					if (wsTmp.getOperation().isCoolant()){
						GCode = GCode +"N" + lineNumber + " M9" + "\n";
						lineNumber = lineNumber + 10;
					}
				}

				/*************************************************************************************************************************************/
				if(wsTmp.getFeature().getClass().equals(Cavidade.class) && ((Cavidade) wsTmp.getFeature()).getItsBoss().size()!=0){



					if(wsTmp.getFeature().getClass().equals(Cavidade.class) && (wsTmp.getFerramenta().getClass().equals(FaceMill.class) || wsTmp.getFerramenta().getClass().equals(EndMill.class))){

						MovimentacaoCavidadeComProtuberancia cavidade= new MovimentacaoCavidadeComProtuberancia(wsTmp);


						this.feedRate = wsTmp.getCondicoesUsinagem().getVf();
						this.spindleRotation = wsTmp.getCondicoesUsinagem().getN();
						this.rotationDirection = wsTmp.getFerramenta().getHandOfCut();

						/********************************************************DESBASTE***************************************************/	
						if(wsTmp.getOperation().getClass() == BottomAndSideRoughMilling.class){

							ArrayList<LinearPath> desbaste = null;
							ArrayList<LinearPath> contorno = null;
							int l=0;

							//			    			if(wsTmp.getFerramenta().getClass() == EndMill.class){
							//			    				desbaste = regionBezier.desbaste();
							//			    			}
							//			    			else 
							if(wsTmp.getFerramenta().getClass() == FaceMill.class){
								desbaste = cavidade.getDesbasteTest();
							}

							int GAux = 0;
							if (rotationDirection == 1){GAux = 3;}
							else if (rotationDirection == 2){GAux = 4;}
							else if (rotationDirection == 3){GAux = 5;}

							GCode = GCode +"N" + lineNumber + " S"+ spindleRotation +" F" +feedRate +" M"+GAux + "\n";
							lineNumber = lineNumber + 10;

							GCode = GCode +"N"+lineNumber+ " T = " + "\""+ wsTmp.getFerramenta().getName()+ "\"" + "\n";
							lineNumber = lineNumber + 10;

							GCode = GCode +"N"+lineNumber+ " M6" + "\n";
							lineNumber = lineNumber + 10;

							if (wsTmp.getOperation().isCoolant()){
								GCode = GCode +"N" + lineNumber + " M8" + "\n";
								lineNumber = lineNumber + 10;
							}

							double xAux = desbaste.get(0).getInitialPoint().getX();
							double yAux = desbaste.get(0).getInitialPoint().getY();
							double zAux = desbaste.get(0).getInitialPoint().getZ();

							if(desbaste.get(0).getTipoDeMovimento()==LinearPath.SLOW_MOV){
								GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
								lineNumber = lineNumber + 10;
							}
							else{
								GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
								lineNumber = lineNumber + 10;
							}

							for(int j = 0; j < desbaste.size(); j++)
							{
								xAux = desbaste.get(j).getFinalPoint().getX();
								yAux = desbaste.get(j).getFinalPoint().getY();
								zAux = desbaste.get(j).getFinalPoint().getZ();


								if(desbaste.get(j).getTipoDeMovimento()==LinearPath.SLOW_MOV){
									if(l==0){
										GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
										lineNumber = lineNumber + 10;
									}
									else{
										if(desbaste.get(j-1).getFinalPoint().getZ()==zAux){
											GCode = GCode + "N" + lineNumber + " X" + xAux + " Y" + yAux + "\n";
											lineNumber = lineNumber + 10;
										}
										else{
											GCode = GCode + "N" + lineNumber + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
											lineNumber = lineNumber + 10;			    						
										}
									}
									l=1;
								}
								else{
									GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
									lineNumber = lineNumber + 10;
									l=0;
								}
							}
						}	
					}
					//			    	if(wsTmp.getFeature().getClass().equals(Cavidade.class) && wsTmp.getFerramenta().getClass().equals(BallEndMill.class)){
					//
					//
					//
					//			    		MovimentacaoRegionSuperficieBezier regionBezier= new MovimentacaoRegionSuperficieBezier(wsTmp);
					//
					//
					//			    		this.feedRate = wsTmp.getCondicoesUsinagem().getVf();
					//			    		this.spindleRotation = wsTmp.getCondicoesUsinagem().getN();
					//			    		this.rotationDirection = wsTmp.getFerramenta().getHandOfCut();
					//
					//			    		/********************************************************ACABAMENTO***************************************************/	
					//			    		if(wsTmp.getOperation().getClass() == FreeformOperation.class){
					//			    			ArrayList<LinearPath> acabamento = regionBezier.acabamento();
					//
					//			    			int GAux = 0;
					//			    			if (rotationDirection == 1){GAux = 3;}
					//			    			else if (rotationDirection == 2){GAux = 4;}
					//			    			else if (rotationDirection == 3){GAux = 5;}
					//			    			int l=0;
					//
					//			    			GCode = GCode +"N" + lineNumber + " S"+ spindleRotation +" F" +feedRate +" M"+GAux + "\n";
					//			    			lineNumber = lineNumber + 10;
					//
					//			    			GCode = GCode +"N"+lineNumber+ " T = " + "\""+ wsTmp.getFerramenta().getName()+ "\"" + "\n";
					//			    			lineNumber = lineNumber + 10;
					//
					//			    			GCode = GCode +"N"+lineNumber+ " M6" + "\n";
					//			    			lineNumber = lineNumber + 10;
					//
					//			    			if (wsTmp.getOperation().isCoolant()){
					//			    				GCode = GCode +"N" + lineNumber + " M8" + "\n";
					//			    				lineNumber = lineNumber + 10;
					//			    			}
					//
					//			    			double xAux = acabamento.get(0).getInitialPoint().getX();
					//			    			double yAux = acabamento.get(0).getInitialPoint().getY();
					//			    			double zAux = acabamento.get(0).getInitialPoint().getZ();
					//
					//
					//			    			if(acabamento.get(0).getTipoDeMovimento()==LinearPath.SLOW_MOV){
					//			    				GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
					//			    				lineNumber = lineNumber + 10;
					//			    			}
					//			    			else{
					//			    				GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
					//			    				lineNumber = lineNumber + 10;
					//			    			}
					//
					//			    			for(int j = 0; j < acabamento.size(); j++)
					//			    			{
					//
					//			    				xAux = acabamento.get(j).getFinalPoint().getX();
					//			    				yAux = acabamento.get(j).getFinalPoint().getY();
					//			    				zAux = acabamento.get(j).getFinalPoint().getZ();
					//
					//
					//			    				//********** Testar se é fast(G0) ou slow(G1)
					//			    				if(acabamento.get(j).getTipoDeMovimento()==LinearPath.SLOW_MOV){
					//			    					if(l==0){
					//			    						GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
					//			    						lineNumber = lineNumber + 10;
					//			    					}
					//			    					else{
					//			    						if(acabamento.get(j-1).getFinalPoint().getX()==acabamento.get(j).getFinalPoint().getX()){
					//			    							GCode = GCode + "N" + lineNumber + " Y" + yAux + " Z" + zAux + "\n";
					//			    							lineNumber = lineNumber + 10;			    						
					//			    						}
					//			    						else{
					//			    							GCode = GCode + "N" + lineNumber + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
					//			    							lineNumber = lineNumber + 10;			    						
					//			    						}
					//			    					}
					//			    					l=1;
					//			    				}
					//			    				else{
					//			    					GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
					//			    					lineNumber = lineNumber + 10;
					//			    					l=0;
					//			    				}					
					//			    			}
					//			    		}	
					//
					//			    	}
				}

				/*************************************************************************************************************************************/
				if(wsTmp.getFeature().getClass().equals(GeneralClosedPocket.class)){



					if(wsTmp.getFeature().getClass().equals(GeneralClosedPocket.class) && (wsTmp.getFerramenta().getClass().equals(FaceMill.class) || wsTmp.getFerramenta().getClass().equals(EndMill.class))){

						MovimentacaoGeneralClosedPocket genClosed= new MovimentacaoGeneralClosedPocket(wsTmp);


						this.feedRate = wsTmp.getCondicoesUsinagem().getVf();
						this.spindleRotation = wsTmp.getCondicoesUsinagem().getN();
						this.rotationDirection = wsTmp.getFerramenta().getHandOfCut();

						/********************************************************DESBASTE***************************************************/	
						if(wsTmp.getOperation().getClass() == BottomAndSideRoughMilling.class){

							ArrayList<LinearPath> desbaste = null;
							ArrayList<LinearPath> contorno = null;
							int l=0;

							//			    			if(wsTmp.getFerramenta().getClass() == EndMill.class){
							//			    				desbaste = regionBezier.desbaste();
							//			    			}
							//			    			else 
							if(wsTmp.getFerramenta().getClass() == FaceMill.class){
								desbaste = genClosed.getDesbaste();
							}

							int GAux = 0;
							if (rotationDirection == 1){GAux = 3;}
							else if (rotationDirection == 2){GAux = 4;}
							else if (rotationDirection == 3){GAux = 5;}

							GCode = GCode +"N" + lineNumber + " S"+ spindleRotation +" F" +feedRate +" M"+GAux + "\n";
							lineNumber = lineNumber + 10;

							GCode = GCode +"N"+lineNumber+ " T = " + "\""+ wsTmp.getFerramenta().getName()+ "\"" + "\n";
							lineNumber = lineNumber + 10;

							GCode = GCode +"N"+lineNumber+ " M6" + "\n";
							lineNumber = lineNumber + 10;

							if (wsTmp.getOperation().isCoolant()){
								GCode = GCode +"N" + lineNumber + " M8" + "\n";
								lineNumber = lineNumber + 10;
							}

							double xAux = desbaste.get(0).getInitialPoint().getX();
							double yAux = desbaste.get(0).getInitialPoint().getY();
							double zAux = desbaste.get(0).getInitialPoint().getZ();

							if(desbaste.get(0).getTipoDeMovimento()==LinearPath.SLOW_MOV){
								GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
								lineNumber = lineNumber + 10;
							}
							else{
								GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
								lineNumber = lineNumber + 10;
							}

							for(int j = 0; j < desbaste.size(); j++)
							{
								xAux = desbaste.get(j).getFinalPoint().getX();
								yAux = desbaste.get(j).getFinalPoint().getY();
								zAux = desbaste.get(j).getFinalPoint().getZ();


								if(desbaste.get(j).getTipoDeMovimento()==LinearPath.SLOW_MOV){
									if(l==0){
										GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
										lineNumber = lineNumber + 10;
									}
									else{
										if(desbaste.get(j-1).getFinalPoint().getZ()==zAux){
											GCode = GCode + "N" + lineNumber + " X" + xAux + " Y" + yAux + "\n";
											lineNumber = lineNumber + 10;
										}
										else{
											GCode = GCode + "N" + lineNumber + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
											lineNumber = lineNumber + 10;			    						
										}
									}
									l=1;
								}
								else{
									GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
									lineNumber = lineNumber + 10;
									l=0;
								}
							}
						}	
					}
					//			    	if(wsTmp.getFeature().getClass().equals(Cavidade.class) && wsTmp.getFerramenta().getClass().equals(BallEndMill.class)){
					//
					//
					//
					//			    		MovimentacaoRegionSuperficieBezier regionBezier= new MovimentacaoRegionSuperficieBezier(wsTmp);
					//
					//
					//			    		this.feedRate = wsTmp.getCondicoesUsinagem().getVf();
					//			    		this.spindleRotation = wsTmp.getCondicoesUsinagem().getN();
					//			    		this.rotationDirection = wsTmp.getFerramenta().getHandOfCut();
					//
					//			    		/********************************************************ACABAMENTO***************************************************/	
					//			    		if(wsTmp.getOperation().getClass() == FreeformOperation.class){
					//			    			ArrayList<LinearPath> acabamento = regionBezier.acabamento();
					//
					//			    			int GAux = 0;
					//			    			if (rotationDirection == 1){GAux = 3;}
					//			    			else if (rotationDirection == 2){GAux = 4;}
					//			    			else if (rotationDirection == 3){GAux = 5;}
					//			    			int l=0;
					//
					//			    			GCode = GCode +"N" + lineNumber + " S"+ spindleRotation +" F" +feedRate +" M"+GAux + "\n";
					//			    			lineNumber = lineNumber + 10;
					//
					//			    			GCode = GCode +"N"+lineNumber+ " T = " + "\""+ wsTmp.getFerramenta().getName()+ "\"" + "\n";
					//			    			lineNumber = lineNumber + 10;
					//
					//			    			GCode = GCode +"N"+lineNumber+ " M6" + "\n";
					//			    			lineNumber = lineNumber + 10;
					//
					//			    			if (wsTmp.getOperation().isCoolant()){
					//			    				GCode = GCode +"N" + lineNumber + " M8" + "\n";
					//			    				lineNumber = lineNumber + 10;
					//			    			}
					//
					//			    			double xAux = acabamento.get(0).getInitialPoint().getX();
					//			    			double yAux = acabamento.get(0).getInitialPoint().getY();
					//			    			double zAux = acabamento.get(0).getInitialPoint().getZ();
					//
					//
					//			    			if(acabamento.get(0).getTipoDeMovimento()==LinearPath.SLOW_MOV){
					//			    				GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
					//			    				lineNumber = lineNumber + 10;
					//			    			}
					//			    			else{
					//			    				GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
					//			    				lineNumber = lineNumber + 10;
					//			    			}
					//
					//			    			for(int j = 0; j < acabamento.size(); j++)
					//			    			{
					//
					//			    				xAux = acabamento.get(j).getFinalPoint().getX();
					//			    				yAux = acabamento.get(j).getFinalPoint().getY();
					//			    				zAux = acabamento.get(j).getFinalPoint().getZ();
					//
					//
					//			    				//********** Testar se é fast(G0) ou slow(G1)
					//			    				if(acabamento.get(j).getTipoDeMovimento()==LinearPath.SLOW_MOV){
					//			    					if(l==0){
					//			    						GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
					//			    						lineNumber = lineNumber + 10;
					//			    					}
					//			    					else{
					//			    						if(acabamento.get(j-1).getFinalPoint().getX()==acabamento.get(j).getFinalPoint().getX()){
					//			    							GCode = GCode + "N" + lineNumber + " Y" + yAux + " Z" + zAux + "\n";
					//			    							lineNumber = lineNumber + 10;			    						
					//			    						}
					//			    						else{
					//			    							GCode = GCode + "N" + lineNumber + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
					//			    							lineNumber = lineNumber + 10;			    						
					//			    						}
					//			    					}
					//			    					l=1;
					//			    				}
					//			    				else{
					//			    					GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
					//			    					lineNumber = lineNumber + 10;
					//			    					l=0;
					//			    				}					
					//			    			}
					//			    		}	
					//
					//			    	}
				}


				/*************************************************************************************************************************************/

				if(wsTmp.getFeature().getClass().equals(RanhuraPerfilVee.class)){

					MovimentacaoRanhuraPerfilVee ranhuraV = new MovimentacaoRanhuraPerfilVee(wsTmp);


					this.feedRate = wsTmp.getCondicoesUsinagem().getVf();
					this.spindleRotation = wsTmp.getCondicoesUsinagem().getN();
					this.rotationDirection = wsTmp.getFerramenta().getHandOfCut();

					/********************************************************DESBASTE***************************************************/	
					if(wsTmp.getOperation().getClass() == BottomAndSideRoughMilling.class){
						ArrayList<LinearPath> desbaste = ranhuraV.getMovimentacaoDesbasteRanhuraPerfilVee();
						int GAux = 0;
						if (rotationDirection == 1){GAux = 3;}
						else if (rotationDirection == 2){GAux = 4;}
						else if (rotationDirection == 3){GAux = 5;}

						GCode = GCode +"N" + lineNumber + " S"+ spindleRotation +" F" +feedRate +" M"+GAux + "\n";
						lineNumber = lineNumber + 10;

						GCode = GCode +"N"+lineNumber+ " T = " + "\""+ wsTmp.getFerramenta().getName()+ "\"" + "\n";
						lineNumber = lineNumber + 10;

						GCode = GCode +"N"+lineNumber+ " M6" + "\n";
						lineNumber = lineNumber + 10;

						if (wsTmp.getOperation().isCoolant()){
							GCode = GCode +"N" + lineNumber + " M8" + "\n";
							lineNumber = lineNumber + 10;
						}

						double xAux = desbaste.get(0).getInitialPoint().getX();
						double yAux = desbaste.get(0).getInitialPoint().getY();
						double zAux = desbaste.get(0).getInitialPoint().getZ();


						if(desbaste.get(0).getTipoDeMovimento()==LinearPath.SLOW_MOV){
							GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
							lineNumber = lineNumber + 10;
						}
						else{
							GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
							lineNumber = lineNumber + 10;
						}

						for(int j = 0; j < desbaste.size(); j++)
						{
							xAux = desbaste.get(j).getFinalPoint().getX();
							yAux = desbaste.get(j).getFinalPoint().getY();
							zAux = desbaste.get(j).getFinalPoint().getZ();

							if(desbaste.get(j).getTipoDeMovimento()==LinearPath.SLOW_MOV){
								GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
								lineNumber = lineNumber + 10;
							}
							else{
								GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
								lineNumber = lineNumber + 10;
							}
						}
					}
					/********************************************************ACABAMENTO***************************************************/	
					if(wsTmp.getOperation().getClass() == BottomAndSideFinishMilling.class){
						ArrayList<LinearPath> acabamento = ranhuraV.getMovimentacaoAcabamentoRanhuraPerfilVee();

						int GAux = 0;
						if (rotationDirection == 1){GAux = 3;}
						else if (rotationDirection == 2){GAux = 4;}
						else if (rotationDirection == 3){GAux = 5;}

						GCode = GCode +"N" + lineNumber + " S"+ spindleRotation +" F" +feedRate +" M"+GAux + "\n";
						lineNumber = lineNumber + 10;

						GCode = GCode +"N"+lineNumber+ " T = " + "\""+ wsTmp.getFerramenta().getName()+ "\"" + "\n";
						lineNumber = lineNumber + 10;

						GCode = GCode +"N"+lineNumber+ " M6" + "\n";
						lineNumber = lineNumber + 10;

						if (wsTmp.getOperation().isCoolant()){
							GCode = GCode +"N" + lineNumber + " M8" + "\n";
							lineNumber = lineNumber + 10;
						}

						double xAux = acabamento.get(0).getInitialPoint().getX();
						double yAux = acabamento.get(0).getInitialPoint().getY();
						double zAux = acabamento.get(0).getInitialPoint().getZ();


						if(acabamento.get(0).getTipoDeMovimento()==LinearPath.SLOW_MOV){
							GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
							lineNumber = lineNumber + 10;
						}
						else{
							GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
							lineNumber = lineNumber + 10;
						}

						for(int j = 0; j < acabamento.size(); j++)
						{

							xAux = acabamento.get(j).getFinalPoint().getX();
							yAux = acabamento.get(j).getFinalPoint().getY();
							zAux = acabamento.get(j).getFinalPoint().getZ();


							//********** Testar se é fast(G0) ou slow(G1)
							if(acabamento.get(j).getTipoDeMovimento()==LinearPath.SLOW_MOV){
								GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
								lineNumber = lineNumber + 10;
							}
							else{
								GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
								lineNumber = lineNumber + 10;
							}					
						}
					}

					if (wsTmp.getOperation().isCoolant()){
						GCode = GCode +"N" + lineNumber + " M9" + "\n";
						lineNumber = lineNumber + 10;
					}
				}

				/*************************************************************************************************************************************/
				if(wsTmp.getFeature().getClass().equals(RanhuraPerfilRoundedU.class)){

					MovimentacaoRanhuraPerfilRoundedU ranhuraQuadU = new MovimentacaoRanhuraPerfilRoundedU(wsTmp);


					this.feedRate = wsTmp.getCondicoesUsinagem().getVf();
					this.spindleRotation = wsTmp.getCondicoesUsinagem().getN();
					this.rotationDirection = wsTmp.getFerramenta().getHandOfCut();

					/********************************************************DESBASTE***************************************************/	
					if(wsTmp.getOperation().getClass() == BottomAndSideRoughMilling.class){
						ArrayList<LinearPath> desbaste = ranhuraQuadU.getMovimentacaoDesbasteRanhuraPerfilRoundedU();
						int GAux = 0;
						if (rotationDirection == 1){GAux = 3;}
						else if (rotationDirection == 2){GAux = 4;}
						else if (rotationDirection == 3){GAux = 5;}

						GCode = GCode +"N" + lineNumber + " S"+ spindleRotation +" F" +feedRate +" M"+GAux + "\n";
						lineNumber = lineNumber + 10;

						GCode = GCode +"N"+lineNumber+ " T = " + "\""+ wsTmp.getFerramenta().getName()+ "\"" + "\n";
						lineNumber = lineNumber + 10;

						GCode = GCode +"N"+lineNumber+ " M6" + "\n";
						lineNumber = lineNumber + 10;

						if (wsTmp.getOperation().isCoolant()){
							GCode = GCode +"N" + lineNumber + " M8" + "\n";
							lineNumber = lineNumber + 10;
						}

						double xAux = desbaste.get(0).getInitialPoint().getX();
						double yAux = desbaste.get(0).getInitialPoint().getY();
						double zAux = desbaste.get(0).getInitialPoint().getZ();


						if(desbaste.get(0).getTipoDeMovimento()==LinearPath.SLOW_MOV){
							GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
							lineNumber = lineNumber + 10;
						}
						else{
							GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
							lineNumber = lineNumber + 10;
						}

						for(int j = 0; j < desbaste.size(); j++)
						{
							xAux = desbaste.get(j).getFinalPoint().getX();
							yAux = desbaste.get(j).getFinalPoint().getY();
							zAux = desbaste.get(j).getFinalPoint().getZ();

							if(desbaste.get(j).getTipoDeMovimento()==LinearPath.SLOW_MOV){
								GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
								lineNumber = lineNumber + 10;
							}
							else{
								GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
								lineNumber = lineNumber + 10;
							}
						}
					}
					/********************************************************ACABAMENTO***************************************************/	
					if(wsTmp.getOperation().getClass() == BottomAndSideFinishMilling.class){
						ArrayList<LinearPath> acabamento = ranhuraQuadU.getMovimentacaoAcabamentoRanhuraPerfilRoundedU();

						int GAux = 0;
						if (rotationDirection == 1){GAux = 3;}
						else if (rotationDirection == 2){GAux = 4;}
						else if (rotationDirection == 3){GAux = 5;}

						GCode = GCode +"N" + lineNumber + " S"+ spindleRotation +" F" +feedRate +" M"+GAux + "\n";
						lineNumber = lineNumber + 10;

						GCode = GCode +"N"+lineNumber+ " T = " + "\""+ wsTmp.getFerramenta().getName()+ "\"" + "\n";
						lineNumber = lineNumber + 10;

						GCode = GCode +"N"+lineNumber+ " M6" + "\n";
						lineNumber = lineNumber + 10;

						if (wsTmp.getOperation().isCoolant()){
							GCode = GCode +"N" + lineNumber + " M8" + "\n";
							lineNumber = lineNumber + 10;
						}

						double xAux = acabamento.get(0).getInitialPoint().getX();
						double yAux = acabamento.get(0).getInitialPoint().getY();
						double zAux = acabamento.get(0).getInitialPoint().getZ();


						if(acabamento.get(0).getTipoDeMovimento()==LinearPath.SLOW_MOV){
							GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
							lineNumber = lineNumber + 10;
						}
						else{
							GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
							lineNumber = lineNumber + 10;
						}

						for(int j = 0; j < acabamento.size(); j++)
						{

							xAux = acabamento.get(j).getFinalPoint().getX();
							yAux = acabamento.get(j).getFinalPoint().getY();
							zAux = acabamento.get(j).getFinalPoint().getZ();


							//********** Testar se é fast(G0) ou slow(G1)
							if(acabamento.get(j).getTipoDeMovimento()==LinearPath.SLOW_MOV){
								GCode = GCode + "N" + lineNumber + " G1" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
								lineNumber = lineNumber + 10;
							}
							else{
								GCode = GCode + "N" + lineNumber + " G0" + " X" + xAux + " Y" + yAux + " Z" + zAux + "\n";
								lineNumber = lineNumber + 10;
							}					
						}
					}

					if (wsTmp.getOperation().isCoolant()){
						GCode = GCode +"N" + lineNumber + " M9" + "\n";
						lineNumber = lineNumber + 10;
					}
				}

				/*************************************************************************************************************************************/


				if (wsTmp.getFeature().getClass().equals(Ranhura.class))
				{
					Ranhura ranhuraTmp = (Ranhura)wsTmp.getFeature();
					int GAux = 0;

					this.feedRate = wsTmp.getCondicoesUsinagem().getVf();
					this.spindleRotation = wsTmp.getCondicoesUsinagem().getN();
					this.rotationDirection = wsTmp.getFerramenta().getHandOfCut();


					if (rotationDirection == 1){GAux = 3;}
					else if (rotationDirection == 2){GAux = 4;}
					else if (rotationDirection == 3){GAux = 5;}

					GCode = GCode +"N" + lineNumber + " S"+ spindleRotation +" F" +feedRate +" M"+GAux + "\n";
					lineNumber = lineNumber + 10;

					double positX = ranhuraTmp.getPosicaoX();
					double positY = ranhuraTmp.getPosicaoY();
					double profundidadeZ = ranhuraTmp.getProfundidade();
					double larguraRanhura = ranhuraTmp.getLargura();
					double comprimentoBloco = projeto.getBloco().getComprimento();
					double larguraBloco = projeto.getBloco().getLargura();

					int HORIZONTAL = 0;
					int VERTICAL = 1;


					GCode = GCode +"N"+lineNumber+ " T = " + "\""+ wsTmp.getFerramenta().getName()+ "\"" + "\n";
					lineNumber = lineNumber + 10;

					GCode = GCode +"N"+lineNumber+ " M6" + "\n";
					lineNumber = lineNumber + 10;

					if (wsTmp.getOperation().isCoolant()){
						GCode = GCode +"N" + lineNumber + " M8" + "\n";
						lineNumber = lineNumber + 10;
					}

					wsTmp.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(wsTmp));
					Vector movimentacaoRanhura = DeterminarMovimentacao.getPontosMovimentacao(wsTmp);
					wsTmp.setPontosMovimentacao(movimentacaoRanhura);
					System.out.println("Quantidade de vetores " + ((Vector)wsTmp.getPontosMovimentacao()).size());
					Vector pontos = (Vector)wsTmp.getPontosMovimentacao().get(0);

					System.out.println("Quantidade de movimentacoes caso 0" + pontos.size());


					for (int i1 = 0; i1 < pontos.size(); i1++){

						Ponto pontoAux = (Ponto)pontos.get(i1);
						double xAux = pontoAux.getX();
						double yAux = pontoAux.getY();
						double zAux = pontoAux.getZ();

						GCode = GCode +"N" + lineNumber + " G1" + " X"+xAux+ " Y" +yAux + " Z"+(-zAux) + "\n";
						lineNumber = lineNumber + 10;


					}

					GCode = GCode +"N" + lineNumber + " G0" + " Z" + alturaSeguranca + "\n";
					lineNumber = lineNumber + 10;

					if (wsTmp.getOperation().isCoolant()){
						GCode = GCode +"N" + lineNumber + " M9" + "\n";
						lineNumber = lineNumber + 10;
					}

				}

				anterior = wsTmp.getFeature();

				//}

			}
		}



		GCode = GCode +"N" + lineNumber + " M30" + "\n";
		lineNumber = lineNumber + 10;


		return GCode;


	}

	/*
	public ArrayList<String> GenerateGCode(){


		ArrayList<String> ArrayLines = new ArrayList<String>();

		double alturaSeguranca = 5.0;

		String LineAux = ("N" + lineNumber + " G54");
		ArrayLines.add(LineAux);
		lineNumber = lineNumber + 10;


		//LineAux = ("N"+lineNumber+ " G1" + " Z"+alturaSeguranca);
		//ArrayLines.add(LineAux);
		//lineNumber = lineNumber + 10;


		Feature anterior = null;

		for (int index = 0; index < this.Workingsteps.size(); index++)
		{

			Vector workinstepsFaceTmp = (Vector)this.Workingsteps.get(index);
			for (int i = 0; i < workinstepsFaceTmp.size(); i++)
			{
				String LineAuxWS = null;

				Workingstep wsTmp = (Workingstep)workinstepsFaceTmp.elementAt(i);

				if ((anterior == null) || !(wsTmp.getFeature().equals(anterior))){

				if (wsTmp.getFeature().getTipo() == Feature.FURO)
				{
					Furo furoTmp = (Furo)wsTmp.getFeature();
					int GAux = 0;

					this.feedRate = wsTmp.getCondicoesUsinagem().getVf();
					this.spindleRotation = wsTmp.getCondicoesUsinagem().getN();
					this.rotationDirection = wsTmp.getFerramenta().getHandOfCut();

					if (rotationDirection == 1){GAux = 3;}
					else if (rotationDirection == 2){GAux = 4;}
					else if (rotationDirection == 3){GAux = 5;}

					LineAuxWS = ("N" + lineNumber + " S"+ spindleRotation +" F" +feedRate +" M"+GAux);
					ArrayLines.add(LineAuxWS);
					lineNumber = lineNumber + 10;
					double positX = furoTmp.getPosicaoX();
					double positY = furoTmp.getPosicaoY();
					double profundidadeZ = furoTmp.getProfundidade();
					//String faceBloco = wsTmp.getFaceMae().getTipoString();
					double alturaBloco = projeto.getBloco().getProfundidade();
					String nomeFerramenta = wsTmp.getFerramenta().getName();




					LineAuxWS = ("N"+lineNumber+ " T = "+ "\"" + nomeFerramenta+ "\"");
					ArrayLines.add(LineAuxWS);
					lineNumber = lineNumber + 10;

					LineAuxWS = ("N"+lineNumber+ " M6");
					ArrayLines.add(LineAuxWS);
					lineNumber = lineNumber + 10;

					LineAuxWS = ("N"+lineNumber+ " G0" + " X"+positX + " Y"+positY);
					ArrayLines.add(LineAuxWS);
					lineNumber = lineNumber + 10;

					LineAux = ("N" + lineNumber + " M8");
					ArrayLines.add(LineAux);
					lineNumber = lineNumber + 10;

					LineAuxWS = ("N"+lineNumber+ " CYCLE81("+(alturaBloco+10)+", "+alturaBloco+", "
							     +alturaSeguranca+ ", "+(alturaBloco-profundidadeZ)+", " +")");
					ArrayLines.add(LineAuxWS);
					lineNumber = lineNumber + 10;

					LineAux = ("N" + lineNumber + " M9");
					ArrayLines.add(LineAux);
					lineNumber = lineNumber + 10;

					//LineAuxWS = ("N"+lineNumber+ " G1" + " Z"+(profundidadeZ + alturaSeguranca));
					//ArrayLines.add(LineAuxWS);
					//lineNumber = lineNumber + 10;

					//LineAuxWS = ("N"+lineNumber+ " G1" + " Z"+(-profundidadeZ - alturaSeguranca));
					//ArrayLines.add(LineAuxWS);
					//lineNumber = lineNumber + 10;

				}

			    if (wsTmp.getFeature().getTipo() == Feature.CAVIDADE)
				{
			    	Cavidade cavidadeTmp = (Cavidade)wsTmp.getFeature();
					int GAux = 0;

					this.feedRate = wsTmp.getCondicoesUsinagem().getVf();
					this.spindleRotation = wsTmp.getCondicoesUsinagem().getN();
					this.rotationDirection = wsTmp.getFerramenta().getHandOfCut();


					if (rotationDirection == 1){GAux = 3;}
					else if (rotationDirection == 2){GAux = 4;}
					else if (rotationDirection == 3){GAux = 5;}

					LineAuxWS = ("N" + lineNumber + " S"+ spindleRotation +" F" +feedRate +" M"+GAux);
					ArrayLines.add(LineAuxWS);
					lineNumber = lineNumber + 10;

					double positX = cavidadeTmp.getPosicaoX();
					double positY = cavidadeTmp.getPosicaoY();
					double comprimentoCavidade = cavidadeTmp.getComprimento();
					double larguraCavidade = cavidadeTmp.getLargura();
					double xCentro = (cavidadeTmp.getPosicaoX()+ (comprimentoCavidade/2));
					double yCentro = (cavidadeTmp.getPosicaoY()+ (larguraCavidade/2));
					double raioCavidade = cavidadeTmp.getRaio();
					double profundidadeZ = cavidadeTmp.getProfundidade()*-1;
					//String faceBloco = wsTmp.getFaceMae().getTipoString();
					double planoDeReferencia = cavidadeTmp.getPosicaoZ();
					//double alturaBloco = projeto.getBloco().getProfundidade();
					double avancoVertical = wsTmp.getCondicoesUsinagem().getAp();
					String nomeFerramenta = wsTmp.getFerramenta().getName();
					double velCorte = wsTmp.getCondicoesUsinagem().getVc();
					double tolerancia = wsTmp.getFeature().getTolerancia();

					LineAuxWS = ("N"+lineNumber+ " T = "+ "\"" + nomeFerramenta+ "\"");
					ArrayLines.add(LineAuxWS);
					lineNumber = lineNumber + 10;

					LineAuxWS = ("N"+lineNumber+ " M6");
					ArrayLines.add(LineAuxWS);
					lineNumber = lineNumber + 10;

					LineAux = ("N" + lineNumber + " M8");
					ArrayLines.add(LineAux);
					lineNumber = lineNumber + 10;

					if (wsTmp.getFeature().isAcabamento()){

						LineAuxWS = ("N"+lineNumber + "POCKET1("+ ((planoDeReferencia*-1)+10)+", "+(planoDeReferencia*-1)+
							     ", "+alturaSeguranca + ", "+((planoDeReferencia*-1)+profundidadeZ)+ ", "
							     +", "+comprimentoCavidade + ", "+ larguraCavidade + ", "+ 
							     raioCavidade+ ", " + xCentro +", "+ yCentro +", "+ "0" + ", "+
							     feedRate + ", " + feedRate+ ", "+avancoVertical + ", "+
							     "2"+ ", " + tolerancia + ", " + "1" + ", "+ ", " + ", "+ ", " + ")" 
							     );

					ArrayLines.add(LineAuxWS);
					lineNumber = lineNumber + 10;


					}

					else{
						LineAuxWS = ("N"+lineNumber + " POCKET1("+ ((planoDeReferencia*-1)+10)+","+(planoDeReferencia*-1)+
							     ","+alturaSeguranca + ","+ ((planoDeReferencia*-1)+profundidadeZ)+ ","
							     +","+comprimentoCavidade + ","+ larguraCavidade + ","+ 
							     raioCavidade+ "," + xCentro +","+ yCentro +","+ "0" + ","+
							     feedRate + "," + feedRate+ ","+avancoVertical + ","+
							     "2"+ "," +"," + "0" + ","+ "," + ","+")" 
							     );

					ArrayLines.add(LineAuxWS);
					lineNumber = lineNumber + 10;


						}


					LineAux = ("N" + lineNumber + " M9");
					ArrayLines.add(LineAux);
					lineNumber = lineNumber + 10;


				}

			    if (wsTmp.getFeature().getTipo() == Feature.RANHURA)
				{
			    	Ranhura ranhuraTmp = (Ranhura)wsTmp.getFeature();
					int GAux = 0;

					this.feedRate = wsTmp.getCondicoesUsinagem().getVf();
					this.spindleRotation = wsTmp.getCondicoesUsinagem().getN();
					this.rotationDirection = wsTmp.getFerramenta().getHandOfCut();


					if (rotationDirection == 1){GAux = 3;}
					else if (rotationDirection == 2){GAux = 4;}
					else if (rotationDirection == 3){GAux = 5;}

					LineAuxWS = ("N" + lineNumber + " S"+ spindleRotation +" F" +feedRate +" M"+GAux);
					ArrayLines.add(LineAuxWS);
					lineNumber = lineNumber + 10;

					double positX = ranhuraTmp.getPosicaoX();
					double positY = ranhuraTmp.getPosicaoY();
					double profundidadeZ = ranhuraTmp.getProfundidade();
					double larguraRanhura = ranhuraTmp.getLargura();
					double comprimentoBloco = projeto.getBloco().getComprimento();
					double larguraBloco = projeto.getBloco().getLargura();

					int HORIZONTAL = 0;
					int VERTICAL = 1;

					LineAuxWS = ("N"+lineNumber+ " T = " + "\""+ wsTmp.getFerramenta().getName()+ "\"");
					ArrayLines.add(LineAuxWS);
					lineNumber = lineNumber + 10;

					LineAuxWS = ("N"+lineNumber+ " M6");
					ArrayLines.add(LineAuxWS);
					lineNumber = lineNumber + 10;

					LineAux = ("N" + lineNumber + " M8");
					ArrayLines.add(LineAux);
					lineNumber = lineNumber + 10;

					Vector pontos = (Vector)wsTmp.getPontosMovimentacao().get(0);

					System.out.println(pontos.size());


					for (int i1 = 0; i1 < pontos.size(); i1++){

						Ponto pontoAux = (Ponto)pontos.get(i1);
						double xAux = pontoAux.getX();
						double yAux = pontoAux.getY();
						double zAux = pontoAux.getZ();

						LineAux = ("N" + lineNumber + " G1" + " X"+xAux+ 
								" Y" +yAux + " Z"+(-zAux));
						ArrayLines.add(LineAux);
						lineNumber = lineNumber + 10;


					}

					LineAux = ("N" + lineNumber + " G0" + " Z" + alturaSeguranca);
					ArrayLines.add(LineAux);
					lineNumber = lineNumber + 10;

					LineAux = ("N" + lineNumber + " M9");
					ArrayLines.add(LineAux);
					lineNumber = lineNumber + 10;

				}

			    anterior = wsTmp.getFeature();

				}

			}
		}



		LineAux = ("N" + lineNumber + " M30");
		ArrayLines.add(LineAux);
		lineNumber = lineNumber + 10;




		return ArrayLines;
	} */

}
