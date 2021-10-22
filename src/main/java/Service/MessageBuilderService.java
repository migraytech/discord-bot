package Service;

import interfaces.IMessageBuilderService;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

public class MessageBuilderService implements IMessageBuilderService {


    @Override
    public void sendMessage(MessageAuthor messageAuthor, String title, String description, String footer, String thumbnail, TextChannel textChannel) {
        new MessageBuilder().setEmbed(new EmbedBuilder()
                .setAuthor(messageAuthor)
                .setTitle(title)
                .setFooter(footer)
                .setThumbnail(thumbnail))
                .send(textChannel);
    }
}
