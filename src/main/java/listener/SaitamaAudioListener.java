package listener;

import Service.MessageBuilderService;
import interfaces.IAudioListener;
import org.javacord.api.event.message.MessageCreateEvent;


/**
 * Message Listener
 *
 * @param
 */
public class SaitamaAudioListener implements IAudioListener {


    private MessageBuilderService messageBuilderService;

    /**
     * Message Listener
     *
     * @param messageCreateEvent
     */
    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {

    }


}
