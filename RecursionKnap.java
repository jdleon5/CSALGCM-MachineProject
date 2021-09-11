import java.util.*;
import java.io.*;
import java.lang.reflect.Array;
import java.util.Random;

public class RecursionKnap
{
    // dump
    private static String dump;

    // Total number of items
    static int n = 0;
    
    // Capacity of the knapsack
    static int capacity = 30;
    
    // To compute the items picked. 
    // Size is capacity+1 because the index is the capacity left
    // has the value of the item if that item's value is computed and '0' if not computed
    static int seen[][]= new int[capacity+1][capacity+1];
    
   // Stores the values of the items
    static int[] value = new int[n];    
    
    // Stores the size/weight of the items
    static int[] size = new int[n];  
    
    // Stores the name of the items
    static String[] name = new String[n]; 

    // Stores the items which is to picked for maximized value
    // has the value '1' if that item picked and '0' if not picked
    static int[][] keep = new int[capacity+1][capacity+1];
    
/*===========================================================================================
 Method which computes the optimal value and items to be picked 
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
                // current item picked and value stored
                keep[index][capacity] = 1;
                seen[index][capacity] = value[0];
                return value[0];
            }
            else
            {
                // current item not picked as its size exceeds the capacity of the sack
                // hence value not stored
                keep[index][capacity] = 0;
                seen[index][capacity] = 0;
                return 0;
            }
        }
        
        // if i pick the item the size of the item will either be equal to the capacity
        // or less than that. Hence i will pick it up
        // picking up increases value but reduces the capacity of the sack
        if(size[index] <= capacity)
            take = knapsack(index-1, capacity - size[index]) + value[index];
           
        // Choice 2 - of not picking it up.
            dontTake = knapsack(index-1 , capacity);
        
            // optimal solution, Maximized value stored
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
        Scanner sc = new Scanner(System.in);

    while(n <= 0) {
        System.out.print("\nNumber of items: ");
        n = sc.nextInt();
    }

    name = new String[20];
    size = new int[20];
    value = new int[20];

        for(int i = 0; i < n; i++) {
            dump = sc.nextLine(); 
            System.out.print("\nItem name: ");
            name[i] = sc.nextLine();
            while(size[i] <= 0){
                System.out.print("Item weight: ");
                size[i] = sc.nextInt();
            }
            while(value[i] <= 0){
                System.out.print("Item value: ");
                value[i] = sc.nextInt();
            }
        }
    }
    
    public static void main(String args[])
    {    
        getInputs();
        System.out.println("---------------------------");
        // printing the sizes of items
        System.out.println("The size array");
        System.out.println("---------------------------");
        for(int i=0 ; i < n ; i++)
            System.out.println("item" +(i+1) +" "+ size[i]); 
        System.out.println("---------------------------");
        System.out.println("");
        System.out.println("---------------------------");
        // printing the values of items
        System.out.println("The value array");
        System.out.println("---------------------------");
        for(int i=0 ; i < n ; i++)
            System.out.println("item" +(i+1) +" "+ value[i]);
        System.out.println("---------------------------");
        System.out.println("");
        System.out.println("---------------------------");
        System.out.println("the max value is");
        System.out.println(knapsack(n-1,capacity));
        System.out.println("---------------------------");
        System.out.println("");
        System.out.println("---------------------------");
        /* we will get the items which gives the optimal solution by this block
         * They are present at the position 
         * [the item number picked][capacity left after picking up the previous item]
         */
    
        System.out.println("items kept");
        System.out.println("---------------------------");
        while(n >= 0)
        {
            if(keep[n][capacity] == 1)
            {
                System.out.println("Item: "+name[n]);
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
        System.out.println("---------------------------");
    }
}