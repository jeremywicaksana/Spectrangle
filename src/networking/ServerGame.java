package networking;

import java.util.*;
import game.*;
import java.io.*;

public class ServerGame extends Game implements Runnable {
	
	private ServerPlayer[] players;
	private Board b;
	
	
	public ServerGame(ServerPlayer[] serverPlayers) {
		super(serverPlayers[0], serverPlayers[1], serverPlayers[2], serverPlayers[3]);
		for (ServerPlayer player : players) {
			player.setServerGame(this);
		}
	}
	
	
	
	
	public void run() {
		start();
	}
	
	
	
	public void start() {
		while (!super.gameOver(b)) {
			boolean doorgaan = true;
			while (doorgaan) {
				reset();
				play();
				doorgaan = readBoolean("\n> Play another time? (y/n)?", "y", "n");
			}
				
		}
		this.sendMessageToAllPlayers("end");
	}
	
	private boolean readBoolean(String prompt, String yes, String no) {
        String answer;
        Scanner in;
        do {
            System.out.print(prompt);
            in = new Scanner(System.in);
            answer = in.hasNextLine() ? in.nextLine() : null;
            in.close();
        } while (answer == null || (!answer.equals(yes) && !answer.equals(no)));
        return answer.equals(yes);
    }
	
	public void sendMessageToAllPlayers(String message) {
		try {
			for (ServerPlayer player : players) {
				player.getWriter().write(message);
				player.getWriter().newLine();
				player.getWriter().flush();
			}
		} catch (IOException e) {
			this.gameOver(b);
		}
	}
	
	
	
	
	public ServerPlayer[] getPlayers() {
		return this.players;
	}
	
	public Board getBoard() {
		return this.board;
	}
}
