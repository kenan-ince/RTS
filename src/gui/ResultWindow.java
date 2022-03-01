/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import actions.ResultActions;
import assets.Definition;
import java.awt.Color;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import results.Result;

/**
 *
 * @author knn
 */
public class ResultWindow {

	private JFrame frame;
	private JPanel panel;
	private JPanel testNamesPanel;
	private JPanel detailButtonPanel;
	private JLabel[] testList;
	private JLabel[] testResults;
	private JButton[] testDetails;

	private JButton ss;

	private final List<Result[]> rList;
	private final int sequenceLength;

	public ResultWindow(List<Result[]> rList, int l) {
		this.rList = rList;
		this.sequenceLength = l;
	}

	public void build() {
		this.testList = new JLabel[this.rList.size()];
		this.testResults = new JLabel[this.rList.size()];
		this.testDetails = new JButton[this.rList.size()];

		int i = 0;
		for (Result[] rr : this.rList) {
			this.singleResultRow(i, rr, sequenceLength);
			i++;
		}
		this.getFrame();
	}

	public JFrame getFrame() {
		if (this.frame == null) {
			this.frame = new JFrame("Test Results");
			this.frame.setSize(515, 695);
			this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.frame.setLocationRelativeTo(null);
			
			this.getPanel().add(this.getSs());
			this.getPanel().add(this.getTestNamesPanel());
			this.getPanel().add(this.getDetailButtonPanel());
			
			this.frame.setContentPane(this.getPanel());
			this.frame.setVisible(true);
		}
		return frame;
	}

	public JButton getSs() {
		if (this.ss == null) {
			this.ss = new JButton("Take Screen Shot");
			this.ss.setBounds(10, 10, 200, 30);
			this.ss.addActionListener(new ResultActions(this));
		}
		return ss;
	}

	public JPanel getTestNamesPanel() {
		if (this.testNamesPanel == null) {
			this.testNamesPanel = new JPanel(null);
			this.testNamesPanel.setBounds(0, 40, 410, 620);
		}
		return testNamesPanel;
	}

	public JPanel getDetailButtonPanel() {
		if (this.detailButtonPanel == null) {
			this.detailButtonPanel = new JPanel(null);
			this.detailButtonPanel.setBounds(400, 40, 100, 620);
		}
		return detailButtonPanel;
	}

	public JPanel getPanel() {
		if (this.panel == null) {
			this.panel = new JPanel(null);
		}
		return panel;
	}

	public void singleResultRow(int i, Result[] r, int l) {
		if (r[0] != null) {
			this.testList[i] = new JLabel(r[0].testName);
			this.testList[i].setBounds(10, 10+(i * 40), 270, 35);
			this.testList[i].setBorder(new EmptyBorder(0, 10, 0, 0));
			this.testList[i].setBackground(Color.LIGHT_GRAY);
			this.testList[i].setOpaque(true);
			this.getTestNamesPanel().add(this.testList[i]);

			this.testDetails[i] = new JButton("Detail");
			this.testDetails[i].setBounds(10, 10+(i * 40), 80, 35);
			this.testDetails[i].setOpaque(true);
			this.testDetails[i].setBackground(Color.WHITE);

			if (r.length > 1) {

				String p_values = "<html>";

				for (int k = 0; k < r.length; k++) {
					p_values += String.format("%1.6f", r[k].p_value) + "<br />";
				}
				p_values += "</html>";

				this.testResults[i] = new JLabel(p_values);
				this.testResults[i].setBounds(290, 10+(i * 40), 110, 35);
				this.testResults[i].setOpaque(true);
				this.testResults[i].setBorder(new EmptyBorder(0, 10, 0, 0));
				if (r[0].passed) {
					if (r[0].testClass.getInputSizeRecommendation() > l) {
						this.testResults[i].setBackground(Color.ORANGE);
					} else {
						this.testResults[i].setBackground(Color.GREEN);
					}
				} else {
					this.testResults[i].setBackground(Color.RED);
					this.testResults[i].setForeground(Color.WHITE);
				}
				this.getTestNamesPanel().add(this.testResults[i]);

				this.testDetails[i].addActionListener((var e) -> {
					ComputationalWindow cw = new ComputationalWindow(r[0].description + "\n" + r[1].description);
				});

			} else {

				this.testResults[i] = new JLabel(String.format("%1.6f", r[0].p_value));
				this.testResults[i].setBounds(290, 10+(i * 40), 110, 35);
				this.testResults[i].setOpaque(true);
				this.testResults[i].setBorder(new EmptyBorder(0, 10, 0, 0));
				if (r[0].passed) {
					if (r[0].testClass.getInputSizeRecommendation() > l) {
						this.testResults[i].setBackground(Color.ORANGE);
					} else {
						this.testResults[i].setBackground(Color.GREEN);
					}
				} else {
					this.testResults[i].setBackground(Color.RED);
					this.testResults[i].setForeground(Color.WHITE);
				}
				this.getTestNamesPanel().add(this.testResults[i]);

				this.testDetails[i].addActionListener((var e) -> {
					ComputationalWindow cw = new ComputationalWindow(r[0].description);
				});
			}

			this.getDetailButtonPanel().add(this.testDetails[i]);
		}
		this.getPanel().repaint();
	}
}
