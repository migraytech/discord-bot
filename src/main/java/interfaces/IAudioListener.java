package interfaces;


import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;


public interface IAudioListener extends MessageCreateListener {


    void setPlayerManager(AudioPlayerManager audioPlayerManager);
    @Override
    void onMessageCreate(MessageCreateEvent messageCreateEvent);
}
