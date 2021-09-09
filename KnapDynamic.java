import java.util.*; 
import java.io.*;

public class KnapDynamic {
    private static int[] wt;
    private static int[] val;
    private static int n;
    private static int cap;
    private static String[] name;
    private static String dump;

    public int KnapsackLuggage(int[] val, int[] wt, int cap) {
        int n = val.length;
        int[][] m = new int[n][cap + 1];
    
        // Fill first column and row with 0 since they have no value
        for(int i = 0; i < n; i++)
            m[i][0] = 0;
    
        // If only 1 weight is given, it will be taken if not more than capacity.
        for(int j = 0; j <= cap; j++) {
            if(wt[0] <= j)
                m[0][j] = val[0];
        }
    
        // process all sub-arrays for all the capacities
        for(int i = 1; i < n; i++) {
            for(int k = 1; k <= cap; k++) {
                int value1 = 0;
                int value2 = 0;
                
                if(wt[i] <= k)  // if not more than the capacity, include the item.
                    value1 = val[i] + m[i-1][k-wt[i]];
                value2 = m[i-1][k]; // exclude item
                m[i][k] = Math.max(value1, value2); // get the maximum profits
            }
        }
    
        printItemsSelected(m, wt, val, cap);
        return m[n-1][cap];
      }
    
    public static void getInputs() {
        Scanner sc = new Scanner(System.in);
        cap = 30;   // Hard coded capacity by the airlines
        System.out.print("Max Weight : " + cap);

        while(n <= 0) {
            System.out.print("\nNumber of items: ");
            n = sc.nextInt();
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
    
    private void printItemsSelected(int m[][], int[] weights, int[] values, int capacity){
        System.out.print("\n------------------------------------------\n");
        System.out.print("\nItems to be included in the Luggage:");
        int totalProfit = m[weights.length-1][capacity];
        for(int i = weights.length - 1; i > 0; i--) {
            if(totalProfit != m[i - 1][capacity]) {
            System.out.print("\n\t> " + name[i]);
            capacity -= weights[i];
            totalProfit -= values[i];
        }
    }
    
        if(totalProfit != 0)
            System.out.print("\n\t> " + name[0]);
        System.out.println("");
     }
    public static void main(String[] args) {
        KnapDynamic knapd = new KnapDynamic();
        System.out.print("\n---------------------------- ");
        System.out.print("WELCOME TO AGREEGE AIRLINES!");
        System.out.print(" ----------------------------\n\n");
        getInputs();
        int maxProfit = knapd.KnapsackLuggage(val, wt, cap);
        System.out.println("\nTotal Profit: " + maxProfit);
        System.out.println("");
    } 
}

