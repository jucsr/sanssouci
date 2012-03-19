package br.UFSC.GRIMA.applet;
import br.UFSC.GRIMA.cad.CriarNovoProjeto;
import br.UFSC.GRIMA.cad.NovoProjeto;

/**
 * Utilizada para inicializar todo o projeto.
 * 
 * TODO Esta classe deveria ser utilizada para inicializar todas
 * as etapas do projeto, separadamente. (CAD depois CAPP)
 * 
 * @author roman
 *
 */
public class Principal {
	private NovoProjeto projeto;
	private int userID;
	private String userName;
	private CriarNovoProjeto criarNovoProjeto;
	
	public Principal(int userID, String userName) 
	{
		this.userID = userID;
		this.userName = userName;
		this.init();		
	}
	
	public void init(){
//		this.projeto = new NovoProjeto(userID, userName);
//		this.projeto.setVisible(true);
		this.criarNovoProjeto = new CriarNovoProjeto(userID, userName);
		this.criarNovoProjeto.setVisible(true);
	}

}
