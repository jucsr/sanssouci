package br.UFSC.GRIMA.integracao;

import java.util.ArrayList;

import javax.vecmath.Point3d;

import jsdai.SCombined_schema.EClosed_pocket;
import jsdai.SCombined_schema.EPlus_minus_value;
import jsdai.SCombined_schema.ERectangular_closed_profile;
import jsdai.lang.SdaiException;
import br.UFSC.GRIMA.entidades.features.CavidadeFundoArredondado;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.util.projeto.Axis2Placement3D;

public class CavidadeFundoArredondadoReader {

	public static CavidadeFundoArredondado getCavidade(EClosed_pocket pocket)
			throws SdaiException {

		String id = pocket.getIts_id(null);

		// double x = pocket.getFeature_placement(null).getLocation(null)
		// .getCoordinates(null).getByIndex(1);
		// double y = pocket.getFeature_placement(null).getLocation(null)
		// .getCoordinates(null).getByIndex(2);
		//
		// double alturaBloco =
		// ((EBlock)pocket.getIts_workpiece(null).getIts_bounding_geometry(null)).getZ(null);
		//
		// double z = pocket.getFeature_placement(null).getLocation(null)
		// .getCoordinates(null).getByIndex(3)*(-1) + alturaBloco;

		double locX = pocket.getFeature_placement(null).getLocation(null)
				.getCoordinates(null).getByIndex(1);
		double locY = pocket.getFeature_placement(null).getLocation(null)
				.getCoordinates(null).getByIndex(2);
		double locZ = pocket.getFeature_placement(null).getLocation(null)
				.getCoordinates(null).getByIndex(3);

		double verticeRaio = pocket.getOrthogonal_radius(null)
				.getTheoretical_size(null);

		double fundoRaio = pocket.getPlanar_radius(null).getTheoretical_size(
				null);

		double largura = ((ERectangular_closed_profile) pocket
				.getFeature_boundary(null)).getProfile_width(null)
				.getTheoretical_size(null);

		double comprimento = ((ERectangular_closed_profile) pocket
				.getFeature_boundary(null)).getProfile_length(null)
				.getTheoretical_size(null);

		double profundidade = pocket.getDepth(null).getPosition(null)
				.getLocation(null).getCoordinates(null).getByIndex(3)
				* (-1);

		double tolerancia = ((EPlus_minus_value) pocket.getOrthogonal_radius(
				null).getImplicit_tolerance(null)).getUpper_limit(null);

		double x = 0, y = 0, z = 0;

		Face faceAtual = FaceReader.getFace(pocket);

		if (faceAtual.getTipo() == Face.XY) {

			x = locX - comprimento / 2;

			y = locY - largura / 2;

			z = faceAtual.getProfundidadeMaxima() - locZ;

		} else if (faceAtual.getTipo() == Face.YX) {

			x = locX - comprimento / 2;

			y = faceAtual.getLargura() - locY - largura / 2;

			z = locZ;

		} else if (faceAtual.getTipo() == Face.YZ) {

			x = faceAtual.getComprimento() - comprimento / 2 - locZ;

			y = locY - largura / 2;

			z = faceAtual.getProfundidadeMaxima() - locX;

		} else if (faceAtual.getTipo() == Face.ZY) {

			x = locZ - comprimento / 2;

			y = locY - largura / 2;

			z = locX;

		} else if (faceAtual.getTipo() == Face.XZ) {

			x = locX - comprimento / 2;

			y = locZ - largura / 2;

			z = locY;

		} else if (faceAtual.getTipo() == Face.ZX) {

			x = locX - comprimento / 2;

			y = faceAtual.getLargura() - locZ - largura / 2;

			z = faceAtual.getProfundidadeMaxima() - locY;

		}

		CavidadeFundoArredondado cavidade = new CavidadeFundoArredondado(id, x,
				y, z, verticeRaio, fundoRaio, largura, comprimento,
				profundidade);
		cavidade.setTolerancia(tolerancia);

		double zLinhaX = pocket.getFeature_placement(null).getAxis(null)
				.getDirection_ratios(null).getByIndex(1);
		double zLinhaY = pocket.getFeature_placement(null).getAxis(null)
				.getDirection_ratios(null).getByIndex(2);
		double zLinhaZ = pocket.getFeature_placement(null).getAxis(null)
				.getDirection_ratios(null).getByIndex(3);

		ArrayList<Double> axis = new ArrayList<Double>();
		axis.add(zLinhaX);
		axis.add(zLinhaY);
		axis.add(zLinhaZ);

		zLinhaX = pocket.getFeature_placement(null).getRef_direction(null)
				.getDirection_ratios(null).getByIndex(1);
		zLinhaY = pocket.getFeature_placement(null).getRef_direction(null)
				.getDirection_ratios(null).getByIndex(2);
		zLinhaZ = pocket.getFeature_placement(null).getRef_direction(null)
				.getDirection_ratios(null).getByIndex(3);

		ArrayList<Double> refDirection = new ArrayList<Double>();
		refDirection.add(zLinhaX);
		refDirection.add(zLinhaY);
		refDirection.add(zLinhaZ);

		double zOriginal = pocket.getFeature_placement(null).getLocation(null)
				.getCoordinates(null).getByIndex(3);

		Point3d coordinates = new Point3d(x, y, zOriginal);

		Axis2Placement3D position = new Axis2Placement3D(coordinates, axis,
				refDirection);

		cavidade.setPosition(position);

		return cavidade;
	}
}
