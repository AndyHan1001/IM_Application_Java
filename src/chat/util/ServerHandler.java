/**
 * 
 */
package chat.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

/**
 * @author lingjiemeng
 *
 */
public class ServerHandler implements Runnable{
	private Socket clientSocket;
//	private BufferedReader input;
	private ObjectInputStream input;
	private PrintWriter output;
	private Map<String, ClientData> records;
	private String name;
	private volatile String serverMessage;
	private volatile boolean stopThread;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	public void sendToClient(String serverMessage) {
		this.serverMessage = serverMessage;
	}


	/**
	 * 
	 */
	public ServerHandler(Socket clientSocket, Map<String, ClientData> records) {
		init(clientSocket, records);
	}


	/**
	 * @param clientSocket
	 */
	private void init(Socket clientSocket, Map<String, ClientData> records) {
		this.records = records;
		this.clientSocket = clientSocket;
		try {
//			this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
			input = new ObjectInputStream(this.clientSocket.getInputStream());
			output = new PrintWriter(this.clientSocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}


	@Override
	public void run() {
		Thread writeThread = new ServerHandlerWriteThread();
		writeThread.start();
		
		while (true) {
			
			try {
				ClientData clientData = (ClientData) input.readObject();
				if (clientData != null) {
					name = clientData.getName();
					//System.out.println(name);
					String message = clientData.getMessage();
					boolean quitRequest = clientData.isCloseRequest();
					// quit
					ClientData storedClientData = null;
					if (quitRequest) {
						if (name != null) {
							storedClientData = records.get(name);
							storedClientData.setCloseRequest(true);
						}
						break;
					}
					if (records.containsKey(name)) {
						storedClientData = records.get(name);
						String oldMessage = storedClientData.getMessage();
						String newMessage = oldMessage
								+ System.getProperty("line.separator")
								+ message;
						storedClientData.setMessage(newMessage);
						storedClientData.setName(name);
						storedClientData.setCloseRequest(quitRequest);
					} else {
						records.put(name, clientData);
					}
				}
				
				//System.out.println(records.get(name));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println(e.getMessage());
				System.exit(1);
			} catch (ClassNotFoundException e) {
				System.err.println(e.getMessage());
				System.exit(1);
			}
			
		}
		
		try {
			
			stopThread = true;   // stop ServerHandlerWriteThread
			
			if (output != null) {
				output.close();
			}
			
			if (input != null) {
				input.close();
			}
			
			if (!clientSocket.isClosed()){
				clientSocket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
			System.exit(1);
		}
		
	}
	
	private class ServerHandlerWriteThread extends Thread {

		@Override
		public void run() {
			while (!stopThread) {
				// send message to the specific client
				if (serverMessage != null) {
					output.println(serverMessage);
					output.flush();
					serverMessage = null;
				}
			}
		}
		
	}

}
