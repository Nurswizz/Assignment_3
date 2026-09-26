package implementor;

import legacy.LegacyOllamaProvider;
import model.LegacyResponse;
import model.RequestData;
import model.Response;

public class LegacyOllamaProviderAdapter implements Provider{

    private final LegacyOllamaProvider legacyProvider;

    public LegacyOllamaProviderAdapter(LegacyOllamaProvider legacyProvider) {
        this.legacyProvider = legacyProvider;
    }

    @Override
    public Response generate(RequestData req) {
        LegacyResponse legacyResponse = legacyProvider.process(req.payload(), 0);
        if (!legacyResponse.errorCode().equals("500")) {
            return new Response(
                    "200",
                    legacyResponse.result()
            );
        }
        return new Response(
                legacyResponse.errorCode(),
                "Legacy provider failed"
        );
    }
}
