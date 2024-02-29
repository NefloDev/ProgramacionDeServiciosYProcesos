import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class DownloaderAndZipper {

    private ObservableList<String> urlList;

    public DownloaderAndZipper() {
        this.urlList = FXCollections.observableArrayList();
    }

    public ObservableList<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(ObservableList<String> urlList) {
        this.urlList = urlList;
    }

    public void downloadAllUrls(File folder){
        CompletableFuture<Map<URL, String>> urlFuture = CompletableFuture.supplyAsync(() -> {
            Map<URL, String> urls = new HashMap<>();
            for (int i = 0; i < urlList.size(); i++) {
                if (!urlList.get(i).equals(Main.EMPTY_STRING)){
                    try {
                        String temp = urlList.get(i);
                        if (!(temp.startsWith("https://") || temp.startsWith("http://"))){
                            if (!temp.startsWith("www.")){
                                temp = "https://www." + temp;
                            }else{
                                temp = "https://" + temp;
                            }
                        }
                        urls.put(URI.create(temp).toURL(), urlList.get(++i));
                    }catch (IOException ignored){
                        System.err.printf("%s url couldn't be retrieved", urlList.get(i));
                    }
                }
            }
            return urls;
        }).whenComplete((value, nothing) -> urlList.clear());

        CompletableFuture<Map<String,String>> mapFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Map<URL, String> map = urlFuture.get();
                Map<String,String> list = new HashMap<>();
                for(Entry<URL, String> entry : map.entrySet()){
                    try (BufferedReader rd = new BufferedReader(new InputStreamReader(entry.getKey().openStream()))){
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = rd.readLine()) != null){
                            sb.append(line);
                        }
                        list.put(sb.toString(), entry.getValue());
                    } catch (IOException e) {
                        System.err.printf("%s html code couldn't be retrieved\n", entry.getKey());
                    }
                }
                return list;
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("An error has ocurred" + e.getMessage());
                return null;
            }
        });

        CompletableFuture.supplyAsync(() -> {
            try {
                BufferedWriter bW;
                Map<String, String> map = mapFuture.get();
                List<Entry<String, String>> html = map.entrySet().stream().toList();
                ArrayList<File> files = new ArrayList<>();
                for(int i = 0; i<html.size(); i++){
                    files.add(Path.of(folder.getAbsolutePath(), html.get(i).getValue() + ".txt").toFile());
                    bW = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(files.get(i)), "UTF-8"));
                    bW.append(html.get(i).getKey());
                    bW.close();
                }
                FileOutputStream fOS = new FileOutputStream("compressed.zip");
                ZipOutputStream zOS = new ZipOutputStream(fOS);
                zipFile(folder, folder.getName(), zOS);
                zOS.close();
                fOS.close();
                return true;
            } catch (InterruptedException | ExecutionException | IOException e) {
                System.err.println("Couldn't load urls html codes");
                return false;
            }
        });
    }

    private void zipFile(File toZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (!toZip.isHidden()){
            if(toZip.isDirectory()){
                if (!toZip.getName().endsWith("\\")){
                    zipOut.putNextEntry(new ZipEntry(toZip.getName() + "\\"));
                }else{
                    zipOut.putNextEntry(new ZipEntry(toZip.getName()));
                }
                zipOut.closeEntry();
                File[] children = toZip.listFiles();
                if(children != null){
                    for (File child : children){
                        zipFile(child, fileName + "\\" + child.getName(), zipOut);
                    }
                }
            }else{
                FileInputStream fIS = new FileInputStream(toZip);
                ZipEntry zipEntry = new ZipEntry(fileName);
                zipOut.putNextEntry(zipEntry);

                byte[] bytes = new byte[1024];
                int length;
                while ((length = fIS.read(bytes)) >= 0){
                    zipOut.write(bytes, 0, length);
                }
                fIS.close();
            }
        }
    }
}
