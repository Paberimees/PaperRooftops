package zillaagility.tasks;

import org.powerbot.script.rt4.ClientContext;
import zillaagility.ZillaAgility;

public class CheckHealth extends Task<ClientContext> {

    public CheckHealth(ClientContext ctx, ZillaAgility main) {
        super(ctx, main);
    }

    @Override
    public boolean activate() {
        return ctx.combat.health() < 8;
    }

    @Override
    public void execute() {
        System.out.println("[TASK] : CheckHealth");
        System.out.println("[LOG] : Health is low! Logging out and stopping script...");
        ctx.game.logout();
        ctx.controller.stop();
    }
}
