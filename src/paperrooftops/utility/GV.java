package paperrooftops.utility;

import java.util.ArrayList;
import java.util.List;

//todo refractor names for better understandability.
/*
Notes:
- GV stands for Global Variables.
 */
public class GV {

    public static int FAILED_ATTEMPTS = 0;

    public static int TOTAL_FAILED_CLICKS = 0;

    public static List<String> FAILED_OBSTACLES = new ArrayList<>();

    public static int RUN_ENERGY_MINIMUM = 70;

    public static int TOTAL_FAILED_MARK_CLICKS = 0;

    public static int CONCURRENT_FAILED_MARK_CLICKS = 0;

    public static int MARK_TILEHEIGHT_DIFF = 0;

    public static int HEALTH_MINIMUM = 8;

    public static void randomizeRunEnergyMinimum() {
        int max = 90;
        int min = 40;
        RUN_ENERGY_MINIMUM = (int)(Math.random()*(max-min))+min;
    }

}
