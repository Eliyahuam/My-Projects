package algorithm.mazeGenerators;

/**
 * interface of which function the Generate Maze algorithms will need to implement
 * and function that will measure the time of maze Creation 
 *
 */
public interface Maze3dGenerator {
	Maze3d generate(int layers,int rows,int cols);
	String measureAlgorithmTime(int layers,int rows,int cols);
}
