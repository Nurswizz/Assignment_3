package implementor;

import implementor.ProviderException.Reason;
import model.RequestData;
import model.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RoutingProviderTest {

    private final Provider router = new RoutingProvider(Map.of(
            "gpt-", req -> new Response("200", "openai"),
            "claude-", req -> new Response("200", "anthropic"),
            "llama", req -> new Response("200", "ollama")
    ));

    @Test
    void picksProviderByModelPrefix() {
        assertEquals("openai", router.generate(new RequestData("gpt-5", "hi")).data());
        assertEquals("anthropic", router.generate(new RequestData("claude-opus", "hi")).data());
        assertEquals("ollama", router.generate(new RequestData("llama3", "hi")).data());
    }

    @Test
    void unknownModelIsInvalidRequest() {
        ProviderException e = assertThrows(ProviderException.class,
                () -> router.generate(new RequestData("mistral", "hi")));
        assertEquals(Reason.INVALID_REQUEST, e.reason());
    }

    @Test
    void missingModelIsInvalidRequest() {
        ProviderException e = assertThrows(ProviderException.class,
                () -> router.generate(new RequestData(null, "hi")));
        assertEquals(Reason.INVALID_REQUEST, e.reason());
    }
}
