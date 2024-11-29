package xo.tictactoe.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotCreatedException extends ResponseStatusException {
        public NotCreatedException(String message){
            super(HttpStatus.UNAUTHORIZED, message);
        }
}
