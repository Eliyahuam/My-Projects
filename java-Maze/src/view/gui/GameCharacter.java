package view.gui;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import algorithm.mazeGenerators.Position;
/**
 * 
 * @author by using this class can introduce the character the maze
 * @param icon the path to the character image
 * @param position allowed to control the position of the character
 *
 */
public class GameCharacter {
	private static final String icon = "resources/Minion-Happy-icon.png";
	private Position position;
	
	public Position getPosition() {
		return position;
	}
	/**
	 * 
	 * @param p get position by the user and set the new position as required
	 */
	public void setPosition (Position p) {
		this.position=p;
	}
	/**
	 * @param e -Paint event , allowed to use gc and his features
	 * @param cellWidth - the width of the character in the maze
	 * @param cellHeight - the height of the character in the maze
	 */
	public void draw(PaintEvent e, int cellWidth, int cellHeight) {
		e.gc.setBackground(new Color(null, 255, 255, 255));
		
		Image img = new Image(null, icon);
		e.gc.drawImage(img, 0, 0, 128, 128, position.rows * cellWidth, position.cols * cellHeight, cellWidth, cellHeight);
	}

}
