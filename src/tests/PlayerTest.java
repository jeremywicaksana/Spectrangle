package tests;

import org.junit.Before;
import org.junit.Test;

import game.HumanPlayer;
import game.Player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class PlayerTest {
	private Player p1;
	private Player p2;
	private Player[] p = new Player[2];
	private String[] pp = new String[2];
	
	
	@Before
	public void setUp() {
		p1 = new HumanPlayer("Mel");
		p2 = new HumanPlayer("Vin");
		p[0] = p1;
		p[1] = p2;
		pp[0] = "Mel";
		pp[1] = "Vin";
	}
	
	@Test
	public void testGetName() {
		assertTrue(p1.getName() == "Mel");
		assertTrue(p2.getName() == "Vin");
		assertFalse(p1.getName() == "Jer");
		assertFalse(p2.getName() == "Emy");
	}
	
	@Test
	public void testGetScoreCounter() {
		assertTrue(p1.getScoreCounter() == 0);
		assertTrue(p2.getScoreCounter() == 0);
	}
	
	@Test
	public void testGetFourRandomTiles() {
		p1.getStartingTiles();
		p2.getStartingTiles();
		assertEquals(p1.getFourRandomTiles().size(), 4);
		assertEquals(p2.getFourRandomTiles().size(), 4);
	}
	
	@Test
	public void testMoveFourTilesToHand() {
		p1.moveFourTilesToHand();
		p2.moveFourTilesToHand();
		assertEquals(p1.getTilesInHand().size(), 4);
		assertEquals(p2.getTilesInHand().size(), 4);
	}
	
	@Test
	public void testGetRandomTileForFirstMove() {
		p1.getRandomTileForFirstMove();
		p2.getRandomTileForFirstMove();
		assertTrue(p1.getRandomTileForFirstMove() != null);
		assertFalse(p1.getRandomTileForFirstMove() == null);
		assertTrue(p2.getRandomTileForFirstMove() != null);
		assertFalse(p2.getRandomTileForFirstMove() == null);
	}
	
	@Test
	public void testGetRandomTile() {
		p1.getRandomTile();
		p2.getRandomTile();
		assertTrue(p1.getTilesInHand() != null);
		assertTrue(p2.getTilesInHand() != null);
		assertFalse(p1.getTilesInHand() == null);
		assertFalse(p2.getTilesInHand() == null);
	}

}
