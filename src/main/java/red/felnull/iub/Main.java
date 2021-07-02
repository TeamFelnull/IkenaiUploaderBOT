package red.felnull.iub;

import com.google.api.services.drive.Drive;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import red.felnull.iub.util.GDriveUtil;

public class Main {
    public static final String SaveFolderID = "1koUwpR2p8vcS4AdbSj-3KKVP-N8hIyb4";
    public static JDA jda;
    public static Drive drive;

    public static void main(String[] args) throws Exception {

        String discordToken = "";

        drive = GDriveUtil.createService();
        jda = JDABuilder.createDefault(discordToken).build();
        jda.getPresence().setPresence(OnlineStatus.ONLINE, Activity.playing("ﾊｧｯ…ﾊｯ ｲｷｽｷﾞｨ!(ﾎｫﾝ!)ｲｸｩｲｸｲｸｩｨｸ…ｱｯﾊｯ、ﾝｱｯｰ!!ｱｧｯｱｯ…ｱｯ…ﾊﾝ、ｳｯ!!…ｯ"));
        jda.addEventListener(new MessageHandler());
    }
}
