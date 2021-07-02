package red.felnull.iub.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class URLUtils {
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.104 Safari/537.36";

    public static InputStream getStream(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.addRequestProperty("user-agent", USER_AGENT);
            return connection.getInputStream();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
