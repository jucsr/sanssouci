package br.UFSC.GRIMA.util;
/*
 *        Permutations - Tim Tyler 2000.
 * 
 *         A generator of permutations.
 *
 * This code has been placed in the public domain.
 * This means that you can do what you like with it.
 * Please note that this code comes with no warranty.
 *
 */

/*
 * To Do:
 *
 */

   import java.lang.Object;
   public class Permutations extends Object {
      public static int size;
      public static int sizemo;
      public static int sizeo2;
      public static int max;
      public static int min;
      public static int debug = 0;
      public static int i, j;
      public static int ret_val;
      public static int temp;
      public static int[] data;
      public static int[] checklist;
      public static int position;
      public static int total_number;
      public static int iterations;
      public static int count;
   
      public static Executor executor;
   
      public static void permute(int n, Executor executor) {
         size = n;
         // total_number = n;
      
         data = new int[size];
      
         for (i = 0; i < size; i++) {
            data[i] = i;
         }
      
         executor.execute();
      
         iterations = factorial(size);
         for (count = 0; count < iterations - 1; count++) {
            getNext();
            executor.execute();
         }
      
         // System.out.println("\nFINISHED");
      }
   
   
      public static void getNext() {
         int i = size - 1;
      
         while (data[i-1] >= data[i]) 
            i = i-1;
      
         int j = size;
      
         while (data[j-1] <= data[i-1]) 
            j = j-1;
      
         swap(i-1, j-1);
      
         i++; 
         j = size;
      
         while (i < j) {
            swap(i-1, j-1);
            i++;
            j--;
         }
      }
   
   
      public static void swap(int a, int b) {
         temp = data[a];
         data[a] = data[b];
         data[b] = temp;
      }
   
   
      public static int factorial(int a) {
         temp = 1;
         if (a > 1) {
            for (i = 1; i <= a; i++) {
               temp *= i;
            }
         }
         return temp;
      }
      public static void main(String args[]) {
         executor = new Executor() {
                  public void execute() {
                     int size = data.length;
                  
                     for (i = 0; i < size; i++) {
                        System.out.print("" + data[i] + " ");
                     }
                  
                     System.out.println("");
                  
                  }                         
               };
      
         total_number = 10;
      
         permute(total_number, executor);
         System.out.println("Total de permuta��es: " + factorial(total_number));
         do {
         } while (true);
      
      }
   }
   
   
