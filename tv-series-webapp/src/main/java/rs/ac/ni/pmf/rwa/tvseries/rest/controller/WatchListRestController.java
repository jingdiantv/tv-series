package rs.ac.ni.pmf.rwa.tvseries.rest.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.ni.pmf.rwa.tvseries.core.service.WatchListService;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.WatchedTvSeriesDTO;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.tvseries.TvSeriesWatchedDTO;
import rs.ac.ni.pmf.rwa.tvseries.rest.mapper.TvSeriesMapper;
import rs.ac.ni.pmf.rwa.tvseries.rest.mapper.UserMapper;
import rs.ac.ni.pmf.rwa.tvseries.rest.mapper.WatchedTvSeriesMapper;

import java.util.List;
import java.util.stream.Collectors;

@SecurityRequirement(name = "default")
@RestController
@RequiredArgsConstructor
public class WatchListRestController {
    private final WatchListService watchListService;
    private final UserMapper userMapper;
    private final TvSeriesMapper tvSeriesMapper;

    private  final WatchedTvSeriesMapper watchedTvSeriesMapper;

    @PreAuthorize("#username == authentication.name")
    @PostMapping("/{username}/watch-list")
    @ResponseStatus(HttpStatus.CREATED)
    public void addToWatchList(
            @PathVariable(name = "username")  String username,
            @RequestBody final WatchedTvSeriesDTO watchedTvSeriesDTO
            )
    {

        watchListService.addToWatchList(username,watchedTvSeriesMapper.fromDto(watchedTvSeriesDTO));
    }

    @PreAuthorize("#username == authentication.name || authentication.authorities.contains('Admin')")
    @GetMapping("/{username}/watch-list")
    @ResponseStatus(HttpStatus.OK)
    public List<TvSeriesWatchedDTO> getWatchList(
            @PathVariable(name = "username")  String username
    )
    {
        return watchListService.getTvSeriesByUsername(username).stream().map(tvSeriesMapper::toDtoWatched ).collect(Collectors.toList());
    }


    @PreAuthorize("#username == authentication.name || authentication.authorities.contains('Admin')")
    @GetMapping("/{username}/watch-list/{tvSeriesId}")
    @ResponseStatus(HttpStatus.OK)
    public TvSeriesWatchedDTO getTvSeriesOnWatchList(
            @PathVariable(name = "username")  String username,
             @PathVariable(name = "tvSeriesId")  Integer tvSeriesId
    )
    {
        return tvSeriesMapper.toDtoWatched(watchListService.getTvSeriesOnWatchListById(username,tvSeriesId)) ;
    }

    @PreAuthorize("#username == authentication.name || authentication.authorities.contains('Admin')")
    @PutMapping("/{username}/watch-list/{tvSeriesId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateWatchList(@RequestBody final WatchedTvSeriesDTO watchedTvSeriesDTO,
                           @PathVariable(name = "username")  String username,
                           @PathVariable(name = "tvSeriesId")  Integer tvSeriesId
    )
    {
        watchListService.update(watchedTvSeriesMapper.fromDto(watchedTvSeriesDTO), username,tvSeriesId);
    }


    @PreAuthorize("#username == authentication.name || authentication.authorities.contains('Admin')")
    @DeleteMapping("/{username}/watch-list/{tvSeriesId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWatchList(
            @PathVariable(name = "username")  String username,
            @PathVariable(name = "tvSeriesId")  Integer tvSeriesId
    )
    {
        watchListService.delete(username,tvSeriesId);
    }


}
