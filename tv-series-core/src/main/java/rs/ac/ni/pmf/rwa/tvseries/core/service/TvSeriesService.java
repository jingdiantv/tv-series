package rs.ac.ni.pmf.rwa.tvseries.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeries;
import rs.ac.ni.pmf.rwa.tvseries.core.provider.TvSeriesProvider;
import rs.ac.ni.pmf.rwa.tvseries.exception.DuplicateIdException;
import rs.ac.ni.pmf.rwa.tvseries.exception.UnknownTvSeriesException;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class TvSeriesService {
    private final TvSeriesProvider tvSeriesProvider;

    public TvSeries getTvSeriesById(Integer id){
        log.info("Getting TvSeries with id [{}] .",id);
        return tvSeriesProvider.getTvSeriesById(id).orElseThrow( () -> new UnknownTvSeriesException(id));
    }

    public List<TvSeries> getAllTvSeries()
    {

        log.info("Getting all TvSeries.");
        return tvSeriesProvider.getAllTvSeries();
    }

    public void createTvSeries(final TvSeries tvSeries)
    {
        final Integer id = tvSeries.getId();


        if (id!=null && tvSeriesProvider.getTvSeriesById(id).isPresent())
        {
            log.warn("Error creating Tv Series: Id for TvSeries [{}] already taken",id);
            throw new DuplicateIdException(id);
        }

        final Integer numberOfEpisodes=tvSeries.getNumberOfEpisodes();
        if(numberOfEpisodes<=0){
            log.warn("Error creating Tv Series: number of episodes is illegal argument ({})",numberOfEpisodes);
            throw new IllegalArgumentException("numberOfEpisodes must be positive number");
        }

        log.info("Creating TvSeries");
        tvSeriesProvider.saveTvSeries(tvSeries);
    }

    public void update(final TvSeries tvSeries, final Integer id){
        final Integer newId = tvSeries.getId();
        if(!id.equals(newId)){
            log.warn("Error updating Tv Series: ids doesnt match ({}!={})",id,newId);
            throw new IllegalArgumentException("Id of tv series cannot be changed");
        }
        if(tvSeriesProvider.getTvSeriesById(id).isEmpty()){
            log.warn("Error updating Tv Series: TvSeries with id[{}] cant be found",id);
            throw new UnknownTvSeriesException(id);
        }

        log.info("Updating Tv Series with id[{}]",id);
        tvSeriesProvider.updateTvSeries(tvSeries);

    }
    public void delete(final Integer id){
        if(tvSeriesProvider.getTvSeriesById(id).isEmpty()){
            log.warn("Error deleting Tv Series: TvSeries with id[{}] cant be found",id);
            throw new UnknownTvSeriesException(id);
        }
        log.info("Updating Tv Series with id[{}]",id);
        tvSeriesProvider.removeTvSeries(id);

    }

}
