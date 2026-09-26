package implementor;

import implementor.ProviderException.Reason;
import model.RequestData;
import model.Response;

import java.util.Map;

public class RoutingProvider implements Provider {

    private final Map<String, Provider> providersByModelPrefix;

    public RoutingProvider(Map<String, Provider> providersByModelPrefix) {
        this.providersByModelPrefix = Map.copyOf(providersByModelPrefix);
    }

    @Override
    public Response generate(RequestData req) {
        if (req.model() == null) {
            throw new ProviderException(Reason.INVALID_REQUEST, "Model is not specified");
        }
        return providersByModelPrefix.entrySet().stream()
                .filter(e -> req.model().startsWith(e.getKey()))
                .findFirst()
                .orElseThrow(() -> new ProviderException(Reason.INVALID_REQUEST, "Unknown model: " + req.model()))
                .getValue()
                .generate(req);
    }
}
