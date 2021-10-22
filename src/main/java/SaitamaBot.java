import interfaces.IBot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.ServerTextChannel;
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


public class SaitamaBot implements IBot, MessageCreateListener {

    private final String version = "1.0";
    private static final Logger logger = LogManager.getLogger(SaitamaBot.class);
    private final double id = 0.1;


    ///TODO

    //CLASSES that are checking and are responsible
    //commands
    //music
    //monitoring


    private static  TextChannel channel= null;
    private static ServerTextChannel serverTextChannel = null;

    //INFO//
    private static DiscordApi discordApi;
    private final String token = "OTAxMDEwMjAzMDYxOTQwMjM1.YXJpJA.xIxtx6sE5UNFIozgRnvzBML8wrM";

    public SaitamaBot() {
    }

     @Override
    public void setup() {
         logger.trace("Create Saitamabot:  "+id+"  "+version);
         discordApi = new DiscordApiBuilder()
                .setToken(token)
                .addServerBecomesAvailableListener(event -> {
                    System.out.println("Loaded " + event.getServer().getName());
                })

                .addListener(new SaitamaBot())
//                 .addListener(new Saitamaudiolistener())
//                 .addListener(new SaitamaCommandListener())

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

         discordApi.setMessageCacheSize(10, 60);
         System.out.println("Setup the bot...");
         logger.info("Setup the bot... ");
    }

    @Override
    public void start() {
        System.out.println("You can invite the bot by using the following url:" + discordApi.createBotInvite());
        logger.info( "Start the SaitamaBot... ");

    }

    @Override
    public void disconnect() {
        logger.info("Disconnect the SaitamaBot... ");
        discordApi.disconnect();
        System.exit(3);
    }

    @Override
    public void onMessageReceived() {
        discordApi.addMessageCreateListener(reactionAddEvent -> {


            if(reactionAddEvent.getMessageContent().equals("Hi SaitamaBot"))
                reactionAddEvent.getChannel().sendMessage("Hi! :smiley:").join();

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
        }).removeAfter(50,TimeUnit.MINUTES);
    }

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {
        serverTextChannel = messageCreateEvent.getServerTextChannel().get();
        channel = messageCreateEvent.getChannel();
        // Add a listener which answers with "Pong!" if someone writes "!ping"
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

//        if(messageCreateEvent.getMessageContent().contains("bad word")){
//
//            messageCreateEvent.().getController().kick(messageCreateEvent.getMember()).complete();
//
//        }
    }

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
