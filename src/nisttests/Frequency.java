/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nisttests;

import assets.Definition;
import org.apache.commons.math3.special.Erf;
import org.apache.commons.math3.util.FastMath;
import results.Result;

/**
 *
 * @author kenanince
 */
public class Frequency extends AbstractTest {

	private double f, s_obs, p_value, sum;

	public Frequency(int[] bits) {
		super(bits);
	}

	@Override
	public Result[] runTest() {
		
		int i;

		sum = 0.0;
		for (i = 0; i < bits.length; i++) {
			sum += 2 * bits[i] - 1;
		}
		s_obs = FastMath.abs(sum) / FastMath.sqrt(bits.length);
		f = s_obs / Definition.sqrt2;
		p_value = Erf.erfc(f);

		Result mbr = new Result("Frequency", p_value, this.getComputationalInformation(), this);

		return new Result[]{mbr};
	}

	@Override
	public String getComputationalInformation() {
		String report = "FREQUENCY TEST \n";
		report += "-------------------------------------------------\n";
		report += String.format("(a) The nth partial sum = %f\n", this.sum);
		report += String.format("(b) S_n/n               = %f\n", (double) this.sum / bits.length);
		report += "-------------------------------------------------\n";

		report += String.format("%s\t\tp_value = %f\n\n", this.p_value > Definition.ALPHA ? "SUCCESS" : "FAILURE", this.p_value);

		return report;
	}

	@Override
	public int getInputSizeRecommendation() {
		return 100;
	}

}
