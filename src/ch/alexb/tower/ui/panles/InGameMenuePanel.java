package ch.alexb.tower.ui.panles;

import java.awt.*;

public class InGameMenuePanel {

    public void menueGameOver(Graphics g2){

        int[] x = {425, 425, 475};
        int[] y = {360, 400, 380};
        g2.setColor(Color.WHITE);
        //Playbutten
        g2.drawRect(420, 350, 70, 60);
        //Settings
        g2.drawRect(510, 350, 70, 60);

        g2.setFont(new Font("Arial", Font.PLAIN, 40));
        g2.drawString("Menue", 440, 300);
        g2.setFont(new Font("Arial", Font.PLAIN, 90));
        g2.drawString("⚙️", 515, 405);
        g2.setColor(Color.GREEN);
        g2.fillPolygon(x, y, 3);

    }

}
