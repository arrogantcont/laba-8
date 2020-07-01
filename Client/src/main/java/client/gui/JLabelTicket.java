package client.gui;

import javax.swing.*;
import java.util.Timer;

public class JLabelTicket extends JLabel {

    public JLabelTicket() {
        super();
    }

    private int ticketSize = 16;

    public int getTicketSize() {
        return ticketSize;
    }

    public void setTicketSize(int ticketSize) {
        this.ticketSize = ticketSize;
    }

    private long id = 0;
    private double angle = 0;

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void rotate() {
        Timer timer = new Timer();
        timer.schedule(new RotateTicket(this), 0, 20);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
