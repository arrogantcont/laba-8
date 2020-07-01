package commons.responses;

import commons.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AuthSuccessResponse extends Response {
    private final User user;

    public AuthSuccessResponse(User user) {
        super(ResponseType.AUTH_SUCCESS);
        this.user = user;
    }
}
