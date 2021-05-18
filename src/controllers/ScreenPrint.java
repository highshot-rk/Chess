/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.controllers;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import src.AllPieces;
import src.Screen;

/*
* service class for managing ScreenPrint
*/	
public class ScreenPrint extends JPanel{
   public static final long serialVersionUID = 10L;
   public MovePiece curMovePiece;
   public Image images[][];
   public String message1;
   public String message2;				
   public Screen.Coordinate selfHighlight;
   public List<Screen.Coordinate> listHighlights;
   public List<Screen.Coordinate> loadHighlights;		
   public Play play;		
   public transient Graphics2D graphics;		
   public Screen.Board screenBoard;
   public Timer timer;
   public boolean bAnimation;		
   public static final int nScreenSize = 100;		

   //screen construct
   public ScreenPrint(Play play) {
       this.play = play;
       this.screenBoard = play.screenBoard;
       this.listHighlights = new LinkedList<Screen.Coordinate>();
       this.loadHighlights = new LinkedList<Screen.Coordinate>();			
       bAnimation = false;
       images = new Image[6][6];
       images[0][0] = getPiece("bottomside", "king");
       images[0][1] = getPiece("bottomside", "queen");
       images[0][2] = getPiece("bottomside", "rook");
       images[0][3] = getPiece("bottomside", "bishop");
       images[0][4] = getPiece("bottomside", "knight");
       images[0][5] = getPiece("bottomside", "pawn");
       images[1][0] = getPiece("topside", "king");
       images[1][1] = getPiece("topside", "queen");
       images[1][2] = getPiece("topside", "rook");
       images[1][3] = getPiece("topside", "bishop");
       images[1][4] = getPiece("topside", "knight");
       images[1][5] = getPiece("topside", "pawn");
   }		

   //define paint function
   public void paint(Graphics g) 
   {
       graphics = (Graphics2D) g;
       super.paintComponent(graphics);
       makeScreenBoard();
       highlightCells(selfHighlight, loadHighlights, listHighlights);
       displayPieces();
       displayMovingPiece();
       notifyMessage();
   }

   // make board of screen
   private void makeScreenBoard() {
       int size  = 8;
       for(int i = 0; i < size; i++) 
       {
           for(int j = 0; j < size; j++) 
           {
               if(!((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1))) 
               {
                   graphics.setColor(Color.orange);
               }
               else 
               {
                   graphics.setColor(Color.LIGHT_GRAY);
               }

           graphics.fillRect(i * nScreenSize, j * nScreenSize, nScreenSize, nScreenSize);
       }
       }
   }

   //display all pieces on cell of main Board
   private void displayPieces() {
       AllPieces.Piece[][] pieces = this.screenBoard.pieceBoards;
       for(int i = 0; i < pieces.length; i++) {
           for(int j = 0; j < pieces.length; j++) {
               if(pieces[i][j] != null && (curMovePiece == null ||	curMovePiece.curPiece != pieces[i][j])) 
               {	
                   displayPiece(pieces[i][j], i, j);
               }
           }
       }
   }

   //display all piece
   private void displayPiece(AllPieces.Piece piece, int x, int y) {
       //top team
       if(!piece.team.equals("Bottom")) 
       {
           if(AllPieces.Pawn.class.isInstance(piece))
               graphics.drawImage(images[1][5], nScreenSize * x, nScreenSize * y, null);	
           else if(AllPieces.Rook.class.isInstance(piece))
               graphics.drawImage(images[1][2], nScreenSize * x, nScreenSize * y, null);		
           else if(AllPieces.Knight.class.isInstance(piece))
               graphics.drawImage(images[1][4], nScreenSize * x, nScreenSize * y, null);	
           else if(AllPieces.Bishop.class.isInstance(piece))
               graphics.drawImage(images[1][3], nScreenSize * x, nScreenSize * y, null);	
           else if(AllPieces.Queen.class.isInstance(piece))
               graphics.drawImage(images[1][1], nScreenSize * x, nScreenSize * y, null);	
           else if(AllPieces.King.class.isInstance(piece))
               graphics.drawImage(images[1][0], nScreenSize * x, nScreenSize * y, null);
       }
       //bottom team
       else {

           if(AllPieces.Pawn.class.isInstance(piece))
               graphics.drawImage(images[0][5], nScreenSize * x, nScreenSize * y, null);	
           else if(AllPieces.Rook.class.isInstance(piece))
               graphics.drawImage(images[0][2], nScreenSize * x, nScreenSize * y, null);		
           else if(AllPieces.Knight.class.isInstance(piece))
               graphics.drawImage(images[0][4], nScreenSize * x, nScreenSize * y, null);	
           else if(AllPieces.Bishop.class.isInstance(piece))
               graphics.drawImage(images[0][3], nScreenSize * x, nScreenSize * y, null);	
           else if(AllPieces.Queen.class.isInstance(piece))
               graphics.drawImage(images[0][1], nScreenSize * x, nScreenSize * y, null);	
           else if(AllPieces.King.class.isInstance(piece))
               graphics.drawImage(images[0][0], nScreenSize * x, nScreenSize * y, null);	
       }
   }

   // get pieces
   private Image getPiece(String team, String piece)
   {			
       ImageIcon icon = new ImageIcon(getClass().getResource("/img/" + team + "_" + piece + ".png"));
       Image image = icon.getImage().getScaledInstance(nScreenSize, nScreenSize, Image.SCALE_SMOOTH);
       icon = new ImageIcon(image, icon.getDescription());
       Image result =  icon.getImage();
       return result;
   }


   //clear all highlights
   public void clearHighlights() {
       this.selfHighlight = null;
       this.listHighlights.clear();
       this.loadHighlights.clear();
   }

   //draw for cell of highlights
   public void highlightCells(Screen.Coordinate source, List<Screen.Coordinate> moves, List<Screen.Coordinate> cells) {
       int x, y;

       if(source != null) {
           graphics.setColor(new Color(0x96, 0xFF, 0x96));
           graphics.fillRect(source.screenX * nScreenSize, source.screenY * nScreenSize, nScreenSize, nScreenSize);
       }

       for(Screen.Coordinate cell: cells) 
       {
           x = cell.screenX;
           y = cell.screenY;

           if((x % 2 == 0 && y % 2 == 0) || (x % 2 == 1 && y % 2 == 1)) 
           {
               graphics.setColor(new Color(200, 230, 255));
           } 
           else 
           {
               graphics.setColor(new Color(125, 200, 255));
           }

           graphics.fillRect(x * nScreenSize, y * nScreenSize, nScreenSize, nScreenSize);
       }


       for(Screen.Coordinate move: moves) {
           x = move.screenX;
           y = move.screenY;

           if((x % 2 == 0 && y % 2 == 0) || (x % 2 == 1 && y % 2 == 1)) 
           {
               graphics.setColor(new Color(255, 80, 80));
           } 
           else 
           {
               graphics.setColor(new Color(200, 0, 0));
           }

           graphics.fillRect(x * nScreenSize, y * nScreenSize, nScreenSize, nScreenSize);
       }
   }

   //draw move
   public void drawMove(AllPieces.Piece piece, Screen.Coordinate tile) { 
       final double oldX = piece.screenCoordinate.screenX * nScreenSize;
       final double oldY = piece.screenCoordinate.screenY * nScreenSize;
       final double newX = tile.screenX * nScreenSize;
       final double newY = tile.screenY * nScreenSize;
       curMovePiece = new MovePiece(piece);
       final int totalAniTime = 250;
       final int nFramePerSecond = 60;			
       int frameRate = totalAniTime / nFramePerSecond;	



       timer = new Timer(frameRate, new ActionListener() 
       {	

           public int framRemain = nFramePerSecond;
           public double x = oldX;
           public double y = oldY;

           public void actionPerformed(ActionEvent e) {
               if(framRemain != 0) 
               {	            		            		
                    x = x + (newX - oldX / nFramePerSecond); 
                    y = y + (newY - oldY / nFramePerSecond);
                    curMovePiece.pieceX = x;
                    curMovePiece.pieceY = y;
                    framRemain--;
                    bAnimation = true;	
               }
               else {	            		
                    curMovePiece = null;
                    bAnimation = false;	            		
                    timer.stop();	            		
               }
               repaint();
           }
       });
       timer.restart();
   }

   //display piece of moving
   private void displayMovingPiece() {
       if(curMovePiece != null)
       {				
           String strTeam = curMovePiece.curPiece.team;

           if(strTeam.equals("Bottom")) 
           {
               if(AllPieces.Pawn.class.isInstance(curMovePiece.curPiece)) 
                   graphics.drawImage(images[0][5], (int) curMovePiece.pieceX, (int) curMovePiece.pieceY, null);	
               else if(AllPieces.Rook.class.isInstance(curMovePiece.curPiece))
                   graphics.drawImage(images[0][2], (int) curMovePiece.pieceX, (int) curMovePiece.pieceY, null);
               else if(AllPieces.Knight.class.isInstance(curMovePiece.curPiece)) 
                   graphics.drawImage(images[0][4], (int) curMovePiece.pieceX, (int) curMovePiece.pieceY, null);
               else if(AllPieces.Bishop.class.isInstance(curMovePiece.curPiece))
                   graphics.drawImage(images[0][3], (int) curMovePiece.pieceX, (int) curMovePiece.pieceY, null);
               else if(AllPieces.Queen.class.isInstance(curMovePiece.curPiece))
                   graphics.drawImage(images[0][1], (int) curMovePiece.pieceX, (int) curMovePiece.pieceY, null);
               else if(AllPieces.King.class.isInstance(curMovePiece.curPiece))
                   graphics.drawImage(images[0][0], (int) curMovePiece.pieceX, (int) curMovePiece.pieceY, null);
           }
           else {
               if(AllPieces.Pawn.class.isInstance(curMovePiece.curPiece)) 
                   graphics.drawImage(images[1][5], (int) curMovePiece.pieceX, (int) curMovePiece.pieceY, null);	
               else if(AllPieces.Rook.class.isInstance(curMovePiece.curPiece))
                   graphics.drawImage(images[1][2], (int) curMovePiece.pieceX, (int) curMovePiece.pieceY, null);
               else if(AllPieces.Knight.class.isInstance(curMovePiece.curPiece)) 
                   graphics.drawImage(images[1][4], (int) curMovePiece.pieceX, (int) curMovePiece.pieceY, null);
               else if(AllPieces.Bishop.class.isInstance(curMovePiece.curPiece))
                   graphics.drawImage(images[1][3], (int) curMovePiece.pieceX, (int) curMovePiece.pieceY, null);
               else if(AllPieces.Queen.class.isInstance(curMovePiece.curPiece))
                   graphics.drawImage(images[1][1], (int) curMovePiece.pieceX, (int) curMovePiece.pieceY, null);
               else if(AllPieces.King.class.isInstance(curMovePiece.curPiece))
                   graphics.drawImage(images[1][0], (int) curMovePiece.pieceX, (int) curMovePiece.pieceY, null);
           }
       }			
   }

   //display notify message
   public void notifyMessage() {
       if(!(message1 != null && message2 != null)) {
           return;
       }				
       graphics.setFont(new Font("Times New Roman", Font.PLAIN, 33));
       graphics.setColor(Color.RED);
       FontMetrics fontMetrics = getFontMetrics(graphics.getFont());
       int sX = ((nScreenSize * 8) - (fontMetrics.stringWidth(message1))) / 2;
       int sY = ((nScreenSize * 8) - (fontMetrics.getHeight() / 2)) / 2;
       graphics.drawString(message1, sX, sY);
       sX = ((nScreenSize * 8) - (fontMetrics.stringWidth(message2))) / 2;
       sY += fontMetrics.getHeight();
       graphics.drawString(message2, sX, sY);			
   }
}