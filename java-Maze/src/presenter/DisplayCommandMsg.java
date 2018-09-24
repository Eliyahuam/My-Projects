package presenter;

import model.Model;
import view.View;

public class DisplayCommandMsg implements Command {
	private Model model;
	private View view;
	
	public DisplayCommandMsg (Model m,View v) {
		this.model=m;
		this.view=v;
	}

	@Override
	public void doCommand(String argu) {
		String msg=model.getMessage();
		view.toPrint(msg);

	}

}
