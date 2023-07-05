package rs.ac.ni.pmf.rwa.tvseries.exception;

public class IllegalEpisodesNumberException extends RuntimeException{
    public IllegalEpisodesNumberException( final Integer episodesWatched)
    {
        super("Number of episodes watched ["+episodesWatched+"] can't be less than 0 or greater than number of episodes TvSeries have");
    }
}
