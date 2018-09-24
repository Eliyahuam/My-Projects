package presenter.commands;

import model.Model;
import presenter.Command;

public class LoadMaze implements Command {
	Model model;

	public LoadMaze(Model m) {
		this.model = m;
	}

	@Override
	public void doCommand(String argu) {
		String args[] = argu.split(" ");
		String filename = null;
		String newname = null;

		filename = args[0];
		newname = args[1];
		model.loadMaze(filename, newname);
	}

}
