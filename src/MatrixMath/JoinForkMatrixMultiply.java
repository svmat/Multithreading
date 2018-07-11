package MatrixMath;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.*;

public class JoinForkMatrixMultiply extends RecursiveAction{

    // code to calculate entry at [row][col] of the resulting matrix
    // for multiplication using ForkJoin Actions

    int[][] first, second, result;
    int startRow, endRow, rowSize, colSize;

    private int divCount = 1; // how many subtasks we'll create to do the work

    public JoinForkMatrixMultiply(int[][]first, int[][] second, int[][] result, int startRow, int endRow, int rowSize, int colSize, int divCount){
        this.first = first;
        this.second = second;
        this.result = result;
        this.startRow = startRow;
        this.endRow   = endRow;
        this.rowSize = rowSize;
        this.colSize = colSize;
        this.divCount = divCount;
    }

    protected synchronized void execute(){
        for(int row = startRow; row < endRow; row++) {
            for (int column = 0; column < colSize; column++) {
                for (int i = 0; i < colSize; i++) {
                    this.result[row][column] += first[row][i] * second[i][column];
                }
            }
        }
    }

    private List<JoinForkMatrixMultiply> createSubtasks() {
        List<JoinForkMatrixMultiply> subtasks =
                new ArrayList<JoinForkMatrixMultiply>();
        int sliceLength = (rowSize / divCount) + 1;
        for (int r = 0; r < rowSize; r += sliceLength) {
            int e = r + sliceLength;
            if(e > rowSize)
                e = rowSize;
            subtasks.add(new JoinForkMatrixMultiply(first, second, result, r, e, rowSize, colSize, 1));
        }
        return subtasks;
    }

    @Override
    protected void compute() {
        if (divCount == 1){
            execute();
        } else {
            invokeAll(createSubtasks());
        }
    }
}
