package br.UFSC.GRIMA.util;

//Interactive 2D Bezier splines,  Evgeny Demidov 16 June 2001
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.StringTokenizer;

public class Bezier extends java.applet.Applet implements MouseMotionListener
{
	Image buffImage;          Graphics buffGraphics;
	int n = 4, n1,  w,h,h1,w2;
	double[] Px,Py;
	
	public void drawFun(){
	  double step = 1./w2, t = step;
	  double[] B = new double[n1], Bo = new double[n1], Bold = new double[n1];
	  B[1] = Bo[1] = h1;
	  Color[] iColor = {Color.gray, Color.red, new Color(0f,.7f,0f),
	   Color.blue, Color.magenta, new Color(0f,.8f,.8f), new Color(.9f,.9f,0f) };
	  for (int k = 1; k < w2; k++){
	   System.arraycopy(B,1,Bold,1,n);
	   System.arraycopy(Bo,1,B,1,n);
	
	   for (int j = 1; j < n; j++)        //  basis functions calculation
	    for (int i = j+1; i > 0; i--)
	     B[i] = (1-t)*B[i] + t*B[i-1];
	
	   for (int m = 1; m <= n; m++){
	    buffGraphics.setColor(iColor[m % 7]);
	    buffGraphics.drawLine(w2+k-1, h1-(int)Bold[m], w2+k, h1-(int)B[m] );}
	   t += step;
	  }
	}
	
	public void drawSpline(){
	  double step = 1./w2, t = step;
	  double[] Pxi = new double[n], Pyi = new double[n];
	  int X,Y, Xold = (int)Px[0], Yold = h1-(int)Py[0];
	  buffGraphics.clearRect(0,0, w2, h);
	  buffGraphics.setColor(Color.blue);
	  for (int i = 0; i < n; i++){
	   X = (int)Px[i];  Y = h1-(int)Py[i];
	   buffGraphics.drawRect(X-1,Y-1, 3,3);}
	  if ( n > 2 ){
	   int Xo = Xold, Yo = Yold;
	   for (int i = 1; i < n; i++){
	    X = (int)Px[i];  Y = h1-(int)Py[i];
	    buffGraphics.drawLine(Xo,Yo, X,Y);
	    Xo = X;  Yo = Y;}
	  }
	  buffGraphics.setColor(Color.red);
	  for (int k = 1; k < w2; k++){
	   System.arraycopy(Px,0,Pxi,0,n);
	   System.arraycopy(Py,0,Pyi,0,n);
	
	   for (int j = n-1; j > 0; j--)        //  points calculation
	    for (int i = 0; i < j; i++){
	     Pxi[i] = (1-t)*Pxi[i] + t*Pxi[i+1];
	     Pyi[i] = (1-t)*Pyi[i] + t*Pyi[i+1];}
	
	   X = (int)Pxi[0];  Y = h1-(int)Pyi[0];
	   buffGraphics.drawLine(Xold,Yold, X,Y );
	   Xold = X; Yold = Y;
	   t += step;
	  }
	}
	
	public void init() {
	  w = Integer.parseInt(getParameter("width"));
	  h = Integer.parseInt(getParameter("height"));  h1 = h-1; w2 = w/2;
	  String s = getParameter("N"); if (s != null) n = Integer.parseInt(s);
	  n1 = n+1;
	  Px = new double[n];  Py = new double[n];
	  s=getParameter("pts");
	  if (s != null){
	   StringTokenizer st = new StringTokenizer(s);
	   for (int i = 0; i < n; i++){
	    Px[i] = w2*Double.valueOf(st.nextToken()).doubleValue();
	    Py[i] = h1*Double.valueOf(st.nextToken()).doubleValue();}}
	  else{
	   Px[0] = .1*w2; Px[1] = .1*w2; Px[2] = .9*w2; Px[3] = .9*w2;
	   Py[0] = .1*h1; Py[1] = .9*h1; Py[2] = .9*h1; Py[3] = .1*h1;}
	  buffImage = createImage(w, h);
	  buffGraphics = buffImage.getGraphics();
	  setBackground(Color.white);
	  buffGraphics.clearRect(0,0, w, h);
	  addMouseMotionListener(this);
	  drawFun();
	  drawSpline();
	}
	
	public void destroy(){ removeMouseMotionListener(this); }
	public void mouseMoved(MouseEvent e){}  //1.1 event handling
	
	public void mouseDragged(MouseEvent e) {
	  int x = e.getX();  if (x < 0) x = 0;  if (x > w2-3) x = w2-3;
	  int y = h1 - e.getY();  if (y < 0) y = 0;  if (y > h1) y = h1;
	  int iMin = 0;
	  double Rmin = 1e10, r2,xi,yi;
	  for (int i = 0; i < n; i++){
	   xi = (x - Px[i]); yi = (y - Py[i]);
	   r2 = xi*xi + yi*yi;
	   if ( r2 < Rmin ){ iMin = i; Rmin = r2;}}
	  Px[iMin] = x; Py[iMin] = y;
	  drawSpline();
	  repaint();
	}
	
	public void paint(Graphics g) {
	  g.drawImage(buffImage, 0, 0, this);
	//  showStatus( " " + x +"  " + y);
	}
	
	public void update(Graphics g){ paint(g); }

}