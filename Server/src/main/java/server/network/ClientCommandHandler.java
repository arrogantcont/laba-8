package server.network;


import commons.User;
import commons.commands.*;
import commons.model.Ticket;
import commons.model.TicketType;
import commons.responses.*;
import lombok.Data;
import server.DB.DataBaseConnection;
import server.lab6.Pair;
import server.lab6.TicketOffice;

import java.io.BufferedReader;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

@Data
public class ClientCommandHandler {
    private HashMap<String, String> users = new HashMap<>();
    private HashMap<String, ArrayDeque<String>> newHistory = new HashMap<>();
    private HashSet<SocketChannel> activeUsers = new HashSet<>();
    private TicketOffice ticketOffice;
    private TicketType ticketType;
    private BufferedReader reader;
    private int nextTicketId = 1;
    private long nextEventId = 1;
    private boolean inputFromFile = false;
    private BufferedReader script;
    private Date dateOFCreation;
    private final DataBaseConnection dataBaseConnection = new DataBaseConnection();
    private static final Logger ClientCommandHandler_LOGGER = Logger.getLogger(ClientCommandHandler.class.getName());
    ResponseSender responseSender;

    public ClientCommandHandler(ResponseSender responseSender) throws SQLException {
        ticketOffice = new TicketOffice(dataBaseConnection);
        this.responseSender = responseSender;
        this.users = new HashMap<>();
        for (User user : dataBaseConnection.loadAllUsers()) {
            users.put(user.getUsername(), user.getPassword());
        }
        ticketOffice.setTickets(dataBaseConnection.loadAllTickets());
    }


    public void addCommandToHistory(Command command) {
        ArrayDeque<String> list = this.newHistory.get(command.getUser().getUsername());
        if (list == null) list = new ArrayDeque<>();
        list.add(command.getCommand());
        this.newHistory.put(command.getUser().getUsername(), list);
    }

    public void removeFirstCommand(String username) {
        this.newHistory.get(username).removeFirst();
    }

    public void handleCommand(Command command, SocketChannel client) {
        new Thread(() -> {
            try {
                handle(command, client);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }).start();

    }

    private void handle(Command command, SocketChannel client) throws SQLException {
        String stringCommand = command.getCommand();
        if (checkUser(command.getUser()) && !(stringCommand.equals("login") || stringCommand.equals("register"))) {
            if (!(stringCommand.equals("Unknown command"))) {
                if (newHistory.get(command.getUser().getUsername()) != null) {
                    if (newHistory.get(command.getUser().getUsername()).size() < 10) addCommandToHistory(command);
                    else {
                        removeFirstCommand(command.getUser().getUsername());
                        addCommandToHistory(command);
                    }
                } else addCommandToHistory(command);
            }
            switch (stringCommand) {
                case "show":
                    Collection<Ticket> allTickets = ticketOffice.getTickets();
                    for (Ticket ticket : allTickets) {
                        responseSender.sendAnswer(new AddTicketResponse(ticket), client);
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    return;
                case "info":
                    // return createInfo();
                case "clear":
                    ticketOffice.clear(command.getUser());
                    //  return new TicketRemoveResponse(command.getUser());
                case "history":
                    // return newHistory.get(command.getUser().getUsername()).toString();
                case "add":
                    ticketOffice.addNewTicket(((AddCommand) command).getTicket(), command.getUser());
                    for (SocketChannel user : activeUsers) {
                        System.out.println("PRIVET OLEG");
                        responseSender.sendAnswer(new AddTicketResponse(((AddCommand) command).getTicket()), user);
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    return;
                // return new AddTicketResponse(command.getUser());
                case "add_if_max":
                    if (addIfMax(((Add_IF_MaxCommand) command).getTicket(), command.getUser()))
                        for (SocketChannel user : activeUsers) {
                            responseSender.sendAnswer(new AddTicketResponse(((Add_IF_MaxCommand) command).getTicket()), user);
                        }
                    else responseSender.sendAnswer(new AddIfFail("Ticket isn't added"), client);
                    return;
                case "add_if_min":
                    if (addIfMin(((Add_IF_MinCommand) command).getTicket(), command.getUser()))
                        for (SocketChannel user : activeUsers) {
                            responseSender.sendAnswer(new AddTicketResponse(((Add_IF_MinCommand) command).getTicket()), user);
                        }
                    else responseSender.sendAnswer(new AddIfFail("Ticket isn't added"), client);
                    return;
                case "print_field_ascending_price":
                    //return ticketOffice.printAllAscendingPrice();
                case "print_field_descending_refundable":
                    // return ticketOffice.printAllDescendingRefundable();
                case "remove_by_id":
                    if (ticketOffice.checkUser(((Remove_By_IdCommand) command).getId(), command.getUser())) {
                        ticketOffice.remove_by_id(((Remove_By_IdCommand) command).getId());
                        System.out.println("Удалили");
                        for (SocketChannel user : activeUsers) {
                            responseSender.sendAnswer(new TicketRemoveResponse(command.getUser(), ((Remove_By_IdCommand) command).getId()), user);
                        }
                        return;
                        //   return new AddTicketResponse(command.getUser());
                    } //else return new ModificationFailResponse(command.getUser());
                case "filter_greater_than_price":
                    // return ticketOffice.printAllTicketsGTPrice(((Filter_greater_than_priceCommand) command).getPrice());
                case "update":
                    if (ticketOffice.checkId(((UpdateCommand) command).getTicket().getTicketId())) {
                        if (ticketOffice.checkUser(((UpdateCommand) command).getTicket().getTicketId(), command.getUser())) {
                            ticketOffice.deleteOld(((UpdateCommand) command).getTicket().getTicketId());
                            ticketOffice.updateTicket(((UpdateCommand) command).getTicket(), command.getUser());
                            for (SocketChannel user : activeUsers) {
                                System.out.println(activeUsers.size());
                                responseSender.sendAnswer(new UpdateTicketResponse(((UpdateCommand) command).getTicket()), user);
                                try {
                                    Thread.sleep(50);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            return;
                            // return ticketOffice.updateTicket(((UpdateCommand) command).getTicket(), command.getUser());
                        }// else return new ModificationFailResponse(command.getUser());
                    } //else return "билета с заданным id нет, попробуйте ещё раз";
                case "execute_script":
                    // return "Начато выполнение скрипта";
                case "exit":
                    ClientCommandHandler_LOGGER.info("Подключение с клиентом разорвано");
                    //return "Клиент вышел из программы";
                default:
                    throw new IllegalArgumentException("Unknown command");
            }
        } else if (stringCommand.equals("register")) {
            responseSender.sendAnswer(registerUser(command.getUser()), client);
        } else if (stringCommand.equals("login")) {
            if (checkUser(command.getUser())) {
                responseSender.sendAnswer(new AuthSuccessResponse(command.getUser()), client);
            } else responseSender.sendAnswer(new AuthFailResponse("error"), client);
        }
        throw new IllegalArgumentException("Unknown command");
    }

    private Response registerUser(User user) throws SQLException {
        if (users.containsKey(user.getUsername())) return new AuthFailResponse("Prohibited username");
        else {
            dataBaseConnection.addUser(user);
            users.put(user.getUsername(), user.getPassword());
            return new AuthSuccessResponse(user);
        }
    }

    private boolean checkUser(User user) {
        return users.containsKey(user.getUsername()) && users.get(user.getUsername()).equals(user.getPassword());
    }

    private Ticket ticketSetIds(Ticket ticket) {
        Pair<Integer, Long> maxs = ticketOffice.getMaxs();
        nextTicketId = maxs.getFirst() + 1;
        nextEventId = maxs.getSecond() + 1;
        ticket.setTicketId(nextTicketId);
        if (ticket.getEvent() != null) ticket.getEvent().setEventId(nextEventId);
        return ticket;
    }


    private Boolean addIfMax(Ticket ticket, User user) throws SQLException {
        if (ticket.getPrice() > ticketOffice.getMaxPrice()) {
            ticketOffice.addNewTicket(ticketSetIds(ticket), user);
            return true;
        } else return false;
    }

    private Boolean addIfMin(Ticket ticket, User user) throws SQLException {
        if (ticket.getPrice() < ticketOffice.getMinPrice()) {
            ticketOffice.addNewTicket(ticketSetIds(ticket), user);
            return true;
        } else return false;
    }

    private String createInfo() {
        dateOFCreation = ticketOffice.findCreationDate();
        String collectType = ("Тип коллекции: ArrayDequeue");
        String collectionSize = ("Количество элементов коллекции: " + ticketOffice.getSize());
        String creation;
        if (dateOFCreation == null || ticketOffice.getSize() == 0) {
            creation = "Коллекция ещё не создана";
        } else creation = ("Дата создания колекции: " + dateOFCreation);
        StringBuilder information = new StringBuilder(150);
        return (information.append(collectType).append("\n").append(collectionSize).append("\n").append(creation)).toString();
    }

}
