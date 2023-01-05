/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nisttests;

import results.Result;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 *
 * @author kenanince
 */
public abstract class AbstractTest implements Test {
	
	int[] bits;

	public AbstractTest(int[] bits) {
		this.bits = bits;
	}

	@Override
	public String getDescription() {
		return getClass().getSimpleName();
	}
}
