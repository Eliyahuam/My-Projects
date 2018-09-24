package algorithm.mazeGenerators;

import java.io.Serializable;
import java.util.Random;
/**
 * this algorithm create maze path by lottery start position and when arrived to bound then set the last position as Goal position
 * the algorithm create few paths and just the first one configuring path from start position to goal position
 */

public class SimpleMaze3dGenerator extends Maze3dGeneratorBase implements Serializable {


	private Random rand=new Random();
	
	public Maze3d maze;
	

    
  /**
   * setPathMaze create the path by lottery the next position 
   * @param p position
   * @return the last position of the path 
   */
    private Position setPathMaze (Position p) {
	   Random sixaxis=new Random();
	   int r;

	   while( true ) {
	   r= sixaxis.nextInt(3);
	   
	   switch (r) {
	   case 0: {
		   r=sixaxis.nextInt(2);
		   if (r==1) { 
			   if(p.layers+2 > maze.getLayers()-1) {return p;}
			   if(maze.getValue(p.layers+1, p.rows, p.cols) == 0 && maze.getValue(p.layers+2, p.rows, p.cols) == 0){continue;}
		   		p.layers=p.layers+1;
		   		maze.setFree(p.layers, p.rows, p.cols);
		   		p.layers=p.layers+1; 
		   		maze.setFree(p.layers, p.rows, p.cols);
			   }
		   else { if (p.layers-2<0 ) { return p; }
		   		if(maze.getValue(p.layers-1, p.rows, p.cols) == 0 && maze.getValue(p.layers-2, p.rows, p.cols) == 0){ continue; }
		   		p.layers=p.layers-1;
		   		maze.setFree(p.layers, p.rows, p.cols);
		   		p.layers=p.layers-1; 
		   		maze.setFree(p.layers, p.rows, p.cols);
		   		}
		 
	   }
	   case 1: {
		   r=sixaxis.nextInt(2);
		   if (r==1) { 
			   if(p.rows+2 > maze.getRows()-1) {return p;}
			   if(maze.getValue(p.layers, p.rows+1, p.cols) == 0 && maze.getValue(p.layers, p.rows+2, p.cols) == 0){continue;}
		   		p.rows=p.rows+1;
		   		maze.setFree(p.layers, p.rows, p.cols);
		   		p.rows=p.rows+1; 
		   		maze.setFree(p.layers, p.rows, p.cols);
			   }
		   else { 
			   	if (p.rows-2<0) { return p; }
		   		if(maze.getValue(p.layers, p.rows-1, p.cols) == 0 && maze.getValue(p.layers, p.rows-2, p.cols) == 0){continue;}
		   		p.rows=p.rows-1;
		   		maze.setFree(p.layers, p.rows, p.cols);
		   		p.rows=p.rows-1; 
		   		maze.setFree(p.layers, p.rows, p.cols);
		   		}

	   }
	   case 2: {
		   r=sixaxis.nextInt(2);
		   if (r==1) { 
			   if(p.cols+2 > maze.getCols()-1 ) { return p; }
			   if(maze.getValue(p.layers, p.rows, p.cols+1) == 0 && maze.getValue(p.layers, p.rows, p.cols+2) == 0){continue;}
		   		p.cols=p.cols+1;
		   		maze.setFree(p.layers, p.rows, p.cols);
		   		p.cols=p.cols+1; 
		   		maze.setFree(p.layers, p.rows, p.cols);
			   }
		   else { 
			   	if (p.cols-2<0 ) { return p; } 
		   		if(maze.getValue(p.layers, p.rows, p.cols-1) == 0 && maze.getValue(p.layers, p.rows, p.cols-2) == 0){continue;}
		   		p.cols=p.cols-1;
		   		maze.setFree(p.layers, p.rows, p.cols);
		   		p.cols=p.cols-1; 
		   		maze.setFree(p.layers, p.rows, p.cols);
		   		}
	   }
	   
    }
	}
	   
    }
    /**
     * randomPosition lottery the StartPosition
     * @return start position
     */
	private Position randomPosition() {
	int layers= rand.nextInt(maze.getLayers());
	while ( layers % 2 != 0 )
		layers = rand.nextInt(maze.getLayers());
		
	int rows = rand.nextInt(maze.getRows());
	while (rows % 2 != 0)
		rows=rand.nextInt(maze.getRows());
	
	int cols = rand.nextInt(maze.getCols());
	while (cols % 2 != 0)
		cols = rand.nextInt(maze.getCols());
	
	
	return new Position(layers,rows,cols);
	
	}
	
	 /**
	  * The generate function get row columns and layers and create maze with path`s goal position and start position
	  */
	public Maze3d generate(int layers, int rows, int cols) {
		maze=new Maze3d(layers,rows,cols);
		int count=0;
		maze.setWalls(); 
		Position pstart= randomPosition();  //start position
		
		do {
			pstart= randomPosition();
			maze.copyStartPosition(pstart.layers, pstart.rows, pstart.cols);
			maze.setFree(pstart.layers, pstart.rows, pstart.cols);
			pstart=setPathMaze(pstart);
			maze.copyGoalPosition(pstart.layers, pstart.rows, pstart.cols);
			count++;
		} while (maze.startP.equals(maze.goalP));
		for (int i=0;i<maze.getRows()*maze.getLayers()- count;i++){
			pstart=randomPosition();
			maze.setFree(pstart.layers, pstart.rows, pstart.cols);
			setPathMaze(pstart);
		}
		


		return maze;
	}


	


}
