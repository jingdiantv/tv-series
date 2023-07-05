package rs.ac.ni.pmf.rwa.tvseries.data.mapper;

import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeries;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.TvSeriesEntity;

public class TvSeriesEntityMapper {

    public static TvSeries fromEntity(final TvSeriesEntity entity)
    {
        return TvSeries.builder()
                .id(entity.getId())
                .name(entity.getName())
                .numberOfEpisodes(entity.getNumberOfEpisodes())
                .build();
    }

    public static TvSeries fromEntityWithRating(final TvSeriesEntity entity,Double rating)
    {
        return TvSeries.builder()
                .id(entity.getId())
                .name(entity.getName())
                .numberOfEpisodes(entity.getNumberOfEpisodes())
                .rating(rating)
                .build();
    }

    public static TvSeriesEntity toEntity(final TvSeries tvSeries)
    {
        return TvSeriesEntity.builder()
                .id(tvSeries.getId())
                .name(tvSeries.getName())
                .numberOfEpisodes(tvSeries.getNumberOfEpisodes())
                .build();
    }
}
