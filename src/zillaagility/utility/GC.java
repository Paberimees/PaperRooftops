package zillaagility.utility;

public class GC {

    public static int RUN_ENERGY_MINIMUM = 70;

    public static void randomizeRunEnergyMinimum() {
        int max = 90;
        int min = 40;
        RUN_ENERGY_MINIMUM = (int)(Math.random()*(max-min))+min;
    }

}
