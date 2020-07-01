package commons.responses;

import commons.User;
import lombok.Getter;

@Getter
public class TicketRemoveResponse extends Response{
    private final User user;
    private int id;
    public TicketRemoveResponse(User user, int id) {
        super(ResponseType.TICKET_REMOVE);
        this.user = user;
        this.id = id;
    }
}