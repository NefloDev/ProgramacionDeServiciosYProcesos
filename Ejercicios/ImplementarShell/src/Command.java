import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Command {

    private String[] cmd;
    private File path;
    private String redirectedPath;
    private final static String CMDCALL = "cmd.exe";
    private final static String CMDSTART = "/c";
    private final static int CMDARRAYSTARTINDEX = 2;

    public Command(String[] cmd, String path){
        this.cmd = cmd;
        this.path = new File(path);
        initCmd();
    }

    public Command(String cmd){
        this.cmd = cmd.substring(0, cmd.indexOf(">")).split(" ");
        this.redirectedPath = cmd.substring(cmd.indexOf(">")+1).strip();
        initCmd();
    }

    private void initCmd(){
        String[] old = cmd;
        cmd = new String[cmd.length+2];
        cmd[0] = CMDCALL;
        cmd[1] = CMDSTART;

        //Copio los comandos al nuevo array de comandos
        System.arraycopy(old, 0, cmd, 2, old.length);
    }

    public void setCmd(String[] cmd) {
        this.cmd = cmd;
        initCmd();
    }

    public void setPath(String path) {
        this.path = new File(path);
    }

    public void setRedirectedPath(String redirectedPath) {
        this.redirectedPath = redirectedPath;
    }

    public String[] getCmd() {
        return cmd;
    }

    public File getPath() {
        return path;
    }

    public String getRedirectedPath() {
        return redirectedPath;
    }

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

    @Override
    public String toString(){
        StringBuilder command = new StringBuilder();
        for (int i = CMDARRAYSTARTINDEX; i < cmd.length; i++) {
            command.append(cmd[i]);
        }
        return "Command: " + command.toString();
    }

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
                    FileWriter fw =  new FileWriter(new File(redirectedPath));
                    while((line = br.readLine()) != null){
                        fw.write(line + "\n");
                    }
                    fw.close();
                }
            }catch (IOException e){
                System.err.println(e.getMessage());
            }
        }

        return sb.toString();
    }

    private void createIfNotExists(String fileName){
        File f = null;
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
