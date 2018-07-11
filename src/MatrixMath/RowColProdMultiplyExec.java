package MatrixMath;

public class RowColProdMultiplyExec implements Runnable{
    // code to calculate entry at [row][col] of the resulting matrix
    // from a multiplication

    int[][] first, second, result;
    int row, col,size;

    RowColProdMultiplyExec(int[][]first, int[][] second, int[][] third, int row, int col, int size){

        this.first = first;
        this.second = second;
        this.result = third;
        this.row = row;
        this.col = col;
        this.size = size;
    }

    public void run(){
        this.result[row][col] = 0;
        for (int k=0; k< size; k++){
            this.result[row][col] += first[row][k]*second[k][col];
        }

    }
}
