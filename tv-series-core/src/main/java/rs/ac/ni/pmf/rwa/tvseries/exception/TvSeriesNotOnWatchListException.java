package rs.ac.ni.pmf.rwa.tvseries.exception;

public class TvSeriesNotOnWatchListException extends  RuntimeException{
    public TvSeriesNotOnWatchListException(final String username , final Integer tvSeriesId)
    {
        super("Tv Series with id ["+tvSeriesId+"] is not on "+username+"'s watch list!");
    }
}
