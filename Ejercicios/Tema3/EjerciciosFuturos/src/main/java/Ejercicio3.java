import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Ejercicio3 {
    public static void main(String[] args) {
        List<String> webPages = List.of(
                "https://www.google.com", "https://www.bing.com", "https://github.com", "https://www.youtube.com",
                "https://openwebinars.net", "https://leetcode.com", "https://www.instant-gaming.com/es/",
                "https://bitcoin.org/es/", "https://discord.com", "https://www.nvidia.com/es-es/"
        );
        List<String> webNames = List.of(
                "Google", "Bing", "Github", "YouTube", "OpenWebinars", "LeetCode", "InstantGaming", "BitCoin",
                "Discord", "NVidia"
        );
        File compressed = new File("compressed.zip");
        ArrayList<String> pagesHtml = CompletableFuture.supplyAsync(() -> {
            ArrayList<String> list = new ArrayList<>();
            for(String web : webPages){
                try {
                    CompletableFuture<URL> url = CompletableFuture.supplyAsync(() ->{
                        try{
                            return URI.create(web).toURL();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    CompletableFuture<String> page = CompletableFuture.supplyAsync(() -> {
                        try(BufferedReader reader = new BufferedReader(new InputStreamReader(url.get().openStream()))){
                            String line;
                            StringBuilder text = new StringBuilder();
                            while((line = reader.readLine()) !=  null){
                                text.append(line);
                            }
                            return text.toString();
                        } catch (ExecutionException | InterruptedException | IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    System.out.println(url.get() + " page downloaded.");
                    list.add(page.get());
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
            return list;
        }).whenComplete((list, exception) -> {
            try{
                File resources = Path.of(".", "src", "main","resources").toFile();
                BufferedWriter bW;
                ArrayList<File> files = new ArrayList<>();
                for(int i = 0; i < list.size(); i++){
                    files.add(Path.of(".","src", "main","resources", webNames.get(i) + ".txt").toFile());
                    bW = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(files.get(i)), "UTF-8"));
                    bW.append(list.get(i));
                    bW.close();
                    System.out.println(webNames.get(i) + " page written");
                }
                FileOutputStream fOS = new FileOutputStream(compressed);
                ZipOutputStream zipOut = new ZipOutputStream(fOS);
                zipFile(resources, resources.getName(), zipOut);
                zipOut.close();
                fOS.close();
                System.out.println("Compressed file created at: \"" + compressed.getAbsolutePath() + "\"");
            }catch (IOException ignored){}
        }).join();
    }

    private static void zipFile(File toZip, String fileName, ZipOutputStream zipOut) throws IOException {
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
