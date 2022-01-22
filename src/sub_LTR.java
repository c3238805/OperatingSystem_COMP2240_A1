/*  
    /============================\
    |  COMP2240 Assignment 1     | 
    |  Name : Ni Zeng            | 
    |  Student Number : c3238805 |
    \============================/   */
//Introduction of class:sub_LTR<T> ,  subclass: LTR Algorithm
// this class holds the calculation for LTR
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

public class sub_LTR<T> extends Super_algorithm implements Comparator<Processor>{
    
    private Processor[] processors;
    private ArrayList<Processor> LTR_order = new ArrayList<>();   // linkedlist to store the processor in order
    private Processor disp;
    private ArrayList<Processor> LTR_Timeslots = new ArrayList<>();    // arrayList of time slot for LTR algorithm.
    private int counter = 0;    // counter to check winner 
    private int randomCounter = 0;
    private int[] randomNumber;
    @Override
    public void addProcessor(Processor[] processors) {
       this.processors = processors;
        
    }
    public void addRandomNumber(int [] randomNumber){
        this.randomNumber = randomNumber;
    }

    @Override
    public Processor getP(int index) {
       
        return processors[index];
    }

    @Override
    public void algorithm() {
        //SRT's algorithm
        if(LTR_Timeslots.isEmpty()){
            for(int i=0;i<processors.length;i++){
                if(processors[i].getarrive() <= LTR_Timeslots.size()){
                    LTR_order.add(processors[i]);
                    processors[i].setStatus(true);
                }            
            }
        } 
        if(LTR_order.isEmpty()){
            // check if any other processor arrive at current time(LTR_Timeslots.size())
            for(Processor x: processors){
                // only loop though if processor not been added into first priorirty queue
                    if((x.getStatus() == false) && (x.getarrive() <= LTR_Timeslots.size())){
                        LTR_order.add(x);
                        x.setStatus(true);                   
                    }
                }
        }

        while(!LTR_order.isEmpty()){
            counter = 0;      // reset counter  
           // check if any other processor arrive at current time(LTR_Timeslots.size())
           for(Processor x: processors){
            // only loop though if processor not been added into first priorirty queue
                if((x.getStatus() == false) && (x.getarrive() <= LTR_Timeslots.size())){
                    // if last processor in timeSlot is interrupted 
                    if(LTR_Timeslots.get(LTR_Timeslots.size()-1) == LTR_order.get(LTR_order.size()-1)){
                        if((LTR_Timeslots.size()-1) == x.getarrive()){  // if x arrive at the same time when last processor interrupted
                            if(LTR_order.size() > 1){
                                LTR_order.add(LTR_order.size()-1,x);// x add before last processor 
                                x.setStatus(true);
                                
                            }
                            else {  // when there is only one processor in LTR_order
                                LTR_order.add(0,x);// x add before last processor 
                                x.setStatus(true);
                            }                            
                        }
                        else {
                            LTR_order.add(LTR_order.size()-1,x);
                            x.setStatus(true);
                        }
                        
                    }
                    else {
                        LTR_order.add(x);
                        x.setStatus(true);
                    }
                    
                    
                }
            }
       
            // if there is only one processor ready, then the winner is that processor
            if(LTR_order.size() == 1){
                // add dis before running the processor p
                for(int s=0 ; s< disp.getexecSize();s++){
                    LTR_Timeslots.add(disp);
                }
                for(int i = 0 ; i< 4 ; i++){
                    if(LTR_order.get(0).getRemains() == 0){
                        break;
                    }
                    LTR_Timeslots.add(LTR_order.get(0));  // add number of processor into number of Timeslots                                        
                    LTR_order.get(0).setRemains(1);
                }
                if(LTR_order.get(0).getRemains() == 0){
                    LTR_order.remove(0);    // if processor finished executeSize
                    
                }
                else {
                    // move back to LTR_order if not finished
                    LTR_order.add(LTR_order.remove(0)); 
                }
                 
                randomCounter ++;      
            }
            else {
                counter = 0;    // reset counter 
                while(counter<=randomNumber[randomCounter]){
                    for(Processor p : LTR_order){
                        counter += p.gettickets();
                    // when winner found
                        if(counter > randomNumber[randomCounter]){
                            // add dis before running the processor p
                            for(int s=0 ; s< disp.getexecSize();s++){
                                LTR_Timeslots.add(disp);
                            }
                            for(int i = 0 ; i< 4 ; i++){
                                if(p.getRemains() == 0){
                                    break;  // if processor finished executeSize
                                }
                                LTR_Timeslots.add(p);  // add number of processor into number of Timeslots                                        
                                p.setRemains(1);
                            }
                            if(p.getRemains() == 0){    // if processor finished executeSize
                                LTR_order.remove(p);
                            }
                            else {
                                // move back to LTR_order if not finished
                                LTR_order.remove(p);
                                LTR_order.add(p);
                            }
                            break; 
                        } 
                                      
                    }
                }
                randomCounter ++;
            }           
        }        
    }
    public boolean checkAllComplete(){
        boolean isCompleted = true;
        int minarrive = 0;
        // if LTR_order is empty but there is processor to arrive later 
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
            int timeslotsSize = LTR_Timeslots.size();
            for(int i = 0; i<(minarrive - timeslotsSize)  ; i++){
                LTR_Timeslots.add(idle);    // add idle time untill next arrive
            }
        }
        return isCompleted;
    }

    @Override
    public LinkedList<String> StartTimeLog(){  
        // return all processor's start Time and processors' id 
        LinkedList<String> TimeLog = new LinkedList<>();
        int ts = 0;      
        for(int i=0;i<LTR_Timeslots.size();i++){
            if(LTR_Timeslots.get(i).getid() == "disp" && LTR_Timeslots.get(i+1).getid() != "disp"){
                ts = (i+1);  // when convert from number of array to time must +1, for[i] finish process at time:i+1
                if(i+1<=LTR_Timeslots.size()){
                    TimeLog.add("T"+ts+": "+LTR_Timeslots.get(i+1).getid()) ;
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
        for(int i=LTR_Timeslots.size();i>0; i--){
            if(LTR_Timeslots.get(i-1).getid().matches(processorId)){
                tr = i - LTR_Timeslots.get(i-1).getarrive();  // find when the processor acturally finished 
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
        for(int i=LTR_Timeslots.size();i>0; i--){
            if(LTR_Timeslots.get(i-1).getid().matches(processorId)){
                T2 = i;  // Find the time unit when processorId finished
                break;
            }
        }
        
        tw = (T2 - LTR_Timeslots.get(T2-1).getarrive())-(LTR_Timeslots.get(T2-1).getexecSize());

        return tw;
    }

	@Override
	public void addDisp(Processor disp) {
		this.disp = disp;
	}

    @Override
    public String toString() {
		System.out.println("\nLTR:");

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
    public int compare(Processor o1, Processor o2) {
        if (o1.getarrive() == o2.getarrive()){            
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
