/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.tests;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

/**
 *
 * @author kenanince
 */
public class NonOverlappingPanel implements TestPanel{
	
	private JPanel panel;
	private JLabel label;
	private JSpinner value;

	public NonOverlappingPanel() {
	}

	@Override
	public JPanel getPanel() {
		if ( this.panel == null ) {
			this.panel = new JPanel();
			this.panel.setSize(250, 30);
			this.panel.add(this.getLabel());
			this.panel.add(this.getValue());
			this.panel.setLayout(null);
		}
		return panel;
	}

	public JLabel getLabel() {
		if ( this.label == null ) {
			this.label = new JLabel("M (Template Length): ");
			this.label.setBounds(0, 0, 130, 30);
			this.label.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return label;
	}
	
	public JSpinner getValue() {
		if (this.value == null) {
			SpinnerModel sm = new SpinnerNumberModel(8, //initial value  
							2, //minimum value  
							10, //maximum value  
							1); //step  
			this.value = new JSpinner(sm);
            this.value .setBounds(140,0,50,25);   
			
		}
		return value;
	}
}
