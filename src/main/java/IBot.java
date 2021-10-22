import org.javacord.api.event.message.MessageCreateEvent;

public interface IBot {

    void setup();
    void start();
    void disconnect();
    void receiveMessage();
    void removeMessage();
}
