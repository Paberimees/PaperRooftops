package zillaagility.utility.courses;

import org.powerbot.script.Area;
import org.powerbot.script.Locatable;
import org.powerbot.script.Tile;

import java.util.ArrayList;
import java.util.List;

public class VarrockRooftop extends Course {

    private String courseName;
    private int levelRequirement;
    private int iterator;
    private List<Obstacle> trackList = new ArrayList<>();

    public VarrockRooftop() {
        courseName = "Varrock rooftop";
        levelRequirement = 30;
        iterator = 0;
        //Adding all the course obstacles one by one, where the first obstacle added is the course start.
        trackList.add(new Obstacle("Climb",
                "Rough wall",
                new int[]{-44, -40, -180, -144, -20, 8},
                new Area(new Tile(3221, 3411,0), new Tile(3223, 3416,0)))); //0
        trackList.add(new Obstacle("Cross",
                "Clothes line",
                new int[]{-16, 16, -48, -16, -16, 16},
                new Area(new Tile(3213, 3420,3), new Tile(3220, 3409, 3)))); //3
        trackList.add(new Obstacle("Leap",
                "Gap",
                new int[]{-16, 16, -48, -16, -16, 16},
                new Area(new Tile(3200, 3420,3), new Tile(3209, 3412, 3)))); //3
        trackList.add(new Obstacle("Balance",
                "Wall",
                new int[]{-16, 16, -48, -16, -16, 16},
                new Area(new Tile(3191, 3417,1), new Tile(3198, 3415, 1)))); //1
        trackList.add(new Obstacle("Leap",
                "Gap",
                new int[]{-16, 16, -48, -16, -16, 16},
                new Area(new Tile(3191, 3407,3), new Tile(3199, 3401, 3)),
                14833)); //3

        trackList.add(new Obstacle("Leap", //Fucked up boundaries, polygon??? Areas would overlap otherwise.
                "Gap",
                new int[]{-16, 16, -48, -16, -16, 16},
                new Area(new Tile(3190, 3381, 3),
                        new Tile(3181, 3381, 3),
                        new Tile(3181, 3399, 3),
                        new Tile(3201, 3399, 3),
                        new Tile(3201, 3404, 3),
                        new Tile(3209, 3404, 3),
                        new Tile(3209, 3394, 3),
                        new Tile(3201, 3394, 3),
                        new Tile(3196, 3389, 3),
                        new Tile(3196, 3387, 3),
                        new Tile(3190, 3387, 3)),
                14834)); //3

        trackList.add(new Obstacle("Leap",
                "Gap",
                new int[]{-16, 16, -48, -16, -16, 16},
                new Area(new Tile(3216, 3405,3), new Tile(3233, 3392, 3)),
                14835)); //3
        trackList.add(new Obstacle("Hurdle",
                "Ledge",
                new int[]{-16, 16, -48, -16, -16, 16},
                new Area(new Tile(3235, 3402,3),new Tile(3241, 3408, 3)))); //3
        trackList.add(new Obstacle("Jump-off",
                "Edge",
                new int[]{-16, 16, -48, -16, -16, 16},
                new Area(new Tile(3235, 3409,3), new Tile(3241, 3417, 3)))); //3

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
