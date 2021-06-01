package paperrooftops.GUI;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import paperrooftops.PaperRooftops;
import paperrooftops.tasks.*;
import paperrooftops.utility.GV;
import paperrooftops.utility.courses.*;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.net.URL;
import java.text.NumberFormat;

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
    private JCheckBox eatFoodCheckBox;
    private JSlider eatFoodSlider;
    private JLabel eatFoodLabel;
    private JCheckBox logoutLevelCheckBox;
    private JLabel logoutLevelLabel;
    private JFormattedTextField logoutLevelFormattedTextField;

    private ClientContext ctx;
    private PaperRooftops main;

    public ScriptOptionsGUI(ClientContext ctx, PaperRooftops main) {
        super("PaperRooftops - made by Paberimees");

        this.ctx = ctx;
        this.main = main;

        this.setMinimumSize(new Dimension(300, 400));
        //this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.setVisible(true);

        initialize();
    }

    private void initialize() {
        /*this.revalidate();
        mainPanel.revalidate();
        this.repaint();
        mainPanel.repaint();*/

        //Course list
        courseList.setSelectedIndex(0);

        //Start button
        startButton.addActionListener((event)->finishSetup());

        //HP Check
        eatFoodCheckBox.addActionListener((event)->toggleEatFood());
        eatFoodSlider.setMinimum(1);
        eatFoodSlider.setMaximum(ctx.skills.realLevel(Constants.SKILLS_HITPOINTS));
        eatFoodSlider.setMinorTickSpacing(5);
        eatFoodSlider.setMajorTickSpacing(10);
        eatFoodSlider.setPaintTicks(true);
        eatFoodSlider.setPaintLabels(true);

        //Level check
        logoutLevelCheckBox.addActionListener((event)->toggleLogoutLevel());
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        //formatter.setMinimum(0);
        //formatter.setMaximum(99);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        logoutLevelFormattedTextField.setFormatterFactory(new DefaultFormatterFactory(formatter));
        logoutLevelFormattedTextField.setHorizontalAlignment(logoutLevelFormattedTextField.CENTER);
    }

    private void toggleEatFood() {
        eatFoodLabel.setEnabled(eatFoodCheckBox.isSelected());
        eatFoodSlider.setEnabled(eatFoodCheckBox.isSelected());
    }

    private void toggleLogoutLevel() {
        logoutLevelLabel.setEnabled(logoutLevelCheckBox.isSelected());
        logoutLevelFormattedTextField.setEnabled(logoutLevelCheckBox.isSelected());
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
            if (logoutLevelCheckBox.isSelected()) {
                try {
                    GV.MAX_LEVEL = Integer.parseInt(logoutLevelFormattedTextField.getValue().toString());
                } catch (Exception e) {
                    System.out.println("EXCEPTION: " + e);
                    ctx.controller.stop();
                }
                main.addTask(new CheckLevel(ctx, main));
            }
            if (eatFoodCheckBox.isSelected()) {
                GV.HEALTH_MINIMUM = eatFoodSlider.getValue();
                main.addTask(new CheckHealth(ctx, main));
            }
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
