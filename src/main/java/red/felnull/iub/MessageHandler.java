package red.felnull.iub;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import red.felnull.iub.util.GDriveUtil;
import red.felnull.iub.util.URLUtils;

import java.io.InputStream;

public class MessageHandler extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.getChannel().getIdLong() == 807931303404109845L && !e.getAuthor().isBot()) {
            e.getMessage().getAttachments().forEach(n -> {
                sendFile(n.getUrl(), n.getFileName());
            });
        }
    }

    private static void sendFile(String url, String name) {

        System.out.println("Upload: " + name + " : " + url);

        InputStream stream = URLUtils.getStream(url);

        if (stream == null)
            return;

        try {
            GDriveUtil.upload(Main.drive, name, stream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
