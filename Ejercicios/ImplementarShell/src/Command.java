import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * <h2>Command Class, it's used to manage command input and output like a shell</h2>
 * @author AlejandroNebot
 * @version 1.0
 * @since 1.0
 */

public class Command {

    /**
     * Array of Strings attribute called "cmd"
     */
    private String[] cmd;
    /**
     * File attribute called "path"
     */
    private File path;
    /**
     * String attribute called "redirectedPath"
     */
    private String redirectedPath;
    /**
     * String final static attribute called "CMDCALL" specifies the call to linux's terminal to execute commands
     */
    private final static String CMDCALL = "/bin/bash";
    /**
     * String final static attribute called "CMDSTART" tells the terminal to execute the following command on the input
     */
    private final static String CMDSTART = "-c";
    /**
     * Integer final static attribute called "CMDARRAYSTARTINDEX" specifies the index where the command starts in the cmd array
     */
    private final static int CMDARRAYSTARTINDEX = 2;

    /**
     * Creates a command with an array of commands and a path to be executed at
     * @param cmd Array of Strings containing commands
     * @param path String that has the path where the commands will be executed at
     */
    public Command(String[] cmd, String path){
        this.cmd = cmd;
        this.path = new File(path);
        initCmd();
    }

    /**
     * Creates a Command from a string with a command that has a redirected output
     * @param cmd String containing a command with a redirection path
     */
    public Command(String cmd){
        this.cmd = cmd.substring(0, cmd.indexOf(">")).split(" ");
        this.redirectedPath = cmd.substring(cmd.indexOf(">")+1).strip();
        initCmd();
    }

    /**
     * Initializes the cmd array with the necessary cmd specifications to allow java to execute the commands
     */

    private void initCmd(){
        String[] old = cmd;
        cmd = new String[cmd.length+2];
        cmd[0] = CMDCALL;
        cmd[1] = CMDSTART;

        //Copio los comandos al nuevo array de comandos
        System.arraycopy(old, 0, cmd, 2, old.length);
    }

    /**
     * Sets the command's array of commands
     * @param cmd Array of Strings containing commands
     */
    public void setCmd(String[] cmd) {
        this.cmd = cmd;
        initCmd();
    }

    /**
     * Sets the command's path
     * @param path String that has the path where the commands will be executed at
     */
    public void setPath(String path) {
        this.path = new File(path);
    }

    /**
     * Sets the command's redirection path, where the output will be written to
     * @param redirectedPath String containing a redirection path
     */
    public void setRedirectedPath(String redirectedPath) {
        this.redirectedPath = redirectedPath;
    }

    /**
     * Gets the command's command
     * @return An array of strings containing the commands to be executed
     */
    public String[] getCmd() {
        return cmd;
    }

    /**
     * Gets the command's path
     * @return A File where the command will be executed
     */
    public File getPath() {
        return path;
    }

    /**
     * Gets the command's redirection path
     * @return A String containing the path of the file where the command's output will be written to
     */
    public String getRedirectedPath() {
        return redirectedPath;
    }

    /**
     * Changes the path to execute the command if a "cd" command has been executed
     */
    public void cdCommand(){
        List<String> temp = Arrays.asList(cmd);
        String path;
        File tempFile;

        if(temp.contains("cd")){

            path = cmd[temp.indexOf("cd")+1];
            if(!(path.equals("..") || path.equals("."))){

                if(!Files.exists(Path.of(path))){

                    path = getPath().getAbsolutePath() + File.separator + path;
                }
            }else if(path.equals("..")){

                path = getPath().getAbsolutePath().substring(0, getPath().getAbsolutePath().lastIndexOf(File.separator)) + File.separator;
            }else{

                path = getPath().getAbsolutePath();
            }

            tempFile = Files.exists(Path.of(path)) ? new File(path) : getPath();
            setPath(tempFile.getAbsolutePath());
        }
    }

    /**
     * Gives a String with the commands of the Command
     * @return A String with the information about the Command
     */
    @Override
    public String toString(){
        StringBuilder command = new StringBuilder();
        for (int i = CMDARRAYSTARTINDEX; i < cmd.length; i++) {
            command.append(cmd[i]).append(" ");
        }
        return "Command: " + "\"" + command.toString().strip() + "\"";
        //No se como pasar la cantidad de parámetros ya que de la forma en la que separo el comando
        //no quedan claros los parámetros, además el id del comando tampoco lo entiendo
    }

    /**
     * Executes a command using a Process Builder, depending on if it has a redirected output or not, it will return the
     * output, or it will write it into a file
     * @return A String containing the output of the command that has been executed
     */
    public String executeCommand(){
        String line;
        StringBuilder sb = new StringBuilder();
        ProcessBuilder pb = new ProcessBuilder();

        pb.command(this.getCmd());
        pb.directory(this.getPath());

        if(cmd[CMDARRAYSTARTINDEX].equals("cd")){
            this.cdCommand();
        }else{
            try{
                Process p = pb.start();
                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                if(redirectedPath == null){
                    System.out.println("Process output:");
                    while ((line=br.readLine())!=null){
                        sb.append(line).append("\n");
                    }
                }else{
                    createIfNotExists(redirectedPath);
                    FileWriter fw =  new FileWriter(redirectedPath);
                    while((line = br.readLine()) != null){
                        fw.write(line + "\n");
                    }
                    fw.close();
                    redirectedPath = null;
                }
            }catch (IOException e){
                System.err.println(e.getMessage());
            }
        }

        return sb.toString();
    }

    /**
     * Creates a File with the name given if it doesn't exist, having into account if it has a full path or just a name,
     * so if it is just a name it will create the file into a new directory called "ShellOutput" inside the path where the Command
     * is executing at
     * @param fileName String containing the name/path of the file to be created
     */
    private void createIfNotExists(String fileName){
        File f;
        int lastIndex = fileName.lastIndexOf(File.separator)+1;
        String absolutePath = fileName.substring(0, lastIndex);

        if(!absolutePath.equals(getPath().getAbsolutePath()) && !absolutePath.isEmpty()){
            File directory = new File(getPath().getAbsolutePath() + File.separator + absolutePath);
            if(!directory.exists()){
                directory.mkdirs();
            }
            f = new File(directory.getAbsolutePath() + File.separator + fileName.substring(lastIndex));
        }else{
            f = new File(getPath().getAbsolutePath() + File.separator + "ShellOutput");
            f.mkdir();
            f = new File(f.getAbsolutePath() + File.separator + fileName.substring(lastIndex));
        }

        try{
            f.createNewFile();
            redirectedPath = f.getAbsolutePath();
        }catch(IOException e){
            System.err.println("Error creating the file");
        }
    }
}
