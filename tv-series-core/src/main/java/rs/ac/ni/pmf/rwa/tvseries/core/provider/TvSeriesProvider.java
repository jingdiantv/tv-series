package rs.ac.ni.pmf.rwa.tvseries.core.provider;

import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeries;

import java.util.List;
import java.util.Optional;

public interface TvSeriesProvider {
    Optional<TvSeries> getTvSeriesById(final Integer id);

    List<TvSeries> getAllTvSeries(String searchKey,int pageNumber);

    void saveTvSeries(final TvSeries tvSeries);

    void removeTvSeries(final Integer id);

    void updateTvSeries(TvSeries tvSeries);
}
