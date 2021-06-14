package SpellingBee.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import java.util.Optional;

public class UnsupportedRequestHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return true;
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        String outputSpeech = "Say yes to continue, or no to end the game.";
        return handlerInput.getResponseBuilder()
                .withSpeech(outputSpeech)
                .withReprompt(outputSpeech)
                .build();
    }
}
