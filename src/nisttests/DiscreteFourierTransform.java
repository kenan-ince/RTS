/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nisttests;

import assets.Definition;
import assets.FFT;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.special.Erf;
import org.apache.commons.math3.util.FastMath;
import results.Result;

/**
 *
 * @author kenanince
 */
public class DiscreteFourierTransform extends AbstractTest {

	private double p_value, upperBound, percentile, N_l, N_o, d;
	private double[] X;
	private double[] m;

	public DiscreteFourierTransform(int[] bits) {
		super(bits);
		X = new double[bits.length];
		m = new double[bits.length / 2];
	}

	@Override
	public Result[] runTest() {

		for (int i = 0; i < bits.length; i++) {
			X[i] = bits[i] * 2 - 1;
		}

		Complex[] S = FFT.computeDft(X);

		for (int i = 0; i < m.length; i++) {
			m[i] = FastMath.sqrt(FastMath.pow(S[i].getReal(), 2)); // S[i].getReal();
		}

		double T = FastMath.sqrt(FastMath.log10(1.0 / 0.05) * bits.length);
		N_o = 0.95 * ((double) bits.length / 2.0);

		N_l = 0;
		double upperBound = FastMath.sqrt(2.995732274 * bits.length);
		for (int i = 0; i < bits.length / 2; i++) {
			if (m[i] < upperBound) {
				N_l++;
			}
		}

		percentile = (double) N_l / (bits.length / 2) * 100;

		d = (N_l - N_o) / (FastMath.sqrt(bits.length / 4.0 * 0.95 * 0.05));

		p_value = Erf.erfc(FastMath.abs(d) / FastMath.sqrt(2));

		Result mbr = new Result("Discrete Fourier Transform", p_value, this.getComputationalInformation(), this);

		return new Result[]{mbr};
	}

	@Override
	public String getComputationalInformation() {
		String report = "DISCRETE FORUIER TEST\n";
		report += "-------------------------------------------\n";
		report += String.format("(a) Percentile = %f\n", percentile);
		report += String.format("(b) N_l        = %f\n", N_l);
		report += String.format("(c) N_o        = %f\n", N_o);
		report += String.format("(d) d          = %f\n", d);
		report += "-------------------------------------------\n";

		report += String.format("%s\t\tp_value = %f\n\n", p_value > Definition.ALPHA ? "SUCCESS" : "FAILURE", p_value);
		return report;
	}

	@Override
	public int getInputSizeRecommendation() {
		return 1000;
	}

}
