/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nisttests;

import assets.Definition;
import org.apache.commons.math3.special.Gamma;
import org.apache.commons.math3.util.FastMath;
import results.Chi2Result;
import results.Result;

/**
 *
 * @author kenanince
 */
public class RandomExcursions extends AbstractTest {

	private int b, i, j, k, J, x;
	private int cycleStart, cycleStop;
	private int[] cycle;
	int[] S_k;
	int[] stateX = {-4, -3, -2, -1, 1, 2, 3, 4};
	int[] counter = {0, 0, 0, 0, 0, 0, 0, 0};
	double p_value, sum, constraint;
	double[][] nu = new double[6][8];

	double[][] pi = {
		{0.0000000000, 0.00000000000, 0.00000000000, 0.00000000000, 0.00000000000, 0.0000000000},
		{0.5000000000, 0.25000000000, 0.12500000000, 0.06250000000, 0.03125000000, 0.0312500000},
		{0.7500000000, 0.06250000000, 0.04687500000, 0.03515625000, 0.02636718750, 0.0791015625},
		{0.8333333333, 0.02777777778, 0.02314814815, 0.01929012346, 0.01607510288, 0.0803755143},
		{0.8750000000, 0.01562500000, 0.01367187500, 0.01196289063, 0.01046752930, 0.0732727051}};

	public RandomExcursions(int[] bits) {
		super(bits);
		cycle = new int[FastMath.max(1000, bits.length / 100)];
		S_k = new int[bits.length];
	}

	@Override
	public Result[] runTest() {

		J = 0;
		/* DETERMINE CYCLES */
		String error = "";

		S_k[0] = 2 * (int) bits[0] - 1;
		for (i = 1; i < bits.length; i++) {
			S_k[i] = S_k[i - 1] + 2 * bits[i] - 1;
			if (S_k[i] == 0) {
				J++;
				cycle[J] = i;
			}
		}
		if (S_k[bits.length - 1] != 0) {
			J++;
		}
		cycle[J] = bits.length;

		constraint = FastMath.max(0.005 * FastMath.pow(bits.length, 0.5), 500);
		if (J > constraint) {
			boolean resultDes = false;
			cycleStart = 0;
			cycleStop = cycle[1];
			for (k = 0; k < 6; k++) {
				for (i = 0; i < 8; i++) {
					nu[k][i] = 0.;
				}
			}
			for (j = 1; j <= J; j++) {
				/* FOR EACH CYCLE */
				for (i = 0; i < 8; i++) {
					counter[i] = 0;
				}
				for (i = cycleStart; i < cycleStop; i++) {
					if ((S_k[i] >= 1 && S_k[i] <= 4) || (S_k[i] >= -4 && S_k[i] <= -1)) {
						if (S_k[i] < 0) {
							b = 4;
						} else {
							b = 3;
						}
						counter[S_k[i] + b]++;
					}
				}
				cycleStart = cycle[j] + 1;
				if (j < J) {
					cycleStop = cycle[j + 1];
				}

				for (i = 0; i < 8; i++) {
					if ((counter[i] >= 0) && (counter[i] <= 4)) {
						nu[counter[i]][i]++;
					} else if (counter[i] >= 5) {
						nu[5][i]++;
					}
				}
			}
			Chi2Result[] mbr = new Chi2Result[8];
			for (i = 0; i < 8; i++) {
				x = stateX[i];
				sum = 0.;
				for (k = 0; k < 6; k++) {
					sum += FastMath.pow(nu[k][i] - J * pi[(int) FastMath.abs(x)][k], 2) / (J * pi[(int) FastMath.abs(x)][k]);
				}
				p_value = Gamma.regularizedGammaQ(2.5, sum / 2.0);

				mbr[i] = new Chi2Result("Random Excursions", p_value, sum, 5, this.getComputationalInformation(), this);

			}
			return mbr;

		}
		
		Chi2Result mbr = new Chi2Result("Random Excursions", p_value, sum, 5, this.getComputationalInformation(), this);
		return new Result[]{mbr};
	}

	@Override
	public String getComputationalInformation() {
		String report = "RANDOM EXCURSIONS TEST\n";
		report += "------------------------------------------------------\n";
		if (J > FastMath.max(1000, bits.length / 100) || J < constraint) {
			report += "TEST NOT APPLICABLE.  THERE ARE AN INSUFFICIENT NUMBER OF CYCLES.\n";
			return report;
		}

		report += String.format("(a) Number Of Cycles (J) = %04d\n", J);
		report += String.format("(b) Sequence Length (n)  = %d\n", bits.length);
		report += String.format("(c) Rejection Constraint = %f\n", constraint);
		report += "------------------------------------------------------";

		for (int i = 0; i < 8; i++) {

			if (p_value < 0.0 || p_value > 1.0) {
				report += "WARNING:  P_VALUE IS OUT OF RANGE.\n";
			}

			report += String.format("%s\t\tx = %2d chi^2 = %9.6f p_value = %f\n", p_value < Definition.ALPHA ? "FAILURE" : "SUCCESS", x, sum, p_value);
		}

		return report;
	}

	@Override
	public int getInputSizeRecommendation() {
		return 1000000;
	}

}
