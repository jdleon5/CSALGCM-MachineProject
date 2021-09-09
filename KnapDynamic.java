import java.util.*; 
import java.io.*;

public class KnapDynamic {
    private static int[] wt;
    private static int[] val;
    private static int n;
    private static int w;
    private static String[] name;
    private static String dump;
    public static void main(String args[]) {
        KnapDynamic app = new KnapDynamic();
        getInputs();
        // int w = 10;
        // int n = 4;
        // int[] val = {10, 40, 30, 50};
        // int[] wt = {5, 4, 6, 3};

        // Populate base cases
        int[][] mat = new int[n + 1][w + 1];
        for (int r = 0; r < w + 1; r++) {
            mat[0][r] = 0;
        }
        for (int c = 0; c < n + 1; c++) {
            mat[c][0] = 0;
        }
        
        // Main logic
        for (int item = 1; item <= n; item++) {
            for (int capacity = 1; capacity <= w; capacity++) {
                int maxValWithoutCurr = mat[item - 1][capacity]; // This is guaranteed to exist
                int maxValWithCurr = 0; // We initialize this value to 0
                
                int weightOfCurr = wt[item - 1]; // We use item -1 to account for the extra row at the top
                if (capacity >= weightOfCurr) { // We check if the knapsack can fit the current item
                    maxValWithCurr = val[item - 1]; // If so, maxValWithCurr is at least the value of the current item
                    
                    int remainingCapacity = capacity - weightOfCurr; // remainingCapacity must be at least 0
                    maxValWithCurr += mat[item - 1][remainingCapacity]; // Add the maximum value obtainable with the remaining capacity
                }
                
                mat[item][capacity] = Math.max(maxValWithoutCurr, maxValWithCurr); // Pick the larger of the two
            }
        }
        
        System.out.println(mat[n][w]); // Final answer
        System.out.println(Arrays.deepToString(mat)); // Visualization of the table
    }

    public static void getInputs() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Number of items: ");
        n = sc.nextInt();
        System.out.print("Weight limit: ");
        w = sc.nextInt();

        name = new String[10];
        wt = new int[10];
        val = new int[10];
        
        for(int i = 0; i < n; i++) {
            dump = sc.nextLine(); 
            System.out.print("Item name: ");
            name[i] = sc.nextLine();
            System.out.println("name = " + name[i]);
            System.out.print("Item weight: ");
            wt[i] = sc.nextInt();
            System.out.println("w = " + wt[i]);
            System.out.print("Item value: ");
            val[i] = sc.nextInt();
            System.out.println("v = " + val[i]);
        }
    }
}

