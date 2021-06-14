package SpellingBee;

import SpellingBee.handlers.*;
import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;

public class SpellingBeeSkillStreamHandler extends SkillStreamHandler {
    public SpellingBeeSkillStreamHandler() {
        super(getSkill());
    }
    
    private static Skill getSkill() {
        return Skills.standard()
                .addRequestHandlers(
                        new CancelAndStopIntentHandler(),
                        new HelpIntentHandler(),
                        new FallbackIntentHandler(),
                        new LaunchRequestHandler(),
                        new SessionEndedRequestHandler())
                .withSkillId("amzn1.ask.skill.73c01e4a-fbe8-4a04-b17d-e329f1b01726")
                .build();
    }
}
