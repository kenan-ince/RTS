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
public class LongestRunOfOnes extends AbstractTest {

	private int blockLen;
	private int numberOfBlocks;
	private double chi2;
	private int K;
	private double p_value;
	private int[] nu = new int[]{0, 0, 0, 0, 0, 0, 0};

	public LongestRunOfOnes(int[] bits) {
		super(bits);
	}

	@Override
	public Result[] runTest() {

		int length = bits.length;
		double pi[] = new double[7];
		int run, v_n_obs, i, j, V[] = new int[7];

		if (length < 128) {
			p_value = 0;
		}
		if (length < 6272) {
			K = 3;
			blockLen = 8;
			V[0] = 1;
			V[1] = 2;
			V[2] = 3;
			V[3] = 4;
			pi[0] = 0.21484375;
			pi[1] = 0.3671875;
			pi[2] = 0.23046875;
			pi[3] = 0.1875;
		} else if (length < 750000) {
			K = 5;
			blockLen = 128;
			V[0] = 4;
			V[1] = 5;
			V[2] = 6;
			V[3] = 7;
			V[4] = 8;
			V[5] = 9;
			pi[0] = 0.1174035788;
			pi[1] = 0.242955959;
			pi[2] = 0.249363483;
			pi[3] = 0.17517706;
			pi[4] = 0.102701071;
			pi[5] = 0.112398847;
		} else {
			K = 6;
			blockLen = 10000;
			V[0] = 10;
			V[1] = 11;
			V[2] = 12;
			V[3] = 13;
			V[4] = 14;
			V[5] = 15;
			V[6] = 16;
			pi[0] = 0.0882;
			pi[1] = 0.2092;
			pi[2] = 0.2483;
			pi[3] = 0.1933;
			pi[4] = 0.1208;
			pi[5] = 0.0675;
			pi[6] = 0.0727;
		}

		numberOfBlocks = length / blockLen;
		for (i = 0; i < numberOfBlocks; i++) {
			v_n_obs = 0;
			run = 0;
			for (j = 0; j < blockLen; j++) {
				if (bits[i * blockLen + j] == 1) {
					run++;
					if (run > v_n_obs) {
						v_n_obs = run;
					}
				} else {
					run = 0;
				}
			}
			if (v_n_obs < V[0]) {
				nu[0]++;
			}
			for (j = 0; j <= K; j++) {
				if (v_n_obs == V[j]) {
					nu[j]++;
				}
			}
			if (v_n_obs > V[K]) {
				nu[K]++;
			}
		}

		chi2 = 0.0;
		for (i = 0; i <= K; i++) {
			chi2 += ((nu[i] - numberOfBlocks * pi[i]) * (nu[i] - numberOfBlocks * pi[i])) / (numberOfBlocks * pi[i]);
		}

		Chi2Result r = new Chi2Result("Longest Run of Ones", Gamma.regularizedGammaQ((double) (K / 2.0), chi2 / 2.0), chi2, K, this.getComputationalInformation(), this);

		return new Result[]{r};

	}

	@Override
	public String getComputationalInformation() {
		String report = "LONGEST RUNS OF ONES TEST\n";
		report += "---------------------------------------------\n";
		report += String.format("(a) N (# of substrings)  = %d\n", numberOfBlocks);
		report += String.format("(b) M (Substring Length) = %d\n", blockLen);
		report += String.format("(c) Chi^2                = %f\n", chi2);
		report += "---------------------------------------------\n";
		report += "      F R E Q U E N C Y\n";
		report += "---------------------------------------------\n";

		switch (K) {
			case 3:
				report += "    <=1      2      3    >=4 P-value  Assignment\n";
				report += String.format(" %6d %6d %6d %6d \n", nu[0], nu[1], nu[2], nu[3]);
				break;
			case 5:
				report += "    <=4      5      6      7      8    >=9 P-value  Assignment\n";
				report += String.format(" %6d %6d %6d %6d %6d %6d\n", nu[0], nu[1], nu[2],
						nu[3], nu[4], nu[5]);
				break;
			default:
				report += "   <=10     11     12     13     14     15   >=16 P-value  Assignment\n";
				report += String.format(" %6d %6d %6d %6d %6d %6d %6d ", nu[0], nu[1], nu[2],
						nu[3], nu[4], nu[5], nu[6]);
				break;
		}

		p_value = Gamma.regularizedGammaQ((double) (K / 2.0), chi2 / 2.0);

		if (p_value < 0.0 || p_value > 1.0) {
			report += "WARNING:  P_VALUE IS OUT OF RANGE.";
		}

		report += String.format("%s\t\tp_value = %f\n\n", this.p_value > Definition.ALPHA ? "SUCCESS" : "FAILURE", p_value);

		return report;
	}

	@Override
	public int getInputSizeRecommendation() {
		return 128;
	}

}
