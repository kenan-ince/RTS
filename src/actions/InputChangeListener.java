/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

import gui.InputWindow;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author knn
 */
public class InputChangeListener implements ChangeListener{
	private InputWindow iw;

	public InputChangeListener(InputWindow iw) {
		this.iw = iw;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if ( e.getSource() == iw.getAll() ) {
			if ( iw.getAll().isSelected() ) {
				for (JCheckBox testSelection : iw.getTestSelections()) {
					testSelection.setSelected(true);
				}
			} else {
				for (JCheckBox testSelection : iw.getTestSelections()) {
					testSelection.setSelected(false);
				}
			}
		}
		
		if ( e.getSource() == iw.getInput() ) {
			iw.getBitLenValue().setText(String.valueOf(iw.getInput().getText().length()));
		}
	}
	
}
