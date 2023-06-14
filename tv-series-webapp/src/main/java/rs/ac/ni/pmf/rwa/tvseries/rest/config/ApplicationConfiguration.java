package rs.ac.ni.pmf.rwa.tvseries.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rs.ac.ni.pmf.rwa.tvseries.core.provider.TvSeriesProvider;
import rs.ac.ni.pmf.rwa.tvseries.core.provider.UserProvider;
import rs.ac.ni.pmf.rwa.tvseries.core.service.TvSeriesService;
import rs.ac.ni.pmf.rwa.tvseries.core.service.UserService;
import rs.ac.ni.pmf.rwa.tvseries.data.dao.UserDao;
import rs.ac.ni.pmf.rwa.tvseries.data.provider.DatabaseTvSeriesProvider;
import rs.ac.ni.pmf.rwa.tvseries.data.dao.TvSeriesDao;
import rs.ac.ni.pmf.rwa.tvseries.data.provider.DatabaseUserProvider;

@Configuration
public class ApplicationConfiguration
{
//	TV SERIES

	@Bean
	public TvSeriesProvider getTvSeriesProvider(final TvSeriesDao tvSeriesDao){
		return  new DatabaseTvSeriesProvider(tvSeriesDao);
	}

	@Bean
	TvSeriesService getTvSeriesService(TvSeriesProvider tvSeriesProvider){
		return new TvSeriesService(tvSeriesProvider);
	}



	// USER

	@Bean
	public UserProvider getUserProvider(final UserDao userDao){
		return  new DatabaseUserProvider(userDao);
	}

	@Bean
	UserService getUserService(UserProvider userProvider){
		return new UserService(userProvider);
	}





}
