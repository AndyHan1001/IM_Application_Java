/**
 * 
 */
package chat.util;

/**
 * @author lingjiemeng
 *
 */
public interface Pool {
	public void borrowThread();
	public void returnThread();
}
