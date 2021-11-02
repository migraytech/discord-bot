package commands;

import Service.ServerCommand;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

public class OnePunchCommand extends ServerCommand {


    public OnePunchCommand() {
        super("one-punch");
    }

    @Override
    protected void runCommand(MessageCreateEvent event, Server server, ServerTextChannel channel, User user, String[] args) {
        if(event.getServer().isPresent()){

            try {
                if(!event.getMessageAuthor().isRegularUser()) {
                    event.getChannel().sendMessage("You have no permissions to do that");
                }
                //split the Message
                String username = args[1];
                User  kickedUser = event.getServer().get().getMembers().stream().filter(user1 -> user1.getName().equals(username)).findFirst().get();
                event.getServer().get().banUser(kickedUser).join();
                event.getChannel().sendMessage("One punch:  "+channel.getName());


            }
            catch (Exception e){
                e.getStackTrace();
            }

        }
    }
}
