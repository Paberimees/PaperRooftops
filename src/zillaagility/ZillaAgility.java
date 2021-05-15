package zillaagility;

import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.GroundItem;
import zillaagility.GUI.DebugTileHeightGUI;
import zillaagility.GUI.ScriptOptionsGUI;
import zillaagility.tasks.*;
import zillaagility.utility.GC;
import zillaagility.utility.courses.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Script.Manifest(name = "ZillaAgility", description = "Does some jumpy and runny stuff", version = "0.1")
public class ZillaAgility extends PollingScript<ClientContext> implements PaintListener {

    //functionality variables
    private List<Task> taskList = new ArrayList<>();
    private boolean startScript = false;
    private boolean debugMode = false;
    public Course course;
    public boolean isMobile;

    //UI mostly for desktop variables
    public int startAgilityXP;
    private final Font helveticaFont = new Font("Helvetica", 0, 12);

    //this is debug
    public GameObject currentGameObject;
    public GroundItem currentMarkOfGrace;
    public int currentMarkOfGraceTileHeight;
    public int currentGameObjectTileHeight;

    //todo make it eat food if it's low, log out if low and no food
    //todo fix course marks
    //todo add autocorrector
    //todo viewport should zoom out if neccessary
    //todo implement zoom check.
    //todo implement level check.
    @Override
    public void start() {
        //Disables random events
        ctx.properties.setProperty("randomevents.disable", "true");
        isMobile = ctx.client().isMobile();
        new ScriptOptionsGUI(ctx, this);

        //UI - mostly desktop
        startAgilityXP = ctx.skills.experience(Constants.SKILLS_AGILITY);
    }

    @Override
    public void poll() {
        if (startScript) {
            System.out.println("[LOG] | Total failed clicks: " + GC.TOTAL_FAILED_CLICKS + " | Obstacles failed in order: " + GC.FAILED_OBSTACLES);
            System.out.println("[LOG] | Total failed mark clicks: " + GC.TOTAL_FAILED_MARK_CLICKS);
            for (Task t : taskList) {
                if (t.activate()) {
                    t.execute();
                    break;
                }
            }
        }
    }

    @Override
    public void repaint(Graphics g) {
        if (debugMode) {
            g.setColor(new Color(0, 255, 0));
            if (currentGameObject != null) {
                currentGameObject.boundingModel().drawWireFrame(g);
            }
            if (currentMarkOfGrace != null) {
                currentMarkOfGrace.boundingModel().drawWireFrame(g);
            } else {
                currentGameObjectTileHeight = 69;
                currentMarkOfGraceTileHeight = 69;
            }
            return;
        }

        //UI for desktop, mostly.
        long currentAgilityXP = ctx.skills.experience(Constants.SKILLS_AGILITY);
        long gainedAgilityXP = currentAgilityXP - startAgilityXP;

        //Window
        g.setColor(new Color(0, 0, 0,255));
        g.drawRect(5,5,250,100);
        g.setColor(new Color(33, 33, 33, 230));
        g.fillRect(5,5,250,100);

        //Info
        g.setFont(helveticaFont);
        g.setColor(new Color(172, 172, 172,255));
        g.drawString("Agility exp. gained: " + gainedAgilityXP, 8, 25);
        g.drawString("Time running: " + formatTime((int)this.getRuntime()/1000), 8, 60);
    }

    private String formatTime(int secs) {
        return String.format("%02d:%02d:%02d", secs / 3600, (secs % 3600) / 60, secs % 60);
    }

    public void setStartScript(boolean b) {
        startScript = b;
    }

    public void setDebugMode(boolean b) { debugMode = b; }

    public void addTask(Task t) {
        taskList.add(t);
    }
}
