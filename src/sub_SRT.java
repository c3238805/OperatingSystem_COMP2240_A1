/*  
    /============================\
    |  COMP2240 Assignment 1     | 
    |  Name : Ni Zeng            | 
    |  Student Number : c3238805 |
    \============================/   */
//Introduction of class:    subclass SRT
// this class holds the calculation for SRT

import java.util.Comparator;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;

public class sub_SRT<T> extends Super_algorithm implements Comparator<Processor>{
    
    private Processor[] processors;
    private ArrayList<Processor> SRT_order = new ArrayList<>();   // linkedlist to store the processor in order
    private Processor disp;
    private ArrayList<Processor> SRT_Timeslots = new ArrayList<>();    // arrayList of time slot for SRT algorithm.

    public sub_SRT(){

    } 
    @Override
    public void addProcessor(Processor[] processors) {
        this.processors = processors;     
        
    }

    @Override
    public Processor getP(int index) {
        return processors[index];
    }

    @Override
    public void algorithm() {
        Processor temp = new Processor();
        boolean beenAdded = false;
        //SRT's algorithm
        if(SRT_Timeslots.isEmpty()){
            for(int i=0;i<processors.length;i++){
                if(processors[i].getarrive() <= SRT_Timeslots.size()){
                    SRT_order.add(processors[i]);
                    processors[i].setStatus(true);
                }            
            }
        }   
        if(SRT_order.isEmpty()){
            // check if any other processor arrive at current time(SRT_Timeslots.size())
            for(Processor x: processors){
                // only loop though if processor not been added into first priorirty queue
                    if((x.getStatus() == false) && (x.getarrive() <= SRT_Timeslots.size())){
                        SRT_order.add(x);
                        x.setStatus(true);                   
                    }
                }
        }
        Collections.sort(SRT_order, new sub_SRT());    
        while(!SRT_order.isEmpty()){             
            // add all processor into arrylist SRT_order
            for(int i=0;i<processors.length;i++){
                // if processor[i] is not in the SRT_Timeslots
                for(int j =0 ;j<SRT_Timeslots.size();j++){
                    if(SRT_Timeslots.get(j).getid() == processors[i].getid() ){
                        beenAdded = true;        
                        break;            
                    }
                }
                if(processors[i].getStatus() == true){
                    beenAdded = true;        
                }
                if( beenAdded == false && SRT_Timeslots.size()!=0){
                    if(processors[i].getarrive() <= SRT_Timeslots.size() ){
                        SRT_order.add(processors[i]);
                        processors[i].setStatus(true);
                    }                   
                }
                beenAdded = false;          
            }
            // sort all the processors' order in the Shortest remaining time order
            Collections.sort(SRT_order, new sub_SRT());        
            //Disp run before each processor.
            if(temp == null || temp != SRT_order.get(0)){
                 // add unit of disp time before loading the processor
                for(int s=0 ; s< disp.getexecSize();s++){
                    SRT_Timeslots.add(disp);
                }
            }
                       
            // compare the size of SRT_timeslots with the first element in SRT_order
            SRT_Timeslots.add(SRT_order.get(0));  // add number of processor into number of Timeslots                
            temp = SRT_order.get(0); // temp running processor
            SRT_order.get(0).setRemains(1);// update remaining Time for execuSize
            
            if(SRT_order.get(0).getRemains() == 0) { 
                temp = null;    // set running processor to null when finished                   
                SRT_order.remove(0);  // remove finished processor from array list
            }      
        }

    }
    public boolean checkAllComplete(){
        boolean isCompleted = true;
        int minarrive = 0;
        // if SRT_order is empty but there is processor to arrive later 
        for(Processor p: processors){
            if(p.getStatus() == false){
                isCompleted = false;
                if((p.getarrive() < minarrive) && minarrive !=0){
                    minarrive = p.getarrive();  
                }
                else if (minarrive == 0){
                    minarrive = p.getarrive();
                }
            }
        }
        if(isCompleted == false){
            Processor idle = new Processor();
            idle.setid("idle");
            int timeslotsSize = SRT_Timeslots.size();
            for(int i = 0; i<(minarrive - timeslotsSize)  ; i++){
                SRT_Timeslots.add(idle);    // add idle time untill next arrive
            }
        }
        return isCompleted;
    }

    @Override
    public int TurnaroundTime(String processorId) {
        // calculate the processor's turnaround time by processorId 
        int tr = 0;      
        for(int i=SRT_Timeslots.size();i>0; i--){
            if(SRT_Timeslots.get(i-1).getid().matches(processorId)){
                tr = i - SRT_Timeslots.get(i-1).getarrive();  // find when the processor acturally finished 
                break;
            }
        }
        return tr;  
    }

    @Override
    public int WaitingTime (String processorId){
        // calculate the  processor's WaitingTime time  
        // (T2 - arrive) - Execute
        int tw = 0;  
        int T2 = 0;

        // find end Time T2
        for(int i=SRT_Timeslots.size();i>0; i--){
            if(SRT_Timeslots.get(i-1).getid().matches(processorId)){
                T2 = i;  // Find the time unit when processorId finished
                break;
            }
        }
        
        tw = (T2 - SRT_Timeslots.get(T2-1).getarrive())-(SRT_Timeslots.get(T2-1).getexecSize());

        return tw;
    }

    @Override
    public void addDisp(Processor disp) {
        this.disp = disp;
    }

    @Override
    public int compare(Processor o1, Processor o2) {
        // compare the execute size
        if(o1.getRemains() > o2.getRemains()){
            return 1;
        }        
        else if (o1.getRemains() == o2.getRemains()){
            // if the arrive time is the same then compare it with processor's ID
            if(Character.compare(o1.getid().charAt(1), o2.getid().charAt(1)) < 0){
                return -1;
            }
            else 
                return 1;
        }
        else return -1;       
    }

    @Override
    public LinkedList<String> StartTimeLog(){  
        // return all processor's start Time and processors' id 
        LinkedList<String> TimeLog = new LinkedList<>();
        int ts = 0;      
        for(int i=0;i<SRT_Timeslots.size();i++){
            if(SRT_Timeslots.get(i).getid() == "disp" && SRT_Timeslots.get(i+1).getid() != "disp"){
                ts = (i+1);  // when convert from number of array to time must +1, for[i] finish process at time:i+1
                if(i+1<=SRT_Timeslots.size()){
                    TimeLog.add("T"+ts+": "+SRT_Timeslots.get(i+1).getid()) ;
                }                
            }
        }
        // return "T"+ ts + ": " + processor'Id
        return TimeLog;     
    }

    @Override
    public String toString() {
		System.out.println("\nSRT:");

        for(String list: StartTimeLog()){
            System.out.println(list);
        }
        System.out.println("\n"+"Process  Turnaround Time  Waiting Time");
        for(int i=0;i<processors.length;i++){
            System.out.printf("%-8s ", "p"+(i+1));
            System.out.printf("%-16s ", TurnaroundTime("p"+(i+1)));
            System.out.printf("%-8s ", WaitingTime("p"+(i+1)));
            System.out.println();
        }
        return "\n";
    }
    @Override
    public double avgTurnaround() {
        double sum = 0;
        for(int i=0;i<processors.length;i++){
            sum += TurnaroundTime("p"+(i+1));
        }
        return sum/processors.length;
    }

    @Override
    public double avgWaiting() {
        double sum = 0;
        for(int i=0;i<processors.length;i++){
            sum += WaitingTime("p"+(i+1));
        }
        return sum/processors.length;
    }
}
