package zillaagility;

import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;
import zillaagility.tasks.*;
import zillaagility.utility.courses.AlKharidRooftop;
import zillaagility.utility.courses.Course;
import zillaagility.utility.courses.DraynorRooftop;
import zillaagility.utility.courses.VarrockRooftop;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Script.Manifest(name = "ZillaAgility", description = "Does some jumpy and runny stuff", version = "0.1")
public class ZillaAgility extends PollingScript<ClientContext> {

    private List<Task> taskList = new ArrayList<>();
    //public Course course = new DraynorRooftop();
    //public Course course = new AlKharidRooftop();
    public Course course = new VarrockRooftop();

    //todo make it eat food if it's low, log out if low and no food
    @Override
    public void start() {
        //Disables random events
        ctx.properties.setProperty("randomevents.disable", "true");
        //Adds tasks for the bot to do
        taskList.add(new CloseMenu(ctx, this));
        taskList.add(new TurnOnRun(ctx, this));
        taskList.add(new PickUpMarkOfGrace(ctx, this));
        taskList.add(new InteractObstacle(ctx, this));
        taskList.add(new GoToStart(ctx, this));
    }

    @Override
    public void poll() {
        //System.out.println(course.getCurrentObstacle(ctx.players.local().tile()));
        System.out.println("POLL!!!");
        for (Task t : taskList) {
            if (t.activate()) {
                t.execute();
                break;
            }
        }
    }

}
