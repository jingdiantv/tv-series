package rs.ac.ni.pmf.rwa.tvseries.core;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeries;
import rs.ac.ni.pmf.rwa.tvseries.core.model.User;
import rs.ac.ni.pmf.rwa.tvseries.core.model.WatchedTvSeries;
import rs.ac.ni.pmf.rwa.tvseries.core.provider.TvSeriesProvider;
import rs.ac.ni.pmf.rwa.tvseries.core.provider.UserProvider;
import rs.ac.ni.pmf.rwa.tvseries.core.provider.WatchListProvider;
import rs.ac.ni.pmf.rwa.tvseries.core.service.TvSeriesService;
import rs.ac.ni.pmf.rwa.tvseries.core.service.WatchListService;
import rs.ac.ni.pmf.rwa.tvseries.exception.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WatchListServiceIT {

    @Mock
    private WatchListProvider watchListProvider;
    @Mock
    private UserProvider userProvider;
    @Mock
    private TvSeriesProvider tvSeriesProvider;

    @InjectMocks
    private WatchListService watchListService;


    @Test
    public void shouldCreateWatchList() {
        String username = "username";

        User user = mock(User.class);
        when(userProvider.getUserByUsername(username)).thenReturn(Optional.of(user));

        Integer tvSeriesId = 1;

        WatchedTvSeries watchedTvSeries = mock(WatchedTvSeries.class);
        when(watchedTvSeries.getTvSeriesId()).thenReturn(tvSeriesId);
        when(watchedTvSeries.getEpisodesWatched()).thenReturn(5);
        when(watchedTvSeries.getRating()).thenReturn(5);

        when(watchListProvider.getTvSeriesOnWatchListById(username, tvSeriesId)).thenReturn(Optional.empty());

        TvSeries tvSeries = mock(TvSeries.class);
        when(tvSeries.getNumberOfEpisodes()).thenReturn(10);

        when(tvSeriesProvider.getTvSeriesById(tvSeriesId)).thenReturn(Optional.of(tvSeries));


        watchListService.addToWatchList(username, watchedTvSeries);

        verify(watchListProvider).addToWatchList(username, watchedTvSeries);

    }

    @Test
    public void shouldThrowWhenCreateWatchList_ValidateUserFail() {

        String username = "username";


        when(userProvider.getUserByUsername(username)).thenReturn(Optional.empty());

        WatchedTvSeries watchedTvSeries = mock(WatchedTvSeries.class);

        assertThatThrownBy(() -> watchListService.addToWatchList(username, watchedTvSeries))
                .isInstanceOf(UnknownUserException.class);


    }

    @Test
    public void shouldThrowWhenCreateWatchList_TvSeriesAlreadyOnWatchList() {
        String username = "username";

        User user = mock(User.class);
        when(userProvider.getUserByUsername(username)).thenReturn(Optional.of(user));

        Integer tvSeriesId = 1;
        TvSeries tvSeries = mock(TvSeries.class);
        when(watchListProvider.getTvSeriesOnWatchListById(username, tvSeriesId)).thenReturn(Optional.of(tvSeries));

        WatchedTvSeries watchedTvSeries = mock(WatchedTvSeries.class);
        when(watchedTvSeries.getTvSeriesId()).thenReturn(tvSeriesId);


        assertThatThrownBy(() -> watchListService.addToWatchList(username, watchedTvSeries))
                .isInstanceOf(TvSeriesAlreadyOnWatchListException.class);


    }

    @Test
    public void shouldThrowWhenCreateWatchList_ValidateEpisodesNumberFail() {
        String username = "username";

        User user = mock(User.class);
        when(userProvider.getUserByUsername(username)).thenReturn(Optional.of(user));

        Integer tvSeriesId = 1;

        WatchedTvSeries watchedTvSeries = mock(WatchedTvSeries.class);
        when(watchedTvSeries.getTvSeriesId()).thenReturn(tvSeriesId);
        when(watchedTvSeries.getEpisodesWatched()).thenReturn(20);


        when(watchListProvider.getTvSeriesOnWatchListById(username, tvSeriesId)).thenReturn(Optional.empty());

        TvSeries tvSeries = mock(TvSeries.class);
        when(tvSeries.getNumberOfEpisodes()).thenReturn(10);

        when(tvSeriesProvider.getTvSeriesById(tvSeriesId)).thenReturn(Optional.of(tvSeries));


        assertThatThrownBy(() -> watchListService.addToWatchList(username, watchedTvSeries))
                .isInstanceOf(IllegalEpisodesNumberException.class);


    }

    @Test
    public void shouldThrowWhenCreateWatchList_ValidateRatingFail() {
        String username = "username";

        User user = mock(User.class);
        when(userProvider.getUserByUsername(username)).thenReturn(Optional.of(user));

        Integer tvSeriesId = 1;

        WatchedTvSeries watchedTvSeries = mock(WatchedTvSeries.class);
        when(watchedTvSeries.getTvSeriesId()).thenReturn(tvSeriesId);
        when(watchedTvSeries.getEpisodesWatched()).thenReturn(5);
        when(watchedTvSeries.getRating()).thenReturn(20);

        when(watchListProvider.getTvSeriesOnWatchListById(username, tvSeriesId)).thenReturn(Optional.empty());

        TvSeries tvSeries = mock(TvSeries.class);
        when(tvSeries.getNumberOfEpisodes()).thenReturn(10);

        when(tvSeriesProvider.getTvSeriesById(tvSeriesId)).thenReturn(Optional.of(tvSeries));


        assertThatThrownBy(() -> watchListService.addToWatchList(username, watchedTvSeries))
                .isInstanceOf(IllegalRatingException.class);


    }


    @Test
    public void shouldReturnWatchList() {
        String username = "username";
        User user = mock(User.class);
        when(userProvider.getUserByUsername(username)).thenReturn(Optional.of(user));

        List<TvSeries> expectedList = mock(List.class);
        when(watchListProvider.getTvSeriesByUsername(username)).thenReturn(expectedList);

        List<TvSeries> actualList = watchListService.getTvSeriesByUsername(username);

        assertThat(actualList).isEqualTo(expectedList);


    }

    @Test
    public void shouldThrowWhenReturnWatchList() {
        String username = "username";
        when(userProvider.getUserByUsername(username)).thenReturn(Optional.empty());


        assertThatThrownBy(() -> watchListService.getTvSeriesByUsername(username))
                .isInstanceOf(UnknownUserException.class);


    }


    @Test
    public void shouldReturnTvSeriesOnWatchList() {
        String username = "username";
        User user = mock(User.class);
        when(userProvider.getUserByUsername(username)).thenReturn(Optional.of(user));

        Integer tvSeriesId = 1;
        TvSeries expectedTvSeries = mock(TvSeries.class);
        when(watchListProvider.getTvSeriesOnWatchListById(username, tvSeriesId)).thenReturn(Optional.of(expectedTvSeries));

        TvSeries actualTvSeries = watchListService.getTvSeriesOnWatchListById(username, tvSeriesId);

        assertThat(actualTvSeries).isEqualTo(expectedTvSeries);


    }

    @Test
    public void shouldThrowWhenReturnTvSeriesOnWatchList_ValidateUserFail() {
        String username = "username";
        when(userProvider.getUserByUsername(username)).thenReturn(Optional.empty());

        Integer tvSeriesId = 1;

        assertThatThrownBy(() -> watchListService.getTvSeriesOnWatchListById(username, tvSeriesId))
                .isInstanceOf(UnknownUserException.class);

    }

    @Test
    public void shouldThrowWhenReturnTvSeriesOnWatchList_TvSeriesNotOnWatchList() {
        String username = "username";
        User user = mock(User.class);
        when(userProvider.getUserByUsername(username)).thenReturn(Optional.of(user));

        Integer tvSeriesId = 1;

        when(watchListProvider.getTvSeriesOnWatchListById(username, tvSeriesId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> watchListService.getTvSeriesOnWatchListById(username, tvSeriesId))
                .isInstanceOf(TvSeriesNotOnWatchListException.class);


    }


    @Test
    public void shouldUpdateWatchList() {


        String username = "username";

        User user = mock(User.class);
        when(userProvider.getUserByUsername(username)).thenReturn(Optional.of(user));


        Integer tvSeriesId = 1;

        WatchedTvSeries watchedTvSeries = mock(WatchedTvSeries.class);
        when(watchedTvSeries.getTvSeriesId()).thenReturn(tvSeriesId);
        when(watchedTvSeries.getEpisodesWatched()).thenReturn(5);
        when(watchedTvSeries.getRating()).thenReturn(5);

        TvSeries tvSeries = mock(TvSeries.class);
        when(tvSeries.getNumberOfEpisodes()).thenReturn(10);

        when(tvSeriesProvider.getTvSeriesById(tvSeriesId)).thenReturn(Optional.of(tvSeries));

        when(watchListProvider.getTvSeriesOnWatchListById(username, tvSeriesId)).thenReturn(Optional.of(tvSeries));

        watchListService.update(watchedTvSeries, username, tvSeriesId);

        verify(watchListProvider).update(watchedTvSeries, username);


    }

    @Test
    public void shouldThrowWhenUpdateWatchList_IdsDontMatch() {

        String username = "username";


        Integer tvSeriesId = 1;
        WatchedTvSeries watchedTvSeries = mock(WatchedTvSeries.class);
        when(watchedTvSeries.getTvSeriesId()).thenReturn(2);


        assertThatThrownBy(() -> watchListService.update(watchedTvSeries, username, tvSeriesId))
                .isInstanceOf(IdsNotMatchException.class);


    }


    @Test
    public void shouldThrowWhenUpdateWatchList_ValidateUserFail() {

        String username = "username";


        when(userProvider.getUserByUsername(username)).thenReturn(Optional.empty());

        Integer tvSeriesId = 1;
        WatchedTvSeries watchedTvSeries = mock(WatchedTvSeries.class);
        when(watchedTvSeries.getTvSeriesId()).thenReturn(tvSeriesId);


        assertThatThrownBy(() -> watchListService.update(watchedTvSeries, username, tvSeriesId))
                .isInstanceOf(UnknownUserException.class);


    }

    @Test
    public void shouldThrowWhenUpdateWatchList_TvSeriesNotOnWatchList() {

        String username = "username";


        User user = mock(User.class);
        when(userProvider.getUserByUsername(username)).thenReturn(Optional.of(user));

        Integer tvSeriesId = 1;
        WatchedTvSeries watchedTvSeries = mock(WatchedTvSeries.class);
        when(watchedTvSeries.getTvSeriesId()).thenReturn(tvSeriesId);

        when(watchListProvider.getTvSeriesOnWatchListById(username, tvSeriesId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> watchListService.update(watchedTvSeries, username, tvSeriesId))
                .isInstanceOf(TvSeriesNotOnWatchListException.class);


    }


    @Test
    public void shouldThrowWhenUpdateWatchList_ValidateEpisodesNumberFail() {
        String username = "username";

        User user = mock(User.class);
        when(userProvider.getUserByUsername(username)).thenReturn(Optional.of(user));

        Integer tvSeriesId = 1;

        WatchedTvSeries watchedTvSeries = mock(WatchedTvSeries.class);
        when(watchedTvSeries.getTvSeriesId()).thenReturn(tvSeriesId);
        when(watchedTvSeries.getEpisodesWatched()).thenReturn(20);


        TvSeries tvSeries = mock(TvSeries.class);
        when(tvSeries.getNumberOfEpisodes()).thenReturn(10);

        when(tvSeriesProvider.getTvSeriesById(tvSeriesId)).thenReturn(Optional.of(tvSeries));

        when(watchListProvider.getTvSeriesOnWatchListById(username, tvSeriesId)).thenReturn(Optional.of(tvSeries));


        assertThatThrownBy(() -> watchListService.update(watchedTvSeries, username, tvSeriesId))
                .isInstanceOf(IllegalEpisodesNumberException.class);


    }

    @Test
    public void shouldThrowWhenUpdateWatchList_ValidateRatingFail() {
        String username = "username";

        User user = mock(User.class);
        when(userProvider.getUserByUsername(username)).thenReturn(Optional.of(user));

        Integer tvSeriesId = 1;

        WatchedTvSeries watchedTvSeries = mock(WatchedTvSeries.class);
        when(watchedTvSeries.getTvSeriesId()).thenReturn(tvSeriesId);
        when(watchedTvSeries.getEpisodesWatched()).thenReturn(5);
        when(watchedTvSeries.getRating()).thenReturn(20);


        TvSeries tvSeries = mock(TvSeries.class);
        when(tvSeries.getNumberOfEpisodes()).thenReturn(10);

        when(tvSeriesProvider.getTvSeriesById(tvSeriesId)).thenReturn(Optional.of(tvSeries));

        when(watchListProvider.getTvSeriesOnWatchListById(username, tvSeriesId)).thenReturn(Optional.of(tvSeries));


        assertThatThrownBy(() -> watchListService.update(watchedTvSeries, username, tvSeriesId))
                .isInstanceOf(IllegalRatingException.class);


    }


    @Test
    public void shouldDeleteWatchList() {


        String username = "username";

        User user = mock(User.class);
        when(userProvider.getUserByUsername(username)).thenReturn(Optional.of(user));


        Integer tvSeriesId = 1;


        TvSeries tvSeries = mock(TvSeries.class);


        when(watchListProvider.getTvSeriesOnWatchListById(username, tvSeriesId)).thenReturn(Optional.of(tvSeries));

        watchListService.delete(username, tvSeriesId);

        verify(watchListProvider).delete(username, tvSeriesId);


    }

    @Test
    public void shouldThrowWhenDeleteWatchList_ValidateUserFail() {


        String username = "username";

        when(userProvider.getUserByUsername(username)).thenReturn(Optional.empty());


        Integer tvSeriesId = 1;


        assertThatThrownBy(() -> watchListService.delete(username, tvSeriesId))
                .isInstanceOf(UnknownUserException.class);


    }

    @Test
    public void shouldThrowWhenDeleteWatchList_TvSeriesNotOnWatchList() {


        String username = "username";

        User user = mock(User.class);
        when(userProvider.getUserByUsername(username)).thenReturn(Optional.of(user));


        Integer tvSeriesId = 1;


        when(watchListProvider.getTvSeriesOnWatchListById(username, tvSeriesId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> watchListService.delete(username, tvSeriesId))
                .isInstanceOf(TvSeriesNotOnWatchListException.class);


    }


}
