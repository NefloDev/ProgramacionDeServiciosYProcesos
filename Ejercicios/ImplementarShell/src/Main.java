import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    /**
     * Main method that handles the command input and executing
     * @param args
     */
    public static void main(String[] args) {
        BufferedReader txt = new BufferedReader(new InputStreamReader(System.in));
        boolean repeat;
        String input = "";
        String output = "";
        Command cm = new Command(new String[]{""}, "C:" + File.separator);

        do{
            do {
                repeat = false;
                try{
                    System.out.print(cm.getPath().getAbsolutePath() + " >> ");
                    input = txt.readLine();
                }catch (IOException e){
                    System.err.println("Input mismatch");
                    repeat = true;
                }finally {
                    txt = new BufferedReader(new InputStreamReader(System.in));
                }
            }while(repeat);
            if(!input.equalsIgnoreCase("exit")){
                if(input.equalsIgnoreCase("last-command")){
                    output = cm.toString();
                }else if(!input.isEmpty()){
                    if(!input.contains(">")){
                        cm.setCmd(input.split(" "));
                        cm.setPath(cm.getPath().getAbsolutePath());
                    }else{
                        Command temp = new Command(input);
                        cm.setCmd(temp.getCmd());
                        cm.setRedirectedPath(temp.getRedirectedPath());
                    }
                    output = cm.executeCommand();
                }
                if(!output.isEmpty()){
                    System.out.println(output);
                }
            }
        }while (!input.equalsIgnoreCase("exit"));
    }

}
