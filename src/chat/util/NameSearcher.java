/**
 * 
 */
package chat.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author lingjiemeng
 *
 */
public class NameSearcher implements Searcher{

	@Override
	public Set<String> searchAndPrint(Map<String, ClientData> map) {
		Set<String> names = map.keySet();
		Set<String> onlineNames = Collections.synchronizedSet(new HashSet<String>());
		
		for (String name : names) {
			if (!map.get(name).isCloseRequest()) {
				System.out.println(name);
				onlineNames.add(name);
			}
		}
		
		return onlineNames;
		
	}

}
