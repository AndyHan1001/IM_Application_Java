package chat.server;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import chat.util.Checker;
import chat.util.ClientData;
import chat.util.NameSearcher;
import chat.util.PortChecker;
import chat.util.Searcher;
import chat.util.ServerHandler;
import chat.util.ThreadPool;

public class ServerDriver {

    /**
     * Runs the server.
     */
    public static void main(String[] args) throws IOException {
    	boolean quitLoop = false;
    	Checker checker = new PortChecker();
    	int port = checker.check(args[0]);
    	ThreadPool threadPool = ThreadPool.getInstance();
		Scanner menu = new Scanner(System.in);
		Scanner scanName = new Scanner(System.in);
		String name = null;
		Searcher nameSearcher = new NameSearcher();
		int menuIndex = 0;
		Set<String> onlineNames = null;
		Map<String, ClientData> records;
    	
    	ServerThread serverThread = new ServerThread(port, threadPool);
		Thread thread = new Thread(serverThread);
		thread.start();
		
		while(!quitLoop){
    		System.out.println("MENU:");
    		System.out.println("1. Broadcast");
    		System.out.println("2. Send message to a client");
    		System.out.println("3. Print message of a client");
    		System.out.println("4. Quit");
    		
    		menuIndex = menu.nextInt();
    		
    		switch (menuIndex) {
    		case 1:
    			records = serverThread.getRecords();
    			Set<String> names = records.keySet();
    			for (String clientName : names) {
    				if (!records.get(clientName).isCloseRequest()) {
    					List<ServerHandler> handlers = serverThread.getHandlers();
        				for (ServerHandler sh : handlers) {
        					if (sh.getName().equals(clientName)) {
        						sh.sendToClient("Hello From Server");
        						break;
        					}
        				}
    				}
    			}
    			break;
    		case 2:
    			System.out.println("Online clients:");
    			onlineNames = nameSearcher.searchAndPrint(serverThread.getRecords());
    			System.out.println("To whom?");
    			name = scanName.nextLine();
    			if (onlineNames.contains(name)) {
    				List<ServerHandler> handlers = serverThread.getHandlers();
    				for (ServerHandler sh : handlers) {
    					if (sh.getName().equals(name)) {
    						sh.sendToClient("Hello From Server");
    						break;
    					}
    				}
    			} else {
    				System.out.println("Invalid name!");
    			}
    			break;
    		case 3:
    			System.out.println("Online clients:");
    			records = serverThread.getRecords();
    			onlineNames = nameSearcher.searchAndPrint(records);
    			System.out.println("Whose message to print?");
    			name = scanName.nextLine();
    			if (onlineNames.contains(name)) {
    				System.out.println(records.get(name));
    			} else {
    				System.out.println("Invalid name!");
    			}
    			break;
    		case 4:
    			quitLoop = true;
    			break;
    		default:
    			break;
    		}
    	}
    	
		if (scanName != null) {
			scanName.close();
		}
		
		if (menu != null) {
			menu.close();
		}
		
		
		// quit the server thread
		serverThread.stopServerThread();
		thread.interrupt();
		
    	System.exit(0);
    }
   
}
