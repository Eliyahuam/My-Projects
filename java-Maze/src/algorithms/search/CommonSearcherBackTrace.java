package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;

import domains.State;

/**
 * created to return the Solution (Back Trace) of Beast Breath Depth First Search 
 * the function get pointer to the goalPosition and restore the way from goal to start position
 * @param s get the Camefrom until s will be null (camefrom pointing to null)
 * the ArrayList saving the steps to the solution 
 */
public abstract class CommonSearcherBackTrace implements Searcher,Serializable {
	protected Solution backtrace(State state) {
		State s = state;
		ArrayList<State> states = new ArrayList<State>();
		while (s != null) {
			states.add(0, s);
			s=s.getCameFrom();
		}
		Solution solution = new Solution();
		solution.setStates(states);
		return solution;
	}

}
