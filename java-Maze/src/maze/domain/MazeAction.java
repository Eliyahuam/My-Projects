package maze.domain;

import algorithm.mazeGenerators.Direction;
import domains.Action;

/**
 * here we configuring the action cost
 * and every action will convert Driection to String and call the constructor of Action with the dricetion that converted to String and the cost
 *
 */
public class MazeAction extends Action {
		
	public static final double mazeMovementCost = 1;
	//private Direction move;
	
	public MazeAction(Direction move) {
		
		super(move.toString(), mazeMovementCost);
	}
	
	
}
