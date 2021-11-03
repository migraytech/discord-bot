package commands;

import service.MessageBuilderService;
import service.ServerCommand;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import java.io.File;


public class OnePunchCommand extends ServerCommand {

    private final MessageBuilderService messageBuilderService = new MessageBuilderService();
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
                System.out.println("Start to one punch the user");
                String username = args[1];
                if(event.getServer().get().getMembers().stream().anyMatch(user1 -> user1.getName().equals(username))) {
                    messageBuilderService.sendMessage(event.getMessageAuthor(),"ONE PUNCH "+username,"FOUND YOU!","Its over for you!!"," ", " ",event.getChannel());
                    event.getChannel().sendMessage(new File("C:/Users/Mignon/Pictures/pic5.jpg"));
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
