package br.UFSC.GRIMA.cad;
/*
 * @(#)Intersection.java	1.5 98/12/03
 * Copyright 1998 by Sun Microsystems, Inc.,
 * 901 San Antonio Road, Palo Alto, California, 94303, U.S.A.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Sun Microsystems, Inc. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Sun.
 */


import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import javax.swing.*;


/**
 * The Intersection class demonstrates animated intersection clipping of a 
 * rectangle or a text shape.
 */
public class Intersection extends JApplet {

    Demo demo;

    public void init() {
        getContentPane().add(demo = new Demo());
        getContentPane().add("North", new DemoControls(demo));
    }

    public void start() {
        demo.start();
    }
  
    public void stop() {
        demo.stop();
    }


    /**
     * The Demo class performs the animation, transformations and
     * painting.
     */
    public class Demo extends JPanel implements Runnable {

        // indicators used to resize the clipping rectangle 
        private static final int HEIGHTDECREASE = 0;
        private static final int HEIGHTINCREASE = 1;
        private static final int WIDTHDECREASE = 2;
        private static final int WIDTHINCREASE = 3;
    
        /* 
         * the x-coordinate, y-coordinate, width and height respectively of
	 * the clipping rectangle
         */
        private int xx, yy, ww, hh;

        // the direction of the current resizing of the clipping rectangle 
        private int direction = HEIGHTDECREASE;

        private int angdeg;    // angle of rotation
        private Shape textshape;   // oultine shape of TextLayout tl
        private double sw, sh;    // width and height variables 
        private GeneralPath ovals;
        private Rectangle2D rectshape;  // bounds of TextLayout tl
        private Thread thread;
        private BufferedImage bimg;
        protected boolean doIntersection = true;  // Intersect button selected
        protected boolean doOvals = true;  // Ovals button selected
        protected boolean doText;  // Text button not selected
        protected boolean threeSixty;  // indicates if rotation has reached
                                       // 360 degrees
    
    
        public Demo() {
            setBackground(Color.white);
        }
    
    
        public void reset(int w, int h) {
            xx = yy = 0;
            ww = w-1; hh = h;
            direction = HEIGHTDECREASE;
            angdeg = 0;

            // creates a TextLayout object with the string "J2D"
            FontRenderContext frc = new FontRenderContext(null, true, false);
            Font f = new Font("serif",Font.BOLD,32);
            TextLayout tl = new TextLayout("J2D", f, frc);

            // the width of the TextLayout created with "J2D"
            sw = tl.getBounds().getWidth();

            // the height of the TextLayout created with "J2D"
            sh = tl.getBounds().getHeight();

            // sets size equal to the minimum of the width and height values
            int size = Math.min(w, h);

            double sx = (size-40)/sw;
            double sy = (size-100)/sh;

            /*
             * creates a scaling transform that scales by sx pixels along the
             * x axis and by sy pixels along the y axis
             */
            AffineTransform Tx = AffineTransform.getScaleInstance(sx, sy);

            /*
             * creates an outline shape with the scaling factor Tx
             */
            textshape = tl.getOutline(Tx);

            /*
             * gets the bounds of textshape and saves the width in sw and 
             * the height in sh
             */
            rectshape = textshape.getBounds();
            sw = rectshape.getWidth();
            sh = rectshape.getHeight();

            // appends four Ellipse2D.Double objects to a single GeneralPath
            ovals = new GeneralPath();
            ovals.append(new Ellipse2D.Double(10, 10, 50, 50), false);
            ovals.append(new Ellipse2D.Double(w-60, 10, 50, 50), false);
            ovals.append(new Ellipse2D.Double(10, h-60, 50, 50), false);
            ovals.append(new Ellipse2D.Double(w-60, h-60, 50, 50), false);
        }
        /*
         * sets the coordinates & size of the clipping & filling rectangle,
         * increments the angle of rotation in a clockwise direction   
         */
        public void step(int w, int h) {
            if (direction == HEIGHTDECREASE) {
                yy+=2; hh-=4;
                if (yy >= h/2) {
                    direction = HEIGHTINCREASE;
                }
            } else if (direction == HEIGHTINCREASE) {
                yy-=2; hh+=4;
                if (yy <= 0) {
                    direction = WIDTHDECREASE;
                    hh = h-1; yy = 0;
                }
            }
            if (direction == WIDTHDECREASE) {
                xx+=2; ww-=4;
                if (xx >= w/2) {
                    direction = WIDTHINCREASE;
                }
            } else if (direction == WIDTHINCREASE) {
                xx-=2; ww+=4;
                if (xx <= 0) {
                    direction = HEIGHTDECREASE;
                    ww = w-1; xx = 0;
                }
            }
            if ((angdeg += 5) == 360) { 
                angdeg = 0;
                threeSixty = true;
            }
        }
    
    
        public void drawDemo(int w, int h, Graphics2D g2) {

	    // creates a rectangle to be used as the clipping & filling area
            Rectangle rect = new Rectangle(xx, yy, ww, hh);
           
            /*
             * an AffineTransform that rotates coordinates around
             * the anchor point (w/2, h/2) and translates coordinates
             * to the center of the window
             */
            AffineTransform Tx = new AffineTransform();
            Tx.rotate(Math.toRadians(angdeg),w/2,h/2);
            Tx.translate(w/2-sw/2, sh+(h-sh)/2);
    
            /*
             * if Ovals is selected, appends all ovals to the
             * new path
             */
            GeneralPath path = new GeneralPath();
            if (doOvals) {
                path.append(ovals, false);
            } 

            /*
             * if Text is selected, appends the transformed
             * outline shape of the TextLayout to the path; 
             * otherwise, appends the rectangle to the path 
             */
            if (doText) {
                path.append(Tx.createTransformedShape(textshape), false);
            } else {
                path.append(Tx.createTransformedShape(rectshape), false);
            }

            /*
             * if Intersect is selected, intersects the current clip with 
             * both the interior of rect and the interior of path and sets
             * the current clip to the result of these intersections
             */  
            if (doIntersection) {
                g2.clip(rect);
                g2.clip(path);
            }
    
            g2.setColor(Color.green);
            g2.fill(rect);
    
            // resets the current clip to the entire viewable surface
            g2.setClip(new Rectangle(0, 0, w, h));
    
            g2.setColor(Color.lightGray);
            g2.draw(rect);
            g2.setColor(Color.black);
            g2.draw(path);
        }
    
    
        public Graphics2D createGraphics2D(int w, int h) {
            Graphics2D g2 = null;
            if (bimg == null || bimg.getWidth() != w || bimg.getHeight() != h) {
                bimg = (BufferedImage) createImage(w, h);
                reset(w, h);
            } 
            g2 = bimg.createGraphics();
            g2.setBackground(getBackground());
            g2.clearRect(0, 0, w, h);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
            return g2;
        }
    
    
        public void paint(Graphics g) {
            Dimension d = getSize();
            step(d.width, d.height);
            Graphics2D g2 = createGraphics2D(d.width, d.height);
            drawDemo(d.width, d.height, g2);
            g2.dispose();
            g.drawImage(bimg, 0, 0, this);
        }
    
    
        public void start() {
            thread = new Thread(this);
            thread.setPriority(Thread.MIN_PRIORITY);
            thread.start();
        }
    
    
        public synchronized void stop() {
            thread = null;
        }
    
    
        public void run() {
            Thread me = Thread.currentThread();
            while (thread == me) {
                repaint();
                try {
                    thread.sleep(10);
                } catch (InterruptedException e) { break; }
            }
            thread = null;
        }
    } // End Demo class 


    /**
     * The DemoControls class provides buttons for intersection, the
     * type of object with which to intersect (Rectangle or Text) and
     * whether or not to append ovals to the rectangle or text
     * intersect object.
     */
    static class DemoControls extends JPanel implements ActionListener {

        Demo demo;
        JToolBar toolbar;

        public DemoControls(Demo demo) {
            this.demo = demo;
            setBackground(Color.gray);
            add(toolbar = new JToolBar());
            toolbar.setFloatable(false);
            addTool("Intersect", true);
            addTool("Text", false);
            addTool("Ovals", true);
        }


        public void addTool(String str, boolean state) {
            JButton b = (JButton) toolbar.add(new JButton(str));
            b.setBackground(state ? Color.green : Color.lightGray);
            b.setSelected(state);
            b.addActionListener(this);
        }


        public void actionPerformed(ActionEvent e) {
            JButton b = (JButton) e.getSource();
            b.setSelected(!b.isSelected());
            b.setBackground(b.isSelected() ? Color.green : Color.lightGray);
            if (b.getText().equals("Intersect")) {
                demo.doIntersection = b.isSelected();
            } else if (b.getText().equals("Ovals")) {
                demo.doOvals = b.isSelected();
            } else if (b.getText().equals("Text")) {
                demo.doText = b.isSelected();
            }
        }
    } // End DemoControls class


    public static void main(String argv[]) {
        final Intersection demo = new Intersection();
        demo.init();
        Frame f = new Frame("Java 2D(TM) Demo - Intersection");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
            public void windowDeiconified(WindowEvent e) { demo.start(); }
            public void windowIconified(WindowEvent e) { demo.stop(); }
        });
        f.add(demo);
        f.pack();
        f.setSize(new Dimension(400,300));
        f.show();
        demo.start();
    }
}