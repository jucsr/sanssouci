package br.UFSC.GRIMA.cad.bezierGraphicInterface;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.util.Calendar;

public class SplashPanel extends BezierSurfacePanel implements MouseListener,Runnable{
    
	boolean active = true;
	boolean stilldrawing = false;
	
	BezierSurfaceApplet beziersurfaceapplet;

	Thread thread;
	
	double[][][] bez = new double[4][4][4];

    public SplashPanel( BezierSurfaceApplet beziersurfaceapplet ){
		this.beziersurfaceapplet = beziersurfaceapplet;
		
		N = 10;
		
		newBez();
		sort();
    }
	
	
	public void start(){
		active = true;
		thread = new Thread( this );
		thread.start();
	}
	
	// rotates p around vec by degrees
	public void rotate( double[] p, double theta, Point vec ){
		double l = vec.x*vec.x + vec.y*vec.y;
		double x = vec.x*(1-Math.cos(theta))*(vec.x*p[0]+vec.y*p[1])/l + p[0]*Math.cos(theta) + (vec.y*p[2])*Math.sin(theta)/Math.sqrt(l);
		double y = vec.y*(1-Math.cos(theta))*(vec.x*p[0]+vec.y*p[1])/l + p[1]*Math.cos(theta) - (vec.x*p[2])*Math.sin(theta)/Math.sqrt(l);
		double z = p[2]*Math.cos(theta) + (vec.x*p[1]-vec.y*p[0])*Math.sin(theta)/Math.sqrt(l);
		
		p[0] = x;
		p[1] = y;
		p[2] = z;
	}

	public double bernstein( double x, double y, double z, double t ){
		return x + 2*t*(y-x) + t*t*(z-2*y+x);
	}

/*
	public double bernstein( double w, double x, double y, double z, double t ){
		return w + 3*t*(x-w) + 3*t*t*(y-2*x+w) + t*t*t*(z-3*y+3*x-w);
	}
*/

	public void run(){
		double t = 0.0;
		long time = Calendar.getInstance().getTimeInMillis()/2000;
		
		while ( active ){
			if ( Calendar.getInstance().getTimeInMillis()/2000 > time ){
				time = Calendar.getInstance().getTimeInMillis()/2000;
				newBez();
			}
			t = Calendar.getInstance().getTimeInMillis()/2000.0 - time;
			
			for ( int i=0; i<4; i++ ){
				for( int j=0; j<4; j++ ){
					control_points[i][j][2] = bernstein( bez[i][j][0], bez[i][j][1], bez[i][j][2], t );
				}
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e){
			}
			if ( !stilldrawing ){

				Point axis = new Point( 0,1 );
				rotate( xaxis, 0.02, axis );
				rotate( yaxis, 0.02, axis );
				rotate( zaxis, 0.02, axis );
		
				newCoef = true;
				stilldrawing = true;
				repaint();
			}
		}
    }
	
	public void newBez(){
		double tmp;
		for ( int i=0; i<4; i++ ){
			for( int j=0; j<4; j++ ){
				tmp = bez[i][j][1];
				bez[i][j][0] = bez[i][j][2];
				bez[i][j][1] = bez[i][j][3];
				bez[i][j][2] = 1*(Math.random()-0.5);
				//bez[i][j][3] = 3*(Math.random()-0.5);
				bez[i][j][3] = 2*bez[i][j][2] - tmp;
			}
		}
	}
	
	
	public void paintComponent( Graphics graphics ){
		w = 400;
		h = 100;
		
		Graphics2D g = (Graphics2D)graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);

		scale = 6*Math.sqrt(2)/Math.max(w,h);  // units per pixel

		g.setColor( Color.white );
		g.fill( new Rectangle2D.Double( 0, 0, w, h ) );

		if ( newCoef ){
			newPoints();
			newCoef = false;
		}
		
		drawSurface( g, 255 );

		w = getWidth();
		h = getHeight();
		g.setColor( new Color( 128,128,128,220 ) );
		g.setFont( new Font("Helvetica",Font.BOLD,18) );
		String str = "CLICK ANYWHERE TO BEGIN";
		g.drawString( str, (int)(w-g.getFontMetrics().stringWidth(str)-5), (int)(h-5) );

		stilldrawing = false;
	}




	public void drawSurface( Graphics2D g, int trans ){
		GeneralPath path;
		float[] p;
		float[] q;
		float[] r;
		float[] s;

		// draw surface and points
		sort();
		g.setStroke( new BasicStroke( 0.25f) );
		for ( int i=0; i<order.length; i++ ){
			if ( order[i][0]>=0 ){
				drawSwatch( g, order[i][0], order[i][1], trans );
			}
		}
	}

	public void mouseClicked( MouseEvent me ){
		active = false;
		beziersurfaceapplet.showFrame();
	}

	public void mouseEntered( MouseEvent me ){
	}

	public void mouseExited( MouseEvent me ){
	}

	public void mousePressed( MouseEvent me ){
	}

	public void mouseReleased(MouseEvent me){
	}	

    public void mouseMoved(MouseEvent me){
    }
    	
    public void mouseDragged(MouseEvent me){
    }	
}