package br.UFSC.GRIMA.util.drawLines;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class LinePanel extends JPanel implements MouseListener, MouseMotionListener
{
//    public List<Point> pointList = new ArrayList<Point>();
    public ArrayList<Point2D> pointList2d = new ArrayList<Point2D>();
    public ArrayList<Double> angulosList = new ArrayList<Double>();
	public GeneralPath poligono = new GeneralPath();
	public ArrayList<Point2D> pointList2dCC = new ArrayList<Point2D>();
	
	public GeneralPath triangulo = new GeneralPath();
	public ArrayList<ArrayList<Point2D>> triangulos = new ArrayList<ArrayList<Point2D>>();
    
    private int separacaoGrade = 25;
    private String x = "";
    private String y = "";
    public LinePanel()
    {
    	this.addMouseListener(this);
    	this.addMouseMotionListener(this);
    	this.setBackground(new Color(20, 20, 20));
    }
//    {
//        addMouseListener(new MouseAdapter()
//        {
//            @Override
//            public void mouseClicked(MouseEvent e)
//            {
//                //Ap�s cada clique, guardamos a posi��o do ponto onde foi clicado.
//                if (e.getClickCount() == 1)
//                {
//                    pointList.add(new Point(e.getX(), e.getY()));
//                    repaint(); //E pedimos para o painel se repintar.
//                }
//            }
//        });
//    };

    /**
     * Esse m�todo ser� chamado toda vez que o componente precisar ser repintado.
     * Isso �, quando a janela for parcial ou totalmente escondida e reexibida ou 
     * quando o paint for chamado.
     */
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        //N�o podemos alterar o estado do objeto g, portanto fazemos uma c�pia dele.
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.scale(1, -1);
        g2d.translate(0, -this.getHeight());
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        this.drawGrade(g2d);
        
        this.drawCoordinates(g2d);
        
        
        g2d.setColor(new Color(58, 100, 0));
        g2d.setColor(new Color(254, 254, 0));
        if (pointList2d.size() < 2)
            return;

        // Percorre a nossa lista, pintando de dois em dois pontos.
//        Point lastPoint = pointList.get(0);
//        for (int i = 1; i < pointList.size(); i++)
//        {
//            g2d.drawLine(lastPoint.x, lastPoint.y, pointList.get(i).x, pointList.get(i).y);
//            lastPoint = pointList.get(i);
//        }
        g2d.draw(poligono);
        //E aqui destruimos a c�pia do objeto g. 
        //N�o se preocupe. O desenho feito por ele n�o ser� destru�do.
        g2d.setColor(new Color(255, 0, 0));
        drawTriangulos(g2d, triangulos);
       
        g2d.dispose();
    }

	@Override
	public void mouseDragged(MouseEvent e) {
//		System.out.println("mouse dragged");
//		 pointList.add(new Point(e.getX(), e.getY()));
//       repaint(); //E pedimos para o painel se repintar.
	}

	@Override
	public void mouseMoved(MouseEvent e) {
//		System.out.println("mouse moved");
		x = "" + e.getX();
		y = "" + (this.getHeight() - e.getY());
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
//		System.out.println("mouse clicked");
//		Point pTmp = new Point(e.getX(), e.getY());
		Point2D p2d = new Point2D.Double(e.getX(), this.getHeight() - e.getY());
		pointList2d.add(p2d);
		
//		pointList.add(pTmp);
		 
		
		if(pointList2d.size() == 1)
			poligono.moveTo(pointList2d.get(0).getX(), pointList2d.get(0).getY());
		else
			poligono.lineTo(p2d.getX(), p2d.getY());
		
//		if (pointList.size() == 1)
//			poligono.moveTo(pointList.get(0).x, pointList.get(0).y);
//		else
//			poligono.lineTo(pTmp.x, pTmp.y);
		
       repaint(); //E pedimos para o painel se repintar.
	}

	@Override
	public void mouseEntered(MouseEvent e) {
//		System.out.println("mouse entered");

	}

	@Override
	public void mouseExited(MouseEvent e) {
//		System.out.println("mouse exited");

	}

	@Override
	public void mousePressed(MouseEvent e) {
//		System.out.println("mouse pressed");

	}

	@Override
	public void mouseReleased(MouseEvent e) {
//		System.out.println("mouse released");

	}
	private void drawGrade(Graphics2D g)
	{
		g.setColor(new Color(205, 205, 205));
		g.setColor(new Color(60, 60, 60));
		Dimension dimension = this.getSize();
		
		int nHorizontal = dimension.width / separacaoGrade;
		int nVertical = dimension.height / separacaoGrade;
		
		for (int i = 0; i <= nVertical; i++)
		{
			g.drawLine(0, i * separacaoGrade, dimension.width, i * separacaoGrade);
		}
		for(int i = 0; i <= nHorizontal; i++)
		{
			g.drawLine(i * separacaoGrade, 0, i * separacaoGrade, dimension.height);
		}
	}
	private void drawCoordinates(Graphics2D g)
	{
		g.scale(1, -1);
		g.setColor(new Color(58, 200, 0));
		g.drawString(x, 10, -20);
		g.drawString(y, 50, -20);
		g.scale(1, -1);
	}
	private void drawTriangulos(Graphics2D g2d, ArrayList<ArrayList<Point2D>> triangulos)
	{
		for(int i = 0; i < triangulos.size(); i++)
		{
			GeneralPath trianguloTmp = new GeneralPath();
			trianguloTmp.moveTo(triangulos.get(i).get(0).getX(), triangulos.get(i).get(0).getY());
			for(int j = 1; j < triangulos.get(i).size(); j++)
			{
				trianguloTmp.lineTo(triangulos.get(i).get(j).getX(), triangulos.get(i).get(j).getY());
			}
			trianguloTmp.closePath();
			g2d.draw(trianguloTmp);
			
		}
	}
}
