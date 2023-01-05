/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import gui.tests.TestPanel;
import gui.tests.BlockFrequencyPanel;
import actions.InputActionListener;
import actions.InputChangeListener;
import actions.InputDocumentListener;
import actions.InputKeyListener;
import actions.InputPopupListener;
import assets.Definition;
import java.awt.Checkbox;
import java.awt.Color;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.Security;
import java.util.HashMap;
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
import javax.swing.SwingConstants;
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
public class InputWindowOriginal {

	private JFrame frame;

	private JLabel inputLabel;
	private JTextArea input;
	private JButton test;
	private JLabel generateLabel;
	private JButton generateButton;
	private JLabel genSizeLabel;
	private JTextField genSizeInput;

	private JLabel bitLen;
	private JTextField bitLenValue;
	private JPanel panel;

	private JComboBox algorithm;
	private JLabel algorithmLabel;

	private JCheckBox[] testSelections;
	private JCheckBox all;

	private JPopupMenu popup;

	private BlockFrequencyPanel bf;
	private HashMap<String, TestPanel> testPanels;

	public InputWindowOriginal() {
		this.testPanels = new HashMap<>();
	}

	public void build() {
		JScrollPane sp = new JScrollPane(this.getInput());
		sp.setBounds(10, 30, 565, 180);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		this.getPanel().add(sp);
		this.getPanel().add(this.getAll());

		this.getPanel().add(this.getTest());
		this.getPanel().add(this.getBitLen());
		this.getPanel().add(this.getBitLenValue());
		this.getPanel().add(this.getGenerateLabel());
		this.getPanel().add(this.getGenerateButton());
		this.getPanel().add(this.getAlgorithmLabel());
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
			this.frame.setSize(600, 820);
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
			this.input.setBounds(0, 0, 565, 180);
			this.input.setLineWrap(true);
			this.input.setWrapStyleWord(true);
			this.input.setBorder(new EmptyBorder(10, 10, 10, 10));
//			this.input.addKeyListener(new InputKeyListener(this));
//			d.addDocumentListener(new InputDocumentListener(this));
//			this.input.addMouseListener(new InputPopupListener(this));
		}
		return input;
	}

	public JButton getTest() {
		if (this.test == null) {
			this.test = new JButton("Run Tests");
			this.test.setBounds(10, 750, 570, 30);
//			this.test.addActionListener(new InputActionListener(this));
		}
		return test;
	}

	public HashMap<String, TestPanel> getTestPanels() {
		return testPanels;
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
			this.bitLen.setBounds(350, 220, 120, 30);
			this.bitLen.setBorder(new EmptyBorder(0, 2, 0, 0));
		}
		return bitLen;
	}

	public JTextField getBitLenValue() {
		if (this.bitLenValue == null) {
			this.bitLenValue = new JTextField("0");
			this.bitLenValue.setBounds(470, 220, 100, 30);
			this.bitLenValue.setEditable(false);
			this.bitLenValue.setBorder(null);
			this.bitLenValue.setHorizontalAlignment(SwingConstants.RIGHT);
//			this.bitLenValue.addKeyListener(new InputKeyListener(this));
		}
		return bitLenValue;
	}

	public JLabel getGenerateLabel() {
		if (this.generateLabel == null) {
			this.generateLabel = new JLabel("Generate");
			this.generateLabel.setBounds(10, 260, 60, 30);
		}
		return generateLabel;
	}

	public JLabel getAlgorithmLabel() {
		if (this.algorithmLabel == null) {
			this.algorithmLabel = new JLabel("algorithm");
			this.algorithmLabel.setBounds(430, 260, 80, 30);
		}
		return algorithmLabel;
	}

	public JButton getGenerateButton() {
		if (this.generateButton == null) {
			this.generateButton = new JButton("GO");
			this.generateButton.setBounds(510, 260, 70, 30);
//			this.generateButton.addActionListener(new InputActionListener(this));
		}
		return generateButton;
	}

	public JComboBox getAlgorithm() {
		if (this.algorithm == null) {

			Set<String> osa = Security.getAlgorithms("SecureRandom");
			String[] algorithms = new String[osa.size()];
			osa.toArray(algorithms);

			this.algorithm = new JComboBox(algorithms);
			this.algorithm.setBounds(240, 260, 180, 30);
		}
		return algorithm;
	}

	public JLabel getGenSizeLabel() {
		if (genSizeLabel == null) {
			this.genSizeLabel = new JLabel("bits using");
			this.genSizeLabel.setBounds(150, 260, 90, 30);
			this.genSizeLabel.setBorder(new EmptyBorder(0, 2, 0, 0));
		}
		return genSizeLabel;
	}

	public JTextField getGenSizeInput() {
		if (this.genSizeInput == null) {
			this.genSizeInput = new JTextField("0");
			this.genSizeInput.setBounds(70, 260, 80, 30);
			this.genSizeInput.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return genSizeInput;
	}

	public JCheckBox[] getTestSelections() {
		if (this.testSelections == null) {
			this.testSelections = new JCheckBox[Definition.tests.length];
			AttributePanelCreator apc = new AttributePanelCreator();
			for (int i = 0; i < Definition.tests.length; i++) {
				this.testSelections[i] = new JCheckBox(Definition.tests[i]);
				this.testSelections[i].setBounds(60, 300 + (i * 30), 250, 30);
				TestPanel tp = apc.getPanel(Definition.tests[i]);
				if (tp != null) {
					tp.getPanel().setBounds(310, 300 + (i * 30), 250, 30);
					this.testPanels.put(Definition.tests[i], tp);
					this.getPanel().add(tp.getPanel());
				}

				/*
				if ( Definition.tests[i] == "Block Frequency" ) {
					this.testSelections[i] = new JCheckBox(Definition.tests[i]);
					this.testSelections[i].setBounds(60, 300 + (i * 30), 280, 30);
					bf = new BlockFrequency();
					bf.getPanel().setBounds(300, 300 + (i * 30), 250, 30);
					bf.getPanel().setBackground(new Color(50, 50, 50));
					this.getPanel().add(bf.getPanel());
				} else {
					this.testSelections[i] = new JCheckBox(Definition.tests[i]);
					this.testSelections[i].setBounds(60, 300 + (i * 30), 280, 30);
				}
				 */
			}
		}
		return this.testSelections;
	}

	public JCheckBox getAll() {
		if (this.all == null) {
			this.all = new JCheckBox("All");
			this.all.setBounds(10, 300, 50, 30);
//			this.all.addChangeListener(new InputChangeListener(this));
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
