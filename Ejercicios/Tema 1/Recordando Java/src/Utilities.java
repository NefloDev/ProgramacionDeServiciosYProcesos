
public class Utilities {

    public static void fillWithRandomNumbers(int[] list){
        int max = (int)(Integer.MAX_VALUE*0.1);
        int min = 0;

        for (int i = 0; i < list.length; i++) {
            list[i] = (int)(Math.random()*((max-min)+1));
        }
    }

    public static boolean naive(int num){
        int count = 0;
        int i = num-1;

        if(num >= 2){
            do {
                count = num%i==0 ? 1 : 0;
                i--;
            }while(i>=2 && count==0);
        }else{
            count += num==0 ? 0 : 1;
        }

        return count==0;
    }

}