package com.fc.servicepay.config;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * yml文件中定义的属性
 * （替代 @Value 的另一种方式）
 */
@Component
@ConfigurationProperties(prefix = "alipay")
@Data
@Slf4j
public class AlipayConfig {

    /**
     * APPID
     */
    private String appId;

    /**
     * 应用私钥
     */
    private String appPrivateKey;

    /**
     * 支付宝公钥
     */
    private String publicKey;

    /**
     * 消息回调 url
     */
    private String notifyUrl;

    @PostConstruct
    public void init() {
        Config config = new Config();
        //基础配置
        config.protocol = "https";
        config.gatewayHost = "openapi-sandbox.dl.alipaydev.com";
        config.signType = "RSA2";

        //业务配置
        config.appId = this.appId;
        config.merchantPrivateKey = this.appPrivateKey;
        config.alipayPublicKey = this.publicKey;
        config.notifyUrl = this.notifyUrl;

        Factory.setOptions(config);
        log.info("支付宝配置初始化完成");
    }
}
