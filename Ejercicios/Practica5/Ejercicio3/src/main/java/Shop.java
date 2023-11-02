import java.util.Random;

public class Shop {
    private static final int MAX_PLAYSTATION_AMOUNT = 20;

    private int playStationAmount;

    public Shop() {
        playStationAmount = MAX_PLAYSTATION_AMOUNT;
    }

    public boolean buyPlayStation(int id){
        playStationAmount -= playStationAmount > 0 ? 1 : 0;
        return playStationAmount > 0;
    }

    public boolean tryToBuyPlayStation(){
        Random random = new Random();
        return random.nextInt(100) < 10;
    }

    public int getPlayStationAmount() {
        return playStationAmount;
    }

}
