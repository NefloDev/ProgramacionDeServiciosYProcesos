import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        final String COMMAND_START = "/bin/bash -c";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String cmd1 = COMMAND_START + " ";
        String cmd2 = COMMAND_START + " ";
        ProcessBuilder pb1;
        ProcessBuilder pb2;
        boolean repeat;

        do {
            repeat = false;
            try {
                System.out.print("Introduce the first command: ");
                cmd1 += br.readLine();
                System.out.print("Introduce the second command: ");
                cmd2 += br.readLine();
            } catch (IOException e) {
                System.err.println("ERROR: Input mismatch");
                repeat = true;
            }
        }while (repeat);

        pb1 = new ProcessBuilder(cmd1.split(" "));
        pb2 = new ProcessBuilder(cmd2.split(" "));

        try {
            Process process = pb1.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder builder =  new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }

            Process process2 = pb2.start();

            BufferedWriter writer = new BufferedWriter(new java.io.OutputStreamWriter(process2.getOutputStream()));
            writer.write(builder.toString());
            writer.close();

            BufferedReader reader2 = new BufferedReader(new InputStreamReader(process2.getInputStream()));
            while ((line = reader2.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            System.err.println("ERROR: " + e.getMessage());
        }

    }

}
