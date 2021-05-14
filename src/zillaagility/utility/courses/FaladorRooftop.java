package zillaagility.utility.courses;

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

        int[] tightRopeBounds3 = {-16, 16, -96, -92, -16, 16};
        int[] tightRopeBounds3_bugged = {-16, 16, -96, -92, -16, 16};
        int[] defaultBounds3 = {-8, 8, -40, -24, -8, 8};
        int[] defaultBounds3_handholds = {-8, 8, -40, 48, -8, 8}; //for viewport easeness
        int[] defaultBounds3_ledges = {-8, 8, -256, -244, -8, 8};
        int[] defaultBounds3_ledges_third_littlebitlower = {-8, 8, -224, -220, -8, 8};
        int[] defaultBounds3_ledges_lower = {-8, 8, 32, 24, -8, 8};
        int[] defaultBounds3_gap_lower_y = {-8, 8, 0, 24, -8, 8};


        trackList.add(new Obstacle("Climb",
                "Rough wall",
                new int[]{-16, 16, -184, -136, 36, 44},
                new Area(new Tile(3039, 3341,0), new Tile(3034, 3338,0)))); //0
        trackList.add(new Obstacle("Cross",
                "Tightrope",
                tightRopeBounds3_bugged,
                new Area(new Tile(3034, 3348,3), new Tile(3041, 3341, 3)))); //3
        trackList.add(new Obstacle("Cross",
                "Hand holds",
                defaultBounds3_handholds,
                new Area(new Tile(3043, 3340,3), new Tile(3052, 3350, 3)))); //3
        trackList.add(new Obstacle("Jump",
                "Gap",
                defaultBounds3,
                new Area(new Tile(3047, 3359,3), new Tile(3051, 3355, 3)), //3
                14903));
        trackList.add(new Obstacle("Jump",
                "Gap",
                defaultBounds3,
                new Area(new Tile(3044, 3368,3), new Tile(3049, 3360, 3)), //3
                14904));
        trackList.add(new Obstacle("Cross",
                "Tightrope",
                defaultBounds3,
                new Area(new Tile(3033, 3365,3), new Tile(3041, 3360, 3)))); //3
        trackList.add(new Obstacle("Cross",
                "Tightrope",
                tightRopeBounds3,
                new Area(new Tile(3025, 3356,3), new Tile(3030, 3351, 3)))); //3
        trackList.add(new Obstacle("Jump",
                "Gap",
                defaultBounds3,
                new Area(new Tile(3008, 3359,3), new Tile(3022, 3352, 3)))); //3
        trackList.add(new Obstacle("Jump", //wow these ledge bounds are fucked
                "Ledge",
                defaultBounds3_ledges,
                new Area(new Tile(3016, 3343,3), new Tile(3022, 3350, 3)),
                14920)); //3
        trackList.add(new Obstacle("Jump",
                "Ledge",
                defaultBounds3_ledges_lower,
                new Area(new Tile(3010, 3343,3), new Tile(3015, 3349, 3)),
                14921)); //3
        trackList.add(new Obstacle("Jump",
                "Ledge",
                defaultBounds3_ledges_third_littlebitlower,
                new Area(new Tile(3008, 3342,3), new Tile(3013, 3335, 3)),
                14922)); //3
        trackList.add(new Obstacle("Jump",
                "Ledge",
                defaultBounds3_ledges_lower,
                new Area(new Tile(3011, 3334,3), new Tile(3018, 3331, 3)),
                14924)); //3
        trackList.add(new Obstacle("Jump",
                "Edge",
                defaultBounds3,
                new Area(new Tile(3019, 3335,3), new Tile(3027, 3332, 3)))); //3

     /*
     Area[] area = {
    new Area(3039, 3341, 3034, 3338),f
    new Area(3034, 3348, 3041, 3341, 3),f
    new Area(3043, 3340, 3052, 3350, 3),f
    new Area(3047, 3359, 3051, 3355, 3),f
    new Area(3044, 3368, 3049, 3360, 3),f
    new Area(3033, 3365, 3041, 3360, 3),f
    new Area(3025, 3356, 3030, 3351, 3),f
    new Area(3008, 3359, 3022, 3352, 3),f
    new Area(3016, 3343, 3022, 3350, 3),f
    new Area(3010, 3343, 3015, 3349, 3),f
    new Area(3008, 3342, 3013, 3335, 3),f
    new Area(3011, 3334, 3018, 3331, 3),f
    new Area(3019, 3335, 3027, 3332, 3)
};
         */
    }

    @Override
    public boolean nextObstacle() {
        if (iterator == trackList.size()-1) {
            reset();
            return false;
        }
        iterator += 1;
        return true;
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
    public void reset() {
        iterator = 0;
    }

    @Override
    public String getName() {
        return courseName;
    }
}
