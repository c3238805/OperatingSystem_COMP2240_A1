/*  
    /============================\
    |  COMP2240 Assignment 1     | 
    |  Name : Ni Zeng            | 
    |  Student Number : c3238805 |
    \============================/   */

// Introduction of class: FileUtil    
// this class is simply to accept data from the input file       

import java.io.*;
import java.util.Scanner;

public class FileUtil {
    private static int processor_size = 0;
    private static int disp;
    private static int numberOfRandom = -1; // count the size of int []randomNumber
    private static int[] randomNumber;

    public static Processor [] ReadFile (String sourceFile) throws IOException {
        File file = new File(sourceFile);
        Scanner scan = new Scanner(file);
        String inputStream = "";
        int counter = 0 ;   // counter for processor's index
        Processor [] processors = new Processor [processor_size];    // create an array to store all processor
        try {
            while(scan.hasNextLine()){
                inputStream = scan.nextLine();
             
                // if line contain "DISP", read the value of "DISP"
                if(inputStream.contains("DISP:")){
                    //split the inputStream by ": ", then select the value after the ": " as dis value.
                    String [] line = inputStream.split(": ");
                    //read disp value .
                    disp = Integer.valueOf(line[1]);
                }
                else if(inputStream.contains("ID:")){
                    String [] idline = inputStream.split(": ");
                    processors[counter] = new Processor();
                    processors[counter].setid(idline[1]);
                                                       
                }
                else if(inputStream.contains("Arrive:")){
                    String [] arriveline = inputStream.split(": ");
                    processors[counter].setarrive(Integer.valueOf(arriveline[1]));
                    
                }
                else if(inputStream.contains("ExecSize:")){
                    String [] execSizeline = inputStream.split(": ");
                   processors[counter].setexecSize(Integer.valueOf(execSizeline[1]));

                }
                else if(inputStream.contains("Tickets:")){
                    String [] ticketsline = inputStream.split(": ");
                   processors[counter].settickets(Integer.valueOf(ticketsline[1]));
 
                   if(counter < processor_size ){
                       counter++;
                   }
                }
                else if(inputStream.contains("BEGINRANDOM")){
                    // start readRandom
                    while(!inputStream.contains("ENDRANDOM")){
                        numberOfRandom++;
                        inputStream = scan.nextLine();
                    }
                }      
            }
        }
        catch( Exception e){
            System.out.println("error occured"+e.getMessage());
        }

        scan.close();
        return processors;
    }

    public static int[] readRandom (String sourceFile) throws IOException{
        File file = new File(sourceFile);
        Scanner scan = new Scanner(file);
        String inputStream = "";
        randomNumber = new int [numberOfRandom];
        int i= 0;
        try{
            while(scan.hasNextLine()){
                inputStream = scan.nextLine();
                if(inputStream.contains("BEGINRANDOM")){
                    // start readRandom
                    inputStream = scan.nextLine();
                    while(!inputStream.contains("ENDRANDOM")){
                        
                        randomNumber[i] = Integer.parseInt(inputStream);
                        i++;
                        inputStream = scan.nextLine();
                    }
                    
                }

            }
        }
        catch( Exception e){
            System.out.println("error "+ e.getMessage());
        }
        scan.close();
        return randomNumber;
    }

    public static int processorCounter (String sourceFile) throws IOException{
        File file = new File(sourceFile);
        Scanner scan = new Scanner(file);
        String inputStream = "";

        try{
            while(scan.hasNextLine()){
                inputStream = scan.nextLine();
                if(inputStream.contains("ID:")){
                    processor_size++;
                }
            }
        }
        catch( Exception e){
            System.out.println("error"+ e.getMessage());
        }
        scan.close();
        return processor_size;
    }

    public static int getDisp(){
        return disp;
    }
    
}
