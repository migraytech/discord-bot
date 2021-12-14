package server;

import bots.ModeratorBot;
import bots.SaitamaBot;
import org.javacord.api.util.logging.FallbackLoggerConfiguration;


public class Application {

    private static SaitamaBot saitamaBot;
    private static ModeratorBot moderatorBot;

    public static void main(String[] args)  {

        FallbackLoggerConfiguration.setTrace(false);

        saitamaBot = new SaitamaBot();
        saitamaBot.setup();
        saitamaBot.start();
        saitamaBot.onMessageReceived();
        saitamaBot.removeMessage();

//        moderatorBot = new ModeratorBot();
//        moderatorBot.setup();
//        moderatorBot.start();
//        moderatorBot.onMessageReceived();
//        moderatorBot.removeMessage();
    }
}
