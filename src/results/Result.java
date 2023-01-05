/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package results;

import assets.Definition;
import nisttests.Test;

/**
 *
 * @author kenanince
 */
public class Result {

	public double p_value;
	public String description;
	public boolean passed;
	public String testName;
	public Test testClass;

	public Result(String testName, double p_value, String description, Test t) {
		this.p_value = p_value;
		this.description = description;
		this.passed = p_value >= Definition.ALPHA;
		this.testName = testName;
		testClass = t;
	}

	@Override
	public String toString() {
		return "Result{" + "p_value=" + p_value + ", passed=" + passed + ", testName=" + testName + '}';
	}

}
