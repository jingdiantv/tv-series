package rs.ac.ni.pmf.rwa.tvseries.rest.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDTO {

    String username;
    String password;
}
