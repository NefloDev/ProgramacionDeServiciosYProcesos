import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {
        ObservableList<String> urlList = FXCollections.observableArrayList();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        urlList.addListener((ListChangeListener<String>) change -> {
            change.next();
            String url = change.getAddedSubList().get(0);
            System.out.printf("Started downloading \"%s\"\n", url);
            initializeDownload(url);
        });
        do{
            try{
                System.out.print(">> ");
                urlList.add(reader.readLine());
            }catch (IOException ignored){
            }
        }while(true);
    }
    private static void initializeDownload(String url){
        CompletableFuture<URL> urlToDownload = CompletableFuture.supplyAsync(() -> {
            try{
                return URI.create(url).toURL();
            }catch (IOException e){
                System.err.println("Url couldn't be retrieved");
                return null;
            }
        });
        CompletableFuture.supplyAsync(() -> {
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(urlToDownload.get().openStream()))){
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null){
                    sb.append(line);
                }
                return sb.toString();
            } catch (IOException | ExecutionException | InterruptedException e) {
                System.err.println("Page html code couldn't be retrieved");
                return null;
            }
        }).whenComplete((page, nothing) -> {
            try {
                String webPage = urlToDownload.get().toString();
                webPage = webPage.substring(0, webPage.lastIndexOf('.'));
                webPage = webPage.substring(webPage.lastIndexOf('.')+1);
                createFile(page, webPage);
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("File couldn't be created");
            }
        });
    }
    private static void createFile(String text, String webName){
        if(text != null){
            File f = Path.of(webName + ".txt").toFile();
            try{
                f.createNewFile();
                FileWriter fW = new FileWriter(f);
                fW.write(text);
                fW.close();
            } catch (IOException e) {
                System.err.printf("Web page couldn't be saved into file: %s", f.getPath());
            }
        }
    }
}
