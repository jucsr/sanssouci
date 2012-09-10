package br.UFSC.GRIMA.cad;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import br.UFSC.GRIMA.util.projeto.Projeto;
/**
 * 
 * @author Jc
 *
 */
public class LinePanel extends DesenhadorDeFaces implements MouseListener, MouseMotionListener
{
    public ArrayList<Point2D> pointList = new ArrayList<Point2D>();
    public ArrayList<Point2D> pointListCC = new ArrayList<Point2D>();
    public ArrayList<Double> angulosList = new ArrayList<Double>();
	public GeneralPath poligono = new GeneralPath();
	private Line2D lineTmp = new Line2D.Double();
    	
    public double separacaoGrade = 20;
    private String x = "";
    private String y = "";
    public boolean grade = true, line = true;
    public Shape shape;
    
    
    public LinePanel(Projeto projeto)
    {
    	super(projeto);
    	this.addMouseListener(this);
    	this.addMouseMotionListener(this);
    	this.setBackground(new Color(20, 20, 20));
//    	addKeyListener(this);
    }
    /**
     * Esse metodo serah chamado toda vez que o componente precisar ser repintado.
     * Isso eh, quando a janela for parcial ou totalmente escondida e reexibida ou 
     * quando o paint for chamado.
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
//        this.setPreferredSize(new Dimension(tamanho.width, tamanho.height + 30));
        //Nao podemos alterar o estado do objeto g, portanto fazemos uma copia dele.
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		
		g2d.setStroke(new BasicStroke());
		if(grade)
			this.drawGrade(g2d);
		
        this.drawCoordinates(g2d);
       
        if(shape != null)
        {
        	g2d.setColor(new Color(255, 69, 0));
        	g2d.draw(shape);
        }
        g2d.setColor(new Color(58, 100, 0));
        g2d.setColor(new Color(178, 34, 34));
        g2d.setColor(Color.BLACK);
//        if (pointList.size() < 2)
//            return;
        
        g2d.draw(poligono);
        if(line)
        	g2d.draw(lineTmp);
        
        g2d.dispose();
    }

	@Override
	public void mouseDragged(MouseEvent e) 
	{

	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		x = "" + (e.getX() - 20) / getZoom();
		y = "" + ((projeto.getBloco().getComprimento() * getZoom() - e.getY() + 20) / getZoom());
		
		double x = Double.parseDouble(this.x) * getZoom() + 20;
		double y = Double.parseDouble(this.y) * getZoom() + 20;
		if(pointList.size() > 0)
		{
			Point2D p0 = new Point2D.Double(pointList.get(pointList.size() - 1).getX() * getZoom() + 20, pointList.get(pointList.size() - 1).getY() * getZoom() + 20);
			Point2D p1 = new Point2D.Double(x, y);
			lineTmp = new Line2D.Double(p0, p1);
		}
		
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		Point2D pTmp = new Point2D.Double(Double.parseDouble(x), Double.parseDouble(y));

		pointList.add(pTmp);
		 
		 
		if (pointList.size() == 1)
			poligono.moveTo(pointList.get(0).getX() * getZoom() + 20, pointList.get(0).getY() * getZoom() + 20);
		else
			poligono.lineTo(pTmp.getX() * getZoom() + 20, pTmp.getY() * getZoom() + 20);
		 
       repaint(); //E pedimos para o painel se repintar.
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{

	}
	@Override
	public void mouseExited(MouseEvent e) 
	{

	}
	@Override
	public void mousePressed(MouseEvent e) 
	{

	}
	@Override
	public void mouseReleased(MouseEvent e)
	{

	}
	private void drawGrade(Graphics2D g)
	{
		g.setColor(new Color(156, 156, 156));
		Dimension dimension = this.getSize();
		
		double nHorizontal = dimension.width / separacaoGrade;
		double nVertical = dimension.height / separacaoGrade;
		
		for (int i = 0; i <= nVertical; i++)
		{
			g.drawLine(0, (int)(20 + i * separacaoGrade * getZoom()), dimension.width, (int)(20 + i * separacaoGrade * getZoom()));
		}
		for(int i = 0; i <= nHorizontal; i++)
		{
			g.drawLine((int)(20 + i * separacaoGrade * getZoom()), 0, (int)(20 + i * separacaoGrade * getZoom()), dimension.height);
		}
	}
	private void drawCoordinates(Graphics2D g)
	{
		g.scale(1, -1);
		g.setColor(new Color(139, 90, 0));
		g.setFont(new Font("Arial", Font.BOLD, 12));
//		g.drawString("x:\t" + x, 10, 20);
//		g.drawString("y:\t" + y, 80, 20);
		int x, y;
		try
		{
			x = (int)Double.parseDouble(this.x);
			y = (int)Double.parseDouble(this.y);
			g.drawString("x:\t" + this.x, (int)(x * getZoom() + 20), -(int)(y * getZoom() + 20 + 13));
			g.drawString("y:\t" + this.y, (int)(x * getZoom() + 20), -(int)(y * getZoom() + 20 + 2));
		} catch(Exception e)
		{
			
		}
		g.scale(1, -1);
	}
//	@Override
//	public void keyPressed(KeyEvent e) 
//	{
//
//		if(e.getKeyCode() == KeyEvent.VK_F9)
//		{
//			System.out.println("========= KEYYYYYYYYYy");
//		}
//	}
//	@Override
//	public void keyReleased(KeyEvent e) 
//	{
//		
//	}
//	@Override
//	public void keyTyped(KeyEvent e) 
//	{
//		
//	}
}
