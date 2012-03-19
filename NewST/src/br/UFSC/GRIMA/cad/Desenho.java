package br.UFSC.GRIMA.cad;
import java.awt.*;
import javax.swing.*;
public class Desenho extends JPanel
{
	public Desenho()
	{
		this.setBackground(Color.BLUE);
	}
	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		this.desenharRetangulo(g2d);
	}
	public void desenharRetangulo(Graphics2D g2d)
	{
		g2d.setColor(Color.green);
		g2d.fillRect(10, 10, 200, 100);
		g2d.setColor(Color.BLUE);
		g2d.drawRect(10, 10, 200, 100);
	}
	public static void main(String[] args)
	{
		JFrame jf = new JFrame();
		Desenho d = new Desenho();
		Container contentPane = jf.getContentPane();
		contentPane.add(d);
		d.repaint();
		jf.setVisible(true);
	}
}