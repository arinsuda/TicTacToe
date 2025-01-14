package xo.tictactoe.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ItemNotFoundException extends ResponseStatusException {
    public ItemNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}

