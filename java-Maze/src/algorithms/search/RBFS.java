package algorithms.search;

import java.io.Serializable;

import domains.Action;
import domains.Searchable;
import domains.State;

/**
 *   This function finds a solution to a searchable problem by Algorithm Breath First Search
 * 	 This class inherited Best First Search algorithm and by overriding costFunction
 * 	 and returning every time cost = 1
 * 	 we creating equal priority and as a result the algorithm ignoring the priority
 *
 */
public class RBFS extends BFS implements Serializable {

	@Override
	protected double costFunction(Action s) {
		return 1;
	}
	

	
	
	

}
