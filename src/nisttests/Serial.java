/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nisttests;

import assets.Definition;
import org.apache.commons.math3.special.Gamma;
import org.apache.commons.math3.util.FastMath;
import results.Result;

/**
 *
 * @author kenanince
 */
public class Serial extends AbstractTest {
	
	private int m;
	private int recSize;
	private double	p_value1, p_value2, psim0, psim1, psim2, del1, del2;

	public Serial(int[] bits, int m) {
		super(bits);
		this.recSize = (int) Math.exp((m + 2.0) * Math.log(2.0));
		this.m = m;
	}

	@Override
	public Result[] runTest() {

		psim0 = Serial.psi2(m, bits);
		psim1 = Serial.psi2(m - 1, bits);
		psim2 = Serial.psi2(m - 2, bits);

		del1 = psim0 - psim1;
		del2 = psim0 - 2.0 * psim1 + psim2;
		p_value1 = Gamma.regularizedGammaQ(FastMath.pow(2, m - 1) / 2, del1 / 2.0);
		p_value2 = Gamma.regularizedGammaQ(FastMath.pow(2, m - 2) / 2, del2 / 2.0);
		
		Result mbr = new Result("Serial", p_value2, this.getComputationalInformation(), this);

		return new Result[]{mbr};
	}

	@Override
	public String getComputationalInformation() {
		String report = "SERIAL TEST\n";
		report += "---------------------------------------------\n";
		
		report += String.format("(a) Block length    (m) = %d\n", m);
		report += String.format("(b) Sequence length (n) = %d\n", bits.length);
		report += String.format("(c) Psi_m               = %f\n", psim0);
		report += String.format("(d) Psi_m-1             = %f\n", psim1);
		report += String.format("(e) Psi_m-2             = %f\n", psim2);
		report += String.format("(f) Del_1               = %f\n", del1);
		report += String.format("(g) Del_2               = %f\n", del2);
		report += "---------------------------------------------\n";
		report += String.format("%s\t\tp_value1 = %f\n", p_value1 < Definition.ALPHA ? "FAILURE" : "SUCCESS", p_value1);
		report += String.format("%s\t\tp_value2 = %f\n", p_value2 < Definition.ALPHA ? "FAILURE" : "SUCCESS", p_value2);
		
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

	public static double psi2(int m, int[] eps) {
		int i, j, k, powLen;
		double sum, numOfBlocks;
		int[] P;
		int n = eps.length;

		if ((m == 0) || (m == -1)) {
			return 0.0;
		}

		numOfBlocks = n;
		powLen = (int) FastMath.pow(2, m + 1) - 1;
		P = new int[powLen];

		for (i = 0; i < numOfBlocks; i++) {
			/* COMPUTE FREQUENCY */
			k = 1;
			for (j = 0; j < m; j++) {
				if (eps[(i + j) % n] == 0) {
					k *= 2;
				} else if (eps[(i + j) % n] == 1) {
					k = 2 * k + 1;
				}
			}
			P[k - 1]++;
		}
		sum = 0.0;
		for (i = (int) FastMath.pow(2, m) - 1; i < (int) FastMath.pow(2, m + 1) - 1; i++) {
			sum += FastMath.pow(P[i], 2);
		}
		sum = (sum * FastMath.pow(2, m) / (double) n) - (double) n;

		return sum;
	}

}
