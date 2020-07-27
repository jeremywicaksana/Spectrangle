package networking;

import java.util.*;

import game.Bag;
import game.Board;
import game.Tile;

import java.io.*;
import java.net.*;

public class ComputerClientPlayer extends ClientPlayer implements Runnable {
	
	private Board board;
	
	public ComputerClientPlayer(String name, Socket socket) throws IOException {
		super(name, socket);
	}
	

	@Override
	public void determineMove(Board board) {
		System.out.println(this.getName() + "'s Score: " + this.getScoreCounter());
		System.out.println("Tiles count inside the bag: " + Bag.getNumTilesInBag());

		System.out.println(this.board);
		
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
