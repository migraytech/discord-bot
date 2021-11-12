package bots;


import interfaces.IBot;
import models.ModeratorBase;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

public class ModeratorBot extends ModeratorBase implements IBot{


    @Override
    public void setup() {

    }

    @Override
    public void start() {

    }

    @Override
    public void disconnect() {

    }

    @Override
    public void onMessageReceived() {

    }

    @Override
    public void removeMessage() {

    }

    @Override
    protected void checkMessage(MessageCreateEvent event, Server server, ServerTextChannel channel, User user, String[] args) {




    }

    @Override
    public void sendMessageToUser(MessageAuthor author) {





    }


    //checkInList
    //count






}
