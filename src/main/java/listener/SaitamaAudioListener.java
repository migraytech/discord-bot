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
import org.javacord.api.audio.AudioSource;
import org.javacord.api.event.message.MessageCreateEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Message Listener
 *
 * @param
 */
public class SaitamaAudioListener implements IAudioListener {


    private final MessageBuilderService messageBuilderService = new MessageBuilderService();

    private static final Logger logger = LogManager.getLogger(SaitamaCommandListener.class);


    private final static Pattern pattern = Pattern.compile("!play (\\w+)");


    private AudioPlayerManager playerManager =  PlayerManager.getManager();
    private  AudioPlayer player;



    @Override
    public void setPlayerManager(AudioPlayerManager audioPlayerManager) {
        System.out.println(audioPlayerManager == null);
        this.playerManager = audioPlayerManager;
        this.player =  audioPlayerManager.createPlayer();
    }

    /**
     * Message Listener
     *
     *  @param messageCreateEvent
     */
    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {

        //Create an audio source and add it to the audio connection's queue
        logger.info("START");
        if(messageCreateEvent.getMessageContent().startsWith("!play")) {
            Matcher matcher = pattern.matcher(messageCreateEvent.getMessageContent());
            String word = messageCreateEvent.getMessageContent();
            System.out.println("TESTS");
            logger.info("TEST");
            if (matcher.matches()) {

                    System.out.println("PLAY TRACK");
                    // split the message connect from !player command en url

                    //Send the MessageBuilder with the url and image of the video;

                    // messageBuilderService.sendMessage();
                    try {

                        //
                        messageBuilderService.sendMessage(messageCreateEvent.getMessageAuthor(),"Song title",messageCreateEvent.getMessageAuthor().getMessage().toString(),"test","",messageCreateEvent.getChannel());
                        // You can now use the AudioPlayer like you would normally do with Lavaplayer, e.g.,
                        playerManager.loadItem("https://www.youtube.com/watch?v=PY8f1Z3nARo", new AudioLoadResultHandler() {
                            @Override
                            public void trackLoaded(AudioTrack track) {
                                player.playTrack(track);
                            }

                            @Override
                            public void playlistLoaded(AudioPlaylist playlist) {
                                for (AudioTrack track : playlist.getTracks()) {
                                    System.out.println("PLAY");
                                    logger.info("PLAY");
                                    player.playTrack(track);
                                }
                            }

                            @Override
                            public void noMatches() {
                                // Notify the user that we've got nothing
                                logger.info("PLAY");

                            }

                            @Override
                            public void loadFailed(FriendlyException e) {
                                logger.info("PLAY");

                            }
                        });
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }






            }
            else {
                messageCreateEvent.getChannel().sendMessage("Are you trying to use the "+word+ "command? Please use the syntax "+"!play"+"[url]."+"Thanks!");
            }

        }
    }
}
