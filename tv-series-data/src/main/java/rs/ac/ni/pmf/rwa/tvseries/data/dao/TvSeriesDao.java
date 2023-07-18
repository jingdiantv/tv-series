package rs.ac.ni.pmf.rwa.tvseries.data.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.TvSeriesEntity;

import java.util.List;


public interface TvSeriesDao extends JpaRepository<TvSeriesEntity,Integer> {

    public List<TvSeriesEntity> findByNameContainingIgnoreCase(String key, Pageable pageable);

}
