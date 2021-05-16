package paperrooftops.tasks;

import org.powerbot.script.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;
import paperrooftops.PaperRooftops;

public abstract class Task <C extends ClientContext> extends ClientAccessor<C> {

    protected PaperRooftops main;

    public Task(C ctx, PaperRooftops main) {
        super(ctx);
        this.main = main;
    }

    public abstract boolean activate();

    public abstract void execute();
}