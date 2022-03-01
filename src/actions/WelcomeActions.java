/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

import gui.InputWindow;
import gui.WelcomeWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author kenanince
 */
public class WelcomeActions implements ActionListener {

	private WelcomeWindow wf;

	public WelcomeActions(WelcomeWindow wf) {
		this.wf = wf;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ( e.getSource() == wf.getSingleTest() ) {
			InputWindow iw = new InputWindow();
			iw.build();
		}
	}
	
}
