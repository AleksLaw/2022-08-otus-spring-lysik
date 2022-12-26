package otus.security;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(H2)
                .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
                .build();
    }

    @Bean(name = "myPasswordEncoder")
    public PasswordEncoder getPasswordEncoder() {
        DelegatingPasswordEncoder delPasswordEncoder = (DelegatingPasswordEncoder) PasswordEncoderFactories.createDelegatingPasswordEncoder();
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        delPasswordEncoder.setDefaultPasswordEncoderForMatches(bcryptPasswordEncoder);
        return delPasswordEncoder;
    }

    @Bean
    public UserDetailsManager users(DataSource dataSource) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        UserDetails user = User.builder()
                .username("u")
                .passwordEncoder(d -> bCryptPasswordEncoder.encode("u"))
                .roles("USER")
                .build();
        UserDetails user2 = User.builder()
                .username("a")
                .passwordEncoder(d -> bCryptPasswordEncoder.encode("a"))
                .roles("ADMIN")
                .build();
        UserDetails user3 = User.builder()
                .username("k")
                .passwordEncoder(d -> bCryptPasswordEncoder.encode("k"))
                .roles("KGB")
                .build();
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.createUser(user);
        users.createUser(user2);
        users.createUser(user3);
        return users;
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new MySimpleUrlAuthenticationSuccessHandler();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/login/**")
                .permitAll()
                .antMatchers("/book/**")
                .authenticated()
                .antMatchers("/author/**")
                .authenticated()
                .antMatchers("/genre/**")
                .authenticated()
                .antMatchers("/comment/**")
                .hasAnyRole("ADMIN", "KGB")
                .and()
                .rememberMe()
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .successHandler(myAuthenticationSuccessHandler());
        return http.build();
    }
}
