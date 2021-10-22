import Service.MessageBuilderService;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import interfaces.IBot;
import listener.SaitamaAudioListener;
import listener.SaitamaCommandListener;
import listener.SaitamaModeratorListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.audio.AudioSource;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.MessageDecoration;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;
import java.io.File;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Message Listener
 *
 * @param
 */

public class SaitamaBot implements IBot, MessageCreateListener {

    /**
     * Message Listener
     *
     * @param event
     */
    private final String version = "1.0";
    private static final Logger logger = LogManager.getLogger(SaitamaBot.class);
    private final double id = 0.1;



    private MessageBuilderService messageBuilderService;

    ///TODO

    //CLASSES that are checking and are responsible

    //commands

    //music

    //monitoring


    private static  TextChannel channel= null;
    private static ServerVoiceChannel serverVoiceChannel = null;
    private static ServerTextChannel serverTextChannel = null;

    private static DiscordApi discordApi;
    private final String token = "OTAxMDEwMjAzMDYxOTQwMjM1.YXJpJA.8pN3VpzvB4CC85ZfqPbKxO6Y_68";

    public SaitamaBot() {
    }

    /**
     * Message Listener
     *
     * @param
     */

     @Override
    public void setup() {
         logger.info("Create Saitamabot:  "+id+"  "+version);


         AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
         playerManager.registerSourceManager(new YoutubeAudioSourceManager());
         AudioPlayer player = playerManager.createPlayer();

         discordApi = new DiscordApiBuilder()
                .setToken(token)
                .addServerBecomesAvailableListener(event -> {
                    System.out.println("Loaded " + event.getServer().getName());
                })
                .addListener(new SaitamaBot())
                .addListener(new SaitamaAudioListener())
                 .addListener(new SaitamaModeratorListener())
                .addListener(new SaitamaCommandListener())
                .setWaitForServersOnStartup(false)
                .login()
                .join();

         discordApi.addServerMemberJoinListener(event -> {
             Optional<TextChannel> channel = discordApi.getTextChannelById(901090217556054066L);
             channel.ifPresent(textChannel -> textChannel.sendMessage("Welcome to the server, " + event.getUser().getMentionTag() + "!"));
         });


         discordApi.addServerMemberJoinListener(event -> {
             Optional<TextChannel> channel = discordApi.getTextChannelById(835207136643907716L);
             channel.ifPresent(textChannel -> textChannel.sendMessage("Welcome to the server, " + event.getUser().getMentionTag() + "!"));
         });


         //Create an audio source and add it to the audio connection's queue
         AudioSource source = new LavaplayerAudioSource(discordApi, player);
         serverVoiceChannel = discordApi.getServerVoiceChannelById(835207222753493042L).get();
         serverVoiceChannel.connect().thenAccept(audioConnection -> {
             audioConnection.setAudioSource(source);

             // You can now use the AudioPlayer like you would normally do with Lavaplayer, e.g.,
             playerManager.loadItem("https://www.youtube.com/watch?v=PY8f1Z3nARo", new AudioLoadResultHandler() {
                 @Override
                 public void trackLoaded(AudioTrack track) {
                     player.playTrack(track);
                 }

                 @Override
                 public void playlistLoaded(AudioPlaylist playlist) {
                     for (AudioTrack track : playlist.getTracks()) {
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


         }).exceptionally(e -> {
             // Failed to connect to voice channel (no permissions?)

             e.printStackTrace();
             return null;
         });

         discordApi.setMessageCacheSize(10, 60);
         System.out.println("Setup the bot...");
         logger.info("Setup the bot... ");
    }
    /**
     * Message Listener
     *
     * @param
     */
    @Override
    public void start() {
        System.out.println("You can invite the bot by using the following url:" + discordApi.createBotInvite());
        logger.info( "Start the SaitamaBot... ");
        System.out.println("Start the SaitamaBot..");

    }

    /**
     * Message Listener
     *
     * @param
     */
    @Override
    public void disconnect() {
        logger.info("Disconnect the SaitamaBot... ");
        discordApi.disconnect();
        System.exit(3);
    }


    /**
     * Message Listener
     *
     * @param
     */
    @Override
    public void onMessageReceived() {
        discordApi.addMessageCreateListener(reactionAddEvent -> {
            if(reactionAddEvent.getMessageContent().equals("Hi SaitamaBot"))
                reactionAddEvent.getChannel().sendMessage("Hi! :smiley:"+reactionAddEvent.getMessage().getUserAuthor().get().getName());

            if(reactionAddEvent.getMessageContent().equals("!commands") || reactionAddEvent.getMessageContent().equals("!help")) {

                reactionAddEvent.getChannel().sendMessage("HI! " + reactionAddEvent.getMessage().getUserAuthor().get().getName()).join();
                reactionAddEvent.getChannel().sendMessage("Here are some commands that I understand:").join();

                reactionAddEvent.getChannel().sendMessage(String.valueOf(new MessageBuilder()
                                .append("!clean ",MessageDecoration.BOLD,MessageDecoration.CODE_SIMPLE).append("Remove all messages from the Text Channel").send(channel))).join();

                reactionAddEvent.getChannel().sendMessage(String.valueOf(new MessageBuilder()
                        .append("!disconnect ",MessageDecoration.BOLD,MessageDecoration.CODE_SIMPLE).append("Stop the SaitamaBot").send(channel))).join();

                reactionAddEvent.getChannel().sendMessage(String.valueOf(new MessageBuilder()
                                .append("!one-punch  <@user>",MessageDecoration.BOLD,MessageDecoration.CODE_SIMPLE).append("One punch a User form the server for Softban a member").send(channel))).join();

                reactionAddEvent.getChannel().sendMessage(String.valueOf(new MessageBuilder()
                                .append("!ban <@user>",MessageDecoration.BOLD,MessageDecoration.CODE_SIMPLE).append("Ban a member for a limited amount of time, by entering the limit in the command.").send(channel))).join();

                reactionAddEvent.getChannel().sendMessage(String.valueOf(new MessageBuilder()
                                .append("!kick <@user> ",MessageDecoration.BOLD,MessageDecoration.CODE_SIMPLE).append("Kick a member in the channel").send(channel))).join();

                reactionAddEvent.getChannel().sendMessage(String.valueOf(new MessageBuilder()
                                .append("!mute <@user> ",MessageDecoration.BOLD,MessageDecoration.CODE_SIMPLE).append("Mute a member so they cannot type or speak for a limited time").send(channel))).join();

                reactionAddEvent.getChannel().sendMessage(String.valueOf(new MessageBuilder()
                                .append("!watch-anime <@name> ",MessageDecoration.BOLD,MessageDecoration.CODE_SIMPLE).append("Watch anime video from kissanime.ru select the anime-name ").send(channel))).join();

                reactionAddEvent.getChannel().sendMessage(String.valueOf(new MessageBuilder()
                        .append("!spam <@user> ",MessageDecoration.BOLD,MessageDecoration.CODE_SIMPLE).append("Choose your victim to get spammed").send(channel))).join();
            }
        });
    }

    /**
     * Message Listener
     *
     * @param messageCreateEvent
     */
    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {
        serverTextChannel = messageCreateEvent.getServerTextChannel().get();
        channel = messageCreateEvent.getChannel();

        if (messageCreateEvent.getMessageContent().equalsIgnoreCase("!ping") || messageCreateEvent.getMessageContent().equals("!info"))  {

            messageCreateEvent.getChannel().sendMessage("HI!" + messageCreateEvent.getMessage().getUserAuthor().get().getName());
            channel.sendMessage("Hi Guys  "+"@"+ serverTextChannel.getServer().getName() +"  " +  "Dont forget to subscribe on my friends Youtube Channel Migray-Tech!!");
            channel.sendMessage("Free to ask what kinda anime or programming tutorials you wanna watch!").join();
            channel.sendMessage("To see all commands, please type '!commands' or '!help'");
            channel.sendMessage(String.valueOf(new MessageBuilder()
                    .append("Look at these ")
                    .append("awesome", MessageDecoration.BOLD, MessageDecoration.UNDERLINE)
                    .addAttachment(new File("C:/Users/Mignon/Pictures/pic1.jpg"))
                    .addAttachment(new File("C:/Users/Mignon/Pictures/pic2.png"))
                    .appendCode("java", "System.out.println(\"Sweet!\");")
                    .setEmbed(new EmbedBuilder()
                            .setTitle("WOW")
                            .setDescription("ONE PUNCH")
                            .setColor(Color.ORANGE))
                    .send(channel)));

            messageCreateEvent.getChannel().typeContinuouslyAfter(5L,TimeUnit.valueOf("MINUTES"));

        }

        // stop the BOT
        if(messageCreateEvent.getMessageContent().equalsIgnoreCase("!disconnect")) {
            channel.sendMessage("See you later!");
            disconnect();
        }

    }



    /**
     * Message Listener
     *
     * @param
     */
    @Override
    public void removeMessage() {
        discordApi.addReactionAddListener(event -> {
            if (event.getEmoji().equalsEmoji("👎")) {
                event.getChannel();
                event.deleteMessage();
            }
        }).removeAfter(30, TimeUnit.MINUTES);
    }




}
