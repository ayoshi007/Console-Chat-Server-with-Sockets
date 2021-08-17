package client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatClient {
	private boolean quit;
	
	private String name;
	
	private Socket socket;
	
	private DataOutputStream out;
	private DataInputStream in;
	private Scanner kb;
	
	public ChatClient(String serverHostname, int serverPort) throws UnknownHostException, IOException {
		this.socket = new Socket(serverHostname, serverPort);
		
		this.out = new DataOutputStream(this.socket.getOutputStream());
		this.in = new DataInputStream(this.socket.getInputStream());
		
		this.kb = new Scanner(System.in);
		this.quit = false;
		
		createUsername();
	}
	
	public void start() {
		//createUsername();
		new Thread(new IncomingMessageHandler()).start();
		new Thread(new MessageSender()).start();
		return;
	}
	
	private void createUsername() {
		try {
			// Read in prompt for entering username
			String response = "";
			
			do {
				System.out.print("Enter username: ");
				this.name = kb.nextLine();
				System.out.println();
				
				// Send username to server
				out.writeUTF(name);
				
				response = in.readUTF();
				System.out.println(response);
				System.out.println();
			} while (response.equals("Username taken"));
			
			
			// Read in chat server welcome message
			System.out.println(in.readUTF());
			
		} catch (IOException e) {
			System.out.println("Error in username creation");
			e.printStackTrace();
		}
		
	}
	// Runnable to handle messages coming in from server
	public class IncomingMessageHandler implements Runnable {
		public void run() {
			try {
				// Runs until the user inputs !quit message
				while (true) {
					// Reads in the incoming message
					String incomingMessage = in.readUTF();
					// If the message is not empty, print it to this user's console
					if (!incomingMessage.isEmpty()) {
						System.out.println(incomingMessage);
					}
					if (quit) {
						break;
					}
				}
			} catch (IOException e) {
				System.out.println("Error occurred while reading incoming message");
				e.printStackTrace();
			}
		}
	}
	// Runnable to handle user-inputted messages
	public class MessageSender implements Runnable {
		public void run() {
			try {
				Scanner kb = new Scanner(System.in);
				// Runs until the quit message is read in
				while (!quit) {
					// Reads in a message from the user
					String message = kb.nextLine();
					out.writeUTF(message);
					// If the message is the quit message
					if (message.equals("!quit")) {
						// Read in the goodbye message from the server
						quit = true;
					}
				}
				kb.close();
			} catch (IOException e) {
				System.out.println("Error occurred while sending message");
				e.printStackTrace();
			}
		}
	}
}
