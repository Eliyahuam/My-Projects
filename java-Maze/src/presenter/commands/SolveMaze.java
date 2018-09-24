package presenter.commands;

import model.Model;
import presenter.Command;
import view.View;


public class SolveMaze implements Command {
	Model model;
	View view;

	public SolveMaze(Model m,View v) {
		this.model = m;
		this.view=v;
	}
	@Override
	public void doCommand(String argu) {
		String args[] = argu.split(" ");
		if (args.length <2) {
			view.toPrint("Please Enter Command as Required");
			return;
			
		}
		String mazename = null;
		String algoname = null;

		mazename = args[0];
		algoname = args[1];
		model.solveMaze(mazename, algoname);
	}

}
