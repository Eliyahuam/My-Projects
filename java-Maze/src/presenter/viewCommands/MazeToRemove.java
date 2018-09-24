package presenter.viewCommands;

import model.Model;
import presenter.Command;
import view.View;

public class MazeToRemove implements Command {
	Model model;

	

	public MazeToRemove(Model model) {
		super();
		this.model = model;

	}


	@Override
	public void doCommand(String mazeName) {
		model.deleteMazeAndSolution(mazeName);
		
	}

}
