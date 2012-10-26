package br.UFSC.GRIMA.cad;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import br.UFSC.GRIMA.cad.CriarRegionFrame;

public class BezierSurfacePanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener{
    
	double w;
	double h;
	
	double u = 0.5;
	double v = 0.5;
	
	double scale = 0.02;					// unit per pixel
	double pixels = 60.0;			// represents number of pixels per unit on graph
	double Z = 12;
	double originX = 0.5;
	double originY = 0.5;
	public int N = 30;
	public int zoom = 3;
	public double[] units = {0.1, 0.2, 0.5, 1.0, 2.0, 5.0, 10.0};

    double[] xaxis = {1.0, 0.0, 0.0};
    double[] yaxis = {0.0, 1.0, 0.0};
    double[] zaxis = {0.0, 0.0, 1.0};
	
	public double[][][] control_points = {
			{ { -3, -3, 0 }, { -1, -3, 0 }, { 1, -3, 0 }, { 3, -3, 0 } },
			{ { -3, -1, 0 }, { -1, -1, 0 }, { 1, -1, 0 }, { 3, -1, 0 } },
			{ { -3, 1, 0 }, { -1, 1, 0 }, { 1, 1, 0 }, { 3, 1, 0 } },
			{ { -3, 3, 0 }, { -1, 3, 0 }, { 1, 3, 0 }, { 3, 3, 0 } },
			{{-3, 6, 0}, {-1, 6, 0}, {1, 6, 0}, {3, 6, 0}}
			};
	double[][][] control_points_2;
	double[][][] control_points_3;
	double[] control_points_4;

	
	double[][][] points;		// points on surface
	double[][][] normals;		// vector normal to points on surface
	
	int[][] order;
	double[] zorder;

	Point POINT;
	
	public boolean newCoef = false;
	boolean shift = false;
	boolean newUV = false;
	
	int[] OVER = { -1, -1 };


    public BezierSurfacePanel(){
		newPoints();
		newParameters();

		Point axis = new Point( 1,0 );
			rotate( xaxis, 0, axis );
			rotate( yaxis, 1.9, axis );
			rotate( zaxis, 1.7, axis );

		addMouseListener( this );
		addMouseMotionListener( this );
		addKeyListener( this );
    }
    public BezierSurfacePanel(double[][][] control_points){
    	this.control_points = control_points;
  		newPoints();
  		newParameters();

  		Point axis = new Point( 1,0 );
  			rotate( xaxis, 1.7, axis );
  			rotate( yaxis, 1.7, axis );
  			rotate( zaxis, 1.7, axis );

  		addMouseListener( this );
  		addMouseMotionListener( this );
  		addKeyListener( this );
      }

	public void zoomIn(){
		zoom--;
		if ( zoom < 0) zoom = 0;
		Z = 12*units[zoom];
		repaint();
	}

	public void zoomOut(){
		zoom++;
		if ( zoom > units.length-1 ) zoom = units.length - 1;
		Z = 12*units[zoom];
		repaint();
	}


	public void paintComponent( Graphics graphics ){
		w = getWidth();
		h = getHeight();

		Graphics2D g = (Graphics2D)graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);

		scale = 6*Math.sqrt(2)/Math.min(w,h);  // units per pixel
		scale = units[zoom]/pixels;

		//g.setColor( getBackground() );
		g.setColor( Color.white );	//paint the background
		g.fill( new Rectangle2D.Double( 0, 0, w, h ) );

		String str = CriarRegionFrame.choice.getSelectedItem().toString();
		
		drawBlock(g);
		
		if ( newCoef ){
			if ( str.equals( "u,v-parametrization" ) ){
				newParameters();
			} else {
				newPoints();
			}
			newCoef = false;
		}
		

		if ( str.equals( "Opaque" ) ){
			drawSurface( g, 255 );
		} else if ( str.equals( "Transparent" ) ){
			drawSurface( g, 150 );
		} else if ( str.equals( "Wire Frame - Opaque" ) ){
			drawOpaqueWireFrame( g );
		} else if ( str.equals( "Wire Frame - Transparent" ) ){
			drawTransparentWireFrame( g );
		} else if ( str.equals( "u,v-parametrization" ) ){
			drawParametrization( g );
		}

		// draw a circle around selected point
		g.setColor( Color.black );
		g.setColor( new Color(190, 10, 0));

		g.setStroke( new BasicStroke(1.0f) );
		if ( OVER[0] != -1 ){
			try{
				int i = OVER[0];
				int j = OVER[1];
				double rad = 5*Z/(Z-(xaxis[2]*control_points[i][j][0] + yaxis[2]*control_points[i][j][1] + zaxis[2]*control_points[i][j][2]));
				float[] p = toScreenPoint( control_points[i][j] );
//				g.draw( new Ellipse2D.Double( p[0] - rad - 2, p[1] - rad - 2, 2*(rad+2), 2*(rad+2) ) );
				g.draw( new Ellipse2D.Double( p[0] - (rad - 2) , p[1] - (rad - 2) , (rad+2), (rad+2) ) );
			} catch ( Exception e ){
			}
		}
	}



	private void drawBlock(Graphics2D g2d) 
	{
//		double [][] X = new double[1][8];
//		double [][][] X = {{{-3,3,0},{3,3,0}},
//						  {{3,-3,0},{-3,-3,0}},
//						  {{-3,3,-6},{3,3,-6}},
//						  {{3,-3,-6},{-3,-3,-6}}};
		
//		g2d.draw(new Line2D.Double(0, 0, 50, 50));
		
		g2d.setStroke( new BasicStroke( 1.0f) );
		
		g2d.setPaint(Color.blue);
		
		double x1 = control_points[0][0][0];
		double y1 = control_points[0][0][1];
		double x2 = control_points[0][3][0];
		double y2 = control_points[0][3][1];
		Line2D line = new Line2D.Double(x1,y1,x2,y2);
		g2d.draw(line);
		

//		for (int i=0; i < X.length; i++){
//			
//		}
	}


	// returns a unit vector perpendicular to u and v
	public double[] cross( double[] u, double[] v ){
		double[] out = new double[3];
		out[0] = u[1]*v[2] - u[2]*v[1];
		out[1] = u[2]*v[0] - u[0]*v[2];
		out[2] = u[0]*v[1] - u[1]*v[0];
		double l = Math.sqrt( out[0]*out[0] + out[1]*out[1] + out[2]*out[2] );
		out[0] /= l;
		out[1] /= l;
		out[2] /= l;
		
		return out;
	}


	public void drawOpaqueWireFrame( Graphics2D g ){		
		float[] NE;
		float[] NW;
		float[] SW;
		float[] SE;

		GeneralPath path;

		g.setStroke( new BasicStroke( 0.25f) );
		sort();		
		for ( int i=0; i<order.length; i++ ){
			try {
				if ( order[i][0]<0 ){
					if ( CriarRegionFrame.checkbox.isSelected() ){
						drawPoint( g, -(order[i][0]+1), order[i][1], Color.black );
					}
				} else {
					SW = toScreenPoint( points[order[i][0]][order[i][1]] );
					SE = toScreenPoint( points[order[i][0]+1][order[i][1]] );
					NE = toScreenPoint( points[order[i][0]+1][order[i][1]+1] );
					NW = toScreenPoint( points[order[i][0]][order[i][1]+1] );

					path = new GeneralPath();
					path.moveTo( NW[0], NW[1] );
					path.lineTo( SW[0], SW[1] );
					path.lineTo( SE[0], SE[1] );
					path.lineTo( NE[0], NE[1] );
					path.lineTo( NW[0], NW[1] );
					g.setColor( Color.white );
					g.fill( path );
					g.setColor( Color.black );
					g.draw( path );
				}
			} catch (Exception e ){
			}
		}
	}


	public void drawPoint( Graphics2D g, double[] P, Color color ){
		int R = color.getRed();
		int G = color.getGreen();
		int B = color.getBlue();

		double s;
		double t;
		try{
			double rad = 5*Z/(Z-(xaxis[2]*P[0] + yaxis[2]*P[1] + zaxis[2]*P[2]));
			float[] p = toScreenPoint( P );
			g.setColor( Color.black );
			for ( float k=0; k<1; k=k+(float)(1/rad) ){
				s = k*k;
				t = k*k*rad;
				g.setColor( new Color(R + (int)(s*(255-R)),G + (int)(s*(255-G)),B + (int)(s*(255-B))) );
				g.setColor( new Color(0, 180, 100));
//				g.fill( new Ellipse2D.Double( p[0] - rad + t/2, p[1] - rad + t/2, 2*(rad-t), 2*(rad-t) ) );
				g.fill( new Ellipse2D.Double( p[0] - rad/2 + t/2, p[1] - rad/2 + t/2, (rad-t), (rad-t) ) );
			}
		} catch ( Exception e ){
		}
	}


	public void drawPoint( Graphics2D g, int i, int j, Color color ){
		drawPoint( g, control_points[i][j], color );
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
			if ( order[i][0]<0 ){
				if ( CriarRegionFrame.checkbox.isSelected() ){
					drawPoint( g, -(order[i][0]+1), order[i][1], Color.black );
				}
			} else {
				drawSwatch( g, order[i][0], order[i][1], trans );
			}
		}
	}


	public void drawSwatch( Graphics2D g, int i, int j, int trans ){
		try {
			float[] P = toScreenPoint( points[i][j] ); 
			float[] Q = toScreenPoint( points[i+1][j] );
			float[] R = toScreenPoint( points[i+1][j+1] );
			float[] S = toScreenPoint( points[i][j+1] );
		
			GeneralPath path = new GeneralPath();
			path.moveTo( P[0], P[1] );
			path.lineTo( Q[0], Q[1] );
			path.lineTo( R[0], R[1] );
			path.lineTo( S[0], S[1] );
			path.lineTo( P[0], P[1] );

			Color color = (Color)CriarRegionFrame.color.getSelectedItem();
			
//			if ( color == ColorComboBox.rainbow ){
//				float u = (float)i/N;
//				float v = (float)j/N;
//				float[] rgb = { 0*(1-u)*(1-v) + 0*u*(1-v) +255*u*v + 255*(1-u)*v,
//								255*(1-u)*(1-v) + 255*u*(1-v) + 0*u*v + 255*(1-u)*v,
//								0*(1-u)*(1-v) + 255*u*(1-v) + 255*u*v + 0*(1-u)*v};
//				color = new Color( (int)rgb[0], (int)rgb[1], (int)rgb[2] );
//			}

			g.setColor( getColor( rotatePoint(points[i][j]), rotatePoint(points[i+1][j]), rotatePoint(points[i+1][j+1]), color, trans ) );
			//g.setColor( color );

			g.setStroke( new BasicStroke( 0.125f ) );
			//if ( trans<200 ) g.setStroke( new BasicStroke( 0.125f ) );
			g.fill( path );
			g.setStroke( new BasicStroke( 0.45f ) );
			g.setColor( Color.black );
			g.draw( path );
		} catch ( Exception e ){
		}
	}


	public void drawTransparentWireFrame( Graphics2D g ){		
		float[] NW;
		float[] SW;
		float[] SE;

		g.setStroke( new BasicStroke( 0.25f) );
		
		sort();
		GeneralPath path = new GeneralPath();
		for ( int i=0; i<N; i++ ){
			for ( int j=0; j<N; j++ ){
				try {
					SW = toScreenPoint( points[i][j] );
					SE = toScreenPoint( points[i+1][j] );
					NW = toScreenPoint( points[i][j+1] );

					g.setColor( new Color(0,0,0, Math.min( 255, (int)(100 + 15*(points[i][j][0]*xaxis[2] + points[i][j][1]*yaxis[2] + points[i][j][2]*zaxis[2]+10)))) );
					path.moveTo( (float)SW[0], (float)SW[1] );
					path.lineTo( (float)NW[0], (float)NW[1] );
					path.moveTo( (float)SW[0], (float)SW[1] );
					path.lineTo( (float)SE[0], (float)SE[1] );
					//g.draw( new Line2D.Double( SW[0], SW[1], NW[0], NW[1]) );
					//g.draw( new Line2D.Double( SW[0], SW[1], SE[0], SE[1]) );
				} catch ( Exception e ){
				}
			}
		}
		
		for ( int i=0; i<N; i++ ){
			try {
				SW = toScreenPoint( points[N][i] );
				SE = toScreenPoint( points[N][i+1] );
				g.setColor( new Color(0,0,0, Math.min( 255, (int)(100 + 15*(points[N][i][0]*xaxis[2] + points[N][i][1]*yaxis[2] + points[N][i][2]*zaxis[2]+10)))) );
				g.draw( new Line2D.Double( SW[0], SW[1], SE[0], SE[1]) );

				SW = toScreenPoint( points[i][N] );
				SE = toScreenPoint( points[i+1][N] );
				g.setColor( new Color(0,0,0, Math.min( 255, (int)(100 + 15*(points[i][N][0]*xaxis[2] + points[i][N][1]*yaxis[2] + points[i][N][2]*zaxis[2]+10)))) );
				path.moveTo( (float)SW[0], (float)SW[1] );
				path.lineTo( (float)SE[0], (float)SE[1] );
				//g.draw( new Line2D.Double( SW[0], SW[1], SE[0], SE[1]) );
			} catch ( Exception e ){
			}
		}
		g.draw( path );
	
		if ( CriarRegionFrame.checkbox.isSelected() ){
			for ( int i=0; i<order.length; i++ ){
				if ( order[i][0]<0 ){
					drawPoint( g, -(order[i][0]+1), order[i][1], Color.black );
				}
			}
		}
	}
	
	public void drawParametrization( Graphics2D g ){
		float[] A;
		float[] B;
		float[] C;
		float[] D;

		//g.setStroke( new BasicStroke( 0.25f) );
		sortParameters();		
		for ( int i=0; i<order.length; i++ ){
			try {
				if ( order[i][0]<0 ){
					boolean draw = CriarRegionFrame.checkbox.isSelected();
					
					switch ( order[i][2] ){
						case 1: if ( draw ) drawPoint( g, control_points[-(order[i][0]+1)][order[i][1]], Color.black ); break;
						case 2: if ( draw ) drawPoint( g, control_points_2[-(order[i][0]+1)][order[i][1]], Color.green ); break;
						case 3: if ( draw ) drawPoint( g, control_points_3[-(order[i][0]+1)][order[i][1]], Color.blue ); break;
						case 4: drawPoint( g, control_points_4, Color.red ); break;
					}
				} else {
					switch ( order[i][2] ){
						case 1:
							A = toScreenPoint( control_points[order[i][0]][order[i][1]] );
							B = toScreenPoint( control_points[order[i][0]+1][order[i][1]] );
							C = toScreenPoint( control_points[order[i][0]+1][order[i][1]+1] );
							D = toScreenPoint( control_points[order[i][0]][order[i][1]+1] );
							break;
						case 2:
							A = toScreenPoint( control_points_2[order[i][0]][order[i][1]] );
							B = toScreenPoint( control_points_2[order[i][0]+1][order[i][1]] );
							C = toScreenPoint( control_points_2[order[i][0]+1][order[i][1]+1] );
							D = toScreenPoint( control_points_2[order[i][0]][order[i][1]+1] );
							break;
						default: // i.e. case 3:
							A = toScreenPoint( control_points_3[order[i][0]][order[i][1]] );
							B = toScreenPoint( control_points_3[order[i][0]+1][order[i][1]] );
							C = toScreenPoint( control_points_3[order[i][0]+1][order[i][1]+1] );
							D = toScreenPoint( control_points_3[order[i][0]][order[i][1]+1] );
							break;
					}

					GeneralPath path = new GeneralPath();
					path.moveTo( D[0], D[1] );
					path.lineTo( A[0], A[1] );
					path.lineTo( B[0], B[1] );
					path.lineTo( C[0], C[1] );
					path.lineTo( D[0], D[1] );
					
					switch ( order[i][2] ){
						case 1: g.setColor( Color.black ); break;
						case 2: g.setColor( Color.green ); break;
						case 3: g.setColor( Color.blue ); break;
					}
					g.draw( path );
				}
			} catch ( Exception e ){
			}
		}

		
		// draw u,v square
		g.setColor( new Color(0,0,0,100) );
		double r = 4;
		g.draw( new Rectangle2D.Double( w-110,h-110,100,100) );
		g.fill( new Ellipse2D.Double( w-110-r,h-110-r,2*r,2*r ) );
		g.fill( new Ellipse2D.Double( w-110-r,h-10-r,2*r,2*r ) );
		g.fill( new Ellipse2D.Double( w-10-r,h-110-r,2*r,2*r ) );
		g.fill( new Ellipse2D.Double( w-10-r,h-10-r,2*r,2*r ) );
		g.setColor( new Color(255,0,0,100) );
		g.fill( new Ellipse2D.Double( w-(100 - 100*u+10)-r,h-(100 - 100*v+10)-r,2*r,2*r ) );
		if ( newUV ) {
			g.draw( new Ellipse2D.Double( w-(100 - 100*u+10)-r-2,h-(100 - 100*v+10)-r-2,2*r+4,2*r+4 ) );
		}
	}


	public Color getColor( double[] p, double[] q, double[] r, Color color ){
		return getColor( p, q, r, color, 255 );
	}
		
	
	public Color getColor( double[] p, double[] q, double[] r, Color color, int trans ){
		// vector normal to swatch
		double[] c = cross( diff(q,p), diff(r,p) );
		double[] d = { -10- p[0], 10-p[1], 10-p[2] };
		
		double F = (c[0]*d[0] + c[1]*d[1] + c[2]*d[2])/Math.sqrt(d[0]*d[0] + d[1]*d[1] + d[2]*d[2]);				
		F = (1+Math.abs(F))/2;
		double G = Math.pow(F,100.0);

		int red = (int)(color.getRed()*(F-G) + G*255);
		int grn = (int)(color.getGreen()*(F-G) + G*255);
		int blu = (int)(color.getBlue()*(F-G) + G*255);
		
		return new Color( red, grn, blu, trans );
	}
	
	
	public double[] getNormal( double u, double v ){
		double[] U = { 0.0, 0.0, 0.0 };
		double[] V = { 0.0, 0.0, 0.0 };
		double[] c = { -3 * (1 - u) * (1 - u), (1 - u) * (1 - 3 * u), u * (2 - 3 * u), 3 * u * u };
		double[] d = { -3 * (1 - v) * (1 - v), (1 - v) * (1 - 3 * v), u * (2 - 3 * v), 3 * v * v };
		double tmp;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				tmp = choose[i] * choose[j] * c[i] * Math.pow(v, j)
						* Math.pow(1 - v, 3 - j);
				U[0] += control_points[i][j][0] * tmp;
				U[1] += control_points[i][j][1] * tmp;
				U[2] += control_points[i][j][2] * tmp;

				tmp = choose[i] * choose[j] * Math.pow(u, i)
						* Math.pow(1 - u, 3 - i) * d[j];
				V[0] += control_points[i][j][0] * tmp;
				V[1] += control_points[i][j][1] * tmp;
				V[2] += control_points[i][j][2] * tmp;
			}
		}
		//double[] out = {U[1]*V[2]-U[2]*V[1],U[2]*V[0]-U[0]*V[2],U[1]*V[2]-U[2]*V[1]};
		return cross( U, V );
	}
		
	
	double[] choose = {1,3,3,1};
	public double[] getPoint( double u, double v ){
		double[] out = { 0.0, 0.0, 0.0 };
		double tmp;
		for ( int i=0; i<4; i++ ){
			for ( int j=0; j<4; j++ ){
				tmp = choose[i]*choose[j]*Math.pow(u,i)*Math.pow(1-u,3-i)*Math.pow(v,j)*Math.pow(1-v,3-j);
				out[0] += control_points[i][j][0]*tmp;
				out[1] += control_points[i][j][1]*tmp;
				out[2] += control_points[i][j][2]*tmp;
			}
		}
		return out;
	}
	
	
	public void newParameters(){
		// setup ordering of 30 points and 14 planes
		order = new int[30+14][3];
		zorder = new double[30+14];

		for ( int i=0; i<4; i++ ){
			for ( int j=0; j<4; j++ ){
				order[4*i+j][0] = -i - 1;
				order[4*i+j][1] = j; 
				order[4*i+j][2] = 1; 
			}
		}

		control_points_2 = new double[3][3][3];
		for ( int i=0; i<3; i++ ){
			for ( int j=0; j<3; j++ ){
				control_points_2[i][j][0] = (1-u)*(1-v)*control_points[i][j][0] + u*(1-v)*control_points[i][j+1][0] + u*v*control_points[i+1][j+1][0] + v*(1-u)*control_points[i+1][j][0];
				control_points_2[i][j][1] = (1-u)*(1-v)*control_points[i][j][1] + u*(1-v)*control_points[i][j+1][1] + u*v*control_points[i+1][j+1][1] + v*(1-u)*control_points[i+1][j][1];
				control_points_2[i][j][2] = (1-u)*(1-v)*control_points[i][j][2] + u*(1-v)*control_points[i][j+1][2] + u*v*control_points[i+1][j+1][2] + v*(1-u)*control_points[i+1][j][2];
				order[16+3*i+j][0] = -i - 1;
				order[16+3*i+j][1] = j; 
				order[16+3*i+j][2] = 2; 
				
				order[30+3*i+j][0] = i;
				order[30+3*i+j][1] = j;
				order[30+3*i+j][2] = 1;
			}
		}

		control_points_3 = new double[2][2][3];
		for ( int i=0; i<2; i++ ){
			for ( int j=0; j<2; j++ ){
				control_points_3[i][j][0] = (1-u)*(1-v)*control_points_2[i][j][0] + u*(1-v)*control_points_2[i][j+1][0] + u*v*control_points_2[i+1][j+1][0] + v*(1-u)*control_points_2[i+1][j][0];
				control_points_3[i][j][1] = (1-u)*(1-v)*control_points_2[i][j][1] + u*(1-v)*control_points_2[i][j+1][1] + u*v*control_points_2[i+1][j+1][1] + v*(1-u)*control_points_2[i+1][j][1];
				control_points_3[i][j][2] = (1-u)*(1-v)*control_points_2[i][j][2] + u*(1-v)*control_points_2[i][j+1][2] + u*v*control_points_2[i+1][j+1][2] + v*(1-u)*control_points_2[i+1][j][2];
				order[25 + 2*i+j][0] = -i - 1;
				order[25 + 2*i+j][1] = j; 
				order[25 + 2*i+j][2] = 3; 

				order[39+2*i+j][0] = i;
				order[39+2*i+j][1] = j;
				order[39+2*i+j][2] = 2;
			}
		}
		
		control_points_4 = new double[3];
			control_points_4[0] = (1-u)*(1-v)*control_points_3[0][0][0] + u*(1-v)*control_points_3[0][1][0] + u*v*control_points_3[1][1][0] + v*(1-u)*control_points_3[1][0][0];
			control_points_4[1] = (1-u)*(1-v)*control_points_3[0][0][1] + u*(1-v)*control_points_3[0][1][1] + u*v*control_points_3[1][1][1] + v*(1-u)*control_points_3[1][0][1];
			control_points_4[2] = (1-u)*(1-v)*control_points_3[0][0][2] + u*(1-v)*control_points_3[0][1][2] + u*v*control_points_3[1][1][2] + v*(1-u)*control_points_3[1][0][2];
			order[29][0] = - 1;
			order[29][1] = 0; 
			order[29][2] = 4; 
			
			order[43][0] = 0;
			order[43][1] = 0;
			order[43][2] = 3;
	}
	

	// could speed this up using forward differencing algorithm for evaluating polynomials
	public void newPoints()
	{		
		points = new double[N + 1][N + 1][3];
		normals = new double[N + 1][N + 1][3];
		order = new int[N * N + 2 * N + 1 + 16][2];
		zorder = new double[N * N + 2 * N + 1 + 16];
		for (int i = 0; i < N + 1; i++) {
			for (int j = 0; j < N + 1; j++) {
				points[i][j] = getPoint((double) (i) / N, (double) (j) / N);
				normals[i][j] = getNormal((double) (i) / N, (double) (j) / N);

				order[j + (N + 1) * i][0] = i;
				order[j + (N + 1) * i][1] = j;
			}
		}
		
		// add control_points to order
		for (int i = 0; i < 4; i++) 
		{
			for (int j = 0; j < 4; j++) {
				order[N * N + 2 * N + 1 + j + 4 * i][0] = -i - 1;
				order[N * N + 2 * N + 1 + j + 4 * i][1] = j;
			}
		}
	}
	

	public void reset(){
//		for (int i = 0; i < 4; i++) 
//		{
//			for (int j = 0; j < 4; j++) {
//				control_points[i][j][0] = 2 * j - 3;
//				control_points[i][j][1] = 2 * i - 3;
//				control_points[i][j][2] = 0;
//			}
//		}
		
		for (int i = 0; i < 4; i++) 
		{
			for (int j = 0; j < 4; j++) {
				control_points[i][j][0] = control_points[i][j][0];
				control_points[i][j][1] = control_points[i][j][1];
				control_points[i][j][2] = 0;
			}
		}
		
		newCoef = true;
		repaint();
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

	public double[] rotatePoint(double[] X) {
		double[] out = new double[3];
		out[0] = X[0] * xaxis[0] + X[1] * yaxis[0] + X[2] * zaxis[0];
		out[1] = X[0] * xaxis[1] + X[1] * yaxis[1] + X[2] * zaxis[1];
		out[2] = X[0] * xaxis[2] + X[1] * yaxis[2] + X[2] * zaxis[2];
		return out;
	}
 

	public double[] rotatePoint(double x, double y, double z) {
		double[] out = new double[3];
		out[0] = x * xaxis[0] + y * yaxis[0] + z * zaxis[0];
		out[1] = x * xaxis[1] + y * yaxis[1] + z * zaxis[1];
		out[2] = x * xaxis[2] + y * yaxis[2] + z * zaxis[2];
		return out;
	}


	public void sort(){
		int I;
		int J;
		for ( int i=0; i<zorder.length; i++ ){
			I = order[i][0];
			J = order[i][1];
			if ( I<0 ){
				zorder[i] = control_points[-(I+1)][J][0]*xaxis[2] + control_points[-(I+1)][J][1]*yaxis[2] + control_points[-(I+1)][J][2]*zaxis[2];
			} else {
				zorder[i] = points[I][J][0]*xaxis[2] + points[I][J][1]*yaxis[2] + points[I][J][2]*zaxis[2];
			}
		}
		sort( 0, order.length-1, 1);
	}

	// sign = 1  means increasing order
	// sign = -1 means decreasing order
	public void sort(int a, int b, int sign) {
		int lo = a;
		int hi = b;
		double mid;
		int[] tmp1;
		double tmp2;

		if (b > a) {
			mid = zorder[(a + b) / 2];
			while (lo <= hi) {
				while ((lo < b) && (sign * zorder[lo] < sign * mid))
					++lo;
				while ((hi > a) && (sign * zorder[hi] > sign * mid))
					--hi;

				if (lo <= hi) {
					// if ( order[lo][0]
					tmp1 = order[lo];
					order[lo] = order[hi];
					order[hi] = tmp1;

					tmp2 = zorder[lo];
					zorder[lo] = zorder[hi];
					zorder[hi] = tmp2;

					lo++;
					hi--;
				}
			}
			if (a < hi)
				sort(a, hi, sign);
			if (lo < b)
				sort(lo, b, sign);
		}
	}


	public void sortParameters(){
		int I;
		int J;
		int K;
		for ( int i=0; i<zorder.length; i++ ){
			I = order[i][0];
			J = order[i][1];
			K = order[i][2];
			if ( I<0 ){
				switch ( K ){
					case 1: zorder[i] = control_points[-(I+1)][J][0]*xaxis[2] + control_points[-(I+1)][J][1]*yaxis[2] + control_points[-(I+1)][J][2]*zaxis[2]; break;
					case 2: zorder[i] = control_points_2[-(I+1)][J][0]*xaxis[2] + control_points_2[-(I+1)][J][1]*yaxis[2] + control_points_2[-(I+1)][J][2]*zaxis[2]; break;
					case 3: zorder[i] = control_points_3[-(I+1)][J][0]*xaxis[2] + control_points_3[-(I+1)][J][1]*yaxis[2] + control_points_3[-(I+1)][J][2]*zaxis[2]; break;
					case 4: zorder[i] = control_points_4[0]*xaxis[2] + control_points_4[1]*yaxis[2] + control_points_4[2]*zaxis[2]; break;
				}
			} else {
				switch ( K ){
					case 1: zorder[i] = (control_points[I][J][0]+control_points[I][J+1][0]+control_points[I+1][J+1][0]+control_points[I+1][J][0])*xaxis[2]/4.0 
						+ (control_points[I][J][1]+control_points[I][J+1][1]+control_points[I+1][J+1][1]+control_points[I+1][J][1])*yaxis[2]/4.0 
						+ (control_points[I][J][2]+control_points[I][J+1][2]+control_points[I+1][J+1][2]+control_points[I+1][J][2])*zaxis[2]/4.0; 
						break;
					case 2: zorder[i] = (control_points_2[I][J][0]+control_points_2[I][J+1][0]+control_points_2[I+1][J+1][0]+control_points_2[I+1][J][0])*xaxis[2]/4.0 
						+ (control_points_2[I][J][1]+control_points_2[I][J+1][1]+control_points_2[I+1][J+1][1]+control_points_2[I+1][J][1])*yaxis[2]/4.0 
						+ (control_points_2[I][J][2]+control_points_2[I][J+1][2]+control_points_2[I+1][J+1][2]+control_points_2[I+1][J][2])*zaxis[2]/4.0; 
						break;
					case 3: zorder[i] = (control_points_3[I][J][0]+control_points_3[I][J+1][0]+control_points_3[I+1][J+1][0]+control_points_3[I+1][J][0])*xaxis[2]/4.0 
						+ (control_points_3[I][J][1]+control_points_3[I][J+1][1]+control_points_3[I+1][J+1][1]+control_points_3[I+1][J][1])*yaxis[2]/4.0 
						+ (control_points_3[I][J][2]+control_points_3[I][J+1][2]+control_points_3[I+1][J+1][2]+control_points_3[I+1][J][2])*zaxis[2]/4.0; 
						break;
				}
			}
		}
		sortParameters( 0, order.length-1 );
	}

	public void sortParameters(int a, int b ){
        int lo = a;
        int hi = b;
        int mid;
		int[] tmp1;
		double tmp2;

        if (b>a){
            mid = (a+b)/2;
            while(lo<=hi){
                while( ( lo < b ) && ( zorder[lo] < zorder[mid] || ( zorder[lo] == zorder[mid] && order[lo][2] < order[mid][2] ) ) )  ++lo;
                while( ( hi > a ) && ( zorder[hi] > zorder[mid] || ( zorder[hi] == zorder[mid] && order[hi][2] > order[mid][2] ) ) )  --hi;

                if( lo<=hi ){
                    tmp1 = order[lo];
                    order[lo] = order[hi];
                    order[hi] = tmp1;
					
					tmp2 = zorder[lo];
                    zorder[lo] = zorder[hi];
                    zorder[hi] = tmp2;
					
					lo++;
					hi--;					
                }
            }
            if(a<hi) sortParameters(a,hi);
            if(lo<b) sortParameters(lo,b);
        }
    }


	// takes a point (x,y,z) in cartesian coordinates
	// returns the corresponding point on the screen
	public float[] toScreenPoint( double[] X ) throws Exception{
		double[] P = rotatePoint( X );		
		if ( P[2] > Z ){
			throw new Exception();
		}
		float[] out = new float[2];
		out[0] = (float)( w/2 + Z*(P[0])/scale/(Z-P[2]) );
		out[1] = (float)( h/2 - Z*(P[1])/scale/(Z-P[2]) );
		
		return out;
	}	

	
	// to u from v
	public double[] diff( double[] u, double[] v ){
		double[] out = {u[0]-v[0],u[1]-v[1],u[2]-v[2]};
		return out;
	}


	// returns the control points that produce a Bernstein polynomial on [0,1]
	// equivalent to the given Bernstein polynomial on the interval [0,t]
	// used for animation purposes
	// used for drawing shells with perspective
	public double[] getBez( double A, double B, double C, double D, double t ){
		double[] out = new double[4];
		out[0] = A;
		out[1] = A + t*(B-A);
		out[2] = A + 2*t*(B-A) + t*t*(C - 2*B + A);
		out[3] = A + 3*t*(B-A) + 3*t*t*(C-2*B + A) + t*t*t*(D - 3*C + 3*B - A);
		return out; 
	}

	public void mouseClicked(MouseEvent me){
		POINT = me.getPoint();
	}

    public void mouseEntered( MouseEvent me ){
		requestFocus();
    }

    public void mouseExited( MouseEvent me ){
    }

    public void mousePressed( MouseEvent me ){
		requestFocus();
		POINT = me.getPoint();
    }

    public void mouseReleased( MouseEvent me ){
    }	

    public void mouseMoved(MouseEvent me){
		Point p = me.getPoint();
		OVER[0] = -1;
		int i = 0;
		int j = 0;
		double r;
		float[] tmp;
		
		while( OVER[0] == -1 && i<4 && j < 4 ){
			r = 5*Z/(Z-(xaxis[2]*control_points[i][j][0] + yaxis[2]*control_points[i][j][1] + zaxis[2]*control_points[i][j][2]));
			try{
				tmp = toScreenPoint( control_points[i][j] );
				if ( (p.x-tmp[0])*(p.x-tmp[0]) + (p.y-tmp[1])*(p.y-tmp[1]) < r*r ){
					OVER[0] = i;
					OVER[1] = j;
					
				}
				repaint();
			} catch ( Exception e ){
			}
			i++;
			if ( i == 4 ){
				i = 0;
				j++;
			}
		}
		newUV = false;
		if ( OVER[0] == -1 && CriarRegionFrame.choice.getSelectedItem().equals( "u,v-parametrization" ) ){
			double x = me.getPoint().x - (w - 110 + 100*u);
			double y = me.getPoint().y - (h - 110 + 100*v);
			if ( x*x + y*y < 40 ){
				newUV = true;
			}
		}
    }
    	
    public void mouseDragged(MouseEvent me)
    {
		Point P = me.getPoint();
		if ( newUV )
		{
			u = (me.getPoint().x - (w-110))/100.0;
			if ( u < 0.0 ) u = 0.0;
			if ( u > 1.0 ) u = 1.0;
			v = (me.getPoint().y - (h-110))/100.0;
			if ( v < 0.0 ) v = 0.0;
			if ( v > 1.0 ) v = 1.0;
			newCoef = true;
			repaint();
		} else if ( OVER[0] != -1 )
		{
			if ( shift )
			{  // edit x & y-coordinate
				double d = control_points[OVER[0]][OVER[1]][2];
				double[][] M = 
				{
						{ xaxis[0], yaxis[0], (P.x - w / 2) * scale / Z },
						{ xaxis[1], yaxis[1], (h / 2 - P.y) * scale / Z },
						{ xaxis[2], yaxis[2], -1 }
				};
				double det = M[0][0]*M[1][1]*M[2][2] + M[0][1]*M[1][2]*M[2][0] + M[0][2]*M[1][0]*M[2][1] 
							- M[0][0]*M[1][2]*M[2][1] - M[0][1]*M[1][0]*M[2][2] - M[0][2]*M[1][1]*M[2][0];
				control_points[OVER[0]][OVER[1]][0] = ((M[1][1]*M[2][2]-M[1][2]*M[2][1])*((P.x-w/2)*scale-zaxis[0]*d) 
													- (M[0][1]*M[2][2]-M[0][2]*M[2][1])*((h/2-P.y)*scale-zaxis[1]*d) 
													+ (M[0][1]*M[1][2]-M[0][2]*M[1][1])*(-zaxis[2]*d))/det;
				control_points[OVER[0]][OVER[1]][1] = -((M[1][0]*M[2][2]-M[1][2]*M[2][0])*((P.x-w/2)*scale-zaxis[0]*d) 
													- (M[0][0]*M[2][2]-M[0][2]*M[2][0])*((h/2-P.y)*scale-zaxis[1]*d) 
													+ (M[0][0]*M[1][2]-M[0][2]*M[1][0])*(-zaxis[2]*d))/det;
			} else {  // edit z-coordiante
				double x = control_points[OVER[0]][OVER[1]][0];
				double y = control_points[OVER[0]][OVER[1]][1];
				double z = scale*Z*(h/2 - P.y);
				z -= Z*(x*xaxis[1]+y*yaxis[1]) + scale*(h/2 - P.y)*(x*xaxis[2]+y*yaxis[2]);
				z /= Z*zaxis[1] + scale*(h/2-P.y)*zaxis[2];
				control_points[OVER[0]][OVER[1]][2] = z;
				
				//imprime a coordenada z
			}
			POINT = me.getPoint();
			
			//imprime o ponto x e y mas sem distincao de qual bolinha esta utilizando
			
			newCoef = true;
			repaint();
		} else {
			double d = Math.sqrt((POINT.x - P.x)*(POINT.x-P.x) + (POINT.y-P.y)*(POINT.y-P.y));
			double degrees = Math.PI*d/180.0;
			Point axis = new Point( P.y-POINT.y, P.x-POINT.x );
			if ( axis.x*axis.x + axis.y*axis.y > 0 ){
				rotate( xaxis, degrees, axis );
				rotate( yaxis, degrees, axis );
				rotate( zaxis, degrees, axis );
				POINT = me.getPoint();
				repaint();
			}
		}
    }	

	public void keyTyped( KeyEvent ke )
	{
		int code = ke.getKeyCode();
		if ( code == KeyEvent.VK_SPACE ){
		}
	}
		
	public void keyPressed( KeyEvent ke )
	{
		int code = ke.getKeyCode();
		if ( code == KeyEvent.VK_SHIFT ){// holding down shift key
			shift = true;
		}
	}
		
	public void keyReleased( KeyEvent ke ){
		shift = false;
	}
}