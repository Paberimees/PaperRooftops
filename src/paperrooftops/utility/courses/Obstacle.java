package paperrooftops.utility.courses;

import org.powerbot.script.Area;

import java.util.Arrays;

public class Obstacle {

    private String action;
    private String name;
    private int[] bounds;
    private Area startArea;
    private int id;

    public Obstacle(String action, String name, int[] bounds, Area startArea, int id) {
        this.action = action;
        this.name = name;
        this.bounds = bounds;
        this.startArea = startArea;
        this.id = id;
    }
    public Obstacle(String action, String name, int[] bounds, Area startArea) {
        this.action = action;
        this.name = name;
        this.bounds = bounds;
        this.startArea = startArea;
        this.id = -1;
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

    public int getId() {return id;}

    public String toString() {
        return action + " " + name + " | bounds:" + Arrays.toString(bounds) + " | startArea:" + startArea;
    }

}
