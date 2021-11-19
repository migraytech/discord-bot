package bots;



import interfaces.IBot;
import models.ModeratorBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import service.MessageBuilderService;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ModeratorBot extends ModeratorBase implements IBot{


    private static final Logger logger = LogManager.getLogger(SaitamaBot.class);
    private final MessageBuilderService messageBuilderService =  new MessageBuilderService();
    private static DiscordApi discordApi;
    protected final String token = System.getenv("DISCORD_TOKEN");
    private boolean  isBadWord ;

    @Override
    public void setup() {

        logger.trace("Create ModeratorBot");
        System.out.println("Create ModeratorBot");

        discordApi = new DiscordApiBuilder()
                .setToken(token)
                .addServerBecomesAvailableListener(event -> {
                    System.out.println("Loaded in: " + event.getServer().getName());
                })
                .addListener(new ModeratorBot())
                .setWaitForServersOnStartup(false)
                .login()
                .join();

        System.out.println("Connected to shard " + discordApi.getCurrentShard());
        discordApi.setMessageCacheSize(10, 60);
        System.out.println("Setup the bot...");
        logger.trace("Setup the bot... ");

    }

    @Override
    public void start() {
        System.out.println("You can invite the bot by using the following url:" + discordApi.createBotInvite());
        logger.trace( "Start the ModeratorBot");
        System.out.println("Start the ModeratorBot");
    }

    /**
     * Message Listener
     *
     * @param
     */
    @Override
    public void disconnect() {
        logger.info("Disconnect the ModeratorBot. ");
        System.out.println("Disconnect the ModeratorBot. ");

        discordApi.disconnect();
        System.exit(3);
    }


    @Override
    public void onMessageReceived() {
        discordApi.addMessageCreateListener(reactionAddEvent -> {

        if( reactionAddEvent.getMessageContent().equalsIgnoreCase("!help") || reactionAddEvent.getMessageContent().equalsIgnoreCase("!info")) {
                reactionAddEvent.getChannel().sendMessage("Test");
                messageBuilderService.sendMessage(reactionAddEvent.getMessageAuthor(),"Hello","test","Information of the moderator","","",reactionAddEvent.getChannel());
                File file = new File(Objects.requireNonNull(ModeratorBot.class.getResource("images/saitama-one-punch-man.gif")).getFile());
                reactionAddEvent.getChannel().sendMessage(file);

        }

        if(reactionAddEvent.getMessageContent().equalsIgnoreCase("!disconnect")) {
            disconnect();
        }

        });
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

    @Override
    protected void checkMessage(MessageCreateEvent event, Server server, ServerTextChannel channel, User user, String[] args) throws Throwable {
        try
        {
            for (String x: moderatorsWords) {
                if(args[0].contains(x)){
                    System.out.println("CHECKED");

                    isBadWord = true;
                    int limit = 3;
                    if (violationCounter.containsKey(user) && event.getServer().isPresent())
                        if (violationCounter.get(user).equals(limit)) {
                            logger.info("User violated the rules");
                            System.out.println("the User: " + user + "violated the rules");
                            sendMessageToUser(event, user, limit);
                            event.getChannel().sendMessage("Your are being asshole!").join();
                            messageBuilderService.sendMessage(event.getMessageAuthor(), "You have been kick from the server !!", "", "You have  violated the rules", "https://i.kym-cdn.com/entries/icons/facebook/000/017/618/pepefroggie.jpg", "", event.getChannel());
                            event.getServer().get().kickUser(user);
                            event.getChannel().sendMessage(user.getName() + "has been kicked from the server");
                            break;

                        } else {
                            logger.info("Test");
                            System.out.println("Insert a value for the User " + user);
                            int count = violationCounter.get(user);
                            violationCounter.replace(user, count, count + 1);
                            count = violationCounter.get(user);
                            System.out.println("New value has been updated" + "the count is on " + count);
                            sendMessageToUser(event, user, count);
                            break;
                        }
                    else
                    {
                        logger.info("Insert User in the violationCounter list ");
                        System.out.println("Insert User in the violationCounter list");
                        violationCounter.put(user,1);
                        sendMessageToUser(event, user,violationCounter.get(user));
                        break;
                    }

                }
                isBadWord = false;
            }


            if(isBadWord){
                event.getMessage().delete().join();
                event.getChannel().sendMessage("Message had been deleted by Moderator Bot").join();
            }

        }
        catch (Exception e){
            throw e.getCause();
        }
    }

    @Override
    public void sendMessageToUser(MessageCreateEvent event,User user,int count) {
           System.out.println("Count: "+count+"from the "+" User "+user.getName());
           event.getChannel().sendMessage("Count: "+count+"from the"+"User "+user.getName()).join();
    }

}
