package implementor;

import legacy.LegacyOllamaProvider;
import model.LegacyResponse;
import model.RequestData;
import model.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LegacyOllamaProviderAdapterTest {

    @Test
    void successIsMappedTo200() {
        Provider adapter = new LegacyOllamaProviderAdapter(new LegacyOllamaProvider());
        assertEquals(new Response("200", "Legacy response: hi"), adapter.generate(new RequestData("hi")));
    }

    @Test
    void errorCodeIsPassedThrough() {
        LegacyOllamaProvider broken = new LegacyOllamaProvider() {
            @Override
            public LegacyResponse process(String data, int timeout) {
                return new LegacyResponse("503", "");
            }
        };
        Provider adapter = new LegacyOllamaProviderAdapter(broken);
        assertEquals(new Response("503", "Legacy provider failed"), adapter.generate(new RequestData("hi")));
    }
}
