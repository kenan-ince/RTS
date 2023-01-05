/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package randomtestsuite;

import gui.WelcomeWindow;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author kenanince
 */
public class RandomTestSuite {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {

		try {
			String className = getLookAndFeelClassName("Metal");
			UIManager.setLookAndFeel(className);

			WelcomeWindow wf = new WelcomeWindow();
			wf.build();

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
			Logger.getLogger(RandomTestSuite.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static String getLookAndFeelClassName(String nameSnippet) {
		LookAndFeelInfo[] plafs = UIManager.getInstalledLookAndFeels();
		for (LookAndFeelInfo info : plafs) {
			if (info.getName().contains(nameSnippet)) {
				return info.getClassName();
			}
		}
		return null;
	}

}
