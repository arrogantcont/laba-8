package client.gui;

import Exeptions.CommandException;
import client.ClientSender;
import commons.commands.Remove_By_IdCommand;
import commons.model.Ticket;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TicketOfficeGui extends JPanel {
    private ClientSender clientSender;
    private GUIManager guiManager;
    private JLabel userLabel;
    private JButton addTicketButton;
    private JButton updateButton;
    private JButton helpButton;
    private JButton removeByIdButton;
    ArrayList<JLabelTicket> ticketsLbls = new ArrayList<>();
    DefaultTableModel tableModel;
    JTable table;
    CopyOnWriteArrayList<Ticket> tickets = new CopyOnWriteArrayList<>();
    private JComboBox languages;
    private String[] columnsHeader;


    public TicketOfficeGui(ClientSender clientSender, GUIManager guiManager) {
        super();
        this.clientSender = clientSender;
        this.guiManager = guiManager;
    }

    public void placeTicketOfficeComponents() {
        this.setLayout(null);
        columnsHeader = new java.lang.String[]{UIMessages.id, UIMessages.ticket_name, UIMessages.x, UIMessages.y, UIMessages.price, UIMessages.creation_date, UIMessages.refundable, UIMessages.type, UIMessages.comment, UIMessages.event_name, UIMessages.ticket_count, UIMessages.event_type, UIMessages.user};
        tableModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;//This causes all cells to be not editable
            }

            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 2:
                    case 3:
                    case 4:
                    case 10:
                    case 0:
                        return Integer.class;
                    default:
                        return String.class;
                }
            }
        };

        tableModel.setColumnIdentifiers(columnsHeader);
        table = new JTable(tableModel);
        table.setBounds(160, 20, 800, 2500);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
        TableRowSorter trs = new TableRowSorter(tableModel);

        class IntComparator implements Comparator {
            public int compare(Object o1, Object o2) {
                Integer int1 = Integer.parseInt((String) o1);
                Integer int2 = Integer.parseInt((String) o2);
                return int1.compareTo(int2);
            }

            public boolean equals(Object o2) {
                return this.equals(o2);
            }
        }

        trs.setComparator(2, new IntComparator());
        trs.setComparator(3, new IntComparator());
        trs.setComparator(4, new IntComparator());
        trs.setComparator(10, new IntComparator());
        trs.setComparator(0, new IntComparator());

        table.setRowSorter(trs);


        table.setAutoCreateRowSorter(false);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>(2001);
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        table.getRowSorter().setSortKeys(sortKeys);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVisible(true);


        userLabel = new JLabel(UIMessages.user + " " + guiManager.getUser().getUsername());
        userLabel.setBounds(10, 20, 150, 25);
        scrollPane.setBounds(160, 20, 800, 270);
        addTicketButton = new JButton(UIMessages.add_ticket);
        addTicketButton.setBounds(10, 150, 130, 25);
        addTicketButton.addActionListener(arg0 -> {
                    guiManager.getPanelTicketForm().addIfMinButton.setEnabled(true);
                    guiManager.getPanelTicketForm().addIfMaxButton.setEnabled(true);
                    guiManager.getPanelTicketForm().clearFields();
                    guiManager.getPanelTicketForm().setUpdateNeeded(false);
                    guiManager.getPanelTicketForm().mainLabel.setText(UIMessages.null_field_message);
                    guiManager.getPanelTicketForm().activeButton.setText(UIMessages.add_ticket);
                    guiManager.showTicketForm();
                }
        );


        helpButton = new JButton(UIMessages.help);
        helpButton.setHorizontalAlignment(SwingConstants.CENTER);
        helpButton.setVerticalAlignment(SwingConstants.CENTER);
        helpButton.setBounds(10, 240, 130, 25);
        helpButton.addActionListener(arg0 -> {
            JFrame frame = new JFrame(UIMessages.help);
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                }
            });
            JTextArea label = new JTextArea(UIMessages.text_help);
            label.setLocation(frame.getLocation());
            label.setEditable(false);
            frame.getContentPane().add(label);
            frame.setPreferredSize(new Dimension(400, 200));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });


        updateButton = new JButton(UIMessages.update_by_id);
        updateButton.setBounds(10, 180, 130, 25);
        updateButton.addActionListener(arg0 -> {
            if (table.getSelectedRowCount() == 0) {
                guiManager.showFailWindow(UIMessages.selection_fail);
                return;
            }
            if (guiManager.getUser().getUsername().equals(table.getValueAt(table.getSelectedRow(), 12))) {
                guiManager.getPanelTicketForm().addIfMinButton.setEnabled(false);
                guiManager.getPanelTicketForm().addIfMaxButton.setEnabled(false);
                guiManager.getPanelTicketForm().setUpdateNeeded(true);
                guiManager.getPanelTicketForm().setUpdateId(Integer.parseInt((String) table.getValueAt(table.getSelectedRow(), 0)));
                guiManager.getPanelTicketForm().mainLabel.setText(UIMessages.update_by_id);
                guiManager.getPanelTicketForm().activeButton.setText(UIMessages.update);
                guiManager.getPanelTicketForm().ticketCountField.setText((String) table.getValueAt(table.getSelectedRow(), 10));
                guiManager.getPanelTicketForm().eventNameField.setText((String) table.getValueAt(table.getSelectedRow(), 9));
                guiManager.getPanelTicketForm().commentField.setText((String) table.getValueAt(table.getSelectedRow(), 8));
                guiManager.getPanelTicketForm().nameField.setText((String) table.getValueAt(table.getSelectedRow(), 1));
                guiManager.getPanelTicketForm().priceField.setText((String) table.getValueAt(table.getSelectedRow(), 4));
                guiManager.getPanelTicketForm().xField.setText((String) table.getValueAt(table.getSelectedRow(), 2));
                guiManager.getPanelTicketForm().yField.setText((String) table.getValueAt(table.getSelectedRow(), 3));
                guiManager.showTicketForm();
            } else {
                guiManager.showFailWindow(UIMessages.modification_fail);
            }
        });

        languages = new JComboBox(new String[]{"ru", "eng", "lv", "ro", "es"});
        languages.setBounds(10, 120, 130, 25);
        languages.addActionListener(arg0 -> {
            String s = (String) languages.getSelectedItem();
            switch (s) {
                case "ru": {
                    UIMessages.setRu();
                    refreshComps();
                    guiManager.getPanelRegister().refreshComps();
                    guiManager.getPanelCont().repaint();
                    guiManager.getPanelLogin().repaint();
                    this.repaint();
                    System.out.println(UIMessages.event_question);
                    guiManager.getFrame().revalidate();
                    guiManager.getFrame().repaint();
                    updateHeaders();
                    break;
                }
                case "eng": {
                    UIMessages.setEn();
                    refreshComps();
                    guiManager.getPanelRegister().refreshComps();
                    guiManager.getPanelCont().repaint();
                    guiManager.getPanelLogin().repaint();
                    this.repaint();
                    guiManager.getFrame().revalidate();
                    guiManager.getFrame().repaint();
                    updateHeaders();
                    break;
                }
                case "lv": {
                    UIMessages.setLv();
                    refreshComps();
                    guiManager.getPanelRegister().refreshComps();
                    guiManager.getPanelCont().repaint();
                    guiManager.getPanelLogin().repaint();
                    this.repaint();
                    guiManager.getFrame().revalidate();
                    guiManager.getFrame().repaint();
                    updateHeaders();
                    break;
                }
                case "ro": {
                    UIMessages.setRo();
                    refreshComps();
                    guiManager.getPanelRegister().refreshComps();
                    guiManager.getPanelCont().repaint();
                    guiManager.getPanelLogin().repaint();
                    this.repaint();
                    guiManager.getFrame().revalidate();
                    guiManager.getFrame().repaint();
                    updateHeaders();
                    break;
                }
                case "es": {
                    UIMessages.setCo();
                    refreshComps();
                    guiManager.getPanelRegister().refreshComps();
                    guiManager.getPanelCont().repaint();
                    guiManager.getPanelLogin().repaint();
                    this.repaint();
                    guiManager.getFrame().revalidate();
                    guiManager.getFrame().repaint();
                    updateHeaders();
                    break;
                }
            }
        });
        this.add(languages);

        removeByIdButton = new JButton(UIMessages.remove);
        removeByIdButton.setBounds(10, 210, 130, 25);
        this.add(helpButton);
        this.add(removeByIdButton);
        this.add(addTicketButton);
        this.add(scrollPane);
        this.add(userLabel);
        this.add(updateButton);

        //добавлем слушатель на выбор строки по аналогии как это было с JList
        table.setRowSelectionAllowed(true);
        ListSelectionModel rowSelectionModel = table.getSelectionModel();
        rowSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        removeByIdButton.addActionListener(arg0 -> {
//чтобы получить id - получаем его из нулевого столбца и выбранной строки
            if (table.getSelectedRowCount() == 0) {
                guiManager.showFailWindow(UIMessages.selection_fail);
                return;
            }
            if (guiManager.getUser().getUsername().equals(table.getValueAt(table.getSelectedRow(), 12))) {
                Remove_By_IdCommand remove_by_idCommand = new Remove_By_IdCommand(Integer.parseInt(table.getModel().getValueAt(table.getSelectedRow(), 0).toString()), guiManager.getUser());
                try {
                    clientSender.sendCommand(remove_by_idCommand);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (CommandException e) {
                    e.printStackTrace();
                }
            } else {
                guiManager.showFailWindow(UIMessages.modification_fail);
            }
        });
    }

    public void addNewTicket(Ticket ticket) throws IOException {
        String[] tableticket;
        if (ticket.getEvent() == null)
            tableticket = new String[]{String.valueOf(ticket.getTicketId()), ticket.getName(), String.valueOf(ticket.getCoordinates().getX()), String.valueOf(ticket.getCoordinates().getY()), String.valueOf(ticket.getPrice()), String.valueOf(ticket.getCreationDate()), String.valueOf(ticket.isRefundable()), String.valueOf(ticket.getType()), ticket.getComment(), "-", "0", "-", ticket.getUser().getUsername()};
        else
            tableticket = new String[]{String.valueOf(ticket.getTicketId()), ticket.getName(), String.valueOf(ticket.getCoordinates().getX()), String.valueOf(ticket.getCoordinates().getY()), String.valueOf(ticket.getPrice()), String.valueOf(ticket.getCreationDate()), String.valueOf(ticket.isRefundable()), String.valueOf(ticket.getType()), ticket.getComment(), ticket.getEvent().getName(), String.valueOf(ticket.getEvent().getTicketsCount()), String.valueOf(ticket.getEvent().getEventType()), ticket.getUser().getUsername()};
        tableModel.addRow(tableticket);
        tickets.add(ticket);


        long max_id = -1;


        JLabelTicket picLabel = new JLabelTicket() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.drawRect(0, 0, 0, 0);
                Color color = new Color((ticket.getUser().getUsername().hashCode() & 242), ticket.getUser().getUsername().hashCode() & 37, ticket.getUser().getUsername().hashCode() & 255).brighter().brighter().brighter();
                System.out.println(color);
                g2.setColor(color);

                g2.rotate(this.getAngle(), (int) this.getTicketSize() / 2, (int) this.getTicketSize() / 2);
                g2.drawRect(0, 0, this.getTicketSize(), this.getTicketSize());
                g2.fillRect(0, 0, this.getTicketSize(), this.getTicketSize());
            }
        };
        int ticketSize = (int) (ticket.getPrice() * 11);
        if (ticketSize > 64) {
            ticketSize = 64;
        }
        picLabel.setTicketSize(ticketSize);
        picLabel.repaint();

        picLabel.setId(ticket.getTicketId());
        picLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                clickedOnTicket((int) picLabel.getId());
            }
        });
        picLabel.setBounds(ticket.getCoordinates().getX() + 100, (int) (ticket.getCoordinates().getY() + 300), ticketSize, ticketSize);
        if (ticket.getTicketId() > max_id) {

            picLabel.rotate();
        }
        ticketsLbls.add(picLabel);

        addTickets();
        System.out.println("Добавлен новый билет");
    }

    public void removeTicket(int id) {
        //тут непостредственно удаляем нужную строку
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).equals(String.valueOf(id))) {
                tableModel.removeRow(i);
            }
        }
        Iterator<Ticket> iterator = tickets.iterator();
        while (iterator.hasNext()) {
            Ticket c = iterator.next();
            if (c.getTicketId() == id) {
                tickets.remove(c);
            }
        }
        for (JLabelTicket pic : ticketsLbls) {
            if (pic.getId() == id) {
                ticketsLbls.remove(pic);
                this.remove(pic);
                clearTickets();
                addTickets();
                this.repaint();
                break;
            }
        }

    }

    void addTickets() {
        for (JLabel pic : ticketsLbls) {
            this.add(pic);
        }
    }

    void clearTickets() {
        for (JLabelTicket pic : ticketsLbls) {
            this.remove(pic);
        }
    }

    public void clickedOnTicket(int id) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketId() == id) {
                String[] arr = ticket.toString().split("event=");
                JFrame frame = new JFrame(UIMessages.help);
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        super.windowClosing(e);
                    }
                });
                JPanel panel = new JPanel();
                panel.setPreferredSize(new Dimension(600, 100));
                JTextArea label = new JTextArea(arr[0] + "\n" + arr[1]);
                label.setBounds(1, 1, 50, 50);
                panel.add(label);
                frame.setPreferredSize(new Dimension(980, 200));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                JButton remove = new JButton(UIMessages.remove);
                remove.setBounds(100, 50, 40, 30);
                remove.addActionListener(arg0 -> {
                    if (ticket.getUser().getUsername().equals(guiManager.getUser().getUsername())) {
                        removeTicket(id);
                        frame.setVisible(false);
                        frame.dispose();
                    } else guiManager.showFailWindow(UIMessages.modification_fail);
                });
                JButton update = new JButton(UIMessages.update);
                update.setBounds(50, 50, 40, 30);
                update.addActionListener(arg0 -> {
                    if (ticket.getUser().getUsername().equals(guiManager.getUser().getUsername())) {
                        guiManager.getPanelTicketForm().addIfMinButton.setEnabled(false);
                        guiManager.getPanelTicketForm().addIfMaxButton.setEnabled(false);
                        guiManager.getPanelTicketForm().setUpdateNeeded(true);
                        guiManager.getPanelTicketForm().setUpdateId(id);
                        guiManager.getPanelTicketForm().mainLabel.setText(UIMessages.update_by_id);
                        guiManager.getPanelTicketForm().activeButton.setText(UIMessages.update);
                        if (!(ticket.getEvent() == null)) {
                            guiManager.getPanelTicketForm().ticketCountField.setText(String.valueOf(ticket.getEvent().getTicketsCount()));
                            guiManager.getPanelTicketForm().eventNameField.setText(ticket.getEvent().getName());
                        }
                        guiManager.getPanelTicketForm().commentField.setText(ticket.getComment());
                        guiManager.getPanelTicketForm().nameField.setText(ticket.getName());
                        guiManager.getPanelTicketForm().priceField.setText(String.valueOf(ticket.getPrice()));
                        guiManager.getPanelTicketForm().xField.setText(String.valueOf(ticket.getCoordinates().getX()));
                        guiManager.getPanelTicketForm().yField.setText(String.valueOf(ticket.getCoordinates().getX()));
                        guiManager.showTicketForm();
                        frame.setVisible(false);
                        frame.dispose();
                    } else guiManager.showFailWindow(UIMessages.modification_fail);
                });
                panel.add(remove);
                panel.add(update);
                JScrollPane scrollPane = new JScrollPane(panel);
                frame.add(scrollPane, BorderLayout.CENTER);
                break;
            }
        }
    }

    public void refreshComps() {
        userLabel.setText(UIMessages.user + " " + guiManager.getUser().getUsername());
        addTicketButton.setText(UIMessages.add_ticket);
        updateButton.setText(UIMessages.update);
        helpButton.setText(UIMessages.help);
        removeByIdButton.setText(UIMessages.remove);
    }

    private void updateHeaders() {
        String[] newHeaders = new java.lang.String[]{UIMessages.id, UIMessages.ticket_name, UIMessages.x, UIMessages.y, UIMessages.price, UIMessages.creation_date, UIMessages.refundable, UIMessages.type, UIMessages.comment, UIMessages.event_name, UIMessages.ticket_count, UIMessages.event_type, UIMessages.user};
        for (int c = 0; c < newHeaders.length; c++) {
            table.getColumnModel().getColumn(c).setHeaderValue(newHeaders[c]);
        }
        table.revalidate();
        table.repaint();
    }
}
