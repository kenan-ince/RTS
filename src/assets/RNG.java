/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assets;

import java.util.Random;

/**
 *
 * @author kenanince
 */
public class RNG {

	private byte[] bytes;
	private int len;
	private String sequence;

	public RNG(int length) {
		bytes = new byte[(length + 7) / 8];
		this.len = length;
		sequence = "";
	}

	public void generate(Random rng) {
		rng.nextBytes(bytes);
		int size = (this.len + 7) / 8;

		String s1 = "";
		int n = 0;
		for (int i = 0; i < size; i++) {
			s1 = this.byteToBinaryString(bytes[i]);
			for (int j = 0; j < s1.length() && n < this.len; j++) {
				sequence += s1.charAt(j);
			}
		}
	}

	public String byteToBinaryString(byte b) {
		StringBuilder sb = new StringBuilder();
		for (int i = 7; i >= 0; --i) {
			sb.append(b >>> i & 1);
		}
		return sb.toString();
	}

	public String getSequence() {
		return sequence;
	}

}
