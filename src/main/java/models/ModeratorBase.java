package models;

import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public abstract class ModeratorBase implements MessageCreateListener {

    //List bad words

    //list count User



    //create a model class that constructor of the discord api , ModeratorBot)

    
    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {


        // check if present




    }


    protected abstract void checkMessage(MessageCreateEvent event, Server server, ServerTextChannel channel, User user, String[] args);

    public abstract void sendMessageToUser(MessageAuthor author);


}
