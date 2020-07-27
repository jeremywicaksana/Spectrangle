package game;

public class Spectrangle {
	public static void main(String[] args) {
	
    	Player s0 = new HumanPlayer("a");
		Player s1 = new HumanPlayer("b");
		
		Game s = new Game(s0, s1, null, null);
    	s.start();
	}
}
