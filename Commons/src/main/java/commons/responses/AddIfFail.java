package commons.responses;

import lombok.Data;

@Data
public class AddIfFail extends Response{
    private final String error;

    public AddIfFail(String error) {
        super(ResponseType.ADD_IF_FAIL);
        this.error = error;
    }
}
