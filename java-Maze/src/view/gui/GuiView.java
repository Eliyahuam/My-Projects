package view.gui;

import java.util.Observable;
import java.util.Observer;

import algorithm.mazeGenerators.Maze3d;
import algorithms.search.Solution;
import view.View;

public class GuiView extends Observable implements View,Observer {
	MazeWindow gui;

	public GuiView () {
		gui=new MazeWindow();
		gui.addObserver(this);
	}

	@Override
	public void start() {
				

				gui.run();

	}

	@Override
	public void toPrint(String toprint) {
		if (toprint.contains("Has Been Created")) {
			gui.popUpmsg(toprint);
		}
		else if(toprint.contains("Has Been Removed")) {
			gui.popUpmsg(toprint);
		}
			
		

	}

	@Override
	public void update(Observable o, Object arg) {

		 if (o== gui) {
			setChanged();
			notifyObservers(arg);
			return;
		}
		
	}
	@Override
	public void setMazes(String[] mazes) {
		
		gui.setMazes(mazes);
		return;
		
	}
	@Override
	public void setMazeToLoad(Maze3d maze) {
		gui.setMazeToLoad(maze);
		
	}

	@Override
	public void setSolution(Solution sol) {
		gui.setMazeSolution(sol);		
	}




}
