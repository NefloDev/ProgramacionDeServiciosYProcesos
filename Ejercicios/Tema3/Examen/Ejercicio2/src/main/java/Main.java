import javafx.collections.ListChangeListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;

public class Main {

    public final static String EMPTY_STRING = "#EMPTY#";
    private final static File BASE_FOLDER = Path.of("urls").toFile();

    public static void main(String[] args) {
        String link;
        BufferedReader rd = new BufferedReader(new InputStreamReader(System.in));
        DownloaderAndZipper dwnlZpr = new DownloaderAndZipper();

        dwnlZpr.getUrlList().addListener((ListChangeListener<String>) change -> {
            change.next();
            List<? extends String> added = change.getAddedSubList();
            if (!added.isEmpty()){
                if (added.get(0).equals(EMPTY_STRING)){
                    System.out.println("Se va a proceder a descargar y comprimir los ficheros.");
                    if (!BASE_FOLDER.exists()){
                        BASE_FOLDER.mkdirs();
                    }
                    dwnlZpr.downloadAllUrls(BASE_FOLDER);
                }else{
                    System.out.printf("%s encolado cómo: %s\n", added.get(0), added.get(1));
                }
            }
        });

        do {
            try{
                System.out.print(">> ");
                link = rd.readLine();
                if (link.trim().isEmpty()){
                    dwnlZpr.getUrlList().add(EMPTY_STRING);
                }else{
                    dwnlZpr.getUrlList().addAll(link, randomString());
                }
            }catch (IOException ignored){
                System.err.println("An error has ocurred.");
                rd = new BufferedReader(new InputStreamReader(System.in));
            }
        }while (true);
    }

    private static String randomString(){
        int chars = 20;
        StringBuilder temp = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < chars; i++) {
            temp.append((char)r.nextInt('a', 'z'));
        }
        return temp.toString();
    }

}
