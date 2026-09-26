package abstraction;

import implementor.Provider;

public abstract class Request {
    protected final Provider provider;

    protected Request(Provider provider) {
        this.provider = provider;
    }

}
