package presenter.commands;

import model.Model;
import presenter.Command;


public class Exit implements Command {
	Model model;

	public Exit(Model m) {
		this.model = m;
	}
	@Override
	public void doCommand(String argu) {
		model.exit();		
	}

}
