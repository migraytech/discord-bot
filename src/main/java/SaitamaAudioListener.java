import Service.MessageBuilderService;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.FunctionalResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import commands.ServerCommand;
import interfaces.IAudioListener;
import listener.SaitamaCommandListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.audio.AudioSource;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Message Listener
 *
 * @param
 */
public class SaitamaAudioListener extends ServerCommand implements IAudioListener{


    private final MessageBuilderService messageBuilderService = new MessageBuilderService();

    private static final Logger logger = LogManager.getLogger(SaitamaCommandListener.class);


    private final static Pattern pattern = Pattern.compile("!play (https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");


    private final  AudioPlayerManager playerManager = PlayerManager.getManager();

    private final AudioPlayer player =  playerManager.createPlayer();

    public SaitamaAudioListener(){
        super("!play");
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


            if (matcher.matches()) {
                    // split the message connect from !player command en url
                    String query = messageCreateEvent.getMessageContent().replace( "!play", " ").trim();

                    if(isUrl(query))
                    {
                        //Send the MessageBuilder with the url and image of the video;
                        try {

                            //Set the thumbnail with the request url as image.

                            messageBuilderService.sendMessage(messageCreateEvent.getMessageAuthor(),"Song Requested",messageCreateEvent.getMessageAuthor().getMessage().toString(),"'Some footer text' ", "https://i0.kym-cdn.com/photos/images/original/001/049/085/9ff.png",messageCreateEvent.getChannel());

                            AudioSource source = new LavaPlayerAudioSource(messageCreateEvent.getApi(), player);
                            ServerVoiceChannel serverVoiceChannel = messageCreateEvent.getApi().getServerVoiceChannelById(835207222753493042L).get();

                            serverVoiceChannel.connect().thenAccept(audioConnection -> {
                                audioConnection.setAudioSource(source);
                                audioConnection.setSelfDeafened(true); // This is optional, but I prefer to have my bot deafen itself.
                                playerManager.loadItem(query, new AudioLoadResultHandler() {
                                            @Override
                                            public void trackLoaded(AudioTrack track) {
                                                player.playTrack(track);
                                            }

                                            @Override
                                            public void playlistLoaded(AudioPlaylist playlist) {
                                                for (AudioTrack track : playlist.getTracks()) {
                                                    player.playTrack(track);
                                                }
                                            }

                                            @Override
                                            public void noMatches() {
                                                // Notify the user that we've got nothing
                                            }

                                            @Override
                                            public void loadFailed(FriendlyException throwable) {
                                                // Notify the user that everything exploded
                                            }
                                        });
                                play(query,messageCreateEvent.getServerTextChannel().get(), new ServerMusicManager(playerManager));

                            }).exceptionally(e -> {
                                // Failed to connect to voice channel (no permissions?)
                                e.printStackTrace();
                                return null;
                            });

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

    @Override
    protected void runCommand(MessageCreateEvent event, Server server, ServerTextChannel channel, User user, String[] args) {
        // Make sure the message have arguments above one (for example: play kano 2020 remix or play https://youtube.com/...).
//        if(args.length > 1){
//
//            // We first check if the user is in any voice channel.
//            event.getMessageAuthor().getConnectedVoiceChannel().isPresent(voiceChannel -> {
//
//                // We have checked that the user is in a channel, but can we see the channel, can we connect and can we speak on the channel?
//                if(voiceChannel.canYouConnect() && voiceChannel.canYouSee() && voiceChannel.hasPermission(event.getApi().getYourself(), PermissionType.SPEAK)){
//
//                    //  We retrieve the ServerMusicManager from the AudioManager class which will create it if it doesn't exist.
//                    ServerMusicManager m = AudioManager.get(server.getId());
//
//                    // We retrieve the URL or the query.
//                    String query = event.getMessageContent().replace(args[0] + " ", "");
//
//                    if (!voiceChannel.isConnected(event.getApi().getYourself()) && server.getAudioConnection().isEmpty()) {
//
//                        voiceChannel.connect().thenAccept(audioConnection -> {
//                            // Create an audio source and add to audio connection queue, this is where we use the ServerMusicManager as well.
//                            AudioSource audio = new LavaPlayerAudioSource(event.getApi(), m.player);
//                            audioConnection.setAudioSource(audio);
//                            audioConnection.setSelfDeafened(true); // This is optional, but I prefer to have my bot deafen itself.
//
//                            // Plays the music.
//                            play(query, channel, m);
//
//                        });
//
//                        // If the bot is already on a channel, and is playing music.
//                    } else if (server.getAudioConnection().isPresent()) {
//                        // Gets the audio connection.
//                        server.getAudioConnection().ifPresent(audioConnection -> {
//                            // Checks if the user is in the same channel as the bot.
//                            if(audioConnection.getChannel().getId() == voiceChannel.getId()) {
//                                // Create an audio source and add to audio connection queue, this is where we use the ServerMusicManager as well.
//                                AudioSource audio = new LavaPlayerAudioSource(event.getApi(), m.player);
//                                audioConnection.setAudioSource(audio);
//                                audioConnection.setSelfDeafened(true); // This is optional, but I prefer to have my bot deafen itself.
//
//                                // Plays the music.
//                                play(query, channel, m);
//                            } else {
//                                event.getChannel().sendMessage("You are not connected with the same channel as the bot.");
//                            }
//                        });
//                    }
//                } else {
//                    // Tell the user that we cannot connect, or see, or speak in the channel.
//                    event.getChannel().sendMessage("Either I cannot connect, cannot see, or do not have the permission to speak on the channel.");
//                }
//            }, () -> event.getChannel().sendMessage("You are not connected in any voice channel."));
//        }
//    }
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
            m.scheduler.queue(audioTrack);
            channel.sendMessage("We have added the track: " + audioTrack.getInfo().title);

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
