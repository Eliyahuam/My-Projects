package algorithms.search;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

import domains.Action;
import domains.Searchable;
import domains.State;

/**
 * Finds a solution to a searchable problem
 * @param s the problem
 * @return solution
 * @param visitedStates contain the vertex`s that the algorithm visited 
 * every vertex that the algorithm visited the algorithm will develop the child`s
 * when state equal to goal position the recursion will stop and return the back trace
 * by using the function(CommonSearchBackTrace) that this class inherited
 */

public class DFS extends CommonSearcher implements Serializable {
	private int countvertex=1;
	
	public int getCountvertex() {
		return countvertex;
	}

	private HashSet<State> visitedStates = new HashSet<State>();
	private Solution solution;
	

	@Override	
	public Solution search(Searchable s) {
		
		dfs(s, s.getStartState());		
		return solution;
	}
	
	private void dfs(Searchable s, State state) {
		if (state.equals(s.getGoalState())) {
			solution = backtrace(state);
			return;
		}
		
		visitedStates.add(state);
		
		HashMap<Action,State> actions = s.getAllPossibleActions(state);
		for(State neighbor: actions.values())
		{
			if (!visitedStates.contains(neighbor)) {
				neighbor.setCameFrom(state);
				countvertex++;
				dfs(s, neighbor);					
			} 			
		}
		return;
	}

}