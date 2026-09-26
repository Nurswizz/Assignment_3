# AI Providers

## Problem

When building AI Systems, we use different AI services such as OpenAI API, Deepseek API, Anthropic and so on.
Also we imagined that we had 2 different business logics as: Answer from AI and Classify by AI.

Basically I had:

### For `Bridge` pattern:

`Request` - Abstraction

`AnswerRequest`, `ClassifyRequest` - Refined Abstractions

`Provider` - Implementor

`OpenAIProvider`, `AnthropicProvider`, `RoutingProvider` - Concrete Implementors



### For `Adapter` Pattern:

`LegacyOllamaProvider` that is incomaptible with our Provider interface , and `LegacyOllamaProviderAdapter` that wraps the original provider and implements the Provider interface


