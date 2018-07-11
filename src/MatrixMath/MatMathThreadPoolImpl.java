package MatrixMath;
import java.util.concurrent.*;

public class MatMathThreadPoolImpl implements MatMath {

    @Override
    public void multiply(int[][] A, int[][] B, int[][] C) {
        ExecutorService executor = Executors.newCachedThreadPool();
        int size = A[0].length;
        for (int row = 0; row < A.length; row++){
            for (int col = 0; col < A[row].length;col++) {
                executor.execute(new RowColProdMultiplyExec(A, B, C, row, col, size));

            }
        }
        executor.shutdown();
    }

    @Override
    public void add(int[][] A, int[][] B, int[][] C) {
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int row = 0; row < A.length; row++){
            executor.execute(new RowColProdAddExec(A, B, C, row, A[0].length));
        }

        executor.shutdown();

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
