package MatrixMath;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Timer{
    String fileName;
    BufferedWriter writer;

    Timer(String filename) {
        this.fileName = filename;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
            writer.write("Starting MatrixMath.Timer\n");
        } catch (IOException ioe){
            System.out.println(ioe.getMessage());
        }

    }

    long writeStartTime(String methodName){
        print(methodName + "\n");

        return System.currentTimeMillis();
    }

    void writeElapsedTime(String methodName, long startTime){
        long timeEl = System.currentTimeMillis() - startTime;
        print("DONE: " + methodName + ", execution time: " + timeEl + "\n");
    }

    void print(String toPrint){
        try {
            writer.append(toPrint);
        } catch (IOException ioe){
            System.out.println(ioe.getMessage());
        }

    }

    void close(){
        try {
            writer.close();
        } catch (IOException ioe){
            System.out.println(ioe.getMessage());
        }

    }

}
