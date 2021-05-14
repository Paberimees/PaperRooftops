package zillaagility.utility.courses;

import org.powerbot.script.Locatable;
import org.powerbot.script.Tile;

import java.time.LocalTime;
import java.util.List;

public abstract class Course {

    public abstract boolean nextObstacle();

    public abstract Obstacle getCurrentObstacle(Locatable x);

    public abstract Obstacle getStartingObstacle();

    public abstract void reset();

    public abstract int getObstacleIndex(Obstacle obstacle);

    public abstract String getName();

    /*
    public String courseName;
    public int levelRequirement;
    public int iterator;
    public List<Obstacle> track;

    public Obstacle nextObstacle() {
        iterator += 1;
        if (iterator == track.size()-1) {
            return null;
        }
        return track.get(iterator);
    }

    public void reset() {
        this.iterator = -1;
    }
     */

}
