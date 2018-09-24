package view.gui;

import java.util.Observable;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * 
 * @author Eliyahu
 * This Class abstract and the base to Maze Window
 * ensures that the shell will still open during playing
 * @param display controlling the command priority queue
 * @param shell configure the shell size layout ,etc
 *
 */
public abstract class BasicWindow extends Observable implements Runnable {
	protected Display display;
	protected Shell shell;

	public BasicWindow() {  //constructor will initialize display and shell 
		display = new Display();
		shell = new Shell(display);
	}

	protected abstract void initWidget();  //abstract function that Maze window need to implement
	@Override
	public void run() {  //function to run the gui
		initWidget();
		shell.open();

		while (!shell.isDisposed()) { //loop to management the event queue while the gui isnt closed

			if (display.readAndDispatch()) {  //if the event queue is empty go to sleep until new event occurs
				display.sleep();
			}
		}
		display.dispose(); //close
	}
}
