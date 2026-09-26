package implementor;

import model.RequestData;
import model.Response;

public class AnthropicProvider implements Provider {

    @Override
    public Response generate(RequestData req) {
        return new Response(
                "200",
                "Hello from Claude, your data is: " + req.payload()
        );
    }
}
