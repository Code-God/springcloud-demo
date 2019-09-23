package com.example.authmanager.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;


/**
 * 认证服务核心配置
 * 这个类将定义通过Oauth2验证服务注册哪些应用程序，需要注意的是不能只因为应程序通过OAuth2服务注册过，就认为服务能够访问任何受保护资源。
 * Auth2Config服务定义了哪些应用程序可以使用服务
 */
@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private RedisConnectionFactory connectionFactory;

    /**
     * <p>设置令牌存储方式</p>
     * InMemoryTokenStore 在内存中存储令牌。
     * RedisTokenStore 在Redis缓存中存储令牌。
     * JwkTokenStore 支持使用JSON Web Key (JWK)验证JSON Web令牌(JwT)的子Web签名(JWS)
     * JwtTokenStore 不是真正的存储，不持久化数据，身份和访问令牌可以相互转换。
     * JdbcTokenStore 在数据库存储，需要创建相应的表存储数据
     */
    @Bean
    public TokenStore tokenStore() {
        return new RedisTokenStore(connectionFactory);
    }

    /**
     * <p>设置密码校验器</p>
     * NoOpPasswordEncoder 直接文本比较  equals
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * *用来配置令牌端点(Token Endpoint)的安全约束。
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()//允许客户表单认证. 禁用HTTP Basich Auth的方法是为客户端启用表单身份验证.
                .passwordEncoder(passwordEncoder())//设置oauth_client_details中的密码编码器
                .checkTokenAccess("permitAll()");//对于CheckEndpoint控制器[框架自带的校验]的/oauth/check端点允许所有客户端发送器请求而不会被Spring-security拦截
    }

    /**
     * 覆盖configure方法。这定义了哪些客户端将注册到服务
     * 配置OAuth2的客户端相关信息。使用了数据库存储
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // jdbcTemplate 会查询指定数据源表： oauth_client_details;
        clients.jdbc(dataSource);

//        InMemoryClientDetailsServiceBuilder build = clients.inMemory();
//        if (ArrayUtils.isNotEmpty(oAuth2Properties.getClients())) {
//            for (OAuth2ClientProperties config : oAuth2Properties.getClients()) {
//                build.withClient(config.getClientId())
//                        .secret(passwordEncoder.encode(config.getClientSecret()))
//                        .accessTokenValiditySeconds(config.getAccessTokenValiditySeconds())
//                        .refreshTokenValiditySeconds(60 * 60 * 24 * 15)
//                        .authorizedGrantTypes("refresh_token", "password", "authorization_code")//OAuth2支持的验证模式
//                        .redirectUris("http://www.merryyou.cn")
//                        .scopes("all");
//            }
//        }

//        clients.inMemory()
//                .withClient("123456")
//                // secret密码配置从 Spring Security 5.0开始必须以 {加密方式}+加密后的密码 这种格式填
//        /*
//         *   当前版本5新增支持加密方式：
//         *   bcrypt - BCryptPasswordEncoder (Also used for encoding)
//         *   ldap - LdapShaPasswordEncoder
//         *   MD4 - Md4PasswordEncoder
//         *   MD5 - new MessageDigestPasswordEncoder("MD5")
//         *   noop - NoOpPasswordEncoder
//         *   pbkdf2 - Pbkdf2PasswordEncoder
//         *   scrypt - SCryptPasswordEncoder
//         *   SHA-1 - new MessageDigestPasswordEncoder("SHA-1")
//         *   SHA-256 - new MessageDigestPasswordEncoder("SHA-256")
//         *   sha256 - StandardPasswordEncoder
//         */
////                .secret("{bcrypt}$2a$10$yLzfQH9M.wuCcP/0QPB3iOZ.8tgAwxjQPQUjcjnLSH/jsgq9nZxMS")
////                .secret(new BCryptPasswordEncoder().encode("123456"))
//                .secret("$2a$10$yLzfQH9M.wuCcP/0QPB3iOZ.8tgAwxjQPQUjcjnLSH/jsgq9nZxMS")
//                .scopes("all")
//                .authorizedGrantTypes("authorization_code", "password", "refresh_token")
//                .autoApprove(true);
    }

    /**
     * 该方法定义了AuthorizationServerConfigurer中使用的不同的组件，这段代码告诉Spring使用Spring提供的默认验证管理器和用户详细信息服务。
     *  *配置授权服务器端点的属性和增强功能。
     *  *设置自定义验证规则， token存储设置使用...
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore())
                // 不添加userDetailsService，刷新access_token时会报错
                .userDetailsService(userDetailsService)
                // refreshToken是否可以重复使用。 默认：true;
                .reuseRefreshTokens(false);
    }
}
