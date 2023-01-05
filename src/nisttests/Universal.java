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
public class Universal extends AbstractTest {

	private int i, j, p, L, Q, K;
	private double arg, sqrt2, sigma, phi, sum, p_value, c;

	private final double[] expected_value = {0, 0, 0, 0, 0, 0, 5.2177052, 6.1962507, 7.1836656, 8.1764248, 9.1723243, 10.170032, 11.168765, 12.168070, 13.167693, 14.167488, 15.167379};
	private final double[] variance = {0, 0, 0, 0, 0, 0, 2.954, 3.125, 3.238, 3.311, 3.356, 3.384, 3.401, 3.410, 3.416, 3.419, 3.421};

	public Universal(int[] bits, int l) {
		super(bits);
		this.L = l;
	}

	public Universal(int[] bits) {
		super(bits);
		L = 5;
		if (bits.length >= 387840) {
			L = 6;
		}
		if (bits.length >= 904960) {
			L = 7;
		}
		if (bits.length >= 2068480) {
			L = 8;
		}
		if (bits.length >= 4654080) {
			L = 9;
		}
		if (bits.length >= 10342400) {
			L = 10;
		}
		if (bits.length >= 22753280) {
			L = 11;
		}
		if (bits.length >= 49643520) {
			L = 12;
		}
		if (bits.length >= 107560960) {
			L = 13;
		}
		if (bits.length >= 231669760) {
			L = 14;
		}
		if (bits.length >= 496435200) {
			L = 15;
		}
		if (bits.length >= 1059061760) {
			L = 16;
		}
	}

	@Override
	public Result[] runTest() {

		int decRep;

		p = (int) FastMath.pow(2, L);
		int[] T = new int[p];

		Q = 10 * p;
		K = (int) (FastMath.floor(bits.length / L) - (double) Q);

		if ((L < 6) || (L > 16) || ((double) Q < 10 * p)) {
			String report = "UNIVERSAL STATISTICAL TEST\n";
			report += "---------------------------------------------\n";
			report += "ERROR:  L IS OUT OF RANGE.\n";
			report += String.format("-OR- :  Q IS LESS THAN %f.\n", 10 * FastMath.pow(2, L));
			Result mbr = new Result("Universal", p_value, report, this);

			return new Result[]{mbr};
		}

		/* COMPUTE THE EXPECTED:  Formula 16, in Marsaglia's Paper */
		c = 0.7 - 0.8 / (double) L + (4 + 32 / (double) L) * FastMath.pow(K, -3 / (double) L) / 15;
		sigma = c * FastMath.sqrt(variance[L] / (double) K);
		sqrt2 = FastMath.sqrt(2);
		sum = 0.0;
		for (i = 0; i < p; i++) {
			T[i] = 0;
		}

		for (i = 1; i <= Q; i++) {
			/* INITIALIZE TABLE */
			decRep = 0;
			for (j = 0; j < L; j++) {
				decRep += bits[(i - 1) * L + j] * (long) FastMath.pow(2, L - 1 - j);
			}
			T[decRep] = i;
		}

		for (i = Q + 1; i <= Q + K; i++) {
			/* PROCESS BLOCKS */
			decRep = 0;
			for (j = 0; j < L; j++) {
				decRep += bits[(i - 1) * L + j] * (long) FastMath.pow(2, L - 1 - j);
			}
			sum += FastMath.log(i - T[decRep]) / FastMath.log(2);
			T[decRep] = i;
		}

		phi = (double) (sum / (double) K);
		arg = FastMath.abs(phi - expected_value[L]) / (sqrt2 * sigma);

		p_value = Erf.erfc(arg);

		Result mbr = new Result("Universal", p_value, this.getComputationalInformation(), this);

		return new Result[]{mbr};
	}

	@Override
	public String getComputationalInformation() {
		String report = "UNIVERSAL STATISTICAL TEST\n";
		report += "---------------------------------------------\n";
		report += String.format("(a) L         = %d\n", L);
		report += String.format("(b) Q         = %d\n", Q);
		report += String.format("(c) K         = %d\n", K);
		report += String.format("(d) sum       = %f\n", sum);
		report += String.format("(e) sigma     = %f\n", sigma);
		report += String.format("(f) variance  = %f\n", variance[L]);
		report += String.format("(g) exp_value = %f\n", expected_value[L]);
		report += String.format("(h) phi       = %f\n", phi);
		report += String.format("(i) WARNING:  %d bits were discarded.\n", bits.length - (Q + K) * L);
		report += "-----------------------------------------\n";

		if (p_value < 0 || p_value > 1.0) {
			report += "WARNING:  P_VALUE IS OUT OF RANGE\n";
		}

		report += String.format("%s\t\tp_value = %f\n\n", p_value < Definition.ALPHA ? "FAILURE" : "SUCCESS", p_value);
		return report;
	}

	@Override
	public int getInputSizeRecommendation() {
		return 387840;
	}

}
