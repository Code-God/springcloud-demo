package com.example.authmanager;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
public class Test {

    public static void main(String[] args) {
//        jasyptTest();
//        cryptTest();
        cryptTest1();
    }

    //oauth2.0的加密方式
    public static void cryptTest() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String encode = passwordEncoder.encode("123456");
        log.info("加密后的密码:" + encode);
        log.info("bcrypt密码对比:" + passwordEncoder.matches("123456", encode));
        String md5Password = "{bcrypt}$2a$10$KUia3BGxCcQzhidberS1ieCgbMRb2dp7ONFOxAcCUX8szZFw7lYMO";//MD5加密前的密码为:password
        log.info("MD5密码对比:" + passwordEncoder.matches("123456", md5Password));
    }

    public static void cryptTest1() {
        System.out.println(new BCryptPasswordEncoder().encode("123456")); //数据库中存储这样格式的密码
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        log.info("bcrypt密码对比:" + passwordEncoder.matches("123456", "{bcrypt}$2a$10$yLzfQH9M.wuCcP/0QPB3iOZ.8tgAwxjQPQUjcjnLSH/jsgq9nZxMS"));
        BCrypt.checkpw("123456", "{bcrypt}$2a$10$yLzfQH9M.wuCcP/0QPB3iOZ.8tgAwxjQPQUjcjnLSH/jsgq9nZxMS");
    }

    //对配置文件对称加密
    public static void jasyptTest() {
        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        // application.yml配置的jasypt.encryptor.password
        encryptor.setPassword("abc123");
        // 对root进行加密操作
        System.out.println(encryptor.encrypt("root"));
        // 进行解密操作
        System.out.println(encryptor.decrypt("0oloHvk4pHj4ldMB4zsAF0gEq3uwDhsp"));
    }
}
