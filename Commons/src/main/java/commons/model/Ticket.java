package commons.model;

import commons.User;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Comparator;

@Builder
@Data
/**
 * Сам класс билета
 */
public class Ticket implements Comparable<Ticket>, Serializable {
    private static int nextid;
    private int ticketId; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int price; //Значение поля должно быть больше 0
    private String comment; //Поле не может быть null
    private boolean refundable;
    private TicketType type; //Поле может быть null
    private Event event; //Поле может быть null
    private User user;

    //    @Override
//    public int compareTo(Ticket ticket) {
//        return price - ticket.price;
//    }
    public static Comparator<Ticket> ticketPriceComporator = (ticket, t1) -> {
        int price1 = ticket.getPrice();
        int price2 = t1.getPrice();
        return price1 - price2;
    };

    public static Comparator<Ticket> ticketPriceComporatorRev = (ticket, t1) -> {
        int price1 = ticket.getPrice();
        int price2 = t1.getPrice();
        return price2 - price1;
    };
    public static Comparator<Ticket> ticketIdComporator = (ticket, t1) -> {
        int id = ticket.getTicketId();
        int id1 = t1.getTicketId();
        return id - id1;
    };

    public static Comparator<Ticket> ticketIdComporatorRev = (ticket, t1) -> {
        int id = ticket.getTicketId();
        int id1 = t1.getTicketId();
        return id1 - id;
    };
    @Override
    public int compareTo(Ticket ticket) {
        int comparePrice = ticket.getPrice();
        /* For Ascending order*/
        return this.price - comparePrice;

        /* For Descending order do like this */
        //return compareage-this.studentage;
    }

    public static Comparator<Ticket> ticketNameComparator = (s1, s2) -> {
        String name1 = s1.getName().toUpperCase();
        String name2 = s2.getName().toUpperCase();

        //ascending order
        return name1.compareTo(name2);

        //descending order
        //return StudentName2.compareTo(StudentName1);
    };
    public static Comparator<Ticket> ticketNameComparatorRev = (s1, s2) -> {
        String name1 = s1.getName().toUpperCase();
        String name2 = s2.getName().toUpperCase();

        return name2.compareTo(name1);
    };
}
