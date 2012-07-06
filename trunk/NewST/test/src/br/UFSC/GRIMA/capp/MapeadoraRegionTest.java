package br.UFSC.GRIMA.capp;

import java.util.ArrayList;
import java.util.Vector;

import javax.vecmath.Point3d;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraFuroBaseArredondada;
import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraRegion;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.PropertyParameter;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.FuroBaseArredondada;
import br.UFSC.GRIMA.entidades.features.Region;
import br.UFSC.GRIMA.util.projeto.Axis2Placement3D;
import br.UFSC.GRIMA.util.projeto.DadosDeProjeto;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class MapeadoraRegionTest 
{
	private Projeto projeto;
	private Bloco bloco;
	private Region region;
	private Face faceXY;
	
	@Before
	public void createProject(){
		

		ArrayList<PropertyParameter> props = new ArrayList<PropertyParameter>();
		
		PropertyParameter properties = new PropertyParameter("Hardness","HB",250);
		
		props.add(properties);
		
		Material material = new Material("Aço", Material.ACO_ALTO_CARBONO, props);
		
		bloco = new Bloco(200.0, 150.0, 40.0, material);
		
		DadosDeProjeto dados = new DadosDeProjeto(123, "fulano da silva", "novo projeto", material);
		
		projeto = new Projeto(bloco, dados);
		
		new ToolManager(projeto);
		
		//feature definition
		region = new Region(Feature.REGION);
		region.setPosicao(0, 0, 0);
		region.setNome("Region");
		region.setTolerancia(0.01);
		region.setRugosidade(0.01);
		Point3d coordinates = new Point3d(region.X, region.Y, region.Z);
		ArrayList<Double> axis = new ArrayList<Double>(), refDirection = new ArrayList<Double>();
		axis.add(0.0);
		axis.add(0.0);
		axis.add(1.0);		
		refDirection.add(1.0);
		refDirection.add(0.0);
		refDirection.add(0.0);
		Axis2Placement3D position = new Axis2Placement3D(coordinates, axis, refDirection);
		position.setName("region placement");
		region.setPosition(position);
		
		
		faceXY = (Face)(bloco.faces.elementAt(Face.XY));
		faceXY.features.addElement(region);
	}
	
	@Test
	public void mapearFuroBaseArredondada(){
		
		MapeadoraRegion mapeadoraRegion = new MapeadoraRegion(projeto, faceXY ,region);
		
		Vector<Workingstep> wssRegion = region.getWorkingsteps();
		
		Assert.assertNotNull(wssRegion);
		
		System.out.println("Size = " + wssRegion.size());
		System.out.println();

		for(int i = 0; i<wssRegion.size();i++){
			System.out.println(wssRegion.get(i).getOperation());
			System.out.println(wssRegion.get(i).getOperation().getStartPoint());
			System.out.println(wssRegion.get(i).getFerramenta().getClass());
			System.out.println(wssRegion.get(i).getFerramenta().getDiametroFerramenta());
			System.out.println();
		}
		
	}
}
