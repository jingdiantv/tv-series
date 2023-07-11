package rs.ac.ni.pmf.rwa.tvseries.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rs.ac.ni.pmf.rwa.tvseries.core.model.User;
import rs.ac.ni.pmf.rwa.tvseries.core.provider.UserProvider;
import rs.ac.ni.pmf.rwa.tvseries.exception.DuplicateUserException;
import rs.ac.ni.pmf.rwa.tvseries.exception.UnknownUserException;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserProvider userProvider;

    public User getUserByUsername(String username){
        log.info("Getting User with username [{}]",username);
        return userProvider.getUserByUsername(username).orElseThrow( () -> new UnknownUserException(username));
    }

    public List<User> getAllUsers()
    {
        log.info("Getting all Users");
        return userProvider.getAllUsers();
    }

    public void createUser(final User user)
    {
        final String username = user.getUsername();


        if ( userProvider.getUserByUsername(username).isPresent())
        {
            log.warn("Error creating User: User with username[{}] already exists",username);
            throw new DuplicateUserException(username);
        }
        log.info("Creating  User with username[{}]",username);
        userProvider.saveUser(user);
    }

    public void update(final User user, final String username){


        if(userProvider.getUserByUsername(username).isEmpty()){
            log.warn("Error updating User: User with username[{}] dose not exists",username);
            throw new UnknownUserException(username);
        }

        final String newUsername = user.getUsername();

        if(!newUsername.equals(username)){
            if (userProvider.getUserByUsername(newUsername).isPresent())
            {
                log.warn("Error updating User: New username[{}] is already taken",newUsername);
                throw new DuplicateUserException(newUsername);
            }

            userProvider.removeUser(username);
        }
        log.info("Updating  User with username[{}]",username);
        userProvider.updateUser(user);

    }
    public void delete(final String username){
        if(userProvider.getUserByUsername(username).isEmpty()){
            log.warn("Error deleting User: User with username[{}] dose not exists",username);
            throw new UnknownUserException(username);

        }
        log.info("Deleting  User with username[{}]",username);
        userProvider.removeUser(username);

    }


}
