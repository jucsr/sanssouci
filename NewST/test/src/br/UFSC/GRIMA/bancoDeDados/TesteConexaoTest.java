package br.UFSC.GRIMA.bancoDeDados;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.junit.Test;

import br.UFSC.GRIMA.entidades.ferramentas.TwistDrill;

public class TesteConexaoTest {

	Connection conn;
	String Query = "";
	String Query2 = "";
	int userID;
	int id = 10;
	ResultSet rs = null;
	Statement statement;
	Dimension d;
	Conexao conexao = new Conexao();
	
	
	@Test
	public void getConexao(){

		
		//150.162.105.1
		conexao.setConn("150.162.105.1", "webTools", "webcad", "julio123");
		
		conn = conexao.getConn();
			
//		Query = "SELECT MAX(f) AS f FROM Condições_De_Usinagem_TwistDrill; ";
		
//		Query = " SELECT ISO, Material_Bloco " +
//				"FROM Condições_De_Usinagem_TwistDrill WHERE f=(SELECT MAX(f) FROM Condições_De_Usinagem_TwistDrill);"; 

		
//		Query = "SELECT * FROM Condições_De_Usinagem_TwistDrill;";
		
		Query = "SELECT * FROM Condicoes_De_Usinagem_TwistDrill WHERE ISO = 'S' AND Material_Bloco = 9;";
		
		Query2 = "SELECT COUNT(*) FROM TwistDrill;";
		
		try {
			
			statement = conn.createStatement();
			rs = statement.executeQuery(Query);
			
			rs.next();
			
			String iso = rs.getString("ISO");
			int mat = rs.getInt("Material_Bloco");
			double vc = rs.getDouble("vc");
			double f = rs.getDouble("f (3.00 < Dc < 6.00)");
			
		System.out.println("ISO = " + iso);
		System.out.println("Material_Bloco = " + mat);
		System.out.println("Diametro = " + 5);
		System.out.println("vc = " + vc);
		System.out.println("f = " + f);
		
		rs = statement.executeQuery(Query2);
	
		rs.next();
		int size = rs.getInt("COUNT(*)");
		
		System.out.println(size);
		
		} catch (SQLException e) {
			JOptionPane
			.showMessageDialog(
					null,
					"Erro ao acessar o banco de dados do sistema.\nTente novamente mais tarde.",
					"Erro", 0);
			e.printStackTrace();
		}

	}

}
