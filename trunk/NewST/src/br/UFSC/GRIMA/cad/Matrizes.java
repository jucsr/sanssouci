package br.UFSC.GRIMA.cad;
import br.UFSC.GRIMA.entidades.features.Face;

public class Matrizes
{
	
//	[Face][VerticeAtivado][Outras posicoes][tipo da face, vertice]
	public static int PLANOS_FACE = 0;
	public static int PLANOS_VERTICE = 1;
	
	//[Face central][vertice ativado face central][face em cada posicao][Face na posicao, vertice da face]
	public static int [][][][] planos = 	{
		{	{{Face.XY, 0}, {Face.ZY, 0}, {Face.ZX, 0}, {Face.YZ, 0}, {Face.XZ, 0}, {Face.YX, 0}},
			{{Face.XY, 1}, {Face.ZX, 1}, {Face.YZ, 1}, {Face.XZ, 1}, {Face.ZY, 1}, {Face.YX, 3}},
			{{Face.XY, 2}, {Face.YZ, 2}, {Face.XZ, 2}, {Face.ZY, 2}, {Face.ZX, 2}, {Face.YX, 2}},
			{{Face.XY, 3}, {Face.XZ, 3}, {Face.ZY, 3}, {Face.ZX, 3}, {Face.YZ, 3}, {Face.YX, 1}}}, //Face.XY 
		{
			{{Face.YZ, 0}, {Face.XY, 0}, {Face.ZX, 3}, {Face.YX, 2}, {Face.XZ, 1}, {Face.ZY, 2}},
			{{Face.YZ, 1}, {Face.ZX, 0}, {Face.YX, 3}, {Face.XZ, 2}, {Face.XY, 1}, {Face.ZY, 1}},
			{{Face.YZ, 2}, {Face.YX, 0}, {Face.XZ, 3}, {Face.XY, 2}, {Face.ZX, 1}, {Face.ZY, 0}},
			{{Face.YZ, 3}, {Face.XZ, 0}, {Face.XY, 3}, {Face.ZX, 2}, {Face.YX, 1}, {Face.ZY, 3}}}, //Face YZ 
		{
			{{Face.XZ, 0}, {Face.ZY, 1}, {Face.XY, 0}, {Face.YZ, 3}, {Face.YX, 0}, {Face.ZX, 0}},
			{{Face.XZ, 1}, {Face.XY, 1}, {Face.YZ, 0}, {Face.YX, 1}, {Face.ZY, 2}, {Face.ZX, 3}},
			{{Face.XZ, 2}, {Face.YZ, 1}, {Face.YX, 2}, {Face.ZY, 3}, {Face.XY, 2}, {Face.ZX, 2}},
			{{Face.XZ, 3}, {Face.YX, 3}, {Face.ZY, 0}, {Face.XY, 3}, {Face.YZ, 2}, {Face.ZX, 1}}}, //Face XZ
		{
			{{Face.YX, 0}, {Face.ZY, 2}, {Face.XZ, 0}, {Face.YZ, 2}, {Face.ZX, 0}, {Face.XY, 0}},
			{{Face.YX, 1}, {Face.XZ, 1}, {Face.YZ, 3}, {Face.ZX, 1}, {Face.ZY, 3}, {Face.XY, 3}},
			{{Face.YX, 2}, {Face.YZ, 0}, {Face.ZX, 2}, {Face.ZY, 0}, {Face.XZ, 2}, {Face.XY, 2}},
			{{Face.YX, 3}, {Face.ZX, 3}, {Face.ZY, 1}, {Face.XZ, 3}, {Face.YZ, 1}, {Face.XY, 1}}}, //Face YX
		{
			{{Face.ZY, 0}, {Face.YX, 2}, {Face.ZX, 1}, {Face.XY, 0}, {Face.XZ, 3}, {Face.YZ, 2}},
			{{Face.ZY, 1}, {Face.ZX, 2}, {Face.XY, 1}, {Face.XZ, 0}, {Face.YX, 3}, {Face.YZ, 1}},
			{{Face.ZY, 2}, {Face.XY, 2}, {Face.XZ, 1}, {Face.YX, 0}, {Face.ZX, 3}, {Face.YZ, 0}},
			{{Face.ZY, 3}, {Face.XZ, 2}, {Face.YX, 1}, {Face.ZX, 0}, {Face.XY, 3}, {Face.YZ, 3}}}, //Face ZY
		{
			{{Face.ZX, 0}, {Face.ZY, 3}, {Face.YX, 0}, {Face.YZ, 1}, {Face.XY, 0}, {Face.XZ, 0}},
			{{Face.ZX, 1}, {Face.YX, 1}, {Face.YZ, 2}, {Face.XY, 1}, {Face.ZY, 0}, {Face.XZ, 3}},
			{{Face.ZX, 2}, {Face.YZ, 3}, {Face.XY, 2}, {Face.ZY, 1}, {Face.YX, 2}, {Face.XZ, 2}},
			{{Face.ZX, 3}, {Face.XY, 3}, {Face.ZY, 2}, {Face.YX, 3}, {Face.YZ, 0}, {Face.XZ, 1}}}  //Face ZX
												};
	
//	[Face Desenhada][Face paralela, Vertice, modo(trocar ou n�o os eixos)]
	public static int PARALELAS_FACE = 0;
	public static int PARALELAS_VERTICE = 1;
	public static int PARALELAS_MODO = 2;
	
	//public static int [][] paralelas = {{Face.YX, 1, 0}, {Face.ZY, 3, 0}, {Face.ZX, 1, 0}, {Face.XY, 1, 0}, {Face.YZ, 3, 0}, {Face.XZ, 1, 0}};	
	
	public static int [][][]paralelas = {
										{{Face.YX, 1, 0}, {Face.YX, 0, 0}, {Face.YX, 3, 0}, {Face.YX, 2, 0}},
										{{Face.ZY, 3, 0}, {Face.ZY, 2, 0}, {Face.ZY, 1, 0}, {Face.ZY, 0, 0}},
										{{Face.ZX, 1, 0}, {Face.ZX, 0, 0}, {Face.ZX, 3, 0}, {Face.ZX, 2, 0}},
										{{Face.XY, 1, 0}, {Face.XY, 0, 0}, {Face.XY, 3, 0}, {Face.XY, 2, 0}},
										{{Face.YZ, 3, 0}, {Face.YZ, 2, 0}, {Face.YZ, 1, 0}, {Face.YZ, 0, 0}},
										{{Face.XZ, 1, 0}, {Face.XZ, 0, 0}, {Face.XZ, 3, 0}, {Face.XZ, 2, 0}}
										};
	
	public static int ORTOGONAIS_FACE = 0;
	public static int ORTOGONAIS_VERTICE = 1;
//	[Face][Vertice ativado][Posicao][Tipo face ortogonal, vertice da ortogonal]
	public static int[][][][] ortogonais  = 	{
														{
															{{Face.ZY, 1}, {Face.ZX, 2}, {Face.YZ, 3}, {Face.XZ, 0}},
															{{Face.ZX, 2}, {Face.YZ, 3}, {Face.XZ, 0}, {Face.ZY, 1}},
															{{Face.YZ, 3}, {Face.XZ, 0}, {Face.ZY, 1}, {Face.ZX, 2}},
															{{Face.XZ, 0}, {Face.ZY, 1}, {Face.ZX, 2}, {Face.YZ, 3}}},
														{
															{{Face.XY, 1}, {Face.ZX, 1}, {Face.YX, 1}, {Face.XZ, 1}},
															{{Face.ZX, 1}, {Face.YX, 1}, {Face.XZ, 1}, {Face.XY, 1}},
															{{Face.YX, 1}, {Face.XZ, 1}, {Face.XY, 1}, {Face.ZX, 1}},
															{{Face.XZ, 1}, {Face.XY, 1}, {Face.ZX, 1}, {Face.YX, 1}}},
														{
															{{Face.ZY, 2}, {Face.XY, 2}, {Face.YZ, 2}, {Face.YX, 0}},
															{{Face.XY, 2}, {Face.YZ, 2}, {Face.YX, 0}, {Face.ZY, 2}},
															{{Face.YZ, 2}, {Face.YX, 0}, {Face.ZY, 2}, {Face.XY, 2}},
															{{Face.YX, 0}, {Face.ZY, 2}, {Face.XY, 2}, {Face.YZ, 2}}},
														{
															{{Face.ZY, 3}, {Face.XZ, 2}, {Face.YZ, 1}, {Face.ZX, 0}},
															{{Face.XZ, 2}, {Face.YZ, 1}, {Face.ZX, 0}, {Face.ZY, 3}},
															{{Face.YZ, 1}, {Face.ZX, 0}, {Face.ZY, 3}, {Face.XZ, 2}},
															{{Face.ZX, 0}, {Face.ZY, 3}, {Face.XZ, 2}, {Face.YZ, 1}}},
														{
															{{Face.YX, 3}, {Face.ZX, 3}, {Face.XY, 3}, {Face.XZ, 3}},
															{{Face.ZX, 3}, {Face.XY, 3}, {Face.XZ, 3}, {Face.YX, 3}},
															{{Face.XY, 3}, {Face.XZ, 3}, {Face.YX, 3}, {Face.ZX, 3}},
															{{Face.XZ, 3}, {Face.YX, 3}, {Face.ZX, 3}, {Face.XY, 3}}},
														{
															{{Face.ZY, 0}, {Face.YX, 2}, {Face.YZ, 0}, {Face.XY, 0}},
															{{Face.YX, 2}, {Face.YZ, 0}, {Face.XY, 0}, {Face.ZY, 0}},
															{{Face.YZ, 0}, {Face.XY, 0}, {Face.ZY, 0}, {Face.YX, 2}},
															{{Face.XY, 0}, {Face.ZY, 0}, {Face.YX, 2}, {Face.YZ, 0}}}
												};
	public static int ROTACOES_FACE = 0;
	public static int ROTACOES_VERTICE = 1;
	// [Face][][][]
	public static int [][][][] rotacoes = {
		{{{Face.ZX, 0}, {Face.ZY, 0}, {Face.XY, 1}}, {{Face.YZ, 1}, {Face.ZX, 1}, {Face.XY, 2}}, {{Face.XZ, 2}, {Face.YZ, 2}, {Face.XY, 3}}, {{Face.ZY, 3}, {Face.XZ, 3}, {Face.XY, 0}}}, //Face XY
		{{{Face.ZX, 3}, {Face.XY, 0}, {Face.YZ, 1}}, {{Face.YX, 3}, {Face.ZX, 0}, {Face.YZ, 2}}, {{Face.XZ, 3}, {Face.YX, 0}, {Face.YZ, 3}}, {{Face.XY, 3}, {Face.XZ, 0}, {Face.YZ, 0}}}, //Face YZ
		{{{Face.XY, 0}, {Face.ZY, 1}, {Face.XZ, 1}}, {{Face.YZ, 0}, {Face.XY, 1}, {Face.XZ, 2}}, {{Face.YX, 2}, {Face.YZ, 1}, {Face.XZ, 3}}, {{Face.ZY, 0}, {Face.YX, 3}, {Face.XZ, 0}}}, //Face XZ
		{{{Face.XZ, 0}, {Face.ZY, 2}, {Face.YX, 1}}, {{Face.YZ, 3}, {Face.XZ, 1}, {Face.YX, 2}}, {{Face.ZX, 2}, {Face.YZ, 0}, {Face.YX, 3}}, {{Face.ZY, 1}, {Face.ZX, 3}, {Face.YX, 0}}}, //Face YX
		{{{Face.XZ, 1}, {Face.YX, 2}, {Face.ZY, 1}}, {{Face.XY, 1}, {Face.ZX, 2}, {Face.ZY, 2}}, {{Face.XZ, 1}, {Face.XY, 2}, {Face.ZY, 3}}, {{Face.YX, 1}, {Face.XZ, 2}, {Face.ZY, 0}}}, //Face ZY
		{{{Face.YX, 0}, {Face.ZY, 3}, {Face.ZX, 1}}, {{Face.YZ, 2}, {Face.YX, 1}, {Face.ZX, 2}}, {{Face.XY, 2}, {Face.YZ, 3}, {Face.ZX, 3}}, {{Face.ZY, 2}, {Face.XY, 3}, {Face.ZX, 0}}}  //Face ZX
				};
	
}