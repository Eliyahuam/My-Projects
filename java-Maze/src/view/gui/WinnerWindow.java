package view.gui;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;


/**
 * 
 * @author Eliyahu - this class playing the winner sound and open popup with picture of fireworks
 * @param shell - get the father shell and display inside the window
 * 
 */
public class WinnerWindow extends Dialog implements Runnable {
	
	Shell shell;
	Clip audioClip;
	AudioInputStream audioStream;
	
	private static final String fireworks = "resources/fireworks.jpg";
	
	public WinnerWindow(Shell shell) {
		super(shell);
		
		
	}

	public void winnerAnimation() {
	Shell parent=getParent();
	final Shell dialog= new Shell(parent,SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM );
	Point point= new Point(0, 0);
	point=parent.getLocation();
	dialog.setLocation(point.x+300,point.y+300);
	dialog.addPaintListener(new PaintListener() {
		
		
		public void paintControl(PaintEvent e) {
			e.gc.setBackground(new Color(null, 255, 255, 255));
			
			Image img = new Image(null, fireworks);
			e.gc.drawImage(img,0,0, 247,200,0,0,500,410);
			
			
		}
	});
	
	
	dialog.setSize(500,450);
	
	dialog.setLayout(new GridLayout(1,false));
	
	
	Label msgLabel= new Label(dialog, SWT.NULL);
	msgLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER,true, true));
	
	msgLabel.setText("Congratulation you win");
	//*****************play sound****************************
	final Thread t= new Thread(new Runnable() {
		
		@Override
		public void run() {
			try {
				
				File audioFile = new File("resources/fireworks.wav");
				audioStream = AudioSystem.getAudioInputStream(audioFile);
				AudioFormat format = audioStream.getFormat();
				 
				DataLine.Info info = new DataLine.Info(Clip.class, format);
				audioClip = (Clip) AudioSystem.getLine(info);
				audioClip.open(audioStream);
				audioClip.start();
		        Thread.sleep(20000);
				audioClip.close();
				audioStream.close();
				
			} catch (UnsupportedAudioFileException e) {
				
			} catch (IOException e) {
			} catch (InterruptedException ex) {
	            
	        } catch (LineUnavailableException el) {
	        	
	        }
		}
	});

	t.start();
	
	//***********************************************************
	Button okbutton=new Button(dialog, SWT.PUSH);
	okbutton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER,true, true));
	okbutton.setText("     Ok     ");
	okbutton.addSelectionListener(new SelectionListener() {
		
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			audioClip.close();
			try {
				audioStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			t.interrupt();
			dialog.close();
			
			
		}
		
		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	});
	
	dialog.open();

	
	
	}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	

}
