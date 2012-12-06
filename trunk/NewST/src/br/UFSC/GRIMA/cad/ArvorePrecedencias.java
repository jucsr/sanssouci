package br.UFSC.GRIMA.cad;

import java.awt.Container;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.Degrau;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.FuroBaseArredondada;
import br.UFSC.GRIMA.entidades.features.FuroBaseConica;
import br.UFSC.GRIMA.entidades.features.FuroBaseEsferica;
import br.UFSC.GRIMA.entidades.features.FuroBasePlana;
import br.UFSC.GRIMA.entidades.features.FuroConico;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.entidades.features.GeneralProfileBoss;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilBezier;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilCircularParcial;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilQuadradoU;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilRoundedU;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilVee;
import br.UFSC.GRIMA.entidades.features.RectangularBoss;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class ArvorePrecedencias 
{
	private static Projeto projeto;
	private static Bloco bloco;

	public ArvorePrecedencias(Projeto projeto)
	{
		this.projeto = projeto;
		this.bloco = this.projeto.getBloco();
	}
//	private static void temFilhoNode(Feature feature, DefaultMutableTreeNode nodoFeatureTmp)
//	{
//		if(feature.itsSons.size() > 0)
//		{
//			for(int i=0; i < feature.itsSons.size(); i++)
//			{
//				Feature featureSon = (Feature)feature.itsSons.get(i);
//				DefaultMutableTreeNode nodoFeatureSon = getRootFeature(featureSon);
//				nodoFeatureTmp.add(nodoFeatureSon);
//				
//				if(featureSon.itsSons.size() > 0)
//				{
//					
//					System.out.println("filhas "+i +": "+featureSon.itsSons.get(i));
//					System.out.println("tamanho itsSon: "+featureSon.itsSons.size());
//					for(int j=0; j < featureSon.itsSons.size(); j++)
//					temFilhoNode(featureSon.itsSons.get(j), nodoFeatureSon);
//				}
//				
//			}
//		}
//	}
//	private static DefaultMutableTreeNode getNode(Projeto projeto)
//	{
//		
// 		bloco = projeto.getBloco();
//		DefaultMutableTreeNode node = new DefaultMutableTreeNode("Precedences: ");
//		for(int i = 0; i < bloco.getFaces().size(); i++)
//		{
//			DefaultMutableTreeNode nodeFaceTmp = new DefaultMutableTreeNode(((Face)bloco.getFaces().elementAt(i)).getStringFace(i));
//			Face faceTmp = (Face)bloco.getFaces().elementAt(i);
//			
//			
//				for(int j = 0; j < faceTmp.features.size(); j++)
//				{
//					Feature featureTmp = (Feature)faceTmp.features.elementAt(j);
////					System.out.println("precedente --> " + featureTmp.getClass() + "\t" + featureTmp.getFeaturePrecedente());
////					System.out.println("class --> " + featureTmp.getClass());
//					if(featureTmp.getFeaturePrecedente() == null)
//					{
//						DefaultMutableTreeNode nodoFeatureTmp = getRootFeature(featureTmp);
//						nodeFaceTmp.add(nodoFeatureTmp);
//						
//						/*
//						 * Pesquisar se tem filhas
//						 */
//						if(featureTmp.itsSons.size() > 0)
//						{
//							temFilhoNode(featureTmp, nodoFeatureTmp.);
//						}
//
//					}
//				}
//
//			node.add(nodeFaceTmp);
//			}
//	return node;
//	}
	private static void temFilhoNode(Feature featureTmp, DefaultMutableTreeNode nodeFeatureTmp)
	{
			for(int i=0; i < featureTmp.itsSons.size(); i++)
			{
				Feature featureSon = (Feature)featureTmp.itsSons.get(i);
				
					DefaultMutableTreeNode nodeFeatureSon = getRootFeature(featureSon);
					nodeFeatureTmp.add(nodeFeatureSon);
					
						for(int j=0; j < featureSon.itsSons.size(); j++)
						{
							Feature featureSon2 = (Feature)featureSon.itsSons.get(j);
//							System.out.println("classFilho --> " + featureSon2.getClass());

							DefaultMutableTreeNode nodeFeatureSon2 = getRootFeature(featureSon2);
							nodeFeatureSon.add(nodeFeatureSon2);

							temFilhoNode(featureSon.itsSons.get(j), nodeFeatureSon2);
						}
			}
	}
	private static DefaultMutableTreeNode getNode(Projeto projeto)
	{
 		bloco = projeto.getBloco();
		DefaultMutableTreeNode node = new DefaultMutableTreeNode("Precedences: ");
		for(int i = 0; i < bloco.getFaces().size(); i++)
		{
			DefaultMutableTreeNode nodeFaceTmp = new DefaultMutableTreeNode(((Face)bloco.getFaces().elementAt(i)).getStringFace(i));
			Face faceTmp = (Face)bloco.getFaces().elementAt(i);
			
				for(int j = 0; j < faceTmp.features.size(); j++)
				{
					Feature featureTmp = (Feature)faceTmp.features.elementAt(j);
//					System.out.println("precedente --> " + featureTmp.getClass() + "\t" + featureTmp.getFeaturePrecedente());
//					System.out.println("class --> " + featureTmp.getClass());
					if(featureTmp.getFeaturePrecedente() == null)
					{
						DefaultMutableTreeNode nodoFeatureTmp = getRootFeature(featureTmp);
						nodeFaceTmp.add(nodoFeatureTmp);
						/*
						 * Pesquisar se tem filhas
						 */
						if(featureTmp.itsSons.size() > 0)
						{
							for(int k=0; k < featureTmp.itsSons.size(); k++)
							{
								Feature featureSon = featureTmp.itsSons.get(k);
								if((featureSon.getClass() == CircularBoss.class) ||
									(featureSon.getClass() == RectangularBoss.class) ||
								     featureSon.getClass() == GeneralProfileBoss.class)
								{
									DefaultMutableTreeNode nodoFilho = (DefaultMutableTreeNode) nodoFeatureTmp.getLastChild();
//									System.out.println("nodeFilho --> " + nodoFilho);
									temFilhoNode(featureSon, nodoFilho);
									
								}else
								{
									DefaultMutableTreeNode nodoFilho = getRootFeature(featureSon);
									nodoFeatureTmp.add(nodoFilho);
									temFilhoNode(featureSon, nodoFilho);
								}
								
							}
						}
					}
				}

			node.add(nodeFaceTmp);
			}
	return node;
	}
	public static JTree getArvorePrecedencias(Projeto projeto)
	{
		JTree arvore = new JTree(getNode(projeto));
		return arvore;
	}
	private static DefaultMutableTreeNode getRootFeature(Feature feature)
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		if(feature.getClass() == FuroBasePlana.class)
		{
			FuroBasePlana furo = (FuroBasePlana)feature;
			root = new DefaultMutableTreeNode("Furo -"	+ furo.getIndice());
			root.add(new DefaultMutableTreeNode("Nome: " + furo.getNome()));
			root.add(new DefaultMutableTreeNode("Raio = " + furo.getRaio()));
			root.add(new DefaultMutableTreeNode("Profundidade = " + furo.getProfundidade()));
			root.add(new DefaultMutableTreeNode("Posicao X, Y, Z = " + furo.getPosicaoX() + ", " + furo.getPosicaoY() + ", " + furo.getPosicaoZ()));
		
		} else if(feature.getClass() == FuroBaseArredondada.class)
		{
			FuroBaseArredondada fba = (FuroBaseArredondada)feature;
			root = new DefaultMutableTreeNode("FuroBaseArredondada -"	+ fba.getIndice());
			root.add(new DefaultMutableTreeNode("Nome: " + fba.getNome()));
			root.add(new DefaultMutableTreeNode("Raio = " + fba.getRaio()));
			root.add(new DefaultMutableTreeNode("Profundidade = " + fba.getProfundidade()));
			root.add(new DefaultMutableTreeNode("Posicao X, Y, Z = " + fba.getPosicaoX() + ", " + fba.getPosicaoY() + ", " + fba.getPosicaoZ()));
			root.add(new DefaultMutableTreeNode("r1 = " + fba.getR1() + " mm"));
			
		}else if(feature.getClass() == FuroBaseConica.class)
		{
			FuroBaseConica fbc = (FuroBaseConica)feature;
			root = new DefaultMutableTreeNode("FuroBaseConica -"	+ fbc.getIndice());
			root.add(new DefaultMutableTreeNode("Nome: " + fbc.getNome()));
			root.add(new DefaultMutableTreeNode("Raio = " + fbc.getRaio()));
			root.add(new DefaultMutableTreeNode("Profundidade = " + fbc.getProfundidade()));
			root.add(new DefaultMutableTreeNode("Posicao X, Y, Z = " + fbc.getPosicaoX() + ", " + fbc.getPosicaoY() + ", " + fbc.getPosicaoZ()));
			root.add(new DefaultMutableTreeNode("Angle = " + fbc.getTipAngle() + "Â°"));
			
		}else if(feature.getClass() == FuroBaseEsferica.class)
		{
			FuroBaseEsferica fbe = (FuroBaseEsferica)feature;
			root = new DefaultMutableTreeNode("FuroBaseEsferica -"	+ fbe.getIndice());
			root.add(new DefaultMutableTreeNode("Nome: " + fbe.getNome()));
			root.add(new DefaultMutableTreeNode("Raio = " + fbe.getRaio()));
			root.add(new DefaultMutableTreeNode("Profundidade = " + fbe.getProfundidade()));
			root.add(new DefaultMutableTreeNode("Posicao X, Y, Z = " + fbe.getPosicaoX() + ", " + fbe.getPosicaoY() + ", " + fbe.getPosicaoZ()));
			
		}else if(feature.getClass() == FuroConico.class)
		{
			FuroConico fc = (FuroConico)feature;
			root = new DefaultMutableTreeNode("FuroConico -"	+ fc.getIndice());
			root.add(new DefaultMutableTreeNode("Nome: " + fc.getNome()));
			root.add(new DefaultMutableTreeNode("Raio = " + fc.getRaio()));
			root.add(new DefaultMutableTreeNode("Profundidade = " + fc.getProfundidade()));
			root.add(new DefaultMutableTreeNode("Posicao X, Y, Z = " + fc.getPosicaoX() + ", " + fc.getPosicaoY() + ", " + fc.getPosicaoZ()));
			root.add(new DefaultMutableTreeNode("r1 = " + fc.getRaio1() + " mm"));
			
		}else if(feature.getClass() == Degrau.class)
		{
			Degrau dg = (Degrau)feature;
			root = new DefaultMutableTreeNode("Degrau -"	+ dg.getIndice());
			root.add(new DefaultMutableTreeNode
					("Largura = " + dg.getLargura()));
			root.add(new DefaultMutableTreeNode
					("Profundidade = " +dg.getProfundidade()));
			root.add(new DefaultMutableTreeNode
					("prosiÃ§Ã£o Z = " + dg.getPosicaoZ()));
			root.add(new DefaultMutableTreeNode
					("Eixo : " + dg.getTipoStringEixo()));
			
//			DefaultMutableTreeNode bossNode = new DefaultMutableTreeNode("Its Boss:");
//			root.add(bossNode);
//			
//			for(int i = 0; i < dg.getItsBoss().size(); i++)
//			{
//				if(dg.getItsBoss().get(i).getClass() == CircularBoss.class)
//				{
//					CircularBoss circular = (CircularBoss)dg.getItsBoss().get(i);
//					bossNode.add(circular.getNodo());
//				} else if(dg.getItsBoss().get(i).getClass() == RectangularBoss.class)
//				{
//					RectangularBoss rectangular = (RectangularBoss)dg.getItsBoss().get(i);
//					bossNode.add(rectangular.getNodo());
//				} else if(dg.getItsBoss().get(i).getClass() == GeneralProfileBoss.class)
//				{
//					GeneralProfileBoss general = (GeneralProfileBoss)dg.getItsBoss().get(i);
//					bossNode.add(general.getNodo());
//				}
//			}
			
		}else if(feature.getClass() == Cavidade.class)
		{
			Cavidade cavidade = (Cavidade)feature;
			root = new DefaultMutableTreeNode("Closed Pocket -"	+ cavidade.getIndice());
			root.add(new DefaultMutableTreeNode("Name: "+ cavidade.getNome()));
			root.add(new DefaultMutableTreeNode("Length = "
					+ cavidade.getComprimento()));
			root.add(new DefaultMutableTreeNode("Largura = " + cavidade.getLargura()));
			root.add(new DefaultMutableTreeNode("Depth = "
					+ cavidade.getProfundidade()));
			root.add(new DefaultMutableTreeNode("Corner radius = " + cavidade.getRaio()));
			root.add(new DefaultMutableTreeNode("Posicao X, Y, Z = " + cavidade.getPosicaoX() + ", " + cavidade.getPosicaoY() + ", " + cavidade.getPosicaoZ()));

		
//			if(cavidade.getItsBoss().size() > 0)
//			{
//				DefaultMutableTreeNode bossNode = new DefaultMutableTreeNode("Its Boss:");
//				
//				for(int i = 0; i < cavidade.getItsBoss().size(); i++)
//				{
//					if(cavidade.getItsBoss().get(i).getClass() == CircularBoss.class)
//					{
//						CircularBoss circular = (CircularBoss)cavidade.getItsBoss().get(i);
//						bossNode.add(circular.getNodo());
//					} else if(cavidade.getItsBoss().get(i).getClass() == RectangularBoss.class)
//					{
//						RectangularBoss rectangular = (RectangularBoss)cavidade.getItsBoss().get(i);
//						bossNode.add(rectangular.getNodo());
//					} else if(cavidade.getItsBoss().get(i).getClass() == GeneralProfileBoss.class)
//					{
//						GeneralProfileBoss general = (GeneralProfileBoss)cavidade.getItsBoss().get(i);
//						bossNode.add(general.getNodo());
//					}
//				}
//				root.add(bossNode);
//			}
			if(cavidade.getItsBoss().size() > 0)
			{
			
				for(int i = 0; i < cavidade.getItsBoss().size(); i++)
				{
					if(cavidade.getItsBoss().get(i).getClass() == CircularBoss.class)
					{
						CircularBoss circular = (CircularBoss)cavidade.getItsBoss().get(i);
						root.add(circular.getNodo());
					} else if(cavidade.getItsBoss().get(i).getClass() == RectangularBoss.class)
					{
						RectangularBoss rectangular = (RectangularBoss)cavidade.getItsBoss().get(i);
						root.add(rectangular.getNodo());
					} else if(cavidade.getItsBoss().get(i).getClass() == GeneralProfileBoss.class)
					{
						GeneralProfileBoss general = (GeneralProfileBoss)cavidade.getItsBoss().get(i);
						root.add(general.getNodo());
					}
				}
				
			}
			
		}else if(feature.getClass() == GeneralClosedPocket.class)
		{
			GeneralClosedPocket gcp = (GeneralClosedPocket)feature;
			root = new DefaultMutableTreeNode("GeneralClosedPocket -"	+ gcp.getIndice());
			root.add(new DefaultMutableTreeNode("Nome: " + gcp.getNome()));
			root.add(new DefaultMutableTreeNode("Profundidade = " + gcp.getProfundidade()));
			root.add(new DefaultMutableTreeNode("pos Z = " + gcp.getPosicaoZ()));
			root.add(new DefaultMutableTreeNode("Radius = " + gcp.getRadius()));
			
			DefaultMutableTreeNode bossNode = new DefaultMutableTreeNode("Its Boss:");
			root.add(bossNode);
			
			for(int i = 0; i < gcp.getItsBoss().size(); i++)
			{
				if(gcp.getItsBoss().get(i).getClass() == CircularBoss.class)
				{
					CircularBoss circular = (CircularBoss)gcp.getItsBoss().get(i);
					bossNode.add(circular.getNodo());
				} else if(gcp.getItsBoss().get(i).getClass() == RectangularBoss.class)
				{
					RectangularBoss rectangular = (RectangularBoss)gcp.getItsBoss().get(i);
					bossNode.add(rectangular.getNodo());
				} else if(gcp.getItsBoss().get(i).getClass() == GeneralProfileBoss.class)
				{
					GeneralProfileBoss general = (GeneralProfileBoss)gcp.getItsBoss().get(i);
					bossNode.add(general.getNodo());
				}
			}
		}else if(feature.getClass() == Ranhura.class)
		{
			Ranhura ranhura = (Ranhura)feature;
			root = new DefaultMutableTreeNode("Ranhura -"	+ ranhura.getIndice());
			root.add(new DefaultMutableTreeNode("Nome: " + ranhura.getNome()));
			root.add(new DefaultMutableTreeNode("Largura = " + ranhura.getLargura()));
			root.add(new DefaultMutableTreeNode("Profundidade = " + ranhura.getProfundidade()));
			root.add(new DefaultMutableTreeNode("posição Z = " + ranhura.getPosicaoZ()));
			root.add(new DefaultMutableTreeNode("Deslocamento : " + ranhura.getDeslocamento()));
			root.add(new DefaultMutableTreeNode("Eixo : " + ranhura.getStringEixo()));
			
		}else if(feature.getClass() == RanhuraPerfilBezier.class)
		{
			RanhuraPerfilBezier rBezier = (RanhuraPerfilBezier)feature;
			root = new DefaultMutableTreeNode("RanhuraPerfilBezier -"	+ rBezier.getIndice());
			root.add(new DefaultMutableTreeNode("Nome: " + rBezier.getNome()));
			
		}else if(feature.getClass() == RanhuraPerfilCircularParcial.class)
		{
			RanhuraPerfilCircularParcial rcp = (RanhuraPerfilCircularParcial)feature;
			root = new DefaultMutableTreeNode("RanhuraPerfilCircularParcial -"	+ rcp.getIndice());
			root.add(new DefaultMutableTreeNode("Nome: " + rcp.getNome()));
			root.add(new DefaultMutableTreeNode("Raio = " + rcp.getRaio()));
			root.add(new DefaultMutableTreeNode("Angulo = " + rcp.getAngulo()));
			root.add(new DefaultMutableTreeNode("Dz = " + rcp.getDz()));
			
		}else if(feature.getClass() == RanhuraPerfilQuadradoU.class)
		{
			RanhuraPerfilQuadradoU rpq = (RanhuraPerfilQuadradoU)feature;
			root = new DefaultMutableTreeNode("RanhuraPerfilQuadradoU -"	+ rpq.getIndice());
			root.add(new DefaultMutableTreeNode("Nome: " + rpq.getNome()));
			root.add(new DefaultMutableTreeNode("Largura 1 = " + rpq.getLargura()));
			root.add(new DefaultMutableTreeNode("Largura 2 = " + rpq.getLargura2()));
			root.add(new DefaultMutableTreeNode("Ângulo = " + rpq.getAngulo()));
			root.add(new DefaultMutableTreeNode("raio = " + rpq.getRaio()));
			root.add(new DefaultMutableTreeNode("Profundidade = " + rpq.getProfundidade()));
			root.add(new DefaultMutableTreeNode("posição Z = " + rpq.getPosicaoZ()));
			root.add(new DefaultMutableTreeNode("Deslocamento : " + rpq.getDeslocamento()));
			root.add(new DefaultMutableTreeNode("Eixo : " + rpq.getStringEixo()));
			
		}else if(feature.getClass() == RanhuraPerfilRoundedU.class)
		{
			RanhuraPerfilRoundedU rru = (RanhuraPerfilRoundedU)feature;
			root = new DefaultMutableTreeNode("RanhuraPerfilRoundedU -"	+ rru.getIndice());
			root.add(new DefaultMutableTreeNode("Nome: " + rru.getNome()));
			
		}else if(feature.getClass() == RanhuraPerfilVee.class)
		{
			RanhuraPerfilVee rv = (RanhuraPerfilVee)feature;
			root = new DefaultMutableTreeNode("RanhuraPerfilVee -"	+ rv.getIndice());
			root.add(new DefaultMutableTreeNode("Nome: " + rv.getNome()));
			root.add(new DefaultMutableTreeNode("Nome: " + rv.getNome()));
			root.add(new DefaultMutableTreeNode("Largura = " + rv.getLargura()));
			root.add(new DefaultMutableTreeNode("Ã¢ngulo = " + rv.getAngulo()));
			root.add(new DefaultMutableTreeNode("raio = " + rv.getRaio()));
			root.add(new DefaultMutableTreeNode("Profundidade = " + rv.getProfundidade()));
			root.add(new DefaultMutableTreeNode("posiÃ§Ã£o Z = " + rv.getPosicaoZ()));
			root.add(new DefaultMutableTreeNode("Deslocamento : " + rv.getDeslocamento()));
			root.add(new DefaultMutableTreeNode("Eixo : " + rv.getStringEixo()));
		}
		return root;
	}
}
