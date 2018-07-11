
import MatrixMath.Timer;

import java.util.Random;
import java.util.stream.*;

public class MatrixClassImpl {
    static IntStream create(int n){
        return IntStream.range(0,n);
    }
    static void doAdd(int i,int j,int[][]A,int[][]B,int[][]C){
        C[i][j] = A[i][j] + B[i][j];
    }
    static void add(int[][]A, int[][]B,int[][]C){
        System.out.println(A.length);
        System.out.println(A[0].length);
        create(A.length).parallel()
                .forEach (i ->create(A[0].length).parallel()
                        .forEach(j -> doAdd(i,j,A,B,C)));
    }
    static void mult(int[][]A, int[][]B,int[][]C){
        create(A.length)
                .parallel()
                .forEach (i ->create(B[0].length)
                        .parallel()
                        .forEach(j ->
                                C[i][j] = create(A[0].length).parallel()
                                        .map(k -> A[i][k] * B[k][j])
                                        .reduce(0, (x,y) -> x+y)));
    }

    static void multiSeq(int[][]A, int[][]B,int[][]C){
        for (int k=0; k< A.length; k++){
            for (int j = 0; j < A[0].length; j++){
                C[k][j] = A[k][j] + B[k][j];
            }
        }
    }

//    public static void main(String[] args) {
//        int SIZE = 1000;
//        int[][] A1 = new int[SIZE][SIZE];
//        int[][] B1 = new int[SIZE][SIZE];
//        int[][]C1 = new int[SIZE][SIZE];
//        // code to initialize A,B,C,D
//        Random rand = new Random();
//        Timer timeCount = new Timer();
//        long start;
//
//        IntStream.range(0, SIZE).forEach(rowInt -> {
//            IntStream.range(0, SIZE).forEach(colInt -> {
//                A1[rowInt][colInt] = rand.nextInt(20);
//                B1[rowInt][colInt] = rand.nextInt(20);
//            });
//        });
//
//        //warm up
//        mult(A1,B1,C1);
//
//        start = timeCount.writeStartTime("Adding two matrices sequentially CLASS VERSION");
//        multiSeq(A1,B1,C1);
//        timeCount.writeElapsedTime("Adding two matrices sequentially", start);
//
//
//        start = timeCount.writeStartTime("Adding two matrices parrallel CLASS VERSION");
//        mult(A1,B1,C1);
//        timeCount.writeElapsedTime("Adding two matrices parrallel", start);
//
//
//        timeCount.close();
//        for (int i=0; i<2; i++){
//            for (int j =0; j<2; j++)
//                System.out.print(C1[i][j]);
//            System.out.println();
//        }
//    }
}
