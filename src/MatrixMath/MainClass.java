package MatrixMath;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * A test for matrix addition and multiplication sequentuelly and usin ForkJoin Framework
 * @author Sviatlana Matchenia
 * @version 1.0.1
 */

class MainClass {
    static final int SIZE_ADD = 1000;
    static final int SIZE_M = 1000;

    public static void main(String[] args) {
        int[][] A = new int[SIZE_ADD][SIZE_ADD];
        int[][] B = new int[SIZE_ADD][SIZE_ADD];
        int[][] C = new int[SIZE_M][SIZE_M];
        int[][] D = new int[SIZE_M][SIZE_M];
        int[][] r = new int[SIZE_ADD][SIZE_ADD];
        int[][] s = new int[SIZE_M][SIZE_M];
        Timer timeCount = new Timer("MatrixCalculationTiming.txt");
        long start;

        MatMath mSeq = new MatMathSeqImpl();  // sequential calculation
        MatMath mPool  = new MatMathForkJoinImpl(); //fork-join calculation
        MatMath mStream = new MatMathStreamImpl();
        MatMath mThread = new MatMathThreadImpl();
        MatMath mThreadPool = new MatMathThreadPoolImpl();

        // code to initialize A,B,C,D
        Random rand = new Random();
        IntStream.range(0, SIZE_ADD).forEach(rowInt -> {
            IntStream.range(0, SIZE_ADD).forEach(colInt -> {
                A[rowInt][colInt] = rand.nextInt(20);
                B[rowInt][colInt] = rand.nextInt(20);
            });
        });

        IntStream.range(0, SIZE_M).forEach(rowInt -> {
            IntStream.range(0, SIZE_M).forEach(colInt -> {
                C[rowInt][colInt] = rand.nextInt(20);
                D[rowInt][colInt] = rand.nextInt(20);
            });
        });

        // warmup
        mThread.multiply(C, D, s);
        mPool.multiply(C, D, s);
        mStream.multiply(C, D, s);
        mThreadPool.multiply(C,D,s);

        start = timeCount.writeStartTime("Adding two matrices sequentially");
        mSeq.add(A, B, r);
        timeCount.writeElapsedTime("Adding two matrices sequentially", start);

        start = timeCount.writeStartTime("Adding two matrices with ForkJoinPool");
        mPool.add(A, B, r);
        timeCount.writeElapsedTime("Adding two matrices with ForkJoinPool", start);

        start = timeCount.writeStartTime("Adding two matrices with Threads");
        mThread.add(A, B, r);
        timeCount.writeElapsedTime("Adding two matrices with Threads", start);

        start = timeCount.writeStartTime("Adding two matrices with Streams");
        mStream.add(A, B, r);
        timeCount.writeElapsedTime("Adding two matrices with Streams", start);

        start = timeCount.writeStartTime("Adding two matrices with ThreadPool");
        mThreadPool.add(A, B, r);
        timeCount.writeElapsedTime("Adding two matrices with ThreadPool", start);

        start = timeCount.writeStartTime("Multiplying to matrices sequentially");
        mSeq.multiply(C, D, s);
        timeCount.writeElapsedTime("Multiplying two matrices sequentially", start);
        //mSeq.print(s, timeCount);

        start = timeCount.writeStartTime("Multiplying two matrices with ForkJoinPool");
        mPool.multiply(C, D, s);
        timeCount.writeElapsedTime("Multiplying two matrices with ForkJoinPool", start);

        start = timeCount.writeStartTime("Multiplying two matrices with Threads");
        mThread.multiply(C, D, s);
        timeCount.writeElapsedTime("Multiplying two matrices with Threads", start);
        //mThread.print(s, timeCount);

        start = timeCount.writeStartTime("Multiplying two matrices with Streams");
        mStream.multiply(C, D, s);
        timeCount.writeElapsedTime("Multiplying two matrices with Streams", start);

        start = timeCount.writeStartTime("Multiplying two matrices with ThreadPool");
        mThreadPool.multiply(A, B, r);
        timeCount.writeElapsedTime("Multiplying two matrices with ThreadPool", start);

        timeCount.close();
    }

}