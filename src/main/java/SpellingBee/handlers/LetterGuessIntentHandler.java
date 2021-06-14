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
        String guessLetter = intentRequest.getIntent().getSlots().get("letter").getValue().split("")[0];
        Map<String, Object> sessionAttributes = handlerInput.getAttributesManager().getSessionAttributes();
        int targetIndex = (int) sessionAttributes.get("guessIndex");
        String targetWord = (String) sessionAttributes.get("guessWord");
        String targetLetter = targetWord.split("")[targetIndex];
        
        if (!guessLetter.equalsIgnoreCase(targetLetter)) {
            sessionAttributes.put("gameState", "ENDED");
            handlerInput.getAttributesManager().setSessionAttributes(sessionAttributes);
            
            StringBuilder response = new StringBuilder(guessLetter + " was incorrect! The correct letter was " + targetLetter + ". " + targetWord + " is spelled ");
            for (String s : targetWord.split("")) {
                response.append(s).append(". ");
            }
            response.append("Thanks for playing!");
            
            return handlerInput.getResponseBuilder()
                    .withSpeech(response.toString())
                    .build();
            
        } else if (guessLetter.equalsIgnoreCase(targetLetter)) {
            int guessIndex = (int) sessionAttributes.get("guessIndex") + 1;
            
            if (guessIndex >= targetWord.length()) {
                sessionAttributes.put("gameState", "ENDED");
                handlerInput.getAttributesManager().setSessionAttributes(sessionAttributes);
                return handlerInput.getResponseBuilder()
                        .withSpeech("Finished! You spelled the word correctly! Thanks for playing!")
                        .build();
            }
            
            sessionAttributes.put("guessIndex", guessIndex);
            handlerInput.getAttributesManager().setSessionAttributes(sessionAttributes);
            
            return handlerInput.getResponseBuilder()
                    .withSpeech("Next letter?")
                    .withReprompt("Next letter?")
                    .build();
        }
        return handlerInput.getResponseBuilder()
                .withSpeech("Sorry I didn't get that. Try saying a letter")
                .withReprompt("Try saying a letter")
                .build();
    }
}
