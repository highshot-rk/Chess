package src;
import java.util.List;
import java.io.Serializable;
import java.util.LinkedList;

/*
 * service class for managing pieces class 
 */
public class AllPieces {	
	/*
	 * service class for Pieces class 
	*/
    abstract public static class Piece implements HostValidate, Serializable{
        /*
         * serial ID
         */
        private static final long serialVersionUID = 1L;
        /*
         * manage action of human
         */
        public Human human;
        /*
         * manage action of computer
         */
        public Computer computer;

        /*
         * display team
         */
        public String team;
        /*
         * display type of piece
         */
        public String pieceType;
        /*
         * manage coordinate of screen for piece
         */
        public Screen.Coordinate screenCoordinate;

        public List<Screen.Coordinate> accept(MovingValid visitor) {
            List<Screen.Coordinate> result = visitor.MovesValid(this);
            return result;
        }
    }
	
    /*
     *service class for Bishop pieces 
     */
    public static class Bishop extends Piece {
        private static final long serialVersionUID = 2L;

        /*
         * construct of Bishop on human
         */
        public Bishop(Human human, String team) {
            this.human = human;
            this.team = team;
            this.pieceType = "B";
        }

        /*
         *  construct of Bishop on computer
         */
        public Bishop(Computer computer, String team) {
            this.computer = computer;
            this.team = team;
        }

        /*
         * Moving valid list
         * @param MovingValid 
         * @return list of coordinates on screen
         */
        public List<Screen.Coordinate> accept(MovingValid visitor) {
            return visitor.MovesValid(this);
        }
    }

    /*
     *service class for King pieces 
     */
    public static class King extends Piece {

        private static final long serialVersionUID = 3L;
        /*
         * check move state		  
         */
        public boolean isMoved;

        /*
         * construct or king class
         * @param Human, String team
         */
        public King(Human human, String team) {
            this.pieceType = "K";
            this.human = human;
            this.team = team;
            this.isMoved = false;
        }

        public King(Computer computer, String team) {
            this.computer = computer;
            this.team = team;
        }

        public List<Screen.Coordinate> accept(MovingValid visitor) {
            return visitor.MovesValid(this);
        }
    }
    /*
     *service class for Knight pieces 
     */
    public static class Knight extends Piece {
        /**
         * 
         */
        private static final long serialVersionUID = 4L;

        public Knight(Human human, String team) {
            this.pieceType = "N";
            this.human = human;
            this.team = team;
        }

        public Knight(Computer computer, String team) {
            this.computer = computer;
            this.team = team;
        }

        public List<Screen.Coordinate> accept(MovingValid visitor) {
            return visitor.MovesValid(this);
        }
    }

    /*
     *service class for Pawn pieces 
     */
    public static class Pawn extends Piece {

        private static final long serialVersionUID = 5L;
        public boolean isPawnMove;

        public Pawn(Human human, String team) {
            this.pieceType = "";
            this.human = human;
            this.team = team;
            this.isPawnMove = true;
        }

        public Pawn(Computer computer, String team) {
            this.computer = computer;
            this.team = team;
            this.isPawnMove = true;
        }

        public List<Screen.Coordinate> accept(MovingValid visitor) {
            return visitor.MovesValid(this);
        }
    }

    /*
     *service class for Queen pieces 
     */
    public static class Queen extends Piece {
        /**
         * 
         */
        private static final long serialVersionUID = 6L;

        public Queen(Human human, String team) {
            this.pieceType = "Q";
            this.human = human;
            this.team = team;
        }

        public Queen(Computer computer, String team) {
            this.computer = computer;
            this.team = team;
        }

        public List<Screen.Coordinate> accept(MovingValid visitor) {
            return visitor.MovesValid(this);
        }
    }

    /*
     *service class for Rook pieces 
     */
    public static class Rook extends Piece {
        /**
         * 
         */
        private static final long serialVersionUID = 7L;
        public boolean isRookMoved;

        public Rook(Human human, String team) {
            this.pieceType = "R"; 
            this.isRookMoved = false;
            this.human = human;
            this.team = team;			
        }		

        public Rook(Computer computer, String team) {
            this.computer = computer;
            this.team = team;
        }

        public List<Screen.Coordinate> accept(MovingValid visitor) {
            return visitor.MovesValid(this);
        }
    }

    interface MovingValid {
        List<Screen.Coordinate> MovesValid(King piece);
        List<Screen.Coordinate> MovesValid(Queen piece);
        List<Screen.Coordinate> MovesValid(Rook piece);
        List<Screen.Coordinate> MovesValid(Bishop piece);
        List<Screen.Coordinate> MovesValid(Knight piece);
        List<Screen.Coordinate> MovesValid(Pawn piece);
        List<Screen.Coordinate> MovesValid(Piece piece);		
    }

    /*
     * define interface HostValidate
     */
    interface HostValidate {
        List<Screen.Coordinate> accept(MovingValid visitor);
    }

    /*
     *service class for define CheckValid class
    */	

}
