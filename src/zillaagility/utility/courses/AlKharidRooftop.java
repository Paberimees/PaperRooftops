package zillaagility.utility.courses;

import org.powerbot.script.Area;
import org.powerbot.script.Locatable;
import org.powerbot.script.Tile;

import java.util.ArrayList;
import java.util.List;

public class AlKharidRooftop extends Course {

    private String courseName;
    private int levelRequirement;
    private int iterator;
    private List<Obstacle> trackList = new ArrayList<>();

    public AlKharidRooftop() {
        courseName = "Draynor rooftop";
        levelRequirement = 10;
        iterator = 0;
        //Adding all the course obstacles one by one.
        trackList.add(new Obstacle("Climb", "Rough wall", new int[]{-12,16,-172,-132,-60, -56},
                new Area(new Tile(3270, 3195, 0), new Tile(3278, 3199,0))));
        trackList.add(new Obstacle("Cross", "Tightrope", new int[]{-16, 16, -4, 0, -16, 16},
                new Area(new Tile(3270, 3193,3), new Tile(3278, 3180, 3))));
        trackList.add(new Obstacle("Swing-across", "Cable", new int[]{-16, 16, -48, -16, -16, 16},
                new Area(new Tile(3264, 3174,3), new Tile(3273, 3160, 3))));
        trackList.add(new Obstacle("Teeth-grip", "Zip line", new int[]{-16, 16, -48, -16, -16, 16},
                new Area(new Tile(3281, 3177,3), new Tile(3303, 3159, 3))));
        trackList.add(new Obstacle("Swing-across", "Tropical tree", new int[]{-16, 16, -48, -16, -16, 16},
                new Area(new Tile(3312, 3166,1), new Tile(3319, 3159, 1))));
        trackList.add(new Obstacle("Climb", "Roof top beams", new int[]{-16, 16, -48, -44, 44, 52},
                new Area(new Tile(3311, 3180,2), new Tile(3319, 3172, 2))));
        trackList.add(new Obstacle("Cross", "Tightrope", new int[]{-8, 20, -4, 0, -24, 8},
                new Area(new Tile(3311, 3187,3), new Tile(3319, 3179, 3))));
        trackList.add(new Obstacle("Jump", "Gap", new int[]{-16, 16, -48, -16, -16, 16},
                new Area(new Tile(3295, 3196,3), new Tile(3307, 3183, 3))));

        /*
        Area[] area = {
    new Area(3270, 3195, 3278, 3199),
    new Area(3270, 3193, 3278, 3180, 3), f
    new Area(3264, 3174, 3273, 3160, 3),f
    new Area(3281, 3177, 3303, 3159, 3),f
    new Area(3312, 3166, 3319, 3159, 2),f
    new Area(3311, 3180, 3319, 3172, 2),f
    new Area(3311, 3187, 3319, 3179, 3),
    new Area(3295, 3196, 3307, 3183, 3)
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
