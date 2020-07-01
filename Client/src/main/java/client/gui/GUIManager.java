package client.gui;


import Exeptions.CommandException;
import client.ClientSender;
import client.CommandHandler;
import commons.User;
import commons.commands.ShowCommand;
import commons.model.Ticket;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.io.IOException;


public class GUIManager {
    private CommandHandler commandHandler;
    @Getter
    private JFrame frame;
    private ClientSender clientSender;
    @Getter
    private CardLayout cl = new CardLayout();
    @Getter
    private JPanel panelCont = new JPanel();
    @Getter
    private LoginGui panelLogin;
    @Getter
    private TicketOfficeGui panelTicketOffice;
    @Getter
    private RegisterGui panelRegister;
    @Getter
    @Setter
    private User user;
    @Getter
    @Setter
    private TicketFormGui panelTicketForm;

    public GUIManager(CommandHandler commandHandler, ClientSender clientSender) {
        this.commandHandler = commandHandler;
        this.clientSender = clientSender;
        panelTicketOffice = new TicketOfficeGui(clientSender, this);
        panelTicketOffice.setOpaque(true);
        panelTicketForm = new TicketFormGui(clientSender, this);
        panelRegister = new RegisterGui(clientSender, this);
        panelLogin = new LoginGui(clientSender, this);
    }

    public void startApplication() {
        panelCont.setLayout(cl);
        panelCont.add(panelLogin, "1");
        panelCont.add(panelRegister, "2");
        panelCont.add(panelTicketOffice, "3");
        panelCont.add(panelTicketForm, "4");
        cl.show(panelCont, "1");
        frame = new JFrame(UIMessages.app_name);
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panelCont);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public void showRegisterForm() {
        cl.show(panelCont, "2");
    }

    public void showLoginForm() {
        cl.show(panelCont, "1");
    }

    public void showTicketForm() {
        cl.show(panelCont, "4");
    }

    public void showTicketOfficeForm() {
        cl.show(panelCont, "3");
    }

    public void sendShowCommand() throws IOException, CommandException {
        clientSender.sendCommand(new ShowCommand(user));
    }

    public void showSuccessWindow() {
        SwingUtilities.invokeLater(() -> {
            panelTicketOffice.placeTicketOfficeComponents();
            panelTicketOffice.refreshComps();
            try {
                sendShowCommand();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CommandException e) {
                e.printStackTrace();
            }
            showDialogWindow(UIMessages.success_message, UIMessages.success_message_close, new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    showTicketOfficeForm();
                    frame.setSize(1000, 750);
                    frame.setLocationRelativeTo(null);
                }
            });
        });
    }

    public void showFailWindow(String cause) {
        showDialogWindow(UIMessages.fail_message, cause, new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                // do nothing
            }
        });
    }

    public void showHelpWindow() {
        showDialogWindow(UIMessages.help, UIMessages.text_help, new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                // do nothing
            }
        });
    }

    public void addNewTicket(Ticket ticket) throws IOException {
        panelTicketOffice.addNewTicket(ticket);
    }

    private void showDialogWindow(String title, String text, WindowAdapter windowAdapter) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.addWindowListener(windowAdapter);

        JLabel label = new JLabel(text);
        label.setLocation(frame.getLocation());
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(label);

        frame.setPreferredSize(new Dimension(300, 200));

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void removeTicket(int id) {
        panelTicketOffice.removeTicket(id);
    }

}

