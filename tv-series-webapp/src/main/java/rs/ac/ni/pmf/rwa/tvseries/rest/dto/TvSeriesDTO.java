package rs.ac.ni.pmf.rwa.tvseries.rest.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TvSeriesDTO {
    Integer id;
    String name;
    Integer numberOfEpisodes;
}
