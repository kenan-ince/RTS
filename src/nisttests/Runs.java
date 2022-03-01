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
public class Runs extends AbstractTest {

	private int S, k, V;
	private double pi, erfc_arg, p_value;

	public Runs(int[] bits) {
		super(bits);
	}

	@Override
	public Result[] runTest() {

		Result mbr;
		S = 0;
		for (k = 0; k < bits.length; k++) {
			if (bits[k] == 1) {
				S++;
			}
		}
		pi = (double) S / (double) bits.length;

		if (FastMath.abs(pi - 0.5) > (2.0 / FastMath.sqrt(bits.length))) {
			String report = "RUNS TEST\n";
			report += "---------------------------------------\n";
			report += String.format("ESTIMATOR CRITERIA NOT MET! PI = %f", pi);
			p_value = 0.0;
			mbr = new Result("Runs", p_value, report, this);

		} else {

			V = 1;
			for (k = 1; k < bits.length; k++) {
				if (bits[k] != bits[k - 1]) {
					V++;
				}
			}

			erfc_arg = FastMath.abs(V - 2.0 * bits.length * pi * (1 - pi)) / (2.0 * pi * (1 - pi) * FastMath.sqrt(2 * bits.length));
			p_value = Erf.erfc(erfc_arg);
			mbr = new Result("Runs", p_value, this.getComputationalInformation(), this);

		}
		return new Result[]{mbr};
	}

	@Override
	public String getComputationalInformation() {
		String report = "RUNS TEST\n";
		report += "------------------------------------------\n";
		if (p_value < Definition.ALPHA) {
			report += "PI ESTIMATOR CRITERIA NOT MET!";
			return report;
		}
		report += String.format("(a) Pi                        = %f\n", pi);
		report += String.format("(b) V_n_obs (Total # of runs) = %d\n", V);
		report += "(c)      V_n_obs - 2 n pi (1-pi)\n";
		report += String.format("     -----------------------   = %f\n", erfc_arg);
		report += "           2 sqrt(2n) pi (1-pi)\n";
		report += "------------------------------------------\n";
		if (p_value < 0 || p_value > 1) {
			report += "WARNING:  P_VALUE IS OUT OF RANGE.\n";
		}

		report += String.format("%s\t\tp_value = %f\n\n", p_value > Definition.ALPHA ? "SUCCESS" : "FAILURE", p_value);
		return report;
	}

	@Override
	public int getInputSizeRecommendation() {
		return 100;
	}

}
