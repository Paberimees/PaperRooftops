package paperrooftops.tasks;

import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GroundItem;
import paperrooftops.PaperRooftops;
import paperrooftops.utility.GV;

import java.util.concurrent.Callable;

public class PickUpMarkOfGrace extends Task<ClientContext> {

    public PickUpMarkOfGrace(ClientContext ctx, PaperRooftops main) {
        super(ctx, main);
    }

    @Override
    public boolean activate() {
        if (ctx.groundItems.toStream().name("Mark of grace").isEmpty()) {
            return false;
        }
        if (main.course.getCurrentObstacle(ctx.players.local().tile()) == null) {
            return false;
        }
        if (!(ctx.players.local().animation() == -1 && !ctx.players.local().inMotion())) {
            return false;
        }
        Area playerArea = main.course.getCurrentObstacle(ctx.players.local().tile()).getStartArea();
        if (!(playerArea.containsOrIntersects(ctx.players.local().tile())
                && playerArea.containsOrIntersects(ctx.groundItems.toStream().name("Mark of grace").nearest().first().tile()))) {
            return false;
        }
        return true;
    }

    @Override
    public void execute() {
        System.out.println("[TASK] : PickUpMarkOfGrace");
        GroundItem markOfGrace = ctx.groundItems.toStream().name("Mark of grace").nearest().first();
        final long markOfGraceCount = ctx.inventory.toStream().name("Mark of grace").count(true);

        //Debug.
        main.currentMarkOfGrace = markOfGrace;

        //If the mark of grace is not in viewport, move and turn to it. Wait until player is not moving.
        if (!markOfGrace.inViewport()) {
            System.out.println("[LOG] : Mark of grace wasn't in viewport. Moving and trying again...");
            ctx.movement.step(markOfGrace);
            ctx.camera.turnTo(markOfGrace);
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return !ctx.players.local().inMotion();
                }
            }, 250, 20);
            return;
        }

        //todo Investigate Game.java for possible faster calculation of y. Currently the y diff is calculated by hand and preset.
        //Sets custom bounds for marks of grace on mobile depending on the floor the mark is on. Works /decently/ enough.
        if (main.isMobile) {
            if (markOfGrace.tile().floor() == 2) {
                markOfGrace.bounds(new int[]{-4, 4, -144, -140, -4, 4});
            } else {
                markOfGrace.bounds(new int[]{-4, 4, -4, 0, -4, 4});
            }
        }

        //Clicking is more effective on desktop
        if (main.isMobile) {
            markOfGrace.interact("Take", "Mark of grace");
        } else {
            markOfGrace.click("Take", "Mark of grace");
        }

        //Waits for the mark of grace to appear in the inventory.
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return markOfGraceCount < ctx.inventory.toStream().name("Mark of grace").count(true);
            }
        }, 250, 20);

        //Checks if clicking the mark of grace failed or not.
        if (markOfGraceCount == ctx.inventory.toStream().name("Mark of grace").count(true)) {
            GV.TOTAL_FAILED_MARK_CLICKS += 1;
            GV.CONCURRENT_FAILED_MARK_CLICKS += 1;
        } else {
            GV.CONCURRENT_FAILED_MARK_CLICKS = 0;
        }
    }

}
