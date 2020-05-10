package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author 张杰
 * @date 2020/5/10 13:33
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityHttpConfig extends WebSecurityConfigurerAdapter {

    /**
     * EnableGlobalMethodSecurity：
     * prePostEnabled：标记@preAuthorize 和 @postAuthorize 是否可用
     *      可以在方法上加权限：@preAuthorize("hasRole('ADMIN')"),@preAuthorize("hasAuthorize('ROLE_ADMIN')")
     *
     * securedEnabled: 表示@secured 是否可用
     * jsr250Enabled: 表示jsr-250 注解是否可用
     */

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/css/**","/index").permitAll()    //以"/css/**"开头的资源和"/index"资源不需要验证，外界可以直接访问
                .antMatchers("/user/**").hasRole("USER")        //以"/user/**" 开头的资源需要验证，需要的用户角色为"Role"
                .antMatchers("/blogs/**").hasRole("USER")       //以"/blogs/**" 开头的资源需要验证，需要的用户角色为"Role"
                .and()
                .formLogin().loginPage("/login").failureUrl("/login-error")  //表单的登录地址为"/login"，登录失败的地址为"/login-error"
                .and()
                .exceptionHandling().accessDeniedPage("/401");                  //异常处理会重定向到401页面
        http.logout().logoutSuccessUrl("/");                                     //注销登录成功，重定向到首页

    }

    /**
     *  通过AuthenticationManagerBuilder 在内存中创建了一个认证用户的信息；
     *  做了以下安全防护的工作：
     *  1.应用的每一个请求都都需要认证
     *  2.自动生成了一个登录表单
     *  3.可以用username和password进行认证
     *  4.用户可以注销
     *  5.阻止了CSRF攻击
     *  6.session Fixation保护
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //auth.inMemoryAuthentication().withUser("zj").password("123456").roles("USER");
        auth.userDetailsService(userDetailsService);
    }

    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    public UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager(); // 在内存中存放用户信息
		manager.createUser(User.withUsername("zj").password("123456").roles("USER").build());
		manager.createUser(User.withUsername("admin").password("123456").roles("USER","ADMIN").build());
		return manager;
	}


    }
