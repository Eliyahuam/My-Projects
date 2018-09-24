package presenter.commands;

import model.Model;
import presenter.Command;


public class CrossSectionByX implements Command {
	char x;
	Model model;
	public CrossSectionByX(Model m,char x) {
		this.x = x;
		this.model=m;
	}

	@Override
	public void doCommand(String argu) {
		String args[] = argu.split(" ");
		String name = null;
		int index = Integer.parseInt(args[0]);
		name = args[1];

		model.crossSectionX(index, name, x);
	}

}
