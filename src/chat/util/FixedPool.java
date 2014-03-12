/**
 * 
 */
package chat.util;

/**
 * @author lingjiemeng
 *
 */
public abstract class FixedPool implements Pool {
	
	protected int poolSize;
	
	public FixedPool(int poolSize){
		this.poolSize = poolSize;
	}
	public abstract void borrowThread();
	public abstract void returnThread();

}
