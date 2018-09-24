package boot;


import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

import model.MyModel;
import presenter.Presenter;
import view.MyView;
import view.gui.GuiView;
import view.gui.MazeWindow;

public class Run {

	public static void main(String[] args) {
		
	
		
		
		
		
		Display display=new Display();
		final Shell shell=new Shell(display);
		final Monitor monitor = display.getPrimaryMonitor();
		
		shell.setSize(300,200);
		
		shell.setLocation((monitor.getClientArea().width/2)-200,(monitor.getClientArea().height/2)-200);
		shell.setLayout(new GridLayout(1, false));
		
		Label label=new Label(shell,SWT.NONE);
		label.setText("choose UI");
		label.setLayoutData(new GridData(SWT.CENTER,SWT.CENTER,true,true));
		Button guibutton=new Button(shell,SWT.PUSH);
		guibutton.setText("Gui");
		guibutton.setLayoutData(new GridData(SWT.FILL,SWT.CENTER,true,true));
		
		guibutton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				shell.dispose();
				
				
				Thread t=new Thread(new Runnable() {
					public void run() {
						MyModel model= new MyModel();
						GuiView view= new GuiView();
						Presenter p = new Presenter(model,view);
						view.addObserver(p);
						model.addObserver(p);
						view.start();
					}
				});
				t.start();
			/*	
				try {
					t.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				t.interrupt();
				*/
				
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		Button clibutton=new Button(shell,SWT.PUSH);
		clibutton.setText("Cli");
		clibutton.setLayoutData(new GridData(SWT.FILL,SWT.CENTER,true,true));
		clibutton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.dispose();
				
				Thread t=new Thread(new Runnable() {
					
					@Override
					public void run() {
						MyModel m= new MyModel();
						MyView v = new MyView(new BufferedReader(new InputStreamReader(System.in)),new PrintWriter(System.out));
						Presenter p= new Presenter(m,v);
						
						v.addObserver(p);
						m.addObserver(p);
						v.start();							
					}
				});
				t.start();
				/*
				try {
					t.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				t.interrupt();
				*/
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		shell.open();
		while (!shell.isDisposed()) { //loop to management the event queue while the gui isnt closed

			if (display.readAndDispatch()) {  //if the event queue is empty go to sleep until new event occurs
				display.sleep();
			}
		}
		display.dispose(); //close
		
		
	}

}