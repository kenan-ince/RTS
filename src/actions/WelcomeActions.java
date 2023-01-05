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
		InputWindow iw = null;
		if (e.getSource() == wf.getSingleTest()) {
			iw = new InputWindow(800, "TextInput");
		}
		if (e.getSource() == wf.getCSVTest()) {
			iw = new InputWindow(610, "FileInput");

		}

		if (e.getSource() == wf.getDataGenerate()) {
			iw = new InputWindow(700, "DataGeneration");

		}

		iw.build();
	}

}
