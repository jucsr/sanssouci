package br.UFSC.GRIMA.integracao;

import jsdai.SCombined_schema.EBall_endmill;
import jsdai.SCombined_schema.EBoring_tool;
import jsdai.SCombined_schema.EBullnose_endmill;
import jsdai.SCombined_schema.ECenter_drill;
import jsdai.SCombined_schema.ECutting_component;
import jsdai.SCombined_schema.ECutting_tool;
import jsdai.SCombined_schema.EEndmill;
import jsdai.SCombined_schema.EFacemill;
import jsdai.SCombined_schema.EMachining_workingstep;
import jsdai.SCombined_schema.EMilling_tool_dimension;
import jsdai.SCombined_schema.EReamer;
import jsdai.SCombined_schema.ETool_body;
import jsdai.SCombined_schema.ETwist_drill;
import jsdai.lang.SdaiException;
import br.UFSC.GRIMA.entidades.ferramentas.BallEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.BoringTool;
import br.UFSC.GRIMA.entidades.ferramentas.BullnoseEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.CenterDrill;
import br.UFSC.GRIMA.entidades.ferramentas.EndMill;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.entidades.ferramentas.Reamer;
import br.UFSC.GRIMA.entidades.ferramentas.TwistDrill;

public class FerramentaReader {

	public static Ferramenta getFerramenta(
			EMachining_workingstep machining_workingstep) throws SdaiException {

		ECutting_tool cutting_tool = (ECutting_tool) machining_workingstep
				.getIts_operation(null).getIts_tool(null);

		ETool_body eTool_body = cutting_tool.getIts_tool_body(null);

		ECutting_component cutting_component = cutting_tool
				.getIts_cutting_edge(null).getByIndex(1);

		if (eTool_body.isKindOf(ECenter_drill.class)) {

			ECenter_drill center_drill = (ECenter_drill) eTool_body;

			EMilling_tool_dimension dimension = center_drill.getDimension(null);

			String name = cutting_tool.getIts_id(null);
			String material = cutting_component.getIts_material(null)
					.getStandard_identifier(null);
			double diametro = dimension.getDiameter(null);
			double tipAngle = dimension.getTool_tip_half_angle(null)*180/Math.PI;
			double cuttingEdge = dimension.getCutting_edge_length(null);
			double profundidade = center_drill.getPilot_length(null);
			double offSetLength = cutting_component.getTool_offset_length(null);
			int handOfCut = center_drill.getHand_of_cut(null);

			CenterDrill centerDrill = new CenterDrill(name, material, diametro,
					tipAngle, cuttingEdge, profundidade, offSetLength, 0, 0, 0,
					handOfCut);

			return centerDrill;

		} else if (eTool_body.isKindOf(ETwist_drill.class)) {

			ETwist_drill twist_drill = (ETwist_drill) eTool_body;

			EMilling_tool_dimension dimension = twist_drill.getDimension(null);

			String name = cutting_tool.getIts_id(null);
			String material = cutting_component.getIts_material(null)
					.getStandard_identifier(null);
			double diametro = dimension.getDiameter(null);
			double tipAngle = dimension.getTool_tip_half_angle(null)*180/Math.PI;
			double cuttingEdge = dimension.getCutting_edge_length(null);
			double profundidade = twist_drill.getPilot_length(null);
			double offSetLength = cutting_component.getTool_offset_length(null);
			int handOfCut = twist_drill.getHand_of_cut(null);

			TwistDrill twistDrill = new TwistDrill(name, material, diametro,
					tipAngle, cuttingEdge, profundidade, offSetLength, 0, 0, 0,
					handOfCut);

			return twistDrill;

		} else if (eTool_body.isKindOf(EFacemill.class)) {

			EFacemill facemill = (EFacemill) eTool_body;

			EMilling_tool_dimension dimension = facemill.getDimension(null);

			String name = cutting_tool.getIts_id(null);
			String material = cutting_component.getIts_material(null)
					.getStandard_identifier(null);
			double diametro = dimension.getDiameter(null);
			double cuttingEdge = dimension.getCutting_edge_length(null);
			double profundidade = facemill.getPilot_length(null);
			double offSetLength = cutting_component.getTool_offset_length(null);
			int handOfCut = facemill.getHand_of_cut(null);

			FaceMill faceMill = new FaceMill(name, material, diametro,
					cuttingEdge, profundidade, offSetLength, 0, 0, 0, handOfCut);

			return faceMill;

		} else if (eTool_body.isKindOf(EEndmill.class)) {

			EEndmill endmill = (EEndmill) eTool_body;

			EMilling_tool_dimension dimension = endmill.getDimension(null);

			String name = cutting_tool.getIts_id(null);
			String material = cutting_component.getIts_material(null)
					.getStandard_identifier(null);
			double diametro = dimension.getDiameter(null);
			double cuttingEdge = dimension.getCutting_edge_length(null);
			double profundidade = endmill.getPilot_length(null);
			double offSetLength = cutting_component.getTool_offset_length(null);
			int handOfCut = endmill.getHand_of_cut(null);

			EndMill endMill = new EndMill(name, material, diametro,
					cuttingEdge, profundidade, offSetLength, 0, 0, 0, handOfCut);

			return endMill;

		} else if (eTool_body.isKindOf(EBall_endmill.class)) {

			EBall_endmill ball_endmill = (EBall_endmill) eTool_body;

			EMilling_tool_dimension dimension = ball_endmill.getDimension(null);

			String name = cutting_tool.getIts_id(null);
			String material = cutting_component.getIts_material(null)
					.getStandard_identifier(null);
			double diametro = dimension.getDiameter(null);
			double edgeRadius = dimension.getEdge_radius(null);
			double edgeCenterVertical = dimension.getEdge_center_vertical(null);
			double cuttingEdge = dimension.getCutting_edge_length(null);
			double profundidade = ball_endmill.getPilot_length(null);
			double offSetLength = cutting_component.getTool_offset_length(null);
			int handOfCut = ball_endmill.getHand_of_cut(null);

			BallEndMill ballEndMill = new BallEndMill(name, material, diametro,
					edgeRadius, edgeCenterVertical, cuttingEdge, profundidade,
					offSetLength, 0, 0, 0, handOfCut);

			return ballEndMill;

		} else if (eTool_body.isKindOf(EBullnose_endmill.class)) {

			EBullnose_endmill bullnose_endmill = (EBullnose_endmill) eTool_body;

			EMilling_tool_dimension dimension = bullnose_endmill.getDimension(null);

			String name = cutting_tool.getIts_id(null);
			String material = cutting_component.getIts_material(null)
					.getStandard_identifier(null);
			double diametro = dimension.getDiameter(null);
			double edgeRadius = dimension.getEdge_radius(null);
			double edgeCenterVertical = dimension.getEdge_center_vertical(null);
			double edgeCenterHorizontal = dimension
					.getEdge_center_horizontal(null);
			double cuttingEdge = dimension.getCutting_edge_length(null);
			double profundidade = bullnose_endmill.getPilot_length(null);
			double offSetLength = cutting_component.getTool_offset_length(null);
			int handOfCut = bullnose_endmill.getHand_of_cut(null);

			BullnoseEndMill bullnoseEndMill = new BullnoseEndMill(name,
					material, diametro, edgeRadius, edgeCenterVertical,
					edgeCenterHorizontal, cuttingEdge, profundidade,
					offSetLength, 0, 0, 0, handOfCut);

			return bullnoseEndMill;

		} else if (eTool_body.isKindOf(EReamer.class)) {

			EReamer eReamer = (EReamer) eTool_body;

			EMilling_tool_dimension dimension = eReamer.getDimension(null);

			String name = cutting_tool.getIts_id(null);
			String material = cutting_component.getIts_material(null)
					.getStandard_identifier(null);
			double diametro = dimension.getDiameter(null);
			double cuttingEdge = dimension.getCutting_edge_length(null);
			double profundidade = eReamer.getPilot_length(null);
			double offSetLength = cutting_component.getTool_offset_length(null);
			int handOfCut = eReamer.getHand_of_cut(null);
			int numberOfTeeth = eReamer.getNumber_of_teeth(null);

			Reamer reamer = new Reamer(name, material, diametro, cuttingEdge,
					profundidade, offSetLength, 0, 0, 0, handOfCut,
					numberOfTeeth);

			return reamer;

		} else if (eTool_body.isKindOf(EBoring_tool.class)) {

			EBoring_tool boring_tool = (EBoring_tool) eTool_body;

			EMilling_tool_dimension dimension = boring_tool.getDimension(null);

			String name = cutting_tool.getIts_id(null);
			String material = cutting_component.getIts_material(null)
					.getStandard_identifier(null);
			double diametroMaximo = dimension.getDiameter(null);
			double edgeRadius = dimension.getEdge_radius(null);
			double cuttingEdge = dimension.getCutting_edge_length(null);
			double profundidade = boring_tool.getPilot_length(null);
			double offSetLength = cutting_component.getTool_offset_length(null);
			int handOfCut = boring_tool.getHand_of_cut(null);

			String diametro = "3-" + diametroMaximo;

			BoringTool boringTool = new BoringTool(name, material, diametro,
					edgeRadius, cuttingEdge, profundidade, offSetLength, 0, 0,
					0, handOfCut, null);

			boringTool.setDiametroFerramenta(diametroMaximo);

			return boringTool;

		} else {

			System.out.println("Ferramenta Desconhecida!!! : "
					+ eTool_body.toString());

			return null;

		}
	}
}
