package rs.ac.ni.pmf.rwa.tvseries.exception;

import lombok.Getter;

public class UnknownUserException extends  RuntimeException {

    @Getter
    private final String username;

    public UnknownUserException(final String username)
    {
        super("Unknown User with username '" + username + "'");
        this.username=username;
    }
}
