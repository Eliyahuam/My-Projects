package view.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * 
 * @author Eliyahu - this class get a massage and display to user
 * @param shell - get the shell father and open inside the popup massage
 *
 */
public class PopUpWindow extends Dialog implements Runnable  {

	Shell shell;
	
	public PopUpWindow(Shell shell) {
		super(shell);
		
	}

	public void popUpopen(String msg) {
	Shell parent=getParent();
	final Shell dialog= new Shell(parent,SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM );
	Point point= new Point(0, 0);
	point=parent.getLocation();
	dialog.setLocation(point.x+300,point.y+300);
	
	
	dialog.setSize(400,150);
	
	dialog.setLayout(new GridLayout(1,false));
	
	
	Label msgLabel= new Label(dialog, SWT.NULL);
	msgLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER,true, true));
	
	msgLabel.setText(msg);
	
	Button okbutton=new Button(dialog, SWT.PUSH);
	okbutton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER,true, true));
	okbutton.setText("     Ok     ");
	okbutton.addSelectionListener(new SelectionListener() {
		
		@Override
		public void widgetSelected(SelectionEvent arg0) {
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
