package zillaagility.tasks;

import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GroundItem;
import zillaagility.ZillaAgility;

import java.util.concurrent.Callable;

public class PickUpMarkOfGrace extends Task<ClientContext> {

    public PickUpMarkOfGrace(ClientContext ctx, ZillaAgility main) {
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
        main.currentMarkOfGrace = markOfGrace;
        final long markOfGraceCount = ctx.inventory.toStream().name("Mark of grace").count(true);
        if (!markOfGrace.inViewport()) {
            System.out.println("[LOG] : Mark of grace wasn't in viewport. Moving and trying again...");
            ctx.movement.step(markOfGrace);
            ctx.camera.turnTo(markOfGrace);
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return !ctx.players.local().inMotion();
                }
            }, 1000, 10);
            return;
        }
        //New bounds due to misclicking bug.
        if (markOfGrace.tile().floor() == 2) {
            markOfGrace.bounds(new int[]{-4, 4, -144, -140, -4, 4});
        } else {
            markOfGrace.bounds(new int[]{-4, 4, -4, 0, -4, 4});
        }
        markOfGrace.interact("Take", "Mark of grace");
        //markOfGrace.click(true);
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return markOfGraceCount < ctx.inventory.toStream().name("Mark of grace").count(true);
            }
        }, 1000, 10);
    }

}
