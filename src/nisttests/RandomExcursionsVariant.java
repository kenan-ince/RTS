/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nisttests;

import assets.Definition;
import org.apache.commons.math3.special.Erf;
import org.apache.commons.math3.util.FastMath;
import results.Chi2Result;
import results.Result;

/**
 *
 * @author kenanince
 */
public class RandomExcursionsVariant extends AbstractTest {

	private int i, p, J, x, constraint, count;
	private int[] S_k;
	private int[] stateX = {-9, -8, -7, -6, -5, -4, -3, -2, -1, 1, 2, 3, 4, 5, 6, 7, 8, 9};
	private double p_value;

	public RandomExcursionsVariant(int[] bits) {
		super(bits);
		this.S_k = new int[bits.length];
	}

	@Override
	public Result[] runTest() {

		J = 0;
		S_k[0] = 2 * (int) bits[0] - 1;
		for (i = 1; i < bits.length; i++) {
			S_k[i] = S_k[i - 1] + 2 * bits[i] - 1;
			if (S_k[i] == 0) {
				J++;
			}
		}
		if (S_k[bits.length - 1] != 0) {
			J++;
		}

		constraint = (int) FastMath.max(0.005 * FastMath.pow(bits.length, 0.5), 500);
		if (J > constraint) {
			Result[] mbr = new Result[18];
			for (p = 0; p <= 17; p++) {
				x = stateX[p];
				count = 0;
				for (i = 0; i < bits.length; i++) {
					if (S_k[i] == x) {
						count++;
					}
				}
				p_value = Erf.erfc(FastMath.abs(count - J) / (FastMath.sqrt(2.0 * J * (4.0 * FastMath.abs(x) - 2))));

				mbr[p] = new Result("Random Excursions Variant", p_value, this.getComputationalInformation(), this);

			}

			return mbr;
		}

		Result mbr = new Result("Random Excursions Variant", p_value, this.getComputationalInformation(), this);
		return new Result[]{mbr};
	}

	@Override
	public String getComputationalInformation() {
		String report = "RANDOM EXCURSIONS VARIANT TEST\n";
		report += "------------------------------------------------------\n";
		if (J < constraint) {
			report += "WARNING:  TEST NOT APPLICABLE.  THERE ARE AN INSUFFICIENT NUMBER OF CYCLES.\n";
			return report;
		}

		report += String.format("(a) Number Of Cycles (J) = %d\n", J);
		report += String.format("(b) Sequence Length (n)  = %d\n", bits.length);
		report += "------------------------------------------------------\n";

		for (int i = 0; i < 18; i++) {

			if (p_value < 0.0 || p_value >= 1.0) {
				report += "(b) WARNING: P_VALUE IS OUT OF RANGE.\n";
			}
			report += String.format("%s\t\t", p_value < Definition.ALPHA ? "FAILURE" : "SUCCESS");
			report += String.format("(x = %2d) Total visits = %4d; p-value = %f\n", x, count, p_value);

		}

		return report;
	}

	@Override
	public int getInputSizeRecommendation() {
		return 1000000;
	}

}
