package tests;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import game.Bag;
import game.Board;
import game.Game;
import game.HumanPlayer;
import game.Player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class GameTest {
	private Game game;
	private Player p1;
	private Player p2;
	private Board board;
	private Player[] p = new Player[2];
	private String[] pp = new String[2];
	
	int test = 0;
	
	@Before
	public void setUp() {
		System.out.println(test++);
		board = new Board();
		p1 = new HumanPlayer("Mel");
		p2 = new HumanPlayer("Vin");
		game = new Game(p1, p2, null, null);
		p[0] = p1;
		p[1] = p2;
		pp[0] = "Mel";
		pp[1] = "Vin";
	}
	
	@Test
	public void resetTest() {
		board.reset();
		for (int i = 0; i < Board.FIELDS; i++) {
			System.out.println(i);
			assertTrue(board.isEmptyField(i));
		}
	}
	
	
	@Test
	public void testFindFirstMove() {
		int a = p1.findFirstMove(board);
		int b = p1.findFirstMove(board);
		if (a > b) {
			assertEquals(p[0], p1);
			assertEquals(p[1], p2);
		}
	}
	
	@Test
	public void testPlay() {
		if (p.length == 2) {
			p[0].moveFourTilesToHand();
	    	p[1].moveFourTilesToHand();
			assertEquals(p1.getTilesInHand().size(), 4);
			assertEquals(p2.getTilesInHand().size(), 4);
		}
	}
	
	@Test
	public void testGameOver() {
		for (int i = 0; i < Board.FIELDS; i++) {
		Bag.getTileInBag().remove(i);
		if (p.length == 2) {
			assertTrue(game.gameOver(board));
		}
		}
	}
	
	
	
	

}
