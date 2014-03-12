package chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import chat.util.Checker;
import chat.util.ClientData;
import chat.util.PortChecker;

public class ClientDriver {
	private static String name = null;
    private static String message = null;

    public static void main(String[] args) throws IOException {

    	String hostName = args[0];	//localhost
    	
    	Checker checker = new PortChecker();
    	int port = checker.check(args[1]);
    	
        Socket socket = new Socket(hostName, port);
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
        
        Scanner menu = new Scanner(System.in);
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.println("MENU:");
        System.out.println("1. Give me a name");
        System.out.println("2. Quit");       
        
        boolean flag = true;
        int menuIndex;
        while (flag) {
        	menuIndex = menu.nextInt();
			switch (menuIndex) {
			case 1:
				name = in.readLine();
				ClientData clientData = new ClientData();
				clientData.setName(name);
				/* here */
				output.writeObject(clientData);
            	output.flush();
				flag = false;
				break;
			case 2:
				quit(socket, input, output, menu, in);
			}
		}
        
		while (true) {
			
	        System.out.println("MENU:");
			System.out.println("1. Send message to server");
			System.out.println("2. Print message from server");
			System.out.println("3. Quit");
			
			menuIndex = menu.nextInt();
	        switch(menuIndex){
	        	case 1:	
	            	message = in.readLine();
	            	ClientData clientData = new ClientData();
	            	clientData.setName(name);
	            	clientData.setMessage(message);
	            	output.writeObject(clientData);
	            	output.flush();
	            	break;
	        	case 2: 
	        		String serverMsg = input.readLine();
	        		System.out.println(serverMsg);
	        		break;
	        	case 3:	
					quit(socket, input, output, menu, in);
	        }
		}
    }

    

	/**
	 * @param socket
	 * @param input
	 * @param output
	 * @param menu
	 * @param in
	 * @throws IOException
	 */
	private static void quit(Socket socket, BufferedReader input,
			ObjectOutputStream output, Scanner menu, BufferedReader in)
			throws IOException {
		
		ClientData clientData = new ClientData();
		clientData.setName(name);
		clientData.setCloseRequest(true);
		output.writeObject(clientData);
		output.flush();
		
		if (in != null) {
			in.close();
		}
		
		if (menu != null) {
			menu.close();
		}
		
		if (output != null) {
			output.close();
		}
		
		if (input != null) {
			input.close();
		}
		
		if (!socket.isClosed()) {
			socket.close();
		}
		System.exit(0);
	}
	
}
