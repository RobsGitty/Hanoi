/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *
 * http://interactivepython.org/runestone/static/pythonds/Recursion/TowerofHanoi.html
 * 
 * http://mathworld.wolfram.com/TowerofHanoi.html
 *
 * https://www.mathsisfun.com/games/towerofhanoi.html
 * 
 * http://www.cut-the-knot.org/recurrence/hanoi.shtml
 * 
 * http://simpledeveloper.com/towers-of-hanoi/
 *
 * http://www.mathcs.emory.edu/~cheung/Courses/170/Syllabus/13/hanoi.html
 *
 * http://www.sanfoundry.com/java-program-implement-solve-tower-of-hanoi-using-stacks/
 *
 * http://www.tutorialspoint.com/javaexamples/method_tower.htm
 * 
 * http://www.javawithus.com/programs/towers-of-hanoi
 *
 * http://mathforum.org/dr.math/faq/faq.tower.hanoi.html
 *
 */
package hanoi;

import java.util.Scanner;
import java.util.*;

/**
 *
 * @author Rob
 */
public class Hanoi {
    
    public static int N;
    
    /* Creating Stack array  */
    public static Stack<Integer>[] tower = new Stack[4]; 
     
    
    public void solve(int n, String start, String auxiliary, String end) {
       if (n == 1) {
           System.out.println(start + " -> " + end);
       } else {
           solve(n - 1, start, end, auxiliary);
           System.out.println(start + " -> " + end);
           solve(n - 1, auxiliary, start, end);
       }
   }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Hanoi towersOfHanoi = new Hanoi();
        System.out.print("Enter number of discs: ");
        Scanner scanner = new Scanner(System.in);
        
        tower[1] = new Stack<Integer>();
        tower[2] = new Stack<Integer>();
        tower[3] = new Stack<Integer>();
        
        /* Accepting number of disks */     
        int discs = scanner.nextInt();
        N = discs;
        toh(discs);
        
        
        towersOfHanoi.solve(discs, "A", "B", "C");
    }
    
    
    
    /* Function to push disks into stack */
    public static void toh(int n)
    {
        for (int d = n; d > 0; d--) {
            tower[1].push(d);
        }     
        display();
        move(n, 1, 2, 3);         
    }
    
    
    /* Recursive Function to move disks */
    public static void move(int n, int a, int b, int c)
    {
        if (n > 0)
        {
            move(n-1, a, c, b);     
            int d = tower[a].pop();                                             
            tower[c].push(d);
            display();                   
            move(n-1, b, a, c);     
        }         
    }

    /*  Function to display */
    public static void display()
    {
        System.out.println("  A  |  B  |  C");
        System.out.println("---------------");
        for(int i = N - 1; i >= 0; i--)
        {
            String d1 = " ", d2 = " ", d3 = " ";
            try
            {
                d1 = String.valueOf(tower[1].get(i));
            }
            catch (Exception e){
            }    
            try
            {
                d2 = String.valueOf(tower[2].get(i));
            }

            catch(Exception e){
            }
            try
            {
                d3 = String.valueOf(tower[3].get(i));
            }
            catch (Exception e){
            }
            System.out.println("  "+d1+"  |  "+d2+"  |  "+d3);
        }
        System.out.println("\n");         
    }
    
}
