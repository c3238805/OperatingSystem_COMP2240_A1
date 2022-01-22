/*  
    /============================\
    |  COMP2240 Assignment 1     | 
    |  Name : Ni Zeng            | 
    |  Student Number : c3238805 |
    \============================/   */
//Introduction of class:A1
//A1 is the main class of assignment 1
import java.io.*;

public class A1 {
    public static void main(String[] args) throws IOException{
        //   In order to start the A1 program,user must enter the path of the input file.

        String sourceFile = "datafile1.txt";
        // get the number of processor
        int processor_size = FileUtil.processorCounter(sourceFile);     // count the number of processor 
        // create an array to store all processor
        Processor [] processors = new Processor [processor_size];
                
        //read data from input file
        processors = FileUtil.ReadFile(sourceFile);     // read data from input file
        Processor disp = new Processor (FileUtil.getDisp());  // processor dispatch
        sub_FCFS<Processor> FCFS = new sub_FCFS<>();
        FCFS.addProcessor(processors);  // add all processors from read file into FCFS class
        FCFS.addDisp(disp);     // add dispatch time for processor
        // compare the each processor's priority and store into the FCFS's linkedlist
        FCFS.algorithm();
        while(FCFS.checkAllComplete() == false){ // check if all processor has finish
            FCFS.algorithm(); 
        }
        FCFS.toString();

      
        //read data from file 
        processors = FileUtil.ReadFile(sourceFile);     // read data from input file
        disp = new Processor (FileUtil.getDisp());  // processor dispatch
        sub_SRT<Processor> SRT = new sub_SRT<>();
        SRT.addProcessor(processors);  // add all processors from read file into SRT class
        SRT.addDisp(disp);     // add dispatch time for processor       
        SRT.algorithm(); // compare the each processor's priority and store into the SRT's ArrayList
        while(SRT.checkAllComplete() == false){ // check if all processor has finish
            SRT.algorithm(); 
        }
        SRT.toString();
       
        // read data from input file
        processors = FileUtil.ReadFile(sourceFile);   
        disp = new Processor (FileUtil.getDisp());  // processor dispatch  
        sub_FBV<Processor> FBV = new sub_FBV<>();
        FBV.addProcessor(processors);// add all processors from read file into FBV class
        FBV.addDisp(disp);     // add dispatch time for processor
        FBV.algorithm();
        while(FBV.checkAllComplete() == false){ // check if all processor has finish
            FBV.algorithm(); 
        }
        FBV.toString();


        processors = FileUtil.ReadFile(sourceFile);  
        disp = new Processor (FileUtil.getDisp());    
        sub_LTR<Processor> LTR = new sub_LTR<>();
        LTR.addProcessor(processors);
        LTR.addRandomNumber(FileUtil.readRandom(sourceFile));
        LTR.addDisp(disp);
        LTR.algorithm();
        while(LTR.checkAllComplete() == false){ // check if all processor has finish
            LTR.algorithm(); 
        }
        LTR.toString();

        System.out.println("\nSummary");
        System.out.println("Algorithm  Average Turnaround Time  Waiting Time");

        System.out.printf("%-10s ", "FCFS");
        System.out.printf("%-24s ", String.format("%.2f",FCFS.avgTurnaround()));
        System.out.printf("%-8s ", String.format("%.2f",FCFS.avgWaiting()));
        System.out.println();

        System.out.printf("%-10s ", "SRT");
        System.out.printf("%-24s ", String.format("%.2f",SRT.avgTurnaround()));
        System.out.printf("%-8s ", String.format("%.2f",SRT.avgWaiting()));
        System.out.println();

        System.out.printf("%-10s ", "FBV");
        System.out.printf("%-24s ", String.format("%.2f",FBV.avgTurnaround()));
        System.out.printf("%-8s ", String.format("%.2f",FBV.avgWaiting()));
        System.out.println();

        System.out.printf("%-10s ", "LTR");
        System.out.printf("%-24s ", String.format("%.2f",LTR.avgTurnaround()));
        System.out.printf("%-8s ", String.format("%.2f",LTR.avgWaiting()));
        System.out.println();
    }
}
