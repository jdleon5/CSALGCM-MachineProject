import java.util.*;
import java.awt.*;
import java.io.*;
import javax.swing.*;

public class KnapDynamic {
    private static float[] ratio;
    private static int[] wt;
    private static int[] val;
    private static int n;
    private static final int cap = 20;
    private static String[] name;
    private static String dump;
    //private dynamicGUI dgui;
    private String text;
    private int maxProfit;
    private JFrame textF;
    private JTextArea log;
    private JScrollPane scroll;

    public KnapDynamic() {
        initGUI();
    }

    private void initGUI() {
        JFrame frame = new JFrame("Agreege Airlines: Dynamic Luggage");
        JFrame mainf = new JFrame();
        JButton nxt = new JButton("CONTINUE");
        frame.setSize(500, 300);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        JLabel welcome = new JLabel("WELCOME TO AGREEGE AIRLINES!");
        JLabel welcp = new JLabel("<html>Agreege Airlines has a 20kg weight limit for every luggage that a passenger can carry. We are very strict when it comes to luggage weight because this affects numerous ways on how the plane will perform such as taking off, landing, and fuel consumption. </html>");
        welcome.setHorizontalAlignment(JLabel.CENTER);
        welcome.setVerticalAlignment(JLabel.CENTER);
        welcp.setHorizontalAlignment(JLabel.CENTER);
        welcp.setVerticalAlignment(JLabel.CENTER);
        frame.add(welcome, BorderLayout.NORTH);
        frame.add(welcp, BorderLayout.CENTER);
        frame.add(nxt, BorderLayout.SOUTH);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        nxt.addActionListener(e -> {
            frame.setVisible(false);
            initTextArea();
            getInputs();
            //start of algorithm
            long startTime = System.nanoTime();
            maxProfit = KnapsackLuggage(val, wt, cap); //log
            text = String.format("\n\n------------------------------------------\nTotal Profit: " + maxProfit + "\n");
            log.append(text);
            text = String.format("\nThank you for choosing Agreege Airlines!");
            log.append(text);


            //end of algorithm
            long endTime   = System.nanoTime();
            long totalTime = endTime - startTime;
            text = String.format("\n\nProgram Runtime: " + totalTime + " nanoseconds");
            log.append(text);
        });

        
    }
    private void initTextArea() {
        textF = new JFrame("Agreege Airlines: Dynamic Result");
        textF.setSize(370, 400);
        log = new JTextArea(23, 41);
        log.setBorder(BorderFactory.createLineBorder(Color.black));
        log.setLineWrap(true);
        log.setEditable(false);
        log.setVisible(true);
        scroll = new JScrollPane(log);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        textF.add(scroll, BorderLayout.CENTER);
        textF.setVisible(true);
        textF.setLocationRelativeTo(null);
        textF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

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
        JOptionPane.showMessageDialog(null, "Max Weight : " + cap);

        while(n <= 0) {
            n = Integer.parseInt(JOptionPane.showInputDialog("Number of items: "));
        }

        name = new String[n];
        wt = new int[n];
        val = new int[n];
        ratio = new float[n];
        
        for(int i = 0; i < n; i++) {
            name[i] = JOptionPane.showInputDialog("Item name: ");
            while(wt[i] <= 0){
                wt[i] = Integer.parseInt(JOptionPane.showInputDialog("Item weight: "));
            }
            while(val[i] <= 0){
                val[i] = Integer.parseInt(JOptionPane.showInputDialog("Item value: "));
            }
            ratio[i] = (float)val[i]/wt[i];
            //System.out.println(ratio[i]);
        }

        //Sort the inputs according to values
        String stemp;
        float ftemp = 0;  
        int itemp = 0;
        for(int i = 0; i < (n - 1); i++){  
            for(int j = 0; j < (n - i - 1); j++){  
                if(ratio[j] < ratio[j + 1]){  
                    // //swap elements  
                    ftemp = ratio[j];  
                    ratio[j] = ratio[j+1];  
                    ratio[j+1] = ftemp;  
                    //name
                    stemp = name[j];  
                    name[j] = name[j+1];  
                    name[j+1] = stemp;   
                    //weight
                    itemp = wt[j];  
                    wt[j] = wt[j+1];  
                    wt[j+1] = itemp;  
                    //value
                    itemp = val[j];  
                    val[j] = val[j+1];  
                    val[j+1] = itemp;  
                }  
                         
            }  
        }   
        System.out.println("< Sort according to ratio >");
        for(int i = 0; i < n; i++) {
            System.out.print(name[i] + "    ");
            System.out.println(ratio[i]);
        }
    }
    
    private void printItemsSelected(int m[][], int[] weights, int[] values, int capacity){
        text = String.format("\n------------------------------------------\n");
        log.append(text);
        text = String.format("\nItems to be included in the Luggage:");
        log.append(text);
        int totalProfit = m[weights.length-1][capacity];
        for(int i = weights.length - 1; i > 0; i--) {
            if(totalProfit != m[i - 1][capacity]) {
            text = String.format("\n\t> " + name[i]);
            log.append(text);
            capacity -= weights[i];
            totalProfit -= values[i];
        }
    }
        if(totalProfit != 0){
            text = String.format("\n\t> " + name[0]);
            log.append(text);
        }
            
        System.out.println("");
        
        
     }

    public static void main(String[] args) {
        KnapDynamic knapd = new KnapDynamic();
    } 
}