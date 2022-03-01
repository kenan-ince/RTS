/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assets;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.util.FastMath;

/**
 *
 * @author kenanince
 */
public class FFT {

	public static Complex[] computeDft(double[] input) {
		int n = input.length;

		double sumreal;
		double sumimag;
		double omega;

//		if (true) {
		omega = 2.0 * Math.PI / n;
//		} else {
//			omega = -2.0 * pi / n;
//		}

		List<Double> rPart = new ArrayList<>();
		List<Double> iPart = new ArrayList<>();
		Complex[] ret = new Complex[n];
		double val;
		for (int k = 1; k <= n; k++) {  // For each output element
			sumreal = 0;
			sumimag = 0;

			for (int j = 1; j <= n; j++) {  // For each input element
				val = omega * (j - 1) * k;
				sumreal += input[j - 1] * FastMath.cos(val) - input[j - 1] * FastMath.sin(val);
//				sumimag += -input[j - 1] * FastMath.sin(val) + input[j - 1] * FastMath.cos(val);
			}

			ret[k - 1] = new Complex(sumreal, sumimag);
		}
		return ret;
	}
}
