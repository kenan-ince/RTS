/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.partials;

import actions.InputActionListener;
import gui.InputWindow;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author kenanince
 */
public class FileInputPanel {

	private JPanel panel;
	private JButton open;
	private JLabel fileLabel;
	private JLabel dividerLabel;
	private JTextField divider;
	private JButton importButton;

	private InputWindow iw;

	public FileInputPanel(InputWindow iw) {
		this.iw = iw;
	}

	public JPanel getPanel() {
		if ( this.panel == null ) {
			this.panel = new JPanel(null);
			this.panel.setSize(600, 30);
			this.panel.add(this.getOpen());
			this.panel.add(this.getFileLabel());
			this.panel.add(this.getDividerLabel());
			this.panel.add(this.getDivider());
			this.panel.add(this.getImportButton());
		}
		return panel;
	}

	public JButton getOpen() {
		if ( this.open == null ) {
			this.open = new JButton("Select File");
			this.open.setBounds(10, 0, 100, 30);
			this.open.addActionListener(new InputActionListener(iw));
		}
		return open;
	}
	
	public JLabel getFileLabel() {
		if ( this.fileLabel == null ) {
			this.fileLabel = new JLabel("No file selected");
			this.fileLabel.setBounds(115, 0, 280, 30);
		}
		return this.fileLabel;
	}

	public JLabel getDividerLabel() {
		if ( this.dividerLabel == null ) {
			this.dividerLabel = new JLabel("Divider : ");
			this.dividerLabel.setBounds(400, 0, 60, 30);
		}
		return dividerLabel;
	}

	public JTextField getDivider() {
		if ( this.divider == null ) {
			this.divider = new JTextField(",");
			this.divider.setHorizontalAlignment(SwingConstants.CENTER);
			this.divider.setBounds(450, 0, 30, 30);
		}
		return divider;
	}

	public JButton getImportButton() {
		if ( this.importButton == null ) {
			this.importButton = new JButton("PARSE");
			this.importButton.setBounds(480, 00, 100, 29);
			this.importButton.addActionListener(new InputActionListener(iw));
		}
		return importButton;
	}
	
	

	public InputWindow getIw() {
		return iw;
	}
	
}
