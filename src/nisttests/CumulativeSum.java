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
public class CumulativeSum extends AbstractTest {

	private int S, sup, inf, z = 0, zrev = 0, k;
	private double sum1, sum2, p_value;
	private String f;

	public CumulativeSum(int[] bits) {
		super(bits);
		f = "FORWARD";
	}

	@Override
	public Result[] runTest() {

		S = 0;
		sup = 0;
		inf = 0;
		for (k = 0; k < bits.length; k++) {
			if (bits[k] == 1) {
				S++;
			} else {
				S--;
			}

			if (S > sup) {
				sup++;
			}
			if (S < inf) {
				inf--;
			}
			z = (sup > -inf) ? sup : -inf;
			zrev = (sup - S > S - inf) ? sup - S : S - inf;
		}

		// forward
		sum1 = 0.0;
		for (k = (-bits.length / z + 1) / 4; k <= (bits.length / z - 1) / 4; k++) {
			sum1 += this.cephesNormal(((4 * k + 1) * z) / FastMath.sqrt(bits.length));
			sum1 -= this.cephesNormal(((4 * k - 1) * z) / FastMath.sqrt(bits.length));
		}
		sum2 = 0.0;
		for (k = (-bits.length / z - 3) / 4; k <= (bits.length / z - 1) / 4; k++) {
			sum2 += this.cephesNormal(((4 * k + 3) * z) / FastMath.sqrt(bits.length));
			sum2 -= this.cephesNormal(((4 * k + 1) * z) / FastMath.sqrt(bits.length));
		}

		p_value = 1.0 - sum1 + sum2;

		Result mbr1 = new Result("Cumulative Sum", p_value, this.getComputationalInformation(), this);

		// backwards
		sum1 = 0.0;
		for (k = (-bits.length / zrev + 1) / 4; k <= (bits.length / zrev - 1) / 4; k++) {
			sum1 += this.cephesNormal(((4 * k + 1) * zrev) / FastMath.sqrt(bits.length));
			sum1 -= this.cephesNormal(((4 * k - 1) * zrev) / FastMath.sqrt(bits.length));
		}
		sum2 = 0.0;
		for (k = (-bits.length / zrev - 3) / 4; k <= (bits.length / zrev - 1) / 4; k++) {
			sum2 += this.cephesNormal(((4 * k + 3) * zrev) / FastMath.sqrt(bits.length));
			sum2 -= this.cephesNormal(((4 * k + 1) * zrev) / FastMath.sqrt(bits.length));
		}
		p_value = 1.0 - sum1 + sum2;
		f = "BACKWARD";

		Result mbr2 = new Result("Cumulative Sum", p_value, this.getComputationalInformation(), this);

		return new Result[]{mbr1, mbr2};
	}

	@Override
	public String getComputationalInformation() {
		String report = "CUMULATIVE SUMS (" + f + ") TEST\n";
		report += "-------------------------------------------\n";

		report += String.format("(a) The maximum partial sum = %d\n", z);
		report += "-------------------------------------------\n";
		if (p_value < 0 || p_value > 1) {
			report += String.format("WARNING:  P_VALUE IS OUT OF RANGE\n");
		}

		report += String.format("%s\t\tp_value = %f\n\n", p_value < Definition.ALPHA ? "FAILURE" : "SUCCESS", p_value);

		return report;
	}

	@Override
	public int getInputSizeRecommendation() {
		return 100;
	}

	public double cephesNormal(double x) {
		double arg, result, sqrt2 = 1.414213562373095048801688724209698078569672;

		if (x > 0) {
			arg = x / sqrt2;
			result = 0.5 * (1 + Erf.erf(arg));
		} else {
			arg = -x / sqrt2;
			result = 0.5 * (1 - Erf.erf(arg));
		}

		return (result);
	}

}
