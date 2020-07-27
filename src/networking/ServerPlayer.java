package networking;

import java.util.*;
import java.io.*;
import java.net.*;
import game.*;

public class ServerPlayer extends Player implements Runnable {	
	private BufferedReader in;
	private BufferedWriter out;
	private ServerGame serverGame;
	private Socket socket;
	private Board b;	

	public ServerPlayer(Socket socket) throws IOException {
		this.socket = socket;
		this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		this.out = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
	}	

	public void run() {
    	String message;
		try {
			while ((message = in.readLine()) != null && !this.socket.isClosed()) {
				if (serverGame != null && serverGame.gameOver(b)) {
					break;
				} else if (message.split(" ")[0].equals("nickname")) {
					if (serverGame != null) {
						sendMessage("403: Game has started!");
					} else {
						super.name = message.split(" ")[1];
					}
				} else if (message.split(" ")[0].equals("features")) {
					sendMessage("features");
				} else if (message.split(" ")[0].equals("placeTile")) {
					int i = Integer.parseInt(message.split(" ")[1]);
					boolean check = false;
					if (!check) {
						sendMessage("404: You do not have this tile!");
						requestMove();
					} else {
						List<ServerPlayer> list = new ArrayList<>();
						for (ServerPlayer player : serverGame.getPlayers()) {
							list.add(player);
						}
						if (!serverGame.getBoard().isField(i)) {
							sendMessage("404: not a valid field");
							requestMove();
						} else if (!serverGame.getBoard().isEmptyField(i)) {
							sendMessage("404: field is occupied!");
							requestMove();
						} else if (serverGame.getPlayer().noPossibleMove(b)) {
							sendMessage("404: You cannot place the tile there!");
							requestMove();
						} 
					
					} 
				} else if (message.split(" ")[0].equals("switchTile")) {
					String tileString = "" + message.split(" ")[1].charAt(0) +
							message.split(" ")[1].charAt(1) + message.split(" ")[1].charAt(2);
					int tileValue = Integer.parseInt("" + message.split(" ")[1].charAt(3));
					Tile selectedTile = null;
					boolean check = false;
					List<ServerPlayer> list = new ArrayList<>();
					for (ServerPlayer player : (ServerPlayer[]) serverGame.getPlayers()) {
						list.add(player);
					}
					for (Tile tile : tileInHand) {
						for (int i = 0; i < 3 && !check; i++) {
							if (tile.toString().equals("" + tileString.charAt(i % 3) +
									tileString.charAt((i + 1) % 3) +
									tileString.charAt((i + 2) % 3))) {
								selectedTile = tile;
								tileInHand.remove(selectedTile);
								Tile newTile = serverGame.getPlayer().getTilesInHand().get(3);
								if (tileValue == tile.getValue()) {
									check = true;
									serverGame.sendMessageToAllPlayers("switchedTile " +
											list.indexOf(this) + " " + tileString + tileValue +
										" " + newTile.toString() + newTile.getValue());
									serverGame.sendMessageToAllPlayers(
											"skippedMove " + list.indexOf(this));
								}
							} else if (tile.toString().equals("" + tileString.charAt(
									(i + 1) % 3) + tileString.charAt(i % 3) +
									tileString.charAt((i + 2) % 3))) {
								selectedTile = tile;
								tileInHand.remove(selectedTile);
								Tile newTile = serverGame.getPlayer().getTilesInHand().get(3);
								if (tileValue == tile.getValue()) {
									check = true;
									serverGame.sendMessageToAllPlayers("switchedTile " +
											list.indexOf(this) + " " + tileString + tileValue +
											" " + newTile.toString() + newTile.getValue());
									serverGame.sendMessageToAllPlayers("skippedMove " +
											list.indexOf(this));
								}
							}
						}
					}
					if (!check) {
						sendMessage("404 You don't have that tile!");
						requestMove();
					}	
				} else if (message.equals("leave")) {
					serverGame.sendMessageToAllPlayers("end");
					serverGame.gameOver(b);
				} else if (message.equals("skipMove")) {
			
					List<ServerPlayer> list = new ArrayList<>();
					for (ServerPlayer player : serverGame.getPlayers()) {
						list.add(player);
					}
					serverGame.sendMessageToAllPlayers("skippedMove " + list.indexOf(this));
				
				} 
			}
		} catch (IOException e) {
			this.serverGame.gameOver(b);
		}
	}
	
	
	public void requestMove() {
		sendMessage("requestMove");
	}
	
	
	
	public void sendMessage(String message) {
		try {
			out.write(message);
			out.newLine();
			out.flush();
		} catch (IOException e) {
			this.serverGame.gameOver(b);
		}
	}
	
	
	
	public void setServerGame(ServerGame game) {
		this.serverGame = game;
	}
	
	
	
	
	public BufferedReader getReader() {
		return this.in;
	}
	
	
	
	public BufferedWriter getWriter() {
		return this.out;
	}




	@Override
	public void determineMove(Board b) throws IOException {		
	}

}
