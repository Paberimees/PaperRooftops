package zillaagility.tasks;

import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import zillaagility.ZillaAgility;

import java.util.concurrent.Callable;

public class GoToStart extends Task<ClientContext> {

    public GoToStart(ClientContext ctx, ZillaAgility main) {
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
        if (playerTile.x() == -1 || playerTile.y() == -1 || playerTile.floor() == -1) {
            System.out.println("[ERROR] : Player tile coordinates included a -1! Trying again...");
            return;
        }
        Area startingArea = main.course.getStartingObstacle().getStartArea();
        int distanceToDestination = ctx.movement.distance(startingArea.getCentralTile(), ctx.players.local().tile());
        if (distanceToDestination > 25 || distanceToDestination == -1) { //todo arbitrary 25, was 23 before; somehow improve upon this, don't want to resort to webwalker
            //todo confirm for certain that webwalker doesn't need a wait condition. doesn't seem like it needs one.
            ctx.movement.moveTo(startingArea.getRandomTile(), false, false);
        } else {
            ctx.movement.step(startingArea.getRandomTile());
            Condition.sleep(2000);
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return !ctx.players.local().inMotion();
                }
            }, 250, 120);
        }
        //todo The wait condition above was 15, worked well for most cases, this probably needs something better though. Webwalker used to bug out right near the start area, just waiting.
        if (!startingArea.containsOrIntersects(ctx.players.local().tile())) {
            System.out.println("[ERROR] : Walking to starting area failed! Trying again...");
            return;
        }
    }

}
