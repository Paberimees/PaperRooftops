package zillaagility.tasks;

import org.powerbot.script.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;
import zillaagility.ZillaAgility;

public abstract class Task <C extends ClientContext> extends ClientAccessor<C> {

    protected ZillaAgility main;

    public Task(C ctx, ZillaAgility main) {
        super(ctx);
        this.main = main;
    }

    public abstract boolean activate();

    public abstract void execute();
}