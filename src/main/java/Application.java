import java.io.IOException;

public class Application {

    private static SaitamaBot saitamaBot;
    public static void main(String[] args) throws IOException {

        saitamaBot = new SaitamaBot();
        saitamaBot.setup();
        saitamaBot.start();
    }
}
