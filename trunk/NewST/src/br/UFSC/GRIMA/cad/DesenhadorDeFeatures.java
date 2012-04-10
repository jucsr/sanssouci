package br.UFSC.GRIMA.cad;
import java.awt.*;

import br.UFSC.GRIMA.entidades.features.*;
public class DesenhadorDeFeatures
{
	public double zoom = 1.0;
	public boolean dFeaturesSecundarias = true;
	boolean alteraComposite = true;
	int posX, posX2, posY, posY2, comprimento, comprimento2, largura, largura2, p1X, p2X, p3X, p4X, p1Y, p2Y, p3Y, p4Y;
	
	public DesenhadorDeFeatures()
	{
		
	}
	public void desenharFeature(Feature feature, Face face, int verticeAtivado, Point origem, boolean modo, Graphics2D g2d)
	{
//		System.out.println("boss!!!!!!!!!!");
		switch (feature.getTipo())
		{
			case Feature.FURO:
				this.desenharFuro((Furo)feature, origem, modo, g2d);
				break;
			case Feature.DEGRAU:
				this.desenharDegrau((Degrau)feature, face, verticeAtivado, origem, modo, g2d);
				break;
			case Feature.RANHURA:
				this.desenharRanhura((Ranhura)feature, face, verticeAtivado, origem, modo, g2d);
				break;
			case Feature.CAVIDADE:
				this.desenharCavidade((Cavidade)feature, origem, modo, g2d);
				break;
			case Feature.CAVIDADE_FUNDO_ARREDONDADO:
				CavidadeFundoArredondado cfa = (CavidadeFundoArredondado)feature;
				Cavidade c = new Cavidade();
				c.setComprimento(cfa.getComprimento());
				c.setLargura(cfa.getLargura());
				c.setPosicao(cfa.X, cfa.Y, cfa.Z);
				c.setProfundidade(cfa.getProfundidade());
				c.setRaio(cfa.getVerticeRaio());
				this.desenharCavidade(c, origem, modo, g2d);
				break;
			case Feature.BOSS:
				if (feature.getClass() == CircularBoss.class){
					CircularBoss cb = (CircularBoss)feature;
					this.desenharCircularBoss(cb, origem, modo, g2d);

				}
			default:
				break;
		}
	}
	
	public void desenharFuro(Furo furo, Point origem, boolean modo, Graphics2D g2d)
	{
		int posX, posY, raio;
		//double zoom = 1;
		posX = (int)Math.round((furo.getPosicaoX() - furo.getRaio()) * zoom + origem.x);
		posY = (int)Math.round((furo.getPosicaoY() - furo.getRaio()) * zoom + origem.y);
		raio = (int)Math.round(furo.getRaio() * 2 * zoom);
		if(modo) //vista de frente
		{
			g2d.setColor(new Color(210, 180, 140));
			g2d.setColor(new Color(122, 139, 139));
			g2d.fillOval(posX, posY, raio, raio);
				
			g2d.setColor(new Color(165, 42, 42));
			g2d.setColor(Color.black);
			g2d.drawOval(posX, posY, raio, raio);
			
			if(furo.isPassante() == true)
			{
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
				g2d.fillOval(posX, posY, raio, raio);
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
				g2d.setStroke(new BasicStroke());
			}
			g2d.setColor(new Color(165, 42, 42));
			g2d.setColor(Color.black);
			g2d.drawOval(posX, posY, raio, raio);
		} else // vista posterior
		{
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,	RenderingHints.VALUE_ANTIALIAS_ON);	
				
			float dash1[] = {5.0f, 2.5f};
			g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));
			if (furo.isPassante())
			{
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
				g2d.fillOval(posX, posY, raio, raio);
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
				g2d.setStroke(new BasicStroke());
			}
			g2d.setColor(new Color(165, 42, 42));
			g2d.setColor(Color.black);
			g2d.drawOval(posX, posY, raio, raio);
		}
	}
	public void desenharDegrau(Degrau degrau, Face face, int verticeAtivado, Point origem, boolean modo, Graphics2D g2d)
	{
		//int posX, posY, comprimento, largura;
		//double zoom = 1;
		if (degrau.getEixo() == Degrau.HORIZONTAL){
			
			posX = (int)Math.round((degrau.getPosicaoX() * zoom + origem.x));
			comprimento = (int)Math.round(face.getComprimentoDesenhado(verticeAtivado) * zoom);
			largura = (int)Math.round(degrau.getLargura() * zoom);
			if ( degrau.Y == 0) /********base********/
			{
				posY = (int)Math.round((degrau.getPosicaoX() * zoom + origem.y));
			}
			else
			{
				posY = (int)Math.round((face.getLarguraDesenhada(verticeAtivado) - 
						degrau.getLargura()) * zoom + origem.y);
			}
		}
		else{/******************vertical********/
			posY = (int)Math.round((degrau.getPosicaoY() * zoom) + origem.y);
			comprimento = (int)Math.round(degrau.getLargura() * zoom);
			largura = (int)Math.round(face.getLarguraDesenhada(verticeAtivado) * zoom);
			if (degrau.X == 0)
			{
				posX = (int)Math.round(0 + origem.x);
			}
			else
			{
				posX = (int)Math.round((face.getComprimentoDesenhado(verticeAtivado) - 
						degrau.getLargura()) * zoom + origem.x);
			}
		}
		if (modo)
		{
			g2d.setColor(new Color(210, 180, 140));
			g2d.setColor(new Color(122, 139, 139));
			g2d.fillRect(posX, posY, comprimento, largura);
		}
		g2d.setColor(new Color(165, 42, 42));
		g2d.setColor(Color.black);
		g2d.drawRect(posX, posY, comprimento, largura);
	}
	public void desenharRanhura(Ranhura ranhura, Face face, int verticeAtivado, Point origem, boolean modo, Graphics2D g2d)
	{
		int posX, posY, comprimento, largura;
		//double zoom = 1;
		if (ranhura.getEixo() == Ranhura.HORIZONTAL)
		{
			posX = (int)Math.round((ranhura.getPosicaoX() * zoom) + origem.x);
			posY = (int)Math.round((ranhura.getPosicaoY() * zoom) + origem.y);
			
			comprimento = (int)Math.round(face.getComprimentoDesenhado(verticeAtivado) * zoom);
			largura = (int)Math.round(ranhura.getLargura() * zoom);
		}
		else	/***********vertical***************/
		{
			posX = (int)Math.round((ranhura.getPosicaoX() * zoom) + origem.x);
			posY = (int)Math.round((ranhura.getPosicaoY() * zoom) + origem.y);
			
			comprimento = (int)Math.round(ranhura.getLargura() * zoom);
			largura = (int)Math.round(face.getLarguraDesenhada(verticeAtivado) * zoom);
		}
		if (modo)
		{
			g2d.setColor(new Color(210, 180, 140));
			g2d.setColor(new Color(122, 139, 139));
			g2d.fillRect(posX , posY , comprimento, largura);
		}
		
		g2d.setColor(new Color(165, 42, 42));
		g2d.setColor(Color.black);
		g2d.drawRect(posX, posY, comprimento, largura);
	}
	public  void desenharCavidade(Cavidade cavidade, Point origem, boolean modo, Graphics2D g2d)
	{
		int posX, posY, largura, comprimento, diametro;
		posX = (int)Math.round((cavidade.getPosicaoX() * zoom) + origem.x);
		posY = (int)Math.round((cavidade.getPosicaoY() * zoom) + origem.y);
		largura = (int)Math.round(cavidade.getLargura() * zoom);
		comprimento = (int)Math.round(cavidade.getComprimento() * zoom);
		diametro = (int)Math.round(cavidade.getRaio() * 2 * zoom);
		if (modo)
		{
			g2d.setColor(new Color(210, 180, 140));
			g2d.setColor(new Color(122, 139, 139));
			g2d.fillRoundRect(posX, posY, comprimento, largura, diametro, diametro);
			
			if(cavidade.isPassante() == true)
			{
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
				g2d.fillRoundRect(posX, posY, comprimento, largura, diametro, diametro);
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
				g2d.setStroke(new BasicStroke());
			}
			g2d.setColor(new Color(165, 42, 42));
			g2d.setColor(Color.black);
			g2d.drawRoundRect(posX, posY, comprimento, largura, diametro, diametro);
			
		}else
		{
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);	
			
			float dash1[] = {5.0f, 2.5f};
			g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));
			
//			g2d.setColor(new Color(210, 180, 140));
//			g2d.setColor(new Color(122, 139, 139));
//			g2d.fillRoundRect(posX, posY, comprimento, largura, diametro, diametro);
//		
			if(cavidade.isPassante() == true)
			{
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
				g2d.fillRoundRect(posX, posY, comprimento, largura, diametro, diametro);
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
				g2d.setStroke(new BasicStroke());
			}
			g2d.setColor(new Color(165, 42, 42));
			g2d.setColor(Color.black);
			g2d.drawRoundRect(posX, posY, comprimento, largura, diametro, diametro);
		}
		
		
//			g2d.setColor(new Color(165, 42, 42));
//			g2d.setColor(Color.black);
//			g2d.drawRoundRect(posX, posY, comprimento, largura, diametro, diametro);
		
	}
	public void desenharCircularBoss(CircularBoss cb, Point origem, boolean modo, Graphics2D g2d)
	{

		int posX, posX2, posY, posY2, raio, raio2;
		//double zoom = 1;
		posX = (int)Math.round((cb.getPosicaoX() - cb.getDiametro1()/2) * zoom + origem.x);
		posX2=(int)Math.round((cb.getPosicaoX() - cb.getDiametro2()/2) * zoom + origem.x);
		posY = (int)Math.round((cb.getPosicaoY() - cb.getDiametro1()/2) * zoom + origem.y);
		posY2 = (int)Math.round((cb.getPosicaoY() - cb.getDiametro2()/2) * zoom + origem.y);

		raio = (int)Math.round(cb.getDiametro1() * zoom);
		raio2 = (int)Math.round(cb.getDiametro2() * zoom);

		if(modo) //vista de frente
		{
//			g2d.setColor(new Color(210, 180, 140));
			g2d.setColor(new Color(122, 139, 139));
			g2d.fillOval(posX2, posY2, raio2, raio2);
			
			g2d.setColor(new Color(165, 42, 42));
			g2d.setColor(Color.black);
			g2d.drawOval(posX2, posY2, raio2, raio2);
			
			g2d.setColor(new Color(205, 205, 193));
			g2d.fillOval(posX, posY, raio, raio);
				
			g2d.setColor(new Color(165, 42, 42));
			g2d.setColor(Color.black);
			g2d.drawOval(posX, posY, raio, raio);
			
//			System.out.println("ccccc");
			
		} else // vista posterior
		{
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,	RenderingHints.VALUE_ANTIALIAS_ON);	
				
			float dash1[] = {5.0f, 2.5f};
			g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));
//			if (cb.isPassante())
//			{
//				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
//				g2d.fillOval(posX, posY, raio, raio);
//				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
//				g2d.setStroke(new BasicStroke());
//			}
			g2d.setColor(new Color(165, 42, 42));
			g2d.setColor(Color.black);
			g2d.drawOval(posX, posY, raio, raio);
		}
	
	}
	
	
	/**
	 * 
	 * @param br.UFSC.GRIMA.feature
	 * @param face
	 * @param verticeAtivado
	 * @param origem
	 * @param posicao
	 * @param g2d
	 */
	public void desenharFeatureOrtogonal(Feature feature, Face face, int verticeAtivado, Point origem, int posicao, Graphics2D g2d)
	{
		switch(feature.getTipo())
		{
			case Feature.FURO:
				this.desenharFuroOrtogonal((Furo)feature, face, verticeAtivado, origem, posicao, g2d);
				break;
			case Feature.DEGRAU:
				this.desenharDegrauOrtogonal((Degrau)feature, face, verticeAtivado, origem, posicao, g2d);
				break;
			case Feature.RANHURA:
				this.desenharRanhuraOrtogonal((Ranhura)feature, face, verticeAtivado, origem, posicao, g2d);
				break;
			case Feature.CAVIDADE:
				this.desenharCavidadeOrtogonal((Cavidade)feature, face, verticeAtivado, origem, posicao, g2d);
				break;
			case Feature.CAVIDADE_FUNDO_ARREDONDADO:
				CavidadeFundoArredondado cfa = (CavidadeFundoArredondado)feature;
				Cavidade c = new Cavidade();
				c.X = cfa.X;
				c.Y = cfa.Y;
				c.Z = cfa.Z;
				c.setComprimento(cfa.getComprimento());
				c.setLargura(cfa.getLargura());
				c.setProfundidade(cfa.getProfundidade());
				c.setRaio(cfa.getVerticeRaio());
				
				this.desenharCavidadeOrtogonal(c, face, verticeAtivado, origem, posicao, g2d);
			default:
				break;
		}
	}
	
	public void desenharFuroOrtogonal(Furo furo, Face faceDesenhada, int verticeAtivado, Point origem, int posicao, Graphics2D g2d)
	{
		float dash1[] = {5.0f, 2.5f};
		g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, 
				BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));
		float dash2[] = {15.0f, 2.5f, 1f, 2.5f};
		switch(posicao)
		{
			case 0:
				if (this.dFeaturesSecundarias)
				{
					posX = (int)Math.round((furo.getPosicaoZ()) * zoom + origem.x);
					posY = (int)Math.round(((faceDesenhada.getLarguraDesenhada(verticeAtivado) - furo.getPosicaoX() - furo.getRaio()) * zoom + origem.y));
					comprimento = (int)Math.round((furo.getProfundidade()) * zoom);
					largura = (int)Math.round((furo.getRaio() * 2 ) * zoom);
					
					g2d.setColor(new Color(0, 0, 255));
					g2d.drawRect(posX, posY, comprimento, largura);
					
					g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, 
							BasicStroke.JOIN_MITER, 10.0f, dash2, 0.0f));
					
					p1X = (int)Math.round(( - 5 + furo.getPosicaoZ()) * zoom + origem.x);
					p2X = (int)Math.round((furo.getPosicaoZ() + furo.getProfundidade() + 5) * zoom + origem.x);
					p1Y = p2Y =(int)Math.round((faceDesenhada.getLarguraDesenhada(verticeAtivado) - furo.getPosicaoX()) * zoom + origem.y);
					
					g2d.setColor(Color.RED);
					g2d.drawLine(p1X, p1Y, p2X , p2Y);
				}
				break;
			case 1:
				if (this.dFeaturesSecundarias)
				{
					posX = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado) - furo.getPosicaoX() - furo.getRaio()) * zoom + origem.x);
					posY = (int)Math.round((faceDesenhada.getLarguraDesenhada(verticeAtivado) - furo.getProfundidade() - furo.getPosicaoZ()) * zoom + origem.y);
					comprimento = (int)Math.round(furo.getRaio() * 2 * zoom);
					largura = (int)Math.round(furo.getProfundidade() * zoom);
					g2d.setColor(new Color(0, 0, 255));
					g2d.drawRect(posX, posY, comprimento, largura);
					
					p1X = p2X = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado) - furo.getPosicaoX()) * zoom + origem.x) ;
					
					p1Y = (int)Math.round((faceDesenhada.getLarguraDesenhada(verticeAtivado) - furo.getPosicaoZ() + 5) * zoom + origem.y);
					p2Y = (int)Math.round((faceDesenhada.getLarguraDesenhada(verticeAtivado) - furo.getProfundidade() - furo.getPosicaoZ() - 5) * zoom + origem.y);
					
					g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, 
							BasicStroke.JOIN_MITER, 10.0f, dash2, 0.0f));
					
					g2d.setColor(Color.RED);
					g2d.drawLine(p1X, p1Y, p2X, p2Y);		
				}
				break;
			case 2:
				if (this.dFeaturesSecundarias)
				{
					posX = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado) - furo.getProfundidade() - furo.getPosicaoZ()) * zoom + origem.x);
					posY = (int)Math.round((furo.getPosicaoX() - furo.getRaio()) * zoom + origem.y);
					comprimento = (int)Math.round(furo.getProfundidade() * zoom);
					largura = (int)Math.round(furo.getRaio() * 2 * zoom);
					
					g2d.setColor(new Color(0, 0, 255));
					g2d.drawRect(posX, posY, comprimento, largura);
					
					p1X = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado) - furo.getProfundidade() - furo.getPosicaoZ() - 5) * zoom + origem.x);
					p2X = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado) - furo.getPosicaoZ() + 5) * zoom + origem.x);
					p1Y = p2Y = (int)Math.round((furo.getPosicaoX()) * zoom + origem.y);
					
					g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, 
							BasicStroke.JOIN_MITER, 10.0f, dash2, 0.0f));
					
					g2d.setColor(Color.RED);
					g2d.drawLine(p1X, p1Y, p2X, p2Y);
				}
				
				break;
			case 3:
				if (this.dFeaturesSecundarias)
				{
					posX = (int)Math.round((furo.getPosicaoX() - furo.getRaio()) * zoom + origem.x);
					posY = (int)Math.round((furo.getPosicaoZ()) * zoom + origem.y);
					comprimento = (int)Math.round(furo.getRaio() * 2 * zoom);
					largura = (int)Math.round(furo.getProfundidade() * zoom);
					
					g2d.setColor(new Color(0, 0, 255));
					g2d.drawRect(posX, posY, comprimento, largura);
					
					p1X = p2X = (int)Math.round((furo.getPosicaoX()) * zoom + origem.x);
					p1Y = (int)Math.round((furo.getPosicaoZ() - 5) * zoom + origem.y);
					p2Y = (int)Math.round((furo.getPosicaoZ() + furo.getProfundidade() + 5) * zoom + origem.y);
					
					g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, 
							BasicStroke.JOIN_MITER, 10.0f, dash2, 0.0f));
					
					g2d.setColor(Color.RED);
					g2d.drawLine(p1X, p1Y, p2X, p2Y);
				}
				
				break;
			default:
				break;
		}
	}
	public void desenharDegrauOrtogonal(Degrau degrau, Face faceDesenhada, int verticeAtivado, Point origem, int posicao, Graphics2D g2d)
	{
		float dash1[] = {5.0f, 2.5f};
		g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, 
				BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));
		boolean alteraComposite = false;
		switch(posicao)
		{
			case 0:
				if (degrau.getEixo() == Degrau.HORIZONTAL)
				{
					if (degrau.getPosicaoY() == 0)
					{
						if (this.dFeaturesSecundarias)
						{
							g2d.setColor(new Color(0, 0, 255));
							p1X = p2X = (int)Math.round((degrau.getPosicaoZ() + degrau.getProfundidade()) * zoom + origem.x);
							p1Y = (int)Math.round((0) * zoom + origem.y);
							p2Y = (int)Math.round((faceDesenhada.getLarguraDesenhada(verticeAtivado)) * zoom + origem.y);
							g2d.drawLine(p1X, p1Y, p2X, p2Y);
						}
					}
					else
					{
						g2d.setStroke(new BasicStroke());
						posX = (int)Math.round((degrau.getPosicaoZ()) * zoom + origem.x);
						posY = (int)Math.round((0) * zoom + origem.y);
						comprimento = (int)Math.round(degrau.getProfundidade() * zoom);
						largura = (int)Math.round(faceDesenhada.getLarguraDesenhada(verticeAtivado) * zoom);
						
						g2d.setColor(new Color(210, 180, 140));
						g2d.setColor(new Color(122, 139, 139));
						g2d.fillRect(posX, posY, comprimento, largura);
						g2d.setColor(new Color(165, 42, 42));
						g2d.setColor(Color.black);
						g2d.drawRect(posX, posY, comprimento, largura);
					}
				}
				else		/*************######## VERTICAL ###########**********/
				{	
					g2d.setStroke(new BasicStroke());
					posX = (int)Math.round((degrau.getPosicaoZ()) * zoom + origem.x);
					posY = (int)Math.round((faceDesenhada.getLarguraDesenhada(verticeAtivado) - degrau.getPosicaoX() - degrau.getLargura()) * zoom + origem.y);
					comprimento = (int)Math.round(degrau.getProfundidade() * zoom);
					largura = (int)Math.round(degrau.getLargura() * zoom);
					alteraComposite = true;
					p1X = p3X = (int)Math.round((degrau.getPosicaoZ()) * zoom + origem.x);
					p2X = p4X = (int)Math.round((degrau.getPosicaoZ() + degrau.getProfundidade()) * zoom + origem.x);
					if (degrau.getPosicaoX() == 0)
					{
						p1Y = p2Y = (int)Math.round((faceDesenhada.getLarguraDesenhada(verticeAtivado)) * zoom + origem.y);
						p3Y = p4Y = (int)Math.round((faceDesenhada.getLarguraDesenhada(verticeAtivado) - degrau.getLargura()) * zoom + origem.y);
					}
					else
					{
						p1Y = p2Y = (int)Math.round(0 * zoom + origem.y);
						p3Y = p4Y = (int)Math.round((degrau.getLargura()) * zoom + origem.y);
					}
					if(alteraComposite)
					{g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
					g2d.fillRect(posX, posY, comprimento + 1, largura + 1);}
					if(alteraComposite)
						g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
					alteraComposite = false;
					g2d.setColor(Color.BLACK);
					g2d.drawLine(p4X, p4Y , p2X, p2Y);//linhas verticais
					g2d.drawLine(p3X, p3Y, p4X, p4Y);//linhas horizontais
				}
				break;
			case 1:
				if (degrau.getEixo() == Degrau.HORIZONTAL)
				{
					if (degrau.getPosicaoY() == 0)
					{
						if (this.dFeaturesSecundarias)
						{
							p1X = (int)Math.round(0 * zoom + origem.x);
							p2X = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado)) * zoom + origem.x);
							p1Y = p2Y = (int)Math.round((faceDesenhada.getLarguraDesenhada(verticeAtivado) - degrau.getPosicaoZ() - degrau.getProfundidade()) * zoom + origem.y);
							g2d.setColor(Color.BLUE);
							g2d.drawLine(p1X, p1Y, p2X, p2Y);
						}
					}
					else
					{
						g2d.setStroke(new BasicStroke());
						g2d.setColor(new Color(210, 180, 140));
						g2d.setColor(new Color(122, 139, 139));

						posX = (int)Math.round(0 * zoom + origem.x);
						posY = (int)Math.round((faceDesenhada.getLarguraDesenhada(verticeAtivado) - degrau.getPosicaoZ() - degrau.getProfundidade()) * zoom + origem.y);
						comprimento = (int)Math.round(faceDesenhada.getComprimentoDesenhado(verticeAtivado) * zoom);
						largura = (int)Math.round(degrau.getProfundidade() * zoom);
						g2d.fillRect(posX, posY, comprimento, largura);
						g2d.setColor(new Color(165, 42, 42));
						
						g2d.setColor(Color.black);
						g2d.drawRect(posX, posY, comprimento, largura);
					}
				}
				else
				{
					g2d.setStroke(new BasicStroke());
					posX = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado) - degrau.getPosicaoX() - degrau.getLargura()) * zoom + origem.x);
					posY = (int)Math.round((faceDesenhada.getLarguraDesenhada(verticeAtivado) - degrau.getProfundidade() - degrau.getPosicaoZ()) * zoom + origem.y);
					comprimento = (int)Math.round(degrau.getLargura() * zoom + 2);
					largura = (int)Math.round(degrau.getProfundidade() * zoom + 2);
					alteraComposite = true;
					
					p1Y = p2Y = (int)Math.round((faceDesenhada.getLarguraDesenhada(verticeAtivado) - degrau.getProfundidade() - degrau.getPosicaoZ()) * zoom + origem.y);
					p3Y = p4Y = (int)Math.round((faceDesenhada.getLarguraDesenhada(verticeAtivado) - degrau.getPosicaoZ()) * zoom + origem.y);
					if (degrau.getPosicaoX() == 0)
					{
						p1X = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado)) * zoom + origem.x);
						p2X = p4X = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado) - degrau.getLargura()) * zoom + origem.x);
					}
					else
					{
						p1X = (int)Math.round(0 * zoom + origem.x);
						p2X = p4X = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado) - degrau.getPosicaoX()) * zoom + origem.x);
					}
					if(alteraComposite)
						g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
					g2d.fillRect(posX, posY, comprimento + 1, largura + 1);
					if(alteraComposite){
						g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
						alteraComposite = false;
					}
					g2d.setColor(Color.BLACK);
					g2d.drawLine(p1X, p1Y, p2X, p2Y);//horizontal
					g2d.drawLine(p2X, p2Y, p4X, p4Y);//vertical
				}
				break;
			case 2:
				if (degrau.getEixo() == Degrau.HORIZONTAL)
				{
					if (degrau.getPosicaoY() == 0)
					{
						if (this.dFeaturesSecundarias)
						{
							p1X = p2X = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado) - degrau.getPosicaoZ() - degrau.getProfundidade()) * zoom + origem.x);
							p1Y = (int)Math.round(0 * zoom + origem.y);
							p2Y = (int)Math.round((faceDesenhada.getLarguraDesenhada(verticeAtivado)) * zoom + origem.y);
							g2d.setColor(Color.BLUE);
							g2d.drawLine(p1X, p1Y, p2X, p2Y);
						}
					}
					else
					{
						g2d.setStroke(new BasicStroke());
						g2d.setColor(new Color(210, 180, 140));
						g2d.setColor(new Color(122, 139, 139));

						posX = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado) - degrau.getProfundidade() - degrau.getPosicaoZ()) * zoom + origem.x);
						posY = (int)Math.round(0 * zoom + origem.y);
						comprimento = (int)Math.round((degrau.getProfundidade()) * zoom);
						largura = (int)Math.round(faceDesenhada.getLarguraDesenhada(verticeAtivado) * zoom);
						g2d.fillRect(posX, posY, comprimento, largura);
						g2d.setColor(new Color(165, 42, 42));
						g2d.setColor(Color.black);
						g2d.drawRect(posX, posY, comprimento, largura);
					}
				}
				else	/***********VERTICAL***************/
				{
					posX = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado) - degrau.getProfundidade() - degrau.getPosicaoZ()) * zoom + origem.x);
					posY = (int)Math.round((degrau.getPosicaoX()) * zoom + origem.y);
					comprimento = (int)Math.round(degrau.getProfundidade() * zoom );
					largura = (int)Math.round(degrau.getLargura() * zoom );
					alteraComposite = true;
					p1X = p3X = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado) - degrau.getProfundidade() - degrau.getPosicaoZ()) * zoom + origem.x);
					p2X = p4X = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado) - degrau.getPosicaoZ()) * zoom + origem.x);
					if(degrau.getPosicaoX() == 0)
					{
						p1Y = p2Y = (int)Math.round(0 * zoom + origem.y);
						p3Y = p4Y = (int)Math.round((degrau.getLargura()) * zoom + origem.y);
					}
					else
					{
						p1Y = p2Y = (int)Math.round((faceDesenhada.getLarguraDesenhada(verticeAtivado)) * zoom + origem.y);
						p3Y = p4Y = (int)Math.round((faceDesenhada.getLarguraDesenhada(verticeAtivado) - degrau.getLargura()) * zoom + origem.y);
					}
					if(alteraComposite)
						g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
					g2d.fillRect(posX, posY, comprimento + 1, largura + 1);
					if(alteraComposite)
						g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
					alteraComposite = false;
					g2d.setColor(Color.BLACK);
					g2d.setStroke(new BasicStroke());
					g2d.drawLine(p1X, p1Y, p3X, p3Y);
					g2d.drawLine(p3X, p3Y, p4X, p4Y);
				}
				break;
			case 3:
			{
				if (degrau.getEixo() == Degrau.HORIZONTAL)
				{
					if (degrau.getPosicaoY() == 0)
					{
						if (this.dFeaturesSecundarias)
						{
							p1X = (int)Math.round(0 * zoom + origem.x);
							p2X = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado)) * zoom + origem.x);
							p1Y = p2Y = (int)Math.round((degrau.getPosicaoZ() + degrau.getProfundidade()) * zoom + origem.y);
							g2d.setColor(Color.BLUE);
							g2d.drawLine(p1X, p1Y, p2X, p2Y);
						}
					}
					else
					{
						g2d.setStroke(new BasicStroke());
						g2d.setColor(new Color(210, 180, 140));
						g2d.setColor(new Color(122, 139, 139));

						posX = (int)Math.round(0 * zoom + origem.x);
						posY = (int)Math.round((degrau.getPosicaoZ()) * zoom + origem.y);
						comprimento = (int)Math.round(faceDesenhada.getComprimentoDesenhado(verticeAtivado) * zoom);
						largura = (int)Math.round(degrau.getProfundidade() * zoom);
						
						g2d.fillRect(posX, posY, comprimento, largura);
						g2d.setColor(new Color(165, 42, 42));
						g2d.setColor(Color.black);
						g2d.drawRect(posX , posY, comprimento, largura);
					}
				}
				else  /*********                  VERTICAL           *************/
				{
						g2d.setStroke(new BasicStroke());
						//posX = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado) - degrau.getPosicaoX() - degrau.getLargura()) * zoom);
						posY = (int)Math.round((degrau.getPosicaoZ()) * zoom + origem.y);
						comprimento = (int)Math.round(degrau.getLargura() * zoom);
						largura = (int)Math.round(degrau.getProfundidade() * zoom);
						alteraComposite = true;
						
						p1Y = p2Y = (int)Math.round((degrau.getPosicaoZ()) * zoom + origem.y);
						p3Y = p4Y = (int)Math.round((degrau.getPosicaoZ() + degrau.getProfundidade()) * zoom + origem.y);
						if (degrau.getPosicaoX() == 0)
						{
							posX = (int)Math.round(0 * zoom + origem.x);
							p1X = p3X = (int)Math.round((degrau.getLargura()) * zoom + origem.x);
							p2X = p4X = (int)Math.round(0 * zoom + origem.x);
						}
						else
						{
							posX = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado) - degrau.getLargura()) * zoom + origem.x);
							p1X = p3X = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado) - degrau.getLargura()) * zoom + origem.x);
							p2X = p4X = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado)) * zoom + origem.x);
						}
						if(alteraComposite)
							g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
						g2d.fillRect(posX, posY, comprimento + 1, largura + 1);
						if(alteraComposite){
							g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
							alteraComposite = false;
						}
						g2d.setColor(Color.BLACK);
						g2d.drawLine(p1X, p1Y, p3X, p3Y);//horizontal
						g2d.drawLine(p3X, p3Y, p4X, p4Y);//vertical
				}
			}
				break;
			default:
				break;
		}
	}
	public void desenharRanhuraOrtogonal(Ranhura ranhura, Face faceDesenhada, int verticeAtivado, Point origem, int posicao, Graphics2D g2d)
	{
		float dash1[] = {5.0f, 2.5f};
		g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, 
				BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));
		switch(posicao)
		{
			case 0:
				if (ranhura.getEixo() == Ranhura.HORIZONTAL)/********NESTA SITUA��O � PARA VER PONTILHADO*********/
				{
					if (this.dFeaturesSecundarias)
					{
						posX = (int)Math.round((ranhura.getPosicaoZ()) * zoom + origem.x);
						posY = (int)Math.round(0 * zoom + origem.y);
						comprimento = (int)Math.round(ranhura.getProfundidade() * zoom);
						largura = (int)Math.round(faceDesenhada.getLarguraDesenhada(verticeAtivado) * zoom);
						p1X = p2X = (int)Math.round((ranhura.getPosicaoZ() + ranhura.getProfundidade()) * zoom + origem.x);
						p1Y = (int)Math.round(0 * zoom + origem.y);
						p2Y = (int)Math.round((faceDesenhada.getLarguraDesenhada(verticeAtivado)) * zoom + origem.y);
						g2d.setColor(new Color(0, 0, 255));
						g2d.drawLine(p1X, p1Y, p2X, p2Y);
					}
				}
				else/******************NESTA � PARA ALTERAR O PERFIL DA FACE**********************/
				{
					g2d.setStroke(new BasicStroke());
					posX = (int)Math.round((ranhura.getPosicaoZ()) * zoom + origem.x);
					posY = (int)Math.round((faceDesenhada.getLarguraDesenhada(verticeAtivado) - ranhura.getPosicaoX() - ranhura.getLargura()) * zoom + origem.y);
					comprimento = (int)Math.round(ranhura.getProfundidade() * zoom);
					largura = (int)Math.round(ranhura.getLargura() * zoom);
					g2d.setColor(new Color(165, 42, 42));
					g2d.fillRect(posX, posY, comprimento, largura);
					alteraComposite = true;
					p1X = p3X = (int)Math.round((ranhura.getPosicaoZ()) * zoom + origem.x);
					p2X = p4X = (int)Math.round((ranhura.getPosicaoZ() + ranhura.getProfundidade()) * zoom + origem.x);
					p1Y = p2Y = (int)Math.round((faceDesenhada.getLarguraDesenhada(verticeAtivado) - ranhura.getPosicaoX() - ranhura.getLargura()) * zoom + origem.y);
					p3Y = p4Y = (int)Math.round((faceDesenhada.getLarguraDesenhada(verticeAtivado) - ranhura.getPosicaoX()) * zoom + origem.y);
					if(alteraComposite)
					{
						g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
						g2d.fillRect(posX, posY, comprimento + 1, largura + 1);
					}
					if(alteraComposite)
						g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
					alteraComposite = false;
					g2d.drawLine(p1X, p1Y, p2X, p2Y);
					g2d.drawLine(p2X, p2Y, p4X, p4Y);
					g2d.drawLine(p3X, p3Y, p4X, p4Y);
				}
				break;
			case 1:
				if (ranhura.getEixo() == Ranhura.HORIZONTAL)
				{
					if (this.dFeaturesSecundarias)
					{
						g2d.setColor(Color.blue);
						p1X = (int)Math.round(0 * zoom + origem.x);
						p2X = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado)) * zoom + origem.x);
						p1Y = p2Y = (int)Math.round((faceDesenhada.getLarguraDesenhada(verticeAtivado) - ranhura.getPosicaoZ() - ranhura.getProfundidade()) * zoom + origem.y);
						g2d.drawLine(p1X , p1Y, p2X, p2Y);
					}
				}
				else
				{
					posX = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado) - ranhura.getPosicaoX() - ranhura.getLargura()) * zoom + origem.x);
					posY = (int)Math.round((faceDesenhada.getLarguraDesenhada(verticeAtivado) - ranhura.getPosicaoZ() - ranhura.getProfundidade()) * zoom + origem.y);
					comprimento = (int)Math.round(ranhura.getLargura() * zoom);
					largura = (int)Math.round(ranhura.getProfundidade() * zoom);
					alteraComposite = true;
					p1X = p3X = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado) - ranhura.getPosicaoX() - ranhura.getLargura()) * zoom + origem.x);
					p2X = p4X = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado) - ranhura.getPosicaoX()) * zoom + origem.x);
					p1Y = p2Y = (int)Math.round((faceDesenhada.getLarguraDesenhada(verticeAtivado) - ranhura.getProfundidade() - ranhura.getPosicaoZ()) * zoom + origem.y);
					p3Y = p4Y = (int)Math.round((faceDesenhada.getLarguraDesenhada(verticeAtivado) - ranhura.getPosicaoZ()) * zoom + origem.y);
					if(alteraComposite)
					{
						g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
						g2d.fillRect(posX, posY, comprimento + 1, largura + 1);
					}
					if(alteraComposite)
						g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
					alteraComposite = false;
					g2d.setStroke(new BasicStroke());
					g2d.setColor(Color.BLACK);
					g2d.drawLine(p3X, p3Y, p1X, p1Y);
					g2d.drawLine(p1X, p1Y, p2X, p2Y);
					g2d.drawLine(p2X, p2Y, p4X, p4Y);
				}
				break;
			case 2:
				if (ranhura.getEixo() == Ranhura.HORIZONTAL)
				{
					if (this.dFeaturesSecundarias)
					{
						p1X = p2X = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado) - ranhura.getPosicaoZ() - ranhura.getProfundidade()) * zoom + origem.x);
						p1Y = (int)Math.round(0 * zoom + origem.y);
						p2Y = (int)Math.round((faceDesenhada.getLarguraDesenhada(verticeAtivado)) * zoom + origem.y);
						g2d.setColor(Color.BLUE);
						g2d.drawLine(p1X, p1Y, p2X, p2Y);
					}
				}
				else
				{
					posX = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado) - ranhura.getPosicaoZ() - ranhura.getProfundidade()) * zoom + origem.x);
					posY = (int)Math.round((ranhura.getPosicaoX()) * zoom + origem.y);/***@@@@@@@@@@@@@@@@@@@@         &&&&&&&&             @@@@@@@@@@@@@@@@@*/
					comprimento = (int)Math.round(ranhura.getProfundidade() * zoom);
					largura = (int)Math.round(ranhura.getLargura() * zoom);
					alteraComposite = true;
					p1X = p3X = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado) - ranhura.getProfundidade() - ranhura.getPosicaoZ()) * zoom + origem.x);
					p2X = p4X = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado) - ranhura.getPosicaoZ()) * zoom + origem.x);
					p1Y = p2Y = (int)Math.round((ranhura.getPosicaoX()) * zoom + origem.y);
					p3Y = p4Y = (int)Math.round((ranhura.getPosicaoX() + ranhura.getLargura()) * zoom + origem.y);
					if(alteraComposite)
						g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
					g2d.fillRect(posX, posY, comprimento + 1, largura + 1);
					if(alteraComposite)
						g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
						alteraComposite = false;
					g2d.setStroke(new BasicStroke());
					g2d.setColor(Color.BLACK);
					g2d.drawLine(p1X, p1Y, p2X, p2Y);
					g2d.drawLine(p1X, p1Y, p3X, p3Y);
					g2d.drawLine(p3X, p3Y, p4X, p4Y);
				}
				break;
			case 3:
				if (ranhura.getEixo() == Ranhura.HORIZONTAL)
				{
					if (this.dFeaturesSecundarias)
					{
						p1X = (int)Math.round(0 * zoom + origem.x);
						p2X = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado)) * zoom + origem.x);
						p1Y = p2Y = (int)Math.round((ranhura.getPosicaoZ() + ranhura.getProfundidade()) * zoom + origem.y);
						g2d.setColor(Color.BLUE);
						g2d.drawLine(p1X, p1Y, p2X, p2Y);
					}
					
				}
				else
				{
					posX = (int)Math.round((ranhura.getPosicaoX()) * zoom + origem.x);
					posY = (int)Math.round((ranhura.getPosicaoZ()) * zoom + origem.y);
					comprimento = (int)Math.round(ranhura.getLargura() * zoom);
					largura = (int)Math.round(ranhura.getProfundidade() * zoom);
					alteraComposite = true;
					p1X = p3X = (int)Math.round((ranhura.getPosicaoX()) * zoom + origem.x);
					p2X = p4X = (int)Math.round((ranhura.getPosicaoX() + ranhura.getLargura()) * zoom + origem.x);
					p1Y = p2Y = (int)Math.round((ranhura.getPosicaoZ()) * zoom + origem.y);
					p3Y = p4Y = (int)Math.round((ranhura.getPosicaoZ() + ranhura.getProfundidade()) * zoom + origem.y);
					if(alteraComposite)
					{
						g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
						g2d.fillRect(posX, posY, comprimento + 1, largura + 1);
					}
					if(alteraComposite)
						g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
					alteraComposite = false;
					g2d.setStroke(new BasicStroke());
					g2d.setColor(Color.BLACK);
					g2d.drawLine(p1X, p1Y, p3X, p3Y);
					g2d.drawLine(p3X, p3Y, p4X, p4Y);
					g2d.drawLine(p4X, p4Y, p2X, p2Y);
				}
				break;
			default:
				break;
		}
	}
	public void desenharCavidadeOrtogonal(Cavidade cavidade, Face faceDesenhada, int verticeAtivado, Point origem, int posicao, Graphics2D g2d)
	{
		float dash1[] = {5.0f, 2.5f};
		g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, 
				BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));
		switch(posicao)
		{
			case 0:
				if (this.dFeaturesSecundarias)
				{
					posX = (int)Math.round((cavidade.getPosicaoZ()) * zoom + origem.x);
					posY = (int)Math.round((faceDesenhada.getLarguraDesenhada(verticeAtivado) - cavidade.getPosicaoX() - cavidade.getComprimento()) * zoom + origem.y);
					comprimento = (int)(cavidade.getProfundidade() * zoom);
					largura = (int)(cavidade.getComprimento() * zoom);
					
					p1X = p3X = (int)Math.round((cavidade.getPosicaoZ()) * zoom + origem.x);
					p1Y = p2Y = (int)Math.round((faceDesenhada.getLarguraDesenhada(verticeAtivado) - cavidade.getPosicaoX() - cavidade.getComprimento() + cavidade.getRaio()) * zoom + origem.y);
					p2X = p4X = (int)Math.round((cavidade.getPosicaoZ() + cavidade.getProfundidade()) * zoom + origem.x);
					p3Y = p4Y = (int)Math.round((faceDesenhada.getLarguraDesenhada(verticeAtivado) - cavidade.getPosicaoX() - cavidade.getRaio()) * zoom + origem.y);
					g2d.setColor(new Color(0, 0, 255));
					g2d.drawLine(p1X, p1Y, p2X, p2Y);
					g2d.drawLine(p3X, p3Y, p4X, p4Y);
					g2d.drawRect(posX, posY, comprimento, largura);
				}
					
				break;
			case 1:
				if (this.dFeaturesSecundarias)
				{
					posX = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado) - cavidade.getPosicaoX() - cavidade.getComprimento()) * zoom + origem.x);
					posY = (int)Math.round((faceDesenhada.getLarguraDesenhada(verticeAtivado) -  cavidade.getProfundidade() - cavidade.getPosicaoZ()) * zoom + origem.y);
					comprimento = (int)Math.round(cavidade.getComprimento() * zoom);
					largura = (int)Math.round(cavidade.getProfundidade() * zoom);
					
					p1X = p2X = (int)Math.round(posX + cavidade.getRaio() * zoom);
					p1Y = p3Y = posY;
					p2Y = p4Y = (int)Math.round(posY + cavidade.getProfundidade() * zoom);
					p3X = p4X = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado) - cavidade.getPosicaoX() - cavidade.getRaio()) * zoom + origem.x);
					g2d.setColor(new Color(0, 0, 255));
					g2d.drawLine(p1X, p1Y, p2X, p2Y);
					g2d.drawLine(p3X, p3Y, p4X, p4Y);
					g2d.setColor(new Color(0, 0, 255));
					g2d.drawRect(posX, posY, comprimento, largura);
				}
				break;
			case 2:
				if (this.dFeaturesSecundarias)
				{
					posX = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado) - cavidade.getProfundidade() - cavidade.getPosicaoZ()) * zoom + origem.x);
					posY = (int)Math.round((cavidade.getPosicaoX()) * zoom + origem.y);
					comprimento = (int)Math.round(cavidade.getProfundidade() * zoom);
					largura = (int)Math.round(cavidade.getComprimento() * zoom);
					
					p1X = p3X = posX;
					p2X = p4X = (int)Math.round(posX + cavidade.getProfundidade() * zoom);
					p1Y = p2Y = (int)Math.round(posY + cavidade.getRaio() * zoom);
					p3Y = p4Y = (int)Math.round(posY + (cavidade.getComprimento() - cavidade.getRaio()) * zoom);
					g2d.setColor(new Color(0, 0, 255));
					g2d.drawLine(p1X, p1Y, p2X, p2Y);
					g2d.drawLine(p3X, p3Y, p4X, p4Y);
					g2d.setColor(new Color(0, 0, 255));
					g2d.drawRect(posX, posY, comprimento, largura);
				}
				break;
			case 3:
				if (this.dFeaturesSecundarias)
				{
					posX = (int)Math.round((cavidade.getPosicaoX()) * zoom + origem.x);
					posY = (int)Math.round((cavidade.getPosicaoZ()) * zoom + origem.y);
					comprimento = (int)Math.round(cavidade.getComprimento() * zoom);
					largura = (int)Math.round(cavidade.getProfundidade() * zoom);
					
					p1X = p2X = (int)Math.round(posX + cavidade.getRaio() * zoom);
					p3X = p4X = (int)Math.round(posX + (cavidade.getComprimento() - cavidade.getRaio()) * zoom);
					p1Y = p3Y = (int)Math.round(posY);
					p2Y = p4Y = (int)Math.round(posY + cavidade.getProfundidade() * zoom);
					g2d.setColor(new Color(0, 0, 255));
					g2d.drawLine(p1X, p1Y, p2X, p2Y);
					g2d.drawLine(p3X, p3Y, p4X, p4Y);
					g2d.setColor(new Color(0, 0, 255));
					g2d.drawRect(posX, posY, comprimento, largura);
				}
				break;
			default:
				break;
		}
	}
	
	public void desenharCircularBossOrtogonal(CircularBoss cb, Face faceDesenhada, int verticeAtivado, Point origem, int posicao, Graphics2D g2d){

		float dash1[] = {5.0f, 2.5f};		//determina o padrao da linha tracejada
		g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, 
				BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));
		float dash2[] = {15.0f, 2.5f, 1f, 2.5f};
		switch(posicao)
		{
			case 0:
				if (this.dFeaturesSecundarias)
				{
					posX  = (int)Math.round((cb.getPosicaoZ()) * zoom + origem.x);
					posY  = (int)Math.round(((faceDesenhada.getLarguraDesenhada(verticeAtivado) - cb.getPosicaoX() - (cb.getDiametro1()/2)) * zoom + origem.y));
					posY2 = (int)Math.round(((faceDesenhada.getLarguraDesenhada(verticeAtivado) - cb.getPosicaoX() - (cb.getDiametro2()/2)) * zoom + origem.y));
					comprimento = (int)Math.round((cb.getAltura()) * zoom);
					largura = (int)Math.round(cb.getDiametro1() * zoom);
					largura2 = (int)Math.round(cb.getDiametro2() * zoom);
					
					g2d.setColor(new Color(0, 0, 255));
					g2d.drawRect(posX, posY, comprimento, largura);
					
					g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, 
							BasicStroke.JOIN_MITER, 10.0f, dash2, 0.0f));
					
					p1X = (int)Math.round(( - 5 + cb.getPosicaoZ()) * zoom + origem.x);
					p2X = (int)Math.round((cb.getPosicaoZ() + cb.getAltura() + 5) * zoom + origem.x);
					p1Y = p2Y =(int)Math.round((faceDesenhada.getLarguraDesenhada(verticeAtivado) - cb.getPosicaoX()) * zoom + origem.y);
					
					g2d.setColor(Color.RED);
					g2d.drawLine(p1X, p1Y, p2X , p2Y);
				}
				break;
			case 1:
				if (this.dFeaturesSecundarias)
				{
					posX = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado) - cb.getPosicaoX() - (cb.getDiametro1()/2)) * zoom + origem.x);
					posX2 = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado) - cb.getPosicaoX() - (cb.getDiametro2()/2)) * zoom + origem.x);
					posY = (int)Math.round((faceDesenhada.getLarguraDesenhada(verticeAtivado) - cb.getAltura() - cb.getPosicaoZ()) * zoom + origem.y);
					comprimento = (int)Math.round(cb.getDiametro1() * zoom);
					comprimento2 = (int)Math.round(cb.getDiametro2() * zoom);

					largura = (int)Math.round(cb.getAltura() * zoom);
					g2d.setColor(new Color(0, 0, 255));
					g2d.drawRect(posX, posY, comprimento, largura);
					
					p1X = p2X = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado) - cb.getPosicaoX()) * zoom + origem.x) ;
					
					p1Y = (int)Math.round((faceDesenhada.getLarguraDesenhada(verticeAtivado) - cb.getPosicaoZ() + 5) * zoom + origem.y);
					p2Y = (int)Math.round((faceDesenhada.getLarguraDesenhada(verticeAtivado) - cb.getAltura() - cb.getPosicaoZ() - 5) * zoom + origem.y);
					
					g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, 
							BasicStroke.JOIN_MITER, 10.0f, dash2, 0.0f));
					
					g2d.setColor(Color.RED);
					g2d.drawLine(p1X, p1Y, p2X, p2Y);		
				}
				break;
			case 2:
				if (this.dFeaturesSecundarias)
				{
					posX  = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado) - cb.getAltura() - cb.getPosicaoZ()) * zoom + origem.x);
					posY  = (int)Math.round((cb.getPosicaoX() - (cb.getDiametro1()/2)) * zoom + origem.y);
					posY2 = (int)Math.round((cb.getPosicaoX() - (cb.getDiametro2()/2)) * zoom + origem.y);
					comprimento = (int)Math.round(cb.getAltura() * zoom);
					largura  = (int)Math.round(cb.getDiametro1() * zoom);
					largura2 = (int)Math.round(cb.getDiametro2() * zoom);
					
					g2d.setColor(new Color(0, 0, 255));
					g2d.drawRect(posX, posY, comprimento, largura);
					
					p1X = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado) - cb.getAltura() - cb.getPosicaoZ() - 5) * zoom + origem.x);
					p2X = (int)Math.round((faceDesenhada.getComprimentoDesenhado(verticeAtivado) - cb.getPosicaoZ() + 5) * zoom + origem.x);
					p1Y = p2Y = (int)Math.round((cb.getPosicaoX()) * zoom + origem.y);
					
					g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, 
							BasicStroke.JOIN_MITER, 10.0f, dash2, 0.0f));
					
					g2d.setColor(Color.RED);
					g2d.drawLine(p1X, p1Y, p2X, p2Y);
				}
				
				break;
			case 3:
				if (this.dFeaturesSecundarias)
				{
					posX = (int)Math.round((cb.getPosicaoX() - (cb.getDiametro1()/2)) * zoom + origem.x);
					posX2 = (int)Math.round((cb.getPosicaoX() - (cb.getDiametro2()/2)) * zoom + origem.x);
					posY = (int)Math.round((cb.getPosicaoZ()) * zoom + origem.y);
					comprimento = (int)Math.round(cb.getDiametro1() * zoom);
					comprimento = (int)Math.round(cb.getDiametro2() * zoom);
					largura = (int)Math.round(cb.getAltura() * zoom);
		
					g2d.setColor(new Color(0, 0, 255));
					g2d.drawRect(posX, posY, comprimento, largura);
					
					p1X = p2X = (int)Math.round((cb.getPosicaoX()) * zoom + origem.x);
					p1Y = (int)Math.round((cb.getPosicaoZ() - 5) * zoom + origem.y);
					p2Y = (int)Math.round((cb.getPosicaoZ() + cb.getAltura() + 5) * zoom + origem.y);
					
					g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, 
							BasicStroke.JOIN_MITER, 10.0f, dash2, 0.0f));
					
					g2d.setColor(Color.RED);
					g2d.drawLine(p1X, p1Y, p2X, p2Y);
				}
				
				break;
			default:
				break;
		}
	
	}

}