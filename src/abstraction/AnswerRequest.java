package abstraction;

import implementor.Provider;
import model.RequestData;
import model.Response;

public class AnswerRequest extends Request {

    public AnswerRequest(Provider provider) {
        super(provider);
    }

    @Override
    public Response execute(RequestData req) {
        return provider.generate(new RequestData("Answer: " + req.payload()));
    }
}
