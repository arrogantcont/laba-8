package client.gui;

import Exeptions.CommandException;
import client.ClientSender;
import commons.commands.AddCommand;
import commons.commands.Add_IF_MaxCommand;
import commons.commands.Add_IF_MinCommand;
import commons.commands.UpdateCommand;
import commons.model.*;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.io.IOException;
import java.util.Date;

public class TicketFormGui extends JPanel {
    public JLabel mainLabel;
    public JLabel ticketName;
    public JTextField nameField;
    private final ClientSender clientSender;
    private final GUIManager guiManager;

    private JButton goBackButton;
    private JRadioButton eventRadioButton;
    public JButton activeButton;
    public JButton addIfMaxButton;
    public JButton addIfMinButton;
    private JLabel xCoordLabel;
    private JLabel yCoordLabel;
    public JTextField xField;
    public JTextField yField;
    private JLabel priceLabel;
    public JTextField priceField;
    private JLabel refundableLabel;
    public JComboBox<Boolean> refundableField;
    private JLabel ticketTypeLabel;
    public JComboBox ticketTypeField;
    private JLabel eventLabel;
    private JLabel commentLabel;
    public JTextField commentField;
    private JLabel eventNameLabel;
    public JTextField eventNameField;
    private JLabel ticketCountLabel;
    public JTextField ticketCountField;
    private JLabel eventTypeLabel;
    private JComboBox eventTypeField;
    private boolean add_if_max = false;
    private boolean add_if_min = false;
    @Getter
    @Setter
    public boolean updateNeeded;
    @Setter
    @Getter
    public int updateId;


    public TicketFormGui(ClientSender clientSender, GUIManager guiManager) {
        super();
        this.clientSender = clientSender;
        this.guiManager = guiManager;
        placeTicketFormComponents();
    }

    public void placeTicketFormComponents() {

        this.setLayout(null);

        mainLabel = new JLabel(UIMessages.null_field_message);

        mainLabel.setBounds(10, 20, 250, 25);
        this.add(mainLabel);

        ticketName = new JLabel(UIMessages.ticket_name);
        ticketName.setBounds(10, 50, 80, 25);
        this.add(ticketName);

        nameField = new JTextField(20);
        nameField.setBounds(100, 50, 165, 25);
        this.add(nameField);

        xCoordLabel = new JLabel(UIMessages.x);
        xCoordLabel.setBounds(10, 80, 80, 25);
        this.add(xCoordLabel);

        xField = new JTextField(20);
        xField.setBounds(100, 80, 165, 25);
        this.add(xField);

        yCoordLabel = new JLabel(UIMessages.y);
        yCoordLabel.setBounds(10, 110, 80, 25);
        this.add(yCoordLabel);

        yField = new JTextField(20);
        yField.setBounds(100, 110, 165, 25);
        this.add(yField);


        priceLabel = new JLabel(UIMessages.price);
        priceLabel.setBounds(10, 140, 80, 25);
        this.add(priceLabel);

        priceField = new JTextField(20);
        priceField.setBounds(100, 140, 165, 25);
        this.add(priceField);

        refundableLabel = new JLabel(UIMessages.refundable);
        refundableLabel.setBounds(10, 170, 80, 25);
        this.add(refundableLabel);

        refundableField = new JComboBox();
        refundableField.addItem(true);
        refundableField.addItem(false);
        refundableField.setBounds(100, 170, 165, 25);
        this.add(refundableField);

        ticketTypeLabel = new JLabel(UIMessages.type);
        ticketTypeLabel.setBounds(10, 200, 80, 25);
        this.add(ticketTypeLabel);

        ticketTypeField = new JComboBox(TicketType.values());
        ticketTypeField.setBounds(100, 200, 165, 25);
        this.add(ticketTypeField);

        commentLabel = new JLabel(UIMessages.comment);
        commentLabel.setBounds(10, 230, 80, 25);
        this.add(commentLabel);

        commentField = new JTextField(20);
        commentField.setBounds(100, 230, 165, 25);
        this.add(commentField);

        eventNameLabel = new JLabel(UIMessages.event_name);
        eventNameLabel.setBounds(10, 260, 80, 25);
        this.add(eventNameLabel);

        eventNameField = new JTextField(20);
        eventNameField.setBounds(100, 260, 165, 25);
        this.add(eventNameField);

        ticketCountLabel = new JLabel(UIMessages.ticket_count);
        ticketCountLabel.setBounds(10, 290, 80, 25);
        this.add(ticketCountLabel);


        ticketCountField = new JTextField(20);
        ticketCountField.setBounds(100, 290, 165, 25);
        this.add(ticketCountField);

        eventTypeLabel = new JLabel(UIMessages.event_type);
        eventTypeLabel.setBounds(10, 320, 80, 25);
        this.add(eventTypeLabel);

        eventTypeField = new JComboBox(EventType.values());
        eventTypeField.setBounds(100, 320, 165, 25);
        this.add(eventTypeField);

        eventLabel = new JLabel(UIMessages.event_question);
        eventLabel.setBounds(10, 350, 200, 25);
        this.add(eventLabel);

        activeButton = new JButton(UIMessages.add_ticket);
        activeButton.setBounds(10, 380, 120, 25);

        addIfMaxButton = new JButton(UIMessages.add_if_max);
        addIfMaxButton.setBounds(10, 410, 120, 25);

        addIfMinButton = new JButton(UIMessages.add_if_min);
        addIfMinButton.setBounds(145, 410, 120, 25);

        eventRadioButton = new JRadioButton(UIMessages.add_event);
        eventRadioButton.setBounds(145, 380, 120, 25);

        setFalse();

        this.add(addIfMaxButton);
        this.add(addIfMinButton);
        this.add(activeButton);
        this.add(eventRadioButton);


        addIfMinButton.addActionListener(arg0 -> {
            add_if_max = false;
            add_if_min = true;
            addIf();
        });

        addIfMaxButton.addActionListener(arg0 -> {
            add_if_max = true;
            add_if_min = false;
            addIf();
        });

        eventRadioButton.addActionListener(actionEvent -> {
            if (!eventNameField.isEditable() && !ticketCountField.isEditable()) {
                eventNameField.setEditable(true);
                ticketCountField.setEditable(true);
                eventTypeField.setEnabled(true);
            } else
                setFalse();
        });


        goBackButton = new JButton(UIMessages.go_back);
        goBackButton.setBounds(185, 20, 80, 25);
        goBackButton.addActionListener(arg0 -> guiManager.showTicketOfficeForm());
        this.add(goBackButton);
        activeButton.addActionListener((actionEvent) -> {
            int ticketId = 0;
            int x = 0;
            int y = 0;
            int price = 0;
            int ticketCount = 0;
            String eventName = "";
            Event event = null;
            try {
                String ticketName = nameField.getText();
                try {
                    x = Integer.parseInt(xField.getText());
                    y = Integer.parseInt(yField.getText());
                    if (x < 0 || x > 800 || y < 0 || y > 250) {
                        guiManager.showFailWindow(UIMessages.wrong_coords);
                        return;
                    }
                } catch (Exception e) {
                    guiManager.showFailWindow(UIMessages.wrong_coords);
                    return;
                }
                boolean refundable = Boolean.parseBoolean(refundableField.getSelectedItem().toString());
                try {
                    price = Integer.parseInt(priceField.getText());
                    if (price == 0) {
                        guiManager.showFailWindow(UIMessages.wrong_price);
                        return;
                    }
                } catch (Exception e) {
                    guiManager.showFailWindow(UIMessages.wrong_price);
                    return;
                }
                String comment = commentField.getText();
                TicketType ticketType = TicketType.valueOf(ticketTypeField.getSelectedItem().toString());
                if (ticketName.equals("") || comment.equals("")) {
                    guiManager.showFailWindow(UIMessages.wrong_text_fields);
                    return;
                }
                if (eventRadioButton.isSelected()) {
                    eventName = eventNameField.getText();
                    if (eventName.equals("")) {
                        guiManager.showFailWindow(UIMessages.wrong_text_fields);
                        return;
                    }
                    try {
                        ticketCount = Integer.parseInt(ticketCountField.getText());
                        if (ticketCount == 0) {
                            guiManager.showFailWindow(UIMessages.wrong_ticket_count);
                            return;
                        }
                    } catch (Exception e) {
                        guiManager.showFailWindow(UIMessages.wrong_ticket_count);
                        return;
                    }
                    event = new Event(0L, eventName, ticketCount, EventType.valueOf(eventTypeField.getSelectedItem().toString()));
                }
                Coordinates coordinates = new Coordinates(x, y);
                if (updateNeeded) ticketId = updateId;
                Ticket ticket = Ticket.builder()
                        .comment(comment)
                        .coordinates(coordinates)
                        .creationDate(new Date())
                        .event(event)
                        .ticketId(ticketId)
                        .name(ticketName)
                        .price(price)
                        .refundable(refundable)
                        .type(ticketType)
                        .user(guiManager.getUser())
                        .build();

                if (!updateNeeded) {
                    AddCommand addCommand = new AddCommand(ticket, ticket.getUser());
                    guiManager.showTicketOfficeForm();
                    clientSender.sendCommand(addCommand);
                } else {
                    UpdateCommand updateCommand = new UpdateCommand(ticket, ticket.getUser());
                    guiManager.showTicketOfficeForm();
                    clientSender.sendCommand(updateCommand);
                }
                clearFields();
            } catch (NullPointerException e) {
                guiManager.showFailWindow("l");
            } catch (CommandException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }

    private void setFalse() {
        eventNameField.setEditable(false);
        ticketCountField.setEditable(false);
        eventTypeField.setEnabled(false);
    }

    public void clearFields() {
        ticketCountField.setText("");
        eventNameField.setText("");
        commentField.setText("");
        nameField.setText("");
        priceField.setText("");
        xField.setText("");
        yField.setText("");
    }

    public void addIf() {
        int ticketId = 0;
        int x = 0;
        int y = 0;
        int price = 0;
        int ticketCount = 0;
        String eventName = "";
        Event event = null;
        String ticketName = nameField.getText();
        try {
            x = Integer.parseInt(xField.getText());
            y = Integer.parseInt(yField.getText());
            if (x < 0 || x > 851 || y < 0 || y > 621) {
                guiManager.showFailWindow(UIMessages.wrong_coords);
                return;
            }
        } catch (Exception e) {
            guiManager.showFailWindow(UIMessages.wrong_coords);
            return;
        }
        boolean refundable = Boolean.parseBoolean(refundableField.getSelectedItem().toString());
        try {
            price = Integer.parseInt(priceField.getText());
            if (price == 0) {
                guiManager.showFailWindow(UIMessages.wrong_price);
                return;
            }
        } catch (Exception e) {
            guiManager.showFailWindow(UIMessages.wrong_price);
            return;
        }
        String comment = commentField.getText();
        TicketType ticketType = TicketType.valueOf(ticketTypeField.getSelectedItem().toString());
        if (ticketName.equals("") || comment.equals("")) {
            guiManager.showFailWindow(UIMessages.wrong_text_fields);
            return;
        }
        if (eventRadioButton.isSelected()) {
            eventName = eventNameField.getText();
            if (eventName.equals("")) {
                guiManager.showFailWindow(UIMessages.wrong_text_fields);
                return;
            }
            try {
                ticketCount = Integer.parseInt(ticketCountField.getText());
                if (ticketCount == 0) {
                    guiManager.showFailWindow(UIMessages.wrong_ticket_count);
                    return;
                }
            } catch (Exception e) {
                guiManager.showFailWindow(UIMessages.wrong_ticket_count);
                return;
            }
            event = new Event(0L, eventName, ticketCount, EventType.valueOf(eventTypeField.getSelectedItem().toString()));
        }
        Coordinates coordinates = new Coordinates(x, y);
        Ticket ticket = Ticket.builder()
                .comment(comment)
                .coordinates(coordinates)
                .creationDate(new Date())
                .event(event)
                .ticketId(ticketId)
                .name(ticketName)
                .price(price)
                .refundable(refundable)
                .type(ticketType)
                .user(guiManager.getUser())
                .build();

        Add_IF_MinCommand add_if_minCommand = new Add_IF_MinCommand(ticket, guiManager.getUser());
        Add_IF_MaxCommand add_if_maxCommand = new Add_IF_MaxCommand(ticket, guiManager.getUser());
        try {
            if (add_if_min) clientSender.sendCommand(add_if_minCommand);
            if (add_if_max) clientSender.sendCommand(add_if_maxCommand);
            clearFields();
            guiManager.showTicketOfficeForm();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }

}
