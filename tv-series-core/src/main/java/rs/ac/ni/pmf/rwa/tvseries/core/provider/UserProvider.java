package rs.ac.ni.pmf.rwa.tvseries.core.provider;

import rs.ac.ni.pmf.rwa.tvseries.core.model.TvSeries;
import rs.ac.ni.pmf.rwa.tvseries.core.model.User;
import rs.ac.ni.pmf.rwa.tvseries.shared.Roles;

import java.util.List;
import java.util.Optional;

public interface UserProvider {

    Optional<User> getUserByUsername(final String username);

    List<User> getAllUsers();

    void saveUser(final User user);

    void removeUser(final String username);

    void updateUser(User user,String username);

    void grantAuthority(String username, Roles authority);
}
