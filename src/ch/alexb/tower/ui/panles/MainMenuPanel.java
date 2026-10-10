package ch.alexb.tower.ui.panles;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {

    private CardLayout cardLayout;

    private JPanel hauptPanel;


    public MainMenuPanel(CardLayout cardLayout, JPanel hauptPanel){

        this.cardLayout = cardLayout;

        this.hauptPanel = hauptPanel;


        JButton mapButton = new JButton("Map auswählen");

        setLayout(null);

        mapButton.setBounds(10, 120, 150, 20);

        add(mapButton);


        mapButton.addActionListener(e -> {

            cardLayout.show(hauptPanel, "maps");


        });




    }



    @Override
    protected void paintComponent(Graphics g){

        super.paintComponent(g);

        g.setColor(Color.GREEN);
        g.fillRect(10, 10, 150, 100);
        g.setColor(Color.GRAY);
        g.fillRect(10, 55, 150, 15);



    }
}
