package src;
import java.io.Serializable;
import java.util.Stack;

/*
 * service class for managing screen
 */
public class Screen {	
	
    /*
     * service class for managing Borad
     */
    public static class Board implements Serializable{

        private static final long serialVersionUID = 13L;
        public int pieceSize = 8;
        public Stack<History> previousMoves = new Stack<>();
        public AllPieces.Piece pieceBoards[][] = new AllPieces.Piece[pieceSize][pieceSize];

        //get history
        public History getHistory() {
            if(!previousMoves.isEmpty())						
                return previousMoves.peek();
            else
                return null;
        }

        /*
         * get piece for x, y
         * @param x, y
         * @return object of piece
         */		
        public AllPieces.Piece getPieceXY(int x, int y) 
        {
            if((x >= 0 && x < pieceBoards.length && y >= 0 && y < pieceBoards[0].length)) 
            {
                return pieceBoards[x][y];				
            }
            else
            {
                return null;
            }			
        }

        /*
         * moving piece for coordinate
         * @param Piece piece, Coordinate coordinate
         */
        public void movePieceForCoordinate(AllPieces.Piece piece, Coordinate coordinate) {

            int newX = coordinate.screenX;
            int newY = coordinate.screenY;
            piece.screenCoordinate.screenX = newX;
            piece.screenCoordinate.screenY = newY;			
        }


        /*
         * moving piece for coordinate
         * @param Piece piece, Coordinate coordinate
         */
        public void movePiece(AllPieces.Piece piece, Coordinate coordinate) {

            int newX = coordinate.screenX;
            int newY = coordinate.screenY;			
            int oldX = piece.screenCoordinate.screenX;
            int oldY = piece.screenCoordinate.screenY;
            AllPieces.Piece newPiece = getPieceXY(newX, newY);			
            if(newPiece != null) {
                newPiece.human.append(newPiece);
            }			
            this.previousMoves.push(new History(piece,new Coordinate(oldX, oldY)));
            if(piece instanceof AllPieces.Pawn) {
                ((AllPieces.Pawn)piece).isPawnMove = false;
                String team = piece.team;
                if((newX < oldX || newX > oldX) && newPiece == null) {
                    int arrow = 0;
                    if(team.equals("Bottom")) {
                            arrow = -1;
                    }
                    else {
                        arrow = 1;
                    }

                    pieceBoards[oldX][oldY] = null;
                    newPiece = getPieceXY(newX, newY-arrow);
                    newPiece.human.append(newPiece);
                    oldX = newX;
                    oldY = newY-arrow; 
                }
            }	

            if(piece instanceof AllPieces.King) {
                ((AllPieces.King)piece).isMoved = true;

                if(newX == oldX + 2 ) {
                    AllPieces.Piece rightRook = pieceBoards[newX+1][newY];
                    pieceBoards[newX - 1][newY] = rightRook;
                    rightRook.screenCoordinate = new Coordinate(newX-1, newY);

                    pieceBoards[newX + 1][newY] = null;
                }
                else if(newX == oldX - 2) {
                    AllPieces.Piece leftRook = pieceBoards[newX-2][newY];
                    pieceBoards[newX + 1][newY] = leftRook;
                    leftRook.screenCoordinate = new Coordinate(newX+1,newY);

                    pieceBoards[newX - 2][newY] = null;
                }
            }

            if(piece instanceof AllPieces.Rook) {
                ((AllPieces.Rook)piece).isRookMoved = true;
            }

            pieceBoards[oldX][oldY] = null;
            pieceBoards[newX][newY] = piece;
            piece.screenCoordinate = coordinate;
        }
    }

    /*
     * service class for managing history
     */
    public static class History implements Serializable{

        private static final long serialVersionUID = 14L;
        public AllPieces.Piece historyPiece;
        public Screen.Coordinate historyMove;

        /*
         * constructor
         * @param Piece historyPiece, Coordinate historyMove
         */
        public History(AllPieces.Piece historyPiece, Screen.Coordinate historyMove) {
            this.historyPiece = historyPiece;
            this.historyMove = historyMove;
        }

    }	

    /*
     * service class for managing Coordinate
     */	
    public static class Coordinate implements Serializable {
        private static final long serialVersionUID = 9L;
        public int screenX;
        public int screenY; 

        /*
         * constructor
         * @param coordinate x, y
         */

        public Coordinate(int x, int y) {
            this.screenX = x;
            this.screenY = y;
        }	

        /*
         * check equal
         * @param object
         */		
        public boolean equals(Object o) {
            if(o instanceof Coordinate) {				
                Coordinate other = (Coordinate) o;
                return other.screenX == this.screenX && other.screenY == this.screenY;
            }
            else
                return false;		
        }
    }
	
}