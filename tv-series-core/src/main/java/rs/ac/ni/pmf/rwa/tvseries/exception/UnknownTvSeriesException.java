package rs.ac.ni.pmf.rwa.tvseries.exception;

import lombok.Getter;

public class UnknownTvSeriesException extends RuntimeException
{
    @Getter
    private final Integer id;

    public UnknownTvSeriesException(final Integer id)
    {
        super("Unknown Tv Series for id '" + id + "'");
        this.id = id;
    }
}
