package game;

import java.util.*;


public class Bag {
	
	public static final Tile BBB = new Tile('B', 'B', 'B', 6);
	public static final Tile RRR = new Tile('R', 'R', 'R', 6);
	public static final Tile GGG = new Tile('G', 'G', 'G', 6);
	public static final Tile PPP = new Tile('P', 'P', 'P', 6);
	public static final Tile YYY = new Tile('Y', 'Y', 'Y', 6);
	public static final Tile RRY = new Tile('R', 'R', 'Y', 5);
	public static final Tile RRP = new Tile('R', 'R', 'P', 5);
	public static final Tile BBR = new Tile('B', 'B', 'R', 5);
	public static final Tile BBP = new Tile('B', 'B', 'P', 5);
	public static final Tile GGR = new Tile('G', 'G', 'R', 5);
	public static final Tile GGB = new Tile('G', 'G', 'B', 5);
	public static final Tile YYG = new Tile('Y', 'Y', 'G', 5);
	public static final Tile YYB = new Tile('Y', 'Y', 'B', 5);
	public static final Tile PPY = new Tile('P', 'P', 'Y', 5);
	public static final Tile PPG = new Tile('P', 'P', 'G', 5);
	public static final Tile RRB = new Tile('R', 'R', 'B', 4);
	public static final Tile RRG = new Tile('R', 'R', 'G', 4);
	public static final Tile BBG = new Tile('B', 'B', 'G', 4);
	public static final Tile BBY = new Tile('B', 'B', 'Y', 4);
	public static final Tile GGY = new Tile('G', 'G', 'Y', 4);
	public static final Tile GGP = new Tile('G', 'G', 'P', 4);
	public static final Tile YYR = new Tile('Y', 'Y', 'R', 4);
	public static final Tile YYP = new Tile('Y', 'Y', 'P', 4);
	public static final Tile PPR = new Tile('P', 'P', 'R', 4);
	public static final Tile PPB = new Tile('P', 'P', 'B', 4);
	public static final Tile YBP = new Tile('Y', 'B', 'P', 3);
	public static final Tile RGY = new Tile('R', 'G', 'Y', 3);
	public static final Tile BGP = new Tile('B', 'G', 'P', 3);
	public static final Tile GRB = new Tile('G', 'R', 'B', 3);
	public static final Tile BRP = new Tile('B', 'R', 'P', 2);
	public static final Tile YPR = new Tile('Y', 'P', 'R', 2);
	public static final Tile YPG = new Tile('Y', 'P', 'G', 2);
	public static final Tile GRP = new Tile('G', 'R', 'P', 1);
	public static final Tile BYG = new Tile('B', 'Y', 'G', 1);
	public static final Tile RYB = new Tile('R', 'Y', 'B', 1);
	public static final Tile WWW = new Tile('W', 'W', 'W', 1);


	public static List<Tile> tileInBag = new ArrayList<>(Arrays.asList(
			RRR, BBB, GGG, YYY, PPP, RRY, RRP, BBR, BBP,
			GGR, GGB, YYG, YYB, PPY, PPG, RRB, RRG, BBG, 
			BBY, GGY, GGP, YYR, YYP, PPR, PPB, YBP, RGY,
			BGP, GRB, BRP, YPR, YPG, GRP, BYG, RYB, WWW));
		
	public static List<Tile> getTileInBag() {
		return tileInBag;
	}
	
	public static int getNumTilesInBag() {
		return tileInBag.size();
	}

}

