package br.UFSC.GRIMA.util.drawLines;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Esse � s� um frame para exibir o painel.
 * O "quente" do c�digo est� l�. 
 * N�o tem nada o que entender aqui.
 */
public class DemoFrame extends JFrame
{
    public DemoFrame()
    {
    	Toolkit toolKit = Toolkit.getDefaultToolkit();
    	Dimension d = toolKit.getScreenSize();
        setSize(d.width/2, d.height/2);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(new LinePanel(), BorderLayout.CENTER); //Aqui adicionamos o frame no painel.
        add(new JLabel("Use o mouse para desenhar"), BorderLayout.NORTH);        
    }
    
    public static void main(String[] args)
    {
        new DemoFrame().setVisible(true);
    }
}
