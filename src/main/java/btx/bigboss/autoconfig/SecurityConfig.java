package btx.bigboss.autoconfig;

import btx.bigboss.security.LoginSuccessHandler;
import btx.bigboss.security.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by BaiyangTX on 2017/1/22.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    private static String[] UNSECURITY_RESOURCE = {
            "/webjars/**" , "/images/**" , "/css/**" , "/js/**" ,
            "/" , "/index.html" , "/login.html" , "/itemimage/**"
    } ;


    @Autowired
    UserDetailServiceImpl userdetailService;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler ;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.userDetailsService(userdetailService);

        http.authorizeRequests().antMatchers(UNSECURITY_RESOURCE)
                .permitAll().anyRequest().authenticated();

        http.formLogin().successHandler(loginSuccessHandler);
        http.formLogin().loginPage("/login").permitAll();
        http.csrf().disable();
        // http.formLogin().loginPage("/loginpage") ;

        // http.exceptionHandling().accessDeniedPage("/loginpage?denied") ;

        // http.authorizeRequests().antMatchers("/static/**").permitAll() ;
        http.logout();
    }


}
