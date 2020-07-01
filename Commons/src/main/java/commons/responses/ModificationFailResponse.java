package commons.responses;

import commons.User;

public class ModificationFailResponse extends Response {
    private final User user;

    public ModificationFailResponse(User user) {
        super(ResponseType.AUTH_SUCCESS);
        this.user = user;
    }
}
