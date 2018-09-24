package presenter.viewCommands;

import model.Model;
import presenter.Command;
import view.View;

public class getMazes implements Command {
	View view;
	Model model;
	
	public getMazes(View view,Model model) {
		this.view=view;
		this.model=model;
	}

	@Override
	public void doCommand(String argu) {
		
			view.setMazes(model.getMazes());
			
		
		
	}
	


}
