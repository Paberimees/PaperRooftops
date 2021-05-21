package paperrooftops.GUI;

import org.powerbot.script.rt4.ClientContext;
import paperrooftops.PaperRooftops;
import paperrooftops.tasks.*;
import paperrooftops.utility.courses.*;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/*
Notes:
- Written with the help of IntelliJ form designer, that's why it looks a bit better.
 */
public class ScriptOptionsGUI extends JFrame {
    private JPanel mainPanel;
    private JList courseList;
    private JButton startButton;
    private JLabel courseHeader;
    private JLabel mainHeader;

    private ClientContext ctx;
    private PaperRooftops main;

    public ScriptOptionsGUI(ClientContext ctx, PaperRooftops main) {
        super("PaperRooftops - made by Paberimees");

        this.ctx = ctx;
        this.main = main;

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.setVisible(true);

        initialize();
    }

    private void initialize() {
        //Course list
        courseList.setSelectedIndex(0);

        //Start button
        startButton.addActionListener((event)->finishSetup());

        //Test
        //System.out.println(main.getClass().getPackageName());
    }

    private void finishSetup() {
        //Hey! This variable should be an option in the GUI and should be removed!
        boolean debugMode = false;

        String value = courseList.getSelectedValue().toString();
        switch (value) {
            case ("Draynor"):
                main.course = new DraynorRooftop();
                break;
            case ("Al Kharid"):
                main.course = new AlKharidRooftop();
                break;
            case ("Varrock"):
                main.course = new VarrockRooftop();
                break;
            case ("Canifis"):
                main.course = new CanifisRooftop();
                break;
            case ("Falador"):
                main.course = new FaladorRooftop();
                break;
            case ("Seers"):
                main.course = new SeersRooftop();
                break;
            default:
                System.out.println("ERROR! Somehow nothing was selected. Exiting script...");
                ctx.controller.stop();
                return;
        }

        if (!debugMode) {
            //Don't need to add the tasks here, but planning to add some options in the future
            //taskList.add(new CheckHealth(ctx, this));
            main.addTask(new CloseMenu(ctx, main));
            main.addTask(new TurnOnRun(ctx, main));
            main.addTask(new PickUpMarkOfGrace(ctx, main));
            main.addTask(new InteractObstacle(ctx, main));
            main.addTask(new GoToStart(ctx, main));
        } else {
            main.addTask(new DebugTask(ctx, main));
            new DebugTileHeightGUI(ctx, main);
        }

        main.setDebugMode(debugMode);
        main.setStartScript(true);
        this.dispose();
    }
}
