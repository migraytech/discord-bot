package models;

import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class ModeratorBase implements MessageCreateListener {

    //List bad words

    static protected  final ArrayList<String> moderatorsWords =  new ArrayList(Arrays.asList("bad","fuck"));

    //list count User
    static protected final HashMap<User,Integer> violationCounter = new HashMap<>();

    
    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {

        // These first two ifs are useless, but let's just leave it there for reasons. (it's useless since there is already ifPresent(...) below.
        if(!messageCreateEvent.isServerMessage())
            return;

        if(!messageCreateEvent.getMessageAuthor().isRegularUser())
            return;

        // Runs everything.
        messageCreateEvent.getServer().ifPresent(server -> messageCreateEvent.getMessageAuthor().asUser().ifPresent(user ->
                messageCreateEvent.getServerTextChannel().ifPresent(serverTextChannel -> checkMessage(messageCreateEvent, server, serverTextChannel, user, messageCreateEvent.getMessageContent().split(" ")))));

    }


    protected abstract void checkMessage(MessageCreateEvent event, Server server, ServerTextChannel channel, User user, String[] args);

    public abstract void sendMessageToUser(MessageCreateEvent event,User user);


}
