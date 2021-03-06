package client.gui;

import java.util.Locale;
import java.util.ResourceBundle;

public class UIMessages {
    public static String fail_message = "Fail";
    public static String creation_date = "Creation date";
    public static String modification_fail = "You can't remove or update others tickets";
    public static String selection_fail = "Please select ticket";
    public static String success_message = "Success";
    public static String success_message_close = "Success close to continue";
    public static String app_name = "TicketOffice V4.0";
    public static String password = "Password";
    public static String login = "Login";
    public static String user = "User";
    public static String register = "register";
    public static String not_matching_pass_message = "Passwords are not equal";
    public static String go_back = "go back";
    public static String null_field_message = "Please fill all the fields";
    public static String add_ticket = "Add ticket";
    public static String ticket_name = "Ticket name";
    public static String x = "x coordinate";
    public static String y = "y coordinate";
    public static String wrong_coords = "Coordinates should be int: X [0;800] Y [0;259]";
    public static String price = "Price";
    public static String wrong_price = "Int price should be >0";
    public static String comment = "Comment";
    public static String wrong_text_fields = "Fill name and comment fields";
    public static String refundable = "Refundable";
    public static String type = "Ticket type";
    public static String event_name = "Event name";
    public static String ticket_count = "Ticket count";
    public static String wrong_ticket_count = "int Ticket count should be >0";
    public static String event_type = "Event type";
    public static String add_event = "Add event";
    public static String event_question = "Do you want to add event?";
    public static String update_by_id = "Update ticket";
    public static String update = "Update";
    public static String remove = "Remove ticket";
    public static String add_if_max = "Add if max";
    public static String add_if_min = "Add if min";
    public static String id = "id";
    public static String help = "Help";
    public static String text_help = "Welcome to ticket office v4!" + "\n" + "To add ticket to collection press *add ticket* and fill all the fields" + "\n" + "You can add Event if you want or add ticket if ticket price is max/min" + "\n" + "To update or remove ticket select YOUR ticket and press exact button" + "\n" + "You can sort your tickets - click on any column header";

    public static void setRu() {
        Locale current = new Locale("ru", "RU");
        ResourceBundle rb = ResourceBundle.getBundle("text", current);
        fail_message = rb.getString("fail_message");
        creation_date = rb.getString("creation_date");
        modification_fail = rb.getString("modification_fail");
        selection_fail = rb.getString("selection_fail");
        success_message = rb.getString("success_message");
        success_message_close = rb.getString("success_message_close");
        password = rb.getString("password");
        login = rb.getString("login");
        user = rb.getString("user");
        register = rb.getString("register");
        not_matching_pass_message = rb.getString("not_matching_pass_message");
        go_back = rb.getString("go_back");
        null_field_message = rb.getString("null_field_message");
        add_ticket = rb.getString("add_ticket");
        ticket_name = rb.getString("ticket_name");
        x = rb.getString("x");
        y = rb.getString("y");
        wrong_coords = rb.getString("wrong_coords");
        price = rb.getString("price");
        wrong_price = rb.getString("wrong_price");
        comment = rb.getString("comment");
        wrong_text_fields = rb.getString("wrong_text_fields");
        refundable = rb.getString("refundable");
        type = rb.getString("type");
        event_name = rb.getString("event_name");
        ticket_count = rb.getString("ticket_count");
        wrong_ticket_count = rb.getString("wrong_ticket_count");
        event_type = rb.getString("event_type");
        add_event = rb.getString("add_event");
        event_question = rb.getString("event_question");
        update_by_id = rb.getString("update_by_id");
        update = rb.getString("update");
        remove = rb.getString("remove");
        add_if_max = rb.getString("add_if_max");
        add_if_min = rb.getString("add_if_min");
        id = rb.getString("id");
        help = rb.getString("help");
        text_help = rb.getString("text_help");
    }

    public static void setEn() {
        Locale current = new Locale("", "");
        ResourceBundle rb = ResourceBundle.getBundle("text", current);
        fail_message = rb.getString("fail_message");
        creation_date = rb.getString("creation_date");
        modification_fail = rb.getString("modification_fail");
        selection_fail = rb.getString("selection_fail");
        success_message = rb.getString("success_message");
        success_message_close = rb.getString("success_message_close");
        password = rb.getString("password");
        login = rb.getString("login");
        user = rb.getString("user");
        register = rb.getString("register");
        not_matching_pass_message = rb.getString("not_matching_pass_message");
        go_back = rb.getString("go_back");
        null_field_message = rb.getString("go_back");
        add_ticket = rb.getString("add_ticket");
        ticket_name = rb.getString("ticket_name");
        x = rb.getString("x");
        y = rb.getString("y");
        wrong_coords = rb.getString("wrong_coords");
        price = rb.getString("price");
        wrong_price = rb.getString("wrong_price");
        comment = rb.getString("comment");
        wrong_text_fields = rb.getString("wrong_text_fields");
        refundable = rb.getString("refundable");
        type = rb.getString("type");
        event_name = rb.getString("event_name");
        ticket_count = rb.getString("ticket_count");
        wrong_ticket_count = rb.getString("wrong_ticket_count");
        event_type = rb.getString("event_type");
        add_event = rb.getString("add_event");
        event_question = rb.getString("event_question");
        update_by_id = rb.getString("update_by_id");
        update = rb.getString("update");
        remove = rb.getString("remove");
        add_if_max = rb.getString("add_if_max");
        add_if_min = rb.getString("add_if_min");
        id = rb.getString("id");
        help = rb.getString("help");
        text_help = rb.getString("text_help");
    }

    public static void setRo() {
        Locale current = new Locale("ro", "RO");
        ResourceBundle rb = ResourceBundle.getBundle("text", current);
        fail_message = rb.getString("fail_message");
        creation_date = rb.getString("creation_date");
        modification_fail = rb.getString("modification_fail");
        selection_fail = rb.getString("selection_fail");
        success_message = rb.getString("success_message");
        success_message_close = rb.getString("success_message_close");
        password = rb.getString("password");
        login = rb.getString("login");
        user = rb.getString("user");
        register = rb.getString("register");
        not_matching_pass_message = rb.getString("not_matching_pass_message");
        go_back = rb.getString("go_back");
        null_field_message = rb.getString("null_field_message");
        add_ticket = rb.getString("add_ticket");
        ticket_name = rb.getString("ticket_name");
        x = rb.getString("x");
        y = rb.getString("y");
        wrong_coords = rb.getString("wrong_coords");
        price = rb.getString("price");
        wrong_price = rb.getString("wrong_price");
        comment = rb.getString("comment");
        wrong_text_fields = rb.getString("wrong_text_fields");
        refundable = rb.getString("refundable");
        type = rb.getString("type");
        event_name = rb.getString("event_name");
        ticket_count = rb.getString("ticket_count");
        wrong_ticket_count = rb.getString("wrong_ticket_count");
        event_type = rb.getString("event_type");
        add_event = rb.getString("add_event");
        event_question = rb.getString("event_question");
        update_by_id = rb.getString("update_by_id");
        update = rb.getString("update");
        remove = rb.getString("remove");
        add_if_max = rb.getString("add_if_max");
        add_if_min = rb.getString("add_if_min");
        id = rb.getString("id");
        help = rb.getString("help");
        text_help = rb.getString("text_help");
    }

    public static void setCo() {
        Locale current = new Locale("es", "CO");
        ResourceBundle rb = ResourceBundle.getBundle("text", current);
        fail_message = rb.getString("fail_message");
        creation_date = rb.getString("creation_date");
        modification_fail = rb.getString("modification_fail");
        selection_fail = rb.getString("selection_fail");
        success_message = rb.getString("success_message");
        success_message_close = rb.getString("success_message_close");
        password = rb.getString("password");
        login = rb.getString("login");
        user = rb.getString("user");
        register = rb.getString("register");
        not_matching_pass_message = rb.getString("not_matching_pass_message");
        go_back = rb.getString("go_back");
        null_field_message = rb.getString("null_field_message");
        add_ticket = rb.getString("add_ticket");
        ticket_name = rb.getString("ticket_name");
        x = rb.getString("x");
        y = rb.getString("y");
        wrong_coords = rb.getString("wrong_coords");
        price = rb.getString("price");
        wrong_price = rb.getString("wrong_price");
        comment = rb.getString("comment");
        wrong_text_fields = rb.getString("wrong_text_fields");
        refundable = rb.getString("refundable");
        type = rb.getString("type");
        event_name = rb.getString("event_name");
        ticket_count = rb.getString("ticket_count");
        wrong_ticket_count = rb.getString("wrong_ticket_count");
        event_type = rb.getString("event_type");
        add_event = rb.getString("add_event");
        event_question = rb.getString("event_question");
        update_by_id = rb.getString("update_by_id");
        update = rb.getString("update");
        remove = rb.getString("remove");
        add_if_max = rb.getString("add_if_max");
        add_if_min = rb.getString("add_if_min");
        id = rb.getString("id");
        help = rb.getString("help");
        text_help = rb.getString("text_help");
    }

    public static void setLv() {
        Locale current = new Locale("lv", "LV");
        ResourceBundle rb = ResourceBundle.getBundle("text", current);
        fail_message = rb.getString("fail_message");
        creation_date = rb.getString("creation_date");
        modification_fail = rb.getString("modification_fail");
        selection_fail = rb.getString("selection_fail");
        success_message = rb.getString("success_message");
        success_message_close = rb.getString("success_message_close");
        password = rb.getString("password");
        login = rb.getString("login");
        user = rb.getString("user");
        register = rb.getString("register");
        not_matching_pass_message = rb.getString("not_matching_pass_message");
        go_back = rb.getString("go_back");
        null_field_message = rb.getString("null_field_message");
        add_ticket = rb.getString("add_ticket");
        ticket_name = rb.getString("ticket_name");
        x = rb.getString("x");
        y = rb.getString("y");
        wrong_coords = rb.getString("wrong_coords");
        price = rb.getString("price");
        wrong_price = rb.getString("wrong_price");
        comment = rb.getString("comment");
        wrong_text_fields = rb.getString("wrong_text_fields");
        refundable = rb.getString("refundable");
        type = rb.getString("type");
        event_name = rb.getString("event_name");
        ticket_count = rb.getString("ticket_count");
        wrong_ticket_count = rb.getString("wrong_ticket_count");
        event_type = rb.getString("event_type");
        add_event = rb.getString("add_event");
        event_question = rb.getString("event_question");
        update_by_id = rb.getString("update_by_id");
        update = rb.getString("update");
        remove = rb.getString("remove");
        add_if_max = rb.getString("add_if_max");
        add_if_min = rb.getString("add_if_min");
        id = rb.getString("id");
        help = rb.getString("help");
        text_help = rb.getString("text_help");
        System.out.println(event_question);
    }
}
