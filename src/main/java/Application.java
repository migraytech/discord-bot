public class Application {

    private static SaitamaBot saitamaBot = new SaitamaBot();
    public static void main(String[] args) {

        saitamaBot = new SaitamaBot();
        saitamaBot.setup();
        saitamaBot.start();
    }
}
