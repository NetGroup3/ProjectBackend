package com.example.NetProjectBackend.confuguration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable().authorizeRequests()//включаем авторизацию
                    .antMatchers("/", "/signup", "/recovery", "/users").permitAll() //разрешаем полный доступ
                    .anyRequest().authenticated() //требуем авторизацию
                .and()//
                    .formLogin()//включаем форм логин
                    .loginPage("/login")//страничка логин находиться по адресу
                    .permitAll()//разрешаем пользоваться всем
                .and()
                    .logout()//включаем логаут
                    .permitAll();//разрешаем пользоваться всем
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .usersByUsernameQuery("select email, password from client where email=?")
                .authoritiesByUsernameQuery("select email, role form client where email=?");
    }
}






/*    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth
                .inMemoryAuthentication()
                .withUser("222@qweqwe.com")
                .password("{noop}12345678")
                .roles("USER");
    }*/

/*    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("222@qweqwe.com")
                        .password("12345678")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }*/
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .passwordEncoder(NoOpPasswordEncoder.getInstance())
//                .usersByUsernameQuery("select email, password from client where email=?");
//        //author..
//
//    }