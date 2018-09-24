package model;

import java.util.HashMap;

import algorithm.mazeGenerators.Maze3d;
import algorithms.search.Solution;

/**
 * 
 * interface that class MyModel will implement
 *
 */
public interface Model {
	public void dir(String path);
	public void generate_3d_maze(String name,int layers,int rows,int cols);
	public void display(String name);
	public void saveMaze(String name,String filename);
	public void loadMaze (String filename,String newname);
	public void mazeSize (String name);
	public void fileSize (String filename);
	public void solveMaze (String mazename,String algoname);
	public void mazeSol (String mazename);
	public void crossSectionX(int x,String name,char by);
	public void exit();
	String getMessage();
	String[] getMazes();
	Maze3d mazeToLoad(String name);
	Solution sol2Gui(String mazeName);
	public void deleteMazeAndSolution(String mazeName);
}
