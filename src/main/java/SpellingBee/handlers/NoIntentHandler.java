package SpellingBee.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

import java.util.Map;
import java.util.Optional;

public class NoIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        boolean isCurrentlyPlaying = false;
        Map<String, Object> sessionAttributes = handlerInput.getAttributesManager().getSessionAttributes();

        if (sessionAttributes.get("gameState") != null && sessionAttributes.get("gameState").equals("STARTED")) {
            isCurrentlyPlaying = true;
        }
        return !isCurrentlyPlaying && handlerInput.matches(Predicates.intentName("AMAZON.NoIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        Map<String, Object> sessionAttributes = handlerInput.getAttributesManager().getSessionAttributes();
        int endedSessionCount = (int) sessionAttributes.get("endedSessionCount") + 1;
        sessionAttributes.put("endedSessionCount", endedSessionCount);

        handlerInput.getAttributesManager().setPersistentAttributes(sessionAttributes);
        handlerInput.getAttributesManager().savePersistentAttributes();
        return handlerInput.getResponseBuilder()
                .withSpeech("Ok, see you next time!")
                .build();
    }
}
