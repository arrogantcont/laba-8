package commons.responses;


import lombok.Data;

@Data
public class AuthFailResponse extends Response{
    private final String error;

    public AuthFailResponse(String error) {
        super(ResponseType.AUTH_FAIL);
        this.error = error;
    }
}
