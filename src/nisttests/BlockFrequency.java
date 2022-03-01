/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nisttests;

import assets.Definition;
import org.apache.commons.math3.special.Gamma;
import results.Chi2Result;
import results.Result;

/**
 *
 * @author kenanince
 */
public class BlockFrequency extends AbstractTest {

	private int i, j, N, blockSum, M;
	private double p_value, sum, pi, v, chi2;
	private int discarded;

	public BlockFrequency(int[] bits, int M) {
		super(bits);
		this.M = M;
	}

	@Override
	public Result[] runTest() {

		N = this.bits.length / M;
		discarded = N % M;
		sum = 0.0;

		for (i = 0; i < N; i++) {
			blockSum = 0;
			
			for (j = 0; j < M; j++) {
				blockSum += this.bits[j + i * M];
			}
			pi = (double) blockSum / (double) M;
			v = pi - 0.5;
			sum += v * v;
		}
		chi2 = 4.0 * M * sum;
		p_value = Gamma.regularizedGammaQ(N / 2.0, chi2 / 2.0);
		
		Chi2Result mbr = new Chi2Result("Block Frequency", p_value, chi2, N, this.getComputationalInformation(), this);

		return new Result[]{mbr};
	}

	@Override
	public String getComputationalInformation() {
		String report = "BLOCK FREQUENCY TEST\n";
		report += "-------------------------------------------------\n";
		
		report += String.format("(a) Chi^2           = %f\n", chi2);
		report += String.format("(b) # of substrings = %d\n", N);
		report += String.format("(c) block length    = %d\n", M);
		report += String.format("(d) Note: %d bits were discarded.\n", discarded);
		report += "-------------------------------------------------\n";

		report += String.format("%s\t\tp_value = %f\n\n", this.p_value > Definition.ALPHA ? "SUCCESS" : "FAILURE", p_value);
		return report;
	}

	@Override
	public int getInputSizeRecommendation() {
		return 100;
	}

}
