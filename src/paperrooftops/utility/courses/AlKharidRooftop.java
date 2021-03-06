package paperrooftops.utility.courses;

import org.powerbot.script.Area;
import org.powerbot.script.Locatable;
import org.powerbot.script.Tile;

public class AlKharidRooftop extends Course {

    public AlKharidRooftop() {
        courseName = "Al Kharid rooftop";
        levelRequirement = 20;

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
        trackList.add(new Obstacle("Climb", "Roof top beams", new int[]{-16, 16, -32, -28, 44, 52},
                new Area(new Tile(3311, 3180,2), new Tile(3319, 3172, 2))));
        trackList.add(new Obstacle("Cross", "Tightrope", new int[]{-8, 20, -4, 0, -24, 8},
                new Area(new Tile(3311, 3187,3), new Tile(3319, 3179, 3))));
        trackList.add(new Obstacle("Jump", "Gap", new int[]{-16, 16, -48, -16, -16, 16},
                new Area(new Tile(3295, 3196,3), new Tile(3307, 3183, 3))));

        //Temporary (?)
        //Desktop bounds
        int[] debugBounds1 = new int[]{-16,16,-4,0,-16,16};
        int[] debugBounds2 = new int[]{-16,16,-48,-16,-16,16};
        trackList.get(1).setDesktopBounds(debugBounds1);
        trackList.get(2).setDesktopBounds(debugBounds2);
        trackList.get(3).setDesktopBounds(debugBounds1);
        trackList.get(4).setDesktopBounds(debugBounds2);
        trackList.get(5).setDesktopBounds(new int[]{-36,56,-48,-40,56,64}); //roof top beams
        trackList.get(6).setDesktopBounds(debugBounds1);
        trackList.get(7).setDesktopBounds(debugBounds1);
    }
}
