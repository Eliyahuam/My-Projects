package domains;

import java.util.HashMap;

/**
 * the Maze Adapter need to implement this interface
 * for the algorithms, the algorithm get Searchable and know to work with it 
 * 
 *
 */

public interface Searchable {
	State getStartState();
	State getGoalState();
	HashMap<Action, State> getAllPossibleActions(State state);
}
