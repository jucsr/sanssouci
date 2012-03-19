package br.UFSC.GRIMA.serialPortProgram;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

import br.UFSC.GRIMA.bancoDeDados.AcessaBD;
import br.UFSC.GRIMA.serialPortProgram.view.SerialPortProgramJFrame_view;




public class SerialPortProgramJFrame extends SerialPortProgramJFrame_view{
	
	
	
	
	
	
	class AbrirPeloBD extends JFrame implements ActionListener
	{
		    JLabel L1;
			JButton B2,B3;
			JComboBox TA1;
			JPanel P1;
			AcessaBD bancoDeDados;
			
			
			
						
			
			public AbrirPeloBD() {
				bancoDeDados = new AcessaBD(0);
				setTitle("Abrir ordem pelo servidor");
				setResizable(false);
				getContentPane().setLayout (new FlowLayout());
				L1 = new JLabel("Ordens:");
				L1.setForeground(Color.black);		
				B2 = new JButton("Abrir");
				B3 = new JButton("Cancelar");		
				B2.addActionListener(this);
				B3.addActionListener(this);
				P1 = new JPanel();
				P1.setLayout (new FlowLayout());
				TA1 = new JComboBox(bancoDeDados.listaOrdem());
				TA1.setEnabled(true);
				getContentPane().add(L1);
				getContentPane().add(TA1);
				getContentPane().add(B2);
				getContentPane().add(B3);
				getContentPane().add(P1);
				setLocation(200,200);
				setResizable(true);
				pack();
			}



			public void actionPerformed(ActionEvent e)
			{
				
				if (e.getSource() == B3) //cancelar
				{
				dispose();
				}
				
				if (e.getSource() == B2) //abrir
				{
					
					 try{
			        	  Object[] in = HPGLFileReader.getDataBD(bancoDeDados.getOrdemPorID(TA1.getSelectedIndex()));
			        	  m_dadosDeProjeto = (DadosDeProjeto)in[0];
			        	  m_hpglFacesCode = (Vector)in[1];
			        	  SerialPortProgramJFrame.this.setState(State.ENABLED);
			        
			          }
			          catch(Exception ex){
			        	  ex.printStackTrace();
			        	JOptionPane.showMessageDialog(null, "Erro ao conectar com o banco de dados \n tente novamente mais tarde.", "Erro", JOptionPane.ERROR_MESSAGE);  
			          }
					dispose();
					
				}
			}
			
			
		
	} 
	
	
	
	

	class Pergunta extends JFrame implements ActionListener
	{
		   
			JButton Sim,Nao;
			AcessaBD bancoDeDados;
			
			
			
						
			
			public Pergunta() {
				bancoDeDados = new AcessaBD(0);
				setTitle("Abrir ordem pelo servidor");
				setResizable(false);
				getContentPane().setLayout (new FlowLayout());						
				Sim = new JButton("Sim");
				Nao = new JButton("Nao");		
				Sim.addActionListener(this);
				Nao.addActionListener(this);			
				getContentPane().add(Sim);
				getContentPane().add(Nao);
				
				setLocation(200,200);
				setResizable(true);
				pack();
			}

			public void actionPerformed(ActionEvent e)
			{
				
				if (e.getSource() == Sim)
				{
				//	br.UFSC.GRIMA.bancoDeDados.incPronta();
				dispose();
				}
				
				if (e.getSource() == Nao) 
				{
					
					
					dispose();
					
				}
			}
			
			
		
	} 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private enum State {
		ENABLED, DISABLED
	}
	
	private String[] m_names = {"Face XY", "Face YZ", "Face XZ", "Face YX", "Face ZY", "Face ZX"};
	
	private State m_state;
	private Vector m_hpglFacesCode;
	private DadosDeProjeto m_dadosDeProjeto;

	
	public SerialPortProgramJFrame() {
		this.init();
	}
	
	public void init(){
		UIManager.LookAndFeelInfo[] looks =
			UIManager.getInstalledLookAndFeels();
		try {
			UIManager.setLookAndFeel(looks[2].getClassName());
			SwingUtilities.updateComponentTreeUI(this);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(this.m_escolherFaceRadioButton);
		bg.add(this.m_nextRadioButton);
		
		this.initListeners();
		this.setState(State.DISABLED);
	}
	
	public void initListeners(){
		SerialPortProgramJFrame_actionAdapter actionListener = new SerialPortProgramJFrame_actionAdapter(this);
		
		this.m_abiriMenuItem.addActionListener(actionListener);
		this.menuItem1.addActionListener(actionListener);
	
		this.m_sairMenuItem.addActionListener(actionListener);
		this.m_ajudaMenuItem.addActionListener(actionListener);
		
		this.m_escolherFaceRadioButton.addActionListener(actionListener);
		this.m_nextRadioButton.addActionListener(actionListener);
		this.m_visualizarButton.addActionListener(actionListener);
		this.m_usinarButton.addActionListener(actionListener);
		
	}
	
	/**
	 * Seta o estado da janela.
	 * 
	 * 
	 * @param state
	 */
	public void setState(State state){
		this.m_state = state;
		
		switch(this.m_state){
			case DISABLED:
				this.m_escolherFaceRadioButton.setEnabled(false);
				this.m_nextRadioButton.setEnabled(false);
				this.m_visualizarButton.setEnabled(false);
				this.m_usinarButton.setEnabled(false);
				this.m_facesComFeaturesComboBox.setEnabled(false);
				this.m_escolherFaceComboBox.setEnabled(false);
				this.m_proximaFaceLabel.setEnabled(false);
				this.label1.setEnabled(false);
				this.label2.setEnabled(false);
				this.label4.setEnabled(false);
				this.label5.setEnabled(false);
				this.label6.setEnabled(false);
				this.label7.setEnabled(false);
				this.m_userName.setEnabled(false);
				this.m_projectName.setEnabled(false);
				this.m_material.setEnabled(false);
				this.m_quantity.setEnabled(false);
				break;
			case ENABLED:
				this.m_escolherFaceRadioButton.setEnabled(true);
				this.m_nextRadioButton.setEnabled(true);
				this.m_visualizarButton.setEnabled(true);
				this.m_usinarButton.setEnabled(true);
				this.m_facesComFeaturesComboBox.setEnabled(true);
				this.m_escolherFaceComboBox.setEnabled(true);
				this.m_proximaFaceLabel.setEnabled(true);
				this.label1.setEnabled(true);
				this.label2.setEnabled(true);
				this.label4.setEnabled(true);
				this.label5.setEnabled(true);
				this.label6.setEnabled(true);
				this.label7.setEnabled(true);
				this.m_userName.setEnabled(true);
				this.m_projectName.setEnabled(true);
				this.m_material.setEnabled(true);
				this.m_quantity.setEnabled(true);
				
				this.updatePanel();
				
				break;
			default:
				break;
		}
	}
	
	public void updatePanel(){
		this.m_userName.setText(this.m_dadosDeProjeto.getUserName());
		this.m_projectName.setText(this.m_dadosDeProjeto.getProjectName());
		this.m_material.setText(this.m_dadosDeProjeto.getMaterial().toString());
		this.m_quantity.setText(this.m_dadosDeProjeto.getQuantidade() + "");
		
		for(int i = 0; i < this.m_hpglFacesCode.size(); i++){
			String codeTmp = (String)this.m_hpglFacesCode.elementAt(i);

			String comboBoxItem = "";
			if(!codeTmp.equals("")){
				switch(i){	
					case 0:
						comboBoxItem = "Face XY";
						break;
					case 1:
						comboBoxItem = "Face YZ";
						break;
					case 2:
						comboBoxItem = "Face XZ";
						break;
					case 3:
						comboBoxItem = "Face YX";
						break;
					case 4:
						comboBoxItem = "Face ZY";
						break;
					case 5:
						comboBoxItem = "Face ZX";
						break;
					default:
						break;
				}
				
				this.m_facesComFeaturesComboBox.addItem(comboBoxItem);
				this.m_escolherFaceComboBox.addItem(comboBoxItem);
			}
		}
		
		this.pack();
	}

	public void actionPerformed(ActionEvent evt){
		Object source = evt.getSource();
		
		if(source.equals(this.m_abiriMenuItem)){	
			JFileChooser chooser = new JFileChooser(".");
	        FileFilter type1 = new ExtensionFilter("Arquivo CAD HPGL", ".chf");
	
	        chooser.addChoosableFileFilter(type1);
	        chooser.setFileFilter(type1); // Initial filter setting
	
	        int status = chooser.showOpenDialog(SerialPortProgramJFrame.this);
	        if (status == JFileChooser.APPROVE_OPTION) {
	          File file = chooser.getSelectedFile();
	          try{
	        	  Object[] in = HPGLFileReader.getFileData(file);
	        	  this.m_dadosDeProjeto = (DadosDeProjeto)in[0];
	        	  this.m_hpglFacesCode = (Vector)in[1];
	        	  this.setState(State.ENABLED);
	          }
	          catch(Exception ex){
	        	JOptionPane.showMessageDialog(null, "Tipo de arquivo n�o suportado.", "Erro", JOptionPane.ERROR_MESSAGE);  
	          }
	        }
		}
		
		else if(source.equals(this.menuItem1)){
			final JFrame Janela = new AbrirPeloBD();
			WindowListener x = new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					Janela.dispose();
				}
			};
			Janela.addWindowListener(x);
			Janela.show();
		}
		else if(source.equals(this.m_sairMenuItem)){
			this.dispose();
		}
		else if(source.equals(this.m_ajudaMenuItem)){
			try{
				Runtime.getRuntime().exec("cmd.exe /C start iexplore.exe http://www.grima.ufsc.br/~jticona/CAD/ajuda.php");
				}
				catch(Exception e){
				e.printStackTrace();
				}
		}
		else if(source.equals(this.m_escolherFaceRadioButton)){
			
		}
		else if(source.equals(this.m_nextRadioButton)){
			
		}
		else if(source.equals(this.m_visualizarButton)){
			String faceTmp = (String)this.m_facesComFeaturesComboBox.getSelectedItem();
			String faceCode;
						
			int faceIndex = -1;
			
			for(int i = 0; i < this.m_names.length; i++){
				if(faceTmp.equals(this.m_names[i])){
					faceIndex = i;
					break;
				}	
			}
			
			if(faceIndex != -1){
				faceCode = (String)this.m_hpglFacesCode.elementAt(faceIndex);
				JFrame frame = new JFrame(this.m_names[faceIndex]);
				frame.setSize(200,300);
				
				JScrollPane scrollPane = new JScrollPane();
				JTextArea area = new JTextArea();
				area.setText(faceCode);
				scrollPane.setViewportView(area);
				
				frame.getContentPane().add(scrollPane);
				
				frame.setVisible(true);
			}
				
		}
		else if(source.equals(this.m_usinarButton)){
			Runtime rt = Runtime.getRuntime();
			Process p = null;
			String portname = "com1:";
			// for Win95 : c:\\windows\\command.com
			//             c:\\windows\\command\\mode.com   
			String[] cmd = {
					"c:\\WINDOWS\\system32\\cmd.exe", "/c",
					"start", "/min",
					"c:\\WINDOWS\\system32\\mode.com", portname,
					"baud=9600", "parity=n", "data=8",
					"stop=1", 
			};
			
			String faceTmp = (String)this.m_facesComFeaturesComboBox.getSelectedItem();
			String faceCode;
						
			int faceIndex = -1;
			
			for(int i = 0; i < this.m_names.length; i++){
				if(faceTmp.equals(this.m_names[i])){
					faceIndex = i;
					break;
				}	
			}
			String osName = System.getProperty("os.name");
			if(faceIndex != -1 && osName.equals("Windows XP")){
				faceCode = (String)this.m_hpglFacesCode.elementAt(faceIndex);
				
				try {
					p = rt.exec( cmd );
					if( p.waitFor() != 0 ) {
						System.out.println("Error executing command: " + cmd );
						System.exit( -1 );
					}
					byte data[] = 
						faceCode.getBytes();
					FileOutputStream fos = new FileOutputStream( portname );
					BufferedOutputStream bos = new BufferedOutputStream( fos );
					fos.write( data, 0, data.length );
					fos.close();
					if(true){//janela pergunta se a peca foi usinada com sucesso
						
					}
				}
				catch( Exception e ) {
					e.printStackTrace();
				}
			}else{
				JOptionPane.showMessageDialog(null, "O seu sistema operacional deve ser Windows XP \n para o correto funcionamento do programa.", "Erro", 0);
			}
		}
		else{
			//erro
		}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SerialPortProgramJFrame ff = new SerialPortProgramJFrame();
		ff.setVisible(true);
		
	}

}

class SerialPortProgramJFrame_actionAdapter implements ActionListener{
	private SerialPortProgramJFrame adaptee;
	
	public SerialPortProgramJFrame_actionAdapter(SerialPortProgramJFrame adaptee){
		this.adaptee = adaptee;
	}
	
	public void actionPerformed(ActionEvent evt){
		this.adaptee.actionPerformed(evt);
	}
}

class ExtensionFilter extends FileFilter {
    private String extensions[];

    private String description;

    public ExtensionFilter(String description, String extension) {
      this(description, new String[] { extension });
    }

    public ExtensionFilter(String description, String extensions[]) {
      this.description = description;
      this.extensions = (String[]) extensions.clone();
    }

    public boolean accept(File file) {
      if (file.isDirectory()) {
        return true;
      }
      int count = extensions.length;
      String path = file.getAbsolutePath();
      for (int i = 0; i < count; i++) {
        String ext = extensions[i];
        if (path.endsWith(ext)
            && (path.charAt(path.length() - ext.length()) == '.')) {
          return true;
        }
      }
      return false;
    }

    public String getDescription() {
      return (description == null ? extensions[0] : description);
    }
  }
