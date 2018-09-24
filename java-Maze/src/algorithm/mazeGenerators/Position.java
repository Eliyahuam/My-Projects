package algorithm.mazeGenerators;

import java.io.Serializable;

/**
 * Position created for get and work on positions in the maze 
 */

public class Position implements Serializable {


	public int layers;
	public int rows;
	public int cols;
	
	public Position(int layers, int rows, int cols) {
		this.layers = layers;
		this.rows = rows;
		this.cols = cols;
	}
	

	public Position() {
		this.layers=0;
		this.rows=0;
		this.cols=0;
	}
/**
 * equals between 2 positions and return true or false respectively
 * @param obj the Position that we want to compare
 * @return true or false if the Positions equal
 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Position))
			throw new IllegalArgumentException("Object must be position");
		
		Position p = (Position)obj;
		return layers == p.layers && rows == p.rows && cols==p.cols;			
	}

/**
 * convert position to string
 */
	@Override
	public String toString() {
		return ("{" + layers + "," + rows + "," + cols + "}");
		
	}		
	/**
	 * DirectionConvert get String and converting to Direction type
	 * @param str the string that we want to convert to direction type
	 * @return directions
	 */
	public Direction convert(String str) {
        for (Direction dir : Direction.values()) {
            if (dir.toString().equals(str)) {
                return dir;
            }
        }
        return null;
    }
	
	/**
	 * NextPosition help to MazeAdapter to know the PossibleMoves for the search algorithms
	 * @param p the Position (change the position per possible directions)
	 * @param d the possible directions
	 * @return the next position
	 */
	public Position nextPosition(Position p, Direction d) {
		Position pos = new Position();
		switch (d) {
		case Up://up
			pos.layers = p.layers +1;
			pos.cols = p.cols;
			pos.rows = p.rows;
			return pos;
			
		case Down://down
			pos.layers = p.layers -1;
			pos.cols = p.cols;
			pos.rows = p.rows;
			return pos;
			
			
		case Right://right
			pos.layers = p.layers;
			pos.cols = p.cols;
			pos.rows = p.rows+1;
			return pos;
			
			
		case Left://left
			pos.layers = p.layers;
			pos.cols = p.cols;
			pos.rows = p.rows-1;
			return pos;
			
		case Forward: //forward
			pos.layers = p.layers;
			pos.cols = p.cols+1;
			pos.rows = p.rows;
			return pos;
			
		case Backward: //backward
			
			pos.layers = p.layers;
			pos.cols = p.cols-1;
			pos.rows = p.rows;
			return pos;
			
			
		 
	}
		return null; // never get this line
  }
	
}
