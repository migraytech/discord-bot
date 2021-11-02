package commands;

import Service.ServerCommand;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

public class CleanCommand extends ServerCommand {

    public CleanCommand() {
        super("clean");

    }

    @Override
    protected void runCommand(MessageCreateEvent event, Server server, ServerTextChannel channel, User user, String[] args) {

        try{
            if(!event.getMessageAuthor().isRegularUser()) {
                event.getChannel().sendMessage("You have no permissions to do that");
            }
            event.getChannel().bulkDelete(99L);
            event.getChannel().sendMessage("Clean the Text Channel!:  "+channel.getName());

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
