/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package randomtestsuite;

import assets.Definition;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.AEADBadTagException;
import nisttests.ApproximateEntropy;
import nisttests.BlockFrequency;
import nisttests.CumulativeSum;
import nisttests.DiscreteFourierTransform;
import nisttests.Frequency;
import nisttests.LinearComplexity;
import nisttests.LongestRunOfOnes;
import nisttests.MatrixRank;
import nisttests.NonOverlappingTemplateMatching;
import nisttests.OverlappingTemplateMatching;
import nisttests.RandomExcursions;
import nisttests.RandomExcursionsVariant;
import nisttests.Runs;
import nisttests.Serial;
import nisttests.Universal;
import org.apache.commons.math3.util.FastMath;
import results.Result;

/**
 *
 * @author kenanince
 */
public class TestSuite {

	private int[] bits;
	private List<Result[]> suiteResult;

	public TestSuite(int[] bits) {
		this.bits = bits;
	}

	public List<Result[]> getSuiteResult() {
		if (this.suiteResult == null) {
			this.suiteResult = new ArrayList<>();
			for (String f : Definition.tests) {
				suiteResult.add(this.fncSelect(f));
			}
		}
		return suiteResult;
	}

	public List<Result[]> getSuiteResult(List<String> tests) {
		if (this.suiteResult == null) {
			this.suiteResult = new ArrayList<>();
			tests.forEach(f -> {
				suiteResult.add(this.fncSelect(f));
			});
		}
		return suiteResult;
	}

	public Result[] fncSelect(String fnc) {

		switch (fnc) {
			case "Frequency":
				Frequency f = new Frequency(bits);
				return f.runTest();
			case "Block Frequency":
				BlockFrequency bf = new BlockFrequency(bits, 10);
				return bf.runTest();
			case "Runs":
				Runs r = new Runs(bits);
				return r.runTest();
			case "Longest Run of Ones":
				LongestRunOfOnes lro = new LongestRunOfOnes(bits);
				return lro.runTest();
			case "Matrix Rank":
				MatrixRank mr = new MatrixRank(bits);
				return mr.runTest();
			case "Discrete Fourier Transform":
				DiscreteFourierTransform dft = new DiscreteFourierTransform(bits);
				return dft.runTest();
			case "Non Overlapping Template Matching":
				NonOverlappingTemplateMatching notm = new NonOverlappingTemplateMatching(bits, 10);
				return notm.runTest();
			case "Overlapping Template Matching":
				OverlappingTemplateMatching otm = new OverlappingTemplateMatching(bits, 10);
				return otm.runTest();
			case "Universal":
				Universal u = new Universal(bits);
				return u.runTest();
			case "Linear Complexity":
				LinearComplexity lc = new LinearComplexity(bits, 500);
				return lc.runTest();
			case "Serial":
				Serial s = new Serial(bits, 2);
				return s.runTest();
			case "Approximate Entropy":
				int t = (int) Math.floor(FastMath.log(2, bits.length)) - 5;
				if (t < 2) {
					t = 2;
				}
				ApproximateEntropy ae = new ApproximateEntropy(bits, t);
				return ae.runTest();
			case "Cumulative Sum":
				CumulativeSum cs = new CumulativeSum(bits);
				return cs.runTest();
			case "Random Excursions":
				RandomExcursions re = new RandomExcursions(bits);
				return re.runTest();
			case "Random Excursions Variant":
				RandomExcursionsVariant rev = new RandomExcursionsVariant(bits);
				return rev.runTest();

		}
		return null;
	}

}
