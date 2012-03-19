package br.UFSC.GRIMA.bancoDeDados;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Conexao { 
    
    private Connection conn; 

    public Conexao() { 
        
        try { //inicio bloco seguro 
            Class.forName("com.mysql.jdbc.Driver").newInstance(); 
           // Class.forName("org.gjt.mm.mysql.Driver").newInstance(); 
        } 
        catch (Exception e) { 
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Não foi possível encontrar classe para conexão.\n\nMensagem de erro: "+ e.getMessage(), "Erro", 0 );            
        } //final bloco seguro 
        
    } 

  /*  public void setConn() { 
        try { 
            Usuario usuario = new Usuario(); 
            conn = DriverManager.getConnection("jdbc:mysql://localhost/agenda?user="+ usuario.getUsuario() +"&password="+ usuario.getSenha()); 
            JOptionPane.showMessageDialog(null, "Conex�o estabelecida.", "Conex�o", 1); 
        }        
        catch (SQLException e) { 
            JOptionPane.showMessageDialog(null, "N�o foi poss�vel conectar-se ao banco de dados.\n\nMensagem: "+ e.getMessage(), "Erro", 0); 
            conn = null; 
        } 
    }*/

    public void setConn(String usuario, String senha) { 
        try { 
            conn = DriverManager.getConnection("jdbc:mysql://gauss/grima?user="+ usuario +"&password="+ senha); 
            //JOptionPane.showMessageDialog(null, "Conex�o estabelecida.", "Conex�o", 1); 
        }        
        catch (SQLException e) { 
//            JOptionPane.showMessageDialog(null, "N�o foi poss�vel conectar-se ao banco de dados.\n\nMensagem: "+ e.getMessage(), "Erro", 0); 
            JOptionPane.showMessageDialog(null, "Não foi possível conectar-se ao banco de dados.", "Erro", 0); 
            conn = null; 
        } 
    }

    public void setConn(String host, String usuario, String senha) { 
        try { 
            conn = DriverManager.getConnection("jdbc:mysql://"+ host +"/grima?user="+ usuario +"&password="+ senha); 
            //JOptionPane.showMessageDialog(null, "Conex�o estabelecida.", "Conex�o", 1); 
        }        
        catch (SQLException e) { 
        	JOptionPane.showMessageDialog(null, "Não foi possível conectar-se ao banco de dados.", "Erro", 0); 
        	conn = null; 
        } 
    }

    public void setConn(String host, String db, String usuario, String senha) { 
        try { 
            conn = DriverManager.getConnection("jdbc:mysql://"+ host +"/"+ db +"?user="+ usuario +"&password="+ senha); 
            //JOptionPane.showMessageDialog(null, "Conex�o estabelecida.", "Conex�o", 1);
        }        
        catch (SQLException e) { 
        	JOptionPane.showMessageDialog(null, "Não foi possível conectar-se ao banco de dados.", "Erro", 0); 
        	conn = null; 
        } 
    }

    public Connection getConn() { 
    	return conn; 
    }
    
    public void Desconectar() { 
        try { 
            conn.close(); 
			JOptionPane.showMessageDialog(null, "Desconectado!", "Conexão", 0);
        } 
        catch (Exception e) { 
            JOptionPane.showMessageDialog(null, "Ocorreram erros ao fechar conexão.\n\nMensagem: "+ e.getMessage(), "Erro", 0); 
            conn = null; 
        } 
    } 
    
} 
