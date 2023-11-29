import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Peticion implements Runnable{
    BufferedReader bfr;
    PrintWriter pw;
    Socket socket;
    public Peticion(Socket socket){
        this.socket=socket;
    }

    public static int extraerNumero(String linea){
        int num;
        try{
            num = Integer.parseInt(linea);
        }catch (NumberFormatException e){
            num = 0;
        }
        if(num > 100000000){
            num = 0;
        }
        return num;
    }

    public static int calcular(String op, String n1, String n2){
        int resultado = 0;
        char simbolo = op.charAt(0);
        int num1 = extraerNumero(n1);
        int num2 = extraerNumero(n2);
        if(simbolo == '+') {
            resultado = num1 + num2;
        }
        return resultado;
    }
    @Override
    public void run() {
        try{
            bfr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw=new PrintWriter(socket.getOutputStream());
            String linea;
            while ((linea = bfr.readLine()) != null){
                String num1=bfr.readLine();
                String num2=bfr.readLine();
                Integer result=calcular(linea, num1, num2);
                System.out.println("El servidor dio resultado:"+result);
                pw.write(result+"\n");
                pw.flush();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
