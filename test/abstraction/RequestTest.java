package abstraction;

import implementor.Provider;
import implementor.ProviderException;
import model.RequestData;
import model.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestTest {

    private final Provider echo = req -> new Response("200", req.payload());

    @Test
    void answerRequestAddsAnswerPrefix() {
        Response response = new AnswerRequest(echo).execute(new RequestData("hi"));
        assertEquals(new Response("200", "Answer: hi"), response);
    }

    @Test
    void classifyRequestAddsClassifyPrefix() {
        Response response = new ClassifyRequest(echo).execute(new RequestData("hi"));
        assertEquals(new Response("200", "Classify: hi"), response);
    }

    @Test
    void providerFailureReachesClient() {
        Provider failing = req -> {
            throw new ProviderException(ProviderException.Reason.UNAVAILABLE, "down");
        };
        assertThrows(ProviderException.class, () -> new AnswerRequest(failing).execute(new RequestData("hi")));
    }
}
