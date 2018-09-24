package maze.domain;

import algorithm.mazeGenerators.Position;
import domains.State;

/**
 * MazeState Constructor get position and convert to string for the algorithm
 * @param currPlayerPosition the current Position that will convert to State Description
 *
 */
public class MazeState extends State {
	private Position currPlayerPosition;

	public Position getCurrPlayerPosition() {
		return currPlayerPosition;
	}

	public void setCurrPlayerPosition(Position currPlayerPosition) {
		this.currPlayerPosition = currPlayerPosition;
	}
	
	public MazeState(Position pos) {
		this.currPlayerPosition = pos;
		this.setDescription(pos.toString());
	}
	
	
	
}
