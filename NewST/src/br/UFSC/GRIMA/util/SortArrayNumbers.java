package br.UFSC.GRIMA.util;

public class SortArrayNumbers
{
	public static void sortEm(int [] array, int len)
	{
		int a,b;
		int temp;
		int sortTheNumbers = len - 1;
		for (a = 0; a < sortTheNumbers; ++a)
		{
			for (b = 0; b < sortTheNumbers; ++b)
				if(array[b] < array[b + 1])
				{
					temp = array[b];
					array[b] = array[b + 1];
					array[b + 1] = temp;	
				}

		}
	}
	public static void main(String[] args)
	{
		int[] array = {10, 23, 3, 6, 12, 16, 100, 22, 36, 15};
		int len = 10;
		sortEm(array, len);
		for (int i = 0; i < array.length; i++)
		{
			System.out.print(" " + array[i]);
		}
		
	}
}

