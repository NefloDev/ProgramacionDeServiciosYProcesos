import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Ejercicio2 {
    public static void main(String[] args) {
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Ruta1: ");
            String route1 = reader.readLine();
            System.out.print("Ruta2: ");
            String route2 = reader.readLine();
            String compressed = "compressed.zip";
            CompletableFuture.runAsync(() -> {
                try{
                    File toZip = Path.of(route1).toFile();
                    FileOutputStream fOS = new FileOutputStream(compressed);
                    ZipOutputStream zipOut = new ZipOutputStream(fOS);
                    zipFile(toZip, toZip.getName(), zipOut);
                    zipOut.close();
                    fOS.close();
                }catch (IOException ignored){}
            }).whenComplete((a, b) -> {
                try {
                    Files.move(Path.of(compressed), Path.of(route2, compressed));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).join();
        }catch (IOException ignored){}

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
