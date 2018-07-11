package MatrixMath;

public class MatMathSeqImpl implements MatMath{

    @Override
    public synchronized void add(int[][] A, int[][] B, int[][] C) {
        for (int row = 0; row < A.length; row++){
            RowColProdAddExec exec = new RowColProdAddExec(A, B, C, row, A[0].length);
            exec.run();
        }
    }

    @Override
    public synchronized void multiply(int[][] A, int[][] B, int[][] C) {
        int size = A[0].length;
        for (int row = 0; row < A.length; row++){
            for (int col = 0; col < A[row].length;col++) {
                RowColProdMultiplyExec exec = new RowColProdMultiplyExec(A, B, C, row, col, size);
                exec.run();
            }
        }
    }

    @Override
    public synchronized void print(int[][] A, Timer t) {
        for (int row = 0; row < A.length; row++) {

            for (int col = 0; col < A[row].length; col++) {
                t.print(A[row][col] + " ");
            }
            t.print("\n");

        }
        t.print("\n");
    }
}
