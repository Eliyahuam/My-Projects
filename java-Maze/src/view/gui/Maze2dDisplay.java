package view.gui;


import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import algorithm.mazeGenerators.Position;


/**
 * 
 * @author Eliyahu -this class inherited the class MazeDisplay and implementing few functions
 * @param goalicon -contain the path to the goal image
 * @param winnerwindow - playing winner sound and image
 *
 */
public class Maze2dDisplay extends MazeDisplay {
	//private static final String wallicon = "images/5.png";
	private static final String goalicon = "resources/goal.png";
	
	private WinnerWindow winnerwindow=new WinnerWindow(getShell());
	

	public Maze2dDisplay(Composite parent, int style) {
		super(parent, style);
		this.setBackground(new Color(null, 255, 255, 255));
	}

	@Override
	protected void drawMaze(PaintEvent e) {

		int width = getSize().x;
		int height = getSize().y;

		int cellWidth = width / mazeData[0].length;
		int cellHeight = height / mazeData.length;
		String[] updown;
		Position p = new Position();
		p.layers = character.getPosition().layers;
		p.rows = 0;
		p.cols = 0;
		for (int i = 0; i < mazeData.length; i++) { //paint the maze walls in black
			for (int j = 0; j < mazeData[0].length; j++) {
				if (mazeData[i][j] != 0) {
					e.gc.setBackground(new Color(null, 0, 0, 0));
					e.gc.fillRectangle(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
					// Image img = new Image(null, wallicon);
					// e.gc.drawImage(img, 0, 0, 128, 128, j * cellWidth, i * cellHeight, cellWidth, cellHeight);
				} else {
					p.rows = j;
					p.cols = i;
					updown = themaze.getPossibleMoves(p);
					for (String move : updown) { //paint the points to up and down layer in green
						if (move.equals("Up") || move.equals("Down")) {
							e.gc.setBackground(new Color(null, 38, 246, 204));
							e.gc.fillRectangle(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
							// Image img = new Image(null, turbine);
							//e.gc.drawImage(img, 0, 0, 128, 128, j * cellWidth, i * cellHeight, cellWidth,cellHeight);
						}

					}

				}

			}
		}

		if (character.getPosition().layers == themaze.goalP.layers) { //paint the goal position image on the maze
			Image img = new Image(null, goalicon);
			e.gc.drawImage(img, 0, 0, 128, 128, themaze.goalP.rows * cellWidth, themaze.goalP.cols * cellHeight,
					cellWidth, cellHeight);
			
		}

		character.draw(e, cellWidth, cellHeight); //paint the character

	}

	@Override
	protected void goLeft() {

		Position position = character.getPosition();
		String[] possible = themaze.getPossibleMoves(position);
		for (String move : possible) { 

			if (move.equals("Left")) {
				character.setPosition(new Position(position.layers, position.rows - 1, position.cols));
				if (themaze.goalP.equals(new Position(position.layers, position.rows-1, position.cols))){ //check if the next position is the goal position and playing winner sound
					
					winnerwindow.winnerAnimation();
				}
				characterTrack.add(new Position(position.layers, position.rows-1, position.cols)); //adding the move to array list helping to Hint function to back the character to the right track
				this.redraw(); //paint the new position
			}
		}

	}

	@Override
	protected void goRight() {

		Position position = character.getPosition();
		String[] possible = themaze.getPossibleMoves(position);
		for (String move : possible) {

			if (move.equals("Right")) {
				character.setPosition(new Position(position.layers, position.rows + 1, position.cols));
				if (themaze.goalP.equals(new Position(position.layers, position.rows+1, position.cols))){ 
					
					winnerwindow.winnerAnimation();
				}
				characterTrack.add(new Position(position.layers, position.rows+1, position.cols));
				this.redraw();
			}
		}

	}

	@Override
	protected void goForward() {

		Position position = character.getPosition();
		String[] possible = themaze.getPossibleMoves(position);
		for (String move : possible) {

			if (move.equals("Forward")) {
				character.setPosition(new Position(position.layers, position.rows, position.cols + 1));
				if (themaze.goalP.equals(new Position(position.layers, position.rows, position.cols+1))){

					winnerwindow.winnerAnimation();
				}
				characterTrack.add(new Position(position.layers, position.rows, position.cols + 1));
				this.redraw();
			}
		}

	}

	@Override
	protected void goBackward() {

		Position position = character.getPosition();
		String[] possible = themaze.getPossibleMoves(position);
		for (String move : possible) {

			if (move.equals("Backward")) {
				character.setPosition(new Position(position.layers, position.rows, position.cols - 1));
				if (themaze.goalP.equals(new Position(position.layers, position.rows, position.cols-1))){
					
					winnerwindow.winnerAnimation();
				}
				characterTrack.add(new Position(position.layers, position.rows, position.cols -1));
				this.redraw();
			}
		}

	}

	@Override
	protected void goUp() {

		Position position = character.getPosition();
		String[] possible = themaze.getPossibleMoves(position);
		for (String move : possible) {

			if (move.equals("Up")) {
				mazeData = themaze.getCrossSectionByX(position.layers + 2);
				character.setPosition(new Position(position.layers + 2, position.rows, position.cols));
				setMazeData(mazeData, themaze);
				if (themaze.goalP.equals(new Position(position.layers+2, position.rows, position.cols))){

					winnerwindow.winnerAnimation();
				}
				characterTrack.add(new Position(position.layers+2, position.rows, position.cols));
				this.redraw();
			}

		}

	}

	@Override
	protected void goDown() {

		Position position = character.getPosition();
		String[] possible = themaze.getPossibleMoves(position);
		for (String move : possible) {

			if (move.equals("Down")) {
				mazeData = themaze.getCrossSectionByX(position.layers - 2);
				character.setPosition(new Position(position.layers - 2, position.rows, position.cols));
				setMazeData(mazeData, themaze);
				if (themaze.goalP.equals(new Position(position.layers-2, position.rows, position.cols))){

					winnerwindow.winnerAnimation();
				}
				characterTrack.add(new Position(position.layers-2, position.rows, position.cols));
				this.redraw();
			}
		}

	}
	protected void updateLocation(Position p) { //updating the new location when using solve or hint
		mazeData = themaze.getCrossSectionByX(p.layers);
		character.setPosition(new Position(p.layers,p.rows,p.cols));
		setMazeData(mazeData, themaze);
		this.redraw();
		
		
		
		
	}

}
