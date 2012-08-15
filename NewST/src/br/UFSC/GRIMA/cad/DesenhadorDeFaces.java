package br.UFSC.GRIMA.cad;
import javax.swing.JPanel;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.entidades.features.Degrau;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.Furo;
import br.UFSC.GRIMA.util.projeto.Projeto;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class DesenhadorDeFaces extends JPanel{
	public Projeto projeto;	
	public int verticeAtivado = 0;
	public int facePrincipal = Face.XY;
	
	int espacamentoX = 10, espacamentoY = 10;
	public boolean alterou = false;
	public boolean dMesa = true;
	public boolean dEixos = true;
	public boolean blank = false;
	private boolean mode = true;
	
	public boolean dFeaturesSecundarias = true;
	int X = 2 * this.espacamentoX;
	int Y = 2 * this.espacamentoY;
	private double zoom = 1;
	public Point origem;
	public Point[] origens;
	public Dimension tamanho;
	
	ArrayList<Point3d> arrayApoios = new ArrayList<Point3d>();
	int raioAp;
	
	boolean drawCP = false;
	
	public DesenhadorDeFeatures desenhadorDeFeatures = new DesenhadorDeFeatures();
	
	public DesenhadorDeApoios desenhadorDeApoios = new DesenhadorDeApoios();
	
	public DesenhadorDeFaces(Projeto projeto){		
		this.projeto = projeto;
		this.setLayout(new FlowLayout());
		this.setSize(100, 100);
	}
	public void paintComponent(Graphics g)
	{/*****/
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, 2000, 2000);
		
		
		
		BufferedImage buffImg = new BufferedImage(2000, 2000, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gbi = buffImg.createGraphics();
		
		this.desenhadorDeFeatures.dFeaturesSecundarias = this.dFeaturesSecundarias;
		
		if(this.mode){//desenha uma face
			
			Face faceTmp = (Face)this.projeto.getBloco().faces.elementAt(this.facePrincipal);
//			g2d.translate(0, (int)(this.projeto.getMaquina().getComprimento() * zoom + this.espacamentoY * 4));
			g2d.translate(0, this.projeto.getBloco().getComprimento() * zoom + this.espacamentoY * 4);
			
			g2d.scale(1, - 1);
			if(this.alterou){
				
//				this.tamanho = new Dimension((int)((this.projeto.getMaquina().comprimento) * zoom + 2 * X), (int)((this.projeto.getMaquina().comprimento) * zoom + 2 * Y));
				this.tamanho = new Dimension((int)((this.projeto.getBloco().getComprimento()) * zoom + 2 * X), (int)((this.projeto.getBloco().getComprimento()) * zoom + 2 * Y));
				
				this.setPreferredSize(this.tamanho);
				this.origem = new Point(espacamentoX * 2, espacamentoY * 2);
				this.revalidate();
				this.alterou = false;
			}
			this.desenharMesa(g2d);
			this.desenharEixos(g2d);
			this.dBlank(g2d);
			this.desenharFacePrincipal(faceTmp, this.verticeAtivado, origem, gbi);
			this.desenharFacesOrtogonais(faceTmp, this.verticeAtivado, origem, gbi);
			if (this.dFeaturesSecundarias)
			this.desenharFaceParelela(faceTmp, this.verticeAtivado, origem, gbi);
			
			if (drawCP)
			{
				this.desenharApoios(faceTmp, this.verticeAtivado, origem, gbi);
			}
			
			//desenhadorDeApoios.DesenharApoios(faceTmp, verticeAtivado,origem, true, g2d);
			
			
			
		}
		else{//desenha todas as faces
			int[][]todasAsVistas = Matrizes.planos[this.facePrincipal][this.verticeAtivado];
			
			if(this.alterou){
				this.tamanho = this.getImageSize(todasAsVistas);
				this.origens = this.determinarOrigens(todasAsVistas);
				this.setPreferredSize(this.tamanho);
				this.revalidate();
				this.alterou = false;
			}
			//g2d.translate(0, tamanho.height);
			g2d.translate(0, this.getImageSize(todasAsVistas).height);//###########NAUM SEI O QUE QUE EU FIZ!!!!!!!!!
			g2d.scale(1, - 1);	
			for (int posicao = 0; posicao < todasAsVistas.length; posicao ++)
			{
				int[] infoFace = todasAsVistas[posicao];
				Face faceTmp = (Face)this.projeto.getBloco().faces.elementAt(infoFace[Matrizes.PLANOS_FACE]);
				
				this.desenharFacePrincipal(faceTmp, infoFace[Matrizes.PLANOS_VERTICE], origens[posicao], gbi);
				this.dBlank(g2d);
				if (this.dFeaturesSecundarias)
				{
					this.desenharFaceParelela(faceTmp, infoFace[Matrizes.PLANOS_VERTICE], origens[posicao], gbi);
				}
				
				this.desenharFacesOrtogonais(faceTmp, infoFace[Matrizes.PLANOS_VERTICE], origens[posicao], gbi);
			}
		}
		g2d.drawImage(buffImg, null, 0, 0);   //######################### IMPORTANTE ##########################
	}
	/**
	 * 
	 * @param face - Face que sera desenhada
	 * @param verticeAtivado - Vertice ativado da face que sera desenhada
	 * @param origem - Origem da face que sera desenhada
	 * @param g2d - graphics
	 */
	public void desenharFacePrincipal(Face face, int verticeAtivado, Point origem, Graphics2D g2d){
		//desenhar retangulo da face
		g2d.setStroke(new BasicStroke());
		
		//int zoom = 1;
		
		int comprimento = (int)Math.round(face.getComprimentoDesenhado(verticeAtivado) * zoom);
		int largura = (int)Math.round(face.getLarguraDesenhada(verticeAtivado) * zoom);
		g2d.setColor(new Color(238, 233, 191));
		g2d.setColor(new Color(205, 205, 193));
		g2d.fillRect(origem.x, origem.y, comprimento, largura);
		g2d.setColor(Color.BLACK);
		g2d.drawRect(origem.x, origem.y, comprimento, largura);		
		
		//desenhar cada uma das features
		
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);	
		
		for(int i = 0; i < face.features.size(); i++){
			Feature featureOriginal = (Feature)face.features.elementAt(i);
			Feature featureAlterada = face.alterarOrientacao(verticeAtivado, featureOriginal);
			desenhadorDeFeatures.desenharFeature(featureAlterada, face, verticeAtivado,origem, true, g2d);
		}
	}
	
	/**
	 * 
	 * @param face - Face principal, na qual se deseja desenhar as features das faces ortogonais.
	 * @param verticeAtivado - Vertice ativado da face principal
	 * @param origem - Origem da face principal
	 * @param g2d
	 */
	public void desenharFacesOrtogonais(Face face, int verticeAtivado, Point origem, Graphics2D g2d){
		int[][] ortogonais = Matrizes.ortogonais[face.getTipo()][verticeAtivado];
		
		float dash1[] = {5.0f, 2.5f};
		g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, 
				BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));
		
		for(int i = 0; i < ortogonais.length; i++){
			int[] infoFace = ortogonais[i];
			
			Face faceOrtogonal = (Face)this.projeto.getBloco().faces.elementAt(infoFace[Matrizes.ORTOGONAIS_FACE]);
			
			for(int j = 0; j < faceOrtogonal.features.size(); j++){
				Feature featureOriginal = (Feature)faceOrtogonal.features.elementAt(j);
				Feature featureAlterada = faceOrtogonal.alterarOrientacao(infoFace[Matrizes.ORTOGONAIS_VERTICE], featureOriginal);
				
				this.desenhadorDeFeatures.desenharFeatureOrtogonal(featureAlterada, face, verticeAtivado, origem, i, g2d);
				
				if(i == 3){
					if(featureAlterada.getTipo() == Feature.DEGRAU){
						Degrau d = (Degrau)featureAlterada;
						//System.out.printf("DEGRAU: posX=%f posY=%f largura=%f prof=%f\n", d.getPosicaoX(), d.getPosicaoY(), d.getLargura(), d.getProfundidade());
					}
				}
			}
		}		
	}
	/**
	 * 
	 * @param face - Face principal, onde ser�o desenahdas as features da face paralela
	 * @param verticeAtivado - Vertice ativado da face pricnipal
	 * @param origem - Origem da face principal
	 * @param g2d
	 */
	public void desenharFaceParelela(Face face, int verticeAtivado, Point origem, Graphics2D g2d){
		//determinar face paralela
		int[] paralela = Matrizes.paralelas[face.getTipo()][verticeAtivado];
		
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);	
		
		float dash1[] = {5.0f, 2.5f};
		g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, 
				BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));
		
		Face faceParalela = (Face)this.projeto.getBloco().faces.elementAt(paralela[Matrizes.PARALELAS_FACE]);
		
		//desenhar cada uma das br.UFSC.GRIMA.feature
		for(int i = 0; i < faceParalela.features.size(); i++){
			Feature featureOriginal = (Feature)faceParalela.features.elementAt(i);
			Feature featureAlterada = faceParalela.alterarOrientacaoParalela(paralela[Matrizes.PARALELAS_VERTICE], paralela[Matrizes.PARALELAS_MODO], featureOriginal);
			
			//DesenhadorDeFeatures.desenharFeature(featureAlterada, face, verticeAtivado, origem, false, g2d);
			this.desenhadorDeFeatures.desenharFeature(featureAlterada, face, verticeAtivado, origem, false, g2d);
		}
	}
	
	public void desenharApoios(Face face, int verticeAtivado, Point origem, Graphics2D g2d)
	{
		
		desenhadorDeApoios.DesenharApoios(face, verticeAtivado,origem, true, g2d,arrayApoios,raioAp);
		
		drawCP = false;
	}
	public void setFacePrincipal(int faceTipo, int verticeAtivado){
		this.facePrincipal = faceTipo;
		this.verticeAtivado = verticeAtivado;
		this.alterou = true;
		
		
		
		repaint();
	}
	
	private void setMode(boolean x){
		this.mode = x;
		//X = true -> desenhar 1 face
		//X = false -> desenhar todas as faces 
	}
	public Dimension getImageSize(int[][] matrizVistas)
	{
		Face faceTmp;
		int largura = 0, altura = 0;
		int larg1, larg0, larg3;
		int alt5, alt4, alt0, alt2;
		// calcular altura e largura pela matriz
		// seta os valores de imageSize
		
		//0, 2, 4, 5 - altura
		//0, 1, 3 - largura
		for(int posicao = 0; posicao < 6; posicao++){//pointIndex => posi��o do plano nas vistas
			faceTmp = (Face)this.projeto.getBloco().faces.elementAt(matrizVistas[posicao][Matrizes.PLANOS_FACE]);
			
			if(posicao == 0 || posicao == 2 || posicao == 4 || posicao == 5){//altura
				altura += faceTmp.getLarguraDesenhada(matrizVistas[posicao][Matrizes.PLANOS_VERTICE]) * zoom;
			}
			if(posicao == 0 || posicao == 1 || posicao == 3){//largura
				largura += faceTmp.getComprimentoDesenhado(matrizVistas[posicao][Matrizes.PLANOS_VERTICE]) * zoom;
			}
		}
		altura += 8 * espacamentoY;
		largura += 6 * espacamentoX;
		//altura = alt5 + alt4 + alt0 + alt2 + 8 * espacamentoY;
		//largura = larg1 + larg0 + larg3 + 6 * espacamentoX;
		
		Dimension d = new Dimension((int)(largura), (int)(altura));
		
		//System.out.println(d.toString()+ "%%%%%%%%%%%%");
		
		return d;
	}
	
	private Point[] determinarOrigens(int[][] matrizVistas)
	{
		Face faceTmp;
		int X = 0, Y = 0;
		int posicao = 0;
		
		Point[] origens = new Point[6];
		// determina o vertice de cada face
		//Point vertice = new Point(X, Y);
		//vistas.setVertices(ths.vertices);
		
		//origem da face de posicao 0
		posicao = 1;
		faceTmp = (Face)this.projeto.getBloco().faces.elementAt(matrizVistas[posicao][Matrizes.PLANOS_FACE]);
		X = (int)(3 * espacamentoX + faceTmp.getComprimentoDesenhado(matrizVistas[posicao][Matrizes.PLANOS_VERTICE]) * zoom);
		
		posicao = 5;
		faceTmp = (Face)this.projeto.getBloco().faces.elementAt(matrizVistas[posicao][Matrizes.PLANOS_FACE]);
		Y = (int)(faceTmp.getLarguraDesenhada(matrizVistas[posicao][Matrizes.PLANOS_VERTICE]) * zoom);
		posicao = 4;
		faceTmp = (Face)this.projeto.getBloco().faces.elementAt(matrizVistas[posicao][Matrizes.PLANOS_FACE]);
		Y += (int)(faceTmp.getLarguraDesenhada(matrizVistas[posicao][Matrizes.PLANOS_VERTICE]) * zoom);
		
		Y += 5 * espacamentoY;
		origens[0] = new Point((int)(X), (int)(Y));
		
		//origem da face de posicao 1
		X = 0;
		Y = 0;
		X = espacamentoX;
		Y = origens[0].y;
		origens[1] = new Point((int)(X), (int)(Y));
		
		//origem da face de posicao 2
		X = 0;
		Y = 0;
		posicao = 0;
		faceTmp = (Face)this.projeto.getBloco().faces.elementAt(matrizVistas[posicao][Matrizes.PLANOS_FACE]);
		Y = (int)(faceTmp.getLarguraDesenhada(matrizVistas[posicao][Matrizes.PLANOS_VERTICE]) * zoom);
		X = origens[0].x;
		Y += origens[0].y;
		Y += 2 * espacamentoY;
		
		origens[2] = new Point((int)(X), (int)(Y));
		
		//origem da face de posicao 3
		X = 0;
		Y = 0;
		posicao = 0;
		faceTmp = (Face)this.projeto.getBloco().faces.elementAt(matrizVistas[posicao][Matrizes.PLANOS_FACE]);
		X += (int)(faceTmp.getComprimentoDesenhado(matrizVistas[posicao][Matrizes.PLANOS_VERTICE]) * zoom);
		X += origens[0].x;
		X += 2 * espacamentoX;
		Y = origens[0].y;
		
		origens[3] = new Point((int)(X), (int)(Y));
		
		//origem da face de posicao 4
		X = 0;
		Y = 0;
		posicao = 5;
		X = origens[0].x;
		faceTmp = (Face)this.projeto.getBloco().faces.elementAt(matrizVistas[posicao][Matrizes.PLANOS_FACE]);
		Y = (int)(faceTmp.getLarguraDesenhada(matrizVistas[posicao][Matrizes.PLANOS_VERTICE]) * zoom);
		Y += 3 * espacamentoY;
		
		origens[4] = new Point((int)(X), (int)(Y));
		
		//origem da face de posicao 5
		X = 0;
		Y = 0;
		X = origens[0].x;
		Y = espacamentoY;
		
		origens[5] = new Point((int)(X), (int)(Y));
		
		for(int i = 0; i < origens.length; i++){
			//System.out.println("Origem(" + pointIndex + ") -> " + origens[pointIndex].x + " " + origens[pointIndex].y);
		}
		return origens;
	}
	public void desenharEixos(Graphics2D g2d)
	{	if(dEixos)
		{	
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
	                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                RenderingHints.VALUE_ANTIALIAS_ON);	
			int fontSize = 0;
			fontSize = (int)(16);
			g2d.setColor(new Color(50, 205, 50));
//			g2d.fillRect( X - 2, Y, 2, (int)Math.round(projeto.getMaquina().getComprimento() * zoom + 5));
//			g2d.fillRect(X, Y - 2, (int)Math.round(projeto.getMaquina().getComprimento() * zoom + 10), 2);
			g2d.fillRect(X - 2, Y, 2, (int)Math.round(projeto.getBloco().getComprimento() * zoom + 5));
			g2d.fillRect(X, Y - 2, (int)Math.round(projeto.getBloco().getComprimento() * zoom + 10), 2);

			g2d.fillOval(X - 5, Y - 5, 10, 10);
			g2d.drawOval(X - 5, Y - 5, 10, 10);
			g2d.setColor(new Color(255, 0, 0));
			
			g2d.scale(1, -1);  /**************revate as letras****/
			g2d.setFont(new Font("Arial", Font.BOLD, fontSize));
//			g2d.drawString("x", (int)Math.round(projeto.getMaquina().getComprimento() * zoom + 10), Y - 25);
//			g2d.drawString("y", X - 13, (int)Math.round(-(projeto.getMaquina().getComprimento() * zoom + 10)));
			g2d.drawString("x", (int)Math.round(projeto.getBloco().getComprimento() * zoom + 10), Y - 25);
			g2d.drawString("y", X - 13, (int)Math.round(-(projeto.getBloco().getComprimento() * zoom + 10)));
			
			g2d.scale(1, -1);
		}
	}
	
	public void dBlank(Graphics2D g2d)
	{
		if (blank)
		{
			g2d.setStroke(new BasicStroke());	
			
			Face face = (Face)this.projeto.getBloco().faces.elementAt(this.facePrincipal);
			
			switch (face.verticeAtivado)
			{
			case 0:
				g2d.setColor(new Color(238, 233, 191));
				g2d.setColor(Color.BLACK);
				g2d.drawRect(X, Y, (int)Math.round(face.getComprimento() * zoom), (int)Math.round(face.getLargura() * zoom));
				break;
			case 1:
				g2d.setColor(new Color(238, 233, 191));
				g2d.setColor(Color.BLACK);
				g2d.drawRect(X, Y, (int)Math.round(face.getLargura() * zoom), (int)Math.round(face.getComprimento() * zoom));
				break;
			case 2:
				g2d.setColor(new Color(238, 233, 191));
				g2d.setColor(Color.BLACK);
				g2d.drawRect(X, Y, (int)Math.round(face.getComprimento() * zoom), (int)Math.round(face.getLargura() * zoom));
				break;
			case 3:
				g2d.setColor(new Color(238, 233, 191));
				g2d.setColor(Color.BLACK);
				g2d.drawRect(X, Y, (int)Math.round(face.getLargura() * zoom), (int)Math.round(face.getComprimento() * zoom));
				break;
			default:
				break;
			}
		}
	}
	
	public void desenharMesa(Graphics2D g2d)
	{	
		if(dMesa){
			
			int comprX = 0;
			int largY = 0;
			int posX = 0;
			int posY = 0;
		
			posX = X;
			posY = Y;
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
	                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                RenderingHints.VALUE_ANTIALIAS_ON);	
//			comprX = (int)Math.round(this.projeto.getMaquina().comprimento * zoom);
//			largY = (int)Math.round(this.projeto.getMaquina().largura * zoom);
			
			g2d.setColor(new Color(179, 238, 58));
			g2d.fillRect(posX, posY, comprX, largY);
			g2d.setColor(new Color(155, 48, 255));
			g2d.drawRect(posX, posY, comprX, largY);
			
			/*int tam = 10;

	          TexturePaint textura;
	          BufferedImage bi = new BufferedImage(tam , tam, BufferedImage.TYPE_INT_RGB);
	          Graphics2D graph = bi.createGraphics();
	          graph.setColor(Color.black);
	          graph.fillRect(10, 10, tam, tam);
	          graph.setColor(new Color(230, 230, 250));
	          graph.fillRect(tam/10, tam/10, tam, tam);
	          textura = new TexturePaint(bi, new Rectangle(10, 10, tam, tam));
	          g2d.setPaint(textura);*/
			
			
			//apaga a image desenhada antes
			
			/*g2d.setColor(new Color(12, 200, 45));
			g2d.fillRect(posX, posY, comprX, largY);*/
			
			// desenha a BufferedImage
			//g2d.drawImage(bi, null, X, Y);
			
			/***** ========================== */
			  /*int tamX, tamY = 20;
			  tamX = (int)Math.round(this.projeto.getMaquina().comprimento * zoom / 10);
			  tamY = (int)Math.round(this.projeto.getMaquina().largura * zoom / 10);
			   
			  System.out.println("zoom" + zoom);
			  
			    BufferedImage bi = new BufferedImage((int)(tamX/1), (int)(tamY/1), BufferedImage.TYPE_INT_RGB);
			    Graphics2D big = bi.createGraphics();
			    big.setColor(new Color(0, 0, 250));
			    big.fillRect(0, 0, tamX, tamY);
			    big.setColor(Color.LIGHT_GRAY);
			    big.drawRect(0, 0, tamX, tamX);
			    Rectangle r = new Rectangle(0, 0, tamX, tamY);
			    g2d.setPaint(new TexturePaint(bi, r));
			    Rectangle rect = new Rectangle(posX, posY, comprX, largY);
			    g2d.fill(rect);*/
			/******==========================*/
			int X1 = 0;
			int Y1 = 0;
			//Toolkit toolkit = this.getToolkit();
			Dimension dimension = this.getSize();
			int numLinhasVert = Math.abs(dimension.width/10);
			int numLinhasHor = Math.abs(dimension.height/20);
//			System.out.println("numLinhashor = " + numLinhasHor);
			for (int i = 0; i <= numLinhasVert; i++)
			{
				float dash1[] = {5.0f, 3.0f};
				g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, 
						BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));
//				X1 = (int)/*Math.round*/(projeto.getMaquina().comprimento / 10) * i;
				X1 = (int)(20 * i );
				g2d.setColor(Color.LIGHT_GRAY);
				g2d.drawLine((int)Math.round(20 + X1 * zoom), -dimension.height, (int)Math.round(20 + X1 * zoom), dimension.height);
				
				g2d.setColor(Color.blue);
				g2d.scale(1, -1);
				
				Font font = new Font("" , 3, 9);
				g2d.setFont(font);
				g2d.drawString("" + i * 20 ,(int)Math.round(20 + X1 * zoom), 0);
				
				g2d.scale(1, -1);
				
//				Y1 = (int)Math.round(projeto.getMaquina().largura / 10 * i);
//				g2d.drawLine(X, (int)Math.round(Y + Y1 * zoom), (int)Math.round(projeto.getMaquina().comprimento * zoom + X), (int)Math.round(Y + Y1 * zoom));
			}
			for(int i = 0; i < numLinhasHor; i++)
			{
				float dash1[] = {5.0f, 3.0f};
				g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, 
						BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));
				
				Y1 = (int)(20 * i);
				g2d.setColor(Color.LIGHT_GRAY);
				g2d.drawLine(0, (int)Math.round(20 + Y1 * zoom), dimension.width, (int)Math.round(20 + Y1 * zoom));
				
				g2d.setColor(Color.blue);
				
				Font font = new Font("" , 3, 9);
				g2d.setFont(font);
				g2d.scale(1, -1);
				g2d.drawString("" + i * 20 ,0 , -(int)Math.round(20 + Y1 * zoom));
				g2d.scale(1, -1);
				
			}
		}
	}
	
	public void setZoom(double zoom)
	{			
		int[][]todasAsVistas = Matrizes.planos[this.facePrincipal][this.verticeAtivado];
			this.zoom = zoom;
			
			this.desenhadorDeFeatures.zoom = zoom;//?????????
			/*
			if (mode)
			this.setPreferredSize(new Dimension((int)((this.projeto.getMaquina().comprimento) * zoom + 2 * X), (int)((this.projeto.getMaquina().comprimento) * zoom + 2 * Y)));
			//System.out.println(this.getPreferredSize().toString());
			else
				this.setPreferredSize(this.getImageSize(todasAsVistas));*/
				//this.setPreferredSize(new Dimension)
			this.alterou = true;
			//this.origens = this.determinarOrigens(todasAsVistas);
			this.repaint();
			//this.revalidate();
			//System.out.println(this.fundo.scrollPaneDesenho.getPreferredSize().toString());
			//this.fundo.scrollPaneDesenho.repaint();
	}
	public double getZoom(){
		return this.zoom;
	}
	
	/*public void setFaceDesenhada(int indice)
	{
		this.facePrincipal = indice;
		repaint();
	}*/
	public void alterarProjeto(Projeto projeto){
		
		this.projeto = projeto;		
		//this.setFaceDesenhada(Face.XY);/*****************/
		this.setFacePrincipal(Face.XY, verticeAtivado);
		this.repaint();
		
	}

	public void trocarModo(){
		if(this.mode)
			this.mode = false;
		else
			this.mode = true;
		
		this.alterou = true;
	}
	
	public void addClampPoints(ArrayList<Point3d> arrayAp,int rAP)
	{
		arrayApoios = arrayAp;
		this.repaint();
		
		drawCP = true;
		raioAp = rAP;
	}
	
	public void pintarFundo(Graphics2D g2d, Color color)
	{
		g2d.setColor(color);
		g2d.fillRect(0, 0, 2000, 2000);
	}
}
