/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.partials;

import actions.InputActionListener;
import gui.InputWindow;
import java.security.Security;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author kenanince
 */
public class GeneratePanel {

	private JPanel panel;

	private JLabel generateLabel;
	private JButton generateButton;
	private JLabel genSizeLabel;
	private JTextField genSizeInput;
	private JComboBox algorithm;
	private JLabel algorithmLabel;
	
	private final InputWindow iw;

	public GeneratePanel(InputWindow iw) {
		this.iw = iw;
	}

	public JPanel getPanel() {
		if (this.panel == null) {
			this.panel = new JPanel(null);
			this.panel.setSize(600, 30);
			this.panel.add(this.getGenerateLabel());
			this.panel.add(this.getAlgorithmLabel());
			this.panel.add(this.getGenerateButton());
			this.panel.add(this.getAlgorithm());
			this.panel.add(this.getGenSizeLabel());
			this.panel.add(this.getGenSizeInput());
		}
		return panel;
	}

	public JLabel getGenerateLabel() {
		if (this.generateLabel == null) {
			this.generateLabel = new JLabel("Generate");
			this.generateLabel.setBounds(10, 0, 60, 30);
		}
		return generateLabel;
	}

	public JLabel getAlgorithmLabel() {
		if (this.algorithmLabel == null) {
			this.algorithmLabel = new JLabel("algorithm");
			this.algorithmLabel.setBounds(430, 0, 80, 30);
		}
		return algorithmLabel;
	}

	public JButton getGenerateButton() {
		if (this.generateButton == null) {
			this.generateButton = new JButton("GO");
			this.generateButton.setBounds(510, 0, 70, 30);
			this.generateButton.addActionListener(new InputActionListener(this.iw));
		}
		return generateButton;
	}

	public JComboBox getAlgorithm() {
		if (this.algorithm == null) {

			Set<String> osa = Security.getAlgorithms("SecureRandom");
			String[] algorithms = new String[osa.size()+1];
			osa.toArray(algorithms);
			algorithms[algorithms.length-1] = "Trivium";

			this.algorithm = new JComboBox(algorithms);
			this.algorithm.setBounds(240, 0, 180, 30);
		}
		return algorithm;
	}

	public JLabel getGenSizeLabel() {
		if (genSizeLabel == null) {
			this.genSizeLabel = new JLabel("bits using");
			this.genSizeLabel.setBounds(150, 0, 90, 30);
			this.genSizeLabel.setBorder(new EmptyBorder(0, 2, 0, 0));
		}
		return genSizeLabel;
	}

	public JTextField getGenSizeInput() {
		if (this.genSizeInput == null) {
			this.genSizeInput = new JTextField("0");
			this.genSizeInput.setBounds(70, 0, 80, 30);
			this.genSizeInput.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return genSizeInput;
	}

}
