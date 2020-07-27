package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.HashMap;

public class Board {	
    // -- Instance variables -----------------------------------------
	List<Tile> neighborFields =  new ArrayList<>(Collections.emptyList());
	List<Integer> index =  new ArrayList<>(Collections.emptyList());
	public Tile[] field;
	public int multiplier;
	public static final int TOP = 1;
	public static final int DOWN = -1; 
	public static int moveCounter = 0;
	public static final int FIELDS = 36;
	
	public static List<Integer> values = new ArrayList<>(Arrays.asList(null, null,
			null, null, null, null,	null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null,	null, null, null, null, null, null,
			null, null, null, null, null, null, null, null));
    public static List<Character> vertical = new ArrayList<>(Arrays.asList(null, null, 
    		null, null, null, null, null, null, null, null, null, null, null, null, null, 
    		null, null, null, null, null, null,	null, null, null, null, null, null, null, 
    		null, null, null, null, null, null, null, null));
    public static List<Character> left = new ArrayList<>(Arrays.asList(null, null, null, 
    		null, null, null, null, null, null, null, null, null, null, null, null, null, 
    		null, null, null, null, null, null, null, null, null, null, null, null, null,
    		null, null, null, null, null, null, null));
    public static List<Character> right = new ArrayList<>(Arrays.asList(null, null, null, null, 
    		null, null, null, null, null, null, null, null, null, null, null, null, null, null, 
    		null, null, null, null,	null, null, null, null, null, null, null, null, null, null,
    		null, null, null, null));

    //@ public invariant field.length == 36;
    /*@ invariant (\forall int i; i < 36;
        getField(i) == null || getField(i) != null); */ 
	// -- Constructors -----------------------------------------------
    
    /**
     * Creates an empty Board.
     */
    //@ ensures (\forall int i; i < 36; getField(i) == null);
	public Board() {		
		field = new Tile[FIELDS];
		for (int i = 0; i < 36; i++) {
			index.add(i);
			field[i] = null;
		}
		this.setScore();
	}
	
	/**
	 * Creates a deep copy of this field.
	 */
	/*@ ensures (\forall int i; i < field.length;
	                            \result.getField(i) == getField(i));
	 @*/
	public Board deepCopy() {
    	Board copy = new Board();
    	for (int i = 0; i < copy.field.length; i++) {
    		copy.field[i] = this.field[i];
    	}
        return copy;
	}
	
	/**
	 * Returns the index belonging to the field in the board.
	 */
	/*@ pure */ public List<Integer> getIndex() {
		return index;
	}
	
	/**
	 * @param index
	 * Checks if index is part of the field on the board.
	 */
	//@ ensures \result == (index >= 0 && index < FIELDS);
	/*@ pure */ public boolean isField(int index) {
		boolean check = index < FIELDS && index >= 0 ? true : false;
		return check;
	}
	
	/**
	 * Returns the field of a chosen index.
	 * @param index
	 */
	/*@ pure */ public Tile getField(int index) {
		return field[index];
	}
	
	/**
	 * Checks if a field is empty.
	 * @param i
	 */
	/*@ pure */ public boolean isEmptyField(int i) {
    	boolean emptyField = this.getField(i) == null ? true : false;
        return emptyField;
    }
	
	/**
	 * Checks if a field is occupied.
	 */
	/*@
	 ensures \result == true || \result == false;
	 */
	/*@ pure */ public boolean isFull() {
    	int i = 0;
    	boolean full = true;
    	while (i < this.field.length && full == true) {
    		if (this.field[i] == null) {
    			full = false;
    		}
    		i = i + 1;
    	}
    	return full;
	}
	
	/**
	 * Resets the board.
	 */
	/*@
	 ensures field == null;
	 */
	public void reset() {
    	for (int i = 0; i < FIELDS; i++) {
    		field[i] = null;
    	}
    	resetMoveCounter();
	}
	
	/**
	 * Puts a tile on the field of a chosen index.
	 * @param i
	 * @param t
	 */
	public void setField(int i, Tile t) {
    	field[i] = t;
    	Board.left.remove(i);
    	Board.left.add(i, t.left);
    	Board.right.remove(i);
    	Board.right.add(i, t.right);
    	Board.vertical.remove(i);
    	Board.vertical.add(i, t.vertical);
    	Board.values.remove(i);
    	Board.values.add(i, this.getScoreMap().get(t));
    	setMoveCounter();
	    	
	}
	public static void main(String[] args) {
		Board bd = new Board();		
		bd.setField(0, Bag.BBB);
		System.out.println(SpectrangleBoardPrinter.
				getBoardString(Board.values, Board.vertical, Board.left, Board.right));
	}

	/**
	 * Returns the multiplier of a field on the board.
	 * @param i
	 */
	/*@
	 requires i < field.length;
	 */
	/*@ pure */ public int boardMultiplier(int i) {
		if (i == 2) {
			this.multiplier = 3;
		} else if (i == 10 || i == 14 || i == 30) {
			this.multiplier = 2;
		} else if (i == 11 || i == 13 || i == 20) {
			this.multiplier = 4;
		} else if (i == 26 || i == 34) {
			this.multiplier = 3;
		} else {
			this.multiplier = 1;
		}
		
		return this.multiplier;
	}
	
	/**
	 * Returns the number of moves done by a player.
	 */
	/*@ pure */ public static int getMoveCounter() {
		return moveCounter;
	}
	
	/**
	 * Resets the move counter to 0.
	 */
	public static void resetMoveCounter() {
		moveCounter = 0;
	}
	
	/**
	 * Increments the move counter after each turn. 
	 */
	public static void setMoveCounter() {
		moveCounter++;
	}
	
	/**
	 * Checks if the field is facing up or down.
	 * @param i
	 */
	public int verticalTopOrDown(int i) {
		List<Integer> verticalTop =  new ArrayList<>(Arrays.asList(2, 5, 7, 10, 12, 14, 17, 19, 21,
				23, 26, 28, 30, 32, 34));
		if (verticalTop.contains(i)) {
			return TOP;
		} else {
			return DOWN;
		}
	}
	
	public void setNeighborFields() {
		for (int j = 0; j < FIELDS; j++) {
			this.setNeighborFields(j);
		}
	}

	/**
	 * Sets the neighboring fields of every field on the board.
	 * @param i
	 */
	/*@ pure */ public List<Tile> setNeighborFields(int i) {
		this.neighborFields.clear();
		if (this.verticalTopOrDown(i) == TOP) {
			this.neighborFields.add(field[i - 1]);
			this.neighborFields.add(field[i + 1]);
			if (i == 2) {
				this.neighborFields.add(field[0]);
			} else if (i >= 5 && i <= 7) {
				this.neighborFields.add(field[i - 4]);
			} else if (i >= 10 && i <= 14) {
				this.neighborFields.add(field[i - 6]);
			} else if (i >= 17 && i <= 23) {
				this.neighborFields.add(field[i - 8]);
			} else if (i >= 26 && i <= 34) {
				this.neighborFields.add(field[i - 10]);
			}
		} else if (this.verticalTopOrDown(i) == DOWN) {
			if (i >= 27 && i <= 33) {
				this.neighborFields.add(field[i - 1]);
				this.neighborFields.add(field[i + 1]);
				this.neighborFields.add(null);
			} else {
				if (i == 0) {
					this.neighborFields.add(null);
					this.neighborFields.add(null);
					this.neighborFields.add(field[2]);
				} else if (i == 1) {
					this.neighborFields.add(null);
					this.neighborFields.add(field[2]);
					this.neighborFields.add(field[5]);
				} else if (i == 3) {
					this.neighborFields.add(field[2]);
					this.neighborFields.add(null);
					this.neighborFields.add(field[7]);
				} else if (i == 4) {
					this.neighborFields.add(null);
					this.neighborFields.add(field[5]);
					this.neighborFields.add(field[10]);
				} else if (i == 8) {
					this.neighborFields.add(field[7]);
					this.neighborFields.add(null);
					this.neighborFields.add(field[14]);
				} else if (i == 9) {
					this.neighborFields.add(null);
					this.neighborFields.add(field[10]);
					this.neighborFields.add(field[17]);
				} else if (i == 15) {
					this.neighborFields.add(field[14]);
					this.neighborFields.add(null);
					this.neighborFields.add(field[23]);
				} else if (i == 16) {
					this.neighborFields.add(null);
					this.neighborFields.add(field[17]);
					this.neighborFields.add(field[26]);
				} else if (i == 24) {
					this.neighborFields.add(field[23]);
					this.neighborFields.add(null);
					this.neighborFields.add(field[34]);
				} else if (i == 25) {
					this.neighborFields.add(null);
					this.neighborFields.add(field[26]);
					this.neighborFields.add(null);
				} else if (i == 35) {
					this.neighborFields.add(field[34]);
					this.neighborFields.add(null);
					this.neighborFields.add(null);
				} else if (i == 6 || i == 11 || i == 13 
						|| i == 18 || i == 20 || i == 22) {
					this.neighborFields.add(field[i - 1]);
					this.neighborFields.add(field[i + 1]);
					if (i == 6) {	
						this.neighborFields.add(field[12]);
					} else if (i == 11 || i == 13) {
						this.neighborFields.add(field[i + 8]);
					} else if (i == 18 || i == 20 || i == 22) {
						this.neighborFields.add(field[i + 10]);
					}
				} 
			}
		}
		return this.neighborFields;
	}
	
	/**
	 * Checks that the left side of the tile has
	 * the same color as the right side of the left neighboring tile.
	 * @param i
	 * @param t
	 */
	/*@ pure */ public boolean checkLeftSide(int i, Tile t) {
		if (this.setNeighborFields(i).get(0) != null) {
			if (t.left == this.setNeighborFields(i).get(0).right || this.setNeighborFields(
					i).get(0).right == Tile.WHITE || t.left == Tile.WHITE) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}
	
	/**
	 * Checks that the right side of the tile has
	 * the same color as the left side of the right neighboring tile.
	 * @param i
	 * @param t
	 */
	/*@ pure */ public boolean checkRightSide(int i, Tile t) {
		if (this.setNeighborFields(i).get(1) != null) {
			if (t.right == this.setNeighborFields(i).get(1).left || this.setNeighborFields(
					i).get(1).left == Tile.WHITE || t.right == Tile.WHITE) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}
	
	/**
	 * Checks that the vertical side of the tile has
	 * the same color as the vertical side of the vertical neighboring tile.
	 * @param i
	 * @param t
	 */
	/*@ pure */ public boolean checkVerticalSide(int i, Tile t) {
		if (this.setNeighborFields(i).get(2) != null) {
			if (t.vertical == this.setNeighborFields(i).get(2).vertical || this.setNeighborFields(
					i).get(2).vertical == Tile.WHITE || t.vertical == Tile.WHITE) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}
	
	
	/*@ pure */ public boolean oneNeighborColor(int i) {	
		if (this.setNeighborFields(i).get(0) != null) {
			return true;
		} else if (this.setNeighborFields(i).get(1) != null) {
			return true;
		} else if (this.setNeighborFields(i).get(2) != null) {
			return true;
		} else {
			return false;
		}
		
	}
		
	/**
	 * Checks if all the fields on the board are empty.
	 */
	/*@
	 ensures \result == true || \result == false;
	 */
	/*@ pure */ public boolean checkAllFieldEmpty() {
		boolean check = true;
		boolean checkAllFieldEmpty = true;
		while (check) {
			for (int j = 0; j < FIELDS; j++) {
				if (!this.isEmptyField(j)) {
					check = false;
					checkAllFieldEmpty = false;
				}
			}
			check = false;
		}
		
		return checkAllFieldEmpty;
	}
	
	/**
	 * Checks if a move chosen by a player is possible.
	 * @param i
	 * @param t
	 */
	/*@ pure */ public boolean isPossibleMove(int i, Tile t) {
		boolean a = false;
		if (this.checkAllFieldEmpty()) {
			List<Integer> b = new ArrayList<>(Arrays.asList(2, 10, 11, 13, 14, 20, 26, 30, 34));
			if (b.contains(i)) {
				return false;
			} else {
				return true;
			}
		} else if (this.oneNeighborColor(i)) {
			if (this.checkLeftSide(i, new Tile(t.vertical, t.right, t.left, t.value)) && 
					this.checkRightSide(i,	new Tile(t.vertical, t.right, t.left, t.value)) 
					&& this.checkVerticalSide(i, new Tile(t.vertical, t.right, t.left, t.value))) {
				a = true;
			}
		}
		
		if (a) {
			return true;
		}
		return false;
	}
	
	public int neighborCounter(int i, Tile t) {
		int counter = 0;
		List<Integer> neighborCounter = new ArrayList<>();
		if (this.checkLeftSide(i, t) && this.setNeighborFields(i).get(0) != null) {
			neighborCounter.add(1);
			counter++;
		}
		
		if (this.checkRightSide(i, t) && this.setNeighborFields(i).get(1) != null) {
			neighborCounter.add(1);
			counter = counter++;
		}
		
		if (this.checkVerticalSide(i, t) && this.setNeighborFields(i).get(2) != null) {
			neighborCounter.add(1);
			counter = counter++;
		}
		
	
			
		HashMap<Integer, Integer> frequencymap = new HashMap<Integer, Integer>();
		for (Integer a :neighborCounter) {
			if (frequencymap.containsKey(a)) {
				frequencymap.put(a, frequencymap.get(a) + 1); 
			} else {
				frequencymap.put(a, 1); 
			}
		}
		
		if (frequencymap.get(1) == null) {
			return 1;
		} else {
			return frequencymap.get(1);
		}
	}
	
	public Map<Tile, Integer> tileScore = new HashMap<Tile, Integer>();
	
	/**
	 * Sets the score for each tile in the bag.
	 */
	public void setScore() {
		for (int i = 0; i < 5; i++) {
			tileScore.put(Bag.tileInBag.get(i), 6);
		}
		for (int j = 5; j < 15; j++) {
			tileScore.put(Bag.tileInBag.get(j), 5);
		}
		
		for (int k = 15; k < 25; k++) {
			tileScore.put(Bag.tileInBag.get(k), 4);
		}
		for (int l = 25; l < 29; l++) {
			tileScore.put(Bag.tileInBag.get(l), 3);
		}
		for (int m = 29; m < 32; m++) {
			tileScore.put(Bag.tileInBag.get(m), 2);
		}
		for (int n = 32; n < 36; n++) {
			tileScore.put(Bag.tileInBag.get(n), 1);
		}
	}
	/**
	 * Returns the score of a tile.
	 */
	/*@ pure */ public Map<Tile, Integer> getScoreMap() {
		return tileScore;
	}
	
	/**
	 * Returns a list of tiles inside the bag.
	 */
	/*@ pure */ public List<Tile> getBag() {
		return Bag.tileInBag;
	}	
	
	

}
