/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assets;

import java.util.Map;
import static java.util.Map.entry;

/**
 *
 * @author kenanince
 */
public class Definition {
	public static double ALPHA = 0.01;
	public static final double sqrt2 = 1.41421356237309504880;
	public static final int MAXNUMOFTEMPLATES = 148;
	
	public static final String[] tests = {"Frequency", "Block Frequency", "Runs","Longest Run of Ones", "Matrix Rank", "Discrete Fourier Transform",
	"Non Overlapping Template Matching","Overlapping Template Matching","Universal","Linear Complexity","Serial","Approximate Entropy",
	"Cumulative Sum","Random Excursions", "Random Excursions Variant"};
	
	public static final Map<String, Integer> testMap = Map.ofEntries(
			entry("Frequency", 100),
			entry("Block Frequency", 100),
			entry("Runs", 100),
			entry("Longest Run of Ones", 128),
			entry("Matrix Rank", 38912),
			entry("Discrete Fourier Transform", 1000),
			entry("Non Overlapping Template Matching", 1048576),
			entry("Overlapping Template Matching", 106),
			entry("Universal", 387840),
			entry("Linear Complexity", 1000000),
			entry("Serial", 32),
			entry("Approximate Entropy", 127),
			entry("Cumulative Sums", 100),
			entry("Random Excursions", 1000000),
			entry("Random Excursions Variant", 1000000)
	);
}
