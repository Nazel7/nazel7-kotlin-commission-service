package com.digitalcolliers.commissionservice.configs

import org.springframework.beans.factory.annotation.Configurable
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@EnableWebSecurity
@Configurable
class WebSecurityAdaoter : WebSecurityConfigurerAdapter() {


    @Value("\${user1.username}")
    lateinit var username: String

    @Value("\${user1.password}")
    lateinit var password: String

    @Value("\${user2.username}")
    lateinit var username2: String

    @Value("\${user2.password}")
    lateinit var password2: String

    @Throws(Exception::class, IllegalArgumentException::class)
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.inMemoryAuthentication()?.withUser(username)?.password("{noop}${password}")
            ?.roles("USER")

        auth?.inMemoryAuthentication()?.withUser(username2)?.password("{noop}${password2}")
            ?.roles("USER")


    }

    override fun configure(http: HttpSecurity?) {
        http?.authorizeRequests()?.antMatchers(HttpMethod.OPTIONS, "/**")
            ?.permitAll()
            ?.antMatchers("/")
            ?.permitAll()?.anyRequest()?.fullyAuthenticated()?.and()?.httpBasic()
        http?.csrf()?.disable()
    }
}
