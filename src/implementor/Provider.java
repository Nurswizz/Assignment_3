package implementor;

import model.RequestData;
import model.Response;

public interface Provider {
    Response generate(RequestData req);
}
