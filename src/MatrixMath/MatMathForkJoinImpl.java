package MatrixMath;

import java.util.concurrent.*;

public class MatMathForkJoinImpl implements MatMath {
    ForkJoinPool pool = new ForkJoinPool();

    @Override
    public void multiply(int[][] A, int[][] B, int[][] C) {
        int size = A[0].length;
        int numberOfForks = 8*6; // chosen via experimentation, 8 cores * 4 for granularity
        JoinForkMatrixMultiply fja = new JoinForkMatrixMultiply(A, B, C, 0, A.length, A.length, size, numberOfForks);
        pool.invoke(fja);

    }

    @Override
    public void add(int[][] A, int[][] B, int[][] C) {
        ForkJoinMatrixAdd fja = new ForkJoinMatrixAdd(A, B, C,0, A[0].length, A.length);
        pool.invoke(fja);
    }

    @Override
    public void print(int[][] A, Timer t) {
        for (int row = 0; row < A.length; row++) {

            for (int col = 0; col < A[row].length; col++) {
                t.print(A[row][col] + " ");
            }
            t.print("\n");
        }
        t.print("\n");
    }
}
