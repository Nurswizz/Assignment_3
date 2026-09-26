package implementor;

import implementor.ProviderException.Reason;
import legacy.LegacyOllamaProvider;
import model.LegacyResponse;
import model.RequestData;
import model.Response;

public class LegacyOllamaProviderAdapter implements Provider {

    private static final int TIMEOUT_MS = 30_000;

    private final LegacyOllamaProvider legacyProvider;

    public LegacyOllamaProviderAdapter(LegacyOllamaProvider legacyProvider) {
        this.legacyProvider = legacyProvider;
    }

    @Override
    public Response generate(RequestData req) {
        LegacyResponse legacyResponse = legacyProvider.process(req.payload(), TIMEOUT_MS);
        if (legacyResponse == null) {
            throw new ProviderException(Reason.UNAVAILABLE, "Provider returned no response");
        }
        if (!"0".equals(legacyResponse.errorCode())) {
            throw translate(legacyResponse.errorCode());
        }
        if (legacyResponse.result() == null) {
            throw new ProviderException(Reason.UNKNOWN, "Provider returned an empty result");
        }
        return new Response("200", legacyResponse.result());
    }

    private static ProviderException translate(String errorCode) {
        return switch (errorCode) {
            case "400" -> new ProviderException(Reason.INVALID_REQUEST, "Provider rejected the request");
            case "408" -> new ProviderException(Reason.TIMEOUT, "Provider timed out");
            case "500", "503" -> new ProviderException(Reason.UNAVAILABLE, "Provider is unavailable");
            default -> new ProviderException(Reason.UNKNOWN, "Provider failed");
        };
    }
}
