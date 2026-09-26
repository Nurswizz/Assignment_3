package legacy;

import model.LegacyResponse;

public class LegacyOllamaProvider {
    public LegacyResponse process(String data, int timeout) {
        return new LegacyResponse(
                "0",
                "Legacy response: " + data
        );
    }
}
