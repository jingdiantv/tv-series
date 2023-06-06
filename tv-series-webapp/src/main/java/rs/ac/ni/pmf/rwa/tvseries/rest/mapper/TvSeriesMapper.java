package rs.ac.ni.pmf.rwa.tvseries.rest.mapper;

import org.springframework.stereotype.Component;
import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeries;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.TvSeriesDTO;

@Component
public class TvSeriesMapper {
    public TvSeriesDTO toDto(final TvSeries tvSeries)
    {
        return TvSeriesDTO.builder()
                .id(tvSeries.getId())
                .name(tvSeries.getName())
                .numberOfEpisodes(tvSeries.getNumberOfEpisodes())
                .build();
    }

    public TvSeries fromDto(final TvSeriesDTO dto)
    {
        return TvSeries.builder()
                .id(dto.getId())
                .name(dto.getName())
                .numberOfEpisodes(dto.getNumberOfEpisodes())
                .build();
    }
}
