/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author kenanince
 */
public class ComputationalWindow extends JFrame {

	public ComputationalWindow(String info) {

		super("COMPUTATIONAL INFORMATION");

		JPanel panel = new JPanel(null);

		super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		super.setSize(750, 400);
		super.setLocationRelativeTo(null);
		super.setContentPane(panel);

		JTextArea i = new JTextArea();
		i.setColumns(90);
		i.setLineWrap(true);
		i.setWrapStyleWord(true);
		i.setText(info);
		i.setBorder(new EmptyBorder(10, 10, 10, 10));
		i.setEditable(false);
		i.setFont(new Font("monospaced", Font.PLAIN, 13));

		JScrollPane sp = new JScrollPane(i);
		sp.setBounds(10, 10, 730, 350);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		panel.add(sp);

		super.setVisible(true);
	}

}
