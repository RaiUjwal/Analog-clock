
import java.applet.*;  
import java.awt.*;  
import java.util.*;  
import java.text.*;  
  
public class abc extends Applet implements Runnable {  
  
   int width, height;  
   Thread t = null;  
   int thickness;
   boolean threadSuspended;  
   int hours=0, minutes=0, seconds=0;  
   String timeString = "";  
  
   public void init() {  
	   setSize(472,384);
	   width = getSize().width;  
	      height = getSize().height;  
      setBackground(Color.gray);  
   }  
  
   public void start() {  
      if ( t == null ) {  
         t = new Thread( this );  
         t.setPriority( Thread.MIN_PRIORITY );  
         threadSuspended = false;  
         t.start();  
      }  
      else {  
         if ( threadSuspended =true) {  
            threadSuspended = false;  
            synchronized( this ) {  
               notify();  
            }  
         }  
      }  
   }  
  
   public void stop() {  
      threadSuspended = true;  
   }  
  
   public void run() {  
      try {  
         while (true) {  
  
            Calendar cal = Calendar.getInstance();  
            hours = cal.get( Calendar.HOUR_OF_DAY );  
            if ( hours > 12 ) hours -= 12;  
            minutes = cal.get( Calendar.MINUTE );  
            seconds = cal.get( Calendar.SECOND );  
  
            SimpleDateFormat formatter  
               = new SimpleDateFormat( "hh:mm:ss", Locale.getDefault() );  
            Date date = cal.getTime();  
            timeString = formatter.format( date );  
  
            // Now the thread checks to see if it should suspend itself  
            if ( threadSuspended ) {  
               synchronized( this ) {  
                  while ( threadSuspended ) {  
                     wait();  
                  }  
               }  
            }  
            repaint();  
            t.sleep( 1000 );  
         }  
      }  
      catch (Exception e) { }  
   }  
  
   void drawHand( double angle, int radius, Graphics g ) {  
      angle -= 0.5 * Math.PI;  
      int x = (int)( radius*Math.cos(angle) );  
      int y = (int)( radius*Math.sin(angle) );  
      g.drawLine( width/2, height/2, width/2 + x, height/2 + y );  
   }  
  
   void drawWedge( double angle, int radius, Graphics g ) {  
      angle -= 0.5 * Math.PI;  
      int x = (int)( radius*Math.cos(angle) );  
      int y = (int)( radius*Math.sin(angle) );  
      angle += 2*Math.PI/3;  
      int x2 = (int)( 5*Math.cos(angle) );  
      int y2 = (int)( 5*Math.sin(angle) );  
      angle += 2*Math.PI/3;  
      int x3 = (int)( 5*Math.cos(angle) );  
      int y3 = (int)( 5*Math.sin(angle) );  
      g.drawLine( width/2+x2, height/2+y2, width/2 + x, height/2 + y );  
      g.drawLine( width/2+x3, height/2+y3, width/2 + x, height/2 + y );  
      g.drawLine( width/2+x2, height/2+y2, width/2 + x3, height/2 + y3 );  
   }  
  
   public void paint( Graphics g ) {  
       
       g.setFont(new Font("TimesNewRoman", Font.BOLD, 20));
g.setColor(Color.black);
g.fillOval(80, 30, 320, 320); 
g.setColor(Color.white);
g.drawString("12",  225, 70);
g.drawString("1",  312, 86);
g.drawString("2",  355, 140);
g.drawString("3", 364, 204);
g.drawString("4", 350, 260);
g.drawString("5", 310, 315);
g.drawString("6", 238, 330);
g.drawString("7", 160, 315);
g.drawString("8", 115, 260);
 g.drawString("9", 105, 203);
 g.drawString("10",  110, 140);
 g.drawString("11",  150, 85);
 g.setColor( Color.red );  
      drawWedge( 2*Math.PI * hours / 12, width/5, g ); 
      g.setColor( Color.cyan );
      drawWedge( 2*Math.PI * minutes / 60, width/3, g );  
      g.setColor( Color.cyan );
      drawHand( 2*Math.PI * seconds / 60, width/5, g );  
      g.setColor( Color.red);
      g.fillOval(235, 190, 10, 8);
      g.setColor(Color.blue);
      g.drawString("ANALOG CLOCK", 155, 20);
 
      g.setColor(Color.red);
g.drawOval(80,30,320,320);
g.setColor(Color.CYAN);
g.drawOval(89+thickness,39+thickness,300-3*thickness,300-3*thickness);
       
      g.setColor( Color.cyan );  
      drawWedge( 2*Math.PI * hours / 12, width/5, g );  
      drawWedge( 2*Math.PI * minutes / 60, width/4, g );  
      drawHand( 2*Math.PI * seconds / 60, width/5, g );  
      g.setColor( Color.BLACK );  
      g.drawString( timeString, 10, height-10 );  
   }
   
 
}

