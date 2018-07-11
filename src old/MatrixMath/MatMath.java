package MatrixMath;

public interface MatMath {
    void multiply(int[][] A, int[][]B, int[][]C);  // multiply A and B into C
    void add(int[][]A, int[][]B, int[][]C);        // add A and B into C
    void print(int[][]A, Timer t);                          // pretty print A
}