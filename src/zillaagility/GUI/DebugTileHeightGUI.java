package zillaagility.GUI;

import org.powerbot.script.rt4.ClientContext;
import zillaagility.ZillaAgility;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class DebugTileHeightGUI {

    protected ZillaAgility main;
    private ClientContext ctx;

    private JLabel playerDataPanelLowerTileHeightLabel;
    private JLabel obstacleDataPanelLowerTileHeightLabel;
    private JLabel obstacleDataPanelHeader;

    public DebugTileHeightGUI(ClientContext ctx, ZillaAgility main) {
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
                    updatePlayerTileHeight(ctx.game.tileHeight(ctx.players.local().boundingModel().x(), ctx.players.local().boundingModel().z()) + "");
                    updateObstacleTileHeight(ctx.game.tileHeight(main.currentGameObject.boundingModel().x(), main.currentGameObject.boundingModel().z()) + "");
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
