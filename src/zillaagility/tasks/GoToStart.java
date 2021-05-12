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
                && main.course.getCurrentObstacle(ctx.players.local().tile()) == null;
    }

    @Override
    public void execute() {
        System.out.println("[TASK] : GoToStart");
        //todo maybe not nearest tile? maybe random tile in area?
        Tile playerTile = ctx.players.local().tile();
        if (playerTile.x() == -1 || playerTile.y() == -1 || playerTile.floor() == -1) {
            System.out.println("[ERROR] : Player tile coordinates included a -1! Trying again...");
            return;
        }
        Area startingArea = main.course.getStartingObstacle().getStartArea();
        ctx.movement.moveTo(startingArea.getRandomTile(), false, false);
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return startingArea.containsOrIntersects(ctx.players.local().tile());
            }
        }, 1000, 60);

        if (!startingArea.containsOrIntersects(ctx.players.local().tile())) {
            System.out.println("[ERROR] : Walking to starting area failed! Trying again...");
            return;
        }
    }

}
