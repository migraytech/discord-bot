package interfaces;


import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;


public interface IAudioListener extends MessageCreateListener {

    @Override
    void onMessageCreate(MessageCreateEvent messageCreateEvent);
}
