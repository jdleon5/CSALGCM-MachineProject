import java.util.*; 
import java.io.*;


public class RecursionKnap {

    private static int[] wt;
    private static int[] val;
    private static int n;
    private static int cap;
    private static String[] name;
    private static String dump;

    public int knapsackRec(int[] v, int[] w, int n, int cap) {
        if (n <= 0) { 
            return 0; 
        } else if (w[n - 1] > cap) {
            return knapsackRec(w, v, n - 1, cap);
        } else {
            return Math.max(knapsackRec(w, v, n - 1, cap), v[n - 1] 
            + knapsackRec(w, v, n - 1, cap - w[n - 1]));
        }
    }

    public static void getInputs()
    {
        Scanner sc = new Scanner(System.in);
        cap = 30;   // Hard coded capacity by the airlines
        System.out.print("Max Weight : " + cap + "kg");

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
    public static void main(String[] args) {
        RecursionKnap knapr = new RecursionKnap();
        System.out.print("\n---------------------------- ");
        System.out.print("WELCOME TO AGREEGE AIRLINES!");
        System.out.print(" ----------------------------\n\n");
        getInputs();
        int maxProfit = knapr.knapsackRec(val,  wt, n, cap);
        System.out.println("\nTotal Profit: " + maxProfit);
        System.out.println("");
    }

}