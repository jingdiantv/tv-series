package rs.ac.ni.pmf.rwa.tvseries.core.service;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeries;
import rs.ac.ni.pmf.rwa.tvseries.core.model.User;
import rs.ac.ni.pmf.rwa.tvseries.core.model.WatchedTvSeries;
import rs.ac.ni.pmf.rwa.tvseries.core.provider.TvSeriesProvider;
import rs.ac.ni.pmf.rwa.tvseries.core.provider.UserProvider;
import rs.ac.ni.pmf.rwa.tvseries.core.provider.WatchListProvider;
import rs.ac.ni.pmf.rwa.tvseries.exception.*;

import java.util.List;

@RequiredArgsConstructor
public class WatchListService {

    final UserProvider userProvider;
    final TvSeriesProvider tvSeriesProvider;

    final WatchListProvider watchListProvider;

    public  void addToWatchList(String username, WatchedTvSeries watchedTvSeries) {

        validateUser(username);

        Integer tvSeriesId=watchedTvSeries.getTvSeriesId();
        if(watchListProvider.getTvSeriesOnWatchListById(username,tvSeriesId).isPresent()){
            throw new TvSeriesAlreadyOnWatchListException(username,tvSeriesId);
        }

        validateEpisodesNumber(tvSeriesId,watchedTvSeries.getEpisodesWatched());
        validateRating(watchedTvSeries.getRating());

        watchListProvider.addToWatchList(username,watchedTvSeries);


    }

    public List<TvSeries> getTvSeriesByUsername(String username) {

       validateUser(username);

        return watchListProvider.getTvSeriesByUsername(username);
    }

    public TvSeries getTvSeriesOnWatchListById(String username, Integer tvSeriesId) {
        if(userProvider.getUserByUsername(username).isEmpty()){
            throw new UnknownUserException(username);
        }

        return watchListProvider.getTvSeriesOnWatchListById(username,tvSeriesId).orElseThrow( () -> new TvSeriesNotOnWatchListException(username,tvSeriesId));
    }

    public void update(WatchedTvSeries watchedTvSeries, String username, Integer tvSeriesId) {

        if(!tvSeriesId.equals(watchedTvSeries.getTvSeriesId())){
            throw new IdsNotMatchException();
        }
        validateUser(username);

        validateEpisodesNumber(tvSeriesId,watchedTvSeries.getEpisodesWatched());
        validateRating(watchedTvSeries.getRating());

        watchListProvider.update(watchedTvSeries,username);

    }

    public void delete(String username, Integer tvSeriesId) {
        validateUser(username);

        if(watchListProvider.getTvSeriesOnWatchListById(username,tvSeriesId).isEmpty()){
            throw new TvSeriesNotOnWatchListException(username,tvSeriesId);
        }

        watchListProvider.delete(username,tvSeriesId);
    }

    //Functions for validation
    public  void validateEpisodesNumber(Integer tvSeriesId,Integer episodesWatched){
        TvSeries tvSeries= tvSeriesProvider.getTvSeriesById(tvSeriesId).orElseThrow(()->new UnknownTvSeriesException(tvSeriesId));
        Integer maxNumberOfEpisodes=tvSeries.getNumberOfEpisodes();

        if(episodesWatched>maxNumberOfEpisodes||episodesWatched<0){
            throw  new IllegalEpisodesNumberException(episodesWatched);
        }
    }

    public void validateRating(Integer rating){
        if(rating>10||rating<0){
            throw  new IllegalRatingException();
        }
    }

    public void validateUser(String  username){
        if(userProvider.getUserByUsername(username).isEmpty()){
            throw new UnknownUserException(username);
        }
    }
}
