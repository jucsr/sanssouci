package br.UFSC.GRIMA.entidades;

import java.io.Serializable;
import java.util.ArrayList;

public class Material implements Serializable{

	private String type;
	private String name;
	private String materialIdentifier;
	private String propertie;
	private ArrayList<PropertyParameter> properties;
	private int category;

	public static final int ACO_SEM_LIGA = 1;
	public static final int ACO_ALTO_CARBONO = 2;
	public static final int ACO_BAIXA_LIGA = 3;
	public static final int ACO_ALTA_LIGA = 4;
	public static final int ACO_FUNDIDO = 5;
	public static final int ACO_INOX_AUST = 6;
	public static final int ACO_INOX_AUST_FUND = 7;
	public static final int SUPER_LIGA_NI = 8;
	public static final int LIGA_TITANIO = 9;
	public static final int FERRO_FUNDIDO_MALEAVEL = 10;
	public static final int FERRO_FUNDIDO_CINZENTO = 11;
	public static final int FERRO_FUNDIDO_NODULAR = 12;
	public static final int ACO_EXTRA_DURO = 13;
	public static final int LIGA_ALUMINIO = 14;
	public static final int LIGA_COBRE = 15;


	public Material()
	{

	}
	public Material(String name, int category)
	{
		this.name = name;
		this.category = category;
	}
	public Material(String name, int category, ArrayList<PropertyParameter> properties)
	{
		this.name = name;
		this.category = category;
		this.properties = properties;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPropertie() {
		return propertie;
	}
	public void setPropertie(String propertie) {
		this.propertie = propertie;
	}
	public void setProperties(ArrayList<PropertyParameter> properties)
	{
		this.properties = properties;
	}
	public ArrayList<PropertyParameter> getProperties()
	{
		return this.properties;
	}
	public int getCategory() 
	{
		return category;
	}
	public void setCategory(int category) 
	{
		this.category = category;
	}
	public static String getTypeMaterial(int material){

		String type = "";

		switch(material){

		case(Material.ACO_SEM_LIGA):
			type = "ACO SEM LIGA";
		break;
		case(Material.ACO_ALTO_CARBONO):
			type = "ACO ALTO CARBONO";
		break;
		case(Material.ACO_BAIXA_LIGA):
			type = "ACO BAIXA LIGA";
		break;
		case(Material.ACO_ALTA_LIGA):
			type = "ACO ALTA LIGA";
		break;
		case(Material.ACO_FUNDIDO):
			type = "ACO FUNDIDO";
		break;
		case(Material.ACO_INOX_AUST):
			type = "ACO INOXIDAVEL AUSTENITICO";
		break;
		case(Material.ACO_INOX_AUST_FUND):
			type = "ACO INOXODAVEL AUSTENITICO FUNDIDO";
		break;
		case(Material.SUPER_LIGA_NI):
			type = "SUPER LIGA RESISTENTE AO CALOR A BASE DE NIQUEL";
		break;
		case(Material.LIGA_TITANIO):
			type = "LIGA DE TITANIO";
		break;
		case(Material.FERRO_FUNDIDO_MALEAVEL):
			type = "FERRO FUNDIDO MALEAVEL";
		break;
		case(Material.FERRO_FUNDIDO_CINZENTO):
			type = "FERRO FUNDIDO CINZENTO";
		break;
		case(Material.FERRO_FUNDIDO_NODULAR):
			type = "FERRO FUNDIDO NODULAR";
		break;
		case(Material.ACO_EXTRA_DURO):
			type = "ACO EXTRA DURO";
		break;
		case(Material.LIGA_ALUMINIO):
			type = "LIGA DE ALUMINIO";
		break;
		case(Material.LIGA_COBRE):
			type = "LIGA DE COBRE";
		break;
		default:
			break;
		}

		return type;
	}


	public String getTypeMaterial(){

		switch(this.category){

		case(Material.ACO_SEM_LIGA):
			this.type = "ACO SEM LIGA";
		break;
		case(Material.ACO_ALTO_CARBONO):
			this.type = "ACO ALTO CARBONO";
		break;
		case(Material.ACO_BAIXA_LIGA):
			this.type = "ACO BAIXA LIGA";
		break;
		case(Material.ACO_ALTA_LIGA):
			this.type = "ACO ALTA LIGA";
		break;
		case(Material.ACO_FUNDIDO):
			this.type = "ACO FUNDIDO";
		break;
		case(Material.ACO_INOX_AUST):
			this.type = "ACO INOXIDAVEL AUSTENITICO";
		break;
		case(Material.ACO_INOX_AUST_FUND):
			this.type = "ACO INOXODAVEL AUSTENITICO FUNDIDO";
		break;
		case(Material.SUPER_LIGA_NI):
			this.type = "SUPER LIGA RESISTENTE AO CALOR A BASE DE NIQUEL";
		break;
		case(Material.LIGA_TITANIO):
			this.type = "LIGA DE TITANIO";
		break;
		case(Material.FERRO_FUNDIDO_MALEAVEL):
			this.type = "FERRO FUNDIDO MALEAVEL";
		break;
		case(Material.FERRO_FUNDIDO_CINZENTO):
			this.type = "FERRO FUNDIDO CINZENTO";
		break;
		case(Material.FERRO_FUNDIDO_NODULAR):
			this.type = "FERRO FUNDIDO NODULAR";
		break;
		case(Material.ACO_EXTRA_DURO):
			this.type = "ACO EXTRA DURO";
		break;
		case(Material.LIGA_ALUMINIO):
			this.type = "LIGA DE ALUMINIO";
		break;
		case(Material.LIGA_COBRE):
			this.type = "LIGA DE COBRE";
		break;
		default:
			break;
		}

		return this.type;
	}

	public static int getCategoryMaterial(String type){

		int category = 0;

		if(type.equals("ACO SEM LIGA"))
			category = Material.ACO_SEM_LIGA;
		else if(type.equals("ACO ALTO CARBONO"))
			category = Material.ACO_ALTO_CARBONO;
		else if(type.equals("ACO BAIXA LIGA"))
			category = Material.ACO_BAIXA_LIGA;
		else if(type.equals("ACO ALTA LIGA"))
			category = Material.ACO_ALTA_LIGA;
		else if(type.equals("ACO FUNDIDO"))
			category = Material.ACO_FUNDIDO;
		else if(type.equals("ACO INOXIDAVEL AUSTENITICO"))
			category = Material.ACO_INOX_AUST;
		else if(type.equals("ACO INOXODAVEL AUSTENITICO FUNDIDO"))
			category = Material.ACO_INOX_AUST_FUND;
		else if(type.equals("SUPER LIGA RESISTENTE AO CALOR A BASE DE NIQUEL"))
			category = Material.SUPER_LIGA_NI;
		else if(type.equals("LIGA DE TITANIO"))
			category = Material.LIGA_TITANIO;
		else if(type.equals("FERRO FUNDIDO MALEAVEL"))
			category = Material.FERRO_FUNDIDO_MALEAVEL;
		else if(type.equals("FERRO FUNDIDO CINZENTO"))
			category = Material.FERRO_FUNDIDO_CINZENTO;
		else if(type.equals("FERRO FUNDIDO NODULAR"))
			category = Material.FERRO_FUNDIDO_NODULAR;
		else if(type.equals("ACO EXTRA DURO"))
			category = Material.ACO_EXTRA_DURO;
		else if(type.equals("LIGA DE ALUMINIO"))
			category = Material.LIGA_ALUMINIO;
		else if(type.equals("LIGA DE COBRE"))
			category = Material.LIGA_COBRE;

		return category;
	}

	public int getCategoryMaterial(){

		if(type.equals("ACO SEM LIGA"))
			category = Material.ACO_SEM_LIGA;
		else if(type.equals("ACO ALTO CARBONO"))
			category = Material.ACO_ALTO_CARBONO;
		else if(type.equals("ACO BAIXA LIGA"))
			category = Material.ACO_BAIXA_LIGA;
		else if(type.equals("ACO ALTA LIGA"))
			category = Material.ACO_ALTA_LIGA;
		else if(type.equals("ACO FUNDIDO"))
			category = Material.ACO_FUNDIDO;
		else if(type.equals("ACO INOXIDAVEL AUSTENITICO"))
			category = Material.ACO_INOX_AUST;
		else if(type.equals("ACO INOXODAVEL AUSTENITICO FUNDIDO"))
			category = Material.ACO_INOX_AUST_FUND;
		else if(type.equals("SUPER LIGA RESISTENTE AO CALOR A BASE DE NIQUEL"))
			category = Material.SUPER_LIGA_NI;
		else if(type.equals("LIGA DE TITANIO"))
			category = Material.LIGA_TITANIO;
		else if(type.equals("FERRO FUNDIDO MALEAVEL"))
			category = Material.FERRO_FUNDIDO_MALEAVEL;
		else if(type.equals("FERRO FUNDIDO CINZENTO"))
			category = Material.FERRO_FUNDIDO_CINZENTO;
		else if(type.equals("FERRO FUNDIDO NODULAR"))
			category = Material.FERRO_FUNDIDO_NODULAR;
		else if(type.equals("ACO EXTRA DURO"))
			category = Material.ACO_EXTRA_DURO;
		else if(type.equals("LIGA DE ALUMINIO"))
			category = Material.LIGA_ALUMINIO;
		else if(type.equals("LIGA DE COBRE"))
			category = Material.LIGA_COBRE;

		return category;
	}
	public String getMaterialIdentifier() {
		return materialIdentifier;
	}
	public void setMaterialIdentifier(String materialIdentifier) {
		this.materialIdentifier = materialIdentifier;
	}
}
