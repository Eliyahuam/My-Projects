package view;

import java.util.HashMap;

import algorithm.mazeGenerators.Maze3d;
import algorithms.search.Solution;

public interface View {
	public void start();
	public void toPrint(String toprint);
	public void setMazes(String[] mazes);
	public void setMazeToLoad(Maze3d maze);
	public void setSolution(Solution sol);

}
