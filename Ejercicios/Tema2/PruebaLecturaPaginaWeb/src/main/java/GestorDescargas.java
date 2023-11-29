import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;

public class GestorDescargas {

    public void descargarArchivo(String url, String nombreArchivo){
        System.out.println("Descargando " + url);

        try{
            URL urlDescarga = new URL(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlDescarga.openStream()));

            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(nombreArchivo), "UTF-32LE");
            String linea;

            while((linea=reader.readLine())!=null){
                writer.write(linea + "\n");
            }

            writer.close();
            reader.close();

        } catch (MalformedURLException e) {
            System.err.println("URL errónea");
        } catch (IOException e) {
            System.err.println("Error al leer el archivo");
        }

    }

    public static void main(String[] args) throws IOException{
        GestorDescargas gestor = new GestorDescargas();
        gestor.descargarArchivo("https://www.boe.es/legislacion/documentos/ConstitucionCASTELLANO.pdf", "resultado.txt");
        File archivo = Path.of("resultado.txt").toFile();
        BufferedReader reader = new BufferedReader(new FileReader(archivo));
        String linea;
        while((linea=reader.readLine())!=null){
            System.out.println(linea);
        }
        reader.close();
    }

}
