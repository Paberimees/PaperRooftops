package paperrooftops.tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import paperrooftops.PaperRooftops;
import paperrooftops.utility.GV;

public class CheckLevel extends Task<ClientContext> {

    public CheckLevel(ClientContext ctx, PaperRooftops main) {
        super(ctx, main);
    }

    @Override
    public boolean activate() {
        return GV.MAX_LEVEL <= ctx.skills.level(Constants.SKILLS_AGILITY);
    }

    @Override
    public void execute() {
        System.out.println("[TASK] : CheckLevel");

        System.out.println("[LOG] : Current level is equal to or lower than maximum level, logging out...");
        ctx.game.logout();
        if (!Condition.wait(()->!ctx.game.loggedIn(), 500, 5)) {
            return;
        }
        ctx.controller.stop();
    }
}
