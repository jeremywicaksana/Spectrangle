package tests;

import org.junit.Before;
import org.junit.Test;

import game.Bag;
import game.Board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class BoardTest {
	private Board board;

	
	@Before
	public void setUp() {
		board = new Board();
	}
	
	@Test
	public void testDeepCopy() {
		board.setField(0, Bag.WWW);
		Board copy = board.deepCopy();	
		assertTrue(copy.field[0] == board.field[0]);
	}
	
	@Test
	public void testIsFieldIndex() {
		assertFalse(board.isField(37));
		assertTrue(board.isField(0));
		assertTrue(board.isField(9));
		assertFalse(board.isField(-1));
	}
	
	@Test
	public void testSetAndGetFieldIndex() {
		board.setField(0, Bag.WWW);
        assertEquals(Bag.WWW, board.getField(0));
        assertEquals(null, board.getField(1));
	}
	
	@Test
	public void testIsEmptyField() {
		board.setField(0, Bag.WWW);
		assertFalse(board.isEmptyField(0));
		assertTrue(board.isEmptyField(1));
		assertTrue(board.isEmptyField(3));
	}
	
	@Test
	public void testIsFull() {
		board.reset();
		assertFalse(board.isFull());
	}
	
	@Test
	public void testReset() {
		board.reset();
		assertTrue(board.isEmptyField(0));
		assertTrue(board.isEmptyField(1));
		assertFalse(!board.isEmptyField(3));
		assertFalse(!board.isEmptyField(5));		
	}
	
	@Test
	public void testSetField() {
		board.setField(0, Bag.WWW);
		assertTrue(board.getField(0) == Bag.WWW);
		assertFalse(board.getField(0) == null);
	}
	
	@Test
	public void testBoardMultiplier() {
		assertTrue(board.boardMultiplier(2) == 3);
		assertFalse(board.boardMultiplier(2) == 4);
		assertTrue(board.boardMultiplier(20) == 4);
		assertFalse(board.boardMultiplier(20) == 3);
	}
	
	@Test
	public void testVerticalTopOrDown() {
		assertTrue(board.verticalTopOrDown(2) == 1);
		assertFalse(board.verticalTopOrDown(2) == -1);
		assertTrue(board.verticalTopOrDown(11) == -1);
		assertFalse(board.verticalTopOrDown(11) == 1);		
	}
	
	@Test
	public void testCheckAllFieldEmpty() {
		assertTrue(board.isEmptyField(0));
		assertFalse(!board.isEmptyField(2));
		assertTrue(board.isEmptyField(14));
		assertFalse(!board.isEmptyField(14));
	}
	
	@Test
	public void testIsPossibleMove() {
		board.reset();
		for (int i = 0; i < 35; i++) {
			board.setField(34, Bag.BBB);
		}
		assertTrue(board.isPossibleMove(35, Bag.WWW));
	}	

}