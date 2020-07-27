package networking;

import java.util.*;
import java.io.*;
import java.net.*;

public class SpectrangleServer {
	public static int readInt(String prompt) {
        int value = 0;
        boolean intRead = false;
        @SuppressWarnings("resource")
        Scanner line = new Scanner(System.in);
        do {
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
	
	@SuppressWarnings("resource")
	public static boolean readBoolean(String prompt, String yes, String no) {
        String answer;
        Scanner in;
        do {
            System.out.print(prompt);
            in = new Scanner(System.in);
            answer = in.hasNextLine() ? in.nextLine() : null;
        } while (answer == null || (!answer.equals(yes) && !answer.equals(no)));
        return answer.equals(yes);
    }
	
	public static String readString(String prompt) {
        String string = "";
        boolean stringRead = false;
        @SuppressWarnings("resource")
        Scanner line = new Scanner(System.in);
        do {
            System.out.print(prompt);
            try (Scanner scannerLine = new Scanner(line.nextLine());) {
                if (scannerLine.hasNextLine()) {
                    stringRead = true;
                    string = scannerLine.nextLine();
                }
            }
        } while (!stringRead);
        return string;
    }
	
	public static void main(String[] args) {
		//Variables
		int port = 0;
		ServerSocket socket = null;
		boolean portValid = false;
		int nrOfPlayers = 0;
		boolean playerNumber = false;
		
		
		
		
		while (!portValid) {
			port = SpectrangleServer.readInt("Enter port number: ");
			try {
				socket = new ServerSocket(port);
				portValid = true;
			} catch (IOException e) {
				System.out.println("Port number already "
						+ "in use! Please enter a new port number.");
			}
		}
		while (!playerNumber) {
			nrOfPlayers = SpectrangleServer.readInt("How many players do"
					+ " you want to play in each game in this server?");
			if (nrOfPlayers >= 1 && nrOfPlayers <= 4) {
				playerNumber = true;
			}
			if (!playerNumber) {
				System.out.println("ERROR: Players have to be at least 1 and at most 4!");
			}
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

		System.out.println("Waiting for clients to connect...");
		
		
	
		while (true) {
			ServerPlayer[] players = new ServerPlayer[nrOfPlayers];
			for (int i = 0; i < players.length; i++) {
				try {
					System.out.println("Waiting for " +
							(nrOfPlayers - i) + " more player(s) to connect...");
					Socket socksock = socket.accept();
					ServerPlayer serverPlayer = new ServerPlayer(socksock);
					Thread serverPlayerThread = new Thread(serverPlayer);
					serverPlayerThread.start();
					players[i] = serverPlayer;
					serverPlayer.sendMessage("features");
				} catch (IOException e) {
					break;
				}
			}
			try {
				Thread.sleep(500);
				ServerGame serverGame = new ServerGame(players);
				Thread serverGameThread = new Thread(serverGame);
				serverGameThread.start();
				String message = "A game has started with players";
				for (int i = 0; i < serverGame.getPlayers().length; i++) {
					if (i == serverGame.getPlayers().length - 1) {
						message = message + " " + serverGame.getPlayers()[i].getName() + ".";
					} else {
						message = message + " " + serverGame.getPlayers()[i].getName() + ",";
					}
				}
				System.out.println(message);
			} catch (NullPointerException e) {
			} catch (InterruptedException e) {
			}
		}
	}
	

}



