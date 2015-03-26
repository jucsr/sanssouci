package br.UFSC.GRIMA.util;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.vecmath.Point3d;

import org.junit.Before;
import org.junit.Test;

import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraGeneralClosedPocket1;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.util.entidadesAdd.GeneralClosedPocketVertexAdd;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;
import br.UFSC.GRIMA.util.findPoints.LimitedLine;

public class TriangulationTest 
{
	ArrayList<LimitedElement> elements = new ArrayList<LimitedElement>();
	private GeneralClosedPocket pocket = new GeneralClosedPocket();
	GeneralClosedPocketVertexAdd addPocketVertex;
	ArrayList<Point2D> points = new ArrayList<Point2D>();
	@Before
	public void init()
	{

//		elements.add(new LimitedLine(new Point3d(10,10,0),new Point3d(50,10,0)));
//		elements.add(new LimitedLine(new Point3d(50,10,0),new Point3d(50,50,0)));
//		elements.add(new LimitedLine(new Point3d(50,50,0),new Point3d(10,50,0)));
//		elements.add(new LimitedLine(new Point3d(10,50,0),new Point3d(10,10,0)));
		elements.add(new LimitedLine(new Point3d(10,10,0),new Point3d(20,10,0)));
		elements.add(new LimitedLine(new Point3d(20,10,0),new Point3d(20,20,0)));
		elements.add(new LimitedLine(new Point3d(20,20,0),new Point3d(10,20,0)));
		elements.add(new LimitedLine(new Point3d(10,20,0),new Point3d(10,10,0)));
		
		//Forma de Cachorrinho --> raio 5
//		points.add(new Point2D.Double(44.0,89.0));
//		points.add(new Point2D.Double(51.0,68.0));
//		points.add(new Point2D.Double(27.0,22.0));
//		points.add(new Point2D.Double(55.0,20.0));
//		points.add(new Point2D.Double(67.0,50.0));
//		points.add(new Point2D.Double(124.0,65.0));
//		points.add(new Point2D.Double(136.0,20.0));
//		points.add(new Point2D.Double(164.0,19.0));
//		points.add(new Point2D.Double(147.0,66.0));
//		points.add(new Point2D.Double(168.0,116.0));
//		points.add(new Point2D.Double(134.0,84.0));
//		points.add(new Point2D.Double(68.0,84.0));
//		points.add(new Point2D.Double(45.0,120.0));
//		points.add(new Point2D.Double(13.0,93.0));
		
		//Forma 2
//		points.add(new Point2D.Double(500, 320));
//		points.add(new Point2D.Double(500, 160));
//		points.add(new Point2D.Double(280, 160));
//		points.add(new Point2D.Double(280, 40));
//		points.add(new Point2D.Double(0, 40));
//		points.add(new Point2D.Double(0, 320));
		
		//Forma 3
		points.add(new Point2D.Double(10, 10));
		points.add(new Point2D.Double(10, 0));
		points.add(new Point2D.Double(0, 0));
		points.add(new Point2D.Double(0, 10));
		
		//Forma 4 (raio 0)
		points.add(new Point2D.Double( 92.678358520567, 36.53602381912822));
		points.add(new Point2D.Double( 75.10660005017213, 33.46523107672911));
		points.add(new Point2D.Double( 72.36193194989104, 33.17884608116982));
		points.add(new Point2D.Double( 69.60396960640882, 33.27298989836504));
		points.add(new Point2D.Double( 66.88521924021393, 33.74587021376959));
		points.add(new Point2D.Double( 64.25744055227464, 34.5884843090672));
		points.add(new Point2D.Double( 61.770661320348694, 35.784790456357186));
		points.add(new Point2D.Double( 59.47222496773052, 37.31201332134623));
		points.add(new Point2D.Double( 57.40588923686881, 39.14107756126407));
		points.add(new Point2D.Double( 55.61099312734932, 41.23716136264555));
		points.add(new Point2D.Double( 54.1217079581181, 43.560359380699545));
		points.add(new Point2D.Double( 54.12170795817213, 43.56035938072914));
		points.add(new Point2D.Double( 42.754866523391996, 64.31720026163198));
		points.add(new Point2D.Double( 41.86682844794845, 66.15583876880764));
		points.add(new Point2D.Double( 41.17088602803825, 68.0754395949369));
		points.add(new Point2D.Double( 40.674293077043245, 70.05599472533058));
		points.add(new Point2D.Double( 40.38222558724415, 72.07686081810095));
		points.add(new Point2D.Double( 40.29772778041173, 74.11697436987868));
		points.add(new Point2D.Double( 40.421680377859154, 76.15507126087593));
		points.add(new Point2D.Double( 40.75279142067666, 78.16990839097625));
		points.add(new Point2D.Double( 41.28760973582959, 80.14048509673617));
		points.add(new Point2D.Double( 42.02056090776239, 82.04626204146736));
		points.add(new Point2D.Double( 42.020560907391996, 82.04626204163199));
		points.add(new Point2D.Double( 45.2141218734992, 89.23177421537318));
		points.add(new Point2D.Double( 48.12112011147508, 93.90692211328509));
		points.add(new Point2D.Double( 52.19263153587154, 97.61238312967077));
		points.add(new Point2D.Double( 57.12016132300909, 100.06739775688119));
		points.add(new Point2D.Double( 62.53035489410654, 101.08595170547733));
		points.add(new Point2D.Double( 68.01328666187783, 100.59087004260812));
		points.add(new Point2D.Double( 73.15351973013497, 98.61966466849287));
		points.add(new Point2D.Double( 77.56158318039746, 95.32169207288132));
		points.add(new Point2D.Double( 80.90348193931041, 90.94683672577314));
		points.add(new Point2D.Double( 82.92600329028701, 85.8265775518847));
		points.add(new Point2D.Double( 82.92600329027933, 85.82657755188283));
		points.add(new Point2D.Double( 83.4258141830781, 84.2156660817287));
		points.add(new Point2D.Double( 84.12325839599505, 82.67995182995423));
		points.add(new Point2D.Double( 85.00737392978797, 81.24357220877447));
		points.add(new Point2D.Double( 86.06426480018756, 79.92910334976696));
		points.add(new Point2D.Double( 87.27731944643267, 78.75720526611904));
		points.add(new Point2D.Double( 88.62747182156706, 77.7462971312455));
		points.add(new Point2D.Double( 90.09350106084062, 76.91226777754777));
		points.add(new Point2D.Double( 91.65236501821177, 76.26822596551713));
		points.add(new Point2D.Double( 93.27956242862894, 75.8242943482987));
		points.add(new Point2D.Double( 93.27956242862894, 75.8242943482987));
		points.add(new Point2D.Double( 99.0134453899127, 73.68421126412665));
		points.add(new Point2D.Double( 103.83167793591547, 69.91035321784307));
		points.add(new Point2D.Double( 107.28306427555914, 64.85611719690175));
		points.add(new Point2D.Double( 109.04440477599836, 58.99479916574423));
		points.add(new Point2D.Double( 108.95076148273094, 52.875273011336056));
		points.add(new Point2D.Double( 107.01090347433116, 47.07059211329292));
		points.add(new Point2D.Double( 103.4064856959106, 42.12432666353535));
		points.add(new Point2D.Double( 98.47503816815282, 38.49966189257634));
		points.add(new Point2D.Double( 92.67835852062481, 36.536023818797375));
		
		
		pocket.setPoints(points);
		pocket.setRadius(0);
		//pocket.setPosicao(50, 50, 0);
		pocket.setProfundidade(15);
		addPocketVertex = new GeneralClosedPocketVertexAdd(pocket.getPoints(), pocket.Z, pocket.getRadius());
	}
	
//	@Test
//	public void getTriangules()
//	{
//		Triangulation triangulation = new Triangulation(addPocketVertex.getElements());
//		//System.out.println(triangulation.getTriangules());
//		System.out.println("Area: " + triangulation.getArea(triangulation.getTriangules()));
////		MapeadoraGeneralClosedPocket1.drawShape(elements, null);
////		for(;;);
//	}
	@Test
	public void getTriangules()
	{
//		Triangulation triangulation = new Triangulation(points);
//		System.out.println(triangulation.getTriangulesIndex());
//		System.out.println(triangulation.getArea());
		MapeadoraGeneralClosedPocket1.drawShape(addPocketVertex.getElements(), null,0);
		for(;;);
	}
}
