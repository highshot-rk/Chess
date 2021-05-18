/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import src.AllPieces;
import src.Human;
import src.Screen;
import java.util.LinkedList;
import java.util.List;

/*
*service class for define CheckValid class
*/	
public class CheckValid implements AllPieces.MovingValid {

    Screen.Board screenBoard;

    //construct
    public CheckValid(Screen.Board screenBoard) {
        this.screenBoard = screenBoard;
    }		

    //check team
    private static boolean isSameTeam(AllPieces.Piece p1, AllPieces.Piece p2) {
        boolean result = p1.team.equals(p2.team);
        return result;
    }

    public List<Screen.Coordinate> MovesValid(final AllPieces.Piece piece) {
        return null;
    }

    public List<Screen.Coordinate> MovesValid(final AllPieces.Pawn pawn) {
        List<Screen.Coordinate>listCoord = new LinkedList<Screen.Coordinate>();
        Screen.Coordinate screenCoordinate = pawn.screenCoordinate;
        int x = screenCoordinate.screenX, y = screenCoordinate.screenY, arrow;
        String team = pawn.team;
        AllPieces.Piece oldPiece = screenBoard.getPieceXY(x, y);

        if(!team.equals("Bottom"))
        {
            arrow = 1;
        }
        else 
        {
            arrow = -1;				
        }

        if(screenBoard.getPieceXY(x, y + arrow) == null) 
        {
            if(!moveCheck(oldPiece, x, y + arrow)) 
            {
                listCoord.add(new Screen.Coordinate(x, y + arrow));
            }

            if(screenBoard.getPieceXY(x, y + (arrow * 2)) == null && pawn.isPawnMove && !moveCheck(oldPiece, x, y + (arrow * 2))) 
            {
                listCoord.add(new Screen.Coordinate(x, y + (arrow * 2)));
            }
        }

        if(screenBoard.getPieceXY(x + 1, y + arrow) != null && !isSameTeam(oldPiece, screenBoard.getPieceXY(x + 1, y + arrow)) && !moveCheck(oldPiece, x + 1, y + arrow)) {
            listCoord.add(screenBoard.getPieceXY(x + 1, y + arrow).screenCoordinate);
        }			

        if(screenBoard.getPieceXY(x + -1, y + arrow) != null &&	!isSameTeam(oldPiece, screenBoard.getPieceXY(x + -1, y + arrow)) &&	!moveCheck(oldPiece, x + -1, y + arrow)) 
        {
            listCoord.add(screenBoard.getPieceXY(x + -1, y + arrow).screenCoordinate);
        }

        checkLegal(pawn, listCoord, arrow, x, y);

        return listCoord;
    }


    //check legal
    private boolean checkLegal(AllPieces.Pawn pawn, List<Screen.Coordinate> listCoord, int arrow, int x, int y) {
        String team = pawn.team;
        Screen.History capture = screenBoard.getHistory();
        int fifthRank = 3;

        if(team.equals("Top")) {
            fifthRank = 4;
        }
        if(y == fifthRank && capture.historyPiece instanceof AllPieces.Pawn && capture.historyPiece.screenCoordinate.screenY == y && capture.historyMove.screenY == fifthRank + arrow * 2 && (capture.historyMove.screenX == x + -1 || capture.historyMove.screenX == x + 1) ) 
        {
            listCoord.add(new Screen.Coordinate(capture.historyPiece.screenCoordinate.screenX, capture.historyPiece.screenCoordinate.screenY + arrow));
            return true;	
        }
        return false;
    }

    //cehck Promotion
    public boolean checkPromotion(AllPieces.Pawn pawn, Screen.Coordinate tile) {
        String team = pawn.team;
        int pos = 0, y = tile.screenY;

        if(team.equals("Top"))
            pos = 7;

        return y == pos;
    }

    public List<Screen.Coordinate> MovesValid(final AllPieces.Rook rook) {
        List<Screen.Coordinate>listCoord = new LinkedList<Screen.Coordinate>();
        Screen.Coordinate coor = rook.screenCoordinate;

        int x = coor.screenX;
        int y = coor.screenY;

        getLoads(listCoord, x, y, 1, 0);
        getLoads(listCoord, x, y, -1, 0);
        getLoads(listCoord, x, y, 0, -1);
        getLoads(listCoord, x, y, 0, 1);

        return listCoord;
    }

    //insert source of piece
    private void insert(List<Screen.Coordinate> listCoord, AllPieces.Piece source, int x, int y) {
        AllPieces.Piece destination = screenBoard.getPieceXY(x, y);
        if((x >= 0 && x <= 7 && y >= 0 && y <= 7) &&
            (destination == null || !isSameTeam(source, destination)) &&
            !moveCheck(source, x, y)
            ) {
            listCoord.add(new Screen.Coordinate(x, y));
        }
    }

    // valid moving
    public List<Screen.Coordinate> MovesValid(final AllPieces.Knight knight) {
        List<Screen.Coordinate>listCoord = new LinkedList<Screen.Coordinate>();
        Screen.Coordinate coordinate = knight.screenCoordinate;
        int x = coordinate.screenX;
        int y = coordinate.screenY;
        AllPieces.Piece source = screenBoard.getPieceXY(x, y);
        insert(listCoord, source, x + 2, y - 1);
        insert(listCoord, source, x + 2, y + 1);
        insert(listCoord, source, x - 2, y - 1);
        insert(listCoord, source, x - 2, y + 1);
        insert(listCoord, source, x + 1, y - 2);
        insert(listCoord, source, x + 1, y + 2);
        insert(listCoord, source, x - 1, y - 2);
        insert(listCoord, source, x - 1, y + 2);
        return listCoord;
    }

    public List<Screen.Coordinate> MovesValid(final AllPieces.Bishop bishop) {

        List<Screen.Coordinate>listCoord = new LinkedList<Screen.Coordinate>();
        Screen.Coordinate coor = bishop.screenCoordinate;
        int x = coor.screenX;
        int y = coor.screenY;
        getLoads(listCoord, x, y, 1, -1);
        getLoads(listCoord, x, y, -1, 1);
        getLoads(listCoord, x, y, -1, -1);
        getLoads(listCoord, x, y, 1, 1);
        return listCoord;
    }

    public List<Screen.Coordinate> MovesValid(final AllPieces.Queen queen) {
        List<Screen.Coordinate> listCoord = new LinkedList<>();
        Screen.Coordinate coor = queen.screenCoordinate;

        int x = coor.screenX;
        int y = coor.screenY;


        getLoads(listCoord, x, y, 1, -1);
        getLoads(listCoord, x, y, -1, 1);
        getLoads(listCoord, x, y, -1, -1);
        getLoads(listCoord, x, y, 1, 1);
        getLoads(listCoord, x, y, 1, 0);
        getLoads(listCoord, x, y, -1, 0);
        getLoads(listCoord, x, y, 0, -1);
        getLoads(listCoord, x, y, 0, 1);

        return listCoord;
    }

    public List<Screen.Coordinate> MovesValid(final AllPieces.King king) {
        List<Screen.Coordinate>listCoord = new LinkedList<Screen.Coordinate>();
        Screen.Coordinate coordinate = king.screenCoordinate;
        int x = coordinate.screenX, y = coordinate.screenY;

        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j++) {
                if((i == 0 && j == 0) ||(x + i < 0 || x + i > 7) || (y + j < 0 || y + j > 7))
                {
                    continue;
                }
                AllPieces.Piece source = screenBoard.getPieceXY(x, y);
                AllPieces.Piece destination = screenBoard.getPieceXY(x + i, y + j);
                if((destination == null || !isSameTeam(source, destination)) &&	!moveCheck(king, x + i, y + j))
                {
                    listCoord.add(new Screen.Coordinate(x + i, y + j));
                }
            }
        }

        legalCastling(king, listCoord, x, y);

        return listCoord;
    }

    //check empty between rows
    private boolean emptyBetweenRow(AllPieces.Piece p1, AllPieces.Piece p2) {
        Screen.Coordinate piece1 = p1.screenCoordinate;
        Screen.Coordinate piece2 = p2.screenCoordinate;

        int start = piece1.screenX, end = piece2.screenX;

        if(start > end) {
            int temp = end;
            end = start;
            start = temp;
        }

        for(; start < end - 1; start++ ) {
            if(screenBoard.getPieceXY(start + 1, piece1.screenY) != null) {
                return false;
            }
        }
        return true;
    }

    //check legal casgling
    private boolean legalCastling(final AllPieces.King king, List<Screen.Coordinate> listCoord, int x, int y) {
        int row = 0;
        String team = king.team;			

        if(team.equals("Bottom")) {
            row = 7;
        }

        if(!moveCheck(king, x, y) && !king.isMoved) {
            AllPieces.Piece left = screenBoard.getPieceXY(0, row);
            AllPieces.Piece right = screenBoard.getPieceXY(7, row);

            if(left instanceof AllPieces.Rook && isSameTeam(king, left)&& (!((AllPieces.Rook) left).isRookMoved && !king.isMoved) && emptyBetweenRow(left, king) && !moveCheck(king, x - 1, y) && !moveCheck(king, x - 2, y)) 
            {
                listCoord.add(new Screen.Coordinate(x - 2, y));
            }

            if(right instanceof AllPieces.Rook && isSameTeam(king, right)	&& (!((AllPieces.Rook) right).isRookMoved && !king.isMoved) && emptyBetweenRow(king, right)	&& !moveCheck(king, x + 1, y) && !moveCheck(king, x + 2, y)) 
            {
                listCoord.add(new Screen.Coordinate(x + 2, y));
            }

            return true;
        }
        return false;
    }

    //check loads
    private void getLoads(List<Screen.Coordinate> listCoord, int xPos, int yPos, int h, int v) {
        assert xPos >= 0 && xPos <= 7 && yPos >= 0 && yPos <= 7;

        for(int x = xPos + h, y = yPos + v; (x >= 0 && x <= 7) && (y >= 0 && y <= 7); x += h, y += v) 
        {
            if((screenBoard.getPieceXY(x, y) == null || !isSameTeam(screenBoard.getPieceXY(x, y), screenBoard.getPieceXY(xPos, yPos))) && !moveCheck(screenBoard.getPieceXY(xPos, yPos), x, y)) 
            {
                listCoord.add(new Screen.Coordinate(x, y));
            }

            if(screenBoard.getPieceXY(x, y) != null) 
            {
                break;
            }
        }
    }

    //check filter
    public List<Screen.Coordinate> filterForEnemyHighlights(List<Screen.Coordinate> moves, AllPieces.Piece piece) {
        List<Screen.Coordinate> enemyHighlights = new LinkedList<Screen.Coordinate>();
        int x, y;
        for(Screen.Coordinate move: moves) {
            x = move.screenX;
            y = move.screenY;
            if(screenBoard.getPieceXY(x, y) != null) {
                enemyHighlights.add(move);
            }
            else if(screenBoard.getPieceXY(x, y) == null &&	piece instanceof AllPieces.Pawn && x != piece.screenCoordinate.screenX )
            {
                enemyHighlights.add(new Screen.Coordinate(x, y));
            }
        }
        return enemyHighlights;
    }

    //backup
    private AllPieces.Piece[][] backup() {
        AllPieces.Piece[][] board = this.screenBoard.pieceBoards;
        AllPieces.Piece[][] copy = new AllPieces.Piece[board.length][board[1].length];

        for(int i = 0; i < copy.length; i++) {
            for(int j = 0; j < copy.length; j++) {
                copy[i][j] = board[i][j];
            }
        }
        return copy;
    }

    //checking move for piece on x, y
    private boolean moveCheck(AllPieces.Piece piece, int x, int y) {
        AllPieces.Piece[][] pieces = backup();

        pieces[piece.screenCoordinate.screenX][piece.screenCoordinate.screenY] = null;
        pieces[x][y] = piece; 

        int calcX = 0;
        int calcY = 0;
        for(int i = 0; i < pieces.length; i++) {
            for(int j = pieces[0].length - 1; j >= 0; j--) {
                if(pieces[i][j] instanceof AllPieces.King && pieces[i][j].human == piece.human) 
                {
                    calcX = i;
                    calcY = j;
                    break;
                }
            }
        }

        boolean result = underCheck(pieces, calcX, calcY);
        return result;
    }

    //check under
    public boolean underCheck(Human huamn) {
        List<AllPieces.Piece> pieces = huamn.listAll;
        int x = 0, y = 0;
        for(AllPieces.Piece piece: pieces) {
            if(piece instanceof AllPieces.King) {
                x = piece.screenCoordinate.screenX; y = piece.screenCoordinate.screenY;
                boolean result = underCheck(screenBoard.pieceBoards, x, y);
                return result;
            }
        }
        return false;
    }

    //check underCheck
    private boolean underCheck(AllPieces.Piece[][] pieces, int coorX, int coorY) {
        boolean result1 = CheckDiagInKing(pieces, coorX, coorY) || checkOrthoInKing(pieces, coorX, coorY);
        boolean result2 = checkAttackByPawnInKing(pieces, coorX, coorY) || checkByKnightInKing(pieces, coorX, coorY) || checkByKingInKing(pieces, coorX, coorY);			
        return result1 || result2;
    }

    //check diag in king
    private boolean CheckDiagInKing(AllPieces.Piece[][] pieces, int coorX, int coorY) {
        assert coorX >= 0 && coorX <= 7 && coorY >= 0 && coorY <= 7;			

        int h, v;
        int x, y;
        for(int a = 0; a < 4; a++) {
            h = (a / 2 == 0) ? -1: 1;
            v = (a % 2 == 0) ? -1: 1;

            for( x = coorX + h, y = coorY + v; (x >= 0 && x <= 7) && (y >= 0 && y <= 7); x += h, y += v) 
            {
                if(pieces[x][y] != null) 
                {
                    if(!isSameTeam(pieces[x][y], pieces[coorX][coorY]) && (pieces[x][y] instanceof AllPieces.Bishop || pieces[x][y] instanceof AllPieces.Queen))
                    {
                        return true;
                    }
                    else 
                    {
                        break;
                    }
                }
            }
        }
        return false;
    }


    private boolean checkOrthoInKing(AllPieces.Piece[][] pieces, int coorX, int coorY) 
    {
        assert coorX >= 0 && coorX <= 7 && coorY >= 0 && coorY <= 7;

        int h, v; 
        int x, y;
        for(int a = 0; a < 4; a++) {
            if(a / 2 == 0) {
                h = (a % 2 == 0) ? -1: 1;
                v = 0;
            }
            else {
                h = 0;
                v = (a % 2 == 0) ? -1: 1;
            }

            for(x = coorX + h, y = coorY + v; (x >= 0 && x <= 7) && (y >= 0 && y <= 7);	x += h, y += v) 
            {
                if(pieces[x][y] != null) {
                    if(!isSameTeam(pieces[x][y], pieces[coorX][coorY]) &&(pieces[x][y] instanceof AllPieces.Rook || pieces[x][y] instanceof AllPieces.Queen)) 
                    {
                        return true;
                    }
                    else 
                    {
                        break;
                    }
                }
            }
        }
        return false;
    }

    //check by knight in king 
    private boolean checkByKnightInKing(AllPieces.Piece[][] pieces, int coorX, int coorY) {

        assert coorX >= 0 && coorX <= 7 && coorY >= 0 && coorY <= 7;

        int h, v, x, y;
        for(int a = 0; a < 8; a++) {
            h = ((a / 2) % 2 == 0) ? -1: 1;
            v = (a % 2 == 0) ? -1: 1;
            if(a / 4 == 0) {
                h *= 2;
            }
            else 
            {
                v *= 2;
            }

            x = coorX + h;
            y = coorY + v;

            if(((x >= 0 && x <= 7) && (y >= 0 && y <= 7)) && pieces[x][y] != null && !isSameTeam(pieces[x][y], pieces[coorX][coorY]) &&	pieces[x][y] instanceof AllPieces.Knight) 
            {
                return true;
            }
        }
        return false;
    }

    //check mate stale
    public boolean checkMateStale(Human waitingPlayer) {
        boolean result = !checkMoveValid(waitingPlayer);
        return result;
    }

    //check fifty
    public boolean checkFifty(int turn, int lastCapture, int lastPawnMove) {
        boolean result = turn >= lastCapture + 50 && turn >= lastPawnMove + 50;
        return result;
    }

    //check by king in king
    private boolean checkByKingInKing(AllPieces.Piece[][] pieces, int coorX, int coorY) {
        int x, y;
        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j++) {
                if(i == 0 && j == 0) 
                {
                    continue;
                }
                else
                {
                    x = coorX + i;
                    y = coorY + j;	
                    if(((x >= 0 && x <= 7) && (y >= 0 && y <= 7)) && pieces[x][y] != null && !isSameTeam(pieces[x][y], pieces[coorX][coorY]) && pieces[x][y] instanceof AllPieces.King)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //check attack by pawn in king
    private boolean checkAttackByPawnInKing(AllPieces.Piece[][] pieces, int coorX, int coorY) 
    {
        assert coorX >= 0 && coorX <= 7 && coorY >= 0 && coorY <= 7;

        int h, x, y;
        if(pieces[coorX][coorY].team.equals("Bottom") ) 
        {				
            y = coorY + -1;
        }			
        else 
        {
            y = coorY + 1;
        }

        for(int a = 0; a < 2; a++) {
            h = (a % 2 == 0) ? -1: 1;
            x = coorX + h;
            if(((x >= 0 && x <= 7) && (y >= 0 && y <= 7)) && pieces[x][y] != null && !isSameTeam(pieces[x][y], pieces[coorX][coorY]) && pieces[x][y] instanceof AllPieces.Pawn ) 
            {
                    return true;
            }
        }
        return false;
    }

    //check mate
    public boolean checkMate(Human human) {
        if(underCheck(human)) {
            return !checkMoveValid(human);
        }
        return false;
    }

    //check move valid state
    private boolean checkMoveValid(Human human) {
        List<Screen.Coordinate> validMoves;
        for(AllPieces.Piece item: human.listAll) {
            validMoves = item.accept(this);
            if(validMoves.size() > 0) {
                    return true;
            }
        }
        return false;
    }
}
