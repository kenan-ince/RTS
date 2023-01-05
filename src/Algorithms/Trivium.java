/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Algorithms;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 *
 * @author kenanince
 */
public class Trivium {

	private boolean[] S;
	int round;

	public Trivium(int round) {
		this.round = round;
		this.S = new boolean[288];
	}

	public int[] generate() throws NoSuchAlgorithmException {

		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");

		boolean[] k = new boolean[80];
		boolean[] v = new boolean[80];

		for (int i = 0; i < 80; i++) {
			k[i] = sr.nextBoolean();
			v[i] = sr.nextBoolean();
		}

		for (int i = 0; i < 288; i++) {
			S[i] = sr.nextBoolean();
		}

		int[] bits = new int[round];
		boolean t1, t2, t3;

		boolean r = false;

		S[285] = true;
		S[286] = true;
		S[287] = true;

		for (int i = 0; i < 1152; i++) {
			t1 = S[65] ^ S[92];
			t2 = S[161] ^ S[176];
			t3 = S[242] ^ S[287];

			t1 = t1 ^ S[90] & S[91] ^ S[170];
			t2 = t2 ^ S[174] & S[175] ^ S[263];
			t3 = t3 ^ S[285] & S[286] ^ S[68];

			this.shift(t1, t2, t3);

		}

		for (int i = 79; i >= 0; i--) {
			S[i + 13] = S[i + 13] ^ k[i];
		}

		for (int i = 79; i >= 0; i--) {
			S[i + 105] = S[i + 105] ^ v[i];
		}

		for (int i = 0; i < round; i++) {
			t1 = S[65] ^ S[92];
			t2 = S[161] ^ S[176];
			t3 = S[242] ^ S[287];

			r = t1 ^ t2 ^ t3;

			t1 = t1 ^ S[90] & S[91] ^ S[170];
			t2 = t2 ^ S[174] & S[175] ^ S[263];
			t3 = t3 ^ S[285] & S[286] ^ S[68];

			this.shift(t1, t2, t3);

			if (r) {
				bits[i] = 1;
			} else {
				bits[i] = 0;
			}

		}

		return bits;
	}

	public void shift(boolean t1, boolean t2, boolean t3) {

		for (int i = 92; i > 0; i--) {
			this.S[i] = this.S[i - 1];
		}
		this.S[0] = t3;

		for (int i = 175; i > 93; i--) {
			this.S[i] = this.S[i - 1];
		}
		this.S[93] = t1;

		for (int i = 286; i > 177; i--) {
			this.S[i] = this.S[i - 1];
		}
		this.S[177] = t2;

	}

}
