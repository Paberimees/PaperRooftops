package zillaagility.utility.courses;

import org.powerbot.script.Area;
import org.powerbot.script.Locatable;
import org.powerbot.script.Tile;

import java.util.ArrayList;
import java.util.List;

public class SeersRooftop extends Course {

    private String courseName;
    private int levelRequirement;
    private List<Obstacle> trackList = new ArrayList<>();

    //todo webwalker is FUCKED with seers. Seers village needs a special case gotostart task with a predefined path. A seperate gotostart task would also allow implementation of teleportation
    //todo implement teleportation option
    public SeersRooftop() {
        courseName = "Seers rooftop";
        levelRequirement = 60;
        //Adding all the course obstacles one by one.
        //DEFAULT = {-32, 32, -64, 0, -32, 32}
        int[] debug_0 = {-36, -4, -108, -64, 44, 48}; //wall
        int[] debug_1 = {-32, 32, -64, 0, -32, 32}; //gap
        int[] debug_2 = {-32, 32, -10, 54, -32, 32}; //tightrope
        int[] debug_3 = {-32, 32, -64, 0, -32, 32}; //gap
        int[] debug_4 = {-32, 32, -64, 0, -32, 32}; //gap
        int[] debug_5 = {-32, 32, -64, 0, -32, 32}; //edge

        trackList.add(new Obstacle("Climb-up",
                "Wall",
                debug_0,
                new Area(new Tile(2728, 3489,0), new Tile(2730, 3485,0))));
        trackList.add(new Obstacle("Jump",
                "Gap",
                debug_1,
                new Area(new Tile(2719, 3498,3), new Tile(2731, 3489, 3))));
        trackList.add(new Obstacle("Cross",
                "Tightrope",
                debug_2,
                new Area(new Tile(2703, 3499,2),new Tile(2715, 3486, 2))));
        trackList.add(new Obstacle("Jump",
                "Gap",
                debug_3,
                new Area(new Tile(2708, 3483,2), new Tile(2717, 3475, 2)),
                14929));
        trackList.add(new Obstacle("Jump",
                "Gap",
                debug_4,
                new Area(new Tile(2698, 3478,3), new Tile(2718, 3468, 3)),
                14930));
        trackList.add(new Obstacle("Jump",
                "Edge",
                debug_5,
                new Area(new Tile(2689, 3467,2), new Tile(2704, 3458, 2))));

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
