/**
 * $Id: AstrolabeGenerator.java,v 3.1
 * <p/>
 * The Astrolabe Generator is free software; you can redistribute it
 * and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3 of
 * the License, or(at your option) any later version.
 * <p/>
 * The Astrolabe Generator is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 * <p/>
 * Copyright (c) 2017 Timothy J. Mitchell
 */
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
