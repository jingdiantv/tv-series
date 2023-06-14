package rs.ac.ni.pmf.rwa.tvseries.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.TvSeriesEntity;


public interface TvSeriesDao extends JpaRepository<TvSeriesEntity,Integer> {


}
