package work.teamfresh.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Getter
@NoArgsConstructor
public class ErrorResponse {

    private String message;
    private int status;
    private String code;

    private ErrorResponse(final ErrorCode code, final String...  message) {
        this.message = code.getMessage() + " " + Arrays.toString(message);
        this.status = code.getStatus();
        this.code = code.getCode();
    }

    public static ErrorResponse of(final ErrorCode code, final String... message) {
        return new ErrorResponse(code, message);
    }
 }
