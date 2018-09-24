package domains;

import java.io.Serializable;

/**
 * This class created for the algorithms them know to work with Action and State
 * @param decreption the state
 * @param cost the cost until this state
 * @param cameFrom saving the father 
 */
public class State implements Comparable<State>, Serializable {
	private String description;
	private double cost;
	private State cameFrom;


	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public State getCameFrom() {
		return cameFrom;
	}
	public void setCameFrom(State cameFrom) {
		this.cameFrom = cameFrom;
	}
	/**
	 * the Priority Queue used this compare to know which priority to relate
	 */
	@Override
	public int compareTo(State s) {
		return (int)(this.cost - s.cost);
	}
	@Override
	public boolean equals(Object arg0) {
		State state = (State)arg0;
		return description.equals(state.description);
	}
	@Override
	public int hashCode() {
		return description.hashCode();
		
	}
	
	
}
