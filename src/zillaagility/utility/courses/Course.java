package zillaagility.utility.courses;

import org.powerbot.script.Locatable;

public abstract class Course {

    public abstract Obstacle getCurrentObstacle(Locatable x);

    public abstract Obstacle getStartingObstacle();

    public abstract int getObstacleIndex(Obstacle obstacle);

    public abstract String getName();

}
