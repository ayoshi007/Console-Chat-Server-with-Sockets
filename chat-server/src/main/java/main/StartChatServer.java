package main;

import java.io.IOException;

import server.ChatServer;

public class StartChatServer {

	public static void main(String[] args) {
		ChatServer chatServer = null;
		try {
			chatServer = new ChatServer(8000);
		} catch (IOException e) {
			System.out.println("Failed to start chat server");
			e.printStackTrace();
			System.exit(1);
		}
		
		chatServer.start();
	}
}
