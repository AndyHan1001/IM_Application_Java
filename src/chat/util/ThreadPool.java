/**
 * 
 */
package chat.util;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lingjiemeng
 *
 */
public class ThreadPool {
	private volatile static ThreadPool thrdPlInst;
//	private int poolSize;
	
	/**
	 * @return the poolSize
	 */
/*	public int getPoolSize() {
		return poolSize;
	}
*/
	private List<WorkerThread> idleThreads = 
				Collections.synchronizedList(new LinkedList<WorkerThread>());
	
	private ThreadPool(){
	}

	public static ThreadPool getInstance(){
		if(thrdPlInst == null){
			synchronized(ThreadPool.class){
				if(thrdPlInst == null){
					thrdPlInst = new ThreadPool();
				}
			}
		}
		return thrdPlInst;
	}
	
	/**
	 * @return 
	 * 
	 */
	public synchronized WorkerThread borrowThread() {
		// TODO Auto-generated constructor stub
		if (idleThreads.isEmpty()) {
			return null;
		}
		return idleThreads.remove(0);
	}
	
	public synchronized void returnThread(WorkerThread idleThread) {
		// TODO Auto-generated method stub
		idleThreads.add(idleThread);
	}
	
	public void start(ServerHandler serverHandler){
		if (!idleThreads.isEmpty()) {
			WorkerThread borrowedThread = this.borrowThread();
			borrowedThread.setTask(serverHandler);
		} else {
			WorkerThread newThread = new WorkerThread(serverHandler);
			newThread.start();
		}
	}
	
	private class WorkerThread extends Thread {
		private ServerHandler serverHandler;
		// private boolean isIdle;
		
		public WorkerThread(ServerHandler serverHandler){
			this.serverHandler = serverHandler;
		}

		/**
		 * @return the isIdle
		 */
//		public boolean isIdle() {
//			return isIdle;
//		}
		
		
		@Override
		public void run() {
			while(true){
				// isIdle = false;
				if (serverHandler!=null) {
					serverHandler.run();	//core service
				}
				// isIdle = true;
				thrdPlInst.returnThread(this);
			
				synchronized(this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						System.err.println(e.getMessage());
						System.exit(1);
					}
				}
			}
		}
		
		public synchronized void setTask(ServerHandler serverHandler) {
			this.serverHandler = serverHandler;
			this.notifyAll();
		}
	}

}
