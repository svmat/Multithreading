package MatrixMath;

import java.util.stream.IntStream;

public class MatMathStreamImpl implements MatMath {

    @Override
    public synchronized void add(int[][] A, int[][] B, int[][] C) {
        int size = A[0].length;
        IntStream.range(0, A.length).parallel().forEach(ind -> new RowColProdAddExec(A, B, C, ind, size).run());
    }

    @Override
    public synchronized void multiply(int[][] A, int[][] B, int[][] C) {
        int size = A[0].length;
        IntStream.range(0, A.length).parallel().forEach(row -> {
            IntStream.range(0, size).forEach(
                    col -> new RowColProdMultiplyExec(A, B, C, row, col,size).run());
        });
    }

    @Override
    public synchronized void print(int[][] A, Timer t) {
        IntStream.range(0, A.length).forEach(row -> {
            IntStream.range(0,  A[row].length).forEach(col -> t.print(A[row][col] + " "));
            t.print("\n");});
        t.print("\n");
        }

}
