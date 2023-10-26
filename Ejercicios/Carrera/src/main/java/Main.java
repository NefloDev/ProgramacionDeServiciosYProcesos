import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Runner> runners = List.of(
                getData(1),
                getData(2),
                getData(3),
                getData(4),
                getData(5)
        );

        Race race = new Race(runners, 20);

        race.startRace();
        race.endRace();

    }

    public static Runner getData(int num){
        System.out.println("Runner " + num + " data:");
        return new Runner(
                getCharacter("Introduce Runner character: "),
                getIntBetweenMinMax(1, 5, "Introduce Runner base speed (1-5): "),
                getIntBetweenMinMax(1, 5, "Introduce Runner turbo probability (1-5): "),
                getIntBetweenMinMax(1, 5, "Introduce Runner crash probability (1-5): ")
                );
    }

    public static int getIntBetweenMinMax(int min, int max, String message){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int input = 0;
        boolean repeat;
        do{
            repeat = false;

            try{
                System.out.print(message);
                input = Integer.parseInt(reader.readLine());
                if(input < min || input > max){
                    System.out.println("Input must be between " + min + " and " + max);
                    repeat = true;
                }
            }catch (Exception e){
                repeat = true;
                System.err.println("Input mismatch");
            }finally {
                reader = new BufferedReader(new InputStreamReader(System.in));
            }

        }while (repeat);

        return input;
    }

    public static char getCharacter(String message){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        boolean repeat;
        do{
            repeat = false;

            try{
                System.out.print(message);
                input = reader.readLine();
                if(input.length() != 1){
                    System.out.println("Input must be one character");
                    repeat = true;
                }
            }catch (Exception e){
                repeat = true;
                System.err.println("Input mismatch");
            }finally {
                reader = new BufferedReader(new InputStreamReader(System.in));
            }

        }while (repeat);

        return input.charAt(0);
    }

}
