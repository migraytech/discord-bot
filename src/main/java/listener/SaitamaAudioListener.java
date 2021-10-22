package listener;

import Service.MessageBuilderService;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import interfaces.IAudioListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.GloballyAttachableListener;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Message Listener
 *
 * @param
 */
public class SaitamaAudioListener implements IAudioListener {


    private MessageBuilderService messageBuilderService;

    private static final Logger logger = LogManager.getLogger(SaitamaCommandListener.class);
    private final static Pattern pattern = Pattern.compile("!play (\\w+)");

    public SaitamaAudioListener(){

    }


    /**
     * Message Listener
     *
     *  @param messageCreateEvent
     */
    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {

        if(messageCreateEvent.getMessageContent().startsWith("!play")){
            Matcher matcher = pattern.matcher(messageCreateEvent.getMessageContent());
            String word = messageCreateEvent.getMessageContent();
            logger.info("TEST");
            if(matcher.matches()){

                // split the message connect from !player command en url

                //Send the MessageBuilder with the url and image of the video;

                // messageBuilderService.sendMessage();



            }
            else {
                messageCreateEvent.getChannel().sendMessage("Are you trying to use the "+word+"  command? Please use the syntax "+" "+"[word]."+"Thanks!");
            }

        }
    }
}
