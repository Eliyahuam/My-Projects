package presenter.viewCommands;

import model.Model;
import presenter.Command;
import view.View;

public class MazeGetSol implements Command {
	View view;
	Model model;
	
	
	public MazeGetSol(View view, Model model) {
		this.view = view;
		this.model = model;
	}


	@Override
	public void doCommand(String mazeName) {
		view.setSolution(model.sol2Gui(mazeName));
		
	}

}
