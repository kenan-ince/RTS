/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import gui.tests.TestPanel;
import gui.tests.UniversalPanel;
import gui.tests.OverlappingPanel;
import gui.tests.BlockFrequencyPanel;
import gui.tests.NonOverlappingPanel;
import gui.tests.LinearComplexityPanel;
import java.util.HashMap;
import javax.swing.JPanel;

/**
 *
 * @author kenanince
 */
public class AttributePanelCreator {
	
	public TestPanel getPanel(String testName) {
		switch (testName) {
			case "Frequency":
				break;
			case "Block Frequency":
				BlockFrequencyPanel bfp = new BlockFrequencyPanel();
				return bfp;
			case "Runs":
				break;
			case "Longest Run of Ones":
				break;
			case "Matrix Rank":
				break;
			case "Discrete Fourier Transform":
				break;
			case "Non Overlapping Template Matching":
				NonOverlappingPanel nop = new NonOverlappingPanel();
				return nop;
			case "Overlapping Template Matching":
				OverlappingPanel op = new OverlappingPanel();
				return op;
			case "Universal":
				UniversalPanel up = new UniversalPanel();
				return up;
			case "Linear Complexity":
				LinearComplexityPanel lcp = new LinearComplexityPanel();
				return lcp;
			case "Serial":
				break;
			case "Approximate Entropy":
				break;
			case "Cumulative Sum":
				break;
			case "Random Excursions":
				break;
			case "Random Excursions Variant":
				break;

		}
		return null;
	}
	
}
