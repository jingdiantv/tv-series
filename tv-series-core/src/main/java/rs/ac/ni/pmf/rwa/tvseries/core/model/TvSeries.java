package rs.ac.ni.pmf.rwa.tvseries.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.Value;

@Value
@AllArgsConstructor
@Builder
public class TvSeries {
    Integer id;

    String name;

    Integer numberOfEpisodes;

    Double averageRating;
    Double usersRating;

    Integer episodesWatched;
}
