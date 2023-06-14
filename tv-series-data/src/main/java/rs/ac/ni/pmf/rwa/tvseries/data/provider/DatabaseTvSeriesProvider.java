package rs.ac.ni.pmf.rwa.tvseries.data.provider;

import lombok.RequiredArgsConstructor;
import rs.ac.ni.pmf.rwa.tvseries.core.provider.TvSeriesProvider;
import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeries;
import rs.ac.ni.pmf.rwa.tvseries.data.dao.TvSeriesDao;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.TvSeriesEntity;
import rs.ac.ni.pmf.rwa.tvseries.data.mapper.TvSeriesEntityMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DatabaseTvSeriesProvider implements TvSeriesProvider {

    private final TvSeriesDao tvSeriesDao;
    @Override
    public Optional<TvSeries> getTvSeriesById(Integer id) {
        Optional<TvSeriesEntity> optionalTvSeriesEntity=tvSeriesDao.findById(id);

        if(optionalTvSeriesEntity.isEmpty()){
            return Optional.empty();
        }
        TvSeriesEntity tvSeriesEntity=optionalTvSeriesEntity.get();



        return Optional.ofNullable(TvSeriesEntityMapper.fromEntity(tvSeriesEntity));
    }

    @Override
    public List<TvSeries> getAllTvSeries() {

        return tvSeriesDao.findAll().stream()
                .map(TvSeriesEntityMapper::fromEntity)
                .collect(Collectors.toList());

    }

    @Override
    public void saveTvSeries(TvSeries tvSeries) {
        tvSeriesDao.save(TvSeriesEntityMapper.toEntity(tvSeries));
    }

    @Override
    public void removeTvSeries(Integer id) {
        tvSeriesDao.deleteById(id);
    }

    @Override
    public void updateTvSeries(TvSeries tvSeries) {
        tvSeriesDao.save(TvSeriesEntityMapper.toEntity(tvSeries));
    }
}
