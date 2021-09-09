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

        int[][] mat = new int[n + 1][w + 1];
        for (int r = 0; r < w + 1; r++) {
            mat[0][r] = 0;
        }
        for (int c = 0; c < n + 1; c++) {
            mat[c][0] = 0;
        }
        
        for (int item = 1; item <= n; item++) {
            for (int capacity = 1; capacity <= w; capacity++) {
                int maxValWithoutCurr = mat[item - 1][capacity]; 
                int maxValWithCurr = 0; 
                
                int weightOfCurr = wt[item - 1]; 
                if (capacity >= weightOfCurr) {  
                    maxValWithCurr = val[item - 1]; 
                    
                    int remainingCapacity = capacity - weightOfCurr;
                    maxValWithCurr += mat[item - 1][remainingCapacity]; 
                }
                
                mat[item][capacity] = Math.max(maxValWithoutCurr, maxValWithCurr); 
            }
        }
        
        System.out.println(mat[n][w]); 
        System.out.println(Arrays.deepToString(mat).replace("],", "],\n"));
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

