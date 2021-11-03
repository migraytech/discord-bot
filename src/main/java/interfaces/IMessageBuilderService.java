package interfaces;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public interface IMessageBuilderService {

    void sendMessage(MessageAuthor messageAuthor, String title, String description, String footer, String thumbnail,String url ,TextChannel textChannel);
}
