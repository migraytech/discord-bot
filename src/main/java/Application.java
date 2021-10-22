import java.io.IOException;

public class Application {

    private static SaitamaBot saitamaBot;

    public static void main(String[] args)  {
        saitamaBot = new SaitamaBot();
        saitamaBot.setup();
        saitamaBot.start();


        saitamaBot.onMessageReceived();
        saitamaBot.removeMessage();
    }
}
