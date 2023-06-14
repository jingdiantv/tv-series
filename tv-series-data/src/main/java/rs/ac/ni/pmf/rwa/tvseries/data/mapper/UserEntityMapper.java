package rs.ac.ni.pmf.rwa.tvseries.data.mapper;

import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeries;
import rs.ac.ni.pmf.rwa.tvseries.core.model.User;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.TvSeriesEntity;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.UserEntity;

public class UserEntityMapper {


    public static User fromEntity(final UserEntity entity)
    {
        return User.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .build();
    }

    public static UserEntity toEntity(final User user)
    {
        return UserEntity.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}
