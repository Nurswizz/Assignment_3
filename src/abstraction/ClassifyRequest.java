package abstraction;

import implementor.Provider;
import model.RequestData;
import model.Response;

public class ClassifyRequest extends Request{

    public ClassifyRequest(Provider provider) {
        super(provider);
    }

    @Override
    public Response execute(RequestData req) {
        return provider.generate(new RequestData(req.model(), "Classify: " + req.payload()));
    }
}
