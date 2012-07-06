package br.UFSC.GRIMA.cad;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import br.UFSC.GRIMA.util.projeto.Projeto;

public class LinePanel extends DesenhadorDeFaces implements MouseListener, MouseMotionListener
{
    public List<Point> pointList = new ArrayList<Point>();
    public List<Double> angulosList = new ArrayList<Double>();
	public GeneralPath poligono = new GeneralPath();

    
    private int separacaoGrade = 30;
    private String x = "";
    private String y = "";
    public LinePanel(Projeto projeto)
    {
    	super(projeto);
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
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        //N�o podemos alterar o estado do objeto g, portanto fazemos uma c�pia dele.
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
//        this.drawGrade(g2d);
        this.drawCoordinates(g2d);
        
        
        g2d.setColor(new Color(58, 100, 0));
        g2d.setColor(new Color(254, 254, 0));
        if (pointList.size() < 2)
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
		y = "" + e.getY();
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
//		System.out.println("mouse clicked");
		Point pTmp = new Point(e.getX(), e.getY());
		pointList.add(pTmp);
		 
		 
		if (pointList.size() == 1)
			poligono.moveTo(pointList.get(0).x, pointList.get(0).y);
		else
			poligono.lineTo(pTmp.x, pTmp.y);
		 
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
		g.setColor(new Color(58, 200, 0));
		g.drawString(x, 10, 20);
		g.drawString(y, 50, 20);
	}
}
