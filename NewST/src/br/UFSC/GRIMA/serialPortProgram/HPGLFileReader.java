package br.UFSC.GRIMA.serialPortProgram;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JOptionPane;



public class HPGLFileReader {

	public HPGLFileReader() {
		// TODO Auto-generated constructor stub
	}

	public static Vector getHPGLCode(File file){
		String fileString = getFileString(file);

		//Vector output = getHPGLCodeByFace(fileString);
		return null;
	}
	
	public static Object[] getFileData(File file){
		Object[] output = new Object[2];

		String fileString = getFileString(file);		
		StringTokenizer token = new StringTokenizer(fileString, "#");
	
		DadosDeProjeto pi = getDadosDeProjeto(token.nextToken());
		
		Vector facesHPGL = new Vector();
		while(token.hasMoreTokens()){
			String codeByFace = getHPGLCodeByFace(token.nextToken());
			facesHPGL.add(codeByFace);
		}
		
		output[0] = pi;
		output[1] = facesHPGL;
		       
		
		return output;
	}
	public static Object[] getDataBD(String in){
		Object[] output = new Object[2];
	
		StringTokenizer token = new StringTokenizer(in, "#");
	
		DadosDeProjeto pi = getDadosDeProjeto(token.nextToken());
		
		Vector facesHPGL = new Vector();
		while(token.hasMoreTokens()){
			String codeByFace = getHPGLCodeByFace(token.nextToken());
			facesHPGL.add(codeByFace);
		}
		
		output[0] = pi;
		output[1] = facesHPGL;
		       
		
		return output;
	}

	public static String getFileString(File file){
		String output = null;

		try {
			FileReader reader = new FileReader(file);
			BufferedReader leitor = new BufferedReader(reader);

			Scanner s = new Scanner(leitor);
			String arquivo = "";

			while (s.hasNextLine()) {
				arquivo += s.nextLine();
				arquivo += "\n";
				
				//System.out.print(s.next());
			}

			// Get the size of the file

			long length = file.length();

			if (length > Integer.MAX_VALUE) {
				// File is too large
				System.out.println("Arquivo muito longo");
			}

			//System.out.println(arquivo);
			output = arquivo;

		}
		catch (Exception e) {
			System.out.println("Erro ao criar o leitor do arquivo de codigo HPGL!");
			e.printStackTrace();
		}

		return output;
	}

	public static String getHPGLCodeByFace(String input){
		String output = "";
		
		try{
			StringTokenizer token = new StringTokenizer(input, "\n");
			token.nextToken();
			token.nextToken();

			while(token.hasMoreElements()){
				output += token.nextToken();
				output += "\n";
			}
		}
		catch(Exception ex){
			
		}
		
		return output;
	}
	
	public static DadosDeProjeto getDadosDeProjeto(String info){
		DadosDeProjeto output = new DadosDeProjeto();
		
		StringTokenizer token = new StringTokenizer(info, "\n");

	    while (token.hasMoreTokens()) {
	        String tmp = token.nextToken();
	        		
			StringTokenizer tokenTmp = new StringTokenizer(tmp, ":");
			
			if(tokenTmp.countTokens() == 2){
				String descritorTmp = tokenTmp.nextToken();
				if(descritorTmp.equals("User")){
					output.setUserName(tokenTmp.nextToken());
				}
				else if(descritorTmp.equals("Project Name")){
					output.setProjectName(tokenTmp.nextToken());
				}
				else if(descritorTmp.equals("Material")){
					output.setMaterial(tokenTmp.nextToken());
				}
				else if(descritorTmp.equals("Quantity")){
					output.setQuantidade(Integer.parseInt(tokenTmp.nextToken()));
				}
			}
	    }
	    output.printData();
		
		return output;
	}
}
