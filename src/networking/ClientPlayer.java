package networking;

import java.util.*;
import java.io.*;
import java.net.*;
import game.*;

public class ClientPlayer extends Player {
	protected Socket socket;
	protected BufferedReader in;
	protected BufferedWriter out;
	protected ClientGame clientGame;
	private Board board;
	private Game game;
	
	public ClientPlayer(String name, Socket socket) throws IOException {
		super(name);
		this.socket = socket;
		this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		this.out = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
		sendMessage("nickname " + super.name);
	}
	
	public void run() {
    	String message;
		try {
			while ((message = in.readLine()) != null) {
				if (clientGame != null && game.gameOver(board)) {
					break;
				} else if (message.startsWith("400") || 
						message.startsWith("403") || message.startsWith("404")) { //### message
					System.out.println(message);
				} else if (message.split(" ")[0].
						equals("features")) { //features [feature] [feature] [...]
					System.out.print("Server's features are:");
					for (int i = 1; i < message.split(" ").length; i++) {
						if (i == (message.split(" ").length - 1)) {
							System.out.print(" " + message.split(" ")[i]);
						} else {
							System.out.print(" " + message.split(" ")[i] + ",");
						}
					}
				} else if (message.split(" ")[0].equals("start")) { //start <nn1> <nn2> [nn3] [nn4]
					String[] playerNames = new String[message.split(" ").length - 1];
					for (int i = 1; i < message.split(" ").length; i++) {
						playerNames[i - 1] = message.split(" ")[i];
					}
					this.clientGame = new ClientGame(this, playerNames);
					Thread clientGameThread = new Thread(clientGame);
					clientGameThread.start();
				} else if (message.equals("requestMove")) { //requestMove
					determineMove(clientGame.getBoard());
				} else if (message.split(" ")[0].equals("drawnTile")) { //drawnTile <player> <tile>
					int playerIndex = Integer.parseInt(message.split(" ")[1]);
					String tileString = "" + message.split(" ")[2].charAt(0) +
							message.split(" ")[2].charAt(1) + message.split(" ")[2].charAt(2);
					int tileValue = Integer.parseInt("" + message.split(" ")[2].charAt(3));
					Tile drawnTile = null;
					for (Tile tile : clientGame.getBoard().getBag()) {
						for (int i = 0; i < 3; i++) {
							if (tileString.charAt((i + 1) % 3) == tile.toString().charAt(0) &&
									tileString.charAt(i % 3) == tile.toString().charAt(1) &&
									tileString.charAt((i + 2) % 3) == tile.toString().charAt(2)) { 
								drawnTile = tile;
							}
						}
					}
					if (drawnTile != null && clientGame.getBoard().getBag().contains(drawnTile)) {
						clientGame.getBoard().getBag().remove(drawnTile);
						clientGame.getPlayers()[playerIndex].tileInHand.add(drawnTile);
						clientGame.update();
						System.out.println("Player" + clientGame.getPlayers()[playerIndex].getName()
								+ " just drew a " + tileString + " " + tileValue  + " tile.");
					}
					
				} else if (message.split(" ")[0].equals("switchedTile")) {
					//switchedTile <player> <oldTile> <newTile>
					int playerIndex = Integer.parseInt(message.split(" ")[1]);
					String oldTileString = "" + message.split(" ")[2].charAt(0) +
							message.split(" ")[2].charAt(1) + message.split(" ")[2].charAt(2);
					int oldValue = Integer.parseInt("" + message.split(" ")[2].charAt(3));
					String newTileString = "" + message.split(" ")[3].charAt(0) +
							message.split(" ")[3].charAt(1) + message.split(" ")[3].charAt(2);
					int newValue = Integer.parseInt("" + message.split(" ")[3].charAt(3));
					Tile selectedOldTile = null;
					Tile selectedNewTile = null;
					for (Tile handTile : clientGame.getPlayers()[playerIndex].tileInHand) {
						for (int i = 0; i < 3; i++) {
							if (oldTileString.charAt(i % 3) == handTile.toString().charAt(0) &&
									oldTileString.charAt((i + 1) % 3) == 
									handTile.toString().charAt(1) &&
									oldTileString.charAt((i + 2) % 3) == 
									handTile.toString().charAt(2)) {
								selectedOldTile = handTile;
							} else if (oldTileString.charAt((i + 1) % 3) ==
									handTile.toString().charAt(0) &&
									oldTileString.charAt(i % 3) == handTile.toString().charAt(1) &&
									oldTileString.charAt((i + 2) % 3) == 
									handTile.toString().charAt(2)) {
								selectedOldTile = handTile;
							}
						}
					}
					for (Tile bagTile : clientGame.getBoard().getBag()) {
						for (int i = 0; i < 3; i++) {
							if (newTileString.charAt((i + 1) % 3) == bagTile.toString().charAt(0) &&
									newTileString.charAt(i % 3) == bagTile.toString().charAt(1) &&
									newTileString.charAt((i + 2) % 3) == 
									bagTile.toString().charAt(2)) {
								selectedNewTile = bagTile;
							} else if (newTileString.charAt(i % 3) == 
									bagTile.toString().charAt(0) &&
									newTileString.charAt((i + 1) % 3) == 
									bagTile.toString().charAt(1) &&
									newTileString.charAt((i + 2) % 3) ==
									bagTile.toString().charAt(2)) {
								selectedNewTile = bagTile;
							}
						}
					}
					if (selectedNewTile != null && selectedOldTile != null) {
						clientGame.getBoard().getBag().add(selectedOldTile);
						clientGame.getPlayers()[playerIndex].tileInHand.remove(selectedOldTile);
						clientGame.getPlayers()[playerIndex].tileInHand.add(selectedNewTile);
						clientGame.getBoard().getBag().remove(selectedNewTile);
						clientGame.update();
						System.out.println("Player " + clientGame.getPlayers()
							[playerIndex].getName() + " switched his/her " +
							oldTileString + " " + oldValue + " Tile with a " + 
							newTileString + " " + newValue + ".");
					}

				} else if (message.split(" ")[0].equals("skippedMove")) { //skippedMove <player>
					System.out.println("Player " + clientGame.getPlayers()[Integer.parseInt(
							message.split(" ")[1])].getName() + " skipped his/her turn!");

				} else if (message.equals("end")) { //end
					game.gameOver(board);
				} else if (message.split(" ")[0].equals("placedTile")) { 
					//placedTile <player> <index> <tile>
					int playerIndex = Integer.parseInt(message.split(" ")[1]);
					int index = Integer.parseInt(message.split(" ")[2]);
					String tileString = "" + message.split(" ")[3].charAt(0) +
							message.split(" ")[3].charAt(1) + message.split(" ")[3].charAt(2);
					Tile placedTile = null;
					for (Tile handTile : clientGame.getPlayers()[playerIndex].tileInHand) {
						for (int i = 0; i < 3; i++) {
							if (tileString.charAt(i % 3) == handTile.toString().charAt(0) &&
									tileString.charAt((i + 1) % 3) == handTile.toString().charAt(1)
									&& tileString.charAt((i + 2) % 3) == 
									handTile.toString().charAt(2)) {
								placedTile = handTile;
							} else if (tileString.charAt((i + 1) % 3) == 
									handTile.toString().charAt(0) 
									&& tileString.charAt(i % 3) == 
									handTile.toString().charAt(1) &&
									tileString.charAt((i + 2) % 3) ==
									handTile.toString().charAt(2)) { 
								placedTile = handTile;
							}
						}
					}
					if (placedTile != null) {
						clientGame.getPlayers()[playerIndex].tileInHand.remove(placedTile);
						clientGame.getBoard().setField(index, placedTile);
					}
					
				} 
			}
		} catch (IOException e) {
			sendMessage("end");
			game.gameOver(board);
			System.out.println("Server has disconnected!");
			System.exit(0);
		}
    }
	
	public Socket getSocket() {
		return this.socket;
	}
	
	public void sendMessage(String message) {
		try {
			out.write(message);
			out.newLine();
			out.flush();
		} catch (IOException e) {
			game.gameOver(board);
			System.out.println("Server has disconnected!");
			System.exit(0);
		}
	}
	
	public int readInt(String prompt) {
        int value = 0;
        boolean intRead = false;
        @SuppressWarnings("resource")
        Scanner line = new Scanner(System.in);
        System.out.println(prompt);
        do {
            boolean chat = false;
            try (Scanner scannerLine = new Scanner(line.nextLine());) {
                if (scannerLine.hasNextInt()) {
                    intRead = true;
                    value = scannerLine.nextInt();
                } else if (scannerLine.hasNextLine()) {
                	String string = scannerLine.nextLine();
                	if (string.equals("HINT")) {
                		return -1;
                	} else if (string.startsWith("=")) {
                		sendMessage("chat " + string.substring(1));
                		chat = true;
                	}
                }
            }
            if (!chat) {
            	System.out.println(prompt);
            }
        } while (!intRead);
        return value;
    }
	
	public String readString(String prompt) {
        String string = "";
        boolean stringRead = false;
        @SuppressWarnings("resource")
        Scanner line = new Scanner(System.in);
        System.out.println(prompt);
        do {
        	boolean chat = false;
            try (Scanner scannerLine = new Scanner(line.nextLine());) {
                if (scannerLine.hasNextLine()) {
                    string = scannerLine.nextLine();
                    if (string.startsWith("=")) {
                    	sendMessage("chat " + string.substring(1));
                    	chat = true;
                    } else {
                        stringRead = true;
                    }
                }
            }
            if (!chat) {
            	System.out.println(prompt);
            }
        } while (!stringRead);
        return string;
    }
	
	private Tile readTile(String prompt, int i) {
		Tile t = this.getTilesInHand().get(i - 1);
		return t;
	}
	
	public void determineMove(Board board) {
			
		System.out.println(this.getName() + "'s Score: " + this.getScoreCounter());
		System.out.println("Tiles count inside the bag: " + Bag.getNumTilesInBag());
		
		if (this.noPossibleMove(board)) {
			System.out.println(this.tileInHand);
			this.chooseAction();
		} else {
			boolean valid2 = false;
			Tile n = null;
			while (!valid2) {
				String prompt = "> " + getName() + 
						", what index do you want to put the tile in?(type HINT for hint) ";
				int j = 0;
				int choice = 0;
				boolean checks = false;
				while (!checks) {
					String input = readString(prompt);
					if (input.equals("HINT")) {
						for (int i = 0; i < this.tileInHand.size(); i++) {
							for (int h = 0; h < Board.FIELDS; h++) {
								Tile test = this.getTilesInHand().get(i);
								if (board.isPossibleMove(h, test) && board.isEmptyField(h)) {
									System.out.println("Maybe you can put " + test +
											" on field " + h);
								}
							}
						
						}
		
					} else {
						boolean check1 = false;
						try {
							j = Integer.parseInt(input);
							check1 = true;
						} catch (NumberFormatException e) {
							System.out.println("error");
						}
						if (check1) {
							if (j >= 0 && j <= 35) {
								choice = j;
								checks = true;
							} 
							if (!checks) {
								System.out.println("ERROR: field " + j
							            + " is out of range");
							}
						}
					}	
				}
		
							
				if (board.oneNeighborColor(choice) || board.checkAllFieldEmpty()) {
					boolean valid = board.isField(choice) && board.isEmptyField(choice);
					while (!valid) {
					    System.out.println("ERROR: field " + choice
					            + " is not a valid choice.");
					    choice = readInt(prompt);
					    valid = board.isField(choice) && board.isEmptyField(choice);
					}
					
					String stringS = "> " + getName() + 
							", which tile do you want to use? (Insert 1,2,3 or 4) ";
					int i = readInt(stringS);
					Tile t = null;
					boolean check = false;
					if (i >= 1 && i <= 4) {
						t = readTile(stringS, i);
						check = (t == null) ? false : true;
					} 
					while (!check) {
					    System.out.println("ERROR: Tile number " + i
					            + " is not a valid choice.");
					    i = readInt(stringS);
					    if (i >= 1 && i <= 4) {
							t = readTile(stringS, i);
							check = (t == null) ? false : true;	
					    }					
					}
					boolean valids = this.getTilesInHand().contains(t);
					while (!valids) {
					    System.out.println("ERROR: Tile " + choice
					            + " is not a valid choice.");
					    t = readTile(stringS, i);
					    valids = this.getTilesInHand().contains(t);
					}
				
					boolean check2 = false;
					while (!check2) {
						String tile = "> " + getName() + 
								", insert the tile!(VERTICAL, RIGHT, LEFT)";
						String tiles = readString(tile);
						try {
							char verticalTileChoice = tiles.charAt(0);
							char rightTileChoice = tiles.charAt(1);
							char leftTileChoice = tiles.charAt(2);	
							if (this.checkAllColor(t, verticalTileChoice, 
									rightTileChoice, leftTileChoice)) {
								n = this.newTile(verticalTileChoice, 
										rightTileChoice, leftTileChoice, i);
								
								if (board.isPossibleMove(choice, n)) {
									board.setField(choice, n);
									this.getTilesInHand().remove(t);
									this.getRandomTile();
									this.scoreCounter(board, choice, t);
									valid2 = true;
								} else {
									System.out.println("Invalid Move");
								}
							} else {
								System.out.println("Invalid characters!!");
							}
							check2 = true;
						} catch (NullPointerException e) {
							System.out.println("not possible");
						}					
					}				
				} else {
					System.out.println(
							"That field has no neighbour or is already filled with a Tile!");
				}
			}
		}
	}

	public Tile newTile(char vertical, char right, char left, int value) {
		return new Tile(vertical, right, left, value);
	}
	
	public void chooseAction() {
		String stringS = "> There is no more possible move! " + getName() + 
				", choose an action: [Switch Tiles, Skip Move] (Insert 1 or 2) ";
		int b = readInt(stringS);
		if (b == 2) {
			this.skipMove();
		} else {
			this.switchTiles();
		}
	}
	
	
	public void skipMove() {
	}
	
	public void switchTiles() {
		if (Bag.getNumTilesInBag() != 0) {
			String stringS = "> " + getName() + 
					", which tile do you want to switch? (Insert 1,2,3 or 4) ";
			int b = readInt(stringS);
			Tile a = readTile(stringS, b);
			boolean valids = false;
			while (!valids) {
				if (b >= 1 || b <= 4) {
					this.getRandomTile();
					this.getTilesInHand().remove(a);
					Bag.getTileInBag().add(a);
					valids = true;
				} else {
					System.out.println("ERROR: Tile " + b
					            + " is not a valid choice.");
					b = readInt(stringS);
				} 
			}
		} else {
			System.out.println("No more tiles inside the bag!");
			this.skipMove();
		}
	}
	
	public boolean checkAllColor(Tile t, char a, char b, char c) {
		if (t.chars.contains(a)) {
			t.chars.remove((Character) a);
			if (t.chars.contains(b)) {
				t.chars.remove((Character) b);
				if (t.chars.contains(c)) {
					t.chars.clear();
					t.chars.add(a);
					t.chars.add(b);
					t.chars.add(c);
					return true;
				} else {
					t.chars.remove((Character) c);
					t.chars.add(a);
					t.chars.add(b);
					t.chars.add(c);
					return false;
				}
			} else if (t.chars.contains(c)) {
				t.chars.remove((Character) c);
				if (t.chars.contains(b)) {
					t.chars.clear();
					t.chars.add(a);
					t.chars.add(b);
					t.chars.add(c);
					return true;
				} else {
					t.chars.clear();
					t.chars.add(a);
					t.chars.add(b);
					t.chars.add(c);
					return false;
				}
			} else {
				t.chars.clear();
				t.chars.add(a);
				t.chars.add(b);
				t.chars.add(c);
				return false;
			}
		} else if (t.chars.contains(b)) {
			t.chars.remove((Character) b);
			if (t.chars.contains(a)) {
				t.chars.remove((Character) a);
				if (t.chars.contains(c)) {
					t.chars.remove((Character) c);
					t.chars.add(a);
					t.chars.add(b);
					t.chars.add(c);
					return true;
				} else {
					t.chars.clear();
					t.chars.add(a);
					t.chars.add(b);
					t.chars.add(c);
					return false;
				}
			} else if (t.chars.contains(c)) {
				t.chars.remove((Character) c);
				if (t.chars.contains(a)) {
					t.chars.remove((Character) a);
					t.chars.add(a);
					t.chars.add(b);
					t.chars.add(c);
					return true;
				} else {
					t.chars.clear();
					t.chars.add(a);
					t.chars.add(b);
					t.chars.add(c);
					return false;
				}
			} else {
				t.chars.clear();
				t.chars.add(a);
				t.chars.add(b);
				t.chars.add(c);
				return false;
			}
		} else if (t.chars.contains(c)) {
			t.chars.remove((Character) c);
			if (t.chars.contains(b)) {
				t.chars.remove((Character) b);
				if (t.chars.contains(a)) {
					t.chars.remove((Character) a);
					t.chars.add(a);
					t.chars.add(b);
					t.chars.add(c);
					return true;
				} else {
					t.chars.clear();
					t.chars.add(a);
					t.chars.add(b);
					t.chars.add(c);
					return false;
				}
			} else if (t.chars.contains(a)) {
				t.chars.remove((Character) a);
				if (t.chars.contains(b)) {
					t.chars.remove((Character) b);
					t.chars.add(a);
					t.chars.add(b);
					t.chars.add(c);
					return true;
				} else {
					t.chars.clear();
					t.chars.add(a);
					t.chars.add(b);
					t.chars.add(c);
					return false;
				}
			} else {
				t.chars.clear();
				t.chars.add(a);
				t.chars.add(b);
				t.chars.add(c);
				return false;
			}
		} else {
			return false;
		}
		
		
	}
			



}

