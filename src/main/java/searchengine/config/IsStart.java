package searchengine.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;


@Component

public class IsStart {
   private static AtomicBoolean start = new AtomicBoolean(false);
    public static boolean getStart() {
        return start.get();
    }

   public    static void setStart(boolean start) {
        IsStart.start.set(start);
    }






}
