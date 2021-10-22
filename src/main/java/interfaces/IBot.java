package interfaces;

public interface IBot {

    void setup();
    void start();
    void disconnect();
    void onMessageReceived();
    void removeMessage();
}
