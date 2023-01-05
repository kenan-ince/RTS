/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

import gui.InputWindow;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author knn
 */
public class InputDocumentListener implements DocumentListener {

	private InputWindow iw;

	public InputDocumentListener(InputWindow iw) {
		this.iw = iw;
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		iw.getIp().getBitLenValue().setText(String.valueOf(iw.getTip().getInput().getText().length()));
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		iw.getIp().getBitLenValue().setText(String.valueOf(iw.getTip().getInput().getText().length()));
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		iw.getIp().getBitLenValue().setText(String.valueOf(iw.getTip().getInput().getText().length()));
	}
}
