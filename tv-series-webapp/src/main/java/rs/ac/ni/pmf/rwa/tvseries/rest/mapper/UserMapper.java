package rs.ac.ni.pmf.rwa.tvseries.rest.mapper;


import org.springframework.stereotype.Component;
import rs.ac.ni.pmf.rwa.tvseries.core.model.User;

import rs.ac.ni.pmf.rwa.tvseries.rest.dto.UserDTO;

@Component
public class UserMapper {
    public UserDTO toDto(final User user)
    {
        return UserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword() )
                .build();
    }

    public User fromDto(final UserDTO dto)
    {
        return User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .build();
    }
}
