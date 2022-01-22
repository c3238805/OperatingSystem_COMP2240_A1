/*  
    /============================\
    |  COMP2240 Assignment 1     | 
    |  Name : Ni Zeng            | 
    |  Student Number : c3238805 |
    \============================/   */
//Introduction of class:sub_FBV<T> ,  subclass: FBV Algorithm
// this class holds the calculation for FBV
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class sub_FBV<T> extends Super_algorithm implements Comparator<Processor>{

    private Processor[] processors;
    private Processor disp;

    private Queue<Processor> first = new LinkedList<>();
    private Queue<Processor> secend = new LinkedList<>();
    private Queue<Processor> third = new LinkedList<>();
    private Queue<Processor> bottom = new LinkedList<>();
    private ArrayList<Processor> FBV_order = new ArrayList<>();   // linkedlist to store the processor in order

    private ArrayList<Processor> FBV_Timeslots = new ArrayList<>();    // arrayList of time slot for FBV algorithm.

    
    public sub_FBV(){

    }

    @Override
    public void addProcessor(Processor[] processors) {
        this.processors = processors;     // add all processor into FBV class, and store it as variable
        
    }

    @Override
    public Processor getP(int index) {
        return processors[index];
    }

    @Override
    public void algorithm() {
        // add all processor into FBV_order, then sort the order using comparetor 
        for(int i=0;i<processors.length;i++){
            FBV_order.add(processors[i]);
        }
        if(FBV_Timeslots.isEmpty()){
            for(int i=0;i<processors.length;i++){
                if(processors[i].getarrive() <= FBV_Timeslots.size()){
                    FBV_order.add(processors[i]);
                    processors[i].setStatus(true);
                    first.add(processors[i]);
                }            
            }
        }
        if(FBV_order.isEmpty()){
            // check if any other processor arrive at current time(FBV_Timeslots.size())
            for(Processor x: processors){
                // only loop though if processor not been added into first priorirty queue
                    if((x.getStatus() == false) && (x.getarrive() <= FBV_Timeslots.size())){
                        FBV_order.add(x);
                        first.add(x);
                        x.setStatus(true);                   
                    }
                }
        }
        Collections.sort(FBV_order, new sub_FBV());

        //picking processor from prority queue to run next 
        firstLevel();
        secondLevel();
        thirdLevel();
        bottomLevel();
        
    }

    public void firstLevel(){
        // check if any other processor arrive at current time(FBV_Timeslots.size())
        for(Processor p: FBV_order){
            // only loop though if processor not been added into first priorirty queue
            if((p.getStatus() == false) && (p.getarrive() <= FBV_Timeslots.size())){
                first.add(p);
                p.setStatus(true);
            }
        }
        // check level 4 queue if processor waiting or running more than 32 ms
        if(!bottom.isEmpty()){
            int bottomSize = bottom.size();
            for(int i= 0 ;i<bottomSize;i++){               
                if((FBV_Timeslots.size() - bottom.peek().getBottomEnter()) > 32 ){
                    first.add(bottom.remove());       // add to first prority queue
                }
                else {
                    bottom.add(bottom.remove());
                }                
            }
        }
        while(!first.isEmpty()){

            // disp run before switching to different processor 
            for(int s=0 ; s< disp.getexecSize();s++){
                FBV_Timeslots.add(disp);
            }
           
            // run the first element in first level queue.
            first.peek().setRemains(1); // first level only run 1 quanta unit
            FBV_Timeslots.add(first.peek());    // first level only run 1 quanta unit
            //if processor finished , remove from the queue
            if(first.peek().getRemains() == 0 ){               
                first.remove();
            }
            else {  // if processor is not complete, move into level 2 queue
                secend.add(first.remove());
            }
            firstLevel();
        }    
    }

    public void secondLevel(){
        // check if first level completed then run level 2 queue
        firstLevel();
        // run level 2 
        while(!secend.isEmpty()){
            // check level 4 queue if processor waiting or running more than 32 ms
            if(!bottom.isEmpty()){
                int bottomSize = bottom.size();
                for(int i= 0 ;i<bottomSize;i++){               
                    if((FBV_Timeslots.size() - bottom.peek().getBottomEnter()) > 32 ){
                        first.add(bottom.remove());       // add to first prority queue
                    }
                    else {
                        bottom.add(bottom.remove());
                    }                
                }
            }
            // disp run before switching to different processor 

            for(int s=0 ; s< disp.getexecSize();s++){
                FBV_Timeslots.add(disp);
            }
        
            // run the first element in second level queue.
            
            for(int i=0; i<2 ; i++){    // loop 2 times
                secend.peek().setRemains(1); // second level only run 2 quanta unit
                FBV_Timeslots.add(secend.peek());    // second level only run 2 quanta unit
                if(secend.peek().getRemains() ==0 ){
                    break;
                }
            }

            //if processor finished , remove from the queue
            if(secend.peek().getRemains() == 0 ){
                secend.remove();
            }
            else {  // if processor is not complete, move into level 3 queue
                third.add(secend.remove());
            }
            // check if first level completed then run level 2 queue
            firstLevel();
            secondLevel();
        }
    }

    public void thirdLevel(){
        // check if first and second level completed then run level 3 queue
        firstLevel();
        secondLevel();
        // run level 3 
        while(!third.isEmpty()){
            // check level 4 queue if processor waiting or running more than 32 ms
            if(!bottom.isEmpty()){
                int bottomSize = bottom.size();
                for(int i= 0 ;i<bottomSize;i++){               
                    if((FBV_Timeslots.size() - bottom.peek().getBottomEnter()) > 32 ){
                        first.add(bottom.remove());       // add to first prority queue
                    }
                    else {
                        bottom.add(bottom.remove());
                    }                
                }
            }
            // disp run before switching to different processor 

            for(int s=0 ; s< disp.getexecSize();s++){
                FBV_Timeslots.add(disp);
            }
           
            // run the first element in third level queue.
            for(int i=0; i< 4 ;i++){
                third.peek().setRemains(1); // third level only run 4 quanta unit
                FBV_Timeslots.add(third.peek());    // third level only run 4 quanta unit
                if(third.peek().getRemains() ==0){
                    break;
                }
            }

            //if processor finished , remove from the queue
            if(third.peek().getRemains() == 0 ){
                third.remove();
            }
            else {  // if processor is not complete, move into level 4 queue
                third.peek().setBottomEnter(FBV_Timeslots.size()); // record the Time when processor enter bottom queue
                bottom.add(third.remove());

            }
            // check if pervious level completed 
            firstLevel();
            secondLevel();
            thirdLevel();
        }
    }

    public void bottomLevel(){
        // check if pervious level completed 
        firstLevel();
        secondLevel();
        thirdLevel();
        // run level 2 
        while(!bottom.isEmpty()){
            // disp run before switching to different processor 

            for(int s=0 ; s< disp.getexecSize();s++){
                FBV_Timeslots.add(disp);
            }
            
            // run the first element in bottom level queue.
            for(int i=0;i<4; i++){
                bottom.peek().setRemains(1); // bottom level only run 4 quanta unit
                FBV_Timeslots.add(bottom.peek());    // bottom level only run 4 quanta unit
                if(bottom.peek().getRemains() ==0){
                    break;
                }
            }
            //if processor finished , remove from the queue
            if(bottom.peek().getRemains() == 0 ){
                bottom.remove();
            }
            else{
                // move processor into bottom queue
                bottom.add(bottom.remove());
            }
            // check if pervious level completed 
            firstLevel();
            secondLevel();
            thirdLevel();
            bottomLevel();
        }
    }
    public boolean checkAllComplete(){
        boolean isCompleted = true;
        int minarrive = 0;
        // if FBV_order is empty but there is processor to arrive later 
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
            int timeslotsSize = FBV_Timeslots.size();
            for(int i = 0; i<(minarrive - timeslotsSize)  ; i++){
                FBV_Timeslots.add(idle);    // add idle time untill next arrive
            }
        }
        return isCompleted;
    }

    @Override
    public LinkedList<String> StartTimeLog(){  
        // return all processor's start Time and processors' id 
        LinkedList<String> TimeLog = new LinkedList<>();
        int ts = 0;      
        for(int i=0;i<FBV_Timeslots.size();i++){
            if(FBV_Timeslots.get(i).getid() == "disp" && FBV_Timeslots.get(i+1).getid() != "disp"){
                ts = (i+1);  // when convert from number of array to time must +1, for[i] finish process at time:i+1
                if(i+1<=FBV_Timeslots.size()){
                    TimeLog.add("T"+ts+": "+FBV_Timeslots.get(i+1).getid()) ;
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
        for(int i=FBV_Timeslots.size();i>0; i--){
            if(FBV_Timeslots.get(i-1).getid().matches(processorId)){
                tr = i - FBV_Timeslots.get(i-1).getarrive();  // find when the processor acturally finished 
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
        for(int i=FBV_Timeslots.size();i>0; i--){
            if(FBV_Timeslots.get(i-1).getid().matches(processorId)){
                T2 = i;  // Find the time unit when processorId finished
                break;
            }
        }
        
        tw = (T2 - FBV_Timeslots.get(T2-1).getarrive())-(FBV_Timeslots.get(T2-1).getexecSize());

        return tw;
    }

    @Override
    public void addDisp(Processor disp) {
        this.disp = disp;
    }

    @Override
    public String toString() {
		System.out.println("\nFBV:");
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
