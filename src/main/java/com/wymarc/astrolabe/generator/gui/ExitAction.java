package com.wymarc.astrolabe.generator.gui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import javax.swing.Action;
import javax.swing.AbstractAction;

/**
 *  This class will create and dispatch a WINDOW_CLOSING event to the active
 *  frame.  As a result a request to close the frame will be made and any
 *  WindowListener that handles the windowClosing event will be executed.
 *  Since clicking on the "Close" button of the frame or selecting the "Close"
 *  option from the system menu also invoke the WindowListener, this will
 *  provide a common exit point for the application.
 */
public class ExitAction extends AbstractAction
{
	public ExitAction()
	{
		super("Exit");
		putValue( Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_X) );
	}

	public void actionPerformed(ActionEvent e)
	{
		//  Find the active frame before creating and dispatching the event

		for (Frame frame : Frame.getFrames())
		{
			if (frame.isActive())
			{
				WindowEvent windowClosing = new WindowEvent(frame, WindowEvent.WINDOW_CLOSING);
				frame.dispatchEvent(windowClosing);
			}
		}
	}
}
