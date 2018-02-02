import java.awt.*;
import java.util.*;
import java.io.*;

public class DayTrackerGraph {
   public final int BRIGHTNESS = 20;
   
   private DayTracker day;
   private File log;
   private long milliInDay;
   private Calendar beginning;
   
   public DayTrackerGraph(DayTracker day, File log) throws FileNotFoundException {
      this.day = day;
      this.log = log;
      DrawingPanel panel = new DrawingPanel(day.getNum() * 210 + 200, 2000);
      Graphics g = panel.getGraphics();
      Color darkerGrey = new Color(BRIGHTNESS * 5, BRIGHTNESS * 5, BRIGHTNESS * 5);
      panel.setBackground(darkerGrey);
      createDates(g); 
      createLines(g);
      createEmpty(day.getNum(), 1, g); 
      drawAllEvents(g);
   }
   
   private void createLines(Graphics g) {
      g.setFont(new Font("Calibri", Font.PLAIN, 24)); 
      for(int i = 0; i < 24; i++) {
         g.setColor(new Color(BRIGHTNESS * 6, BRIGHTNESS * 6, BRIGHTNESS * 6));
         g.drawLine(0, 101 + 60 * i, day.getNum() * 210 + 200, 101 + 60 * i);
         g.setColor(new Color(BRIGHTNESS * 10, BRIGHTNESS * 10, BRIGHTNESS * 10));
         g.drawString("" + i, 0, 125 + 60 * i);
      }
   }
   
   private void createEmpty(int max, int cur, Graphics g) {
      if(max >= cur) {
         if(day.getMap().get(cur)) {
            g.setColor(new Color(BRIGHTNESS * 8, BRIGHTNESS * 8, BRIGHTNESS * 8, 100));
            g.fillRect(-100 + 210 * cur, 100, 200, 1440);
         } else {
            g.setColor(new Color(BRIGHTNESS * 6, BRIGHTNESS * 6, BRIGHTNESS * 6, 100));
            g.fillRect(-100 + 210 * cur, 100, 200, 1440);
         }
         createEmpty(max, cur + 1, g);
      }
   }
   
   private void createDates(Graphics g) throws FileNotFoundException {
      Scanner input = new Scanner(log);
      int cur = 1;
      g.setColor(new Color(BRIGHTNESS * 12, BRIGHTNESS * 12, BRIGHTNESS * 12));
      g.setFont(new Font("Calibri", Font.PLAIN, 24)); 
      while(input.hasNextLine()) {
         String line = input.nextLine();
         if(cont(line, 4, "DATE") || cont(line, 4, "EMPT")) {
            g.drawString(line.substring(6, 20), -100 + 210 * cur, 80);
            cur++;
         }
      }
   }
   
   private boolean cont(String str, int lengthSearch, String search) {
      if(str.length() < lengthSearch) {
            return false;
      } else {
            return str.substring(0, lengthSearch).equals(search);
      }
   }   
   
   private void drawAllEvents(Graphics g) throws FileNotFoundException {
      Scanner input = new Scanner(log);
      int count = 0;
      while(input.hasNextLine()) {
         String line = input.nextLine();
         if(cont(line, 9, "   Event:")) {
            String type = line.substring(10);            
            Calendar startTime = getTime(input.nextLine());
            Calendar duraTime = getTime(input.nextLine());            
            String note = input.nextLine();
            input.nextLine();
            drawEvent(-100 + 210 * count, 100, type, note, timeToPixels(startTime), timeToPixels(duraTime), g);
         } else if(cont(line, 4, "DATE") || cont(line, 4, "EMPT")) {
            count++;
         }  
      }
   }
   
   private void drawEvent(int oX, int oY, String type, String note, int start, int dura, Graphics g) {
      if(start + dura <= 1440) {
         g.setColor(eventColor(type));
         g.fillRect(oX, oY + start, 200, dura);
         g.setFont(new Font("Calibri", Font.PLAIN, 24));
         g.setColor(new Color(180, 180, 180));
         //g.drawString(type, oX, oY + start + 20);
         g.drawString(note.substring(9), oX, oY + start + 20);
      } else {
         g.setColor(eventColor(type));
         g.fillRect(oX, oY + start, 200, 1440 - start);
         g.setFont(new Font("Calibri", Font.PLAIN, 24));
         g.setColor(new Color(180, 180, 180));
         //g.drawString(type, oX, oY + start + 20);
         g.drawString(note.substring(9), oX, oY + start + 20);
         drawEvent(oX + 210, 100, type, note, 0, dura - (1440 - start), g);
      }
   }   
   
   private Color eventColor(String type) {
      if(type.equalsIgnoreCase("sleep")) {
         return new Color(48, 69, 87, 180);
      } else if(type.equalsIgnoreCase("eat")) {
         return new Color(133, 136, 72, 180);
      } else if(type.equalsIgnoreCase("exercise")) {
         return new Color(73, 56, 55, 180);
      } else if(type.equals("study")){
         return new Color(110, 111, 90, 180);
      } else {
         return new Color(75, 75, 75);
      }
   }
   
   private Calendar getTime(String line) {
      Scanner lineRead = new Scanner(line);
      lineRead.next();
      int hr = lineRead.nextInt();
      lineRead.next();
      int min = lineRead.nextInt();
      Calendar time = new GregorianCalendar();
      time.set(0, 0, 0, hr, min, 0);
      return time;
   }
   
   private int timeToPixels(Calendar cal) {
      int hr = cal.get(Calendar.HOUR_OF_DAY);
      int min = cal.get(Calendar.MINUTE);
      return hr * 60 + min;
   }   
}





















/*
import java.awt.*;
import java.util.*;
import java.io.*;

public class DayTrackerGraph {
   public static void main(String[] args) throws FileNotFoundException  {
      DrawingPanel panel = new DrawingPanel(3800, 2000);
      Graphics g = panel.getGraphics();
      Color darkerGrey = new Color(40, 40, 40);
      panel.setBackground(new Color(40, 40, 40));
      
      g.fillRect(100, 100, 200, 1440);
      
   }
}
*/