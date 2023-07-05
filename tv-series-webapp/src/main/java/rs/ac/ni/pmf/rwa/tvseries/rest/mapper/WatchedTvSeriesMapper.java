package rs.ac.ni.pmf.rwa.tvseries.rest.mapper;

import org.springframework.stereotype.Component;
import rs.ac.ni.pmf.rwa.tvseries.core.model.User;
import rs.ac.ni.pmf.rwa.tvseries.core.model.WatchedTvSeries;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.UserDTO;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.WatchedTvSeriesDTO;

@Component
public class WatchedTvSeriesMapper {

    public WatchedTvSeriesDTO toDto(final WatchedTvSeries watchedTvSeries)
    {
        return WatchedTvSeriesDTO.builder()
                .tvSeriesId(watchedTvSeries.getTvSeriesId())
                .rating(watchedTvSeries.getRating())
                .episodesWatched(watchedTvSeries.getEpisodesWatched())
                .build();
    }

    public WatchedTvSeries fromDto(final WatchedTvSeriesDTO dto)
    {
        return WatchedTvSeries.builder()
                .tvSeriesId(dto.getTvSeriesId())
                .rating(dto.getRating())
                .episodesWatched(dto.getEpisodesWatched())
                .build();
    }
}
