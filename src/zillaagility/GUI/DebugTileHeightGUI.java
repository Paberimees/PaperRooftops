package zillaagility.GUI;

import zillaagility.ZillaAgility;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class DebugTileHeightGUI {

    protected ZillaAgility main;

    private JLabel playerDataPanelLowerTileHeightLabel;
    private JLabel obstacleDataPanelLowerTileHeightLabel;
    private JLabel obstacleDataPanelHeader;

    public DebugTileHeightGUI(ZillaAgility main) {
        //What an absolute piece of garbage. Gets the job done though
        this.main = main;
        JFrame mainFrame = new JFrame();

        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new GridLayout(1,2));

        JPanel playerDataPanel = new JPanel();
        playerDataPanel.setLayout(new GridLayout(2,1));
        JLabel playerDataPanelHeader = new JLabel("Player");
        playerDataPanel.add(playerDataPanelHeader);
        JPanel playerDataPanelLower = new JPanel();
        playerDataPanelLower.setLayout(new GridLayout(1,2));
        playerDataPanelLower.add(new JLabel("TileHeight:"));
        playerDataPanelLowerTileHeightLabel = new JLabel("<no data>");
        playerDataPanelLower.add(playerDataPanelLowerTileHeightLabel);
        playerDataPanel.add(playerDataPanelLower);
        dataPanel.add(playerDataPanel);

        JPanel obstacleDataPanel = new JPanel();
        obstacleDataPanel.setLayout(new GridLayout(2,1));
        obstacleDataPanelHeader = new JLabel("Obstacle");
        obstacleDataPanel.add(obstacleDataPanelHeader);
        JPanel obstacleDataPanelLower = new JPanel();
        obstacleDataPanelLower.setLayout(new GridLayout(1,2));
        obstacleDataPanelLower.add(new JLabel("TileHeight:"));
        obstacleDataPanelLowerTileHeightLabel = new JLabel("<no data>");
        obstacleDataPanelLower.add(obstacleDataPanelLowerTileHeightLabel);
        obstacleDataPanel.add(obstacleDataPanelLower);
        dataPanel.add(obstacleDataPanel);

        JButton getDataButton = new JButton("Pull TileHeights");
        getDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (main.currentGameObject.valid()) {
                    updatePlayerTileHeight(((int) (Math.random() * 640) + 480) * -1 + "");
                    updateObstacleTileHeight(((int) (Math.random() * 640) + 480) * -1 + "");
                    updateObstacleHeader(main.currentGameObject.name());
                    return;
                }
                updatePlayerTileHeight("<no data>");
                updateObstacleTileHeight("<no data>");
                updateObstacleHeader("Obstacle");
            }
        });

        mainFrame.add(dataPanel);
        mainFrame.add(getDataButton);

        mainFrame.setSize(320, 240);
        mainFrame.setLayout(new GridLayout(2,2));

        //mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Lmao this exits the whole script and closes the bot aswell.
        mainFrame.setVisible(true);
    }

    public void updatePlayerTileHeight(String newTileHeight) {
        playerDataPanelLowerTileHeightLabel.setText(newTileHeight);
    }

    public void updateObstacleTileHeight(String newTileHeight) {
        obstacleDataPanelLowerTileHeightLabel.setText(newTileHeight);
    }

    public void updateObstacleHeader(String newHeader) {
        obstacleDataPanelHeader.setText(newHeader);
    }
}
