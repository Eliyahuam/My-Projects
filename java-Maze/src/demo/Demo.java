package demo;
import algorithm.mazeGenerators.MyMaze3dGenerator;
import algorithms.demo.MazeAdapter;
import algorithms.search.BFS;
import algorithms.search.DFS;
import algorithms.search.RBFS;
import algorithms.search.Searcher;
import algorithms.search.Solution;
import algorithm.mazeGenerators.Maze3d;

public class Demo {
	public void run (){
		int layers=3;
		int rows=10;
		int cols=10;
		
		MyMaze3dGenerator mg= new MyMaze3dGenerator();
		Searcher searchdfs=new DFS();
		Searcher searchbfs=new BFS();
		Searcher searchbreathfs=new RBFS();
		Maze3d maze=mg.generate(layers, rows, cols);
		MazeAdapter ma=new MazeAdapter(maze);
		Solution sol;
		
		
		System.out.println(maze);
		sol=searchdfs.search(ma);
		System.out.println("DFS Algorithm:" + ((DFS) searchdfs).getCountvertex());
		System.out.println(sol);
		sol=searchbfs.search(ma);
		System.out.println("Best First Search:" + ((BFS) searchbfs).getCountvertex());
		System.out.println(sol);
		sol=searchbreathfs.search(ma);
		System.out.println("Breath First Search:" +((RBFS) searchbreathfs).getCountvertex());
		System.out.println(sol);
		
		
		
	}

}
