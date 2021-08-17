package main;

import java.io.IOException;

import server.ChatServer;

public class StartChatServer {

	public static void main(String[] args) {
		int port = 8000;
		
		if (args.length == 1) {
			port = Integer.parseInt(args[0]);
		}
		ChatServer chatServer = null;
		try {
			chatServer = new ChatServer(port);
		} catch (IOException e) {
			System.out.println("Failed to start chat server");
			e.printStackTrace();
			System.exit(1);
		}
		
		chatServer.start();
	}
}
