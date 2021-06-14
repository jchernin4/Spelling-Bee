package SpellingBee.handlers;

import SpellingBee.data.Words;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class YesIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        // only start a new game if yes is said when not playing a game.
        boolean isCurrentlyPlaying = false;
        Map<String, Object> sessionAttributes = handlerInput.getAttributesManager().getSessionAttributes();

        if (sessionAttributes.get("gameState") != null && sessionAttributes.get("gameState").equals("STARTED")) {
            isCurrentlyPlaying = true;
        }

        return !isCurrentlyPlaying && handlerInput.matches(Predicates.intentName("AMAZON.YesIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        Map<String, Object> sessionAttributes = handlerInput.getAttributesManager().getSessionAttributes();
        sessionAttributes.put("gameState", "STARTED");
        String randomWord = Words.words[new Random().nextInt(Words.words.length)];
        sessionAttributes.put("guessWord", randomWord);
        sessionAttributes.put("guessIndex", 0);
        handlerInput.getAttributesManager().setSessionAttributes(sessionAttributes);

        return handlerInput.getResponseBuilder()
                .withSpeech("Great! Your word is " + randomWord + ". Try saying a letter to start the game.")
                .withReprompt("Try saying a letter")
                .build();
    }
}
