package SpellingBee.handlers;

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
                        new StopAndCancelIntentHandler(),
                        new HelpIntentHandler(),
                        new LaunchRequestHandler(),
                        new SessionEndedRequestHandler())
                .build();
    }
}
