package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Tile {
	protected static final char WHITE = 'W';
	protected static final char RED = 'R';
	protected static final char BLUE = 'B';
	protected static final char GREEN = 'G';
	protected static final char YELLOW = 'Y';
	protected static final char PURPLE = 'P';
	protected char left;
	protected char right;
	protected char vertical;
	protected int value;
	protected int score;
	protected Board b;
	
	public List<Character> chars = new ArrayList<>(Collections.emptyList());
	
	
	public Tile(char left, char right, char vertical, int value) {
		chars.clear();
		this.value = value;
		this.left = left;
		this.right = right;
		this.vertical = vertical;
		this.chars.add(this.left);
		this.chars.add(this.right);
		this.chars.add(this.vertical);
	}
	
	
	@Override
	public String toString() {
		String l = Character.toString(this.left);
		String r = Character.toString(this.right);
		String v = Character.toString(this.vertical);
		String s = Integer.toString(this.value);
		return l + r + v + " " + s;
	}
	
	public int getValue() {
		return this.value;
	}
	
}
