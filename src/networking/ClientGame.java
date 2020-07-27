package networking;

import java.util.*;
import java.io.*;
import java.net.*;
import game.*;

public class ClientGame implements Runnable  {
	private String myName;
	private ClientPlayer me;
	private Board board;
	private Player[] players;
	private String[] playerNames;
	private int nrOfPlayers;
	private boolean gameStarted;
	private Game game;
	
	
	public ClientGame(ClientPlayer player, String[] playerNames) {
		this.me = player;
		this.myName = this.me.getName();
		this.board = new Board();
		this.playerNames = playerNames;
		this.nrOfPlayers = playerNames.length;
		List<String> nameList = new ArrayList<>();
		for (int i = 0; i < this.nrOfPlayers; i++) {
			nameList.add(playerNames[i]);
		}
		int myOrder = nameList.indexOf(this.myName);
		this.players = new Player[this.nrOfPlayers];
		for (int i = 0; i < this.nrOfPlayers; i++) {
			if (i == myOrder) {
				players[i] = this.me;
			} else {
				players[i] = new HumanPlayer(playerNames[i]);
			}
		}
	}
	
	
	
	public void run() {
		play();
	}
	
	public void play() {
		this.gameStarted = true;
		while (!game.gameOver(board)) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}
		}
		game.theWinner();
	}
	
	public void update() {
		System.out.println(SpectrangleBoardPrinter.getBoardString(
				Board.values, Board.vertical, Board.left, Board.right));
	}
	
	public Board getBoard() {
		return this.board;
	}
	
	public Player[] getPlayers() {
		return this.players;
	}
	
	

}

