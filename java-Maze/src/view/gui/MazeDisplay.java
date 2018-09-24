package view.gui;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithm.mazeGenerators.Maze3d;
import algorithm.mazeGenerators.Position;

public abstract class MazeDisplay extends Canvas {
	protected int[][] mazeData; 
	protected Maze3d themaze;
	protected GameCharacter character= new GameCharacter();
	protected ArrayList<Position> characterTrack=new ArrayList<Position>();

	public MazeDisplay(Composite parent, int style) {
		super(parent, style);
		this.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				drawMaze(e);
				
			}
		});
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.keyCode) {
				case SWT.ARROW_LEFT:
						goLeft();

					break;
				case SWT.ARROW_RIGHT:
					goRight();

					break;
				case SWT.ARROW_UP:
					
						goBackward();
					break;
				case SWT.ARROW_DOWN:
					goForward();

					break;
				case SWT.PAGE_DOWN:
					goDown();


					break;
				case SWT.PAGE_UP:
					goUp();
					

					break;
				}
				
			}
		});
	}
	protected abstract void drawMaze(PaintEvent e);

	protected abstract void goLeft();

	protected abstract void goRight();

	protected abstract void goForward();

	protected abstract void goBackward();

	protected abstract void goUp();

	protected abstract void goDown();
	
	protected abstract void updateLocation(Position p);
	
	
	
	public void setMazeData(int[][] MazeData,Maze3d themaze) {
		this.mazeData=MazeData;
		this.themaze=themaze;
		
	}
	public void setCharacterPosition (Position p) {
		character.setPosition(p);
		
	}


}
