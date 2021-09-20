import java.util.*;
import java.io.*;
import java.lang.reflect.Array;
import javax.swing.*;
import java.awt.*;

public class RecursionKnap
{
    private JFrame textF;
    private JTextArea log;
    private JScrollPane scroll;
    private String text;
    // dump
    private static String dump;

    // Total number of items
    static int n = 0;
    
    // Capacity of the knapsack
    static int capacity = 20;
    
    // To compute the items picked. 
    // Size is capacity+1 because the index is the capacity left
    // has the val of the item if that item's val is computed and '0' if not computed
    static int seen[][]= new int[capacity+1][capacity+1];
    
   // Stores the vals of the items
    static int[] val = new int[n];    
    
    // Stores the size/weight of the items
    static int[] size = new int[n];  
    
    // Stores the name of the items
    static String[] name = new String[n]; 

    // Stores the items which is to picked for maximized val
    // has the val '1' if that item picked and '0' if not picked
    static int[][] keep = new int[capacity+1][capacity+1];
    
    //gui
    public RecursionKnap() {
        initGUI();
    }

    private void initGUI() {
        //dgui = new dynamicGUI();
        // JButton confirm = gui.getConfirm();
        // JButton clear = gui.getClear();
        // JTextArea log = gui.getTextArea();
        JFrame frame = new JFrame("Agreege Airlines: Recursive Solution for Luggage");
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
        //frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        //text = String.format("\n---------------------------- WELCOME TO AGREEGE AIRLINES! ----------------------------\n\n");
        //JOptionPane.showMessageDialog(null, "\n---------------------------- WELCOME TO AGREEGE AIRLINES! ----------------------------\n\n");
        
        //log.append(text);
        nxt.addActionListener(e -> {
            frame.setVisible(false);
            initTextArea();
        getInputs();

        long startTime = System.nanoTime();

        text = String.format("The maximum value is " + knapsack(n-1,capacity) + "\n");
        log.append(text);

        text = String.format("Items kept are the following: \n");
        log.append(text);

        while(n >= 0)
        {
            if(keep[n][capacity] == 1)
            {
                text = String.format("\n>Item: "+name[n]);
                log.append(text);
                // item picked hence capacity reduced
                capacity-=size[n];
                // moving to next item, one row above
                n--;                
            }
            else
            {
                n--;
            }
        }  
    long endTime   = System.nanoTime();
    long totalTime = endTime - startTime;
    text = String.format("\n\nProgram Runtime: " + totalTime + " nanoseconds");
    log.append(text);
        });
    }
        private void initTextArea() {
            textF = new JFrame("Agreege Airlines: Recursive Solution Result");
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

/*===========================================================================================
 Method which computes the optimal val and items to be picked 
 We start with the last element and climb up to the first item.
 ===========================================================================================*/
    static int knapsack(int index, int capacity)
    {
        // Sum if the items picked/taken and not picked/not taken
        int take = 0, dontTake = 0;
        
        // for the first item
        if(index==0)
        {
            // capacity of the sack will not be exceeded if item0 picked
            if(size[0] <= capacity)
            {
                // current item picked and val stored
                keep[index][capacity] = 1;
                seen[index][capacity] = val[0];
                return val[0];
            }
            else
            {
                // current item not picked as its size exceeds the capacity of the sack
                // hence val not stored
                keep[index][capacity] = 0;
                seen[index][capacity] = 0;
                return 0;
            }
        }
        
        // if i pick the item the size of the item will either be equal to the capacity
        // or less than that. Hence i will pick it up
        // picking up increases val but reduces the capacity of the sack
        if(size[index] <= capacity)
            take = knapsack(index-1, capacity - size[index]) + val[index];
           
        // Choice 2 - of not picking it up.
            dontTake = knapsack(index-1 , capacity);
        
            // optimal solution, Maximized val stored
        seen[index][capacity] = Math.max(take, dontTake);
        
        // if the item which i am picking gives the optimal solution then
        // i will store it
        if( take>dontTake)
        {
            keep[index][capacity] = 1;
        }
        else
        {
            keep[index][capacity] = 0;
        }
        // returning the optimal solution
        return seen[index][capacity];
        
    }
    static void getInputs()
    {
        JOptionPane.showMessageDialog(null, "Max Weight : " + capacity);

        while(n <= 0) {
            //System.out.print("\nNumber of items: ");
            n = Integer.parseInt(JOptionPane.showInputDialog("Number of items: "));
            //n = sc.nextInt();
        }

    name = new String[20];
    size = new int[20];
    val = new int[20];

            for(int i = 0; i < n; i++) 
            {
            name[i] = JOptionPane.showInputDialog("Item name: ");
                while(size[i] <= 0)
                {
                    size[i] = Integer.parseInt(JOptionPane.showInputDialog("Item weight: "));
                }
                while(val[i] <= 0)
                {
                    val[i] = Integer.parseInt(JOptionPane.showInputDialog("Item value: "));
                }  
            }
            //sort
            bubbleSort(size, name, val, n);
    }

        public static void bubbleSort(int[] size, String[] name, int[] val, int n) {
            boolean sorted = false;
            int tempSize, tempVal;
            String tempName;
            while(!sorted) {
                sorted = true;
                for (int i = 0; i < n-1; i++) {
                    if (size[i] > size[i+1]) 
                    {
                        tempSize = size[i];
                        tempVal = val[i];
                        tempName = name[i];

                        size[i] = size[i+1];
                        name[i] = name[i+1];
                        val[i] = val[i+1];

                        size[i+1] = tempSize;
                        name[i+1] = tempName;
                        val[i+1] = tempVal;

                        sorted = false;
                    }
                }
            }
        }    
    
    public static void main(String args[])
    {    
        RecursionKnap knapr = new RecursionKnap();
        return;
    }
}