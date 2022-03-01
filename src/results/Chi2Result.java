/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package results;

import nisttests.Test;

/**
 *
 * @author kenanince
 */
public class Chi2Result extends Result {

	public double chi2;
	public int degrees;
	
	public Chi2Result(String testName, double p_value, double chi2, int degrees, String description, Test t) {
		super(testName, p_value, description, t);
		this.chi2 = chi2;
		this.degrees = degrees;
	}
}
