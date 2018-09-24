package presenter.commands;

import model.Model;
import presenter.Command;


public class Dir implements Command{
	Model model;

	public Dir(Model m) {
		this.model = m;
	}

	@Override
	public void doCommand(String path) {
		model.dir(path);
	}

}
