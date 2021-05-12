package zillaagility.tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import zillaagility.ZillaAgility;
import zillaagility.utility.GC;

import java.util.concurrent.Callable;

public class TurnOnRun extends Task<ClientContext> {

    public TurnOnRun(ClientContext ctx, ZillaAgility main) {
        super(ctx, main);
    }

    @Override
    public boolean activate() {
        return !ctx.movement.running()
                && ctx.movement.energyLevel() > GC.RUN_ENERGY_MINIMUM;
    }

    @Override
    public void execute() {
        System.out.println("[TASK] : TurnOnRun");
        ctx.movement.running(true);
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.movement.running();
            }
        }, 500, 10);
        GC.randomizeRunEnergyMinimum();
    }
}
