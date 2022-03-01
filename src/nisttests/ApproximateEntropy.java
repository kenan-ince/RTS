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
public class ApproximateEntropy extends AbstractTest {

	private int m;
	private int recSize;

	private int i, j, k, r, blockSize, seqLength, powLen, index;
	private double sum, numOfBlocks, apen, chi2, p_value;
	private double[] ApEn = new double[2];
	private int[] P;

	public ApproximateEntropy(int[] bits, int m) {
		super(bits);
		this.m = m;
		this.recSize = (int) FastMath.exp((m + 5.0) * FastMath.log(2.0));
	}

	@Override
	public Result[] runTest() {
		
		seqLength = bits.length;
		r = 0;

		for (blockSize = m; blockSize <= m + 1; blockSize++) {
			if (blockSize == 0) {
				ApEn[0] = 0.00;
				r++;
			} else {
				numOfBlocks = (double) seqLength;
				powLen = (int) FastMath.pow(2, blockSize + 1) - 1;
				P = new int[powLen];

				for (i = 1; i < powLen - 1; i++) {
					P[i] = 0;
				}
				for (i = 0; i < numOfBlocks; i++) {
					/* COMPUTE FREQUENCY */
					k = 1;
					for (j = 0; j < blockSize; j++) {
						k <<= 1;
						if ((int) bits[(i + j) % seqLength] == 1) {
							k++;
						}
					}
					P[k - 1]++;
				}
				/* DISPLAY FREQUENCY */
				sum = 0.0;
				index = (int) FastMath.pow(2, blockSize) - 1;
				for (i = 0; i < (int) FastMath.pow(2, blockSize); i++) {
					if (P[index] > 0) {
						sum += P[index] * FastMath.log(P[index] / numOfBlocks);
					}
					index++;
				}
				sum /= numOfBlocks;
				ApEn[r] = sum;
				r++;
			}
		}
		apen = ApEn[0] - ApEn[1];

		chi2 = 2.0 * seqLength * (FastMath.log(2) - apen);
		p_value = Gamma.regularizedGammaQ(FastMath.pow(2, m - 1), chi2 / 2.0);

		Chi2Result mbr = new Chi2Result("Approximate Entropy", p_value, chi2, 1 << m, this.getComputationalInformation(), this);

		return new Result[]{mbr};
	}

	@Override
	public String getComputationalInformation() {
		String report = "APPROXIMATE ENTROPY TEST\n";
		report += "--------------------------------------------\n";
		
		report += String.format("(a) m (block length)    = %d\n", m);
		report += String.format("(b) n (sequence length) = %d\n", seqLength);
		report += String.format("(c) Chi^2               = %f\n", chi2);
		report += String.format("(d) Phi(m)              = %f\n", ApEn[0]);
		report += String.format("(e) Phi(m+1)            = %f\n", ApEn[1]);
		report += String.format("(f) ApEn                = %f\n", apen);
		report += String.format("(g) Log(2)              = %f\n", Math.log(2.0));
		report += "--------------------------------------------\n";

		int recBlockSize = (int) (Math.log(seqLength) / Math.log(2) - 5);
		
		if (m > recBlockSize) {
			report += String.format("Note: The blockSize = %d exceeds recommended value of %d\n", m, Math.max(1, recBlockSize));
			report += "Results are inaccurate!\n";
			report += "--------------------------------------------\n";
		}

		report += String.format("%s\t\tp_value = %f\n\n", p_value < Definition.ALPHA ? "FAILURE" : "SUCCESS", p_value);
		
		return report;
	}

	@Override
	public int getInputSizeRecommendation() {
		return recSize;
	}

	@Override
	public String getDescription() {
		return super.getDescription() + String.format("(%d)", m);
	}

}
