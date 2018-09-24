package presenter.commands;

import model.Model;
import presenter.Command;


public class Display implements Command {
	Model model;

	public Display(Model m) {
		this.model = m;
	}

	@Override
	public void doCommand(String name) {
		model.display(name);
	}

}
