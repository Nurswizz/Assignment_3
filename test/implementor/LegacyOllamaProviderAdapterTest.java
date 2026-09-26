package implementor;

import implementor.ProviderException.Reason;
import legacy.LegacyOllamaProvider;
import model.LegacyResponse;
import model.RequestData;
import model.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LegacyOllamaProviderAdapterTest {

    private static Provider adapterReturning(LegacyResponse response) {
        return new LegacyOllamaProviderAdapter(new LegacyOllamaProvider() {
            @Override
            public LegacyResponse process(String data, int timeout) {
                return response;
            }
        });
    }

    @Test
    void successIsMappedTo200() {
        Provider adapter = new LegacyOllamaProviderAdapter(new LegacyOllamaProvider());
        assertEquals(new Response("200", "Legacy response: hi"), adapter.generate(new RequestData("hi")));
    }

    @ParameterizedTest
    @CsvSource({
            "400, INVALID_REQUEST",
            "408, TIMEOUT",
            "500, UNAVAILABLE",
            "503, UNAVAILABLE",
            "999, UNKNOWN"
    })
    void errorCodeIsTranslatedToProviderException(String code, Reason expected) {
        Provider adapter = adapterReturning(new LegacyResponse(code, null));
        ProviderException e = assertThrows(ProviderException.class, () -> adapter.generate(new RequestData("hi")));
        assertEquals(expected, e.reason());
        assertFalse(e.getMessage().contains(code), "legacy error code must not leak");
    }

    @Test
    void nullResponseIsUnavailable() {
        ProviderException e = assertThrows(ProviderException.class,
                () -> adapterReturning(null).generate(new RequestData("hi")));
        assertEquals(Reason.UNAVAILABLE, e.reason());
    }

    @Test
    void successWithoutResultIsUnknown() {
        ProviderException e = assertThrows(ProviderException.class,
                () -> adapterReturning(new LegacyResponse("0", null)).generate(new RequestData("hi")));
        assertEquals(Reason.UNKNOWN, e.reason());
    }
}
