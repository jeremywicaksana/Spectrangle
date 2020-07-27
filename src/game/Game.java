package game;

import java.io.IOException;
import java.util.Scanner;

//import ServerClient.ServerPlayer;

public class Game {
    // -- Instance variables -----------------------------------------
	protected Board board;
	protected Player[] players;
	Bag bag;
	Player player;

    // -- Constructors -----------------------------------------------

	/**
	 * Instantiate the number of players.
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param p4
	 */
	//@ ensures p1 != null; 
	//@ ensures p2 != null;
	public Game(Player p1, Player p2, Player p3, Player p4) {
		board = new Board();
		bag = new Bag();
		if (p3 == null && p4 == null) {
			players = new Player[2];
			this.findFirstMove(p1, p2);	
		} else if (p4 == null) {
			players = new Player[3];
			this.findFirstMove(p1, p2, p3);
		} else {
			players = new Player[4];
			this.findFirstMove(p1, p2, p3, p4);
		}
		
	}
	//public Game(ServerPlayer[] serverPl) {		
	//}	

	/**
	 * Starts the game.
	 */
	//@ ensures players.length >= 2 && players.length <= 4;
	//2-4 player
	public void start() {
		boolean doorgaan = true;
		if (players.length == 2) {
			while (doorgaan) {
				reset();
				play();
				doorgaan = readBoolean("\n> Play another time? (y/n)?", "y", "n");
			}
		} else if (players.length == 3) {
			while (doorgaan) {
				reset();
				play();
				doorgaan = readBoolean("\n> Play another time? (y/n)?", "y", "n");
			}
		} else if (players.length == 4) {
			while (doorgaan) {
				reset();
				play();
				doorgaan = readBoolean("\n> Play another time? (y/n)?", "y", "n");
			}
		}
		
	}
	

	@SuppressWarnings("resource")
	private boolean readBoolean(String prompt, String yes, String no) {
        String answer;
        Scanner in;
        do {
            System.out.print(prompt);
            in = new Scanner(System.in);
            answer = in.hasNextLine() ? in.nextLine() : null;
        } while (answer == null || (!answer.equals(yes) && !answer.equals(no)));
        return answer.equals(yes);
    }
     
	/**
	 * Resets the board.
	 */
	public void reset() {
		board.reset();
	}
	
	/**
	 * Updates the board after each turn.
	 */
	public void update() {
		System.out.println(SpectrangleBoardPrinter.
				getBoardString(Board.values, Board.vertical, Board.left, Board.right));
	}
	
	/**
	 * Find the player to start first based on the highest value
	 * of the random tile took.
	 * @param p1
	 * @param p2
	 */
	//@ ensures p1 != null && p2 != null;
	public void findFirstMove(Player p1, Player p2) {		
		boolean check = false;
		while (!check) {
			int a = p1.findFirstMove(board);
			System.out.println(p1.getName() + ": " + a);
			int b = p2.findFirstMove(board);
			System.out.println(p2.getName() + ": " + b);
			
			if (a > b) {
				System.out.println(p1.getName() + " goes first!");
				players[0] = p1;
				players[1] = p2;
				check = true;
			} else if (b > a) {
				System.out.println(p2.getName() + " goes first!");
				players[0] = p2;
				players[1] = p1;
				check = true;
			} 
		}
	}
	
	/**
	 * Find the player to start first based on the highest value
	 * of the random tile took.
	 * @param p1
	 * @param p2
	 * @param p3
	 */
	//@ ensures p1 != null && p2 != null && p3 != null;
	public void findFirstMove(Player p1, Player p2, Player p3) {		
		boolean check = false;
		while (!check) {
			int a = p1.findFirstMove(board);
			System.out.println(p1.getName() + ": " + a);
			int b = p2.findFirstMove(board);
			System.out.println(p2.getName() + ": " + b);
			int c = p3.findFirstMove(board);
			System.out.println(p3.getName() + ": " + c);
			
			if (a > b && a > c && b > c) {
				System.out.println(p1.getName() + " goes first!");
				players[0] = p1;
				players[1] = p2;
				players[2] = p3;
				check = true;
			} else if (a > b && a > c && c > b) {
				System.out.println(p1.getName() + " goes first!");
				players[0] = p1;
				players[1] = p3;
				players[2] = p2;
				check = true;
			} else if (b > a && b > c && c > a) {
				System.out.println(p2.getName() + " goes first!");
				players[0] = p2;
				players[1] = p3;
				players[2] = p1;
				check = true;
			} else if (b > a && b > c && a > c) {
				System.out.println(p2.getName() + " goes first!");
				players[0] = p2;
				players[1] = p1;
				players[2] = p3;
				check = true;
			} else if (c > a && c > b && b > a) {
				System.out.println(p3.getName() + " goes first!");
				players[0] = p3;
				players[1] = p2;
				players[2] = p1;
				check = true;
			} else if (c > a && c > b && a > b) {
				System.out.println(p3.getName() + " goes first!");
				players[0] = p3;
				players[1] = p1;
				players[2] = p2;
				check = true;
			} 
		}
	}
	
	/**
	 * Find the player to start first based on the highest value
	 * of the random tile took.
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param p4
	 */
	//@ ensures p1 != null && p2 != null && p3 != null && p4 != null;
	public void findFirstMove(Player p1, Player p2, Player p3, Player p4) {	
		boolean check = false;
		while (!check) {
			int a = p1.findFirstMove(board);
			System.out.println(p1.getName() + ": " + a);
			int b = p2.findFirstMove(board);
			System.out.println(p2.getName() + ": " + b);
			int c = p3.findFirstMove(board);
			System.out.println(p3.getName() + ": " + c);
			int d = p4.findFirstMove(board);
			System.out.println(p4.getName() + ": " + d);
		// a > b,c,d && b,c,d (random)
			if (a > b && a > c && a > d && b > d && b > c && c > d) {
				System.out.println(p1.getName() + " goes first!");
				players[0] = p1;
				players[1] = p2;
				players[2] = p3;
				players[3] = p4;
				check = true;
			} else if (a > b && a > c && a > d && b > d && b > c && d > c) {
				System.out.println(p1.getName() + " goes first!");
				players[0] = p1;
				players[1] = p2;
				players[2] = p4;
				players[3] = p3;
				check = true;
			} else if (a > b && a > c && a > d && c > b && c > d && b > d) {
				System.out.println(p1.getName() + " goes first!");
				players[0] = p1;
				players[1] = p3;
				players[2] = p2;
				players[3] = p4;
				check = true;
			} else if (a > b && a > c && a > d && c > d && c > b && d > b) {
				System.out.println(p1.getName() + " goes first!");
				players[0] = p1;
				players[1] = p3;
				players[2] = p4;
				players[3] = p2;
				check = true;
			} else if (a > b && a > c && a > d && d > b && d > c && b > c) {
				System.out.println(p1.getName() + " goes first!");
				players[0] = p1;
				players[1] = p4;
				players[2] = p2;
				players[3] = p3;
				check = true;
			} else if (a > b && a > c && a > d && d > b && d > c && c > b) {
				System.out.println(p1.getName() + " goes first!");
				players[0] = p1;
				players[1] = p4;
				players[2] = p3;
				players[3] = p2;
				check = true;
			// b > a,c,d && a,c,d (random)
			} else if (b > a && b > c && b > d && a > c && a > d && c > d) {
				System.out.println(p2.getName() + " goes first!");
				players[0] = p2;
				players[1] = p1;
				players[2] = p3;
				players[3] = p4;
				check = true;
			} else if (b > a && b > c && b > d && a > c && a > d && d > c) {
				System.out.println(p2.getName() + " goes first!");
				players[0] = p2;
				players[1] = p1;
				players[2] = p4;
				players[3] = p3;
				check = true;
			} else if (b > a && b > c && b > d && c > a && c > d && a > d) {
				System.out.println(p2.getName() + " goes first!");
				players[0] = p2;
				players[1] = p3;
				players[2] = p1;
				players[3] = p4;
				check = true;
			} else if (b > a && b > c && b > d && c > a && c > d && d > a) {
				System.out.println(p2.getName() + " goes first!");
				players[0] = p2;
				players[1] = p3;
				players[2] = p4;
				players[3] = p1;
				check = true;
			} else if (b > a && b > c && b > d && d > c && d > a && a > c) {
				System.out.println(p2.getName() + " goes first!");
				players[0] = p2;
				players[1] = p4;
				players[2] = p1;
				players[3] = p3;
				check = true;
			} else if (b > a && b > c && b > d && d > c && d > a && c > a) {
				System.out.println(p2.getName() + " goes first!");
				players[0] = p2;
				players[1] = p4;
				players[2] = p3;
				players[3] = p1;
				check = true;
			// c > a,b,d && a,b,d (random)
			} else if (c > a && c > b && c > d && a > d && a > b && b > d) {
				System.out.println(p3.getName() + " goes first!");
				players[0] = p3;
				players[1] = p1;
				players[2] = p2;
				players[3] = p4;
				check = true;
			} else if (c > a && c > b && c > d && a > d && a > b && d > b) {
				System.out.println(p3.getName() + " goes first!");
				players[0] = p3;
				players[1] = p1;
				players[2] = p4;
				players[3] = p2;
				check = true;
			} else if (c > a && c > b && c > d && b > a && b > d && a > d) {
				System.out.println(p3.getName() + " goes first!");
				players[0] = p3;
				players[1] = p2;
				players[2] = p1;
				players[3] = p4;
				check = true;
			} else if (c > a && c > b && c > d && b > a && b > d && d > a) {
				System.out.println(p3.getName() + " goes first!");
				players[0] = p3;
				players[1] = p2;
				players[2] = p4;
				players[3] = p1;
				check = true;
			} else if (c > a && c > b && c > d && d > a && d > b && a > b) {
				System.out.println(p3.getName() + " goes first!");
				players[0] = p3;
				players[1] = p4;
				players[2] = p1;
				players[3] = p2;
				check = true;
			} else if (c > a && c > b && c > d && d > a && d > b && b > a) {
				System.out.println(p3.getName() + " goes first!");
				players[0] = p3;
				players[1] = p4;
				players[2] = p2;
				players[3] = p1;
				check = true;
			// d > a,b,c && a,b,c (random)
			} else if (d > a && d > c && d > a && a > c && a > b && b > c) {
				System.out.println(p4.getName() + " goes first!");
				players[0] = p4;
				players[1] = p1;
				players[2] = p2;
				players[3] = p3;
				check = true;
			} else if (d > a && d > c && d > a && a > c && a > b && c > b) {
				System.out.println(p4.getName() + " goes first!");
				players[0] = p4;
				players[1] = p1;
				players[2] = p3;
				players[3] = p2;
				check = true;
			} else if (d > a && d > c && d > a && b > c && b > a && a > c) {
				System.out.println(p4.getName() + " goes first!");
				players[0] = p4;
				players[1] = p2;
				players[2] = p1;
				players[3] = p3;
				check = true;
			} else if (d > a && d > c && d > a && b > c && b > a && c > a) {
				System.out.println(p4.getName() + " goes first!");
				players[0] = p4;
				players[1] = p2;
				players[2] = p3;
				players[3] = p1;
				check = true;
			} else if (d > a && d > c && d > a && c > a && c > b && a > b) {
				System.out.println(p4.getName() + " goes first!");
				players[0] = p4;
				players[1] = p3;
				players[2] = p1;
				players[3] = p2;
				check = true;
			} else if (d > a && d > c && d > a && c > a && c > b && b > a) {
				System.out.println(p4.getName() + " goes first!");
				players[0] = p4;
				players[1] = p3;
				players[2] = p2;
				players[3] = p1;
				check = true;
			}
		}
	}

	/**
	 * Plays the actual Spectrangle game until a winner is found.
	 */
	// 2-4 player
	protected void play() {        
		if (players.length == 2) {
			players[0].moveFourTilesToHand();
	    	players[1].moveFourTilesToHand();
	    	update();
		
	    	
	    	while (!this.gameOver(board)) {
	        	if (!this.gameOver(board)) {
	        		System.out.println("Move number: " + Board.getMoveCounter());
	        		System.out.println(players[0].getName() + " have "  + players[0].tileInHand);
	        		System.out.println(players[1].getName() + " have "  + players[1].tileInHand);
	        		System.out.println(" ");
	        		try {
						players[0].determineMove(board);
					} catch (IOException e) {
						e.printStackTrace();
					}
	        		update();
	        	}
	        	if (!this.gameOver(board)) {
	        		System.out.println("Move number: " + Board.getMoveCounter());
	        		System.out.println(players[0].getName() + " have "  + players[0].tileInHand);
	        		System.out.println(players[1].getName() + " have "  + players[1].tileInHand);
	        		System.out.println(" ");
	        		try {
						players[1].determineMove(board);
					} catch (IOException e) {
						e.printStackTrace();
					}
	        		update();
	        	}
	        }
	    	theWinner();
		} else if (players.length == 3) {
			
			players[0].moveFourTilesToHand();
	    	players[1].moveFourTilesToHand();
	    	players[2].moveFourTilesToHand();
	    	update();
		
	    	
	    	while (!this.gameOver(board)) {
	        	if (!this.gameOver(board)) {
	        		System.out.println("Move number: " + Board.getMoveCounter());
	        		System.out.println(players[0].getName() + " have "  + players[0].tileInHand);
	        		System.out.println(players[1].getName() + " have "  + players[1].tileInHand);
	        		System.out.println(players[2].getName() + " have "  + players[2].tileInHand);
	        		System.out.println(" ");
	        		try {
						players[0].determineMove(board);
					} catch (IOException e) {
						e.printStackTrace();
					}
	        		update();
	        	}
	        	if (!this.gameOver(board)) {
	        		System.out.println("Move number: " + Board.getMoveCounter());
	        		System.out.println(players[0].getName() + " have "  + players[0].tileInHand);
	        		System.out.println(players[1].getName() + " have "  + players[1].tileInHand);
	        		System.out.println(players[2].getName() + " have "  + players[2].tileInHand);
	        		System.out.println(" ");
	        		try {
						players[1].determineMove(board);
					} catch (IOException e) {
						e.printStackTrace();
					}
	        		update();
	        	}
	        	if (!this.gameOver(board)) {
	        		System.out.println("Move number: " + Board.getMoveCounter());
	        		System.out.println(players[0].getName() + " have "  + players[0].tileInHand);
	        		System.out.println(players[1].getName() + " have "  + players[1].tileInHand);
	        		System.out.println(players[2].getName() + " have "  + players[2].tileInHand);
	        		System.out.println(" ");
	        		try {
						players[2].determineMove(board);
					} catch (IOException e) {
						e.printStackTrace();
					}
	        		update();
	        	}
	        }
	    	theWinner();
			
		} else if (players.length == 4) {
			players[0].moveFourTilesToHand();
	    	players[1].moveFourTilesToHand();
	    	players[2].moveFourTilesToHand();
	    	players[3].moveFourTilesToHand();
	    	update();
	    		
		
	    	while (!this.gameOver(board)) {
	        	if (!this.gameOver(board)) {
	        		System.out.println("Move number: " + Board.getMoveCounter());
	        		System.out.println(players[0].getName() + " have "  + players[0].tileInHand);
	        		System.out.println(players[1].getName() + " have "  + players[1].tileInHand);
	        		System.out.println(players[2].getName() + " have "  + players[2].tileInHand);
	        		System.out.println(players[3].getName() + " have "  + players[3].tileInHand);
	        		System.out.println(" ");
	        		try {
						players[0].determineMove(board);
					} catch (IOException e) {
						e.printStackTrace();
					}
	        		update();
	        	}
	        	if (!this.gameOver(board)) {
	        		System.out.println("Move number: " + Board.getMoveCounter());
	        		System.out.println(players[0].getName() + " have "  + players[0].tileInHand);
	        		System.out.println(players[1].getName() + " have "  + players[1].tileInHand);
	        		System.out.println(players[2].getName() + " have "  + players[2].tileInHand);
	        		System.out.println(players[3].getName() + " have "  + players[3].tileInHand);
	        		System.out.println(" ");
	        		
	        		try {
						players[1].determineMove(board);
					} catch (IOException e) {
						e.printStackTrace();
					}
	        		update();
	        	}
	        	if (!this.gameOver(board)) {
	        		System.out.println("Move number: " + Board.getMoveCounter());
	        		System.out.println(players[0].getName() + " have "  + players[0].tileInHand);
	        		System.out.println(players[1].getName() + " have "  + players[1].tileInHand);
	        		System.out.println(players[2].getName() + " have "  + players[2].tileInHand);
	        		System.out.println(players[3].getName() + " have "  + players[3].tileInHand);
	        		System.out.println(" ");
	        		try {
						players[2].determineMove(board);
					} catch (IOException e) {
						e.printStackTrace();
					}
	        		update();
	        	}
	        	if (!this.gameOver(board)) {
	        		System.out.println("Move number: " + Board.getMoveCounter());
	        		System.out.println(players[0].getName() + " have "  + players[0].tileInHand);
	        		System.out.println(players[1].getName() + " have "  + players[1].tileInHand);
	        		System.out.println(players[2].getName() + " have "  + players[2].tileInHand);
	        		System.out.println(players[3].getName() + " have "  + players[3].tileInHand);
	        		System.out.println(" ");
	        		try {
						players[3].determineMove(board);
					} catch (IOException e) {
						e.printStackTrace();
					}
	        		update();
	        	}
	        }
	    	theWinner();
		}
		
    }
	
	/**
	 * Determines the winner of the game based on the player 
	 * with the highest score.
	 */
	public void theWinner() {
		if (players.length == 2) {
			if (players[0].getScoreCounter() > players[1].getScoreCounter()) {
				System.out.println(players[0].getName() + 
						" is the winner with a score of " + players[0].getScoreCounter());
			} else if (players[1].getScoreCounter() > players[0].getScoreCounter()) {
				System.out.println(players[1].getName() + 
						" is the winner with a score of " + players[1].getScoreCounter());
			} else if (players[2].getScoreCounter() > players[0].getScoreCounter()) {
				System.out.println(players[2].getName() + 
						" is the winner with a score of " + players[2].getScoreCounter());
			} else if (players[3].getScoreCounter() > players[0].getScoreCounter()) {
				System.out.println(players[3].getName() + 
						" is the winner with a score of " + players[3].getScoreCounter());
			}
		} else if (players.length == 3) {
			if (players[0].getScoreCounter() > players[1].getScoreCounter() 
					&& players[0].getScoreCounter() > players[2].getScoreCounter()) {
				System.out.println(players[0].getName() + 
						" is the winner with a score of " + players[0].getScoreCounter());
			} else if (players[1].getScoreCounter() > players[0].getScoreCounter() 
					&& players[1].getScoreCounter() > players[2].getScoreCounter()) {
				System.out.println(players[1].getName() + 
						" is the winner with a score of " + players[1].getScoreCounter());
			} else if (players[2].getScoreCounter() > players[0].getScoreCounter()
					&& players[2].getScoreCounter() > players[1].getScoreCounter()) {
				System.out.println(players[2].getName() + 
						" is the winner with a score of " + players[2].getScoreCounter());
			} else if (players[3].getScoreCounter() > players[0].getScoreCounter() 
					&& players[3].getScoreCounter() > players[2].getScoreCounter()) {
				System.out.println(players[3].getName() + 
						" is the winner with a score of " + players[3].getScoreCounter());
			}	
		} else if (players.length == 4) {
			if (players[0].getScoreCounter() > players[1].getScoreCounter() 
					&& players[0].getScoreCounter() > players[2].getScoreCounter() 
					&& players[0].getScoreCounter() > players[3].getScoreCounter()) {
				System.out.println(players[0].getName() + 
						" is the winner with a score of " + players[0].getScoreCounter());
			} else if (players[1].getScoreCounter() > players[0].getScoreCounter() 
					&& players[1].getScoreCounter() > players[2].getScoreCounter() 
					&& players[1].getScoreCounter() > players[3].getScoreCounter()) {
				System.out.println(players[1].getName() + 
						" is the winner with a score of " + players[1].getScoreCounter());
			} else if (players[2].getScoreCounter() > players[0].getScoreCounter() 
					&& players[2].getScoreCounter() > players[1].getScoreCounter() 
					&& players[2].getScoreCounter() > players[3].getScoreCounter()) {
				System.out.println(players[2].getName() + 
						" is the winner with a score of " + players[2].getScoreCounter());
			} else if (players[3].getScoreCounter() > players[0].getScoreCounter() 
					&& players[3].getScoreCounter() > players[2].getScoreCounter() 
					&& players[3].getScoreCounter() > players[1].getScoreCounter()) {
				System.out.println(players[3].getName() + 
						" is the winner with a score of " + players[3].getScoreCounter());
			}	
		}
		
	}
	
	/**
	 * Ends the game when the bag is empty and there are no
	 * more possible moves for all players.
	 * @param b
	 */
	/*@ pure */ public boolean gameOver(Board b) {		
		if (players.length == 2) {
			if (Bag.tileInBag.size() == 0 && players[0].noPossibleMove(b) 
					&& players[1].noPossibleMove(b)) {
				return true;
			}
		} else if (players.length == 3) {
			if (Bag.tileInBag.size() == 0 && players[0].noPossibleMove(b) 
					&& players[1].noPossibleMove(b) && players[2].noPossibleMove(b)) {
				return true;
			}
		} else if (players.length == 4) {
			if (Bag.tileInBag.size() == 0 && players[0].noPossibleMove(b) && players[1].
					noPossibleMove(b) && players[2].noPossibleMove(b)	&& players[3].
					noPossibleMove(b)) {				
				return true;
			}
		}
		return false;
	}

	/*@ pure */ public Player getPlayer() {
		return this.player;
	}
	
}
