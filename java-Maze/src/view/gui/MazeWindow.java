package view.gui;



import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import algorithm.mazeGenerators.Maze3d;
import algorithm.mazeGenerators.Position;
import algorithms.search.Solution;
import domains.State;
import presenter.Properties;

/**
 * 
 * @author Eliyahu - this class inherited from BasicWindow
 * @param mazeDisplay - data memeber from class MazeDisplay and allowed to use mazeDisplay features
 * @param themaze - data member from class Maze3d 
 * @param themazes - contain in string array the list of the mazes were generated
 * @param mazeSolution - contain the solution of the maze
 * @param mazeLoaded -contain the name of the maze were loaded
 * @param states- array list of states until to goal position
 *
 */
public class MazeWindow extends BasicWindow {
	private MazeDisplay mazeDisplay;
	private Maze3d themaze;
	private String[] themazes;
	private Solution mazeSolution;
	String mazeLoaded;
	private ArrayList<State> states = new ArrayList<State>();
	Properties prop;
	
	public void setProperties () {
		prop=new Properties(15, 15, 6, 1, "bfs");
		prop.writeToXML();
		prop.readFromXml();

		
	}

	public void setMazes(String[] themazes) {
		this.themazes = themazes;

	}

	public void setMazeToLoad(Maze3d maze) {
		this.themaze = maze;

	}

	public void setMazeSolution(Solution sol) {
		this.mazeSolution = sol;
	}
	public void popUpmsg (String msg) { //msgs to the user by pop-up
		PopUpWindow pop=new PopUpWindow(shell);
		pop.popUpopen(msg);
		
	}

	@Override
	protected void initWidget() {

		shell.setSize(1000, 800);
		shell.setLayout(new GridLayout(2, false));
		setProperties();
		Composite toolBar = new Composite(shell, SWT.NONE);
		toolBar.setLayout(new GridLayout(1, true));
		

		// *********Generate maze **************
		Composite mazeGenerateComposite = new Composite(toolBar, SWT.NONE);
		mazeGenerateComposite.setLayout(new GridLayout(1, false));
		mazeGenerateComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		Button generateMazebtn = new Button(mazeGenerateComposite, SWT.PUSH);
		generateMazebtn.setText("Generate The Maze");
		generateMazebtn.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		Composite textGenComposite = new Composite(mazeGenerateComposite, SWT.NONE);
		textGenComposite.setLayout(new GridLayout(4, false));
		textGenComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));

		Composite floorComposite= new Composite(textGenComposite,SWT.NONE);
		floorComposite.setLayout(new GridLayout(1,true));
		Label floorLabel=new Label(floorComposite,SWT.NULL);
		floorLabel.setText("floor");
		final Text textBoxfloors = new Text(floorComposite, SWT.BORDER);
		textBoxfloors.setText(Integer.toString(prop.getNumOfFloors()));
		
		Composite rowComposite=new Composite(textGenComposite,SWT.NULL);
		rowComposite.setLayout(new GridLayout(1,false));
		Label rowLabel=new Label(rowComposite,SWT.NULL);
		rowLabel.setText("rows");
		final Text textBoxrows = new Text(rowComposite, SWT.BORDER);
		textBoxrows.setText(Integer.toString(prop.getNumOfRows()));
		
		Composite colsComposite=new Composite(textGenComposite,SWT.NULL);
		colsComposite.setLayout(new GridLayout(1,false));
		Label colLabel=new Label(colsComposite,SWT.NULL);
		colLabel.setText("  cols");
		final Text textBoxcols = new Text(colsComposite, SWT.BORDER);
		textBoxcols.setText(Integer.toString(prop.getNumOfCols()));
		
		Composite nameComposite=new Composite(textGenComposite,SWT.NULL);
		nameComposite.setLayout(new GridLayout(1,false));
		Label nameLabel=new Label(nameComposite,SWT.NULL);
		nameLabel.setText("  Name");
		final Text textBoxname = new Text(nameComposite, SWT.BORDER);
		textBoxname.setMessage("Name");
		
		setChanged();
		notifyObservers("get_mazes <34>");

		// *********Load Maze + implement (listener)**************
		
		Composite mazesLoad = new Composite(toolBar, SWT.NONE);
		mazesLoad.setLayout(new GridLayout(3, false));
		mazesLoad.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		final Combo mazesChoose = new Combo(mazesLoad, SWT.DROP_DOWN | SWT.READ_ONLY);
		mazesChoose.setItems(themazes);
		mazesChoose.select(0);
		
		Button loadMazebtn = new Button(mazesLoad, SWT.PUSH);
		loadMazebtn.setText("Load Maze");

		Button mazeToDelete = new Button(mazesLoad, SWT.PUSH);
		mazeToDelete.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		mazeToDelete.setText("Delete");
		
		mazeToDelete.addSelectionListener(new SelectionListener() { //delete button implement
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String mazenameSelected;
				int selnum;
				selnum = mazesChoose.getSelectionIndex();
				mazenameSelected = mazesChoose.getItem(selnum);
				setChanged();
				notifyObservers("maze_to_remove " + "<" + mazenameSelected + ">");
				setChanged();
				notifyObservers("get_mazes <34>");
				mazesChoose.setItems(themazes);
				mazesChoose.select(0);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		loadMazebtn.addSelectionListener(new SelectionListener() { //load button implement

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String mazenameSelected;
				
				int selnum;
				selnum = mazesChoose.getSelectionIndex();
				mazenameSelected = mazesChoose.getItem(selnum);
				setChanged();
				notifyObservers("maze_to_load " + "<" + mazenameSelected + ">");
				mazeLoaded=mazenameSelected;
				mazeDisplay = new Maze2dDisplay(shell, SWT.BORDER);
				int x = themaze.getStartPosition().layers;
				int mazeData[][] = themaze.getCrossSectionByX(x);
				mazeDisplay.setMazeData(mazeData, themaze);
				mazeDisplay.setCharacterPosition(themaze.getStartPosition());
				mazeDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 4));
				display.readAndDispatch();
				
				
				Control[] oldControl = shell.getChildren();
				shell.layout();
				if (oldControl.length > 2) { //close the previous maze that loaded

					oldControl[1].dispose();
					shell.layout();

				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		// *************Generate maze button implement*************
		
		generateMazebtn.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setChanged();
				if (textBoxcols.getText().isEmpty() || textBoxfloors.getText().isEmpty() || textBoxrows.getText().isEmpty() || textBoxname.getText().isEmpty()) {
					
					popUpmsg("please fill the fields right"); return;

				}
				else {
				notifyObservers("generate_maze_3d <"+ textBoxname.getText() + "> <" + textBoxfloors.getText() + "> <" +textBoxrows.getText() + "> <" +textBoxcols.getText()+ ">");
				setChanged();
				notifyObservers("get_mazes <34>");
				mazesChoose.setItems(themazes);
				mazesChoose.select(0);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		// *******Algorithm to Solve Maze Button *******

		Composite algoChoose = new Composite(toolBar, SWT.NONE);
		algoChoose.setLayout(new GridLayout(2, false));
		algoChoose.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		final Combo algolist = new Combo(algoChoose, SWT.DROP_DOWN | SWT.READ_ONLY);
		algolist.setLayout(new GridLayout(2, false));
		algolist.setItems("rbfs bfs dfs".split(" "));
		int j=0;
		for (j=0;j<"rbfs bfs dfs".split(" ").length;j++) { //select the default algorithm that configured in the xml
		if(algolist.getItem(j).equals(prop.getAlgorithmForMazeSolution())) {
			break;
		}
		}
		algolist.select(j);
		Button solveByAlgobtn = new Button(algoChoose, SWT.PUSH);
		solveByAlgobtn.setText("Solve");
		solveByAlgobtn.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		solveByAlgobtn.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String mazenameSelected;
				String algoSelected;
				

				int selnum;
				selnum = mazesChoose.getSelectionIndex();
				mazenameSelected = mazesChoose.getItem(selnum);
				selnum = algolist.getSelectionIndex();
				algoSelected = algolist.getItem(selnum);
				if (mazenameSelected.equals(mazeLoaded)) {
				setChanged();
				notifyObservers("solve_maze " + "<" + mazenameSelected + "> " + "<" + algoSelected + ">");
				
				setChanged();
				notifyObservers("maze_to_get_solve " + "<" + mazenameSelected + ">");
				states = mazeSolution.getStates();
				
				
			for (int i = 0; i < states.size(); i++) {
						mazeDisplay.updateLocation(convertStateToPosition(states.get(i).getDescription()));
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
						}
						display.readAndDispatch();

				}
				}
				else {popUpmsg("please load maze before");}

			}


			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		//************Hint Button*******************
		Button hintBtn = new Button(toolBar, SWT.PUSH);
		hintBtn.setText("Hint");
		hintBtn.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		hintBtn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String mazenameSelected;
				String algoSelected;
				boolean trackInspection=false;
				int countBacksteps=-1;

				int selnum;
				selnum = mazesChoose.getSelectionIndex();
				mazenameSelected = mazesChoose.getItem(selnum);
				selnum = algolist.getSelectionIndex();
				algoSelected = algolist.getItem(selnum);
				setChanged();
				notifyObservers("solve_maze " + "<" + mazenameSelected + "> " + "<" + algoSelected + ">");
				
				setChanged();
				notifyObservers("maze_to_get_solve " + "<" + mazenameSelected + ">");
				states = mazeSolution.getStates();
				
				ArrayList<Position> positionList=new ArrayList<Position>(); //contain array list of position untill goal position
				for (int i=0;i<states.size();i++) {
					positionList.add(convertStateToPosition(states.get(i).getDescription()));
					
				}
				for (int i=0;i<positionList.size();i++) { //if the character on the goal position
					if(mazeDisplay.character.getPosition().equals(themaze.goalP)) {
						popUpmsg("You Win you Can`t get Hint :)");
						trackInspection=true;
						break;
					}
					
					if(positionList.get(i).equals(mazeDisplay.character.getPosition())) { //if the character on position of the arraylist positionList
						trackInspection=true;
						mazeDisplay.updateLocation(positionList.get(i+1));
						display.readAndDispatch();
						if (mazeDisplay.character.getPosition().layers%2 !=0) {
							trackInspection=true;
							mazeDisplay.updateLocation(positionList.get(i+2));
							display.readAndDispatch();
						}

						break;
					}
				}
				if (trackInspection==false) { //if the character not on the right track the hint return the character to the right track
					countBacksteps--;
					mazeDisplay.updateLocation(mazeDisplay.characterTrack.get(mazeDisplay.characterTrack.size()+countBacksteps));
					mazeDisplay.characterTrack.remove(mazeDisplay.characterTrack.get(mazeDisplay.characterTrack.size()+countBacksteps));
					display.readAndDispatch();
					if (mazeDisplay.character.getPosition().layers%2 !=0) {
						countBacksteps--;
						mazeDisplay.updateLocation(mazeDisplay.characterTrack.get(mazeDisplay.characterTrack.size()+countBacksteps));
						mazeDisplay.characterTrack.remove(mazeDisplay.characterTrack.get(mazeDisplay.characterTrack.size()+countBacksteps));
					
					}
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		
		
		
		
		// ************close Button + Implement****************

		Button closebtn = new Button(toolBar, SWT.PUSH);
		closebtn.setText("   Close   ");
		closebtn.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.dispose();
				setChanged();
				notifyObservers("exit");

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});

	}

	protected Position convertStateToPosition(String s) { //convert position from states description to Position format
		Position p = new Position();
		StringBuilder sb = new StringBuilder();
		sb.append(s);
		for (int i = 0; i < sb.length(); i++) {
			if (sb.charAt(i) == '{' || sb.charAt(i) == ',' || sb.charAt(i) == '}') {
				sb.replace(i, i + 1, " ");
			}
		}
		sb.replace(0, 1, "");
		String[] pos = sb.toString().split(" ");
		p.layers = Integer.parseInt(pos[0]);
		p.rows = Integer.parseInt(pos[1]);
		p.cols = Integer.parseInt(pos[2]);

		return p;

	}

}
