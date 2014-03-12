/**
 * 
 */
package chat.util;

import java.util.Map;
import java.util.Set;

/**
 * @author lingjiemeng
 *
 */
public interface Searcher {

	public Set<String> searchAndPrint(Map<String, ClientData> records);
	
}
