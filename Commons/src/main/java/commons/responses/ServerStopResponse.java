package commons.responses;

import commons.User;

public class ServerStopResponse extends Response{
    private final User user;

    public ServerStopResponse(User user) {
        super(ResponseType.SERVER_STOP);
        this.user = user;
    }
}