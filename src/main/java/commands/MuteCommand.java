package commands;

import service.MessageBuilderService;
import service.ServerCommand;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.io.File;

public class MuteCommand extends ServerCommand {


    private final MessageBuilderService  messageBuilderService = new MessageBuilderService();
    public MuteCommand() {
        super("mute");
    }

    @Override
    protected void runCommand(MessageCreateEvent event, Server server, ServerTextChannel channel, User user, String[] args) {
        if(event.getServer().isPresent()){

            try {
                if(event.getMessageAuthor().isRegularUser()) {
                    event.getChannel().sendMessage("You have no permissions to do that");
                }
                //split the Message
                String username = args[1];

                if(event.getServer().get().getMembers().stream().anyMatch(user1 -> user1.getName().equals(username))) {
                    User muteUser = event.getServer().get().getMembers().stream().filter(user1 -> user1.getName().equals(username)).findFirst().get();
                    event.getServer().get().muteUser(muteUser);
                    messageBuilderService.sendMessage(event.getMessageAuthor(),"Mute this user","","","","",event.getChannel());
                    event.getChannel().sendMessage(new File("C:/Users/Mignon/Pictures/pic1.jpg"));
                    return;
                }
                event.getChannel().sendMessage("This username:"+ username+" is not in server ");
            }
            catch (Exception e){
                e.getStackTrace();
            }

        }
    }
}
