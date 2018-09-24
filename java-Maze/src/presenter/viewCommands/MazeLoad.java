package presenter.viewCommands;

import model.Model;
import presenter.Command;
import view.View;

public class MazeLoad implements Command {

	View view;
	Model model;
	
	public MazeLoad (View v,Model m) {
		this.view=v;
		this.model=m;
	}
	@Override
	public void doCommand(String name) {
		view.setMazeToLoad(model.mazeToLoad(name));
		
	}

}
