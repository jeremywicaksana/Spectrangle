package networking;

import java.util.*;
import java.io.*;
import java.net.*;

public class SpectrangleClient {
	
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
	
	public static void main(String[] args) {
		String name = null;
		int ogPort = 0;
		int port = 0;
		boolean available = false;
		boolean validName = false;
		boolean playAgain = true;
		InetAddress address = null;
		Socket sock = null;
		
		
		
		
		//Asking for IP address
		while (!available) {
			try {
				address = InetAddress.getByName(SpectrangleClient.
						readString("Enter Server's IP address: "));
				available = true;
			} catch (UnknownHostException e) {
				System.out.println(
						"IP address seems to be offline, please reenter antoher IP address.");
			}
		}
		
		
		
		
		ogPort = SpectrangleClient.readInt("Enter the desired port number: ");
		
		
		port = ogPort;
		while (playAgain) {
			
			do {
				name = SpectrangleClient.readString(
						"Please enter your nickname (please do not use space(s)): ");
				if (!name.contains(" ")) {
					validName = true;
				}
				if (!validName) {
					System.out.println("Name '" + name + 
							"' is invalid! please reenter your name without space(s).");
				}
			} while (!validName);
			
			
			boolean computer = SpectrangleClient.readBoolean(
					"Do you want a computer to play for you? (enter y or n)", "y", "n");
			
			System.out.println("Connected to the server! Waiting for "
					+ "other players to connect to start the game...");
			
			
			try {
				sock = new Socket(address, port);
			} catch (IOException e) {
				System.out.println("Server disconnected!");
				System.exit(0);
			}
			try {
				if (computer) {
					ComputerClientPlayer clientPlayer = new ComputerClientPlayer(name, sock);
					clientPlayer.run();
				} else {
					ClientPlayer clientPlayer = new ClientPlayer(name, sock);
					clientPlayer.run();
				}
				
			} catch (IOException e) {
				System.out.println("Server disconnected!");
			}
			playAgain = SpectrangleClient.readBoolean("Play again? (Enter y or n)", "y", "n");
		}
		
	}
	
	
	
		
		
		
	
	
}
