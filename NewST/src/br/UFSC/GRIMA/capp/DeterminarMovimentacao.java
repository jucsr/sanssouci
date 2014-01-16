package br.UFSC.GRIMA.capp;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.vecmath.Point3d;
import br.UFSC.GRIMA.capp.machiningOperations.Boring;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideFinishMilling;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.capp.machiningOperations.CenterDrilling;
import br.UFSC.GRIMA.capp.machiningOperations.Drilling;
import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraDeWorkingsteps;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.Degrau;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.Furo;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilQuadradoU;
import br.UFSC.GRIMA.entidades.ferramentas.BoringTool;
import br.UFSC.GRIMA.entidades.ferramentas.CenterDrill;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.entidades.ferramentas.TwistDrill;
import br.UFSC.GRIMA.util.CircularPath;
import br.UFSC.GRIMA.util.LinearPath;
import br.UFSC.GRIMA.util.Path;
import br.UFSC.GRIMA.util.Ponto;
/**
 * 
 * @author Jc
 *
 */


public class DeterminarMovimentacao 
{
	public Workingstep ws;
	public DeterminarMovimentacao()
	{
		System.out.println("Determinar mov");

	}
	public ArrayList<Path> getTrajetoriasCantosDaCavidade(Workingstep ws)
	{
		System.out.println("Trajetorias cantos");
		ArrayList<Path> trajetoriasCantos = new ArrayList<Path>();
		Cavidade cavidade = (Cavidade)ws.getFeature();
		
		System.out.println("GGGGGGGG RAIO 1 : "+ cavidade.getRaio());
		
		Ferramenta f = ws.getFerramenta();

		//		if(ws.getOperation().getStartPoint() != new Point3d(0, 0, 0) && ws.getTipo() == Workingstep.DESBASTE) // isto indica q este WS é o segundo de desbaste
		if(ws.getOperation().getStartPoint() != new Point3d(0, 0, 0) && ws.getTipo() == Workingstep.DESBASTE) // isto indica q este WS é o segundo de desbaste
		{
			boolean terminouZ_SE = false; // verifica se a altura desejada já foi alcanzada (Canto Superior Esquerdo)
			boolean terminouXY_SE = false; // verifica se no plano XY já se consiguiu barrer toda a superficie (Canto Superior Esquerdo)
			boolean terminouZ_SD = false; // verifica se a altura desejada já foi alcanzada (Canto Superior Direito)
			boolean terminouXY_SD = false; // verifica se no plano XY já se consiguiu barrer toda a superficie (Canto Superior Direito)
			boolean terminouZ_IE = false; // verifica se a altura desejada já foi alcanzada (Canto Inferior Esquerdo)
			boolean terminouXY_IE = false; // verifica se no plano XY já se consiguiu barrer toda a superficie (Canto Inferior Esquerdo)
			boolean terminouZ_ID = false; // verifica se a altura desejada já foi alcanzada (Canto Inferior Direito)
			boolean terminouXY_ID = false; // verifica se no plano XY já se consiguiu barrer toda a superficie (Canto Inferior Direito)
			double zAtual = ws.getOperation().getRetractPlane();
			double apUtilizada = ws.getCondicoesUsinagem().getAp();
			double aeUtilizado = ws.getCondicoesUsinagem().getAe();
			//System.out.println("Antes do raio");
			//System.out.println("Feature " + ws.getFeature());
			//System.out.println("Ferramenta atual " + ws.getFerramenta());
			//System.out.println("Raio atual " + ws.getFerramenta().getDiametroFerramenta()/2);
			//System.out.println("Ferramenta 0 " + ((Workingstep)ws.getFeature().getWorkingsteps().get(0)).getFerramenta());
			//System.out.println("Raio ferramenta 0 " + ((Workingstep)ws.getFeature().getWorkingsteps().get(0)).getFerramenta().getDiametroFerramenta()/2);
			double raioAnteriorIt = 0;
			//System.out.println("size ws " + ws.getFeature().getWorkingsteps().size());
			for(int i = 0; i < ws.getFeature().getWorkingsteps().size(); i++){
				//System.out.println("raio ws" + i + "= " + ((Workingstep)ws.getFeature().getWorkingsteps().get(i)).getFerramenta().getDiametroFerramenta()/2);
				if (((Workingstep)ws.getFeature().getWorkingsteps().get(i)).getFerramenta().getDiametroFerramenta()/2 > raioAnteriorIt){
					raioAnteriorIt = ((Workingstep)ws.getFeature().getWorkingsteps().get(i)).getFerramenta().getDiametroFerramenta()/2;
				}

				//System.out.println("raio anterior" + raioAnteriorIt);
			}

			double raioAnterior = raioAnteriorIt;
			//double raioAnterior = cavidade.getLargura() / 2 - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide() - ws.getOperation().getStartPoint().y;
			System.out.println("raioAnterior = " + raioAnterior);
			double xyAtual = raioAnterior * (1 - Math.sqrt(2)) + 0.25 * f.getDiametroFerramenta();
			double raioCavidadeSemAllowance = cavidade.getRaio() - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide();
			//System.out.println("raioAnterior = " + raioAnterior);
			Point3d pontoAtual = new Point3d();
			Point3d initialPoint = new Point3d(cavidade.X, cavidade.Y, zAtual);
			Point3d finalPoint = new Point3d(cavidade.X + f.getDiametroFerramenta() / 2 + ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide(), cavidade.Y + cavidade.getLargura() - raioAnterior - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide()  , zAtual);
			LinearPath toPontoInicial = new LinearPath(initialPoint, finalPoint);
			toPontoInicial.setTipoDeMovimento(LinearPath.FAST_MOV);
			trajetoriasCantos.add(toPontoInicial);
			initialPoint = new Point3d(cavidade.X + f.getDiametroFerramenta() / 2 + ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide(), cavidade.Y + cavidade.getLargura() - raioAnterior - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide()  , zAtual);
			finalPoint = new Point3d(initialPoint.getX(), initialPoint.getY(), -cavidade.Z);
			LinearPath aproximacao = new LinearPath(initialPoint, finalPoint);
			trajetoriasCantos.add(aproximacao);
			pontoAtual = trajetoriasCantos.get(trajetoriasCantos.size() - 1).getFinalPoint(); 
			zAtual = aproximacao.getFinalPoint().getZ();
			System.out.println("zAtual = " + zAtual);
			boolean ladoA_SE = true;
			double avancoLateral;

			System.out.println("Iniciando canto SE");
			//Aqui eh feito o desbaste do canto superior esquerdo
			while (!terminouZ_SE)
			{

				//				if (zAtual - apUtilizada >= - cavidade.getProfundidade())
				if (Math.abs(zAtual - apUtilizada) <= Math.abs(-cavidade.Z -cavidade.getProfundidade()))
				{

					apUtilizada = ws.getCondicoesUsinagem().getAp();
				} else
				{

					apUtilizada = (cavidade.getProfundidade() + cavidade.Z) + zAtual;
				}
				zAtual -= apUtilizada;

				System.out.println("apUtilizada " + apUtilizada);
				initialPoint = new Point3d(finalPoint.x, finalPoint.y, finalPoint.z);
				finalPoint = new Point3d(initialPoint.x, initialPoint.y, zAtual);
				LinearPath mergulho = new LinearPath(initialPoint, finalPoint);
				trajetoriasCantos.add(mergulho);

				System.out.println("zAtual " + zAtual);
				System.out.println("profundidade cavidade " + cavidade.getProfundidade());
				System.out.println("posicao Z caviade " + cavidade.getPosicaoZ());
				//if(zAtual == -cavidade.getProfundidade() - cavidade.getPosicaoZ()) //atencao!, ajustar para caso absoluto (posZ)
				if(zAtual == (-cavidade.Z -cavidade.getProfundidade()))
				{
					terminouZ_SE = true;
				}


				avancoLateral = (ws.getFerramenta().getDiametroFerramenta()/2);

				//				Optimiza o numero de passadas que faz.. mas tem que modificar os pontos em todo o codigo.
				//				
				//				Verificar ambos os LinearPaths pre-Curva.

				boolean avancoLateralConcluido = false;

				aeUtilizado = ws.getCondicoesUsinagem().getAe();

				terminouXY_SE = false;
				while (!terminouXY_SE)
				{


					if(!avancoLateralConcluido){




						if(ladoA_SE){

							//Verificar se e necessario fazer a curva
							if (avancoLateral + (f.getDiametroFerramenta() / 2) < (raioCavidadeSemAllowance - (f.getDiametroFerramenta() / 2) - avancoLateral)){

								//LinearPath pre-Curva
								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
								finalPoint = new Point3d(initialPoint.getX(), initialPoint.getY() + (raioAnterior - raioCavidadeSemAllowance) - avancoLateral + (f.getDiametroFerramenta() / 2), zAtual);
								LinearPath LinearPathCantoA = new LinearPath(initialPoint, finalPoint);
								trajetoriasCantos.add(LinearPathCantoA);

								//CircularPath em direcao a B
								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual); 
								finalPoint = new Point3d(initialPoint.getX() + raioCavidadeSemAllowance - (ws.getFerramenta().getDiametroFerramenta()/2), cavidade.Y - avancoLateral + cavidade.getLargura() - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide(),zAtual);
								Point3d centerCanto = new Point3d(cavidade.X + ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide() + raioCavidadeSemAllowance, cavidade.Y + cavidade.getLargura() - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide() - raioCavidadeSemAllowance, zAtual);


								CircularPath CircularPathCantoA = new CircularPath(initialPoint, finalPoint, centerCanto);
								CircularPathCantoA.setSense(CircularPath.CW);
								trajetoriasCantos.add(CircularPathCantoA);

								//LinearPath ate B
								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
								finalPoint = new Point3d(cavidade.X + raioAnterior + ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide(), initialPoint.getY(), zAtual );

								LinearPath LinearPath2BwithCP = new LinearPath(initialPoint, finalPoint);
								trajetoriasCantos.add(LinearPath2BwithCP);

								//Verifica se o avanco lateral ja foi concluido para avancar em profundidade

								//					if(!(avancoLateral < ((raioAnterior - (raioAnterior/ Math.sqrt(2))) + (ws.getFerramenta().getDiametroFerramenta() * 0.25) ))){
								if(!(avancoLateral + (ws.getFerramenta().getDiametroFerramenta()/2) <= ((raioAnterior - (raioAnterior/ Math.sqrt(2))) ))){	
									//Manda de volta ate o limite do canto

									initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
									finalPoint = new Point3d(initialPoint.getX(), initialPoint.getY() + avancoLateral - (ws.getFerramenta().getDiametroFerramenta()/2), zAtual);

									if(!(initialPoint.getX() == finalPoint.getX() &&
											initialPoint.getY() == finalPoint.getY() && initialPoint.getZ() == finalPoint.getZ())){
										LinearPath linearLimite = new LinearPath(initialPoint, finalPoint);
										trajetoriasCantos.add(linearLimite);
									}

									avancoLateralConcluido = true;
								}

								else {
									// incrementar Ae e avancar Lateralmente

									if(( avancoLateral + aeUtilizado > (raioAnterior - (raioAnterior/(Math.sqrt(2)))))){
										aeUtilizado = (raioAnterior - (raioAnterior/(Math.sqrt(2)))) - avancoLateral;

									}

									avancoLateral += aeUtilizado;

									//LinearPath de avanco
									initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
									finalPoint = new Point3d(initialPoint.getX(), initialPoint.getY() - aeUtilizado, zAtual);

									LinearPath LPAvancoLateral = new LinearPath(initialPoint, finalPoint);
									trajetoriasCantos.add(LPAvancoLateral);


								}


							}

							else{

								//LinearPath subindo
								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
								//						finalPoint = new Point3d(initialPoint.getX(), initialPoint.getY() + raioAnterior - (ws.getFerramenta().getDiametroFerramenta()/2) - avancoLateral, zAtual);
								finalPoint = new Point3d(initialPoint.getX(), initialPoint.getY() + raioAnterior - avancoLateral, zAtual);
								LinearPath LinearPathCantoA = new LinearPath(initialPoint, finalPoint);
								trajetoriasCantos.add(LinearPathCantoA);

								//LinearPath ate B
								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
								finalPoint = new Point3d(cavidade.X + raioAnterior + ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide(), initialPoint.getY(), zAtual ); 

								LinearPath LinearPath2BwithoutCP = new LinearPath(initialPoint, finalPoint);
								trajetoriasCantos.add(LinearPath2BwithoutCP);

								//Verifica se o avanco lateral ja foi concluido para avancar em profundidade

								//					if(!(avancoLateral < ((raioAnterior - (raioAnterior/ Math.sqrt(2))) + (ws.getFerramenta().getDiametroFerramenta() * 0.25) ))){
								if(!(avancoLateral + (ws.getFerramenta().getDiametroFerramenta()/2) <= ((raioAnterior - (raioAnterior/ Math.sqrt(2))) ))){	
									//Manda de volta ate o limite do canto (volta para cima)

									initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
									finalPoint = new Point3d(initialPoint.getX(), initialPoint.getY() + avancoLateral -(ws.getFerramenta().getDiametroFerramenta()/2), zAtual);
									//aqui emcima estava finaPoint..ao em vez de initialPoint verificar outros
									if(!(initialPoint.getX() == finalPoint.getX() &&
											initialPoint.getY() == finalPoint.getY() && initialPoint.getZ() == finalPoint.getZ())){

										LinearPath linearLimite = new LinearPath(initialPoint, finalPoint);
										trajetoriasCantos.add(linearLimite);
									}

									avancoLateralConcluido = true;
								}

								else {
									// incrementar Ae e avancar Lateralmente

									if(( avancoLateral + aeUtilizado > (raioAnterior - (raioAnterior/(Math.sqrt(2)))))){
										aeUtilizado = (raioAnterior - (raioAnterior/(Math.sqrt(2)))) - avancoLateral;

									}

									avancoLateral += aeUtilizado;

									//LinearPath de avanco
									initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
									finalPoint = new Point3d(initialPoint.getX(), initialPoint.getY() - aeUtilizado, zAtual);

									LinearPath LPAvancoLateral = new LinearPath(initialPoint, finalPoint);
									trajetoriasCantos.add(LPAvancoLateral);


								}

							}

							ladoA_SE = false; // Seta como falso para passar que o path seguinte seja o Lado B
						}

						else{


							//Verificar se e necessario fazer a curva

							if (avancoLateral + (f.getDiametroFerramenta() / 2) < (raioCavidadeSemAllowance - (f.getDiametroFerramenta() / 2) - avancoLateral)){

								//LinearPath pre Curva


								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), finalPoint.getZ());
								finalPoint = new Point3d(initialPoint.getX() - raioAnterior + raioCavidadeSemAllowance + avancoLateral - (f.getDiametroFerramenta() / 2) , initialPoint.getY(), zAtual);

								LinearPath linearPathB = new LinearPath(initialPoint, finalPoint);
								trajetoriasCantos.add(linearPathB);

								//CircularPath
								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual); 
								finalPoint = new Point3d(initialPoint.getX() - raioCavidadeSemAllowance + avancoLateral,  cavidade.Y + cavidade.getLargura() - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide() - raioCavidadeSemAllowance - avancoLateral, zAtual);

								Point3d centerCanto = new Point3d(cavidade.X + ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide() + raioCavidadeSemAllowance, cavidade.Y + cavidade.getLargura() - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide() - raioCavidadeSemAllowance, zAtual);

								CircularPath CircularPathCantoB = new CircularPath(initialPoint, finalPoint,  centerCanto);
								CircularPathCantoB.setSense(CircularPath.CCW);
								trajetoriasCantos.add(CircularPathCantoB);

								//LinearPath ate A
								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
								//						finalPoint = new Point3d(cavidade.X + avancoLateral +  f.getDiametroFerramenta() / 2 + ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide(), cavidade.Y + cavidade.getLargura() - raioAnterior - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide()  , zAtual);
								finalPoint = new Point3d(initialPoint.getX(), cavidade.Y + cavidade.getLargura() - raioAnterior - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide()  , zAtual);
								LinearPath LinearPath2AwithCP = new LinearPath(initialPoint, finalPoint);
								trajetoriasCantos.add(LinearPath2AwithCP);

								//Verifica se o avanco lateral ja foi concluido para avancar em profundidade


								if(!(avancoLateral + (ws.getFerramenta().getDiametroFerramenta()/2) <= ((raioAnterior - (raioAnterior/ Math.sqrt(2)))  ))){
									//Manda de volta ate o limite do canto (volta pra esquerda)

									initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
									finalPoint = new Point3d(initialPoint.getX() - avancoLateral + (ws.getFerramenta().getDiametroFerramenta()/2), initialPoint.getY(), zAtual);

									if(!(initialPoint.getX() == finalPoint.getX() &&
											initialPoint.getY() == finalPoint.getY() && initialPoint.getZ() == finalPoint.getZ())){
										LinearPath linearLimite = new LinearPath(initialPoint, finalPoint);
										trajetoriasCantos.add(linearLimite);
									}

									avancoLateralConcluido = true;
								}

								else{
									// incrementar Ae e avancar Lateralmente

									if(( avancoLateral + aeUtilizado > (raioAnterior - (raioAnterior/(Math.sqrt(2)))))){
										aeUtilizado = (raioAnterior - (raioAnterior/(Math.sqrt(2)))) - avancoLateral;

									}

									avancoLateral += aeUtilizado;

									//LinearPath de avanco
									initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
									finalPoint = new Point3d(initialPoint.getX() + aeUtilizado, initialPoint.getY(), zAtual);

									LinearPath LPAvancoLateral = new LinearPath(initialPoint, finalPoint);
									trajetoriasCantos.add(LPAvancoLateral);

								}


							}


							else{
								//LinearPath


								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), finalPoint.getZ());
								finalPoint = new Point3d(initialPoint.getX() - raioAnterior + avancoLateral , initialPoint.getY(), zAtual);

								LinearPath linearPathB = new LinearPath(initialPoint, finalPoint);
								trajetoriasCantos.add(linearPathB);

								//LinearPath ate A
								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);

								finalPoint = new Point3d(initialPoint.getX(), cavidade.Y + cavidade.getLargura() - raioAnterior - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide(), zAtual);
								//finalPoint = new Point3d(cavidade.X + avancoLateral +  f.getDiametroFerramenta() / 2 + ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide(), cavidade.Y + cavidade.getLargura() - raioAnterior - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide()  , zAtual);
								LinearPath LinearPath2AwithoutCP = new LinearPath(initialPoint, finalPoint);
								trajetoriasCantos.add(LinearPath2AwithoutCP);


								if(!(avancoLateral + (ws.getFerramenta().getDiametroFerramenta()/2) <= ((raioAnterior - (raioAnterior/ Math.sqrt(2))) ))){
									//Manda de volta ate o limite do canto (volta pra esquerda)

									initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
									finalPoint = new Point3d(initialPoint.getX() - avancoLateral + (ws.getFerramenta().getDiametroFerramenta()/2), initialPoint.getY(), zAtual);

									if(!(initialPoint.getX() == finalPoint.getX() &&
											initialPoint.getY() == finalPoint.getY() && initialPoint.getZ() == finalPoint.getZ())){

										LinearPath linearLimite = new LinearPath(initialPoint, finalPoint);
										trajetoriasCantos.add(linearLimite);
									}

									avancoLateralConcluido = true;
								}

								else{
									// incrementar Ae e avancar Lateralmente

									if(( avancoLateral + aeUtilizado > (raioAnterior - (raioAnterior/(Math.sqrt(2)))))){

										aeUtilizado = (raioAnterior - (raioAnterior/(Math.sqrt(2)))) - avancoLateral;

									}

									avancoLateral += aeUtilizado;

									//LinearPath de avanco
									initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
									finalPoint = new Point3d(initialPoint.getX() + aeUtilizado, initialPoint.getY(), zAtual);

									LinearPath LPAvancoLateral = new LinearPath(initialPoint, finalPoint);
									trajetoriasCantos.add(LPAvancoLateral);

								}



							}

							ladoA_SE = true;// Seta como verdadeira para que o path seguinte seja o Lado A

						}

					}

					else {

						terminouXY_SE = true;

					}

				}


			}

			//LinearPath Saida

			initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
			finalPoint = new Point3d(initialPoint.getX(), initialPoint.getY(), ws.getOperation().getRetractPlane());

			LinearPath pathSaida = new LinearPath(initialPoint, finalPoint);
			pathSaida.setTipoDeMovimento(LinearPath.FAST_MOV);
			trajetoriasCantos.add(pathSaida);

			zAtual = ws.getOperation().getRetractPlane();


			//Inicializa o canto IE
			initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
			finalPoint = new Point3d(cavidade.X + f.getDiametroFerramenta() / 2 + ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide(), cavidade.Y + cavidade.getLargura() - raioAnterior - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide()  , zAtual);
			toPontoInicial = new LinearPath(initialPoint, finalPoint);
			toPontoInicial.setTipoDeMovimento(LinearPath.FAST_MOV);
			if(!(initialPoint.getX() == finalPoint.getX() &&
					initialPoint.getY() == finalPoint.getY() && initialPoint.getZ() == finalPoint.getZ())){
				trajetoriasCantos.add(toPontoInicial);
			}
			initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY() , zAtual);
			finalPoint = new Point3d(initialPoint.getX(), initialPoint.getY(), -cavidade.Z);
			aproximacao = new LinearPath(initialPoint, finalPoint);
			trajetoriasCantos.add(aproximacao);
			zAtual = aproximacao.getFinalPoint().getZ();
			boolean ladoA_IE = true;

			System.out.println("Iniciando canto IE");
			//Aqui eh feito o desbaste do canto inferior esquerdo
			while (!terminouZ_IE)
			{


				//				if (zAtual - apUtilizada >= - cavidade.getProfundidade())
				if (Math.abs(zAtual - apUtilizada) <= Math.abs(-cavidade.Z -cavidade.getProfundidade()))
				{
					apUtilizada = ws.getCondicoesUsinagem().getAp();
				} else
				{
					apUtilizada = (cavidade.getProfundidade() + cavidade.Z) + zAtual;
				}

				zAtual -= apUtilizada;

				initialPoint = new Point3d(finalPoint.x, finalPoint.y, finalPoint.z);
				finalPoint = new Point3d(initialPoint.x, initialPoint.y, zAtual);
				LinearPath mergulho = new LinearPath(initialPoint, finalPoint);
				trajetoriasCantos.add(mergulho);

				//				if(zAtual == -cavidade.getProfundidade() - cavidade.getPosicaoZ()) //atencao!, ajustar para caso absoluto (posZ)
				if(zAtual == (-cavidade.Z -cavidade.getProfundidade()))
				{
					terminouZ_IE = true;
				}

				avancoLateral =  (ws.getFerramenta().getDiametroFerramenta()/2);
				System.out.println("AVANCO LATERAL " + avancoLateral);

				boolean avancoLateralConcluido = false;

				terminouXY_IE = false;
				while (!terminouXY_IE)
				{
					if(!avancoLateralConcluido){

						if(ladoA_IE){

							//Verificar se e necessario fazer a curva
							//							
							if (avancoLateral + (f.getDiametroFerramenta() / 2) < (raioCavidadeSemAllowance - (f.getDiametroFerramenta() / 2) - avancoLateral)){

								//LinearPath pre-Curva
								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
								finalPoint = new Point3d(initialPoint.getX(), cavidade.Y + raioCavidadeSemAllowance + avancoLateral - (f.getDiametroFerramenta() / 2), zAtual);
								LinearPath LinearPathCantoA = new LinearPath(initialPoint, finalPoint);
								trajetoriasCantos.add(LinearPathCantoA);

								//CircularPath em direcao a B
								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual); 
								finalPoint = new Point3d(initialPoint.getX() + raioCavidadeSemAllowance - (ws.getFerramenta().getDiametroFerramenta()/2), cavidade.Y + avancoLateral + ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide(),zAtual);
								Point3d centerCanto = new Point3d(cavidade.X + ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide() + raioCavidadeSemAllowance, cavidade.Y + ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide() + raioCavidadeSemAllowance, zAtual);


								CircularPath CircularPathCantoA = new CircularPath(initialPoint, finalPoint, centerCanto);
								CircularPathCantoA.setSense(CircularPath.CCW);
								trajetoriasCantos.add(CircularPathCantoA);

								//LinearPath ate B
								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
								finalPoint = new Point3d(cavidade.X + raioAnterior + ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide(), initialPoint.getY(), zAtual );

								LinearPath LinearPath2BwithCP = new LinearPath(initialPoint, finalPoint);
								trajetoriasCantos.add(LinearPath2BwithCP);

								//Verifica se o avanco lateral ja foi concluido para avancar em profundidade

								//							
								if(!(avancoLateral + (ws.getFerramenta().getDiametroFerramenta()/2) <= ((raioAnterior - (raioAnterior/ Math.sqrt(2)))  ))){
									//Manda de volta ate o limite do canto

									initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
									finalPoint = new Point3d(initialPoint.getX(), initialPoint.getY() - avancoLateral + (ws.getFerramenta().getDiametroFerramenta()/2), zAtual);

									if(!(initialPoint.getX() == finalPoint.getX() &&
											initialPoint.getY() == finalPoint.getY() && initialPoint.getZ() == finalPoint.getZ())){
										LinearPath linearLimite = new LinearPath(initialPoint, finalPoint);
										trajetoriasCantos.add(linearLimite);
									}

									avancoLateralConcluido = true;
								}

								else {
									// incrementar Ae e avancar Lateralmente

									if(( avancoLateral + aeUtilizado > (raioAnterior - (raioAnterior/(Math.sqrt(2)))))){
										aeUtilizado = (raioAnterior - (raioAnterior/(Math.sqrt(2)))) - avancoLateral;

									}

									avancoLateral += aeUtilizado;

									//LinearPath de avanco
									initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
									finalPoint = new Point3d(initialPoint.getX(), initialPoint.getY() + aeUtilizado, zAtual);

									LinearPath LPAvancoLateral = new LinearPath(initialPoint, finalPoint);
									trajetoriasCantos.add(LPAvancoLateral);


								}


							}

							else{

								//LinearPath descendo

								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
								finalPoint = new Point3d(initialPoint.getX(), initialPoint.getY() - raioAnterior + avancoLateral, zAtual);
								LinearPath LinearPathCantoA = new LinearPath(initialPoint, finalPoint);
								trajetoriasCantos.add(LinearPathCantoA);

								//LinearPath ate B
								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
								finalPoint = new Point3d(cavidade.X + raioAnterior + ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide(), initialPoint.getY(), zAtual ); 

								LinearPath LinearPath2BwithoutCP = new LinearPath(initialPoint, finalPoint);
								trajetoriasCantos.add(LinearPath2BwithoutCP);

								//Verifica se o avanco lateral ja foi concluido para avancar em profundidade

								if(!(avancoLateral + (ws.getFerramenta().getDiametroFerramenta()/2) <= ((raioAnterior - (raioAnterior/ Math.sqrt(2)))  ))){
									//Manda de volta ate o limite do canto (volta para cima)

									initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
									finalPoint = new Point3d(initialPoint.getX(), initialPoint.getY() - avancoLateral + (ws.getFerramenta().getDiametroFerramenta()/2), zAtual);
									if(!(initialPoint.getX() == finalPoint.getX() &&
											initialPoint.getY() == finalPoint.getY() && initialPoint.getZ() == finalPoint.getZ())){

										LinearPath linearLimite = new LinearPath(initialPoint, finalPoint);
										trajetoriasCantos.add(linearLimite);
									}

									avancoLateralConcluido = true;
								}

								else {
									// incrementar Ae e avancar Lateralmente

									if(( avancoLateral + aeUtilizado > (raioAnterior - (raioAnterior/(Math.sqrt(2)))))){
										aeUtilizado = (raioAnterior - (raioAnterior/(Math.sqrt(2)))) - avancoLateral;

									}


									avancoLateral += aeUtilizado;

									//LinearPath de avanco
									initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
									finalPoint = new Point3d(initialPoint.getX(), initialPoint.getY() + aeUtilizado, zAtual);

									LinearPath LPAvancoLateral = new LinearPath(initialPoint, finalPoint);
									trajetoriasCantos.add(LPAvancoLateral);


								}

							}

							ladoA_IE = false; // Seta como falso para passar que o path seguinte seja o Lado B
						}

						else{



							if (avancoLateral + (f.getDiametroFerramenta() / 2) < (raioCavidadeSemAllowance - (f.getDiametroFerramenta() / 2) - avancoLateral)){

								//LinearPath pre Curva


								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), finalPoint.getZ());
								finalPoint = new Point3d(initialPoint.getX() - raioAnterior + raioCavidadeSemAllowance + avancoLateral - (f.getDiametroFerramenta() / 2), initialPoint.getY(), zAtual);

								LinearPath linearPathB = new LinearPath(initialPoint, finalPoint);
								trajetoriasCantos.add(linearPathB);

								//CircularPath
								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual); 
								finalPoint = new Point3d(initialPoint.getX() - raioCavidadeSemAllowance + avancoLateral,  cavidade.Y + ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide() + raioCavidadeSemAllowance + avancoLateral - (f.getDiametroFerramenta() / 2), zAtual);

								Point3d centerCanto = new Point3d(cavidade.X + ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide() + raioCavidadeSemAllowance, cavidade.Y + ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide() + raioCavidadeSemAllowance, zAtual);

								CircularPath CircularPathCantoB = new CircularPath(initialPoint, finalPoint, centerCanto);
								CircularPathCantoB.setSense(CircularPath.CW);
								trajetoriasCantos.add(CircularPathCantoB);

								//LinearPath ate A
								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
								//								finalPoint = new Point3d(cavidade.X + avancoLateral +  f.getDiametroFerramenta() / 2 + ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide(), cavidade.Y + cavidade.getLargura() - raioAnterior - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide()  , zAtual);
								finalPoint = new Point3d(initialPoint.getX(), cavidade.Y + raioAnterior + ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide()  , zAtual);
								LinearPath LinearPath2AwithCP = new LinearPath(initialPoint, finalPoint);
								trajetoriasCantos.add(LinearPath2AwithCP);

								//Verifica se o avanco lateral ja foi concluido para avancar em profundidade

								if(!(avancoLateral + (ws.getFerramenta().getDiametroFerramenta()/2) <= ((raioAnterior - (raioAnterior/ Math.sqrt(2)))  ))){
									//Manda de volta ate o limite do canto (volta pra esquerda)

									initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
									finalPoint = new Point3d(initialPoint.getX() - avancoLateral + (ws.getFerramenta().getDiametroFerramenta()/2), initialPoint.getY(), zAtual);

									if(!(initialPoint.getX() == finalPoint.getX() &&
											initialPoint.getY() == finalPoint.getY() && initialPoint.getZ() == finalPoint.getZ())){
										LinearPath linearLimite = new LinearPath(initialPoint, finalPoint);
										trajetoriasCantos.add(linearLimite);
									}

									avancoLateralConcluido = true;
								}

								else{
									// incrementar Ae e avancar Lateralmente

									if(( avancoLateral + aeUtilizado > (raioAnterior - (raioAnterior/(Math.sqrt(2)))))){
										aeUtilizado = (raioAnterior - (raioAnterior/(Math.sqrt(2)))) - avancoLateral;

									}
									avancoLateral += aeUtilizado;

									//LinearPath de avanco
									initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
									finalPoint = new Point3d(initialPoint.getX() + aeUtilizado, initialPoint.getY(), zAtual);

									LinearPath LPAvancoLateral = new LinearPath(initialPoint, finalPoint);
									trajetoriasCantos.add(LPAvancoLateral);

								}


							}


							else{
								//LinearPath


								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), finalPoint.getZ());
								finalPoint = new Point3d(initialPoint.getX() - raioAnterior + avancoLateral , initialPoint.getY(), zAtual);

								LinearPath linearPathB = new LinearPath(initialPoint, finalPoint);
								trajetoriasCantos.add(linearPathB);

								//LinearPath ate A
								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);

								finalPoint = new Point3d(initialPoint.getX(), cavidade.Y + raioAnterior + ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide(), zAtual);
								//finalPoint = new Point3d(cavidade.X + avancoLateral +  f.getDiametroFerramenta() / 2 + ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide(), cavidade.Y + cavidade.getLargura() - raioAnterior - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide()  , zAtual);
								LinearPath LinearPath2AwithoutCP = new LinearPath(initialPoint, finalPoint);
								trajetoriasCantos.add(LinearPath2AwithoutCP);

								//								System.out.println("Novo Inicial" + initialPoint);
								//								System.out.println(" NovoFinal" + finalPoint);

								if(!(avancoLateral + (ws.getFerramenta().getDiametroFerramenta()/2) <= ((raioAnterior - (raioAnterior/ Math.sqrt(2)))  ))){
									//Manda de volta ate o limite do canto (volta pra esquerda)

									initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
									finalPoint = new Point3d(initialPoint.getX() - avancoLateral + (ws.getFerramenta().getDiametroFerramenta()/2), initialPoint.getY(), zAtual);

									if(!(initialPoint.getX() == finalPoint.getX() &&
											initialPoint.getY() == finalPoint.getY() && initialPoint.getZ() == finalPoint.getZ())){

										LinearPath linearLimite = new LinearPath(initialPoint, finalPoint);
										trajetoriasCantos.add(linearLimite);
									}

									avancoLateralConcluido = true;
								}

								else{
									// incrementar Ae e avancar Lateralmente

									if(( avancoLateral + aeUtilizado > (raioAnterior - (raioAnterior/(Math.sqrt(2)))))){
										aeUtilizado = (raioAnterior - (raioAnterior/(Math.sqrt(2)))) - avancoLateral;

									}
									avancoLateral += aeUtilizado;

									//LinearPath de avanco
									initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
									finalPoint = new Point3d(initialPoint.getX() + aeUtilizado, initialPoint.getY(), zAtual);

									LinearPath LPAvancoLateral = new LinearPath(initialPoint, finalPoint);
									trajetoriasCantos.add(LPAvancoLateral);

								}



							}

							ladoA_IE = true;// Seta como verdadeira para que o path seguinte seja o Lado A
						}
					}

					else {

						terminouXY_IE = true;

					}

				}





			}	

			//	LinearPath Saida

			initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
			finalPoint = new Point3d(initialPoint.getX(), initialPoint.getY(), ws.getOperation().getRetractPlane());

			pathSaida = new LinearPath(initialPoint, finalPoint);
			pathSaida.setTipoDeMovimento(LinearPath.FAST_MOV);
			trajetoriasCantos.add(pathSaida);



			zAtual = ws.getOperation().getRetractPlane();

			//Aqui eh feito o desbaste do canto superior direito


			System.out.println("Iniciando canto SD");
			initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
			finalPoint = new Point3d(cavidade.X + cavidade.getComprimento() - (f.getDiametroFerramenta()/2) - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide(), cavidade.Y + cavidade.getLargura() - raioAnterior - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide()  , zAtual);
			toPontoInicial = new LinearPath(initialPoint, finalPoint);
			toPontoInicial.setTipoDeMovimento(LinearPath.FAST_MOV);
			trajetoriasCantos.add(toPontoInicial);
			initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY() , zAtual);
			finalPoint = new Point3d(initialPoint.getX(), initialPoint.getY(), -cavidade.Z);
			aproximacao = new LinearPath(initialPoint, finalPoint);
			trajetoriasCantos.add(aproximacao);
			zAtual = aproximacao.getFinalPoint().getZ();
			boolean ladoA_SD = true;

			while (!terminouZ_SD)
			{

				//				if (zAtual - apUtilizada >= - cavidade.getProfundidade())
				if (Math.abs(zAtual - apUtilizada) <= Math.abs(-cavidade.Z -cavidade.getProfundidade()))
				{
					apUtilizada = ws.getCondicoesUsinagem().getAp();
				} else
				{
					apUtilizada = (cavidade.getProfundidade() + cavidade.Z) + zAtual;
				}
				zAtual -= apUtilizada;


				initialPoint = new Point3d(finalPoint.x, finalPoint.y, finalPoint.z);
				finalPoint = new Point3d(initialPoint.x, initialPoint.y, zAtual);
				LinearPath mergulho = new LinearPath(initialPoint, finalPoint);
				trajetoriasCantos.add(mergulho);


				//				if(zAtual == -cavidade.getProfundidade() - cavidade.getPosicaoZ()) //atencao!, ajustar para caso absoluto (posZ)
				if(zAtual == (-cavidade.Z -cavidade.getProfundidade()))
				{
					terminouZ_SD = true;
				}

				avancoLateral =  (f.getDiametroFerramenta()/2);

				boolean avancoLateralConcluido = false;

				terminouXY_SD = false;
				while (!terminouXY_SD)
				{


					if(!avancoLateralConcluido){

						if(ladoA_SD){

							//Verificar se e necessario fazer a curva
							if (avancoLateral + (f.getDiametroFerramenta() / 2) < (raioCavidadeSemAllowance - (f.getDiametroFerramenta() / 2) - avancoLateral)){

								//LinearPath pre-Curva
								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
								finalPoint = new Point3d(initialPoint.getX(), initialPoint.getY() + (raioAnterior - raioCavidadeSemAllowance) - avancoLateral + (f.getDiametroFerramenta() / 2), zAtual);
								LinearPath LinearPathCantoA = new LinearPath(initialPoint, finalPoint);
								trajetoriasCantos.add(LinearPathCantoA);

								//CircularPath em direcao a B
								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual); 
								finalPoint = new Point3d(initialPoint.getX() - raioCavidadeSemAllowance + (ws.getFerramenta().getDiametroFerramenta()/2), cavidade.Y - avancoLateral + cavidade.getLargura() - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide(),zAtual);
								Point3d centerCanto = new Point3d(cavidade.X + cavidade.getComprimento() - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide() - raioCavidadeSemAllowance, cavidade.Y + cavidade.getLargura() - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide() - raioCavidadeSemAllowance, zAtual);


								CircularPath CircularPathCantoA = new CircularPath(initialPoint, finalPoint, centerCanto);
								CircularPathCantoA.setSense(CircularPath.CCW);
								trajetoriasCantos.add(CircularPathCantoA);

								//LinearPath ate B
								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
								finalPoint = new Point3d(cavidade.X + cavidade.getComprimento() - raioAnterior - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide(), initialPoint.getY(), zAtual );

								LinearPath LinearPath2BwithCP = new LinearPath(initialPoint, finalPoint);
								trajetoriasCantos.add(LinearPath2BwithCP);

								//Verifica se o avanco lateral ja foi concluido para avancar em profundidade

								//							if(!(avancoLateral < ((raioAnterior - (raioAnterior/ Math.sqrt(2))) + (ws.getFerramenta().getDiametroFerramenta() * 0.25) ))){
								if(!(avancoLateral + (ws.getFerramenta().getDiametroFerramenta()/2) <= ((raioAnterior - (raioAnterior/ Math.sqrt(2))) ))){	
									//Manda de volta ate o limite do canto

									initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
									finalPoint = new Point3d(initialPoint.getX(), initialPoint.getY() + avancoLateral - (ws.getFerramenta().getDiametroFerramenta()/2), zAtual);

									if(!(initialPoint.getX() == finalPoint.getX() &&
											initialPoint.getY() == finalPoint.getY() && initialPoint.getZ() == finalPoint.getZ())){
										LinearPath linearLimite = new LinearPath(initialPoint, finalPoint);
										trajetoriasCantos.add(linearLimite);
									}

									avancoLateralConcluido = true;
								}

								else {
									// incrementar Ae e avancar Lateralmente

									if(( avancoLateral + aeUtilizado > (raioAnterior - (raioAnterior/(Math.sqrt(2)))))){
										aeUtilizado = (raioAnterior - (raioAnterior/(Math.sqrt(2)))) - avancoLateral;

									}

									avancoLateral += aeUtilizado;

									//LinearPath de avanco
									initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
									finalPoint = new Point3d(initialPoint.getX(), initialPoint.getY() - aeUtilizado, zAtual);

									LinearPath LPAvancoLateral = new LinearPath(initialPoint, finalPoint);
									trajetoriasCantos.add(LPAvancoLateral);


								}


							}

							else{

								//LinearPath subindo
								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
								//								finalPoint = new Point3d(initialPoint.getX(), initialPoint.getY() + raioAnterior - (ws.getFerramenta().getDiametroFerramenta()/2) - avancoLateral, zAtual);
								finalPoint = new Point3d(initialPoint.getX(), initialPoint.getY() + raioAnterior - avancoLateral, zAtual);
								LinearPath LinearPathCantoA = new LinearPath(initialPoint, finalPoint);
								trajetoriasCantos.add(LinearPathCantoA);

								//LinearPath ate B
								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
								finalPoint = new Point3d(cavidade.X + cavidade.getComprimento() - raioAnterior - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide(), initialPoint.getY(), zAtual ); 

								LinearPath LinearPath2BwithoutCP = new LinearPath(initialPoint, finalPoint);
								trajetoriasCantos.add(LinearPath2BwithoutCP);

								//Verifica se o avanco lateral ja foi concluido para avancar em profundidade

								//							if(!(avancoLateral < ((raioAnterior - (raioAnterior/ Math.sqrt(2))) + (ws.getFerramenta().getDiametroFerramenta() * 0.25) ))){
								if(!(avancoLateral + (ws.getFerramenta().getDiametroFerramenta()/2) <= ((raioAnterior - (raioAnterior/ Math.sqrt(2))) ))){	
									//Manda de volta ate o limite do canto (volta para cima)

									initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
									finalPoint = new Point3d(initialPoint.getX(), initialPoint.getY() + avancoLateral -(ws.getFerramenta().getDiametroFerramenta()/2), zAtual);
									//aqui emcima estava finaPoint..ao em vez de initialPoint verificar outros
									if(!(initialPoint.getX() == finalPoint.getX() &&
											initialPoint.getY() == finalPoint.getY() && initialPoint.getZ() == finalPoint.getZ())){

										LinearPath linearLimite = new LinearPath(initialPoint, finalPoint);
										trajetoriasCantos.add(linearLimite);
									}

									avancoLateralConcluido = true;
								}

								else {
									// incrementar Ae e avancar Lateralmente

									if(( avancoLateral + aeUtilizado > (raioAnterior - (raioAnterior/(Math.sqrt(2)))))){
										aeUtilizado = (raioAnterior - (raioAnterior/(Math.sqrt(2)))) - avancoLateral;

									}

									avancoLateral += aeUtilizado;

									//LinearPath de avanco
									initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
									finalPoint = new Point3d(initialPoint.getX(), initialPoint.getY() - aeUtilizado, zAtual);

									LinearPath LPAvancoLateral = new LinearPath(initialPoint, finalPoint);
									trajetoriasCantos.add(LPAvancoLateral);


								}

							}

							ladoA_SD = false; // Seta como falso para passar que o path seguinte seja o Lado B

						}

						else{

							//Verificar se e necessario fazer a curva

							if (avancoLateral + (f.getDiametroFerramenta() / 2) < (raioCavidadeSemAllowance - (f.getDiametroFerramenta() / 2) - avancoLateral)){

								//LinearPath pre Curva


								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), finalPoint.getZ());
								finalPoint = new Point3d(initialPoint.getX() + raioAnterior - raioCavidadeSemAllowance - avancoLateral + (f.getDiametroFerramenta() / 2) , initialPoint.getY(), zAtual);

								LinearPath linearPathB = new LinearPath(initialPoint, finalPoint);
								trajetoriasCantos.add(linearPathB);

								//CircularPath
								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual); 
								finalPoint = new Point3d(initialPoint.getX() + raioCavidadeSemAllowance - avancoLateral,  cavidade.Y + cavidade.getLargura() - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide() - raioCavidadeSemAllowance - avancoLateral, zAtual);

								Point3d centerCanto = new Point3d(cavidade.X + cavidade.getComprimento() - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide() - raioCavidadeSemAllowance, cavidade.Y + cavidade.getLargura() - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide() - raioCavidadeSemAllowance, zAtual);

								CircularPath CircularPathCantoB = new CircularPath(initialPoint, finalPoint, centerCanto);
								CircularPathCantoB.setSense(CircularPath.CW);
								trajetoriasCantos.add(CircularPathCantoB);

								//LinearPath ate A
								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
								//							finalPoint = new Point3d(cavidade.X + avancoLateral +  f.getDiametroFerramenta() / 2 + ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide(), cavidade.Y + cavidade.getLargura() - raioAnterior - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide()  , zAtual);
								finalPoint = new Point3d(initialPoint.getX(), cavidade.Y + cavidade.getLargura() - raioAnterior - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide()  , zAtual);
								LinearPath LinearPath2AwithCP = new LinearPath(initialPoint, finalPoint);
								trajetoriasCantos.add(LinearPath2AwithCP);

								//Verifica se o avanco lateral ja foi concluido para avancar em profundidade


								if(!(avancoLateral + (ws.getFerramenta().getDiametroFerramenta()/2) <= ((raioAnterior - (raioAnterior/ Math.sqrt(2)))  ))){
									//Manda de volta ate o limite do canto (volta pra esquerda)

									initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
									finalPoint = new Point3d(initialPoint.getX() + avancoLateral - (ws.getFerramenta().getDiametroFerramenta()/2), initialPoint.getY(), zAtual);

									if(!(initialPoint.getX() == finalPoint.getX() &&
											initialPoint.getY() == finalPoint.getY() && initialPoint.getZ() == finalPoint.getZ())){
										LinearPath linearLimite = new LinearPath(initialPoint, finalPoint);
										trajetoriasCantos.add(linearLimite);
									}

									avancoLateralConcluido = true;
								}

								else{
									// incrementar Ae e avancar Lateralmente

									if(( avancoLateral + aeUtilizado > (raioAnterior - (raioAnterior/(Math.sqrt(2)))))){
										aeUtilizado = (raioAnterior - (raioAnterior/(Math.sqrt(2)))) - avancoLateral;

									}

									avancoLateral += aeUtilizado;

									//LinearPath de avanco
									initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
									finalPoint = new Point3d(initialPoint.getX() - aeUtilizado, initialPoint.getY(), zAtual);

									LinearPath LPAvancoLateral = new LinearPath(initialPoint, finalPoint);
									trajetoriasCantos.add(LPAvancoLateral);

								}


							}


							else{
								//LinearPath


								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), finalPoint.getZ());
								finalPoint = new Point3d(initialPoint.getX() + raioAnterior - avancoLateral , initialPoint.getY(), zAtual);

								LinearPath linearPathB = new LinearPath(initialPoint, finalPoint);
								trajetoriasCantos.add(linearPathB);

								//LinearPath ate A
								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);

								finalPoint = new Point3d(initialPoint.getX(), cavidade.Y + cavidade.getLargura() - raioAnterior - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide(), zAtual);
								//finalPoint = new Point3d(cavidade.X + avancoLateral +  f.getDiametroFerramenta() / 2 + ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide(), cavidade.Y + cavidade.getLargura() - raioAnterior - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide()  , zAtual);
								LinearPath LinearPath2AwithoutCP = new LinearPath(initialPoint, finalPoint);
								trajetoriasCantos.add(LinearPath2AwithoutCP);


								if(!(avancoLateral + (ws.getFerramenta().getDiametroFerramenta()/2) <= ((raioAnterior - (raioAnterior/ Math.sqrt(2))) ))){
									//Manda de volta ate o limite do canto (volta pra esquerda)

									initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
									finalPoint = new Point3d(initialPoint.getX() + avancoLateral - (ws.getFerramenta().getDiametroFerramenta()/2), initialPoint.getY(), zAtual);

									if(!(initialPoint.getX() == finalPoint.getX() &&
											initialPoint.getY() == finalPoint.getY() && initialPoint.getZ() == finalPoint.getZ())){

										LinearPath linearLimite = new LinearPath(initialPoint, finalPoint);
										trajetoriasCantos.add(linearLimite);
									}

									avancoLateralConcluido = true;
								}

								else{
									// incrementar Ae e avancar Lateralmente

									if(( avancoLateral + aeUtilizado > (raioAnterior - (raioAnterior/(Math.sqrt(2)))))){

										aeUtilizado = (raioAnterior - (raioAnterior/(Math.sqrt(2)))) - avancoLateral;

									}

									avancoLateral += aeUtilizado;

									//LinearPath de avanco
									initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
									finalPoint = new Point3d(initialPoint.getX() - aeUtilizado, initialPoint.getY(), zAtual);

									LinearPath LPAvancoLateral = new LinearPath(initialPoint, finalPoint);
									trajetoriasCantos.add(LPAvancoLateral);

								}



							}

							ladoA_SD = true;// Seta como verdadeira para que o path seguinte seja o Lado A


						}


					}

					else {

						terminouXY_SD = true;

					}

				}

			}

			//	LinearPath Saida

			initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
			finalPoint = new Point3d(initialPoint.getX(), initialPoint.getY(), ws.getOperation().getRetractPlane());

			pathSaida = new LinearPath(initialPoint, finalPoint);
			pathSaida.setTipoDeMovimento(LinearPath.FAST_MOV);
			trajetoriasCantos.add(pathSaida);

			zAtual = ws.getOperation().getRetractPlane();


			initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
			finalPoint = new Point3d(cavidade.X + cavidade.getComprimento() - (f.getDiametroFerramenta()/2) - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide(), cavidade.Y + raioAnterior + ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide()  , zAtual);
			toPontoInicial = new LinearPath(initialPoint, finalPoint);
			toPontoInicial.setTipoDeMovimento(LinearPath.FAST_MOV);
			trajetoriasCantos.add(toPontoInicial);
			initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY() , zAtual);
			finalPoint = new Point3d(initialPoint.getX(), initialPoint.getY(), -cavidade.Z);
			aproximacao = new LinearPath(initialPoint, finalPoint);
			trajetoriasCantos.add(aproximacao);
			boolean ladoA_ID = true;

			zAtual = aproximacao.getFinalPoint().getZ();
			System.out.println("Iniciando canto ID");
			//Aqui eh feito o desbaste do canto inferior direito
			while (!terminouZ_ID)
			{


				//				if (zAtual - apUtilizada >= - cavidade.getProfundidade())
				if (Math.abs(zAtual - apUtilizada) <= Math.abs(-cavidade.Z -cavidade.getProfundidade()))
				{
					apUtilizada = ws.getCondicoesUsinagem().getAp();
				} else
				{
					apUtilizada = (cavidade.getProfundidade() + cavidade.Z) + zAtual;
				}
				zAtual -= apUtilizada;


				initialPoint = new Point3d(finalPoint.x, finalPoint.y, finalPoint.z);
				finalPoint = new Point3d(initialPoint.x, initialPoint.y, zAtual);
				LinearPath mergulho = new LinearPath(initialPoint, finalPoint);
				trajetoriasCantos.add(mergulho);



				//				if(zAtual == -cavidade.getProfundidade() - cavidade.getPosicaoZ()) //atencao!, ajustar para caso absoluto (posZ)
				if(zAtual == (-cavidade.Z -cavidade.getProfundidade()))
				{
					terminouZ_ID = true;
				}

				avancoLateral = (f.getDiametroFerramenta() / 2);

				boolean avancoLateralConcluido = false;

				terminouXY_ID = false;
				while (!terminouXY_ID)
				{


					if(!avancoLateralConcluido){

						if(ladoA_ID){



							//Verificar se e necessario fazer a curva
							if (avancoLateral + (f.getDiametroFerramenta() / 2) < (raioCavidadeSemAllowance - (f.getDiametroFerramenta() / 2) - avancoLateral)){

								//LinearPath pre-Curva
								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
								finalPoint = new Point3d(initialPoint.getX(), initialPoint.getY() - raioAnterior + raioCavidadeSemAllowance + avancoLateral - (f.getDiametroFerramenta() / 2), zAtual);
								LinearPath LinearPathCantoA = new LinearPath(initialPoint, finalPoint);
								trajetoriasCantos.add(LinearPathCantoA);

								//CircularPath em direcao a B
								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual); 
								finalPoint = new Point3d(initialPoint.getX() - raioCavidadeSemAllowance + (ws.getFerramenta().getDiametroFerramenta()/2), cavidade.Y + avancoLateral + ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide(),zAtual);
								Point3d centerCanto = new Point3d(cavidade.X + cavidade.getComprimento() - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide() - raioCavidadeSemAllowance, cavidade.Y + ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide() + raioCavidadeSemAllowance, zAtual);


								CircularPath CircularPathCantoA = new CircularPath(initialPoint, finalPoint, centerCanto);
								CircularPathCantoA.setSense(CircularPath.CW);
								trajetoriasCantos.add(CircularPathCantoA);

								//LinearPath ate B
								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
								finalPoint = new Point3d(cavidade.X + cavidade.getComprimento() - raioAnterior - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide(), initialPoint.getY(), zAtual );

								LinearPath LinearPath2BwithCP = new LinearPath(initialPoint, finalPoint);
								trajetoriasCantos.add(LinearPath2BwithCP);

								//Verifica se o avanco lateral ja foi concluido para avancar em profundidade

								//							if(!(avancoLateral < ((raioAnterior - (raioAnterior/ Math.sqrt(2))) + (ws.getFerramenta().getDiametroFerramenta() * 0.25) ))){
								if(!(avancoLateral + (ws.getFerramenta().getDiametroFerramenta()/2) <= ((raioAnterior - (raioAnterior/ Math.sqrt(2))) ))){	
									//Manda de volta ate o limite do canto

									initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
									finalPoint = new Point3d(initialPoint.getX(), initialPoint.getY() - avancoLateral + (ws.getFerramenta().getDiametroFerramenta()/2), zAtual);

									if(!(initialPoint.getX() == finalPoint.getX() &&
											initialPoint.getY() == finalPoint.getY() && initialPoint.getZ() == finalPoint.getZ())){
										LinearPath linearLimite = new LinearPath(initialPoint, finalPoint);
										trajetoriasCantos.add(linearLimite);
									}

									avancoLateralConcluido = true;
								}

								else {
									// incrementar Ae e avancar Lateralmente

									if(( avancoLateral + aeUtilizado > (raioAnterior - (raioAnterior/(Math.sqrt(2)))))){
										aeUtilizado = (raioAnterior - (raioAnterior/(Math.sqrt(2)))) - avancoLateral;

									}

									avancoLateral += aeUtilizado;

									//LinearPath de avanco
									initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
									finalPoint = new Point3d(initialPoint.getX(), initialPoint.getY() + aeUtilizado, zAtual);

									LinearPath LPAvancoLateral = new LinearPath(initialPoint, finalPoint);
									trajetoriasCantos.add(LPAvancoLateral);


								}


							}

							else{

								//LinearPath descendo
								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
								finalPoint = new Point3d(initialPoint.getX(), initialPoint.getY() - raioAnterior + avancoLateral, zAtual);
								LinearPath LinearPathCantoA = new LinearPath(initialPoint, finalPoint);
								trajetoriasCantos.add(LinearPathCantoA);

								//LinearPath ate B
								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
								finalPoint = new Point3d(cavidade.X + cavidade.getComprimento() - raioAnterior - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide(), initialPoint.getY(), zAtual ); 

								LinearPath LinearPath2BwithoutCP = new LinearPath(initialPoint, finalPoint);
								trajetoriasCantos.add(LinearPath2BwithoutCP);

								//Verifica se o avanco lateral ja foi concluido para avancar em profundidade

								//							if(!(avancoLateral < ((raioAnterior - (raioAnterior/ Math.sqrt(2))) + (ws.getFerramenta().getDiametroFerramenta() * 0.25) ))){
								if(!(avancoLateral + (ws.getFerramenta().getDiametroFerramenta()/2) <= ((raioAnterior - (raioAnterior/ Math.sqrt(2))) ))){	
									//Manda de volta ate o limite do canto (volta para cima)

									initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
									finalPoint = new Point3d(initialPoint.getX(), initialPoint.getY() - avancoLateral +(ws.getFerramenta().getDiametroFerramenta()/2), zAtual);
									//aqui emcima estava finaPoint..ao em vez de initialPoint verificar outros
									if(!(initialPoint.getX() == finalPoint.getX() &&
											initialPoint.getY() == finalPoint.getY() && initialPoint.getZ() == finalPoint.getZ())){

										LinearPath linearLimite = new LinearPath(initialPoint, finalPoint);
										trajetoriasCantos.add(linearLimite);
									}

									avancoLateralConcluido = true;
								}

								else {
									// incrementar Ae e avancar Lateralmente

									if(( avancoLateral + aeUtilizado > (raioAnterior - (raioAnterior/(Math.sqrt(2)))))){
										aeUtilizado = (raioAnterior - (raioAnterior/(Math.sqrt(2)))) - avancoLateral;

									}

									avancoLateral += aeUtilizado;

									//LinearPath de avanco
									initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
									finalPoint = new Point3d(initialPoint.getX(), initialPoint.getY() + aeUtilizado, zAtual);

									LinearPath LPAvancoLateral = new LinearPath(initialPoint, finalPoint);
									trajetoriasCantos.add(LPAvancoLateral);


								}

							}

							ladoA_ID = false; // Seta como falso para passar que o path seguinte seja o Lado B


						}

						else{



							//Verificar se e necessario fazer a curva

							if (avancoLateral + (f.getDiametroFerramenta() / 2) < (raioCavidadeSemAllowance - (f.getDiametroFerramenta() / 2) - avancoLateral)){

								//LinearPath pre Curva


								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), finalPoint.getZ());
								finalPoint = new Point3d(initialPoint.getX() + raioAnterior - raioCavidadeSemAllowance - avancoLateral + (f.getDiametroFerramenta() / 2) , initialPoint.getY(), zAtual);

								LinearPath linearPathB = new LinearPath(initialPoint, finalPoint);
								trajetoriasCantos.add(linearPathB);

								//CircularPath
								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual); 
								finalPoint = new Point3d(initialPoint.getX() + raioCavidadeSemAllowance - avancoLateral,  cavidade.Y + ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide() + raioCavidadeSemAllowance + avancoLateral, zAtual);

								Point3d centerCanto = new Point3d(cavidade.X + cavidade.getComprimento() - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide() - raioCavidadeSemAllowance, cavidade.Y + ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide() + raioCavidadeSemAllowance, zAtual);

								CircularPath CircularPathCantoB = new CircularPath(initialPoint, finalPoint, centerCanto);
								CircularPathCantoB.setSense(CircularPath.CCW);
								trajetoriasCantos.add(CircularPathCantoB);

								//LinearPath ate A
								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
								finalPoint = new Point3d(initialPoint.getX(), cavidade.Y + raioAnterior + ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide()  , zAtual);
								LinearPath LinearPath2AwithCP = new LinearPath(initialPoint, finalPoint);
								trajetoriasCantos.add(LinearPath2AwithCP);

								//Verifica se o avanco lateral ja foi concluido para avancar em profundidade


								if(!(avancoLateral + (ws.getFerramenta().getDiametroFerramenta()/2) <= ((raioAnterior - (raioAnterior/ Math.sqrt(2)))  ))){
									//Manda de volta ate o limite do canto (volta pra esquerda)

									initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
									finalPoint = new Point3d(initialPoint.getX() + avancoLateral - (ws.getFerramenta().getDiametroFerramenta()/2), initialPoint.getY(), zAtual);

									if(!(initialPoint.getX() == finalPoint.getX() &&
											initialPoint.getY() == finalPoint.getY() && initialPoint.getZ() == finalPoint.getZ())){
										LinearPath linearLimite = new LinearPath(initialPoint, finalPoint);
										trajetoriasCantos.add(linearLimite);
									}

									avancoLateralConcluido = true;
								}

								else{
									// incrementar Ae e avancar Lateralmente

									if(( avancoLateral + aeUtilizado > (raioAnterior - (raioAnterior/(Math.sqrt(2)))))){
										aeUtilizado = (raioAnterior - (raioAnterior/(Math.sqrt(2)))) - avancoLateral;

									}

									avancoLateral += aeUtilizado;

									//LinearPath de avanco
									initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
									finalPoint = new Point3d(initialPoint.getX() - aeUtilizado, initialPoint.getY(), zAtual);

									LinearPath LPAvancoLateral = new LinearPath(initialPoint, finalPoint);
									trajetoriasCantos.add(LPAvancoLateral);

								}


							}


							else{
								//LinearPath


								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), finalPoint.getZ());
								finalPoint = new Point3d(initialPoint.getX() + raioAnterior - avancoLateral , initialPoint.getY(), zAtual);

								LinearPath linearPathB = new LinearPath(initialPoint, finalPoint);
								trajetoriasCantos.add(linearPathB);

								//LinearPath ate A
								initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);

								finalPoint = new Point3d(initialPoint.getX(), cavidade.Y +  raioAnterior + ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide(), zAtual);
								//finalPoint = new Point3d(cavidade.X + avancoLateral +  f.getDiametroFerramenta() / 2 + ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide(), cavidade.Y + cavidade.getLargura() - raioAnterior - ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide()  , zAtual);
								LinearPath LinearPath2AwithoutCP = new LinearPath(initialPoint, finalPoint);
								trajetoriasCantos.add(LinearPath2AwithoutCP);


								if(!(avancoLateral + (ws.getFerramenta().getDiametroFerramenta()/2) <= ((raioAnterior - (raioAnterior/ Math.sqrt(2))) ))){
									//Manda de volta ate o limite do canto (volta pra esquerda)

									initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
									finalPoint = new Point3d(initialPoint.getX() + avancoLateral - (ws.getFerramenta().getDiametroFerramenta()/2), initialPoint.getY(), zAtual);

									if(!(initialPoint.getX() == finalPoint.getX() &&
											initialPoint.getY() == finalPoint.getY() && initialPoint.getZ() == finalPoint.getZ())){

										LinearPath linearLimite = new LinearPath(initialPoint, finalPoint);
										trajetoriasCantos.add(linearLimite);
									}

									avancoLateralConcluido = true;
								}

								else{
									// incrementar Ae e avancar Lateralmente

									if(( avancoLateral + aeUtilizado > (raioAnterior - (raioAnterior/(Math.sqrt(2)))))){

										aeUtilizado = (raioAnterior - (raioAnterior/(Math.sqrt(2)))) - avancoLateral;

									}

									avancoLateral += aeUtilizado;

									//LinearPath de avanco
									initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
									finalPoint = new Point3d(initialPoint.getX() - aeUtilizado, initialPoint.getY(), zAtual);

									LinearPath LPAvancoLateral = new LinearPath(initialPoint, finalPoint);
									trajetoriasCantos.add(LPAvancoLateral);

								}



							}

							ladoA_ID = true;// Seta como verdadeira para que o path seguinte seja o Lado A



						}
					}

					else {

						terminouXY_ID = true;

					}
				}


			}

			//	LinearPath Saida

			initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
			finalPoint = new Point3d(initialPoint.getX(), initialPoint.getY(), ws.getOperation().getRetractPlane());

			pathSaida = new LinearPath(initialPoint, finalPoint);
			pathSaida.setTipoDeMovimento(LinearPath.FAST_MOV);
			trajetoriasCantos.add(pathSaida);

			zAtual = ws.getOperation().getRetractPlane();
		}

		System.out.println("Saindo.. 4 cantos feitos");
		
		System.out.println("GGGGGGGG RAIO FINAL : "+ cavidade.getRaio());
		
		return trajetoriasCantos;
	}

	public ArrayList<ArrayList<Path>> getTrajetoriasAcabamentoRPQU(Workingstep ws){

		ArrayList<ArrayList<Path>> trajetoriasAcabamentoRPQU = new ArrayList<ArrayList<Path>>();

		ArrayList<Path> trajetoriasAcabamentoRPQUHorizontalEsqInf = new ArrayList<Path>(); //0
		//		ArrayList<Path> trajetoriasAcabamentoRPQUHorizontalEsqSup = new ArrayList<Path>(); //1
		//		ArrayList<Path> trajetoriasAcabamentoRPQUHorizontalDirInf = new ArrayList<Path>(); //2
		//		ArrayList<Path> trajetoriasAcabamentoRPQUHorizontalDirSup = new ArrayList<Path>(); //3
		//		
		//		ArrayList<Path> trajetoriasAcabamentoRPQUVerticalEsqInf = new ArrayList<Path>(); //4
		//		ArrayList<Path> trajetoriasAcabamentoRPQUVerticalEsqSup = new ArrayList<Path>(); //5
		//		ArrayList<Path> trajetoriasAcabamentoRPQUVerticalDirInf = new ArrayList<Path>(); //6
		//		ArrayList<Path> trajetoriasAcabamentoRPQUVerticalDirSup = new ArrayList<Path>(); //7

		RanhuraPerfilQuadradoU ranhuraPQU = (RanhuraPerfilQuadradoU)ws.getFeature();
		Ferramenta f = ws.getFerramenta();
		double anguloRanhuraPQU = ranhuraPQU.getAngulo();
		double zAtual = ws.getOperation().getRetractPlane();
		double sideAllowance = ((BottomAndSideFinishMilling)ws.getOperation()).getAllowanceSide();
		double distProfTangCurva = ((ranhuraPQU.getRaio() * Math.tan(Math.toRadians(anguloRanhuraPQU / 2))) * Math.sin(Math.toRadians(anguloRanhuraPQU)));

		if(ranhuraPQU.getEixo() == Ranhura.HORIZONTAL){

			double[] limites = calcularLimites(ws);

			double limiteEsquerda = ranhuraPQU.getPosicaoX();
			double limiteDireita = ws.getFace().getComprimento();
			double limiteSuperior = ranhuraPQU.getPosicaoY() + ranhuraPQU.getLargura();
			double limiteInferior = ranhuraPQU.getPosicaoY();


			double largEmDistProfTang = ranhuraPQU.getLargura() - (2 * (ranhuraPQU.getProfundidade() - ranhuraPQU.getRaio()) * Math.tan(Math.toRadians(90 - anguloRanhuraPQU)));
			double secaoPlanaBase = largEmDistProfTang - (2 * (ranhuraPQU.getRaio() + ((ranhuraPQU.getRaio() - distProfTangCurva) / Math.tan(Math.toRadians(anguloRanhuraPQU))) ) ); 
			double distAteCurvaNaBase = ((ranhuraPQU.getLargura2() - secaoPlanaBase)/2);

			double limL2Inferior = ((limiteInferior + (ranhuraPQU.getLargura()/2)) - (ranhuraPQU.getLargura2()));
			double limL2Superior = ((limiteInferior + (ranhuraPQU.getLargura()/2)) + (ranhuraPQU.getLargura2()));

			double apUtilizada = ws.getCondicoesUsinagem().getAp();
			double aeUtilizada = ws.getCondicoesUsinagem().getAe();

			Point3d initialPoint = new Point3d(0, 0, zAtual);
			Point3d finalPoint = new Point3d(limiteEsquerda, (limiteInferior + (f.getDiametroFerramenta()/2)), zAtual);

			LinearPath aoInicio = new LinearPath(initialPoint, finalPoint); //Vai ao ponto inicial para o caso 0 (Horizontal - inicio esquerda inferior)
			aoInicio.setTipoDeMovimento(LinearPath.FAST_MOV);
			trajetoriasAcabamentoRPQUHorizontalEsqInf.add(aoInicio);

			initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
			finalPoint = new Point3d(initialPoint.getX(), finalPoint.getY(), -ranhuraPQU.getPosicaoZ());
			LinearPath aproximacao = new LinearPath(initialPoint, finalPoint);
			trajetoriasAcabamentoRPQUHorizontalEsqInf.add(aproximacao);

			zAtual = -ranhuraPQU.getPosicaoZ();

			boolean terminouZ = false;

			boolean ladoA = true;

			double avancoProfAteCurva = (ranhuraPQU.getProfundidade() - distProfTangCurva); //E o que se avanca em profundidade ate chegar na curva
			double avancoLateralAteCurva = ((Math.tan(Math.toRadians((90 - anguloRanhuraPQU))))*(avancoProfAteCurva)); //Se a ferramenta avanca certa profundidade, debe avancar isso lateralmente
			//Os doubles anteriores foram criados para auxiliar no momento de escrever o codigo que segue abaixo

			double avancoLateral = 0;
			while(!terminouZ)
			{

				if(finalPoint.getX() + ws.getCondicoesUsinagem().getAe() <= (limiteDireita))
				{
					aeUtilizada = ws.getCondicoesUsinagem().getAe();
				}
				else
				{
					aeUtilizada = (limiteDireita - finalPoint.getX());
				}

				if(ladoA)
				{


					//Desce em profundidade ate onde comeca a curva 
					initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
					//					finalPoint = new Point3d(initialPoint.getX(), (initialPoint.getY()+ avancoLateralAteCurva + f.getDiametroFerramenta()/2), -avancoProfAteCurva);
					finalPoint = new Point3d(initialPoint.getX(), (initialPoint.getY()+ avancoLateralAteCurva), -avancoProfAteCurva);

					LinearPath linear = new LinearPath(initialPoint, finalPoint);

					zAtual = -avancoProfAteCurva;

					trajetoriasAcabamentoRPQUHorizontalEsqInf.add(linear);

					// Faz a curva ate a profundidade da ranhura 

					initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
					finalPoint = new Point3d(initialPoint.getX(), (limL2Inferior + distAteCurvaNaBase), -ranhuraPQU.getProfundidade());
					//finalPoint = new Point3d(initialPoint.getX(), (limiteInferior + distAteCurvaNaBase + (f.getDiametroFerramenta()/2)), -ranhuraPQU.getProfundidade());

					zAtual = -ranhuraPQU.getProfundidade();

					Point3d centroCurva = new Point3d(initialPoint.getX(),((limiteInferior + ((Math.tan(Math.toRadians(90 - anguloRanhuraPQU)))*(ranhuraPQU.getProfundidade() - ranhuraPQU.getRaio()))) + ranhuraPQU.getRaio()) ,-(ranhuraPQU.getProfundidade() - ranhuraPQU.getRaio())); 

					CircularPath circularPath = new CircularPath(initialPoint,finalPoint, centroCurva);

					trajetoriasAcabamentoRPQUHorizontalEsqInf.add(circularPath);

					//Na profundidade da ranhura, vai em trajeto linear ate o inicio da outra curva
					initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual); 
					finalPoint = new Point3d(initialPoint.getX(), (limL2Superior - distAteCurvaNaBase), zAtual);
					linear = new LinearPath(initialPoint, finalPoint);

					trajetoriasAcabamentoRPQUHorizontalEsqInf.add(linear);

					//Faz a curva ate o fim dela, para logo subir
					initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual); 
					finalPoint = new Point3d(initialPoint.getX(), (limiteSuperior - avancoLateralAteCurva - (f.getDiametroFerramenta()/2)), -avancoProfAteCurva );

					zAtual = -avancoProfAteCurva;

					centroCurva = new Point3d(initialPoint.getX(),((limiteSuperior + ((Math.tan(Math.toRadians(90 - anguloRanhuraPQU)))*(ranhuraPQU.getProfundidade() - ranhuraPQU.getRaio()))) + ranhuraPQU.getRaio()) ,-(ranhuraPQU.getProfundidade() - ranhuraPQU.getRaio()));

					circularPath = new CircularPath(initialPoint, finalPoint, centroCurva);

					trajetoriasAcabamentoRPQUHorizontalEsqInf.add(circularPath);

					//Avanco linear ate Altura da ranhura
					initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual); 
					finalPoint = new Point3d(initialPoint.getX(), (limiteSuperior - (f.getDiametroFerramenta()/2)), -ranhuraPQU.getPosicaoZ());

					zAtual = -ranhuraPQU.getPosicaoZ();

					linear = new LinearPath(initialPoint, finalPoint);

					trajetoriasAcabamentoRPQUHorizontalEsqInf.add(linear);

					ladoA = false;					
				}

				else
				{
					//Desce em profundidade ate onde comeca a curva 
					initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual); 
					finalPoint = new Point3d(initialPoint.getX(), (limiteSuperior - avancoLateralAteCurva - (f.getDiametroFerramenta()/2)), -avancoProfAteCurva );

					LinearPath linear = new LinearPath(initialPoint,finalPoint);

					trajetoriasAcabamentoRPQUHorizontalEsqInf.add(linear);

					// Faz a curva ate a profundidade da ranhura 
					initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual); 
					finalPoint = new Point3d(initialPoint.getX(),(limL2Superior - distAteCurvaNaBase), -ranhuraPQU.getProfundidade());

					zAtual = -ranhuraPQU.getProfundidade();

					Point3d centroCurva = new Point3d(initialPoint.getX(),((limiteSuperior + ((Math.tan(Math.toRadians(90 - anguloRanhuraPQU)))*(ranhuraPQU.getProfundidade() - ranhuraPQU.getRaio()))) + ranhuraPQU.getRaio()) ,-(ranhuraPQU.getProfundidade() - ranhuraPQU.getRaio()));

					CircularPath circularPath = new CircularPath(initialPoint, finalPoint, centroCurva);

					trajetoriasAcabamentoRPQUHorizontalEsqInf.add(circularPath);

					//Na profundidade da ranhura, vai em trajeto linear ate o inicio da outra curva
					initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual); 
					finalPoint = new Point3d(initialPoint.getX(), (limL2Inferior + distAteCurvaNaBase), zAtual);
					linear = new LinearPath(initialPoint, finalPoint);

					trajetoriasAcabamentoRPQUHorizontalEsqInf.add(linear);

					//Faz a curva ate o fim dela, para logo subir
					initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual); 
					finalPoint = new Point3d(initialPoint.getX(), (limiteInferior + avancoLateralAteCurva + (f.getDiametroFerramenta()/2)), -avancoProfAteCurva );

					zAtual = -avancoProfAteCurva;

					centroCurva = new Point3d(initialPoint.getX(),((limiteInferior + ((Math.tan(Math.toRadians(90 - anguloRanhuraPQU)))*(ranhuraPQU.getProfundidade() - ranhuraPQU.getRaio()))) + ranhuraPQU.getRaio()) ,-(ranhuraPQU.getProfundidade() - ranhuraPQU.getRaio())); 

					circularPath = new CircularPath(initialPoint,finalPoint, centroCurva);

					trajetoriasAcabamentoRPQUHorizontalEsqInf.add(circularPath);

					//Avanco linear ate Altura da ranhura
					initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual); 
					finalPoint = new Point3d(initialPoint.getX(), (limiteInferior + (f.getDiametroFerramenta()/2)), -ranhuraPQU.getPosicaoZ());

					zAtual = -ranhuraPQU.getPosicaoZ();

					linear = new LinearPath(initialPoint, finalPoint);

					trajetoriasAcabamentoRPQUHorizontalEsqInf.add(linear);

					ladoA = true;

				}


				if (finalPoint.getX() == limiteDireita)
				{
					terminouZ = true;
				}

				//Avanco Lateral ate chegar no limite direito
				initialPoint = new Point3d(finalPoint.getX(),finalPoint.getY(),zAtual);
				finalPoint = new Point3d((initialPoint.getX()+aeUtilizada),initialPoint.getY(),zAtual);
				LinearPath avancoAe = new LinearPath(initialPoint,finalPoint);
				trajetoriasAcabamentoRPQUHorizontalEsqInf.add(avancoAe);

			}



		}

		if(ranhuraPQU.getEixo() == Ranhura.VERTICAL){
			//			double limEsquerdo = ranhuraPQU.getPosicaoX();
			//			double limDireito = (ranhuraPQU.getPosicaoX() + ranhuraPQU.getComprimento());
			//			double limInferior = 0;
			//			double limSuperior = ws.getFace().getLargura();


			double compEmDistProfTang = ranhuraPQU.getComprimento() - (2 * (ranhuraPQU.getProfundidade() - ranhuraPQU.getRaio()) * Math.tan(Math.toRadians(90 - anguloRanhuraPQU)));
			double secaoPlanaBase = compEmDistProfTang - (2 * (ranhuraPQU.getRaio() + ((ranhuraPQU.getRaio() - distProfTangCurva) / Math.tan(Math.toRadians(anguloRanhuraPQU))))); 

		}


		trajetoriasAcabamentoRPQU.add(trajetoriasAcabamentoRPQUHorizontalEsqInf); //0
		//		trajetoriasAcabamentoRPQU.add(trajetoriasAcabamentoRPQUHorizontalEsqSup); //1
		//		trajetoriasAcabamentoRPQU.add(trajetoriasAcabamentoRPQUHorizontalDirInf); //2
		//		trajetoriasAcabamentoRPQU.add(trajetoriasAcabamentoRPQUHorizontalDirSup); //3
		//		
		//		trajetoriasAcabamentoRPQU.add(trajetoriasAcabamentoRPQUVerticalEsqInf); //4
		//		trajetoriasAcabamentoRPQU.add(trajetoriasAcabamentoRPQUVerticalEsqSup); //5
		//		trajetoriasAcabamentoRPQU.add(trajetoriasAcabamentoRPQUVerticalDirInf); //6
		//		trajetoriasAcabamentoRPQU.add(trajetoriasAcabamentoRPQUVerticalDirSup); //7


		return trajetoriasAcabamentoRPQU;


	} 

	public ArrayList<ArrayList<Path>> getTrajetoriasRanhuraPerfilQuadradoU(Workingstep ws)
	{

		//CRIAR SITUACAO PARA VERTICAL E SEPARAR AS SITUACOES EM QUE NAO EH BULL NOSE (DEIXA DEGRAU) E QUANDO E (ELIMINA DEGRAUS);

		ArrayList<ArrayList<Path>> trajetoriasRanhuraPQuadradoU = new ArrayList<ArrayList<Path>>();

		ArrayList<Path> trajetoriasRPQUHorizontalEsqInf = new ArrayList<Path>(); //0
		//		ArrayList<Path> trajetoriasRPQUHorizontalEsqSup = new ArrayList<Path>(); //1
		//		ArrayList<Path> trajetoriasRPQUHorizontalDirInf = new ArrayList<Path>(); //2
		//		ArrayList<Path> trajetoriasRPQUHorizontalDirSup = new ArrayList<Path>(); //3
		//		
		//		ArrayList<Path> trajetoriasRPQUVerticalEsqInf = new ArrayList<Path>(); //4
		//		ArrayList<Path> trajetoriasRPQUVerticalEsqSup = new ArrayList<Path>(); //5
		//		ArrayList<Path> trajetoriasRPQUVerticalDirInf = new ArrayList<Path>(); //6
		//		ArrayList<Path> trajetoriasRPQUVerticalDirSup = new ArrayList<Path>(); //7


		RanhuraPerfilQuadradoU ranhuraPQU = (RanhuraPerfilQuadradoU)ws.getFeature();
		Ferramenta f = ws.getFerramenta();
		double anguloRanhuraPQU = ranhuraPQU.getAngulo();
		double zAtual = ws.getOperation().getRetractPlane();
		double sideAllowance = ((BottomAndSideRoughMilling)ws.getOperation()).getAllowanceSide();
		double distProfTangCurva = ((ranhuraPQU.getRaio() * Math.tan(Math.toRadians(anguloRanhuraPQU / 2))) * Math.sin(Math.toRadians(anguloRanhuraPQU)));


		System.out.println("Diametro da ferramenta" + f.getDiametroFerramenta());


		System.out.println("A distancia ate a tangencia com a curva desde a base eh de " + distProfTangCurva);
		System.out.println("Inv: " +(ranhuraPQU.getProfundidade()-distProfTangCurva));
		System.out.println("Adicionar isso " + ( (ranhuraPQU.getRaio() - distProfTangCurva )  / Math.tan(Math.toRadians(anguloRanhuraPQU)) ) ); 


		if(ranhuraPQU.getEixo() == Ranhura.HORIZONTAL){
			//			double limEsquerdo = 0;
			//			double limDireito = ws.getFace().getComprimento();
			//			double limInferior = ranhuraPQU.getPosicaoY();
			//			double limSuperior = (ranhuraPQU.getPosicaoY() + ranhuraPQU.getLargura());

			System.out.println("A ranhura eh horizontal");

			double[] limites = calcularLimites(ws);
			//			double limiteEsquerda = limites[0];
			//			double limiteDireita = limites[1];
			//			double limiteSuperior = limites[2];
			//			double limiteInferior = limites[3];

			double limiteEsquerda = ranhuraPQU.getPosicaoX();
			double limiteDireita = ws.getFace().getComprimento();
			double limiteSuperior = ranhuraPQU.getPosicaoY() + ranhuraPQU.getLargura();
			double limiteInferior = ranhuraPQU.getPosicaoY();

			System.out.println("limiteEsquerda:  " + limiteEsquerda);
			System.out.println("limiteDireita:  " + limiteDireita);
			System.out.println("limiteSuperior:  " + limiteSuperior);
			System.out.println("limiteInferior:  " + limiteInferior);

			//			System.out.println("limiteEsquerda:  " + limEsquerda);
			//			System.out.println("limiteDireita:  " + limDireita);
			//			System.out.println("limiteSuperior:  " + limSuperior);
			//			System.out.println("limiteInferior:  " + limInferior);

			double largEmDistProfTang = ranhuraPQU.getLargura() - (2 * (ranhuraPQU.getProfundidade() - ranhuraPQU.getRaio()) * Math.tan(Math.toRadians(90 - anguloRanhuraPQU)));
			double secaoPlanaBase = largEmDistProfTang - (2 * (ranhuraPQU.getRaio() + ((ranhuraPQU.getRaio() - distProfTangCurva) / Math.tan(Math.toRadians(anguloRanhuraPQU))) ) ); 

			System.out.println("zAtual:  " + zAtual);

			System.out.println("L3:  " + largEmDistProfTang);
			System.out.println("Secao plana da base eh: " + secaoPlanaBase);
			Point3d initialPoint = new Point3d(0,0,zAtual);
			Point3d finalPoint = new Point3d(limiteEsquerda,limiteInferior + (f.getDiametroFerramenta()/2)+ sideAllowance,zAtual);

			LinearPath toPontoInicial = new LinearPath(initialPoint,finalPoint);

			toPontoInicial.setTipoDeMovimento(LinearPath.FAST_MOV);
			trajetoriasRPQUHorizontalEsqInf.add(toPontoInicial);


			double avancoInicial = (Math.tan(Math.toRadians(90 - anguloRanhuraPQU)) * ranhuraPQU.getPosicaoZ());

			initialPoint = new Point3d(finalPoint.getX(),finalPoint.getY(),zAtual);
			finalPoint = new Point3d(initialPoint.getX(),initialPoint.getY() + avancoInicial,-ranhuraPQU.getPosicaoZ());
			LinearPath aproximacao = new LinearPath(initialPoint, finalPoint);
			trajetoriasRPQUHorizontalEsqInf.add(aproximacao);

			zAtual = -ranhuraPQU.getPosicaoZ();

			//VER PROF MAXIMA DA CURVA E DA PARTE RETA DE NOVO!!;

			System.out.println("Eh possivel avancar isto DEPOIS da tangencia: " + ((Math.tan(Math.toRadians(90 - anguloRanhuraPQU) / 2)) * (largEmDistProfTang - (2*ranhuraPQU.getRaio()) + ((2*ranhuraPQU.getRaio()) / Math.tan(Math.toRadians(90 - anguloRanhuraPQU))) - (f.getDiametroFerramenta() + (2*sideAllowance)) ) ));
			//			double profMaxCurva = ((ranhuraPQU.getProfundidade() - ranhuraPQU.getRaio()) + 
			//					((Math.tan(Math.toRadians(90 - (anguloRanhuraPQU / 2)))) * (largEmDistProfTang - (2*ranhuraPQU.getRaio()) + ((2*ranhuraPQU.getRaio()) / Math.tan(Math.toRadians(90 - anguloRanhuraPQU))) - (f.getDiametroFerramenta() + (2*sideAllowance)) ) ));
			double profMaxCurva = ((ranhuraPQU.getProfundidade() - ranhuraPQU.getRaio()) + 
					((Math.tan(Math.toRadians(90 - anguloRanhuraPQU) / 2)) * (largEmDistProfTang - (2*ranhuraPQU.getRaio()) + ((2*ranhuraPQU.getRaio()) / Math.tan(Math.toRadians(90 - anguloRanhuraPQU))) - (f.getDiametroFerramenta() + (2*sideAllowance)) ) ));

			double profundidadeMaxima = 0;
			System.out.println("A suposta profundidade maxima que pode alcancar a ferramenta de diametro " + f.getDiametroFerramenta() + " depois ta tangencia da curva eh de " + profMaxCurva);
			// Verifica qual e a profundidade maxima que pode se alcancar com a ferramenta atual

			System.out.println("Se nao tivesse curva seria isso " + ((((ranhuraPQU.getLargura() - (2*sideAllowance) - f.getDiametroFerramenta())) / 2) / Math.tan(Math.toRadians(90 - anguloRanhuraPQU))));

			if ( ((((ranhuraPQU.getLargura() - (2*sideAllowance) - f.getDiametroFerramenta())) / 2) / Math.tan(Math.toRadians(90 - anguloRanhuraPQU))) <= (ranhuraPQU.getProfundidade() - distProfTangCurva))
			{
				profundidadeMaxima = ((((ranhuraPQU.getLargura() - (2*sideAllowance) - f.getDiametroFerramenta())) / 2) / Math.tan(Math.toRadians(90 - anguloRanhuraPQU))); 
			}

			else 
			{
				if(profMaxCurva > ranhuraPQU.getProfundidade())
				{
					profundidadeMaxima = ranhuraPQU.getProfundidade();
				}

				else 
				{
					profundidadeMaxima = profMaxCurva;
				}

			}

			System.out.println("A profundidade maxima que pode alcancar a ferramenta de diametro " + f.getDiametroFerramenta() + " eh de " + profundidadeMaxima);
			zAtual = finalPoint.getZ();
			double apUtilizada = ws.getCondicoesUsinagem().getAp();
			boolean terminouZ = false;
			boolean ladoA = true;


			double avancoLateral = 0;
			while(!terminouZ)
			{
				System.out.println("Entrou no laco Z ");

				if (Math.abs(zAtual - apUtilizada) <= Math.abs(profundidadeMaxima))  
				{
					apUtilizada = ws.getCondicoesUsinagem().getAp();
				} else
				{
					apUtilizada = profundidadeMaxima + zAtual;//fica esperto com os sinais + e -
				}

				LinearPath sideLinear;				

				//avancoLateral antes da tangencia com a curva
				if((zAtual - apUtilizada) < (ranhuraPQU.getProfundidade() - distProfTangCurva))
				{
					avancoLateral = (Math.tan(Math.toRadians(90 - anguloRanhuraPQU)) * Math.abs((zAtual - apUtilizada)));
					initialPoint = new Point3d(finalPoint.getX(),finalPoint.getY(),zAtual);
					//					finalPoint = new Point3d(initialPoint.getX(), limiteSuperior - (f.getDiametroFerramenta()/2) - sideAllowance - avancoLateral,zAtual);
					finalPoint = new Point3d(initialPoint.getX(), limiteInferior + (f.getDiametroFerramenta()/2) + sideAllowance + avancoLateral,zAtual);
					sideLinear = new LinearPath(initialPoint, finalPoint);
					trajetoriasRPQUHorizontalEsqInf.add(sideLinear);
				}

				//avancoLateral depois da tangencia
				if((zAtual - apUtilizada) >= (ranhuraPQU.getProfundidade() - distProfTangCurva))
				{
					double avPreCurv = (Math.tan(Math.toRadians(90 - anguloRanhuraPQU)) * (ranhuraPQU.getProfundidade() - ranhuraPQU.getRaio())) ;
					avancoLateral = (avPreCurv + (((ranhuraPQU.getRaio() - distProfTangCurva) / Math.tan(Math.toRadians(anguloRanhuraPQU))) + (ranhuraPQU.getRaio() - Math.sqrt(Math.pow(ranhuraPQU.getRaio(), 2) - Math.pow( ((zAtual - apUtilizada) + (ranhuraPQU.getProfundidade() - ranhuraPQU.getRaio()) ) , 2)) )));
					initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), zAtual);
					//					finalPoint = new Point3d(initialPoint.getX(), limiteSuperior - (f.getDiametroFerramenta()/2) - sideAllowance - avancoLateral ,zAtual);
					finalPoint = new Point3d(initialPoint.getX(), limiteInferior + (f.getDiametroFerramenta()/2) + sideAllowance + avancoLateral ,zAtual);
					sideLinear = new LinearPath(initialPoint, finalPoint);
					trajetoriasRPQUHorizontalEsqInf.add(sideLinear);
				}

				System.out.println("zAtual= " + zAtual);
				System.out.println("prof Maxima= " + profundidadeMaxima);
				if(Math.abs(zAtual) == Math.abs(profundidadeMaxima))
				{
					terminouZ = true;
				}


				LinearPath avancoProfundidade;

				zAtual -= apUtilizada;

				System.out.println("zAtual depois de diminuicao= " + zAtual);

				initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), finalPoint.getZ());
				finalPoint = new Point3d(initialPoint.getX(), initialPoint.getY(), zAtual);
				avancoProfundidade = new LinearPath(initialPoint, finalPoint);
				trajetoriasRPQUHorizontalEsqInf.add(avancoProfundidade);


				boolean terminouXY = false;
				while(!terminouXY){

					LinearPath linear;
					System.out.println("zatual eh " + zAtual);
					Ranhura ranhuraAux = new Ranhura("Ranhura auxiliar",ranhuraPQU.getPosicaoX(),ranhuraPQU.getPosicaoY() + avancoLateral,zAtual+2,0,0,0,(ranhuraPQU.getLargura() - (2*sideAllowance) - (2*avancoLateral)),apUtilizada,ranhuraPQU.getComprimento(),Ranhura.HORIZONTAL);
					Workingstep wsAux = new Workingstep(ranhuraAux ,ws.getFace());
					wsAux.setCondicoesUsinagem(ws.getCondicoesUsinagem());
					wsAux.setOperation(ws.getOperation());
					wsAux.setFerramenta(ws.getFerramenta());
					wsAux.setTipo(ws.getTipo());

					System.out.println("Avanco lateral:" + avancoLateral);
					System.out.println("Ranhura x: " + ranhuraAux.getPosicaoX() + " y:" + ranhuraAux.getPosicaoY() + " z: " + ranhuraAux.getPosicaoZ());
					System.out.println("Ranhura largura: " + ranhuraAux.getLargura() + " comprimento:" + ranhuraAux.getComprimento() + " profundidade: " + ranhuraAux.getProfundidade());

					wsAux.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(wsAux));
					Vector movimentacaoRanhura = DeterminarMovimentacao.getPontosMovimentacao(wsAux);
					wsAux.setPontosMovimentacao(movimentacaoRanhura);
					System.out.println("Quantidade de vetores " + ((Vector)wsAux.getPontosMovimentacao()).size());
					//					Vector pontos = (Vector)wsAux.getPontosMovimentacao().get(0);
					System.out.println("AAAAAAAAAAAEEEEEEEEEEEEEEEEEEEEEEEEE");
					Vector pontos = wsAux.getPontosMovimentacao();
					System.out.println("Quantidade de movimentacoes caso 0" + pontos.size());

					//						initialPoint = new Point3d(finalPoint.getX(), finalPoint.getY(), finalPoint.getZ());
					//						finalPoint = new Point3d(limiteEsquerda, initialPoint.getY()+ avancoLateral,zAtual);

					for(int i = 0; i < pontos.size(); i++)
					{
						initialPoint = new Point3d(finalPoint.getX(), (ranhuraPQU.getPosicaoY() + avancoLateral), finalPoint.getZ());
						finalPoint = new Point3d(((Ponto)pontos.get(i)).getX(), ((Ponto)pontos.get(i)).getY(), ((Ponto)pontos.get(i)).getZ() - 4);


						linear = new LinearPath(initialPoint, finalPoint);

						System.out.println("i: " + i + " pontos size: " + (pontos.size()));
						if ((i == 0) || (i == (pontos.size() - 1)))
						{
							finalPoint = new Point3d(((Ponto)pontos.get(i)).getX(), ((Ponto)pontos.get(i)).getY(), 0);
							linear = new LinearPath(initialPoint, finalPoint);
							trajetoriasRPQUHorizontalEsqInf.add(linear);

						}


						else{
							if(!((initialPoint.getX() == finalPoint.getX()) && (initialPoint.getY() == finalPoint.getY()) && (initialPoint.getZ() == finalPoint.getZ())))
							{
								trajetoriasRPQUHorizontalEsqInf.add(linear);

							}
						}
						System.out.println("FinalPoint x " + finalPoint.getX() + " Final point y " + finalPoint.getY() + " finalPoint z: " + finalPoint.getZ());
					}


					terminouXY=true;

				}

			}

			//			if(pontoAtual.getZ() < ranhura.getPosicaoZ() + ranhura.getProfundidade()){//verificar se novo ponto em Z � valido
			//				if(pontoAtual.getZ() + ws.getCondicoesUsinagem().getAp() <= ranhura.getPosicaoZ() + ranhura.getProfundidade()){
			//					apUtilizada = ws.getCondicoesUsinagem().getAp();
			//				}
			//				else{
			//					apUtilizada = (ranhura.getPosicaoZ() + ranhura.getProfundidade()) - pontoAtual.getZ();
			//				}
		}

		if(ranhuraPQU.getEixo() == Ranhura.VERTICAL){
			//			double limEsquerdo = ranhuraPQU.getPosicaoX();
			//			double limDireito = (ranhuraPQU.getPosicaoX() + ranhuraPQU.getComprimento());
			//			double limInferior = 0;
			//			double limSuperior = ws.getFace().getLargura();


			double compEmDistProfTang = ranhuraPQU.getComprimento() - (2 * (ranhuraPQU.getProfundidade() - ranhuraPQU.getRaio()) * Math.tan(Math.toRadians(90 - anguloRanhuraPQU)));
			double secaoPlanaBase = compEmDistProfTang - (2 * (ranhuraPQU.getRaio() + ((ranhuraPQU.getRaio() - distProfTangCurva) / Math.tan(Math.toRadians(anguloRanhuraPQU))))); 

		}


		trajetoriasRanhuraPQuadradoU.add(trajetoriasRPQUHorizontalEsqInf); //0
		//		trajetoriasRanhuraPQuadradoU.add(trajetoriasRPQUHorizontalEsqSup); //1
		//		trajetoriasRanhuraPQuadradoU.add(trajetoriasRPQUHorizontalDirInf); //2
		//		trajetoriasRanhuraPQuadradoU.add(trajetoriasRPQUHorizontalDirSup); //3
		//		
		//		trajetoriasRanhuraPQuadradoU.add(trajetoriasRPQUVerticalEsqInf); //4
		//		trajetoriasRanhuraPQuadradoU.add(trajetoriasRPQUVerticalEsqSup); //5
		//		trajetoriasRanhuraPQuadradoU.add(trajetoriasRPQUVerticalDirInf); //6
		//		trajetoriasRanhuraPQuadradoU.add(trajetoriasRPQUVerticalDirSup); //7
		return trajetoriasRanhuraPQuadradoU;

	}
	public ArrayList<Path> getTrajetoriasAcabamentoLadosCavidade(Workingstep ws)
	{

		ArrayList<Path> trajetoriaCavidade = new ArrayList<Path>();
		Cavidade cavidade = (Cavidade)ws.getFeature();
		//System.out.println("cavidade: " + cavidade.getNome());
		Ferramenta f = ws.getFerramenta();
		double zAtual = ws.getOperation().getRetractPlane();

		Point3d initialPoint = new Point3d(cavidade.X, cavidade.Y, zAtual);
		Point3d finalPoint = new Point3d(cavidade.X + cavidade.getRaio(), cavidade.Y + f.getDiametroFerramenta()/2, zAtual);
		LinearPath toPontoInicial = new LinearPath(initialPoint, finalPoint);
		toPontoInicial.setTipoDeMovimento(LinearPath.FAST_MOV);
		trajetoriaCavidade.add(toPontoInicial);

		initialPoint = new Point3d(cavidade.X + cavidade.getRaio(), cavidade.Y + f.getDiametroFerramenta()/2, zAtual);
		finalPoint = new Point3d(cavidade.X + cavidade.getRaio(), cavidade.Y + f.getDiametroFerramenta()/2, -cavidade.Z);

		LinearPath aproximacao = new LinearPath(initialPoint, finalPoint);
		//		Point3d pontoAtual = new Point3d(cavidade.X + cavidade.getRaio(), cavidade.Y, 0);
		trajetoriaCavidade.add(aproximacao);
		//		System.out.println("A mergulho I"  + trajetoriaCavidade.get(0).getInitialPoint());
		//		System.out.println("A metgulho F "  + trajetoriaCavidade.get(0).getFinalPoint());
		zAtual = finalPoint.getZ();
		double apUtilizada = ws.getCondicoesUsinagem().getAp();
		boolean terminouZ = false;
		while(!terminouZ)
		{

			//			if(zAtual - apUtilizada >= -cavidade.getProfundidade())//atencao!, ajustar para caso absoluto (posZ)

			if (Math.abs(zAtual - apUtilizada) <= Math.abs(-cavidade.Z -cavidade.getProfundidade()))
			{
				apUtilizada = ws.getCondicoesUsinagem().getAp();
			} else
			{
				apUtilizada = (cavidade.getProfundidade() + cavidade.Z) + zAtual;//fica esperto com os sinais + e -
			}


			//			System.out.println("apUtilizada = " + apUtilizada);
			zAtual -= apUtilizada;

			initialPoint = new Point3d(finalPoint.x, finalPoint.y, finalPoint.z);
			finalPoint = new Point3d(initialPoint.x, initialPoint.y, zAtual);
			LinearPath mergulho = new LinearPath(initialPoint, finalPoint);
			trajetoriaCavidade.add(mergulho);

			//			if(zAtual == -cavidade.getProfundidade() - cavidade.getPosicaoZ()) //atencao!, ajustar para caso absoluto (posZ)

			if(zAtual == (-cavidade.Z -cavidade.getProfundidade()))
			{
				terminouZ = true;
			}
			//linear path lado1
			initialPoint = new Point3d(finalPoint.x, finalPoint.y, zAtual);
			finalPoint = new Point3d(cavidade.X + cavidade.getComprimento() - cavidade.getRaio(), finalPoint.y, zAtual);
			LinearPath lado1 = new LinearPath(initialPoint, finalPoint);
			trajetoriaCavidade.add(lado1);

			//circular path c1
			initialPoint = new Point3d(finalPoint.x, finalPoint.y, finalPoint.z);
			finalPoint = new Point3d(cavidade.X + cavidade.getComprimento() - f.getDiametroFerramenta()/2, cavidade.Y + cavidade.getRaio(), zAtual );
			//			finalPoint.setX(cavidade.X + cavidade.getComprimento() - f.getDiametroFerramenta()/2);
			//			finalPoint.setY(cavidade.Y + cavidade.getRaio());
			Point3d center = new Point3d(cavidade.X + cavidade.getComprimento() - cavidade.getRaio(), cavidade.Y + cavidade.getRaio(),zAtual);//coordenadas abs?
			CircularPath c1 = new CircularPath(initialPoint, finalPoint, center);//coord abs?
			trajetoriaCavidade.add(c1);

			//linear path l2
			initialPoint = new Point3d(finalPoint.x, finalPoint.y, finalPoint.z);
			finalPoint = new Point3d(cavidade.X + cavidade.getComprimento() - f.getDiametroFerramenta()/2, cavidade.Y + cavidade.getLargura() - cavidade.getRaio(), zAtual );
			//			finalPoint.setY(cavidade.Y + cavidade.getLargura() - cavidade.getRaio());
			LinearPath lado2 = new LinearPath(initialPoint, finalPoint);
			trajetoriaCavidade.add(lado2);

			//circular c2
			initialPoint = new Point3d(finalPoint.x, finalPoint.y, finalPoint.z);
			finalPoint = new Point3d(cavidade.X + cavidade.getComprimento() - cavidade.getRaio(), cavidade.Y + cavidade.getLargura() - f.getDiametroFerramenta()/2, zAtual);
			//			finalPoint.setX(cavidade.X + cavidade.getComprimento() - cavidade.getRaio());
			//			finalPoint.setY(cavidade.Y + cavidade.getLargura() - f.getDiametroFerramenta()/2);
			center = new Point3d(cavidade.X + cavidade.getComprimento() - cavidade.getRaio(), cavidade.Y + cavidade.getLargura() - cavidade.getRaio(), zAtual);
			//			center.setX(cavidade.X + cavidade.getComprimento() - cavidade.getRaio());
			//			center.setY(cavidade.Y + cavidade.getLargura() - cavidade.getRaio());
			CircularPath c2 = new CircularPath(initialPoint, finalPoint, center);
			trajetoriaCavidade.add(c2);

			//linear l3
			initialPoint = new Point3d(finalPoint.x, finalPoint.y, finalPoint.z);
			finalPoint = new Point3d(cavidade.X + cavidade.getRaio(), cavidade.Y + cavidade.getLargura() - f.getDiametroFerramenta()/2, zAtual);
			//			finalPoint.setX(cavidade.X + cavidade.getRaio());
			LinearPath lado3 = new LinearPath(initialPoint, finalPoint);
			trajetoriaCavidade.add(lado3);

			//circular c3
			initialPoint = new Point3d(finalPoint.x, finalPoint.y, finalPoint.z);
			finalPoint = new Point3d(cavidade.X + f.getDiametroFerramenta()/2, cavidade.Y + cavidade.getLargura() - cavidade.getRaio(), zAtual);
			//			finalPoint.setX(cavidade.X + f.getDiametroFerramenta()/2);
			//			finalPoint.setY(cavidade.Y + cavidade.getLargura() - cavidade.getRaio());
			center = new Point3d(cavidade.X + cavidade.getRaio(), cavidade.Y + cavidade.getLargura() - cavidade.getRaio(), zAtual);
			//			center.setX(cavidade.X + cavidade.getRaio());
			CircularPath c3 = new CircularPath(initialPoint, finalPoint, center);
			trajetoriaCavidade.add(c3);

			//linear path l4
			initialPoint = new Point3d(finalPoint.x, finalPoint.y, finalPoint.z);
			finalPoint = new Point3d(cavidade.X + f.getDiametroFerramenta()/2, cavidade.Y + cavidade.getRaio(), zAtual );
			//			finalPoint.setY(cavidade.Y + cavidade.getRaio());
			LinearPath lado4 = new LinearPath(initialPoint, finalPoint);
			trajetoriaCavidade.add(lado4);

			//circular c4
			initialPoint = new Point3d(finalPoint.x, finalPoint.y, finalPoint.z);
			finalPoint = new Point3d(cavidade.X + cavidade.getRaio(), cavidade.Y + f.getDiametroFerramenta()/2, zAtual);
			//			finalPoint.setX(cavidade.X + cavidade.getRaio());
			//			finalPoint.setY(cavidade.Y + f.getDiametroFerramenta()/2);
			center = new Point3d(cavidade.X + cavidade.getRaio(), cavidade.Y + cavidade.getRaio(), zAtual);
			//			center.setY(cavidade.Y + cavidade.getRaio());
			CircularPath c4 = new CircularPath(initialPoint, finalPoint, center);
			trajetoriaCavidade.add(c4);

			//			 System.out.println("ap atual = " +apUtilizada);
			//			 System.out.println("zAtual = " + zAtual);
		}

		// linear path saida
		initialPoint = new Point3d(finalPoint.x, finalPoint.y, finalPoint.z);
		finalPoint = new Point3d(initialPoint.x, initialPoint.y, initialPoint.z + 2);
		LinearPath saida = new LinearPath(initialPoint, finalPoint);
		trajetoriaCavidade.add(saida);
		return trajetoriaCavidade;
	}
	/**
	 * Este metodo deve devolver um vetor de pontos, 
	 * esses pontos sao os pontos de movimentacao da ferrameta
	 * @param ws -> Workingstep
	 * @return
	 */
	public static Vector getPontosMovimentacao(Workingstep ws){
		Vector saida = new Vector();
		Feature f = ws.getFeature();
		Ponto pontoAtual;

		boolean terminouXY = false;
		boolean terminouZ = false;
		Vector movimentacao = null; 
		double aeUtilizada = 0;
		double apUtilizada;
		double raioAtual = 0;
		double dZ = 0; // security plane

		switch(f.getTipo())
		{
		case Feature.FURO:
			Furo furo = (Furo)f;
			movimentacao = new Vector();
			pontoAtual = ws.getPontos()[0];

			pontoAtual = new Ponto(pontoAtual.getX(), pontoAtual.getY(), dZ);
			movimentacao.add(pontoAtual);

			pontoAtual = new Ponto(pontoAtual.getX(), pontoAtual.getY(), f.getPosicaoZ() - 1);
			movimentacao.add(pontoAtual);

			boolean gambiarra = false;

			while(!terminouZ)
			{
				if(furo.getPosicaoZ() + furo.getProfundidade() - pontoAtual.getZ() != 1 || gambiarra)
					// verifica se o novo ponto em Z � valido
				{

					if( ws.getOperation().getClass() == CenterDrilling.class ){

						apUtilizada = ((CenterDrilling)ws.getOperation()).getCuttingDepth();

						movimentacao.add(new Ponto(pontoAtual.getX(), pontoAtual.getY(), pontoAtual.getZ() + apUtilizada + 1));

						gambiarra = true;
					}
					else if(ws.getOperation().getClass() == Drilling.class ){

						apUtilizada = ((Drilling)ws.getOperation()).getCuttingDepth();

						movimentacao.add(new Ponto(pontoAtual.getX(), pontoAtual.getY(), pontoAtual.getZ() + apUtilizada + 1));

						gambiarra = true;

					}else if(ws.getOperation().getClass() == Boring.class ){
						
						apUtilizada = ((Boring)ws.getOperation()).getCuttingDepth();

						movimentacao.add(new Ponto(pontoAtual.getX(), pontoAtual.getY(), pontoAtual.getZ() + apUtilizada + 1));

						gambiarra = true;
						
					}
					else if(pontoAtual.getZ() + (ws.getCondicoesUsinagem().getAp() +1) <= furo.getPosicaoZ() + furo.getProfundidade())
					{
						apUtilizada = ws.getCondicoesUsinagem().getAp() + 1;//29/01
					}
					else
					{
						apUtilizada = furo.getProfundidade() + furo.getPosicaoZ() - pontoAtual.getZ();
					}
					//System.out.println("PontoAtual ===>" + pontoAtual.toString());

				}
				else
				{
					apUtilizada = -1;
				}

				//JOptionPane.showMessageDialog(null, "imprimir os dados : " + "    ap= "+apUtilizada + "   **ae= " + aeUtilizada);
				///
				if(apUtilizada != -1 && !gambiarra) // valida
				{

					movimentacao.add(new Ponto(pontoAtual.getX(), pontoAtual.getY(), pontoAtual.getZ() + apUtilizada));
					pontoAtual = (Ponto)movimentacao.lastElement();
					//System.out.println("Desce: " + pontoAtual.toString());
					terminouXY = false;
					while(!terminouXY){

						System.out.println("XY");
						if(raioAtual + ws.getFerramenta().getDiametroFerramenta() / 2 < furo.getRaio()){
							if( raioAtual + ws.getCondicoesUsinagem().getAe() + ws.getFerramenta().getDiametroFerramenta() / 2 <= furo.getRaio())
							{
								aeUtilizada = ws.getCondicoesUsinagem().getAe();
							}
							else
							{
								aeUtilizada = furo.getRaio() - (raioAtual + ws.getFerramenta().getDiametroFerramenta()/2);
								terminouXY = true;
							}
						}
						else if(raioAtual + ws.getFerramenta().getDiametroFerramenta() / 2 == furo.getRaio()){
							//JOptionPane.showMessageDialog(null, "Raio atual furo-> " + raioAtual);
							aeUtilizada = -1;
							terminouXY = true;
						}
						else{
							//erro -> matou o furo
						}
						//faz a movimentacao
						if(aeUtilizada != -1){ // raio valido, atualizar raio
							int n;
							raioAtual += aeUtilizada;

							if(furo.getRaio() < ws.getFerramenta().getDiametroFerramenta())
							{
								n = determinarNumeroPontos(2 * Math.PI, ws.getFerramenta().getDiametroFerramenta() / 8, raioAtual);
							}
							else
							{
								n = determinarNumeroPontos(2 * Math.PI, ws.getFerramenta().getDiametroFerramenta(), raioAtual);/*****ATENCAO!!!!!!!!!!!!!*******/
							}


							Ponto[] pontosDaCircunferencia = determinarPontosEmCircunferencia(0 * Math.PI, 2 * Math.PI, raioAtual, n, pontoAtual.getZ());
							//System.out.println("Circunferencia===============");
							//System.out.println("raioAtual: " + raioAtual);
							for(int i = 0; i < pontosDaCircunferencia.length; i++)
							{
								pontosDaCircunferencia[i].setX(pontosDaCircunferencia[i].getX() + furo.getPosicaoX());
								pontosDaCircunferencia[i].setY(pontosDaCircunferencia[i].getY() + furo.getPosicaoY());
								movimentacao.add(pontosDaCircunferencia[i]);
								//System.out.println("pontoAtual = " + pontosDaCircunferencia[pointIndex].toString());
							}
							pontoAtual = (Ponto)movimentacao.lastElement();								
						}							
						if(terminouXY){//tem q ir para o centro
							movimentacao.add(new Ponto(pontoAtual.getX(), pontoAtual.getY(), pontoAtual.getZ() - 1));	
							pontoAtual = (Ponto)movimentacao.lastElement();
							//System.out.println("Sobe: " + pontoAtual.toString());
							movimentacao.add(new Ponto(furo.getPosicaoX(), furo.getPosicaoY(), pontoAtual.getZ())); //====??????
							pontoAtual = (Ponto)movimentacao.lastElement();
							//System.out.println("Volta para o centro: " + pontoAtual.toString());
							raioAtual = 0;
						}
					}

					//JOptionPane.showMessageDialog(null, "pause");
				}
				else
				{
					pontoAtual = new Ponto(ws.getPontos()[0].getX(), ws.getPontos()[0].getY(), dZ);
					movimentacao.add(pontoAtual);
					terminouZ = true;
					gambiarra = false;
				}

			}// fim do while

			//System.out.println("pontoAtual = " + (Ponto)movimentacao.lastElement());
			// mandar para o ponto final = ponto
			saida.add(movimentacao);
			break;
		case Feature.DEGRAU:
			Degrau degrau = (Degrau)f;
			for(int i = 0; i < ws.getPontos().length; i++){
				terminouZ = false;
				Ponto pontoTmp = ws.getPontos()[i];
				//System.out.println("pontoTmp= " + pontoTmp.toString());
				movimentacao = new Vector();
				boolean sentido = true;
				/**
				 * true = horizontal
				 * false = vertical
				 */
				double[] limites = calcularLimites(ws);
				double limiteEsquerda = limites[0];
				double limiteDireita = limites[1];
				double limiteSuperior = limites[2];
				double limiteInferior = limites[3];
				if(degrau.getEixo() == Degrau.HORIZONTAL)
				{
					sentido = true;
				}
				else
				{
					sentido = false;
				}
				pontoAtual = pontoTmp;
				pontoAtual = new Ponto(pontoAtual.getX(), pontoAtual.getY(), dZ);
				movimentacao.add(pontoAtual);
				pontoAtual = new Ponto(pontoAtual.getX(), pontoAtual.getY(), degrau.getPosicaoZ());
				movimentacao.add(pontoAtual);
				//System.out.println("limites: esquerda= " + limites[0] + "  direita= " + limites[1] + "  superior= " + limites[2] + "  inferior= " + limites[3]);
				while(!terminouZ)
				{
					//System.out.println("While do Z -> " + pontoAtual);
					if(pontoAtual.getZ() < degrau.getPosicaoZ() + degrau.getProfundidade()){//verificar se novo ponto em Z � valido
						if(pontoAtual.getZ() + ws.getCondicoesUsinagem().getAp() <= degrau.getPosicaoZ() + degrau.getProfundidade()){
							apUtilizada = ws.getCondicoesUsinagem().getAp();
						}
						else{
							apUtilizada = (degrau.getPosicaoZ() + degrau.getProfundidade()) - pontoAtual.getZ();
						}
						//System.out.println("Ap utilizada = " + apUtilizada);
						movimentacao.add(new Ponto(pontoAtual.getX(), pontoAtual.getY(), pontoAtual.getZ() + apUtilizada));
						pontoAtual = (Ponto)movimentacao.lastElement();
						//System.out.println("PontoAtual ===>" + pontoAtual.toString());

						boolean sentidoQuebra;
						if(sentido){//horizontal
							if(pontoAtual.getY() == limiteSuperior){
								sentidoQuebra = false;// sentidoQuebra = false -> descer
								//System.out.println("Sentido movimentacao -> horizontal + descendo");								
							}
							else{
								sentidoQuebra = true;// sentidoQuebra = true -> subir
								//System.out.println("Sentido movimentacao -> horizontal + subindo");
							}
						}
						else{//vertical
							if(pontoAtual.getX() == limiteEsquerda){
								sentidoQuebra = false;//vai para a direita
								//System.out.println("Sentido movimentacao -> vertical + esquerda");
							}
							else{
								sentidoQuebra = true;//vai para esquerda
								//System.out.println("Sentido movimentacao -> vertical + esquerda");
							}
							//System.out.println("sentidoQuebra: " + sentidoQuebra);
						}
						//JOptionPane.showMessageDialog(null, "Pause para entrar no whileXY");
						terminouXY = false;
						while(!terminouXY){
							//double aeUtilizada;//Ae
							boolean mov1 = false;
							boolean mov2 = false;
							if(sentido){//-------------------sentido = true (horizontal)---------------

								if(sentidoQuebra){//subindo
									//System.out.print("Subindo ");
									if(pontoAtual.getY() < limiteSuperior){
										mov1 = true;
										mov2 = true;
									}
									else if(pontoAtual.getY() == limiteSuperior){
										mov1 = true;
										mov2 = false;
									}
									else{
										mov1 = false;
										mov2 = false;
										//erro! JOptionPane
									}							
								}
								else{//descendo
									//System.out.print("Descendo ");
									if(pontoAtual.getY() > limiteInferior){
										mov1 = true;
										mov2 = true;		
									}
									else if(pontoAtual.getY() == limiteInferior){
										mov1 = true;
										mov2 = false;
									}
									else{
										mov1 = false;
										mov2 = false;
										//erro! JOptionPane
									}
								}
								//System.out.println("mov1=" + mov1 + " mov2=" + mov2);
								if(mov1){
									if(pontoAtual.getX() == limiteEsquerda){//vai para limiteDireita
										pontoAtual = new Ponto(limiteDireita, pontoAtual.getY(), pontoAtual.getZ());
									}
									else{//esta no limiteDireita e vai para o limiteEsquerda
										pontoAtual = new Ponto(limiteEsquerda, pontoAtual.getY(), pontoAtual.getZ());
									}
									//System.out.println("pontos: " + pontoAtual);
									movimentacao.add(pontoAtual);
									if(mov2){
										if(sentidoQuebra){//subindo
											if(pontoAtual.getY() + ws.getCondicoesUsinagem().getAe() <= limiteSuperior){
												aeUtilizada = ws.getCondicoesUsinagem().getAe();
											}
											else{
												aeUtilizada = limiteSuperior - pontoAtual.getY();
											}
											pontoAtual = new Ponto(pontoAtual.getX(), pontoAtual.getY() + aeUtilizada, pontoAtual.getZ());
											movimentacao.add(pontoAtual);
										}
										else{//descendo
											if(pontoAtual.getY() - ws.getCondicoesUsinagem().getAe() >= limiteInferior){
												aeUtilizada = ws.getCondicoesUsinagem().getAe();
											}
											else{
												aeUtilizada = pontoAtual.getY() - limiteInferior;
											}
											pontoAtual = new Ponto(pontoAtual.getX(), pontoAtual.getY() - aeUtilizada, pontoAtual.getZ());
											movimentacao.add(pontoAtual);
										}
										//System.out.println("pontos: " + pontoAtual.toString());
									}
									else{
										terminouXY = true;
									}
								}									
							} //----------------Acabou sentido = true ---------------------------
							else{//*****************************************vertical**********************
								/**
								 * colocar o negocio para movimentacao na vertical
								 */
								if(sentidoQuebra)//indo para a esquerda
								{
									//System.out.print("indo para a esquerda");
									if (pontoAtual.getX() > limiteEsquerda)
									{
										mov1 = true;
										mov2 = true;
									}
									else if(pontoAtual.getX() == limiteEsquerda)
									{
										mov1 = true;
										mov2 = false;
									}
									else
									{
										mov1 = false;
										mov2 = false;
										//!!!erro JOptionPane
										//JOptionPane.showMessageDialog(null, "(indo para a esquerda)erro-> mov1 = mov2 = false!!!!!!");
									}
								}
								else // indo para a direita
								{
									//System.out.println("indo para a direita");
									if(pontoAtual.getX() < limiteDireita)
									{
										mov1 = true;
										mov2 = true;
									}
									else if(pontoAtual.getX() == limiteDireita)
									{
										mov1 = true;
										mov2 = false;
									}
									else
									{
										mov1 = false;
										mov2 = false;
										//erro!!! JOptionPane
										//JOptionPane.showMessageDialog(null, "(indo para direita)erro-> mov1 = mov2 =false");
									}
								}
								//System.out.println("***mov1 = " + mov1 + "   ***mov2 = " + mov2);
								if (mov1) // ------------VERTICAL 
								{
									if(pontoAtual.getY() == limiteInferior)
									{
										pontoAtual = new Ponto(pontoAtual.getX(), limiteSuperior, pontoAtual.getZ());
									}
									else
									{
										pontoAtual = new Ponto(pontoAtual.getX(), limiteInferior, pontoAtual.getZ());
									}
									//System.out.println("  ***V-pontos: " + pontoAtual);
									movimentacao.add(pontoAtual);

									if (mov2) //------------------HORIZONTAL 
									{
										if(sentidoQuebra) // indo para a esquerda
										{
											if(pontoAtual.getX() - ws.getCondicoesUsinagem().getAe() >= limiteEsquerda)
											{
												aeUtilizada = ws.getCondicoesUsinagem().getAe();
											}
											else
											{
												aeUtilizada = pontoAtual.getX() - limiteEsquerda;
											}
											pontoAtual = new Ponto(pontoAtual.getX() - aeUtilizada, pontoAtual.getY(), pontoAtual.getZ());
											movimentacao.add(pontoAtual);
										}
										else // indo para a direita
										{
											if(pontoAtual.getX() + ws.getCondicoesUsinagem().getAe() <= limiteDireita)
											{
												aeUtilizada = ws.getCondicoesUsinagem().getAe();
											}
											else
											{
												aeUtilizada = limiteDireita - pontoAtual.getX();
											}
											pontoAtual = new Ponto(pontoAtual.getX() + aeUtilizada, pontoAtual.getY(), pontoAtual.getZ());
											movimentacao.add(pontoAtual);
										}
										//System.out.println("  ***V-PONTOS: " + pontoAtual);
									}
									else
									{
										terminouXY = true;
									}
								}
							}	
						}
					}
					else{
						pontoAtual = new Ponto(pontoAtual.getX(), pontoAtual.getY(), dZ);
						movimentacao.add(pontoAtual);
						//System.out.println("pontoAtual = " + (Ponto)movimentacao.lastElement());
						terminouZ = true;
					}						
				}
				saida.add(movimentacao);
			}
			break;
		case Feature.RANHURA:
			Ranhura ranhura = (Ranhura)f;
			System.out.println("ran");

			System.out.println("Pontos: " + ws.getPontos());

			for(int i = 0; i < ws.getPontos().length; i++){

				Ponto pontoTmp = ws.getPontos()[i];
				//					System.out.println("pontoTmp= " + pontoTmp.toString());
				movimentacao = new Vector();
				terminouZ = false;
				boolean sentido = false;

				/**
				 * true = horizontal
				 * false = vertical
				 */
				double[] limites = calcularLimites(ws);
				double limiteEsquerda = limites[0];
				double limiteDireita = limites[1];
				double limiteSuperior = limites[2];
				double limiteInferior = limites[3];
				if(ranhura.getEixo() == Ranhura.HORIZONTAL)
				{
					sentido = true;
				}
				else
				{
					sentido = false;
				}
				pontoAtual = pontoTmp;
				//					pontoAtual = new Ponto(pontoAtual.getX(), pontoAtual.getY(), dZ);
				//					movimentacao.add(pontoAtual);
				pontoAtual = new Ponto(pontoAtual.getX(), pontoAtual.getY(), ranhura.getPosicaoZ());
				movimentacao.add(pontoAtual);
				//System.out.println("limites: esquerda= " + limites[0] + "  direita= " + limites[1] + "  superior= " + limites[2] + "  inferior= " + limites[3]);
				while(!terminouZ)
				{
					//System.out.println("While do Z -> " + pontoAtual);

					if(pontoAtual.getZ() < ranhura.getPosicaoZ() + ranhura.getProfundidade()){//verificar se novo ponto em Z � valido

						if(pontoAtual.getZ() + ws.getCondicoesUsinagem().getAp() <= ranhura.getPosicaoZ() + ranhura.getProfundidade()){
							apUtilizada = ws.getCondicoesUsinagem().getAp();
						}
						else{
							apUtilizada = (ranhura.getPosicaoZ() + ranhura.getProfundidade()) - pontoAtual.getZ();
						}
						//System.out.println("Ap utilizada = " + apUtilizada);
						movimentacao.add(new Ponto(pontoAtual.getX(), pontoAtual.getY(), pontoAtual.getZ() + apUtilizada));
						pontoAtual = (Ponto)movimentacao.lastElement();
						//System.out.println("PontoAtual ===>" + pontoAtual.toString());

						boolean sentidoQuebra;
						if(sentido){//horizontal
							if(pontoAtual.getY() == limiteSuperior){
								sentidoQuebra = false;// sentidoQuebra = false -> descer
								//System.out.println("Sentido movimentacao -> horizontal + descendo");								
							}
							else{
								sentidoQuebra = true;// sentidoQuebra = true -> subir
								//System.out.println("Sentido movimentacao -> horizontal + subindo");
							}
						}
						else{//vertical
							if(pontoAtual.getX() == limiteEsquerda){
								sentidoQuebra = false;//vai para a direita
								//System.out.println("Sentido movimentacao -> vertical + esquerda");
							}
							else{
								sentidoQuebra = true;//vai para esquerda
								//System.out.println("Sentido movimentacao -> vertical + esquerda");
							}
							//System.out.println("sentidoQuebra: " + sentidoQuebra);
						}
						//JOptionPane.showMessageDialog(null, "Pause para entrar no whileXY");
						terminouXY = false;
						while(!terminouXY){
							//double aeUtilizada;//Ae
							boolean mov1 = false;
							boolean mov2 = false;
							if(sentido){//-------------------sentido = true (horizontal)---------------

								if(sentidoQuebra){//subindo
									//System.out.print("Subindo ");
									if(pontoAtual.getY() < limiteSuperior){
										mov1 = mov2 = true;	
									}
									else if(pontoAtual.getY() == limiteSuperior){
										mov1 = true;
										mov2 = false;
									}
									else{
										mov1 = mov2 = false;	
										//erro! JOptionPane
									}							
								}
								else{//descendo
									//System.out.print("Descendo ");
									if(pontoAtual.getY() > limiteInferior){
										mov1 = mov2 = true;		
									}
									else if(pontoAtual.getY() == limiteInferior){
										mov1 = true;
										mov2 = false;
									}
									else{
										mov1 = mov2 = false;
										//erro! JOptionPane
									}
								}
								//System.out.println("mov1=" + mov1 + " mov2=" + mov2);
								if(mov1){
									if(pontoAtual.getX() == limiteEsquerda){//vai para limiteDireita
										pontoAtual = new Ponto(limiteDireita, pontoAtual.getY(), pontoAtual.getZ());
									}
									else{//esta no limiteDireita e vai para o limiteEsquerda
										pontoAtual = new Ponto(limiteEsquerda, pontoAtual.getY(), pontoAtual.getZ());
									}
									//System.out.println("pontos: " + pontoAtual);
									movimentacao.add(pontoAtual);

									if(mov2){
										if(sentidoQuebra){//subindo
											if(pontoAtual.getY() + ws.getCondicoesUsinagem().getAe() <= limiteSuperior){
												aeUtilizada = ws.getCondicoesUsinagem().getAe();
											}
											else{
												aeUtilizada = limiteSuperior - pontoAtual.getY();
											}

											pontoAtual = new Ponto(pontoAtual.getX(), pontoAtual.getY() + aeUtilizada, pontoAtual.getZ());
											movimentacao.add(pontoAtual);
										}
										else{//descendo
											if(pontoAtual.getY() - ws.getCondicoesUsinagem().getAe() >= limiteInferior){
												aeUtilizada = ws.getCondicoesUsinagem().getAe();
											}
											else{
												aeUtilizada = pontoAtual.getY() - limiteInferior;
											}
											pontoAtual = new Ponto(pontoAtual.getX(), pontoAtual.getY() - aeUtilizada, pontoAtual.getZ());
											movimentacao.add(pontoAtual);
										}
										//System.out.println("pontos: " + pontoAtual.toString());
									}
									else{
										terminouXY = true;
									}
								}									
							} //----------------Acabou sentido = true ---------------------------
							else{//*****************************************vertical**********************
								/**
								 * colocar o negocio para movimentacao na vertical
								 */
								if(sentidoQuebra)//indo para a esquerda
								{
									//System.out.print("indo para a esquerda");
									if (pontoAtual.getX() > limiteEsquerda)
									{
										mov1 = mov2 = true;	
									}
									else if(pontoAtual.getX() == limiteEsquerda)
									{
										mov1 = true;
										mov2 = false;
									}
									else
									{
										mov1 = mov2 = false;	
										//!!!erro JOptionPane
										//JOptionPane.showMessageDialog(null, "(indo para a esquerda)erro-> mov1 = mov2 = false!!!!!!");
									}
								}
								else // indo para a direita
								{
									//System.out.println("indo para a direita");
									if(pontoAtual.getX() < limiteDireita)
									{
										mov1 = mov2 = true;	
									}
									else if(pontoAtual.getX() == limiteDireita)
									{
										mov1 = true;
										mov2 = false;
									}
									else
									{
										mov1 = mov2 = false;	
										//JOptionPane.showMessageDialog(null, "(indo para direita)erro-> mov1 = mov2 =false");
									}
								}
								//System.out.println("***mov1 = " + mov1 + "   ***mov2 = " + mov2);
								if (mov1) // ------------VERTICAL 
								{
									if(pontoAtual.getY() == limiteInferior)
									{
										pontoAtual = new Ponto(pontoAtual.getX(), limiteSuperior, pontoAtual.getZ());
									}
									else
									{
										pontoAtual = new Ponto(pontoAtual.getX(), limiteInferior, pontoAtual.getZ());
									}
									//System.out.println("  ***V-pontos: " + pontoAtual);
									movimentacao.add(pontoAtual);

									if (mov2) //------------------HORIZONTAL 
									{
										if(sentidoQuebra) // indo para a esquerda
										{
											if(pontoAtual.getX() - ws.getCondicoesUsinagem().getAe() >= limiteEsquerda)
											{
												aeUtilizada = ws.getCondicoesUsinagem().getAe();
											}
											else
											{
												aeUtilizada = pontoAtual.getX() - limiteEsquerda;
											}
											pontoAtual = new Ponto(pontoAtual.getX() - aeUtilizada, pontoAtual.getY(), pontoAtual.getZ());
											movimentacao.add(pontoAtual);
										}
										else // indo para a direita
										{
											if(pontoAtual.getX() + ws.getCondicoesUsinagem().getAe() <= limiteDireita)
											{
												aeUtilizada = ws.getCondicoesUsinagem().getAe();
											}
											else
											{
												aeUtilizada = limiteDireita - pontoAtual.getX();
											}
											pontoAtual = new Ponto(pontoAtual.getX() + aeUtilizada, pontoAtual.getY(), pontoAtual.getZ());
											movimentacao.add(pontoAtual);
										}
										//System.out.println("  ***V-PONTOS: " + pontoAtual);
									}
									else
									{
										terminouXY = true;
									}
								}
							}	
						}
					}
					else{
						pontoAtual = new Ponto(pontoAtual.getX(), pontoAtual.getY(), dZ);
						movimentacao.add(pontoAtual);
						terminouZ = true;
					}						
				}
				saida.add(movimentacao);


			}
			break;
		case Feature.CAVIDADE:
			Cavidade cavidade = (Cavidade)f;
			double diagonalTotal;
			double diagonal;
			double raio = cavidade.getRaio();
//			if (ws.getOperation().getStartPoint().x == 0 && ws.getOperation().getStartPoint().y == 0 && ws.getOperation().getStartPoint().z == 0 && ws.getFeature().getWorkingsteps().size() > 1)
			if (ws.getOperation().getStartPoint().x == 0 && ws.getOperation().getStartPoint().y == 0 && ws.getOperation().getStartPoint().z == 0 && cavidade.getRaio() < ws.getFerramenta().getDiametroFerramenta() / 2)
			{
				System.out.println("ENTROOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOU!!!!");
//				cavidade = new Cavidade(cavidade.X, cavidade.Y, cavidade.Z, cavidade.getLocX(), cavidade.getLocY(), cavidade.getLocZ(), ws.getFerramenta().getDiametroFerramenta() / 2, cavidade.getLargura(), cavidade.getComprimento(), cavidade.getProfundidade());
				System.out.println("RAIIIIIIOOOOOOOOO1111 : " + cavidade.getRaio());

				cavidade.setRaio(ws.getFerramenta().getDiametroFerramenta() / 2);
				
				System.out.println("RAIIIIIIOOOOOOOOO2222 : " + cavidade.getRaio());
			}
			if (cavidade.getLargura() < cavidade.getComprimento())
			{
				diagonalTotal = Math.sqrt(2) * (cavidade.getLargura() / 2 - cavidade.getRaio()) + cavidade.getRaio();
			}
			else
			{
				diagonalTotal = Math.sqrt(2) * (cavidade.getComprimento() / 2 - cavidade.getRaio()) + cavidade.getRaio();
			}

			diagonal = diagonalTotal - cavidade.getRaio();

			for(int i = 0; i < ws.getPontos().length; i++)
			{
				terminouZ = false;
				Ponto pontoTmp = ws.getPontos()[i];
				pontoAtual = pontoTmp;
				pontoAtual = new Ponto(pontoAtual.getX(), pontoAtual.getY(), dZ);
				movimentacao = new Vector();
				//					movimentacao.add(pontoAtual);
				pontoAtual = new Ponto(pontoAtual.getX(), pontoAtual.getY(), cavidade.getPosicaoZ() - 1);
				//					movimentacao.add(pontoAtual);

				while(!terminouZ)
				{
					//System.out.println("While do Z -> " + pontoAtual);
					if(cavidade.getPosicaoZ() + cavidade.getProfundidade() - pontoAtual.getZ() != 1)//verificar se novo ponto em Z � valido
					{
						if(pontoAtual.getZ() + (ws.getCondicoesUsinagem().getAp() + 1) <= cavidade.getPosicaoZ() + cavidade.getProfundidade())
						{
							apUtilizada = ws.getCondicoesUsinagem().getAp() + 1;
						}
						else
						{
							apUtilizada = (cavidade.getPosicaoZ() + cavidade.getProfundidade()) - pontoAtual.getZ();
						}
						//System.out.println("PontoAtual ===>" + pontoAtual.toString());
					}
					else
					{
						apUtilizada = -1;
					}
					//JOptionPane.showMessageDialog(null, "imprimir os dados : " + "    ap= "+apUtilizada + "   **ae= " + aeUtilizada);
					if(apUtilizada != -1)
					{
						movimentacao.add(new Ponto(pontoAtual.getX(), pontoAtual.getY(), pontoAtual.getZ() + apUtilizada));
						pontoAtual = (Ponto)movimentacao.lastElement();
						//System.out.println("Desce: " + pontoAtual.toString());

						// **** comeca a se mexer do ws.getPonto()[0] ao ponto [1] 
						// **** ou viseversa
						boolean modo = true;
						if(pontoAtual.getX() == ws.getPontos()[0].getX() && pontoAtual.getY() == ws.getPontos()[0].getY())
						{
							pontoAtual = new Ponto(ws.getPontos()[1].getX(), ws.getPontos()[1].getY(), pontoAtual.getZ());
							modo = true;
						}
						else if(pontoAtual.getX() == ws.getPontos()[1].getX() && pontoAtual.getY() == ws.getPontos()[1].getY())
						{
							pontoAtual = new Ponto(ws.getPontos()[0].getX(), ws.getPontos()[0].getY(), pontoAtual.getZ());
							modo = false;
						}
						else{
							JOptionPane.showMessageDialog(null, "BUG!");
						}
						movimentacao.add(pontoAtual);

						//System.out.println("pontoAtual = " +  pontoAtual);
						terminouXY = false;
						raioAtual = 0;
						while(!terminouXY)
						{
							boolean dentroRet = true;
							if(raioAtual + ws.getFerramenta().getDiametroFerramenta() / 2 < diagonalTotal)
							{
								if(raioAtual < diagonal)
								{
									if(raioAtual + ws.getCondicoesUsinagem().getAe() < diagonal)
									{
										aeUtilizada = ws.getCondicoesUsinagem().getAe();
									}
									else
									{
										aeUtilizada = diagonal - raioAtual;
									}
									dentroRet = true;
								}
								else
								{
									dentroRet = false;
									if(raioAtual + ws.getCondicoesUsinagem().getAe() + ws.getFerramenta().getDiametroFerramenta() / 2 < diagonalTotal)
									{
										aeUtilizada = ws.getCondicoesUsinagem().getAe();
									}
									else
									{
										aeUtilizada = diagonalTotal - ws.getFerramenta().getDiametroFerramenta() / 2 - raioAtual;
									}
								}
							}
							else
							{
								aeUtilizada = -1;
								terminouXY = true;
							}
							//System.out.println("Dentro do retangulo = = = " + dentroRet);
							// faz movimentacao
							if (aeUtilizada != -1) // "raio" valido, atualizar "raio"
							{
								//JOptionPane.showMessageDialog(null, "modo " + modo + "(Z= " + pontoAtual.getZ() + ")");
								int n;
								raioAtual += aeUtilizada;
								double raioCircunferencia; // raio da circunferencia a interpolar
								raioCircunferencia = raioAtual - diagonal;
								//System.out.println("RaioAtual == " + raioAtual);
								if(dentroRet)
								{
									//verificar o modo antes de pegar os pontos
									//pega os 5 pontos
									//adiciona os pontos no vetor movimentacao
									Ponto[] pontosRetanguloCavidade;

									if (modo) // os pontos comecam a partir de 0 e terminam em 360
									{
										pontosRetanguloCavidade = determinarPontosRetanguloCavidade(ws, pontoAtual.getZ(), raioAtual, true);
									}
									else // os pontos comencao a partir de 180 e terminam com 180 + 360
									{
										pontosRetanguloCavidade = determinarPontosRetanguloCavidade(ws, pontoAtual.getZ(), raioAtual, false);
									}
									//System.out.println("MODO = " + modo);
									for(int j = 0; j < pontosRetanguloCavidade.length; j++)
									{
										movimentacao.add(pontosRetanguloCavidade[j]);
										//System.out.println("PontoAtual ====" + pontosRetanguloCavidade[j]);
									}
								}
								else
								{
									//System.out.println(" ======== INTERPOLAR ARCO CIRCULAR =======");
									//determina o raio da circunf. da interpolacao -> raioAtual - diagonal
									/**
									 * se modo == true
									 * 	solicita pontos a partir de 45 com delta 45
									 * 	1 for
									 * 	muda a posicao dos pontos e adiciona no vetor
									 * 	1 for
									 * 	desloca ate outra extremidade
									 * 	solicita pontos a partir de 90 at� 180 (delta 90)
									 * 	2 for
									 * 	muda posicao + adiciona no vetor
									 * 	2 for
									 * 	movimenta at� outra extremidade
									 * 	solicita pontos a partir de 180 at� 270 (delta 90)
									 * 	3 for
									 * 	muda posicao + adiciona no vetor
									 * 	3 for
									 * 	movimenta at� outra extremidade
									 * 	solicita pontos a partir de 270 at� 360 (delta 90)
									 * 	4 for
									 * 	muda posicao + adiciona no vetor
									 * 	4 for
									 * 	solicita pontos a partir de 0 com delta 45
									 * 	5 for
									 * 	muda a posicao dos pontos e adiciona no vetor
									 * 	5 for
									 */
									//System.out.println("Cavidade=========================");
									Ponto[] pontosInterpolacaoCavidade;
									//System.out.println("RaioAtual == " + raioAtual);
									if(modo)
									{
										n = determinarNumeroPontos(Math.PI / 4, ws.getFerramenta().getDiametroFerramenta(), raioCircunferencia);
										pontosInterpolacaoCavidade = determinarPontosEmCircunferencia(Math.PI / 4, Math.PI / 4, raioCircunferencia, n, pontoAtual.getZ());
										for(int j = 0; j < pontosInterpolacaoCavidade.length; j++)
										{
											pontosInterpolacaoCavidade[j].setX(pontosInterpolacaoCavidade[j].getX() + cavidade.getPosicaoX() + cavidade.getComprimento() - cavidade.getRaio());
											pontosInterpolacaoCavidade[j].setY(pontosInterpolacaoCavidade[j].getY() + cavidade.getPosicaoY() + cavidade.getLargura() - cavidade.getRaio());
											movimentacao.add(pontosInterpolacaoCavidade[j]);
											//System.out.println("pontoAtual --->  : " + pontosInterpolacaoCavidade[j]);
										}
										n = determinarNumeroPontos(Math.PI / 2, ws.getFerramenta().getDiametroFerramenta(), raioCircunferencia);// ======?????? =====
										pontosInterpolacaoCavidade = determinarPontosEmCircunferencia(Math.PI / 2, Math.PI / 2, raioCircunferencia, n, pontoAtual.getZ());
										for(int j = 0; j < pontosInterpolacaoCavidade.length; j++)
										{
											pontosInterpolacaoCavidade[j].setX(pontosInterpolacaoCavidade[j].getX() + cavidade.getPosicaoX() + cavidade.getRaio());
											pontosInterpolacaoCavidade[j].setY(pontosInterpolacaoCavidade[j].getY() + cavidade.getPosicaoY() + cavidade.getLargura() - cavidade.getRaio());
											movimentacao.add(pontosInterpolacaoCavidade[j]);
											//System.out.println("pontoAtual --->  : " + pontosInterpolacaoCavidade[j]);
										}
										pontosInterpolacaoCavidade = determinarPontosEmCircunferencia(Math.PI, Math.PI / 2, raioCircunferencia, n, pontoAtual.getZ());
										for(int j = 0; j < pontosInterpolacaoCavidade.length; j++)
										{
											pontosInterpolacaoCavidade[j].setX(pontosInterpolacaoCavidade[j].getX() + cavidade.getPosicaoX() + cavidade.getRaio());
											pontosInterpolacaoCavidade[j].setY(pontosInterpolacaoCavidade[j].getY() + cavidade.getPosicaoY() + cavidade.getRaio());
											movimentacao.add(pontosInterpolacaoCavidade[j]);
											//System.out.println("pontoAtual --->  : " + pontosInterpolacaoCavidade[j]);
										}
										pontosInterpolacaoCavidade = determinarPontosEmCircunferencia(3 * Math.PI / 2, Math.PI / 2, raioCircunferencia, n, pontoAtual.getZ());
										for(int j = 0; j < pontosInterpolacaoCavidade.length; j++)
										{
											pontosInterpolacaoCavidade[j].setX(pontosInterpolacaoCavidade[j].getX() + cavidade.getPosicaoX() + cavidade.getComprimento() - cavidade.getRaio());
											pontosInterpolacaoCavidade[j].setY(pontosInterpolacaoCavidade[j].getY() + cavidade.getPosicaoY() + cavidade.getRaio());
											movimentacao.add(pontosInterpolacaoCavidade[j]);
											//System.out.println("pontoAtual --->  : " + pontosInterpolacaoCavidade[j]);
										}
										n = determinarNumeroPontos(Math.PI / 4, ws.getFerramenta().getDiametroFerramenta(), raioCircunferencia);
										pontosInterpolacaoCavidade = determinarPontosEmCircunferencia(0, Math.PI / 4, raioCircunferencia, n, pontoAtual.getZ());
										for(int j = 0; j < pontosInterpolacaoCavidade.length; j++)
										{
											pontosInterpolacaoCavidade[j].setX(pontosInterpolacaoCavidade[j].getX() + cavidade.getPosicaoX() + cavidade.getComprimento() - cavidade.getRaio());
											pontosInterpolacaoCavidade[j].setY(pontosInterpolacaoCavidade[j].getY() + cavidade.getPosicaoY() + cavidade.getLargura() - cavidade.getRaio());
											movimentacao.add(pontosInterpolacaoCavidade[j]);
											//System.out.println("pontoAtual --->  : " + pontosInterpolacaoCavidade[j]);
										}
										pontoAtual = (Ponto)movimentacao.lastElement();
									}
									else
									{
										n = determinarNumeroPontos(Math.PI / 4, ws.getFerramenta().getDiametroFerramenta(), raioCircunferencia);
										pontosInterpolacaoCavidade = determinarPontosEmCircunferencia(5 * Math.PI / 4, Math.PI / 4, raioCircunferencia, n, pontoAtual.getZ());
										for(int j = 0; j < pontosInterpolacaoCavidade.length; j++)
										{
											pontosInterpolacaoCavidade[j].setX(pontosInterpolacaoCavidade[j].getX() + cavidade.getPosicaoX() + cavidade.getRaio());
											pontosInterpolacaoCavidade[j].setY(pontosInterpolacaoCavidade[j].getY() + cavidade.getPosicaoY() + cavidade.getRaio());
											movimentacao.add(pontosInterpolacaoCavidade[j]);
											//System.out.println("pontoAtual --->  : " + pontosInterpolacaoCavidade[j]);
										}
										n = determinarNumeroPontos(Math.PI / 2, ws.getFerramenta().getDiametroFerramenta(), raioCircunferencia);
										pontosInterpolacaoCavidade = determinarPontosEmCircunferencia(3 * Math.PI / 2, Math.PI / 2, raioCircunferencia, n, pontoAtual.getZ());
										for(int j = 0; j < pontosInterpolacaoCavidade.length; j++)
										{
											pontosInterpolacaoCavidade[j].setX(pontosInterpolacaoCavidade[j].getX() + cavidade.getPosicaoX() + cavidade.getComprimento() - cavidade.getRaio());
											pontosInterpolacaoCavidade[j].setY(pontosInterpolacaoCavidade[j].getY() + cavidade.getPosicaoY() + cavidade.getRaio());
											movimentacao.add(pontosInterpolacaoCavidade[j]);
											//System.out.println("pontoAtual --->  : " + pontosInterpolacaoCavidade[j]);
										}
										pontosInterpolacaoCavidade = determinarPontosEmCircunferencia(0, Math.PI / 2, raioCircunferencia, n, pontoAtual.getZ());
										for(int j = 0; j < pontosInterpolacaoCavidade.length; j++)
										{
											pontosInterpolacaoCavidade[j].setX(pontosInterpolacaoCavidade[j].getX() + cavidade.getPosicaoX() + cavidade.getComprimento() - cavidade.getRaio());
											pontosInterpolacaoCavidade[j].setY(pontosInterpolacaoCavidade[j].getY() + cavidade.getPosicaoY() + cavidade.getLargura() - cavidade.getRaio());
											movimentacao.add(pontosInterpolacaoCavidade[j]);
											//System.out.println("pontoAtual --->  : " + pontosInterpolacaoCavidade[j]);
										}
										pontosInterpolacaoCavidade = determinarPontosEmCircunferencia(Math.PI / 2, Math.PI / 2, raioCircunferencia, n, pontoAtual.getZ());
										for(int j = 0; j < pontosInterpolacaoCavidade.length; j++)
										{
											pontosInterpolacaoCavidade[j].setX(pontosInterpolacaoCavidade[j].getX() + cavidade.getPosicaoX() + cavidade.getRaio());
											pontosInterpolacaoCavidade[j].setY(pontosInterpolacaoCavidade[j].getY() + cavidade.getPosicaoY() + cavidade.getLargura() - cavidade.getRaio());
											movimentacao.add(pontosInterpolacaoCavidade[j]);
											//System.out.println("pontoAtual --->  : " + pontosInterpolacaoCavidade[j]);
										}
										n = determinarNumeroPontos(Math.PI / 4, ws.getFerramenta().getDiametroFerramenta(), raioCircunferencia);
										pontosInterpolacaoCavidade = determinarPontosEmCircunferencia(Math.PI, Math.PI / 4, raioCircunferencia, n, pontoAtual.getZ());
										for(int j = 0; j < pontosInterpolacaoCavidade.length; j++)
										{
											pontosInterpolacaoCavidade[j].setX(pontosInterpolacaoCavidade[j].getX() + cavidade.getPosicaoX() + cavidade.getRaio());
											pontosInterpolacaoCavidade[j].setY(pontosInterpolacaoCavidade[j].getY() + cavidade.getPosicaoY() + cavidade.getRaio());
											movimentacao.add(pontosInterpolacaoCavidade[j]);
											//System.out.println("pontoAtual --->  : " + pontosInterpolacaoCavidade[j]);
										}
										pontoAtual = (Ponto)movimentacao.lastElement();
									}
								}
								pontoAtual = (Ponto)movimentacao.lastElement();
							}
							if(terminouXY) // tem de ir para o ponto inicial
							{
								movimentacao.add(new Ponto(pontoAtual.getX(), pontoAtual.getY(), pontoAtual.getZ() - 1)); //Sobe
								pontoAtual = (Ponto)movimentacao.lastElement();
								//System.out.println("Sobe: " + pontoAtual);

								//29/01
								/**
								 * Vai para o ponto de controle da cavidade mais proximo!
								 */
								double distUltimo = 0;
								double distTmp = 0;
								Ponto ultimoPonto = null;
								for(int k = 0; k < ws.getPontos().length; k++){
									if(k == 0){
										ultimoPonto = new Ponto(ws.getPontos()[k].getX(), ws.getPontos()[k].getY(), pontoAtual.getZ());
									}
									else{
										Ponto pTmp = new Ponto(ws.getPontos()[k].getX(), ws.getPontos()[k].getY(), pontoAtual.getZ());

										//verifica se a distancia entre o pTmp e pontoAtual � menor q a
										//distancia entre ultimoPonto e pAtual
										distUltimo = Math.sqrt((pontoAtual.getX() - ultimoPonto.getX())*(pontoAtual.getX() - ultimoPonto.getX()) + (pontoAtual.getY() - ultimoPonto.getY())*(pontoAtual.getY() - ultimoPonto.getY()) + (pontoAtual.getZ() - ultimoPonto.getZ())*(pontoAtual.getZ() - ultimoPonto.getZ()));
										distTmp = Math.sqrt((pontoAtual.getX() - pTmp.getX())*(pontoAtual.getX() - pTmp.getX()) + (pontoAtual.getY() - pTmp.getY())*(pontoAtual.getY() - pTmp.getY()) + (pontoAtual.getZ() - pTmp.getZ())*(pontoAtual.getZ() - pTmp.getZ()));
										if(distTmp < distUltimo){
											ultimoPonto = pTmp;
										}
									}
								}

								//JOptionPane.showMessageDialog(null, ws.getPontos()[0].toString() + " " + ws.getPontos()[1] + " |||| " + pontoAtual + " " + ultimoPonto + "\n" + distUltimo + " " + distTmp);

								movimentacao.add(ultimoPonto); //Vai para o ponto inicial
								pontoAtual = (Ponto)movimentacao.lastElement();
								//System.out.println("Volta para a posicao inicial: " + pontoAtual.toString());
								raioAtual = 0;
							}
						}

						//JOptionPane.showMessageDialog(null, "Pause");
					}
					else
					{
						terminouZ = true;
					}
				} // final do While em Z
				//pontoAtual = new Ponto(ws.getPontos()[pointIndex].getX(), ws.getPontos()[pointIndex].getY(), dZ);
				pontoAtual = (Ponto)movimentacao.lastElement();
				Ponto ultimo = new Ponto(pontoAtual.getX(), pontoAtual.getY(), dZ);
				//					movimentacao.add(ultimo);
				cavidade.setRaio(raio);
				saida.add(movimentacao);
				
				System.out.println("RAIIIIIIOOOOOOOOO FINNNAAAAAAAALLLLLLLL : " + cavidade.getRaio());
			}
			break;
		default:
			break;
		}
		return saida;
	}
	/**
	 * @param ws -> working Step
	 * @return -> array de limites: [0]-> esquerda / [1]-> direita / [2]-> superior / [3]-> inferior
	 */
	public static double [] calcularLimites(Workingstep ws)
	{
		Feature f = ws.getFeature();
		double [] limites = null;
		boolean acabamento = false;
		switch(f.getTipo())
		{
		case Feature.DEGRAU:
			Degrau d = (Degrau)f;
			limites = new double[4];
			if(acabamento)
			{

			}
			else
			{
				if (d.getEixo() == Degrau.HORIZONTAL)
				{	
					if(d.getPosicaoY() == 0)
					{
						limites[0] = d.getPosicaoX();
						limites[1] = ws.getFace().getComprimento();
						limites[2] = d.getPosicaoY() + d.getLargura() - ws.getFerramenta().getDiametroFerramenta() / 2;
						limites[3] = d.getPosicaoY();
					}
					else
					{
						limites[0] = d.getPosicaoX();
						limites[1] = ws.getFace().getComprimento();
						limites[2] = d.getPosicaoY() + d.getLargura();
						limites[3] = d.getPosicaoY() + ws.getFerramenta().getDiametroFerramenta() / 2;
					}
				}
				else
				{
					if (d.getPosicaoX() == 0)
					{
						limites[0] = d.getPosicaoX();
						limites[1] = d.getPosicaoX() + d.getLargura() - ws.getFerramenta().getDiametroFerramenta() / 2;
						limites[2] = ws.getFace().getLargura();
						limites[3] = d.getPosicaoY();
					}
					else
					{
						limites[0] = d.getPosicaoX() + ws.getFerramenta().getDiametroFerramenta() / 2;
						limites[1] = ws.getFace().getComprimento();
						limites[2] = ws.getFace().getLargura();
						limites[3] = d.getPosicaoY();
					}
				}
			}
			break;
		case Feature.RANHURA:
			Ranhura r = (Ranhura)f;
			limites = new double[4];
			if(acabamento)
			{
				// implementar movimentacao de acabamento
			}
			else
			{
				if (r.getEixo() == Ranhura.HORIZONTAL)
				{
					limites[0] = r.getPosicaoX();
					limites[1] = ws.getFace().getComprimento();
					limites[2] = r.getPosicaoY() + r.getLargura() - ws.getFerramenta().getDiametroFerramenta() / 2;
					limites[3] = r.getPosicaoY() + ws.getFerramenta().getDiametroFerramenta() / 2;
				}
				else
				{
					limites[0] = r.getPosicaoX() + ws.getFerramenta().getDiametroFerramenta() / 2;
					limites[1] = r.getPosicaoX() + r.getLargura() - ws.getFerramenta().getDiametroFerramenta() / 2;
					limites[2] = ws.getFace().getLargura();
					limites[3] = r.getPosicaoY();
				}
			}
			break;
		default:
			break;
		}
		return limites;
	}
	public static int determinarNumeroPontossssss(double raio, double diametroFerramenta)
	{
		int saida = 0;

		if (raio == 0) //Nao deve interpolar ponto nenhum, pois jah esta no centro!!!!!
		{
			saida = 0;
		}
		else
		{
			saida = (int)Math.round((2 * Math.PI * raio)/(diametroFerramenta / 4));
		}
		return saida;
	}
	public static Ponto[] determinarPontosCircunferencia(double zAtual, int numeroDePontos, double raio)
	{
		double alfa;
		double x, y;
		Ponto[] saida = null;
		if (numeroDePontos == 0)
		{
			saida = null;
		}
		else
		{
			saida = new Ponto[numeroDePontos + 1];
			alfa = (2 * Math.PI )/ numeroDePontos;
			for (int i = 0; i < numeroDePontos + 1; i++)
			{
				x = raio * Math.cos(i * alfa);
				y = raio * Math.sin(i * alfa);
				saida[i] = new Ponto(x, y, zAtual);
			}
		}
		return saida;
	}
	/**
	 * @param ws -> workinstep
	 * @param zAtual -> posicao Z atual de ponto
	 * @param raio -> "raio Total"
	 * @param numeroDePontos -> Total numero de pontos de uma circunferencia a interpolar
	 * @param modo -> se comeca do ponto ws.getPontos[0] -> true   ou se comeca dese [1] -> false
	 * @return -> Array com os pontos da cavidade
	 */
	public static Ponto[] determinarPontosRetanguloCavidade(Workingstep ws, double zAtual, double raio, boolean modo)
	{
		Ponto[] saida = null;
		double x, y, z;
		Feature f = ws.getFeature();
		Cavidade cavidade = (Cavidade)f;

		// ==========================================================
		double diagonalTotal, diagonal;
		if(cavidade.getComprimento() > cavidade.getLargura())
		{
			diagonal = (cavidade.getLargura() / 2 - cavidade.getRaio()) * Math.sqrt(2);
			diagonalTotal = diagonal + cavidade.getRaio();
		}
		else
		{
			diagonal = (cavidade.getComprimento() / 2 - cavidade.getRaio()) * Math.sqrt(2);
			diagonalTotal = diagonal + cavidade.getRaio();
		}
		//if (raio <= diagonal)
		//{
		saida = new Ponto[5];
		if (modo)
		{
			x = ws.getPontos()[1].getX() + raio / Math.sqrt(2);
			y = ws.getPontos()[1].getY() + raio / Math.sqrt(2);
			z = zAtual;

			saida[0] = new Ponto(x, y, z);
			x = ws.getPontos()[0].getX() - raio / Math.sqrt(2);
			saida[1] = new Ponto(x, y, z);
			y = ws.getPontos()[0].getY() - raio / Math.sqrt(2);
			saida[2] = new Ponto(x, y, z);
			x = ws.getPontos()[1].getX() + raio / Math.sqrt(2);
			saida[3] = new Ponto(x, y, z);
			saida[4] = saida[0];
		}
		else
		{
			x = ws.getPontos()[0].getX() - raio / Math.sqrt(2);
			y = ws.getPontos()[0].getY() - raio / Math.sqrt(2);
			z = zAtual;
			saida[0] = new Ponto(x, y, z);
			x = ws.getPontos()[1].getX() + raio / Math.sqrt(2);
			saida[1] = new Ponto(x, y, z);
			y = ws.getPontos()[1].getY() + raio / Math.sqrt(2);
			saida[2] = new Ponto(x, y, z);
			x = ws.getPontos()[0].getX() - raio / Math.sqrt(2);
			saida[3] = new Ponto(x, y, z);
			saida[4] = saida[0];
		}
		//}
	return saida;
	}
	/**
	 * @param angulo -> angulo do arco da circunferencia
	 * @param diametroFerramenta -> diametro da ferramenta
	 * @param raio -> raio para a interpolacao
	 * @return numero de pontos necessarios
	 */
	public static int determinarNumeroPontos(double angulo, double diametroFerramenta, double raio)
	{
		int numeroDePontos;
		numeroDePontos = (int)Math.round((angulo * raio) / (diametroFerramenta / 4));
		if(numeroDePontos == 0)
		{
			numeroDePontos = 1;
		}
		return numeroDePontos;
	}
	/**
	 * @param anguloInicial -> angulo de onde se inicia a interpolacao
	 * @param deltaAngulo -> angulo percorrido
	 * @param raio -> raio de interpolacao do arco circular
	 * @param numeroDePontos -> numero de pontos de interoplacao
	 * @param zAtual -> posicao Z atual
	 * @return -> Array de pontos de interpolacao de um arco circular
	 */
	public static Ponto[] determinarPontosEmCircunferencia(double anguloInicial, double deltaAngulo, double raio, int numeroDePontos, double zAtual)
	{
		Ponto[] saida = new Ponto[numeroDePontos + 1];
		double x, y, dAngulo = 0;

		dAngulo = deltaAngulo / numeroDePontos;
		for(int i = 0; i < numeroDePontos + 1; i++)
		{
			x = raio * Math.cos(anguloInicial + i * dAngulo);
			y = raio * Math.sin(anguloInicial + i * dAngulo);
			saida[i] = new Ponto(x, y, zAtual);
		}

		return saida;
	}
	public String imprimirCodigoHPGL(Vector movimentacao)
	{

		return "";
	}
}
