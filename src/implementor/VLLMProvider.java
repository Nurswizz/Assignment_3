package implementor;

import model.RequestData;
import model.Response;

public class VLLMProvider implements Provider {

    @Override
    public Response generate(RequestData req) {
        return new Response(
                "200",
                "Hello from VLLM inference, your data is: " + req.payload()
        );
    }
}
