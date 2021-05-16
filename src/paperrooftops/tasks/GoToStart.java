package paperrooftops.tasks;

import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import paperrooftops.PaperRooftops;

import java.util.concurrent.Callable;

public class GoToStart extends Task<ClientContext> {

    public GoToStart(ClientContext ctx, PaperRooftops main) {
        super(ctx, main);
    }

    @Override
    public boolean activate() {
        return ctx.players.local().animation() == -1
                && !ctx.players.local().inMotion()
                && main.course.getCurrentObstacle(ctx.players.local().tile()) == null
                && ctx.players.local().tile().floor() == 0;
    }

    @Override
    public void execute() {
        System.out.println("[TASK] : GoToStart");
        Tile playerTile = ctx.players.local().tile();

        //Check in case the player is still mid air etc. if this function fires. Could be added as task conditions, but would need to add it to all below-tasks aswell.
        if (playerTile.x() == -1 || playerTile.y() == -1 || playerTile.floor() == -1) {
            System.out.println("[ERROR] : Player tile coordinates included a -1! Trying again...");
            return;
        }

        //Walk to start, web or not based on distance to start.
        Area startingArea = main.course.getStartingObstacle().getStartArea();
        int distanceToDestination = ctx.movement.distance(startingArea.getCentralTile(), ctx.players.local().tile());
        if (distanceToDestination > 25 || distanceToDestination == -1) { //Arbitrary 25
            //Webwalker
            ctx.movement.moveTo(startingArea.getRandomTile(), false, false);
        } else {
            //Not webwalker
            ctx.movement.step(startingArea.getRandomTile());
            Condition.sleep(2000);
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return !ctx.players.local().inMotion();
                }
            }, 250, 120);
        }

        //Check for logging to console.
        if (!startingArea.containsOrIntersects(ctx.players.local().tile())) {
            System.out.println("[ERROR] : Walking to starting area failed! Trying again...");
            return;
        }
    }

}
