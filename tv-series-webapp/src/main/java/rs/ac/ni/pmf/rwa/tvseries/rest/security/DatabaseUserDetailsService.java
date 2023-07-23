package rs.ac.ni.pmf.rwa.tvseries.rest.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import rs.ac.ni.pmf.rwa.tvseries.data.dao.UserDao;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.UserEntity;

import java.util.Optional;

@RequiredArgsConstructor
public class DatabaseUserDetailsService implements UserDetailsService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      Optional<UserEntity> user=  userDao.findByUsername(username);
      if(user.isEmpty()){
          throw new  UsernameNotFoundException("User with username '"+username+"' dose not exists");
      }

      return new DatabaseUserDetails(user.get(),passwordEncoder);

    }
}
