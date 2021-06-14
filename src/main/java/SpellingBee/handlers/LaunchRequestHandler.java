package SpellingBee.handlers;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

import java.util.Map;
import java.util.Optional;

public class LaunchRequestHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        // Check if incoming request is a LaunchRequest
        return handlerInput.matches(Predicates.requestType(LaunchRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        AttributesManager attributesManager = handlerInput.getAttributesManager();
        Map<String, Object> attributes = attributesManager.getSessionAttributes();
        if (attributes.isEmpty()) {
            attributes.put("gameState", "ENDED");
        }

        attributesManager.setSessionAttributes(attributes);

        String speechOutput = "Welcome to Spelling Bee. would you like to play?";
        String reprompt = "Say yes to start the game or no to quit.";
        return handlerInput.getResponseBuilder()
                .withSpeech(speechOutput)
                .withReprompt(reprompt)
                .build();
    }
}
