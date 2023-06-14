package rs.ac.ni.pmf.rwa.tvseries.core.service;

import lombok.RequiredArgsConstructor;
import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeries;
import rs.ac.ni.pmf.rwa.tvseries.core.model.User;
import rs.ac.ni.pmf.rwa.tvseries.core.provider.UserProvider;
import rs.ac.ni.pmf.rwa.tvseries.exception.DuplicateIdException;
import rs.ac.ni.pmf.rwa.tvseries.exception.DuplicateUserException;
import rs.ac.ni.pmf.rwa.tvseries.exception.UnknownTvSeriesException;
import rs.ac.ni.pmf.rwa.tvseries.exception.UnknownUserException;

import java.util.List;

@RequiredArgsConstructor
public class UserService {

    private final UserProvider userProvider;

    public User getUSerByUsername(String username){
        return userProvider.getUserByUsername(username).orElseThrow( () -> new UnknownUserException(username));
    }

    public List<User> getAllUsers()
    {
        return userProvider.getAllUsers();
    }

    public void createUser(final User user)
    {
        final String username = user.getUsername();


        if ( userProvider.getUserByUsername(username).isPresent())
        {
            throw new DuplicateUserException(username);
        }

        userProvider.saveUser(user);
    }

    public void update(final User user, final String username){


        if(userProvider.getUserByUsername(username).isEmpty()){
            throw new UnknownUserException(username);
        }

        final String newUsername = user.getUsername();

        if(!newUsername.equals(username)){
            if (userProvider.getUserByUsername(newUsername).isPresent())
            {
                throw new DuplicateUserException(newUsername);
            }

            userProvider.removeUser(username);
        }

        userProvider.updateUser(user);

    }
    public void delete(final String username){
        if(userProvider.getUserByUsername(username).isEmpty()){
            throw new UnknownUserException(username);
        }
        userProvider.removeUser(username);

    }


}
