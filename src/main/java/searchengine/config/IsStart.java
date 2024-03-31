package searchengine.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Data
@Component
public class IsStart {
    private AtomicBoolean start = new AtomicBoolean(false);

    public void setStart() {
        start.set(true);
    }

    public boolean getStart() {
        return start.get();
    }
}
