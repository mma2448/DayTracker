import java.util.*;
import java.io.*;

public class DayTrackerMain {
   public static void main(String[] args) throws FileNotFoundException, IOException {
      Scanner console = new Scanner(System.in);
      System.out.print("Person's name     : ");
      String name = console.next();
      System.out.print("Create 'new'?  : ");
      if(console.next().equals("new")) {
         createTxt(console, name);                
      }
      DayTracker day = new DayTracker(name, new Scanner(new File(name + ".txt")));
      String response = "response";
      while(!response.equals("n")) {
         System.out.print("add or display? (press type 'n' to stop): ");
         response = console.next();
         if(response.equals("add")) {
            PrintWriter write = new PrintWriter(new FileWriter(name + ".txt", true));
            day.add(write);
            write.close(); 
         } else if(response.equals("display")) {
            DayTrackerGraph graph = new DayTrackerGraph(day, new File(name + ".txt"));
         } else if(!response.equals("n")){
            System.out.println("invalid input");
         }
      }  
   }
   
   public static void createTxt(Scanner console, String name) throws FileNotFoundException {
      PrintStream output = new PrintStream(new File(name + ".txt"));
      output.println(name + "'s Log");
      Calendar first = new GregorianCalendar();
      output.print("Started: ");
      output.print(first.get(Calendar.MONTH) + 1 + " - ");
      output.print(first.get(Calendar.DAY_OF_MONTH) + " - "); 
      output.println(first.get(Calendar.YEAR));
      output.println("___________________");
      output.print("DATE: ");
      output.print(first.get(Calendar.MONTH) + 1 + " - ");
      output.print(first.get(Calendar.DAY_OF_MONTH) + " - "); 
      output.print(first.get(Calendar.YEAR));
      output.println("     NUM 1");
      output.println();
      System.out.println("new file created");
   }  
}