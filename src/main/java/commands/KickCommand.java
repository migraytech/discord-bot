package commands;

import service.ServerCommand;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

public class KickCommand extends ServerCommand {


    public KickCommand( ) {
        super("kick");

    }

    @Override
    protected void runCommand(MessageCreateEvent event, Server server, ServerTextChannel channel, User user, String[] args) {

        if(event.getServer().isPresent()){

            try {
                if(event.getServer().isPresent()){

                    if(event.getMessageAuthor().isRegularUser()) {
                        event.getChannel().sendMessage("You have no permissions to do that");
                    }
                    //split the Message
                    String username = args[1];
                    User  kickedUser = event.getServer().get().getMembers().stream().filter(user1 -> user1.getName().equals(username)).findFirst().get();
                    event.getServer().get().kickUser(kickedUser);
                    event.getChannel().sendMessage("Kick:  "+ username);

                }


            }
            catch (Exception e){
                e.getStackTrace();
            }

        }
    }


}
