/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import actions.InputActionListener;
import actions.InputChangeListener;
import actions.InputDocumentListener;
import actions.InputKeyListener;
import actions.InputPopupListener;
import assets.Definition;
import java.awt.Checkbox;
import java.awt.Color;
import java.security.Security;
import java.util.Set;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;

/**
 *
 * @author knn
 */
public class InputWindow {

	private JFrame frame;

	private JLabel inputLabel;
	private JTextArea input;
	private JButton test;
	private JButton generate;
	private JLabel genSizeLabel;
	private JTextField genSizeInput;

	private JLabel bitLen;
	private JTextField bitLenValue;
	private JPanel panel;

	private JComboBox algorithm;

	private JCheckBox[] testSelections;
	private JCheckBox all;

	private JPopupMenu popup;

	public InputWindow() {
	}

	public void build() {
		JScrollPane sp = new JScrollPane(this.getInput());
		sp.setBounds(10, 30, 475, 180);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		this.getPanel().add(sp);
		this.getPanel().add(this.getAll());

		this.getPanel().add(this.getTest());
		this.getPanel().add(this.getBitLen());
		this.getPanel().add(this.getBitLenValue());
		this.getPanel().add(this.getGenerate());
		this.getPanel().add(this.getGenSizeLabel());
		this.getPanel().add(this.getAlgorithm());
		this.getPanel().add(this.getGenSizeInput());
		this.getPanel().add(this.getInputLabel());

		for (JCheckBox testSelection : this.getTestSelections()) {
			this.getPanel().add(testSelection);
		}

		this.getFrame();
	}

	public JFrame getFrame() {
		if (this.frame == null) {
			this.frame = new JFrame("Single Sequence Test");
			this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.frame.setSize(510, 755);
			this.frame.setLocationRelativeTo(null);
			this.frame.setContentPane(this.getPanel());
			this.frame.setVisible(true);
		}
		return frame;
	}

	public JLabel getInputLabel() {
		if (this.inputLabel == null) {
			this.inputLabel = new JLabel("INPUT");
			this.inputLabel.setBounds(10, 5, 300, 25);
		}
		return inputLabel;
	}

	public JTextArea getInput() {
		if (this.input == null) {
			Document d = new DefaultStyledDocument();
			this.input = new JTextArea(d);
			this.input.setBounds(0, 0, 475, 180);
			this.input.setLineWrap(true);
			this.input.setWrapStyleWord(true);
			this.input.setBorder(new EmptyBorder(10, 10, 10, 10));
			this.input.addKeyListener(new InputKeyListener(this));
			d.addDocumentListener(new InputDocumentListener(this));
			this.input.addMouseListener(new InputPopupListener(this));
		}
		return input;
	}

	public JButton getTest() {
		if (this.test == null) {
			this.test = new JButton("Run Tests");
			this.test.setBounds(10, 680, 475, 30);
			this.test.addActionListener(new InputActionListener(this));
		}
		return test;
	}

	public JPanel getPanel() {
		if (this.panel == null) {
			this.panel = new JPanel(null);
		}
		return panel;
	}

	public JLabel getBitLen() {
		if (this.bitLen == null) {
			this.bitLen = new JLabel("Length Counter:");
			this.bitLen.setBounds(260, 220, 120, 30);
			this.bitLen.setBorder(new EmptyBorder(0, 2, 0, 0));
		}
		return bitLen;
	}

	public JTextField getBitLenValue() {
		if (this.bitLenValue == null) {
			this.bitLenValue = new JTextField("0");
			this.bitLenValue.setBounds(380, 220, 100, 30);
			this.bitLenValue.setEditable(false);
			this.bitLenValue.addKeyListener(new InputKeyListener(this));
		}
		return bitLenValue;
	}

	public JButton getGenerate() {
		if (this.generate == null) {
			this.generate = new JButton("Generate");
			this.generate.setBounds(380, 260, 100, 30);
			this.generate.addActionListener(new InputActionListener(this));
		}
		return generate;
	}

	public JComboBox getAlgorithm() {
		if (this.algorithm == null) {

			Set<String> osa = Security.getAlgorithms("SecureRandom");
			String[] algorithms = new String[osa.size()];
			osa.toArray(algorithms);

			this.algorithm = new JComboBox(algorithms);
			this.algorithm.setBounds(190, 260, 180, 30);
		}
		return algorithm;
	}

	public JLabel getGenSizeLabel() {
		if (genSizeLabel == null) {
			this.genSizeLabel = new JLabel("bits using");
			this.genSizeLabel.setBounds(120, 260, 90, 30);
			this.genSizeLabel.setBorder(new EmptyBorder(0, 2, 0, 0));
		}
		return genSizeLabel;
	}

	public JTextField getGenSizeInput() {
		if (this.genSizeInput == null) {
			this.genSizeInput = new JTextField("0");
			this.genSizeInput.setBounds(10, 260, 100, 30);
		}
		return genSizeInput;
	}

	public JCheckBox[] getTestSelections() {
		if (this.testSelections == null) {
			this.testSelections = new JCheckBox[Definition.tests.length];
			for (int i = 0; i < Definition.tests.length; i++) {
				this.testSelections[i] = new JCheckBox(Definition.tests[i]);
				this.testSelections[i].setBounds(200, 300 + (i * 25), 280, 25);
			}
		}
		return this.testSelections;
	}

	public JCheckBox getAll() {
		if (this.all == null) {
			this.all = new JCheckBox("All");
			this.all.setBounds(10, 300, 190, 30);
			this.all.addChangeListener(new InputChangeListener(this));
			this.all.setBorderPainted(true);
			this.all.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
//			this.all.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		}
		return all;
	}

	public JPopupMenu getPopup() {
		if (this.popup == null) {

			Action[] textActions = {new DefaultEditorKit.CutAction(), new DefaultEditorKit.CopyAction(), new DefaultEditorKit.PasteAction(),};

			this.popup = new JPopupMenu();
			for (Action textAction : textActions) {
				this.popup.add(new JMenuItem(textAction));
			}
		}
		return popup;
	}

}
