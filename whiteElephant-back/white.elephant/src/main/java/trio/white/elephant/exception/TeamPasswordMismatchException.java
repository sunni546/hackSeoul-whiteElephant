package trio.white.elephant.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TeamPasswordMismatchException extends RuntimeException {

    public TeamPasswordMismatchException(String message){
        super(message);
        log.error(message);
    }
}
