package br.UFSC.GRIMA.capp.mapeadoras;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;

import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.InfoMovimentacao;
import br.UFSC.GRIMA.capp.OrdenadoraDeWorkingsteps;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.CavidadeFundoArredondado;
import br.UFSC.GRIMA.entidades.features.Degrau;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.Furo;
import br.UFSC.GRIMA.entidades.features.FuroBaseArredondada;
import br.UFSC.GRIMA.entidades.features.FuroBaseConica;
import br.UFSC.GRIMA.entidades.features.FuroBaseEsferica;
import br.UFSC.GRIMA.entidades.features.FuroBasePlana;
import br.UFSC.GRIMA.entidades.features.FuroConico;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilBezier;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilCircularParcial;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilQuadradoU;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilRoundedU;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilVee;
import br.UFSC.GRIMA.entidades.features.Region;
import br.UFSC.GRIMA.entidades.ferramentas.BallEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.BoringTool;
import br.UFSC.GRIMA.entidades.ferramentas.BullnoseEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.CenterDrill;
import br.UFSC.GRIMA.entidades.ferramentas.EndMill;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.entidades.ferramentas.Reamer;
import br.UFSC.GRIMA.entidades.ferramentas.TwistDrill;
import br.UFSC.GRIMA.util.Ponto;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class MapeadoraDeWorkingsteps {
	
	private Vector<Face> faces;// recebe
	private CondicoesDeUsinagem condicoesDeUsinagem;
	private Vector<Vector<Workingstep>> workingsteps;// saida - vetor de faces
														// com as workingsteps
	private Projeto projeto;
	
	public MapeadoraDeWorkingsteps() {

	}

	public MapeadoraDeWorkingsteps(Projeto projeto) {
		this.projeto = projeto;
		this.mapearWorkingsteps();
		

	}

	public MapeadoraDeWorkingsteps(Vector faces,
			CondicoesDeUsinagem condicoesDeUsinagem) {
		this.faces = faces;
		this.condicoesDeUsinagem = condicoesDeUsinagem;
		this.mapearWorkingsteps();
		this.imprimirDados();
	}

	public void mapearWorkingsteps() {
		
		this.workingsteps = new Vector<Vector<Workingstep>>();
		
		Bloco bloco = this.projeto.getBloco();

		// ---- Mapeia as WorkingSteps de cada Feature em cada Face ---
		for (int i = 0; i < bloco.faces.size(); i++) {
			
			Vector<Workingstep> wssCurrentFace = new Vector<Workingstep>();
			
			Face faceTmp = (Face) bloco.faces.elementAt(i);

			for (int j = 0; j < faceTmp.features.size(); j++) {
				
				Feature featureTmp = (Feature) faceTmp.features.elementAt(j);

				
				
				if (featureTmp.getClass() == FuroBasePlana.class) {

					MapeadoraFuroBasePlana mapeadoraFuroBasePlana = new MapeadoraFuroBasePlana(
							this.projeto, faceTmp, (FuroBasePlana) featureTmp);

					Vector<Workingstep> wssFuroBasePlana = featureTmp
							.getWorkingsteps();

//					System.out.println("Nome Feature: " + featureTmp.getNome());
//
//					if (featureTmp.getFeaturePrecedente() != null)
//						System.out.println("Nome Feature Precedente: "
//								+ featureTmp.getFeaturePrecedente().getNome());

					for (int k = 0; k < wssFuroBasePlana.size(); k++) {

//						System.out.println("\n==========================================\nWs atual : "
//								+ wssFuroBasePlana.get(k).getId());
//						
//						System.out.println("Ferramenta Ws atual : "
//								+ wssFuroBasePlana.get(k).getFerramenta());
//
//						if (wssFuroBasePlana.get(k).getWorkingstepPrecedente() != null) {
//							
//							System.out
//									.println("Ws Precedente : "
//											+ wssFuroBasePlana.get(k)
//													.getWorkingstepPrecedente()
//													.getId());
//							
//							System.out.println("Ferramenta Ws Precedente : "
//									+ wssFuroBasePlana.get(k)
//											.getWorkingstepPrecedente()
//											.getFerramenta() + "\n===============================================\n");
//						}

						wssCurrentFace.add(wssFuroBasePlana.get(k));
						
					}

				} else if (featureTmp.getClass() == FuroBaseConica.class) {

					MapeadoraFuroBaseConica mapeadoraFuroBaseConica = new MapeadoraFuroBaseConica(
							this.projeto, faceTmp, (FuroBaseConica) featureTmp);

					Vector<Workingstep> wssFuroBaseConica = featureTmp
							.getWorkingsteps();

					for (int k = 0; k < wssFuroBaseConica.size(); k++) {
						wssCurrentFace.add(wssFuroBaseConica.get(k));
					}

				} else if (featureTmp.getClass() == FuroBaseEsferica.class) {

					MapeadoraFuroBaseEsferica mapeadoraFuroBaseEsferica = new MapeadoraFuroBaseEsferica(
							this.projeto, faceTmp,
							(FuroBaseEsferica) featureTmp);

					Vector<Workingstep> wssFuroBaseEsferica = featureTmp
							.getWorkingsteps();

					for (int k = 0; k < wssFuroBaseEsferica.size(); k++) {
						wssCurrentFace.add(wssFuroBaseEsferica.get(k));
					}

				} else if (featureTmp.getClass() == FuroBaseArredondada.class) {

					MapeadoraFuroBaseArredondada mapeadoraFuroBaseArredondada = new MapeadoraFuroBaseArredondada(
							this.projeto, faceTmp,
							(FuroBaseArredondada) featureTmp);

					Vector<Workingstep> wssFuroBaseArredondada = featureTmp
							.getWorkingsteps();

					for (int k = 0; k < wssFuroBaseArredondada.size(); k++) {
						wssCurrentFace.add(wssFuroBaseArredondada.get(k));
					}

				} else if (featureTmp.getClass() == FuroConico.class) {

					MapeadoraFuroConico mapeadoraFuroConico = new MapeadoraFuroConico(
							this.projeto, faceTmp, (FuroConico) featureTmp);

					Vector<Workingstep> wssFuroConico = featureTmp
							.getWorkingsteps();

					for (int k = 0; k < wssFuroConico.size(); k++) {
						wssCurrentFace.add(wssFuroConico.get(k));
					}

				} else if (featureTmp.getClass() == Ranhura.class) {

					MapeadoraRanhura mapeadoraRanhura = new MapeadoraRanhura(
							this.projeto, faceTmp, (Ranhura) featureTmp);

					Vector<Workingstep> wssRanhura = featureTmp
							.getWorkingsteps();

					for (int k = 0; k < wssRanhura.size(); k++) {
						wssCurrentFace.add(wssRanhura.get(k));
					}

				} else if (featureTmp.getClass() == RanhuraPerfilQuadradoU.class) {

					MapeadoraRanhuraPerfilQuadradoU mapeadoraRanhuraPerfilQuadradoU = new MapeadoraRanhuraPerfilQuadradoU(
							this.projeto, faceTmp,
							(RanhuraPerfilQuadradoU) featureTmp);

					Vector<Workingstep> wssRanhura = featureTmp
							.getWorkingsteps();

					for (int k = 0; k < wssRanhura.size(); k++) {
						wssCurrentFace.add(wssRanhura.get(k));
					}

				} else if (featureTmp.getClass() == RanhuraPerfilCircularParcial.class) { //sem parede

					MapeadoraRanhuraPerfilCircularParcial mapeadoraRanhura = new MapeadoraRanhuraPerfilCircularParcial(
							this.projeto, faceTmp, (RanhuraPerfilCircularParcial) featureTmp);

					Vector<Workingstep> wssRanhura = featureTmp
							.getWorkingsteps();

					for (int k = 0; k < wssRanhura.size(); k++) {
						wssCurrentFace.add(wssRanhura.get(k));
					}

				} else if (featureTmp.getClass() == RanhuraPerfilRoundedU.class) { //com parede,

					MapeadoraRanhuraPerfilRoundedU mapeadoraRanhura = new MapeadoraRanhuraPerfilRoundedU(
							this.projeto, faceTmp, (RanhuraPerfilRoundedU) featureTmp);

					Vector<Workingstep> wssRanhura = featureTmp
							.getWorkingsteps();

					for (int k = 0; k < wssRanhura.size(); k++) {
						wssCurrentFace.add(wssRanhura.get(k));
					}
					
				} else if (featureTmp.getClass() == RanhuraPerfilVee.class) { 

					MapeadoraRanhuraPerfilVee mapeadoraRanhura = new MapeadoraRanhuraPerfilVee(
							this.projeto, faceTmp, (RanhuraPerfilVee) featureTmp);

					Vector<Workingstep> wssRanhura = featureTmp
							.getWorkingsteps();

					for (int k = 0; k < wssRanhura.size(); k++) {
						wssCurrentFace.add(wssRanhura.get(k));
					}
					
				} else if (featureTmp.getClass() == RanhuraPerfilBezier.class) { 
					
					MapeadoraRanhuraPerfilBezier mapeadoraRanhura = new MapeadoraRanhuraPerfilBezier(
							this.projeto, faceTmp, (RanhuraPerfilBezier) featureTmp);

					Vector<Workingstep> wssRanhura = featureTmp
							.getWorkingsteps();

					for (int k = 0; k < wssRanhura.size(); k++) {
						wssCurrentFace.add(wssRanhura.get(k));
					}

				} else if (featureTmp.getClass() == Cavidade.class) {

					MapeadoraCavidade mapeadoraCavidade = new MapeadoraCavidade(
							this.projeto, faceTmp, (Cavidade) featureTmp);

					Vector<Workingstep> wssCavidade = featureTmp
							.getWorkingsteps();

					for (int k = 0; k < wssCavidade.size(); k++) {
						wssCurrentFace.add(wssCavidade.get(k));
					}

				} else if (featureTmp.getClass() == GeneralClosedPocket.class) {

					MapeadoraGeneralClosedPocket1 mapeadoraGeneral = new MapeadoraGeneralClosedPocket1(
							this.projeto, faceTmp, (GeneralClosedPocket) featureTmp);

					Vector<Workingstep> wssGeneral = featureTmp
							.getWorkingsteps();

					for (int k = 0; k < wssGeneral.size(); k++) {
						wssCurrentFace.add(wssGeneral.get(k));
					}

				} else if (featureTmp.getClass() == CavidadeFundoArredondado.class) { //banheira

					MapeadoraCavidadeFundoArredondado mapeadoraCavidadeFundoArredondado = new MapeadoraCavidadeFundoArredondado(
							this.projeto, faceTmp, (CavidadeFundoArredondado) featureTmp);

					Vector<Workingstep> wssCavidade = featureTmp
							.getWorkingsteps();

					for (int k = 0; k < wssCavidade.size(); k++) {
						wssCurrentFace.add(wssCavidade.get(k));
					}

					
				} else if (featureTmp.getClass() == Degrau.class) {
					
					MapeadoraDegrau mapeadoraDegrau = new MapeadoraDegrau(
							this.projeto, faceTmp, (Degrau) featureTmp);

					Vector<Workingstep> wssDegrau = featureTmp
							.getWorkingsteps();

					for (int k = 0; k < wssDegrau.size(); k++) {
						wssCurrentFace.add(wssDegrau.get(k));
					}
					

				} else if(featureTmp.getClass() == Region.class)
				{
					MapeadoraRegion mapeadoraRegion = new MapeadoraRegion(projeto, faceTmp, (Region)featureTmp);
					
					Vector<Workingstep> wssRegion = featureTmp.getWorkingsteps();
					
					for(int k = 0; k < wssRegion.size(); k++)
					{
						wssCurrentFace.add(wssRegion.get(k));
					}
				}
				else {
					System.out.println("CLASSE FEATURE DESCONHECIDA, CLASSE: "
							+ featureTmp.getClass());
				}
			}
			
			this.workingsteps.add(wssCurrentFace);
			
			for (int n = 0; n < wssCurrentFace.size(); n++) {

				Workingstep wsTmp = wssCurrentFace.get(n);

				Ferramenta ferrTmp = wsTmp.getFerramenta();

				if (!alreadyUsed(ferrTmp)) {

					if (ferrTmp.getClass() == CenterDrill.class) {

						this.projeto.addCenterDrill((CenterDrill) ferrTmp);
						
						System.out.println("Wstp = " + wsTmp.getId());
					} else if (ferrTmp.getClass() == TwistDrill.class) {

						this.projeto.addTwistDrill((TwistDrill) ferrTmp);

						System.out.println("Wstp = " + wsTmp.getId());
						
					} else if (ferrTmp.getClass() == FaceMill.class) {

						this.projeto.addFaceMill((FaceMill) ferrTmp);
						System.out.println("Wstp = " + wsTmp.getId());
					} else if (ferrTmp.getClass() == EndMill.class) {

						this.projeto.addEndMill((EndMill) ferrTmp);
						System.out.println("Wstp = " + wsTmp.getId());
					} else if (ferrTmp.getClass() == BallEndMill.class) {

						this.projeto.addBallEndMill((BallEndMill) ferrTmp);
						System.out.println("Wstp = " + wsTmp.getId());
					} else if (ferrTmp.getClass() == BullnoseEndMill.class) {

						this.projeto.addBullnoseEndMill((BullnoseEndMill) ferrTmp);
						System.out.println("Wstp = " + wsTmp.getId());
					} else if (ferrTmp.getClass() == BoringTool.class) {

						this.projeto.addBoringTool((BoringTool) ferrTmp);
						System.out.println("Wstp = " + wsTmp.getId());
					} else if (ferrTmp.getClass() == Reamer.class) {

						this.projeto.addReamer((Reamer) ferrTmp);
						System.out.println("Wstp = " + wsTmp.getId());
					} else {

						System.out
								.println("Ferramenta Desconhecida!!!  Classe = "
										+ ferrTmp.getClass());

					}
				}

			}
		}
		
		this.workingsteps = OrdenadoraDeWorkingsteps.ordenarWorkingSteps(this.workingsteps);
		
		this.projeto.setWorkingsteps(this.workingsteps);
	}

	private boolean alreadyUsed(Ferramenta ferrTmp) {
	
		String nomeF = ferrTmp.getName();
		int materialClass = ferrTmp.getMaterialClasse();
		
		this.projeto.setAllToolss();
		
		ArrayList<ArrayList> allTools = this.projeto.getAllTools();
		
		for(int i = 0; i< allTools.size(); i++){
			
			for(int j = 0; j< allTools.get(i).size(); j++){
				
				Ferramenta fTmp = (Ferramenta) allTools.get(i).get(j);
				
				if(nomeF.equals(fTmp.getName()) & materialClass == fTmp.getMaterialClasse() ){
					
					System.out.println("ENTROU!");
					
					return true;
				}
				
			}
		}
		
		System.out.println("NAO ENTROU!");
		
		return false;
	}

	public static CondicoesDeUsinagem getCondicoesDeUsinagem(Projeto projeto,
			Ferramenta ferr, Material materialBloco) {

		CondicoesDeUsinagem cond = null;

		double vc = 0, f = 0, vf = 0, n = 0, ap = 0, ae = 0;

		Statement statement;
		String Query = "";
		ResultSet rs = null;

		String iso = ferr.getMaterial();
		System.out.println("MAT = " + iso);
		int mat = materialBloco.getCategory();
		double diametro = ferr.getDiametroFerramenta();

		if (ferr.getClass() == CenterDrill.class) {

			Query = "SELECT * FROM Condicoes_De_Usinagem_CenterDrill WHERE ISO LIKE '"
					+ iso + "' AND Material_Bloco LIKE " + mat + ";";

		} else if (ferr.getClass() == TwistDrill.class) {

			Query = "SELECT * FROM Condicoes_De_Usinagem_TwistDrill WHERE ISO LIKE '"
					+ iso + "' AND Material_Bloco LIKE " + mat + ";";

		} else if (ferr.getClass() == FaceMill.class) {

			Query = "SELECT * FROM Condicoes_De_Usinagem_FaceMill WHERE ISO LIKE '"
					+ iso + "' AND Material_Bloco LIKE " + mat + ";";

		} else if (ferr.getClass() == EndMill.class) {

			Query = "SELECT * FROM Condicoes_De_Usinagem_EndMill WHERE ISO LIKE '"
					+ iso + "' AND Material_Bloco LIKE " + mat + ";";

		} else if (ferr.getClass() == BallEndMill.class) {

			Query = "SELECT * FROM Condicoes_De_Usinagem_BallEndMill WHERE ISO LIKE '"
					+ iso + "' AND Material_Bloco LIKE " + mat + ";";

		} else if (ferr.getClass() == BullnoseEndMill.class) {

			Query = "SELECT * FROM Condicoes_De_Usinagem_BullnoseEndMill WHERE ISO LIKE '"
					+ iso + "' AND Material_Bloco LIKE " + mat + ";";

		} else if (ferr.getClass() == Reamer.class) {

			Query = "SELECT * FROM Condicoes_De_Usinagem_Reamer WHERE ISO LIKE '"
					+ iso + "' AND Material_Bloco LIKE " + mat + ";";

		} else if (ferr.getClass() == BoringTool.class) {

			Query = "SELECT * FROM Condicoes_De_Usinagem_BoringTool WHERE ISO LIKE '"
					+ iso + "' AND Material_Bloco LIKE " + mat + ";";

		} else {
			JOptionPane
					.showMessageDialog(null,
							"Classe de Ferramenta não encontrada! = "
									+ ferr.getClass(), "Erro",
							JOptionPane.ERROR_MESSAGE);
		}

		if (ferr.getClass() == TwistDrill.class) {

			try {

				statement = projeto.getStatement();
				rs = statement.executeQuery(Query);
				rs.next();

				vc = rs.getDouble("vc");

				if (diametro >= 3.00 && diametro <= 6.00)
					f = rs.getDouble("f (3.00 < Dc < 6.00)");
				else if (diametro > 6.00 && diametro <= 10.00)
					f = rs.getDouble("f (6.01 < Dc < 10.00)");
				else if (diametro > 10.00 && diametro <= 14.00)
					f = rs.getDouble("f (10.01 < Dc < 14.00)");
				else if (diametro > 14.00 && diametro <= 20.00)
					f = rs.getDouble("f (14.01 < Dc < 20.00)");
				else if (diametro <= 0.0) {
					JOptionPane
							.showMessageDialog(
									null,
									"O diametro deve ser um número Positivo Real e Maior que Zero! \nDiametro Atual = "
											+ diametro,
									"Diametro Desconhecido!",
									JOptionPane.ERROR_MESSAGE);

					throw new NumberFormatException(
							"Diametro nao pode ser negativo ou nulo!");
				} else {
					JOptionPane
							.showMessageDialog(
									null,
									"Diametro da ferramenta encontra-se fora dos limites tabelados (3.00<= Dc <=20.00).\nDiametro Atual = "
											+ diametro
											+ "\nSerá feita uma aproximação!",
									"Diametro não Tabelado",
									JOptionPane.WARNING_MESSAGE);
					if (0 < diametro && diametro < 3.00)
						f = rs.getDouble("f (3.00 < Dc < 6.00)");
					else if (diametro > 20.00)
						f = rs.getDouble("f (14.01 < Dc < 20.00)");
				}

			} catch (SQLException e) {

				if (e.getMessage().equals(
						"Illegal operation on empty result set.")) {
					JOptionPane.showMessageDialog(null,
							"Nao e possivel usinar "
									+ Material.getTypeMaterial(materialBloco
											.getCategory())
									+ " com Ferramenta da classe "
									+ ferr.getMaterial() + " !",
							"Incompatibilidade de Materiais ",
							JOptionPane.WARNING_MESSAGE);
				} else {

					JOptionPane
							.showMessageDialog(
									null,
									"Erro ao acessar o banco de dados do sistema.\nTente novamente mais tarde.",
									"Erro", JOptionPane.ERROR_MESSAGE);

					e.printStackTrace();

				}
			}

			n = vc * 1000 / (Math.PI * diametro);
			vf = f * n;
			ap = 0;
			ae = 0;

			cond = new CondicoesDeUsinagem(vc, f, vf, n, ap, ae);

		} else if (ferr.getClass() == CenterDrill.class) {

			try {

				statement = projeto.getStatement();
				rs = statement.executeQuery(Query);
				rs.next();

				vc = rs.getDouble("vc");

				if (diametro >= 1.00 && diametro <= 3.00)
					f = rs.getDouble("f (1.00 < Dc < 3.00)");
				else if (diametro > 3.00 && diametro <= 6.00)
					f = rs.getDouble("f (3.01 < Dc < 6.00)");
				else if (diametro > 6.00 && diametro <= 10.00)
					f = rs.getDouble("f (6.01 < Dc < 10.00)");
				else if (diametro <= 0.0) {
					JOptionPane
							.showMessageDialog(
									null,
									"O diametro deve ser um número Positivo Real e Maior que Zero! \nDiametro Atual = "
											+ diametro,
									"Diametro Desconhecido!",
									JOptionPane.ERROR_MESSAGE);

					throw new NumberFormatException(
							"Diametro nao pode ser negativo ou nulo!");
				} else {
					JOptionPane
							.showMessageDialog(
									null,
									"Diametro da ferramenta encontra-se fora dos limites tabelados (1.00<= Dc <=10.00).\nDiametro Atual = "
											+ diametro
											+ "\nSerá feita uma aproximação!",
									"Diametro não Tabelado",
									JOptionPane.WARNING_MESSAGE);
					if (0 < diametro && diametro < 1.00)
						f = rs.getDouble("f (1.00 < Dc < 3.00)");
					else if (diametro > 10.00)
						f = rs.getDouble("f (6.01 < Dc < 10.00)");
				}

			} catch (SQLException e) {

				if (e.getMessage().equals(
						"Illegal operation on empty result set.")) {
					JOptionPane.showMessageDialog(null,
							"Nao e possivel usinar "
									+ Material.getTypeMaterial(materialBloco
											.getCategory())
									+ " com Ferramenta da classe "
									+ ferr.getMaterial() + " !",
							"Incompatibilidade de Materiais ",
							JOptionPane.WARNING_MESSAGE);
				} else {

					JOptionPane
							.showMessageDialog(
									null,
									"Erro ao acessar o banco de dados do sistema.\nTente novamente mais tarde.",
									"Erro", JOptionPane.ERROR_MESSAGE);

					e.printStackTrace();

				}
			}

			n = vc * 1000 / (Math.PI * diametro);
			vf = f * n;
			ap = 0;
			ae = 0;

			cond = new CondicoesDeUsinagem(vc, f, vf, n, ap, ae);

		} else if (ferr.getClass() == FaceMill.class
				|| ferr.getClass() == EndMill.class
				|| ferr.getClass() == BallEndMill.class
				|| ferr.getClass() == BullnoseEndMill.class) {

			try {

				statement = projeto.getStatement();
				rs = statement.executeQuery(Query);
				rs.next();

				vc = rs.getDouble("vc");

				if (diametro >= 2.00 && diametro <= 4.00)
					f = rs.getDouble("f (2.00 < Dc < 4.00)");
				else if (diametro > 4.00 && diametro <= 6.00)
					f = rs.getDouble("f (4.01 < Dc < 6.00)");
				else if (diametro > 6.00 && diametro <= 8.00)
					f = rs.getDouble("f (6.01 < Dc < 8.00)");
				else if (diametro > 8.00 && diametro <= 10.00)
					f = rs.getDouble("f (8.01 < Dc < 10.00)");
				else if (diametro > 10.00 && diametro <= 12.00)
					f = rs.getDouble("f (10.01 < Dc < 12.00)");
				else if (diametro > 12.00 && diametro <= 14.00)
					f = rs.getDouble("f (12.01 < Dc < 14.00)");
				else if (diametro > 14.00 && diametro <= 16.00)
					f = rs.getDouble("f (14.01 < Dc < 16.00)");
				else if (diametro > 16.00 && diametro <= 18.00)
					f = rs.getDouble("f (16.01 < Dc < 18.00)");
				else if (diametro > 18.00 && diametro <= 20.00)
					f = rs.getDouble("f (18.01 < Dc < 20.00)");
				else if (diametro <= 0.0) {
					JOptionPane
							.showMessageDialog(
									null,
									"O diametro deve ser um número Positivo Real e Maior que Zero! \nDiametro Atual = "
											+ diametro,
									"Diametro Desconhecido!",
									JOptionPane.ERROR_MESSAGE);

					throw new NumberFormatException(
							"Diametro nao pode ser negativo ou nulo!");
				} else {
					JOptionPane
							.showMessageDialog(
									null,
									"Diametro da ferramenta encontra-se fora dos limites tabelados (2.00<= Dc <=20.00).\nDiametro Atual = "
											+ diametro
											+ "\nSerá feita uma aproximação!",
									"Diametro não Tabelado",
									JOptionPane.WARNING_MESSAGE);
					if (0 < diametro && diametro < 2.00)
						f = rs.getDouble("f (2.00 < Dc < 4.00)");
					else if (diametro > 20.00)
						f = rs.getDouble("f (18.01 < Dc < 20.00)");
				}

			} catch (SQLException e) {

				if (e.getMessage().equals(
						"Illegal operation on empty result set.")) {
					JOptionPane.showMessageDialog(null,
							"Nao e possivel usinar "
									+ Material.getTypeMaterial(materialBloco
											.getCategory())
									+ " com Ferramenta da classe "
									+ ferr.getMaterial() + " !",
							"Incompatibilidade de Materiais ",
							JOptionPane.WARNING_MESSAGE);
				} else {

					JOptionPane
							.showMessageDialog(
									null,
									"Erro ao acessar o banco de dados do sistema.\nTente novamente mais tarde.",
									"Erro", JOptionPane.ERROR_MESSAGE);

					e.printStackTrace();

				}
			}
			if(materialBloco.getCategory() == Material.ACO_ALTA_LIGA||
					materialBloco.getCategory() == Material.ACO_ALTO_CARBONO||
					materialBloco.getCategory() == Material.ACO_BAIXA_LIGA||
				    materialBloco.getCategory()==Material.ACO_EXTRA_DURO||
				    materialBloco.getCategory()==Material.ACO_INOX_AUST||
				    materialBloco.getCategory()==Material.ACO_INOX_AUST_FUND||
				    materialBloco.getCategory()==Material.ACO_SEM_LIGA||
				    materialBloco.getCategory()==Material.FERRO_FUNDIDO_CINZENTO||
				    materialBloco.getCategory()==Material.FERRO_FUNDIDO_MALEAVEL||
				    materialBloco.getCategory()==Material.FERRO_FUNDIDO_NODULAR){
				 
				ap = 0.10*diametro;
			
			}else if(materialBloco.getCategory() == Material.LIGA_ALUMINIO||
					materialBloco.getCategory()==Material.LIGA_COBRE){
				
				ap = 0.20*diametro;
			}else if(materialBloco.getCategory() == Material.LIGA_TITANIO||
					materialBloco.getCategory()==Material.SUPER_LIGA_NI){
				
				ap = 0.05*diametro;
			}
			
			n = vc * 1000 / (Math.PI * diametro);
			vf = f * n;
//			ap = 2;
			ae = 0.75 * ferr.getDiametroFerramenta();

			if (ferr.getClass() == BallEndMill.class
					|| ferr.getClass() == BullnoseEndMill.class) {
				ap = 0.05 * ferr.getDiametroFerramenta();
				ae = ap;
			}

			cond = new CondicoesDeUsinagem(vc, f, vf, n, ap, ae);

		} else if (ferr.getClass() == Reamer.class) {

			try {

				statement = projeto.getStatement();
				rs = statement.executeQuery(Query);
				rs.next();

				vc = rs.getDouble("vc");

				if (diametro >= 3.00 && diametro <= 6.00)
					f = rs.getDouble("f (3.00 < Dc < 6.00)");
				else if (diametro > 6.00 && diametro <= 10.00)
					f = rs.getDouble("f (6.01 < Dc < 10.00)");
				else if (diametro > 10.00 && diametro <= 14.00)
					f = rs.getDouble("f (10.01 < Dc < 14.00)");
				else if (diametro <= 0.0) {
					JOptionPane
							.showMessageDialog(
									null,
									"O diametro deve ser um número Positivo Real e Maior que Zero! \nDiametro Atual = "
											+ diametro,
									"Diametro Desconhecido!",
									JOptionPane.ERROR_MESSAGE);

					throw new NumberFormatException(
							"Diametro nao pode ser negativo ou nulo!");
				} else {
					JOptionPane
							.showMessageDialog(
									null,
									"Diametro da ferramenta encontra-se fora dos limites tabelados (3.00<= Dc <=14.00).\nDiametro Atual = "
											+ diametro
											+ "\nSerá feita uma aproximação!",
									"Diametro não Tabelado",
									JOptionPane.WARNING_MESSAGE);
					if (0 < diametro && diametro < 3.00)
						f = rs.getDouble("f (3.00 < Dc < 6.00)");
					else if (diametro > 20.00)
						f = rs.getDouble("f (10.01 < Dc < 14.00)");
				}

			} catch (SQLException e) {

				if (e.getMessage().equals(
						"Illegal operation on empty result set.")) {
					JOptionPane.showMessageDialog(null,
							"Nao e possivel usinar "
									+ Material.getTypeMaterial(materialBloco
											.getCategory())
									+ " com Ferramenta da classe "
									+ ferr.getMaterial() + " !",
							"Incompatibilidade de Materiais ",
							JOptionPane.WARNING_MESSAGE);
				} else {

					JOptionPane
							.showMessageDialog(
									null,
									"Erro ao acessar o banco de dados do sistema.\nTente novamente mais tarde.",
									"Erro", JOptionPane.ERROR_MESSAGE);

					e.printStackTrace();

				}
			}

			n = vc * 1000 / (Math.PI * diametro);
			vf = f * n;
			ap = 0;
			ae = 0;

			cond = new CondicoesDeUsinagem(vc, f, vf, n, ap, ae);

		} else if (ferr.getClass() == BoringTool.class) {

			try {

				statement = projeto.getStatement();
				rs = statement.executeQuery(Query);
				rs.next();

				vc = rs.getDouble("vc");

				if (diametro >= 3.00 && diametro <= 42.00)
					f = rs.getDouble("f (3.00 < Dc < 42.00)");
				else if (diametro <= 0.0) {
					JOptionPane
							.showMessageDialog(
									null,
									"O diametro deve ser um número Positivo Real e Maior que Zero! \nDiametro Atual = "
											+ diametro,
									"Diametro Desconhecido!",
									JOptionPane.ERROR_MESSAGE);

					throw new NumberFormatException(
							"Diametro nao pode ser negativo ou nulo!");
				} else {
					JOptionPane
							.showMessageDialog(
									null,
									"Diametro da ferramenta encontra-se fora dos limites tabelados (3.00<= Dc <=42.00).\nDiametro Atual = "
											+ diametro
											+ "\nSerá feita uma aproximação!",
									"Diametro não Tabelado",
									JOptionPane.WARNING_MESSAGE);

					f = rs.getDouble("f (3.00 < Dc < 42.00)");
				}

			} catch (SQLException e) {

				if (e.getMessage().equals(
						"Illegal operation on empty result set.")) {
					JOptionPane.showMessageDialog(null,
							"Nao e possivel usinar "
									+ Material.getTypeMaterial(materialBloco
											.getCategory())
									+ " com Ferramenta da classe "
									+ ferr.getMaterial() + " !",
							"Incompatibilidade de Materiais ",
							JOptionPane.WARNING_MESSAGE);
				} else {

					JOptionPane
							.showMessageDialog(
									null,
									"Erro ao acessar o banco de dados do sistema.\nTente novamente mais tarde.",
									"Erro", JOptionPane.ERROR_MESSAGE);

					e.printStackTrace();

				}
			}

			n = vc * 1000 / (Math.PI * diametro);
			vf = f * n;
			ap = 0;
			ae = 0;

			cond = new CondicoesDeUsinagem(vc, f, vf, n, ap, ae);
		}

		return cond;
	}

	public static String selectMaterialFerramenta(Projeto projeto,
			Material material, String tableCondicoes) {

		String ISO = null;
		Statement statement;
		String Query = "";
		ResultSet rs = null;

		Query = "SELECT ISO, Material_Bloco FROM " + tableCondicoes
				+ " WHERE Material_Bloco LIKE " + material.getCategory() + ";";

		statement = projeto.getStatement();

		try {
			
			System.out.println("STATEMENT : " + statement);
			System.out.println("STATEMENT2 : " + Query);
			System.out.println("STATEMENT3 : " + statement.executeQuery(Query));
			
			rs = statement.executeQuery(Query);
			rs.next();

			ISO = rs.getString("ISO");

		} catch (SQLException e) {

			JOptionPane
					.showMessageDialog(
							null,
							"Erro ao acessar o banco de dados do sistema.\nTente novamente mais tarde. (selectMaterialFerramenta)",
							"Erro", JOptionPane.ERROR_MESSAGE);

			e.printStackTrace();
		}

		return ISO;

	}

	public Vector getWorkingsteps() {
		return this.workingsteps;
	}

	public static Ponto[] determinadorDePontos(Workingstep workingstep) {
		Feature feature = workingstep.getFeature();
		System.out.println("Dados br.UFSC.GRIMA.feature: "
				+ feature.getTipoString() + " posicao: "
				+ feature.getPosicaoX() + " " + feature.getPosicaoY() + " "
				+ feature.getPosicaoZ() + " ");
		// workingstep.setFerramenta(new Ferramenta());
		Ponto[] ponto = null;
		boolean acabamento = false;
		double dZ = 0, x, y, z;
		switch (feature.getTipo()) {
		case Feature.FURO:
			Furo furo = (Furo) feature;
			ponto = new Ponto[1];
			Ponto p = new Ponto(furo.getPosicaoX(), furo.getPosicaoY(), furo
					.getPosicaoZ()
					- dZ);
			ponto[0] = p;
			break;
		case Feature.DEGRAU:
			Degrau degrau = (Degrau) feature;
			ponto = new Ponto[4];
			if (acabamento) {
				// faz um negocio
			} else {
				if (degrau.getEixo() == Degrau.HORIZONTAL) {
					if (degrau.getPosicaoY() == 0) {
						x = degrau.getPosicaoX();
						y = degrau.getPosicaoY();
						z = degrau.getPosicaoZ() - dZ;
						ponto[0] = new Ponto(x, y, z);
						y = degrau.getLargura()
								- workingstep.getFerramenta()
										.getDiametroFerramenta() / 2;
						ponto[1] = new Ponto(x, y, z);
						x = workingstep.getFace().getComprimento();
						ponto[2] = new Ponto(x, y, z);
						y = degrau.getPosicaoY();
						ponto[3] = new Ponto(x, y, z);
					} else {
						x = degrau.getPosicaoX();
						y = workingstep.getFace().getLargura();
						z = degrau.getPosicaoZ() - dZ;
						ponto[0] = new Ponto(x, y, z);
						y = degrau.getPosicaoY()
								+ workingstep.getFerramenta()
										.getDiametroFerramenta() / 2;
						ponto[1] = new Ponto(x, y, z);
						x = workingstep.getFace().getComprimento();
						ponto[2] = new Ponto(x, y, z);
						y = workingstep.getFace().getLargura();
						ponto[3] = new Ponto(x, y, z);
					}
				} else {
					if (degrau.getPosicaoX() == 0) {
						x = degrau.getPosicaoX();
						y = degrau.getPosicaoY();
						z = degrau.getPosicaoZ() - dZ;
						ponto[0] = new Ponto(x, y, z);
						y = workingstep.getFace().getLargura();
						ponto[1] = new Ponto(x, y, z);
						x = degrau.getLargura()
								- workingstep.getFerramenta()
										.getDiametroFerramenta() / 2;
						ponto[2] = new Ponto(x, y, z);
						y = degrau.getPosicaoY();
						ponto[3] = new Ponto(x, y, z);
					} else {
						x = workingstep.getFace().getComprimento();
						y = degrau.getPosicaoY();
						z = degrau.getPosicaoZ() - dZ;
						ponto[0] = new Ponto(x, y, z);
						y = workingstep.getFace().getLargura();
						ponto[1] = new Ponto(x, y, z);
						x = degrau.getPosicaoX()
								+ workingstep.getFerramenta()
										.getDiametroFerramenta() / 2;
						ponto[2] = new Ponto(x, y, z);
						y = degrau.getPosicaoY();
						ponto[3] = new Ponto(x, y, z);
					}
				}
			}
			break;
		case Feature.RANHURA:
			Ranhura ranhura = (Ranhura) feature;
			ponto = new Ponto[4];
			if (ranhura.getEixo() == Ranhura.HORIZONTAL) {
				if (acabamento) {

				} else {
					x = ranhura.getPosicaoX();
					y = ranhura.getPosicaoY()
							+ workingstep.getFerramenta()
									.getDiametroFerramenta() / 2;
					z = ranhura.getPosicaoZ() - dZ;
					Ponto rP0 = new Ponto(x, y, z);
					ponto[0] = rP0;
					y = ranhura.getPosicaoY()
							+ ranhura.getLargura()
							- workingstep.getFerramenta()
									.getDiametroFerramenta() / 2;
					Ponto rP1 = new Ponto(x, y, z);
					ponto[1] = rP1;
					x += workingstep.getFace().getComprimento();
					Ponto rP2 = new Ponto(x, y, z);
					ponto[2] = rP2;
					y = ranhura.getPosicaoY()
							+ workingstep.getFerramenta()
									.getDiametroFerramenta() / 2;
					Ponto rP3 = new Ponto(x, y, z);
					ponto[3] = rP3;
				}
			} else {
				if (acabamento) {

				} else {
					x = ranhura.getPosicaoX()
							+ workingstep.getFerramenta()
									.getDiametroFerramenta() / 2;
					y = ranhura.getPosicaoY();
					z = ranhura.getPosicaoZ() - dZ;
					Ponto rP0 = new Ponto(x, y, z);
					ponto[0] = rP0;
					y += workingstep.getFace().getLargura();
					Ponto rP1 = new Ponto(x, y, z);
					ponto[1] = rP1;
					x = ranhura.getPosicaoX()
							+ ranhura.getLargura()
							- workingstep.getFerramenta()
									.getDiametroFerramenta() / 2;
					Ponto rP2 = new Ponto(x, y, z);
					ponto[2] = rP2;
					y -= workingstep.getFace().getLargura();
					Ponto rP3 = new Ponto(x, y, z);
					ponto[3] = rP3;
				}
			}
			break;
		case Feature.CAVIDADE:
			ponto = new Ponto[2];
			Cavidade cavidade = (Cavidade) feature;
			z = cavidade.getPosicaoZ() - dZ;
			if (cavidade.getLargura() < cavidade.getComprimento()) {
				x = cavidade.getPosicaoX() + cavidade.getLargura() / 2;
				y = cavidade.getPosicaoY() + cavidade.getLargura() / 2;
				ponto[0] = new Ponto(x, y, z);
				x = 0;
				x = cavidade.getPosicaoX() + cavidade.getComprimento()
						- cavidade.getLargura() / 2;
				ponto[1] = new Ponto(x, y, z);
			} else {
				x = cavidade.getPosicaoX() + cavidade.getComprimento() / 2;
				y = cavidade.getPosicaoY() + cavidade.getComprimento() / 2;
				ponto[0] = new Ponto(x, y, z);
				y = 0;
				y = cavidade.getPosicaoY() + cavidade.getLargura()
						- cavidade.getComprimento() / 2;
				ponto[1] = new Ponto(x, y, z);
			}

			break;
		default:
			break;
		}
		/*
		 * for(int pointIndex = 0; pointIndex < ponto.length; pointIndex++){
		 * System.out.println("ponto[" + pointIndex + "] = " +
		 * ponto[pointIndex].getX() + " " + ponto[pointIndex].getY() + " " +
		 * ponto[pointIndex].getZ()); }
		 */

		return ponto;
	}

	public InfoMovimentacao determinarInfoMovimentacao(Workingstep ws) {
		InfoMovimentacao infoMovimentacao = new InfoMovimentacao();
		Feature feature = ws.getFeature();
		switch (feature.getTipo()) {
		case Feature.FURO:
			infoMovimentacao.setTipo(InfoMovimentacao.RADIAL_FURO);
			break;
		case Feature.DEGRAU:
			infoMovimentacao.setTipo(InfoMovimentacao.ZIG_ZAG);
			break;
		case Feature.RANHURA:
			infoMovimentacao.setTipo(InfoMovimentacao.ZIG_ZAG);
			break;
		case Feature.CAVIDADE:
			infoMovimentacao.setTipo(InfoMovimentacao.RADIAL_CAVIDADE);
			break;
		default:
			break;
		}
		return infoMovimentacao;
	}

	public void imprimirDados() {
		System.out.println("\n");
		System.out
				.println("=======================================================");
		System.out
				.println("==        Dados da Mapeadora de Workingsteps         ==");
		System.out
				.println("=======================================================");

		for (int i = 0; i < this.workingsteps.size(); i++) {
			Vector workingstepsFaceI = (Vector) this.workingsteps.elementAt(i);

			System.out.printf("..:: Face #%d (possui %d workingsteps) ::..", i,
					workingstepsFaceI.size());

			for (int j = 0; j < workingstepsFaceI.size(); j++) {
				Workingstep wsJ = (Workingstep) workingstepsFaceI.elementAt(j);

				System.out.printf("\tWorkingstep #%d\n", j);
				System.out.println(wsJ.getDados("\t"));

			}
			System.out.println("\n");
		}

		System.out
				.println("=======================================================");
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public void setWorkingsteps(Vector<Vector<Workingstep>> workingsteps) {
		this.workingsteps = workingsteps;
	}
}
