package SpellingBee.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

import java.util.Map;
import java.util.Optional;

public class LetterGuessIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        boolean isCurrentlyPlaying = false;
        Map<String, Object> sessionAttributes = handlerInput.getAttributesManager().getSessionAttributes();

        if (sessionAttributes.get("gameState") != null && sessionAttributes.get("gameState").equals("STARTED")) {
            isCurrentlyPlaying = true;
        }

        return isCurrentlyPlaying && handlerInput.matches(Predicates.intentName("LetterGuessIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        IntentRequest intentRequest = (IntentRequest) handlerInput.getRequestEnvelope().getRequest();
        String guessLetter = intentRequest.getIntent().getSlots().get("letter").getValue();
        Map<String, Object> sessionAttributes = handlerInput.getAttributesManager().getSessionAttributes();
        int targetIndex = (int) sessionAttributes.get("guessIndex");
        String targetWord = (String) sessionAttributes.get("guessWord");
        String targetLetter = targetWord.split("")[targetIndex];
        
        if (!guessLetter.equalsIgnoreCase(targetLetter)) {
            int gamesPlayed = (int) sessionAttributes.get("gamesPlayed") + 1;
            sessionAttributes.put("gamesPlayed", gamesPlayed);
            sessionAttributes.put("gameState", "ENDED");
            handlerInput.getAttributesManager().setPersistentAttributes(sessionAttributes);
            handlerInput.getAttributesManager().savePersistentAttributes();
            
            return handlerInput.getResponseBuilder()
                    .withSpeech(guessLetter + " was incorrect!")
                    .build();
            
        } else if (guessLetter.equalsIgnoreCase(targetLetter)) {
            int guessIndex = (int) sessionAttributes.get("guessIndex") + 1;
            sessionAttributes.put("guessIndex", guessIndex);
            handlerInput.getAttributesManager().setSessionAttributes(sessionAttributes);
            
            return handlerInput.getResponseBuilder()
                    .withReprompt("Next letter?")
                    .build();
        }
        return handlerInput.getResponseBuilder()
                .withSpeech("Sorry I didn't get that. Try saying a letter")
                .withReprompt("Try saying a letter")
                .build();
    }
}
