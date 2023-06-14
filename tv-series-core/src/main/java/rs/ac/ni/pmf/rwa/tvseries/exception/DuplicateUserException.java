package rs.ac.ni.pmf.rwa.tvseries.exception;

public class DuplicateUserException extends  RuntimeException{

    public DuplicateUserException(final String username)
    {
        super("Username '" + username + "' already taken!");
    }
}
