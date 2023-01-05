/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.partials;

import actions.InputDocumentListener;
import actions.InputKeyListener;
import actions.InputPopupListener;
import gui.InputWindow;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;

/**
 *
 * @author kenanince
 */
public class TextInputPanel {

	private JPanel panel;
	private JTextArea input;
	private JLabel inputLabel;
	private JScrollPane sp;

	private final InputWindow iw;

	public TextInputPanel(InputWindow iw) {
		this.iw = iw;
	}

	public JPanel getPanel() {
		if ( this.panel == null ) {
			this.panel = new JPanel(null);
			this.panel.setSize(600, 180);
			this.panel.add(this.getSp());
			this.panel.add(this.getInputLabel());
		}
		return panel;
	}

	public JTextArea getInput() {
		if (this.input == null) {
			Document d = new DefaultStyledDocument();
			this.input = new JTextArea(d);
			this.input.setBounds(0, 0, 565, 180);
			this.input.setLineWrap(true);
			this.input.setWrapStyleWord(true);
			this.input.setBorder(new EmptyBorder(10, 10, 10, 10));
			this.input.addKeyListener(new InputKeyListener(this.iw));
			d.addDocumentListener(new InputDocumentListener(this.iw));
			this.input.addMouseListener(new InputPopupListener(this.iw));
		}
		return input;
	}

	public JLabel getInputLabel() {
		if (this.inputLabel == null) {
			this.inputLabel = new JLabel("INPUT");
			this.inputLabel.setBounds(10, 5, 300, 25);
		}
		return inputLabel;
	}

	public JScrollPane getSp() {
		if ( this.sp == null ) {
			this.sp = new JScrollPane(this.getInput());
			this.sp.setBounds(10, 30, 565, 150);
			this.sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		}
		return sp;
	}

}
