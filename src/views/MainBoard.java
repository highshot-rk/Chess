/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import static src.Chess.screen;
import src.controllers.Logger;
import src.controllers.ScreenPrint;

/**
 *
 * @author Simkoniv-PC
 */
 
    /*
     *define main board class 
     */
public class MainBoard{

    /*
     * jfram
     */
    private JFrame jframe = new JFrame("Chess Player");		
    public Insets insets;		
    /*
     * configure panel
     */
    public JPanel configurePanel = new JPanel(new FlowLayout());

    /*
     * save pgn button
     */
    public JButton btSave = new JButton("Save PGN");

    /*
     * restore pgn button
     */
    public JButton btRestore = new JButton("Restore PGN");

    /*
     * start button
     */
    public JButton btStart = new JButton("Start Game");

    /*
     * stop button
     */
    public JButton btStop = new JButton("Stop Game");

    /*
     * display clock
     */
    public JLabel statusLabel = new JLabel("00:00:00",JLabel.CENTER);
    public JLabel timeTracker = new JLabel("00:00:00",JLabel.CENTER);
    static int counter = 0;	    
    public AtomicBoolean running = new AtomicBoolean(false);
    public AtomicBoolean running1 = new AtomicBoolean(false);


    public Logger log = new Logger();
    /*
     * thread
     */
    public Thread t;

    public Thread at;

    public int ActionTime;
    public int ActionTime1;

    /*
     * interrupt function
     */
    public void interrupt() {
        running.set(false);
        t.interrupt();
    }

    /*
     * interrupt function
     */
    public void interrupt1() {
        running1.set(false);
    at.interrupt();
    }

    /*
     * check running state
     */
    public boolean isRunning1() {
        return running1.get();
    }

    /*
    * check running state
    */
    public boolean isRunning() {
        return running.get();
    }

    /*
     * constructor
     */
    public MainBoard(ScreenPrint screenPrint) 
    {
        String time = JOptionPane.showInputDialog("Set time");
        ActionTime = Integer.parseInt(time);
        ActionTime1 = ActionTime;
        t = new Thread(new Runnable() {
        @Override
        public void run() {	            	
            while (true) {
                try {
                    if(running.get())
                    {
                        //System.out.println("Thread reading counter is: " + counter);
                        counter ++;
                        int seconds = counter % 60;
                        int minutes = (counter - seconds) / 60;
                        String dispayTime = ""; 
                        dispayTime = minutes + "m " +  seconds + "s";
                        statusLabel.setText(dispayTime);
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    });

    t.start();

    at = new Thread(new Runnable() {
        @Override
        public void run() {	            	
            while (true) {
                try {
                    if(running1.get())
                    {
                        //System.out.println("Time1 reading counter is: " + ActionTime);
                        if(ActionTime1 == 0) {
                            ActionTime1 = ActionTime;
                        }
                        int seconds = ActionTime1 % 60;
                        int minutes = (ActionTime1 - seconds) / 60;
                        String dispayTime = ""; 
                        dispayTime = minutes + "m " +  seconds + "s";
                        timeTracker.setText(dispayTime);
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    });

    at.start();

            final int screenSize = ScreenPrint.nScreenSize * screenPrint.play.screenBoard.pieceBoards.length;					

            //when clicking start button
            btStart.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(screenPrint, "Game started");
                    DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                    Date dateobj = new Date();
                    String logo = df.format(dateobj) + ": Game started";

                    try {
                            log.setLoger(logo);
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }


                    if(t.isAlive())
                    {			   

                        running.set(true);			    
                    }
                    if(at.isAlive())
                    {			    		
                        running1.set(true);			    
                    }
                }
            });

            //when clicking stop button
            btStop.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {	
                    DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                    Date dateobj = new Date();
                    String logo = df.format(dateobj) + ": Game Stoped.";

                    try {
                            log.setLoger(logo);
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }

                    if(t.isAlive())
                    {
                        running.set(false);			    		
                    }
                    if(at.isAlive())
                    {
                        running1.set(false);			    		
                    }

                }
            });

            //when clicking save button
            btSave.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try			    	
                    {
                        final JFileChooser fc = new JFileChooser();			    					    		
                        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                        fc.addChoosableFileFilter(new FileNameExtensionFilter("PGN Data File (*.pgn)", "pgn"));

                        int returnVal = fc.showSaveDialog(configurePanel);

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fc.getSelectedFile();

                        String filename = file.getAbsolutePath() + ".pgn";
                        PrintWriter out = new PrintWriter(filename);
                        out.println(screenPrint.play.pgnString);
                        out.close();
                    } 

                       FileOutputStream myFileOutputStream = new FileOutputStream("game.dat");
                       ObjectOutputStream myObjectOutputStream = new ObjectOutputStream(myFileOutputStream);
                       myObjectOutputStream.writeObject(screen.screenBoard);
                       myObjectOutputStream.close();
                    }
                    catch (Exception e1)
                    {
                        System.out.println("Error when saving to file." + e1.getMessage()); 
                    }
                }
            });

            //when clicking restore button
            btRestore.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try
                    {
                        final JFileChooser fc = new JFileChooser();			    					    		
                        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                        fc.addChoosableFileFilter(new FileNameExtensionFilter("PGN Data File (*.pgn)", "pgn"));

                        int returnVal = fc.showOpenDialog(configurePanel);

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fc.getSelectedFile();

                        String filename = file.getAbsolutePath();
                        byte[] encoded = Files.readAllBytes(Paths.get(filename));
                        String content = new String(encoded, Charset.defaultCharset());
                        JOptionPane.showMessageDialog(null, content);
                    } 

                        FileInputStream myFileInputStream = new FileInputStream("game.dat");
                        ObjectInputStream myObjectInputStream = new ObjectInputStream(myFileInputStream);
                        screen.screenBoard = (src.Screen.Board) myObjectInputStream.readObject(); 
                        myObjectInputStream.close();
                        screenPrint.repaint();
                    }
                    catch (Exception e1)
                    {
                        System.out.println("Error when loading from file." + e1.getMessage());
                    }
                }
            });			

            //when closing windows
            jframe.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            if(t.isAlive())
                {
                    running.set(false);			    		
                }	            	
            System.exit(0);
        }
    });

    configurePanel.add(btStart);
    configurePanel.add(btStop);
    configurePanel.add(statusLabel);
    configurePanel.add(timeTracker);	
    configurePanel.add(btSave);
    configurePanel.add(btRestore);

    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(configurePanel, BorderLayout.SOUTH);
    mainPanel.add(screenPrint, BorderLayout.CENTER);

    jframe.add(mainPanel);
    jframe.setVisible(true);
    insets = jframe.getInsets();			

    jframe.setSize(screenSize + insets.left + insets.right -12,	screenSize + insets.top + insets.bottom + 20);			
    jframe.setResizable(false);
    }
}