package commands;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageSet;
import service.ServerCommand;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CleanCommand extends ServerCommand {

    public CleanCommand() {
        super("clean");

    }

    @Override
    protected void runCommand(MessageCreateEvent event, Server server, ServerTextChannel channel, User user, String[] args) {

        try{
            if(event.getServer().isPresent()){
                if(!event.getMessageAuthor().isRegularUser()) {
                    event.getChannel().sendMessage("You have no permissions to do that");
                }

                int counter = (int) channel.getMessagesAsStream().count();
                System.out.println("Messages counter"+counter);
                channel.getMessages(counter).get().deleteAll();
                event.getChannel().sendMessage("Clean the Text Channel Messages!:  "+channel.getName());

            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
