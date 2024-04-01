package searchengine.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Data
@Component
@RequiredArgsConstructor
public class IsStart {
    private AtomicBoolean start = new AtomicBoolean(false);


}
