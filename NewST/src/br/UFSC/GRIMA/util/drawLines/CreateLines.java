package br.UFSC.GRIMA.util.drawLines;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CreateLines extends LineFrame implements ActionListener
{
	static LinePanel linePanel = new LinePanel();
	static GeneralClosedPocket generalPocket = new GeneralClosedPocket();
	static ArrayList<Point2D> poligonoAuxiliar = new ArrayList<Point2D>();// --> poligono com os vertices arredondados para triangulacao
	static ArrayList<ArrayList<Point2D>> triangles = new ArrayList<ArrayList<Point2D>>();
	//static ArrayList<int>;
	
	public double radius = 0;

	
	public CreateLines(Frame frame)
	{
		super(frame);
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		this.button1.addActionListener(this);
		this.button2.addActionListener(this);
		super.radiusSpinner.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e) {
				radius = (Double)radiusSpinner.getValue();
//				System.out.println("CCPass: " + linePanel.pointList2dCC);
				ArrayList<Point2D> novaLista = transformPolygonInRoundPolygon(linePanel.pointList2dCC, radius);
				linePanel.poligono = new GeneralPath();
				
				linePanel.angulosList = new ArrayList<Double>();
				
				GeneralPath forma = new GeneralPath();
				forma.moveTo(linePanel.pointList2dCC.get(0).getX(), linePanel.pointList2dCC.get(0).getY());
				for(int i = 0; i < linePanel.pointList2dCC.size(); i++)
				{
					forma.lineTo(linePanel.pointList2dCC.get(i).getX(), linePanel.pointList2dCC.get(i).getY());
				}
				forma.closePath();
				
				double alfa;
				Point2D p0 = null, p1 = null, p2 = null;
				for (int i = 0; i < linePanel.pointList2dCC.size(); i++) {
					p0 = linePanel.pointList2dCC.get(i);
					try 
					{
						p1 = linePanel.pointList2dCC.get(i - 1);
					} catch (Exception ex) {
						p1 = linePanel.pointList2dCC.get(linePanel.pointList2dCC.size() - 1);
					}
					try 
					{
						p2 = linePanel.pointList2dCC.get(i + 1);
					} catch (Exception ex) 
					{
						p2 = linePanel.pointList2dCC.get(0);
					}
					alfa = solveAngle(p0, p1, p2, linePanel.poligono);			
//					System.err.println("alfa = " + alfa * 180 / Math.PI);
					linePanel.angulosList.add(alfa);
				}
				for(int i = 0; i < linePanel.pointList2dCC.size(); i++)
				{
//					linePanel.angulosList.add(i);
					
				}
				
				linePanel.poligono.moveTo(novaLista.get(0).getX(), novaLista.get(0).getY());

				for(int i = 1; i < novaLista.size(); i++)
				{
					linePanel.poligono.lineTo(novaLista.get(i).getX(), novaLista.get(i).getY());
				}
				linePanel.poligono.closePath();
				linePanel.repaint();
			}
		});
		
		Toolkit toolKit = Toolkit.getDefaultToolkit();
    	Dimension d = toolKit.getScreenSize();
        setSize(d.width/2, d.height/2);
        setLocationRelativeTo(null);
	}
	public static void main(String [] args)
	{
		CreateLines create = new CreateLines(null);
		create.contentPanel.add(linePanel);
		create.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object o = e.getSource();
		if(o.equals(okButton))
		{
			this.ok();
		} 
		else if(o.equals(cancelButton))
		{
			this.dispose();
		}
		else if(o.equals(button1))
		{
			this.closeCurve();
		}
		else if(o.equals(button2))
		{
			this.help();
		}
	}
	private void closeCurve() 
	{
		try{
		if(linePanel.pointList2d.get(0) != linePanel.pointList2d.get(linePanel.pointList2d.size() - 1) && linePanel.pointList2d.size() > 2)
		{
			linePanel.poligono.closePath();
			
			this.button1.setEnabled(false);
			linePanel.removeMouseListener(linePanel);
		}
		else
			JOptionPane.showMessageDialog(null, "O poligono deve ter mais de dois pontos");
		
		linePanel.pointList2dCC = transformPolygonInCounterClockPolygon(linePanel.pointList2d);
		//System.out.println("OR = " + linePanel.pointList2d);
		//System.out.println("CC = " + linePanel.pointList2dCC);
		linePanel.repaint();
		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "Voce ainda nao definiu os pontos do poligono!");
		}
	}
	private void help() 
	{
		
	}
	private void ok() 
	{
		if(this.validateAngles())
		{
//			dispose();
//			generalPocket.setPoints(poligonoAuxiliar);
//			linePanel.poligono = new GeneralPath();
//			
//			linePanel.poligono.moveTo(generalPocket.getPoints().get(0).getX(), generalPocket.getPoints().get(0).getY());
//			for(int i = 0; i < generalPocket.getPoints().size(); i++)
//			{
//				linePanel.poligono.lineTo(generalPocket.getPoints().get(i).getX(), generalPocket.getPoints().get(i).getY());
//			}
//			
//			linePanel.poligono.closePath();
//			linePanel.repaint();
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Determinate the concordance radius (radius should be > 0)!");
		}
		/*
		GeneralPath forma = new GeneralPath();
		forma.moveTo(poligonoAuxiliar.get(0).getX(), poligonoAuxiliar.get(0).getY());
		for (int j=1;j<poligonoAuxiliar.size();j++)
		{
			forma.lineTo(poligonoAuxiliar.get(j).getX(), poligonoAuxiliar.get(j).getY());
		}
		forma.closePath();
		*/
		triangulation(poligonoAuxiliar);
		
//		linePanel.poligono = new GeneralPath();
//		
//		linePanel.poligono.moveTo(triangles.get(0).get(0).getX(), triangles.get(0).get(0).getY());
//		
//		for(int i = 0; i < triangles.size(); i++)
//		{
//			for(int j = 0; j < triangles.get(i).size(); j++)
//			{
//				linePanel.poligono.lineTo(triangles.get(i).get(j).getX(), triangles.get(i).get(j).getY());
//			}
//		}
//		linePanel.poligono.closePath();		
//		linePanel.repaint();
		
		for(int i = 0; i < triangles.size(); i++)
		{
			for(int j = 0; j < triangles.get(i).size(); j++)
			{
				linePanel.triangulos.add(triangles.get(i));
			}
		}
		linePanel.repaint();
		
	}
	
	public void triangulation(ArrayList<Point2D> polygon)
	{		
		ArrayList<Point2D> oldPolygon = new ArrayList<Point2D>();
		int iPolygon=1;
		System.out.println("################################################");
		System.out.println(iPolygon+ " Polygon");
		System.out.println("################################################");

		for (int i=0;i<polygon.size();i++)
		{
			System.out.println("Adding Point:"+polygon.get(i));
			oldPolygon.add(polygon.get(i));
		}
		while (oldPolygon.size()>3)
		{
			ArrayList<Point2D> newPolygon = new ArrayList<Point2D>();
			newPolygon = cutEar(oldPolygon);
			oldPolygon = new ArrayList<Point2D>();
			iPolygon++;
			System.out.println("################################################");
			System.out.println(iPolygon+ " Polygon");
			System.out.println("################################################");
			for (int i=0;i<newPolygon.size();i++)
			{
				System.out.println("Adding Point:"+newPolygon.get(i));
				oldPolygon.add(newPolygon.get(i));
			}			
		}
		triangles.add(oldPolygon);
		System.out.println(" --------------------------------------------------");
		System.out.println(" - Triangle " + triangles.size() + " " + oldPolygon);
	}
	
	public ArrayList<Point2D> cutEar(ArrayList<Point2D> polygon)
	{
		ArrayList<Point2D> newPolygon = new ArrayList<Point2D>();
		GeneralPath forma=new GeneralPath();
		int current=0;
		newPolygon = new ArrayList<Point2D>();
		forma.moveTo(polygon.get(0).getX(), polygon.get(0).getY());
		for (int i=1;i<polygon.size();i++)
		{
			forma.lineTo(polygon.get(i).getX(), polygon.get(i).getY());
		}
		forma.closePath();
		
		if (polygon.size()==3)
		{
			for (int i =0;i< polygon.size();i++)
			{
				newPolygon.add(polygon.get(i));
			}
		}
		else
		{
			for (int i = 0; i < polygon.size(); i++)
			{
				boolean ear=false;
				ArrayList<Point2D> pTemp = new ArrayList<Point2D>();
				
				pTemp = pointIteration(i,polygon);
				ear = isEar(i, polygon);
				//System.out.println(i + " alfa " + ear +" "+ 1/Math.PI*180*solveAngle(pTemp.get(0), pTemp.get(1), pTemp.get(2), forma) + " " + pTemp.get(0));				
				
				//System.out.println(" ear " + ear);
				if (ear)
				{
					current=i;
					triangles.add(pTemp);
					System.out.println("Triangle " + triangles.size() + " " + pTemp);
					newPolygon = new ArrayList<Point2D>();
					for (int j=0;j< polygon.size();j++)
					{
						if (j!=current)
						{
							newPolygon.add(polygon.get(j));
						}
					}		
					
					break;
				}
			}
		}
		return newPolygon;
	}
	
	
	public boolean isEar(int indexPoint, ArrayList<Point2D> polygon)
	{
		boolean ear = true;
		GeneralPath tempTriangle = new GeneralPath();
		GeneralPath forma = new GeneralPath();
		
		ArrayList<Point2D> threePoints = new ArrayList<Point2D>();
		
		threePoints = pointIteration(indexPoint, polygon);
		
		System.out.println("testEar:"+threePoints.get(0)+" "+threePoints.get(1)+" "+threePoints.get(2));
		tempTriangle.moveTo(threePoints.get(0).getX(),threePoints.get(0).getY());
		tempTriangle.lineTo(threePoints.get(1).getX(),threePoints.get(1).getY());
		tempTriangle.lineTo(threePoints.get(2).getX(),threePoints.get(2).getY());
		tempTriangle.closePath();
		
		forma.moveTo(polygon.get(0).getX(),polygon.get(0).getY());
		for (int i=1;i<polygon.size();i++)
		{
			forma.lineTo(polygon.get(i).getX(),polygon.get(i).getY());
		}
		forma.closePath();
		
		double alfa = solveAngle(threePoints.get(0), threePoints.get(1), threePoints.get(2), forma);
		if (alfa < Math.PI)
		{			
			for (int i = 0; i < polygon.size(); i ++)
			{
				if (threePoints.get(0).distance(polygon.get(i))!=0&&threePoints.get(1).distance(polygon.get(i))!=0&&threePoints.get(2).distance(polygon.get(i))!=0)
				{
					if (tempTriangle.contains(polygon.get(i)))
					{
						ear=false;
						System.out.println("-Found Point within triangle:"+polygon.get(i));
						break;
					}
				}
			}			
		}
		else
		{
			ear=false;
		}
		System.out.println("alfaTestEar:"+alfa*180/Math.PI + " ear:" + ear);
		return ear;
	}

public ArrayList<Point2D> pointIteration(int index, ArrayList<Point2D> polygon)
{
	ArrayList<Point2D> pointAroundI = new ArrayList<Point2D>();
	Point2D p0 = null, pAfter = null, pBefore = null;
		
	p0 = polygon.get(index);
	try {
		pBefore = polygon.get(index - 1);
	} catch (Exception e) {
		pBefore = polygon.get(polygon.size() - 1);
	}
	try {
		pAfter = polygon.get(index + 1);
	} catch (Exception e) {
		pAfter = polygon.get(0);
	}
	
	pointAroundI.add(p0);	
	pointAroundI.add(pAfter);
	pointAroundI.add(pBefore);
	
	return pointAroundI;
}	
	private boolean validateAngles() 
	{
		boolean isValid = true;
		double raio = 0;
		double alfa;
		ArrayList<Point2D> poligonoCC = transformPolygonInCounterClockPolygon(linePanel.pointList2d);

		//System.out.println("PCC =" + poligonoCC);
		
		Point2D p0 = null, p1 = null, p2 = null;
		for (int i = 0; i < poligonoCC.size(); i++) {
			p0 = poligonoCC.get(i);
			try {
				p1 = poligonoCC.get(i - 1);
			} catch (Exception e) {
				p1 = poligonoCC.get(poligonoCC.size() - 1);
			}
			try {
				p2 = poligonoCC.get(i + 1);
			} catch (Exception e) {
				p2 = poligonoCC.get(0);
			}
			alfa = solveAngle(p0, p1, p2, linePanel.poligono);			
			//System.err.println("alfa = " + alfa * 180 / Math.PI);
			linePanel.angulosList.add(alfa);
		}

		for (int i = 0; i < linePanel.angulosList.size(); i++) {
			if (linePanel.angulosList.get(i) <= Math.PI && radius <= 0) {
				isValid = false;
				break;
			}
		}
		return isValid;
	}
	
	public static double solveAngle(Point2D p0, Point2D p1, Point2D p2, GeneralPath forma)
	{
		double distance0, distance1, distance2;
		double alfa;

		distance0 = p1.distance(p2);
		distance1 = p0.distance(p1);
		distance2 = p2.distance(p0);

		int nPoints = 20;
		double hx = (p2.getX() - p1.getX()) / nPoints;
		double hy = (p2.getY() - p1.getY()) / nPoints;
		boolean allOut;
		/*
		System.out.println("p0:"+p0);
		System.out.println("p1:"+p1);
		System.out.println("p2:"+p2);
		*/
		double v1v2=(p1.getX() - p0.getX()) * (p2.getX() - p0.getX()) + (p1.getY() - p0.getY()) * (p2.getY() - p0.getY());
		alfa = Math.acos(v1v2/(distance1 * distance2));
		//System.out.println("alfaOr="+alfa*180/Math.PI);

		int nPointsIn = 0;
		for (int iPoint = 1; iPoint < nPoints - 1; iPoint++) 
		{
			Point2D testPoint = new Point2D.Double((p1.getX() + hx * iPoint),
					(int) (p1.getY() + hy * iPoint));

			if (forma.contains(testPoint)|| linePanel.pointList2d.size()==3) 
			{
				nPointsIn++;
			}
		}

		if (nPointsIn == 0)
		{
			alfa = 2 * Math.PI - alfa;
		}
		//System.out.println("alfaAf="+alfa*180/Math.PI);
		return alfa;
	}
	
	public static ArrayList<Point2D> solveArc(GeneralPath forma, Point2D p0, Point2D p2, Point2D p1, double radius)
	{
		double alfa = solveAngle(p0, p1, p2, forma);
		double teta = Math.PI / 2 - alfa / 2;
		double h;
		ArrayList<Point2D> arcPoints = new ArrayList<Point2D>();
		/*
		System.out.println("***********************");
		System.out.println("alfa:" + alfa*180/Math.PI);
		System.out.println("P0:"+p0.getX()+","+p0.getY());
		System.out.println("P1:"+p1.getX()+","+p1.getY());
		System.out.println("P2:"+p2.getX()+","+p2.getY());
		*/

		if (alfa > Math.PI)		
		{
			arcPoints.add(p0);
			
		}
		else
		{
			Point2D a = new Point2D.Double();
			Point2D b = new Point2D.Double();
			Point2D c = new Point2D.Double();
			Point2D cc = new Point2D.Double();
	
			h = radius * Math.cos(teta);
	
			a = pointT(p0, p1, radius, alfa);
			b = pointT(p0, p2, radius, alfa);
			c.setLocation((a.getX() + b.getX()) / 2, (a.getY() + b.getY()) / 2);
	
			double dc = radius / Math.sin(alfa / 2);
	
			cc = pointAlong(p0, c, dc);
			/*
			System.out.println("Doing Arc for alpha");
			System.out.println("a:"+a.getX()+","+a.getY());
			System.out.println("b:"+b.getX()+","+b.getY());
			System.out.println("CC:"+cc.getX()+","+cc.getY());
			*/
			Point2D bb = new Point2D.Double(b.getX()-cc.getX(),b.getY()-cc.getY());
			Point2D aa = new Point2D.Double(a.getX()-cc.getX(),a.getY()-cc.getY());
	
			//System.out.println("bb:"+bb);
			//System.out.println("aa:"+aa);
			
			poligonoAuxiliar.add(b);
			poligonoAuxiliar.add(a);
			
			
			double anguloInicial=Math.atan2(bb.getY(),bb.getX()); 					
			//double anguloFinal=Math.atan2(aa.getY(),aa.getX());
			
			if (anguloInicial<0.0)
			{
				anguloInicial=anguloInicial+2*Math.PI;
			}
			
			double anguloFinal = anguloInicial + Math.PI - alfa;
			
			double deltaAngulo=Math.PI-alfa;
			/*
			if (anguloFinal<0.0)
			{
				anguloFinal=anguloFinal+2*Math.PI;
			}			
			
			if (anguloFinal<anguloInicial)
			{
				anguloFinal=anguloFinal+2*Math.PI;
			}
			*/
			
			//System.out.println("AInicial: "+anguloInicial*180/Math.PI+" AFinal: "+anguloFinal*180/Math.PI);
			double comprimentoLinha = 5;
			arcPoints=interpolarArco(cc,radius,anguloInicial, deltaAngulo, comprimentoLinha, true);		
			//p0 medio
			//p1 derecha
			//p2 izquierda
		}
		return arcPoints;
	}	
	
	public static Point2D pointAlong(Point2D p0, Point2D p1, double d)
	{
		Point2D p = new Point2D.Double();
		double distancep1 = p0.distance(p1);
		int x = (int) (p0.getX() + d * (p1.getX() - p0.getX()) / distancep1);
		int y = (int) (p0.getY() + d * (p1.getY() - p0.getY()) / distancep1);
		p.setLocation(x, y);
		return p;		
	}
	
	public static Point2D pointT (Point2D p0, Point2D p1, double radius, double alfa)
	{
		Point2D p = new Point2D.Double();
		double d = radius / Math.tan(alfa / 2);
		double x = p0.getX()+(p1.getX() - p0.getX())/ p0.distance(p1) * d;
		double y = p0.getY()+(p1.getY() - p0.getY())/ p0.distance(p1) * d;
		p.setLocation(x, y);
		return p;
	}
	public static boolean isCounterClock(List<Point> pontosPoligono)
	{
		boolean isCounterClock = true;
		double area = 0;
		
		for (int i = 0; i < pontosPoligono.size() - 1; i++)
		{
			area += (pontosPoligono.get(i).x * pontosPoligono.get(i + 1).y - pontosPoligono.get(i + 1).x * pontosPoligono.get(i).y);
		}
		area = area / 2;
//		System.out.println("AREA = " + area);
		
		if(area > 0)
			isCounterClock = true;
		else
			isCounterClock = false;
		
		return isCounterClock;
	}
	public static boolean isCounterClock(ArrayList<Point2D> pontosPoligono)
	{
		boolean isCounterClock = true;
		double area = 0;
		
		for (int i = 0; i < pontosPoligono.size() - 1; i++)
		{
			area += (pontosPoligono.get(i).getX() * pontosPoligono.get(i + 1).getY() - pontosPoligono.get(i + 1).getX() * pontosPoligono.get(i).getY());
		}
		area = area / 2;
//		System.out.println("AREA = " + area);
		
		if(area > 0)
			isCounterClock = true;
		else
			isCounterClock = false;
		
		return isCounterClock;
	}
//	public static boolean isCounterClock(GeneralPath poligono)
//	{
//		PathIterator iterator = poligono.getPathIterator(null);
//		while(iterator.isDone() == false)
//		{
//			
//			iterator.next();
//		}
//		
//		boolean isCounterClock = true;
//		double area = 0;
//		
//		for (int i = 0; i < pontosPoligono.size() - 1; i++)
//		{
//			area += (pontosPoligono.get(i).x * pontosPoligono.get(i + 1).y - pontosPoligono.get(i + 1).x * pontosPoligono.get(i).y);
//		}
//		area = area / 2;
//		
//		if(area > 0)
//			isCounterClock = true;
//		else
//			isCounterClock = false;
//		
//		return isCounterClock;
//	}
	public static List<Point> transformPolygonInCounterClockPolygon(List<Point> polygon)
	{
		ArrayList<Point> novoPoligono = new ArrayList<Point>();
		if(!isCounterClock(polygon))
		{
			for(int i = 0; i < polygon.size(); i++)
			{
				novoPoligono.add(new Point(polygon.get(polygon.size() - 1 - i)));
			}
		}
		else 
		{
			return polygon;
		}
		
		return novoPoligono;
	}
	public static ArrayList<Point2D> transformPolygonInCounterClockPolygon(ArrayList<Point2D> polygon)
	{
		ArrayList<Point2D> novoPoligono = new ArrayList<Point2D>();
		if(!isCounterClock(polygon))
		{
			for(int i = 0; i < polygon.size(); i++)
			{
				novoPoligono.add(new Point2D.Double(polygon.get(polygon.size() - 1 - i).getX(), polygon.get(polygon.size() - 1 - i).getY()));
			}
			return novoPoligono;
		}
		else 
		{
			return polygon;
		}
	}
	/**
	 * 
	 * @param centro
	 * @param anguloInicial
	 * @param deltaAngulo --> o incremento do angulo a partir do angulo inicial
	 * @param numPontos
	 * @param isCounterClock
	 * @return
	 */
	public static ArrayList<Point2D> interpolarArco(Point2D centro, double raio, double anguloInicial, double deltaAngulo, double comprimentoLinha, boolean isCounterClock)
	{
		ArrayList<Point2D> saida = new ArrayList<Point2D>();
		double incrementoAngulo, x, y;
		int numPontos;
		incrementoAngulo = comprimentoLinha/raio;
		numPontos=(int)Math.ceil(deltaAngulo/incrementoAngulo);
		incrementoAngulo = deltaAngulo/numPontos;
		
		if (!isCounterClock)
			incrementoAngulo = -incrementoAngulo;
		
		for(int i = 0; i < numPontos; i++)
		{
			x = centro.getX() + raio * Math.cos(anguloInicial + i*incrementoAngulo);
			y = centro.getY() + raio * Math.sin(anguloInicial + i*incrementoAngulo);
			saida.add(new Point2D.Double(x, y));
			
		}
		return saida;
	}
	
	public ArrayList<Point2D> transformPolygonInRoundPolygon(ArrayList<Point2D> polygon, double radius)
	{
		ArrayList<Point2D> saida = new ArrayList<Point2D>();
		double anguloTmp = 0;
		GeneralPath forma = new GeneralPath();
		forma.moveTo(polygon.get(0).getX(), polygon.get(0).getY());
		for(int i = 1; i < polygon.size(); i++)
		{
			forma.lineTo(polygon.get(i).getX(), polygon.get(i).getY());
		}
		forma.closePath();
		
		Point2D p0 = null, p1 = null, p2 = null;
		for (int i = 0; i < polygon.size(); i++)
		{
			p0 = polygon.get(i);
			try 
			{
				p1 = polygon.get(i - 1);
			} catch (Exception e) 
			{
				p1 = polygon.get(polygon.size() - 1);
			}
			try
			{
				p2 = polygon.get(i + 1);
			} catch (Exception e) 
			{
				p2 = polygon.get(0);
			}
			anguloTmp = solveAngle(p0, p1, p2, forma);
			//System.out.println("angulo = " + anguloTmp);
			if (anguloTmp < Math.PI) 
			{
				ArrayList<Point2D> arcoTmp = solveArc(forma, p0, p1, p2, radius);
				for (int j = 0; j < arcoTmp.size(); j++) 
				{
					ArrayList<Point2D> tempTriangle = new ArrayList<Point2D>();
					if (j+1<arcoTmp.size())
					{
						tempTriangle.add(arcoTmp.get(0));
						tempTriangle.add(arcoTmp.get(j));
						tempTriangle.add(arcoTmp.get(j+1));
						triangles.add(tempTriangle);
					}
					saida.add(arcoTmp.get(j));
				}
			} else 
			{
				saida.add(polygon.get(i));
				poligonoAuxiliar.add(p0);
			}
		}
		
		//System.out.println("SAIDA: " + saida);
		return saida;
	}
}
