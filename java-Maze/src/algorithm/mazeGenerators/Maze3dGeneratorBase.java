package algorithm.mazeGenerators;


/**
 * abstract class that the Generators inheriting
 */
public abstract class Maze3dGeneratorBase implements Maze3dGenerator {

	
/**
 * measure the maze creation time
 * @param layers number of maze layers
 * @param rows number of maze rows
 * @param cols number of maze columns	
 */
	@Override
	public String measureAlgorithmTime(int layers, int rows, int cols) {
		long starTime=System.currentTimeMillis();
		generate(layers,rows,cols);
		long endTime=System.currentTimeMillis();
		long sumTime=endTime-starTime;
		return String.valueOf(sumTime);
	}
	
	
}
