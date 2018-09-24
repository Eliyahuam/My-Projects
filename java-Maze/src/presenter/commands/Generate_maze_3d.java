package presenter.commands;

import model.Model;
import presenter.Command;
import view.View;


public class Generate_maze_3d implements Command {
	Model model;


	public Generate_maze_3d(Model m) {
		this.model = m;
		
	}

	@Override
	public void doCommand(String argu) {
		String args[] = argu.split(" ");
		if (args.length < 4)
			throw new IllegalArgumentException("Incorrect number of args");

		String name = args[0];
		int layers = Integer.parseInt(args[1]);
		int rows = Integer.parseInt(args[2]);
		int cols = Integer.parseInt(args[3]);

		model.generate_3d_maze(name, layers, rows, cols);
	}

}
