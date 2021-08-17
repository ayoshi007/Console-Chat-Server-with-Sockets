package main;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

import client.ChatClient;

public class StartChatClient {

	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);
		
		System.out.print("Enter hostname of server: ");
		String hostname = kb.nextLine();
		System.out.print("Enter port number of server: ");
		int port = kb.nextInt();
		System.out.println();
		
		ChatClient chat = null;
		try {
			chat = new ChatClient(hostname, port);
		} catch (UnknownHostException e) {
			System.out.println("Couldn't find host");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Failed to connect to chat server");
			e.printStackTrace();
			System.exit(1);
		}
		
		chat.start();
	}
}
