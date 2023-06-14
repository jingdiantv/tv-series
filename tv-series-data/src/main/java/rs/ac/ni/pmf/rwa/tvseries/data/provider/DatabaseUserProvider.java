package rs.ac.ni.pmf.rwa.tvseries.data.provider;

import lombok.RequiredArgsConstructor;
import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeries;
import rs.ac.ni.pmf.rwa.tvseries.core.model.User;
import rs.ac.ni.pmf.rwa.tvseries.core.provider.UserProvider;
import rs.ac.ni.pmf.rwa.tvseries.data.dao.TvSeriesDao;
import rs.ac.ni.pmf.rwa.tvseries.data.dao.UserDao;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.TvSeriesEntity;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.UserEntity;
import rs.ac.ni.pmf.rwa.tvseries.data.mapper.TvSeriesEntityMapper;
import rs.ac.ni.pmf.rwa.tvseries.data.mapper.UserEntityMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DatabaseUserProvider implements UserProvider {

    private final UserDao userDao;

    @Override
    public Optional<User> getUserByUsername(String username) {
        Optional<UserEntity> optionalUserEntity=userDao.findById(username);

        if(optionalUserEntity.isEmpty()){
            return Optional.empty();
        }
        UserEntity userEntity=optionalUserEntity.get();



        return Optional.ofNullable(UserEntityMapper.fromEntity(userEntity));
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll().stream()
                .map(UserEntityMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void saveUser(User user) {
        userDao.save(UserEntityMapper.toEntity(user));
    }

    @Override
    public void removeUser(String username) {
    userDao.deleteById(username);
    }

    @Override
    public void updateUser(User user) {
        userDao.save(UserEntityMapper.toEntity(user));
    }
}
