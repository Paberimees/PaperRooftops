package paperrooftops.tasks;

import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GroundItem;
import paperrooftops.PaperRooftops;
import paperrooftops.utility.GC;

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
            }, 250, 20);
            return;
        }
        /*
        //New bounds due to misclicking bug.
        //todo improve upon this. some marks are broken, some are not. probably need to implement markofgrace bounds to obstacle individually

        if (!(ctx.players.local().tile().x() == markOfGrace.tile().x() && ctx.players.local().tile().y() == markOfGrace.tile().y() && ctx.players.local().tile().floor() == markOfGrace.tile().floor())) {
            ctx.movement.step(markOfGrace.tile());
            Condition.sleep(1000);
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return !ctx.players.local().inMotion();
                }
            }, 1000, 10);
        }

        //this markofgrace shit is broken
        if (!(ctx.players.local().tile().x() == markOfGrace.tile().x() && ctx.players.local().tile().y() == markOfGrace.tile().y() && ctx.players.local().tile().floor() == markOfGrace.tile().floor())) {
            System.out.println("[ERROR] : Fucking marks of graces, apparently im not standing right on top it.");
            return;
        }
        int markTileHeight = ctx.game.tileHeight(markOfGrace.boundingModel().x(), markOfGrace.boundingModel().z());
        int playerTileHeight = ctx.game.tileHeight(ctx.players.local().boundingModel().x(), ctx.players.local().boundingModel().z());
        int tileHeightDiff = markTileHeight-playerTileHeight;
        Condition.sleep(2500); //todo IMPROVE THIS? sleeping for a bit, because that tileheight function... it scared me
        markOfGrace.bounds(-4, 4, -4+tileHeightDiff, 0+tileHeightDiff, -4, 4);
*/
        //todo fuck this look at Game.java for possibly faster calculations @api
        if (main.isMobile) {
            if (markOfGrace.tile().floor() == 2) {
                markOfGrace.bounds(new int[]{-4, 4, -144, -140, -4, 4});
            } else {
                markOfGrace.bounds(new int[]{-4, 4, -4, 0, -4, 4});
            }
        }

        /*
        //todo THIS IS FUCKING DOGSHIT. REMOVE THIS.
        if (GC.CONCURRENT_FAILED_MARK_CLICKS >= 3 && GC.MARK_TILEHEIGHT_DIFF == 0) {
            int markTileHeight = ctx.game.tileHeight(markOfGrace.boundingModel().x(), markOfGrace.boundingModel().z());
            int playerTileHeight = ctx.game.tileHeight(ctx.players.local().boundingModel().x(), ctx.players.local().boundingModel().z());
            GC.MARK_TILEHEIGHT_DIFF = markTileHeight-playerTileHeight;
            Condition.sleep(2500); //todo IMPROVE THIS? sleeping for a bit, because that tileheight function... it scared me
            //markOfGrace.bounds(-4, 4, -4+tileHeightDiff, 0+tileHeightDiff, -4, 4);
            return;
        } else {
            markOfGrace.bounds(-4, 4, -4+GC.MARK_TILEHEIGHT_DIFF, 0+GC.MARK_TILEHEIGHT_DIFF, -4, 4);
        }
         */
        if (main.isMobile) {
            markOfGrace.interact("Take", "Mark of grace");
        } else {
            markOfGrace.click("Take", "Mark of grace");
        }
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return markOfGraceCount < ctx.inventory.toStream().name("Mark of grace").count(true);
            }
        }, 250, 20);

        if (markOfGraceCount == ctx.inventory.toStream().name("Mark of grace").count(true)) {
            GC.TOTAL_FAILED_MARK_CLICKS += 1;
            GC.CONCURRENT_FAILED_MARK_CLICKS += 1;
        } else {
            GC.CONCURRENT_FAILED_MARK_CLICKS = 0;
        }
    }

}
