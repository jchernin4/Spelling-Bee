package SpellingBee.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

import java.util.Optional;

public class LaunchRequestHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        // Check if incoming request is a LaunchRequest
        return handlerInput.matches(Predicates.requestType(LaunchRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        String speechText = "Welcome to Spelling Bee, you can say hello";
        // Respond with basic greeting
        return handlerInput.getResponseBuilder()
                .withSpeech(speechText)
                //.withSimpleCard("HelloWorld", speechText)
                .withReprompt(speechText)
                .build();
    }
}
