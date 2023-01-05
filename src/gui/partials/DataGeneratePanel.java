/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.partials;

import actions.InputActionListener;
import gui.InputWindow;
import java.security.Security;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author kenanince
 */
public class DataGeneratePanel {

	private JPanel panel;

	private JLabel sampleSizeLabel;
	private JTextField sampleSize;

	private JCheckBox generateHash;

	private JButton generate;

	private JTextField path;

	private JLabel ratioLabel;
	private JLabel percentage;
	private JTextField ratio;

	private JLabel algorithmLabel;
	private JComboBox algorithm;

	private InputWindow iw;
	
	int yStart = 250;

	public DataGeneratePanel(InputWindow iw) {
		this.iw = iw;
	}

	public JPanel getPanel() {
		if (this.panel == null) {
			this.panel = new JPanel(null);
			this.panel.setSize(600, 780);
			this.getPanel().add(this.getGenerateHash());
			this.getPanel().add(this.getSampleSize());
			this.getPanel().add(this.getSampleSizeLabel());
			this.getPanel().add(this.getGenerate());
			this.getPanel().add(this.getPath());
			this.getPanel().add(this.getRatio());
			this.getPanel().add(this.getRatioLabel());
			this.getPanel().add(this.getPercentage());
			this.getPanel().add(this.getAlgorithmLabel());
			this.getPanel().add(this.getAlgorithm());

//			for (JCheckBox testSelection : this.getTestSelections()) {
//				this.getPanel().add(testSelection);
//			}
		}
		return panel;
	}

	public JLabel getSampleSizeLabel() {
		if (this.sampleSizeLabel == null) {
			this.sampleSizeLabel = new JLabel("Sample Count: ");
			this.sampleSizeLabel.setBounds(10, yStart + 290, 100, 30);
		}
		return sampleSizeLabel;
	}

	public JTextField getSampleSize() {
		if (this.sampleSize == null) {
			this.sampleSize = new JTextField("100");
			this.sampleSize.setBounds(120, yStart + 290, 100, 30);
		}
		return sampleSize;
	}

	public JLabel getRatioLabel() {
		if (this.ratioLabel == null) {
			this.ratioLabel = new JLabel("T/F ratio: ");
			this.ratioLabel.setBounds(240, yStart + 290, 80, 30);
		}
		return ratioLabel;
	}

	public JTextField getRatio() {
		if (this.ratio == null) {
			this.ratio = new JTextField("50");
			this.ratio.setBounds(330, yStart + 290, 40, 30);
		}
		return ratio;
	}

	public JLabel getPercentage() {
		if (this.percentage == null) {
			this.percentage = new JLabel("% ");
			this.percentage.setBounds(370, yStart + 290, 100, 30);
		}
		return percentage;
	}

	public JCheckBox getGenerateHash() {
		if (this.generateHash == null) {
			this.generateHash = new JCheckBox("Generate Hash");
			this.generateHash.setBounds(470, yStart + 290, 160, 30);
		}
		return generateHash;
	}

	public JButton getGenerate() {
		if (this.generate == null) {
			this.generate = new JButton("GENERATE");
			this.generate.setBounds(10, yStart + 380, 580, 30);
			this.generate.addActionListener(new InputActionListener(iw));

		}
		return generate;
	}

	public JTextField getPath() {
		if (this.path == null) {
			this.path = new JTextField(System.getProperty("user.dir") + "/");
			this.path.setBorder(BorderFactory.createTitledBorder("Data Save Directory"));
			this.path.setBounds(10, yStart + 330, 580, 40);
		}
		return path;
	}

	public JLabel getAlgorithmLabel() {

		if (this.algorithmLabel == null) {
			this.algorithmLabel = new JLabel("Generation Algorithm: ");
			this.algorithmLabel.setBounds(10, yStart + 250, 160, 30);
		}
		return algorithmLabel;
	}

	public JComboBox getAlgorithm() {
		if (this.algorithm == null) {

			Set<String> osa = Security.getAlgorithms("SecureRandom");
			String[] algorithms = new String[osa.size()];
			osa.toArray(algorithms);

			this.algorithm = new JComboBox(algorithms);
			this.algorithm.setBounds(160, yStart + 250, 180, 30);
		}
		return algorithm;
	}
}
