package zillaagility.utility.courses;

import org.powerbot.script.Area;
import org.powerbot.script.Locatable;
import org.powerbot.script.Tile;

import java.util.ArrayList;
import java.util.List;

public class CanifisRooftop extends Course {

    private String courseName;
    private int levelRequirement;
    private int iterator;
    private List<Obstacle> trackList = new ArrayList<>();

    //todo i saw misclick at pole vault
    //todo absolutely broken course. fails to click on most marks
    //todo misclicks on lots of obstacles a lot
    public CanifisRooftop() {
        courseName = "Canifis rooftop";
        levelRequirement = 40;
        iterator = 0;
        //Adding all the course obstacles one by one.
        //Every obstacle seems to change places when going from desktop -> mobile.
        //Anyways, the misclicks seems to be going below, so maybe raise up bounding models and see what happens?
        //todo fix misclicks
        //todo enlarge starting area!!!

        int[] defaultBounds = {-12, 12, -128, -112, -12, 12};
        int[] defaultBoundsPoleVault = {-12, 12, -152, -136, -12, 12};
        int[] defaultBounds3 = {-12, 12, -96, -80, -12, 12};

        trackList.add(new Obstacle("Climb",
                "Tall tree",
                new int[]{176, 196, -64, -24, -32, -8},
                new Area(new Tile(3504, 3484, 0),
                        new Tile(3512, 3484, 0),
                        new Tile(3512, 3486, 0),
                        new Tile(3510, 3486, 0),
                        new Tile(3510, 3487, 0),
                        new Tile(3509, 3487, 0),
                        new Tile(3509, 3488, 0),
                        new Tile(3508, 3488, 0),
                        new Tile(3508, 3491, 0),
                        new Tile(3504, 3491, 0)))); //0
        trackList.add(new Obstacle("Jump",
                "Gap",
                defaultBounds, //{-8, 8, -64, -56, -8, 8},
                new Area(new Tile(3503, 3487,2), new Tile(3512, 3499,2)), //2
                14844));
        trackList.add(new Obstacle("Jump",
                "Gap",
                defaultBounds,
                new Area(new Tile(3505, 3508,2), new Tile(3494, 3502,2)), //2
                14845));
        trackList.add(new Obstacle("Jump",
                "Gap",
                defaultBounds,
                new Area(new Tile(3493, 3506,2), new Tile(3483, 3497,2)), //2
                14848));
        trackList.add(new Obstacle("Jump",
                "Gap",
                defaultBounds3,
                new Area(new Tile(3481, 3501,3), new Tile(3473, 3490,3)), //3
                14846));
        trackList.add(new Obstacle("Vault",
                "Pole-vault",
                defaultBoundsPoleVault,
                new Area(new Tile(3476, 3488,2), new Tile(3485, 3480,2)))); //2
        trackList.add(new Obstacle("Jump",
                "Gap",
                defaultBounds3,
                new Area(new Tile(3487, 3480,3), new Tile(3505, 3467,3)), //3
                14847));
        trackList.add(new Obstacle("Jump",
                "Gap",
                defaultBounds,
                new Area(new Tile(3506, 3473,2), new Tile(3517, 3484,2)), //2
                14897));

     /*
     new Tile(3504, 3484, 0),
        new Tile(3512, 3484, 0),
        new Tile(3512, 3486, 0),
        new Tile(3510, 3486, 0),
        new Tile(3510, 3487, 0),
        new Tile(3509, 3487, 0),
        new Tile(3509, 3488, 0),
        new Tile(3508, 3488, 0),
        new Tile(3508, 3491, 0),
        new Tile(3504, 3491, 0)
      */

        /*
        Area[] area = {
    new Area(3508, 3490, 3504, 3485),f
    new Area(3503, 3490, 3512, 3499, 3),f
    new Area(3505, 3508, 3494, 3502, 3),f
    new Area(3493, 3506, 3483, 3497, 3),f
    new Area(3481, 3501, 3473, 3490, 3),f
    new Area(3476, 3488, 3485, 3480, 3),f
    new Area(3487, 3480, 3505, 3467, 3),f
    new Area(3506, 3473, 3517, 3484, 3)f
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

    @Override
    public int getObstacleIndex(Obstacle obstacle) {
        return trackList.indexOf(obstacle);
    }
}
