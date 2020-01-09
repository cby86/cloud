package com.spring.cloud.config;

import com.spring.cloud.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@Order(2)
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {
    //认证管理器
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Autowired
    JwtTokenStore jwtTokenStore;

    @Autowired
    JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    CustomerAuthenticationEntryPoint customerAuthenticationEntryPoint;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory() // 使用in-memory存储
                .withClient("client") // client_id
                .secret("$2a$10$DoeJkpCczRp72DZiyXeHRO6ictIipY9yO0t2AScsMeP1JoQN81hqK") // client_secret
                .authorizedGrantTypes("password", "authorization_code", "refresh_token") // 该client允许的授权类型
                //过期
                .accessTokenValiditySeconds(2 * 60 * 1000)
                .scopes("app") // 允许的授权范围
                .redirectUris("http://localhost:8085/spring-user/home");
    }

    /**
     * 认证服务器的安全配置
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        /**
         * oauth2 验证阶段全局错误处理
         */
        security.authenticationEntryPoint(customerAuthenticationEntryPoint);
        security.passwordEncoder(new BCryptPasswordEncoder())
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()") //isAuthenticated():排除anonymous   isFullyAuthenticated():排除anonymous以及remember-me
                .allowFormAuthenticationForClients();  //允许表单认证
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager).
                tokenStore(jwtTokenStore)
                .userDetailsService(userService).
                accessTokenConverter(jwtAccessTokenConverter).exceptionTranslator(new CustomerExceptionTranslator());
    }
}
