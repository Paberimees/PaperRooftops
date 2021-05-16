package paperrooftops.tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.GroundItem;
import paperrooftops.PaperRooftops;
import paperrooftops.utility.courses.Obstacle;

public class DebugTask extends Task<ClientContext> {

    public DebugTask(ClientContext ctx, PaperRooftops main) {
        super(ctx, main);
    }

    @Override
    public boolean activate() {
        return main.course.getCurrentObstacle(ctx.players.local().tile()) != null;
    }

    @Override
    public void execute() {
        System.out.println("[TASK] : DebugTask");

        Obstacle currentObstacle = main.course.getCurrentObstacle(ctx.players.local().tile());
        System.out.println("[OBSTACLE] : " + currentObstacle.getName());

        GameObject currentObstacleObject = ctx.objects.toStream()
                .filter((obj)-> {
                    if (currentObstacle.getId() == -1) {
                        return obj.name().equals(currentObstacle.getName());
                    }
                    return obj.name().equals(currentObstacle.getName()) && obj.id() == currentObstacle.getId();
                }).nearest().first();

        if (currentObstacleObject.valid()) {
            main.currentGameObject = currentObstacleObject;
        } else {
            main.currentGameObject = null;
        }

        GroundItem markOfGrace = ctx.groundItems.toStream().name("Mark of grace").nearest().first();

        if (markOfGrace.valid()) {
            main.currentMarkOfGrace = markOfGrace;
        } else {
            main.currentMarkOfGrace = null;
        }

        if (!currentObstacleObject.valid()) {
            System.out.println("[ERROR] : Current obstacle was not valid! Trying again.../Skipping...");
            return;
        }

        if (main.isMobile) {
            currentObstacleObject.bounds(currentObstacle.getBounds());
        }

        Condition.sleep(1000);
    }
}
