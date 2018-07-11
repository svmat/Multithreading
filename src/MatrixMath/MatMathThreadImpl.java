package MatrixMath;

import java.util.ArrayList;
import java.util.List;

public class MatMathThreadImpl implements MatMath {
    List<Thread> threads = new ArrayList<>();

    @Override
    public void add(int[][] A, int[][] B, int[][] C) {
        for (int row = 0; row < A.length; row++){
            Thread t = new Thread( new RowColProdAddExec(A, B, C, row, A[0].length));
            t.run();
            threads.add(t);
        }
        for (int i = 0; i < threads.size(); i++) {
            try {
                threads.get(i).join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void multiply(int[][] A, int[][] B, int[][] C) {
        int size = A[0].length;
        for (int row = 0; row < A.length; row++){
            for (int col = 0; col < A[row].length;col++) {
                Thread t = new Thread( new RowColProdMultiplyExec(A, B, C, row, col, size));
                t.run();
                threads.add(t);

            }
        }
        for (int i = 0; i < threads.size(); i++) {
            try {
                threads.get(i).join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
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
