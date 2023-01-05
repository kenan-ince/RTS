/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

import Algorithms.Trivium;
import assets.Definition;
import assets.RNG;
import assets.RandomHash;
import gui.InputWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;
import randomtestsuite.TestSuite;
import results.Result;

/**
 *
 * @author kenanince
 */
public class InputActionListener extends TestRunner implements ActionListener {

	public InputActionListener(InputWindow iw) {
		super(iw);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == iw.getRunTests()) {
			if (iw.getPanelType().equals("TextInput")) {
				iw.setInput(iw.getTip().getInput().getText());
			}
			String input = iw.getInput();
			super.executeTest(iw, input);
		}

		if (e.getSource() == iw.getGp().getGenerateButton()) {
			try {
				int sai = iw.getGp().getAlgorithm().getSelectedIndex();
				int lenght = Integer.valueOf(iw.getGp().getGenSizeInput().getText());

				if (lenght <= 0) {
					int input = JOptionPane.showConfirmDialog(null, "In order to generate random sequence, please input a value", "Lenght Error!", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
				} else {
					if (iw.getGp().getAlgorithm().getItemAt(sai).toString() == "Trivium") {
						Trivium t = new Trivium(lenght); // kaç bit üreteceği
						int[] bits = t.generate();
						String seq = "";
						for (int i = 0; i < bits.length; i++) {
							seq += bits[i];
						}
						iw.getTip().getInput().setText(seq);
					} else {
						SecureRandom sr = SecureRandom.getInstance(iw.getGp().getAlgorithm().getItemAt(sai).toString());

						RNG rg = new RNG(lenght);
						rg.generate(sr);

						iw.getTip().getInput().setText(rg.getSequence());
					}
				}

			} catch (NoSuchAlgorithmException ex) {
				Logger.getLogger(InputActionListener.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		if (e.getSource() == iw.getFip().getOpen()) {
			JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

			// invoke the showsOpenDialog function to show the save dialog
			int r = j.showOpenDialog(null);

			// if the user selects a file
			if (r == JFileChooser.APPROVE_OPTION) {
				// set the label to the path of the selected file
				iw.getFip().getFileLabel().setText(j.getSelectedFile().getAbsolutePath());
			} // if the user cancelled the operation
			else {
				iw.getFip().getFileLabel().setText("the user cancelled the operation");
			}
		}

		if (e.getSource() == iw.getFip().getImportButton()) {

			Path filePath = Path.of(iw.getFip().getFileLabel().getText());
			try {
				String str = Files.readString(filePath);
				String divider = iw.getFip().getDivider().getText();

				String[] bits = str.split(divider);

				str = "";
				for (String s : bits) {
					str += s;
				}

				iw.setInput(str);
				iw.getIp().getBitLenValue().setText(String.valueOf(str.length()));
			} catch (IOException ex) {
				Logger.getLogger(InputActionListener.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		if (e.getSource() == iw.getDgp().getGenerate()) {

			boolean hash = iw.getDgp().getGenerateHash().isSelected();

			try {
				List<String> testList = new ArrayList<>();

				for (JCheckBox testSelection : iw.getTp().getTestSelections()) {
					if (testSelection.isSelected()) {
						testList.add(testSelection.getText());
					}
				}

				int sampleCount = Integer.parseInt(iw.getDgp().getSampleSize().getText());
				int sampleRatio = Integer.parseInt(iw.getDgp().getRatio().getText());

				String path = iw.getDgp().getPath().getText();

				FileWriter dataWriter;

				int maxTrueCount = sampleCount * sampleRatio / 100;
				int maxFalseCount = sampleCount - maxTrueCount;

				SecureRandom sr = SecureRandom.getInstance(iw.getDgp().getAlgorithm().getItemAt(iw.getDgp().getAlgorithm().getSelectedIndex()).toString());
				TestSuite ts = new TestSuite();

				File directory = new File(path + "Data");

				if (!directory.exists()) {
					directory.mkdir();
				}

				for (String testName : testList) {

					int trueCount = 0;
					int falseCount = 0;

					directory = new File(path + "Data" + File.separator + testName);

					if (!directory.exists()) {
						directory.mkdir();
					}

					String filePath = path + "Data" + File.separator + testName + File.separator + sampleCount + ".csv";

					File file = new File(filePath);

					Files.deleteIfExists(file.toPath());
					file.createNewFile();

					dataWriter = new FileWriter(filePath);

					int len = Definition.testMap.get(testName);

					String dataString;

					int totalSample = 0;

					while (totalSample < sampleCount) {

						RNG rg = new RNG(len);
						rg.generate(sr);
						String tmp = rg.getSequence();

						int[] bits = new int[tmp.length()];
						for (int j = 0; j < tmp.length(); j++) {
							bits[j] = tmp.charAt(j) - 48;
						}

						ts.setBits(bits);

						if (ts.isPassed(testName, iw)) {
							if (trueCount < maxTrueCount) {
								dataString = Arrays.toString(bits);
								dataString = dataString.substring(1, dataString.length() - 1);
								dataString += ", 1\n";
								dataWriter.append(dataString);
								trueCount++;
								totalSample++;
							}
						} else {
							if (falseCount < maxFalseCount) {
								dataString = Arrays.toString(bits);
								dataString = dataString.substring(1, dataString.length() - 1);
								dataString += ", 0\n";
								dataWriter.append(dataString);
								falseCount++;
								totalSample++;
							}
						}
						dataWriter.flush();
					}
					dataWriter.close();
					
					if (hash) {
						RandomHash.createHashFile(filePath);
					}
				}
			} catch (NoSuchAlgorithmException | IOException ex) {
				Logger.getLogger(InputActionListener.class.getName()).log(Level.SEVERE, null, ex);
			}

		}
	}
}
