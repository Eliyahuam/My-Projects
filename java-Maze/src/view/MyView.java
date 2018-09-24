package view;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import algorithm.mazeGenerators.Maze3d;
import algorithms.search.Solution;
import view.gui.BasicWindow;
import view.gui.MazeWindow;



public class MyView extends Observable implements View, Observer {
	BufferedReader in;
	PrintWriter out;
	private Cli cli;
	
	
	
	public MyView(BufferedReader in,PrintWriter out) {
		this.in=in;
		this.out=out;
		cli= new Cli(in,out);
		cli.addObserver(this);
		
		
	}

	@Override
	public void start() {
				
				cli.start();

	}

	@Override
	public void toPrint(String toprint) {

		out.write(toprint);
		out.flush();
		
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o==cli) {
			setChanged();
			notifyObservers(arg);
			
		}
		
	}
	@Override
	public void setMazes(String[] mazes) {
		
		
	}
	@Override
	public void setMazeToLoad(Maze3d maze) {
		
	}
	@Override
	public void setSolution(Solution sol) {
		// TODO Auto-generated method stub
		
	}



}
