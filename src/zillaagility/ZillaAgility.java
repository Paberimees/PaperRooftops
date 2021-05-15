package zillaagility;

import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.GroundItem;
import zillaagility.GUI.DebugTileHeightGUI;
import zillaagility.tasks.*;
import zillaagility.utility.GC;
import zillaagility.utility.courses.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Script.Manifest(name = "ZillaAgility", description = "Does some jumpy and runny stuff", version = "0.1")
public class ZillaAgility extends PollingScript<ClientContext> implements PaintListener {

    private List<Task> taskList = new ArrayList<>();
    private boolean startScript = true;
    private boolean debugMode = false;
    //public Course course = new DraynorRooftop();
    //public Course course = new AlKharidRooftop();
    //public Course course = new VarrockRooftop();
    //public Course course = new CanifisRooftop();
    public Course course = new FaladorRooftop();

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

        //new ScriptOptionsGUI(ctx, this);


        if (!debugMode) {
            taskList.add(new CheckHealth(ctx, this));
            taskList.add(new CloseMenu(ctx, this));
            taskList.add(new TurnOnRun(ctx, this));
            taskList.add(new PickUpMarkOfGrace(ctx, this));
            taskList.add(new InteractObstacle(ctx, this));
            taskList.add(new GoToStart(ctx, this));
        } else { //debug mode
            new DebugTileHeightGUI(this);
            taskList.add(new DebugTask(ctx, this));
        }



    }

    @Override
    public void poll() {
        System.out.println("[LOG] | Total failed clicks: " + GC.TOTAL_FAILED_CLICKS + " | Obstacles failed in order: " + GC.FAILED_OBSTACLES);
        System.out.println("[LOG] | Total failed mark clicks: " + GC.TOTAL_FAILED_MARK_CLICKS);
        if (startScript) {
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
        }
    }
}
