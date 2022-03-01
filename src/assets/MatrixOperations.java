/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assets;

import org.apache.commons.math3.util.FastMath;

/**
 *
 * @author kenanince
 */
public class MatrixOperations {

	private final int MATRIX_FORWARD_ELIMINATION = 0;
	private final int MATRIX_BACKWARD_ELIMINATION = 1;

	private int[][] bitSequence;
	private int[] epsilon;

	public MatrixOperations(int[] eps, int M, int Q) {
		this.epsilon = eps;
		this.bitSequence = new int[M][Q];
	}

	public int computeRank(int M, int Q) {
		int i, rank, m = FastMath.min(M, Q);

		/* FORWARD APPLICATION OF ELEMENTARY ROW OPERATIONS */
		for (i = 0; i < m - 1; i++) {
			if (bitSequence[i][i] == 1) {
				performElementaryRowOperations(MATRIX_FORWARD_ELIMINATION, i, M, Q);
			} else {
				/* matrix[i][i] = 0 */
				if (findUnitElementAndSwap(MATRIX_FORWARD_ELIMINATION, i, M, Q) == 1) {
					performElementaryRowOperations(MATRIX_FORWARD_ELIMINATION, i, M, Q);
				}
			}
		}

		/* BACKWARD APPLICATION OF ELEMENTARY ROW OPERATIONS */
		for (i = m - 1; i > 0; i--) {
			if (bitSequence[i][i] == 1) {
				performElementaryRowOperations(MATRIX_BACKWARD_ELIMINATION, i, M, Q);
			} else {
				/* matrix[i][i] = 0 */
				if (findUnitElementAndSwap(MATRIX_BACKWARD_ELIMINATION, i, M, Q) == 1) {
					performElementaryRowOperations(MATRIX_BACKWARD_ELIMINATION, i, M, Q);
				}
			}
		}

		rank = determineRank(m, M, Q);

		return rank;
	}

	public void performElementaryRowOperations(int flag, int i, int M, int Q) {
		int j, k;

		if (flag == MATRIX_FORWARD_ELIMINATION) {
			for (j = i + 1; j < M; j++) {
				if (bitSequence[j][i] == 1) {
					for (k = i; k < Q; k++) {
						bitSequence[j][k] = (bitSequence[j][k] + bitSequence[i][k]) % 2;
					}
				}
			}
		} else {
			for (j = i - 1; j >= 0; j--) {
				if (bitSequence[j][i] == 1) {
					for (k = 0; k < Q; k++) {
						bitSequence[j][k] = (bitSequence[j][k] + bitSequence[i][k]) % 2;
					}
				}
			}
		}
	}

	public int findUnitElementAndSwap(int flag, int i, int M, int Q) {
		int index, rowOp = 0;

		if (flag == MATRIX_FORWARD_ELIMINATION) {
			index = i + 1;
			while ((index < M) && (bitSequence[index][i] == 0)) {
				index++;
			}
			if (index < M) {
				rowOp = swapRows(i, index, Q);
			}
		} else {
			index = i - 1;
			while ((index >= 0) && (bitSequence[index][i] == 0)) {
				index--;
			}
			if (index >= 0) {
				rowOp = swapRows(i, index, Q);
			}
		}

		return rowOp;
	}

	public int swapRows(int i, int index, int Q) {
		int p;
		int temp;

		for (p = 0; p < Q; p++) {
			temp = bitSequence[i][p];
			bitSequence[i][p] = bitSequence[index][p];
			bitSequence[index][p] = temp;
		}

		return 1;
	}

	public int determineRank(int m, int M, int Q) {
		int i, j, rank, allZeroes;

		/* DETERMINE RANK, THAT IS, COUNT THE NUMBER OF NONZERO ROWS */
		rank = m;
		for (i = 0; i < M; i++) {
			allZeroes = 1;
			for (j = 0; j < Q; j++) {
				if (bitSequence[i][j] == 1) {
					allZeroes = 0;
					break;
				}
			}
			if (allZeroes == 1) {
				rank--;
			}
		}

		return rank;
	}

	public void defMatrix(int M, int Q, int k) {
		int i, j;

		for (i = 0; i < M; i++) {
			for (j = 0; j < Q; j++) {
				bitSequence[i][j] = epsilon[k * (M * Q) + j + i * M];
			}
		}
	}

	public int[][] getBitSequence() {
		return bitSequence;
	}

	public void setBitSequence(int[][] bitSequence) {
		this.bitSequence = bitSequence;
	}

	public int[] getEpsilon() {
		return epsilon;
	}

	public void setEpsilon(int[] epsilon) {
		this.epsilon = epsilon;
	}
	
	
}
