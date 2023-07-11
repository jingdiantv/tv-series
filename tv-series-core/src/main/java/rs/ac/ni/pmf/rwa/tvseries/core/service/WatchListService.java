package rs.ac.ni.pmf.rwa.tvseries.core.service;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeries;
import rs.ac.ni.pmf.rwa.tvseries.core.model.User;
import rs.ac.ni.pmf.rwa.tvseries.core.model.WatchedTvSeries;
import rs.ac.ni.pmf.rwa.tvseries.core.provider.TvSeriesProvider;
import rs.ac.ni.pmf.rwa.tvseries.core.provider.UserProvider;
import rs.ac.ni.pmf.rwa.tvseries.core.provider.WatchListProvider;
import rs.ac.ni.pmf.rwa.tvseries.exception.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class WatchListService {

    final UserProvider userProvider;
    final TvSeriesProvider tvSeriesProvider;

    final WatchListProvider watchListProvider;

    public void addToWatchList(String username, WatchedTvSeries watchedTvSeries) {

        validateUser(username);

        Integer tvSeriesId = watchedTvSeries.getTvSeriesId();
        if (watchListProvider.getTvSeriesOnWatchListById(username, tvSeriesId).isPresent()) {
            log.warn("Error Adding TvSeries to Watch List : User [{}] already added TvSeries with id[{}] on his Watch List ",username,tvSeriesId);
            throw new TvSeriesAlreadyOnWatchListException(username, tvSeriesId);

        }

        validateEpisodesNumber(tvSeriesId, watchedTvSeries.getEpisodesWatched());
        validateRating(watchedTvSeries.getRating());

        log.info("Tv Series with id[{}] successfully added to user[{}] Watch List  ",tvSeriesId,username);
        watchListProvider.addToWatchList(username, watchedTvSeries);
    }

    public List<TvSeries> getTvSeriesByUsername(String username) {

        validateUser(username);

        log.info("Getting all tvSeries on User[{}] watchlist ",username);
        return watchListProvider.getTvSeriesByUsername(username);
    }

    public TvSeries getTvSeriesOnWatchListById(String username, Integer tvSeriesId) {
        validateUser(username);

        log.info("Getting tvSeries  with id[{}] that's on User[{}] watchlist ",tvSeriesId,username);
        return watchListProvider.getTvSeriesOnWatchListById(username, tvSeriesId).orElseThrow(() -> new TvSeriesNotOnWatchListException(username, tvSeriesId));
    }

    public void update(WatchedTvSeries watchedTvSeries, String username, Integer tvSeriesId) {

        if (!tvSeriesId.equals(watchedTvSeries.getTvSeriesId())) {
            log.warn("Error Updating TvSeries with id[{}] on Users[{}] to Watch List : ids of tv series dose not match({}!={}) ",tvSeriesId,username,tvSeriesId,watchedTvSeries.getTvSeriesId());
            throw new IdsNotMatchException();
        }
        validateUser(username);

        if (watchListProvider.getTvSeriesOnWatchListById(username, tvSeriesId).isEmpty()) {
            log.warn("Error Updating TvSeries with id[{}] on Users[{}] to Watch List : Tv Series not on users Watch List ",tvSeriesId,username);
            throw new TvSeriesNotOnWatchListException(username, tvSeriesId);
        }

        validateEpisodesNumber(tvSeriesId, watchedTvSeries.getEpisodesWatched());
        validateRating(watchedTvSeries.getRating());

        log.info("Updating Users[{}] watchlist info about Tv Series with id[{}] ",username,tvSeriesId);
        watchListProvider.update(watchedTvSeries, username);
    }

    public void delete(String username, Integer tvSeriesId) {
        validateUser(username);

        if (watchListProvider.getTvSeriesOnWatchListById(username, tvSeriesId).isEmpty()) {
            log.warn("Error Updating TvSeries with id[{}] on Users[{}] to Watch List : Tv Series not on users Watch List ",tvSeriesId,username);
            throw new TvSeriesNotOnWatchListException(username, tvSeriesId);
        }

        watchListProvider.delete(username, tvSeriesId);
    }

    //Functions for validation
    public void validateEpisodesNumber(Integer tvSeriesId, Integer episodesWatched) {
        TvSeries tvSeries = tvSeriesProvider.getTvSeriesById(tvSeriesId).orElseThrow(() -> new UnknownTvSeriesException(tvSeriesId));
        Integer maxNumberOfEpisodes = tvSeries.getNumberOfEpisodes();

        if (episodesWatched > maxNumberOfEpisodes || episodesWatched < 0) {
            log.warn("Error validating Episodes number : Episodes watched({}) is not withing boundaries(0-{})",episodesWatched,maxNumberOfEpisodes);
            throw new IllegalEpisodesNumberException(episodesWatched);
        }
    }

    public void validateRating(Integer rating) {
        if (rating > 10 || rating < 0) {
            log.warn("Error validating rating :Rating [{}] is out of bounds",rating);
            throw new IllegalRatingException();
        }
    }

    public void validateUser(String username) {
        if (userProvider.getUserByUsername(username).isEmpty()) {
            log.warn("Error validating User : User with username[{}] dose not exists",username);
            throw new UnknownUserException(username);
        }
    }
}
