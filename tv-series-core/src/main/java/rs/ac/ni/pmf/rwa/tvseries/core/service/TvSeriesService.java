package rs.ac.ni.pmf.rwa.tvseries.core.service;

import lombok.RequiredArgsConstructor;
import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeries;
import rs.ac.ni.pmf.rwa.tvseries.core.provider.TvSeriesProvider;
import rs.ac.ni.pmf.rwa.tvseries.exception.DuplicateIdException;
import rs.ac.ni.pmf.rwa.tvseries.exception.UnknownTvSeriesException;

import java.util.List;

@RequiredArgsConstructor
public class TvSeriesService {
    private final TvSeriesProvider tvSeriesProvider;

    public TvSeries getTvSeriesById(Integer id){
        return tvSeriesProvider.getTvSeriesById(id).orElseThrow( () -> new UnknownTvSeriesException(id));
    }

    public List<TvSeries> getAllTvSeries()
    {
        return tvSeriesProvider.getAllTvSeries();
    }

    public void createTvSeries(final TvSeries tvSeries)
    {
        final Integer id = tvSeries.getId();


        if (id!=null && tvSeriesProvider.getTvSeriesById(id).isPresent())
        {
            throw new DuplicateIdException(id);
        }

        final Integer numberOfEpisodes=tvSeries.getNumberOfEpisodes();
        if(numberOfEpisodes<=0){
            throw new IllegalArgumentException("numberOfEpisodes must be positive number");
        }


        tvSeriesProvider.saveTvSeries(tvSeries);
    }

    public void update(final TvSeries tvSeries, final Integer id){
        final Integer newId = tvSeries.getId();
        if(!id.equals(newId)){
            throw new IllegalArgumentException("Id of tv series cannot be changed");
        }
        if(tvSeriesProvider.getTvSeriesById(id).isEmpty()){
            throw new UnknownTvSeriesException(id);
        }
        tvSeriesProvider.updateTvSeries(tvSeries);

    }
    public void delete(final Integer id){
        if(tvSeriesProvider.getTvSeriesById(id).isEmpty()){
            throw new UnknownTvSeriesException(id);
        }
        tvSeriesProvider.removeTvSeries(id);

    }

}
