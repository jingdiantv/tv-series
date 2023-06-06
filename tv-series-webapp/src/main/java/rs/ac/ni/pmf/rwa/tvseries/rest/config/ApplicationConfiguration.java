package rs.ac.ni.pmf.rwa.tvseries.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rs.ac.ni.pmf.rwa.tvseries.core.*;
import rs.ac.ni.pmf.rwa.tvseries.data.DatabaseTvSeriesProvider;
import rs.ac.ni.pmf.rwa.tvseries.data.dao.TvSeriesDao;

@Configuration
public class ApplicationConfiguration
{
//	TV SERIES

	@Bean
	public TvSeriesProvider getTvSeriesProvider(final TvSeriesDao tvSeriesDao){
		return  new DatabaseTvSeriesProvider(tvSeriesDao);
	}

	@Bean TvSeriesService getTvSeriesService(TvSeriesProvider tvSeriesProvider){
		return new TvSeriesService(tvSeriesProvider);
	}



}
