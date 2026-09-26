import abstraction.AnswerRequest;
import abstraction.ClassifyRequest;
import abstraction.Request;
import implementor.AnthropicProvider;
import implementor.LegacyOllamaProviderAdapter;
import implementor.OpenAIProvider;
import implementor.Provider;
import legacy.LegacyOllamaProvider;
import model.RequestData;

public class Main {

    public static void main(String[] args) {
        Provider[] providers = {
                new OpenAIProvider(),
                new AnthropicProvider(),
                new LegacyOllamaProviderAdapter(new LegacyOllamaProvider())
        };
        RequestData data = new RequestData("What is a design pattern?");

        for (Provider provider : providers) {
            Request[] requests = {new AnswerRequest(provider), new ClassifyRequest(provider)};
            for (Request request : requests) {
                System.out.println(request.execute(data));
            }
        }
    }

}
