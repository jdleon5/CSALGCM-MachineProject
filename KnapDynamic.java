import java.util.*; 
import java.io.*;

public class KnapDynamic {
    private static int[] wt;
    private static int[] val;
    private static int n;
    private static int cap;
    private static String[] name;
    private static String dump;

    public int solveKnapsack(int[] val, int[] wt, int cap) {
        // base checks
        if (cap <= 0 || val.length == 0 || wt.length != val.length)
          return 0;
    
        int n = val.length;
        int[][] dp = new int[n][cap + 1];
    
        // populate the capacity=0 columns, with '0' capacity we have '0' profit
        for(int i=0; i < n; i++)
          dp[i][0] = 0;
    
        // if we have only one weight, we will take it if it is not more than the capacity
        for(int c=0; c <= cap; c++) {
          if(wt[0] <= c)
            dp[0][c] = val[0];
        }
    
        // process all sub-arrays for all the capacities
        for(int i=1; i < n; i++) {
          for(int c=1; c <= cap; c++) {
            int profit1= 0, profit2 = 0;
            // include the item, if it is not more than the capacity
            if(wt[i] <= c)
              profit1 = val[i] + dp[i-1][c-wt[i]];
            // exclude the item
            profit2 = dp[i-1][c];
            // take maximum
            dp[i][c] = Math.max(profit1, profit2);
          }
        }
    
        printSelectedElements(dp, wt, val, cap);
        return dp[n-1][cap];
      }
    
     private void printSelectedElements(int dp[][], int[] weights, int[] profits, int capacity){
       System.out.print("\nItems included in Knapsack:");
       int totalProfit = dp[weights.length-1][capacity];
       for(int i=weights.length-1; i > 0; i--) {
         if(totalProfit != dp[i-1][capacity]) {
           System.out.print(" " + name[i]);
           capacity -= weights[i];
           totalProfit -= profits[i];
         }
       }
    
       if(totalProfit != 0)
         System.out.print(" " + name[0]);
       System.out.println("");
     }
    public static void main(String[] args) {
        KnapDynamic knapd = new KnapDynamic();
        getInputs();
        int maxProfit = knapd.solveKnapsack(val, wt, cap);
        System.out.println("Total Profit: " + maxProfit);
        //maxProfit = ks.solveKnapsack(val, wt, w-1);
        //System.out.println("Total knapsack profit ---> " + maxProfit);
    } 

    public static void getInputs() {
        Scanner sc = new Scanner(System.in);
        while(n <= 0) {
            System.out.print("Number of items: ");
            n = sc.nextInt();
        }
        while(cap <= 0) {
            System.out.print("Weight limit: ");
            cap = sc.nextInt();
        }
        

        name = new String[20];
        wt = new int[20];
        val = new int[20];
        
        for(int i = 0; i < n; i++) {
            dump = sc.nextLine(); 
            System.out.print("\nItem name: ");
            name[i] = sc.nextLine();
            while(wt[i] <= 0){
                System.out.print("Item weight: ");
                wt[i] = sc.nextInt();
            }
            while(val[i] <= 0){
                System.out.print("Item value: ");
                val[i] = sc.nextInt();
            }
        }
    }
}

