import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import javax.security.auth.login.Configuration;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;


public class SaitamaBot implements IBot, MessageCreateListener {

    private final String version = "1.0";
    private static final Logger logger = LogManager.getLogger(SaitamaBot.class);
    private final double id = 0.1;
    private static  TextChannel channel= null;



    //INFO//
    private DiscordApi discordApi;
    private final String token = System.getenv("DISCORD_TOKEN");

    public SaitamaBot() {
    }

     @Override
    public void setup() {
         logger.trace("Create Saitamabot:  "+id+"  "+version);
         System.out.println(token);
         discordApi = new DiscordApiBuilder()
                .setToken(token)
                .addServerBecomesAvailableListener(event -> {
                    System.out.println("Loaded " + event.getServer().getName());
                })
                .addListener(new SaitamaBot())
                .setWaitForServersOnStartup(false)
                .login()
                .join();

         System.out.println("Setup the bot...");
       logger.info("Setup the bot... ");
    }

    @Override
    public void start() {
        System.out.println("You can invite the bot by using the following url:" + discordApi.createBotInvite());
       logger.trace( "Start the SaitamaBot... ");

    }

    @Override
    public void disconnect() {
        logger.info("Disconnect the SaitamaBot... ");
        discordApi.disconnect();

    }

    @Override
    public void receiveMessage() {

        discordApi.addReactionAddListener(reactionAddEvent -> {
            if(reactionAddEvent.getMessageContent().equals("Hey SaitamaBot")){
                reactionAddEvent.getChannel().sendMessage("Hey" +reactionAddEvent.getUser());
            }
            if(reactionAddEvent.getMessageContent().equals("stop SaitamaBot")) {
                disconnect();
            }
          }).removeAfter(30, TimeUnit.MINUTES);;
    }

    @Override
    public void removeMessage() {
        discordApi.addReactionAddListener(event -> {
            if (event.getEmoji().equalsEmoji("👎")) {
                event.deleteMessage();
            }

        }).removeAfter(30, TimeUnit.MINUTES);
    }

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {
        // Add a listener which answers with "Pong!" if someone writes "!ping"
        if (messageCreateEvent.getMessageContent().equalsIgnoreCase("!ping")) {
            channel = messageCreateEvent.getChannel();
            messageCreateEvent.getChannel().sendMessage("Pong!");
            channel.sendMessage("Hi Guys"+"@"+channel);

        }
    }

}
