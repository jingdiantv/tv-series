package rs.ac.ni.pmf.rwa.tvseries.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rs.ac.ni.pmf.rwa.tvseries.core.service.WatchListService;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.UserDTO;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.tvseries.TvSeriesDTO;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.WatchedTvSeriesDTO;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.tvseries.TvSeriesWithEpisodesWatchedDTO;
import rs.ac.ni.pmf.rwa.tvseries.rest.mapper.TvSeriesMapper;
import rs.ac.ni.pmf.rwa.tvseries.rest.mapper.UserMapper;
import rs.ac.ni.pmf.rwa.tvseries.rest.mapper.WatchedTvSeriesMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class WatchListRestController {
    private final WatchListService watchListService;
    private final UserMapper userMapper;
    private final TvSeriesMapper tvSeriesMapper;

    private  final WatchedTvSeriesMapper watchedTvSeriesMapper;

    @PostMapping("/{user}/watch-list/")
    @ResponseStatus(HttpStatus.CREATED)
    public void addToWatchList(
            @PathVariable(name = "user")  String username,
            @RequestBody final WatchedTvSeriesDTO watchedTvSeriesDTO
            )
    {

        watchListService.addToWatchList(username,watchedTvSeriesMapper.fromDto(watchedTvSeriesDTO));
    }

    @GetMapping("/{user}/watch-list")
    @ResponseStatus(HttpStatus.OK)
    public List<TvSeriesWithEpisodesWatchedDTO> getWatchList(
            @PathVariable(name = "user")  String username
    )
    {
        return watchListService.getTvSeriesByUsername(username).stream().map(tvSeriesMapper::toDtoWithEpisodesWatched ).collect(Collectors.toList());
    }


    @GetMapping("/{user}/watch-list/{tvSeries}")
    @ResponseStatus(HttpStatus.OK)
    public TvSeriesWithEpisodesWatchedDTO getTvSeriesOnWatchList(
            @PathVariable(name = "user")  String username,
             @PathVariable(name = "tvSeries")  Integer tvSeriesId
    )
    {
        return tvSeriesMapper.toDtoWithEpisodesWatched(watchListService.getTvSeriesOnWatchListById(username,tvSeriesId)) ;
    }

    @PutMapping("/{user}/watch-list/{tvSeries}")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateWatchList(@RequestBody final WatchedTvSeriesDTO watchedTvSeriesDTO,
                           @PathVariable(name = "user")  String username,
                           @PathVariable(name = "tvSeries")  Integer tvSeriesId
    )
    {
        watchListService.update(watchedTvSeriesMapper.fromDto(watchedTvSeriesDTO), username,tvSeriesId);
    }


    @DeleteMapping("/{user}/watch-list/{tvSeries}")
    public void deleteWatchList(
            @PathVariable(name = "user")  String username,
            @PathVariable(name = "tvSeries")  Integer tvSeriesId
    )
    {
        watchListService.delete(username,tvSeriesId);
    }


}
