package rs.ac.ni.pmf.rwa.tvseries.rest.dto.tvseries;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TvSeriesWatchedDTO {

    Integer id;
    String name;
    Integer numberOfEpisodes;
    Double usersRating;
    Integer episodesWatched;
}
