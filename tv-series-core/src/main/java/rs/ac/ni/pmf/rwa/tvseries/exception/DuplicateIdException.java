package rs.ac.ni.pmf.rwa.tvseries.exception;

public class DuplicateIdException extends RuntimeException
{
	public DuplicateIdException(final Integer id)
	{
		super("Id '" + id + "' already exists");
	}
}
