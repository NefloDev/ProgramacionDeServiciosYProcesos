import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader txt = new BufferedReader(new InputStreamReader(System.in));
        String input;
        String output;
        Command cm = new Command(new String[]{""}, "C:" + File.separator);

        do{
            System.out.print(cm.getPath().getAbsolutePath() + " >> ");
            input = txt.readLine();
            if(!input.equalsIgnoreCase("exit")){
                if(!input.contains(">")){
                    cm.setCmd(input.split(" "));
                    cm.setPath(cm.getPath().getAbsolutePath());
                }else{
                    Command temp = new Command(input);
                    cm.setCmd(temp.getCmd());
                    cm.setRedirectedPath(temp.getRedirectedPath());
                }
                output = cm.executeCommand();
                if(!output.isEmpty()){
                    System.out.println(output);
                }
                cm.setRedirectedPath(null);
            }
        }while (!input.equalsIgnoreCase("exit"));
    }

}
