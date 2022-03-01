/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

import assets.Definition;
import gui.InputWindow;
import gui.ResultWindow;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import randomtestsuite.TestSuite;
import results.Result;

/**
 *
 * @author knn
 */
public abstract class TestRunner {

    protected InputWindow iw;
    protected ResultWindow rw;

    public TestRunner(InputWindow iw) {
        this.iw = iw;
    }

    public void executeTest() {
        String input = iw.getInput().getText();
        input = input.replace("\n", "");
        input = input.replace(" ", "");

        iw.getBitLenValue().setText(String.valueOf(input.length()));

        int[] bits = new int[input.length()];
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '1' || input.charAt(i) == '0') {
                bits[i] = Integer.valueOf(input.charAt(i)) - 48;
            }
        }

        TestSuite ts = new TestSuite(bits);
        List<String> testList = new ArrayList<>();

        for (int i = 0; i < iw.getTestSelections().length; i++) {
            if (iw.getTestSelections()[i].isSelected()) {
                testList.add(iw.getTestSelections()[i].getText());
            }
        }

        if (testList.size() == 0) {
            JOptionPane.showConfirmDialog(null, "Please select at least one test to run!!!", "Test Selection Error!", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
        } else {

            List<Result[]> rList = ts.getSuiteResult(testList);

            ResultWindow rw = new ResultWindow(rList, input.length());
            rw.build();
        }
    }
}
