/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nisttests;

import assets.Definition;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.math3.special.Gamma;
import org.apache.commons.math3.util.FastMath;
import results.Chi2Result;
import results.Result;

/**
 *
 * @author kenanince
 */
public class NonOverlappingTemplateMatching extends AbstractTest {

	private final int[] numOfTemplates = {0, 0, 2, 4, 6, 12, 20, 40, 74, 148, 284, 568, 1116,
		2232, 4424, 8848, 17622, 35244, 70340, 140680, 281076, 562152};

	private int bit, W_obs;
	private int[] nu = new int[6];
	private double sum, chi2, p_value, lambda, varWj;
	private double[] pi = new double[6];
	private int i, j, jj, k, match, SKIP, M, N, K = 5;
	private int[] Wj;
	private int m;

	public NonOverlappingTemplateMatching(int[] bits, int m) {
		super(bits);
		this.m = m;
	}

	@Override
	public Result[] runTest() {
		String directory = "./templates/template" + m;
		int[] sequence = new int[m];

		N = 8;
		M = bits.length / N;

		Wj = new int[N];

		lambda = (M - m + 1) / FastMath.pow(2, m);
		varWj = M * (1.0 / FastMath.pow(2.0, m) - (2.0 * m - 1.0) / FastMath.pow(2.0, 2.0 * m));
		
		Chi2Result mbr = new Chi2Result("Non Overlapping Template Matching", 0, 0, 0, "", this);

		if (lambda <= 0) {
			mbr.description = "Lambda (" + lambda + ") not being positive!";
			mbr.p_value = 0;
			return new Result[]{mbr};
		} else {

			BufferedReader br = null;
			try {
				if (numOfTemplates[m] < Definition.MAXNUMOFTEMPLATES) {
					SKIP = 1;
				} else {
					SKIP = (int) (numOfTemplates[m] / Definition.MAXNUMOFTEMPLATES);
				}
				numOfTemplates[m] = (int) numOfTemplates[m] / SKIP;
				sum = 0.0;
				for (i = 0; i < 2; i++) {
					/* Compute Probabilities */
					pi[i] = FastMath.exp(-lambda + i * FastMath.log(lambda) - Gamma.logGamma(i + 1));
					sum += pi[i];
				}
				pi[0] = sum;
				for (i = 2; i <= K; i++) {
					/* Compute Probabilities */
					pi[i - 1] = FastMath.exp(-lambda + i * FastMath.log(lambda) - Gamma.logGamma(i + 1));
					sum += pi[i - 1];
				}
				pi[K] = 1 - sum;
				br = new BufferedReader(new FileReader(directory));
				for (jj = 0; jj < FastMath.min(Definition.MAXNUMOFTEMPLATES, numOfTemplates[m]); jj++) {
					sum = 0;

					String line = br.readLine();
					String[] tmp = line.split(" ");

					for (j = 0; j < tmp.length; j++) {
						sequence[j] = Integer.parseInt(tmp[j]);
					}

					for (k = 0; k <= K; k++) {
						nu[k] = 0;
					}

					for (i = 0; i < N; i++) {
						W_obs = 0;
						for (j = 0; j < M - m + 1; j++) {
							match = 1;
							for (k = 0; k < m; k++) {
								if ((int) sequence[k] != (int) bits[i * M + j + k]) {
									match = 0;
									break;
								}
							}
							if (match == 1) {
								W_obs++;
								j += m - 1;
							}
						}
						Wj[i] = W_obs;
					}
					sum = 0;
					chi2 = 0.0;
					/* Compute Chi Square */

					for (i = 0; i < N; i++) {
						chi2 += FastMath.pow(((double) Wj[i] - lambda) / FastMath.pow(varWj, 0.5), 2);
					}
					p_value = Gamma.regularizedGammaQ(N / 2.0, chi2 / 2.0);
				}

				mbr = new Chi2Result("Non Overlapping Template Matching", p_value, chi2, N, this.getComputationalInformation(), this);
				
			} catch (Exception ex) {
				Logger.getLogger(NonOverlappingTemplateMatching.class.getName()).log(Level.SEVERE, null, ex);
			} finally {
				try {
					br.close();
				} catch (Exception ex) {
					Logger.getLogger(NonOverlappingTemplateMatching.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		return new Result[]{mbr};
	}

	@Override
	public String getComputationalInformation() {
		String report = "NON OVERLAPPING TEMPLATE MATCHING\n";
		report += "-------------------------------------------------------------------------------------\n";
		if (p_value == 0) {
			report += "Lambda (" + lambda + ") not being positive!";
			return report;
		}

		report += String.format("\tLAMBDA = %f\tM = %d\tN = %d\tm = %d\tn = %d\n",
				lambda, M, N, m, bits.length);
		report += "-------------------------------------------------------------------------------------\n";
		report += "\t\tF R E Q U E N C Y\n";
		report += "Template   W_1  W_2  W_3  W_4  W_5  W_6  W_7  W_8    Chi^2   P_value Assignment Index\n";
		report += "-------------------------------------------------------------------------------------\n\t   ";

		for (i = 0; i < N; i++) {
			if (m == 10) {
				report += String.format("%-3d  ", Wj[i]);
			} else {
				report += String.format("%-4d ", Wj[i]);
			}
		}

		report += String.format("%9.6f %f  %s   %3d\n", chi2, p_value, p_value < Definition.ALPHA ? "FAILURE" : "SUCCESS", jj);

		return report;
	}

	@Override
	public int getInputSizeRecommendation() {
		return N;
	}
	
	@Override
	public String getDescription() {
		return super.getDescription() + String.format("(%d)", m);
	}

}
