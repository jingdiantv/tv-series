package rs.ac.ni.pmf.rwa.tvseries.core.provider;

import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeries;
import rs.ac.ni.pmf.rwa.tvseries.core.model.WatchedTvSeries;

import java.util.List;
import java.util.Optional;

public interface WatchListProvider {

    void addToWatchList(final String username, final WatchedTvSeries watchedTvSeries);

     List<TvSeries> getTvSeriesByUsername(String username);

     Double getAverageRating(Integer tvSeriesId);

    Optional<TvSeries> getTvSeriesOnWatchListById(String username, Integer tvSeriesId);

    void update(WatchedTvSeries fromDto, String username);

    void delete(String username, Integer tvSeriesId);
}
