package work.teamfresh.error.exception;

import work.teamfresh.error.ErrorCode;

public class ObjectNotFoundException extends BusinessException {
    public ObjectNotFoundException() {super(ErrorCode.OBJECT_NOT_FOUND);}

    public ObjectNotFoundException(String message) {
        super(message, ErrorCode.OBJECT_NOT_FOUND);
    }
}
