package commands;

import Service.ServerCommand;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

public class BanCommand extends ServerCommand {


    public BanCommand() {
        super("ban");
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
                event.getServer().get().banUser(kickedUser);
                event.getChannel().sendMessage("Ban:  "+ username);


            }
            catch (Exception e){
                e.getStackTrace();
            }

        }

    }


    private void banUserFromServer(User user,MessageCreateEvent event){


        // ban user from server

        // send message that user is ban

    }
}
