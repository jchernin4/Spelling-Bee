package SpellingBee.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

import java.util.Map;
import java.util.Optional;

public class FallbackIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(Predicates.intentName("AMAZON.FallbackIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        
        Map<String, Object> sessionAttributes = handlerInput.getAttributesManager().getSessionAttributes();
        if (sessionAttributes.get("gameState") != null && sessionAttributes.get("gameState").equals("STARTED")) {
            // currently playing
            return handlerInput.getResponseBuilder()
                    .withSpeech("I'm sorry, that didn't seem to be a valid response.")
                    .withReprompt("Please say a valid letter.")
                    .build();
        }

        // not playing
        return handlerInput.getResponseBuilder()
                .withSpeech("You are not currently in a game. Would you like to start one?")
                .withReprompt("Say yes to start the game, or say no to quit.")
                .build();
    }
}
