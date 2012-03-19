package br.UFSC.GRIMA.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import br.UFSC.GRIMA.bancoDeDados.Conexao;

public class ToolReader {
	

	Conexao conexao = new Conexao();
	Connection conn;
	Statement statement;
	ResultSet rs = null;
	String ferramentaTable;
	
	public ToolReader(){
			
		conexao.setConn("150.162.105.1", "webTools", "webcad", "julio123"); // host, db, usuario, senha
		
		conn = conexao.getConn();
		
		try {
			
			statement = conn.createStatement();
			
		} catch (SQLException e) {
			JOptionPane
			.showMessageDialog(
					null,
					"Erro ao acessar o banco de dados do sistema.\nTente novamente mais tarde.",
					"Erro", 0);
			e.printStackTrace();
		}
		
	}
	
	public int getSize(){
		
		String Query = "SELECT COUNT(*) FROM "+ ferramentaTable +";";
		
		int size = 0;
		
		try {
			rs = statement.executeQuery(Query);
			rs.next();
			size = rs.getInt("COUNT(*)");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return size;
	}
	
	
	public String getPropertie(String propertie, int id){
		
		String prop = "";
		
		String Query = "SELECT * FROM " + ferramentaTable + " WHERE Id = " + id + ";";
		
		try {

			rs = statement.executeQuery(Query);
			rs.next();
			prop = rs.getString(propertie);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return prop;
	}
	

	public int getFerramentaByDiameter(double diameter) {
		
		for(int id=1;id<=getSize();id++){
		
			if( Double.parseDouble(getPropertie("Diametro", id)) == diameter){
			
				return id;
			}
		}
		return 0;
	}


	public String getFerramentaTable() {
		return ferramentaTable;
	}


	public void setFerramentaTable(String ferramentaTable) {
		this.ferramentaTable = ferramentaTable;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public Statement getStatement() {
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}
}
