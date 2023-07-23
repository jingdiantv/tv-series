package rs.ac.ni.pmf.rwa.tvseries.rest.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import rs.ac.ni.pmf.rwa.tvseries.data.dao.UserDao;
import rs.ac.ni.pmf.rwa.tvseries.data.entity.UserEntity;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.ErrorCode;
import rs.ac.ni.pmf.rwa.tvseries.rest.dto.ErrorDTO;
import rs.ac.ni.pmf.rwa.tvseries.rest.security.DatabaseUserDetailsService;

import java.util.ArrayList;
import java.util.List;


@EnableGlobalMethodSecurity(prePostEnabled=true)
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration {

    private  static  final  String ADMIN="ADMIN";
    private  static  final  String USER="USER";
    private  static  final  String GUEST="GUEST";

    private final ObjectMapper objectMapper;



    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests(registry -> registry
                        .antMatchers("/doc", "/","swagger-ui/**" ).permitAll()

                        .antMatchers(HttpMethod.GET, "/tv-series", "/tv-series/**" ).permitAll()
                        .antMatchers(HttpMethod.POST, "/tv-series"  ).hasAnyAuthority(ADMIN)
                        .antMatchers(HttpMethod.DELETE, "/tv-series"  ).hasAnyAuthority(ADMIN)
                        .antMatchers(HttpMethod.PUT, "/tv-series/**"  ).hasAnyAuthority(ADMIN)

                        .antMatchers(HttpMethod.GET, "/users" ).hasAnyAuthority(ADMIN)
                        .antMatchers(HttpMethod.GET, "/users/**" ).hasAnyAuthority(ADMIN,USER)
                        .antMatchers(HttpMethod.POST, "/users"  ).anonymous()
                        .antMatchers(HttpMethod.DELETE, "/users/**"  ).hasAnyAuthority(ADMIN,USER)
                        .antMatchers(HttpMethod.PUT, "/users/**"  ).hasAnyAuthority(ADMIN,USER)
                        .antMatchers(HttpMethod.PUT, "/users/grant-authority/**" ).hasAnyAuthority(ADMIN)

                        .antMatchers(HttpMethod.GET, "/**/watch-list/**"  ).hasAnyAuthority(ADMIN,USER)
                        .antMatchers(HttpMethod.GET, "/**/watch-list"  ).hasAnyAuthority(ADMIN,USER)
                        .antMatchers(HttpMethod.POST, "/**/watch-list"  ).hasAnyAuthority(USER)
                        .antMatchers(HttpMethod.DELETE, "/**/watch-list/**"  ).hasAnyAuthority(ADMIN,USER)
                        .antMatchers(HttpMethod.PUT, "/**/watch-list/**"  ).hasAnyAuthority(ADMIN,USER)



                        .anyRequest().denyAll()
                )
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                            final ErrorDTO errorDTO = ErrorDTO.builder()
                                    .code(ErrorCode.GENERAL_REQUEST_ERROR)
                                    .details(
                                            "You don't have rights to access '" + request.getServletPath() + "'. " + accessDeniedException.getMessage())
                                    .build();

                            response.getWriter().println(objectMapper.writeValueAsString(errorDTO));
                        }))
                .httpBasic(httpCustomizer -> httpCustomizer
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                            final ErrorDTO errorDTO = ErrorDTO.builder()
                                    .code(ErrorCode.GENERAL_REQUEST_ERROR)
                                    .details("Bad username and/or password. " + authException.getMessage())
                                    .build();

                            response.getWriter().println(objectMapper.writeValueAsString(errorDTO));
                        }))
        ;


        return http.build();


    }

    @Bean
    public UserDetailsService getUserDetailsService(UserDao dao,PasswordEncoder passwordEncoder) {

        return new DatabaseUserDetailsService(dao,passwordEncoder);
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer()
    {
        return web -> web.ignoring().antMatchers("/swagger-ui/*", "/v3/api-docs*/**");
    }
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }



}
