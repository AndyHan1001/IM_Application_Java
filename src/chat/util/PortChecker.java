/**
 * 
 */
package chat.util;

/**
 * @author lingjiemeng
 *
 */
public class PortChecker implements Checker {

	@Override
	public int check(String str) {
    	String portStr = str;
		int port = 0;
    	
    	//check if port number is an int
    	try {
			port = Integer.parseInt(portStr);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
			System.exit(1);
		}
    	
    	try {
    		if (port<1024 || port>65535) {
        		throw new IllegalArgumentException("Port number is NOT from 1024 to 65535.");
        	}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
			System.exit(1);
		}
    	return port;
	}
	
}
