/*  
    /============================\
    |  COMP2240 Assignment 1     | 
    |  Name : Ni Zeng            | 
    |  Student Number : c3238805 |
    \============================/   */
//Introduction of class:

public class Processor  {
    private String id;  // id of the processor
    private int arrive; //processor's arrive time
    private int execSize;   //processor execution Size.
    private int tickets;    // processor's tickets
    private int remainDuration;     // processor's remaining time
    private boolean Status = false;
    private int FBV_bottomEnter = 0;    // record the time for FBV when processor enter bottom queue

    public Processor(){
        this.id = null;     //inital variable id as null;
        this.arrive = 0;    // inital variable arrive time to 0
        this.execSize = 0;  // inital variable execSize to 0
        this.tickets = 0;   //inital variable tickets to 0
        this.remainDuration = this.execSize;    // initial remainDuration same as execuSize
    }
    public Processor(int disp){ // processor disp
        this.id = "disp";
        this.execSize = disp;  
    }
//=================== setter ===========================
    public void setid(String id){
        this.id = id;
    }
    public void setarrive(int arrive){
        this.arrive = arrive;
    }
    public void setexecSize(int execSize){
        this.execSize = execSize;
        this.remainDuration = execSize; // set remainning Duration same as execSize
    }
    public void settickets(int tickets){
        this.tickets = tickets;
    }
    public void setRemains(int unit){
        this.remainDuration = remainDuration - unit;
    }
    public void setStatus(boolean status){
        Status = status;
    }
    public void setBottomEnter(int enterTime){
        FBV_bottomEnter = enterTime;
    }
//=================== getters ===========================
    public String getid(){
        return id;
    }
    public int getarrive(){
        return arrive;
    }
    public int getexecSize(){
        return execSize;
    }
    public int gettickets(){
        return tickets;
    }
    public int getRemains(){
        return remainDuration;
    }
    public boolean getStatus(){
        return Status;
    }
    public int getBottomEnter(){
        return FBV_bottomEnter;
    }
//======================= toString =========
    public String  toString(){
        System.out.println(id + "\n"+arrive + "\n"+execSize + "\n"+tickets); 
        return "";
    }


    

   

}
