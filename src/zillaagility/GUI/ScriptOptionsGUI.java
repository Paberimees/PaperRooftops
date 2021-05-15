package zillaagility.GUI;

import org.powerbot.script.rt4.ClientContext;
import zillaagility.ZillaAgility;
import zillaagility.tasks.*;
import zillaagility.utility.courses.*;

import javax.swing.*;

public class ScriptOptionsGUI extends JFrame {
    private JPanel mainPanel;
    private JList courseList;
    private JButton startButton;
    private JLabel courseHeader;

    private ClientContext ctx;
    private ZillaAgility main;

    public ScriptOptionsGUI(ClientContext ctx, ZillaAgility main) {
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

        //Hp checkbox
        //hpCheckBox.addActionListener((event)->hpCheckBoxAction());

        //Start button
        startButton.addActionListener((event)->finishSetup());
    }

    private void finishSetup() {
        //todo add debugmode option to gui
        boolean debugMode = false; //THIS SHOULD BE AN OPTION IN THE GUI

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


        main.setStartScript(true);
        this.dispose();
    }

    /*
    public void hpCheckBoxAction() {
        if (hpCheckBox.isSelected()) {
            hpFormattedTextField.setEnabled(true);
            hpFormattedTextField.setEditable(true);
            return;
        }
        hpFormattedTextField.setEnabled(false);
        hpFormattedTextField.setEditable(false);
    }
     */

}
