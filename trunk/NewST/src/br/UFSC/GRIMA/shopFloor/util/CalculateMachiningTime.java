package br.UFSC.GRIMA.shopFloor.util;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.vecmath.Point3d;

import br.UFSC.GRIMA.bancoDeDados.Conexao;
import br.UFSC.GRIMA.capp.DeterminarMovimentacao;
import br.UFSC.GRIMA.capp.Workingstep;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideFinishMilling;
import br.UFSC.GRIMA.capp.machiningOperations.BottomAndSideRoughMilling;
import br.UFSC.GRIMA.capp.machiningOperations.CenterDrilling;
import br.UFSC.GRIMA.capp.machiningOperations.Drilling;
import br.UFSC.GRIMA.capp.mapeadoras.MapeadoraDeWorkingsteps;
import br.UFSC.GRIMA.capp.movimentacoes.MovimentacaoFuroBaseArredondada;
import br.UFSC.GRIMA.capp.movimentacoes.MovimentacaoGeneralClosedPocket;
import br.UFSC.GRIMA.capp.movimentacoes.MovimentacaoRanhuraPerfilGenerico;
import br.UFSC.GRIMA.capp.movimentacoes.MovimentacaoRanhuraPerfilParcialCircular;
import br.UFSC.GRIMA.capp.movimentacoes.MovimentacaoRanhuraPerfilQuadradoU;
import br.UFSC.GRIMA.capp.movimentacoes.MovimentacaoRanhuraPerfilRoundedU;
import br.UFSC.GRIMA.capp.movimentacoes.MovimentacaoRanhuraPerfilVee;
import br.UFSC.GRIMA.entidades.Material;
import br.UFSC.GRIMA.entidades.features.Cavidade;
import br.UFSC.GRIMA.entidades.features.Degrau;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.Furo;
import br.UFSC.GRIMA.entidades.features.FuroBaseArredondada;
import br.UFSC.GRIMA.entidades.features.FuroBaseConica;
import br.UFSC.GRIMA.entidades.features.FuroBaseEsferica;
import br.UFSC.GRIMA.entidades.features.FuroBasePlana;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.entidades.features.Ranhura;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilBezier;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilCircularParcial;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilQuadradoU;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilRoundedU;
import br.UFSC.GRIMA.entidades.features.RanhuraPerfilVee;
import br.UFSC.GRIMA.entidades.ferramentas.BallEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.BullnoseEndMill;
import br.UFSC.GRIMA.entidades.ferramentas.EndMill;
import br.UFSC.GRIMA.entidades.ferramentas.FaceMill;
import br.UFSC.GRIMA.entidades.machiningResources.MachineTool;
import br.UFSC.GRIMA.entidades.machiningResources.MillingMachine;
import br.UFSC.GRIMA.entidades.machiningResources.Spindle;
import br.UFSC.GRIMA.integracao.WorkingStepsReader;
import br.UFSC.GRIMA.simulator.ProjetoDeSimulacao;
import br.UFSC.GRIMA.util.LinearPath;
import br.UFSC.GRIMA.util.Path;
import br.UFSC.GRIMA.util.Ponto;

/**
 * 
 * @author Thiago
 *
 */
public class CalculateMachiningTime 
{
	private Connection conn;
	private String Query = "";
	private String Query2 = "";
	private int userID;
	private int id = 10;
	private ResultSet rs = null;
	private Statement statement;
	private Dimension d;
	private Conexao conexao = new Conexao();
	
	private double K; 
	private double Kc1;
	private double Z;
	private double Kc;
	
	
	private Workingstep workingstep;
	private MachineTool machine;

	
	public CalculateMachiningTime(Workingstep workingstep, MachineTool machine, Material material)
	{
		conexao.setConn("150.162.105.1", "webTools", "webcad", "julio123");
		
		conn = conexao.getConn();
		int materialType = material.getCategory();
		Query = "SELECT * FROM Specific_Cutting_Force_Kc_Value WHERE Material_bloco = " + materialType;
		
		try 
		{
			this.statement = conn.createStatement();
			
			rs = statement.executeQuery(Query);
			rs.next();
			
			 K = rs.getDouble("K"); 
			 Kc1 = rs.getDouble("Kc1");
			 Z = rs.getDouble("Z");
			// Kc = rs.getDouble("Kc");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		this.workingstep = workingstep;
		this.machine = machine;
		this.calculateTimes();
	}
	
	public double calculateTimes() 
	{	
		double P = 0,P_teste = 0,P_max = 0, fn = 0, Vc, D, n, Vf = 0, prof = 0, T = 0, fn_max = 0, larg = 0, raio = 0,ap = 0, ae = 0, Hm = 0, Kc2 = 0, ap_max = 0,
				comprimento = 0, VolumeR = 0, VolumePP = 0, Volume1P = 0, np = 0, nd,  Vfm = 0, Tm = 0, RPM = 0, Tc = 0, T_max = 0;
		
		
		for(int j = 0; j < machine.getItsSpindle().size(); j++){
				
				Spindle spindleTemp = machine.getItsSpindle().get(j);
				P = spindleTemp.getSpindleMaxPower();
				Tm = spindleTemp.getMaxTorque();
				RPM = spindleTemp.getItsSpeedRange();
		}

		
		fn = workingstep.getCondicoesUsinagem().getF();
		Vc = workingstep.getCondicoesUsinagem().getVc();
		D = workingstep.getFerramenta().getDiametroFerramenta();
		ap = workingstep.getCondicoesUsinagem().getAp();
		ae = workingstep.getCondicoesUsinagem().getAe();
		nd = workingstep.getFerramenta().getNumberOfTeeth();
	
		
		workingstep.getFeature().getClass();
		
		Feature feature = workingstep.getFeature();
		
		//--------- Get Profundidade------------
		
		if (feature.getClass()== FuroBasePlana.class)
		{
				FuroBasePlana furo = ((FuroBasePlana)feature);
					
				prof = furo.getProfundidade();
			
		}else if(feature.getClass() == FuroBaseArredondada.class){
			
			FuroBaseArredondada furo = ((FuroBaseArredondada)feature);	
			
			prof = furo.getProfundidade();
			
		}else if(feature.getClass() == Cavidade.class)
		{
			Cavidade cavidade = (Cavidade)feature;
			
			prof = cavidade.getProfundidade();
			larg = cavidade.getLargura();
			raio = cavidade.getRaio();
		
		}else if(feature.getClass()== FuroBaseConica.class){
		
			FuroBaseConica furo = ((FuroBaseConica)feature);
			
			prof = furo.getProfundidade();
		
		}else if(feature.getClass() == FuroBaseEsferica.class){
			
			FuroBaseEsferica furo = ((FuroBaseEsferica)feature);
			
			prof = furo.getProfundidade();
			
		}else if(feature.getClass()== Ranhura.class){
			
			Ranhura ranhura = ((Ranhura)feature);
			
			prof = ranhura.getProfundidade();
			larg = ranhura.getLargura();
			comprimento = ranhura.getComprimento();
			
		}else if(feature.getClass()== Degrau.class){
			
			Degrau degrau = ((Degrau)feature);
			
			prof = degrau.getProfundidade();
			larg = degrau.getLargura();
			comprimento = degrau.getComprimento();
		}
		
	//------------- Calculando Tempos--------------
		
		if(this.workingstep.getOperation().getClass() == Drilling.class || 
				this.workingstep.getOperation().getClass() == CenterDrilling.class)
		{
			n = Vc*1000/(Math.PI*D);
			
			Vf = n*fn;
			
			P_teste = (1.25*Math.pow(D,2)*K*n*(0.056+1.5*fn))/100000;
		
			if(P_teste <= P){
				
				T = prof/Vf;
				
			}else{
				
				fn_max = ((P*100000)/(1.25*Math.pow(D,2)*K*n))*(1/1.5);
				
				Vf = n*fn_max;
				
				T = prof/Vf;
			}
					

		}else if(machine.getClass()== MillingMachine.class &&(this.workingstep.getOperation().getClass() == BottomAndSideRoughMilling.class ||
				this.workingstep.getOperation().getClass() == BottomAndSideFinishMilling.class) &&
				(this.workingstep.getFeature().getClass() == Ranhura.class))
		{
			 //	if(workingstep.getFeature().getRugosidade() >= machine.getAccuracy()&& this.workingstep.getOperation().getClass() == BottomAndSideRoughMilling.class ){
    		
	 		
					workingstep.getCondicoesUsinagem().setAp(workingstep.getFerramenta().getCuttingEdgeLength()*0.75);
					workingstep.getCondicoesUsinagem().setAe(D*0.75);
	    		
					n = Vc * 1000 /(Math.PI * D);
					P_max = (P/RPM)*n;
					Vf = n * fn * nd;
					Hm = (fn*ae*360)/(D*Math.PI*180 / Math.PI * Math.acos(1-(2*ae/D)));
					Kc = Kc1*Math.pow(Hm, -Z);
					Kc = Kc/100;
			    	Tc = (ap*ae*Vf*Kc)/(2*Math.PI*n);
			 		T_max = ((-Tm/RPM)*n)+Tm; 
			    	System.out.println("Torque "+Tc);
			 		System.out.println("n "+n);
			 		
			 		System.out.println("Kc : "+Kc);
					
			    	if(Tc > T_max){
			    		
			    		System.out.println("Entrou 1");
			    		double Vf_max = (Tm*2*Math.PI*n)/(ap*ae*Kc);
			    		System.out.println("Vf max "+Vf_max+" Vf: "+Vf);
			    		
			    		if(Vf_max >= Vf*0.42 && Vf_max <= Vf*1.57){
			    			System.out.println("Entrou 2");
			    			Vf = Vf_max; 
			    			n = Vf/fn*nd;
			    			System.out.println("n2 "+n);
			    		
			    		}else{
			    			
			    			System.out.println("Entrou 3");
			    			ap_max = (Tm*2*Math.PI*n)/(ae*Vf*Kc);
			    			System.out.println("ap max "+ap_max);
			    		
			    			workingstep.getCondicoesUsinagem().setAp(ap_max);
			    		}
			    	}
			    	if(n > RPM){
			    		System.out.println("Entrou 4 ");
			    		n = RPM;
			    		P_max = (P/RPM)*n;
			    		Vf = n * fn * nd;
			    	}
			    	P_teste = (ap*ae*Vf*Kc)/(60*102*9.81);
			    	System.out.println("Potencia teste: "+P_teste+" ap: "+ap+" Vf"+Vf);
			    	
			    	if(P_teste > P){
			    		
			    		System.out.println("P: "+P);
			    		System.out.println("Entrou 5");
			    		ap = (P*60*102*9.81)/(ae*Kc*Vf);
			    		
			    		if(ap <= workingstep.getFerramenta().getCuttingEdgeLength()*0.75) workingstep.getCondicoesUsinagem().setAp(ap);
		    			else workingstep.getCondicoesUsinagem().setAp(workingstep.getFerramenta().getCuttingEdgeLength()*0.75);
			    	}
			    	
		    //	}
			    	System.out.println("Face "+workingstep.getFace().getComprimento());
			    	
			    	workingstep.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(workingstep));
					System.out.println("PONTO INICIAL : " + workingstep.getPontos()[0]);
					Vector movimentacoes = DeterminarMovimentacao.getPontosMovimentacao(workingstep);
					System.out.println("PONTOS MOVIMENTAÇAOOOOOOOOOOOOOOOOOOOO2: " + movimentacoes);

					Vector movimentacao = (Vector)movimentacoes.get(0);
					workingstep.setPontosMovimentacao(movimentacao);
			    System.out.println("mov: "+movimentacao);
					
					for(int i = 0; i < movimentacao.size()-1; i++){
					
					Ponto p1 = (Ponto)movimentacao.elementAt(i);
					Ponto p2 = (Ponto)movimentacao.elementAt(i+1);
					comprimento += Math.sqrt(Math.pow(p2.getX()-p1.getX(), 2) + Math.pow(p2.getY()-p1.getY(), 2) + Math.pow(p2.getZ()-p1.getZ(), 2));
				}
			
				System.out.println("ap = "+ap+"\t L = "+comprimento+"\tVf "+Vf);
				
				T = comprimento/Vf;
			
			
		}else if(machine.getClass()== MillingMachine.class &&(this.workingstep.getOperation().getClass() == BottomAndSideRoughMilling.class ||
				this.workingstep.getOperation().getClass() == BottomAndSideFinishMilling.class) && 
				(this.workingstep.getFeature().getClass() == Cavidade.class))
		{
				
	    
		   	workingstep.getCondicoesUsinagem().setAp(workingstep.getFerramenta().getCuttingEdgeLength()*0.75);
    		workingstep.getCondicoesUsinagem().setAe(D*0.75);
    		
    				n = Vc * 1000 /(Math.PI * D);
					P_max = (P/RPM)*n;
					Vf = n * fn * nd;
					Hm = (fn*ae*360)/(D*Math.PI*180 / Math.PI * Math.acos(1-(2*ae/D)));
					Kc = Kc1*Math.pow(Hm, -Z);
					Kc = Kc/100;
			    	Tc = (ap*ae*Vf*Kc)/(2*Math.PI*n);
			 		T_max = ((-Tm/RPM)*n)+Tm; 
			    	System.out.println("Torque "+Tc);
			 		System.out.println("n "+n);
			 		
			 	
					if(Tc > T_max){
			    		
			    		System.out.println("Entrou 1");
			    		double Vf_max = (Tm*2*Math.PI*n)/(ap*ae*Kc);
			    		System.out.println("Vf max "+Vf_max+" Vf: "+Vf);
			    		
			    		if(Vf_max >= Vf*0.42 && Vf_max <= Vf*1.57){
			    			System.out.println("Entrou 2");
			    			Vf = Vf_max; 
			    			n = Vf/fn*nd;
			    			System.out.println("n2 "+n);
			    		
			    		}else{
			    			
			    			System.out.println("Entrou 3");
			    			ap_max = (Tm*2*Math.PI*n)/(ae*Vf*Kc);
			    			System.out.println("ap max "+ap_max);
			    		
			    			workingstep.getCondicoesUsinagem().setAp(ap_max);
			    		}
			    	}
					
			    	if(n > RPM){
			    		System.out.println("Entrou 4 ");
			    		n = RPM;
			    		P_max = (P/RPM)*n;
			    		Vf = n * fn * nd;
			    	}
			    	
			    	P_teste = (ap*ae*Vf*Kc)/(60*102*9.81);
			    	
			    	if(P_teste > P){
			    		
			    		System.out.println("Entrou 5");
			    		ap = (P*60*102*9.81)/(ae*Kc*Vf);
			    		
			    		if(ap <= workingstep.getFerramenta().getCuttingEdgeLength()*0.75) workingstep.getCondicoesUsinagem().setAp(ap);
		    			else workingstep.getCondicoesUsinagem().setAp(workingstep.getFerramenta().getCuttingEdgeLength()*0.75);
			    	}
			    	
			    	
			    	
				workingstep.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(workingstep));
				Vector movimentacao = (Vector)(DeterminarMovimentacao.getPontosMovimentacao(workingstep)).elementAt(0);
				
				for(int i = 0; i < movimentacao.size()-1; i++){
					
					Ponto p1 = (Ponto)movimentacao.elementAt(i);
					Ponto p2 = (Ponto)movimentacao.elementAt(i+1);
					comprimento += Math.sqrt(Math.pow(p2.getX()-p1.getX(), 2) + Math.pow(p2.getY()-p1.getY(), 2) + Math.pow(p2.getZ()-p1.getZ(), 2));
				}
			
				System.out.println("ap = "+ap+"\t L = "+comprimento+"\tVf "+Vf);
				T = comprimento/Vf;
	
		}else if((this.workingstep.getOperation().getClass() == BottomAndSideRoughMilling.class ||
				this.workingstep.getOperation().getClass() == BottomAndSideFinishMilling.class) && 
				this.workingstep.getFeature().getClass() == Degrau.class)
		{	
			n = Vc*1000/(Math.PI*D);
			Vf = n*fn*nd;
			Hm = (fn*ae*360)/(D*Math.PI*180 / Math.PI *Math.acos(1-(2*ae/D)));
			Kc = Kc1*Math.pow(Hm, -Z);
			ap_max = (P*60*Math.pow(10, 6))/(ae*Vf*Kc);
			if (ap_max < ap){
				
				VolumeR = prof*larg*comprimento;  // Volume da ranhura
				VolumePP = ap_max*ae*comprimento; // Volume tirado em cada passagem
				np = VolumeR/VolumePP; // Numero de passagens da fresa
				np = Math.floor(np);
			
				T = comprimento/Vf;
				T = np*T;
			}else{
				
				VolumeR = prof*larg*comprimento;  // Volume da ranhura
				VolumePP = ap*ae*comprimento; // Volume tirado em cada passagem
				np = VolumeR/VolumePP; // Numero de passagens da fresa
				np = Math.floor(np);
			
				T = comprimento/Vf;
				T = np*T;
			}
			
		}else if((this.workingstep.getOperation().getClass() == BottomAndSideRoughMilling.class ||
				this.workingstep.getOperation().getClass() == BottomAndSideFinishMilling.class) && 
				this.workingstep.getFeature().getClass() == FuroBasePlana.class ){
			
			workingstep.getCondicoesUsinagem().setAp(workingstep.getFerramenta().getCuttingEdgeLength()*0.75);
			workingstep.getCondicoesUsinagem().setAe(D*0.75);
		
			n = Vc * 1000 /(Math.PI * D);
			P_max = (P/RPM)*n;
			Vf = n * fn * nd;
			Hm = (fn*ae*360)/(D*Math.PI*180 / Math.PI * Math.acos(1-(2*ae/D)));
			Kc = Kc1*Math.pow(Hm, -Z);
			Kc = Kc/100;
	    	Tc = (ap*ae*Vf*Kc)/(2*Math.PI*n);
	 		T_max = ((-Tm/RPM)*n)+Tm; 
	    	System.out.println("Torque "+Tc);
	 		System.out.println("n "+n);
	 		
	 		System.out.println("Kc : "+Kc);
			
	    	if(Tc > T_max){
	    		
	    		System.out.println("Entrou 1");
	    		double Vf_max = (Tm*2*Math.PI*n)/(ap*ae*Kc);
	    		System.out.println("Vf max "+Vf_max+" Vf: "+Vf);
	    		
	    		if(Vf_max >= Vf*0.42 && Vf_max <= Vf*1.57){
	    			System.out.println("Entrou 2");
	    			Vf = Vf_max; 
	    			n = Vf/fn*nd;
	    			System.out.println("n2 "+n);
	    		
	    		}else{
	    			
	    			System.out.println("Entrou 3");
	    			ap_max = (Tm*2*Math.PI*n)/(ae*Vf*Kc);
	    			System.out.println("ap max "+ap_max);
	    		
	    			workingstep.getCondicoesUsinagem().setAp(ap_max);
	    		}
	    	}
	    	if(n > RPM){
	    		System.out.println("Entrou 4 ");
	    		n = RPM;
	    		P_max = (P/RPM)*n;
	    		Vf = n * fn * nd;
	    	}
	    	P_teste = (ap*ae*Vf*Kc)/(60*102*9.81);
	    	System.out.println("Potencia teste: "+P_teste+" ap: "+ap+" Vf"+Vf);
	    	
	    	if(P_teste > P){
	    		
	    		System.out.println("P: "+P);
	    		System.out.println("Entrou 5");
	    		ap = (P*60*102*9.81)/(ae*Kc*Vf);
	    		
	    		if(ap <= workingstep.getFerramenta().getCuttingEdgeLength()*0.75) workingstep.getCondicoesUsinagem().setAp(ap);
    			else workingstep.getCondicoesUsinagem().setAp(workingstep.getFerramenta().getCuttingEdgeLength()*0.75);
	    	}
			
			workingstep.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(workingstep));
			Vector movimentacao = (Vector)(DeterminarMovimentacao.getPontosMovimentacao(workingstep)).elementAt(0);
			
			
			for(int i = 0; i < movimentacao.size()-1; i++){
				
				Ponto p1 = (Ponto)movimentacao.elementAt(i);
				Ponto p2 = (Ponto)movimentacao.elementAt(i+1);
				comprimento += Math.sqrt(Math.pow(p2.getX()-p1.getX(), 2) + Math.pow(p2.getY()-p1.getY(), 2) + Math.pow(p2.getZ()-p1.getZ(), 2));
			}
		
			T = comprimento/Vf;
			
		}else if(workingstep.getFerramenta().getClass()== BullnoseEndMill.class &&(this.workingstep.getOperation().getClass() == BottomAndSideRoughMilling.class ||
				this.workingstep.getOperation().getClass() == BottomAndSideFinishMilling.class) && 
				this.workingstep.getFeature().getClass() == FuroBaseArredondada.class ){
			
			workingstep.getCondicoesUsinagem().setAp(workingstep.getFerramenta().getCuttingEdgeLength()*0.75);
			workingstep.getCondicoesUsinagem().setAe(D*0.75);
		
			n = Vc * 1000 /(Math.PI * D);
			P_max = (P/RPM)*n;
			Vf = n * fn * nd;
			Hm = (fn*ae*360)/(D*Math.PI*180 / Math.PI * Math.acos(1-(2*ae/D)));
			Kc = Kc1*Math.pow(Hm, -Z);
			Kc = Kc/100;
	    	Tc = (ap*ae*Vf*Kc)/(2*Math.PI*n);
	 		T_max = ((-Tm/RPM)*n)+Tm; 
	    	System.out.println("Torque "+Tc);
	 		System.out.println("n "+n);
	 		
	 		System.out.println("Kc : "+Kc);
			
	    	if(Tc > T_max){
	    		
	    		System.out.println("Entrou 1");
	    		double Vf_max = (Tm*2*Math.PI*n)/(ap*ae*Kc);
	    		System.out.println("Vf max "+Vf_max+" Vf: "+Vf);
	    		
	    		if(Vf_max >= Vf*0.42 && Vf_max <= Vf*1.57){
	    			System.out.println("Entrou 2");
	    			Vf = Vf_max; 
	    			n = Vf/fn*nd;
	    			System.out.println("n2 "+n);
	    		
	    		}else{
	    			
	    			System.out.println("Entrou 3");
	    			ap_max = (Tm*2*Math.PI*n)/(ae*Vf*Kc);
	    			System.out.println("ap max "+ap_max);
	    		
	    			workingstep.getCondicoesUsinagem().setAp(ap_max);
	    		}
	    	}
	    	if(n > RPM){
	    		System.out.println("Entrou 4 ");
	    		n = RPM;
	    		P_max = (P/RPM)*n;
	    		Vf = n * fn * nd;
	    	}
	    	P_teste = (ap*ae*Vf*Kc)/(60*102*9.81);
	    	System.out.println("Potencia teste: "+P_teste+" ap: "+ap+" Vf"+Vf);
	    	
	    	if(P_teste > P){
	    		
	    		System.out.println("P: "+P);
	    		System.out.println("Entrou 5");
	    		ap = (P*60*102*9.81)/(ae*Kc*Vf);
	    		
	    		if(ap <= workingstep.getFerramenta().getCuttingEdgeLength()*0.75) workingstep.getCondicoesUsinagem().setAp(ap);
    			else workingstep.getCondicoesUsinagem().setAp(workingstep.getFerramenta().getCuttingEdgeLength()*0.75);
	    	}
			
			Vector movimentacao = new Vector();
			MovimentacaoFuroBaseArredondada mov = new MovimentacaoFuroBaseArredondada(workingstep);
			Vector <Point3d> path = MovimentacaoFuroBaseArredondada.transformCircularPathInPoints3d(mov.operacaoComBullnoseEndMill());
			for(Point3d pointTmp: path)
			{
				Ponto ponto = new Ponto(pointTmp.x, pointTmp.y, -pointTmp.z);
				movimentacao.add(ponto);
			}
			System.out.println("SIZE = "+movimentacao);
		
		
			for(int i = 0; i < movimentacao.size()-1; i++){
				
				Ponto p1 = (Ponto)movimentacao.elementAt(i);
				Ponto p2 = (Ponto)movimentacao.elementAt(i+1);
				comprimento += Math.sqrt(Math.pow(p2.getX()-p1.getX(), 2) + Math.pow(p2.getY()-p1.getY(), 2) + Math.pow(p2.getZ()-p1.getZ(), 2));
			}
		
			T = comprimento/Vf;
			
		}else if((this.workingstep.getOperation().getClass() == BottomAndSideRoughMilling.class ||
				this.workingstep.getOperation().getClass() == BottomAndSideFinishMilling.class) && 
				this.workingstep.getFeature().getClass() == GeneralClosedPocket.class ){
			
			workingstep.getCondicoesUsinagem().setAp(workingstep.getFerramenta().getCuttingEdgeLength()*0.75);
			workingstep.getCondicoesUsinagem().setAe(D*0.75);
		
			n = Vc * 1000 /(Math.PI * D);
			P_max = (P/RPM)*n;
			Vf = n * fn * nd;
			Hm = (fn*ae*360)/(D*Math.PI*180 / Math.PI * Math.acos(1-(2*ae/D)));
			Kc = Kc1*Math.pow(Hm, -Z);
			Kc = Kc/100;
	    	Tc = (ap*ae*Vf*Kc)/(2*Math.PI*n);
	 		T_max = ((-Tm/RPM)*n)+Tm; 
	    	System.out.println("Torque "+Tc);
	 		System.out.println("n "+n);
	 		
	 		System.out.println("Kc : "+Kc);
			
	    	if(Tc > T_max){
	    		
	    		System.out.println("Entrou 1");
	    		double Vf_max = (Tm*2*Math.PI*n)/(ap*ae*Kc);
	    		System.out.println("Vf max "+Vf_max+" Vf: "+Vf);
	    		
	    		if(Vf_max >= Vf*0.42 && Vf_max <= Vf*1.57){
	    			System.out.println("Entrou 2");
	    			Vf = Vf_max; 
	    			n = Vf/fn*nd;
	    			System.out.println("n2 "+n);
	    		
	    		}else{
	    			
	    			System.out.println("Entrou 3");
	    			ap_max = (Tm*2*Math.PI*n)/(ae*Vf*Kc);
	    			System.out.println("ap max "+ap_max);
	    		
	    			workingstep.getCondicoesUsinagem().setAp(ap_max);
	    		}
	    	}
	    	if(n > RPM){
	    		System.out.println("Entrou 4 ");
	    		n = RPM;
	    		P_max = (P/RPM)*n;
	    		Vf = n * fn * nd;
	    	}
	    	P_teste = (ap*ae*Vf*Kc)/(60*102*9.81);
	    	System.out.println("Potencia teste: "+P_teste+" ap: "+ap+" Vf"+Vf);
	    	
	    	if(P_teste > P){
	    		
	    		System.out.println("P: "+P);
	    		System.out.println("Entrou 5");
	    		ap = (P*60*102*9.81)/(ae*Kc*Vf);
	    		
	    		if(ap <= workingstep.getFerramenta().getCuttingEdgeLength()*0.75) workingstep.getCondicoesUsinagem().setAp(ap);
    			else workingstep.getCondicoesUsinagem().setAp(workingstep.getFerramenta().getCuttingEdgeLength()*0.75);
	    	}
			
			MovimentacaoGeneralClosedPocket detMov = new MovimentacaoGeneralClosedPocket(workingstep);
			ArrayList<LinearPath> path = detMov.getDesbaste();
			Vector movimentacao = new Vector();
			 for(int j = 0; j < path.size(); j++)
				{
					double xAux = path.get(j).getFinalPoint().getX();
					double yAux = path.get(j).getFinalPoint().getY();
					double zAux = -path.get(j).getFinalPoint().getZ();
					
					movimentacao.add(new Ponto(xAux, yAux, zAux));
				}
			 for(int i = 0; i < movimentacao.size()-1; i++){
					
					Ponto p1 = (Ponto)movimentacao.elementAt(i);
					Ponto p2 = (Ponto)movimentacao.elementAt(i+1);
					comprimento += Math.sqrt(Math.pow(p2.getX()-p1.getX(), 2) + Math.pow(p2.getY()-p1.getY(), 2) + Math.pow(p2.getZ()-p1.getZ(), 2));
				}
			
				T = comprimento/Vf;
		}else if((this.workingstep.getOperation().getClass() == BottomAndSideRoughMilling.class ||
				this.workingstep.getOperation().getClass() == BottomAndSideFinishMilling.class) && 
				this.workingstep.getFeature().getClass() == RanhuraPerfilQuadradoU.class && 
				workingstep.getFerramenta().getClass()== FaceMill.class  ){
			

			workingstep.getCondicoesUsinagem().setAp(workingstep.getFerramenta().getCuttingEdgeLength()*0.75);
			workingstep.getCondicoesUsinagem().setAe(D*0.75);
		
			n = Vc * 1000 /(Math.PI * D);
			P_max = (P/RPM)*n;
			Vf = n * fn * nd;
			Hm = (fn*ae*360)/(D*Math.PI*180 / Math.PI * Math.acos(1-(2*ae/D)));
			Kc = Kc1*Math.pow(Hm, -Z);
			Kc = Kc/100;
	    	Tc = (ap*ae*Vf*Kc)/(2*Math.PI*n);
	 		T_max = ((-Tm/RPM)*n)+Tm; 
	    	System.out.println("Torque "+Tc);
	 		System.out.println("n "+n);
	 		
	 		System.out.println("Kc : "+Kc);
			
	    	if(Tc > T_max){
	    		
	    		System.out.println("Entrou 1");
	    		double Vf_max = (Tm*2*Math.PI*n)/(ap*ae*Kc);
	    		System.out.println("Vf max "+Vf_max+" Vf: "+Vf);
	    		
	    		if(Vf_max >= Vf*0.42 && Vf_max <= Vf*1.57){
	    			System.out.println("Entrou 2");
	    			Vf = Vf_max; 
	    			n = Vf/fn*nd;
	    			System.out.println("n2 "+n);
	    		
	    		}else{
	    			
	    			System.out.println("Entrou 3");
	    			ap_max = (Tm*2*Math.PI*n)/(ae*Vf*Kc);
	    			System.out.println("ap max "+ap_max);
	    		
	    			workingstep.getCondicoesUsinagem().setAp(ap_max);
	    		}
	    	}
	    	if(n > RPM){
	    		System.out.println("Entrou 4 ");
	    		n = RPM;
	    		P_max = (P/RPM)*n;
	    		Vf = n * fn * nd;
	    	}
	    	P_teste = (ap*ae*Vf*Kc)/(60*102*9.81);
	    	System.out.println("Potencia teste: "+P_teste+" ap: "+ap+" Vf"+Vf);
	    	
	    	if(P_teste > P){
	    		
	    		System.out.println("P: "+P);
	    		System.out.println("Entrou 5");
	    		ap = (P*60*102*9.81)/(ae*Kc*Vf);
	    		
	    		if(ap <= workingstep.getFerramenta().getCuttingEdgeLength()*0.75) workingstep.getCondicoesUsinagem().setAp(ap);
    			else workingstep.getCondicoesUsinagem().setAp(workingstep.getFerramenta().getCuttingEdgeLength()*0.75);
	    	}
			
			workingstep.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(workingstep));
			Vector movimentacao = new Vector();
			MovimentacaoRanhuraPerfilQuadradoU detMov = new MovimentacaoRanhuraPerfilQuadradoU(workingstep);
			ArrayList<LinearPath> path = detMov.getMovimentacaoDesbasteRanhuraPerfilQuadradoU();
			
			 for(int j = 0; j < path.size(); j++)
				{
					double xAux = path.get(j).getFinalPoint().getX();
					double yAux = path.get(j).getFinalPoint().getY();
					double zAux = -path.get(j).getFinalPoint().getZ();
					
					movimentacao.add(new Ponto(xAux, yAux, zAux));
				}
			 for(int i = 0; i < movimentacao.size()-1; i++){
					
					Ponto p1 = (Ponto)movimentacao.elementAt(i);
					Ponto p2 = (Ponto)movimentacao.elementAt(i+1);
					comprimento += Math.sqrt(Math.pow(p2.getX()-p1.getX(), 2) + Math.pow(p2.getY()-p1.getY(), 2) + Math.pow(p2.getZ()-p1.getZ(), 2));
				}
			
				T = comprimento/Vf;
			
			
		}else if((this.workingstep.getOperation().getClass() == BottomAndSideRoughMilling.class ||
				this.workingstep.getOperation().getClass() == BottomAndSideFinishMilling.class) && 
				this.workingstep.getFeature().getClass() == RanhuraPerfilQuadradoU.class && 
				(workingstep.getFerramenta().getClass()== BullnoseEndMill.class || workingstep.getFerramenta().getClass()== BallEndMill.class )
				){
			

			workingstep.getCondicoesUsinagem().setAp(workingstep.getFerramenta().getCuttingEdgeLength()*0.75);
			workingstep.getCondicoesUsinagem().setAe(D*0.75);
		
			n = Vc * 1000 /(Math.PI * D);
			P_max = (P/RPM)*n;
			Vf = n * fn * nd;
			Hm = (fn*ae*360)/(D*Math.PI*180 / Math.PI * Math.acos(1-(2*ae/D)));
			Kc = Kc1*Math.pow(Hm, -Z);
			Kc = Kc/100;
	    	Tc = (ap*ae*Vf*Kc)/(2*Math.PI*n);
	 		T_max = ((-Tm/RPM)*n)+Tm; 
	    	System.out.println("Torque "+Tc);
	 		System.out.println("n "+n);
	 		
	 		System.out.println("Kc : "+Kc);
			
	    	if(Tc > T_max){
	    		
	    		System.out.println("Entrou 1");
	    		double Vf_max = (Tm*2*Math.PI*n)/(ap*ae*Kc);
	    		System.out.println("Vf max "+Vf_max+" Vf: "+Vf);
	    		
	    		if(Vf_max >= Vf*0.42 && Vf_max <= Vf*1.57){
	    			System.out.println("Entrou 2");
	    			Vf = Vf_max; 
	    			n = Vf/fn*nd;
	    			System.out.println("n2 "+n);
	    		
	    		}else{
	    			
	    			System.out.println("Entrou 3");
	    			ap_max = (Tm*2*Math.PI*n)/(ae*Vf*Kc);
	    			System.out.println("ap max "+ap_max);
	    		
	    			workingstep.getCondicoesUsinagem().setAp(ap_max);
	    		}
	    	}
	    	if(n > RPM){
	    		System.out.println("Entrou 4 ");
	    		n = RPM;
	    		P_max = (P/RPM)*n;
	    		Vf = n * fn * nd;
	    	}
	    	P_teste = (ap*ae*Vf*Kc)/(60*102*9.81);
	    	System.out.println("Potencia teste: "+P_teste+" ap: "+ap+" Vf"+Vf);
	    	
	    	if(P_teste > P){
	    		
	    		System.out.println("P: "+P);
	    		System.out.println("Entrou 5");
	    		ap = (P*60*102*9.81)/(ae*Kc*Vf);
	    		
	    		if(ap <= workingstep.getFerramenta().getCuttingEdgeLength()*0.75) workingstep.getCondicoesUsinagem().setAp(ap);
    			else workingstep.getCondicoesUsinagem().setAp(workingstep.getFerramenta().getCuttingEdgeLength()*0.75);
	    	}
			
			workingstep.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(workingstep));
			Vector movimentacao = new Vector();
			MovimentacaoRanhuraPerfilQuadradoU detMov = new MovimentacaoRanhuraPerfilQuadradoU(workingstep);
			ArrayList<LinearPath> path = detMov.getMovimentacaoAcabamentoRanhuraPerfilQuadradoU();
			
			 for(int j = 0; j < path.size(); j++)
				{
					double xAux = path.get(j).getFinalPoint().getX();
					double yAux = path.get(j).getFinalPoint().getY();
					double zAux = -path.get(j).getFinalPoint().getZ();
					
					movimentacao.add(new Ponto(xAux, yAux, zAux));
				}
			 for(int i = 0; i < movimentacao.size()-1; i++){
					
					Ponto p1 = (Ponto)movimentacao.elementAt(i);
					Ponto p2 = (Ponto)movimentacao.elementAt(i+1);
					comprimento += Math.sqrt(Math.pow(p2.getX()-p1.getX(), 2) + Math.pow(p2.getY()-p1.getY(), 2) + Math.pow(p2.getZ()-p1.getZ(), 2));
				}
			
				T = comprimento/Vf;
			
			
		}else if((this.workingstep.getOperation().getClass() == BottomAndSideRoughMilling.class ||
				this.workingstep.getOperation().getClass() == BottomAndSideFinishMilling.class) && 
				this.workingstep.getFeature().getClass() == RanhuraPerfilCircularParcial.class && 
				workingstep.getFerramenta().getClass()== FaceMill.class  ){
			
			workingstep.getCondicoesUsinagem().setAp(workingstep.getFerramenta().getCuttingEdgeLength()*0.75);
			workingstep.getCondicoesUsinagem().setAe(D*0.75);
		
			n = Vc * 1000 /(Math.PI * D);
			P_max = (P/RPM)*n;
			Vf = n * fn * nd;
			Hm = (fn*ae*360)/(D*Math.PI*180 / Math.PI * Math.acos(1-(2*ae/D)));
			Kc = Kc1*Math.pow(Hm, -Z);
			Kc = Kc/100;
	    	Tc = (ap*ae*Vf*Kc)/(2*Math.PI*n);
	 		T_max = ((-Tm/RPM)*n)+Tm; 
	    	System.out.println("Torque "+Tc);
	 		System.out.println("n "+n);
	 		
	 		System.out.println("Kc : "+Kc);
			
	    	if(Tc > T_max){
	    		
	    		System.out.println("Entrou 1");
	    		double Vf_max = (Tm*2*Math.PI*n)/(ap*ae*Kc);
	    		System.out.println("Vf max "+Vf_max+" Vf: "+Vf);
	    		
	    		if(Vf_max >= Vf*0.42 && Vf_max <= Vf*1.57){
	    			System.out.println("Entrou 2");
	    			Vf = Vf_max; 
	    			n = Vf/fn*nd;
	    			System.out.println("n2 "+n);
	    		
	    		}else{
	    			
	    			System.out.println("Entrou 3");
	    			ap_max = (Tm*2*Math.PI*n)/(ae*Vf*Kc);
	    			System.out.println("ap max "+ap_max);
	    		
	    			workingstep.getCondicoesUsinagem().setAp(ap_max);
	    		}
	    	}
	    	if(n > RPM){
	    		System.out.println("Entrou 4 ");
	    		n = RPM;
	    		P_max = (P/RPM)*n;
	    		Vf = n * fn * nd;
	    	}
	    	P_teste = (ap*ae*Vf*Kc)/(60*102*9.81);
	    	System.out.println("Potencia teste: "+P_teste+" ap: "+ap+" Vf"+Vf);
	    	
	    	if(P_teste > P){
	    		
	    		System.out.println("P: "+P);
	    		System.out.println("Entrou 5");
	    		ap = (P*60*102*9.81)/(ae*Kc*Vf);
	    		
	    		if(ap <= workingstep.getFerramenta().getCuttingEdgeLength()*0.75) workingstep.getCondicoesUsinagem().setAp(ap);
    			else workingstep.getCondicoesUsinagem().setAp(workingstep.getFerramenta().getCuttingEdgeLength()*0.75);
	    	}
			
			workingstep.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(workingstep));
			Vector movimentacao = new Vector();
			MovimentacaoRanhuraPerfilParcialCircular detMov = new MovimentacaoRanhuraPerfilParcialCircular(workingstep, workingstep.getFace());
			ArrayList<LinearPath> path = detMov.getMovimentacaoDesbasteRanhuraPerfilCircularParcial();
			
			 for(int j = 0; j < path.size(); j++)
				{
					double xAux = path.get(j).getFinalPoint().getX();
					double yAux = path.get(j).getFinalPoint().getY();
					double zAux = -path.get(j).getFinalPoint().getZ();
					
					movimentacao.add(new Ponto(xAux, yAux, zAux));
				}
			 for(int i = 0; i < movimentacao.size()-1; i++){
					
					Ponto p1 = (Ponto)movimentacao.elementAt(i);
					Ponto p2 = (Ponto)movimentacao.elementAt(i+1);
					comprimento += Math.sqrt(Math.pow(p2.getX()-p1.getX(), 2) + Math.pow(p2.getY()-p1.getY(), 2) + Math.pow(p2.getZ()-p1.getZ(), 2));
				}
			
				T = comprimento/Vf;
			
		}else if((this.workingstep.getOperation().getClass() == BottomAndSideRoughMilling.class ||
				this.workingstep.getOperation().getClass() == BottomAndSideFinishMilling.class) && 
				this.workingstep.getFeature().getClass() == RanhuraPerfilCircularParcial.class && 
				workingstep.getFerramenta().getClass()== BallEndMill.class ){
			
			workingstep.getCondicoesUsinagem().setAp(workingstep.getFerramenta().getCuttingEdgeLength()*0.75);
			workingstep.getCondicoesUsinagem().setAe(D*0.75);
		
			n = Vc * 1000 /(Math.PI * D);
			P_max = (P/RPM)*n;
			Vf = n * fn * nd;
			Hm = (fn*ae*360)/(D*Math.PI*180 / Math.PI * Math.acos(1-(2*ae/D)));
			Kc = Kc1*Math.pow(Hm, -Z);
			Kc = Kc/100;
	    	Tc = (ap*ae*Vf*Kc)/(2*Math.PI*n);
	 		T_max = ((-Tm/RPM)*n)+Tm; 
	    	System.out.println("Torque "+Tc);
	 		System.out.println("n "+n);
	 		
	 		System.out.println("Kc : "+Kc);
			
	    	if(Tc > T_max){
	    		
	    		System.out.println("Entrou 1");
	    		double Vf_max = (Tm*2*Math.PI*n)/(ap*ae*Kc);
	    		System.out.println("Vf max "+Vf_max+" Vf: "+Vf);
	    		
	    		if(Vf_max >= Vf*0.42 && Vf_max <= Vf*1.57){
	    			System.out.println("Entrou 2");
	    			Vf = Vf_max; 
	    			n = Vf/fn*nd;
	    			System.out.println("n2 "+n);
	    		
	    		}else{
	    			
	    			System.out.println("Entrou 3");
	    			ap_max = (Tm*2*Math.PI*n)/(ae*Vf*Kc);
	    			System.out.println("ap max "+ap_max);
	    		
	    			workingstep.getCondicoesUsinagem().setAp(ap_max);
	    		}
	    	}
	    	if(n > RPM){
	    		System.out.println("Entrou 4 ");
	    		n = RPM;
	    		P_max = (P/RPM)*n;
	    		Vf = n * fn * nd;
	    	}
	    	P_teste = (ap*ae*Vf*Kc)/(60*102*9.81);
	    	System.out.println("Potencia teste: "+P_teste+" ap: "+ap+" Vf"+Vf);
	    	
	    	if(P_teste > P){
	    		
	    		System.out.println("P: "+P);
	    		System.out.println("Entrou 5");
	    		ap = (P*60*102*9.81)/(ae*Kc*Vf);
	    		
	    		if(ap <= workingstep.getFerramenta().getCuttingEdgeLength()*0.75) workingstep.getCondicoesUsinagem().setAp(ap);
    			else workingstep.getCondicoesUsinagem().setAp(workingstep.getFerramenta().getCuttingEdgeLength()*0.75);
	    	}
			workingstep.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(workingstep));
			Vector movimentacao = new Vector();
			MovimentacaoRanhuraPerfilParcialCircular detMov = new MovimentacaoRanhuraPerfilParcialCircular(workingstep, workingstep.getFace());
			ArrayList<LinearPath> path = detMov.getMovimentacaoAcabamentoRanhuraPerfilCircularParcial();
			
			 for(int j = 0; j < path.size(); j++)
				{
					double xAux = path.get(j).getFinalPoint().getX();
					double yAux = path.get(j).getFinalPoint().getY();
					double zAux = -path.get(j).getFinalPoint().getZ();
					
					movimentacao.add(new Ponto(xAux, yAux, zAux));
				}
			 for(int i = 0; i < movimentacao.size()-1; i++){
					
					Ponto p1 = (Ponto)movimentacao.elementAt(i);
					Ponto p2 = (Ponto)movimentacao.elementAt(i+1);
					comprimento += Math.sqrt(Math.pow(p2.getX()-p1.getX(), 2) + Math.pow(p2.getY()-p1.getY(), 2) + Math.pow(p2.getZ()-p1.getZ(), 2));
				}
			
				T = comprimento/Vf;
		}else if((this.workingstep.getOperation().getClass() == BottomAndSideRoughMilling.class ||
				this.workingstep.getOperation().getClass() == BottomAndSideFinishMilling.class) && 
				this.workingstep.getFeature().getClass() == RanhuraPerfilVee.class && 
				workingstep.getFerramenta().getClass()== FaceMill.class  ){
			
			workingstep.getCondicoesUsinagem().setAp(workingstep.getFerramenta().getCuttingEdgeLength()*0.75);
			workingstep.getCondicoesUsinagem().setAe(D*0.75);
		
			n = Vc * 1000 /(Math.PI * D);
			P_max = (P/RPM)*n;
			Vf = n * fn * nd;
			Hm = (fn*ae*360)/(D*Math.PI*180 / Math.PI * Math.acos(1-(2*ae/D)));
			Kc = Kc1*Math.pow(Hm, -Z);
			Kc = Kc/100;
	    	Tc = (ap*ae*Vf*Kc)/(2*Math.PI*n);
	 		T_max = ((-Tm/RPM)*n)+Tm; 
	    	System.out.println("Torque "+Tc);
	 		System.out.println("n "+n);
	 		
	 		System.out.println("Kc : "+Kc);
			
	    	if(Tc > T_max){
	    		
	    		System.out.println("Entrou 1");
	    		double Vf_max = (Tm*2*Math.PI*n)/(ap*ae*Kc);
	    		System.out.println("Vf max "+Vf_max+" Vf: "+Vf);
	    		
	    		if(Vf_max >= Vf*0.42 && Vf_max <= Vf*1.57){
	    			System.out.println("Entrou 2");
	    			Vf = Vf_max; 
	    			n = Vf/fn*nd;
	    			System.out.println("n2 "+n);
	    		
	    		}else{
	    			
	    			System.out.println("Entrou 3");
	    			ap_max = (Tm*2*Math.PI*n)/(ae*Vf*Kc);
	    			System.out.println("ap max "+ap_max);
	    		
	    			workingstep.getCondicoesUsinagem().setAp(ap_max);
	    		}
	    	}
	    	if(n > RPM){
	    		System.out.println("Entrou 4 ");
	    		n = RPM;
	    		P_max = (P/RPM)*n;
	    		Vf = n * fn * nd;
	    	}
	    	P_teste = (ap*ae*Vf*Kc)/(60*102*9.81);
	    	System.out.println("Potencia teste: "+P_teste+" ap: "+ap+" Vf"+Vf);
	    	
	    	if(P_teste > P){
	    		
	    		System.out.println("P: "+P);
	    		System.out.println("Entrou 5");
	    		ap = (P*60*102*9.81)/(ae*Kc*Vf);
	    		
	    		if(ap <= workingstep.getFerramenta().getCuttingEdgeLength()*0.75) workingstep.getCondicoesUsinagem().setAp(ap);
    			else workingstep.getCondicoesUsinagem().setAp(workingstep.getFerramenta().getCuttingEdgeLength()*0.75);
	    	};
			
			workingstep.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(workingstep));
			Vector movimentacao = new Vector();
			MovimentacaoRanhuraPerfilVee detMov = new MovimentacaoRanhuraPerfilVee(workingstep);
			ArrayList<LinearPath> path = detMov.getMovimentacaoDesbasteRanhuraPerfilVee();
			
			 for(int j = 0; j < path.size(); j++)
				{
					double xAux = path.get(j).getFinalPoint().getX();
					double yAux = path.get(j).getFinalPoint().getY();
					double zAux = -path.get(j).getFinalPoint().getZ();
					
					movimentacao.add(new Ponto(xAux, yAux, zAux));
				}
			 for(int i = 0; i < movimentacao.size()-1; i++){
					
					Ponto p1 = (Ponto)movimentacao.elementAt(i);
					Ponto p2 = (Ponto)movimentacao.elementAt(i+1);
					comprimento += Math.sqrt(Math.pow(p2.getX()-p1.getX(), 2) + Math.pow(p2.getY()-p1.getY(), 2) + Math.pow(p2.getZ()-p1.getZ(), 2));
				}
			
				T = comprimento/Vf;
		}else if((this.workingstep.getOperation().getClass() == BottomAndSideRoughMilling.class ||
				this.workingstep.getOperation().getClass() == BottomAndSideFinishMilling.class) && 
				this.workingstep.getFeature().getClass() == RanhuraPerfilVee.class && 
				workingstep.getFerramenta().getClass()== BallEndMill.class  ){
			
			workingstep.getCondicoesUsinagem().setAp(workingstep.getFerramenta().getCuttingEdgeLength()*0.75);
			workingstep.getCondicoesUsinagem().setAe(D*0.75);
		
			n = Vc * 1000 /(Math.PI * D);
			P_max = (P/RPM)*n;
			Vf = n * fn * nd;
			Hm = (fn*ae*360)/(D*Math.PI*180 / Math.PI * Math.acos(1-(2*ae/D)));
			Kc = Kc1*Math.pow(Hm, -Z);
			Kc = Kc/100;
	    	Tc = (ap*ae*Vf*Kc)/(2*Math.PI*n);
	 		T_max = ((-Tm/RPM)*n)+Tm; 
	    	System.out.println("Torque "+Tc);
	 		System.out.println("n "+n);
	 		
	 		System.out.println("Kc : "+Kc);
			
	    	if(Tc > T_max){
	    		
	    		System.out.println("Entrou 1");
	    		double Vf_max = (Tm*2*Math.PI*n)/(ap*ae*Kc);
	    		System.out.println("Vf max "+Vf_max+" Vf: "+Vf);
	    		
	    		if(Vf_max >= Vf*0.42 && Vf_max <= Vf*1.57){
	    			System.out.println("Entrou 2");
	    			Vf = Vf_max; 
	    			n = Vf/fn*nd;
	    			System.out.println("n2 "+n);
	    		
	    		}else{
	    			
	    			System.out.println("Entrou 3");
	    			ap_max = (Tm*2*Math.PI*n)/(ae*Vf*Kc);
	    			System.out.println("ap max "+ap_max);
	    		
	    			workingstep.getCondicoesUsinagem().setAp(ap_max);
	    		}
	    	}
	    	if(n > RPM){
	    		System.out.println("Entrou 4 ");
	    		n = RPM;
	    		P_max = (P/RPM)*n;
	    		Vf = n * fn * nd;
	    	}
	    	P_teste = (ap*ae*Vf*Kc)/(60*102*9.81);
	    	System.out.println("Potencia teste: "+P_teste+" ap: "+ap+" Vf"+Vf);
	    	
	    	if(P_teste > P){
	    		
	    		System.out.println("P: "+P);
	    		System.out.println("Entrou 5");
	    		ap = (P*60*102*9.81)/(ae*Kc*Vf);
	    		
	    		if(ap <= workingstep.getFerramenta().getCuttingEdgeLength()*0.75) workingstep.getCondicoesUsinagem().setAp(ap);
    			else workingstep.getCondicoesUsinagem().setAp(workingstep.getFerramenta().getCuttingEdgeLength()*0.75);
	    	}
			
			workingstep.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(workingstep));
			Vector movimentacao = new Vector();
			MovimentacaoRanhuraPerfilVee detMov = new MovimentacaoRanhuraPerfilVee(workingstep);
			ArrayList<LinearPath> path = detMov.getMovimentacaoAcabamentoRanhuraPerfilVee();
			
			 for(int j = 0; j < path.size(); j++)
				{
					double xAux = path.get(j).getFinalPoint().getX();
					double yAux = path.get(j).getFinalPoint().getY();
					double zAux = -path.get(j).getFinalPoint().getZ();
					
					movimentacao.add(new Ponto(xAux, yAux, zAux));
				}
			 for(int i = 0; i < movimentacao.size()-1; i++){
					
					Ponto p1 = (Ponto)movimentacao.elementAt(i);
					Ponto p2 = (Ponto)movimentacao.elementAt(i+1);
					comprimento += Math.sqrt(Math.pow(p2.getX()-p1.getX(), 2) + Math.pow(p2.getY()-p1.getY(), 2) + Math.pow(p2.getZ()-p1.getZ(), 2));
				}
			
				T = comprimento/Vf;
		}else if((this.workingstep.getOperation().getClass() == BottomAndSideRoughMilling.class ||
				this.workingstep.getOperation().getClass() == BottomAndSideFinishMilling.class) && 
				this.workingstep.getFeature().getClass() == RanhuraPerfilRoundedU.class && 
				workingstep.getFerramenta().getClass()== FaceMill.class  ){
			
			workingstep.getCondicoesUsinagem().setAp(workingstep.getFerramenta().getCuttingEdgeLength()*0.75);
			workingstep.getCondicoesUsinagem().setAe(D*0.75);
		
			n = Vc * 1000 /(Math.PI * D);
			P_max = (P/RPM)*n;
			Vf = n * fn * nd;
			Hm = (fn*ae*360)/(D*Math.PI*180 / Math.PI * Math.acos(1-(2*ae/D)));
			Kc = Kc1*Math.pow(Hm, -Z);
			Kc = Kc/100;
	    	Tc = (ap*ae*Vf*Kc)/(2*Math.PI*n);
	 		T_max = ((-Tm/RPM)*n)+Tm; 
	    	System.out.println("Torque "+Tc);
	 		System.out.println("n "+n);
	 		
	 		System.out.println("Kc : "+Kc);
			
	    	if(Tc > T_max){
	    		
	    		System.out.println("Entrou 1");
	    		double Vf_max = (Tm*2*Math.PI*n)/(ap*ae*Kc);
	    		System.out.println("Vf max "+Vf_max+" Vf: "+Vf);
	    		
	    		if(Vf_max >= Vf*0.42 && Vf_max <= Vf*1.57){
	    			System.out.println("Entrou 2");
	    			Vf = Vf_max; 
	    			n = Vf/fn*nd;
	    			System.out.println("n2 "+n);
	    		
	    		}else{
	    			
	    			System.out.println("Entrou 3");
	    			ap_max = (Tm*2*Math.PI*n)/(ae*Vf*Kc);
	    			System.out.println("ap max "+ap_max);
	    		
	    			workingstep.getCondicoesUsinagem().setAp(ap_max);
	    		}
	    	}
	    	if(n > RPM){
	    		System.out.println("Entrou 4 ");
	    		n = RPM;
	    		P_max = (P/RPM)*n;
	    		Vf = n * fn * nd;
	    	}
	    	P_teste = (ap*ae*Vf*Kc)/(60*102*9.81);
	    	System.out.println("Potencia teste: "+P_teste+" ap: "+ap+" Vf"+Vf);
	    	
	    	if(P_teste > P){
	    		
	    		System.out.println("P: "+P);
	    		System.out.println("Entrou 5");
	    		ap = (P*60*102*9.81)/(ae*Kc*Vf);
	    		
	    		if(ap <= workingstep.getFerramenta().getCuttingEdgeLength()*0.75) workingstep.getCondicoesUsinagem().setAp(ap);
    			else workingstep.getCondicoesUsinagem().setAp(workingstep.getFerramenta().getCuttingEdgeLength()*0.75);
	    	}
			
			workingstep.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(workingstep));
			Vector movimentacao = new Vector();
			MovimentacaoRanhuraPerfilRoundedU detMov = new MovimentacaoRanhuraPerfilRoundedU(workingstep);
			ArrayList<LinearPath> path = detMov.getMovimentacaoDesbasteRanhuraPerfilRoundedU();
			
			 for(int j = 0; j < path.size(); j++)
				{
					double xAux = path.get(j).getFinalPoint().getX();
					double yAux = path.get(j).getFinalPoint().getY();
					double zAux = -path.get(j).getFinalPoint().getZ();
					
					movimentacao.add(new Ponto(xAux, yAux, zAux));
				}
			 
			 for(int i = 0; i < movimentacao.size()-1; i++){
					
					Ponto p1 = (Ponto)movimentacao.elementAt(i);
					Ponto p2 = (Ponto)movimentacao.elementAt(i+1);
					comprimento += Math.sqrt(Math.pow(p2.getX()-p1.getX(), 2) + Math.pow(p2.getY()-p1.getY(), 2) + Math.pow(p2.getZ()-p1.getZ(), 2));
				}
			
				T = comprimento/Vf;
			
		}else if((this.workingstep.getOperation().getClass() == BottomAndSideRoughMilling.class ||
				this.workingstep.getOperation().getClass() == BottomAndSideFinishMilling.class) && 
				this.workingstep.getFeature().getClass() == RanhuraPerfilRoundedU.class && 
				(workingstep.getFerramenta().getClass()== BallEndMill.class && workingstep.getFerramenta().getClass() == EndMill.class)){
			
			workingstep.getCondicoesUsinagem().setAp(workingstep.getFerramenta().getCuttingEdgeLength()*0.75);
			workingstep.getCondicoesUsinagem().setAe(D*0.75);
		
			n = Vc * 1000 /(Math.PI * D);
			P_max = (P/RPM)*n;
			Vf = n * fn * nd;
			Hm = (fn*ae*360)/(D*Math.PI*180 / Math.PI * Math.acos(1-(2*ae/D)));
			Kc = Kc1*Math.pow(Hm, -Z);
			Kc = Kc/100;
	    	Tc = (ap*ae*Vf*Kc)/(2*Math.PI*n);
	 		T_max = ((-Tm/RPM)*n)+Tm; 
	    	System.out.println("Torque "+Tc);
	 		System.out.println("n "+n);
	 		
	 		System.out.println("Kc : "+Kc);
			
	    	if(Tc > T_max){
	    		
	    		System.out.println("Entrou 1");
	    		double Vf_max = (Tm*2*Math.PI*n)/(ap*ae*Kc);
	    		System.out.println("Vf max "+Vf_max+" Vf: "+Vf);
	    		
	    		if(Vf_max >= Vf*0.42 && Vf_max <= Vf*1.57){
	    			System.out.println("Entrou 2");
	    			Vf = Vf_max; 
	    			n = Vf/fn*nd;
	    			System.out.println("n2 "+n);
	    		
	    		}else{
	    			
	    			System.out.println("Entrou 3");
	    			ap_max = (Tm*2*Math.PI*n)/(ae*Vf*Kc);
	    			System.out.println("ap max "+ap_max);
	    		
	    			workingstep.getCondicoesUsinagem().setAp(ap_max);
	    		}
	    	}
	    	if(n > RPM){
	    		System.out.println("Entrou 4 ");
	    		n = RPM;
	    		P_max = (P/RPM)*n;
	    		Vf = n * fn * nd;
	    	}
	    	P_teste = (ap*ae*Vf*Kc)/(60*102*9.81);
	    	System.out.println("Potencia teste: "+P_teste+" ap: "+ap+" Vf"+Vf);
	    	
	    	if(P_teste > P){
	    		
	    		System.out.println("P: "+P);
	    		System.out.println("Entrou 5");
	    		ap = (P*60*102*9.81)/(ae*Kc*Vf);
	    		
	    		if(ap <= workingstep.getFerramenta().getCuttingEdgeLength()*0.75) workingstep.getCondicoesUsinagem().setAp(ap);
    			else workingstep.getCondicoesUsinagem().setAp(workingstep.getFerramenta().getCuttingEdgeLength()*0.75);
	    	}
			
			workingstep.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(workingstep));
			Vector movimentacao = new Vector();
			MovimentacaoRanhuraPerfilRoundedU detMov = new MovimentacaoRanhuraPerfilRoundedU(workingstep);
			ArrayList<LinearPath> path = detMov.getMovimentacaoAcabamentoRanhuraPerfilRoundedU();
			
			 for(int j = 0; j < path.size(); j++)
				{
					double xAux = path.get(j).getFinalPoint().getX();
					double yAux = path.get(j).getFinalPoint().getY();
					double zAux = -path.get(j).getFinalPoint().getZ();
					
					movimentacao.add(new Ponto(xAux, yAux, zAux));
				}
			 
			 for(int i = 0; i < movimentacao.size()-1; i++){
					
					Ponto p1 = (Ponto)movimentacao.elementAt(i);
					Ponto p2 = (Ponto)movimentacao.elementAt(i+1);
					comprimento += Math.sqrt(Math.pow(p2.getX()-p1.getX(), 2) + Math.pow(p2.getY()-p1.getY(), 2) + Math.pow(p2.getZ()-p1.getZ(), 2));
				}
			
				T = comprimento/Vf;
			
		}else if((this.workingstep.getOperation().getClass() == BottomAndSideRoughMilling.class ||
				this.workingstep.getOperation().getClass() == BottomAndSideFinishMilling.class) && 
				this.workingstep.getFeature().getClass() == RanhuraPerfilBezier.class && 
				workingstep.getFerramenta().getClass()== FaceMill.class  ){
			
			workingstep.getCondicoesUsinagem().setAp(workingstep.getFerramenta().getCuttingEdgeLength()*0.75);
			workingstep.getCondicoesUsinagem().setAe(D*0.75);
		
			n = Vc * 1000 /(Math.PI * D);
			P_max = (P/RPM)*n;
			Vf = n * fn * nd;
			Hm = (fn*ae*360)/(D*Math.PI*180 / Math.PI * Math.acos(1-(2*ae/D)));
			Kc = Kc1*Math.pow(Hm, -Z);
			Kc = Kc/100;
	    	Tc = (ap*ae*Vf*Kc)/(2*Math.PI*n);
	 		T_max = ((-Tm/RPM)*n)+Tm; 
	    	System.out.println("Torque "+Tc);
	 		System.out.println("n "+n);
	 		
	 		System.out.println("Kc : "+Kc);
			
	    	if(Tc > T_max){
	    		
	    		System.out.println("Entrou 1");
	    		double Vf_max = (Tm*2*Math.PI*n)/(ap*ae*Kc);
	    		System.out.println("Vf max "+Vf_max+" Vf: "+Vf);
	    		
	    		if(Vf_max >= Vf*0.42 && Vf_max <= Vf*1.57){
	    			System.out.println("Entrou 2");
	    			Vf = Vf_max; 
	    			n = Vf/fn*nd;
	    			System.out.println("n2 "+n);
	    		
	    		}else{
	    			
	    			System.out.println("Entrou 3");
	    			ap_max = (Tm*2*Math.PI*n)/(ae*Vf*Kc);
	    			System.out.println("ap max "+ap_max);
	    		
	    			workingstep.getCondicoesUsinagem().setAp(ap_max);
	    		}
	    	}
	    	if(n > RPM){
	    		System.out.println("Entrou 4 ");
	    		n = RPM;
	    		P_max = (P/RPM)*n;
	    		Vf = n * fn * nd;
	    	}
	    	P_teste = (ap*ae*Vf*Kc)/(60*102*9.81);
	    	System.out.println("Potencia teste: "+P_teste+" ap: "+ap+" Vf"+Vf);
	    	
	    	if(P_teste > P){
	    		
	    		System.out.println("P: "+P);
	    		System.out.println("Entrou 5");
	    		ap = (P*60*102*9.81)/(ae*Kc*Vf);
	    		
	    		if(ap <= workingstep.getFerramenta().getCuttingEdgeLength()*0.75) workingstep.getCondicoesUsinagem().setAp(ap);
    			else workingstep.getCondicoesUsinagem().setAp(workingstep.getFerramenta().getCuttingEdgeLength()*0.75);
	    	}
			
			workingstep.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(workingstep));
			Vector movimentacao = new Vector();
			MovimentacaoRanhuraPerfilGenerico detMov = new MovimentacaoRanhuraPerfilGenerico(workingstep);
			ArrayList<LinearPath> path = detMov.getMovimentacaoDesbasteRanhuraPerfilGenerico();
			
			 for(int j = 0; j < path.size(); j++)
				{
					double xAux = path.get(j).getFinalPoint().getX();
					double yAux = path.get(j).getFinalPoint().getY();
					double zAux = -path.get(j).getFinalPoint().getZ();
					
					movimentacao.add(new Ponto(xAux, yAux, zAux));
				}
			 for(int i = 0; i < movimentacao.size()-1; i++){
					
					Ponto p1 = (Ponto)movimentacao.elementAt(i);
					Ponto p2 = (Ponto)movimentacao.elementAt(i+1);
					comprimento += Math.sqrt(Math.pow(p2.getX()-p1.getX(), 2) + Math.pow(p2.getY()-p1.getY(), 2) + Math.pow(p2.getZ()-p1.getZ(), 2));
				}
			
				T = comprimento/Vf;
		
		}else if((this.workingstep.getOperation().getClass() == BottomAndSideRoughMilling.class ||
				this.workingstep.getOperation().getClass() == BottomAndSideFinishMilling.class) && 
				this.workingstep.getFeature().getClass() == RanhuraPerfilBezier.class && 
				workingstep.getFerramenta().getClass()== BallEndMill.class  ){
			
			workingstep.getCondicoesUsinagem().setAp(workingstep.getFerramenta().getCuttingEdgeLength()*0.75);
			workingstep.getCondicoesUsinagem().setAe(D*0.75);
		
			n = Vc * 1000 /(Math.PI * D);
			P_max = (P/RPM)*n;
			Vf = n * fn * nd;
			Hm = (fn*ae*360)/(D*Math.PI*180 / Math.PI * Math.acos(1-(2*ae/D)));
			Kc = Kc1*Math.pow(Hm, -Z);
			Kc = Kc/100;
	    	Tc = (ap*ae*Vf*Kc)/(2*Math.PI*n);
	 		T_max = ((-Tm/RPM)*n)+Tm; 
	    	System.out.println("Torque "+Tc);
	 		System.out.println("n "+n);
	 		
	 		System.out.println("Kc : "+Kc);
			
	    	if(Tc > T_max){
	    		
	    		System.out.println("Entrou 1");
	    		double Vf_max = (Tm*2*Math.PI*n)/(ap*ae*Kc);
	    		System.out.println("Vf max "+Vf_max+" Vf: "+Vf);
	    		
	    		if(Vf_max >= Vf*0.42 && Vf_max <= Vf*1.57){
	    			System.out.println("Entrou 2");
	    			Vf = Vf_max; 
	    			n = Vf/fn*nd;
	    			System.out.println("n2 "+n);
	    		
	    		}else{
	    			
	    			System.out.println("Entrou 3");
	    			ap_max = (Tm*2*Math.PI*n)/(ae*Vf*Kc);
	    			System.out.println("ap max "+ap_max);
	    		
	    			workingstep.getCondicoesUsinagem().setAp(ap_max);
	    		}
	    	}
	    	if(n > RPM){
	    		System.out.println("Entrou 4 ");
	    		n = RPM;
	    		P_max = (P/RPM)*n;
	    		Vf = n * fn * nd;
	    	}
	    	P_teste = (ap*ae*Vf*Kc)/(60*102*9.81);
	    	System.out.println("Potencia teste: "+P_teste+" ap: "+ap+" Vf"+Vf);
	    	
	    	if(P_teste > P){
	    		
	    		System.out.println("P: "+P);
	    		System.out.println("Entrou 5");
	    		ap = (P*60*102*9.81)/(ae*Kc*Vf);
	    		
	    		if(ap <= workingstep.getFerramenta().getCuttingEdgeLength()*0.75) workingstep.getCondicoesUsinagem().setAp(ap);
    			else workingstep.getCondicoesUsinagem().setAp(workingstep.getFerramenta().getCuttingEdgeLength()*0.75);
	    	}
			
			workingstep.setPontos(MapeadoraDeWorkingsteps.determinadorDePontos(workingstep));
			Vector movimentacao = new Vector();
			MovimentacaoRanhuraPerfilGenerico detMov = new MovimentacaoRanhuraPerfilGenerico(workingstep);
			ArrayList<LinearPath> path = detMov.getMovimentacaoAcabamentoRanhuraPerfilGenerico();
			
			 for(int j = 0; j < path.size(); j++)
				{
					double xAux = path.get(j).getFinalPoint().getX();
					double yAux = path.get(j).getFinalPoint().getY();
					double zAux = -path.get(j).getFinalPoint().getZ();
					
					movimentacao.add(new Ponto(xAux, yAux, zAux));
				}
			 for(int i = 0; i < movimentacao.size()-1; i++){
					
					Ponto p1 = (Ponto)movimentacao.elementAt(i);
					Ponto p2 = (Ponto)movimentacao.elementAt(i+1);
					comprimento += Math.sqrt(Math.pow(p2.getX()-p1.getX(), 2) + Math.pow(p2.getY()-p1.getY(), 2) + Math.pow(p2.getZ()-p1.getZ(), 2));
				}
			
				T = comprimento/Vf;
		}
		
		
		
		return T;
	}
	
}
