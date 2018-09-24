package algorithm.mazeGenerators;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * The constructor get layers,rows,and columns and create maze per requested
 * @param layers the quantity of maze layers
 * @param rows the number of rows
 * @param cols the number of columns
 * @param startP type of Position containing the Start Position
 * @param goalP type of Position containing the Goal Position
 */
public class Maze3d implements Serializable {


	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Maze3d))
			throw new IllegalArgumentException("Object must be Maze3d");
		
		Maze3d m= (Maze3d) obj;
		if(this.layers != m.layers ) {return false;}
		if (this.rows != m.rows) {return false;}
		if(this.cols!=m.cols)  {return false;}
		if (!this.startP.equals(m.startP)) {return false;}
		if(!this.goalP.equals(m.goalP)) {return false;}
		
		for (int i=0;i<layers;i++) {
			for (int j=0;j<rows;j++) {
				for (int k=0;k<cols;k++){
					if (this.maze[i][j][k]!=m.maze[i][j][k]) {return false;}
				}
			}

		}

		return true;
	}

	private int layers;
	private int rows;
	private int cols;
	public Position startP;
	public Position goalP;
	
	public static final int WALL=1;
	public static final int FREE=0;
	
	private int[][][] maze;
	
	public Maze3d(int layers, int rows, int cols) {
		this.layers = layers;
		this.rows = rows;
		this.cols = cols;
		this.maze = new int[layers][rows][cols];

	}
	
	public byte[] toByteArray() {
		ArrayList<Byte> arrb=new ArrayList<>();
		// dim of array
		arrb.add ((byte)layers );
		arrb.add ((byte)rows );
		arrb.add ((byte)cols );
		
		//start position
		arrb.add((byte) startP.layers);
		arrb.add((byte) startP.rows);
		arrb.add((byte) startP.cols);
		
		//goal position
		arrb.add((byte) goalP.layers);
		arrb.add((byte) goalP.rows);
		arrb.add((byte) goalP.cols);
		
		// the maze
		for (int i = 0; i < layers; i++)
		{
			for (int j = 0; j < rows; j++) {
				for (int k = 0; k < cols; k++ ){
					arrb.add((byte)maze[i][j][k]);
				}
			}			
		}
		// Copy the array list to array of bytes
		byte[] bytesarray = new byte[arrb.size()];
		for (int i = 0; i < arrb.size(); i++) {
			bytesarray[i] = arrb.get(i);
		}
		return bytesarray;
	}
	
	// constructor 
	public Maze3d (byte[] b) {
		
		layers=b[0];
		rows=b[1];
		cols=b[2];
	     
		Position ps = new Position();
		ps.layers=b[3];
		ps.rows = b[4];
		ps.cols = b[5];
		
		startP=ps;
		
		Position pg = new Position();
		pg.layers=b[6];
		pg.rows = b[7];
		pg.cols = b[8];
		goalP = pg;
		
		
		int temp=9;
		this.maze = new int[layers][rows][cols];
		
		for (int i=0;i<layers;i++) {
				for(int j=0;j<rows;j++)
				{
				for(int k=0;k<cols;k++) {
					maze[i][j][k]=b[temp];
					temp++;
				}
			}
		}
	}
		
	
	public void copyStartPosition(int layers,int rows,int cols){
		this.startP = new Position(layers, rows, cols);
	
	}
	
	public void copyGoalPosition(int layers,int rows,int cols){
		this.goalP = new Position(layers, rows, cols);
	}
	public int getLayers() {
		return layers;
	}

	public void setLayers(int layers) {
		this.layers = layers;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}
	public int getValue(int layers,int rows,int cols) {
		return maze[layers][rows][cols];
	}
	
	
	// return the value of start position of player
    public Position getStartPosition(){
    	return startP;
    }

	// return the value of goal position of player
    public Position getGoalPosition(){
    	return goalP;
    }
    
	public void setFree (int layers, int rows,int cols){
		maze[layers][rows][cols]= FREE;
	}

	public void setWall (int layers, int rows,int cols){
		maze[layers][rows][cols]= WALL;
	}
/**
 * 
 */
	@Override
	public String toString() {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < layers; i++) {
				
				for (int j = 0; j < rows; j++){
					for (int k=0;k<cols;k++){
					sb.append(maze[i][j][k] + " ");
				}
					sb.append("\n");
				}
				
				sb.append("\n");
			}
			return sb.toString();
		}

/**
 * This function get position and return array of which moves the next position could be
 * @param p the position to check the next possible move
 * @return String Array that contain the possible next moves
 */
	
	public String[] getPossibleMoves(Position p) {
		
		ArrayList<String> possible = new ArrayList<String>();
		
		if (p.layers + 1 < getLayers() && 
				getValue(p.layers +1, p.rows, p.cols) == FREE) {
			possible.add("Up");
		}
		
		if (p.layers - 1 >= 0 && 
				getValue(p.layers - 1,p.rows,p.cols) == FREE) {
			possible.add("Down");
		}
		
		if (p.rows + 1 < getRows() && 
				getValue(p.layers, p.rows + 1,p.cols) ==FREE) {
			possible.add("Right");
		}
		
		if (p.rows - 1 >= 0 && 
				getValue(p.layers, p.rows - 1, p.cols) == FREE) {
			possible.add("Left");
		}
		if (p.cols + 1 < getCols() && 
				getValue(p.layers, p.rows,p.cols+ 1) ==FREE) {
			possible.add("Forward");
		}
		
		if (p.cols - 1 >=0 && 
				getValue(p.layers, p.rows , p.cols- 1) == FREE) {
			possible.add("Backward");
		}
		
		String[] arrpos = new String[possible.size()];
		for(int i= 0; i< possible.size(); i++){
			arrpos[i]= possible.get(i);
		}
		
		return arrpos;
	}

/**
 * @function getCrossBySection* will create maze2d
 * @param x to get cross section per x
 * @return Maze2d by X,Y or Z
 */
	public int[][] getCrossSectionByX(int x) {
		
		if (x<0||x>getLayers())
			throw new IndexOutOfBoundsException("Error out of bound");
		
		int[][] m2d=new int[getRows()][getCols()];
		for (int i=0;i< getRows(); i++)
		{
			for (int j=0;j<getCols();j++)
			{
				m2d[i][j]=getValue(x,j,i);
			}
		}
	return m2d;
	}

	/**
	 * @functions getCrossBySection* will create maze2d
	 * @param y to get cross section per y
	 * @return Maze2d by X,Y or Z
	 */
	public int[][] getCrossSectionByY(int y) {
		
		if (y<0||y>getRows())
			throw new IndexOutOfBoundsException("Error out of bound");
		
		int[][] m2d=new int[getLayers()][getCols()];
		
		for (int i=0;i< getLayers(); i++)
		{
			for (int j=0;j<getCols();j++)
			{
				m2d[i][j]=getValue(i,y,j);
			}
		}
		
		
	return m2d;
	}
	/**
	 * @function getCrossBySection* will create maze2d
	 * @param z to get cross section per z
	 * @return Maze2d by X,Y or Z
	 */

	public int[][] getCrossSectionByZ(int z) {
		if (z<0||z>getCols())
			throw new IndexOutOfBoundsException("Error out of bound");
		
		int[][] m2d=new int[getLayers()][getRows()];
		
		
		for (int i=0;i< getLayers(); i++)
		{
			for (int j=0;j<getRows();j++)
			{
				m2d[i][j]=getValue(i,j,z);
			}
		}
		
	
		
		
	
		return m2d;
	}

	/**
	 * @functions setWalls created to initialize the maze with 1 in every cell
	 */
   public void setWalls() {

    	for(int i=0;i<getLayers();i++){
    		for (int j=0;j<getRows();j++){
    			for (int k=0;k<getCols();k++){
    				setWall(i,j,k);
    			}
    		}
    	}
		
	}
	

	
}
