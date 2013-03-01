package br.UFSC.GRIMA.bancoDeDados;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import br.UFSC.GRIMA.entidades.machiningResources.MachineTool;
import br.UFSC.GRIMA.util.projeto.Projeto;

/**
 * Fun��o que faz todo o tipo de acesso ao banco de dados.
 * 
 * @author roman
 * 
 */
public class AcessaBD extends Conexao {
	private Connection conn;

	private String Query = new String();
	private int userID = 0;
	private ResultSet rs = null;
	private Statement statement;
	private Dimension d;

	public AcessaBD(int userID) {
		this.userID = userID;
		setConn("150.162.105.1", "webTools", "webcad", "julio123");
		conn = getConn();
	}

	public AcessaBD(String host, String banco, String user, String senha) {
		setConn(host, banco, user, senha);
		conn = getConn();
	}


//	public String getProjetoPorID(int id) {//retorna a String do projeto
//
//		Query = "SELECT * FROM Projetos_Usuarios WHERE UserID = " + id
//		+ " ORDER BY UserID ";
//
//		String pString;
//
//		try {
//			statement = conn.createStatement();
//			rs = statement.executeQuery(Query);
//			rs.next();
//			//			while (id >= 0) {
//			//				rs.next();
//			//				id--;
//			//			}
//
//			pString = rs.getString("StringProjeto");
//
//			return pString;
//
//		} catch (SQLException e) {
//			JOptionPane
//			.showMessageDialog(
//					null,
//					"Erro ao acessar o banco de dados do sistema.\nTente novamente mais tarde.",
//					"Erro", 0);
//			e.printStackTrace();
//		}
//
//		return null;
//	}
	
	public String getProjetoPorNome(int id , String nomeP) {//retorna a String do projeto

		Query = "SELECT StringProjeto FROM Projetos_Usuarios " +
				"WHERE UserID = " + id
		+ " AND NomeProjeto = '" + nomeP + "';";

		String pString;

		try {
			statement = conn.createStatement();
			
			rs = statement.executeQuery(Query);
			rs.next();
			//			while (id >= 0) {
			//				rs.next();
			//				id--;
			//			}

			pString = rs.getString("StringProjeto");

			return pString;

		} catch (SQLException e) {
			JOptionPane
			.showMessageDialog(
					null,
					"Erro ao acessar o banco de dados do sistema.\nTente novamente mais tarde.",
					"Erro", 0);
			e.printStackTrace();
		}

		return null;
	}


	public String getFerramentasProjetoPorNome(int id, String nomeP) {

		Query = "SELECT * FROM Projetos_Usuarios WHERE UserID = '" + id
		+ "' AND NomeProjeto = '" + nomeP + "';";

		String tString;

		try {
			statement = conn.createStatement();
			
			rs = statement.executeQuery(Query);
			rs.next();
			//			while (id >= 0) {
			//				rs.next();
			//				id--;
			//			}

			tString = rs.getString("StringFerramentas");

			return tString;

		} catch (SQLException e) {
			JOptionPane
			.showMessageDialog(
					null,
					"Erro ao acessar o banco de dados do sistema.\nTente novamente mais tarde.",
					"Erro", 0);
			e.printStackTrace();
		}

		return null;
	}
	
	public String getOrdemPorID(int id) {
		Query = "SELECT * FROM ordens1 order by Id";
		String bbb = "";
		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(Query);
			rs.next();
			while (id > 0) {
				rs.next();
				id--;
			}

			bbb = rs.getString("codigo_hpgl");
			return bbb;
		} catch (SQLException e) {
			JOptionPane
			.showMessageDialog(
					null,
					"Erro ao acessar o banco de dados do sistema.\nTente novamente mais tarde.",
					"Erro", 0);
			e.printStackTrace();
		}

		return null;
	}


	public String[] listaProjeto(int id) {
		Query = "SELECT * FROM Projetos_Usuarios WHERE UserID = " + id;
		String[] ttt;
		int i = 0;
		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(Query);
			while (rs.next()) {
				i++;
			}
			ttt = new String[i];
			rs.first();
			i = 0;
			if (ttt.length == 0) {
				return ttt;
			}

			do {
				ttt[i] = rs.getString("NomeProjeto") + " : "
				+ rs.getString("Data_Modificado");
				i++;
			} while (rs.next());

			return ttt;
		} catch (SQLException e) {
			JOptionPane
			.showMessageDialog(
					null,
					"Erro ao acessar o banco de dados.\nTente novamente mais tarde.",
					"Erro", 0);
			e.printStackTrace();
		}

		return null;
	}

	public String[] listaOrdem() {
		Query = "SELECT * FROM ordens1  ORDER BY id";
		String[] ttt;
		int i = 0;
		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(Query);
			while (rs.next()) {
				i++;
			}
			ttt = new String[i];
			rs.first();
			i = 0;
			if (ttt.length == 0) {
				return ttt;
			}

			do {
				ttt[i] = "nome da ordem:" + rs.getString("nome_ordem")
				+ "    data: " + rs.getString("data_mod")
				+ "   usuario: " + rs.getString("usuario");
				i++;

			} while (rs.next());

			return ttt;
		} catch (SQLException e) {
			JOptionPane
			.showMessageDialog(
					null,
					"Erro ao acessar o banco de dados.\nTente novamente mais tarde.",
					"Erro", 0);
			e.printStackTrace();
		}

		return null;
	}

	public boolean salvoNoBD(Projeto projeto) {  // verifica se o projeto está salvo no BD
		Query = "SELECT * FROM Projetos_Usuarios WHERE NomeProjeto = '"
			+ projeto.getDadosDeProjeto().getProjectName() + "'"
			+ " AND UserID = " + projeto.getDadosDeProjeto().getUserID();

		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(Query);
			if (rs.next())
				return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao conectar com o servidor.", "Erro", 0);
		}

		return false;
	}
	
	public boolean salvoNoBDMachines(MachineTool machine, int userID) {  // verifica se o projeto está salvo no BD
		Query = "SELECT * FROM Maquinas_Usuarios WHERE NomeMaquina = '"
			+ machine.getItsId() + "'"
			+ " AND UserID = " + userID;

		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(Query);
			if (rs.next())
				return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao conectar com o servidor.", "Erro", 0);
		}

		return false;
	}

	public boolean insertProjeto(Projeto projeto, String pString) {

		Query = "INSERT INTO Projetos_Usuarios (UserID, NomeProjeto, StringProjeto, Hora_Criado, Data_Criado, Hora_Modificado, Data_Modificado ) VALUES ('"
			+ projeto.getDadosDeProjeto().getUserID()
			+ "', '"
			+ projeto.getDadosDeProjeto().getProjectName()
			+ "', '"
			+ pString
			+ "', "
			+ "CURRENT_TIME"
			+ ", "
			+ "CURRENT_DATE"
			+ ", "
			+ "CURRENT_TIME" 
			+ ", " 
			+ "CURRENT_DATE" 
			+ ")";

		try {
			statement = conn.createStatement();
			statement.executeUpdate(Query);
			return true;
		} catch (SQLException e) {
			JOptionPane
			.showMessageDialog(
					null,
					"Erro ao inserir o novo projeto no banco de dados do sistema.\nTente novamente mais tarde.",
					"Erro", 0);
			e.printStackTrace();
		}

		return false;
	}


	public boolean insertFerramentasProjeto(Projeto projeto, String tString) {

		Query = "INSERT INTO Projetos_Usuarios (UserID, NomeProjeto, StringFerramentas, Hora_Criado, Data_Criado, Hora_Modificado, Data_Modificado ) VALUES ('"
			+ projeto.getDadosDeProjeto().getUserID()
			+ "', '"
			+ projeto.getDadosDeProjeto().getProjectName()
			+ "', '"
			+ tString
			+ "', "
			+ "CURRENT_TIME"
			+ ", "
			+ "CURRENT_DATE"
			+ ", "
			+ "CURRENT_TIME" 
			+ ", " 
			+ "CURRENT_DATE" 
			+ ")";

		try {
			statement = conn.createStatement();
			statement.executeUpdate(Query);
			return true;
		} catch (SQLException e) {
			JOptionPane
			.showMessageDialog(
					null,
					"Erro ao inserir o novo projeto no banco de dados do sistema.\nTente novamente mais tarde.",
					"Erro", 0);
			e.printStackTrace();
		}

		return false;
	}
	
	public boolean insertMaquina(MachineTool machine, String tString, int userID) {

		Query = "INSERT INTO Maquinas_Usuarios (NomeMaquina, UserID, StringMaquina, Hora_Criado, Data_Criado, Hora_Modificado, Data_Modificado ) VALUES ('"
			+ machine.getItsId()
			+ "', '"
			+ userID
			+ "', '"
			+ tString
			+ "', "
			+ "CURRENT_TIME"
			+ ", "
			+ "CURRENT_DATE"
			+ ", "
			+ "CURRENT_TIME" 
			+ ", " 
			+ "CURRENT_DATE" 
			+ ")";

		try {
			statement = conn.createStatement();
			statement.executeUpdate(Query);
			return true;
		} catch (SQLException e) {
			JOptionPane
			.showMessageDialog(
					null,
					"Erro ao inserir a nova maquina no banco de dados do sistema.\nTente novamente mais tarde.",
					"Erro", 0);
			e.printStackTrace();
		}

		return false;
	}

	public boolean incProntas() {
		Query = "UPDATE ordens1 SET prontas = " + "'" + "'" + ", data_mod = "
		+ "CURRENT_DATE" + " WHERE nome_projeto =";

		try {
			statement = conn.createStatement();
			statement.executeUpdate(Query);
			return true;
		} catch (SQLException e) {
			JOptionPane
			.showMessageDialog(
					null,
					"Erro ao inserir o novo projeto no banco de dados do sistema.\nTente novamente mais tarde.",
					"Erro", 0);
			e.printStackTrace();
		}

		return false;
	}

	public boolean insertOrdem(Projeto projeto, String sss,
			String material, int quantidade) {

		Query = "INSERT INTO Ordens_De_Fabricacao (usuario, projeto, material,"
			+ " quantidade, prontas, mensagem, "
			+ "status, hora, data, hora_mod, data_mod) VALUES ('"
			+ projeto.getDadosDeProjeto().getUserID()
			+ "', '"
			+ sss
			+ "', '"
			+ material
			+ "', '"
			+ quantidade
			+ "', '"
			+ "0"
			+ "', '"
			+ "ok"
			+ "', '"
			+ "aguardando fabricação"
			+ "', "
			+ "CURRENT_TIME"
			+ ", "
			+ "CURRENT_DATE"
			+ ", "
			+ "CURRENT_TIME"
			+ ", "
			+ "CURRENT_DATE" + ")";

		/*
		 * projeto.getDadosDeProjeto().getProjectName() + "', '" +
		 * projeto.getDadosDeProjeto().getUserID() + "', '" + sss + "', " +
		 * "CURRENT_TIME" + ", " + "CURRENT_DATE" + ", " + "CURRENT_TIME" + ", "
		 * + "CURRENT_DATE" +")";
		 */

		try {
			statement = conn.createStatement();
			statement.executeUpdate(Query);
			return true;
		} catch (SQLException e) {
			JOptionPane
			.showMessageDialog(
					null,
					"Erro ao inserir a ordem de fabricação no banco de dados do sistema.\nTente novamente mais tarde.",
					"Erro", 0);
		}

		return false;
	}


	public boolean updateProjeto(Projeto projeto, String pString ) {
		Query = "UPDATE Projetos_Usuarios SET"
			+ " StringProjeto = " + "'" + pString + "'" 
			+ ", Data_Modificado = " + "CURRENT_DATE" + ", Hora_Modificado = " + "CURRENT_TIME" + 
			" WHERE NomeProjeto ="
			+ "'" + projeto.getDadosDeProjeto().getProjectName() + "'"
			+ " AND UserID = " + projeto.getDadosDeProjeto().getUserID();

		try {
			statement = conn.createStatement();
			statement.executeUpdate(Query);
			return true;
		} catch (SQLException e) {
			JOptionPane
			.showMessageDialog(
					null,
					"Erro ao inserir o novo projeto no banco de dados do sistema.\nTente novamente mais tarde.",
					"Erro", 0);
			e.printStackTrace();
		}

		return false;
	}
	
	public boolean existeProjeto(int userID, String projectName) {

		Query = "SELECT * FROM Projetos_Usuarios WHERE NomeProjeto = '"
			+ projectName + "'"
			+ " AND UserID = " + userID;

		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(Query);
			if (rs.next())
				return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao conectar com o servidor.", "Erro", 0);
		}

		return false;
		
	}
	
	public boolean existeMaquina(int userID, String machineName)
	{
		Query = "SELECT * FROM Maquinas_Usuarios WHERE NomeMaquina = '"
				+ machineName + "'"
				+ " AND UserID = " + userID;

			try {
				statement = conn.createStatement();
				rs = statement.executeQuery(Query);
				if (rs.next())
					return true;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null,
						"Erro ao conectar com o servidor.", "Erro", 0);
			}

			return false;
	}
	
	public boolean updateFerramentasProjeto(Projeto projeto, String tString) {

		Query = "UPDATE Projetos_Usuarios SET"
			+ " StringFerramentas = " + "'" + tString + "'" 
			+ ", Data_Modificado = " + "CURRENT_DATE" + ", Hora_Modificado = " + "CURRENT_TIME" + 
			" WHERE NomeProjeto ="
			+ "'" + projeto.getDadosDeProjeto().getProjectName() + "'"
			+ " AND UserID = " + projeto.getDadosDeProjeto().getUserID();

		try {
			statement = conn.createStatement();
			statement.executeUpdate(Query);
			return true;
		} catch (SQLException e) {
			JOptionPane
			.showMessageDialog(
					null,
					"Erro ao inserir o novo projeto no banco de dados do sistema.\nTente novamente mais tarde.",
					"Erro", 0);
			e.printStackTrace();
		}

		return false;

	}
	
	public boolean updateMaquina(MachineTool machine, String tString, int userID) {

		Query = "UPDATE Maquinas_Usuarios SET"
			+ " StringMaquinas = " + "'" + tString + "'" 
			+ ", Data_Modificado = " + "CURRENT_DATE" + ", Hora_Modificado = " + "CURRENT_TIME" + 
			" WHERE NomeMaquina ="
			+ "'" + machine.getItsId() + "'"
			+ " AND UserID = " + userID;

		try {
			statement = conn.createStatement();
			statement.executeUpdate(Query);
			return true;
		} catch (SQLException e) {
			JOptionPane
			.showMessageDialog(
					null,
					"Erro ao inserir a nova maquina no banco de dados do sistema.\nTente novamente mais tarde.",
					"Erro", 0);
			e.printStackTrace();
		}

		return false;

	}

	/*
	 * obtem tabela com informa��es dos pedidos
	 */
	public JTable getdados(String tablename) {
		JTable table = null;
		Vector columnHeads, rows;
		Query = "SELECT * FROM " + tablename + " ORDER BY Id DESC";

		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(Query);
			if (rs.next()) {
				columnHeads = new Vector();
				rows = new Vector();

				String[] columnNames = { "ID-circ", "ID-user", "Circuito",
						"Quant", "Status", "Manufatura", "Tam X", "Tam Y" };

				try {
					ResultSetMetaData rsmd = rs.getMetaData();
					for (int i = 1; i <= rsmd.getColumnCount(); ++i)
						columnHeads
						.addElement(/* rsmd.getColumnName(pointIndex) */columnNames[i - 1]);

					Vector currentRow;
					do {
						currentRow = new Vector();
						for (int i = 1; i <= rsmd.getColumnCount(); ++i)
							switch (rsmd.getColumnType(i)) {
							case Types.VARCHAR:
								currentRow.addElement(rs.getString(i));
								break;
							case Types.INTEGER:
								currentRow.addElement(new Long(rs.getLong(i)));
								break;
							case Types.CHAR:
								currentRow.addElement(rs.getString(i));
								break;
							case Types.LONGVARCHAR:
								currentRow.addElement(rs.getString(i));
								break;
							default:
								System.out.println("Tipos de dados: "
										+ rsmd.getColumnTypeName(i));
							}
						rows.addElement(currentRow);
					} while (rs.next());

					table = new JTable(rows, columnHeads);
					d = Toolkit.getDefaultToolkit().getScreenSize();
					d.height = d.height / 4;
					d.width = d.width / 5;
					table.setPreferredScrollableViewportSize(d);
					table.setEnabled(false);
					table.getColumn("Mesa").setMinWidth(80);
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null,
							"Erro ao criar Tabela!", "Erro", 0);
				}
			}
			// else System.out.println("tabela vazia!!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro1", "Erro", 0);
			e.printStackTrace();
		}

		if (table != null) {
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.setDragEnabled(false);
		}

		return table;
	}

	/*
	 * obtem tabela com informa��es das mesas
	 */
	public JTable getmesas(String tablename) {
		JTable table = null;
		Vector columnHeads, rows;
		Query = "SELECT * FROM " + tablename + " ORDER BY id DESC";

		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(Query);
			if (rs.next()) {
				columnHeads = new Vector();
				rows = new Vector();

				String[] columnNames = { "ID-mesa", "Nome", "Tam x", "Tam y",
						"Agente", "Veloc", "Status" };

				try {
					ResultSetMetaData rsmd = rs.getMetaData();
					for (int i = 1; i <= rsmd.getColumnCount(); ++i)
						columnHeads
						.addElement(/* rsmd.getColumnName(pointIndex) */columnNames[i - 1]);

					Vector currentRow;
					do {
						currentRow = new Vector();
						for (int i = 1; i <= rsmd.getColumnCount(); ++i)
							switch (rsmd.getColumnType(i)) {
							case Types.VARCHAR:
								currentRow.addElement(rs.getString(i));
								break;
							case Types.INTEGER:
								currentRow.addElement(new Long(rs.getLong(i)));
								break;
							case Types.CHAR:
								currentRow.addElement(rs.getString(i));
								break;
							case Types.OTHER:
								currentRow.addElement((String) rs.getObject(i));
								break;
							case Types.LONGVARCHAR:
								currentRow.addElement(rs.getString(i));
								break;
							default:
								System.out.println("Tipos de dados: "
										+ rsmd.getColumnTypeName(i));
							}
						rows.addElement(currentRow);
					} while (rs.next());

					table = new JTable(rows, columnHeads);
					d = Toolkit.getDefaultToolkit().getScreenSize();
					d.height = d.height / 3;
					d.width = d.width / 2 - d.width / 10;
					table.setPreferredScrollableViewportSize(d);
					table.getColumn("Nome").setMinWidth(80);
					table.getColumn("Agente").setMinWidth(80);
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null,
							"Erro ao criar Tabela!", "Erro", 0);
				}
			}
			// else
			// System.out.println("Nao existem registros na tabela de mesas!!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro1", "Erro", 0);
			e.printStackTrace();
		}

		if (table != null) {
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.setDragEnabled(false);
		}
		return table;
	}

	/*
	 * retorna Vetor de vetores que s�o todos os pedidos n�o processados
	 */
	public Vector buscapedidos(String tabela) {
		Vector busca = null;
		Query = "SELECT * FROM " + tabela
		+ " WHERE status = 0 ORDER BY idcircuito";
		Vector pedidos = null;
		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(Query);
			if (rs.next()) {
				try {
					ResultSetMetaData rsmd = rs.getMetaData();
					pedidos = new Vector();
					do {
						busca = new Vector();
						for (int i = 1; i <= rsmd.getColumnCount(); ++i) {
							switch (rsmd.getColumnType(i)) {
							case Types.VARCHAR:
								busca.addElement(rs.getString(i));
								break;
							case Types.INTEGER:
								busca.addElement(new Long(rs.getLong(i)));
								break;
							case Types.CHAR:
								busca.addElement(rs.getString(i));
								break;
							case Types.LONGVARCHAR:
								busca.addElement(rs.getString(i));
								break;
							default:
								System.out.println("Tipos de dados: "
										+ rsmd.getColumnTypeName(i));
							}
						}
						pedidos.addElement(busca);
					} while (rs.next());
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null,
							"Erro ao criar Tabela!", "Erro", 0);
				}
			}
			// else System.out.println("Nao existem processos!!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro1", "Erro", 0);
			e.printStackTrace();
		}

		return pedidos;
	}

	/*
	 * Busca mesas que podem executar o circuito enviado
	 */
	public Vector buscamesas(String tabela, int xmax, int ymax) {
		Vector mesas = null;
		Vector busca;
		// for (int j=0; j<agentes.size(); j++)
		// {
		Query = "SELECT * FROM " + tabela + " WHERE (status = 1) AND ((tamx > "
		+ xmax + ") AND (tamy > " + ymax + ")) ORDER BY id";
		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(Query);
			if (rs.next()) {
				try {
					ResultSetMetaData rsmd = rs.getMetaData();
					mesas = new Vector();
					do {
						busca = new Vector();
						for (int i = 1; i <= rsmd.getColumnCount(); ++i)
							switch (rsmd.getColumnType(i)) {
							case Types.VARCHAR:
								busca.addElement(rs.getString(i));
								break;
							case Types.INTEGER:
								busca.addElement(new Long(rs.getLong(i)));
								break;
							case Types.CHAR:
								busca.addElement(rs.getString(i));
								break;
							case Types.OTHER:
								busca.addElement((String) rs.getObject(i));
								break;
							case Types.LONGVARCHAR:
								busca.addElement(rs.getString(i));
								break;
							default:
								System.out.println("Tipos de dados: "
										+ rsmd.getColumnTypeName(i));
							}
						mesas.addElement(busca);
					} while (rs.next());
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null,
							"Erro ao criar Tabela!", "Erro", 0);
				}
			}
			// else System.out.println("Nao existe esta mesa com processo");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro1", "Erro", 0);
			e.printStackTrace();
		}
		// }
		return mesas;
	}

	/*
	 * 
	 * public void removepedido(String tabela, Long idTemp) { Query =
	 * "DELETE FROM " + tabela + " WHERE id='" + idTemp + "'";
	 * 
	 * try { stm = con.createStatement(); stm.executeUpdate(Query); } catch
	 * (SQLException e){JOptionPane.showMessageDialog(null,
	 * "Erro ao remover pedido", "Erro", 0);} }
	 * 
	 * public void saveContacts(String tabela) { Query =
	 * "SELECT name, lastname, mailpri, homephone, celphone, msn INTO OUTFILE \"E:/Users/Roman/Agenda/Atual/Arquivos/"
	 * + tabela + "_contacts.txt\" FROM t_" +tabela;
	 * //System.out.println(Query);
	 * 
	 * try { stm = con.createStatement(); stm.executeQuery(Query);
	 * JOptionPane.showMessageDialog(null, "Salvou", "Erro", 0); } catch
	 * (SQLException e){JOptionPane.showMessageDialog(null,
	 * "Erro ao salvar contato" +e.getMessage(), "Erro", 0);} }
	 * 
	 * 
	 * // procura por um item em alguma tabela //true se o item existir, false
	 * se ele n�o existir
	 * 
	 * public boolean findItem(String tableName, String colName, String item) {
	 * Query = "SELECT " + colName + " FROM " + tableName + " WHERE " + colName
	 * + "=\"" + item + "\""; try { stm =
	 * con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
	 * ResultSet.CONCUR_UPDATABLE); rs = stm.executeQuery(Query); if(rs.next())
	 * return true; else return false; } catch (SQLException e){return false;} }
	 * 
	 * //remove um item de uma dada coluna de uma tabela public void
	 * removeItem(String tableName, String colName, String thing) { Query =
	 * "DELETE FROM " + tableName + " WHERE " + colName + "=\"" + thing + "\"";
	 * try { stm = con.createStatement(); stm.executeUpdate(Query); } catch
	 * (SQLException e){} }
	 * 
	 * //insere na tabela de usu�rios "users" public void insertUser(Vector
	 * user) { //System.out.println(user.get(0).toString());
	 * //System.out.println(user.get(1)); if(findTable("users") == false)
	 * createGreatTable(); Query = "INSERT INTO users VALUES(null, '" +
	 * user.get(0) + "', '" + user.get(1) + "')"; try { stm =
	 * con.createStatement(); stm.executeUpdate(Query); } catch (SQLException
	 * e){JOptionPane.showMessageDialog(null,
	 * "N�o foi possivel criar usu�rio.\n\nMensagem: "+ e.getMessage(), "Erro",
	 * 0);} createUserTable(user.get(0).toString()); }
	 * 
	 * private void printTable(String tableName) { rs = null; Query =
	 * "SELECT username from " + tableName; try{ stm =
	 * con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
	 * ResultSet.CONCUR_UPDATABLE); rs = stm.executeQuery(Query);
	 * JOptionPane.showMessageDialog(null, "bleeeeee", "Erro", 0);
	 * if(rs.next()){ do { System.out.println(rs.getString("username")); } while
	 * (rs.next()); } else{} } catch(SQLException e){
	 * JOptionPane.showMessageDialog(null, "Imprimindo.\n\nMensagem: "+
	 * e.getMessage(), "Erro", 0); } }
	 * 
	 * //retorna um vetor com todos os dados desejados ("things") de uma tabela
	 * qquer public Vector search(String tableName, String[] things) { Vector v
	 * = new Vector(); rs = null; Query = "SELECT * FROM " + tableName; try {
	 * stm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
	 * ResultSet.CONCUR_UPDATABLE); rs = stm.executeQuery(Query); if(rs.next())
	 * { do { Vector v2 = new Vector(); int pointIndex = 0; for(pointIndex = 0; pointIndex < things.length;
	 * pointIndex++) { v2.add(rs.getString(things[pointIndex])); } v.add(v2); } while (rs.next());
	 * } else{} } catch (SQLException e) { JOptionPane.showMessageDialog(null,
	 * "Search.\n\nMensagem: " + e.getMessage(), "Erro", 0); }
	 * 
	 * return v; }
	 * 
	 * //retorna os dados de uma �nica coluna de uma tabela public Vector
	 * search(String tableName, String colName) { Vector v = new Vector(); rs =
	 * null; Query = "SELECT " + colName + " FROM " + tableName; try { stm =
	 * con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
	 * ResultSet.CONCUR_UPDATABLE); rs = stm.executeQuery(Query); if(rs.next())
	 * { do { v.add(rs.getString(colName)); } while (rs.next()); } else{} }
	 * catch (SQLException e) { JOptionPane.showMessageDialog(null,
	 * "Search.\n\nMensagem: " + e.getMessage(), "Erro", 0); }
	 * 
	 * return v; }
	 * 
	 * public Vector getContact(String tabela, Long idTemp) { Vector v = null;
	 * Query = "SELECT * FROM " + tabela + " WHERE id = '" + idTemp + "'";
	 * 
	 * try { stm = con.createStatement(); rs = stm.executeQuery(Query);
	 * if(rs.next()) { v = new Vector(); for (int pointIndex = 2; pointIndex < 17 ; pointIndex++){
	 * v.add(rs.getString(pointIndex)); } } } catch (SQLException
	 * e){JOptionPane.showMessageDialog(null, "Erro ao buscar contato", "Erro",
	 * 0);}
	 * 
	 * return v; }
	 */

}
