package paperrooftops.utility.courses;

import org.powerbot.script.Locatable;

import java.util.ArrayList;
import java.util.List;

public abstract class Course {

    String courseName;
    int levelRequirement;
    List<Obstacle> trackList = new ArrayList<>();

    public Obstacle getCurrentObstacle(Locatable x) {
        if (trackList.stream().anyMatch((obs) -> obs.getStartArea().containsOrIntersects(x))) {
            return trackList.stream().filter((obs) -> obs.getStartArea().containsOrIntersects(x)).findFirst().get();
        }
        return null;
    }

    public Obstacle getStartingObstacle() {
        return trackList.get(0);
    }

    public String getName() {
        return courseName;
    }

    public int getObstacleIndex(Obstacle obstacle) {
        return trackList.indexOf(obstacle);
    }

}
