package rs.ac.ni.pmf.rwa.tvseries.core;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import rs.ac.ni.pmf.rwa.tvseries.core.TvSeriesProvider;
import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeries;
import rs.ac.ni.pmf.rwa.tvseries.exception.DuplicateIdException;
import rs.ac.ni.pmf.rwa.tvseries.exception.UnknownTvSeriesException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TvSeriesServiceIT {
    @Mock
    private TvSeriesProvider tvSeriesProvider;

    @InjectMocks
    private TvSeriesService tvSeriesService;


    @Test
    public void shouldGetTvSeriesById( ){
        final TvSeries expectedTvSeries = mock(TvSeries.class);
        when(tvSeriesProvider.getTvSeriesById(1))
                .thenReturn(Optional.of(expectedTvSeries));

        final TvSeries actualTvSeries = tvSeriesService.getTvSeriesById(1);

        assertThat(actualTvSeries).isEqualTo(expectedTvSeries);

    }
    @Test
    public void shouldThrowWhenGetTvSeriesById( ){
     when(tvSeriesProvider.getTvSeriesById(1)).thenReturn(Optional.empty());

     assertThatThrownBy(() ->tvSeriesService.getTvSeriesById(1) )
             .hasMessage("Unknown Tv Series for id '1'")
             .isInstanceOf(UnknownTvSeriesException.class);

    }

    @Test
    public void shouldGetAllTvSeries( ){
        final List<TvSeries> expectedTvSeries = mock(List.class);
        when(tvSeriesProvider.getAllTvSeries())
                .thenReturn(expectedTvSeries);

        final List<TvSeries> actualTvSeries = tvSeriesService.getAllTvSeries();

        assertThat(actualTvSeries).isEqualTo(expectedTvSeries);

    }

    @Test
    public void shouldCreateTvSeries(){
        final Integer id=1;

        final TvSeries tvSeries=mock(TvSeries.class);
        when(tvSeries.getId()).thenReturn(id);
        when(tvSeries.getNumberOfEpisodes()).thenReturn(10);
        when(tvSeriesProvider.getTvSeriesById(id)).thenReturn(Optional.empty());

        tvSeriesService.createTvSeries(tvSeries);

        verify(tvSeriesProvider).saveTvSeries(tvSeries);

        //when passed tv series id is null
        final Integer id2=null;

        final TvSeries tvSeries2=mock(TvSeries.class);
        when(tvSeries2.getId()).thenReturn(1);
        when(tvSeries2.getNumberOfEpisodes()).thenReturn(10);
        when(tvSeriesProvider.getTvSeriesById(id)).thenReturn(Optional.empty());

        tvSeriesService.createTvSeries(tvSeries2);

        verify(tvSeriesProvider).saveTvSeries(tvSeries2);

    }

    @Test
    public void shouldThrowWhenCreateTvSeries(){

        //DUPLICATE ID
        final Integer id=1;

        final TvSeries tvSeries=mock(TvSeries.class);
        when(tvSeries.getId()).thenReturn(id);
        when(tvSeriesProvider.getTvSeriesById(id)).thenReturn(Optional.of(tvSeries));

        assertThatThrownBy(() -> tvSeriesService.createTvSeries(tvSeries))
                .isInstanceOf(DuplicateIdException.class)
                .hasMessage("Id '1' already exists");

        //BAD NUMBER OF EPISODES

        final Integer id1=1;

        final TvSeries tvSeries1=mock(TvSeries.class);

        when(tvSeries1.getId()).thenReturn(id);
        when(tvSeries1.getNumberOfEpisodes()).thenReturn(0);

        when(tvSeriesProvider.getTvSeriesById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tvSeriesService.createTvSeries(tvSeries1))
                .isInstanceOf(IllegalArgumentException.class);

        final Integer id2=1;

        final TvSeries tvSeries2=mock(TvSeries.class);

        when(tvSeries2.getId()).thenReturn(id);
        when(tvSeries2.getNumberOfEpisodes()).thenReturn(-10);

        when(tvSeriesProvider.getTvSeriesById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tvSeriesService.createTvSeries(tvSeries2))
                .isInstanceOf(IllegalArgumentException.class);


    }

    @Test
    public void shouldUpdateTvSeries(){
        final Integer id=1;
        final TvSeries tvSeries=mock(TvSeries.class);
        when(tvSeries.getId()).thenReturn(id);
        when(tvSeriesProvider.getTvSeriesById(id)).thenReturn(Optional.of(tvSeries));

        tvSeriesService.update(tvSeries,id);
        verify(tvSeriesProvider).updateTvSeries(tvSeries);
    }

    @Test
    public void shouldThrowWhenUpdateTvSeries(){
        final Integer id=1;
        final TvSeries tvSeries=mock(TvSeries.class);
        when(tvSeries.getId()).thenReturn(2);

        assertThatThrownBy(() -> tvSeriesService.update(tvSeries,id))
                .isInstanceOf(IllegalArgumentException.class);

        final Integer id2=1;
        final TvSeries tvSeries2=mock(TvSeries.class);
        when(tvSeries2.getId()).thenReturn(id);
        when(tvSeriesProvider.getTvSeriesById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tvSeriesService.update(tvSeries2,id2))
                .isInstanceOf(UnknownTvSeriesException.class);
    }

    @Test
    public void shouldDeleteTvSeries(){
        final Integer id=1;
        final TvSeries tvSeries=mock(TvSeries.class);
        when(tvSeriesProvider.getTvSeriesById(id)).thenReturn(Optional.of(tvSeries));

        tvSeriesService.delete(id);
        verify(tvSeriesProvider).removeTvSeries(id);
    }

    @Test
    public void shouldThrowWhenDeleteTvSeries(){
        final Integer id=1;
        final TvSeries tvSeries=mock(TvSeries.class);
        when(tvSeriesProvider.getTvSeriesById(id)).thenReturn(Optional.empty());

       assertThatThrownBy(() -> tvSeriesService.delete(id))
               .isInstanceOf(UnknownTvSeriesException.class);
    }



}
