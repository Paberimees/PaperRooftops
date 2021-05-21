package paperrooftops.tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.Filter;
import org.powerbot.script.MenuCommand;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import paperrooftops.PaperRooftops;
import paperrooftops.utility.GV;
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

        //Gets the current obstacle.
        Obstacle currentObstacle = main.course.getCurrentObstacle(ctx.players.local().tile());
        System.out.println("[OBSTACLE] : " + currentObstacle.getName());

        //Finds the current obstacle in the game, depending on if the Obstacle() object had an ID attached to it or not.
        Tile playerTile = ctx.players.local().tile();
        GameObject currentObstacleObject = ctx.objects.toStream()
                .filter((obj)-> {
                    if (currentObstacle.getId() == -1) {
                        return obj.name().equals(currentObstacle.getName());
                    }
                    return obj.name().equals(currentObstacle.getName()) && obj.id() == currentObstacle.getId();
                }).nearest().first();

        //Debug painting.
        main.currentGameObject = currentObstacleObject;

        //Check if the obstacle object in game is valid.
        if (!currentObstacleObject.valid()) {
            System.out.println("[ERROR] : Current obstacle was not valid! Trying again.../Skipping...");
            return;
        }

        //Try and correct itself on longer obstacles if it's too far away from the player (Al Kharid big roof; Varrock bigger roof; Etc).
        int distanceToObject = ctx.movement.distance(currentObstacleObject.tile(), ctx.players.local().tile());
        if (distanceToObject > 15) {//arbitrary 15 || distanceToObject == -1 ||  bugs it out, cant have it, even though valid calculations seem to return -1 sometimes.
            System.out.println("[ERROR] : Obstacle too far away, moving to try and correct it!");
            ctx.movement.step(currentObstacleObject.tile());
            return;
        }
        System.out.println("[LOG] : GameObject: " + currentObstacleObject.name() + " " + currentObstacleObject.id());

        //Sets custom bounds for mobile objects.
        if (main.isMobile || main.course.getStartingObstacle() == currentObstacle) { //|| main.course.getStartingObstacle() == currentObstacle //starting obstacles are walls etc that are hard to click and might improve desktop support
            currentObstacleObject.bounds(currentObstacle.getBounds());
        } /*else {
            if (currentObstacle.getDesktopTileHeightDifference() != 0) {
                int[] desktopBounds = currentObstacle.getBounds();
                desktopBounds[2] = desktopBounds[2] - currentObstacle.getDesktopTileHeightDifference();
                desktopBounds[3] = desktopBounds[3] - currentObstacle.getDesktopTileHeightDifference();
                currentObstacleObject.bounds(desktopBounds);
            }
        }*/
        // ^ Else - use desktop bounds. Experimented with smaller bounds, but they stall stay default as is for now, except the first obstacle (Walls to climb up)

        //If the current obstacle is not in viewport, move and turn to it, wait until player is not in motion, return.
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

        //todo Confirm that this actually improves desktop clicks.
        //For some reason, switching from interact to click seemed to improve clicks. Might be placebo, since IIRC they both call click() in the end anyway in the API.
        if (main.isMobile) {
            currentObstacleObject.interact(currentObstacle.getAction(), currentObstacle.getName());
        } else { //possibly to improve desktop clicks.
            //currentObstacleObject.click(currentObstacle.getAction(), currentObstacle.getName());
            System.out.println("Interacting, not clicking! " + currentObstacle.getAction());
            //Condition.sleep(3000);
            //currentObstacleObject.click(true);
            currentObstacleObject.interact(currentObstacle.getAction(), currentObstacle.getName());
        }

        //Wait until the player has interacted with the obstacle
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.players.local().animation() != -1;
            }
        }, 250, 20);

        //Wait until the player has stopped interacting with the obstacle
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return !ctx.players.local().inMotion()
                        && ctx.players.local().animation() == -1;
            }
        }, 250, 40);

        //Check if the player successfully did the obstacle, otherwise after 3 failed attemps move to a random tile on the roof the player is on for autocorrection.
        if (currentObstacle.getStartArea().containsOrIntersects(ctx.players.local().tile())) {
            System.out.println("[ERROR] : Obstacle failed! Adding +1...");
            GV.FAILED_ATTEMPTS += 1;
            GV.TOTAL_FAILED_CLICKS += 1;
            GV.FAILED_OBSTACLES.add("[" + currentObstacle.getName() + " | " + main.course.getObstacleIndex(currentObstacle) + "]");
            if (GV.FAILED_ATTEMPTS >= 3) {
                System.out.println("[ERROR] : Something is fucked and bricked the script. Not sure what. Trying to webwalk to obstacle.");
                ctx.movement.step(currentObstacle.getStartArea().getRandomTile());
            }
        } else {
            GV.FAILED_ATTEMPTS = 0;
        }
    }
}
