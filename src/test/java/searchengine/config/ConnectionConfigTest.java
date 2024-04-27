package searchengine.config;





import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConnectionConfigTest {

    @Test
    public void setConnectionConfigTest() {
        ConnectionConfig connectionConfig = new ConnectionConfig();
        connectionConfig.setReferer("url");
        connectionConfig.setUserAgent("username");
        assertEquals("url", connectionConfig.getReferer());
        assertEquals("username", connectionConfig.getUserAgent());
    }


}
