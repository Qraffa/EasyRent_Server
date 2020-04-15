package com.qraffa.easyrentboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // 开启stomp服务
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 设置消息代理前缀,前缀为/topic的消息会转发给消息代理,再由消息代理广播给客户端
        // --- 接受客户端 订阅 的路径前缀
        registry.enableSimpleBroker("/topic","/queue");
        // 通过前缀过滤出需要被注解方法处理的消息
        // 前缀为/app的消息通过注解方法@MessageMapping处理
        // --- 接受客户端 消息 的路径前缀
        registry.setApplicationDestinationPrefixes("/app");

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 定义前缀为/chat的endpoint,开启sockjs支持
        // 客户端通过该url建立连接
        // 设置允许跨域
        registry.addEndpoint("/websocket")
                .setAllowedOrigins("*")
                .withSockJS();
    }
}