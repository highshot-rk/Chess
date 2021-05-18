

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class CheessUniTest {
	
	Human human1;
	Human human2;
	Chess.Play play;
	Chess.ScreenPrint screen;
	Chess.MainBoard main;
	
	@Before
	public void setUp() throws Exception {
		human1 = new Human();
		human2 = new Human();	
		play = new Chess.Play(human1, human2);
		screen =  new Chess.ScreenPrint(play);
		main =  new Chess.MainBoard(screen);
	}	

	@Test
	public void test() {		 
		 
		human1.initHuman("White");
		/*
		 * check team
		 */
		assertEquals("White", human1.team);
		
		/*
		 * check count of  all piece
		 */
		assertEquals(16, human1.listAll.size()); 
		
		AllPieces.Piece pieceKing = new AllPieces.King(human1, "White");
		/*
		 * check team of piece
		 */
		assertEquals(human1.team, pieceKing.team);
		
		/*
		 * check type of piece
		 */
		assertEquals("K", pieceKing.pieceType);
		
		
		AllPieces.Piece pieceQueen = new AllPieces.Queen(human1, "White");
		/*
		 * check team of piece
		 */
		assertEquals(human1.team, pieceQueen.team);
		
		/*
		 * check type of piece
		 */
		assertEquals("Q", pieceQueen.pieceType);
		
		AllPieces.Piece pieceBishop = new AllPieces.Bishop(human1, "White");
		/*
		 * check team of piece
		 */
		assertEquals(human1.team, pieceBishop.team);
		
		/*
		 * check type of piece
		 */
		assertEquals("B", pieceBishop.pieceType);
		
		AllPieces.Piece pieceRook = new AllPieces.Rook(human1, "White");
		/*
		 * check team of piece
		 */
		assertEquals(human1.team, pieceRook.team);
		
		/*
		 * check type of piece
		 */
		assertEquals("R", pieceRook.pieceType);
		
		AllPieces.Piece piece = human1.listAll.get(1);
		assertEquals("Q", piece.pieceType);
		assertEquals("White", piece.team);
		
		piece.screenCoordinate =  new Screen.Coordinate(2, 3);		
		Screen.Coordinate coordinate = new Screen.Coordinate(3, 4);		
		screen.play.screenBoard.movePieceForCoordinate(piece, coordinate);
		assertEquals(3, piece.screenCoordinate.screenX);
		assertEquals(4, piece.screenCoordinate.screenY);
		
		AllPieces.Piece piece1 = human1.listAll.get(2);
		assertEquals("R", piece1.pieceType);
		assertEquals("White", piece1.team);
		
		piece1.screenCoordinate =  new Screen.Coordinate(2, 3);		
		Screen.Coordinate coordinate1 = new Screen.Coordinate(3, 4);		
		screen.play.screenBoard.movePieceForCoordinate(piece1, coordinate1);
		assertEquals(3, piece1.screenCoordinate.screenX);
		assertEquals(4, piece1.screenCoordinate.screenY);
		
		AllPieces.Piece piece2 = human1.listAll.get(3);
		assertEquals("B", piece2.pieceType);
		assertEquals("White", piece2.team);
		
		piece2.screenCoordinate =  new Screen.Coordinate(2, 3);
		screen.play.screenBoard.movePieceForCoordinate(piece2, coordinate1);
		assertEquals(3, piece2.screenCoordinate.screenX);
		assertEquals(4, piece2.screenCoordinate.screenY);
		
		AllPieces.Piece piece3 = human1.listAll.get(4);
		assertEquals("N", piece3.pieceType);
		assertEquals("White", piece3.team);
		
	}

}
