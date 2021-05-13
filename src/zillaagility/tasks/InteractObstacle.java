package zillaagility.tasks;

import jdk.swing.interop.SwingInterOpUtils;
import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import zillaagility.ZillaAgility;
import zillaagility.utility.courses.Course;
import zillaagility.utility.courses.Obstacle;

import java.util.concurrent.Callable;

public class InteractObstacle extends Task<ClientContext> {

    public InteractObstacle(ClientContext ctx, ZillaAgility main) {
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

        //todo check if menu is open just in case? could brick whole code if menu open when it's not supposed to be

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

        //GameObject currentObstacleObject = ctx.objects.toStream().name(currentObstacle.getName()).nearest().first();
        /*GameObject currentObstacleObject = ctx.objects.toStream().filter((obj)->obj.name().equals(currentObstacle.getName())
                && ctx.movement.reachable(new Tile(
                        obj.tile().x()+((obj.tile().x()-playerTile.x())/(obj.tile().x()-playerTile.x())),
                obj.tile().y()+((obj.tile().y()-playerTile.y())/(obj.tile().y()-playerTile.y())))
                , playerTile))
                .nearest().first();

         */

        //GameObject currentObstacleObject = ctx.objects.toStream().filter(obj->obj.name().equals("Rough wall") && ctx.movement.reachable(obj.tile(), playerTile)).nearest().first();

        if (!currentObstacleObject.valid()) {
            System.out.println("[ERROR] : Current obstacle was not valid! Trying again.../Skipping...");
            main.course.nextObstacle();
            return;
        }

        System.out.println("[LOG] : GameObject: " + currentObstacleObject.name() + " " + currentObstacleObject.id());

        //todo sometimes misclicks, because currentObstacle is too far away? Unsure why; mostly happened on varrock jumpgap house. Add distance checking just in case?
        //todo this might need webwwalker
        currentObstacleObject.bounds(currentObstacle.getBounds());
        if (!currentObstacleObject.inViewport()) {
            System.out.println("[LOG] : Current obstacle was not in viewport. Moving and returning...");
            ctx.movement.step(currentObstacleObject);
            ctx.camera.turnTo(currentObstacleObject);
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return !ctx.players.local().inMotion();
                }
            }, 1000, 5);
            return;
        }

        currentObstacleObject.interact(currentObstacle.getAction(), currentObstacle.getName());

        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.players.local().animation() != -1;
            }
        }, 500, 10);

        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return !ctx.players.local().inMotion()
                        && ctx.players.local().animation() == -1;
            }
        }, 1000, 10);

        //Because sometimes it fails and kills webwalker otherwise, gets player coords -1-1-1
        //Condition.sleep(1000); //todo replace this sleep with tile -1 -1 -1 check???

        if (currentObstacle.getStartArea().containsOrIntersects(ctx.players.local().tile())) {
            System.out.println("[ERROR] : Something is fucked and bricked the script. Not sure what. Trying to webwalk to obstacle.");
            ctx.movement.step(currentObstacle.getStartArea().getRandomTile());
            //webwalker can bugger off for now, using random tile as a reset
            //ctx.movement.moveTo(currentObstacleObject, false, false);
            //camera turning didnt work, but fuck it.
            //ctx.camera.angleTo((int)(Math.random()*(360)));
            //ctx.camera.turnTo(currentObstacleObject);
        }

        main.course.nextObstacle();
    }
}
