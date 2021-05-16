package paperrooftops.tasks;

import org.powerbot.script.rt4.ClientContext;
import paperrooftops.PaperRooftops;

public class CloseMenu extends Task<ClientContext> {

    public CloseMenu(ClientContext ctx, PaperRooftops main) {
        super(ctx, main);
    }

    @Override
    public boolean activate() {
        return ctx.menu.opened()
                && ctx.players.local().animation() == -1
                && !ctx.players.local().inMotion();
    }

    @Override
    public void execute() {
        System.out.println("[TASK] : CloseMenu");
        if (ctx.menu.containsAction("Cancel")) {
            System.out.println("Menu contains action Cancel, clicking it...");
            ctx.menu.click((menuCommand)->menuCommand.action.equals("Cancel"));
        }
    }
}
