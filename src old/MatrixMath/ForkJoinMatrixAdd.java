package MatrixMath;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class ForkJoinMatrixAdd extends RecursiveAction {
    // code to calculate entry at [row][col] of the resulting matrix
    // for adding using ForkJoin Actions

    int[][] first, second, result;
    int rowStart, rowEnd, colSize;

    private int sliceLength = 8*6; // how many subtasks we'll create to do the work

    public ForkJoinMatrixAdd(int[][]first, int[][] second, int[][] result, int row, int rowEnd, int colSize){
        this.first = first;
        this.second = second;
        this.result = result;
        this.rowStart = row;
        this.rowEnd = rowEnd;
        this.colSize = colSize;

    }

    protected synchronized void execute(){
        for (int row = rowStart; row< rowEnd; row++){
            for (int i = 0; i < colSize; i++){
                this.result[row][i] = first[row][i] + second[row][i];
            }
        }

    }

    private List<ForkJoinMatrixAdd> createSubtasks() {
        List<ForkJoinMatrixAdd> subtasks =
                new ArrayList<ForkJoinMatrixAdd>();
        for (int r = 0; r < rowEnd; r += sliceLength) {
            int e = r + sliceLength;
            if (e > rowEnd)
                e = rowEnd;
            subtasks.add(new ForkJoinMatrixAdd(first, second, result, r, e, colSize));
        }
        return subtasks;
    }

    @Override
    protected void compute() {
        if (rowEnd - rowStart <= sliceLength){
            execute();
            return;
        } else {
            invokeAll(createSubtasks());
        }

    }
}
