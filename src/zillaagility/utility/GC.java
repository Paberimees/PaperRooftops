package zillaagility.utility;

import java.util.ArrayList;
import java.util.List;

public class GC {

    public static int FAILED_ATTEMPTS = 0;

    public static int TOTAL_FAILED_CLICKS = 0;

    public static List<String> FAILED_OBSTACLES = new ArrayList<>();

    public static int RUN_ENERGY_MINIMUM = 70;

    public static int TOTAL_FAILED_MARK_CLICKS = 0;

    public static void randomizeRunEnergyMinimum() {
        int max = 90;
        int min = 40;
        RUN_ENERGY_MINIMUM = (int)(Math.random()*(max-min))+min;
    }

}
