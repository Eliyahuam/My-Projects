package presenter.commands;

import model.Model;
import presenter.Command;

public class MazeSize implements Command {
	Model model;

	public MazeSize(Model m) {
		this.model = m;
	}

	@Override
	public void doCommand(String name) {
		model.mazeSize(name);
	}

}
