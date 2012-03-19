package br.UFSC.GRIMA.simulator;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;
import br.UFSC.GRIMA.capp.DeterminarMovimentacao;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.capp.machiningOperations.CenterDrilling;
import br.UFSC.GRIMA.capp.machiningOperations.Drilling;
import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraDeWorkingsteps;
import br.UFSC.GRIMA.entidades.Rectangle3D;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.Degrau;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.FuroBaseConica;
import br.UFSC.GRIMA.entidades.features.FuroBasePlana;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.ferramentas.CenterDrill;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.entidades.ferramentas.TwistDrill;
import br.UFSC.GRIMA.util.Ponto;

public class SimulationApplet extends JApplet {
	JFrame win;
//	FuroBasePlana furo;
	FuroBaseConica furo;
	Ranhura ranhura;
	Degrau degrau;
	Cavidade cavidade;
	Ferramenta ferramenta;

	public void init() {
		try {
			
			boolean fazCav1 = false;
			boolean fazCav2 = false;
			boolean fazRanhura = false;
			boolean fazDegrau = true;
			boolean fazFuro = false;
			
			Vector<Feature> features = new Vector<Feature>();
			Vector<Workingstep> workingsteps = new Vector<Workingstep>();

			// ;;;;;;;;;;;;;;;;;;;;;;;;;
			Vector list = new Vector();
			Point3d point3d;

			point3d = new Point3d(60.0, 100.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(140.0, 100.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(141.59099025766974, 101.59099025766973, 3.0);
			list.add(point3d);
			point3d = new Point3d(58.40900974233027, 101.59099025766973, 3.0);
			list.add(point3d);
			point3d = new Point3d(58.40900974233027, 98.40900974233027, 3.0);
			list.add(point3d);
			point3d = new Point3d(141.59099025766974, 98.40900974233027, 3.0);
			list.add(point3d);
			point3d = new Point3d(141.59099025766974, 101.59099025766973, 3.0);
			list.add(point3d);
			point3d = new Point3d(143.18198051533946, 103.18198051533946, 3.0);
			list.add(point3d);
			point3d = new Point3d(56.81801948466054, 103.18198051533946, 3.0);
			list.add(point3d);
			point3d = new Point3d(56.81801948466054, 96.81801948466054, 3.0);
			list.add(point3d);
			point3d = new Point3d(143.18198051533946, 96.81801948466054, 3.0);
			list.add(point3d);
			point3d = new Point3d(143.18198051533946, 103.18198051533946, 3.0);
			list.add(point3d);
			point3d = new Point3d(144.7729707730092, 104.77297077300919, 3.0);
			list.add(point3d);
			point3d = new Point3d(55.22702922699081, 104.77297077300919, 3.0);
			list.add(point3d);
			point3d = new Point3d(55.22702922699081, 95.22702922699081, 3.0);
			list.add(point3d);
			point3d = new Point3d(144.7729707730092, 95.22702922699081, 3.0);
			list.add(point3d);
			point3d = new Point3d(144.7729707730092, 104.77297077300919, 3.0);
			list.add(point3d);
			point3d = new Point3d(146.36396103067892, 106.36396103067892, 3.0);
			list.add(point3d);
			point3d = new Point3d(53.63603896932107, 106.36396103067892, 3.0);
			list.add(point3d);
			point3d = new Point3d(53.63603896932107, 93.63603896932108, 3.0);
			list.add(point3d);
			point3d = new Point3d(146.36396103067892, 93.63603896932108, 3.0);
			list.add(point3d);
			point3d = new Point3d(146.36396103067892, 106.36396103067892, 3.0);
			list.add(point3d);
			point3d = new Point3d(147.95495128834867, 107.95495128834865, 3.0);
			list.add(point3d);
			point3d = new Point3d(52.04504871165134, 107.95495128834865, 3.0);
			list.add(point3d);
			point3d = new Point3d(52.04504871165134, 92.04504871165135, 3.0);
			list.add(point3d);
			point3d = new Point3d(147.95495128834867, 92.04504871165135, 3.0);
			list.add(point3d);
			point3d = new Point3d(147.95495128834867, 107.95495128834865, 3.0);
			list.add(point3d);
			point3d = new Point3d(149.54594154601838, 109.54594154601838, 3.0);
			list.add(point3d);
			point3d = new Point3d(50.45405845398161, 109.54594154601838, 3.0);
			list.add(point3d);
			point3d = new Point3d(50.45405845398161, 90.45405845398162, 3.0);
			list.add(point3d);
			point3d = new Point3d(149.54594154601838, 90.45405845398162, 3.0);
			list.add(point3d);
			point3d = new Point3d(149.54594154601838, 109.54594154601838, 3.0);
			list.add(point3d);
			point3d = new Point3d(151.13693180368813, 111.13693180368813, 3.0);
			list.add(point3d);
			point3d = new Point3d(48.86306819631188, 111.13693180368813, 3.0);
			list.add(point3d);
			point3d = new Point3d(48.86306819631188, 88.86306819631187, 3.0);
			list.add(point3d);
			point3d = new Point3d(151.13693180368813, 88.86306819631187, 3.0);
			list.add(point3d);
			point3d = new Point3d(151.13693180368813, 111.13693180368813, 3.0);
			list.add(point3d);
			point3d = new Point3d(152.72792206135784, 112.72792206135786, 3.0);
			list.add(point3d);
			point3d = new Point3d(47.27207793864214, 112.72792206135786, 3.0);
			list.add(point3d);
			point3d = new Point3d(47.27207793864214, 87.27207793864214, 3.0);
			list.add(point3d);
			point3d = new Point3d(152.72792206135784, 87.27207793864214, 3.0);
			list.add(point3d);
			point3d = new Point3d(152.72792206135784, 112.72792206135786, 3.0);
			list.add(point3d);
			point3d = new Point3d(154.3189123190276, 114.31891231902759, 3.0);
			list.add(point3d);
			point3d = new Point3d(45.68108768097241, 114.31891231902759, 3.0);
			list.add(point3d);
			point3d = new Point3d(45.68108768097241, 85.68108768097241, 3.0);
			list.add(point3d);
			point3d = new Point3d(154.3189123190276, 85.68108768097241, 3.0);
			list.add(point3d);
			point3d = new Point3d(154.3189123190276, 114.31891231902759, 3.0);
			list.add(point3d);
			point3d = new Point3d(155.9099025766973, 115.90990257669732, 3.0);
			list.add(point3d);
			point3d = new Point3d(44.09009742330268, 115.90990257669732, 3.0);
			list.add(point3d);
			point3d = new Point3d(44.09009742330268, 84.09009742330268, 3.0);
			list.add(point3d);
			point3d = new Point3d(155.9099025766973, 84.09009742330268, 3.0);
			list.add(point3d);
			point3d = new Point3d(155.9099025766973, 115.90990257669732, 3.0);
			list.add(point3d);
			point3d = new Point3d(157.50089283436705, 117.50089283436705, 3.0);
			list.add(point3d);
			point3d = new Point3d(42.49910716563295, 117.50089283436705, 3.0);
			list.add(point3d);
			point3d = new Point3d(42.49910716563295, 82.49910716563295, 3.0);
			list.add(point3d);
			point3d = new Point3d(157.50089283436705, 82.49910716563295, 3.0);
			list.add(point3d);
			point3d = new Point3d(157.50089283436705, 117.50089283436705, 3.0);
			list.add(point3d);
			point3d = new Point3d(159.09188309203677, 119.09188309203678, 3.0);
			list.add(point3d);
			point3d = new Point3d(40.90811690796322, 119.09188309203678, 3.0);
			list.add(point3d);
			point3d = new Point3d(40.90811690796322, 80.90811690796322, 3.0);
			list.add(point3d);
			point3d = new Point3d(159.09188309203677, 80.90811690796322, 3.0);
			list.add(point3d);
			point3d = new Point3d(159.09188309203677, 119.09188309203678, 3.0);
			list.add(point3d);
			point3d = new Point3d(160.6828733497065, 120.68287334970651, 3.0);
			list.add(point3d);
			point3d = new Point3d(39.31712665029349, 120.68287334970651, 3.0);
			list.add(point3d);
			point3d = new Point3d(39.31712665029349, 79.31712665029349, 3.0);
			list.add(point3d);
			point3d = new Point3d(160.6828733497065, 79.31712665029349, 3.0);
			list.add(point3d);
			point3d = new Point3d(160.6828733497065, 120.68287334970651, 3.0);
			list.add(point3d);
			point3d = new Point3d(162.27386360737626, 122.27386360737624, 3.0);
			list.add(point3d);
			point3d = new Point3d(37.72613639262376, 122.27386360737624, 3.0);
			list.add(point3d);
			point3d = new Point3d(37.72613639262376, 77.72613639262376, 3.0);
			list.add(point3d);
			point3d = new Point3d(162.27386360737626, 77.72613639262376, 3.0);
			list.add(point3d);
			point3d = new Point3d(162.27386360737626, 122.27386360737624, 3.0);
			list.add(point3d);
			point3d = new Point3d(163.86485386504597, 123.86485386504597, 3.0);
			list.add(point3d);
			point3d = new Point3d(36.13514613495403, 123.86485386504597, 3.0);
			list.add(point3d);
			point3d = new Point3d(36.13514613495403, 76.13514613495403, 3.0);
			list.add(point3d);
			point3d = new Point3d(163.86485386504597, 76.13514613495403, 3.0);
			list.add(point3d);
			point3d = new Point3d(163.86485386504597, 123.86485386504597, 3.0);
			list.add(point3d);
			point3d = new Point3d(165.45584412271572, 125.45584412271572, 3.0);
			list.add(point3d);
			point3d = new Point3d(34.54415587728429, 125.45584412271572, 3.0);
			list.add(point3d);
			point3d = new Point3d(34.54415587728429, 74.54415587728428, 3.0);
			list.add(point3d);
			point3d = new Point3d(165.45584412271572, 74.54415587728428, 3.0);
			list.add(point3d);
			point3d = new Point3d(165.45584412271572, 125.45584412271572, 3.0);
			list.add(point3d);
			point3d = new Point3d(167.04683438038543, 127.04683438038543, 3.0);
			list.add(point3d);
			point3d = new Point3d(32.95316561961456, 127.04683438038543, 3.0);
			list.add(point3d);
			point3d = new Point3d(32.95316561961456, 72.95316561961457, 3.0);
			list.add(point3d);
			point3d = new Point3d(167.04683438038543, 72.95316561961457, 3.0);
			list.add(point3d);
			point3d = new Point3d(167.04683438038543, 127.04683438038543, 3.0);
			list.add(point3d);
			point3d = new Point3d(168.63782463805518, 128.63782463805518, 3.0);
			list.add(point3d);
			point3d = new Point3d(31.36217536194483, 128.63782463805518, 3.0);
			list.add(point3d);
			point3d = new Point3d(31.36217536194483, 71.36217536194482, 3.0);
			list.add(point3d);
			point3d = new Point3d(168.63782463805518, 71.36217536194482, 3.0);
			list.add(point3d);
			point3d = new Point3d(168.63782463805518, 128.63782463805518, 3.0);
			list.add(point3d);
			point3d = new Point3d(170.0, 130.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(30.0, 130.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(30.0, 70.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(170.0, 70.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(170.0, 130.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(171.59099025766974, 131.59099025766972, 3.0);
			list.add(point3d);
			point3d = new Point3d(170.0, 132.25, 3.0);
			list.add(point3d);
			point3d = new Point3d(30.0, 132.25, 3.0);
			list.add(point3d);
			point3d = new Point3d(27.75, 130.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(27.75, 70.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(30.0, 67.75, 3.0);
			list.add(point3d);
			point3d = new Point3d(170.0, 67.75, 3.0);
			list.add(point3d);
			point3d = new Point3d(172.25, 70.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(172.25, 130.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(171.59099025766974, 131.59099025766972, 3.0);
			list.add(point3d);
			point3d = new Point3d(173.18198051533946, 133.18198051533946, 3.0);
			list.add(point3d);
			point3d = new Point3d(170.0, 134.5, 3.0);
			list.add(point3d);
			point3d = new Point3d(30.0, 134.5, 3.0);
			list.add(point3d);
			point3d = new Point3d(27.75, 133.89711431702997, 3.0);
			list.add(point3d);
			point3d = new Point3d(26.102885682970026, 132.25, 3.0);
			list.add(point3d);
			point3d = new Point3d(25.5, 130.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(25.5, 70.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(26.102885682970026, 67.75, 3.0);
			list.add(point3d);
			point3d = new Point3d(27.75, 66.10288568297003, 3.0);
			list.add(point3d);
			point3d = new Point3d(30.0, 65.5, 3.0);
			list.add(point3d);
			point3d = new Point3d(170.0, 65.5, 3.0);
			list.add(point3d);
			point3d = new Point3d(172.25, 66.10288568297003, 3.0);
			list.add(point3d);
			point3d = new Point3d(173.89711431702997, 67.75, 3.0);
			list.add(point3d);
			point3d = new Point3d(174.5, 70.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(174.5, 130.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(173.18198051533946, 133.18198051533946, 3.0);
			list.add(point3d);
			point3d = new Point3d(174.7729707730092, 134.7729707730092, 3.0);
			list.add(point3d);
			point3d = new Point3d(172.58311316846437, 136.2361868444512, 3.0);
			list.add(point3d);
			point3d = new Point3d(170.0, 136.75, 3.0);
			list.add(point3d);
			point3d = new Point3d(30.0, 136.75, 3.0);
			list.add(point3d);
			point3d = new Point3d(27.416886831535642, 136.2361868444512, 3.0);
			list.add(point3d);
			point3d = new Point3d(25.227029226990805, 134.7729707730092, 3.0);
			list.add(point3d);
			point3d = new Point3d(23.763813155548814, 132.58311316846437, 3.0);
			list.add(point3d);
			point3d = new Point3d(23.25, 130.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(23.25, 70.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(23.763813155548814, 67.41688683153563, 3.0);
			list.add(point3d);
			point3d = new Point3d(25.2270292269908, 65.22702922699081, 3.0);
			list.add(point3d);
			point3d = new Point3d(27.416886831535642, 63.76381315554882, 3.0);
			list.add(point3d);
			point3d = new Point3d(30.0, 63.25, 3.0);
			list.add(point3d);
			point3d = new Point3d(170.0, 63.25, 3.0);
			list.add(point3d);
			point3d = new Point3d(172.58311316846437, 63.76381315554882, 3.0);
			list.add(point3d);
			point3d = new Point3d(174.7729707730092, 65.2270292269908, 3.0);
			list.add(point3d);
			point3d = new Point3d(176.2361868444512, 67.41688683153563, 3.0);
			list.add(point3d);
			point3d = new Point3d(176.75, 70.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(176.75, 130.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(176.2361868444512, 132.58311316846437, 3.0);
			list.add(point3d);
			point3d = new Point3d(174.7729707730092, 134.7729707730092, 3.0);
			list.add(point3d);
			point3d = new Point3d(176.36396103067892, 136.36396103067892, 3.0);
			list.add(point3d);
			point3d = new Point3d(174.5, 137.79422863405995, 3.0);
			list.add(point3d);
			point3d = new Point3d(172.3293714059227, 138.69333243660162, 3.0);
			list.add(point3d);
			point3d = new Point3d(170.0, 139.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(30.0, 139.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(27.670628594077314, 138.69333243660162, 3.0);
			list.add(point3d);
			point3d = new Point3d(25.5, 137.79422863405995, 3.0);
			list.add(point3d);
			point3d = new Point3d(23.63603896932107, 136.36396103067892, 3.0);
			list.add(point3d);
			point3d = new Point3d(22.205771365940052, 134.5, 3.0);
			list.add(point3d);
			point3d = new Point3d(21.306667563398385, 132.3293714059227, 3.0);
			list.add(point3d);
			point3d = new Point3d(21.0, 130.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(21.0, 70.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(21.306667563398385, 67.67062859407731, 3.0);
			list.add(point3d);
			point3d = new Point3d(22.205771365940052, 65.5, 3.0);
			list.add(point3d);
			point3d = new Point3d(23.63603896932107, 63.63603896932107, 3.0);
			list.add(point3d);
			point3d = new Point3d(25.499999999999996, 62.20577136594005, 3.0);
			list.add(point3d);
			point3d = new Point3d(27.670628594077314, 61.306667563398385, 3.0);
			list.add(point3d);
			point3d = new Point3d(30.0, 61.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(170.0, 61.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(172.32937140592267, 61.306667563398385, 3.0);
			list.add(point3d);
			point3d = new Point3d(174.5, 62.20577136594005, 3.0);
			list.add(point3d);
			point3d = new Point3d(176.36396103067892, 63.63603896932107, 3.0);
			list.add(point3d);
			point3d = new Point3d(177.79422863405995, 65.5, 3.0);
			list.add(point3d);
			point3d = new Point3d(178.69333243660162, 67.67062859407731, 3.0);
			list.add(point3d);
			point3d = new Point3d(179.0, 70.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(179.0, 130.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(178.69333243660162, 132.3293714059227, 3.0);
			list.add(point3d);
			point3d = new Point3d(177.79422863405995, 134.5, 3.0);
			list.add(point3d);
			point3d = new Point3d(176.36396103067892, 136.36396103067892, 3.0);
			list.add(point3d);
			point3d = new Point3d(177.95495128834867, 137.95495128834867, 3.0);
			list.add(point3d);
			point3d = new Point3d(176.25016512147053, 139.35403313840362, 3.0);
			list.add(point3d);
			point3d = new Point3d(174.30518861410727, 140.39364474075197, 3.0);
			list.add(point3d);
			point3d = new Point3d(172.19476612268144, 141.03383440453635, 3.0);
			list.add(point3d);
			point3d = new Point3d(170.0, 141.25, 3.0);
			list.add(point3d);
			point3d = new Point3d(30.0, 141.25, 3.0);
			list.add(point3d);
			point3d = new Point3d(27.496639492991463, 140.96793901204552, 3.0);
			list.add(point3d);
			point3d = new Point3d(25.11880793492747, 140.13589976390222, 3.0);
			list.add(point3d);
			point3d = new Point3d(22.985739729089246, 138.79560417776534, 3.0);
			list.add(point3d);
			point3d = new Point3d(21.204395822234666, 137.01426027091077, 3.0);
			list.add(point3d);
			point3d = new Point3d(19.864100236097784, 134.88119206507253, 3.0);
			list.add(point3d);
			point3d = new Point3d(19.032060987954484, 132.50336050700855, 3.0);
			list.add(point3d);
			point3d = new Point3d(18.75, 130.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(18.75, 70.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(19.032060987954484, 67.49663949299146, 3.0);
			list.add(point3d);
			point3d = new Point3d(19.864100236097784, 65.11880793492747, 3.0);
			list.add(point3d);
			point3d = new Point3d(21.204395822234666, 62.98573972908925, 3.0);
			list.add(point3d);
			point3d = new Point3d(22.985739729089246, 61.204395822234666, 3.0);
			list.add(point3d);
			point3d = new Point3d(25.118807934927467, 59.864100236097784, 3.0);
			list.add(point3d);
			point3d = new Point3d(27.496639492991463, 59.03206098795448, 3.0);
			list.add(point3d);
			point3d = new Point3d(30.0, 58.75, 3.0);
			list.add(point3d);
			point3d = new Point3d(170.0, 58.75, 3.0);
			list.add(point3d);
			point3d = new Point3d(172.50336050700852, 59.03206098795448, 3.0);
			list.add(point3d);
			point3d = new Point3d(174.88119206507253, 59.864100236097784, 3.0);
			list.add(point3d);
			point3d = new Point3d(177.01426027091074, 61.204395822234666, 3.0);
			list.add(point3d);
			point3d = new Point3d(178.79560417776534, 62.98573972908925, 3.0);
			list.add(point3d);
			point3d = new Point3d(180.13589976390222, 65.11880793492747, 3.0);
			list.add(point3d);
			point3d = new Point3d(180.96793901204552, 67.49663949299146, 3.0);
			list.add(point3d);
			point3d = new Point3d(181.25, 70.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(181.25, 130.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(181.03383440453635, 132.19476612268144, 3.0);
			list.add(point3d);
			point3d = new Point3d(180.39364474075197, 134.30518861410727, 3.0);
			list.add(point3d);
			point3d = new Point3d(179.35403313840362, 136.25016512147053, 3.0);
			list.add(point3d);
			point3d = new Point3d(177.95495128834867, 137.95495128834867, 3.0);
			list.add(point3d);
			point3d = new Point3d(179.54594154601838, 139.54594154601838, 3.0);
			list.add(point3d);
			point3d = new Point3d(177.50019814576464, 141.22483976608436, 3.0);
			list.add(point3d);
			point3d = new Point3d(175.1662263369287, 142.47237368890237, 3.0);
			list.add(point3d);
			point3d = new Point3d(172.63371934721772, 143.2406012854436, 3.0);
			list.add(point3d);
			point3d = new Point3d(170.0, 143.5, 3.0);
			list.add(point3d);
			point3d = new Point3d(30.0, 143.5, 3.0);
			list.add(point3d);
			point3d = new Point3d(27.36628065278227, 143.2406012854436, 3.0);
			list.add(point3d);
			point3d = new Point3d(24.833773663071288, 142.47237368890237, 3.0);
			list.add(point3d);
			point3d = new Point3d(22.499801854235372, 141.22483976608436, 3.0);
			list.add(point3d);
			point3d = new Point3d(20.45405845398161, 139.54594154601838, 3.0);
			list.add(point3d);
			point3d = new Point3d(18.77516023391564, 137.5001981457646, 3.0);
			list.add(point3d);
			point3d = new Point3d(17.527626311097627, 135.16622633692873, 3.0);
			list.add(point3d);
			point3d = new Point3d(16.75939871455639, 132.63371934721772, 3.0);
			list.add(point3d);
			point3d = new Point3d(16.5, 130.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(16.5, 70.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(16.75939871455639, 67.36628065278227, 3.0);
			list.add(point3d);
			point3d = new Point3d(17.527626311097627, 64.8337736630713, 3.0);
			list.add(point3d);
			point3d = new Point3d(18.775160233915635, 62.49980185423537, 3.0);
			list.add(point3d);
			point3d = new Point3d(20.454058453981606, 60.45405845398161, 3.0);
			list.add(point3d);
			point3d = new Point3d(22.499801854235372, 58.77516023391564, 3.0);
			list.add(point3d);
			point3d = new Point3d(24.83377366307128, 57.527626311097634, 3.0);
			list.add(point3d);
			point3d = new Point3d(27.366280652782265, 56.75939871455639, 3.0);
			list.add(point3d);
			point3d = new Point3d(30.0, 56.5, 3.0);
			list.add(point3d);
			point3d = new Point3d(170.0, 56.5, 3.0);
			list.add(point3d);
			point3d = new Point3d(172.63371934721772, 56.75939871455639, 3.0);
			list.add(point3d);
			point3d = new Point3d(175.16622633692873, 57.527626311097634, 3.0);
			list.add(point3d);
			point3d = new Point3d(177.5001981457646, 58.775160233915635, 3.0);
			list.add(point3d);
			point3d = new Point3d(179.54594154601838, 60.4540584539816, 3.0);
			list.add(point3d);
			point3d = new Point3d(181.22483976608436, 62.49980185423537, 3.0);
			list.add(point3d);
			point3d = new Point3d(182.47237368890237, 64.83377366307127, 3.0);
			list.add(point3d);
			point3d = new Point3d(183.2406012854436, 67.36628065278227, 3.0);
			list.add(point3d);
			point3d = new Point3d(183.5, 70.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(183.5, 130.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(183.2406012854436, 132.63371934721772, 3.0);
			list.add(point3d);
			point3d = new Point3d(182.47237368890237, 135.16622633692873, 3.0);
			list.add(point3d);
			point3d = new Point3d(181.22483976608436, 137.5001981457646, 3.0);
			list.add(point3d);
			point3d = new Point3d(179.54594154601838, 139.54594154601838, 3.0);
			list.add(point3d);
			point3d = new Point3d(180.6066017177982, 140.6066017177982, 3.0);
			list.add(point3d);
			point3d = new Point3d(178.8167787843871, 142.13525491562422, 3.0);
			list.add(point3d);
			point3d = new Point3d(176.8098574960932, 143.3650978628255, 3.0);
			list.add(point3d);
			point3d = new Point3d(174.63525491562422, 144.2658477444273, 3.0);
			list.add(point3d);
			point3d = new Point3d(172.34651697560346, 144.81532510892708, 3.0);
			list.add(point3d);
			point3d = new Point3d(170.0, 145.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(30.0, 145.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(27.395277334996045, 144.77211629518314, 3.0);
			list.add(point3d);
			point3d = new Point3d(24.869697850114967, 144.0953893117886, 3.0);
			list.add(point3d);
			point3d = new Point3d(22.500000000000004, 142.99038105676658, 3.0);
			list.add(point3d);
			point3d = new Point3d(20.358185854701908, 141.49066664678466, 3.0);
			list.add(point3d);
			point3d = new Point3d(18.509333353215332, 139.6418141452981, 3.0);
			list.add(point3d);
			point3d = new Point3d(17.009618943233423, 137.5, 3.0);
			list.add(point3d);
			point3d = new Point3d(15.904610688211376, 135.13030214988504, 3.0);
			list.add(point3d);
			point3d = new Point3d(15.227883704816879, 132.60472266500395, 3.0);
			list.add(point3d);
			point3d = new Point3d(15.0, 130.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(15.0, 70.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(15.227883704816879, 67.39527733499605, 3.0);
			list.add(point3d);
			point3d = new Point3d(15.904610688211374, 64.86969785011496, 3.0);
			list.add(point3d);
			point3d = new Point3d(17.00961894323342, 62.50000000000001, 3.0);
			list.add(point3d);
			point3d = new Point3d(18.50933335321533, 60.358185854701915, 3.0);
			list.add(point3d);
			point3d = new Point3d(20.358185854701908, 58.50933335321533, 3.0);
			list.add(point3d);
			point3d = new Point3d(22.499999999999993, 57.00961894323342, 3.0);
			list.add(point3d);
			point3d = new Point3d(24.86969785011497, 55.90461068821138, 3.0);
			list.add(point3d);
			point3d = new Point3d(27.395277334996045, 55.227883704816875, 3.0);
			list.add(point3d);
			point3d = new Point3d(29.999999999999996, 55.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(170.0, 55.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(172.60472266500395, 55.227883704816875, 3.0);
			list.add(point3d);
			point3d = new Point3d(175.130302149885, 55.90461068821137, 3.0);
			list.add(point3d);
			point3d = new Point3d(177.5, 57.00961894323342, 3.0);
			list.add(point3d);
			point3d = new Point3d(179.6418141452981, 58.509333353215325, 3.0);
			list.add(point3d);
			point3d = new Point3d(181.49066664678466, 60.35818585470191, 3.0);
			list.add(point3d);
			point3d = new Point3d(182.99038105676658, 62.49999999999999, 3.0);
			list.add(point3d);
			point3d = new Point3d(184.09538931178864, 64.86969785011497, 3.0);
			list.add(point3d);
			point3d = new Point3d(184.7721162951831, 67.39527733499605, 3.0);
			list.add(point3d);
			point3d = new Point3d(185.0, 70.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(185.0, 130.0, 3.0);
			list.add(point3d);
			point3d = new Point3d(184.81532510892706, 132.34651697560346, 3.0);
			list.add(point3d);
			point3d = new Point3d(184.2658477444273, 134.63525491562422, 3.0);
			list.add(point3d);
			point3d = new Point3d(183.3650978628255, 136.8098574960932, 3.0);
			list.add(point3d);
			point3d = new Point3d(182.13525491562422, 138.8167787843871, 3.0);
			list.add(point3d);
			point3d = new Point3d(180.6066017177982, 140.6066017177982, 3.0);
			list.add(point3d);
			point3d = new Point3d(180.6066017177982, 140.6066017177982, 4.0);
			list.add(point3d);
			point3d = new Point3d(140.0, 100.0, 4.0);
			list.add(point3d);

			if(fazCav1){
				Ferramenta ferramenta1 = new FaceMill("FaceMillAplet2", "Carbide", 10.0,13.0,39.00,57.0,6.0,12,0.12,1);

				Cavidade cavidade1 = new Cavidade(10, 50, 0.0, 20, 100, 180,10.0);
				Workingstep wsCav1 = new Workingstep(cavidade1, new Face(Face.XY,200.0,200.0));
				wsCav1.setFerramenta(ferramenta1);

				CondicoesDeUsinagem cond1 = new CondicoesDeUsinagem(300,10,10,1000,10,10);
				wsCav1.setCondicoesUsinagem(cond1);

				//			BottomAndSideRoughMilling operationCav = new BottomAndSideRoughMilling();

				wsCav1.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(wsCav1));
				Vector<Vector<Ponto>> movimentacoes1 = DeterminarMovimentacao.getPontosMovimentacao(wsCav1);
				wsCav1.setPontosMovimentacao(movimentacoes1.get(0));
				//			wsCav1.setPontosMovimentacao(list);

				cavidade1.getWorkingsteps().add(wsCav1);

				//			features.add(cavidade1);
				workingsteps.add(wsCav1);
			}
			// ::::;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

			
			Vector list2 = new Vector();
			point3d = new Point3d(20.0, 20.0, 11.0);
			list2.add(point3d);
			point3d = new Point3d(21.59099025766973, 21.59099025766973, 11.0);
			list2.add(point3d);
			point3d = new Point3d(18.40900974233027, 21.59099025766973, 11.0);
			list2.add(point3d);
			point3d = new Point3d(18.40900974233027, 18.40900974233027, 11.0);
			list2.add(point3d);
			point3d = new Point3d(21.59099025766973, 18.40900974233027, 11.0);
			list2.add(point3d);
			point3d = new Point3d(21.59099025766973, 21.59099025766973, 11.0);
			list2.add(point3d);
			point3d = new Point3d(23.181980515339465, 23.181980515339465, 11.0);
			list2.add(point3d);
			point3d = new Point3d(16.818019484660535, 23.181980515339465, 11.0);
			list2.add(point3d);
			point3d = new Point3d(16.818019484660535, 16.818019484660535, 11.0);
			list2.add(point3d);
			point3d = new Point3d(23.181980515339465, 16.818019484660535, 11.0);
			list2.add(point3d);
			point3d = new Point3d(23.181980515339465, 23.181980515339465, 11.0);
			list2.add(point3d);
			point3d = new Point3d(24.772970773009195, 24.772970773009195, 11.0);
			list2.add(point3d);
			point3d = new Point3d(15.227029226990805, 24.772970773009195, 11.0);
			list2.add(point3d);
			point3d = new Point3d(15.227029226990805, 15.227029226990805, 11.0);
			list2.add(point3d);
			point3d = new Point3d(24.772970773009195, 15.227029226990805, 11.0);
			list2.add(point3d);
			point3d = new Point3d(24.772970773009195, 24.772970773009195, 11.0);
			list2.add(point3d);
			point3d = new Point3d(26.36396103067893, 26.36396103067893, 11.0);
			list2.add(point3d);
			point3d = new Point3d(13.636038969321072, 26.36396103067893, 11.0);
			list2.add(point3d);
			point3d = new Point3d(13.636038969321072, 13.636038969321072, 11.0);
			list2.add(point3d);
			point3d = new Point3d(26.36396103067893, 13.636038969321072, 11.0);
			list2.add(point3d);
			point3d = new Point3d(26.36396103067893, 26.36396103067893, 11.0);
			list2.add(point3d);
			point3d = new Point3d(27.95495128834866, 27.95495128834866, 11.0);
			list2.add(point3d);
			point3d = new Point3d(11.04504871165134, 27.95495128834866, 11.0);
			list2.add(point3d);
			point3d = new Point3d(12.04504871165134, 11.04504871165134, 11.0);
			list2.add(point3d);
			point3d = new Point3d(27.95495128834866, 12.04504871165134, 11.0);
			list2.add(point3d);
			point3d = new Point3d(27.95495128834866, 27.95495128834866, 11.0);
			list2.add(point3d);
			point3d = new Point3d(28.5, 28.5, 11.0);
			list2.add(point3d);
			point3d = new Point3d(11.5, 28.5, 11.0);
			list2.add(point3d);
			point3d = new Point3d(11.5, 11.5, 11.0);
			list2.add(point3d);
			point3d = new Point3d(28.5, 11.5, 11.0);
			list2.add(point3d);
			point3d = new Point3d(28.5, 28.5, 11.0);
			list2.add(point3d);
			
			if(fazCav2){
				Ferramenta ferramenta2 = new FaceMill("FaceMillAplet2", "Carbide", 5.0,13.0,39.00,57.0,6.0,12,0.12,1);

				Cavidade cavidade2 = new Cavidade(10, 10, 0.0, 1.5, 20, 20, 2.0);

				Workingstep wsCav2 = new Workingstep(cavidade2, new Face(Face.XY, 200.0,200.0));
				wsCav2.setFerramenta(ferramenta2);
				//			wsCav2.setPontosMovimentacao(list2);
				//vc,f,vf,n,ap,ae
				CondicoesDeUsinagem cond2 = new CondicoesDeUsinagem(200,10,10,1000,1,1);
				wsCav2.setCondicoesUsinagem(cond2);

				wsCav2.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(wsCav2));
				System.out.println("PONTOS CAV: " + wsCav2.getPontos());
				Vector<Vector<Ponto>> movimentacoes2 = DeterminarMovimentacao.getPontosMovimentacao(wsCav2);
				wsCav2.setPontosMovimentacao(movimentacoes2.get(0));
				System.out.println("MOV CAV: " + wsCav2.getPontosMovimentacao());

				//			System.out.println("MOV : " + movimentacao2);

				cavidade2.getWorkingsteps().add(wsCav2);

				workingsteps.add(wsCav2);
			}
			
			if(fazRanhura){
				ferramenta = new FaceMill("FaceMillAplet3", "Carbide", 5.0,13.0,39.00,57.0,6.0,12,0.12,1);
				//
				////			ranhura = new Ranhura("SLOT_1", 0, 20, 0, 20.00, 9.0,
				////					Ranhura.HORIZONTAL);
				//
				ranhura = new Ranhura("Ranho Teste", 0.0,20.0,0.0,0.0,30.0,0.0,20,4,200.0,Ranhura.HORIZONTAL);


				Workingstep wsRanhu = new Workingstep(ranhura, new Face(Face.XY, 200.0, 200.0));
				wsRanhu.setFerramenta(ferramenta);
				//vc,f  ,vf ,n   ,ap ,ae
				CondicoesDeUsinagem cond = new CondicoesDeUsinagem(100,1,1,1000,2,4);
				wsRanhu.setCondicoesUsinagem(cond);

				wsRanhu.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(wsRanhu));
				System.out.println("PONTOS RAN: " + wsRanhu.getPontos());
				Vector movimentacoes = DeterminarMovimentacao.getPontosMovimentacao(wsRanhu);
				Vector movimentacao = (Vector)movimentacoes.get(0);
				wsRanhu.setPontosMovimentacao(movimentacao);
				System.out.println("MOV RAN: " + wsRanhu.getPontosMovimentacao());
				////			
				ranhura.getWorkingsteps().add(wsRanhu);

				workingsteps.add(wsRanhu);
			}
			
//			ferramenta = new FaceMill(20, 11, 2.0);
//			ranhura = new Ranhura("SLOT_2", 70, 0, 13, 20.00, 4,
//					Ranhura.VERTICAL, ferramenta);
//			features.add(ranhura);
//		
			
			if(fazDegrau){
				ferramenta = new FaceMill("FaceMillAplet3", "Carbide", 5.0,13.0,39.00,57.0,6.0,12,0.12,1);

				degrau = new Degrau("Degrau Teste", 0.0,0.0,0.0,20.0,10);
				degrau.setEixo(Degrau.VERTICAL);


				Workingstep wsDegrau = new Workingstep(degrau, new Face(Face.XY, 200.0, 200.0));
				wsDegrau.setFerramenta(ferramenta);
				//vc,f  ,vf ,n   ,ap ,ae
				CondicoesDeUsinagem cond4 = new CondicoesDeUsinagem(100,1,1,1000,2,4);
				wsDegrau.setCondicoesUsinagem(cond4);

				wsDegrau.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(wsDegrau));
				System.out.println("PONTOS DEGRAU: " + wsDegrau.getPontos());
				Vector movimentacoes4 = DeterminarMovimentacao.getPontosMovimentacao(wsDegrau);
				Vector movimentacao4 = (Vector)movimentacoes4.get(1);
				wsDegrau.setPontosMovimentacao(movimentacao4);
				System.out.println("MOV DEGRAU: " + wsDegrau.getPontosMovimentacao());
				////						
				degrau.getWorkingsteps().add(wsDegrau);

				workingsteps.add(wsDegrau);
			}
			
			
			//FUROOOOOOOOOO
			
			if(fazFuro){
				double profundidade = 20;
				double diametro = 20;

				//diametro = 6
				ferramenta = new CenterDrill("Twist Teste", "Criptonita", 6, 10, 60, 100, 120, 25, 1,1,1);

				//			furo = new FuroBasePlana("Furo Teste", 100, 50, 0, diametro, profundidade);
				furo = new FuroBaseConica("Furo Teste",100,50,0,diametro,profundidade,30,11);

				Workingstep wsFuro = new Workingstep(furo, new Face(Face.XY, 200.0, 200.0));

				wsFuro.setFerramenta(ferramenta);
				//vc,f  ,vf ,n   ,ap ,ae
				CondicoesDeUsinagem cond3 = new CondicoesDeUsinagem(100,1,1,1000,2,0);
				wsFuro.setCondicoesUsinagem(cond3);

				CenterDrilling cDrilling = new CenterDrilling("Drilling", 5);
				cDrilling.setCuttingDepth(10);
				wsFuro.setOperation(cDrilling);

				wsFuro.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(wsFuro));
				Vector movimentacoes3 = DeterminarMovimentacao.getPontosMovimentacao(wsFuro);
				Vector movimentacao3 = (Vector)movimentacoes3.get(0);
				wsFuro.setPontosMovimentacao(movimentacao3);

				furo.getWorkingsteps().add(wsFuro);

				System.out.println("PASSOU CENTER DRILLING");

				//diametro = 20
				ferramenta = new TwistDrill("Twist Teste", "Criptonita", 20, 30, 60, 100, 120, 25, 1,1,1);

				wsFuro = new Workingstep(furo, new Face(Face.XY, 200.0, 200.0));
				wsFuro.setFerramenta(ferramenta);
				//vc,f  ,vf ,n   ,ap ,ae
				cond3 = new CondicoesDeUsinagem(100,1,1,1000,2,0);
				wsFuro.setCondicoesUsinagem(cond3);

				Drilling drilling = new Drilling("Drilling", 5);
				drilling.setCuttingDepth(profundidade);
				wsFuro.setOperation(drilling);

				wsFuro.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(wsFuro));
				movimentacoes3 = DeterminarMovimentacao.getPontosMovimentacao(wsFuro);
				movimentacao3 = (Vector)movimentacoes3.get(0);
				wsFuro.setPontosMovimentacao(movimentacao3);

				furo.getWorkingsteps().add(wsFuro);

				System.out.println("PASSOU DRILLING");

				//diametro = 15
				ferramenta = new FaceMill("Face Teste", "Titanio", 15, 5, 50, 70, 20, 1,1,1);

				wsFuro = new Workingstep(furo, new Face(Face.XY, 200.0, 200.0));
				wsFuro.setFerramenta(ferramenta);
				//vc,f  ,vf ,n   ,ap ,ae
				cond3 = new CondicoesDeUsinagem(100,1,1,1000,5,5);
				wsFuro.setCondicoesUsinagem(cond3);

				BottomAndSideRoughMilling milling = new BottomAndSideRoughMilling("Milling", 5);
				wsFuro.setOperation(milling);

				wsFuro.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(wsFuro));
				movimentacoes3 = DeterminarMovimentacao.getPontosMovimentacao(wsFuro);
				movimentacao3 = (Vector)movimentacoes3.get(0);
				wsFuro.setPontosMovimentacao(movimentacao3);

				furo.getWorkingsteps().add(wsFuro);

				System.out.println("PASSOU FRESAMENTO");

				workingsteps.add(furo.getWorkingsteps().get(0));
				workingsteps.add(furo.getWorkingsteps().get(1));
				workingsteps.add(furo.getWorkingsteps().get(2));
				
			}
			
			
			
//			furo = new Furo("HOLE_2", 190, 150, 13, 3, ferramenta);
//			features.add(furo);
//
//			furo = new Furo("HOLE_3", 15, 70, 13, 3, ferramenta);
////			features.add(furo);
//
//			ferramenta = new TwistDrill(10, 16, 1);
//			furo = new Furo("HOLE_4", 170, 170, 13, 10, ferramenta);
//			features.add(furo);
//
//			furo = new Furo("HOLE_5", 35, 65, 13, 10, ferramenta);
////			features.add(furo);
//
//			ferramenta = new TwistDrill(3, 14, 1);
//
//			furo = new Furo("HOLE_6", 30, 85, 13, 3, ferramenta);
////			features.add(furo);
//			furo = new Furo("HOLE_7", 45, 85, 13, 3, ferramenta);
////			features.add(furo);
//
//			features.add(cavidade1);
			
			
//			
//			workingsteps.add(wsCav1);
//			workingsteps.add(wsCav2);
//			workingsteps.add(wsRanhu);
//			workingsteps.add(wsDegrau);
//			workingsteps.add(furo.getWorkingsteps().get(0));
//			workingsteps.add(furo.getWorkingsteps().get(1));
//			workingsteps.add(furo.getWorkingsteps().get(2));
			
			ArrayList<Point3d> pontosApoio = new ArrayList<Point3d>();

			ProjetoDeSimulacao projetoSimulacao = new ProjetoDeSimulacao(
					new Rectangle3D(200, 200, 30), pontosApoio, workingsteps,1);

			System.out.println("RÁÁÁÁ");
			
			win = new SimulationPanel(projetoSimulacao);
			win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			win.pack();
			win.setVisible(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
