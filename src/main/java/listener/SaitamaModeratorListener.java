package listener;

import Service.MessageBuilderService;
import interfaces.IModeratorListener;
import org.javacord.api.event.message.MessageCreateEvent;
 /**
 * Message Listener
 *
 * @param
 */
public class SaitamaModeratorListener implements IModeratorListener {


    //List of bad words check if contains in the message

    //Channel
    /**
     * Message Listener
     *
     * @param event
     */
    private MessageBuilderService messageBuilderService;

    //Remove message

     /**
      * Message Listener
      *
      * @param messageCreateEvent
      */

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {

        if(messageCreateEvent.getMessageContent().contains("bad word")){
            //messageCreateEvent.().getController().kick(messageCreateEvent.getMember()).complete();
            messageCreateEvent.deleteMessage();
        }
    }
}
