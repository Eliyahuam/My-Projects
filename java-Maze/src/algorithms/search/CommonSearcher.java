package algorithms.search;
import java.util.PriorityQueue;

import algorithms.search.Searcher;
import domains.State;
/**
 * base to the all algorithms that need Priority Queue 
 * @param openList - Priority queue
 * @param closeList - Priority queue
 */

public abstract class CommonSearcher extends CommonSearcherBackTrace implements Searcher {
	protected PriorityQueue<State> openList;
	protected PriorityQueue<State> closedList;
	
	
	public CommonSearcher() {
		openList = new PriorityQueue<State>();
		closedList = new PriorityQueue<State>();
	}
}
