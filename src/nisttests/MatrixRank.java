/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nisttests;

import assets.Definition;
import assets.MatrixOperations;
import org.apache.commons.math3.util.FastMath;
import results.Result;

/**
 *
 * @author kenanince
 */
public class MatrixRank extends AbstractTest {

	private int N, i, k, r;
	private double p_value, product, chi2, arg1, p_32, p_31, p_30, R, F_32, F_31, F_30;
	private int[][] matrix = new int[32][32];

	public MatrixRank(int[] bits) {
		super(bits);
	}

	@Override
	public Result[] runTest() {

		N = bits.length / (32 * 32);

		if (N == 0) {
			p_value = 0.00;
		} else {

			MatrixOperations mo = new MatrixOperations(bits, 32, 32);

			r = 32;
			/* COMPUTE PROBABILITIES */
			product = 1;
			for (i = 0; i <= r - 1; i++) {
				product *= ((1.e0 - FastMath.pow(2, i - 32)) * (1.e0 - FastMath.pow(2, i - 32))) / (1.e0 - FastMath.pow(2, i - r));
			}
			p_32 = FastMath.pow(2, r * (32 + 32 - r) - 32 * 32) * product;

			r = 31;
			product = 1;
			for (i = 0; i <= r - 1; i++) {
				product *= ((1.e0 - FastMath.pow(2, i - 32)) * (1.e0 - FastMath.pow(2, i - 32))) / (1.e0 - FastMath.pow(2, i - r));
			}
			p_31 = FastMath.pow(2, r * (32 + 32 - r) - 32 * 32) * product;

			p_30 = 1 - (p_32 + p_31);

			F_32 = 0;
			F_31 = 0;
			for (k = 0; k < N; k++) {
				/* FOR EACH 32x32 MATRIX   */
				mo.defMatrix(32, 32, k);

				R = mo.computeRank(32, 32);
				if (R == 32) {
					F_32++;
				}
				if (R == 31) {
					F_31++;
				}
			}
			F_30 = (double) N - (F_32 + F_31);

			chi2 = (FastMath.pow(F_32 - N * p_32, 2) / (double) (N * p_32)
					+ FastMath.pow(F_31 - N * p_31, 2) / (double) (N * p_31)
					+ FastMath.pow(F_30 - N * p_30, 2) / (double) (N * p_30));

			arg1 = -chi2 / 2.e0;

			p_value = FastMath.exp(arg1);
		}
		Result mbr = new Result("Matrix Rank", p_value, this.getComputationalInformation(), this);
		return new Result[]{mbr};
	}

	@Override
	public String getComputationalInformation() {
		String report = "MATRIX RANK TEST\n";
		report += "---------------------------------------------\n";
		if (N == 0) {
			report += "Insuffucient # Of Bits To Define An 32x32 Matrix\n";
			return report;
		}

		report += String.format("(a) Probability P_%d = %f\n", 32, p_32);
		report += String.format("(b)             P_%d = %f\n", 31, p_31);
		report += String.format("(c)             P_%d = %f\n", 30, p_30);
		report += String.format("(d) Frequency   F_%d = %d\n", 32, (int)p_32);
		report += String.format("(e)             F_%d = %d\n", 31, (int)p_31);
		report += String.format("(f)             F_%d = %d\n", 30, (int)p_30);
		report += String.format("(g) # of matrices    = %d\n", bits.length / (32 * 32));
		report += String.format("(h) Chi^2            = %f\n", chi2);
		report += String.format("(i) NOTE: %d BITS WERE DISCARDED.\n", bits.length % (32 * 32));
		report += "---------------------------------------------\n";

		if (p_value < 0 || p_value > 1) {
			report += "WARNING:  P_VALUE IS OUT OF RANGE.\n";
		}

		report += String.format("%s\t\tp_value = %f\n\n", p_value < Definition.ALPHA ? "SUCCESS" : "FAILURE", p_value);
		
		return report;
	}

	@Override
	public int getInputSizeRecommendation() {
		return 38912;
	}

}
