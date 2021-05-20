package paperrooftops.utility.courses;

import org.powerbot.script.Area;
import org.powerbot.script.Locatable;
import org.powerbot.script.Tile;

import java.util.ArrayList;
import java.util.List;

public class CanifisRooftop extends Course {

    private String courseName;
    private int levelRequirement;
    private List<Obstacle> trackList = new ArrayList<>();

    //todo use debug tool to fix bounds
    public CanifisRooftop() {
        courseName = "Canifis rooftop";
        levelRequirement = 40;

        //Adding all the course obstacles one by one.
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
        //FOR TESTING PURPOSES FOR NOW.
        //TODO DESKTOP - MAKE THIS BETTER (CONSTRUCTOR)
        trackList.get(0).setDesktopTileHeightDifference(-17);
        trackList.get(1).setDesktopTileHeightDifference(-103);
        trackList.get(2).setDesktopTileHeightDifference(-136);
        trackList.get(3).setDesktopTileHeightDifference(-136);
        trackList.get(4).setDesktopTileHeightDifference(0);
        trackList.get(5).setDesktopTileHeightDifference(-151);
        trackList.get(6).setDesktopTileHeightDifference(-7);
        trackList.get(7).setDesktopTileHeightDifference(-97);

         */
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
