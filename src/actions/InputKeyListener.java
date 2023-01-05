/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

import gui.InputWindow;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author knn
 */
public class InputKeyListener extends TestRunner implements KeyListener {

	public InputKeyListener(InputWindow iw) {
		super(iw);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		int code = e.getKeyCode();
		char c = e.getKeyChar();

		if (e.getSource() == iw.getTip().getInput()) {
			if (!(c == '0' || c == '1' || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE || c == KeyEvent.VK_COPY || c == KeyEvent.VK_PASTE || c == KeyEvent.VK_LEFT || c == KeyEvent.VK_RIGHT || c == KeyEvent.VK_ENTER)) {
				e.consume();
			}
		}

		if (e.getSource() == iw.getTip().getInput()) {
			if (!(code >= 0 && code <= 9)) {
				e.consume();
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getSource() == iw.getTip().getInput()) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				String input = iw.getInput();
				super.executeTest(iw, input);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

}
