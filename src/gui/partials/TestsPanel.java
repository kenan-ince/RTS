/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.partials;

import actions.InputActionListener;
import actions.InputChangeListener;
import assets.Definition;
import gui.AttributePanelCreator;
import gui.InputWindow;
import gui.tests.BlockFrequencyPanel;
import gui.tests.TestPanel;
import java.util.HashMap;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EtchedBorder;
import javax.swing.text.DefaultEditorKit;

/**
 *
 * @author kenanince
 */
public class TestsPanel {

	private JPanel panel;

	private JCheckBox[] testSelections;
	private JCheckBox allTests;

	private JPopupMenu warning;

	private BlockFrequencyPanel bf;
	private HashMap<String, TestPanel> testPanels;
	
	private final InputWindow iw;

	public TestsPanel(InputWindow iw) {
		this.iw = iw;
		this.testPanels = new HashMap<>();
	}

	public JPanel getPanel() {
		if (this.panel == null) {
			this.panel = new JPanel(null);
			this.panel.setSize(600, 490);

			for (JCheckBox testSelection : this.getTestSelections()) {
				this.getPanel().add(testSelection);
			}
			this.panel.add(this.getAllTests());
		}
		return panel;
	}

	public HashMap<String, TestPanel> getTestPanels() {
		return testPanels;
	}

	public JCheckBox[] getTestSelections() {
		if (this.testSelections == null) {
			this.testSelections = new JCheckBox[Definition.tests.length];
			AttributePanelCreator apc = new AttributePanelCreator();
			for (int i = 0; i < Definition.tests.length; i++) {
				this.testSelections[i] = new JCheckBox(Definition.tests[i]);
				this.testSelections[i].setBounds(60, (i * 30), 250, 30);
				TestPanel tp = apc.getPanel(Definition.tests[i]);
				if (tp != null) {
					tp.getPanel().setBounds(310, (i * 30), 250, 30);
					this.testPanels.put(Definition.tests[i], tp);
					this.getPanel().add(tp.getPanel());
				}
			}
		}
		return this.testSelections;
	}

	public JCheckBox getAllTests() {
		if (this.allTests == null) {
			this.allTests = new JCheckBox("All");
			this.allTests.setBounds(10, 0, 50, 30);
			this.allTests.addChangeListener(new InputChangeListener(iw));
			this.allTests.setBorderPainted(true);
			this.allTests.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
			this.allTests.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		}
		return allTests;
	}

	

	public JPopupMenu getWarning() {
		if (this.warning == null) {

			Action[] textActions = {new DefaultEditorKit.CutAction(), new DefaultEditorKit.CopyAction(), new DefaultEditorKit.PasteAction(),};

			this.warning = new JPopupMenu();
			for (Action textAction : textActions) {
				this.warning.add(new JMenuItem(textAction));
			}
		}
		return warning;
	}
}
