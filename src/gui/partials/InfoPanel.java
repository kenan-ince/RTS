/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.partials;

import gui.InputWindow;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author kenanince
 */
public class InfoPanel {

	private JPanel panel;
	private JLabel bitLen;
	private JTextField bitLenValue;

	private final InputWindow iw;

	public InfoPanel(InputWindow iw) {
		this.iw = iw;
	}

	public JPanel getPanel() {
		if ( this.panel == null ) {
			this.panel = new JPanel(null);
			this.panel.setSize(600, 30);
			this.panel.add(this.getBitLen());
			this.panel.add(this.getBitLenValue());
		}
		return panel;
	}

	public JLabel getBitLen() {
		if (this.bitLen == null) {
			this.bitLen = new JLabel("Length Counter:");
			this.bitLen.setBounds(350, 0, 120, 30);
			this.bitLen.setBorder(new EmptyBorder(0, 2, 0, 0));
		}
		return bitLen;
	}

	public JTextField getBitLenValue() {
		if (this.bitLenValue == null) {
			this.bitLenValue = new JTextField("0");
			this.bitLenValue.setBounds(470, 0, 100, 30);
			this.bitLenValue.setEditable(false);
			this.bitLenValue.setBorder(null);
			this.bitLenValue.setHorizontalAlignment(SwingConstants.RIGHT);
//			this.bitLenValue.addKeyListener(new InputKeyListener(this));
		}
		return bitLenValue;
	}

}
