package rs.ac.ni.pmf.rwa.tvseries.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rs.ac.ni.pmf.rwa.tvseries.core.model.User;
import rs.ac.ni.pmf.rwa.tvseries.core.service.UserService;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.UserDTO;
import rs.ac.ni.pmf.rwa.tvseries.rest.mapper.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    private final UserMapper userMapper;
    @GetMapping("/users")
    public List<UserDTO> getAllUsers()
    {
        return userService.getAllUsers().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/users/{username}")
    public UserDTO getUserByUsername(@PathVariable(name = "username") final String username)
    {
        final User user = userService.getUSerByUsername(username);
        return userMapper.toDto(user);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody final UserDTO userDTO)
    {

        userService.createUser(userMapper.fromDto(userDTO));
    }

    @PutMapping("/users/{username}")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateUser(@RequestBody final UserDTO userDTO,@PathVariable(value = "username") String username)
    {
        userService.update(userMapper.fromDto(userDTO), username);
    }
    @DeleteMapping("/users/{username}")
    public void deleteUser(@PathVariable(value = "username") String username)
    {
        userService.delete(username);
    }



}
