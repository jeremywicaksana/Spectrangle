package game;

import java.util.Scanner;


public class HumanPlayer extends Player {
	
	Tile t;
	Game game;
	Board b;
	
	
	public HumanPlayer(String name) {
        super(name);
    }
	
	public Tile newTile(char vertical, char right, char left, int value) {
		return new Tile(vertical, right, left, value);
	}
	
	public void chooseAction() {
		String stringS = "> There is no more possible move! " + getName() + 
				", choose an action: [Switch Tiles, Skip Move] (Insert 1 or 2)";
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
					", which tile do you want to switch? (Insert 1,2,3 or 4)";
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
						", what index do you want to put the tile in?(type HINT for hint)";
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
			
								
				if (board.oneNeighborColor(choice) || board.checkAllFieldEmpty()){
					boolean valid = board.isField(choice) && board.isEmptyField(choice);
					while (!valid) {
					    System.out.println("ERROR: field " + choice
					            + " is not a valid choice.");
					    choice = readInt(prompt);
					    valid = board.isField(choice) && board.isEmptyField(choice);
					}
					
					String stringS = "> " + getName() + 
							", which tile do you want to use? (Insert 1,2,3 or 4)";
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
								n = this.newTile(verticalTileChoice, rightTileChoice, 
										leftTileChoice, i);
								
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
	
	 
	private int readInt(String prompt) {
		int value = 0;
		boolean intRead = false;
        do {
        	@SuppressWarnings("resource")
        	Scanner line = new Scanner(System.in);
            System.out.print(prompt);
            try (Scanner scannerLine = new Scanner(line.nextLine());) {
                if (scannerLine.hasNextInt()) {
                    intRead = true;
                    value = scannerLine.nextInt();
                }
            }
        } while (!intRead);
        return value;
	}
	
	
	private Tile readTile(String prompt, int i) {
		Tile t = this.getTilesInHand().get(i - 1);
		return t;
	}
	
	public static String readString(String prompt) {
        String string = "";
        boolean stringRead = false;
        @SuppressWarnings("resource")
        Scanner line = new Scanner(System.in);
        do {
            System.out.println(prompt);
            try (Scanner scannerLine = new Scanner(line.nextLine());) {
                if (scannerLine.hasNextLine()) {
                    stringRead = true;
                    string = scannerLine.nextLine();
                }
            }
        } while (!stringRead);
        return string;
    }

}