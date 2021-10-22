package listener;

import interfaces.IModeratorListener;
import org.javacord.api.event.message.MessageCreateEvent;

public class SaitamaModeratorListener implements IModeratorListener {


    //List of bad words check if contains in the message


    //Remove message

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {

        if(messageCreateEvent.getMessageContent().contains("bad word")){
            //messageCreateEvent.().getController().kick(messageCreateEvent.getMember()).complete();
            messageCreateEvent.deleteMessage();
        }
    }
}
