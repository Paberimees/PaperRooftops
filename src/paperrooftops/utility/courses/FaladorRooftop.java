package paperrooftops.utility.courses;

import org.powerbot.script.Area;
import org.powerbot.script.Locatable;
import org.powerbot.script.Tile;

import java.util.ArrayList;
import java.util.List;

public class FaladorRooftop extends Course {

    private String courseName;
    private int levelRequirement;
    private int iterator;
    private List<Obstacle> trackList = new ArrayList<>();

    public FaladorRooftop() {
        courseName = "Falador rooftop";
        levelRequirement = 50;
        iterator = 0;
        //Adding all the course obstacles one by one.

        //int[] defaultBounds = {-12, 12, -128, -112, -12, 12};
        //int[] defaultBoundsPoleVault = {-12, 12, -152, -136, -12, 12};
        //int[] defaultBounds3 = {-12, 12, -96, -80, -12, 12};
        int[] actualDefaultBounds = {-32, 32, -64, 0, -32, 32};
        int[] debug_0 = {-32, 32, -64, 0, -32, 32}; //rough wall
        int[] debug_1 = {-32, 32, -65, -61, -32, 32}; //tightrope
        int[] debug_2 = {-16, 16, -112, -80, -16, 16}; //handholds
        int[] debug_3 = {-32, 32, -97, -33, -32, 32}; //gap
        int[] debug_4 = {-32, 32, -38, 26, -32, 32}; //gap
        int[] debug_5 = {-32, 32, -37, -33, -32, 32}; //tightrope
        int[] debug_6 = {-32, 32, -46, -42, -32, 32}; //tightrope
        int[] debug_7 = {-32, 32, 95, 159, -32, 32}; //gap
        int[] debug_8 = {-32, 32, -150, -86, -32, 32}; //ledge
        int[] debug_9 = {-32, 32, -16, 48, -32, 32}; //ledge
        int[] debug_10 = {-32, 32, -124, -60, -32, 32}; //ledge
        int[] debug_11 = {-32, 32, -112, -48, -32, 32}; //ledge
        int[] debug_12 = {-32, 32, -36, 28, -32, 32}; //edge
        int[] actualDefaultBounds_ledge = {-32, 32, -152, -88, -32, 32}; //ydiff 88

        int[] tightRopeBounds3 = {-16, 16, -96, -92, -16, 16};
        int[] tightRopeBounds3_bugged = {-16, 16, -96, -92, -16, 16};
        int[] defaultBounds3 = {-8, 8, -40, -24, -8, 8};
        int[] defaultBounds3_bugged_gap = {-8, 8, 320, 328, -8, 8};

        int[] defaultBounds3_handholds = {-8, 8, 310, 318, -128, -136}; //for viewport easeness

        int[] defaultBounds3_ledges = {-8, 8, -256, -244, -8, 8};
        int[] defaultBounds3_ledges_third_littlebitlower = {-8, 8, -224, -220, -8, 8};
        int[] defaultBounds3_ledges_lower = {-8, 8, 32, 24, -8, 8};
        int[] defaultBounds3_ledges_lower_bugged = {-8, 8, -72, -64, -8, 8};
        int[] defaultBounds3_gap_lower_y = {-8, 8, 0, 24, -8, 8};


        trackList.add(new Obstacle("Climb",
                "Rough wall",
                new int[]{-16, 16, -184, -136, 36, 44}, //custom bounds for rough walls usually. work well.
                //actualDefaultBounds,
                new Area(new Tile(3039, 3341,0), new Tile(3034, 3338,0)))); //0
        trackList.add(new Obstacle("Cross",
                "Tightrope",
                //tightRopeBounds3_bugged,
                debug_1,
                new Area(new Tile(3034, 3348,3), new Tile(3041, 3341, 3)))); //3
        trackList.add(new Obstacle("Cross",
                "Hand holds",
                //defaultBounds3_handholds,
                debug_2,
                new Area(new Tile(3043, 3340,3), new Tile(3052, 3350, 3)))); //3
        trackList.add(new Obstacle("Jump",
                "Gap",
                //defaultBounds3,
                debug_3,
                new Area(new Tile(3047, 3359,3), new Tile(3051, 3355, 3)), //3
                14903));
        trackList.add(new Obstacle("Jump",
                "Gap",
                //defaultBounds3,
                debug_4,
                new Area(new Tile(3044, 3368,3), new Tile(3049, 3360, 3)), //3
                14904));
        trackList.add(new Obstacle("Cross",
                "Tightrope",
                //defaultBounds3,
                debug_5,
                new Area(new Tile(3033, 3365,3), new Tile(3041, 3360, 3)))); //3
        trackList.add(new Obstacle("Cross",
                "Tightrope",
                //tightRopeBounds3,
                debug_6,
                new Area(new Tile(3025, 3356,3), new Tile(3030, 3351, 3)))); //3
        trackList.add(new Obstacle("Jump",
                "Gap",
                //defaultBounds3_bugged_gap,
                debug_7,
                new Area(new Tile(3008, 3359,3), new Tile(3022, 3352, 3)))); //3
        trackList.add(new Obstacle("Jump", //wow these ledge bounds are fucked
                "Ledge",
                //defaultBounds3_ledges,
                debug_8,
                new Area(new Tile(3016, 3343,3), new Tile(3022, 3350, 3)),
                14920)); //3
        trackList.add(new Obstacle("Jump",
                "Ledge",
                //defaultBounds3_ledges_lower,
                debug_9,
                new Area(new Tile(3010, 3343,3), new Tile(3015, 3349, 3)),
                14921)); //3
        trackList.add(new Obstacle("Jump",
                "Ledge",
                //defaultBounds3_ledges_third_littlebitlower,
                debug_10,
                new Area(new Tile(3008, 3342,3), new Tile(3013, 3335, 3)),
                14922)); //3
        trackList.add(new Obstacle("Jump",
                "Ledge",
                //defaultBounds3_ledges_lower_bugged,
                debug_11,
                new Area(new Tile(3011, 3334,3), new Tile(3018, 3331, 3)),
                14924)); //3
        trackList.add(new Obstacle("Jump",
                "Edge",
                //defaultBounds3,
                debug_12,
                new Area(new Tile(3019, 3335,3), new Tile(3027, 3332, 3)))); //3
    }

    @Override
    public Obstacle getCurrentObstacle(Locatable x) {
        if (trackList.stream().anyMatch((obs) -> obs.getStartArea().containsOrIntersects(x))) {
            return trackList.stream().filter((obs) -> obs.getStartArea().containsOrIntersects(x)).findFirst().get();
        }
        return null;
    }

    @Override
    public Obstacle getStartingObstacle() {
        return trackList.get(0);
    }

    @Override
    public String getName() {
        return courseName;
    }

    @Override
    public int getObstacleIndex(Obstacle obstacle) {
        return trackList.indexOf(obstacle);
    }
}
