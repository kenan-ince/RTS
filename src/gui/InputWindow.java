/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import actions.InputActionListener;
import gui.partials.DataGeneratePanel;
import gui.partials.FileInputPanel;
import gui.partials.GeneratePanel;
import gui.partials.InfoPanel;
import gui.partials.TestsPanel;
import gui.partials.TextInputPanel;
import java.awt.Point;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.text.DefaultEditorKit;

/**
 *
 * @author knn
 */
public class InputWindow {

	private JFrame frame;
	private JPanel panel;

	private TextInputPanel tip;
	private FileInputPanel fip;

	private InfoPanel ip;
	private GeneratePanel gp;
	private TestsPanel tp;
	private DataGeneratePanel dgp;

	private String input;

	private JPopupMenu popup;
	
	private JButton runTests;

	private final int h;
	private final String panelType;

	public InputWindow(int h, String t) {
		this.h = h;
		this.panelType = t;
	}

	public void build() {
		this.getFrame();
	}

	public JFrame getFrame() {
		if (this.frame == null) {

			this.frame = new JFrame();

			if (panelType.equals("FileInput")) {
				this.frame.setTitle("File Input Test");
				this.frame.setContentPane(this.getFileTestPanel());
			} else if (panelType.equals("TextInput")) {
				this.frame.setTitle("Text Input Test");
				this.frame.setContentPane(this.getSingleTestPanel());
			} else {
				this.frame.setTitle("Data Generation Toolbox");
				this.frame.setContentPane(this.getDataGenerationPanel());
			}

			this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.frame.setSize(600, h);
			this.frame.setLocationRelativeTo(null);

			this.frame.setVisible(true);
		}
		return frame;
	}

	public JPanel getFileTestPanel() {
		if (this.panel == null) {
			this.panel = new JPanel(null);

			this.getFip().getPanel().setLocation(new Point(0, 10));
			this.getIp().getPanel().setLocation(new Point(0, 40));
			this.getTp().getPanel().setLocation(new Point(0, 70));
			this.getTp().getPanel().add(this.getRunTests());

			this.panel.add(this.getFip().getPanel());
			this.panel.add(this.getIp().getPanel());
			this.panel.add(this.getTp().getPanel());
		}
		return panel;
	}

	public JPanel getDataGenerationPanel() {
		if (this.panel == null) {
			this.panel = new JPanel(null);
			this.getTp().getPanel().setLocation(new Point(0, 10));
			this.panel.add(this.getTp().getPanel());
			this.panel.add(this.getDgp().getPanel());
		}
		return panel;
	}

	public JPanel getSingleTestPanel() {
		if (this.panel == null) {
			this.panel = new JPanel(null);

			this.getTip().getPanel().setLocation(new Point(0, 0));
			this.getIp().getPanel().setLocation(new Point(0, 180));
			this.getGp().getPanel().setLocation(new Point(0, 220));
			this.getTp().getPanel().setLocation(new Point(0, 260));
			this.getTp().getPanel().add(this.getRunTests());
			this.panel.add(tip.getPanel());
			this.panel.add(ip.getPanel());
			this.panel.add(gp.getPanel());
			this.panel.add(tp.getPanel());
//			this.panel.add(this.getRunTests());
		}
		return panel;
	}

	public TextInputPanel getTip() {
		if (this.tip == null) {
			this.tip = new TextInputPanel(this);
		}
		return tip;
	}

	public InfoPanel getIp() {
		if (this.ip == null) {
			this.ip = new InfoPanel(this);
		}
		return ip;
	}

	public GeneratePanel getGp() {
		if (this.gp == null) {
			this.gp = new GeneratePanel(this);
		}
		return gp;
	}

	public TestsPanel getTp() {
		if (this.tp == null) {
			this.tp = new TestsPanel(this);
		}
		return tp;
	}

	public FileInputPanel getFip() {
		if (this.fip == null) {
			this.fip = new FileInputPanel(this);
		}
		return fip;
	}

	public DataGeneratePanel getDgp() {
		if (this.dgp == null) {
			this.dgp = new DataGeneratePanel(this);
		}
		return dgp;
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
	
	public JButton getRunTests() {
		if (this.runTests == null) {
			this.runTests = new JButton("Run Tests");
			this.runTests.setBounds(10, 460, 570, 30);
			this.runTests.addActionListener(new InputActionListener(this));
		}
		return runTests;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getPanelType() {
		return panelType;
	}

}
