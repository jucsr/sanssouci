package br.UFSC.GRIMA.util;
public class Insercao
{
	static void insercao (int n, int v[])
	{
		int j, i, x;
		for (j = 1; j < n; j++) {
			x = v[j];
			for (i = j-1; i >= 0 && v[i] > x; --i) 
				v[i+1] = v[i];
			v[i+1] = x;
		}
	}
	public static void main(String[] args)
	{
		int[] v = {45, 23, 12, 32, 3, 11, 36, 45, 17, 20};
		int n = 10;
		insercao(n, v);
		for (int i = 0; i < v.length; i++)
		{
			System.out.print(" " + v[i]);
		}
	}
}