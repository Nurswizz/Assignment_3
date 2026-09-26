package implementor;

import abstraction.Request;
import model.RequestData;
import model.Response;

public interface Provider {
    Response generate(RequestData req);
}
