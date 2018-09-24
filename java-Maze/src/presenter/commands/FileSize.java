package presenter.commands;

import model.Model;
import presenter.Command;


public class FileSize implements Command {
	Model model;

	public FileSize(Model m) {
		this.model = m;
	}
	@Override
	public void doCommand(String filename) {
		model.fileSize(filename);		
	}

}
