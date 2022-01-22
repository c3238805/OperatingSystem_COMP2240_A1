/*  
    /============================\
    |  COMP2240 Assignment 1     | 
    |  Name : Ni Zeng            | 
    |  Student Number : c3238805 |
    \============================/   */
//Introduction of class:    superclass Super_algorithm
// superclass for sub_FBV , sub_FCFS ,sub_LTR, sub_SRT
import java.util.LinkedList;

public abstract class Super_algorithm  {
    

    public abstract void addProcessor(Processor[] processors);    // add processor.
    public abstract Processor getP(int index); // return processors in class.
    public abstract void algorithm();   // processor algorithm
    public abstract LinkedList<String> StartTimeLog();
    public abstract int TurnaroundTime(String processorId);  // get each processor's turnaround time
    public abstract int WaitingTime (String processorId);

    public abstract double avgTurnaround(); // return average turnaround time
    public abstract double avgWaiting();    // return average waiting Time 
    
    public abstract void addDisp(Processor disp);
    public abstract String toString();
}