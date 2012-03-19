package br.UFSC.GRIMA.integracao;

import jsdai.SCombined_schema.EBottom_and_side_finish_milling;
import jsdai.SCombined_schema.EBottom_and_side_rough_milling;
import jsdai.SCombined_schema.EMachining_workingstep;
import jsdai.SCombined_schema.EMilling_technology;
import jsdai.lang.SdaiException;
import br.UFSC.GRIMA.capp.CondicoesDeUsinagem;

public class CondicoesDeUsinagemReader {

	public static CondicoesDeUsinagem getCondicoes(
			EMachining_workingstep machining_workingstep) throws SdaiException {
		
		double ap = 2.0, ae = 0, vf = 0, vc = 0, n = 0; 		
		
		if(machining_workingstep.getIts_operation(null).isKindOf(EBottom_and_side_rough_milling.class)){
			
			ap = ((EBottom_and_side_rough_milling)machining_workingstep.getIts_operation(null)).getAxial_cutting_depth(null);
		
			ae =((EBottom_and_side_rough_milling)machining_workingstep.getIts_operation(null)).getRadial_cutting_depth(null);
			
		}else if (machining_workingstep.getIts_operation(null).isKindOf(EBottom_and_side_finish_milling.class)){
			
			ap = ((EBottom_and_side_finish_milling)machining_workingstep.getIts_operation(null)).getAxial_cutting_depth(null);
			
			ae =((EBottom_and_side_finish_milling)machining_workingstep.getIts_operation(null)).getRadial_cutting_depth(null);
			
		}else{
//			System.out.println("Não é Milling Operation = " + machining_workingstep.getIts_operation(null) );
		}
		
		EMilling_technology technology = (EMilling_technology)machining_workingstep.getIts_operation(null).getIts_technology(null);
		
		vf = technology.getFeedrate(null);
		
		vc = technology.getCutspeed(null);
		
		n = technology.getSpindle(null);
			
		return new CondicoesDeUsinagem(vc, vf, vf, n, ap, ae);
	}

}
