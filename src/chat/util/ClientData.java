/**
 * 
 */
package chat.util;

import java.io.Serializable;

/**
 * @author lingjiemeng
 *
 */
public class ClientData implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String message = "";
	private boolean closeRequest;

	/**
	 * @return the closeRequest
	 */
	public boolean isCloseRequest() {
		return closeRequest;
	}

	/**
	 * @param closeRequest the closeRequest to set
	 */
	public void setCloseRequest(boolean closeRequest) {
		this.closeRequest = closeRequest;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return name + ": " + message;
	}


}
