package searchengine.config;

import java.util.concurrent.atomic.AtomicBoolean;


public class StartAndStop {
   private static AtomicBoolean startAndStop = new AtomicBoolean(false);
    public static boolean getStart() {
        return startAndStop.get();
    }

   public  static void setStart(boolean start) {
        StartAndStop.startAndStop.set(start);
    }






}
