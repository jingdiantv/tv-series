package rs.ac.ni.pmf.rwa.tvseries.rest.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class WatchedTvSeriesDTO {
    Integer tvSeriesId;
    Integer rating;
    Integer episodesWatched;
}
