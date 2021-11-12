package bots;


import bots.listeners.SaitamaAudioListener;
import bots.listeners.commands.*;
import interfaces.IBot;
import models.ModeratorBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import service.MessageBuilderService;

import java.awt.*;
import java.io.File;

public class ModeratorBot extends ModeratorBase implements IBot{


    private static final Logger logger = LogManager.getLogger(SaitamaBot.class);
    private final MessageBuilderService messageBuilderService =  new MessageBuilderService();
    private static DiscordApi discordApi;
    protected final String token = System.getenv("DISCORD_TOKEN");
    private boolean  isBadWord ;

    @Override
    public void setup() {

        logger.trace("Create bots.ModeratorBot");
        discordApi = new DiscordApiBuilder()
                .setToken(token)
                .addServerBecomesAvailableListener(event -> {
                    System.out.println("Loaded " + event.getServer().getName());
                })
                .addListener(new SaitamaBot())
                .addListener(new SaitamaAudioListener())
                .addListener(new BanCommand())
                .addListener(new CleanCommand())
                .addListener(new KickCommand())
                .addListener(new MuteCommand())
                .addListener(new WatchAnimeCommand())
                .addListener(new OnePunchCommand())
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
        logger.trace( "Start the bots.SaitamaBot... ");
        System.out.println("Start the bots.SaitamaBot..");
    }

    /**
     * Message Listener
     *
     * @param
     */
    @Override
    public void disconnect() {
        logger.info("Disconnect the bots.SaitamaBot... ");
        discordApi.disconnect();
        System.exit(3);
    }


    @Override
    public void onMessageReceived() {
//        serverTextChannel = messageCreateEvent.getServerTextChannel().get();
//        channel = messageCreateEvent.getChannel();
//        if (messageCreateEvent.getMessageContent().equalsIgnoreCase("!ping") || messageCreateEvent.getMessageContent().equals("!info"))  {
//            messageCreateEvent.getChannel().sendMessage("HI!" + messageCreateEvent.getMessage().getUserAuthor().get().getName()).join();
//            channel.sendMessage("Hi Guys  "+"@"+ serverTextChannel.getServer().getName() +"  " +  "Dont forget to subscribe on my friends Youtube Channel Migray-Tech!!").join();
//            channel.sendMessage("Free to ask what kinda anime or programming tutorials you wanna watch!").join();
//            channel.sendMessage("To see all bots.listeners.commands, please type '!bots.listeners.commands' or '!help'").join();
//            channel.sendMessage(String.valueOf(new MessageBuilder()
//                    .append("Look at these ")
//                    .append("awesome", MessageDecoration.BOLD, MessageDecoration.UNDERLINE)
//                    .addAttachment(new File("C:/Users/Mignon/Pictures/pic1.jpg"))
//                    .addAttachment(new File("C:/Users/Mignon/Pictures/pic2.png"))
//                    .appendCode("java", "System.out.println(\"Sweet!\");")
//                    .setEmbed(new EmbedBuilder()
//                            .setTitle("WOW")
//                            .setDescription("ONE PUNCH")
//                            .setColor(Color.ORANGE))
//                    .send(channel))).join();
//        }
//
//        // stop the BOT
//        if(messageCreateEvent.getMessageContent().equalsIgnoreCase("!disconnect")) {
//            channel.sendMessage("See you later!");
//            disconnect();
//        }

    }

    @Override
    public void removeMessage() {


    }

    @Override
    protected void checkMessage(MessageCreateEvent event, Server server, ServerTextChannel channel, User user, String[] args) {

        for (String x: moderatorsWords) {
            if(event.getMessage().toString().toLowerCase().contains(x)){
                isBadWord = true;

                //check if user in the volationlist
                  //true    get gounter pluss old value
                // fals    create new with the methode put





                sendMessageToUser(event, user);
                break;
            }
        }

    }

    @Override
    public void sendMessageToUser(MessageCreateEvent event,User user) {
           //Warning message , get the violationCounter from list to so the issue
          //messageBuilderService.sendMessage(null,"","","","","",null);
           event.getChannel().sendMessage(""+user);
    }


    //checkInList
    //count






}
