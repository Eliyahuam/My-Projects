package algorithms.demo;

import java.io.Serializable;
import java.util.HashMap;

import algorithm.mazeGenerators.Direction;
import algorithm.mazeGenerators.Maze3d;
import algorithm.mazeGenerators.Position;
import domains.Action;
import domains.Searchable;
import domains.State;
import maze.domain.MazeAction;
import maze.domain.MazeState;

/**
 * This class Adapting between Maze goalPosition startPosition and possibleActions to the algorithm requirements (Searchable)
 */
public class MazeAdapter implements Searchable , Serializable{
	
	private Maze3d maze;
	
	public MazeAdapter(Maze3d maze) {
		this.maze = maze;
	}
/**
 * convert start position to MazeState
 * * MazeState get in his constructor position and converting to State (decreption)
 */
	@Override
	public State getStartState() {	
		MazeState StartState = new MazeState(maze.getStartPosition());
		return StartState;
	}
/**
 * convert goal position to MazeState
 * MazeState get in his constructor position and converting to State (decreption)
 */
	@Override
	public State getGoalState() {
		MazeState GoalState = new MazeState(maze.getGoalPosition());
		return GoalState;
	}
/**
 * get state and developing the next possible Actions by help from getPossibleMoves of maze3d
 */
	@Override
	public HashMap<Action, State> getAllPossibleActions(State state) {
		// TODO Auto-generated method stub
		HashMap<Action, State> map = new HashMap<Action,State>();
		MazeState ms = (MazeState) state;
		Position p = ms.getCurrPlayerPosition();//will return the position of state
		
		String[] moves = maze.getPossibleMoves(p);// return the direction from position
		// foreach diraction in the array moves i insert the action and the state 
		for (String move : moves) {
			Direction d = p.convert(move);
			if(d!=null){// Not going to happen, but just in case
			MazeAction action = new MazeAction(d);
			MazeState newstate= new MazeState(p.nextPosition(p, d));
			map.put((Action)action, newstate);
			}
			
		}
		return map;
	}

}
