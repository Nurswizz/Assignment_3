package abstraction;

import implementor.Provider;
import model.RequestData;
import model.Response;

public abstract class Request {
    protected final Provider provider;

    protected Request(Provider provider) {
        this.provider = provider;
    }

    public abstract Response execute(RequestData req);
}
