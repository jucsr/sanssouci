package br.UFSC.GRIMA.cad;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.bReps.BezierSurface;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.PropertyParameter;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.CavidadeFundoArredondado;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Furo;
import br.UFSC.GRIMA.entidades.features.FuroBasePlana;
import br.UFSC.GRIMA.entidades.features.Region;
import br.UFSC.GRIMA.operationSolids.CSGSolid;
import br.UFSC.GRIMA.util.projeto.DadosDeProjeto;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class CreateBezierSurfaceTest
{
	Projeto projeto;
	Bloco bloco;
	Face faceXY;
	Region region;
	Point3d [][] controlVertex = new Point3d[4][4];
	
	Point3d p00 = new Point3d(0, 0, -20);
	Point3d p01 = new Point3d(30, 0, -30);
	Point3d p02 = new Point3d(60, 0, -30);
	Point3d p03 = new Point3d(90, 0, -30);
	Point3d p10 = new Point3d(0, 3, -30);
	Point3d p11 = new Point3d(30, 30, -30);
	Point3d p12 = new Point3d(60, 30, 0);
	Point3d p13 = new Point3d(90, 30, -30);
	Point3d p20 = new Point3d(0, 60, -30);
	Point3d p21 = new Point3d(30, 60, -90);
	Point3d p22 = new Point3d(60, 60, -30);
	Point3d p23 = new Point3d(90, 60, -30);
	Point3d p30 = new Point3d(0, 90, -40);
	Point3d p31 = new Point3d(30, 90, -30);
	Point3d p32 = new Point3d(60, 90, -30);
	Point3d p33 = new Point3d(90, 90, -60);
	
	@Before
	public void init()
	{
		controlVertex[0][0] = p00;
		controlVertex[0][1] = p01;
		controlVertex[0][2] = p02;
		controlVertex[0][3] = p03;
		controlVertex[1][0] = p10;
		controlVertex[1][1] = p11;
		controlVertex[1][2] = p12;
		controlVertex[1][3] = p13;
		controlVertex[2][0] = p20;
		controlVertex[2][1] = p21;
		controlVertex[2][2] = p22;
		controlVertex[2][3] = p23;
		controlVertex[3][0] = p30;
		controlVertex[3][1] = p31;
		controlVertex[3][2] = p32;
		controlVertex[3][3] = p33;
		
		ArrayList<PropertyParameter> props = new ArrayList<PropertyParameter>();
		
		PropertyParameter properties = new PropertyParameter("Hardness","HB",250);
		
		props.add(properties);
		
		Material material = new Material("Aço", Material.ACO_ALTO_CARBONO, props);
		
		bloco = new Bloco(90, 90.0, 100.0, material);
		faceXY = (Face) bloco.faces.get(Face.XY);
		DadosDeProjeto dados = new DadosDeProjeto(123, "fulano da silva", "novo projeto", material);
		
		projeto = new Projeto(bloco, dados);
	}
	@Test
	public void createRegionTest()
	{
		region = new Region(7);
		region.setItsId("REGION");
		region.setControlVertex(controlVertex);
		region.setSplitU(13);
		region.setSplitV(13);
		region.setSurface(new BezierSurface(controlVertex, 7, 7));
		for(int i = 0; i < region.getSurface().getMeshArray().length; i++)
		{
			for (int j = 0; j < region.getSurface().getMeshArray()[i].length; j++)
			{
				System.out.println(region.getSurface().getMeshArray()[i][j]);
			}
		}
		
		CavidadeFundoArredondado c = new CavidadeFundoArredondado(10, 10, 10, 10, 10, 70, 70, 70);
		FuroBasePlana furo = new FuroBasePlana("", 7, 5, 20, 3, 80);
		Furo furo1 = new FuroBasePlana("", 7, 83, 20, 3, 80);
		Furo furo2 = new FuroBasePlana("", 83, 83, 20, 3, 80);
		Furo furo3 = new FuroBasePlana("", 83, 7, 20, 3, 80);
		faceXY.addFeature(region);
		faceXY.addFeature(c);
		faceXY.addFeature(furo);
		faceXY.addFeature(furo1);
		faceXY.addFeature(furo2);
		faceXY.addFeature(furo3);
		Generate3Dview g = new Generate3Dview(projeto);
		g.setVisible(true);
		for(;;);
	}
}
