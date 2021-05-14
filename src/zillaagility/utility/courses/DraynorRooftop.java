package zillaagility.utility.courses;

import org.powerbot.script.Area;
import org.powerbot.script.Locatable;
import org.powerbot.script.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DraynorRooftop extends Course {

    private String courseName;
    private int levelRequirement;
    private int iterator;
    private List<Obstacle> trackList = new ArrayList<>();

    public DraynorRooftop() {
        courseName = "Draynor rooftop";
        levelRequirement = 10;
        iterator = 0;
        //Adding all the course obstacles one by one.
        trackList.add(new Obstacle("Climb",
                "Rough wall",
                new int[]{-40, -36, -180, -140, -32, 8},
                new Area(new Tile(3103, 3281, 0), new Tile(3106, 3274, 0))));
        trackList.add(new Obstacle("Cross",
                "Tightrope",
                new int[]{-8, 32, -32, -32, -32, 0},
                new Area(new Tile(3097, 3281,3), new Tile(3102, 3277,3))));
        trackList.add(new Obstacle("Cross",
                "Tightrope",
                new int[]{-8, 8, -40, -24, -8, 8},
                new Area(new Tile(3086, 3278,3), new Tile(3093, 3272,3))));
        trackList.add(new Obstacle("Balance",
                "Narrow wall",
                new int[]{-16, 16, -48, 16, -16, 16},
                new Area(new Tile(3089, 3268, 3), new Tile(3094, 3265,3))));
        trackList.add(new Obstacle("Jump-up",
                "Wall",
                new int[]{-16, 16, -48, -16, -16, 16},
                new Area(new Tile(3082, 3261,3), new Tile(3088, 3256,3))));
        trackList.add(new Obstacle("Jump",
                "Gap",
                new int[]{-16, 16, -48, -16, -16, 16},
                new Area(new Tile(3087, 3254, 3), new Tile(3095, 3255, 3))));
        trackList.add(new Obstacle("Climb-down",
                "Crate",
                new int[]{-16, 16, -48, -16, -16, 16},
                new Area(new Tile(3096, 3256,3), new Tile(3101, 3261,3))));
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

    @Override
    public int getObstacleIndex(Obstacle obstacle) {
        return trackList.indexOf(obstacle);
    }
}
