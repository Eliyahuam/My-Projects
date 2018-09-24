package domains;
/**
  * This class created for the algorithms them know to work with Action and State
  * @param descreption - descreption the action
  * @param cost - the cost of the action
  */

public class Action {
	private String description;
	private double cost;
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
	public Action() {}
	public Action(String description, double cost) {		
		this.description = description;
		this.cost = cost;
	}
	
	@Override
	public String toString() {
		return description;
	}	
}
