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
public class OverlappingTemplateMatching extends AbstractTest {

	private int i, k, match, m;
	private double W_obs, eta, sum, chi2, p_value, lambda;
	private int M, N, j, K = 5;
	private int[] nu = {0, 0, 0, 0, 0, 0};
	private final double[] pi = {0.364091, 0.185659, 0.139381, 0.100571, 0.0704323, 0.139865};

	public OverlappingTemplateMatching(int[] bits, int m) {
		super(bits);
		this.m = m;
	}

	@Override
	public Result[] runTest() {

		int LEN = bits.length;

		int[] sequence = new int[m];

		M = 1032;
		N = LEN / M;

		for (i = 0; i < m; i++) {
			sequence[i] = 1;
		}

		lambda = (double) (M - m + 1) / FastMath.pow(2, m);
		eta = lambda / 2.0;
		sum = 0.0;

		for (i = 0; i < K; i++) {
			/* Compute Probabilities */
			pi[i] = this.Pr(i, eta);
			sum += pi[i];
		}
		pi[K] = 1 - sum;

		for (i = 0; i < N; i++) {
			W_obs = 0;
			for (j = 0; j < M - m + 1; j++) {
				match = 1;
				for (k = 0; k < m; k++) {
					if (sequence[k] != bits[i * M + j + k]) {
						match = 0;
					}
				}
				if (match == 1) {
					W_obs++;
				}
			}
			if (W_obs <= 4) {
				nu[(int) W_obs]++;
			} else {
				nu[K]++;
			}
		}
		sum = 0;
		chi2 = 0.0;
		/* Compute Chi Square */
		for (i = 0; i < K + 1; i++) {
			chi2 += FastMath.pow((double) nu[i] - (double) N * pi[i], 2) / ((double) N * pi[i]);
			sum += nu[i];
		}
		p_value = Gamma.regularizedGammaQ(K / 2.0, chi2 / 2.0);

		Chi2Result mbr = new Chi2Result("Overlapping Template Matching", p_value, chi2, LEN, this.getComputationalInformation(), this);

		return new Result[]{mbr};
	}

	@Override
	public String getComputationalInformation() {
		String report = "OVERLAPPING TEMPLATE OF ALL ONES TEST\n";
		report += "-------------------------------------------------------------------\n";
		report += String.format("(a) n (sequence_length)      = %d\n", bits.length);
		report += String.format("(b) m (block length of 1s)   = %d\n", m);
		report += String.format("(c) M (length of substring)  = %d\n", M);
		report += String.format("(d) N (number of substrings) = %d\n", N);
		report += String.format("(e) lambda [(M-m+1)/2^m]     = %f\n", lambda);
		report += String.format("(f) eta                      = %f\n", eta);
		report += "-------------------------------------------------------------------\n";
		report += "F R E Q U E N C Y\n";
		report += "    0     1     2     3     4   >=5   Chi^2   P-value  Assignment\n";
		report += "-------------------------------------------------------------------\n";

		report += String.format("%5d %5d %5d %5d %5d %5d   %f ",
				nu[0], nu[1], nu[2], nu[3], nu[4], nu[5], chi2);

		if (p_value < 0 || p_value > 1) {
			report += "WARNING:  P_VALUE IS OUT OF RANGE.\n";
		}

		report += String.format("    %2.6f   %s\n\n", p_value, p_value < Definition.ALPHA ? "FAILURE" : "SUCCESS");
		return report;
	}

	@Override
	public int getInputSizeRecommendation() {
		return 106;
	}

	@Override
	public String getDescription() {
		return super.getDescription() + String.format("(%d)", m);
	}

	public double Pr(int u, double eta) {
		int l;
		double sum, p;

		if (u == 0) {
			p = FastMath.exp(-eta);
		} else {
			sum = 0.0;
			for (l = 1; l <= u; l++) {
				sum += FastMath.exp(-eta - u * FastMath.log(2) + l * FastMath.log(eta) - Gamma.logGamma(l + 1) + Gamma.logGamma(u) - Gamma.logGamma(l) - Gamma.logGamma(u - l + 1));
			}
			p = sum;
		}
		return p;
	}

}
