package rs.ac.ni.pmf.rwa.tvseries.rest.dto.tvseries;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TvSeriesDTO  {


    Integer id;
    String name;
    Integer numberOfEpisodes;
    Double averageRating;

}
