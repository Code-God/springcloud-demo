package com.example.authmanager.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/oauth")
public class ResourceWeb {

    @GetMapping("/user")
    public Principal user(Principal principal) {

//        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
//        OAuth2Request storedRequest = oAuth2Authentication.getOAuth2Request();
//        Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();
////        OAuth2Authentication
//        // 为了服务端进行token权限隔离 定制OAuth2Authentication
//        OAuth2Authentication authentication = new OAuth2Authentication(storedRequest, userAuthentication);
//        authentication.setDetails(oAuth2Authentication.getDetails());
        //获取当前用户信息


//        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
//        OAuth2Request storedRequest = oAuth2Authentication.getOAuth2Request();
//        Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();
//        // 为了服务端进行token权限隔离 定制OAuth2Authentication
//        CustomOAuth2Authentication customOAuth2Authentication = new CustomOAuth2Authentication(storedRequest, userAuthentication, storedRequest.getAuthorities());
//        customOAuth2Authentication.setDetails(oAuth2Authentication.getDetails());
        return principal;
    }
}
