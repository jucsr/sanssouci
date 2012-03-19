package br.UFSC.GRIMA.capp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JOptionPane;

import br.UFSC.GRIMA.cad.JanelaPrincipal;
import br.UFSC.GRIMA.cad.visual.AlterarPosicaoFrame;

public class AlterarPosicaoWs extends AlterarPosicaoFrame implements ActionListener{

	private JanelaPrincipal janelaPrincipal;
	private Vector<Workingstep> workingstepsFace;
	private int indexWs;

	public AlterarPosicaoWs(JanelaPrincipal janelaPrincipal, Vector<Workingstep> workingstepsFace, int indexWS) {
		
		super(janelaPrincipal);
		
		this.janelaPrincipal = janelaPrincipal;
		this.workingstepsFace = workingstepsFace;
		this.indexWs = indexWS;
		
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		this.labelNumTotal.setText(workingstepsFace.size()+"");
		this.labelPosAtual.setText(indexWS+"");
		
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Object object = e.getSource();
		if(object == okButton)
		{
			this.ok();
		} else if (object == cancelButton)
		{
			this.dispose();
		}
	}

	private void ok() {

		int posDesejada = (Integer) this.spinnerPosDesejada.getValue();

		if(posDesejada>=this.workingstepsFace.size())
			posDesejada = this.workingstepsFace.size()-1;

		Workingstep wsTmp = this.workingstepsFace.get(indexWs);

		Workingstep wsPrecedente = wsTmp.getWorkingstepPrecedente();

		Workingstep wsPoscedente = null;
		
		for(int j = 0; j<this.workingstepsFace.size(); j++){
			
			Workingstep wsTmpp = this.workingstepsFace.get(j);
			
			if(wsTmpp.getWorkingstepPrecedente()!= null){
			
				if(wsTmpp.getWorkingstepPrecedente().equals(wsTmp)){
				
					wsPoscedente = wsTmpp;
				
				}
			}
		}
		
		int indexPrecedente = this.workingstepsFace.indexOf(wsPrecedente);

		if(wsPoscedente==null){
			
			if(indexPrecedente<posDesejada){ 

				this.workingstepsFace.remove(indexWs);
				this.workingstepsFace.add(posDesejada, wsTmp);

				this.janelaPrincipal.atualizarArvoreCAPP();
				this.janelaPrincipal.atualizarArvore();

				this.dispose();

			}else{
				
				JOptionPane.
				showConfirmDialog(
						null,
						"Nâo é possível realizar esta alteração." +
						"\nO seguinte Machining Workingstep deve estar após o seguinte Machining Workingstep:" +
						"\n"+" WS - "+indexPrecedente+" :"+wsPrecedente.getId(),
						"Alteração Inconsistente",
						JOptionPane.OK_CANCEL_OPTION);
				
			}

		}else{

			int indexPoscedente = this.workingstepsFace.indexOf(wsPoscedente);

			if(indexPrecedente<posDesejada && posDesejada<indexPoscedente){ 

				this.workingstepsFace.remove(indexWs);
				this.workingstepsFace.add(posDesejada, wsTmp);

				this.janelaPrincipal.atualizarArvoreCAPP();
				this.janelaPrincipal.atualizarArvore();

				this.dispose();

			}else{

				JOptionPane.
				showConfirmDialog(
						null,
						"Nâo é possível realizar esta alteração." +
						"\nO seguinte Machining Workingstep deve estar entre os seguintes Machining Workingsteps:" +
						"\n"+" WS - "+indexPrecedente+" :"+wsPrecedente.getId() +
						"\n"+" WS - "+indexPoscedente+" :"+wsPoscedente.getId(),
						"Alteração Inconsistente",
						JOptionPane.OK_CANCEL_OPTION);

			}
		}
	}

}
