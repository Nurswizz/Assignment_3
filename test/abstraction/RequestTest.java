package abstraction;

import implementor.Provider;
import model.RequestData;
import model.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void requestWorksWithAnyProvider() {
        Provider failing = req -> new Response("500", "down");
        assertEquals(new Response("500", "down"), new AnswerRequest(failing).execute(new RequestData("hi")));
    }
}
