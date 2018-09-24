package algorithms.search;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;
import domains.Action;
import domains.Searchable;
import domains.State;

/* answers to the questions
 * i could to create breath first search and to inheriting for best first search but after i will have to create priority queue in separately
 * i preferred to create the best first search and only to create function that override on the priority and to set cost is 1
 */
/**
 *  Finds a solution to a searchable problem by Algorithm Best First Search
 *	@param countvertex count the number of vertex developed
 */
public class BFS extends CommonSearcher implements Serializable {
	private int countvertex=1;
	
	public int getCountvertex() {
		return countvertex;
	}


	@Override
	public Solution search(Searchable s) {
		openList.add(s.getStartState());
		
		while (!openList.isEmpty()) {
			State state = openList.poll();
			closedList.add(state);
			
			if (state.equals(s.getGoalState())) 
				return backtrace(state);
			
			HashMap<Action, State> actions = s.getAllPossibleActions(state);
			for (Entry<Action, State> entry: actions.entrySet()) {
				Action action = entry.getKey();
				State successor = entry.getValue();
				
				if (!openList.contains(successor) && !closedList.contains(successor)) {
					successor.setCameFrom(state);
					successor.setCost(state.getCost() + costFunction(action));
					openList.add(successor);
					countvertex++;
					
				}
				else if (state.getCost() + costFunction(action) < successor.getCost()) {
					
					successor.setCameFrom(state);
					successor.setCost(state.getCost() + costFunction(action));
					
					// update priority in queue by removing and adding the state
					openList.remove(successor);
					openList.add(successor);	
					countvertex++;
				}
			}
		}
		return null; // won't reach this line never
	}
	/**
	 * costFunction create to allow to Best First Search and Breath First Search to use almost the same code
	 * @param s the action that we want to return the cost
	 * @return the cost of the developing ,created for the Breath First Search
	 */

	protected double costFunction(Action s) {
		
		return s.getCost();
	} 
}
