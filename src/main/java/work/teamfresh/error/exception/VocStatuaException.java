package work.teamfresh.error.exception;

import work.teamfresh.error.ErrorCode;

public class VocStatuaException extends BusinessException {
    public VocStatuaException() {super(ErrorCode.VOC_STATUS_ERROR);}

    public VocStatuaException(String message) {
        super(message, ErrorCode.VOC_STATUS_ERROR);
    }
}
