package zillaagility.utility.courses;

import org.powerbot.script.Area;

import java.util.Arrays;

public class Obstacle {

    private String action;
    private String name;
    private int[] bounds;
    private Area startArea;

    public Obstacle(String action, String name, int[] bounds, Area startArea) {
        this.action = action;
        this.name = name;
        this.bounds = bounds;
        this.startArea = startArea;
    }

    public String getAction() {
        return action;
    }

    public String getName() {
        return name;
    }

    public int[] getBounds() {
        return bounds;
    }

    public Area getStartArea() {return startArea;}

    public String toString() {
        return action + " " + name + " | bounds:" + Arrays.toString(bounds) + " | startArea:" + startArea;
    }

}
