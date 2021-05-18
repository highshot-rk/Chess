package src;
import java.util.Stack;
import java.util.List;


/*
 *service class for managing computer 
 */
public class Computer {
	
    public List<AllPieces.Piece> listAll;	
    public String team;	
    public Stack<AllPieces.Piece> stackAll;

    /*
     * construct 
     */

    public Computer() {
        listAll =  null;
        stackAll =  null;
    }

    /*
     * append piece
     * @param Piece
     */

    public void append(AllPieces.Piece piece) {
        stackAll.push(piece);
        listAll.remove(piece);
    }
}