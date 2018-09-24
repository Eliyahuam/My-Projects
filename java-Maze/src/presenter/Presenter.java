package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import model.Model;
import presenter.commands.CrossSectionByX;
import presenter.commands.Dir;
import presenter.commands.Display;
import presenter.commands.Exit;
import presenter.commands.FileSize;
import presenter.commands.Generate_maze_3d;
import presenter.commands.LoadMaze;
import presenter.commands.MazeSize;
import presenter.commands.MazeSol;
import presenter.commands.SaveMaze;
import presenter.commands.SolveMaze;
import presenter.viewCommands.MazeGetSol;
import presenter.viewCommands.MazeLoad;
import presenter.viewCommands.MazeToRemove;
import presenter.viewCommands.getMazes;
import view.View;

public class Presenter implements Observer {
	private Model model;
	private View view;

	private HashMap<String, Command> vcommand = new HashMap<String, Command>();
	private HashMap<String, Command> mcommand = new HashMap<String, Command>();

	public Presenter(Model m, View v) {
		this.model = m;
		this.view = v;
		createCommands();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o == view) {
			String line = (String) arg;
			if (line.equals("exit")){
				Command exitcommand = vcommand.get("exit");
				exitcommand.doCommand(null);
				return;
				
			}
			StringBuilder argu = new StringBuilder();
			StringBuilder command = new StringBuilder();


			String[] split = line.split(" ");

			for (int i = 0; i < split.length; i++) {
				if (split[i].startsWith("<")) {
					argu.append(split[i]);
				}
				if (!split[i].startsWith("<")) {
					command.append(split[i]);
				}
			}
			for (int i = 0; i < argu.length(); i++) {
				if (argu.charAt(i) == '<') {
					argu.replace(i, i + 1, "");
				}
				if (argu.charAt(i) == '>') {
					argu.replace(i, i + 1, " ");
				}
			}
			argu.delete(argu.length() - 1, argu.length());
			if (vcommand.containsKey(command.toString())) {
				Command com = vcommand.get(command.toString());
				com.doCommand(argu.toString());
				
			}
			else { view.toPrint("Command not found! \n"); }
			} else if (o == model) {
				String commandName = (String)arg;
				Command modelcommand = mcommand.get(commandName);
				modelcommand.doCommand(null);
			}
		

		}
	

	private void createCommands() {
		vcommand.put("dir", new Dir(model)); // Constructor here we need to add
												// all the commands
		vcommand.put("generate_maze_3d", new Generate_maze_3d(model));
		vcommand.put("display", new Display(model));
		vcommand.put("save_maze", new SaveMaze(model));
		vcommand.put("load_maze", new LoadMaze(model));
		vcommand.put("maze_size", new MazeSize(model));
		vcommand.put("file_size", new FileSize(model));
		vcommand.put("solve_maze", new SolveMaze(model,view));
		vcommand.put("display_solution", new MazeSol(model));
		vcommand.put("display_cross_section_by_x", new CrossSectionByX(model,'x'));
		vcommand.put("display_cross_section_by_y", new CrossSectionByX(model,'y'));
		vcommand.put("display_cross_section_by_z", new CrossSectionByX(model,'z'));
		vcommand.put("exit", new Exit(model));
		mcommand.put("display_message", new DisplayCommandMsg(model, view));
		vcommand.put("maze_to_load", new MazeLoad(view,model));
		vcommand.put("maze_to_get_solve",new MazeGetSol(view,model));
		vcommand.put("maze_to_remove", new MazeToRemove(model));
		vcommand.put("get_mazes", new getMazes(view,model));
	}

}
