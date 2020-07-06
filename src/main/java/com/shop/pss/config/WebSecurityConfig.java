package com.shop.pss.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.pss.common.RestResult;
import com.shop.pss.common.UserUtils;
import com.shop.pss.enums.ResultEnum;
import com.shop.pss.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 王瑞
 * @description
 * @date 2019-02-26 19:01
 */
@Order(-1)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    protected final Logger LOGGER = LoggerFactory.getLogger(WebSecurityConfig.class);
    @Autowired
    UserService userService;

    @Autowired
    CustomMetadataSource metadataSource;

    @Autowired
    UrlAccessDecisionManager urlAccessDecisionManager;

    @Autowired
    AuthenticationAccessDeniedHandler deniedHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/pss/api/wechat/**")
                .antMatchers("/websocket/**")
                .antMatchers("/pss/api/user/retrieve_pwd")
                .antMatchers("/index.html", "/static/**", "/pss/api/login_p", "/favicon.ico");
    }

    /**
     * @param http
     * @throws Exception
     * @creator 王瑞
     * @createtime 2019/2/26 19:04
     * @description: 登录验证核心
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().invalidSessionUrl("/pss/api/login_p");
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(metadataSource);
                        o.setAccessDecisionManager(urlAccessDecisionManager);
                        return o;
                    }
                })
                .and()
                .formLogin().loginPage("/pss/api/login_p").loginProcessingUrl("/pss/api/login")
                .usernameParameter("username").passwordParameter("password")
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest req,
                                                        HttpServletResponse resp,
                                                        AuthenticationException e) throws IOException {
                        resp.setContentType("application/json;charset=utf-8");
                        RestResult respBean = null;
                        if (e instanceof BadCredentialsException ||
                                e instanceof UsernameNotFoundException) {
                            respBean = RestResult.error(ResultEnum.USERNAME_PASSWORD_ERROR);
                        } else if (e instanceof LockedException) {
                            respBean = RestResult.error(ResultEnum.USERNAME_LOCK);
                        } else if (e instanceof CredentialsExpiredException) {
                            respBean = RestResult.error(ResultEnum.PASSWORD_OVERDUE);
                        } else if (e instanceof AccountExpiredException) {
                            respBean = RestResult.error(ResultEnum.USERNAME_OVERDUE);
                        } else if (e instanceof DisabledException) {
                            respBean = RestResult.error(ResultEnum.USERNAME_PROHIBIT);
                        } else {
                            respBean = RestResult.error(ResultEnum.LOGIN_ERROR);
                        }
                        resp.setStatus(401);
                        ObjectMapper om = new ObjectMapper();
                        PrintWriter out = resp.getWriter();
                        out.write(om.writeValueAsString(respBean));
                        out.flush();
                        out.close();
                        LOGGER.info("------ login fail username:{}", req.getParameter("username"));
                    }
                })
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest req,
                                                        HttpServletResponse resp,
                                                        Authentication auth) throws IOException {
                        resp.setContentType("application/json;charset=utf-8");
                        RestResult respBean = RestResult.success(UserUtils.getCurrentHr());
                        ObjectMapper om = new ObjectMapper();
                        PrintWriter out = resp.getWriter();
                        out.write(om.writeValueAsString(respBean));
                        out.flush();
                        out.close();
                        LOGGER.info("------ login success username:{}", req.getParameter("username"));
                    }
                })
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/pss/api/logout")
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        RestResult respBean = RestResult.success("注销成功!");
                        ObjectMapper om = new ObjectMapper();
                        PrintWriter out = resp.getWriter();
                        out.write(om.writeValueAsString(respBean));
                        out.flush();
                        out.close();
                        LOGGER.info("------ logout success username:{}", req.getParameter("username"));
                    }
                })
                .permitAll()
                .and().exceptionHandling().authenticationEntryPoint(macLoginUrlAuthenticationEntryPoint())
                .and().cors().and().csrf().disable()
                .exceptionHandling().accessDeniedHandler(deniedHandler);
    }

    @Bean
    public AuthenticationEntryPoint macLoginUrlAuthenticationEntryPoint() {
        return new MacLoginUrlAuthenticationEntryPoint("/pss/api/login");
    }

}