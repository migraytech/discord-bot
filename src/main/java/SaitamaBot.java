import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SaitamaBot implements IBot, MessageCreateListener {

    private final String version = "1.0";

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final double id = 0.1;


    private static  TextChannel channel= null;


    //INFO//
    private DiscordApi discordApi;
    private final String token = "OTAxMDEwMjAzMDYxOTQwMjM1.YXJpJA.I7irfJAPY1Mdwuvq_G6aWCZEgJk";

    public SaitamaBot() {
    }

     @Override
    public void setup() {
         logger.config("Create Saitamabot:  "+id+"  "+version);
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
        logger.log(Level.FINE,"Setup the bot... ");
    }

    @Override
    public void start() {
        //System.out.println("You can invite the bot by using the following url:" + discordApi.createBotInvite());
        logger.log(Level.CONFIG,"Start the SaitamaBot... ");

    }

    @Override
    public void disconnect() {
        logger.log(Level.CONFIG,"Disconnect the SaitamaBot... ");
        discordApi.disconnect();

    }

    @Override
    public void receiveMessage() {

        discordApi.addReactionAddListener(reactionAddEvent -> {
            if(reactionAddEvent.getMessageContent().equals("Hey SaitamaBot")){
                reactionAddEvent.getChannel().sendMessage("Hey" +reactionAddEvent.getUser());
            }
          });
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
        }
    }

}
