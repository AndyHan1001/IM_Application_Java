package chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chat.util.ClientData;
import chat.util.ServerHandler;
import chat.util.ThreadPool;

public class ServerThread implements Runnable {
	private boolean isStop = false;
	private ServerSocket listener;
	private ThreadPool threadPool;
	private Map<String, ClientData> records;
	private List<ServerHandler> handlers;

	public ServerThread(int port, ThreadPool threadPool) throws IOException {
		listener = new ServerSocket(port);	//port: 9191
		records = Collections.synchronizedMap(new HashMap<String, ClientData>());
		handlers = Collections.synchronizedList(new ArrayList<ServerHandler>());		
		this.threadPool = threadPool;
	}

	@Override
	public void run() {
		while (!isStop) {
			try {
				Socket clientSocket = listener.accept();
	    		ServerHandler serverHandler = new ServerHandler(clientSocket, records);
	    		handlers.add(serverHandler);
	    		threadPool.start(serverHandler);
			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.exit(1);
			}
		}
		
		if (!listener.isClosed()) {
    		try {
				listener.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.exit(1);
			}
    	}

	}
	
	public void stopServerThread() {
		isStop = true;
	}
	
	public ThreadPool getThreadPool() {
		return threadPool;
	}

	public Map<String, ClientData> getRecords() {
		return records;
	}

	public List<ServerHandler> getHandlers() {
		return handlers;
	}

	
}
