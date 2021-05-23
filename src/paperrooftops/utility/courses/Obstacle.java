package paperrooftops.utility.courses;

import org.powerbot.script.Area;

import java.util.Arrays;

public class Obstacle {

    private String action;
    private String name;
    private int[] bounds;
    private int[] desktopBounds;
    private Area startArea;
    private int id;
    //private int desktopTileHeightDifference = 0;

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

    public void setDesktopBounds(int[] desktopBounds) {
        this.desktopBounds = desktopBounds;
    }

    public int[] getDesktopBounds() {
        return desktopBounds;
    }

    /*
    //TODO DESKTOP ADD TO CONSTRUCTOR
    public void setDesktopTileHeightDifference(int diff) {
        desktopTileHeightDifference = diff;
    }

    public int getDesktopTileHeightDifference() {
        return desktopTileHeightDifference;
    }

     */

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
