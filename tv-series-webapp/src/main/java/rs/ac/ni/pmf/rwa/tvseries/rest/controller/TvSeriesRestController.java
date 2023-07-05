package rs.ac.ni.pmf.rwa.tvseries.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rs.ac.ni.pmf.rwa.tvseries.core.service.TvSeriesService;
import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeries;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.tvseries.TvSeriesDTO;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.tvseries.TvSeriesSimpleDTO;
import rs.ac.ni.pmf.rwa.tvseries.rest.mapper.TvSeriesMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class TvSeriesRestController {
    private final TvSeriesService tvSeriesService;

    private final TvSeriesMapper tvSeriesMapper;
    @GetMapping("/tv-series")
    public List<TvSeriesDTO> getAllTvSeries()
    {
        return tvSeriesService.getAllTvSeries().stream()
                .map(tvSeriesMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/tv-series/{id}")
    public TvSeriesDTO getTvSeriesById(@PathVariable(name = "id") final Integer id)
    {
        final TvSeries tvSeries = tvSeriesService.getTvSeriesById(id);
        return tvSeriesMapper.toDto(tvSeries);
    }

    @PostMapping("/tv-series")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTvSeries(@RequestBody final TvSeriesSimpleDTO tvSeriesDTO)
    {

        tvSeriesService.createTvSeries(tvSeriesMapper.fromDtoSimple(tvSeriesDTO));
    }

    @PutMapping("/tv-series/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateTvSeries(@RequestBody final TvSeriesSimpleDTO tvSeriesDTO,@PathVariable(value = "id") Integer id)
    {
        tvSeriesService.update(tvSeriesMapper.fromDtoSimple(tvSeriesDTO), id);
    }
    @DeleteMapping("/tv-series/{id}")
    public void deleteTvSeries(@PathVariable(value = "id") Integer id)
    {
        tvSeriesService.delete(id);
    }


}
