package paperrooftops.tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import paperrooftops.PaperRooftops;
import paperrooftops.utility.GC;
import paperrooftops.utility.courses.Obstacle;

import java.util.concurrent.Callable;

public class InteractObstacle extends Task<ClientContext> {

    public InteractObstacle(ClientContext ctx, PaperRooftops main) {
        super(ctx, main);
    }

    @Override
    public boolean activate() {
        return ctx.players.local().animation() == -1
                && !ctx.players.local().inMotion()
                && main.course.getCurrentObstacle(ctx.players.local().tile()) != null;
    }

    @Override
    public void execute() {
        System.out.println("[TASK] : InteractObstacle");

        Obstacle currentObstacle = main.course.getCurrentObstacle(ctx.players.local().tile());
        System.out.println("[OBSTACLE] : " + currentObstacle.getName());

        Tile playerTile = ctx.players.local().tile();
        GameObject currentObstacleObject = ctx.objects.toStream()
                .filter((obj)-> {
                    if (currentObstacle.getId() == -1) {
                        return obj.name().equals(currentObstacle.getName());
                    }
                    return obj.name().equals(currentObstacle.getName()) && obj.id() == currentObstacle.getId();
                }).nearest().first();

        main.currentGameObject = currentObstacleObject;

        if (!currentObstacleObject.valid()) {
            System.out.println("[ERROR] : Current obstacle was not valid! Trying again.../Skipping...");
            return;
        }

        //To try and make it autocorrect itself for longer obstacles.
        int distanceToObject = ctx.movement.distance(currentObstacleObject.tile(), ctx.players.local().tile());
        if (distanceToObject > 15) {//arbitrary 15 || distanceToObject == -1 ||  bugs it out, cant have it.
            System.out.println("[ERROR] : Obstacle too far away, moving to try and correct it!");
            ctx.movement.step(currentObstacleObject.tile());
            return;
        }
        System.out.println("[LOG] : GameObject: " + currentObstacleObject.name() + " " + currentObstacleObject.id());

        //todo this might need webwwalker for some cases, mostly redundant in most cases
        //todo check if this improves desktop support or no (first obstacle bounds)
        if (main.isMobile || main.course.getStartingObstacle() == currentObstacle) { //starting obstacles are walls etc that are hard to click and might improve desktop support
            currentObstacleObject.bounds(currentObstacle.getBounds());
        } else {
            //use desktop bounds, but much smaller.
            currentObstacleObject.bounds(new int[]{-4, 4, -36, -28, -4, 4});
        }
        if (!currentObstacleObject.inViewport()) {
            System.out.println("[LOG] : Current obstacle was not in viewport. Moving and returning...");
            ctx.movement.step(currentObstacleObject);
            ctx.camera.turnTo(currentObstacleObject);
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return !ctx.players.local().inMotion();
                }
            }, 250, 20);
            return;
        }

        //todo check if this improves desktop support or not.
        if (main.isMobile) {
            currentObstacleObject.interact(currentObstacle.getAction(), currentObstacle.getName());
        } else { //possibly to improve desktop clicks.
            currentObstacleObject.click(currentObstacle.getAction(), currentObstacle.getName());
        }

        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.players.local().animation() != -1;
            }
        }, 250, 20);

        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return !ctx.players.local().inMotion()
                        && ctx.players.local().animation() == -1;
            }
        }, 250, 40);

        //Because sometimes it fails and kills webwalker otherwise, gets player coords -1-1-1
        //Condition.sleep(1000); //todo replace this sleep with tile -1 -1 -1 check???

        if (currentObstacle.getStartArea().containsOrIntersects(ctx.players.local().tile())) {
            System.out.println("[ERROR] : Obstacle failed! Adding +1...");
            GC.FAILED_ATTEMPTS += 1;
            GC.TOTAL_FAILED_CLICKS += 1;
            GC.FAILED_OBSTACLES.add("[" + currentObstacle.getName() + " | " + main.course.getObstacleIndex(currentObstacle) + "]");
            if (GC.FAILED_ATTEMPTS >= 3) {
                System.out.println("[ERROR] : Something is fucked and bricked the script. Not sure what. Trying to webwalk to obstacle.");
                ctx.movement.step(currentObstacle.getStartArea().getRandomTile());
            }
        } else {
            GC.FAILED_ATTEMPTS = 0;
        }
    }
}