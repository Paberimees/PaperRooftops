package paperrooftops;

import org.powerbot.script.Condition;
import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.GroundItem;
import paperrooftops.GUI.ScriptOptionsGUI;
import paperrooftops.tasks.*;
import paperrooftops.utility.GV;
import paperrooftops.utility.courses.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/*
Notes:
- sout - Why? Monkey log doesn't work for me. Use cmd.exe and "java -Xmx1536M -jar PowBot.jar" to get the log.
 */
@Script.Manifest(name = "PaperRooftops", description = "Paperman on the rooftops", version = "0.1")
public class PaperRooftops extends PollingScript<ClientContext> implements PaintListener {

    //Functionality variables
    private List<Task> taskList = new ArrayList<>();
    private boolean startScript = false;
    private boolean debugMode = false;
    public Course course;
    public boolean isMobile;

    //UI mostly for desktop variables
    public int startAgilityXP;
    public int startAgilityLevel;
    private final Font helveticaFont = new Font("Helvetica", 0, 12);

    //This is debug
    public GameObject currentGameObject;
    public GroundItem currentMarkOfGrace;
    public int currentMarkOfGraceTileHeight;
    public int currentGameObjectTileHeight;
    public boolean drawBoundingModelWireFrames = true;

    //todo FEATURE: Option to eat food when low.
    //todo FEATURE: Automatic viewport zooming out/correction.
    //todo FEATURE: Agility level check for courses.
    //todo FEATURE: Option to choose debug mode from GUI.
    //todo BUGFIX: Course marks are still a little iffy on mobile. Fix could possibly be including y TileHeight offset in Obstacle for calculating MoG bounds?
    //todo BUGFIX: Enable Seers course && it needs a preset path, because Webwalking to starting area takes a massive detour.
    @Override
    public void start() {
        //Disables random events
        ctx.properties.setProperty("randomevents.disable", "true");
        isMobile = ctx.client().isMobile();
        //UI mostly for desktop variables values
        startAgilityXP = ctx.skills.experience(Constants.SKILLS_AGILITY);
        startAgilityLevel = ctx.skills.level(Constants.SKILLS_AGILITY);
        //Opens up the GUI
        new ScriptOptionsGUI(ctx, this);
    }

    @Override
    public void poll() {
        if (startScript) {
            System.out.println("[LOG] | Total failed clicks: " + GV.TOTAL_FAILED_CLICKS + " | Obstacles failed in order: " + GV.FAILED_OBSTACLES);
            System.out.println("[LOG] | Total failed mark clicks: " + GV.TOTAL_FAILED_MARK_CLICKS);
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

        //Debug mode bounds wireframe drawing. If debugMode=true, it doesn't draw the stats window.
        if (debugMode) {
            if (drawBoundingModelWireFrames) {
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
            return;
        }

        //UI mostly for desktop painting
        long currentAgilityXP = ctx.skills.experience(Constants.SKILLS_AGILITY);
        long gainedAgilityXP = currentAgilityXP - startAgilityXP;

        //Info
        g.setFont(helveticaFont);
        g.setColor(new Color(0, 0, 0,255));
        g.drawString("Runtime: " + formatTime((int)this.getRuntime()/1000), 8, 250);
        g.drawString("Exp. gained: " + gainedAgilityXP, 8, 264);
        g.drawString("Exp. till lvl: " + (ctx.skills.experienceAt(ctx.skills.level(Constants.SKILLS_AGILITY)+1)-ctx.skills.experience(Constants.SKILLS_AGILITY)),8,278);
        g.drawString("Levels: " + ctx.skills.level(Constants.SKILLS_AGILITY) + " +" + (ctx.skills.level(Constants.SKILLS_AGILITY)-startAgilityLevel), 8, 292);
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
