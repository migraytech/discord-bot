import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.MessageDecoration;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;
import java.io.File;
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
    private final String token = "OTAxMDEwMjAzMDYxOTQwMjM1.YXJpJA.rk3q7MPyvWzaAhKKdNe8rQpWfnE";

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
                .setWaitForServersOnStartup(true)
                .login()
                .join();

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
            channel = reactionAddEvent.getChannel();

            if(reactionAddEvent.getMessageContent().equals("!commands") || reactionAddEvent.getMessageContent().equals("!help")) {

                reactionAddEvent.getChannel().sendMessage("HI! " + reactionAddEvent.getMessage().getUserAuthor().get().getName());
                reactionAddEvent.getChannel().sendMessage("Here are some commands that I understand:");

                channel.sendMessage(String.valueOf(new MessageBuilder()
                                .append("!clean ",MessageDecoration.BOLD,MessageDecoration.CODE_SIMPLE).append("Remove all messages from the Text Channel").send(channel)));

                channel.sendMessage(String.valueOf(new MessageBuilder()
                                .append("!one-punch ",MessageDecoration.BOLD,MessageDecoration.CODE_SIMPLE).append("One punch a User form the server for Softban a member").send(channel)));

                reactionAddEvent.getChannel().sendMessage(String.valueOf(new MessageBuilder()
                                .append("!ban ",MessageDecoration.BOLD,MessageDecoration.CODE_SIMPLE).append("Ban a member for a limited amount of time, by entering the limit in the command.").send(channel)));

                reactionAddEvent.getChannel().sendMessage(String.valueOf(new MessageBuilder()
                                .append("!kick ",MessageDecoration.BOLD,MessageDecoration.CODE_SIMPLE).append("Kick a member in the channel").send(channel)));

                reactionAddEvent.getChannel().sendMessage(String.valueOf(new MessageBuilder()
                                .append("!mute ",MessageDecoration.BOLD,MessageDecoration.CODE_SIMPLE).append("Mute a member so they cannot type or speak for a limited time").send(channel)));

                reactionAddEvent.getChannel().sendMessage(String.valueOf(new MessageBuilder()
                                .append("!watch-anime ",MessageDecoration.BOLD,MessageDecoration.CODE_SIMPLE).append("Watch anime video from kissanime.ru select the anime-name ").send(channel)));

                reactionAddEvent.getChannel().sendMessage(String.valueOf(new MessageBuilder()
                        .append("!spam ",MessageDecoration.BOLD,MessageDecoration.CODE_SIMPLE).append("Choose your victim to get spammed").send(channel)));
            }
        }).removeAfter(50,TimeUnit.MINUTES);
    }

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {
        serverTextChannel = messageCreateEvent.getServerTextChannel().get();
        channel = messageCreateEvent.getChannel();
        // Add a listener which answers with "Pong!" if someone writes "!ping"
        if (messageCreateEvent.getMessageContent().equalsIgnoreCase("!ping") || messageCreateEvent.getMessageContent().equals("!info") || messageCreateEvent.getMessageContent().equals("!help"))  {

            messageCreateEvent.getChannel().sendMessage("HI!" + messageCreateEvent.getMessage().getUserAuthor().get().getName());
            channel.sendMessage("Hi Guys  "+"@"+ serverTextChannel.getServer().getName() +"  " +  "Dont forget to subscribe on my friends Youtube Channel Migray-Tech!!");
            channel.sendMessage("Free to ask what kinda anime or programming tutorials you wanna watch!");
            channel.sendMessage("To see all commands, please type '!commands' or '!help'");
            channel.sendMessage(String.valueOf(new MessageBuilder()
                    .append("Look at these ")
                    .append("awesome", MessageDecoration.BOLD, MessageDecoration.UNDERLINE)
                    .append(" Look Saitama pictures! 😃")
                    .addAttachment(new File("C:/Users/Mignon/Pictures/pic1.jpg"))
                    .addAttachment(new File("C:/Users/Mignon/Pictures/pic2.png"))
                    .appendCode("java", "System.out.println(\"Sweet!\");")
                    .setEmbed(new EmbedBuilder()
                            .setTitle("WOW")
                            .setDescription("Really cool pictures!")
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
