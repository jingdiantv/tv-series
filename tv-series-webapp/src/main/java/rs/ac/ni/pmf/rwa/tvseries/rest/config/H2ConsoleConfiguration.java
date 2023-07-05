package rs.ac.ni.pmf.rwa.tvseries.rest.config;

import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class H2ConsoleConfiguration {

    @Bean
    ServletRegistrationBean<WebServlet> h2ServletRegistration(){
        ServletRegistrationBean<WebServlet> registrationBean=new ServletRegistrationBean<WebServlet>(new WebServlet());
        registrationBean.addUrlMappings("/h2/*");
        return registrationBean;
    }
}