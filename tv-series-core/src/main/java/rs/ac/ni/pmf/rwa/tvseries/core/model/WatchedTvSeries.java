package rs.ac.ni.pmf.rwa.tvseries.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor
@Builder
public class WatchedTvSeries {

    Integer tvSeriesId;
    Integer rating;
    Integer episodesWatched;
}
