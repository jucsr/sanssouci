package br.UFSC.GRIMA.capp;

import java.io.Serializable;
import java.util.Vector;

import br.UFSC.GRIMA.cam.GerarCodigoHPGL;
import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraDeWorkingsteps;
import br.UFSC.GRIMA.util.projeto.OrdemDeFabricacao;
import br.UFSC.GRIMA.util.projeto.Projeto;






//import sun.security.krb5.internal.am;


public class CAPP implements Serializable {
	//janela de configuracao
	//chama o programa logico que determina as sequencias
	//chama o programa logico que determina qual a melhor sequencia para as configuracoes do usuario
	public Projeto projeto;
	public OrdemDeFabricacao ordemDeFabricacao;
	public CondicoesDeUsinagem condicoesDeUsinagem;
	//public int quantidade = 0;
	
	public MapeadoraDeWorkingsteps mapeadora;
	public Vector vetorDeWorkingSteps;
	public Vector<Workingstep> workingStepsOrdenadas;
	
	public AgrupadorDeWorkingSteps agrWs;
	public Vector gruposCamadasDoBloco;
	
	//DeterminadorDeSequencias dds;
	//DeterminarMovimentacao detMov;
	
	public OrdenadoraDeSequencias ordenador;
	public Vector sequenciasPorCamadasDoBloco;
	
	public int [] indiceFace;
	public Vector codigoHPGL;
	public String codigoHPGL_texto;
	
	public CAPP(Projeto projeto, CondicoesDeUsinagem condicoesDeUsinagem) {
		this.projeto = projeto;
		this.ordemDeFabricacao = new OrdemDeFabricacao(condicoesDeUsinagem, 0);
		//this.condicoesDeUsinagem = condicoesDeUsinagem;
		this.iniciar();
	}
	
	public CAPP(Projeto projeto, OrdemDeFabricacao ordemDeFabricacao) {
		this.projeto = projeto;
		this.ordemDeFabricacao = ordemDeFabricacao;
		//this.condicoesDeUsinagem = ordemDeFabricacao.getCondicoesDeUsinagem();
		//this.quantidade = ordemDeFabricacao.getQuantidade();
		this.iniciar();
	}
	public CAPP(Projeto projeto)
	{
		// CHAMAR A JANELA DAS FERRAMENTAS
		this.projeto = projeto;
		
		this.mapeadora = new MapeadoraDeWorkingsteps(this.projeto);
		this.vetorDeWorkingSteps = this.mapeadora.getWorkingsteps();
		

	}

	
	public void iniciar(){
		//mapear as features para workingsteps
		//determinar todas as sequencias possivei de workingsteps
		
		/*
		 * sequencia de operacoes
		 * 1 - mapear para workingsteps
		 * 2 - agrupar - nao � feito
		 * 3 - sequencias de ws (todas) - nao � feito
		 * 4 - movimentacao - � feito na mapeadora
		 * 5 - tempo - � feito na mapeadora
		 * 6 - gerar codigo
		 */
		
		/**
		 * 1� Etapa - Mapeamento de Workingsteps:
		 * 		Todas as features do projeto, inseridas no CAD, s�o mapeadas
		 * 		para workingsteps. S�o determinados dados como condi��es de usinagem,
		 * 		tipo de movimenta��o, pontos de controle para a movimenta��o e
		 * 		a ferramenta que ser� utilizada durante a usinagem.
		 */
		this.mapeadora = new MapeadoraDeWorkingsteps(this.projeto.getBloco().faces, this.ordemDeFabricacao.getCondicoesDeUsinagem());
		this.vetorDeWorkingSteps = this.mapeadora.getWorkingsteps();
		
		/**
		 * 2� Etapa - Agrupamento de Workingsteps em Camadas
		 * 		Os workingsteps de cada face s�o agrupados em camadas, sendo que
		 * 		cada uma destas possui workingsteps com posi��o em "Z" igual.
		 */
		this.agrWs = new AgrupadorDeWorkingSteps(vetorDeWorkingSteps);
		this.gruposCamadasDoBloco = this.agrWs.grupos;
		//this.dds = new DeterminadorDeSequencias(this.agrWs.grupos);
		
		/**
		 * 3� Etapa - Determina��o da Sequencia de Usinagem por Camada
		 * 		� determinada a melhor sequencia para a usinagem dos workingsteps
		 * 		das camadas. Esta sequencia � obtida atraves da menor distancia a ser
		 * 		percorrida pela ferramenta durante a usinagem de uma camada.
		 */
		
//		this.workingStepsOrdenadas = OrdenadoraDeSequencias.organizarWorkingSteps(gruposCamadasDoBloco);
		
		this.sequenciasPorCamadasDoBloco = OrdenadoraDeSequencias.determinarSequenciasCamadas(gruposCamadasDoBloco);
		
		/**
		 * 4� Etapa - Gera��o do C�digo HPGL
		 * 		� gerado um programa em linguagem HPGL para a usinagem da pe�a projetada.
		 */
		
		this.codigoHPGL_texto = GerarCodigoHPGL.determinarCodigoCamadasHPGL(gruposCamadasDoBloco, this.sequenciasPorCamadasDoBloco, this.projeto.getDadosDeProjeto(), this.ordemDeFabricacao );
	}
	
	public void imprimeGruposCamadas(Vector gruposCamadasDoBloco)
	{
		for(int i = 0; i < gruposCamadasDoBloco.size(); i++)
		{
			Vector faceTmp = (Vector)gruposCamadasDoBloco.elementAt(i);
			for(int j = 0; j < faceTmp.size(); j++)
			{
				
			}
		}
	}
}
