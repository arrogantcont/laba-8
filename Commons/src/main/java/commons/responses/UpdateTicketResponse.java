package commons.responses;

import commons.model.Ticket;
import lombok.Data;
@Data
public class UpdateTicketResponse extends Response {
        private Ticket ticket;

        public UpdateTicketResponse(Ticket ticket) {
            super(ResponseType.UPDATE_TICKET);
            this.ticket = ticket;
        }
}
