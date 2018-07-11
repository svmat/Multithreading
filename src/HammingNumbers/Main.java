package HammingNumbers;
/*
 * The system of processes for the generation of the ordered sequence of the Hamming numbers.
 * Produces now limited number of integers, then system finishes.
 * @author Sviatlana Matchenia
 * @version 1.0.1
 */
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import static HammingNumbers.Main.execute;

public class Main {
    protected static BlockingQueue<Integer> input_t1 = new PriorityBlockingQueue<>();
    protected static BlockingQueue<Integer> input_t2 = new PriorityBlockingQueue<>();
    protected static BlockingQueue<Integer> input_t3 = new PriorityBlockingQueue<>();
    protected static BlockingQueue<Integer> output_t1 = new PriorityBlockingQueue<>();
    protected static BlockingQueue<Integer> output_t2 = new PriorityBlockingQueue<>();
    protected static BlockingQueue<Integer> output_t3 = new PriorityBlockingQueue<>();
    protected static BlockingQueue<Integer> copy_queue = new ArrayBlockingQueue<Integer>(60);
    protected static BlockingQueue<Integer> print_queue = new ArrayBlockingQueue<Integer>(60);

    public static boolean execute = true;


    public static void main(String[] args){
        copy_queue.add(1);
        Thread[] threads = {
                new Thread(new Copier(copy_queue, print_queue, input_t1, input_t2, input_t3, 60)),
                new Thread(new Multiplier(2, input_t1, output_t1)),
                new Thread(new Multiplier(3, input_t2, output_t2)),
                new Thread(new Multiplier(5, input_t3, output_t3)),
                new Thread(new Merger(output_t1, output_t2, output_t3, copy_queue)),
                new Thread(new Printer(print_queue))
        };

        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();

            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }


    }
}

class Copier implements Runnable {
    /*
    * Process that replicates the data from its input queue into four output queues
     */
    BlockingQueue<Integer> input;
    BlockingQueue<Integer> printer;
    BlockingQueue<Integer> output1;
    BlockingQueue<Integer> output2;
    BlockingQueue<Integer> output3;
    int maxNumbers;

    Copier(BlockingQueue<Integer> input, BlockingQueue<Integer> printer, BlockingQueue<Integer> output1, BlockingQueue<Integer> output2, BlockingQueue<Integer> output3, int maxNumbers){
        this.input = input;
        this.printer = printer;
        this.output1 = output1;
        this.output2 = output2;
        this.output3 = output3;
        this.maxNumbers = maxNumbers;
    }
    @Override
    public void run() {
        int winner = 0;
        int numbersComputed = 0;
        while (execute) {
            try {
                winner = input.take();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            numbersComputed++;
            if(numbersComputed >= maxNumbers) {
                execute = false;
            }
            else {
                printer.add(winner);
                output1.add(winner);
                output2.add(winner);
                output3.add(winner);
            }
        }

//        System.out.println("Copy printer: finishing. Last Hamming mumber is " + winner);
    }
}

class Printer implements Runnable {
    /*
    * Process that takes the data from its input queue and visualizes them.
     */
    BlockingQueue<Integer> printer;

    Printer(BlockingQueue<Integer> printer){
        this.printer = printer;
    }

    @Override
    public void run() {
        int winner = 0;
        while (execute || !printer.isEmpty()){

            if(!printer.isEmpty()) {
                try {
                    winner = printer.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("----------------------->        Hamming Number: " + winner);
            }
        }
    }
}

class Merger implements Runnable {
    /*
    * Process that performs ordered merge on three input queues
     */
    BlockingQueue<Integer> input1;
    BlockingQueue<Integer> input2;
    BlockingQueue<Integer> input3;
    BlockingQueue<Integer> output;

    Merger(BlockingQueue<Integer> input1, BlockingQueue<Integer> input2, BlockingQueue<Integer> input3, BlockingQueue<Integer> out){
        this.input1 = input1;
        this.input2 = input2;
        this.input3 = input3;
        this.output = out;
    }

    @Override
    public void run() {

        while (execute){
//            System.out.println("Merger: waiting for all multipliers proceed");
            if (!input1.isEmpty() && !input2.isEmpty() && !input3.isEmpty()) {
//                System.out.println("Merger: all multipliers processed numbers. Merging");
                int val1 = input1.peek();
                int val2 = input2.peek();
                int val3 = input3.peek();

                int selected = val1;
                if(val2 < selected)
                    selected = val2;
                if(val3 < selected)
                    selected = val3;

//                System.out.println("Merger: selected value: " + selected);
                output.add(selected);

                if(val1 == selected)
                    input1.remove();
                if(val2 == selected)
                    input2.remove();
                if(val3 == selected)
                    input3.remove();
            }
        }
//        System.out.println("Merger: finishing.");
    }
}

class Multiplier implements Runnable {
    /*
    * Process that multiplies by n every number from its input queue, and inserts the result into its output queue
     */
    int multiplier;
    BlockingQueue<Integer> input = null;
    BlockingQueue<Integer> output = null;

    Multiplier(int m, BlockingQueue<Integer> input, BlockingQueue<Integer> output){
        multiplier = m;
        this.input = input;
        this.output = output;
    }
    @Override
    public void run() {
//        System.out.println("Multiplier " + multiplier + ": starting");
//        System.out.println("Multiplier " + multiplier + ": waiting for the input number");
        while (execute) {
            if (!input.isEmpty()) {
                try {
                    int result = input.take() * multiplier;
//                    System.out.println("Multiplier " + multiplier + ": number for output: " + result);
                    output.put(result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
//        System.out.println("Multiplier " + multiplier + ": finishing.");
    }
}
