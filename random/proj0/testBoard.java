/* Tests for the Board Constructor */

import static org.junit.Assert.*;
import org.junit.Test;

public class testBoard {

	@Test
	public void testConstructor() {
		Board x = new Board(false);

		assertEquals(true, x.pieceAt(0,0) != null);
		// assertEquals("pawn", x.pieceAt(0,0).type);
		assertEquals(true, x.pieceAt(1,1).isShield());
		assertEquals(true, x.pieceAt(0,2).isBomb());

		assertEquals(true, x.pieceAt(0,2).isFire());
		assertEquals(false, x.pieceAt(0,6).isFire());

		assertEquals(true, x.pieceAt(0,2).isBomb());
		assertEquals(false, x.pieceAt(0,2).isKing());
	}

	@Test
	public void testMethods() {
		Board x = new Board(false);

		/*PieceAt method tests */
		assertEquals(null, x.pieceAt(0,1));
		assertEquals(null, x.pieceAt(9,9));

		/* canSelect method test */
		assertEquals(false, x.canSelect(0,1));
		assertEquals(true, x.canSelect(0,2));
		assertEquals(true, x.canSelect(1,1));

		x.select(1,1);

		assertEquals(false, x.canSelect(3,3));
		assertEquals(false, x.canSelect(5,6));
		assertEquals(false, x.canSelect(5,5));
		assertEquals(true, x.canSelect(0,2));


		/* validMove method tests */
		assertEquals(false, x.canSelect(3,3)); //this won't work once implement no jumping same character
		assertEquals(false, x.canSelect(1,2));
		assertEquals(true, x.canSelect(2,2));
		assertEquals(false, x.canSelect(5,5));

		x.select(2,2);

		assertEquals(true, x.canSelect(3,3));
		assertEquals(true, x.canSelect(1,3));

		x.remove(2,2);

		assertEquals(false, x.canSelect(2,2));
		assertEquals(null, x.pieceAt(2,2));

		// assertEquals("<Piece at....", x.pieceAt(0,0));
	}

	@Test
	public void testSelected() {
		Board x = new Board(false);
		Piece y = new Piece(true, x, 3, 3, "bomb");

		assertEquals(true, x.pieceAt(3,3) == null);

		x.place(y, 3, 3);

		assertEquals(false, x.pieceAt(3,3) == null);
		assertEquals(true, x.pieceAt(3, 3).isBomb());

		Piece z = new Piece(false, x, 3, 3, "shield");
		x.place(z, 3, 3);

		assertEquals(true, x.pieceAt(3,3).isShield());
		
		x.select(3,3);

		assertEquals(true, x.canSelect(4,4));
		assertEquals(false, x.canSelect(4,3));
		assertEquals(false, x.canSelect(1,7));

		x.select(4,4);

		assertEquals(null, x.pieceAt(3,3));
		assertEquals(true, x.pieceAt(4,4).isShield());

		x.endTurn();
	}

	@Test
	public void testPieceAt() {
		Board x = new Board(false);

		Piece bomb = new Piece(true, x, 2,2, "bomb");
		Piece pawn1 = new Piece(false, x, 3,3,"pawn");
		Piece pawn2 = new Piece(false, x, 5,3,"pawn");
		Piece shield = new Piece(false, x, 3,5, "shield");

		x.place(bomb, 2,2);
		x.place(pawn1, 3,3);
		x.place(pawn2, 5,3);
		x.place(shield, 3,5);

		assertEquals(true, x.pieceAt(2,2).isBomb());
		assertEquals(0, x.pieceAt(2,2).side());

		assertEquals(false, x.pieceAt(3,3).isFire());

		x.select(2,2);

		assertEquals(true, x.canSelect(4,4));
		x.select(4,4);

		assertEquals(null, x.pieceAt(4,4));

		assertEquals(null, x.pieceAt(3,3));
		assertEquals(null, x.pieceAt(5,3));
		assertEquals(true, x.pieceAt(3,5).isShield());

		x.remove(3,5);
		assertEquals(null, x.pieceAt(3,5));
	}

	@Test
	public void testRemove() {
		Board x = new Board(true);

		Piece bomb = new Piece(true, x, 2,2, "bomb");
		Piece pawn1 = new Piece(false, x, 3,3,"pawn");
		Piece pawn2 = new Piece(false, x, 5,3,"pawn");
		Piece shield = new Piece(false, x, 3,5, "shield");
		x.place(bomb, 2,2);
		x.place(pawn1, 3,3);
		x.place(pawn2, 5,3);
		x.place(shield, 3,5);

		x.select(2,2);

		x.select(1,3);
		assertEquals(null, x.pieceAt(2,2));
		assertEquals(true, x.pieceAt(1,3).isBomb());

		x.remove(1,3);
	}

	@Test 
	public void testWinner() {
		Board x = new Board(true);

		Piece bomb = new Piece(true, x, 2,2, "bomb");
		Piece pawn1 = new Piece(false, x, 3,3,"pawn");

		x.place(bomb, 2,2);
		x.place(pawn1, 3,3);

		assertEquals(null, x.winner());

		x.remove(2,2);

		assertEquals("Water", x.winner());

		x.remove(3,3);

		assertEquals("No one", x.winner());
	}

	@Test 
	public void testMultiJump() {
		Board x = new Board(true);

		Piece fpawn = new Piece(true, x, 0,0, "pawn");
		Piece wpawn1 = new Piece(false, x, 1,1,"pawn");
		Piece wpawn2 = new Piece(false, x, 3,3,"pawn");

		x.place(fpawn, 0,0);
		x.place(wpawn1, 1,1);
		x.place(wpawn2, 3,3);

		x.select(0,0);

		assertEquals(true, x.canSelect(2,2));

		x.select(2,2);

		assertEquals(true, fpawn.hasCaptured()); 

		assertEquals(true, x.canSelect(4,4));

	}

	@Test
	public void testTest4() {
		Board x = new Board(true);

		assertEquals("No one", x.winner());

		Piece fpawn = new Piece(true, x, 3,5,"pawn");
		Piece wpawn = new Piece(false, x, 2,6,"pawn");

		x.place(fpawn, 3,5);
		x.place(wpawn,2,6);

		assertEquals(null, x.winner());

		x.select(3,5);
		x.select(1,7);
		x.endTurn();
		assertEquals("Fire", x.winner());
}
	@Test
	public void testWin2() {
		Board z = new Board(true);
		Piece fpawn2 = new Piece(true, z, 2,2,"pawn");
		Piece wpawn2 = new Piece(false, z, 3,3,"pawn");

		z.place(fpawn2, 2,2);
		z.place(wpawn2,3,3);

		z.select(3,3);
		z.select(1,1);

		z.endTurn();

		assertEquals("Water", z.winner());

		Board y = new Board(true);

		Piece fbomb = new Piece(true, y, 2,2,"bomb");
		Piece wbomb = new Piece(false, y, 3,3,"bomb");

		y.place(fbomb, 2,2);
		y.place(wbomb,3,3);

		y.select(3,3);
		y.select(1,1);

		y.endTurn();

		assertEquals("No one", y.winner());
	}
	
	//to follow path in debugger
	@Test
	public void debuggerTests() {
		Board x = new Board(true);

		Piece fpawn = new Piece(true, x, 4,4,"pawn");
		Piece wpawn = new Piece(false, x, 2,6,"pawn");

		x.place(fpawn, 4,4);
		x.place(wpawn, 2,6);
		x.select(4,4);
		x.select(3,5);
		x.select(1,7);
	}

	@Test
	public void test9() {
		Board x = new Board(true);	

		Piece fbomb = new Piece(true, x, 0,0, "bomb");
		Piece wpawn = new Piece(false, x, 1,1,"pawn");
		Piece wshield = new Piece(false, x, 3,3,"shield");

		x.place(fbomb, 0,0);
		x.place(wpawn, 1,1);
		x.place(wshield, 3,3);

		assertEquals(true, x.pieceAt(0,0).isBomb());

		x.select(0,0);
		x.select(2,2);

		assertEquals(null, x.pieceAt(2,2));
		assertEquals(true, x.pieceAt(3,3).isShield());
		assertEquals(false, x.canSelect(4,4));

	}

	@Test 
	public void test11(){
		Board x = new Board(true);	

		Piece fshield = new Piece(true, x, 0,0, "shield");
		Piece wpawn = new Piece(false, x, 1,1,"pawn");
		Piece wshield = new Piece(false, x, 3,3,"shield");

		x.place(fshield, 0,0);
		x.place(wpawn, 1,1);
		x.place(wshield, 3,3);

		x.select(0,0);
		x.select(2,2);

		assertEquals(true, x.pieceAt(2,2).isShield());
		assertEquals(true, x.pieceAt(3,3).isShield());
		assertEquals(true, x.canSelect(4,4));
		assertEquals(false, x.canSelect(2,4));
	}

	@Test
	public void friendlyFire() {
		Board x = new Board(true);	

		Piece fbomb = new Piece(true, x, 0,0, "bomb");
		Piece fpawn = new Piece(true, x, 1,3,"pawn");
		Piece wpawn = new Piece(false, x, 1,1,"pawn");
		Piece wshield = new Piece(false, x, 3,3,"shield");

		x.place(fbomb, 0,0);
		x.place(fpawn, 1,3);
		x.place(wpawn, 1,1);
		x.place(wshield, 3,3);

		assertEquals(false, x.pieceAt(1,3).isShield());

		x.select(0,0);
		x.select(2,2);

		assertEquals(null, x.pieceAt(2,2));
		assertEquals(null, x.pieceAt(1,3));
		assertEquals(true, x.pieceAt(3,3).isShield());
		assertEquals(false, x.canSelect(4,4));
	}

	@Test

	public void jumpSuperTest() {
		Board x = new Board(true);	

		Piece fpawn = new Piece(true, x, 0,0, "pawn");
		Piece fpawn2 = new Piece(true, x, 1,3,"pawn");
		Piece wpawn0 = new Piece(false, x, 3,1, "pawn");
		// place fpawn4 later
		Piece fpawn4 = new Piece(true, x, 0, 4, "pawn");
		Piece wpawn = new Piece(false, x, 1,1,"pawn");
		Piece wpawn2 = new Piece(false, x, 4,4, "pawn");
		Piece wshield = new Piece(false, x, 3,3,"shield");

		x.place(fpawn, 0,0);
		x.place(wpawn0, 1,3);
		x.place(wpawn0, 3,1);
		x.place(wpawn, 1,1);
		x.place(wpawn2, 4,4);
		x.place(wshield, 3,3);

		x.select(0,0);
		x.select(2,2);

		assertEquals(true, fpawn.hasCaptured());

		assertEquals(false, x.canSelect(1,3));
		assertEquals(false, x.canSelect(3,1));
		assertEquals(false, x.canSelect(0,0));
		assertEquals(false, x.canSelect(4,4));
		assertEquals(false, x.canSelect(3,3));
		assertEquals(true, x.canSelect(0,4));

		x.place(fpawn4, 0, 4);

		assertEquals(false, x.canSelect(0,4));


		x.endTurn();
		assertEquals(false, fpawn.hasCaptured());
	}

	@Test

	public void test18() {
		Board x = new Board(true);	

		Piece fbomb = new Piece(true, x, 0,0, "bomb");
		Piece fpawn = new Piece(true, x, 1,3,"pawn");
		Piece wshield = new Piece(false, x, 1,1,"shield");
		Piece wshield2 = new Piece(false, x, 3,3,"shield");

		x.place(fbomb, 0,0);
		x.place(fpawn, 1,3);
		x.place(wshield, 1,1);
		x.place(wshield2, 3,3);

		assertEquals(true, x.pieceAt(1,1).isShield());
		assertEquals(true, x.pieceAt(3,3).isShield());

		x.select(0,0);
		x.select(2,2);

		assertEquals(null, x.pieceAt(2,2));
		assertEquals(null, x.pieceAt(1,3));
		assertEquals(null, x.pieceAt(1,1));
		assertEquals(true, x.pieceAt(3,3).isShield());
	}

	@Test

	public void winnerTests2() {
		Board x = new Board(true);	

		Piece fbomb = new Piece(true, x, 0,0, "bomb");
		Piece fpawn = new Piece(true, x, 1,3,"pawn");
		Piece wshield = new Piece(false, x, 1,1,"shield");
		Piece wshield2 = new Piece(false, x, 3,3,"shield");

		assertEquals("No one", x.winner());

		x.place(fbomb, 0,0);

		assertEquals("Fire", x.winner());

		x.remove(0,0);
		x.place(wshield, 1,1);

		assertEquals("Water", x.winner());
	}

    public static void main(String[] args) {
		jh61b.junit.textui.runClasses(testBoard.class);
	}

}
