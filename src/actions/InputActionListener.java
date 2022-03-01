/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

import assets.RNG;
import gui.InputWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

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

        if (e.getSource() == iw.getTest()) {
            super.executeTest();
        }

        if (e.getSource() == iw.getGenerate()) {
            try {
                int sai = iw.getAlgorithm().getSelectedIndex();
                int lenght = Integer.valueOf(iw.getGenSizeInput().getText());

                if (lenght <= 0) {
                    int input = JOptionPane.showConfirmDialog(null, "In order to generate random sequence, please input a value", "Lenght Error!", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
                } else {
                    SecureRandom sr = SecureRandom.getInstance(iw.getAlgorithm().getItemAt(sai).toString());

                    RNG rg = new RNG(lenght);
                    rg.generate(sr);
                    iw.getInput().setText(rg.getSequence());
                }

            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(InputActionListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
