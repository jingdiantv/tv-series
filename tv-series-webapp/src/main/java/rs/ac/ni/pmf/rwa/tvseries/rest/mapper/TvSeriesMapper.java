package rs.ac.ni.pmf.rwa.tvseries.rest.mapper;

import org.springframework.stereotype.Component;
import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeries;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.tvseries.TvSeriesDTO;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.tvseries.TvSeriesSimpleDTO;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.tvseries.TvSeriesWithEpisodesWatchedDTO;

@Component
public class TvSeriesMapper {
    public TvSeriesDTO toDto(final TvSeries tvSeries)
    {
        return TvSeriesDTO.builder()
                .id(tvSeries.getId())
                .name(tvSeries.getName())
                .numberOfEpisodes(tvSeries.getNumberOfEpisodes())
                .rating(tvSeries.getRating())
                .build();
    }

    public TvSeries fromDto(final TvSeriesDTO dto)
    {
        return TvSeries.builder()
                .id(dto.getId())
                .name(dto.getName())
                .numberOfEpisodes(dto.getNumberOfEpisodes())
                .rating(dto.getRating())
                .build();
    }

    public TvSeriesSimpleDTO toDtoSimple(final TvSeries tvSeries)
    {
        return TvSeriesSimpleDTO.builder()
                .id(tvSeries.getId())
                .name(tvSeries.getName())
                .numberOfEpisodes(tvSeries.getNumberOfEpisodes())
                .build();
    }

    public TvSeries fromDtoSimple(final TvSeriesSimpleDTO dto)
    {
        return TvSeries.builder()
                .id(dto.getId())
                .name(dto.getName())
                .numberOfEpisodes(dto.getNumberOfEpisodes())
                .build();
    }


    public TvSeriesWithEpisodesWatchedDTO toDtoWithEpisodesWatched(final TvSeries tvSeries)
    {
        return TvSeriesWithEpisodesWatchedDTO.builder()
                .id(tvSeries.getId())
                .name(tvSeries.getName())
                .numberOfEpisodes(tvSeries.getNumberOfEpisodes())
                .rating(tvSeries.getRating())
                .episodesWatched(tvSeries.getEpisodesWatched())
                .build();
    }

    public TvSeries fromDtoWithEpisodesWatched(final TvSeriesWithEpisodesWatchedDTO dto)
    {
        return TvSeries.builder()
                .id(dto.getId())
                .name(dto.getName())
                .numberOfEpisodes(dto.getNumberOfEpisodes())
                .rating(dto.getRating())
                .episodesWatched(dto.getEpisodesWatched())
                .build();
    }



}
