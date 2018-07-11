package MatrixMath;

public class RowColProdAddExec implements Runnable {

    // code to calculate entry at [row][col] of the resulting matrix
    // for addition

    int[][] first, second, result;
    int row, size;

    RowColProdAddExec(int[][]first, int[][] second, int[][] third, int row, int size){

        this.first = first;
        this.second = second;
        this.result = third;
        this.row = row;
        this.size = size;
    }

    public void run(){
        for (int k=0; k< size; k++){
            this.result[row][k] = first[row][k]+ second[row][k];
        }
    }

}
