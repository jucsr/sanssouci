package br.UFSC.GRIMA.cad;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import br.UFSC.GRIMA.cad.visual.JanelaOrdemDeFabricacao;
import br.UFSC.GRIMA.capp.CAPP;
import br.UFSC.GRIMA.capp.CondicoesDeUsinagem.Material;
import br.UFSC.GRIMA.util.projeto.OrdemDeFabricacao;

public class OrdemDeFabricacaoJDialog extends JanelaOrdemDeFabricacao implements ActionListener{
	private JanelaPrincipal parent;
	private Toolkit m_toolkit;

	public OrdemDeFabricacaoJDialog(JanelaPrincipal owner) {
		// TODO Auto-generated constructor stub
		super(owner);

		this.parent = owner;

		this.m_toolkit = Toolkit.getDefaultToolkit();

		this.adjustSize();
		this.adjustPosition();
		this.init();

		this.setVisible(true);
	}

	public void adjustSize(){
		this.pack();
	}

	//centraliza a janela no desktop do usuario
	public void adjustPosition(){
		Dimension screenSize = this.m_toolkit.getScreenSize();
		Dimension size = this.getPreferredSize();

		int posX = (int)(screenSize.width - size.getWidth())/2;
		int posY = (int)(screenSize.height - size.getHeight())/2;

		this.setLocation(posX, posY);
		System.out.println(this.getLocation().toString());
	}

	public void init(){
		this.initListeners();
	}

	public void initListeners(){
		//OrdemDeFabricacaoJFrame_actionAdapter ouvidor = new OrdemDeFabricacaoJFrame_actionAdapter(this);
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
	}


	public void actionPerformed(ActionEvent evt1) {

		Object source = evt1.getSource();

		boolean ok = false;

		if(source.equals(this.okButton)){	

			int quantidade = (Integer) this.spinner1.getValue();


			if(this.parent.isDoneCAPP()){

				if(quantidade>0){

					ok = true;

				}else{

					JOptionPane.showMessageDialog(null, "Quantidade de peças desejadas deve ser maior que zero.", "Error", JOptionPane.ERROR_MESSAGE);

				}

			}else{

				int i = JOptionPane
				.showConfirmDialog(
						null,
						"Você deve antes criar um Plano do Processo (CAPP).\n" +
						"Deseja criá-lo agora?",
						"Plano do Processo Ausente",
						JOptionPane.OK_CANCEL_OPTION);


				if(i==JOptionPane.OK_OPTION){

					this.parent.gerarCAPP();

				}
			}

			if(ok){

				OrdemDeFabricacao ordem = new OrdemDeFabricacao(quantidade, this.parent.getProjeto().getDadosDeProjeto().getMaterial());

				this.parent.getProjeto().addOrdemDeFabricacao(ordem);

				////inserir no bd
				ByteArrayOutputStream bout = new ByteArrayOutputStream();
				ObjectOutputStream out;
				try {
					out = new ObjectOutputStream(bout);
					out.writeObject(this.parent.getProjeto());
					out.flush();
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Não foi possivel serializar o objeto.", "Error", JOptionPane.ERROR_MESSAGE);
				}

				byte[] b1 = bout.toByteArray();

				String s1;

				try {

					s1 = parent.write(b1);

					parent.getBancoDeDados().insertOrdem(parent.getProjeto(),s1,ordem.getMaterial().getName(),ordem.getQuantidade());

				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "" +
							"Erro ao enviar ordem de fabricação ao servidor \n" +
							"Tente novamente mais tarde.", "Error", JOptionPane.ERROR_MESSAGE);	
				}

				JOptionPane.showMessageDialog(null, "Ordem enviada com sucesso","Ordem de Fabricação", JOptionPane.INFORMATION_MESSAGE);

				try {
					this.parent.salvarNoBD();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					
					JOptionPane.showMessageDialog(null, "" +
							"Erro ao salvar projeto no servidor \n" +
							"Tente novamente mais tarde.", "Error", JOptionPane.ERROR_MESSAGE);	
					
					e.printStackTrace();
				}
				
				this.parent.gerenciarOrdem(this);

			}
		}
		else if(source.equals(this.cancelButton)){
			//fecha a janela
			parent.gerenciarOrdem(this);

		}

	}

}
