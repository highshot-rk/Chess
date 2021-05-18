package src;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import src.CheckValid;
import src.controllers.Play;
import src.controllers.MovePiece;
import src.controllers.Logger;
import src.controllers.ScreenPrint;
import src.views.MainBoard;

/*
 *service class for Chess 
 */
public class Chess {
    /*
     * service class for moving piece
     */
    public static MainBoard main;

    /*
     * member human1 and human2
     */
    public static Human human1;
    public static Human human2;	

    public static ScreenPrint screen;

    /*
     * main function 
     */
    public static void main(String args[]) throws IOException {
            Play play = new Play(human1 = new Human(), human2= new Human());
            screen = new ScreenPrint(play);
            main = new MainBoard(screen);
            
            play._screenPrint = screen;
            
            Thread t = new Thread(new Runnable() {
        @Override
        public void run() {	            	
            while (true) {
                try {
                    if(main.running1.get())
                    {
                        if(play.changed) {
                            main.ActionTime1 = main.ActionTime;
                            play.changed = false;
                        }
                        //System.out.println("Time1 reading counter is: " + ActionTime);
                        main.ActionTime1--;
                        if(main.ActionTime1 == 0) {
                            play.changePlayer();
                            main.ActionTime1 = main.ActionTime;
                        }
                        int seconds = main.ActionTime1 % 60;
                        int minutes = (main.ActionTime1 - seconds) / 60;
                        String dispayTime = ""; 
                        dispayTime = minutes + "m " +  seconds + "s";
                        main.timeTracker.setText(dispayTime);
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    });

    t.start();
        new ProcessingMouseEvent(screen);
    }
}


