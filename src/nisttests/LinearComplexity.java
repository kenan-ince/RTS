/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nisttests;

import org.apache.commons.math3.special.Gamma;
import org.apache.commons.math3.util.FastMath;
import results.Chi2Result;
import results.Result;

/**
 *
 * @author kenanince
 */
public class LinearComplexity extends AbstractTest {

	private int i, ii, j, d, N, L, m, N_, parity, sign, K = 6;
	private double p_value, T_, mean, chi2;
	private double[] nu = new double[7];
	private int M;
	private final double[] pi = {0.01047, 0.03125, 0.12500, 0.50000, 0.25000, 0.06250, 0.020833};
	private int[] T;
	private int[] P;
	private int[] B_;
	private int[] C;

	public LinearComplexity(int[] bits, int m) {
		super(bits);
		this.M = m;
		this.T = new int[M];
		this.P = new int[M];
		this.B_ = new int[M];
		this.C = new int[M];
	}

	@Override
	public Result[] runTest() {
		String tmp = "";

		N = (int) FastMath.floor(bits.length / M);

		for (i = 0; i < K + 1; i++) {
			nu[i] = 0.00;
		}
		for (ii = 0; ii < N; ii++) {
			for (i = 0; i < M; i++) {
				B_[i] = 0;
				C[i] = 0;
				T[i] = 0;
				P[i] = 0;
			}
			L = 0;
			m = -1;
			d = 0;
			C[0] = 1;
			B_[0] = 1;

			/* DETERMINE LINEAR COMPLEXITY */
			N_ = 0;
			while (N_ < M) {
				d = (int) bits[ii * M + N_];
				for (i = 1; i <= L; i++) {
					d += C[i] * bits[ii * M + N_ - i];
				}
				d = d % 2;
				if (d == 1) {
					for (i = 0; i < M; i++) {
						T[i] = C[i];
						P[i] = 0;
					}
					for (j = 0; j < M; j++) {
						if (B_[j] == 1) {
							P[j + N_ - m] = 1;
						}
					}
					for (i = 0; i < M; i++) {
						C[i] = (C[i] + P[i]) % 2;
					}
					if (L <= N_ / 2) {
						L = N_ + 1 - L;
						m = N_;
						for (i = 0; i < M; i++) {
							B_[i] = T[i];
						}
					}
				}
				N_++;
			}
			if ((parity = (M + 1) % 2) == 0) {
				sign = -1;
			} else {
				sign = 1;
			}

			mean = M / 2.0 + (9.0 + sign) / 36.0 - 1.0 / FastMath.pow(2, M) * (M / 3.0 + 2.0 / 9.0);

			if ((parity = M % 2) == 0) {
				sign = 1;
			} else {
				sign = -1;
			}
			T_ = sign * (L - mean) + 2.0 / 9.0;

			if (T_ <= -2.5) {
				nu[0]++;
			} else if (T_ > -2.5 && T_ <= -1.5) {
				nu[1]++;
			} else if (T_ > -1.5 && T_ <= -0.5) {
				nu[2]++;
			} else if (T_ > -0.5 && T_ <= 0.5) {
				nu[3]++;
			} else if (T_ > 0.5 && T_ <= 1.5) {
				nu[4]++;
			} else if (T_ > 1.5 && T_ <= 2.5) {
				nu[5]++;
			} else {
				nu[6]++;
			}
		}
		chi2 = 0.00;

		for (i = 0; i < K + 1; i++) {
			chi2 += FastMath.pow(nu[i] - N * pi[i], 2) / (N * pi[i]);
		}
		p_value = Gamma.regularizedGammaQ(((double) K / 2.0), chi2 / 2);

		Chi2Result mbr = new Chi2Result("Linear Complexity", p_value, chi2, K, this.getComputationalInformation(), this);

		return new Result[]{mbr};
	}

	@Override
	public String getComputationalInformation() {
		String report = "LINEAR COMPLEXITY\n";
		report += "-----------------------------------------------------\n";

		report += String.format("M (substring length)     = %d\n", M);
		report += String.format("N (number of substrings) = %d\n", N);
		report += "-----------------------------------------------------\n";
		report += "        F R E Q U E N C Y                            \n";
		report += "-----------------------------------------------------\n";
		report += "  C0   C1   C2   C3   C4   C5   C6    CHI2    P-value\n";
		report += "-----------------------------------------------------\n";

		int cnt = K + 1;
		for (int i = 0; i < cnt; i++) {
			report += String.format("%4d ", (int) nu[i]);
		}

		report += String.format("%9.6f%9.6f\n\n", chi2, p_value);
		
		
		report += String.format("Note: %d bits were discarded!\n", bits.length % M);
		return report;
	}

	@Override
	public int getInputSizeRecommendation() {
		return Math.max(1000000, 200 * M);
	}

	@Override
	public String getDescription() {
		return super.getDescription() + String.format("(%d)", M);
	}
}
