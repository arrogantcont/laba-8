package commons.responses;

import commons.User;
import commons.model.Ticket;
import lombok.Data;

@Data
public class AddTicketResponse extends Response{
    private Ticket ticket;

    public AddTicketResponse(Ticket ticket) {
        super(ResponseType.ADD_TICKET);
        this.ticket = ticket;
    }
}