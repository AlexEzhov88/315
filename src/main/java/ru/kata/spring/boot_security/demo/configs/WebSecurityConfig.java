package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;
    private final UserDetailsService userDetailsService;


    public WebSecurityConfig(SuccessUserHandler successUserHandler, @Lazy  UserDetailsService userDetailsService) {
        this.successUserHandler = successUserHandler;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/user").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
                .successHandler(successUserHandler)
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .and().cors().and().csrf()
                .disable();

    }

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
//                .passwordEncoder(passwordEncoder());
    }
}



// В классе WebSecurityConfig задаются параметры авторизации и аутентификации пользователя в системе.
// Здесь определяются права доступа к различным страницам в зависимости от ролей пользователя.
//
//Метод configure(HttpSecurity http) устанавливает основные настройки безопасности приложения.
// Здесь определяются разрешения для различных URL-адресов, задается страница авторизации,
// метод аутентификации, параметры входа (логин, пароль), настройки выхода из системы,
// а также включение и отключение защиты от CSRF-атак и Cross-Origin Resource Sharing (CORS).
//
//Метод daoAuthenticationProvider() создает объект провайдера аутентификации DaoAuthenticationProvider,
// который использует для проверки пользовательских данных сервис userDetailsService,
// и задает ему passwordEncoder, который используется для шифрования паролей.
//
//Метод configure(AuthenticationManagerBuilder auth) настраивает менеджер аутентификации,
// который использует userDetailsService для загрузки пользовательских данных из базы данных,
// и passwordEncoder, который используется для проверки паролей.
//
//Класс SuccessUserHandler реализует интерфейс AuthenticationSuccessHandler и задает действия при успешной
// аутентификации пользователя в системе. В данном случае, при успешной аутентификации пользователь будет
// перенаправлен на страницу /user.
//
//Пакет configs содержит также класс WebMvcConfig, который настраивает конфигурацию веб-приложения.
// Здесь определяется использование шаблонизатора Thymeleaf, задаются пути к файлам стилей и скриптам,
// а также ресурсам приложения.