package listener;

import Service.MessageBuilderService;
import interfaces.ICommandListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Message Listener
 *
 * @param
 */
public class SaitamaCommandListener implements ICommandListener {

    //List of patterns with different commandes

    //Execute the command trigger


    //Check which command is executed
    /**
     * Message Listener
     *
     * @param event
     */

    private static final Logger logger = LogManager.getLogger(SaitamaCommandListener.class);
    private final static Pattern pattern = Pattern.compile("!clean (\\w+)");
    private MessageBuilderService messageBuilderService;
    private String word;

    /**
     * Message Listener
     *
     * @param messageCreateEvent
     */
    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {

        if(messageCreateEvent.getMessageContent().startsWith("!clean")){

            Matcher matcher = pattern.matcher(messageCreateEvent.getMessageContent());
            word = messageCreateEvent.getMessageContent();
            logger.info("TEST");
            if(matcher.matches()){



                //DO the command lines


                //switch case ?


                //messageCreateEvent.getChannel().sendMessage("EXECUTE MESSAGE");

            }
            else {
                messageCreateEvent.getChannel().sendMessage("Are you trying to use the "+word+"  command? Please use the syntax "+" "+"[word]."+"Thanks!");

            }

        }

    }
}
