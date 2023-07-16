package rs.ac.ni.pmf.rwa.tvseries;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SecurityScheme(name = "default",type = SecuritySchemeType.HTTP,scheme = "basic",in= SecuritySchemeIn.HEADER)
@SpringBootApplication(exclude = {
		SecurityAutoConfiguration.class
})
public class TvSeriesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TvSeriesApplication.class, args);
	}

}
