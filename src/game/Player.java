package game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class Player {
    // -- Instance variables -----------------------------------------
	protected String name;	
	private int scoreCounter = 0;
	protected Board b;
	public List<Tile> tileInHand = new ArrayList<>(Collections.emptyList());
	protected List<Tile> fourRandomTiles = new ArrayList<>(Collections.emptyList());
	
	// -- Constructors -----------------------------------------------
	
	/*@
    requires name != null;
    ensures this.getName() == name;
	*/
	/**
	 * Creates a new Player object.
	 * @param name
	 */
	public Player(String name) {
		this.name = name;
	}
	
	public Player() {
		this("abc");
	}
	
	 // -- Queries ----------------------------------------------------
	
	/**
	 * Returns the name of the player.
	 */
    /*@ pure */ public String getName() {
		return name;
	}
	
	/*@
	requires i >= 0 & i <= 35;
	requires b != null;
	 */
    /**
     * Counts the score of a player after placing a tile on the board.
     * @param b
     * @param i
     * @param t
     */
	public void scoreCounter(Board b, int i, Tile t) {
		this.scoreCounter = this.scoreCounter + (b.boardMultiplier(i) * 
				b.neighborCounter(i, t) * b.getScoreMap().get(t));
		
	}
	
	/**
	 * Returns the current score of a player.
	 */
    /*@ pure */ public int getScoreCounter() {
		return this.scoreCounter;
	}
	
    /*@
     requires b != null;
     */
    /**
     * Gets a random tile to determine player that goes first.
     * @param b
     */
    /*@ pure */ public int findFirstMove(Board b) {
		Tile p = this.getRandomTileForFirstMove();
		int a = b.getScoreMap().get(p);
		return a;
	}

    /*@
    requires b != null & !b.isFull();
  */	
	public abstract void determineMove(Board b) throws IOException;

	/*@
	 ensures getFourRandomTiles().size() != 0;
	 */
	/**
	 * Gets 4 random tiles from the bag for a player.
	 */
	public void getStartingTiles() {
		fourRandomTiles.clear();
		Random rand = new Random();
		for (int i = 0; i < 4; i++) {
			if (Bag.tileInBag.size() == 0) {
				break;
			}
			int j = rand.nextInt(Bag.tileInBag.size());
			Tile randomElement = Bag.tileInBag.get(j);
			fourRandomTiles.add(randomElement);
			Bag.tileInBag.remove(j);
		}
	}
	
	/**
	 * Returns four random tiles.
	 */
    /*@ pure */ public List<Tile> getFourRandomTiles() {
		return this.fourRandomTiles;
	}
	
	/*@
	 ensures getTilesInHand().size() != 0;
	 */
    /**
     * Moves the four random tiles to a player's hand.
     */
	public void moveFourTilesToHand() {
		this.getStartingTiles();
		for (int i = 0; i < 4; i++) {
			if (Bag.tileInBag.size() == 0) {
				break;
			}
			tileInHand.add(this.getFourRandomTiles().get(i));
		}
	}
	
	/*@
	 ensures \result != null;
	 */
	/**
	 * Returns the random tile to determine which player goes first.
	 */
    /*@ pure */ public Tile getRandomTileForFirstMove() {
		Random rand = new Random();
		int j = rand.nextInt(Bag.tileInBag.size());
		Tile randomElement = Bag.tileInBag.get(j);
		return randomElement;
		
	}
	
	/*@
	 requires Bag.tileInBag.size() != 0;
	 ensures getTilesInHand() != null;
	 */
    /**
     * Gets a random tile from the bag.
     */
	public void getRandomTile() {
		Random rand = new Random();
		if (Bag.tileInBag.size() != 0) {
			int j = rand.nextInt(Bag.tileInBag.size());
			Tile randomElement = Bag.tileInBag.get(j);
			Bag.tileInBag.remove(j);
			this.getTilesInHand().add(randomElement);
		}
	}
	
	/**
	 * Returns tiles in a player's hand.
	 */
    /*@ pure */ public List<Tile> getTilesInHand() {
		return this.tileInHand;
	}
	
	/*@
	 ensures \result == true || \result == false;
	 */
    /**
     * Checks if there is no possible move for a player in a turn.
     * @param b
     * @return
     */
    /*@ pure */ public boolean noPossibleMove(Board b) {
		List<Integer> checker = new ArrayList<>(Collections.emptyList());
		boolean a;
		int d;
		for (int j = 0; j < 4; j++) {
			for (int i = 0; i < Board.FIELDS; i++) {
				Tile c = this.getTilesInHand().get(j);
				a = b.isEmptyField(i) && b.isPossibleMove(i, c);
				if (a) {
					d = 1;
					checker.add(d);
				} else {
					d = 0;
					checker.add(d);
				}
			}
		}
		return !checker.contains(1);
	}
}
