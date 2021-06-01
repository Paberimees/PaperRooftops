package paperrooftops.tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Game;
import org.powerbot.script.rt4.Item;
import paperrooftops.PaperRooftops;
import paperrooftops.utility.GV;

import java.util.Arrays;

public class CheckHealth extends Task<ClientContext> {

    public CheckHealth(ClientContext ctx, PaperRooftops main) {
        super(ctx, main);
    }

    @Override
    public boolean activate() {
        return ctx.combat.health() < GV.HEALTH_MINIMUM;
    }

    @Override
    public void execute() {
        System.out.println("[TASK] : CheckHealth");

        //Find food from inventory
        Item foodItem = ctx.inventory.toStream().filter(item -> Arrays.stream(item.actions()).anyMatch(action->action.contains("Eat"))).first(); //|| action.contains("Drink") drinks all ur pots otherwise

        //Check if found food, otherwise log out
        if (!foodItem.valid()) {
            System.out.println("[LOG] : Health is low! Logging out and stopping script...");
            ctx.game.logout();
            if (!Condition.wait(()->!ctx.game.loggedIn(), 500, 5)) {
                return;
            }
            ctx.controller.stop();
            return;
        }

        //Eat the food, wait for hp to rise.
        final int oldHealth = ctx.combat.health();
        ctx.game.tab(Game.Tab.INVENTORY);
        foodItem.interact("Eat");
        if (Condition.wait(()->ctx.combat.health()>oldHealth,250,10)) {
            Condition.sleep(500);
        }
    }
}
