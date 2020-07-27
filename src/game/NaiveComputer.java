package game;

import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
import java.util.Random;

public class NaiveComputer extends Player {
	Tile t;
	Game game;
	Board board;

	public NaiveComputer(String name) {
		super(name);
	}
	
	public void skipMove() {
	}


	@Override
	public void determineMove(Board board) throws IOException {
		System.out.println(this.getName() + "'s Score: " + this.getScoreCounter());
		System.out.println("Tiles count inside the bag: " + Bag.getNumTilesInBag());
		
		if (this.noPossibleMove(board)) {
			if (Bag.getNumTilesInBag() != 0) {
				if (this.getTilesInHand().size() == 4) {
					Random rand = new Random();
					int choice = rand.nextInt(this.getTilesInHand().size());
					this.getTilesInHand().remove(choice);
					this.getRandomTile();
				} else if (this.getTilesInHand().size() < 4) {
					this.getRandomTile();
				}
			} else {
				this.skipMove();
			}
		} else {
			boolean done = false;
			for (int i = 0; i < tileInHand.size() && !done; i++) {
				for (int j = 0; j < Board.FIELDS; j++) {											
					Tile choice = this.getTilesInHand().get(i);
					if (board.isPossibleMove(j, choice) && board.isEmptyField(j)) {
						board.setField(j, choice);
						this.getTilesInHand().remove(i);
						this.getRandomTile();
						this.scoreCounter(board, i, choice);
						done = true;
						break;
					}
				} 
			}
		}
	} 
	
}
