package paperrooftops.utility.courses;

import org.powerbot.script.Area;
import org.powerbot.script.Locatable;
import org.powerbot.script.Tile;

import java.util.ArrayList;
import java.util.List;

public class SeersRooftop extends Course {

    //todo webwalker is FUCKED with seers. Seers village needs a special case gotostart task with a predefined path. A seperate gotostart task would also allow implementation of teleportation
    //todo implement teleportation option
    public SeersRooftop() {
        courseName = "Seers rooftop";
        levelRequirement = 60;

        //Adding all the course obstacles one by one.
        trackList.add(new Obstacle("Climb-up",
                "Wall",
                new int[]{-36, -4, -108, -64, 44, 48},
                new Area(new Tile(2728, 3489,0), new Tile(2730, 3485,0))));
        trackList.add(new Obstacle("Jump",
                "Gap",
                new int[]{-32, 32, -64, 0, -32, 32},
                new Area(new Tile(2719, 3498,3), new Tile(2731, 3489, 3))));
        trackList.add(new Obstacle("Cross",
                "Tightrope",
                new int[]{-32, 32, -10, 54, -32, 32},
                new Area(new Tile(2703, 3499,2),new Tile(2715, 3486, 2))));
        trackList.add(new Obstacle("Jump",
                "Gap",
                new int[]{-32, 32, -64, 0, -32, 32},
                new Area(new Tile(2708, 3483,2), new Tile(2717, 3475, 2)),
                14929));
        trackList.add(new Obstacle("Jump",
                "Gap",
                new int[]{-32, 32, -64, 0, -32, 32},
                new Area(new Tile(2698, 3478,3), new Tile(2718, 3468, 3)),
                14930));
        trackList.add(new Obstacle("Jump",
                "Edge",
                new int[]{-32, 32, -64, 0, -32, 32},
                new Area(new Tile(2689, 3467,2), new Tile(2704, 3458, 2))));

    }
}
