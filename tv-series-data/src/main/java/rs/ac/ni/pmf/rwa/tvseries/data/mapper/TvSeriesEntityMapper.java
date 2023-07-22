package rs.ac.ni.pmf.rwa.tvseries.data.mapper;

import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeries;
import rs.ac.ni.pmf.rwa.tvseries.core.model.WatchedTvSeries;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.TvSeriesEntity;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.WatchListEntity;

public class TvSeriesEntityMapper {

    public static TvSeries fromEntity(final TvSeriesEntity entity)
    {
        Double averageRating= entity.getUsersWatched().stream()
                .mapToInt( WatchListEntity::getRating)
                .average()
                .orElse(0.0d);

        return TvSeries.builder()
                .id(entity.getId())
                .name(entity.getName())
                .numberOfEpisodes(entity.getNumberOfEpisodes())
                .averageRating(averageRating)
                .build();
    }

    public static TvSeries fromWatchListEntity(final WatchListEntity watchListEntity)
    {
        return TvSeries.builder()
                .id(watchListEntity.getTvSeries().getId())
                .name(watchListEntity.getTvSeries().getName())
                .numberOfEpisodes(watchListEntity.getTvSeries().getNumberOfEpisodes())
                .usersRating(Double.valueOf(watchListEntity.getRating()) )
                .episodesWatched(watchListEntity.getEpisodesWatched())
                .build();
    }

    public static TvSeries fromEntityWithRating(final TvSeriesEntity entity,Double usersRating)
    {
        Double averageRating= entity.getUsersWatched().stream()
                .mapToInt( WatchListEntity::getRating)
                .average()
                .orElse(0.0d);

        return TvSeries.builder()
                .id(entity.getId())
                .name(entity.getName())
                .numberOfEpisodes(entity.getNumberOfEpisodes())
                .averageRating(averageRating)
                .usersRating(usersRating)
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
