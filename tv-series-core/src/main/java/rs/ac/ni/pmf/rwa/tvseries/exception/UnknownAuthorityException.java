package rs.ac.ni.pmf.rwa.tvseries.exception;

import rs.ac.ni.pmf.rwa.tvseries.shared.Roles;

public class UnknownAuthorityException extends  RuntimeException {
    public UnknownAuthorityException(final Roles authority )
    {
        super("Invalid authority '"+authority+"'");
    }
}
