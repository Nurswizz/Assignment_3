package implementor;

import model.RequestData;
import model.Response;

public class OpenAIProvider implements Provider {

    @Override
    public Response generate(RequestData req) {
        return new Response(
                "200",
                "Hello I am gpt-6 luna, your data is: " + req.payload()
        );
    }
}
