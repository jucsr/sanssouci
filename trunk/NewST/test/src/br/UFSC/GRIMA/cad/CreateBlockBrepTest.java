package br.UFSC.GRIMA.cad;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.capp.DeterminateShortestDistance;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.PropertyParameter;
import br.UFSC.GRIMA.entidades.features.Bloco;
import br.UFSC.GRIMA.entidades.features.Boss;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.CircularBoss;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.util.projeto.DadosDeProjeto;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class CreateBlockBrepTest 
{
	Projeto projeto;
	Bloco bloco;
	Cavidade cavidade;
	CircularBoss boss;
	Face faceXY;
	@Before
	public void createProject()
	{
		ArrayList<PropertyParameter> props = new ArrayList<PropertyParameter>();
		
		PropertyParameter properties = new PropertyParameter("Hardness","HB",250);
		
		props.add(properties);
		
		Material material = new Material("Aço", Material.ACO_ALTO_CARBONO, props);
		
		bloco = new Bloco(200.0, 150.0, 40.0, material);
		faceXY = (Face) bloco.faces.get(Face.XY);
		DadosDeProjeto dados = new DadosDeProjeto(123, "fulano da silva", "novo projeto", material);
		
		projeto = new Projeto(bloco, dados);
	}
	@Test
	public void createBlockBrepTest()
	{
		
		Generate3Dview Janela3D = new Generate3Dview(this.projeto);
		Janela3D.setVisible(true);

		DeterminateShortestDistance dsd = new DeterminateShortestDistance(this.cavidade);
		System.out.println("shortest distance = " + dsd.getShortestDistance());
		while(true);
	}
}
