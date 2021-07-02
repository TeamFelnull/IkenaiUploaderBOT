package red.felnull.iub.util;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import red.felnull.iub.Main;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GDriveUtil {

    private static final String APPLICATION_NAME = "Drive API Java Sample";
    private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"), ".credentials/drive-java-quickstart");
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    static {
        try {
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static HttpTransport HTTP_TRANSPORT;

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);

    public static Drive createService() throws IOException {
        Credential credential = authorize();
        return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static Credential authorize() throws IOException {
        InputStream is = GDriveUtil.class.getResourceAsStream("/credentials.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(is));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();

        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    public static void upload(Drive service, String name, InputStream stream) throws IOException {
        File file = new File();
        file.setName(name);
        file.setParents(Collections.singletonList(Main.SaveFolderID));
        InputStreamContent mediaContent = new InputStreamContent("", stream);
        service.files().create(file, mediaContent).execute();
    }

    public static void upload(Drive service, String name, java.io.File afile) throws IOException {
        File file = new File();
        file.setName(name);
        file.setParents(Collections.singletonList(Main.SaveFolderID));
        FileContent mediaContent = new FileContent("", afile);
        service.files().create(file, mediaContent).execute();
    }

    public static void createFile(Drive service, String path) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(path);
        fileMetadata.setMimeType("application/vnd.google-apps.folder");
        service.files().create(fileMetadata).execute();
    }
}
