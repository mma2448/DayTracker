import java.util.*;
import java.io.*;

public class DayTracker {
   private String name;
   private Scanner input;   
   private Scanner console;
   private Calendar lastDate;
   private int num;
   private Map<Integer, Boolean> map;
   
   public DayTracker(String name, Scanner input){
      this.input = input;
      this.console = new Scanner(System.in);
      this.name = name;
      this.lastDate = new GregorianCalendar();
      this.map = new TreeMap<Integer, Boolean>();
      input.nextLine();
      input.nextLine();
      input.nextLine();
      setLastDate(this.input.nextLine(), true);
      while(this.input.hasNextLine()) {
         String line = input.nextLine();
         if(cont(line, 5, "DATE:")) {
            setLastDate(line, true);
         } else if(cont(line, 5, "EMPT:")) {
            setLastDate(line, false);
         }
      }
   }
   
   public void add(PrintWriter write) throws IOException {
      System.out.println("Last entry date: " + dateToString(lastDate));
      System.out.print("'same', 'next', 'other' day?  :");
      String response = console.next();
      Calendar eventDate = (Calendar) lastDate.clone();
      if(response.equals("next")) {
         eventDate.add(Calendar.DAY_OF_MONTH, 1);
      } else if(response.equals("other")) {
         System.out.print("Event Month?   :");
         int month = console.nextInt();
         System.out.print("Event Day?     :");
         int day = console.nextInt();
         System.out.print("Event Year?    :");
         int year = console.nextInt();
         eventDate.set(year, month, day, 0, 0, 0);
      } else if(!response.equals("same")) {
         System.out.println("invalid input");
         return;
      }
      add(write, eventDate);      
   }
   
   public int getNum() {
      return num;
   }
   
   public Map<Integer, Boolean> getMap() {
      return this.map;
   }
   
   private void add(PrintWriter write, Calendar eventDate) {
      if(lastDate.compareTo(eventDate) > 0) {
         System.out.println("Date of event occurs before current date");
      } else if(lastDate.compareTo(eventDate) == 0){
         System.out.print("Type?       : ");
         write.println("   Event: " + console.next());
         System.out.print("Start Hour? : ");
         eventDate.set(Calendar.HOUR_OF_DAY, console.nextInt());
         System.out.print("Start Min?  : ");
         eventDate.set(Calendar.MINUTE, console.nextInt());
         write.println("   Start: " + timeToString(eventDate));
         System.out.print("Dur Hour?   : ");
         eventDate.set(Calendar.HOUR_OF_DAY, console.nextInt());
         System.out.print("Dur Min?    : ");
         eventDate.set(Calendar.MINUTE, console.nextInt());
         write.println("   Duration: " + timeToString(eventDate));
         System.out.print("Note?       : ");
         write.println("   Note: " + console.next());
         write.println();
      } else if((int)((eventDate.getTimeInMillis() - lastDate.getTimeInMillis()) / (86400000)) == 1) { 
         num++;     
         lastDate.add(Calendar.DAY_OF_MONTH, 1);
         write.println("DATE: " + dateToString(lastDate) + "     NUM " + num);
         write.println();
         map.put(num, true);
         add(write, eventDate);
      } else {
         num++;     
         lastDate.add(Calendar.DAY_OF_MONTH, 1);
         write.println("EMPT: " + dateToString(lastDate) + "     NUM " + num);
         write.println();
         map.put(num, false);
         add(write, eventDate);
      }
   }
   
   private boolean cont(String str, int lengthSearch, String search) {
      if(str.length() < lengthSearch) {
            return false;
      } else {
            return str.substring(0, lengthSearch).equals(search);
      }
   }
   
   private void setLastDate(String line, boolean hasEvent) {
      Scanner lineRead = new Scanner(line);
      lineRead.next();
      int month = lineRead.nextInt();
      lineRead.next();
      int day = lineRead.nextInt();
      lineRead.next();
      int year = lineRead.nextInt();
      lineRead.next();
      this.num = lineRead.nextInt();
      lastDate.set(year, month, day, 0, 0, 0);
      map.put(num, hasEvent);
   }
   
   private String dateToString(Calendar date) {
      String text = "";
      text += date.get(Calendar.MONTH) + " - ";
      text += date.get(Calendar.DAY_OF_MONTH) + " - "; 
      text += date.get(Calendar.YEAR); 
      return text;
   }
   
   private String timeToString(Calendar date) {
      String text = "";
      text += date.get(Calendar.HOUR_OF_DAY) + " : ";
      text += date.get(Calendar.MINUTE);
      return text;
   }
}