/*  
    /============================\
    |  COMP2240 Assignment 1     | 
    |  Name : Ni Zeng            | 
    |  Student Number : c3238805 |
    \============================/   */
//Introduction of class: subclass FCFS
// this class holds the calculation for FCFS
import java.util.Comparator;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;

public class sub_FCFS<T> extends Super_algorithm implements Comparator<Processor> {
    
    private Processor[] processors;
    private ArrayList<Processor> FCFS_order = new ArrayList<>();   // linkedlist to store the processor in order
    private Processor disp;
    private ArrayList<Processor> FCFS_Timeslots = new ArrayList<>();    // arrayList of time slot for FCFS algorithm.

    public sub_FCFS(){
        
    }

    @Override
    public void addProcessor(Processor [] processors ) {
        this.processors = processors;     // add all processor into FCFS class, and store it as variable
    }

    @Override
    public Processor getP(int index) {
       
        return processors[index];
    }

    @Override
    public void algorithm(){
        //FCFS's algorithm
        if(FCFS_Timeslots.isEmpty()){
            for(int i=0;i<processors.length;i++){
                if(processors[i].getarrive() <= FCFS_Timeslots.size()){
                    FCFS_order.add(processors[i]);
                    processors[i].setStatus(true);
                }            
            }
        }
        if(FCFS_order.isEmpty()){
            // check if any other processor arrive at current time(FCFS_Timeslots.size())
            for(Processor x: processors){
                // only loop though if processor not been added into first priorirty queue
                    if((x.getStatus() == false) && (x.getarrive() <= FCFS_Timeslots.size())){
                        FCFS_order.add(x);
                        x.setStatus(true);                   
                    }
                }
        }
        // sort all the processors' order in the linkedlist using the comparetor
        Collections.sort(FCFS_order, new sub_FCFS());
        
        while(!FCFS_order.isEmpty()){
            // check if any other processor arrive at current time(FCFS_Timeslots.size())
           for(Processor x: processors){
            // only loop though if processor not been added into first priorirty queue
                if((x.getStatus() == false) && (x.getarrive() <= FCFS_Timeslots.size())){
                    FCFS_order.add(x);
                    x.setStatus(true);                   
                }
            }
            // sort all the processors' order in the linkedlist using the comparetor
            Collections.sort(FCFS_order, new sub_FCFS()); 

            // add unit of disp time before loading the processor
            for(int s=0 ; s< disp.getexecSize();s++){
                FCFS_Timeslots.add(disp);
            }
            // add number of processor into number of Timeslots base on the processor's execSize.
            for(int i =0;i< FCFS_order.get(0).getexecSize(); i++){
                FCFS_Timeslots.add(FCFS_order.get(0));  // add first processor in the queue into FCFS_Timeslots
                FCFS_order.get(0).setRemains(1);
            }
            if(FCFS_order.get(0).getRemains() == 0){
                FCFS_order.remove(0);    // if processor finished executeSize                                
            }  
           // check if any other processor arrive at current time(FCFS_Timeslots.size())
           for(Processor x: processors){
            // only loop though if processor not been added into first priorirty queue
                if((x.getStatus() == false) && (x.getarrive() <= FCFS_Timeslots.size())){
                    FCFS_order.add(x);
                    x.setStatus(true);                   
                }
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
            int timeslotsSize = FCFS_Timeslots.size();
            for(int i = 0; i<(minarrive - timeslotsSize)  ; i++){
                FCFS_Timeslots.add(idle);    // add idle time untill next arrive
            }
        }
        return isCompleted;
    }
    @Override
    public LinkedList<String> StartTimeLog(){  
        // return all processor's start Time and processors' id 
        LinkedList<String> TimeLog = new LinkedList<>();
        int ts = 0;      
        for(int i=0;i<FCFS_Timeslots.size();i++){
            if(FCFS_Timeslots.get(i).getid() == "disp" && FCFS_Timeslots.get(i+1).getid() != "disp"){
                ts = (i+1);  // when convert from number of array to time must +1, for[i] finish process at time:i+1
                if(i+1<FCFS_Timeslots.size()){
                    TimeLog.add("T"+ts+": "+FCFS_Timeslots.get(i+1).getid()) ;
                }               
            }
        }
        // return "T"+ ts + ": " + processor'Id
        return TimeLog;     
    }

    @Override
    public int TurnaroundTime(String processorId) {
        // calculate the processor's turnaround time by processorId 
        int tr = 0;      
        for(int i=FCFS_Timeslots.size();i>0; i--){
            if(FCFS_Timeslots.get(i-1).getid().matches(processorId)){
                tr = i - FCFS_Timeslots.get(i-1).getarrive();  // find when the processor acturally finished 
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
        for(int i=FCFS_Timeslots.size();i>0; i--){
            if(FCFS_Timeslots.get(i-1).getid().matches(processorId)){
                T2 = i;  // Find the time unit when processorId finished
                break;
            }
        }
        
        tw = (T2 - FCFS_Timeslots.get(T2-1).getarrive())-(FCFS_Timeslots.get(T2-1).getexecSize());

        return tw;
    }

    @Override
    public int compare(Processor o1, Processor o2) {
        if(o1.getarrive() > o2.getarrive()){
            return 1;   // true  , o2 before o1         
        }
        else if (o1.getarrive() == o2.getarrive()){            
            // if the arrive time is the same then compare it with processor's ID
            if(Character.compare(o1.getid().charAt(1), o2.getid().charAt(1)) < 0){
                return -1;
            }
            else 
                return 1;
        }
        else {
            return -1; // false  , o1 before o2    
        }
    }

	@Override
	public void addDisp(Processor disp) {
		this.disp = disp;
		
	}

//==========================================

	@Override
	public String toString() {
		System.out.println("FCFS:");
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
