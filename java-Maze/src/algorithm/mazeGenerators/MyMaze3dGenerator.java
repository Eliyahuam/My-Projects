package algorithm.mazeGenerators;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;



/**
 * This Algorithms taken from wikipedia in form of pseudo code
 * create Maze path (DFS)
 */
public class MyMaze3dGenerator extends Maze3dGeneratorBase implements Serializable{

	private Random rand=new Random();
	
	public Maze3d maze;
	
/**
 * random position lottery the Start Position and DFS algorithm start to develop the maze path from the start position
 * @return Position 
 */
	private Position randomPosition() {
		int layers= rand.nextInt(maze.getLayers());
			while ( layers % 2 != 0)
				layers = rand.nextInt(maze.getLayers());
		
		int rows = rand.nextInt(maze.getRows());
			while (rows % 2 != 0)
				rows=rand.nextInt(maze.getRows());
	
		int cols = rand.nextInt(maze.getCols());
			while (cols % 2 != 0)
				cols = rand.nextInt(maze.getCols());
	
	
		return new Position(layers,rows,cols);
	
    	}
	
	@Override
	public Maze3d generate(int layers, int rows, int cols) {
			maze = new Maze3d(layers,rows, cols);
			maze.setWalls();
			Position pstart= randomPosition(); 
			//set start position
			maze.copyStartPosition(pstart.layers, pstart.rows, pstart.cols);
			
			maze.setFree(pstart.layers,pstart.rows,pstart.cols);
			DFS(pstart);
			// set goal position 
			randomGoalposition();
		return maze;
	}
	 
	/**
	 * another function of random position that lottery position on the path that the DFS created
	 */
	private  void randomGoalposition() {
		
		int layers= rand.nextInt(maze.getLayers());
		while(layers%2 != 0 ){
			
			layers= rand.nextInt(maze.getLayers());
		}
		int rows = rand.nextInt(maze.getRows());
		int cols = rand.nextInt(maze.getCols());
		Position p = new Position(layers,rows,cols);
		
		while(maze.getValue(layers, rows, cols)==1 || p.equals(maze.startP)){
			layers= rand.nextInt(maze.getLayers());
			rows = rand.nextInt(maze.getRows());
			cols = rand.nextInt(maze.getCols());
			p = new Position(layers,rows,cols);
		}
		maze.copyGoalPosition(p.layers, p.rows, p.cols);
	}
	
	
	// the recursive dfs algo
	/**
	 * DFS- working in recursive method every position developed until the bounds getPossibleDirection give the development possible states
	 * @param p the position to develop the next move
	 * */
	private void DFS (Position p){
		Position newPos;
		Direction d;
		
		ArrayList<Direction> dirs = getPossibleDirections(p);
		
		if(dirs.size() <=0){
			return;
		}
		int dir = rand.nextInt(dirs.size());
		d = dirs.get(dir);

		
		for (int i = 0; i < dirs.size(); i++) {
			
		switch (d) {
		case Up://up
			
			maze.setFree(p.layers + 1, p.rows, p.cols);
			maze.setFree(p.layers + 2, p.rows,p.cols);
			newPos = new Position(p.layers + 2, p.rows, p.cols);
			DFS(newPos);
			break;
			
		case Down://down
			
			maze.setFree(p.layers - 1, p.rows, p.cols);
			maze.setFree(p.layers - 2, p.rows,p.cols);
			 newPos = new Position(p.layers - 2, p.rows, p.cols);
			DFS(newPos);
			break;
			
		case Right://right
			
			maze.setFree(p.layers , p.rows+ 1, p.cols);
			maze.setFree(p.layers , p.rows+ 2,p.cols);
			 newPos = new Position(p.layers , p.rows+ 2, p.cols);
			DFS(newPos);
			break;
			
			
		case Left://left
			
			maze.setFree(p.layers , p.rows- 1, p.cols);
			maze.setFree(p.layers , p.rows- 2,p.cols);
			newPos = new Position(p.layers , p.rows- 2, p.cols);
			DFS(newPos);
			break;
			
		case Forward: //forward
			
			maze.setFree(p.layers , p.rows, p.cols+ 1);
			maze.setFree(p.layers , p.rows,p.cols+ 2);
			 newPos = new Position(p.layers , p.rows, p.cols+2);
			DFS(newPos);
			break;
			
		case Backward: //backward
			
			maze.setFree(p.layers , p.rows, p.cols - 1);
			maze.setFree(p.layers , p.rows,p.cols - 2);
			newPos = new Position(p.layers, p.rows, p.cols-2);
			DFS(newPos);
			break;
			
			
		}
		}
		
	}
	
/**
 * function that return all the possible directions  from specific position 
 * @param currPos
 * @return possible directions as ArrayList
 */

private ArrayList<Direction> getPossibleDirections(Position currPos) {
	ArrayList<Direction> directions = new ArrayList<Direction>();
	
	if (currPos.layers + 2 < maze.getLayers() && 
			maze.getValue(currPos.layers +2, currPos.rows, currPos.cols) == 1) {
		directions.add(Direction.Up);
	}
	
	if (currPos.layers - 2 >= 0 && 
			maze.getValue(currPos.layers - 2,currPos.rows,currPos.cols) == 1) {
		directions.add(Direction.Down);
	}
	
	if (currPos.rows + 2 < maze.getRows() && 
			maze.getValue(currPos.layers, currPos.rows + 2,currPos.cols) == 1) {
		directions.add(Direction.Right);
	}
	
	if (currPos.rows - 2 >= 0 && 
			maze.getValue(currPos.layers, currPos.rows - 2, currPos.cols) == 1) {
		directions.add(Direction.Left);
	}
	if (currPos.cols + 2 < maze.getCols() && 
			maze.getValue(currPos.layers, currPos.rows,currPos.cols+ 2) == 1) {
		directions.add(Direction.Forward);
	}
	
	if (currPos.cols - 2 >=0 && 
			maze.getValue(currPos.layers, currPos.rows , currPos.cols- 2) == 1) {
		directions.add(Direction.Backward);
	}
	return directions;
}

}//end class