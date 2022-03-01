/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import actions.WelcomeActions;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author kenanince
 */
public class WelcomeWindow {

	private JFrame frame;
	private JPanel panel;
	private JButton singleTest;
	private JButton CSVTest;
	
	private WelcomeActions wa;
	
	public void build() {
		this.getPanel().add(this.getSingleTest());
		this.getPanel().add(this.getCSVTest());
		this.getFrame();
	}

	public JFrame getFrame() {
		if (this.frame == null) {
			this.frame = new JFrame("Options");
			this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.frame.setSize(335, 150);
			this.frame.setLocationRelativeTo(null);
			this.frame.setContentPane(this.getPanel());
			this.frame.setVisible(true);
		}
		return frame;
	}

	public JPanel getPanel() {
		if ( this.panel == null ) {
			this.panel = new JPanel(null);
		}
		return panel;
	}

	public JButton getSingleTest() {
		if ( this.singleTest == null ) {
			this.singleTest = new JButton("Single Sequence Test");
			this.singleTest.setBounds(10, 10, 300, 30);
			this.singleTest.addActionListener(this.getWa());
		}
		return singleTest;
	}

	public JButton getCSVTest() {
		if ( this.CSVTest == null ) {
			this.CSVTest = new JButton("Test From File");
            this.CSVTest.setEnabled(false);
			this.CSVTest.setBounds(10, 50, 300, 30);
		}
		return CSVTest;
	}

	public WelcomeActions getWa() {
		if ( wa == null ) {
			this.wa = new WelcomeActions(this);
		}
		return wa;
	}
}
