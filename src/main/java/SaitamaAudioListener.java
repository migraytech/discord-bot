import Service.MessageBuilderService;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.FunctionalResultHandler;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import interfaces.IAudioListener;
import listener.SaitamaCommandListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.audio.AudioSource;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Message Listener
 *
 * @param
 */
public class SaitamaAudioListener implements IAudioListener{


    private final MessageBuilderService messageBuilderService = new MessageBuilderService();

    private static final Logger logger = LogManager.getLogger(SaitamaCommandListener.class);


    private final static Pattern pattern = Pattern.compile("!play (https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");


    private final  AudioPlayerManager playerManager = PlayerManager.getManager();

    private final AudioPlayer player =  playerManager.createPlayer();


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
            AudioSource source = new LavaPlayerAudioSource(messageCreateEvent.getApi(), player);
            ServerVoiceChannel serverVoiceChannel = messageCreateEvent.getApi().getServerVoiceChannelById(835207222753493042L).get();
            serverVoiceChannel.connect().join().setAudioSource(source);
            System.out.println("TESTS");
            if (matcher.matches()) {
                    // split the message connect from !player command en url
                    String query = messageCreateEvent.getMessageContent().replace( "!play", " ").trim();

                    if(isUrl(query))
                    {
                        //Send the MessageBuilder with the url and image of the video;
                        try {

                            //Set the thumbnail with the request url as image.

                            messageBuilderService.sendMessage(messageCreateEvent.getMessageAuthor(),"Song Requested",messageCreateEvent.getMessageAuthor().getMessage().toString(),"'Some footer text' ", "https://i0.kym-cdn.com/photos/images/original/001/049/085/9ff.png",messageCreateEvent.getChannel());


                            play(query,messageCreateEvent.getServerTextChannel().get(), new ServerMusicManager(playerManager));
                            // You can now use the AudioPlayer like you would normally do with Lavaplayer, e.g.,
//                            playerManager.loadItem(query, new AudioLoadResultHandler() {
//                                @Override
//                                public void trackLoaded(AudioTrack track) {
//                                    player.playTrack(track);
//                                }
//
//                                @Override
//                                public void playlistLoaded(AudioPlaylist playlist) {
//                                    for (AudioTrack track : playlist.getTracks()) {
//                                        System.out.println("PLAY");
//                                        logger.info("PLAY");
//                                        player.playTrack(track);
//                                    }
//                                }
//
//                                @Override
//                                public void noMatches() {
//                                    // Notify the user that we've got nothing
//                                    logger.info("PLAY");
//
//                                }
//
//                                @Override
//                                public void loadFailed(FriendlyException e) {
//                                    logger.info("PLAY");
//
//                                }
//                            });
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    }
            }
            else {
                messageCreateEvent.getChannel().sendMessage("Are you trying to use the "+word+ "   command? Please use the syntax "+"!play"+"[url]."+"Thanks!");
            }

        }
    }


    /**
     * Plays the music and notifies the user that we have successfully played the music.
     * @param query the query to search for.
     * @param channel the channel where the command was sent.
     * @param m the server music manager.
     */
    private void play(String query, ServerTextChannel channel, ServerMusicManager m){
        // Load the track, we use isUrl to see if the argument is a URL, otherwise if it is not then we use YouTube Search to search the query.
        playerManager.loadItemOrdered(m, isUrl(query) ? query : "ytsearch: " + query, new FunctionalResultHandler(audioTrack -> {
            // This is for track loaded.
            channel.sendMessage("We have added the track: " + audioTrack.getInfo().title);
            m.scheduler.queue(audioTrack);

        }, audioPlaylist -> {
            // If the playlist is a search result, then we only need to get the first one.
            if (audioPlaylist.isSearchResult()) {
                // YOU HAVE TO ADD THIS, UNLESS YOU WANT YOUR BOT TO GO SPAM MODE.
                m.scheduler.queue(audioPlaylist.getTracks().get(0));
                channel.sendMessage("We have added the track: " + audioPlaylist.getTracks().get(0).getInfo().title);
            } else {
                // If it isn't then simply queue every track.
                audioPlaylist.getTracks().forEach(audioTrack -> {
                    m.scheduler.queue(audioTrack);
                    channel.sendMessage("We have queued the track: " + audioTrack.getInfo().title);
                });
            }
        }, () -> {
            // If there are no matches, then we tell the user that we couldn't find any track.
            channel.sendMessage("We couldn't find the track.");
        }, e -> {
            // In the case of when an exception occurs, we inform the user about it.
            channel.sendMessage("We couldn't play the track: " + e.getMessage());
        }));
    }

    /**
     * Checks if the string is a URL.
     * @param argument the string to validate.
     * @return boolean.
     */
    private boolean isUrl(String argument){
        return argument.startsWith("https://") || argument.startsWith("http://");
    }
}
