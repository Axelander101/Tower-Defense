package ch.alexb.tower;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {


        JFrame frame = new JFrame("ch.alexb.tower.ui.Tower Defense");

        CardLayout cardLayout = new CardLayout();
        JPanel container = new JPanel(cardLayout);

        GamePanel gamePanel = new GamePanel();
        UpgradePanel upgradePanel = new UpgradePanel();

        container.add(gamePanel, "GAME");
        container.add(upgradePanel, "UPGRADE");

        frame.add(container);

        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}