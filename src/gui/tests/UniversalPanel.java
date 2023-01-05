/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.tests;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.apache.commons.math3.util.FastMath;

/**
 *
 * @author kenanince
 */
public class UniversalPanel implements TestPanel {

	private JPanel panel;
	private JLabel ll;
	private JLabel ql;
	private JSpinner L;
	private JTextField Q;

	public UniversalPanel() {
	}

	@Override
	public JPanel getPanel() {
		if (this.panel == null) {
			this.panel = new JPanel();
			this.panel.setSize(250, 30);
			this.panel.add(this.getLl());
			this.panel.add(this.getQl());
			this.panel.add(this.getL());
			this.panel.add(this.getQ());
			this.panel.setLayout(null);
		}
		return panel;
	}

	public JLabel getLl() {
		if (this.ll == null) {
			this.ll = new JLabel("L : ");
			this.ll.setBounds(0, 0, 20, 30);
			this.ll.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return ll;
	}

	public JLabel getQl() {
		if (this.ql == null) {
			this.ql = new JLabel("Q : ");
			this.ql.setBounds(90, 0, 20, 30);
			this.ql.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return ql;
	}

	public JSpinner getL() {
		JTextField q = this.getQ();
		if (this.L == null) {
			SpinnerModel sm = new SpinnerNumberModel(5, //initial value  
					5, //minimum value  
					16, //maximum value  
					1); //step  
			this.L = new JSpinner(sm);
			this.L.setBounds(20, 0, 50, 25);
			this.L.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					JSpinner js = (JSpinner) e.getSource();
					int l = (int) js.getValue();
					int p = (int) FastMath.pow(2, (double) l);
					int[] T = new int[p];

					int Q = 10 * p;
					q.setText(String.valueOf(Q));
				}
			});

		}
		return L;
	}

	public JTextField getQ() {
		if (this.Q == null) {
			this.Q = new JTextField("320");
			this.Q.setBounds(110, 0, 70, 25);
			this.Q.setEditable(false);
			this.Q.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return Q;
	}

}
