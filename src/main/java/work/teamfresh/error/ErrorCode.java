package work.teamfresh.error;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // Common
    INTERNAL_SERVER_ERROR(500, "E001", "Server Error"),
    VOC_STATUS_ERROR(400, "E002", "VOC Status Error"),
    OBJECT_NOT_FOUND(400, "E003", "OBJECT Not Found");

    ;

    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }


}



