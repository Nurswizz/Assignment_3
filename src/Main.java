import abstraction.AnswerRequest;
import abstraction.ClassifyRequest;
import abstraction.Request;
import implementor.AnthropicProvider;
import implementor.LegacyOllamaProviderAdapter;
import implementor.OpenAIProvider;
import implementor.Provider;
import implementor.ProviderException;
import implementor.RoutingProvider;
import legacy.LegacyOllamaProvider;
import model.RequestData;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Provider provider = new RoutingProvider(Map.of(
                "gpt-", new OpenAIProvider(),
                "claude-", new AnthropicProvider(),
                "llama", new LegacyOllamaProviderAdapter(new LegacyOllamaProvider())
        ));
        List<Request> requests = List.of(new AnswerRequest(provider), new ClassifyRequest(provider));

        for (String model : List.of("gpt-5", "claude-opus", "llama3", "mistral")) {
            RequestData data = new RequestData(model, "What is a design pattern?");
            for (Request request : requests) {
                try {
                    System.out.println(model + " -> " + request.execute(data));
                } catch (ProviderException e) {
                    System.out.println(model + " -> " + e.reason() + ": " + e.getMessage());
                }
            }
        }
    }

}
