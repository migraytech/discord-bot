package server;

import bots.SaitamaBot;
import org.javacord.api.util.logging.FallbackLoggerConfiguration;


public class Application {

    private static SaitamaBot saitamaBot;

    public static void main(String[] args)  {

        FallbackLoggerConfiguration.setTrace(false);

        saitamaBot = new SaitamaBot();
        saitamaBot.setup();
        saitamaBot.start();
        saitamaBot.onMessageReceived();
        saitamaBot.removeMessage();

    }
}
