package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;

import domains.State;

/**
 *  @class that created for the BackTrace that will return the Solution
 *  override toString to print the Solution
 */
public class Solution implements Serializable {
	private ArrayList<State> states;

	public ArrayList<State> getStates() {
		return states;
	}

	public void setStates(ArrayList<State> states) {
		this.states = states;
	}
/**
 * convert solution to string
 */
	@Override
	public String toString() {
		StringBuilder str =new StringBuilder();
		
		for (State state : states) {
		str.append(state.getDescription());
		}
		return str.toString();
	}

	
	
}
