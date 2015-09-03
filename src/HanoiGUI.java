// Link to Learn Workshop
// Towers of Hanoi Demonstration
// This handout demonstrates using recursion to solve a problem that does not
// have a straightforward non-recursive solution.

import javax.swing.*; 
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;  // Needed for ActionEvent and ActionListener

public class HanoiGUI extends JFrame implements Runnable
{   
     private Tower [] theTowers;
     private ShapePanel drawPanel;
     private JPanel bPanel;
     private JButton runDemo, playGame;
     private JButton [] towerButtons;
     private JLabel info;
     private Thread runThread;
     private int size, delay;
     private Rectangle2D currDisk;
     private boolean selected;

     public HanoiGUI()
     {
            super("Towers of Hanoi Exercise");
            drawPanel = new ShapePanel(400, 300);

            runDemo = new JButton("Run Demo");
            playGame = new JButton("Play Game");
            
            ButtonHandler bhandler = new ButtonHandler();
            runDemo.addActionListener(bhandler);
            playGame.addActionListener(bhandler);

            TowerHandler thandler = new TowerHandler();
            bPanel = new JPanel();
            bPanel.setLayout(new GridLayout(1,3));

            towerButtons = new JButton[3];
            for (int i = 0; i < 3; i++)
            {
                 towerButtons[i] = new JButton("Tower " + i);
                 towerButtons[i].setVisible(false);
                 towerButtons[i].setEnabled(false);
                 towerButtons[i].addActionListener(thandler);
                 bPanel.add(towerButtons[i]);
            }

            info = new JLabel("Choose to Play or Run the Demo", (int) CENTER_ALIGNMENT);
            
            Container c = getContentPane();
            c.add(drawPanel, BorderLayout.CENTER);
            c.add(runDemo, BorderLayout.WEST);
            c.add(playGame, BorderLayout.EAST);
            c.add(bPanel, BorderLayout.SOUTH);
            c.add(info, BorderLayout.NORTH);

            theTowers = new Tower[3];

            runThread = null;
		
            setSize(580, 420);
            show();
     }

     /* Note that by far MOST of the code for this program deals with the graphics
        and the user interface.  The short subprogram below actually solves the
        Towers of Hanoi problem.  It is so short because it calls itself recursively,
        as you can see in the code
     */

     public void solveHanoi(int currsize, int start, int middle, int end)
     {
            if (currsize == 1)
                  theTowers[start].move(theTowers[end]);
            else
            {
                  solveHanoi(currsize-1, start, end, middle);
                  theTowers[start].move(theTowers[end]);
                  solveHanoi(currsize-1, middle, start, end);
            }
     }

     public void run()
     {
            solveHanoi(size, 0, 1, 2);
            playGame.setEnabled(true);
            runDemo.setEnabled(true);
            info.setText("Choose to Play or Run the Demo");
     }


     public static void main(String [] args)
     {
            Hanoi win = new Hanoi();

            win.addWindowListener(
                new WindowAdapter() 
                {
                    public void windowClosing(WindowEvent e)
                    { System.exit(0); }
                }
            ); 
     }

     private class ButtonHandler implements ActionListener
     {
            public void actionPerformed(ActionEvent e)
            {
                 if (e.getSource() == runDemo)
                 {
                       playGame.setEnabled(false);
                       runDemo.setEnabled(false);
                       do
                       {
                             size = Integer.parseInt(
                                   JOptionPane.showInputDialog(HanoiGUI.this, 
                                                       "How many disks? (<= 10)")); 
                       } while (size < 1 || size > 10);

                       do
                       {
                             delay = Integer.parseInt(
                                  JOptionPane.showInputDialog(HanoiGUI.this, 
                                                              "Delay between moves?")); 
                       } while (delay < 0);
                       for (int i = 0; i < 3; i++)
                            theTowers[i] = new Tower(size, i);
                       theTowers[0].fill();
                  
                       info.setText("Running demonstration...please wait until it finishes");    
                       if (runThread == null || (!runThread.isAlive()))
                       {
                            runThread = new Thread(HanoiGUI.this);
                            runThread.start();
                       }
                       repaint();
                 } 
                 else if (e.getSource() == playGame)
                 {
                       if (playGame.getText() == "Play Game")
                       {
                             runDemo.setEnabled(false);
                             do
                             {
                                   size = Integer.parseInt(
                                          JOptionPane.showInputDialog(HanoiGUI.this, 
                                                       "How many disks? (<= 10)")); 
                             } while (size < 1 || size > 10);

                             for (int i = 0; i < 3; i++)
                                  theTowers[i] = new Tower(size, i);
                             theTowers[0].fill();
                             
                             for (int i = 0; i < 3; i++)
                             {
                                  towerButtons[i].setVisible(true);
                             }
                             towerButtons[0].setEnabled(true);
                             selected = false;
                             info.setText("Select Tower to REMOVE disk from");
                             playGame.setText("Stop Game");
                       }
                       else if (playGame.getText() == "Stop Game")
                       {
                             runDemo.setEnabled(true);
                             for (int i = 0; i < 3; i++)
                             {
                                  towerButtons[i].setVisible(false);
                                  towerButtons[i].setEnabled(false);
                             }
                             info.setText("Choose to Play or Run the Demo");
                             playGame.setText("Play Game");
                       }
                 }
            }
     } 

     private class TowerHandler implements ActionListener
     {
            public void actionPerformed(ActionEvent e)
            {
                 for (int i = 0; i < 3; i++)
                 {
                      if (e.getSource() == towerButtons[i])
                      {
                            if (!selected)
                            {
                                 selected = true;
                                 currDisk = theTowers[i].remove();
                                 int wid = (int) currDisk.getWidth();
                                 for (int j = 0; j < 3; j++)
                                 {
                                      if (theTowers[j].isEmpty() || 
                                               wid < theTowers[j].topWidth())
                                            towerButtons[j].setEnabled(true);
                                      else
                                            towerButtons[j].setEnabled(false);
                                 }
                                 info.setText("Select Tower to ADD disk to");
                            }
                            else
                            {
                                 selected = false;
                                 theTowers[i].add(currDisk);
                                 for (int j = 0; j < 3; j++)
                                 {
                                      if (!theTowers[j].isEmpty())
                                           towerButtons[j].setEnabled(true);
                                      else
                                           towerButtons[j].setEnabled(false);
                                 }
                                 info.setText("Select Tower to REMOVE disk from");
                            }
                            repaint();
                            break;
                      }
                 }
            }
     } 

     private class ShapePanel extends JPanel
     {
      
         private int prefwid, prefht;
      
         public ShapePanel (int pwid, int pht)
         {
          
             prefwid = pwid;   
             prefht = pht;     
         }

         public Dimension getPreferredSize()
         {
             return new Dimension(prefwid, prefht);
         }
            
         public void paintComponent (Graphics g)       
         {                                             
             super.paintComponent(g); 
             Graphics2D g2d = (Graphics2D) g; 
             for (int i = 0; i < 3; i++) 
                  if (theTowers[i] != null)                  
                      theTowers[i].draw(g2d);
         }
     }  

     private class Tower
     {
         public final int MAXWIDTH = 100, HEIGHT = 25;
         private Rectangle2D [] disks;
         private int number, xoffset;
     
         public Tower(int maxsize, int id)
         {
             disks = new Rectangle2D[maxsize];
             number = 0;
             xoffset = (id * (MAXWIDTH + 20)) + 20;
         }

         public void fill()
         {
             int width = MAXWIDTH;
             for (int i = 0; i < disks.length; i++)
             {
                    int x = xoffset + (MAXWIDTH - width)/2;
                    int y = 300 - ((i+1) * HEIGHT);
                    disks[i] = new Rectangle2D.Double(x, y, width, 25);
                    width = width - 10;
             }
             number = disks.length;
         }

         public void move(Tower dest)
         {
             try
             {
                 Thread.sleep(delay);
             }
             catch(InterruptedException e) {}

             Rectangle2D curr = this.remove();
             dest.add(curr);
             repaint();
         }

         public Rectangle2D remove()
         {
             Rectangle2D curr = disks[number - 1];
             number--;
             return curr;
         }

         public void add(Rectangle2D curr)
         {
             int width = (int) curr.getWidth();
             int x = xoffset + (MAXWIDTH - width)/2;
             int y = 300 - ((number+1) * HEIGHT);
             curr.setFrame(x, y, width, 25);
             disks[number] = curr;
             number++;
         }

         public boolean isEmpty()
         {
             return (number == 0);
         }

         public int topWidth()
         {
             return ( (int) disks[number-1].getWidth());
         }
             
         public void draw(Graphics2D g2d)
         {
             for (int i = 0; i < number; i++)
             {
                  g2d.setColor(Color.red);
                  g2d.fill(disks[i]);
                  g2d.setColor(Color.black);
                  g2d.draw(disks[i]);
             }
         }

    }
                              
}

