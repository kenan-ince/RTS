/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

import gui.InputWindow;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author kenanince
 */
public class InputPopupListener extends MouseAdapter {

	private InputWindow iw;

	public InputPopupListener(InputWindow iw) {
		this.iw = iw;
	}

	public void mousePressed(MouseEvent e) {
		maybeShowPopup(e);
	}

	public void mouseReleased(MouseEvent e) {
		maybeShowPopup(e);
	}

	private void maybeShowPopup(MouseEvent e) {
		if (e.isPopupTrigger()) {
			iw.getPopup().show(e.getComponent(),
					e.getX(), e.getY());
		}
	}
}
