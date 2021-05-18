package src;
import java.util.Stack;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 *service class for managing human 
 */
public class Human implements Serializable{
    private static final long serialVersionUID = 1L;
    /*
     * all list of pieces
     */
    public List<AllPieces.Piece> listAll;

    /*
     * display team
     */
    public String team;

    /*
     * stack of pieces
     */
    public Stack<AllPieces.Piece> stackAll;

    /*
     * constructor
     */
    public Human() {
        listAll =  null;
        stackAll =  null;
    }

    /*
     * append pieces
     */
    public void append(AllPieces.Piece piece) {
        stackAll.push(piece);
        listAll.remove(piece);
    }


    /*
     * initialize human object
     * @param team
     */

    public void initHuman(String team) {

        listAll = new ArrayList<AllPieces.Piece>();
        stackAll = new Stack<AllPieces.Piece>();
        this.team = team;

        listAll.add(new AllPieces.King(this, team));
        listAll.add(new AllPieces.Queen(this, team));		

        listAll.add(new AllPieces.Rook(this, team));
        listAll.add(new AllPieces.Bishop(this, team));
        listAll.add(new AllPieces.Knight(this, team));

        listAll.add(new AllPieces.Rook(this, team));
        listAll.add(new AllPieces.Bishop(this, team));
        listAll.add(new AllPieces.Knight(this, team));		

        int count = 0;
        while(true) {
            if(count >= 8)
                break;

            listAll.add(new AllPieces.Pawn(this, team));
            count ++;
        }
    }	
}