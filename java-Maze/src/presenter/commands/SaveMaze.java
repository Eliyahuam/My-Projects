package presenter.commands;

import model.Model;
import presenter.Command;

public class SaveMaze implements Command {
	Model model;

	public SaveMaze(Model m) {
		this.model = m;
	}
	@Override
	public void doCommand(String argu) {
		String args[] = argu.split(" ");
		String filename = null;
		String name = null;

		name = args[0];
		filename = args[1];
		model.saveMaze(name, filename);
	}

}
