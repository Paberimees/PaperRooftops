package zillaagility.GUI;

import javax.swing.*;
import java.text.NumberFormat;

public class ScriptOptionsGUI extends JFrame {
    private JPanel mainPanel;
    private JList courseList;
    private JButton startButton;
    private JCheckBox hpCheckBox;
    private JLabel courseHeader;
    private JFormattedTextField hpFormattedTextField;

    //private ClientContext ctx;
    //private ZillaAgility main;

    /*public ScriptOptionsGUI(ClientContext ctx, ZillaAgility main) {
        this.ctx = ctx;
        this.main = main;
    }*/

    public ScriptOptionsGUI() {
        super("PaperRooftops - made by Paberimees");
        this.setContentPane(mainPanel);
        this.pack();
        this.setVisible(true);

        initialize();
    }

    private void initialize() {
        //Hp checkbox
        hpCheckBox.addActionListener((event)->hpCheckBoxAction());

        //Start button
        startButton.addActionListener((event)->finishSetup());
    }

    private void finishSetup() {
        System.out.println("FinishSetup...");
    }

    public void hpCheckBoxAction() {
        if (hpCheckBox.isSelected()) {
            hpFormattedTextField.setEnabled(true);
            hpFormattedTextField.setEditable(true);
            return;
        }
        hpFormattedTextField.setEnabled(false);
        hpFormattedTextField.setEditable(false);
    }

    private void createUIComponents() {
        //Hp textfield
        NumberFormat integerFieldFormatter = NumberFormat.getIntegerInstance();
        integerFieldFormatter.setGroupingUsed(false);
        hpFormattedTextField = new JFormattedTextField(integerFieldFormatter);
    }
}
