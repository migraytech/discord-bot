package bots.listeners.commands;

import service.MessageBuilderService;
import models.ServerCommand;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

public class KickCommand extends ServerCommand {

    private final MessageBuilderService messageBuilderService = new MessageBuilderService();
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
                    if(event.getServer().get().getMembers().stream().anyMatch(user1 -> user1.getName().equals(username))) {
                        User kickedUser = event.getServer().get().getMembers().stream().filter(user1 -> user1.getName().equals(username)).findFirst().get();
                        messageBuilderService.sendMessage(event.getMessageAuthor(),"Kicker "+username," ","requested get kicked "," ", " ",event.getChannel());
                        event.getServer().get().kickUser(kickedUser);
                        event.getChannel().sendMessage("Kick: " + username);
                        return;
                    }
                    event.getChannel().sendMessage("This username:"+ username+" is not in the server ");
                }
            }
            catch (Exception e){
                e.getStackTrace();
            }

        }
    }


}
