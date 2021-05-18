/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import src.AllPieces;
import src.Chess;
import static src.Chess.main;
import src.Human;
import src.Screen;
import src.controllers.Logger;
import src.controllers.ScreenPrint;
/*
*service class for Play 
*/	
public class Play {
    /*
     * check valid state
     */
    public src.CheckValid checkValid;
    /*
     * on off
     */
    public int nTurned;
    /*
     * manage screenBorad
     */
    public Screen.Board screenBoard;

    /*
     * check thread state
     */
    public boolean checkRunning;

    /*
     * define player
     */
    public Human human1, human2, usingMan, otherMan;

    /*
     * current select piece
     */
    public AllPieces.Piece currentSelect;

    /*
     * final cap
     */
    public int finalCap;

    /*
     * final pawn
     */
    public int finalPawn;

    public Logger log = new Logger();

    /*
     * pgn string
     */
    public String pgnString = "[Event \"Chess Game\"]\n";

    /*
     * count move
     */
    public int MoveCount;


    public boolean changed = false;
    
    public ScreenPrint _screenPrint = null;
    
    public boolean _isFinishAutoPlay = false;
    /*
     * 
     * 
     * 
     * constructor 
     * @param Human h1, Human h2 
     */
    public Play(Human h1, Human h2) {
        MoveCount = 0;
        nTurned = 1;
        currentSelect = null;			
        checkRunning = true;			
        h1.initHuman("Bottom");
        h2.initHuman("Top");
        this.human1 = h1;
        this.human2 = h2;
        usingMan = human1;
        otherMan = human2;
        screenBoard = new Screen.Board();
        LayoutPieces();
        checkValid = new src.CheckValid(screenBoard);	
        LocalDate date = LocalDate.now();			
        pgnString =  pgnString + "[Date \"" + date.toString() + "\"]\n";
        pgnString = pgnString + "[White \"player1\"]\n" + "[Black \"player2\"]\n\n";
    }

    /*
     * layout all pieces
     */		
    private void LayoutPieces() {			
        int size = 8;				
        for(int i = 0; i < size; i++) {
            screenBoard.pieceBoards[i][6] = human1.listAll.get(8 + i);
            screenBoard.pieceBoards[i][1] = human2.listAll.get(8 + i);

            screenBoard.pieceBoards[i][6].screenCoordinate = new Screen.Coordinate(i, 6);
            screenBoard.pieceBoards[i][1].screenCoordinate = new Screen.Coordinate(i, 1);
        }

        screenBoard.pieceBoards[4][0] = human2.listAll.get(0);	
        screenBoard.pieceBoards[3][0] = human2.listAll.get(1);	
        screenBoard.pieceBoards[0][0] = human2.listAll.get(2);	 
        screenBoard.pieceBoards[2][0] = human2.listAll.get(3);	
        screenBoard.pieceBoards[1][0] = human2.listAll.get(4);	
        screenBoard.pieceBoards[7][0] = human2.listAll.get(5);	
        screenBoard.pieceBoards[5][0] = human2.listAll.get(6);	
        screenBoard.pieceBoards[6][0] = human2.listAll.get(7);

        screenBoard.pieceBoards[4][7] = human1.listAll.get(0);	
        screenBoard.pieceBoards[3][7] = human1.listAll.get(1);	
        screenBoard.pieceBoards[0][7] = human1.listAll.get(2);	
        screenBoard.pieceBoards[2][7] = human1.listAll.get(3);	
        screenBoard.pieceBoards[1][7] = human1.listAll.get(4);	
        screenBoard.pieceBoards[7][7] = human1.listAll.get(5);	
        screenBoard.pieceBoards[5][7] = human1.listAll.get(6);	
        screenBoard.pieceBoards[6][7] = human1.listAll.get(7);

        screenBoard.pieceBoards[4][7].screenCoordinate = new Screen.Coordinate(4, 7);
        screenBoard.pieceBoards[3][7].screenCoordinate = new Screen.Coordinate(3, 7);
        screenBoard.pieceBoards[0][7].screenCoordinate = new Screen.Coordinate(0, 7);
        screenBoard.pieceBoards[2][7].screenCoordinate = new Screen.Coordinate(2, 7);
        screenBoard.pieceBoards[1][7].screenCoordinate = new Screen.Coordinate(1, 7);
        screenBoard.pieceBoards[7][7].screenCoordinate = new Screen.Coordinate(7, 7);
        screenBoard.pieceBoards[5][7].screenCoordinate = new Screen.Coordinate(5, 7);
        screenBoard.pieceBoards[6][7].screenCoordinate = new Screen.Coordinate(6, 7);
        screenBoard.pieceBoards[4][0].screenCoordinate = new Screen.Coordinate(4, 0);
        screenBoard.pieceBoards[3][0].screenCoordinate = new Screen.Coordinate(3, 0);
        screenBoard.pieceBoards[0][0].screenCoordinate = new Screen.Coordinate(0, 0);
        screenBoard.pieceBoards[2][0].screenCoordinate = new Screen.Coordinate(2, 0);
        screenBoard.pieceBoards[1][0].screenCoordinate = new Screen.Coordinate(1, 0);
        screenBoard.pieceBoards[7][0].screenCoordinate = new Screen.Coordinate(7, 0);
        screenBoard.pieceBoards[5][0].screenCoordinate = new Screen.Coordinate(5, 0);
        screenBoard.pieceBoards[6][0].screenCoordinate = new Screen.Coordinate(6, 0);			
    }		

    /*
     * select location
     * @param Coordinate coord, ScreenPrint screenPrint
     */
    public void selectLocation(Screen.Coordinate coord, ScreenPrint screenPrint) {			

        screenPrint.clearHighlights();
        AllPieces.Piece pieces = screenBoard.getPieceXY(coord.screenX, coord.screenY);			
        List<Screen.Coordinate> coordList;
        boolean b = pieces != null && usingMan.equals(pieces.human) && currentSelect != pieces;
        if(b) 
        {
            currentSelect = pieces;
            coordList = pieces.accept(checkValid);
            screenPrint.listHighlights = coordList;
            screenPrint.selfHighlight = pieces.screenCoordinate;
            screenPrint.loadHighlights = checkValid.filterForEnemyHighlights(coordList, currentSelect);
        }

        else if(currentSelect != null) {
            coordList = currentSelect.accept(checkValid);
            if(coordList.contains(coord)) {
                screenPrint.drawMove(currentSelect, coord);
                movingAndCheck(currentSelect, coord, screenPrint);
            }
            currentSelect = null;
        }
    }


    /*
     * make x coordinate --> alphabet
     * @param int coorX
     * @return alpha string
     */

    public String alphabetFromXScreenX(int coorX)
    {
        String result = "";
        switch(coorX)
        {
        case 0:
            result =  "a";
            break;
        case 1:
            result =  "b";
            break;
        case 2:
            result =  "c";
            break;
        case 3:
            result =  "d";
            break;
        case 4:
            result =  "e";
            break;
        case 5:
            result =  "f";
            break;
        case 6:
            result =  "g";
            break;
        case 7:
            result =  "h";
            break;
        default:
            break;
        }

        return result;
    }


    /*
     * make current piece and cell
     * @param Piece piece, Coordinate cell
     */
    public void makePGNString(AllPieces.Piece piece, Screen.Coordinate cell)
    {
        MoveCount ++;						
        int oldScreenX = piece.screenCoordinate.screenX;
        int oldScreenY = piece.screenCoordinate.screenY;
        String pieceType = piece.pieceType;
        int newScreenX = cell.screenX;
        int newScreenY = cell.screenY;

        if(MoveCount % 2 == 1)
            pgnString = pgnString  + (MoveCount / 2  + 1) + ". "; 

        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date dateobj = new Date();
        String logo = df.format(dateobj) + ": Move From x:" + oldScreenX + ", Y:" + oldScreenY + " to x:" + newScreenX + ", Y:" + newScreenY;

        try {
                log.setLoger(logo);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        pgnString = pgnString + pieceType + alphabetFromXScreenX(oldScreenX) + (oldScreenY + 1) 
                        + "-" + alphabetFromXScreenX(newScreenX) + (newScreenY + 1) + " ";
    }

    /*
     * moving and check piece
     * @param  Piece piece, Coordinate cell, ScreenPrint screenPrint
     */
    public void movingAndCheck(AllPieces.Piece piece, Screen.Coordinate cell, ScreenPrint screenPrint) 
    {
        makePGNString(piece, cell);
        screenBoard.movePiece(piece, cell);

        if(piece instanceof AllPieces.Pawn) 
        {
            finalPawn = nTurned;
            if(checkValid.checkPromotion((AllPieces.Pawn) piece, cell)) 
            {
                promote(piece);
            }
        }

        if(otherMan.stackAll.size() > otherMan.stackAll.size()) {
            finalCap = nTurned;
        }

        checkPlay(screenPrint);

        _isFinishAutoPlay = true;
        
        if(usingMan != human1) 
        {
            currentSelect = null;
            
            changed = true;
            usingMan = human1;
            otherMan = human2;
            nTurned++;
        }
        else {
            currentSelect = null;
            
            changed = true;
            usingMan = human2;
            otherMan = human1;
            
            _isFinishAutoPlay = false;
            autoPlay();
        }
    }
    
    public void autoPlay()
    {
        if(!main.isRunning())
                return;

        while (_isFinishAutoPlay == false)
        {
            int x = 0;
            int y = 0;
            while(currentSelect == null)
            {
                x = getRandomNumberUsingInts(0, 7);
                y = getRandomNumberUsingInts(0, 7);
                if(!_screenPrint.bAnimation && _screenPrint.play.checkRunning) {				
                    _screenPrint.play.selectLocation(new Screen.Coordinate(x, y), _screenPrint);
                    _screenPrint.repaint();   
                }
            }
            
            if (currentSelect != null)
            {
                x = getRandomNumberUsingInts(0, 7);
                y = getRandomNumberUsingInts(0, 7);
                if(!_screenPrint.bAnimation && _screenPrint.play.checkRunning) {				
                    _screenPrint.play.selectLocation(new Screen.Coordinate(x, y), _screenPrint);
                    _screenPrint.repaint();
                }
            }

            x = 0;
            y = 0;
        }
    }
    
    public int getRandomNumberUsingInts(int min, int max) {
        Random random = new Random();
        return random.ints(min, max)
          .findFirst()
          .getAsInt();
    }

    public void changePlayer() {
        if(usingMan != human1) 
        {
            usingMan = human1;
            otherMan = human2;
            nTurned++;

        }
        else {
            usingMan = human2;
            otherMan = human1;
        }
    }

    /*
     * checking current game state
     * @param ScreenPrint screenPrint
     */
    public void checkPlay(ScreenPrint screenPrint) {
        String str1 = null;
        String str2 = null;

        //check mate
        if(checkValid.checkMate(otherMan)) 
        {
            str1 = "Checkmate!"; str2 = "Wins! : " + "<"  + usingMan.team + ">";
        }

        //check mate stale
        else if(checkValid.checkMateStale(otherMan)) 
        {
            str1 = "Stalemate!"; str2 = "Draw!";
        }

        //check moving fifty
        else if(checkValid.checkFifty(nTurned, finalCap, finalPawn)) 
        {
            str1 = "Move Fifty!"; str2 = "Draw!";
        }
        else {
            return;
        }

        checkRunning = false;			
        screenPrint.message1 = str1;
        screenPrint.message2 = str2;
        screenPrint.repaint();
    }		

    /*
     * function for promotion
     * @param  Piece piece
     */
    private void promote(AllPieces.Piece piece) {
        Screen.Coordinate coor = piece.screenCoordinate;
        int x = coor.screenX, y = coor.screenY;

        String[] exchange = {"Rook", "Bishop", "Knight", "Queen"};

        JFrame frame = new JFrame();
        frame.setResizable(true);	

        int n = JOptionPane.showOptionDialog(frame,
            "Please select your mind",
            "Piece:",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null, exchange, exchange[0]);

        switch(n)
        {
            case 0 :
                screenBoard.pieceBoards[x][y] = new AllPieces.Rook(piece.human, piece.team);
                break;

            case 1:
                screenBoard.pieceBoards[x][y] = new AllPieces.Bishop(piece.human, piece.team);
                break;					 

            case 2:
                screenBoard.pieceBoards[x][y] = new AllPieces.Knight(piece.human,piece.team);
                break;					

            case 3:
                screenBoard.pieceBoards[x][y] = new AllPieces.Queen(piece.human,piece.team);
                break;

            case JOptionPane.CLOSED_OPTION:
                n = JOptionPane.showOptionDialog(frame,	"change piece", "Choose a piece:", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, exchange, exchange[0]);
                break;
            default :
                break;
        }

        usingMan.listAll.remove(piece);
        screenBoard.pieceBoards[x][y].screenCoordinate = new Screen.Coordinate(x,y);
    }		
}