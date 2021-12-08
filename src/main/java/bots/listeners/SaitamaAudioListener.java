package bots.listeners;

import bots.helpers.PlayerManager;
import bots.helpers.AudioManager;
import bots.helpers.LavaPlayerAudioSource;
import bots.helpers.ServerMusicManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import service.MessageBuilderService;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.FunctionalResultHandler;
import models.ServerCommand;
import interfaces.IAudioListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.audio.AudioSource;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.ServerVoiceChannel;
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

    private static final Logger logger = LogManager.getLogger(SaitamaAudioListener.class);


    private final static Pattern pattern = Pattern.compile("!play (https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");


    private final  AudioPlayerManager playerManager = PlayerManager.getManager();


    public SaitamaAudioListener(){
        super("play");
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
                String query = messageCreateEvent.getMessageContent().replace("!play", " ").trim();

                if (isUrl(query)) {
                    //Send the MessageBuilder with the url and image of the video;
                    try {
                        //Set the thumbnail with the request url as image.
                        System.out.println("START THE PLAY COMMMAND.....");

                        //
                        if (messageCreateEvent.getMessageAuthor().getConnectedVoiceChannel().isPresent()) {
                            System.out.println("START SETTING UP THE SOURCE AND SERVER MANAGER");
                            ServerMusicManager m = AudioManager.get(messageCreateEvent.getServer().get().getId());
                            AudioSource source = new LavaPlayerAudioSource(messageCreateEvent.getApi(), m.player);
                            ServerVoiceChannel serverVoiceChannel = messageCreateEvent.getApi().getServerVoiceChannelById(835207222753493042L).get();

                            if (!serverVoiceChannel.isConnected(messageCreateEvent.getApi().getYourself()) && !messageCreateEvent.getServer().get().getAudioConnection().isPresent()) {

                                System.out.println("JOIN THE BOT IN THE VOICE CHANNEL");
                                serverVoiceChannel.connect().thenAccept(audioConnection -> {
                                    System.out.println("START");
                                    // Create an audio source and add to audio connection queue, this is where we use the ServerMusicManager as well.
                                    audioConnection.setAudioSource(source);
                                    audioConnection.setSelfDeafened(true); // This is optional, but I prefer to have my bot deafen itself.
                                    // Plays the music.
                                    play(query, messageCreateEvent.getServerTextChannel().get(), m,messageCreateEvent);
                                }).join();
                            }
                            else if (messageCreateEvent.getServer().flatMap(Server::getAudioConnection).isPresent()) {

                                messageCreateEvent.getServer().flatMap(Server::getAudioConnection).ifPresent(audioConnection -> {
                                    // Checks if the user is in the same channel as the bot.
                                    if (audioConnection.getChannel().getId() == serverVoiceChannel.getId()) {
                                        // Create an audio source and add to audio connection queue, this is where we use the ServerMusicManager as well.
                                        audioConnection.setAudioSource(source);
                                        audioConnection.setSelfDeafened(true); // This is optional, but I prefer to have my bot deafen itself.
                                        // Plays the music.
                                        play(query, messageCreateEvent.getServerTextChannel().get(), m,messageCreateEvent);
                                    } else {
                                        messageCreateEvent.getChannel().sendMessage("You are not connected with the same channel as the bot.");
                                    }
                                });
                            }
                            else {
                                 messageCreateEvent.getChannel().sendMessage("Either I cannot connect, cannot see, or do not have the permission to speak on the channel.");
                            }
                        }
                        else {
                            messageCreateEvent.getChannel().sendMessage("You are not connected in any voice channel.");
                            System.out.println("Haven't found user(s) in the voice channel");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                messageCreateEvent.getChannel().sendMessage("Are you trying to use the " + word + " command? Please use the syntax " + "!play" + "[yt-url]." + "Thanks!");
            }
        }
    }

    @Override
    protected void runCommand(MessageCreateEvent event, Server server, ServerTextChannel channel, User user, String[] args) {







    }


    /**
     * Plays the music and notifies the user that we have successfully played the music.
     * @param query the query to search for.
     * @param channel the channel where the command was sent.
     * @param m the server music manager.
     */
    private void play(String query, ServerTextChannel channel, ServerMusicManager m, MessageCreateEvent messageCreateEvent){
        playerManager.loadItemOrdered(m, isUrl(query) ? query : "ytsearch: " + query, new FunctionalResultHandler(audioTrack -> {
            // This is for track loaded.
            m.scheduler.queue(audioTrack);
            channel.sendMessage("Saitama-bot have added the track: " + audioTrack.getInfo().title);
            messageBuilderService.sendMessage(messageCreateEvent.getMessageAuthor(),audioTrack.getInfo().title,messageCreateEvent.getMessageAuthor().getMessage().toString(),"'Duration time: "+ (audioTrack.getInfo().length) / 60L, "https://i0.kym-cdn.com/photos/images/original/001/049/085/9ff.png",audioTrack.getInfo().uri,messageCreateEvent.getChannel());

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
                    messageBuilderService.sendMessage(messageCreateEvent.getMessageAuthor(),audioTrack.getInfo().title,messageCreateEvent.getMessageAuthor().getMessage().toString(),"'Duration time: "+ (audioTrack.getInfo().length) / 60L, "https://i0.kym-cdn.com/photos/images/original/001/049/085/9ff.png", audioTrack.getInfo().uri,messageCreateEvent.getChannel());
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
