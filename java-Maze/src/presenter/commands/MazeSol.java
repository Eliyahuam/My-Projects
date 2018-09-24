package presenter.commands;

import model.Model;
import presenter.Command;

public class MazeSol implements Command {
	Model model;

	public MazeSol(Model m) {
		this.model = m;
	}
	@Override
	public void doCommand(String mazename) {
		model.mazeSol(mazename);		
	}

}
