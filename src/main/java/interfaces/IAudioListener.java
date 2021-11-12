package interfaces;


import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;


public interface IAudioListener extends MessageCreateListener {



    @Override
    void onMessageCreate(MessageCreateEvent messageCreateEvent);
}
