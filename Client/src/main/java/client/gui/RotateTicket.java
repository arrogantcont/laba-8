package client.gui;

import java.util.TimerTask;

public class RotateTicket extends TimerTask {
    int step = 0;
    JLabelTicket rotatingTicket;

    public RotateTicket(JLabelTicket rotatingTicket) {
        this.rotatingTicket = rotatingTicket;
    }

    public void run() {
        if (step >= 32) {
            this.cancel();
            rotatingTicket.setAngle(0);
        }
        if (rotatingTicket != null) {
            rotatingTicket.setAngle(rotatingTicket.getAngle() + Math.PI / 32);
            rotatingTicket.repaint();
            step += 1;
        }
    }
}
